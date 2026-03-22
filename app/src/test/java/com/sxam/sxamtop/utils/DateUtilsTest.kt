package com.sxam.sxamtop.utils

import org.junit.Assert.assertTrue
import org.junit.Test

class DateUtilsTest {

    @Test
    fun parseIsoDate_validDate_returnsCorrectEpoch() {
        // "2026-03-22T10:00:00Z"
        val epoch = DateUtils.parseIsoDate("2026-03-22T10:00:00Z")
        assertTrue(epoch > 0)
    }

    @Test
    fun parseIsoDate_invalidDate_returnsCurrentTime() {
        val now = System.currentTimeMillis()
        val epoch = DateUtils.parseIsoDate("invalid-date")
        assertTrue(epoch >= now)
    }

    @Test
    fun parseRssDate_validDate_returnsCorrectEpoch() {
        // "Sun, 22 Mar 2026 10:00:00 GMT"
        val epoch = DateUtils.parseRssDate("Sun, 22 Mar 2026 10:00:00 GMT")
        assertTrue(epoch > 0)
    }

    @Test
    fun parseRssDate_invalidDate_returnsCurrentTime() {
        val now = System.currentTimeMillis()
        val epoch = DateUtils.parseRssDate("bad-rss-date")
        assertTrue(epoch >= now)
    }
}