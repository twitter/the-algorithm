package com.ExTwitter.follow_recommendations.common.feature_hydration.sources

import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.follow_recommendations.common.feature_hydration.adapters.PreFetchedFeatureAdapter
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.FeatureSource
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.FeatureSourceId
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasSimilarToContext
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.FeatureContext
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams

@Provides
@Singleton
class PreFetchedFeatureSource @Inject() () extends FeatureSource {
  override def id: FeatureSourceId = FeatureSourceId.PreFetchedFeatureSourceId
  override def featureContext: FeatureContext = PreFetchedFeatureAdapter.getFeatureContext
  override def hydrateFeatures(
    target: HasClientContext
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContext
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]] = {
    Stitch.value(candidates.map { candidate =>
      candidate -> PreFetchedFeatureAdapter.adaptToDataRecord((target, candidate))
    }.toMap)
  }
}
