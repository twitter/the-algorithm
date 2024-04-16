package com.tw ter.fr gate.pushserv ce.ml

 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.{Model => T et althModel}
 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.T etScor ngRequest
 mport com.tw ter.abuse.detect on.scor ng.thr ftscala.T etScor ngResponse
 mport com.tw ter.fr gate.common.base.FeatureMap
 mport com.tw ter.fr gate.common.base.T etAuthor
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.pred cate. althPred cates.user althS gnalValueToDouble
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateHydrat onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter.fr gate.pushserv ce.ut l. d aAnnotat onsUt l
 mport com.tw ter.fr gate.thr ftscala.User d aRepresentat on
 mport com.tw ter.hss.ap .thr ftscala.S gnalValue
 mport com.tw ter.hss.ap .thr ftscala.User althS gnal
 mport com.tw ter.hss.ap .thr ftscala.User althS gnal.AgathaCal bratedNsfwDouble
 mport com.tw ter.hss.ap .thr ftscala.User althS gnal.NsfwTextUserScoreDouble
 mport com.tw ter.hss.ap .thr ftscala.User althS gnalResponse
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

object  althFeatureGetter {

  def getFeatures(
    pushCand date: PushCand date,
    producer d aRepresentat onStore: ReadableStore[Long, User d aRepresentat on],
    user althScoreStore: ReadableStore[Long, User althS gnalResponse],
    t et althScoreStoreOpt: Opt on[ReadableStore[T etScor ngRequest, T etScor ngResponse]] =
      None
  ): Future[FeatureMap] = {

    pushCand date match {
      case cand: PushCand date w h T etCand date w h T etAuthor w h T etAuthorDeta ls =>
        val p d aNsfwRequest =
          T etScor ngRequest(cand.t et d, T et althModel.Exper  ntal althModelScore4)
        val pT etTextNsfwRequest =
          T etScor ngRequest(cand.t et d, T et althModel.Exper  ntal althModelScore1)

        cand.author d match {
          case So (author d) =>
            Future
              .jo n(
                user althScoreStore.get(author d),
                producer d aRepresentat onStore.get(author d),
                t et althScoreStoreOpt.map(_.get(p d aNsfwRequest)).getOrElse(Future.None),
                t et althScoreStoreOpt.map(_.get(pT etTextNsfwRequest)).getOrElse(Future.None),
                cand.t etAuthor
              ).map {
                case (
                       althS gnalsResponseOpt,
                      producerMuOpt,
                      p d aNsfwOpt,
                      pT etTextNsfwOpt,
                      t etAuthorOpt) =>
                  val  althS gnalScoreMap =  althS gnalsResponseOpt
                    .map(_.s gnalValues).getOrElse(Map.empty[User althS gnal, S gnalValue])
                  val agathaNSFWScore = user althS gnalValueToDouble(
                     althS gnalScoreMap
                      .getOrElse(AgathaCal bratedNsfwDouble, S gnalValue.DoubleValue(0.5)))
                  val userTextNSFWScore = user althS gnalValueToDouble(
                     althS gnalScoreMap
                      .getOrElse(NsfwTextUserScoreDouble, S gnalValue.DoubleValue(0.15)))
                  val p d aNsfwScore = p d aNsfwOpt.map(_.score).getOrElse(0.0)
                  val pT etTextNsfwScore = pT etTextNsfwOpt.map(_.score).getOrElse(0.0)

                  val  d aRepresentat onMap =
                    producerMuOpt.map(_. d aRepresentat on).getOrElse(Map.empty[Str ng, Double])
                  val sumScore: Double =  d aRepresentat onMap.values.sum
                  val nud yRate =
                     f (sumScore > 0)
                       d aRepresentat onMap.getOrElse(
                         d aAnnotat onsUt l.nud yCategory d,
                        0.0) / sumScore
                    else 0.0
                  val beautyRate =
                     f (sumScore > 0)
                       d aRepresentat onMap.getOrElse(
                         d aAnnotat onsUt l.beautyCategory d,
                        0.0) / sumScore
                    else 0.0
                  val s nglePersonRate =
                     f (sumScore > 0)
                       d aRepresentat onMap.getOrElse(
                         d aAnnotat onsUt l.s nglePersonCategory d,
                        0.0) / sumScore
                    else 0.0
                  val d sl keCt = cand.nu r cFeatures.getOrElse(
                    "t et.mag c_recs_t et_real_t  _aggregates_v2.pa r.v2.mag crecs.realt  . s_ntab_d sl ked.any_feature.Durat on.Top.count",
                    0.0)
                  val sentCt = cand.nu r cFeatures.getOrElse(
                    "t et.mag c_recs_t et_real_t  _aggregates_v2.pa r.v2.mag crecs.realt  . s_sent.any_feature.Durat on.Top.count",
                    0.0)
                  val d sl keRate =  f (sentCt > 0) d sl keCt / sentCt else 0.0

                  val authorD sl keCt = cand.nu r cFeatures.getOrElse(
                    "t et_author_aggregate.pa r.label.ntab. sD sl ked.any_feature.28.days.count",
                    0.0)
                  val authorReportCt = cand.nu r cFeatures.getOrElse(
                    "t et_author_aggregate.pa r.label.reportT etDone.any_feature.28.days.count",
                    0.0)
                  val authorSentCt = cand.nu r cFeatures
                    .getOrElse(
                      "t et_author_aggregate.pa r.any_label.any_feature.28.days.count",
                      0.0)
                  val authorD sl keRate =
                     f (authorSentCt > 0) authorD sl keCt / authorSentCt else 0.0
                  val authorReportRate =
                     f (authorSentCt > 0) authorReportCt / authorSentCt else 0.0

                  val ( sNsfwAccount, authorAccountAge) = t etAuthorOpt match {
                    case So (t etAuthor) =>
                      (
                        Cand dateHydrat onUt l. sNsfwAccount(
                          t etAuthor,
                          cand.target.params(PushFeatureSw chParams.NsfwTokensParam)),
                        (T  .now - T  .fromM ll seconds(t etAuthor.createdAtMsec)). nH s
                      )
                    case _ => (false, 0)
                  }

                  val t etSemant cCore ds = cand.sparseB naryFeatures
                    .getOrElse(PushConstants.T etSemant cCore dFeature, Set.empty[Str ng])

                  val cont nuousFeatures = Map[Str ng, Double](
                    "agathaNsfwScore" -> agathaNSFWScore,
                    "textNsfwScore" -> userTextNSFWScore,
                    "p d aNsfwScore" -> p d aNsfwScore,
                    "pT etTextNsfwScore" -> pT etTextNsfwScore,
                    "nud yRate" -> nud yRate,
                    "beautyRate" -> beautyRate,
                    "s nglePersonRate" -> s nglePersonRate,
                    "numS ces" -> Cand dateUt l.getTagsCRCount(cand),
                    "favCount" -> cand.nu r cFeatures
                      .getOrElse("t et.core.t et_counts.favor e_count", 0.0),
                    "act veFollo rs" -> cand.nu r cFeatures
                      .getOrElse("RecT etAuthor.User.Act veFollo rs", 0.0),
                    "favorsRcvd28Days" -> cand.nu r cFeatures
                      .getOrElse("RecT etAuthor.User.FavorsRcvd28Days", 0.0),
                    "t ets28Days" -> cand.nu r cFeatures
                      .getOrElse("RecT etAuthor.User.T ets28Days", 0.0),
                    "d sl keCount" -> d sl keCt,
                    "d sl keRate" -> d sl keRate,
                    "sentCount" -> sentCt,
                    "authorD sl keCount" -> authorD sl keCt,
                    "authorD sl keRate" -> authorD sl keRate,
                    "authorReportCount" -> authorReportCt,
                    "authorReportRate" -> authorReportRate,
                    "authorSentCount" -> authorSentCt,
                    "authorAge nH " -> authorAccountAge.toDouble
                  )

                  val booleanFeatures = Map[Str ng, Boolean](
                    " sS mclusterBased" -> RecTypes.s mclusterBasedT ets
                      .conta ns(cand.commonRecType),
                    " sTop cT et" -> RecTypes. sTop cT etType(cand.commonRecType),
                    " sHashSpace" -> RecTypes.tagspaceTypes.conta ns(cand.commonRecType),
                    " sFRS" -> RecTypes.frsTypes.conta ns(cand.commonRecType),
                    " sModel ngBased" -> RecTypes.mrModel ngBasedTypes.conta ns(cand.commonRecType),
                    " sGeoPop" -> RecTypes.GeoPopT etTypes.conta ns(cand.commonRecType),
                    "hasPhoto" -> cand.booleanFeatures
                      .getOrElse("RecT et.T etyP eResult.HasPhoto", false),
                    "hasV deo" -> cand.booleanFeatures
                      .getOrElse("RecT et.T etyP eResult.HasV deo", false),
                    "hasUrl" -> cand.booleanFeatures
                      .getOrElse("RecT et.T etyP eResult.HasUrl", false),
                    " sMrTw stly" -> Cand dateUt l. sMrTw stlyCand date(cand),
                    "abuseStr keTop2Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.AbuseStr ke_Top2Percent_ d),
                    "abuseStr keTop1Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.AbuseStr ke_Top1Percent_ d),
                    "abuseStr keTop05Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.AbuseStr ke_Top05Percent_ d),
                    "abuseStr keTop025Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.AbuseStr ke_Top025Percent_ d),
                    "allSpamReportsPerFavTop1Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.AllSpamReportsPerFav_Top1Percent_ d),
                    "reportsPerFavTop1Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.ReportsPerFav_Top1Percent_ d),
                    "reportsPerFavTop2Percent" -> t etSemant cCore ds.conta ns(
                      PushConstants.ReportsPerFav_Top2Percent_ d),
                    " sNud y" -> t etSemant cCore ds.conta ns(
                      PushConstants. d aUnderstand ng_Nud y_ d),
                    "beautyStyleFash on" -> t etSemant cCore ds.conta ns(
                      PushConstants. d aUnderstand ng_Beauty_ d),
                    "s nglePerson" -> t etSemant cCore ds.conta ns(
                      PushConstants. d aUnderstand ng_S nglePerson_ d),
                    "pornL st" -> t etSemant cCore ds.conta ns(PushConstants.PornL st_ d),
                    "pornographyAndNsfwContent" -> t etSemant cCore ds.conta ns(
                      PushConstants.PornographyAndNsfwContent_ d),
                    "sexL fe" -> t etSemant cCore ds.conta ns(PushConstants.SexL fe_ d),
                    "sexL feOrSexualOr entat on" -> t etSemant cCore ds.conta ns(
                      PushConstants.SexL feOrSexualOr entat on_ d),
                    "profan y" -> t etSemant cCore ds.conta ns(PushConstants.Profan yF lter_ d),
                    " sVer f ed" -> cand.booleanFeatures
                      .getOrElse("RecT etAuthor.User. sVer f ed", false),
                    "hasNsfwToken" ->  sNsfwAccount
                  )

                  val str ngFeatures = Map[Str ng, Str ng](
                    "t etLanguage" -> cand.categor calFeatures
                      .getOrElse("t et.core.t et_text.language", "")
                  )

                  FeatureMap(
                    booleanFeatures = booleanFeatures,
                    nu r cFeatures = cont nuousFeatures,
                    categor calFeatures = str ngFeatures)
              }
          case _ => Future.value(FeatureMap())
        }
      case _ => Future.value(FeatureMap())
    }
  }
}
