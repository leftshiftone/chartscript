package chartscript.api

import java.awt.Font

interface IFontProvider {
    fun getFont(name:String, size:Number = 10, style:Int = Font.PLAIN):Font
}
