package com.tw ter.s mclusters_v2.score

 mport com.tw ter.s mclusters_v2.thr ftscala.{Score => Thr ftScore}

/**
 * A un form value type for all k nds of Calculat on Score.
 **/
case class Score(score: Double) {

   mpl c  lazy val toThr ft: Thr ftScore = {
    Thr ftScore(score)
  }
}

object Score {

  /**
   * Only support Double Type Thr ft score
   */
   mpl c  val fromThr ftScore: Thr ftScore => Score = { thr ftScore => Score(thr ftScore.score) }

}
