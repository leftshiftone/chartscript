package chartscript.font

import chartscript.api.IFontProvider
import chartscript.chart.PieChart
import java.awt.Font
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class TrueTypeFontProvider() : IFontProvider {

    private val fonts = ConcurrentHashMap<String, Font>()
    private val fontMap = Properties()

    init {
        fontMap.load(TrueTypeFontProvider::class.java.getResourceAsStream("/META-INF/chartscript-fonts.properties"))
    }

    override fun getFont(name: String, size: Number, style: Int): Font {
        if (!fonts.containsKey(name) && fontMap.containsKey(name)) {
            synchronized(fonts) {
                if (!fonts.containsKey(name)) {
                    val stream = PieChart::class.java.getResourceAsStream("${fontMap[name]}")
                    fonts[name] = Font.createFont(Font.TRUETYPE_FONT, stream)
                }
            }
        }
        val font = fonts[name]!!
        return font.deriveFont(size.toFloat())
    }
}
