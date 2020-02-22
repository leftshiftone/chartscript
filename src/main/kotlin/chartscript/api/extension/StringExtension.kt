package chartscript.api.extension

import java.awt.Color

fun String.toColor():Color {
    return when {
        this.startsWith("#") -> Color.decode(this)
        this.startsWith("rgb(") -> {
            val str = this.substring(4, this.length - 1)
            val arr = str.split(",").map { it.trim().toInt() }

            Color(arr[0], arr[1], arr[2])
        }
        // TODO: move to properties file
        this.toLowerCase() == "black" -> Color.BLACK
        this.toLowerCase() == "blue" -> Color.BLUE
        this.toLowerCase() == "cyan" -> Color.CYAN
        this.toLowerCase() == "darkgray" -> Color.DARK_GRAY
        this.toLowerCase() == "gray" -> Color.GRAY
        this.toLowerCase() == "lightgray" -> Color.LIGHT_GRAY
        this.toLowerCase() == "green" -> Color.GREEN
        this.toLowerCase() == "magenta" -> Color.MAGENTA
        this.toLowerCase() == "orange" -> Color.ORANGE
        this.toLowerCase() == "pink" -> Color.PINK
        this.toLowerCase() == "red" -> Color.RED
        this.toLowerCase() == "white" -> Color.WHITE
        this.toLowerCase() == "yellow" -> Color.YELLOW
        this.toLowerCase() == "transparent" -> Color(0f, 0f, 0f, 0f)

        else -> throw IllegalArgumentException("cannot cast $this to a Color instance")
    }
}
