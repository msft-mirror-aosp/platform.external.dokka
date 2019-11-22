package org.jetbrains.dokka.tests.format

import org.jetbrains.dokka.*
import org.jetbrains.dokka.tests.FileGeneratorTestCase
import org.jetbrains.dokka.tests.verifyJavaOutput
import org.jetbrains.dokka.tests.verifyOutput
import org.junit.Test

class DacAsJavaFormatTest: FileGeneratorTestCase() {
    override val formatService = HtmlFormatService(fileGenerator, KotlinLanguageService(), HtmlTemplateService.default(), listOf())

    @Test fun javaSeeTag() {
        verifyJavaHtmlNode("javaSeeTag")
    }

    private fun verifyJavaHtmlNode(fileName: String, withKotlinRuntime: Boolean = false) {
        verifyJavaHtmlNodes(fileName, withKotlinRuntime) { model -> model.members.single().members }
    }

    private fun verifyJavaHtmlNodes(fileName: String,
                                    withKotlinRuntime: Boolean = false,
                                    format: String = "dac-as-java",
                                    nodeFilter: (DocumentationModule) -> List<DocumentationNode>
    ) {
        verifyJavaOutput("testdata/format/dac-as-java/$fileName.java",".html", format = format, withKotlinRuntime = withKotlinRuntime) { model, output ->
            buildPagesAndReadInto(nodeFilter(model), output)
        }
    }
}