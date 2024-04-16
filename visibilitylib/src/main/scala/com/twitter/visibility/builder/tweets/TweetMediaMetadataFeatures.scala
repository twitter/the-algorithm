package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. d aserv ces.commons. d a nformat on.thr ftscala.Add  onal tadata
 mport com.tw ter. d aserv ces. d a_ut l.Gener c d aKey
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.T et d a tadataS ce
 mport com.tw ter.v s b l y.features.HasDmca d aFeature
 mport com.tw ter.v s b l y.features. d aGeoRestr ct onsAllowL st
 mport com.tw ter.v s b l y.features. d aGeoRestr ct onsDenyL st

class T et d a tadataFeatures(
   d a tadataS ce: T et d a tadataS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("t et_ d a_ tadata_features")
  pr vate[t ] val reportedStats = scopedStatsRece ver.scope("dmcaStats")

  def forT et(
    t et: T et,
     d aKeys: Seq[Gener c d aKey],
    enableFetch d a tadata: Boolean
  ): FeatureMapBu lder => FeatureMapBu lder = { featureMapBu lder =>
    featureMapBu lder.w hFeature(
      HasDmca d aFeature,
       d a sDmca(t et,  d aKeys, enableFetch d a tadata))
    featureMapBu lder.w hFeature(
       d aGeoRestr ct onsAllowL st,
      allowl st(t et,  d aKeys, enableFetch d a tadata))
    featureMapBu lder.w hFeature(
       d aGeoRestr ct onsDenyL st,
      denyl st(t et,  d aKeys, enableFetch d a tadata))
  }

  pr vate def  d a sDmca(
    t et: T et,
     d aKeys: Seq[Gener c d aKey],
    enableFetch d a tadata: Boolean
  ) = get d aAdd  onal tadata(t et,  d aKeys, enableFetch d a tadata)
    .map(_.ex sts(_.restr ct ons.ex sts(_. sDmca)))

  pr vate def allowl st(
    t et: T et,
     d aKeys: Seq[Gener c d aKey],
    enableFetch d a tadata: Boolean
  ) = get d aGeoRestr ct ons(t et,  d aKeys, enableFetch d a tadata)
    .map(_.flatMap(_.wh el stedCountryCodes))

  pr vate def denyl st(
    t et: T et,
     d aKeys: Seq[Gener c d aKey],
    enableFetch d a tadata: Boolean
  ) = get d aGeoRestr ct ons(t et,  d aKeys, enableFetch d a tadata)
    .map(_.flatMap(_.blackl stedCountryCodes))

  pr vate def get d aGeoRestr ct ons(
    t et: T et,
     d aKeys: Seq[Gener c d aKey],
    enableFetch d a tadata: Boolean
  ) = {
    get d aAdd  onal tadata(t et,  d aKeys, enableFetch d a tadata)
      .map(add  onal tadatasSeq => {
        for {
          add  onal tadata <- add  onal tadatasSeq
          restr ct ons <- add  onal tadata.restr ct ons
          geoRestr ct ons <- restr ct ons.geoRestr ct ons
        } y eld {
          geoRestr ct ons
        }
      })
  }

  pr vate def get d aAdd  onal tadata(
    t et: T et,
     d aKeys: Seq[Gener c d aKey],
    enableFetch d a tadata: Boolean
  ): St ch[Seq[Add  onal tadata]] = {
     f ( d aKeys. sEmpty) {
      reportedStats.counter("empty"). ncr()
      St ch.value(Seq.empty)
    } else {
      t et. d a.flatMap {  d aEnt  es =>
        val alreadyHydrated tadata =  d aEnt  es
          .f lter(_. d aKey. sDef ned)
          .flatMap(_.add  onal tadata)

         f (alreadyHydrated tadata.nonEmpty) {
          So (alreadyHydrated tadata)
        } else {
          None
        }
      } match {
        case So (add  onal tadata) =>
          reportedStats.counter("already_hydrated"). ncr()
          St ch.value(add  onal tadata)
        case None =>
          St ch
            .collect(
               d aKeys.map(fetchAdd  onal tadata(t et. d, _, enableFetch d a tadata))
            ).map(maybe tadatas => {
              maybe tadatas
                .f lter(_. sDef ned)
                .map(_.get)
            })
      }
    }
  }

  pr vate def fetchAdd  onal tadata(
    t et d: Long,
    gener c d aKey: Gener c d aKey,
    enableFetch d a tadata: Boolean
  ): St ch[Opt on[Add  onal tadata]] =
     f (enableFetch d a tadata) {
      gener c d aKey.toThr ft d aKey() match {
        case So ( d aKey) =>
          reportedStats.counter("request"). ncr()
           d a tadataS ce.fetch(t et d,  d aKey)
        case None =>
          reportedStats.counter("empty_key"). ncr()
          St ch.None
      }
    } else {
      reportedStats.counter("l ght_request"). ncr()
      St ch.None
    }

}
