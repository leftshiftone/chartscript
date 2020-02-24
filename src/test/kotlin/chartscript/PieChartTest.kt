package chartscript

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input

class PieChartTest {

    @Test
    fun `render pie chart`() {
        val bytes = ChartScript.piechart {
            value("Java", 17)
            value("C/C++", 10)
            value("Python", 7)
            value("C#", 5)
            value("Javascript", 3)
        }

        val source1 = Input.fromByteArray(bytes).build()
        val source2 = Input.fromStream(PieChartTest::class.java.getResourceAsStream("/piechart.svg")).build()

        val diff = DiffBuilder.compare(source1).withTest(source2)
                .ignoreComments()
                .ignoreWhitespace()
                .build()

        assertThat(diff.hasDifferences())
                .withFailMessage(diff.differences.joinToString { "\n${it}" })
                .isFalse()
    }

}
