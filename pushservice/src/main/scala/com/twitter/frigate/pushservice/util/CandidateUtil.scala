package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.contentrecom nder.thr ftscala. tr cTag
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.OutOfNetworkT etCand date
 mport com.tw ter.fr gate.common.base.Soc alContextAct on
 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.common.base.Target nfo
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.base.Top cProofT etCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.params.CrtGroupEnum
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType.Tr pGeoT et
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType.Tr pHqT et
 mport com.tw ter.fr gate.thr ftscala.{Soc alContextAct on => TSoc alContextAct on}
 mport com.tw ter.ut l.Future

object Cand dateUt l {
  pr vate val mrTw stly tr cTags =
    Seq( tr cTag.PushOpenOrNtabCl ck,  tr cTag.Request althF lterPushOpenBasedT etEmbedd ng)

  def getSoc alContextAct onsFromCand date(cand date: RawCand date): Seq[TSoc alContextAct on] = {
    cand date match {
      case cand dateW hSoc alContex: RawCand date w h Soc alContextAct ons =>
        cand dateW hSoc alContex.soc alContextAct ons.map { scAct on =>
          TSoc alContextAct on(
            scAct on.user d,
            scAct on.t  stamp nM ll s,
            scAct on.t et d
          )
        }
      case _ => Seq.empty
    }
  }

  /**
   * Rank ng Soc al Context based on t  Real Graph   ght
   * @param soc alContextAct ons  Sequence of Soc al Context Act ons
   * @param seedsW h  ght       Real Graph map cons st ng of User  D as key and RG   ght as t  value
   * @param defaultToRecency      Boolean to represent  f   should use t  t  stamp of t  SC to rank
   * @return                      Returns t  ranked sequence of SC Act ons
   */
  def getRankedSoc alContext(
    soc alContextAct ons: Seq[Soc alContextAct on],
    seedsW h  ght: Future[Opt on[Map[Long, Double]]],
    defaultToRecency: Boolean
  ): Future[Seq[Soc alContextAct on]] = {
    seedsW h  ght.map {
      case So (follow ngsMap) =>
        soc alContextAct ons.sortBy { act on => -follow ngsMap.getOrElse(act on.user d, 0.0) }
      case _ =>
         f (defaultToRecency) soc alContextAct ons.sortBy(-_.t  stamp nM ll s)
        else soc alContextAct ons
    }
  }

  def shouldApply althQual yF ltersForPrerank ngPred cates(
    cand date: T etAuthorDeta ls w h Target nfo[TargetUser w h TargetABDec der]
  )(
     mpl c  stats: StatsRece ver
  ): Future[Boolean] = {
    cand date.t etAuthor.map {
      case So (user) =>
        val numFollo rs: Double = user.counts.map(_.follo rs.toDouble).getOrElse(0.0)
        numFollo rs < cand date.target
          .params(PushFeatureSw chParams.NumFollo rThresholdFor althAndQual yF ltersPrerank ng)
      case _ => true
    }
  }

  def shouldApply althQual yF lters(
    cand date: PushCand date
  )(
     mpl c  stats: StatsRece ver
  ): Boolean = {
    val numFollo rs =
      cand date.nu r cFeatures.getOrElse("RecT etAuthor.User.Act veFollo rs", 0.0)
    numFollo rs < cand date.target
      .params(PushFeatureSw chParams.NumFollo rThresholdFor althAndQual yF lters)
  }

  def useAggress ve althThresholds(cand: PushCand date): Boolean =
     sMrTw stlyCand date(cand) ||
      (cand.commonRecType == CommonRecom ndat onType.GeoPopT et && cand.target.params(
        PushFeatureSw chParams.PopGeoT etEnableAggress veThresholds))

  def  sMrTw stlyCand date(cand: PushCand date): Boolean =
    cand match {
      case oonCand date: PushCand date w h OutOfNetworkT etCand date =>
        oonCand date.tagsCR
          .getOrElse(Seq.empty). ntersect(mrTw stly tr cTags).nonEmpty && oonCand date.tagsCR
          .map(_.toSet.s ze).getOrElse(0) == 1
      case oonCand date: PushCand date w h Top cProofT etCand date
           f cand.target.params(PushFeatureSw chParams.Enable althF ltersForTop cProofT et) =>
        oonCand date.tagsCR
          .getOrElse(Seq.empty). ntersect(mrTw stly tr cTags).nonEmpty && oonCand date.tagsCR
          .map(_.toSet.s ze).getOrElse(0) == 1
      case _ => false
    }

  def getTagsCRCount(cand: PushCand date): Double =
    cand match {
      case oonCand date: PushCand date w h OutOfNetworkT etCand date =>
        oonCand date.tagsCR.map(_.toSet.s ze).getOrElse(0).toDouble
      case oonCand date: PushCand date w h Top cProofT etCand date
           f cand.target.params(PushFeatureSw chParams.Enable althF ltersForTop cProofT et) =>
        oonCand date.tagsCR.map(_.toSet.s ze).getOrElse(0).toDouble
      case _ => 0.0
    }

  def  sRelatedToMrTw stlyCand date(cand: PushCand date): Boolean =
    cand match {
      case oonCand date: PushCand date w h OutOfNetworkT etCand date =>
        oonCand date.tagsCR.getOrElse(Seq.empty). ntersect(mrTw stly tr cTags).nonEmpty
      case oonCand date: PushCand date w h Top cProofT etCand date
           f cand.target.params(PushFeatureSw chParams.Enable althF ltersForTop cProofT et) =>
        oonCand date.tagsCR.getOrElse(Seq.empty). ntersect(mrTw stly tr cTags).nonEmpty
      case _ => false
    }

  def getCrtGroup(commonRecType: CommonRecom ndat onType): CrtGroupEnum.Value = {
    commonRecType match {
      case crt  f RecTypes.tw stlyT ets(crt) => CrtGroupEnum.Tw stly
      case crt  f RecTypes.frsTypes(crt) => CrtGroupEnum.Frs
      case crt  f RecTypes.f1RecTypes(crt) => CrtGroupEnum.F1
      case crt  f crt == Tr pGeoT et || crt == Tr pHqT et => CrtGroupEnum.Tr p
      case crt  f RecTypes.Top cT etTypes(crt) => CrtGroupEnum.Top c
      case crt  f RecTypes. sGeoPopT etType(crt) => CrtGroupEnum.GeoPop
      case _ => CrtGroupEnum.Ot r
    }
  }
}
