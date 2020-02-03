package org.jetbrains.dokka.tests.format

import org.jetbrains.dokka.Formats.DacAsJavaFormatDescriptor
import org.junit.Test

class DacAsJavaFormatTest: DacFormatTestCase() {
    override val formatDescriptor = DacAsJavaFormatDescriptor()
    override val dokkaFormat = "dac-as-java"

    @Test
    fun javaSeeTag() {
        verifyDirectory("javaSeeTag")
    }

    @Test
    fun javaConstructor() {
        verifyDirectory("javaConstructor")
    }

    @Test
    fun javaDefaultConstructor() {
        verifyDirectory("javaDefaultConstructor")
    }

    @Test
    fun javaInheritedMethods() {
        verifyDirectory("inheritedMethods")
    }

    @Test fun javaMethodVisibilities() {
        verifyDirectory("javaMethodVisibilities")
    }

    @Test fun javaClassLinks() {
        verifyDirectory("javaClassLinks")
    }
}
