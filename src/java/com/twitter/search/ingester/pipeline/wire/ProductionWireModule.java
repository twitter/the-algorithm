package com.tw ter.search. ngester.p pel ne.w re;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport java.ut l.concurrent.Executors;
 mport javax.annotat on.Nullable;
 mport javax.nam ng.Context;
 mport javax.nam ng. n  alContext;
 mport javax.nam ng.Nam ngExcept on;

 mport scala.Opt on;
 mport scala.collect on.JavaConvers ons$;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;

 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.cl ents.producer.Part  oner;
 mport org.apac .kafka.common.ser al zat on.Deser al zer;
 mport org.apac .kafka.common.ser al zat on.Ser al zer;
 mport org.apac .thr ft.TBase;
 mport org.apac .thr ft.protocol.TB naryProtocol;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.dec der.Dec derFactory;
 mport com.tw ter.dec der.Dec derFactory$;
 mport com.tw ter.dec der.dec s onmaker.Dec s onMaker;
 mport com.tw ter.dec der.dec s onmaker.MutableDec s onMaker;
 mport com.tw ter.eventbus.cl ent.EventBusSubscr ber;
 mport com.tw ter.eventbus.cl ent.EventBusSubscr berBu lder;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.Thr ftMux;
 mport com.tw ter.f nagle.bu lder.Cl entBu lder;
 mport com.tw ter.f nagle.bu lder.Cl entConf g;
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er;
 mport com.tw ter.f nagle.mtls.cl ent.MtlsThr ftMuxCl ent;
 mport com.tw ter.f nagle.mux.transport.Opportun st cTls;
 mport com.tw ter.f nagle.serv ce.RetryPol cy;
 mport com.tw ter.f nagle.stats.DefaultStatsRece ver;
 mport com.tw ter.f nagle.thr ft.Cl ent d;
 mport com.tw ter.f nagle.thr ft.Thr ftCl entRequest;
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer;
 mport com.tw ter.g zmoduck.thr ftjava.UserServ ce;
 mport com.tw ter. tastore.cl ent_v2. tastoreCl ent;
 mport com.tw ter.p nk_floyd.thr ft.Storer;
 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.common.relevance.class f ers.T etOffens veEvaluator;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l. o.kafka.F nagleKafkaCl entUt ls;
 mport com.tw ter.search. ngester.p pel ne.strato_fetc rs.Aud oSpaceCoreFetc r;
 mport com.tw ter.search. ngester.p pel ne.strato_fetc rs.Aud oSpacePart c pantsFetc r;
 mport com.tw ter.search. ngester.p pel ne.strato_fetc rs.Na dEnt yFetc r;
 mport com.tw ter.search. ngester.p pel ne.ut l.Pengu nVers onsUt l;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neExcept onHandler;
 mport com.tw ter.storage.cl ent.manhattan.kv.JavaManhattanKVEndpo nt;
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl ent;
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams;
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo ntBu lder;
 mport com.tw ter.strato.cl ent.Cl ent;
 mport com.tw ter.strato.cl ent.Strato;
 mport com.tw ter.t etyp e.thr ftjava.T etServ ce;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

/**
 * T   nject on module that prov des all product on b nd ngs.
 */
