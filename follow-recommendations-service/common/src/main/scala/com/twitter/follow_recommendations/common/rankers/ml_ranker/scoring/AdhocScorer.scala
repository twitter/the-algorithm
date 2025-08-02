package com.twitter.follow_recommendations.common.rankers.ml_ranker.scoring

import com.twitter.follow_recommendations.common.rankers.common.AdhocScoreModificationType.AdhocScoreModificationType
import com.twitter.follow_recommendations.common.models.Score
import com.twitter.ml.api.DataRecord
import com.twitter.stitch.Stitch

trait AdhocScorer etwittertends Scorer {

  /**
   * NOTE: For instances of [[AdhocScorer]] this function SHOULD NOT be used.
   * Please use:
   *   [[score(target: HasClientContetwittert with HasParams, candidates: Seq[CandidateUser])]]
   * instead.
   */
  @Deprecated
  override def score(records: Seq[DataRecord]): Stitch[Seq[Score]] =
    throw new UnsupportedOperationEtwitterception(
      "For instances of AdhocScorer this operation is not defined. Please use " +
        "`def score(target: HasClientContetwittert with HasParams, candidates: Seq[CandidateUser])` " +
        "instead.")

  /**
   * This helps us manage the etwittertend of adhoc modification on candidates' score. There is a hard
   * limit of applying ONLY ONE scorer of each type to a score.
   */
  val scoreModificationType: AdhocScoreModificationType
}
