package com.twitter.tweet_mixer.product.home_recommended_tweets.marshaller.response

import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.model.common.identifier.DomainMarshallerIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.tweet_mixer.feature.InReplyToTweetIdFeature
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.SignalInfo
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.tweet_mixer.functional_component.hydrator.SignalInfoFeature
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.model.response.TweetResult
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.request.HomeRecommendedTweetsQuery
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.response.HomeRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.response.HomeRecommendedTweetsResult
import com.twitter.tweet_mixer.utils.CandidateSourceUtil
import com.twitter.tweet_mixer.{thriftscala => t}
import com.twitter.usersignalservice.thriftscala.SignalType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRecommendedTweetsDomainResponseMarshaller @Inject() ()
    extends DomainMarshaller[HomeRecommendedTweetsQuery, TweetMixerResponse] {
  //Keep it same as home recommended tweets recommendation pipeline name
  override val identifier: DomainMarshallerIdentifier =
    DomainMarshallerIdentifier("HomeRecommendedTweets")

  private def toMetricTag(info: SignalInfo): Option[t.MetricTag] = {
    info.signalType match {
      case SignalType.TweetFavorite => Some(t.MetricTag.TweetFavorite)
      case SignalType.Retweet => Some(t.MetricTag.Retweet)
      case _ => None
    }
  }

  override def apply(
    query: HomeRecommendedTweetsQuery,
    selections: Seq[CandidateWithDetails]
  ): TweetMixerResponse = {
    val response = selections.map { candidateWithDetails: CandidateWithDetails =>
      val candidateSourceId: String = candidateWithDetails.source.name
      val signalBasedMetricTag = candidateWithDetails.features
        .getOrElse(SignalInfoFeature, Seq.empty).flatMap(toMetricTag)
      val sourceSignal = candidateWithDetails.features.getOrElse(SourceSignalFeature, 0L)
      val sourceSignalTypes = candidateWithDetails.features
        .getOrElse(SignalInfoFeature, Seq.empty).map(_.signalType)
      val sourceSignalEntity = candidateWithDetails.features
        .getOrElse(SignalInfoFeature, Seq.empty).map(_.signalEntity)
      val signalAuthorId =
        candidateWithDetails.features
          .getOrElse(SignalInfoFeature, Seq.empty).flatMap(_.authorId)
      val candidateSourceBasedMetricTags = CandidateSourceUtil.getMetricTag(candidateSourceId)

      val tweetMetadata = t.TweetMetadata(
        sourceSignalId = Some(sourceSignal),
        signalType = Some(sourceSignalTypes),
        servedType = CandidateSourceUtil.getServedType(identifier.name, candidateSourceId),
        signalEntity = sourceSignalEntity.headOption,
        authorId = signalAuthorId.headOption,
      )

      HomeRecommendedTweetsResult(
        TweetResult(
          id = candidateWithDetails.candidateIdLong,
          score = candidateWithDetails.features.getOrElse(ScoreFeature, 0.0),
          metricTags = signalBasedMetricTag ++ candidateSourceBasedMetricTags,
          metadata = Some(tweetMetadata),
          inReplyToTweetId = candidateWithDetails.features.getOrElse(InReplyToTweetIdFeature, None),
          authorId = candidateWithDetails.features.getOrElse(AuthorIdFeature, None)
        ))
    }

    TweetMixerResponse(HomeRecommendedTweetsProductResponse(response))
  }
}
