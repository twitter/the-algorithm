package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.text.language.LocaleUt l;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 * Export stats for query languages.
 */
@S ngleton
publ c class QueryLangStatF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  publ c stat c class Conf g {
    //   put a l m   re  n case an error  n t  cl ent are send ng us random lang codes.
    pr vate  nt maxNumberOfLangs;

    publ c Conf g( nt maxNumberOfLangs) {
      t .maxNumberOfLangs = maxNumberOfLangs;
    }

    publ c  nt getMaxNumberOfLangs() {
      return maxNumberOfLangs;
    }
  }

  @V s bleForTest ng
  protected stat c f nal Str ng LANG_STATS_PREF X = "num_quer es_ n_lang_";

  pr vate f nal Conf g conf g;
  pr vate f nal SearchCounter allCountsForLangsOverMaxNumLang =
      SearchCounter.export(LANG_STATS_PREF X + "overflow");

  pr vate f nal ConcurrentHashMap<Str ng, SearchCounter> langCounters =
      new ConcurrentHashMap<>();

  @ nject
  publ c QueryLangStatF lter(Conf g conf g) {
    t .conf g = conf g;
  }

  pr vate SearchCounter getCounter(Str ng lang) {
    Precond  ons.c ckNotNull(lang);

    SearchCounter counter = langCounters.get(lang);
     f (counter == null) {
       f (langCounters.s ze() >= conf g.getMaxNumberOfLangs()) {
        return allCountsForLangsOverMaxNumLang;
      }
      synchron zed (langCounters) { // T  double-c cked lock ng  s safe,
                                    // s nce  're us ng a ConcurrentHashMap
        counter = langCounters.get(lang);
         f (counter == null) {
          counter = SearchCounter.export(LANG_STATS_PREF X + lang);
          langCounters.put(lang, counter);
        }
      }
    }

    return counter;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

    Str ng lang = null;

    Thr ftSearchQuery searchQuery = requestContext.getRequest().getSearchQuery();

    lang = searchQuery.getQueryLang();

     f (lang == null) {
      // fallback to u  lang
      lang = searchQuery.getU Lang();
    }

     f (lang == null && searchQuery. sSetUserLangs()) {
      // fallback to t  user lang w h t  h g st conf dence
      double maxConf dence = Double.M N_VALUE;

      for (Map.Entry<Thr ftLanguage, Double> entry : searchQuery.getUserLangs().entrySet()) {
         f (entry.getValue() > maxConf dence) {
          lang = Thr ftLanguageUt l.getLanguageCodeOf(entry.getKey());
          maxConf dence = entry.getValue();
        }
      }
    }

     f (lang == null) {
      lang = LocaleUt l.UNDETERM NED_LANGUAGE;
    }

    getCounter(lang). ncre nt();

    return serv ce.apply(requestContext);
  }
}
