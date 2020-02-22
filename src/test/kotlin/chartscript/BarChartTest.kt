package chartscript

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input

class BarChartTest {

    @Test
    fun `render bar chart`() {
        val bytes = ChartScript.barchart("ABC", "XYZ", {}) {
            value(10, 15, 20)
            value(5, 17, 12)
            value(14, 19, 27)
        }

        val source1 = Input.fromByteArray(bytes).build()
        val source2 = Input.fromStream(BarChartTest::class.java.getResourceAsStream("/barchart.svg")).build()

        val diff = DiffBuilder.compare(source1).withTest(source2)
                .ignoreComments()
                .ignoreWhitespace()
                .build()
        Assertions.assertFalse(diff.hasDifferences())
    }

}