publ c class Product onW reModule extends W reModule {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Product onW reModule.class);

  pr vate stat c f nal Str ng DEC DER_BASE = "conf g/ ngester- ndexer-dec der.yml";
  pr vate stat c f nal Str ng GEOCODE_APP_ D = "search_ ngester_readonly";
  pr vate stat c f nal Str ng CLUSTER_DEST_NAME = "";

  pr vate stat c f nal Str ng JND _G ZMODUCK_DEST = JND _P PEL NE_ROOT + "g zmoduckDest";

  pr vate stat c f nal Str ng PENGU N_VERS ONS_JND _NAME = JND _P PEL NE_ROOT + "pengu nVers ons";
  pr vate stat c f nal Str ng SEGMENT_BUFFER_S ZE_JND _NAME =
      JND _P PEL NE_ROOT + "seg ntBufferS ze";
  pr vate stat c f nal Str ng SEGMENT_SEAL_DELAY_T ME_MS_JND _NAME =
      JND _P PEL NE_ROOT + "seg ntSealDelayT  Ms";
  pr vate stat c f nal Str ng JND _DL_UR  = JND _P PEL NE_ROOT + "d str butedlog/dlUr ";
  pr vate stat c f nal Str ng JND _DL_CONF G_F LE =
      JND _P PEL NE_ROOT + "d str butedlog/conf gF le";
  pr vate stat c f nal Str ng CLUSTER_JND _NAME = JND _P PEL NE_ROOT + "cluster";

  pr vate stat c f nal Str ng T ME_SL CE_MANAGER_ROOT_PATH = "";
  pr vate stat c f nal Str ng MAX_T MESL CES_JND _NAME =
      T ME_SL CE_MANAGER_ROOT_PATH + "hashPart  on/maxT  Sl ces";
  pr vate stat c f nal Str ng MAX_SEGMENT_S ZE_JND _NAME =
      T ME_SL CE_MANAGER_ROOT_PATH + "hashPart  on/maxSeg ntS ze";
  pr vate stat c f nal Str ng NUM_PART T ONS_JND _NAME =
      T ME_SL CE_MANAGER_ROOT_PATH + "hashPart  on/numPart  ons";

  pr vate stat c f nal Str ng P NK_CL ENT_ D = "search_ ngester";

  pr vate f nal Dec der dec der;
  pr vate f nal MutableDec s onMaker mutableDec s onMaker;
  pr vate f nal  nt part  on;
  pr vate P pel neExcept onHandler p pel neExcept onHandler;
  pr vate f nal Strato taStoreW reModule strato taStoreW reModule;

  pr vate f nal Cl ent stratoCl ent;

  pr vate Serv ce dent f er serv ce dent f er = Serv ce dent f er.empty();

  pr vate L st<Pengu nVers on> pengu nVers ons;

  publ c Product onW reModule(Str ng dec derOverlay,  nt part  on, Opt on<Str ng>
      serv ce dent f erFlag) {
    mutableDec s onMaker = new MutableDec s onMaker();
    dec der = Dec derFactory.get()
        .w hBaseConf g(DEC DER_BASE)
        .w hOverlayConf g(dec derOverlay)
        .w hRefreshBase(false)
        .w hDec s onMakers(
             mmutableL st.<Dec s onMaker>bu lder()
                .add(mutableDec s onMaker)
                .addAll(JavaConvers ons$.MODULE$.asJavaCollect on(
                    Dec derFactory$.MODULE$.DefaultDec s onMakers()))
                .bu ld())
        .apply();
    t .part  on = part  on;
    t .strato taStoreW reModule = new Strato taStoreW reModule(t );
     f (serv ce dent f erFlag. sDef ned()) {
      t .serv ce dent f er =
          Serv ce dent f er.flagOfServ ce dent f er().parse(serv ce dent f erFlag.get());
    }

    t .stratoCl ent = Strato.cl ent()
        .w hMutualTls(serv ce dent f er)
        .w hRequestT  out(Durat on.fromM ll seconds(500))
        .bu ld();
  }

  publ c Product onW reModule(Str ng dec derOverlay,
                               nt part  on,
                              P pel neExcept onHandler p pel neExcept onHandler,
                              Opt on<Str ng> serv ce dent f erFlag) {
    t (dec derOverlay, part  on, serv ce dent f erFlag);
    t .p pel neExcept onHandler = p pel neExcept onHandler;
  }

  publ c vo d setP pel neExcept onHandler(P pel neExcept onHandler p pel neExcept onHandler) {
    t .p pel neExcept onHandler = p pel neExcept onHandler;
  }

  @Overr de
  publ c Serv ce dent f er getServ ce dent f er() {
    return serv ce dent f er;
  }

  @Overr de
  publ c Part  onMapp ngManager getPart  onMapp ngManager() {
    return Part  onMapp ngManager.get nstance();
  }

  @Overr de
  publ c JavaManhattanKVEndpo nt getJavaManhattanKVEndpo nt() {
    Precond  ons.c ckNotNull(serv ce dent f er,
        "Can't create Manhattan cl ent w h S2S aut nt cat on because Serv ce  dent f er  s null");
    LOG. nfo(Str ng.format("Serv ce  dent f er for Manhattan cl ent: %s",
        Serv ce dent f er.asStr ng(serv ce dent f er)));
    ManhattanKVCl entMtlsParams mtlsParams = ManhattanKVCl entMtlsParams.apply(serv ce dent f er,
        ManhattanKVCl entMtlsParams.apply$default$2(),
        Opportun st cTls.Requ red()
    );
    return ManhattanKVEndpo ntBu lder
        .apply(ManhattanKVCl ent.apply(GEOCODE_APP_ D, CLUSTER_DEST_NAME, mtlsParams))
        .bu ldJava();
  }

  @Overr de
  publ c Dec der getDec der() {
    return dec der;
  }

  // S nce MutableDec s onMaker  s needed only for product on Tw terServer, t   thod  s def ned
  // only  n Product onW reModule.
  publ c MutableDec s onMaker getMutableDec s onMaker() {
    return mutableDec s onMaker;
  }

  @Overr de
  publ c  nt getPart  on() {
    return part  on;
  }

  @Overr de
  publ c P pel neExcept onHandler getP pel neExcept onHandler() {
    return p pel neExcept onHandler;
  }

  @Overr de
  publ c Storer.Serv ce face getStorer(Durat on requestT  out,  nt retr es) {
    TB naryProtocol.Factory factory = new TB naryProtocol.Factory();

    MtlsThr ftMuxCl ent mtlsThr ftMuxCl ent = new MtlsThr ftMuxCl ent(
        Thr ftMux.cl ent().w hCl ent d(new Cl ent d(P NK_CL ENT_ D)));
    Thr ftMux.Cl ent tmuxCl ent = mtlsThr ftMuxCl ent
        .w hMutualTls(serv ce dent f er)
        .w hOpportun st cTls(Opportun st cTls.Requ red());

    Cl entBu lder<
        Thr ftCl entRequest,
        byte[],
        Cl entConf g.Yes,
        Cl entConf g.Yes,
        Cl entConf g.Yes> bu lder = Cl entBu lder.get()
          .dest("")
          .requestT  out(requestT  out)
          .retr es(retr es)
          .t  out(requestT  out.mul(retr es))
          .stack(tmuxCl ent)
          .na ("p nkcl ent")
          .reportTo(DefaultStatsRece ver.get());
    return new Storer.Serv ceToCl ent(Cl entBu lder.safeBu ld(bu lder), factory);
  }

  @Overr de
  publ c  tastoreCl ent get tastoreCl ent() throws Nam ngExcept on {
    return strato taStoreW reModule.get tastoreCl ent(t .serv ce dent f er);
  }

  @Overr de
  publ c ExecutorServ ce getThreadPool( nt numThreads) {
    return Executors.newF xedThreadPool(numThreads);
  }

  @Overr de
  publ c T etServ ce.Serv ceToCl ent getT etyP eCl ent(Str ng t etyp eCl ent d)
      throws Nam ngExcept on {
    return T etyP eW reModule.getT etyP eCl ent(t etyp eCl ent d, serv ce dent f er);
  }

  @Overr de
  publ c UserServ ce.Serv ceToCl ent getG zmoduckCl ent(Str ng cl ent d)
      throws Nam ngExcept on {
    Context context = new  n  alContext();
    Str ng dest = (Str ng) context.lookup(JND _G ZMODUCK_DEST);

    MtlsThr ftMuxCl ent mtlsThr ftMuxCl ent = new MtlsThr ftMuxCl ent(
        Thr ftMux.cl ent().w hCl ent d(new Cl ent d(cl ent d)));

    Serv ce<Thr ftCl entRequest, byte[]> cl entBu lder =
        Cl entBu lder.safeBu ld(
            Cl entBu lder
                .get()
                .requestT  out(Durat on.fromM ll seconds(800))
                .retryPol cy(RetryPol cy.tr es(3))
                .na ("search_ ngester_g zmoduck_cl ent")
                .reportTo(DefaultStatsRece ver.get())
                .daemon(true)
                .dest(dest)
                .stack(mtlsThr ftMuxCl ent.w hMutualTls(serv ce dent f er)
                        .w hOpportun st cTls(Opportun st cTls.Requ red())));
    return new UserServ ce.Serv ceToCl ent(cl entBu lder, new TB naryProtocol.Factory());
  }

  @Overr de
  publ c <T extends TBase<?, ?>> EventBusSubscr ber<T> createEventBusSubscr ber(
      Funct on<T, Future<?>> process,
      Class<T> thr ftStructClass,
      Str ng eventBusSubscr ber d,
       nt maxConcurrentEvents) {
    Precond  ons.c ckNotNull(serv ce dent f er,
        "Can't create EventBusSubscr ber w h S2S auth because Serv ce  dent f er  s null");
    LOG. nfo(Str ng.format("Serv ce  dent f er for EventBusSubscr ber Manhattan cl ent: %s",
        Serv ce dent f er.asStr ng(serv ce dent f er)));
    //   set t  processT  outMs para ter  re to be Durat on.Top because   do not want to read
    // more events from EventBus  f   are exper enc ng back pressure and cannot wr e t m to t 
    // downstream queue.
    return EventBusSubscr berBu lder.apply()
        .subscr ber d(eventBusSubscr ber d)
        .sk pToLatest(false)
        .fromAllZones(true)
        .statsRece ver(DefaultStatsRece ver.get().scope("eventbus"))
        .thr ftStruct(thr ftStructClass)
        .serv ce dent f er(serv ce dent f er)
        .maxConcurrentEvents(maxConcurrentEvents)
        .processT  out(Durat on.Top())
        .bu ld(process);
  }

  @Overr de
  publ c Clock getClock() {
    return Clock.SYSTEM_CLOCK;
  }

  @Overr de
  publ c T etOffens veEvaluator getT etOffens veEvaluator() {
    return new T etOffens veEvaluator();
  }

  @Overr de
  publ c Earlyb rdCluster getEarlyb rdCluster() throws Nam ngExcept on {
    Context jnd Context = new  n  alContext();
    Str ng clusterNa  = (Str ng) jnd Context.lookup(CLUSTER_JND _NAME);
    return Earlyb rdCluster.valueOf(clusterNa .toUpperCase());
  }

  @Overr de
  publ c L st<Pengu nVers on> getPengu nVers ons() throws Nam ngExcept on {
    Context context = new  n  alContext();
    Str ng pengu nVers onsStr = (Str ng) context.lookup(PENGU N_VERS ONS_JND _NAME);
    pengu nVers ons = new ArrayL st<>();

    for (Str ng pengu nVers on : pengu nVers onsStr.spl (",")) {
      Pengu nVers on pv = Pengu nVers on.vers onFromByteValue(Byte.parseByte(pengu nVers on));
       f (Pengu nVers onsUt l. sPengu nVers onAva lable(pv, dec der)) {
        pengu nVers ons.add(pv);
      }
    }

    Precond  ons.c ckArgu nt(pengu nVers ons.s ze() > 0,
        "At least one pengu n vers on must be spec f ed.");

    return pengu nVers ons;
  }

  //   update pengu n vers ons v a dec ders  n order to d sable one  n case of an e rgency.
  @Overr de
  publ c L st<Pengu nVers on> getCurrentlyEnabledPengu nVers ons() {
    return Pengu nVers onsUt l.f lterPengu nVers onsW hDec ders(pengu nVers ons, dec der);
  }

  @Overr de
  publ c Na dEnt yFetc r getNa dEnt yFetc r() {
    return new Na dEnt yFetc r(stratoCl ent);
  }

  @Overr de
  publ c Aud oSpacePart c pantsFetc r getAud oSpacePart c pantsFetc r() {
    return new Aud oSpacePart c pantsFetc r(stratoCl ent);
  }

  @Overr de
  publ c Aud oSpaceCoreFetc r getAud oSpaceCoreFetc r() {
    return new Aud oSpaceCoreFetc r(stratoCl ent);
  }

  @Overr de
  publ c <T> KafkaConsu r<Long, T> newKafkaConsu r(
      Str ng kafkaClusterPath, Deser al zer<T> deser al zer, Str ng cl ent d, Str ng group d,
       nt maxPollRecords) {
    return F nagleKafkaCl entUt ls.newKafkaConsu r(
        kafkaClusterPath, deser al zer, cl ent d, group d, maxPollRecords);
  }

  @Overr de
  publ c <T> Block ngF nagleKafkaProducer<Long, T> newF nagleKafkaProducer(
      Str ng kafkaClusterPath, Ser al zer<T> ser al zer, Str ng cl ent d,
      @Nullable Class<? extends Part  oner> part  onerClass) {
    return F nagleKafkaCl entUt ls.newF nagleKafkaProducer(
        kafkaClusterPath, true, ser al zer, cl ent d, part  onerClass);
  }
}
