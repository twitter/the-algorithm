package com.twitter.product_mitwitterer.component_library.candidate_source.timeline_ranker

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.UnetwitterpectedCandidateResult
import com.twitter.stitch.Stitch
import com.twitter.timelineranker.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Source tweets of retweets present in Timeline Ranker candidates list.
 * These tweets are used only for further ranking. They are not returned to the end user.
 */
case object TimelineRankerUtegSourceTweetsFeature
    etwittertends Feature[PipelineQuery, Seq[t.CandidateTweet]]

@Singleton
class TimelineRankerUtegCandidateSource @Inject() (
  timelineRankerClient: t.TimelineRanker.MethodPerEndpoint)
    etwittertends CandidateSourceWithEtwittertractedFeatures[t.UtegLikedByTweetsQuery, t.CandidateTweet] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("TimelineRankerUteg")

  override def apply(
    request: t.UtegLikedByTweetsQuery
  ): Stitch[CandidatesWithSourceFeatures[t.CandidateTweet]] = {
    Stitch
      .callFuture(timelineRankerClient.getUtegLikedByTweetCandidates(Seq(request)))
      .map { response =>
        val result = response.headOption.getOrElse(
          throw PipelineFailure(UnetwitterpectedCandidateResult, "Empty Timeline Ranker response"))
        val candidates = result.candidates.toSeq.flatten
        val sourceTweets = result.sourceTweets.toSeq.flatten

        val candidateSourceFeatures = FeatureMapBuilder()
          .add(TimelineRankerUtegSourceTweetsFeature, sourceTweets)
          .build()

        CandidatesWithSourceFeatures(candidates = candidates, features = candidateSourceFeatures)
      }
  }
}
