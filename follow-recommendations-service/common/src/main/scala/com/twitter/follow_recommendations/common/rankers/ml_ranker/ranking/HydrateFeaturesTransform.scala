package com.ExTwitter.follow_recommendations.common.rankers.ml_ranker.ranking

import com.google.inject.Inject
import com.google.inject.Singleton
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.base.GatedTransform
import com.ExTwitter.follow_recommendations.common.base.StatsUtil.profileStitchMapResults
import com.ExTwitter.follow_recommendations.common.feature_hydration.common.HasPreFetchedFeature
import com.ExTwitter.follow_recommendations.common.feature_hydration.sources.UserScoringFeatureSource
import com.ExTwitter.follow_recommendations.common.models.CandidateUser
import com.ExTwitter.follow_recommendations.common.models.HasDebugOptions
import com.ExTwitter.follow_recommendations.common.models.HasDisplayLocation
import com.ExTwitter.follow_recommendations.common.models.HasSimilarToContext
import com.ExTwitter.follow_recommendations.common.models.RichDataRecord
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import com.ExTwitter.util.logging.Logging

/**
 * Hydrate features given target and candidates lists.
 * This is a required step before MlRanker.
 * If a feature is not hydrated before MlRanker is triggered, a runtime exception will be thrown
 */
@Singleton
class HydrateFeaturesTransform[
  Target <: HasClientContext with HasParams with HasDebugOptions with HasPreFetchedFeature with HasSimilarToContext with HasDisplayLocation] @Inject() (
  userScoringFeatureSource: UserScoringFeatureSource,
  stats: StatsReceiver)
    extends GatedTransform[Target, CandidateUser]
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
          val debugDataRecord = if (target.debugOptions.exists(_.fetchDebugInfo)) {
            Some(candidate.toDebugDataRecord(dataRecord, userScoringFeatureSource.featureContext))
          } else None
          candidate.copy(
            dataRecord = Some(RichDataRecord(Some(dataRecord), debugDataRecord))
          )
        }
    }
  }
}
