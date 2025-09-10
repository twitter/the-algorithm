package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.hydra.PublishGroxUserInterestsRequestKafkaClientColumn
import com.twitter.strato.columns.content_understanding.content_exploration.thriftscala.UserInterestsRequests
import com.twitter.strato.columns.content_understanding.content_exploration.thriftscala.UserInterestsTriggerScene
import com.twitter.tweet_mixer.functional_component.hydrator.UserInterestSummaryEmbeddingFeature
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UserInterestSummarySimilarityEnabled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PublishGroxUserInterestsSideEffect @Inject() (
  publishGroxUserInterestsRequestKafkaClientColumn: PublishGroxUserInterestsRequestKafkaClientColumn)
    extends PipelineResultSideEffect[PipelineQuery, TweetMixerResponse]
    with PipelineResultSideEffect.Conditionally[PipelineQuery, TweetMixerResponse] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("PublishGroxUserInterests")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: TweetMixerResponse
  ): Boolean = {
    query.params(UserInterestSummarySimilarityEnabled) &&
    query.features.flatMap(_.getOrElse(UserInterestSummaryEmbeddingFeature, None)).isEmpty
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, TweetMixerResponse]
  ): Stitch[Unit] = {
    val userId = inputs.query.getRequiredUserId
    val publishStitch = publishGroxUserInterestsRequestKafkaClientColumn.inserter.insert(
      userId,
      UserInterestsRequests(
        userId = userId,
        scene = Some(UserInterestsTriggerScene.TweetMixer)
      )
    )
    publishStitch.map(_ => ())
  }
} 