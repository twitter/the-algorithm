package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Ranker
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l.prof leSeqResults
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasDebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.Scores
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.ut ls.Ut ls
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng.AdhocScorer
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng.Scorer
 mport com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng.ScorerFactory
 mport com.tw ter.follow_recom ndat ons.common.ut ls.Collect onUt l
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.logg ng.Logg ng

/**
 * T  class has a rank funct on that w ll perform 4 steps:
 *   - choose wh ch scorer to use for each cand date
 *   - score cand dates g ven t  r respect ve features
 *   - add scor ng  nformat on to t  cand date
 *   - sort cand dates by t  r respect ve scores
 *   T  feature s ce and scorer w ll depend on t  request's params
 */
@S ngleton
class MlRanker[
  Target <: HasCl entContext w h HasParams w h HasD splayLocat on w h HasDebugOpt ons] @ nject() (
  scorerFactory: ScorerFactory,
  statsRece ver: StatsRece ver)
    extends Ranker[Target, Cand dateUser]
    w h Logg ng {

  pr vate val stats: StatsRece ver = statsRece ver.scope("ml_ranker")

  pr vate val  nputStat = stats.scope("1_ nput")
  pr vate val selectScorerStat = stats.scope("2_select_scorer")
  pr vate val scoreStat = stats.scope("3_score")

  overr de def rank(
    target: Target,
    cand dates: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] = {
    prof leSeqResults(cand dates,  nputStat)
    val requestRanker d = target.params(MlRankerParams.RequestScorer dParam)
    val ranker ds = chooseRankerByCand date(cand dates, requestRanker d)

    val scoreSt ch = score(cand dates, ranker ds, requestRanker d).map { scoredCand dates =>
      {
        // sort t  cand dates by score
        val sortedCand dates = sort(target, scoredCand dates)
        // add scr be f eld to cand dates ( f appl cable) and return cand dates
        scr beCand dates(target, sortedCand dates)
      }
    }
    StatsUt l.prof leSt ch(scoreSt ch, stats.scope("rank"))
  }

  /**
   * @param target: T  WTF request for a g ven consu r.
   * @param cand dates A l st of cand dates cons dered for recom ndat on.
   * @return A map from each cand date to a tuple that  ncludes:
   *          (1) T  selected scorer that should be used to rank t  cand date
   *          (2) a flag determ n ng w t r t  cand date  s  n a producer-s de exper  nt.
   */
  pr vate[rank ng] def chooseRankerByCand date(
    cand dates: Seq[Cand dateUser],
    requestRanker d: Ranker d
  ): Map[Cand dateUser, Ranker d] = {
    cand dates.map { cand date =>
      val selectedCand dateRanker d =
         f (cand date.params == Params. nval d || cand date.params == Params.Empty) {
          selectScorerStat.counter("cand date_params_empty"). ncr()
          requestRanker d
        } else {
          val cand dateRanker d = cand date.params(MlRankerParams.Cand dateScorer dParam)
           f (cand dateRanker d == Ranker d.None) {
            // T  cand date  s a not part of any producer-s de exper  nt.
            selectScorerStat.counter("default_to_request_ranker"). ncr()
            requestRanker d
          } else {
            // T  cand date  s  n a treat nt bucket of a producer-s de exper  nt.
            selectScorerStat.counter("use_cand date_ranker"). ncr()
            cand dateRanker d
          }
        }
      selectScorerStat.scope("selected").counter(selectedCand dateRanker d.toStr ng). ncr()
      cand date -> selectedCand dateRanker d
    }.toMap
  }

  @V s bleForTest ng
  pr vate[rank ng] def score(
    cand dates: Seq[Cand dateUser],
    ranker ds: Map[Cand dateUser, Ranker d],
    requestRanker d: Ranker d
  ): St ch[Seq[Cand dateUser]] = {
    val features = cand dates.map(_.dataRecord.flatMap(_.dataRecord))

    requ re(features.forall(_.nonEmpty), "features are not hydrated for all t  cand dates")

    val scorers = scorerFactory.getScorers(ranker ds.values.toSeq.sorted.d st nct)

    // Scorers are spl   nto ML-based and Adhoc (def ned as a scorer that does not need to call an
    // ML pred ct on serv ce and scores cand dates us ng locally-ava lable data).
    val (adhocScorers, mlScorers) = scorers.part  on {
      case _: AdhocScorer => true
      case _ => false
    }

    // score cand dates
    val scoresSt ch = score(features.map(_.get), mlScorers)
    val cand datesW hMlScoresSt ch = scoresSt ch.map { scoresSeq =>
      cand dates
        .z p(scoresSeq).map { // copy datarecord and score  nto cand date object
          case (cand date, scores) =>
            val selectedRanker d = ranker ds(cand date)
            val useRequestRanker =
              cand date.params == Params. nval d ||
                cand date.params == Params.Empty ||
                cand date.params(MlRankerParams.Cand dateScorer dParam) == Ranker d.None
            cand date.copy(
              score = scores.scores.f nd(_.ranker d.conta ns(requestRanker d)).map(_.value),
              scores =  f (scores.scores.nonEmpty) {
                So (
                  scores.copy(
                    scores = scores.scores,
                    selectedRanker d = So (selectedRanker d),
                     s nProducerScor ngExper  nt = !useRequestRanker
                  ))
              } else None
            )
        }
    }

    cand datesW hMlScoresSt ch.map { cand dates =>
      // T  bas s for adhoc scores are t  "request-level" ML ranker.   add t  base score  re
      // wh le adhoc scorers are appl ed  n [[AdhocRanker]].
      addMlBaseScoresForAdhocScorers(cand dates, requestRanker d, adhocScorers)
    }
  }

  @V s bleForTest ng
  pr vate[rank ng] def addMlBaseScoresForAdhocScorers(
    cand dates: Seq[Cand dateUser],
    requestRanker d: Ranker d,
    adhocScorers: Seq[Scorer]
  ): Seq[Cand dateUser] = {
    cand dates.map { cand date =>
      cand date.scores match {
        case So (oldScores) =>
          // 1.   fetch t  ML score that  s t  bas s of adhoc scores:
          val baseMlScoreOpt = Ut ls.getCand dateScoreByRanker d(cand date, requestRanker d)

          // 2. For each adhoc scorer,   copy t  ML score object, chang ng only t   D and type.
          val newScores = adhocScorers flatMap { adhocScorer =>
            baseMlScoreOpt.map(
              _.copy(ranker d = So (adhocScorer. d), scoreType = adhocScorer.scoreType))
          }

          // 3.   add t  new adhoc score entr es to t  cand date.
          cand date.copy(scores = So (oldScores.copy(scores = oldScores.scores ++ newScores)))
        case _ =>
          // S nce t re  s no base ML score, t re should be no adhoc score mod f cat on as  ll.
          cand date
      }
    }
  }

  pr vate[t ] def score(
    dataRecords: Seq[DataRecord],
    scorers: Seq[Scorer]
  ): St ch[Seq[Scores]] = {
    val scoredResponse = scorers.map { scorer =>
      StatsUt l.prof leSt ch(scorer.score(dataRecords), scoreStat.scope(scorer. d.toStr ng))
    }
    //  f   could score a cand date w h too many rankers,    s l kely to blow up t  whole system.
    // and fa l back to default product on model
    StatsUt l.prof leSt ch(St ch.collect(scoredResponse), scoreStat).map { scoresByScorer d =>
      Collect onUt l.transposeLazy(scoresByScorer d).map { scoresPerCand date =>
        Scores(scoresPerCand date)
      }
    }
  }

  // sort cand dates us ng score  n descend ng order
  pr vate[t ] def sort(
    target: Target,
    cand dates: Seq[Cand dateUser]
  ): Seq[Cand dateUser] = {
    cand dates.sortBy(c => -c.score.getOrElse(MlRanker.DefaultScore))
  }

  pr vate[t ] def scr beCand dates(
    target: Target,
    cand dates: Seq[Cand dateUser]
  ): Seq[Cand dateUser] = {
    val scr beRank ng nfo: Boolean = target.params(MlRankerParams.Scr beRank ng nfo nMlRanker)
    scr beRank ng nfo match {
      case true => Ut ls.addRank ng nfo(cand dates, "MlRanker")
      case false => cand dates
    }
  }
}

object MlRanker {
  // t   s to ensure cand dates w h absent scores are ranked t  last
  val DefaultScore: Double = Double.M nValue
}
