package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.L st;

 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.converter.earlyb rd.Delayed ndex ngConverter;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdSc maCreateTool;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducedTypes( ngesterThr ftVers onedEvents.class)
publ c class ConvertDelayed ssageToThr ftStage extends Tw terBaseStage
    <Tw ter ssage,  ngesterThr ftVers onedEvents> {
  pr vate L st<Pengu nVers on> pengu nVers onL st;
  pr vate F eldStatExporter f eldStatExporter;
  pr vate Delayed ndex ngConverter  ssageConverter;

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    Sc ma sc ma;
    try {
      sc ma = Earlyb rdSc maCreateTool.bu ldSc ma(Precond  ons.c ckNotNull(earlyb rdCluster));
    } catch (Sc ma.Sc maVal dat onExcept on e) {
      throw new StageExcept on(t , e);
    }

    pengu nVers onL st = w reModule.getPengu nVers ons();
     ssageConverter = new Delayed ndex ngConverter(sc ma, dec der);
    f eldStatExporter = new F eldStatExporter("unsorted_urls", sc ma, pengu nVers onL st);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not an  ngesterTw ter ssage  nstance: " + obj);
    }

    pengu nVers onL st = w reModule.getCurrentlyEnabledPengu nVers ons();
    f eldStatExporter.updatePengu nVers ons(pengu nVers onL st);

     ngesterTw ter ssage  ssage =  ngesterTw ter ssage.class.cast(obj);
    for ( ngesterThr ftVers onedEvents events : bu ldVers onedEvents( ssage)) {
      f eldStatExporter.addF eldStats(events);
      em AndCount(events);
    }
  }

  /**
   *  thod that converts all URL and card related f elds and features of a Tw ter ssage to a
   * Thr ftVers onedEvents  nstance.
   *
   * @param tw ter ssage An  ngesterThr ftVers onedEvents  nstance to be converted.
   * @return T  correspond ng Thr ftVers onedEvents  nstance.
   */
  pr vate L st< ngesterThr ftVers onedEvents> bu ldVers onedEvents(
       ngesterTw ter ssage tw ter ssage) {
    L st<Thr ftVers onedEvents> vers onedEvents =
         ssageConverter.convert ssageToOutOfOrderAppendAndFeatureUpdate(
            tw ter ssage, pengu nVers onL st);
    Precond  ons.c ckArgu nt(
        vers onedEvents.s ze() == 2,
        "Delayed ndex ngConverter produced an  ncorrect number of Thr ftVers onedEvents.");
    return L sts.newArrayL st(
        to ngesterThr ftVers onedEvents(vers onedEvents.get(0), tw ter ssage),
        to ngesterThr ftVers onedEvents(vers onedEvents.get(1), tw ter ssage));
  }

  pr vate  ngesterThr ftVers onedEvents to ngesterThr ftVers onedEvents(
      Thr ftVers onedEvents vers onedEvents,  ngesterTw ter ssage tw ter ssage) {
    //   don't want to propagate t  sa  DebugEvents  nstance to mult ple
    //  ngesterThr ftVers onedEvents  nstances, because future stages m ght want to add new events
    // to t  l st for mult ple events at t  sa  t  , wh ch would result  n a
    // ConcurrentMod f cat onExcept on. So   need to create a DebugEvents deep copy.
     ngesterThr ftVers onedEvents  ngesterThr ftVers onedEvents =
        new  ngesterThr ftVers onedEvents(tw ter ssage.getUser d());
     ngesterThr ftVers onedEvents.setDarkWr e(false);
     ngesterThr ftVers onedEvents.set d(tw ter ssage.getT et d());
     ngesterThr ftVers onedEvents.setVers onedEvents(vers onedEvents.getVers onedEvents());
     ngesterThr ftVers onedEvents.setDebugEvents(tw ter ssage.getDebugEvents().deepCopy());
    return  ngesterThr ftVers onedEvents;
  }
}
