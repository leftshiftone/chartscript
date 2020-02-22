package chartscript.chart

import chartscript.ChartScript
import chartscript.api.IFontProvider
import chartscript.api.extension.toColor
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.labels.StandardPieSectionLabelGenerator
import org.jfree.chart.plot.PiePlot
import org.jfree.chart.ui.RectangleEdge
import org.jfree.data.general.DefaultPieDataset
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Rectangle
import java.awt.geom.Ellipse2D
import java.text.DecimalFormat
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
class PieChart(private val styler: PieChart.PieStyle.() -> Unit = {},
               fontProvider: IFontProvider) : AbstractChart() {

    private val values = HashMap<String, Number>()
    private val styles = ArrayList<PieValueStyle>()

    private val style = PieStyle(fontProvider)

    @JvmOverloads
    fun value(key: String, value: Number, style: PieValueStyle.() -> Unit = {}) {
        values[key] = value
        styles.add(PieValueStyle(key, getDefaultColor(values.size - 1)).apply(style))
    }

    override fun toChart(): JFreeChart {
        val dataset = DefaultPieDataset()
        values.forEach { dataset.setValue(it.key, it.value) }

        val chart = ChartFactory.createPieChart("", dataset)
        val theme = getTheme()
        theme.apply(chart)
        val plot = chart.plot

        styler(style)
        style.forEach { it(chart, plot as PiePlot, theme) }
        styles.forEach { it.forEach { x -> x(chart, plot as PiePlot, theme) } }

        return chart
    }

    class PieStyle(private val fontProvider: IFontProvider) : ArrayList<(JFreeChart, PiePlot, ChartTheme) -> Unit>() {
        init {
            backgroundPaint("transparent")
            labelBackgroundPaint("transparent")
            labelBackgroundPaint("transparent")
            labelOutlinePaint("transparent")
            labelShadowPaint("transparent")
            shadowPaint("transparent")
            borderPaint("transparent")
            isOutlineVisible(false)
            labelLinkStroke(0)
            labelOutlineStroke(0)
            outlineStroke(0)
            antiAlias(true)
            borderStroke(0)
            isBorderVisible(false)
            legendShape("box", 25)
            legendPosition("right")
            labelPaint("white")
            simpleLabels(true)
            labelFormat("{2}")
            val defaultFont = Optional.ofNullable(ChartScript.settings.getProperty("defaultFont"))
            defaultFont.ifPresent {
                legendFont(it, 24)
                labelFont(it, 24)
            }
        }

        fun title(title: String) = add { x, _, _ -> x.setTitle(title) }
        fun backgroundPaint(paint: String) = add { x, _, _ -> x.backgroundPaint = paint.toColor() }
        fun labelBackgroundPaint(paint: String) = add { _, x, _ -> x.labelBackgroundPaint = paint.toColor() }
        fun labelOutlinePaint(paint: String) = add { _, x, _ -> x.labelOutlinePaint = paint.toColor() }
        fun labelShadowPaint(paint: String) = add { _, x, _ -> x.labelShadowPaint = paint.toColor() }
        fun shadowPaint(paint: String) = add { _, x, _ -> x.shadowPaint = paint.toColor() }
        fun isOutlineVisible(visible: Boolean) = add { _, x, _ -> x.isOutlineVisible = visible }
        fun borderPaint(paint: String) = add { x, _, _ -> x.borderPaint = paint.toColor() }
        fun labelPaint(paint: String) = add { _, x, _ -> x.labelPaint = paint.toColor() }
        fun labelLinkStroke(width: Number) = add { _, x, _ -> x.labelLinkStroke = BasicStroke(width.toFloat()) }
        fun labelOutlineStroke(width: Number) = add { _, x, _ -> x.labelOutlineStroke = BasicStroke(width.toFloat()) }
        fun outlineStroke(width: Number) = add { _, x, _ -> x.outlineStroke = BasicStroke(width.toFloat()) }
        fun borderStroke(width: Number) = add { x, _, _ -> x.borderStroke = BasicStroke(width.toFloat()) }
        fun antiAlias(antiAlias: Boolean) = add { x, _, _ -> x.antiAlias = antiAlias }
        fun isBorderVisible(borderVisible: Boolean) = add { x, _, _ -> x.isBorderVisible = borderVisible }
        fun simpleLabels(simpleLabels: Boolean) = add { _, x, _ -> x.simpleLabels = simpleLabels }
        fun labelFont(font: String, size: Number) = add { _, x, _ -> x.labelFont = fontProvider.getFont(font, size) }

        fun legendPosition(position: String) {
            when (position) {
                "bottom" -> add { x, _, _ -> x.legend.position = RectangleEdge.BOTTOM }
                "left" -> add { x, _, _ -> x.legend.position = RectangleEdge.LEFT }
                "right" -> add { x, _, _ -> x.legend.position = RectangleEdge.RIGHT }
                "top" -> add { x, _, _ -> x.legend.position = RectangleEdge.TOP }
                else -> throw IllegalArgumentException("invalid legend position $position")
            }
        }

        fun legendShape(shape: String, size: Int) {
            when (shape) {
                "box" -> add { _, x, _ -> x.legendItemShape = Rectangle(size, size) }
                "circle" -> add { _, x, _ -> x.legendItemShape = Ellipse2D.Double(0.0, 0.0, size.toDouble(), size.toDouble()) }
                else -> throw IllegalArgumentException("invalid legend shape $shape")
            }
        }

        fun legendFont(font: String, size: Int) {
            add { x, _, _ -> x.legend.itemFont = fontProvider.getFont(font, size) }
        }

        fun labelFormat(format: String) {
            val gen = StandardPieSectionLabelGenerator(format, DecimalFormat("0"), DecimalFormat("0%"))
            add { _, x, _ -> x.labelGenerator = gen }
        }
    }

    class PieValueStyle(val key: String, color: Color) : ArrayList<(JFreeChart, PiePlot, ChartTheme) -> Unit>() {
        init {
            background(color)
            explodePercent(0.05)
        }

        fun background(paint: Color) = add { _, x, _ -> x.setSectionPaint(key, paint) }
        fun background(paint: String) = add { _, x, _ -> x.setSectionPaint(key, paint.toColor()) }
        fun explodePercent(percent: Double) = add { _, x, _ -> x.setExplodePercent(key, percent) }
    }

}
