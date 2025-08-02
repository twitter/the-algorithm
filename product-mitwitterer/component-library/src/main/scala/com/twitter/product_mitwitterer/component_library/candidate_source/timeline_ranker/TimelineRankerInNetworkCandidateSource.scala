package com.twitter.product_mitwitterer.component_library.candidate_source.timeline_ranker

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelineranker.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Map of tweetId -> sourceTweet of retweets present in Timeline Ranker candidates list.
 * These tweets are used only for further ranking. They are not returned to the end user.
 */
object TimelineRankerInNetworkSourceTweetsByTweetIdMapFeature
    etwittertends Feature[PipelineQuery, Map[Long, t.CandidateTweet]]

@Singleton
class TimelineRankerInNetworkCandidateSource @Inject() (
  timelineRankerClient: t.TimelineRanker.MethodPerEndpoint)
    etwittertends CandidateSourceWithEtwittertractedFeatures[t.RecapQuery, t.CandidateTweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("TimelineRankerInNetwork")

  override def apply(
    request: t.RecapQuery
  ): Stitch[CandidatesWithSourceFeatures[t.CandidateTweet]] = {
    Stitch
      .callFuture(timelineRankerClient.getRecycledTweetCandidates(Seq(request)))
      .map { response: Seq[t.GetCandidateTweetsResponse] =>
        val candidates =
          response.headOption.flatMap(_.candidates).getOrElse(Seq.empty).filter(_.tweet.nonEmpty)
        val sourceTweetsByTweetId =
          response.headOption
            .flatMap(_.sourceTweets).getOrElse(Seq.empty).filter(_.tweet.nonEmpty)
            .map { candidate =>
              (candidate.tweet.get.id, candidate)
            }.toMap
        val sourceTweetsByTweetIdMapFeature = FeatureMapBuilder()
          .add(TimelineRankerInNetworkSourceTweetsByTweetIdMapFeature, sourceTweetsByTweetId)
          .build()
        CandidatesWithSourceFeatures(
          candidates = candidates,
          features = sourceTweetsByTweetIdMapFeature)
      }
  }
}
