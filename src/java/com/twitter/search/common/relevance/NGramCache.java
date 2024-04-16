package com.tw ter.search.common.relevance;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Locale;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.collect. mmutableL st;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.pengu n.search.f lter.Str ngMatchF lter;
 mport com.tw ter.ut l.Durat on;

/**
 * t  Cac  for Trends
 */
publ c class NGramCac  {
  pr vate stat c f nal  nt DEFAULT_MAX_CACHE_S ZE = 5000;
  pr vate stat c f nal long DEFAULT_CACHE_ TEM_TTL_SEC = 24 * 3600; // 1 day

  pr vate f nal Pengu nVers on pengu nVers on;

  // Keys are trends. Values are empty str ngs.
  pr vate f nal Map<Str ng, Str ng> trendsCac ;

  pr vate volat le Str ngMatchF lter trendsMatc r = null;

  /**
   * Extract Trends from a l st of normal zed tokens
   */
  publ c L st<Str ng> extractTrendsFromNormal zed(L st<Str ng> tokens) {
     f (trendsMatc r == null) {
      return Collect ons.emptyL st();
    }

     mmutableL st.Bu lder<Str ng> trends =  mmutableL st.bu lder();
    for (Str ng trend : trendsMatc r.extractNormal zed(tokens)) {
       f (trendsCac .conta nsKey(trend)) {
        trends.add(trend);
      }
    }

    return trends.bu ld();
  }

  /**
   * Extract Trends from a l st of tokens
   */
  publ c L st<Str ng> extractTrendsFrom(L st<Str ng> tokens, Locale language) {
     f (trendsMatc r == null) {
      return Collect ons.emptyL st();
    }
    return trendsMatc r.extract(language, tokens);
  }

  /**
   * Extract Trends from a g ven CharSequence
   */
  publ c L st<Str ng> extractTrendsFrom(CharSequence text, Locale language) {
     f (trendsMatc r == null) {
      return Collect ons.emptyL st();
    }

     mmutableL st.Bu lder<Str ng> trends =  mmutableL st.bu lder();
    for (Str ng trend : trendsMatc r.extract(language, text)) {
       f (trendsCac .conta nsKey(trend)) {
        trends.add(trend);
      }
    }

    return trends.bu ld();
  }

  publ c long numTrend ngTerms() {
    return trendsCac .s ze();
  }

  publ c Set<Str ng> getTrends() {
    return trendsCac .keySet();
  }

  publ c vo d clear() {
    trendsCac .clear();
    trendsMatc r = null;
  }

  /** Adds all trends to t  NGramCac . */
  publ c vo d addAll( erable<Str ng> trends) {
    for (Str ng trend : trends) {
      trendsCac .put(trend, "");
    }

    trendsMatc r = new Str ngMatchF lter(trendsCac .keySet(), pengu nVers on);
  }

  publ c stat c Bu lder bu lder() {
    return new Bu lder();
  }

  publ c stat c class Bu lder {
    pr vate  nt maxCac S ze = DEFAULT_MAX_CACHE_S ZE;
    pr vate long cac  emTTLSecs = DEFAULT_CACHE_ TEM_TTL_SEC; // 1 day
    pr vate Pengu nVers on pengu nVers on = Pengu nVers on.PENGU N_4;

    publ c Bu lder maxCac S ze( nt cac S ze) {
      t .maxCac S ze = cac S ze;
      return t ;
    }

    publ c Bu lder cac  emTTL(long cac  emTTL) {
      t .cac  emTTLSecs = cac  emTTL;
      return t ;
    }

    publ c Bu lder pengu nVers on(Pengu nVers on newPengu nVers on) {
      t .pengu nVers on = Precond  ons.c ckNotNull(newPengu nVers on);
      return t ;
    }

    /** Bu lds an NGramCac   nstance. */
    publ c NGramCac  bu ld() {
      return new NGramCac (
          maxCac S ze,
          Durat on.apply(cac  emTTLSecs, T  Un .SECONDS),
          pengu nVers on);
    }
  }

  // Should be used only  n tests that want to mock out t  class.
  @V s bleForTest ng
  publ c NGramCac () {
    t (DEFAULT_MAX_CACHE_S ZE,
         Durat on.apply(DEFAULT_CACHE_ TEM_TTL_SEC, T  Un .SECONDS),
         Pengu nVers on.PENGU N_4);
  }

  pr vate NGramCac ( nt maxCac S ze, Durat on cac  emTTL, Pengu nVers on pengu nVers on) {
    //   only have 1 refres r thread that wr es to t  cac 
    t .trendsCac  = Cac Bu lder.newBu lder()
        .concurrencyLevel(1)
        .exp reAfterWr e(cac  emTTL. nSeconds(), T  Un .SECONDS)
        .max mumS ze(maxCac S ze)
        .<Str ng, Str ng>bu ld()
        .asMap();
    t .pengu nVers on = pengu nVers on;
  }
}
