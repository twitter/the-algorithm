package com.twitter.product_mitwitterer.component_library.scorer.tweet_tltwitter

import com.twitter.ml.featurestore.timelines.thriftscala.TimelineScorerScoreView
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Fetch.Result
import com.twitter.strato.generated.client.ml.featureStore.TimelineScorerTweetScoresV1ClientColumn
import com.twitter.timelinescorer.thriftscala.v1
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Score Tweets via Timeline Scorer (TLtwitter) Strato API
 *
 * @note This results in an additional hop through Strato Server
 * @note This is the [[Scorer]] version of
 * [[com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.tweet_tltwitter.TweetTLtwitterScoreCandidateFeatureHydrator]]
 */
@Singleton
class TweetTLtwitterStratoScorer @Inject() (column: TimelineScorerTweetScoresV1ClientColumn)
    etwittertends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("TweetTLtwitter")

  override val features: Set[Feature[_, _]] = Set(TLtwitterScore)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = query.getOptionalUserId match {
    case Some(userId) => getScoredTweetsFromTLtwitter(userId, candidates.map(_.candidate))
    case _ =>
      val defaultFeatureMap = FeatureMapBuilder().add(TLtwitterScore, None).build()
      Stitch.value(candidates.map(_ => defaultFeatureMap))
  }

  def getScoredTweetsFromTLtwitter(
    userId: Long,
    tweetCandidates: Seq[TweetCandidate]
  ): Stitch[Seq[FeatureMap]] = Stitch.collect(tweetCandidates.map { candidate =>
    column.fetcher
      .fetch(candidate.id, TimelineScorerScoreView(Some(userId)))
      .map {
        case Result(Some(v1.ScoredTweet(_, score, _, _)), _) =>
          FeatureMapBuilder()
            .add(TLtwitterScore, score)
            .build()
        case fetchResult => throw new Etwitterception(s"Invalid response from TLtwitter: ${fetchResult.v}")
      }
  })
}
