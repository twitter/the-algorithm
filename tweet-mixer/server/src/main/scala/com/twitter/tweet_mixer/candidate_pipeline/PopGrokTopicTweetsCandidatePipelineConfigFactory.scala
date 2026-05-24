package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.popular_grok_topic_tweets.GrokTopicTweetsQuery
import com.twitter.tweet_mixer.candidate_source.popular_grok_topic_tweets.PopGrokTopicTweetsCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.GrokTopicTweetsQueryTransformer
import com.twitter.tweet_mixer.param.PopGrokTopicTweetsParams
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopGrokTopicTweetsCandidatePipelineConfigFactory @Inject() (
  popGrokTopicTweetsCandidateSource: PopGrokTopicTweetsCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): PopGrokTopicTweetsCandidatePipelineConfig[Query] = {
    new PopGrokTopicTweetsCandidatePipelineConfig(
      identifierPrefix,
      popGrokTopicTweetsCandidateSource
    )
  }
}

class PopGrokTopicTweetsCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  popGrokTopicTweetsCandidateSource: PopGrokTopicTweetsCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      GrokTopicTweetsQuery,
      TweetCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.PopGrokTopicTweets)

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(PopGrokTopicTweetsParams.PopGrokTopicTweetsEnable)

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, GrokTopicTweetsQuery] =
    GrokTopicTweetsQueryTransformer(PopGrokTopicTweetsParams.MaxNumCandidates)

  override def candidateSource: CandidateSource[
    GrokTopicTweetsQuery,
    TweetCandidate
  ] = popGrokTopicTweetsCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetCandidate,
    TweetCandidate
  ] = { sourceResult => sourceResult }

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert(warnThreshold = 80, criticalThreshold = 90),
  )
}
