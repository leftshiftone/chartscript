package chartscript

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.OS.WINDOWS
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input
import java.nio.file.Files

class BarChartTest {

    @Test
    fun `render bar chart`() {
        val bytes = ChartScript.barchart("ABC", "XYZ", {}) {
            value(10, 15, 20)
            value(5, 17, 12)
            value(14, 19, 27)
        }

        val actualSvgFile = Files.createTempFile(this::class.java.simpleName, ".svg")
        Files.write(actualSvgFile.toAbsolutePath(), bytes)
        val source1 = Input.fromByteArray(bytes).build()
        val source2 =
            Input.fromStream(BarChartTest::class.java.getResourceAsStream("/barchart-${if (WINDOWS.isCurrentOs) "win" else "unix"}.svg"))
                .build()

        val diff = DiffBuilder.compare(source1).withTest(source2)
            .ignoreComments()
            .ignoreWhitespace()
            .build()

        assertThat(diff.hasDifferences())
            .withFailMessage(diff.differences.joinToString { "\n${it}" })
            .isFalse()
    }

}
