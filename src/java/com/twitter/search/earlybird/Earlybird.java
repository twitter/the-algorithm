package com.tw ter.search.earlyb rd;

 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.net. netAddress;
 mport java.net.UnknownHostExcept on;
 mport java.ut l.Arrays;
 mport java.ut l.Map;
 mport java.ut l.funct on.Pred cate;
 mport java.ut l.stream.Collectors;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.f nagle.Http;
 mport com.tw ter.f nagle.http.HttpMuxer;
 mport com.tw ter.search.common.aurora.Aurora nstanceKey;
 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common.conf g.LoggerConf gurat on;
 mport com.tw ter.search.common.constants.SearchThr ft bFormsAccess;
 mport com.tw ter.search.common. tr cs.Bu ld nfoStats;
 mport com.tw ter.search.common.ut l.Kerberos;
 mport com.tw ter.search.common.ut l.PlatformStatsExporter;
 mport com.tw ter.search.earlyb rd.adm n.Earlyb rdAdm nManager;
 mport com.tw ter.search.earlyb rd.adm n.Earlyb rd althHandler;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;
 mport com.tw ter.search.earlyb rd.except on.UncaughtExcept onHandler;
 mport com.tw ter.search.earlyb rd.factory.Earlyb rdServerFactory;
 mport com.tw ter.search.earlyb rd.factory.Earlyb rdW reModule;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd.ut l.Earlyb rdDec der;
 mport com.tw ter.server.handler.Dec derHandler$;
 mport com.tw ter.server.AbstractTw terServer;
 mport com.tw ter.thr ft bforms.D splaySett ngsConf g;
 mport com.tw ter.thr ft bforms. thodOpt onsAccessConf g;
 mport com.tw ter.thr ft bforms.Thr ftCl entSett ngsConf g;
 mport com.tw ter.thr ft bforms.Thr ft thodSett ngsConf g;
 mport com.tw ter.thr ft bforms.Thr ftServ ceSett ngs;
 mport com.tw ter.thr ft bforms.Thr ft bFormsSett ngs;
 mport com.tw ter.thr ft bforms.Tw terServerThr ft bForms;
 mport com.tw ter.ut l.Awa ;
 mport com.tw ter.ut l.T  outExcept on;

