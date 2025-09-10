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
import com.twitter.tweet_mixer.candidate_source.popular_geo_tweets.PopularGeoTweetsCandidateSource
import com.twitter.tweet_mixer.candidate_source.popular_geo_tweets.TripStratoGeoQuery
import com.twitter.tweet_mixer.functional_component.transformer.TripStratoGeoQueryTransformer
import com.twitter.tweet_mixer.functional_component.transformer.TripTweetFeatureTransformer
import com.twitter.tweet_mixer.param.PopularGeoTweetsParams
import com.twitter.tweet_mixer.param.PopularGeoTweetsParams.PopularGeoTweetsEnable
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularGeoTweetsCandidatePipelineConfigFactory @Inject() (
  popularGeoTweetsCandidateSource: PopularGeoTweetsCandidateSource) {

  def build[Query <: PipelineQuery](
    identifierPrefix: String
  )(
    implicit notificationGroup: Map[String, NotificationGroup]
  ): PopularGeoTweetsCandidatePipelineConfig[Query] = {
    new PopularGeoTweetsCandidatePipelineConfig(identifierPrefix, popularGeoTweetsCandidateSource)
  }
}

class PopularGeoTweetsCandidatePipelineConfig[Query <: PipelineQuery](
  identifierPrefix: String,
  popularGeoTweetsCandidateSource: PopularGeoTweetsCandidateSource
)(
  implicit notificationGroup: Map[String, NotificationGroup])
    extends CandidatePipelineConfig[
      Query,
      TripStratoGeoQuery,
      TripTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier(
    identifierPrefix + CandidatePipelineConstants.PopularGeoTweets)

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(PopularGeoTweetsEnable)

  override val queryTransformer: CandidatePipelineQueryTransformer[Query, TripStratoGeoQuery] =
    TripStratoGeoQueryTransformer(
      geoSourceIdsParam = PopularGeoTweetsParams.GeoSourceIds,
      maxTweetsPerDomainParam = PopularGeoTweetsParams.MaxNumCandidatesPerTripSource,
      maxPopGeoTweetsParam = PopularGeoTweetsParams.MaxNumPopGeoCandidates
    )

  override def candidateSource: CandidateSource[
    TripStratoGeoQuery,
    TripTweet
  ] = popularGeoTweetsCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TripTweet,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TripTweet]
  ] = Seq(TripTweetFeatureTransformer)

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert()
  )
}
