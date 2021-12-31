package com.max.rhymes.utils

import com.max.rhymes.Response
import com.max.rhymes.myEnum.HtmlTags
import com.max.rhymes.myEnum.NetworkResponse
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class NetworkUtils {
    private lateinit var allHtmlResponse: String
    private val noMatches=-1
    private fun getAllHtmlResponse(wordForRhymes: String): String {
        return try {
            val url = URL("${HtmlTags.BASE_URL.tag}$wordForRhymes")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream = httpURLConnection.inputStream
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")
            httpURLConnection.disconnect()
            allHtmlResponse = scanner.next()
            allHtmlResponse
        } catch (e: Throwable) {
            allHtmlResponse = NetworkResponse.NOT_INTERNET.response
            allHtmlResponse
        }

    }

    private fun getPartHtmlWithRhymes(allHtml: String): String {
        return when (allHtml) {
            NetworkResponse.NOT_INTERNET.response -> allHtml
            else -> {
                try {
                    val upperIndex =
                        allHtml.indexOf(HtmlTags.UPPER_BOUND_HTML_WITH_RHYMES.tag) + HtmlTags.UPPER_BOUND_HTML_WITH_RHYMES.tag.length
                    val lowerIndex =
                        allHtml.indexOf(HtmlTags.LOWER_BOUND_HTML_WITH_RHYMES.tag, upperIndex)
                    allHtml.substring(upperIndex, lowerIndex)
                } catch (e: Throwable) {
                    NetworkResponse.RHYMES_NOT_FIND.response
                }

            }
        }

    }

    private fun getNumberAccent(allHtml: String): Int {

        return try {
            val upperIndex =
                allHtml.indexOf(" слог. Качественный поиск, учитывающий звучание") - 1
            allHtml[upperIndex].digitToInt()
        } catch (e: Throwable) {
            noMatches
        }
    }


    private fun extractRhymes(partHTMLWithRhymes: String): String {
        return when (partHTMLWithRhymes) {
            NetworkResponse.NOT_INTERNET.response -> partHTMLWithRhymes
            NetworkResponse.RHYMES_NOT_FIND.response -> partHTMLWithRhymes
            else -> {
                var upperIndex =
                    partHTMLWithRhymes.indexOf(HtmlTags.OPENING_TAG_RHYME.tag) + HtmlTags.OPENING_TAG_RHYME.tag.length
                var lowerIndex =
                    partHTMLWithRhymes.indexOf(HtmlTags.CLOSING_TAG_RHYME.tag, upperIndex)
                var rhymes = partHTMLWithRhymes.substring(upperIndex, lowerIndex)
                while (partHTMLWithRhymes.indexOf(
                        HtmlTags.OPENING_TAG_RHYME.tag,
                        lowerIndex
                    ) != noMatches
                ) {
                    upperIndex = partHTMLWithRhymes.indexOf(
                        HtmlTags.OPENING_TAG_RHYME.tag,
                        lowerIndex
                    ) + HtmlTags.OPENING_TAG_RHYME.tag.length
                    lowerIndex =
                        partHTMLWithRhymes.indexOf(HtmlTags.CLOSING_TAG_RHYME.tag, upperIndex)
                    rhymes = "$rhymes ${partHTMLWithRhymes.substring(upperIndex, lowerIndex)}"
                }
                return rhymes
            }
        }

    }

    fun getResponse(wordForRhymes: String): Response {
        return when (wordForRhymes) {
            NetworkResponse.EMPTY_WORD.response -> Response(wordForRhymes, -1)
            else -> {
                when (val rhymes =
                    extractRhymes(getPartHtmlWithRhymes(getAllHtmlResponse(wordForRhymes)))) {
                    HtmlTags.ERROR_RHYMES_NOT_FIND.tag -> Response(
                        NetworkResponse.RHYMES_NOT_FIND.response,
                        noMatches
                    )
                    else -> Response(rhymes, getNumberAccent(allHtmlResponse))
                }

            }
        }
    }
}