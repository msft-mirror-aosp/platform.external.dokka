package org.jetbrains.dokka.tests.format

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.name.Names
import org.jetbrains.dokka.*
import org.jetbrains.dokka.Formats.JavaLayoutHtmlFormatDescriptorBase
import org.jetbrains.dokka.Formats.JavaLayoutHtmlFormatGenerator
import org.jetbrains.dokka.Utilities.bind
import org.jetbrains.dokka.tests.verifyJavaOutput
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import java.net.URI

abstract class DacFormatTestCase {
    abstract val formatDescriptor: JavaLayoutHtmlFormatDescriptorBase
    abstract val dokkaFormat: String
    @get:Rule
    var folder = TemporaryFolder()

    val injector: Injector by lazy {
        val options =
            DocumentationOptions(
                folder.toString(),
                dokkaFormat,
                apiVersion = null,
                languageVersion = null,
                generateClassIndexPage = false,
                generatePackageIndexPage = false,
                noStdlibLink = false,
                noJdkLink = false,
                collectInheritedExtensionsFromLibraries = true
            )

        Guice.createInjector(Module { binder ->

            binder.bind<Boolean>().annotatedWith(Names.named("generateClassIndex")).toInstance(false)
            binder.bind<Boolean>().annotatedWith(Names.named("generatePackageIndex")).toInstance(false)

            binder.bind<String>().annotatedWith(Names.named("dacRoot")).toInstance("")
            binder.bind<String>().annotatedWith(Names.named("outlineRoot")).toInstance("")
            binder.bind<File>().annotatedWith(Names.named("outputDir")).toInstance(folder.root)

            binder.bind<DocumentationOptions>().toProvider { options }
            binder.bind<DokkaLogger>().toInstance(object : DokkaLogger {
                override fun info(message: String) {
                    println(message)
                }

                override fun warn(message: String) {
                    println("WARN: $message")
                }

                override fun error(message: String) {
                    println("ERROR: $message")
                }
            })

            formatDescriptor.configureOutput(binder)
        })
    }

    protected fun verifyJavaHtmlNode(fileName: String, withKotlinRuntime: Boolean = false) {
        verifyJavaHtmlNodes(fileName, withKotlinRuntime) { model -> model.members.single().members }
    }

    private fun verifyJavaHtmlNodes(fileName: String,
                                    withKotlinRuntime: Boolean = false,
                                    format: String = dokkaFormat,
                                    nodeFilter: (DocumentationModule) -> List<DocumentationNode>
    ) {
        verifyJavaOutput("testdata/format/$dokkaFormat/$fileName.java",".html", format = format, withKotlinRuntime = withKotlinRuntime) { model, output ->
            buildPagesAndReadInto(model, nodeFilter(model), output)
        }
    }

    protected fun buildPagesAndReadInto(model: DocumentationNode, nodes: List<DocumentationNode>, sb: StringBuilder) =
        with(injector.getInstance(Generator::class.java)) {
            this as JavaLayoutHtmlFormatGenerator
            buildPages(listOf(model))
            val byLocations = nodes.groupBy { mainUri(it) }
            val tmpFolder = folder.root.toURI().resolve(model.name+"/")
            byLocations.forEach { (loc, _) ->
                sb.append(tmpFolder.resolve(URI("/").relativize(loc)).toURL().readText())
            }
        }
}