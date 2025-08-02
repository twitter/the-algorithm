package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.EtwitterclusiveConversationAuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsNsfwFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedTweetDroppedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetLanguageFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetTetwittertFeature
import com.twitter.home_mitwitterer.model.request.FollowingProduct
import com.twitter.home_mitwitterer.model.request.ForYouProduct
import com.twitter.home_mitwitterer.model.request.ListTweetsProduct
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProduct
import com.twitter.home_mitwitterer.model.request.SubscribedProduct
import com.twitter.home_mitwitterer.util.tweetypie.RequestFields
import com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.tweet_is_nsfw.IsNsfw
import com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.tweet_visibility_reason.VisibilityReason
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.{thriftscala => rtf}
import com.twitter.stitch.Stitch
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.tweetypie.{thriftscala => tp}
import com.twitter.util.logging.Logging
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetypieFeatureHydrator @Inject() (
  tweetypieStitchClient: TweetypieStitchClient,
  statsReceiver: StatsReceiver)
    etwittertends CandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Logging {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Tweetypie")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    EtwitterclusiveConversationAuthorIdFeature,
    InReplyToTweetIdFeature,
    IsHydratedFeature,
    IsNsfw,
    IsNsfwFeature,
    IsRetweetFeature,
    QuotedTweetDroppedFeature,
    QuotedTweetIdFeature,
    QuotedUserIdFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    TweetTetwittertFeature,
    TweetLanguageFeature,
    VisibilityReason
  )

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(IsHydratedFeature, false)
    .add(IsNsfw, None)
    .add(IsNsfwFeature, false)
    .add(QuotedTweetDroppedFeature, false)
    .add(TweetTetwittertFeature, None)
    .add(VisibilityReason, None)
    .build()

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    etwitteristingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    val safetyLevel = query.product match {
      case FollowingProduct => rtf.SafetyLevel.TimelineHomeLatest
      case ForYouProduct =>
        val inNetwork = etwitteristingFeatures.getOrElse(InNetworkFeature, true)
        if (inNetwork) rtf.SafetyLevel.TimelineHome else rtf.SafetyLevel.TimelineHomeRecommendations
      case ScoredTweetsProduct => rtf.SafetyLevel.TimelineHome
      case ListTweetsProduct => rtf.SafetyLevel.TimelineLists
      case SubscribedProduct => rtf.SafetyLevel.TimelineHomeSubscribed
      case unknown => throw new UnsupportedOperationEtwitterception(s"Unknown product: $unknown")
    }

    val tweetFieldsOptions = tp.GetTweetFieldsOptions(
      tweetIncludes = RequestFields.TweetTPHydrationFields,
      includeRetweetedTweet = true,
      includeQuotedTweet = true,
      visibilityPolicy = tp.TweetVisibilityPolicy.UserVisible,
      safetyLevel = Some(safetyLevel),
      forUserId = query.getOptionalUserId
    )

    val etwitterclusiveAuthorIdOpt =
      etwitteristingFeatures.getOrElse(EtwitterclusiveConversationAuthorIdFeature, None)

    tweetypieStitchClient.getTweetFields(tweetId = candidate.id, options = tweetFieldsOptions).map {
      case tp.GetTweetFieldsResult(_, tp.TweetFieldsResultState.Found(found), quoteOpt, _) =>
        val coreData = found.tweet.coreData
        val isNsfwAdmin = coreData.etwitterists(_.nsfwAdmin)
        val isNsfwUser = coreData.etwitterists(_.nsfwUser)

        val quotedTweetDropped = quoteOpt.etwitterists {
          case _: tp.TweetFieldsResultState.Filtered => true
          case _: tp.TweetFieldsResultState.NotFound => true
          case _ => false
        }
        val quotedTweetIsNsfw = quoteOpt.etwitterists {
          case quoteTweet: tp.TweetFieldsResultState.Found =>
            quoteTweet.found.tweet.coreData.etwitterists(data => data.nsfwAdmin || data.nsfwUser)
          case _ => false
        }

        val sourceTweetIsNsfw =
          found.retweetedTweet.etwitterists(_.coreData.etwitterists(data => data.nsfwAdmin || data.nsfwUser))

        val tweetTetwittert = coreData.map(_.tetwittert)
        val tweetLanguage = found.tweet.language.map(_.language)

        val tweetAuthorId = coreData.map(_.userId)
        val inReplyToTweetId = coreData.flatMap(_.reply.flatMap(_.inReplyToStatusId))
        val retweetedTweetId = found.retweetedTweet.map(_.id)
        val quotedTweetId = quoteOpt.flatMap {
          case quoteTweet: tp.TweetFieldsResultState.Found =>
            Some(quoteTweet.found.tweet.id)
          case _ => None
        }

        val retweetedTweetUserId = found.retweetedTweet.flatMap(_.coreData).map(_.userId)
        val quotedTweetUserId = quoteOpt.flatMap {
          case quoteTweet: tp.TweetFieldsResultState.Found =>
            quoteTweet.found.tweet.coreData.map(_.userId)
          case _ => None
        }

        val isNsfw = isNsfwAdmin || isNsfwUser || sourceTweetIsNsfw || quotedTweetIsNsfw

        FeatureMapBuilder()
          .add(AuthorIdFeature, tweetAuthorId)
          .add(EtwitterclusiveConversationAuthorIdFeature, etwitterclusiveAuthorIdOpt)
          .add(InReplyToTweetIdFeature, inReplyToTweetId)
          .add(IsHydratedFeature, true)
          .add(IsNsfw, Some(isNsfw))
          .add(IsNsfwFeature, isNsfw)
          .add(IsRetweetFeature, retweetedTweetId.isDefined)
          .add(QuotedTweetDroppedFeature, quotedTweetDropped)
          .add(QuotedTweetIdFeature, quotedTweetId)
          .add(QuotedUserIdFeature, quotedTweetUserId)
          .add(SourceTweetIdFeature, retweetedTweetId)
          .add(SourceUserIdFeature, retweetedTweetUserId)
          .add(TweetLanguageFeature, tweetLanguage)
          .add(TweetTetwittertFeature, tweetTetwittert)
          .add(VisibilityReason, found.suppressReason)
          .build()

      // If no tweet result found, return default and pre-etwitteristing features
      case _ =>
        DefaultFeatureMap ++ FeatureMapBuilder()
          .add(AuthorIdFeature, etwitteristingFeatures.getOrElse(AuthorIdFeature, None))
          .add(EtwitterclusiveConversationAuthorIdFeature, etwitterclusiveAuthorIdOpt)
          .add(InReplyToTweetIdFeature, etwitteristingFeatures.getOrElse(InReplyToTweetIdFeature, None))
          .add(IsRetweetFeature, etwitteristingFeatures.getOrElse(IsRetweetFeature, false))
          .add(QuotedTweetIdFeature, etwitteristingFeatures.getOrElse(QuotedTweetIdFeature, None))
          .add(QuotedUserIdFeature, etwitteristingFeatures.getOrElse(QuotedUserIdFeature, None))
          .add(SourceTweetIdFeature, etwitteristingFeatures.getOrElse(SourceTweetIdFeature, None))
          .add(SourceUserIdFeature, etwitteristingFeatures.getOrElse(SourceUserIdFeature, None))
          .add(TweetLanguageFeature, etwitteristingFeatures.getOrElse(TweetLanguageFeature, None))
          .build()
    }
  }
}
