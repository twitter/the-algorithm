package com.twitter.follow_recommendations.common.feature_hydration.sources

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.feature_hydration.adapters.ClientContetwittertAdapter
import com.twitter.follow_recommendations.common.feature_hydration.common.FeatureSource
import com.twitter.follow_recommendations.common.feature_hydration.common.FeatureSourceId
import com.twitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasDisplayLocation
import com.twitter.follow_recommendations.common.models.HasSimilarToContetwittert
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

/**
 * This source only takes features from the request (e.g. client contetwittert, WTF display location)
 * No etwitterternal calls are made.
 */
@Provides
@Singleton
class ClientContetwittertSource() etwittertends FeatureSource {

  override val id: FeatureSourceId = FeatureSourceId.ClientContetwittertSourceId

  override val featureContetwittert: FeatureContetwittert = ClientContetwittertAdapter.getFeatureContetwittert

  override def hydrateFeatures(
    t: HasClientContetwittert
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContetwittert
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]] = {
    Stitch.value(
      candidates
        .map(_ -> ((t.clientContetwittert, t.displayLocation))).toMap.mapValues(
          ClientContetwittertAdapter.adaptToDataRecord))
  }
}
