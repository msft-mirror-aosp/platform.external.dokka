package org.jetbrains.dokka

import java.io.File

interface HtmlTemplateService {
    fun appendHeader(to: StringBuilder, title: String?, basePath: File)
    fun appendFooter(to: StringBuilder)

    companion object {
        fun default(css: String? = null): HtmlTemplateService {
            return object : HtmlTemplateService {
                override fun appendFooter(to: StringBuilder) {
                    if (!to.endsWith('\n')) {
                        to.append('\n')
                    }
                    to.appendLine("</BODY>")
                    to.appendLine("</HTML>")
                }
                override fun appendHeader(to: StringBuilder, title: String?, basePath: File) {
                    to.appendLine("<HTML>")
                    to.appendLine("<HEAD>")
                    to.appendLine("<meta charset=\"UTF-8\">")
                    if (title != null) {
                        to.appendLine("<title>$title</title>")
                    }
                    if (css != null) {
                        val cssPath = basePath.resolve(css).toUnixString()
                        to.appendLine("<link rel=\"stylesheet\" href=\"$cssPath\">")
                    }
                    to.appendLine("</HEAD>")
                    to.appendLine("<BODY>")
                }
            }
        }
    }
}


