package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Collect on;
 mport javax.nam ng.Nam ngExcept on;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.Batc dEle nt;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.search. ngester.p pel ne.ut l.UserPropert esManager;
 mport com.tw ter.ut l.Future;

@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c class LookupUserPropert esBatc dStage extends Tw terBatc dBaseStage
    < ngesterTw ter ssage,  ngesterTw ter ssage> {

  protected UserPropert esManager userPropert esManager;

  @Overr de
  protected Class< ngesterTw ter ssage> getQueueObjectType() {
    return  ngesterTw ter ssage.class;
  }

  @Overr de
  protected Future<Collect on< ngesterTw ter ssage>>  nnerProcessBatch(Collect on<Batc dEle nt
      < ngesterTw ter ssage,  ngesterTw ter ssage>> batch) {
    Collect on< ngesterTw ter ssage> batc dEle nts = extractOnlyEle ntsFromBatch(batch);
    return userPropert esManager.populateUserPropert es(batc dEle nts);
  }

  @Overr de
  protected boolean needsToBeBatc d( ngesterTw ter ssage ele nt) {
    return true;
  }

  @Overr de
  protected  ngesterTw ter ssage transform( ngesterTw ter ssage ele nt) {
    return ele nt;
  }

  @Overr de
  publ c synchron zed vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    super.do nnerPreprocess();
    common nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    super. nnerSetup();
    common nnerSetup();
  }

  pr vate vo d common nnerSetup() throws Nam ngExcept on {
    userPropert esManager = new UserPropert esManager(w reModule.get tastoreCl ent());
  }
}