publ c class Earlyb rd extends AbstractTw terServer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rd.class);

  // Flags def ned  re need to be processed before sett ng overr de values to Earlyb rdConf g.

  pr vate f nal Flag<F le> conf gF le = flag().create(
      "conf g_f le",
      new F le("earlyb rd-search.yml"),
      "spec fy conf g f le",
      Flaggable.ofF le()
  );

  pr vate f nal Flag<Str ng> logD r = flag().create(
      "earlyb rd_log_d r",
      "",
      "overr de log d r from conf g f le",
      Flaggable.ofStr ng()
  );

  pr vate f nal Map<Str ng, Flag<?>> flagMap = Arrays.stream(Earlyb rdProperty.values())
      .collect(Collectors.toMap(
          property -> property.na (),
          property -> property.createFlag(flag())));

  pr vate f nal UncaughtExcept onHandler uncaughtExcept onHandler =
      new UncaughtExcept onHandler();

  pr vate Earlyb rdServer earlyb rdServer;
  pr vate Earlyb rdAdm nManager earlyb rdAdm nManager;

  publ c Earlyb rd() {
    // Default  alth handler  s added  ns de L fecycle tra .  To overr de that   need to set  
    //  n t  constructor s nce HttpAdm nServer  s started before Earlyb rd.preMa n()  s called.
    HttpMuxer.addHandler("/ alth", new Earlyb rd althHandler());
  }

  /**
   * Needs to be called from preMa n and not from on n () as flags / args pars ng happens after
   * on n ()  s called.
   */
  @V s bleForTest ng
  vo d conf gureFromFlagsAndSetupLogg ng() {
    // Makes sure t  Earlyb rdStats  s  njected w h a var able repos ory.
    Earlyb rdConf g. n (conf gF le.getW hDefault().get().getNa ());

     f (logD r. sDef ned()) {
      Earlyb rdConf g.overr deLogD r(logD r.get().get());
    }
    new LoggerConf gurat on(Earlyb rdConf g.getLogPropert esF le(),
        Earlyb rdConf g.getLogD r()).conf gure();

    Str ng  nstanceKey = System.getProperty("aurora. nstanceKey");
     f ( nstanceKey != null) {
      Earlyb rdConf g.setAurora nstanceKey(Aurora nstanceKey.from nstanceKey( nstanceKey));
      LOG. nfo("Earlyb rd  s runn ng on Aurora");
      c ckRequ redPropert es(Earlyb rdProperty:: sRequ redOnAurora, "Aurora");
    } else {
      LOG. nfo("Earlyb rd  s runn ng on ded cated hardware");
      c ckRequ redPropert es(Earlyb rdProperty:: sRequ redOnDed cated, "ded cated hardware");
    }
    LOG. nfo("Conf g env ron nt: {}", Conf g.getEnv ron nt());

     f (adm nPort(). sDef ned() && adm nPort().get(). sDef ned()) {
       nt adm nPort = adm nPort().get().get().getPort();
      LOG. nfo("Adm n port  s {}", adm nPort);
      Earlyb rdConf g.setAdm nPort(adm nPort);
    }

    Earlyb rdConf g.setOverr deValues(
        flagMap.values().stream()
            .f lter(Flag:: sDef ned)
            .collect(Collectors.toMap(Flag::na , flag -> flag.get().get())));
  }

  pr vate vo d c ckRequ redPropert es(
      Pred cate<Earlyb rdProperty> propertyPred cate, Str ng locat on) {
    Arrays.stream(Earlyb rdProperty.values())
        .f lter(propertyPred cate)
        .map(property -> flagMap.get(property.na ()))
        .forEach(flag ->
            Precond  ons.c ckState(flag. sDef ned(),
                "-%s  s requ red on %s", flag.na (), locat on));
  }

  pr vate vo d logEarlyb rd nfo() {
    try {
      LOG. nfo("Hostna : {}",  netAddress.getLocalHost().getHostNa ());
    } catch (UnknownHostExcept on e) {
      LOG. nfo("Unable to be get local host: {}", e.get ssage());
    }
    LOG. nfo("Earlyb rd  nfo [Na : {}, Zone: {}, Env: {}]",
            Earlyb rdProperty.EARLYB RD_NAME.get(),
            Earlyb rdProperty.ZONE.get(),
            Earlyb rdProperty.ENV.get());
    LOG. nfo("Earlyb rd scrubgen from Aurora: {}]",
        Earlyb rdProperty.EARLYB RD_SCRUB_GEN.get());
    LOG. nfo("F nd f nal part  on conf g by search ng t  log for \"Part  on conf g  nfo\"");
  }

  pr vate Earlyb rdServer makeEarlyb rdServer() {
    Earlyb rdW reModule earlyb rdW reModule = new Earlyb rdW reModule();
    Earlyb rdServerFactory earlyb rdFactory = new Earlyb rdServerFactory();
    try {
      return earlyb rdFactory.makeEarlyb rdServer(earlyb rdW reModule);
    } catch ( OExcept on e) {
      LOG.error("Except on wh le construct ng Earlyb rdServer.", e);
      throw new Runt  Except on(e);
    }
  }

  pr vate vo d setupThr ft bForms() {
    Tw terServerThr ft bForms.addAdm nRoutes(t , Tw terServerThr ft bForms.apply(
        Thr ft bFormsSett ngs.apply(
            D splaySett ngsConf g.DEFAULT,
            Thr ftServ ceSett ngs.apply(
                Earlyb rdServ ce.Serv ce face.class.getS mpleNa (),
                Earlyb rdConf g.getThr ftPort()),
            Thr ftCl entSett ngsConf g.makeCompactRequ red(
                Earlyb rdProperty.getServ ce dent f er()),
            Thr ft thodSett ngsConf g.access(
               thodOpt onsAccessConf g.byLdapGroup(
                SearchThr ft bFormsAccess.READ_LDAP_GROUP))),
        scala.reflect.ClassTag$.MODULE$.apply(Earlyb rdServ ce.Serv ce face.class)));
  }

  pr vate vo d setupDec der bForms() {
    addAdm nRoute(
        Dec derHandler$.MODULE$.route(
            "earlyb rd",
            Earlyb rdDec der.getMutableDec s onMaker(),
            Earlyb rdDec der.getDec der()));
  }

  @Overr de
  publ c Http.Server conf gureAdm nHttpServer(Http.Server server) {
    return server.w hMon or(uncaughtExcept onHandler);
  }

  @Overr de
  publ c vo d preMa n() {
    conf gureFromFlagsAndSetupLogg ng();
    logEarlyb rd nfo();
    LOG. nfo("Start ng preMa n()");

    Bu ld nfoStats.export();
    PlatformStatsExporter.exportPlatformStats();

    // Use   own except on handler to mon or all unhandled except ons.
    Thread.setDefaultUncaughtExcept onHandler((thread, e) -> {
      LOG.error(" nvoked default uncaught except on handler.");
      uncaughtExcept onHandler.handle(e);
    });
    LOG. nfo("Reg stered unhandled except on mon or.");

    Kerberos.k n (
        Earlyb rdConf g.getStr ng("kerberos_user", ""),
        Earlyb rdConf g.getStr ng("kerberos_keytab_path", "")
    );

    LOG. nfo("Creat ng earlyb rd server.");
    earlyb rdServer = makeEarlyb rdServer();

    uncaughtExcept onHandler.setShutdownHook(() -> {
      earlyb rdServer.shutdown();
      t .close();
    });

    earlyb rdAdm nManager = Earlyb rdAdm nManager.create(earlyb rdServer);
    earlyb rdAdm nManager.start();
    LOG. nfo("Started adm n  nterface.");

    setupThr ft bForms();
    setupDec der bForms();

    LOG. nfo("Opened thr ft serv ng form.");

    LOG. nfo("preMa n() complete.");
  }

  @Overr de
  publ c vo d ma n() throws  nterruptedExcept on, T  outExcept on, Earlyb rdStartupExcept on {
     nnerMa n();
  }

  /**
   * Sett ng up an  nnerMa n() so that tests can mock out t  contents of ma n w hout  nterfer ng
   * w h reflect on be ng done  n App.scala look ng for a  thod na d "ma n".
   */
  @V s bleForTest ng
  vo d  nnerMa n() throws T  outExcept on,  nterruptedExcept on, Earlyb rdStartupExcept on {
    LOG. nfo("Start ng ma n().");

    //  f t   thod throws, Tw terServer w ll catch t  except on and call close, so   don't
    // catch    re.
    try {
      earlyb rdServer.start();
    } catch (Throwable throwable) {
      LOG.error("Except on wh le start ng:", throwable);
      throw throwable;
    }

    Awa .ready(adm nHttpServer());
    LOG. nfo("ma n() complete.");
  }

  @Overr de
  publ c vo d onEx () {
    LOG. nfo("Start ng onEx ()");
    earlyb rdServer.shutdown();
    try {
      earlyb rdAdm nManager.doShutdown();
    } catch ( nterruptedExcept on e) {
      LOG.warn("earlyb rdAdm nManager shutdown was  nterrupted w h " + e);
    }
    LOG. nfo("onEx () complete.");
  }
}
