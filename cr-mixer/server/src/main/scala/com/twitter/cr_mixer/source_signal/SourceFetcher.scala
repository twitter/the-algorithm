package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.cr_m xer.thr ftscala.{Product => TProduct}
 mport com.tw ter.f nagle.GlobalRequestT  outExcept on
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.mux.ServerAppl cat onError
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  outExcept on
 mport org.apac .thr ft.TAppl cat onExcept on
 mport com.tw ter.ut l.logg ng.Logg ng

/**
 * A S ceFetc r  s a tra  wh ch, g ven a [[Fetc rQuery]], returns [[ResultType]]
 * T  ma n purposes of a S ceFetc r  s to prov de a cons stent  nterface for s ce fetch
 * log c, and prov des default funct ons,  nclud ng:
 * -  dent f cat on
 * - Observab l y
 * - T  out sett ngs
 * - Except on Handl ng
 */
tra  S ceFetc r[ResultType] extends ReadableStore[Fetc rQuery, ResultType] w h Logg ng {

  protected f nal val t  r = com.tw ter.f nagle.ut l.DefaultT  r
  protected f nal def  dent f er: Str ng = t .getClass.getS mpleNa 
  protected def stats: StatsRece ver
  protected def t  outConf g: T  outConf g

  /***
   * Use FeatureSw ch to dec de  f a spec f c s ce  s enabled.
   */
  def  sEnabled(query: Fetc rQuery): Boolean

  /***
   * T  funct on fetc s t  raw s ces and process t m.
   * Custom stats track ng can be added depend ng on t  type of ResultType
   */
  def fetchAndProcess(
    query: Fetc rQuery,
  ): Future[Opt on[ResultType]]

  /***
   * S de-effect funct on to track stats for s gnal fetch ng and process ng.
   */
  def trackStats(
    query: Fetc rQuery
  )(
    func: => Future[Opt on[ResultType]]
  ): Future[Opt on[ResultType]]

  /***
   * T  funct on  s called by t  top level class to fetch s ces.   executes t  p pel ne to
   * fetch raw data, process and transform t  s ces. Except ons, Stats, and t  out control are
   * handled  re.
   */
  overr de def get(
    query: Fetc rQuery
  ): Future[Opt on[ResultType]] = {
    val scopedStats = stats.scope(query.product.or g nalNa )
     f ( sEnabled(query)) {
      scopedStats.counter("gate_enabled"). ncr()
      trackStats(query)(fetchAndProcess(query))
        .ra seW h n(t  outConf g.s gnalFetchT  out)(t  r)
        .onFa lure { e =>
          scopedStats.scope("except ons").counter(e.getClass.getS mpleNa ). ncr()
        }
        .rescue {
          case _: T  outExcept on | _: GlobalRequestT  outExcept on | _: TAppl cat onExcept on |
              _: Cl entD scardedRequestExcept on |
              _: ServerAppl cat onError // TAppl cat onExcept on  ns de
              =>
            Future.None
          case e =>
            logger. nfo(e)
            Future.None
        }
    } else {
      scopedStats.counter("gate_d sabled"). ncr()
      Future.None
    }
  }
}

object S ceFetc r {

  /***
   * Every S ceFetc r all share t  sa   nput: Fetc rQuery
   */
  case class Fetc rQuery(
    user d: User d,
    product: TProduct,
    userState: UserState,
    params: Params)

}
