package com.twitter.home_mixer.functional_component.scorer

import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetAuthorFollowersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Scales scores of each out-of-network tweet by a factor that dynamically
 * adjusts based on author follower count to encourage discovery of new voices.
 */
object OONTweetScalingScorer extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("OONTweetScaling")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  private val BaseScaleFactor = 0.75

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val tweetAuthorFollowers = query.features
      .map(_.getOrElse(TweetAuthorFollowersFeature, Map.empty[Long, Option[Long]]))
      .getOrElse(Map.empty)

    Stitch.value {
      candidates.map { candidate =>
        val score = candidate.features.getOrElse(ScoreFeature, None)
        
        val updatedScore = if (selector(candidate)) {
          val followers = tweetAuthorFollowers.get(candidate.candidate.id).flatten.getOrElse(0L)
          val discoveryBoost = calculateDiscoveryBoost(followers)
          score.map(_ * BaseScaleFactor * discoveryBoost)
        } else {
          score
        }
        
        FeatureMapBuilder().add(ScoreFeature, updatedScore).build()
      }
    }
  }

  /**
   * Calculate discovery boost using a logarithmic inverse of follower count.
   * Boost = 1 + (1 / ln(followers + e))
   */
  private def calculateDiscoveryBoost(followers: Long): Double = {
    val f = math.max(0L, followers).toDouble
    1.0 + (1.0 / math.log(f + math.E))
  }

  /**
   * We should only be applying this multiplier to Out-Of-Network tweets.
   * In-Network Retweets of Out-Of-Network tweets should not have this multiplier applied
   */
  private def selector(candidate: CandidateWithFeatures[TweetCandidate]): Boolean = {
    !candidate.features.getOrElse(InNetworkFeature, false) &&
    !candidate.features.getOrElse(IsRetweetFeature, false)
  }
}
