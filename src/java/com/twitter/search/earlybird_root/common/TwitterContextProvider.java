package com.tw ter.search.earlyb rd_root.common;

 mport javax. nject.S ngleton;

 mport scala.Opt on;

 mport com.tw ter.context.Tw terContext;
 mport com.tw ter.context.thr ftscala.V e r;
 mport com.tw ter.search.Tw terContextPerm ;

/**
 * T  class  s needed to prov de an easy way for un  tests to " nject"
 * a Tw terContext V e r
 */
@S ngleton
publ c class Tw terContextProv der {
  publ c Opt on<V e r> get() {
    return Tw terContext.acqu re(Tw terContextPerm .get()).apply();
  }
}
