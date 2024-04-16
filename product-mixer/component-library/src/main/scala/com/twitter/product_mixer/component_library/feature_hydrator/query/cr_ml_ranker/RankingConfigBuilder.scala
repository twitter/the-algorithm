package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.cr_ml_ranker

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.cr_ml_ranker.{thr ftscala => t}

/**
 * Bu lder for construct ng a rank ng conf g from a query
 */
tra  Rank ngConf gBu lder {
  def apply(query: P pel neQuery): t.Rank ngConf g
}
