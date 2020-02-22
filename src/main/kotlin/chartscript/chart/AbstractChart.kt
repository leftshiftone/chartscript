package chartscript.chart

import org.apache.batik.dom.GenericDOMImplementation
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.svggen.SVGGraphics2D.DEFAULT_XML_ENCODING
import org.jfree.chart.ChartTheme
import org.jfree.chart.JFreeChart
import org.jfree.chart.StandardChartTheme
import org.jfree.chart.renderer.category.StandardBarPainter
import org.jfree.chart.ui.RectangleInsets
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import kotlin.random.Random


abstract class AbstractChart {

    protected abstract fun toChart():JFreeChart

    fun render(width:Double = 640.0, height:Double = 480.0): ByteArray {
        val svgDom = GenericDOMImplementation.getDOMImplementation()
        val document = svgDom.createDocument(null, "svg", null)
        val svgGraphics = SVGGraphics2D(document)
        toChart().draw(svgGraphics, Rectangle2D.Double(0.0, 0.0, width, height), null)

        val oos = ByteArrayOutputStream()
        val writer = OutputStreamWriter(oos, DEFAULT_XML_ENCODING)
        svgGraphics.stream(writer)
        writer.flush()
        writer.close()
        return oos.toByteArray()
    }

    protected fun getTheme():ChartTheme {
        val theme = StandardChartTheme.createJFreeTheme() as StandardChartTheme

        theme.titlePaint = Color.decode("#4572a7")
        theme.rangeGridlinePaint = Color.decode("#C0C0C0")
        theme.plotBackgroundPaint = Color.white
        theme.chartBackgroundPaint = Color.white
        theme.gridBandPaint = Color.red
        theme.axisOffset = RectangleInsets(0.0, 0.0, 0.0, 0.0)
        theme.barPainter = StandardBarPainter()
        theme.axisLabelPaint = Color.decode("#666666")

        return theme
    }

    protected fun getDefaultColor(index:Int):Color {
        return when (index) {
            0 -> Color(25, 25, 70)
            1 -> Color(5, 85, 105)
            2 -> Color(5, 130, 150)
            3 -> Color(5, 225, 185)
            4 -> Color(15, 245, 200)
            5 -> Color(15, 190, 200)
            6 -> Color(10, 150, 160)
            7 -> Color(5, 80, 135)
            else -> Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
        }
    }

}
