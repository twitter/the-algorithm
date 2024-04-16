package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Collect on;
 mport javax.nam ng.Nam ngExcept on;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducesConsu d;

 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.ut l.Batc dEle nt;
 mport com.tw ter.search. ngester.p pel ne.ut l.ManhattanCodedLocat onProv der;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.ut l.Future;

/**
 * Read-only stage for look ng up locat on  nfo and populat ng   onto  ssages.
 */
@Consu dTypes( ngesterTw ter ssage.class)
@ProducesConsu d
publ c f nal class PopulateCodedLocat onsBatc dStage
    extends Tw terBatc dBaseStage< ngesterTw ter ssage,  ngesterTw ter ssage> {
  pr vate stat c f nal Str ng GEOCODE_DATASET_NAME = " ngester_geocode_prof le_locat on";

  pr vate ManhattanCodedLocat onProv der manhattanCodedLocat onProv der = null;

  /**
   * Requ re lat/lon from Tw ter ssage  nstead of lookup from coded_locat ons,
   * do not batch sql, and s mply em   ssages passed  n w h reg ons populated on t m
   * rat r than em t ng to  ndex ng queues.
   */
  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    super.do nnerPreprocess();
    common nnerSetup();
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    super. nnerSetup();
    common nnerSetup();
  }

  pr vate vo d common nnerSetup() throws Nam ngExcept on {
    t .manhattanCodedLocat onProv der = ManhattanCodedLocat onProv der.createW hEndpo nt(
        w reModule.getJavaManhattanKVEndpo nt(),
        getStageNa Pref x(),
        GEOCODE_DATASET_NAME);
  }

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
  }

  @Overr de
  protected Class< ngesterTw ter ssage> getQueueObjectType() {
    return  ngesterTw ter ssage.class;
  }

  @Overr de
  protected Future<Collect on< ngesterTw ter ssage>>  nnerProcessBatch(Collect on<Batc dEle nt
      < ngesterTw ter ssage,  ngesterTw ter ssage>> batch) {

    Collect on< ngesterTw ter ssage> batc dEle nts = extractOnlyEle ntsFromBatch(batch);
    return manhattanCodedLocat onProv der.populateCodedLatLon(batc dEle nts);
  }

  @Overr de
  protected boolean needsToBeBatc d( ngesterTw ter ssage  ssage) {
    return ! ssage.hasGeoLocat on() && ( ssage.getLocat on() != null)
        && ! ssage.getLocat on(). sEmpty();
  }

  @Overr de
  protected  ngesterTw ter ssage transform( ngesterTw ter ssage ele nt) {
    return ele nt;
  }
}
