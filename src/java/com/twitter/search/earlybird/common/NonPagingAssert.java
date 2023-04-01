package com.twitter.search.earlybird.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.metrics.SearchRateCounter;

/**
 * When incremented, a non-paging alert will be triggered. Use this to assert for bad conditions
 * that should generally never happen.
 */
public class NonPagingAssert {
    private static final Logger LOG = LoggerFactory.getLogger(NonPagingAssert.class);

    private static final String ASSERT_STAT_PREFIX = "non_paging_assert_";

    private final String name;
    private final SearchRateCounter assertCounter;

    public NonPagingAssert(String name) {
        this.name = name;
        this.assertCounter = SearchRateCounter.export(ASSERT_STAT_PREFIX + name);
    }

    public void assertFailed() {
        LOG.error("NonPagingAssert failed: {}", name);
        assertCounter.increment();
    }

    public static void assertFailed(String name) {
        NonPagingAssert nonPagingAssert = new NonPagingAssert(name);
        nonPagingAssert.assertFailed();
    }
}
