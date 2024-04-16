package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.T etPerspect veS ce
 mport com.tw ter.v s b l y.features.V e rReportedT et

class T etPerspect veFeatures(
  t etPerspect veS ce: T etPerspect veS ce,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("t et_perspect ve_features")
  pr vate[t ] val reportedStats = scopedStatsRece ver.scope("reported")

  def forT et(
    t et: T et,
    v e r d: Opt on[Long],
    enableFetchReportedPerspect ve: Boolean
  ): FeatureMapBu lder => FeatureMapBu lder =
    _.w hFeature(
      V e rReportedT et,
      t et sReported(t et, v e r d, enableFetchReportedPerspect ve))

  pr vate[bu lder] def t et sReported(
    t et: T et,
    v e r d: Opt on[Long],
    enableFetchReportedPerspect ve: Boolean = true
  ): St ch[Boolean] = {
    ((t et.perspect ve, v e r d) match {
      case (So (perspect ve), _) =>
        St ch.value(perspect ve.reported).onSuccess { _ =>
          reportedStats.counter("already_hydrated"). ncr()
        }
      case (None, So (v e r d)) =>
         f (enableFetchReportedPerspect ve) {
          t etPerspect veS ce.reported(t et. d, v e r d).onSuccess { _ =>
            reportedStats.counter("request"). ncr()
          }
        } else {
          St ch.False.onSuccess { _ =>
            reportedStats.counter("l ght_request"). ncr()
          }
        }
      case _ =>
        St ch.False.onSuccess { _ =>
          reportedStats.counter("empty"). ncr()
        }
    }).onSuccess { _ =>
      reportedStats.counter(""). ncr()
    }
  }
}
