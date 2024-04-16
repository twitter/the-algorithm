package com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout

 mport com.tw ter.eventdetect on.event_context.ut l.S mClustersUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.mag c_events.thr ftscala._
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutNewsEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutProductLaunchPushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.ut l.Future

object Mag cFanoutPred catesUt l {

  val UttDoma n: Long = 0L
  type Doma n d = Long
  type Ent y d = Long
  val BroadCategoryTag = "utt:broad_category"
  val UgmMo ntTag = "MMTS. sUGMMo nt"
  val TopKS mClustersCount = 50

  case class S mClusterScores(s mClusterScoreVector: Map[ nt, Double]) {
    def dotProduct(ot r: S mClusterScores): Double = {
      s mClusterScoreVector
        .map {
          case (cluster d, score) => ot r.s mClusterScoreVector.getOrElse(cluster d, 0.0) * score
        }.foldLeft(0.0) { _ + _ }
    }

    def norm(): Double = {
      val sumOfSquares: Double = s mClusterScoreVector
        .map {
          case (cluster d, score) => score * score
        }.foldLeft(0.0)(_ + _)
      scala.math.sqrt(sumOfSquares)
    }

    def nor dDotProduct(ot r: S mClusterScores, normal zer: S mClusterScores): Double = {
      val denom nator = normal zer.norm()
      val score = dotProduct(ot r)
       f (denom nator != 0.0) {
        score / denom nator
      } else {
        score
      }
    }
  }

  pr vate def  sSemant cCoreEnt yBroad(
    semant cCoreEnt yTags: Map[(Doma n d, Ent y d), Set[Str ng]],
    scEnt y d: Semant cCore D
  ): Boolean = {
    semant cCoreEnt yTags
      .getOrElse((scEnt y d.doma n d, scEnt y d.ent y d), Set.empty).conta ns(BroadCategoryTag)
  }

  def  s nCountryL st(accountCountryCode: Str ng, locales: Seq[Str ng]): Boolean = {
    locales.map(_.toLo rCase).conta ns(accountCountryCode.toLo rCase)
  }

  /**
   * Boolean c ck of  f a Mag cFanout  s h gh pr or y push
   */
  def c ck fH ghPr or yNewsEventForCand date(
    cand date: Mag cFanoutNewsEventPushCand date
  ): Future[Boolean] = {
    cand date. sH ghPr or yEvent.map {  sH ghPr or y =>
       sH ghPr or y && (cand date.target.params(PushFeatureSw chParams.EnableH ghPr or yPush))
    }
  }

  /**
   * Boolean c ck of  f a Mag cFanout event  s h gh pr or y push
   */
  def c ck fH ghPr or yEventForCand date(
    cand date: Mag cFanoutEventPushCand date
  ): Future[Boolean] = {
    cand date. sH ghPr or yEvent.map {  sH ghPr or y =>
      cand date.commonRecType match {
        case CommonRecom ndat onType.Mag cFanoutSportsEvent =>
           sH ghPr or y && (cand date.target.params(
            PushFeatureSw chParams.EnableH ghPr or ySportsPush))
        case _ => false
      }
    }
  }

  /**
   * Boolean c ck  f to sk p target blue ver f ed
   */
  def shouldSk pBlueVer f edC ckForCand date(
    cand date: Mag cFanoutProductLaunchPushCand date
  ): Future[Boolean] =
    Future.value(
      cand date.target.params(PushFeatureSw chParams.D sable sTargetBlueVer f edPred cate))

  /**
   * Boolean c ck  f to sk p target  s legacy ver f ed
   */
  def shouldSk pLegacyVer f edC ckForCand date(
    cand date: Mag cFanoutProductLaunchPushCand date
  ): Future[Boolean] =
    Future.value(
      cand date.target.params(PushFeatureSw chParams.D sable sTargetLegacyVer f edPred cate))

  def shouldSk pSuperFollowCreatorC ckForCand date(
    cand date: Mag cFanoutProductLaunchPushCand date
  ): Future[Boolean] =
    Future.value(
      !cand date.target.params(PushFeatureSw chParams.Enable sTargetSuperFollowCreatorPred cate))

