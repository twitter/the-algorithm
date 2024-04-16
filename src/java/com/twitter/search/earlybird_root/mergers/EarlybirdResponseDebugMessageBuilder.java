package com.tw ter.search.earlyb rd_root. rgers;


 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Funct on;
 mport com.google.common.base.Jo ner;
 mport com.google.common.collect. erables;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.logg ng.Debug ssageBu lder;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;

/**
 * Collects debug  ssages to attach to Earlyb rdResponse
 */
class Earlyb rdResponseDebug ssageBu lder {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdResponseDebug ssageBu lder.class);

  pr vate stat c f nal Logger TOO_MANY_FA LED_PART T ONS_LOG =
      LoggerFactory.getLogger(Str ng.format("%s_too_many_fa led_part  ons",
                                            Earlyb rdResponseDebug ssageBu lder.class.getNa ()));

  @V s bleForTest ng
  protected f nal SearchCounter  nsuff c entVal dResponseCounter =
      SearchCounter.export(" nsuff c ent_val d_part  on_responses_count");
  @V s bleForTest ng
  protected f nal SearchCounter val dPart  onResponseCounter =
      SearchCounter.export("val d_part  on_response_count");

  // t  comb ned debug str ng for all earlyb rd responses
  pr vate f nal Str ngBu lder debugStr ng;
  /**
   * A  ssage bu lder backed by t  sa  {@l nk #debugStr ng} above.
   */
  pr vate f nal Debug ssageBu lder debug ssageBu lder;

  pr vate stat c f nal Jo ner JO NER = Jo ner.on(", ");

  Earlyb rdResponseDebug ssageBu lder(Earlyb rdRequest request) {
    t (getDebugLevel(request));
  }

  Earlyb rdResponseDebug ssageBu lder(Debug ssageBu lder.Level level) {
    t .debugStr ng = new Str ngBu lder();
    t .debug ssageBu lder = new Debug ssageBu lder(debugStr ng, level);
  }

  pr vate stat c Debug ssageBu lder.Level getDebugLevel(Earlyb rdRequest request) {
     f (request. sSetDebugMode() && request.getDebugMode() > 0) {
      return Debug ssageBu lder.getDebugLevel(request.getDebugMode());
    } else  f (request. sSetDebugOpt ons()) {
      return Debug ssageBu lder.Level.DEBUG_BAS C;
    } else {
      return Debug ssageBu lder.Level.DEBUG_NONE;
    }
  }

  protected boolean  sDebugMode() {
    return debug ssageBu lder.getDebugLevel() > 0;
  }

  vo d append(Str ng msg) {
    debugStr ng.append(msg);
  }

  vo d debugAndLogWarn ng(Str ng msg) {
     f ( sDebugMode()) {
      debugStr ng.append(msg).append('\n');
    }
    LOG.warn(msg);
  }

  vo d debugDeta led(Str ng format, Object... args) {
    debugAtLevel(Debug ssageBu lder.Level.DEBUG_DETA LED, format, args);
  }

  vo d debugVerbose(Str ng format, Object... args) {
    debugAtLevel(Debug ssageBu lder.Level.DEBUG_VERBOSE, format, args);
  }

  vo d debugVerbose2(Str ng format, Object... args) {
    debugAtLevel(Debug ssageBu lder.Level.DEBUG_VERBOSE_2, format, args);
  }

  vo d debugAtLevel(Debug ssageBu lder.Level level, Str ng format, Object... args) {
    boolean levelOK = debug ssageBu lder. sAtLeastLevel(level);
     f (levelOK || LOG. sDebugEnabled()) {
      //   c ck both modes  re  n order to bu ld t  formatted  ssage only once.
      Str ng  ssage = Str ng.format(format, args);

      LOG.debug( ssage);

       f (levelOK) {
        debugStr ng.append( ssage).append('\n');
      }
    }
  }

  Str ng debugStr ng() {
    return debugStr ng.toStr ng();
  }

  Debug ssageBu lder getDebug ssageBu lder() {
    return debug ssageBu lder;
  }

  vo d logBelowSuccessThreshold(Thr ftSearchQuery searchQuery,  nt numSuccessResponses,
                                 nt numPart  ons, double successThreshold) {
    Str ng rawQuery = (searchQuery != null && searchQuery. sSetRawQuery())
        ? "[" + searchQuery.getRawQuery() + "]" : "null";
    Str ng ser al zedQuery = (searchQuery != null && searchQuery. sSetSer al zedQuery())
        ? "[" + searchQuery.getSer al zedQuery() + "]" : "null";
    // Not enough successful responses from part  ons.
    Str ng error ssage = Str ng.format(
        "Only %d val d responses returned out of %d part  ons for raw query: %s"
            + " ser al zed query: %s. Lo r than threshold of %s",
        numSuccessResponses, numPart  ons, rawQuery, ser al zedQuery, successThreshold);

    TOO_MANY_FA LED_PART T ONS_LOG.warn(error ssage);

     nsuff c entVal dResponseCounter. ncre nt();
    val dPart  onResponseCounter.add(numSuccessResponses);
    debugStr ng.append(error ssage);
  }


  @V s bleForTest ng
  vo d logResponseDebug nfo(Earlyb rdRequest earlyb rdRequest,
                            Str ng part  onT erNa ,
                            Earlyb rdResponse response) {
     f (response. sSetDebugStr ng() && !response.getDebugStr ng(). sEmpty()) {
      debugStr ng.append(Str ng.format("Rece ved response from [%s] w h debug str ng [%s]",
          part  onT erNa , response.getDebugStr ng())).append("\n");
    }

     f (!response. sSetResponseCode()) {
      debugAndLogWarn ng(Str ng.format(
          "Rece ved Earlyb rd null response code for query [%s] from [%s]",
          earlyb rdRequest, part  onT erNa ));
    } else  f (response.getResponseCode() != Earlyb rdResponseCode.SUCCESS
        && response.getResponseCode() != Earlyb rdResponseCode.PART T ON_SK PPED
        && response.getResponseCode() != Earlyb rdResponseCode.PART T ON_D SABLED
        && response.getResponseCode() != Earlyb rdResponseCode.T ER_SK PPED) {
      debugAndLogWarn ng(Str ng.format(
          "Rece ved Earlyb rd response error [%s] for query [%s] from [%s]",
          response.getResponseCode(), earlyb rdRequest, part  onT erNa ));
    }

     f (debug ssageBu lder. sVerbose2()) {
      debugVerbose2("Earlyb rd [%s] returned response: %s", part  onT erNa , response);
    } else  f (debug ssageBu lder. sVerbose()) {
       f (response. sSetSearchResults() && response.getSearchResults().getResultsS ze() > 0) {
        Str ng  ds = JO NER.jo n( erables.transform(
            response.getSearchResults().getResults(),
            new Funct on<Thr ftSearchResult, Long>() {
              @Nullable
              @Overr de
              publ c Long apply(Thr ftSearchResult result) {
                return result.get d();
              }
            }));
        debugVerbose("Earlyb rd [%s] returned T et Ds: %s", part  onT erNa ,  ds);
      }
    }
  }
}
