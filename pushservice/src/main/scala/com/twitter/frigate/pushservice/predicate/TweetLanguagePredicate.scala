package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.language.normal zat on.UserD splayLanguage
 mport com.tw ter.ut l.Future

object T etLanguagePred cate {

  def oonT eetLanguageMatch(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date w h Recom ndat onType w h T etDeta ls
  ] = {
    val na  = "oon_t et_language_pred cate"
    val scopedStatsRece ver = stats.scope(na )
    val oonCand datesCounter =
      scopedStatsRece ver.counter("oon_cand dates")
    val enableF lterCounter =
      scopedStatsRece ver.counter("enabled_f lter")
    val sk p d aT etsCounter =
      scopedStatsRece ver.counter("sk p_ d a_t ets")

    Pred cate
      .fromAsync { cand date: PushCand date w h Recom ndat onType w h T etDeta ls =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&  sOonCand date) {
          oonCand datesCounter. ncr()

          target.featureMap.map { featureMap =>
            val userPreferredLanguages = featureMap.sparseB naryFeatures
              .getOrElse("user.language.user.preferred_contents", Set.empty[Str ng])
            val userEngage ntLanguages = featureMap.sparseCont nuousFeatures.getOrElse(
              "user.language.user.engage nts",
              Map.empty[Str ng, Double])
            val userFollowLanguages = featureMap.sparseCont nuousFeatures.getOrElse(
              "user.language.user.follow ng_accounts",
              Map.empty[Str ng, Double])
            val userProducedT etLanguages = featureMap.sparseCont nuousFeatures
              .getOrElse("user.language.user.produced_t ets", Map.empty)
            val userDev ceLanguages = featureMap.sparseCont nuousFeatures.getOrElse(
              "user.language.user.recent_dev ces",
              Map.empty[Str ng, Double])
            val t etLanguageOpt = cand date.categor calFeatures
              .get(target.params(PushFeatureSw chParams.T etLanguageFeatureNa Param))

             f (userPreferredLanguages. sEmpty)
              scopedStatsRece ver.counter("userPreferredLanguages_empty"). ncr()
             f (userEngage ntLanguages. sEmpty)
              scopedStatsRece ver.counter("userEngage ntLanguages_empty"). ncr()
             f (userFollowLanguages. sEmpty)
              scopedStatsRece ver.counter("userFollowLanguages_empty"). ncr()
             f (userProducedT etLanguages. sEmpty)
              scopedStatsRece ver
                .counter("userProducedT etLanguages_empty")
                . ncr()
             f (userDev ceLanguages. sEmpty)
              scopedStatsRece ver.counter("userDev ceLanguages_empty"). ncr()
             f (t etLanguageOpt. sEmpty) scopedStatsRece ver.counter("t etLanguage_empty"). ncr()

            val t etLanguage = t etLanguageOpt.getOrElse("und")
            val undef nedT etLanguages = Set("")

             f (!undef nedT etLanguages.conta ns(t etLanguage)) {
              lazy val user nferredLanguageThreshold =
                target.params(PushFeatureSw chParams.User nferredLanguageThresholdParam)
              lazy val userDev ceLanguageThreshold =
                target.params(PushFeatureSw chParams.UserDev ceLanguageThresholdParam)
              lazy val enableT etLanguageF lter =
                target.params(PushFeatureSw chParams.EnableT etLanguageF lter)
              lazy val sk pLanguageF lterFor d aT ets =
                target.params(PushFeatureSw chParams.Sk pLanguageF lterFor d aT ets)

              lazy val allLanguages = userPreferredLanguages ++
                userEngage ntLanguages.f lter(_._2 > user nferredLanguageThreshold).keySet ++
                userFollowLanguages.f lter(_._2 > user nferredLanguageThreshold).keySet ++
                userProducedT etLanguages.f lter(_._2 > user nferredLanguageThreshold).keySet ++
                userDev ceLanguages.f lter(_._2 > userDev ceLanguageThreshold).keySet

               f (enableT etLanguageF lter && allLanguages.nonEmpty) {
                enableF lterCounter. ncr()
                val has d a = cand date.hasPhoto || cand date.hasV deo

                 f (has d a && sk pLanguageF lterFor d aT ets) {
                  sk p d aT etsCounter. ncr()
                  true
                } else {
                  allLanguages.map(UserD splayLanguage.toT etLanguage).conta ns(t etLanguage)
                }
              } else true
            } else true
          }
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }
}
