package com.tw ter.search.earlyb rd_root;

 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport javax.annotat on.Nullable;
 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.ut l.concurrent.ThreadFactoryBu lder;
 mport com.google.common.ut l.concurrent.Tw terRateL m erProxyFactory;
 mport com.google. nject.Prov des;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl ent dArch veAccessF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl ent dQuotaF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.D sableCl entByT erF lter;
 mport com.tw ter.search.earlyb rd_root.quota.Conf gBasedQuotaConf g;
 mport com.tw ter.search.earlyb rd_root.quota.Conf gRepoBasedQuotaManager;

publ c class QuotaModule extends Tw terModule {
  @V s bleForTest ng
  publ c stat c f nal Str ng NAMED_QUOTA_CONF G_PATH = "quotaConf gPath";
  publ c stat c f nal Str ng NAMED_CL ENT_QUOTA_KEY = "cl entQuotaKey";
  pr vate stat c f nal Str ng NAMED_REQU RE_QUOTA_CONF G_FOR_CL ENTS
      = "requ reQuotaConf gForCl ents";

  pr vate f nal Flag<Str ng> quotaConf gPathFlag = createMandatoryFlag(
      "quota_conf g_path",
      "",
      "Path to t  quota conf g f le",
      Flaggable.ofStr ng());

  pr vate f nal Flag<Str ng> cl entQuotaKeyFlag = createFlag(
      "cl ent_quota_key",
      "quota",
      "T  key that w ll be used to extract cl ent quotas",
      Flaggable.ofStr ng());

  pr vate f nal Flag<Boolean> requ reQuotaConf gForCl entsFlag = createFlag(
      "requ re_quota_conf g_for_cl ents",
      true,
      " f true, requ re a quota value under <cl ent_quota_key> for each cl ent  n t  conf g",
      Flaggable.ofJavaBoolean());

  @Prov des
  @S ngleton
  @Na d(NAMED_QUOTA_CONF G_PATH)
  Str ng prov deQuotaConf gPath() {
    return quotaConf gPathFlag.apply();
  }

  @Prov des
  @S ngleton
  @Na d(NAMED_CL ENT_QUOTA_KEY)
  Str ng prov deCl entQuotaKey() {
    return cl entQuotaKeyFlag.apply();
  }

  @Prov des
  @S ngleton
  @Na d(NAMED_REQU RE_QUOTA_CONF G_FOR_CL ENTS)
  boolean prov deRequ reQuotaConf gForCl ents() {
    return requ reQuotaConf gForCl entsFlag.apply();
  }

  @Prov des
  @S ngleton
  Cl ent dQuotaF lter prov deConf gRepoBasedCl ent dQuotaF lter(
      Conf gRepoBasedQuotaManager conf gRepoBasedQuotaManager,
      Tw terRateL m erProxyFactory rateL m erProxyFactory) throws Except on {
    return new Cl ent dQuotaF lter(conf gRepoBasedQuotaManager, rateL m erProxyFactory);
  }

  @Prov des
  @S ngleton
  Conf gBasedQuotaConf g prov desConf gBasedQuotaConf g(
      @Nullable @Na d(NAMED_QUOTA_CONF G_PATH) Str ng quotaConf gPath,
      @Nullable @Na d(NAMED_CL ENT_QUOTA_KEY) Str ng cl entQuotaKey,
      @Nullable @Na d(NAMED_REQU RE_QUOTA_CONF G_FOR_CL ENTS) boolean requ reQuotaConf gForCl ents,
      Clock clock
  ) throws Except on {
    Sc duledExecutorServ ce executorServ ce = Executors.newS ngleThreadSc duledExecutor(
        new ThreadFactoryBu lder()
            .setNa Format("quota-conf g-reloader")
            .setDaemon(true)
            .bu ld());
    return Conf gBasedQuotaConf g.newConf gBasedQuotaConf g(
        quotaConf gPath, cl entQuotaKey, requ reQuotaConf gForCl ents, executorServ ce, clock);
  }

  @Prov des
  @S ngleton
  D sableCl entByT erF lter prov deD sableCl entByT erF lter(
      Conf gRepoBasedQuotaManager conf gRepoBasedQuotaManager,
      SearchDec der searchDec der) {
    return new D sableCl entByT erF lter(conf gRepoBasedQuotaManager, searchDec der);
  }

  @Prov des
  @S ngleton
  Cl ent dArch veAccessF lter cl ent dArch veAccessF lter(
      Conf gRepoBasedQuotaManager conf gRepoBasedQuotaManager) {
    return new Cl ent dArch veAccessF lter(conf gRepoBasedQuotaManager);
  }
}
