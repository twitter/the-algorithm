package com.tw ter.search. ngester.p pel ne.ut l;
 mport java.ut l.concurrent.T  Un ;
 mport com.tw ter.common.base.MorePrecond  ons;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport org.apac .commons.p pel ne.stage.StageT  r;
/**
 * Adds sc ence stats export to StageT  r
 */
publ c class  ngesterStageT  r extends StageT  r {
  pr vate f nal Str ng na ;
  pr vate f nal SearchT  rStats t  r;

  publ c  ngesterStageT  r(Str ng statNa ) {
    na  = MorePrecond  ons.c ckNotBlank(statNa );
    t  r = SearchT  rStats.export(na , T  Un .NANOSECONDS, true);
  }

  publ c Str ng getNa () {
    return na ;
  }

  @Overr de
  publ c vo d start() {
    // T  overr de  s not necessary;    s added for code readab l y.
    // super.start puts t  current t    n startT  
    super.start();
  }

  @Overr de
  publ c vo d stop() {
    super.stop();
    long runT   = System.nanoT  () - startT  .get();
    t  r.t  r ncre nt(runT  );
  }
}
