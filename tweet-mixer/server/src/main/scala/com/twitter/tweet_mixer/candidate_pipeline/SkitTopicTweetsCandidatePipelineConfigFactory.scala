package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.gate.ParamGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.topic_tweets.SkitTopicTweetsCandidateSource
import com.twitter.tweet_mixer.candidate_source.topic_tweets.SkitTopicTweetsQuery
import com.twitter.tweet_mixer.functional_component.transformer.TopicTweetFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkitTopicTweetsCandidatePipelineConfigFactory @Inject() (
  skitTopicTweetsCandidateSource: SkitTopicTweetsCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    enabledParam: FSParam[Boolean],
    identifierString: String,
    queryTransformer: CandidatePipelineQueryTransformer[Query, SkitTopicTweetsQuery]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): SkitTopicTweetsCandidatePipelineConfig[Query] = {
    new SkitTopicTweetsCandidatePipelineConfig(
      identifierPrefix = identifierPrefix,
      enabledParam = enabledParam,
      identifierString = identifierString,
      candidateSource = skitTopicTweetsCandidateSource,
      queryTransformer = queryTransformer,
    )
  }
}

class SkitTopicTweetsCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  enabledParam: FSParam[Boolean],
  identifierString: String,
  override val candidateSource: CandidateSource[
    SkitTopicTweetsQuery,
    TweetMixerCandidate
  ],
  override val queryTransformer: CandidatePipelineQueryTransformer[Query, SkitTopicTweetsQuery]
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      SkitTopicTweetsQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + identifierString)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    ParamGate(
      name = s"${identifierString}Enabled",
      param = enabledParam
    )
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer, TopicTweetFeatureTransformer)

  override val alerts = Seq(
    defaultSuccessRateAlert(),
  )
}
