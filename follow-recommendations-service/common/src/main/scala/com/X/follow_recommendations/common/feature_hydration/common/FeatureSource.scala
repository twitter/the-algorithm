package com.X.follow_recommendations.common.feature_hydration.common

import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.follow_recommendations.common.models.HasSimilarToContext
import com.X.ml.api.DataRecord
import com.X.ml.api.FeatureContext
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams

trait FeatureSource {
  def id: FeatureSourceId
  def featureContext: FeatureContext
  def hydrateFeatures(
    target: HasClientContext
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContext
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]]
}
