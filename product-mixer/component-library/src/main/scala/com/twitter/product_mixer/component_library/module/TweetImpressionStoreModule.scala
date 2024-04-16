package com.tw ter.product_m xer.component_l brary.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle.Resolver
 mport com.tw ter.f nagle. mcac d.protocol.Command
 mport com.tw ter.f nagle. mcac d.protocol.Response
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.param.H ghResT  r
 mport com.tw ter.f nagle.serv ce.RetryExcept onsF lter
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.serv ce.StatsF lter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes. mpress onstore.store.T et mpress onsStore
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala. mpress onL st
 mport javax. nject.S ngleton

object T et mpress onStoreModule extends Tw terModule {
  pr vate val T et mpress on mcac W lyPath = "/s/cac /t  l nes_ mpress onstore:t mcac s"
  pr vate val t et mpress onLabel = "t  l nesT et mpress ons"

  @Prov des
  @S ngleton
  def prov deT  l neT et mpress onStore(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): ReadableStore[Long,  mpress onL st] = {
    val scopedStatsRece ver = statsRece ver.scope("t  l nesT et mpress ons")

    // t  below values for conf gur ng t   mcac d cl ent
    // are set to be t  sa  as Ho  t  l ne's read path to t   mpress on store.
    val acqu s  onT  outM ll s = 200.m ll seconds
    val requestT  outM ll s = 300.m ll seconds
    val numTr es = 2

    val statsF lter = new StatsF lter[Command, Response](scopedStatsRece ver)
    val retryF lter = new RetryExcept onsF lter[Command, Response](
      retryPol cy = RetryPol cy.tr es(
        numTr es,
        RetryPol cy.T  outAndWr eExcept onsOnly
          .orElse(RetryPol cy.ChannelClosedExcept onsOnly)
      ),
      t  r = H ghResT  r.Default,
      statsRece ver = scopedStatsRece ver
    )

    val cl ent =  mcac d.cl ent
      .w hMutualTls(serv ce dent f er)
      .w hSess on
      .acqu s  onT  out(acqu s  onT  outM ll s)
      .w hRequestT  out(requestT  outM ll s)
      .w hStatsRece ver(scopedStatsRece ver)
      .f ltered(statsF lter.andT n(retryF lter))
      .newR chCl ent(
        dest = Resolver.eval(T et mpress on mcac W lyPath),
        label = t et mpress onLabel
      )

    T et mpress onsStore.t et mpress onsStore(cl ent)
  }

}
