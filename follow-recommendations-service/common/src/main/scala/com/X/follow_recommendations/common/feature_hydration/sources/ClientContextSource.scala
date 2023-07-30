package com.X.follow_recommendations.common.feature_hydration.sources

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.follow_recommendations.common.feature_hydration.adapters.ClientContextAdapter
import com.X.follow_recommendations.common.feature_hydration.common.FeatureSource
import com.X.follow_recommendations.common.feature_hydration.common.FeatureSourceId
import com.X.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.follow_recommendations.common.models.HasSimilarToContext
import com.X.ml.api.DataRecord
import com.X.ml.api.FeatureContext
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams

/**
 * This source only takes features from the request (e.g. client context, WTF display location)
 * No external calls are made.
 */
@Provides
@Singleton
class ClientContextSource() extends FeatureSource {

  override val id: FeatureSourceId = FeatureSourceId.ClientContextSourceId

  override val featureContext: FeatureContext = ClientContextAdapter.getFeatureContext

  override def hydrateFeatures(
    t: HasClientContext
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContext
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]] = {
    Stitch.value(
      candidates
        .map(_ -> ((t.clientContext, t.displayLocation))).toMap.mapValues(
          ClientContextAdapter.adaptToDataRecord))
  }
}
