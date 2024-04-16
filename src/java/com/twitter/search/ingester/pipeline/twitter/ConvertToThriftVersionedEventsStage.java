package com.tw ter.search. ngester.p pel ne.tw ter;

 mport javax.nam ng.Nam ngExcept on;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducedTypes(Thr ftVers onedEvents.class)
publ c class ConvertToThr ftVers onedEventsStage extends Tw terBaseStage
    < ngesterTw ter ssage,  ngesterThr ftVers onedEvents> {
  pr vate Thr ftVers onedEventsConverter converter;

  @Overr de
  publ c vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    super.do nnerPreprocess();
     nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() throws Nam ngExcept on {
    converter = new Thr ftVers onedEventsConverter(w reModule.getPengu nVers ons());
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not an  ngesterTw ter ssage: " + obj);
    }

     ngesterTw ter ssage  ngesterTw ter ssage = ( ngesterTw ter ssage) obj;
     ngesterThr ftVers onedEvents maybeEvents = tryToConvert( ngesterTw ter ssage);

     f (maybeEvents == null) {
      throw new StageExcept on(
          t , "Object  s not a ret et or a reply: " +  ngesterTw ter ssage);
    }

    em AndCount(maybeEvents);

  }

  @Overr de
  protected  ngesterThr ftVers onedEvents  nnerRunStageV2( ngesterTw ter ssage  ssage) {
     ngesterThr ftVers onedEvents maybeEvents = tryToConvert( ssage);

     f (maybeEvents == null) {
      throw new P pel neStageRunt  Except on("Object  s not a ret et or reply, does not have to"
          + " pass to next stage");
    }

    return maybeEvents;
  }

  pr vate  ngesterThr ftVers onedEvents tryToConvert( ngesterTw ter ssage  ssage) {
    converter.updatePengu nVers ons(w reModule.getCurrentlyEnabledPengu nVers ons());

     f (! ssage. sRet et() && ! ssage. sReplyToT et()) {
      return null;
    }

     f ( ssage. sRet et()) {
      return converter.toOutOfOrderAppend(
           ssage.getRet et ssage().getShared d(),
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.RETWEETED_BY_USER_ D,
           ssage.getUser d(),
           ssage.getDebugEvents().deepCopy());
    }

    return converter.toOutOfOrderAppend(
         ssage.get nReplyToStatus d().get(),
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.REPL ED_TO_BY_USER_ D,
         ssage.getUser d(),
         ssage.getDebugEvents().deepCopy());
  }
}
