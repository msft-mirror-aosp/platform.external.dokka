package org.jetbrains.dokka.tests.format

import org.jetbrains.dokka.Formats.DacFormatDescriptor
import org.junit.Test

class DacFormatTest: DacFormatTestCase() {
    override val formatDescriptor = DacFormatDescriptor()
    override val dokkaFormat = "dac"

    @Test fun javaSeeTag() {
        verifyDirectory("javaSeeTag")
    }

    @Test fun javaConstructor() {
        verifyDirectory("javaConstructor")
    }

    @Test fun javaClassLinks() {
        verifyDirectory("javaClassLinks")
    }
}