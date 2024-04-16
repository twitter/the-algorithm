package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.Opt onal;

 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.converter.earlyb rd.Bas c ndex ngConverter;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdSc maCreateTool;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class Convert ssageToThr ftStage extends Tw terBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Convert ssageToThr ftStage.class);

  pr vate L st<Pengu nVers on> pengu nVers onL st;
  pr vate Str ng thr ftVers onedEventsBranchNa ;
  pr vate F eldStatExporter f eldStatExporter;
  pr vate Bas c ndex ngConverter  ssageConverter;

  pr vate SearchCounter tw ter ssageToTveErrorCount;

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
    tw ter ssageToTveErrorCount = SearchCounter.export(
        getStageNa Pref x() + "_ ngester_convert_tw ter_ ssage_to_tve_error_count");
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    Sc ma sc ma;
    try {
      sc ma = Earlyb rdSc maCreateTool.bu ldSc ma(Precond  ons.c ckNotNull(earlyb rdCluster));
    } catch (Sc ma.Sc maVal dat onExcept on e) {
      throw new StageExcept on(t , e);
    }

    pengu nVers onL st = w reModule.getPengu nVers ons();
    Precond  ons.c ckState(Str ngUt ls. sNotBlank(thr ftVers onedEventsBranchNa ));
     ssageConverter = new Bas c ndex ngConverter(sc ma, earlyb rdCluster);
    f eldStatExporter = new F eldStatExporter("unsorted_t ets", sc ma, pengu nVers onL st);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not an  ngesterTw ter ssage  nstance: " + obj);
    }

    pengu nVers onL st = w reModule.getCurrentlyEnabledPengu nVers ons();
    f eldStatExporter.updatePengu nVers ons(pengu nVers onL st);

     ngesterTw ter ssage  ssage =  ngesterTw ter ssage.class.cast(obj);

    Opt onal< ngesterThr ftVers onedEvents> maybeEvents = bu ldVers onedEvents( ssage);
     f (maybeEvents. sPresent()) {
       ngesterThr ftVers onedEvents events = maybeEvents.get();
      f eldStatExporter.addF eldStats(events);
      em ToBranchAndCount(thr ftVers onedEventsBranchNa , events);
    }

    em AndCount( ssage);
  }

  /**
   *  thod that converts a Tw ter ssage to a Thr ftVers onedEvents.
   *
   * @param tw ter ssage An  ngesterThr ftVers onedEvents  nstance to be converted.
   * @return T  correspond ng Thr ftVers onedEvents.
   */
  pr vate Opt onal< ngesterThr ftVers onedEvents> bu ldVers onedEvents(
       ngesterTw ter ssage tw ter ssage) {
     ngesterThr ftVers onedEvents  ngesterEvents =
        new  ngesterThr ftVers onedEvents(tw ter ssage.getUser d());
     ngesterEvents.setDarkWr e(false);
     ngesterEvents.set d(tw ter ssage.getT et d());

    //   w ll em  both t  or g nal Tw ter ssage, and t  Thr ftVers onedEvents  nstance, so  
    // need to make sure t y have separate DebugEvents cop es.
     ngesterEvents.setDebugEvents(tw ter ssage.getDebugEvents().deepCopy());

    try {
      Thr ftVers onedEvents vers onedEvents =
           ssageConverter.convert ssageToThr ft(tw ter ssage, true, pengu nVers onL st);
       ngesterEvents.setVers onedEvents(vers onedEvents.getVers onedEvents());
      return Opt onal.of( ngesterEvents);
    } catch ( OExcept on e) {
      LOG.error("Fa led to convert t et " + tw ter ssage.getT et d() + " from Tw ter ssage "
                + "to Thr ftVers onedEvents for Pengu n vers ons " + pengu nVers onL st,
                e);
      tw ter ssageToTveErrorCount. ncre nt();
    }
    return Opt onal.empty();
  }

  publ c vo d setThr ftVers onedEventsBranchNa (Str ng thr ftVers onedEventsBranchNa ) {
    t .thr ftVers onedEventsBranchNa  = thr ftVers onedEventsBranchNa ;
  }
}
