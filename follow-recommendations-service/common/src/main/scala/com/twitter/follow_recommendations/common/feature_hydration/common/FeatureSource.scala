package com.ExTwitter.follow_recommendations.common.feature_hydration.common

import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasSimilarToContext
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.FeatureContext
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams

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
