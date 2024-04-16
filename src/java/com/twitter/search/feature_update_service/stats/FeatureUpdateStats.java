package com.tw ter.search.feature_update_serv ce.stats;

 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.ConcurrentMap;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponseCode;

/** Stat track ng for t  feature update  ngester serv ce. */
publ c class FeatureUpdateStats {
  publ c stat c f nal Str ng PREF X = "feature_update_serv ce_";

  pr vate f nal SearchRateCounter requestRate = SearchRateCounter.export(
      PREF X + "requests");

  pr vate ConcurrentMap<Str ng, SearchRateCounter> perCl entRequestRate =
      new ConcurrentHashMap<>();

  pr vate ConcurrentMap<Str ng, SearchRateCounter> responseCodeRate =
      new ConcurrentHashMap<>();

  pr vate ConcurrentMap<Str ng, SearchRateCounter> preCl entResponseCodeRate =
      new ConcurrentHashMap<>();

  /**
   * Record  tr cs for a s ngle  ncom ng request.
   */
  publ c vo d cl entRequest(Str ng cl ent D) {
    // 1. Track total request rate.  's better to precompute than compute t  per cl ent sum at
    // query t  .
    requestRate. ncre nt();

    // 2. Track request rate per cl ent.
     ncre ntPerCl entCounter(perCl entRequestRate, cl entRequestRateKey(cl ent D));
  }

  /**
   * Record  tr cs for a s ngle response.
   */
  publ c vo d cl entResponse(Str ng cl ent D, FeatureUpdateResponseCode responseCode) {
    Str ng code = responseCode.toStr ng().toLo rCase();

    // 1. Track rates per response code.
     ncre ntPerCl entCounter(responseCodeRate, responseCodeKey(code));

    // 2. Track rates per cl ent per response code.
     ncre ntPerCl entCounter(preCl entResponseCodeRate, cl entResponseCodeKey(cl ent D, code));
  }

  /**
   * Returns t  total number of requests.
   */
  publ c long getRequestRateCount() {
    return requestRate.getCount();
  }

  /**
   * Returns t  total number of requests for t  spec f ed cl ent.
   */
  publ c long getCl entRequestCount(Str ng cl ent D)  {
    Str ng key = cl entRequestRateKey(cl ent D);
     f (perCl entRequestRate.conta nsKey(key)) {
      return perCl entRequestRate.get(key).getCount();
    }
    return 0;
  }

  /**
   * Returns t  total number of responses w h t  spec f ed code.
   */
  publ c long getResponseCodeCount(FeatureUpdateResponseCode responseCode) {
    Str ng code = responseCode.toStr ng().toLo rCase();
    Str ng key = responseCodeKey(code);
     f (responseCodeRate.conta nsKey(key)) {
      return responseCodeRate.get(key).getCount();
    }
    return 0;
  }

  /**
   * Returns t  total number of responses to t  spec f ed cl ent w h t  spec f ed code.
   */
  publ c long getCl entResponseCodeCount(Str ng cl ent D, FeatureUpdateResponseCode responseCode) {
    Str ng code = responseCode.toStr ng().toLo rCase();
    Str ng key = cl entResponseCodeKey(cl ent D, code);
     f (preCl entResponseCodeRate.conta nsKey(key)) {
      return preCl entResponseCodeRate.get(key).getCount();
    }
    return 0;
  }

  pr vate stat c Str ng cl entRequestRateKey(Str ng cl ent D) {
    return Str ng.format(PREF X + "requests_for_cl ent_ d_%s", cl ent D);
  }

  pr vate stat c Str ng responseCodeKey(Str ng responseCode) {
    return Str ng.format(PREF X + "response_code_%s", responseCode);
  }

  pr vate stat c Str ng cl entResponseCodeKey(Str ng cl ent D, Str ng responseCode) {
    return Str ng.format(PREF X + "response_for_cl ent_ d_%s_code_%s", cl ent D, responseCode);
  }

  pr vate vo d  ncre ntPerCl entCounter(
      ConcurrentMap<Str ng, SearchRateCounter> rates,
      Str ng key
  ) {
    rates.put fAbsent(key, SearchRateCounter.export(key));
    rates.get(key). ncre nt();
  }
}
