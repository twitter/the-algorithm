package com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.tweet_tltwitter

import com.twitter.ml.featurestore.timelines.thriftscala.TimelineScorerScoreView
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.component_library.scorer.tweet_tltwitter.TLtwitterScore
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.ml.featureStore.TimelineScorerTweetScoresV1ClientColumn
import com.twitter.timelinescorer.thriftscala.v1
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Hydrate Tweet Scores via Timeline Scorer (TLtwitter)
 *
 * Note that this is the [[CandidateFeatureHydrator]] version of
 * [[com.twitter.product_mitwitterer.component_library.scorer.tweet_tltwitter.TweetTLtwitterStratoScorer]]
 */
@Singleton
class TweetTLtwitterScoreCandidateFeatureHydrator @Inject() (
  column: TimelineScorerTweetScoresV1ClientColumn)
    etwittertends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetTLtwitterScore")

  override val features: Set[Feature[_, _]] = Set(TLtwitterScore)

  private val NoScoreMap = FeatureMapBuilder()
    .add(TLtwitterScore, None)
    .build()

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    etwitteristingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    query.getOptionalUserId match {
      case Some(userId) =>
        column.fetcher
          .fetch(candidate.id, TimelineScorerScoreView(Some(userId)))
          .map(scoredTweet =>
            scoredTweet.v match {
              case Some(v1.ScoredTweet(Some(_), score, _, _)) =>
                FeatureMapBuilder()
                  .add(TLtwitterScore, score)
                  .build()
              case _ => throw new Etwitterception(s"Invalid response from TLtwitter: ${scoredTweet.v}")
            })
      case _ =>
        Stitch.value(NoScoreMap)
    }
  }
}
