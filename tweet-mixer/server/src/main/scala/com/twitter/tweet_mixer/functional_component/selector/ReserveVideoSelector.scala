package com.twitter.tweet_mixer.functional_component.selector

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.HasVideo
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.isFeatureSet

case class ReserveVideoSelector(
  override val pipelineScope: CandidateScope = AllPipelines,
  maxResults: Param[Int])
    extends Selector[PipelineQuery] {

  def isVideo(candidateWithDetails: CandidateWithDetails): Boolean = {
    val tweetBooleanInfo =
      candidateWithDetails.features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0)
    isFeatureSet(HasVideo, tweetBooleanInfo)
  }
  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val topK = query.requestedMaxResults.getOrElse(query.params(maxResults))
    val topKResult = result.take(topK)

    val (videoResultTopK, notVideoResultTopK) = topKResult.partition(isVideo)

    val (videoResult, notVideoResult) = result.partition(isVideo)

    SelectorResult(
      remainingCandidates = remainingCandidates ++ notVideoResult,
      result = videoResultTopK)
  }
}
