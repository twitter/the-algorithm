package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.cr_m xer.model.GraphS ce nfo
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.ut l.Future

/***
 * A S ceGraphFetc r  s a tra  that extends from `S ceFetc r`
 * and  s spec al zed  n tackl ng User Graph (eg., RealGraphOon, FRS) fetch.
 *
 * T  [[ResultType]] of a S ceGraphFetc r  s a `GraphS ce nfo` wh ch conta ns a userSeedSet.
 * W n   pass  n user d, t  underly ng store returns one GraphS ce nfo.
 */
tra  S ceGraphFetc r extends S ceFetc r[GraphS ce nfo] {
  protected f nal val DefaultSeedScore = 1.0
  protected def graphS ceType: S ceType

  /***
   * RawDataType conta ns a consu rs seed User d and a score (  ght)
   */
  protected type RawDataType = (User d, Double)

  def trackStats(
    query: Fetc rQuery
  )(
    func: => Future[Opt on[GraphS ce nfo]]
  ): Future[Opt on[GraphS ce nfo]] = {
    val productScopedStats = stats.scope(query.product.or g nalNa )
    val productUserStateScopedStats = productScopedStats.scope(query.userState.toStr ng)
    StatsUt l
      .trackOpt onStats(productScopedStats) {
        StatsUt l
          .trackOpt onStats(productUserStateScopedStats) {
            func
          }
      }
  }

  // Track per  em stats on t  fetc d graph results
  def trackPer emStats(
    query: Fetc rQuery
  )(
    func: => Future[Opt on[Seq[RawDataType]]]
  ): Future[Opt on[Seq[RawDataType]]] = {
    val productScopedStats = stats.scope(query.product.or g nalNa )
    val productUserStateScopedStats = productScopedStats.scope(query.userState.toStr ng)
    StatsUt l.trackOpt on emsStats(productScopedStats) {
      StatsUt l.trackOpt on emsStats(productUserStateScopedStats) {
        func
      }
    }
  }

  /***
   * Convert Seq[RawDataType]  nto GraphS ce nfo
   */
  protected f nal def convertGraphS ce nfo(
    userW hScores: Seq[RawDataType]
  ): GraphS ce nfo = {
    GraphS ce nfo(
      s ceType = graphS ceType,
      seedW hScores = userW hScores.map { userW hScore =>
        userW hScore._1 -> userW hScore._2
      }.toMap
    )
  }
}
