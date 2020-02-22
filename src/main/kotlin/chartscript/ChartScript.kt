package chartscript

import chartscript.api.extension.load
import chartscript.chart.BarChart
import chartscript.chart.BarChart.BarStyle
import chartscript.chart.PieChart
import chartscript.chart.PieChart.PieStyle
import chartscript.font.TrueTypeFontProvider
import java.util.*

class ChartScript {

    companion object {
        @JvmStatic
        fun piechart(style: PieStyle.() -> Unit = {}, config: PieChart.() -> Unit): ByteArray {
            return PieChart(style, TrueTypeFontProvider()).apply(config).render()
        }
        @JvmStatic
        fun barchart(xLabel:String, yLabel:String, style: BarStyle.() -> Unit = {}, config: BarChart.() -> Unit): ByteArray {
            return BarChart(xLabel, yLabel, style, TrueTypeFontProvider()).apply(config).render()
        }

        val settings = Properties().load("/META-INF/chartscript.properties")
    }

}
