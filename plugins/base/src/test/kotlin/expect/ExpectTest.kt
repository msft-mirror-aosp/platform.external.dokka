package expect

import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class ExpectTest : AbstractExpectTest() {
    private val ignores: List<String> = listOf(
        "*.js",
        "*.css",
        "*.svg",
        "*.map"
    )

    @TestFactory
    fun expectTest() = testDir?.dirsWithFormats(formats).orEmpty().map { (p, f) ->
        dynamicTest("${p.fileName}-$f") { testOutputWithExcludes(p, f, ignores) }
    }
}