package chartscript.chart

import chartscript.ChartScript
import chartscript.api.IFontProvider
import chartscript.api.extension.toColor
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.plot.PlotOrientation.HORIZONTAL
import org.jfree.chart.plot.PlotOrientation.VERTICAL
import org.jfree.chart.renderer.category.BarRenderer
import org.jfree.chart.renderer.category.StandardBarPainter
import org.jfree.chart.ui.RectangleEdge
import org.jfree.data.general.DatasetUtils
import java.awt.BasicStroke
import java.awt.Color
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
class BarChart(private val xLabel: String,
               private val yLabel: String,
               private val styler: BarChart.BarStyle.() -> Unit = {},
               fontProvider: IFontProvider) : AbstractChart() {

    private val values = ArrayList<DoubleArray>()

    private val style = BarStyle(fontProvider)

    fun value(vararg values: Number) {
        this.values.add(values.map { it.toDouble() }.toDoubleArray())
    }

    override fun toChart(): JFreeChart {
        val array: Array<DoubleArray> = values.toTypedArray()
        val dataset = DatasetUtils.createCategoryDataset("", "", array)

        val chart = ChartFactory.createBarChart("", xLabel, yLabel, dataset)
        val theme = getTheme()
        theme.apply(chart)
        BarRenderer.setDefaultBarPainter(StandardBarPainter())
        val plot = chart.categoryPlot

        (0..7).forEach { style.seriesPaint(it, getDefaultColor(it)) }
        styler(style)
        style.forEach { it(chart, plot, theme) }

        return chart
    }

    class BarStyle(private val fontProvider: IFontProvider) : ArrayList<(JFreeChart, CategoryPlot, ChartTheme) -> Unit>() {
        init {
            backgroundPaint("transparent")
            borderPaint("transparent")
            isOutlineVisible(false)
            outlineStroke(0)
            antiAlias(true)
            borderStroke(0)
            isBorderVisible(false)
            legendPosition("right")

            val defaultFont = Optional.ofNullable(ChartScript.settings.getProperty("defaultFont"))
            defaultFont.ifPresent {
                legendFont(it, 24)
                labelFont(it, 16)
            }
        }

        fun title(title: String) = add { x, _, _ -> x.setTitle(title) }
        fun backgroundPaint(paint: String) = add { x, _, _ -> x.backgroundPaint = paint.toColor() }
        fun isOutlineVisible(visible: Boolean) = add { _, x, _ -> x.isOutlineVisible = visible }
        fun borderPaint(paint: String) = add { x, _, _ -> x.borderPaint = paint.toColor() }
        fun outlineStroke(width: Number) = add { _, x, _ -> x.outlineStroke = BasicStroke(width.toFloat()) }
        fun borderStroke(width: Number) = add { x, _, _ -> x.borderStroke = BasicStroke(width.toFloat()) }
        fun antiAlias(antiAlias: Boolean) = add { x, _, _ -> x.antiAlias = antiAlias }
        fun isBorderVisible(borderVisible: Boolean) = add { x, _, _ -> x.isBorderVisible = borderVisible }
        fun vertical(vertical: Boolean) = add { _, x, _ -> x.orientation = if (vertical) VERTICAL else HORIZONTAL }
        fun seriesPaint(series: Int, paint: String) = add { _, x, _ -> x.renderer.setSeriesPaint(series, paint.toColor()) }
        fun seriesPaint(series: Int, paint: Color) = add { _, x, _ -> x.renderer.setSeriesPaint(series, paint) }
        fun labelFont(font: String, size:Number) = add { _, x, _ -> x.renderer.defaultItemLabelFont = fontProvider.getFont(font, size) }

        fun legendPosition(position: String) {
            when (position) {
                "bottom" -> add { x, _, _ -> x.legend.position = RectangleEdge.BOTTOM }
                "left" -> add { x, _, _ -> x.legend.position = RectangleEdge.LEFT }
                "right" -> add { x, _, _ -> x.legend.position = RectangleEdge.RIGHT }
                "top" -> add { x, _, _ -> x.legend.position = RectangleEdge.TOP }
                else -> throw IllegalArgumentException("invalid legend position $position")
            }
        }

        fun legendFont(font: String, size: Int) {
            add { x, _, _ -> x.legend.itemFont = fontProvider.getFont(font, size) }
        }

    }

}
