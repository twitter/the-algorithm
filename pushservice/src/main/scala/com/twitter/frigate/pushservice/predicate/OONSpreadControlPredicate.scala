package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants._
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

object OONSpreadControlPred cate {

  def oonT etSpreadControlPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date w h T etCand date w h Recom ndat onType
  ] = {
    val na  = "oon_t et_spread_control_pred cate"
    val scopedStatsRece ver = stats.scope(na )
    val allOonCand datesCounter = scopedStatsRece ver.counter("all_oon_cand dates")
    val f lteredCand datesCounter =
      scopedStatsRece ver.counter("f ltered_oon_cand dates")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h Recom ndat onType =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)

        lazy val m nT etSendsThreshold =
          target.params(PushFeatureSw chParams.M nT etSendsThresholdParam)
        lazy val spreadControlRat o =
          target.params(PushFeatureSw chParams.SpreadControlRat oParam)
        lazy val favOverSendThreshold =
          target.params(PushFeatureSw chParams.FavOverSendThresholdParam)

        lazy val sentCount = cand date.nu r cFeatures.getOrElse(sentFeatureNa , 0.0)
        lazy val follo rCount =
          cand date.nu r cFeatures.getOrElse(authorAct veFollo rFeatureNa , 0.0)
        lazy val favCount = cand date.nu r cFeatures.getOrElse(favFeatureNa , 0.0)
        lazy val favOverSends = favCount / (sentCount + 1.0)

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&  sOonCand date) {
          allOonCand datesCounter. ncr()
           f (sentCount > m nT etSendsThreshold &&
            sentCount > spreadControlRat o * follo rCount &&
            favOverSends < favOverSendThreshold) {
            f lteredCand datesCounter. ncr()
            Future.False
          } else Future.True
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def oonAuthorSpreadControlPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date w h T etCand date w h Recom ndat onType
  ] = {
    val na  = "oon_author_spread_control_pred cate"
    val scopedStatsRece ver = stats.scope(na )
    val allOonCand datesCounter = scopedStatsRece ver.counter("all_oon_cand dates")
    val f lteredCand datesCounter =
      scopedStatsRece ver.counter("f ltered_oon_cand dates")

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h Recom ndat onType =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)

        lazy val m nAuthorSendsThreshold =
          target.params(PushFeatureSw chParams.M nAuthorSendsThresholdParam)
        lazy val spreadControlRat o =
          target.params(PushFeatureSw chParams.SpreadControlRat oParam)
        lazy val reportRateThreshold =
          target.params(PushFeatureSw chParams.AuthorReportRateThresholdParam)
        lazy val d sl keRateThreshold =
          target.params(PushFeatureSw chParams.AuthorD sl keRateThresholdParam)

        lazy val authorSentCount =
          cand date.nu r cFeatures.getOrElse(authorSendCountFeatureNa , 0.0)
        lazy val authorReportCount =
          cand date.nu r cFeatures.getOrElse(authorReportCountFeatureNa , 0.0)
        lazy val authorD sl keCount =
          cand date.nu r cFeatures.getOrElse(authorD sl keCountFeatureNa , 0.0)
        lazy val follo rCount = cand date.nu r cFeatures
          .getOrElse(authorAct veFollo rFeatureNa , 0.0)
        lazy val reportRate =
          authorReportCount / (authorSentCount + 1.0)
        lazy val d sl keRate =
          authorD sl keCount / (authorSentCount + 1.0)

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&  sOonCand date) {
          allOonCand datesCounter. ncr()
           f (authorSentCount > m nAuthorSendsThreshold &&
            authorSentCount > spreadControlRat o * follo rCount &&
            (reportRate > reportRateThreshold || d sl keRate > d sl keRateThreshold)) {
            f lteredCand datesCounter. ncr()
            Future.False
          } else Future.True
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }
}
