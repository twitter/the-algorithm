package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.features.T et sModerated

class Moderat onFeatures(moderat onS ce: Long => Boolean, statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver: StatsRece ver =
    statsRece ver.scope("moderat on_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val t et sModerated =
    scopedStatsRece ver.scope(T et sModerated.na ).counter("requests")

  def forT et d(t et d: Long): FeatureMapBu lder => FeatureMapBu lder = { featureMapBu lder =>
    requests. ncr()
    t et sModerated. ncr()

    featureMapBu lder.w hConstantFeature(T et sModerated, moderat onS ce(t et d))
  }
}
