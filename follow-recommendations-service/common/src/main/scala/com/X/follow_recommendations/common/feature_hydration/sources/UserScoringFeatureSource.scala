package com.X.follow_recommendations.common.feature_hydration.sources

import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.follow_recommendations.common.feature_hydration.common.FeatureSource
import com.X.follow_recommendations.common.feature_hydration.common.FeatureSourceId
import com.X.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.follow_recommendations.common.models.HasSimilarToContext
import com.X.ml.api.DataRecord
import com.X.ml.api.DataRecordMerger
import com.X.ml.api.FeatureContext
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams

/**
 * This source wraps around the separate sources that we hydrate features from
 * @param featureStoreSource        gets features that require a RPC call to feature store
 * @param stratoFeatureHydrationSource    gets features that require a RPC call to strato columns
 * @param clientContextSource       gets features that are already present in the request context
 * @param candidateAlgorithmSource  gets features that are already present from candidate generation
 * @param preFetchedFeatureSource   gets features that were prehydrated (shared in request lifecycle)
 */
@Provides
@Singleton
class UserScoringFeatureSource @Inject() (
  featureStoreSource: FeatureStoreSource,
  featureStoreGizmoduckSource: FeatureStoreGizmoduckSource,
  featureStorePostNuxAlgorithmSource: FeatureStorePostNuxAlgorithmSource,
  featureStoreTimelinesAuthorSource: FeatureStoreTimelinesAuthorSource,
  featureStoreUserMetricCountsSource: FeatureStoreUserMetricCountsSource,
  clientContextSource: ClientContextSource,
  candidateAlgorithmSource: CandidateAlgorithmSource,
  preFetchedFeatureSource: PreFetchedFeatureSource)
    extends FeatureSource {

  override val id: FeatureSourceId = FeatureSourceId.UserScoringFeatureSourceId

  override val featureContext: FeatureContext = FeatureContext.merge(
    featureStoreSource.featureContext,
    featureStoreGizmoduckSource.featureContext,
    featureStorePostNuxAlgorithmSource.featureContext,
    featureStoreTimelinesAuthorSource.featureContext,
    featureStoreUserMetricCountsSource.featureContext,
    clientContextSource.featureContext,
    candidateAlgorithmSource.featureContext,
    preFetchedFeatureSource.featureContext,
  )

  val sources =
    Seq(
      featureStoreSource,
      featureStorePostNuxAlgorithmSource,
      featureStoreTimelinesAuthorSource,
      featureStoreUserMetricCountsSource,
      featureStoreGizmoduckSource,
      clientContextSource,
      candidateAlgorithmSource,
      preFetchedFeatureSource
    )

  val dataRecordMerger = new DataRecordMerger

  def hydrateFeatures(
    target: HasClientContext
      with HasPreFetchedFeature
      with HasParams
      with HasSimilarToContext
      with HasDisplayLocation,
    candidates: Seq[CandidateUser]
  ): Stitch[Map[CandidateUser, DataRecord]] = {
    Stitch.collect(sources.map(_.hydrateFeatures(target, candidates))).map { featureMaps =>
      (for {
        candidate <- candidates
      } yield {
        val combinedDataRecord = new DataRecord
        featureMaps
          .flatMap(_.get(candidate).toSeq).foreach(dataRecordMerger.merge(combinedDataRecord, _))
        candidate -> combinedDataRecord
      }).toMap
    }
  }
}
