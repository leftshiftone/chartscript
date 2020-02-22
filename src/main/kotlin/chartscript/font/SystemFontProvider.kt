package chartscript.font

import chartscript.api.IFontProvider
import java.awt.Font

class SystemFontProvider:IFontProvider {
    override fun getFont(name: String, size:Number, style:Int): Font {
        return Font(name, style, size.toInt())
    }
}
