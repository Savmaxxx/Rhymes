package com.max.rhymes.myEnum

enum class HtmlTags(val tag: String) {
    UPPER_BOUND_HTML_WITH_RHYMES("<!-- adapt rifme сверху -->"),
    LOWER_BOUND_HTML_WITH_RHYMES("<!-- native rifme снизу -->"),
    OPENING_TAG_RHYME("data-w="),
    CLOSING_TAG_RHYME(" class="),
    BASE_URL("https://rifme.net/r/"),
    ERROR_RHYMES_NOT_FIND("ass=\"marginTop\">Нажмите на ударную гласную букву</h2><form method=\"post\" action=\"/u/\" accept-charset=\"UTF-8\"");
}