package com.tw ter.s mclusters_v2.score

 mport com.tw ter.s mclusters_v2.score.  ghtedSumAggregatedScoreStore.  ghtedSumAggregatedScorePara ter
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Embedd ngType,
  Gener cPa rScore d,
  ModelVers on,
  Score nternal d,
  Scor ngAlgor hm,
  S mClustersEmbedd ng d,
  Score => Thr ftScore,
  Score d => Thr ftScore d,
  S mClustersEmbedd ngPa rScore d => Thr ftS mClustersEmbedd ngPa rScore d
}
 mport com.tw ter.ut l.Future

/**
 * A gener c store wrapper to aggregate t  scores of N underly ng stores  n a   ghted fash on.
 *
 */
case class   ghtedSumAggregatedScoreStore(para ters: Seq[  ghtedSumAggregatedScorePara ter])
    extends AggregatedScoreStore {

  overr de def get(k: Thr ftScore d): Future[Opt on[Thr ftScore]] = {
    val underly ngScores = para ters.map { para ter =>
      scoreFacadeStore
        .get(Thr ftScore d(para ter.scoreAlgor hm, para ter. dTransform(k. nternal d)))
        .map(_.map(s => para ter.scoreTransform(s.score) * para ter.  ght))
    }
    Future.collect(underly ngScores).map { scores =>
       f (scores.ex sts(_.nonEmpty)) {
        val newScore = scores.foldLeft(0.0) {
          case (sum, maybeScore) =>
            sum + maybeScore.getOrElse(0.0)
        }
        So (Thr ftScore(score = newScore))
      } else {
        // Return None  f all of t  underly ng score  s None.
        None
      }
    }
  }
}

object   ghtedSumAggregatedScoreStore {

  /**
   * T  para ter of   ghtedSumAggregatedScoreStore. Create 0 to N para ters for a   ghtedSum
   * AggregatedScore Store. Please evaluate t  performance before product on zat on any new score.
   *
   * @param scoreAlgor hm t  underly ng score algor hm na 
   * @param   ght contr but on to   ghted sum of t  sub-score
   * @param  dTransform transform t  s ce Score nternal d to underly ng score  nternal d.
   * @param scoreTransform funct on to apply to sub-score before add ng to   ghted sum
   */
  case class   ghtedSumAggregatedScorePara ter(
    scoreAlgor hm: Scor ngAlgor hm,
      ght: Double,
     dTransform: Score nternal d => Score nternal d,
    scoreTransform: Double => Double =  dent yScoreTransform)

  val Sa TypeScore nternal dTransform: Score nternal d => Score nternal d = {  d =>  d }
  val  dent yScoreTransform: Double => Double = { score => score }

  // Convert Gener c  nternal  d to a S mClustersEmbedd ng d
  def gener cPa rScore dToS mClustersEmbedd ngPa rScore d(
    embedd ngType1: Embedd ngType,
    embedd ngType2: Embedd ngType,
    modelVers on: ModelVers on
  ): Score nternal d => Score nternal d = {
    case  d: Score nternal d.Gener cPa rScore d =>
      Score nternal d.S mClustersEmbedd ngPa rScore d(
        Thr ftS mClustersEmbedd ngPa rScore d(
          S mClustersEmbedd ng d(embedd ngType1, modelVers on,  d.gener cPa rScore d. d1),
          S mClustersEmbedd ng d(embedd ngType2, modelVers on,  d.gener cPa rScore d. d2)
        ))
  }

  val s mClustersEmbedd ngPa rScore dToGener cPa rScore d: Score nternal d => Score nternal d = {
    case Score nternal d.S mClustersEmbedd ngPa rScore d(s mClusters d) =>
      Score nternal d.Gener cPa rScore d(
        Gener cPa rScore d(s mClusters d. d1. nternal d, s mClusters d. d2. nternal d))
  }
}
