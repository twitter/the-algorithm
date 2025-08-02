package com.twitter.follow_recommendations.common.rankers.ml_ranker.ranking

import com.google.inject.Inject
import com.google.inject.Singleton
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.base.GatedTransform
import com.twitter.follow_recommendations.common.base.StatsUtil.profileStitchMapResults
import com.twitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.twitter.follow_recommendations.common.feature_hydration.sources.UserScoringFeatureSource
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasDebugOptions
import com.twitter.follow_recommendations.common.models.HasDisplayLocation
import com.twitter.follow_recommendations.common.models.HasSimilarToContetwittert
import com.twitter.follow_recommendations.common.models.RichDataRecord
import com.twitter.ml.api.DataRecord
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import com.twitter.util.logging.Logging

/**
 * Hydrate features given target and candidates lists.
 * This is a required step before MlRanker.
 * If a feature is not hydrated before MlRanker is triggered, a runtime etwitterception will be thrown
 */
@Singleton
class HydrateFeaturesTransform[
  Target <: HasClientContetwittert with HasParams with HasDebugOptions with HasPreFetchedFeature with HasSimilarToContetwittert with HasDisplayLocation] @Inject() (
  userScoringFeatureSource: UserScoringFeatureSource,
  stats: StatsReceiver)
    etwittertends GatedTransform[Target, CandidateUser]
    with Logging {

  private val hydrateFeaturesStats = stats.scope("hydrate_features")

  def transform(target: Target, candidates: Seq[CandidateUser]): Stitch[Seq[CandidateUser]] = {
    // get features
    val featureMapStitch: Stitch[Map[CandidateUser, DataRecord]] =
      profileStitchMapResults(
        userScoringFeatureSource.hydrateFeatures(target, candidates),
        hydrateFeaturesStats)

    featureMapStitch.map { featureMap =>
      candidates
        .map { candidate =>
          val dataRecord = featureMap(candidate)
          // add debugRecord only when the request parameter is set
          val debugDataRecord = if (target.debugOptions.etwitterists(_.fetchDebugInfo)) {
            Some(candidate.toDebugDataRecord(dataRecord, userScoringFeatureSource.featureContetwittert))
          } else None
          candidate.copy(
            dataRecord = Some(RichDataRecord(Some(dataRecord), debugDataRecord))
          )
        }
    }
  }
}
