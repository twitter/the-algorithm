package com.tw ter.search. ngester.p pel ne.tw ter.kafka;

 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;

 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.tw ter.Thr ftVers onedEventsConverter;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;

@Consu dTypes( ngesterTw ter ssage.class)
publ c class DeleteUpdateEventsKafkaProducerStage extends KafkaProducerStage
    < ngesterTw ter ssage> {
  pr vate Thr ftVers onedEventsConverter converter;

  publ c DeleteUpdateEventsKafkaProducerStage() {
    super();
  }

  publ c DeleteUpdateEventsKafkaProducerStage(Str ng top cNa , Str ng cl ent d,
                                              Str ng clusterPath) {
    super(top cNa , cl ent d, clusterPath);
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    super. nnerSetup();
    common nnerSetup();
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    super.do nnerPreprocess();
    common nnerSetup();
  }

  pr vate vo d common nnerSetup() throws Nam ngExcept on {
    converter = new Thr ftVers onedEventsConverter(w reModule.getPengu nVers ons());

  }
  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not an  ngesterTw ter ssage: " + obj);
    }

     ngesterTw ter ssage  ssage = ( ngesterTw ter ssage) obj;
     nnerRunF nalStageOfBranchV2( ssage);
  }

  @Overr de
  protected vo d  nnerRunF nalStageOfBranchV2( ngesterTw ter ssage  ssage) {
    converter.updatePengu nVers ons(w reModule.getCurrentlyEnabledPengu nVers ons());

    Precond  ons.c ckArgu nt( ssage.getFromUserTw ter d(). sPresent(),
        "M ss ng user  D.");

    super.tryToSendEventsToKafka(converter.toDelete(
         ssage.getT et d(),  ssage.getUser d(),  ssage.getDebugEvents()));
  }


}
