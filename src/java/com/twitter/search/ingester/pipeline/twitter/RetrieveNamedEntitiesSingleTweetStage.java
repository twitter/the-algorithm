package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.concurrent.CompletableFuture;
 mport javax.nam ng.Nam ngExcept on;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.ut l.Funct on;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class Retr eveNa dEnt  esS ngleT etStage extends Tw terBaseStage
    < ngesterTw ter ssage, CompletableFuture< ngesterTw ter ssage>> {

  pr vate Na dEnt yHandler na dEnt yHandler;

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
     nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() {
    na dEnt yHandler = new Na dEnt yHandler(
        w reModule.getNa dEnt yFetc r(), dec der, getStageNa Pref x(),
        "s ngle_t et");
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterTw ter ssage)) {
      throw new StageExcept on(t , "Object  s not a  ngesterTw ter ssage object: " + obj);
    }
     ngesterTw ter ssage tw ter ssage = ( ngesterTw ter ssage) obj;

     f (na dEnt yHandler.shouldRetr eve(tw ter ssage)) {
      na dEnt yHandler.retr eve(tw ter ssage)
          .onSuccess(Funct on.cons(result -> {
            na dEnt yHandler.addEnt  esTo ssage(tw ter ssage, result);
            em AndCount(tw ter ssage);
          }))
          .onFa lure(Funct on.cons(throwable -> {
            na dEnt yHandler. ncre ntErrorCount();
            em AndCount(tw ter ssage);
          }));
    } else {
      em AndCount(tw ter ssage);
    }
  }

  @Overr de
  protected CompletableFuture< ngesterTw ter ssage>  nnerRunStageV2( ngesterTw ter ssage
                                                                       ssage) {
    CompletableFuture< ngesterTw ter ssage> cf = new CompletableFuture<>();

     f (na dEnt yHandler.shouldRetr eve( ssage)) {
      na dEnt yHandler.retr eve( ssage)
          .onSuccess(Funct on.cons(result -> {
            na dEnt yHandler.addEnt  esTo ssage( ssage, result);
            cf.complete( ssage);
          }))
          .onFa lure(Funct on.cons(throwable -> {
            na dEnt yHandler. ncre ntErrorCount();
            cf.complete( ssage);
          }));
    } else {
      cf.complete( ssage);
    }

    return cf;
  }
}
