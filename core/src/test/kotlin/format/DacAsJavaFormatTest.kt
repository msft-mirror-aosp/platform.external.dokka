package org.jetbrains.dokka.tests.format

import com.google.inject.Guice.createInjector
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.name.Names
import org.jetbrains.dokka.*
import org.jetbrains.dokka.Formats.DacAsJavaFormatDescriptor
import org.jetbrains.dokka.Formats.DacFormatDescriptor
import org.jetbrains.dokka.Formats.JavaLayoutHtmlFormatGenerator
import org.jetbrains.dokka.Utilities.bind
import org.jetbrains.dokka.tests.verifyJavaOutput
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.net.URI

class DacAsJavaFormatTest: DacFormatTestCase() {
    override val formatDescriptor = DacAsJavaFormatDescriptor()
    override val dokkaFormat = "dac-as-java"

    @Test fun javaSeeTag() {
        verifyJavaHtmlNode("javaSeeTag")
    }

    @Test fun javaConstructor() {
        verifyJavaHtmlNode("javaConstructor")
    }

    @Test fun javaDefaultConstructor() {
        verifyJavaHtmlNode("javaDefaultConstructor")
    }
}