package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d.Ranker d
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

/**
 * Type of Score. T   s used to d fferent ate scores.
 *
 * Def ne   as a tra  so    s poss ble to add more  nformat on for d fferent score types.
 */
sealed tra  ScoreType {
  def getNa : Str ng
}

/**
 * Ex st ng Score Types
 */
object ScoreType {

  /**
   * t  score  s calculated based on  ur st cs and most l kely not normal zed
   */
  case object  ur st cBasedScore extends ScoreType {
    overr de def getNa : Str ng = " ur st cBasedScore"
  }

  /**
   * probab l y of follow after t  cand date  s recom nded to t  user
   */
  case object PFollowG venReco extends ScoreType {
    overr de def getNa : Str ng = "PFollowG venReco"
  }

  /**
   * probab l y of engage after t  user follows t  cand date
   */
  case object PEngage ntG venFollow extends ScoreType {
    overr de def getNa : Str ng = "PEngage ntG venFollow"
  }

  /**
   * probab l y of engage per t et  mpress on
   */
  case object PEngage ntPer mpress on extends ScoreType {
    overr de def getNa : Str ng = "PEngage ntPer mpress on"
  }

  /**
   * probab l y of engage per t et  mpress on
   */
  case object PEngage ntG venReco extends ScoreType {
    overr de def getNa : Str ng = "PEngage ntG venReco"
  }

  def fromScoreTypeStr ng(scoreTypeNa : Str ng): ScoreType = scoreTypeNa  match {
    case " ur st cBasedScore" =>  ur st cBasedScore
    case "PFollowG venReco" => PFollowG venReco
    case "PEngage ntG venFollow" => PEngage ntG venFollow
    case "PEngage ntPer mpress on" => PEngage ntPer mpress on
    case "PEngage ntG venReco" => PEngage ntG venReco
  }
}

/**
 * Represent t  output from a certa n ranker or scorer. All t  f elds are opt onal
 *
 * @param value value of t  score
 * @param ranker d ranker  d
 * @param scoreType score type
 */
f nal case class Score(
  value: Double,
  ranker d: Opt on[Ranker d] = None,
  scoreType: Opt on[ScoreType] = None) {

  def toThr ft: t.Score = t.Score(
    value = value,
    ranker d = ranker d.map(_.toStr ng),
    scoreType = scoreType.map(_.getNa )
  )

  def toOffl neThr ft: offl ne.Score =
    offl ne.Score(
      value = value,
      ranker d = ranker d.map(_.toStr ng),
      scoreType = scoreType.map(_.getNa )
    )
}

object Score {

  val RandomScore = Score(0.0d, So (Ranker d.RandomRanker))

  def opt musScore(score: Double, scoreType: ScoreType): Score = {
    Score(value = score, scoreType = So (scoreType))
  }

  def pred ct onScore(score: Double, ranker d: Ranker d): Score = {
    Score(value = score, ranker d = So (ranker d))
  }

  def fromThr ft(thr ftScore: t.Score): Score =
    Score(
      value = thr ftScore.value,
      ranker d = thr ftScore.ranker d.flatMap(Ranker d.getRankerByNa ),
      scoreType = thr ftScore.scoreType.map(ScoreType.fromScoreTypeStr ng)
    )
}

/**
 * a l st of scores
 */
f nal case class Scores(
  scores: Seq[Score],
  selectedRanker d: Opt on[Ranker d] = None,
   s nProducerScor ngExper  nt: Boolean = false) {

  def toThr ft: t.Scores =
    t.Scores(
      scores = scores.map(_.toThr ft),
      selectedRanker d = selectedRanker d.map(_.toStr ng),
       s nProducerScor ngExper  nt =  s nProducerScor ngExper  nt
    )

  def toOffl neThr ft: offl ne.Scores =
    offl ne.Scores(
      scores = scores.map(_.toOffl neThr ft),
      selectedRanker d = selectedRanker d.map(_.toStr ng),
       s nProducerScor ngExper  nt =  s nProducerScor ngExper  nt
    )
}

object Scores {
  val Empty: Scores = Scores(N l)

  def fromThr ft(thr ftScores: t.Scores): Scores =
    Scores(
      scores = thr ftScores.scores.map(Score.fromThr ft),
      selectedRanker d = thr ftScores.selectedRanker d.flatMap(Ranker d.getRankerByNa ),
       s nProducerScor ngExper  nt = thr ftScores. s nProducerScor ngExper  nt
    )
}
