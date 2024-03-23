package com.ExTwitter.follow_recommendations.common.transforms.ranker_id

import com.google.inject.Inject
import com.google.inject.Singleton
import com.ExTwitter.follow_recommendations.common.base.GatedTransform
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.Score
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams

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
