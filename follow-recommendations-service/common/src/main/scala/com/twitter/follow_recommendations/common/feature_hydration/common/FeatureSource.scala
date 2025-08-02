package com.twitter.follow_recommendations.common.feature_hydration.common

import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasDisplayLocation
import com.twitter.follow_recommendations.common.models.HasSimilarToContetwittert
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

trait FeatureSource {
  def id: FeatureSourceId
  def featureContetwittert: FeatureContetwittert
  def hydrateFeatures(
    target: HasClientContetwittert
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContetwittert
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]]
}
