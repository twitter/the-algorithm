package com.tw ter.search.earlyb rd_root.quota;

 mport java. o. OExcept on;
 mport java. o. nputStream;
 mport java.n o.charset.StandardCharsets;
 mport java.ut l. erator;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.atom c.Atom cReference;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport org.apac .commons. o. OUt ls;
 mport org.json.JSONExcept on;
 mport org.json.JSONObject;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common.ut l. o.per od c.Per od cF leLoader;
 mport com.tw ter.search.common.ut l.json.JSONPars ngUt l;

/**
 * Per od cally loads a json ser al zed map that conta ns t  quota  nformat on  ndexed by
 * cl ent  d.
 *
 * Each json object from t  map  s requ red to have an  nt property that represents a cl ent's quota.
 * T  key for t  quota property  s passed to t  class.
 *
 * Opt onally   can have a <b>should_enforce</b> property of type boolean
 *
 *  f t  two propert es are not present an except on w ll be thrown.
 */
publ c class Conf gBasedQuotaConf g extends Per od cF leLoader {
  pr vate stat c f nal Str ng UNSET_EMA L = "unset";

  pr vate stat c f nal Str ng PER_CL ENT_QUOTA_GAUGE_NAME_PATTERN =
      "conf g_based_quota_for_cl ent_ d_%s";
  pr vate stat c f nal Str ng PER_EMA L_QUOTA_GAUGE_NAME_PATTERN =
      "conf g_based_quota_for_ema l_%s";

  @V s bleForTest ng
  stat c f nal SearchLongGauge TOTAL_QUOTA =
     SearchLongGauge.export("total_conf g_based_quota");

  @V s bleForTest ng
  stat c f nal SearchLongGauge ENTR ES_COUNT =
      SearchLongGauge.export("conf g_repo_quota_conf g_entr es_count");

  pr vate f nal Atom cReference< mmutableMap<Str ng, Quota nfo>> cl entQuotas =
    new Atom cReference<>();

  pr vate Str ng cl entQuotaKey;
  pr vate boolean requ reQuotaConf gForCl ents;

  /**
   * Creates t  object that manages loads t  conf g from: quotaConf gPath.   per od cally
   * reloads t  conf g f le us ng t  g ven executor serv ce.
   *
   * @param quotaConf gPath Path to conf gurat on f le.
   * @param executorServ ce Sc duledExecutorServ ce to be used for per od cally reload ng t  f le.
   * @param cl entQuotaKey T  key that w ll be used to extract cl ent quotas.
   * @param requ reQuotaConf gForCl ents Determ nes w t r a cl ent can be sk pped
   *  f t  assoc ated object  s m ss ng t  quota key
   * ( e a cl ent that  s a SuperRoot cl ent but t  current serv ce  s Arch ve)
   */
  publ c stat c Conf gBasedQuotaConf g newConf gBasedQuotaConf g(
      Str ng quotaConf gPath,
      Str ng cl entQuotaKey,
      boolean requ reQuotaConf gForCl ents,
      Sc duledExecutorServ ce executorServ ce,
      Clock clock
  ) throws Except on {
    Conf gBasedQuotaConf g conf gLoader = new Conf gBasedQuotaConf g(
        quotaConf gPath,
        cl entQuotaKey,
        requ reQuotaConf gForCl ents,
        executorServ ce,
        clock
    );
    conf gLoader. n ();
    return conf gLoader;
  }

  publ c Conf gBasedQuotaConf g(
      Str ng quotaConf gPath,
      Str ng cl entQuotaKey,
      boolean requ reQuotaConf gForCl ents,
      Sc duledExecutorServ ce executorServ ce,
      Clock clock
  ) throws Except on {
    super("quotaConf g", quotaConf gPath, executorServ ce, clock);
    t .cl entQuotaKey = cl entQuotaKey;
    t .requ reQuotaConf gForCl ents = requ reQuotaConf gForCl ents;
  }

  /**
   * Returns t  quota  nformat on for a spec f c cl ent  d.
   */
  publ c Opt onal<Quota nfo> getQuotaForCl ent(Str ng cl ent d) {
    return Opt onal.ofNullable(cl entQuotas.get().get(cl ent d));
  }

  /**
   * Load t  json format and store    n a map.
   */
  @Overr de
  protected vo d accept( nputStream f leStream) throws JSONExcept on,  OExcept on {
    Str ng f leContents =  OUt ls.toStr ng(f leStream, StandardCharsets.UTF_8);
    JSONObject quotaConf g = new JSONObject(JSONPars ngUt l.str pCom nts(f leContents));

    Map<Str ng,  nteger> perEma lQuotas = Maps.newHashMap();
     mmutableMap.Bu lder<Str ng, Quota nfo> quotasBu lder = new  mmutableMap.Bu lder<>();
     erator<Str ng> cl ent ds = quotaConf g.keys();

    long totalQuota = 0;
    wh le (cl ent ds.hasNext()) {
      Str ng cl ent d = cl ent ds.next();
      JSONObject cl entQuota = quotaConf g.getJSONObject(cl ent d);

      // Sk p cl ents that don't send requests to t  serv ce.
      // ( e so  SuperRoot cl ents are not Arch ve cl ents)
       f (!requ reQuotaConf gForCl ents && !cl entQuota.has(cl entQuotaKey)) {
        cont nue;
      }

       nt quotaValue = cl entQuota.get nt(cl entQuotaKey);
      boolean shouldEnforce = cl entQuota.optBoolean("should_enforce", false);
      Str ng t erValue = cl entQuota.optStr ng("t er", Quota nfo.DEFAULT_T ER_VALUE);
      boolean arch veAccess = cl entQuota.optBoolean("arch ve_access",
          Quota nfo.DEFAULT_ARCH VE_ACCESS_VALUE);
      Str ng ema l = cl entQuota.optStr ng("ema l", UNSET_EMA L);

      quotasBu lder.put(
          cl ent d,
          new Quota nfo(cl ent d, ema l, quotaValue, shouldEnforce, t erValue, arch veAccess));

      SearchLongGauge perCl entQuota = SearchLongGauge.export(
          Str ng.format(PER_CL ENT_QUOTA_GAUGE_NAME_PATTERN, cl ent d));
      perCl entQuota.set(quotaValue);
      totalQuota += quotaValue;

       nteger ema lQuota = perEma lQuotas.get(ema l);
       f (ema lQuota == null) {
        ema lQuota = 0;
      }
      perEma lQuotas.put(ema l, ema lQuota + quotaValue);
    }

    cl entQuotas.set(quotasBu lder.bu ld());
    TOTAL_QUOTA.set(totalQuota);
    ENTR ES_COUNT.set(cl entQuotas.get().s ze());

    for (Str ng ema l : perEma lQuotas.keySet()) {
      SearchLongGauge.export(Str ng.format(PER_EMA L_QUOTA_GAUGE_NAME_PATTERN, ema l)).set(
          perEma lQuotas.get(ema l));
    }
  }
}