  /**
   * Boolean c ck of  f a reason of a Mag cFanout  s h g r than t  rank threshold of an event
   */
  def c ck fErgScEnt yReason etsThreshold(
    rankThreshold:  nt,
    reason: Mag cEventsReason,
  ): Boolean = {
    reason.reason match {
      case Target D.Semant cCore D(scEnt y d: Semant cCore D) =>
        reason.rank match {
          case So (rank) => rank < rankThreshold
          case _ => false
        }
      case _ => false
    }
  }

  /**
   * C ck  f Mag cEventsReasons conta ns a reason that matc s t  thresholdw
   */
  def c ck fVal dErgScEnt yReasonEx sts(
    mag cEventsReasons: Opt on[Seq[Mag cEventsReason]],
    rankThreshold:  nt
  )(
     mpl c  stats: StatsRece ver
  ): Boolean = {
    mag cEventsReasons match {
      case So (reasons)  f reasons.ex sts(_. sNewUser.conta ns(true)) => true
      case So (reasons) =>
        reasons.ex sts { reason =>
          reason.s ce.conta ns(ReasonS ce.ErgShortTerm nterestSemant cCore) &&
          c ck fErgScEnt yReason etsThreshold(
            rankThreshold,
            reason
          )
        }

      case _ => false
    }
  }

  /**
   * Get event s mcluster vector from event context
   */
  def getEventS mClusterVector(
    s mClustersEmbedd ngOpt on: Opt on[Map[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]],
    embedd ngMapKey: (ModelVers on, Embedd ngType),
    topKS mClustersCount:  nt
  ): Opt on[S mClusterScores] = {
    s mClustersEmbedd ngOpt on.map { thr ftS mClustersEmbedd ngs =>
      val s mClustersEmbedd ngs: Map[S mClustersEmbedd ng d, S mClustersEmbedd ng] =
        thr ftS mClustersEmbedd ngs.map {
          case (s mClustersEmbedd ng d, s mClustersEmbedd ngValue) =>
            (s mClustersEmbedd ng d, S mClustersEmbedd ng(s mClustersEmbedd ngValue))
        }.toMap
      val emptySeq = Seq[( nt, Double)]()
      val s mClusterScoreTuple: Map[(ModelVers on, Embedd ngType), Seq[( nt, Double)]] =
        S mClustersUt l
          .getMaxTopKT etS mClusters(s mClustersEmbedd ngs, topKS mClustersCount)
      S mClusterScores(s mClusterScoreTuple.getOrElse(embedd ngMapKey, emptySeq).toMap)
    }
  }

  /**
   * Get user s mcluster vector mag c events reasons
   */
  def getUserS mClusterVector(
    mag cEventsReasonsOpt: Opt on[Seq[Mag cEventsReason]]
  ): Opt on[S mClusterScores] = {
    mag cEventsReasonsOpt.map { mag cEventsReasons: Seq[Mag cEventsReason] =>
      val reasons: Seq[( nt, Double)] = mag cEventsReasons.flatMap { reason =>
        reason.reason match {
          case Target D.S mCluster D(s mCluster d: S mCluster D) =>
            So ((s mCluster d.cluster d, reason.score.getOrElse(0.0)))
          case _ =>
            None
        }
      }
      S mClusterScores(reasons.toMap)
    }
  }

  def reasonsConta nGeoTarget(reasons: Seq[Mag cEventsReason]): Boolean = {
    reasons.ex sts { reason =>
      val  sGeoGraphS ce = reason.s ce.conta ns(ReasonS ce.GeoGraph)
      reason.reason match {
        case Target D.Place D(_)  f  sGeoGraphS ce => true
        case _ => false
      }
    }
  }

  def geoPlace dsFromReasons(reasons: Seq[Mag cEventsReason]): Set[Long] = {
    reasons.flatMap { reason =>
      val  sGeoGraphS ce = reason.s ce.conta ns(ReasonS ce.GeoGraph)
      reason.reason match {
        case Target D.Place D(Place D( d))  f  sGeoGraphS ce => So ( d)
        case _ => None
      }
    }.toSet
  }
}
