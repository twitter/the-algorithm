package com.twitter.follow_recommendations.common.transforms.ranker_id

import com.google.inject.Inject
import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.base.GatedTransform
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.Score
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

/**
 * This class appends each candidate's rankerIds with the RandomRankerId.
 * This is primarily for determining if a candidate was generated via random shuffling.
 */
@Singleton
class RandomRankerIdTransform @Inject() () extends GatedTransform[HasParams, CandidateUser] {

  override def transform(
    target: HasParams,
    candidates: Seq[CandidateUser]
  ): Stitch[Seq[CandidateUser]] = {
    Stitch.value(candidates.map(_.addScore(Score.RandomScore)))
  }
}
