package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

object T etEngage ntRat oPred cate {

  def QTtoNtabCl ckBasedPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date w h T etCand date w h Recom ndat onType
  ] = {
    val na  = "oon_t et_engage nt_f lter_qt_to_ntabcl ck_rat o_based_pred cate"
    val scopedStatsRece ver = stats.scope(na )
    val allOonCand datesCounter = scopedStatsRece ver.counter("all_oon_cand dates")
    val f lteredCand datesCounter =
      scopedStatsRece ver.counter("f ltered_oon_cand dates")

    val quoteCountFeature =
      "t et.core.t et_counts.quote_count"
    val ntabCl ckCountFeature =
      "t et.mag c_recs_t et_real_t  _aggregates_v2.pa r.v2.mag crecs.realt  . s_ntab_cl cked.any_feature.Durat on.Top.count"

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h Recom ndat onType =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)

        lazy val QTtoNtabCl ckRat oThreshold =
          target.params(PushFeatureSw chParams.T etQTtoNtabCl ckRat oThresholdParam)
        lazy val quoteCount = cand date.nu r cFeatures.getOrElse(quoteCountFeature, 0.0)
        lazy val ntabCl ckCount = cand date.nu r cFeatures.getOrElse(ntabCl ckCountFeature, 0.0)
        lazy val quoteRate =  f (ntabCl ckCount > 0) quoteCount / ntabCl ckCount else 1.0

         f ( sOonCand date) allOonCand datesCounter. ncr()
         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&  sOonCand date) {
          val ntabCl ckThreshold = 1000
          cand date.cac Pred cate nfo(
            na  + "_count",
            ntabCl ckCount,
            ntabCl ckThreshold,
            ntabCl ckCount >= ntabCl ckThreshold)
          cand date.cac Pred cate nfo(
            na  + "_rat o",
            quoteRate,
            QTtoNtabCl ckRat oThreshold,
            quoteRate < QTtoNtabCl ckRat oThreshold)
           f (ntabCl ckCount >= ntabCl ckThreshold && quoteRate < QTtoNtabCl ckRat oThreshold) {
            f lteredCand datesCounter. ncr()
            Future.False
          } else Future.True
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }

  def T etReplyL keRat oPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[PushCand date w h T etCand date] = {
    val na  = "t et_reply_l ke_rat o"
    val scopedStatsRece ver = stats.scope(na )
    val allCand datesCounter = scopedStatsRece ver.counter("all_cand dates")
    val f lteredCand datesCounter = scopedStatsRece ver.counter("f ltered_cand dates")
    val bucketedCand datesCounter = scopedStatsRece ver.counter("bucketed_cand dates")

    Pred cate
      .fromAsync { cand date: PushCand date =>
        allCand datesCounter. ncr()
        val target = cand date.target
        val l keCount = cand date.nu r cFeatures
          .getOrElse(PushConstants.T etL kesFeatureNa , 0.0)
        val replyCount = cand date.nu r cFeatures
          .getOrElse(PushConstants.T etRepl esFeatureNa , 0.0)
        val rat o = replyCount / l keCount.max(1)
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(cand date.commonRecType) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(cand date.commonRecType)

         f ( sOonCand date
          && Cand dateUt l.shouldApply althQual yF lters(cand date)
          && replyCount > target.params(
            PushFeatureSw chParams.T etReplytoL keRat oReplyCountThreshold)) {
          bucketedCand datesCounter. ncr()
           f (rat o > target.params(
              PushFeatureSw chParams.T etReplytoL keRat oThresholdLo rBound)
            && rat o < target.params(
              PushFeatureSw chParams.T etReplytoL keRat oThresholdUpperBound)) {
            f lteredCand datesCounter. ncr()
            Future.False
          } else {
            Future.True
          }
        } else {
          Future.True
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }
}
