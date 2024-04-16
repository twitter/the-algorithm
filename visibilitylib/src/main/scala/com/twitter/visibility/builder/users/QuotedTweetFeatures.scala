package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.features.AuthorBlocksOuterAuthor
 mport com.tw ter.v s b l y.features.OuterAuthorFollowsAuthor
 mport com.tw ter.v s b l y.features.OuterAuthor d
 mport com.tw ter.v s b l y.features.OuterAuthor s nnerAuthor

class QuotedT etFeatures(
  relat onsh pFeatures: Relat onsh pFeatures,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("quoted_t et_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val outerAuthor dStat =
    scopedStatsRece ver.scope(OuterAuthor d.na ).counter("requests")
  pr vate[t ] val authorBlocksOuterAuthor =
    scopedStatsRece ver.scope(AuthorBlocksOuterAuthor.na ).counter("requests")
  pr vate[t ] val outerAuthorFollowsAuthor =
    scopedStatsRece ver.scope(OuterAuthorFollowsAuthor.na ).counter("requests")
  pr vate[t ] val outerAuthor s nnerAuthor =
    scopedStatsRece ver.scope(OuterAuthor s nnerAuthor.na ).counter("requests")

  def forOuterAuthor(
    outerAuthor d: Long,
     nnerAuthor d: Long
  ): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    outerAuthor dStat. ncr()
    authorBlocksOuterAuthor. ncr()
    outerAuthorFollowsAuthor. ncr()
    outerAuthor s nnerAuthor. ncr()

    val v e r = So (outerAuthor d)

    _.w hConstantFeature(OuterAuthor d, outerAuthor d)
      .w hFeature(
        AuthorBlocksOuterAuthor,
        relat onsh pFeatures.authorBlocksV e r( nnerAuthor d, v e r))
      .w hFeature(
        OuterAuthorFollowsAuthor,
        relat onsh pFeatures.v e rFollowsAuthor( nnerAuthor d, v e r))
      .w hConstantFeature(
        OuterAuthor s nnerAuthor,
         nnerAuthor d == outerAuthor d
      )
  }
}
