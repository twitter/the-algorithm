package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.ut l.Future

/***
 * A S ceS gnalFetc r  s a tra  that extends from `S ceFetc r`
 * and  s spec al zed  n tackl ng S gnals (eg., USS, FRS) fetch.
 * Currently,   def ne S gnals as (but not l m ed to) a set of past engage nts that
 * t  user makes, such as RecentFav, RecentFollow, etc.
 *
 * T  [[ResultType]] of a S ceS gnalFetc r  s `Seq[S ce nfo]`. W n   pass  n user d,
 * t  underly ng store returns a l st of s gnals.
 */
tra  S ceS gnalFetc r extends S ceFetc r[Seq[S ce nfo]] {

  protected type S gnalConvertType

  def trackStats(
    query: Fetc rQuery
  )(
    func: => Future[Opt on[Seq[S ce nfo]]]
  ): Future[Opt on[Seq[S ce nfo]]] = {
    val productScopedStats = stats.scope(query.product.or g nalNa )
    val productUserStateScopedStats = productScopedStats.scope(query.userState.toStr ng)
    StatsUt l
      .trackOpt on emsStats(productScopedStats) {
        StatsUt l
          .trackOpt on emsStats(productUserStateScopedStats) {
            func
          }
      }
  }

  /***
   * Convert a l st of S gnals of type [[S gnalConvertType]]  nto S ce nfo
   */
  def convertS ce nfo(
    s ceType: S ceType,
    s gnals: Seq[S gnalConvertType]
  ): Seq[S ce nfo]
}
