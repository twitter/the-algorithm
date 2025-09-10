package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.twitter.tweet_mixer.candidate_source.popular_topic_tweets.PopularTopicTweetsCandidateSource
import com.twitter.tweet_mixer.candidate_source.popular_topic_tweets.TripStratoTopicQuery
import com.twitter.tweet_mixer.functional_component.transformer.TripStratoTopicQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.TripTweetFeatureTransformer
import com.twitter.tweet_mixer.param.PopularTopicTweetsParams
import com.twitter.tweet_mixer.param.PopularTopicTweetsParams.PopularTopicTweetsEnable
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularTopicTweetsCandidatePipelineConfigFactory @Inject() (
  popularTopicTweetsCandidateSource: PopularTopicTweetsCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): PopularTopicTweetsCandidatePipelineConfig[Query] = {
    new PopularTopicTweetsCandidatePipelineConfig(
      identifierPrefix,
      popularTopicTweetsCandidateSource)
  }
}

class PopularTopicTweetsCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  popularTopicTweetsCandidateSource: PopularTopicTweetsCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      TripStratoTopicQuery,
      TripTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.PopularTopicTweets)

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(PopularTopicTweetsEnable)

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, TripStratoTopicQuery] =
    TripStratoTopicQueryTransformer(
      sourceIdsParam = PopularTopicTweetsParams.SourceIds,
      maxTweetsPerDomainParam = PopularTopicTweetsParams.MaxNumCandidatesPerTripSource,
      maxTweetsParam = PopularTopicTweetsParams.MaxNumCandidates,
      popTopicIdsParam = PopularTopicTweetsParams.PopTopicIds
    )

  override def candidateSource: CandidateSource[
    TripStratoTopicQuery,
    TripTweet
  ] = popularTopicTweetsCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TripTweet,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TripTweet]
  ] = Seq(TripTweetFeatureTransformer)

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert(warnThreshold = 80, criticalThreshold = 90),
  )
}
