package com.tw ter.product_m xer.component_l brary.selector.ads

 mport com.google. nject. nject
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.goldf nch.adaptors.ads.productm xer.ProductM xerPromotedEntr esAdaptor
 mport com.tw ter.goldf nch.adaptors.productm xer.ProductM xerNonPromotedEntr esAdaptor
 mport com.tw ter.goldf nch.adaptors.productm xer.ProductM xerQueryConverter
 mport com.tw ter.goldf nch.ap .Ads nject onRequestContextConverter
 mport com.tw ter.goldf nch.ap .Ads nject onSurfaceAreas.SurfaceAreaNa 
 mport com.tw ter.goldf nch.ap .{Ads njector => Goldf nchAds njector}
 mport com.tw ter.goldf nch.ap .NonPromotedEntr esAdaptor
 mport com.tw ter.goldf nch.ap .PromotedEntr esAdaptor
 mport com.tw ter.goldf nch. mpl. njector.Ads njectorBu lder
 mport com.tw ter.goldf nch. mpl. njector.product_m xer.Ads nject onSurfaceAreaAdjustersMap
 mport com.tw ter.goldf nch. mpl. njector.product_m xer.Vert calS zeAdjust ntConf gMap
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.product_m xer.component_l brary.model.query.ads._
 mport com.tw ter.product_m xer.core.model.common.presentat on._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport javax. nject.S ngleton
 mport com.tw ter.goldf nch. mpl.core.DefaultFeatureSw chResultsFactory
 mport com.tw ter.goldf nch. mpl.core.LocalDevelop ntFeatureSw chResultsFactory
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Conf gRepoLocalPath
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal

@S ngleton
class Ads njector @ nject() (
  statsRece ver: StatsRece ver,
  @Flag(Conf gRepoLocalPath) localConf gRepoPath: Str ng,
  @Flag(Serv ceLocal)  sServ ceLocal: Boolean)
    extends Logg ng {
  pr vate val adsQueryRequestConverter: Ads nject onRequestContextConverter[
    P pel neQuery w h AdsQuery
  ] = ProductM xerQueryConverter

  def forSurfaceArea(
    surfaceAreaNa : SurfaceAreaNa 
  ): Goldf nchAds njector[
    P pel neQuery w h AdsQuery,
    Cand dateW hDeta ls,
    Cand dateW hDeta ls
  ] = {

    val scopedStatsRece ver: StatsRece ver =
      statsRece ver.scope("goldf nch", surfaceAreaNa .toStr ng)

    val nonAdsAdaptor: NonPromotedEntr esAdaptor[Cand dateW hDeta ls] =
      ProductM xerNonPromotedEntr esAdaptor(
        Vert calS zeAdjust ntConf gMap.conf gsBySurfaceArea(surfaceAreaNa ),
        scopedStatsRece ver)

    val adsAdaptor: PromotedEntr esAdaptor[Cand dateW hDeta ls] =
      new ProductM xerPromotedEntr esAdaptor(scopedStatsRece ver)

    val featureSw chFactory =  f ( sServ ceLocal) {
      new LocalDevelop ntFeatureSw chResultsFactory(
        surfaceAreaNa .toStr ng,
        conf gRepoAbsPath = localConf gRepoPath)
    } else new DefaultFeatureSw chResultsFactory(surfaceAreaNa .toStr ng)

    new Ads njectorBu lder[P pel neQuery w h AdsQuery, Cand dateW hDeta ls, Cand dateW hDeta ls](
      requestAdapter = adsQueryRequestConverter,
      nonPromotedEntr esAdaptor = nonAdsAdaptor,
      promotedEntr esAdaptor = adsAdaptor,
      adjusters =
        Ads nject onSurfaceAreaAdjustersMap.getAdjusters(surfaceAreaNa , scopedStatsRece ver),
      featureSw chFactory = featureSw chFactory,
      statsRece ver = scopedStatsRece ver,
      logger = logger
    ).bu ld()
  }
}
