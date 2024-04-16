package com.tw ter.search. ngester.p pel ne.ut l;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.p pel ne.Feeder;
 mport org.apac .commons.p pel ne.stage. nstru ntedBaseStage;

publ c f nal class P pel neUt l {

  /**
   * Feed an object to a spec f ed stage.  Used for stages that follow t  pattern of
   * loop ng  ndef n ely  n t  f rst call to process() and don't care what t  object passed
   *  n  s, but st ll needs at least one  em fed to t  stage to start process ng.
   *
   * Examples of stages l ke t  are: EventBusReaderStage and KafkaBytesReaderStage
   *
   * @param stage stage to enqueue an arb rary object to.
   */
  publ c stat c vo d feedStartObjectToStage( nstru ntedBaseStage stage) {
    Feeder stageFeeder = stage.getStageContext().getStageFeeder(stage);
    Precond  ons.c ckNotNull(stageFeeder);
    stageFeeder.feed("off to t  races");
  }

  pr vate P pel neUt l() { /* prevent  nstant at on */ }
}
