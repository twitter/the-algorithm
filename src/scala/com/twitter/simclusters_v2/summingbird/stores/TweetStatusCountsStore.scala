package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.t etyp e.thr ftscala.{GetT etOpt ons, StatusCounts, T et}

object T etStatusCountsStore {

  def t etStatusCountsStore(
    stratoCl ent: Cl ent,
    column: Str ng
  ): ReadableStore[T et d, StatusCounts] = {
    StratoFetchableStore
      .w hV ew[T et d, GetT etOpt ons, T et](stratoCl ent, column, getT etOpt ons)
      .mapValues(_.counts.getOrElse(emptyStatusCount))
  }

  pr vate val emptyStatusCount = StatusCounts()

  pr vate val getT etOpt ons =
    GetT etOpt ons(
       ncludeRet etCount = true,
       ncludeReplyCount = true,
       ncludeFavor eCount = true,
       ncludeQuoteCount = true)
}
