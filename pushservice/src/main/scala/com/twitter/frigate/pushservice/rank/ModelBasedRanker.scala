package com.tw ter.fr gate.pushserv ce.rank

 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.ut l.Future

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.params.MrQual yUprank ngPart alTypeEnum
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants.OoncQual yComb nedScore

object ModelBasedRanker {

  def rankBySpec f edScore(
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    scoreExtractor: PushCand date => Future[Opt on[Double]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

    val scoredCand datesFutures = cand datesDeta ls.map { cand =>
      scoreExtractor(cand.cand date).map { scoreOp => (cand, scoreOp.getOrElse(0.0)) }
    }

    Future.collect(scoredCand datesFutures).map { scores =>
      val sorted = scores.sortBy { cand dateDeta ls => -1 * cand dateDeta ls._2 }
      sorted.map(_._1)
    }
  }

  def populatePred ct onScoreStats(
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    scoreExtractor: PushCand date => Future[Opt on[Double]],
    pred ct onScoreStats: StatsRece ver
  ): Un  = {
    val scoreScaleFactorForStat = 10000
    val statNa  = "pred ct on_scores"
    cand datesDeta ls.map {
      case Cand dateDeta ls(cand date, s ce) =>
        val crt = cand date.commonRecType
        scoreExtractor(cand date).map { scoreOp =>
          val scaledScore = (scoreOp.getOrElse(0.0) * scoreScaleFactorForStat).toFloat
          pred ct onScoreStats.scope("all_cand dates").stat(statNa ).add(scaledScore)
          pred ct onScoreStats.scope(crt.toStr ng()).stat(statNa ).add(scaledScore)
        }
    }
  }

  def populateMr  ghtedOpenOrNtabCl ckScoreStats(
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    pred ct onScoreStats: StatsRece ver
  ): Un  = {
    populatePred ct onScoreStats(
      cand datesDeta ls,
      cand date => cand date.mr  ghtedOpenOrNtabCl ckRank ngProbab l y,
      pred ct onScoreStats
    )
  }

  def populateMrQual yUprank ngScoreStats(
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    pred ct onScoreStats: StatsRece ver
  ): Un  = {
    populatePred ct onScoreStats(
      cand datesDeta ls,
      cand date => cand date.mrQual yUprank ngProbab l y,
      pred ct onScoreStats
    )
  }

  def rankByMr  ghtedOpenOrNtabCl ckScore(
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

    rankBySpec f edScore(
      cand datesDeta ls,
      cand date => cand date.mr  ghtedOpenOrNtabCl ckRank ngProbab l y
    )
  }

  def transformS gmo d(
    score: Double,
      ght: Double = 1.0,
    b as: Double = 0.0
  ): Double = {
    val base = -1.0 * (  ght * score + b as)
    val cappedBase = math.max(math.m n(base, 100.0), -100.0)
    1.0 / (1.0 + math.exp(cappedBase))
  }

  def transformL near(
    score: Double,
    bar: Double = 1.0
  ): Double = {
    val pos  veBar = math.abs(bar)
    val cappedScore = math.max(math.m n(score, pos  veBar), -1.0 * pos  veBar)
    cappedScore / pos  veBar
  }

  def transform dent y(
    score: Double
  ): Double = score

  def rankByQual yOoncComb nedScore(
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    qual yScoreTransform: Double => Double,
    qual yScoreBoost: Double = 1.0
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

    rankBySpec f edScore(
      cand datesDeta ls,
      cand date => {
        val ooncScoreFutOpt: Future[Opt on[Double]] =
          cand date.mr  ghtedOpenOrNtabCl ckRank ngProbab l y
        val qual yScoreFutOpt: Future[Opt on[Double]] =
          cand date.mrQual yUprank ngProbab l y
        Future
          .jo n(
            ooncScoreFutOpt,
            qual yScoreFutOpt
          ).map {
            case (So (ooncScore), So (qual yScore)) =>
              val transfor dQual yScore = qual yScoreTransform(qual yScore)
              val comb nedScore = ooncScore * (1.0 + qual yScoreBoost * transfor dQual yScore)
              cand date
                .cac ExternalScore(OoncQual yComb nedScore, Future.value(So (comb nedScore)))
              So (comb nedScore)
            case _ => None
          }
      }
    )
  }

  def rerankByProducerQual yOoncComb nedScore(
    cand dateDeta ls: Seq[Cand dateDeta ls[PushCand date]]
  )(
     mpl c  stat: StatsRece ver
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    val scopedStat = stat.scope("producer_qual y_rerank ng")
    val oonCand dates = cand dateDeta ls.f lter {
      case Cand dateDeta ls(pushCand date: PushCand date, _) =>
        t etCand dateSelector(pushCand date, MrQual yUprank ngPart alTypeEnum.Oon)
    }

    val rankedOonCand datesFut = rankBySpec f edScore(
      oonCand dates,
      cand date => {
        val baseScoreFutureOpt: Future[Opt on[Double]] = {
          val qual yComb nedScoreFutureOpt =
            cand date.getExternalCac dScoreByNa (OoncQual yComb nedScore)
          val ooncScoreFutureOpt = cand date.mr  ghtedOpenOrNtabCl ckRank ngProbab l y
          Future.jo n(qual yComb nedScoreFutureOpt, ooncScoreFutureOpt).map {
            case (So (qual yComb nedScore), _) =>
              scopedStat.counter("qual y_comb ned_score"). ncr()
              So (qual yComb nedScore)
            case (_, ooncScoreOpt) =>
              scopedStat.counter("oonc_score"). ncr()
              ooncScoreOpt
          }
        }
        baseScoreFutureOpt.map {
          case So (baseScore) =>
            val boostRat o = cand date.mrProducerQual yUprank ngBoost.getOrElse(1.0)
             f (boostRat o > 1.0) scopedStat.counter("author_uprank"). ncr()
            else  f (boostRat o < 1.0) scopedStat.counter("author_downrank"). ncr()
            else scopedStat.counter("author_noboost"). ncr()
            So (baseScore * boostRat o)
          case _ =>
            scopedStat.counter("empty_score"). ncr()
            None
        }
      }
    )

    rankedOonCand datesFut.map { rankedOonCand dates =>
      val sortedOonCand date erator = rankedOonCand dates.to erator
      cand dateDeta ls.map { ooncRankedCand date =>
        val  sOon = t etCand dateSelector(
          ooncRankedCand date.cand date,
          MrQual yUprank ngPart alTypeEnum.Oon)

         f (sortedOonCand date erator.hasNext &&  sOon)
          sortedOonCand date erator.next()
        else ooncRankedCand date
      }
    }
  }

  def t etCand dateSelector(
    pushCand date: PushCand date,
    selectedCand dateType: MrQual yUprank ngPart alTypeEnum.Value
  ): Boolean = {
    pushCand date match {
      case cand date: PushCand date w h T etCand date =>
        selectedCand dateType match {
          case MrQual yUprank ngPart alTypeEnum.Oon =>
            val crt = cand date.commonRecType
            RecTypes. sOutOfNetworkT etRecType(crt) || RecTypes.outOfNetworkTop cT etTypes
              .conta ns(crt)
          case _ => true
        }
      case _ => false
    }
  }
}
