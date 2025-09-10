package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.topic_tweets.CertoTopicTweetsCandidateSource
import com.twitter.tweet_mixer.candidate_source.topic_tweets.CertoTopicTweetsQuery
import com.twitter.tweet_mixer.functional_component.gate.AllowLowSignalUserGate
import com.twitter.tweet_mixer.functional_component.transformer.TopicTweetFeatureTransformer
import com.twitter.tweet_mixer.functional_component.transformer.CertoTopicTweetsQueryTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.CertoTopicTweetsParams
import com.twitter.tweet_mixer.param.CertoTopicTweetsParams.CertoTopicTweetsEnable
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableUserTopicIdsHydrator
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CertoTopicTweetsCandidatePipelineConfigFactory @Inject() (
  certoTopicTweetsCandidateSource: CertoTopicTweetsCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String,
    topicsSignalFn: PipelineQuery => Seq[Long]
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): CertoTopicTweetsCandidatePipelineConfig[Query] = {
    new CertoTopicTweetsCandidatePipelineConfig(
      identifierPrefix = identifierPrefix,
      topicsSignalFn = topicsSignalFn,
      certoTopicTweetsCandidateSource = certoTopicTweetsCandidateSource
    )
  }
}

class CertoTopicTweetsCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  topicsSignalFn: PipelineQuery => Seq[Long],
  certoTopicTweetsCandidateSource: CertoTopicTweetsCandidateSource
)(
  implicit val notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      CertoTopicTweetsQuery,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.CertoTopicTweets)

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(CertoTopicTweetsEnable)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(AllowLowSignalUserGate)

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, CertoTopicTweetsQuery] =
    CertoTopicTweetsQueryTransformer(
      maxTweetsPerTopicParam = CertoTopicTweetsParams.MaxNumCandidatesPerTopic,
      maxTweetsParam = CertoTopicTweetsParams.MaxNumCandidates,
      minCertoScoreParam = CertoTopicTweetsParams.MinCertoScore,
      minCertoFavCountParam = CertoTopicTweetsParams.MinFavCount,
      userInferredTopicIdsEnabled = EnableUserTopicIdsHydrator,
      signalsFn = query => topicsSignalFn(query)
    )

  override def candidateSource: CandidateSource[
    CertoTopicTweetsQuery,
    TweetMixerCandidate
  ] = certoTopicTweetsCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TopicTweetFeatureTransformer)

  override val alerts = Seq(
    defaultSuccessRateAlert()
  )
}
