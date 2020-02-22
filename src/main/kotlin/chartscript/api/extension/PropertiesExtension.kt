package chartscript.api.extension

import java.util.*

fun Properties.load(path: String): Properties {
    val stream = javaClass.getResourceAsStream(path)
    if (stream != null) {
        this.load(stream)
    }
    return this
}
