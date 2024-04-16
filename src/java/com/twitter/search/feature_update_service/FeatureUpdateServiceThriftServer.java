package com.tw ter.search.feature_update_serv ce;

 mport java.ut l.ArrayL st;
 mport java.ut l.Arrays;
 mport java.ut l.Collect on;
 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.base.Precond  ons;
 mport com.google. nject.Module;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.f nagle.F lter;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.Thr ftMux;
 mport com.tw ter.f natra.annotat ons.DarkTraff cF lterType;
 mport com.tw ter.f natra.dec der.modules.Dec derModule$;
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule;
 mport com.tw ter.f natra.mtls.thr ftmux.AbstractMtlsThr ftServer;
 mport com.tw ter.f natra.thr ft.f lters.AccessLogg ngF lter;
 mport com.tw ter.f natra.thr ft.f lters.Logg ngMDCF lter;
 mport com.tw ter.f natra.thr ft.f lters.StatsF lter;
 mport com.tw ter.f natra.thr ft.f lters.Thr ftMDCF lter;
 mport com.tw ter.f natra.thr ft.f lters.Trace dMDCF lter;
 mport com.tw ter.f natra.thr ft.rout ng.JavaThr ftRouter;
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule$;
 mport com.tw ter.search.common.constants.SearchThr ft bFormsAccess;
 mport com.tw ter.search.common. tr cs.Bu ld nfoStats;
 mport com.tw ter.search.common.ut l.PlatformStatsExporter;
 mport com.tw ter.search.feature_update_serv ce.f lters.Cl ent dWh el stF lter;
 mport com.tw ter.search.feature_update_serv ce.modules.Cl ent dWh el stModule;
 mport com.tw ter.search.feature_update_serv ce.modules.Earlyb rdUt lModule;
 mport com.tw ter.search.feature_update_serv ce.modules.FeatureUpdateServ ceD ffyModule;
 mport com.tw ter.search.feature_update_serv ce.modules.F nagleKafkaProducerModule;
 mport com.tw ter.search.feature_update_serv ce.modules.FuturePoolModule;
 mport com.tw ter.search.feature_update_serv ce.modules.T etyp eModule;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateServ ce;
 mport com.tw ter.thr ft bforms. thodOpt onsAccessConf g;
 mport com.tw ter.ut l.ExecutorServ ceFuturePool;

publ c class FeatureUpdateServ ceThr ftServer extends AbstractMtlsThr ftServer {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(FeatureUpdateServ ceThr ftServer.class);

  //  deally   would not have to access t  "env ron nt" flag  re and   could  nstead pass
  // a flag to t  Thr ft bFormsModule that would e  r enable or d sable thr ft  b forms.
  // Ho ver,    s not s mple to create   own Tw terModule that both extends t 
  // Thr ft bFormsModule and consu s an  njected flag.
  pr vate Flag<Str ng> envFlag = flag().create("env ron nt",
      "",
      "Env ron nt for serv ce (prod, stag ng, stag ng1, devel)",
      Flaggable.ofStr ng());

  FeatureUpdateServ ceThr ftServer(Str ng[] args) {
    Bu ld nfoStats.export();
    PlatformStatsExporter.exportPlatformStats();

    flag().parseArgs(args, true);
  }

  @Overr de
  @SuppressWarn ngs("unc cked")
  publ c Collect on<Module> javaModules() {
    L st<Module> modules = new ArrayL st<>();
    modules.addAll(Arrays.asL st(
        Thr ftCl ent dModule$.MODULE$,
        Dec derModule$.MODULE$,
        new Cl ent dWh el stModule(),
        new F nagleKafkaProducerModule(),
        new Earlyb rdUt lModule(),
        new FuturePoolModule(),
        new FeatureUpdateServ ceD ffyModule(),
        new T etyp eModule()));

    // Only add t  Thr ft  b Forms module for non-prod serv ces because   should
    // not allow wr e access to product on data through Thr ft  b Forms.
    Str ng env ron nt = envFlag.apply();
     f ("prod".equals(env ron nt)) {
      LOG. nfo("Not  nclud ng Thr ft  b Forms because t  env ron nt  s prod");
    } else {
      LOG. nfo(" nclud ng Thr ft  b Forms because t  env ron nt  s " + env ron nt);
      modules.add(
        MtlsThr ft bFormsModule.create(
          t ,
          FeatureUpdateServ ce.Serv ce face.class,
           thodOpt onsAccessConf g.byLdapGroup(SearchThr ft bFormsAccess.WR TE_LDAP_GROUP)
        )
      );
    }

    return modules;
  }

  @Overr de
  publ c vo d conf gureThr ft(JavaThr ftRouter router) {
    router
        //  n  al ze Mapped D agnost c Context (MDC) for logg ng
        // (see https://logback.qos.ch/manual/mdc.html)
        .f lter(Logg ngMDCF lter.class)
        //  nject trace  D  n MDC for logg ng
        .f lter(Trace dMDCF lter.class)
        //  nject request  thod and cl ent  D  n MDC for logg ng
        .f lter(Thr ftMDCF lter.class)
        // Log cl ent access
        .f lter(AccessLogg ngF lter.class)
        // Export bas c serv ce stats
        .f lter(StatsF lter.class)
        .f lter(Cl ent dWh el stF lter.class)
        .add(FeatureUpdateController.class);
  }

  @Overr de
  publ c Serv ce<byte[], byte[]> conf gureServ ce(Serv ce<byte[], byte[]> serv ce) {
    // Add t  DarkTraff cF lter  n "front" of t  serv ce be ng served.
    return  njector()
        . nstance(F lter.TypeAgnost c.class, DarkTraff cF lterType.class)
        .andT n(serv ce);
  }

  @Overr de
  publ c Thr ftMux.Server conf gureThr ftServer(Thr ftMux.Server server) {
    // T  cast looks redundant, but    s requ red for pants to comp le t  f le.
    return (Thr ftMux.Server) server.w hResponseClass f er(new FeatureUpdateResponseClass f er());
  }

  @Overr de
  publ c vo d postWarmup() {
    super.postWarmup();

    ExecutorServ ceFuturePool futurePool =  njector(). nstance(ExecutorServ ceFuturePool.class);
    Precond  ons.c ckNotNull(futurePool);

    onEx (() -> {
      try {
        futurePool.executor().shutdownNow();

        futurePool.executor().awa Term nat on(10L, T  Un .SECONDS);
      } catch ( nterruptedExcept on e) {
        LOG.error(" nterrupted wh le awa  ng future pool term nat on", e);
      }

      return null;
    });
  }
}
