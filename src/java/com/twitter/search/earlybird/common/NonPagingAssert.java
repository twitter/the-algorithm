package com.tw ter.search.earlyb rd.common;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;

/**
 * W n  ncre nted, a non-pag ng alert w ll be tr ggered. Use t  to assert for bad cond  ons
 * that should generally never happen.
 */
publ c class NonPag ngAssert {
    pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(NonPag ngAssert.class);

    pr vate stat c f nal Str ng ASSERT_STAT_PREF X = "non_pag ng_assert_";

    pr vate f nal Str ng na ;
    pr vate f nal SearchRateCounter assertCounter;

    publ c NonPag ngAssert(Str ng na ) {
        t .na  = na ;
        t .assertCounter = SearchRateCounter.export(ASSERT_STAT_PREF X + na );
    }

    publ c vo d assertFa led() {
        LOG.error("NonPag ngAssert fa led: {}", na );
        assertCounter. ncre nt();
    }

    publ c stat c vo d assertFa led(Str ng na ) {
        NonPag ngAssert nonPag ngAssert = new NonPag ngAssert(na );
        nonPag ngAssert.assertFa led();
    }
}
