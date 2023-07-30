package com.X.product_mixer.component_library.feature_hydrator.candidate.tweet_tlx

import com.X.ml.featurestore.timelines.thriftscala.TimelineScorerScoreView
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.component_library.scorer.tweet_tlx.TLXScore
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.strato.generated.client.ml.featureStore.TimelineScorerTweetScoresV1ClientColumn
import com.X.timelinescorer.thriftscala.v1
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Hydrate Tweet Scores via Timeline Scorer (TLX)
 *
 * Note that this is the [[CandidateFeatureHydrator]] version of
 * [[com.X.product_mixer.component_library.scorer.tweet_tlx.TweetTLXStratoScorer]]
 */
@Singleton
class TweetTLXScoreCandidateFeatureHydrator @Inject() (
  column: TimelineScorerTweetScoresV1ClientColumn)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetTLXScore")

  override val features: Set[Feature[_, _]] = Set(TLXScore)

  private val NoScoreMap = FeatureMapBuilder()
    .add(TLXScore, None)
    .build()

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    query.getOptionalUserId match {
      case Some(userId) =>
        column.fetcher
          .fetch(candidate.id, TimelineScorerScoreView(Some(userId)))
          .map(scoredTweet =>
            scoredTweet.v match {
              case Some(v1.ScoredTweet(Some(_), score, _, _)) =>
                FeatureMapBuilder()
                  .add(TLXScore, score)
                  .build()
              case _ => throw new Exception(s"Invalid response from TLX: ${scoredTweet.v}")
            })
      case _ =>
        Stitch.value(NoScoreMap)
    }
  }
}
