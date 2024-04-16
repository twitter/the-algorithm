package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport org.apac .lucene.ut l.CloseableThreadLocal;

 mport com.tw ter.search.common.search.QueryCostProv der;

publ c class QueryCostTracker  mple nts QueryCostProv der {
  publ c stat c enum CostType {
    // For t  realt   seg nt   track how many post ng l st blocks
    // are accessed dur ng t  l fet   of one query.
    LOAD_REALT ME_POST NG_BLOCK(1),

    // Number of opt m zed post ng l st blocks
    LOAD_OPT M ZED_POST NG_BLOCK(1);

    pr vate f nal double cost;

    pr vate CostType(double cost) {
      t .cost = cost;
    }
  }

  pr vate stat c f nal CloseableThreadLocal<QueryCostTracker> TRACKERS
      = new CloseableThreadLocal<QueryCostTracker>() {
    @Overr de protected QueryCostTracker  n  alValue() {
      return new QueryCostTracker();
    }
  };

  publ c stat c QueryCostTracker getTracker() {
    return TRACKERS.get();
  }

  pr vate double totalCost;

  publ c vo d track(CostType costType) {
    totalCost += costType.cost;
  }

  publ c vo d reset() {
    totalCost = 0;
  }

  @Overr de
  publ c double getTotalCost() {
    return totalCost;
  }
}
