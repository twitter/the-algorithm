package com.ExTwitter.follow_recommendations.common.rankers.ml_ranker.scoring

import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasDebugOptions
import com.ExTwitter.follow_recommendations.common.models.Score
import com.ExTwitter.follow_recommendations.common.models.ScoreType
import com.ExTwitter.follow_recommendations.common.rankers.common.RankerId
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams

trait Scorer {

  // unique id of the scorer
  def id: RankerId.Value

  // type of the output scores
  def scoreType: Option[ScoreType] = None

  // Scoring when an ML model is used.
  def score(records: Seq[DataRecord]): Stitch[Seq[Score]]

  /**
   * Scoring when a non-ML method is applied. E.g: Boosting, randomized reordering, etc.
   * This method assumes that candidates' scores are already retrieved from heavy-ranker models and
   * are available for use.
   */
  def score(
    target: HasClientContext with HasParams with HasDisplayLocation with HasDebugOptions,
    candidates: Seq[CandidateUser]
  ): Seq[Option[Score]]
}
