package com.twitter.home_mitwitterer.product.for_you.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TweetTetwittertFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.{thriftscala => rtf}
import com.twitter.stitch.Stitch
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.tweetypie.{thriftscala => TP}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetPreviewTweetypieCandidateFeatureHydrator @Inject() (
  tweetypieStitchClient: TweetypieStitchClient)
    etwittertends CandidateFeatureHydrator[PipelineQuery, BaseTweetCandidate] {

  private val CoreTweetFields: Set[TP.TweetInclude] = Set[TP.TweetInclude](
    TP.TweetInclude.TweetFieldId(TP.Tweet.IdField.id),
    TP.TweetInclude.TweetFieldId(TP.Tweet.CoreDataField.id)
  )

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(TweetTetwittertFeature, None)
    .add(IsHydratedFeature, false)
    .add(AuthorIdFeature, None)
    .build()

  override val features: Set[Feature[_, _]] =
    Set(TweetTetwittertFeature, IsHydratedFeature, AuthorIdFeature)

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetPreviewTweetypie")

  override def apply(
    query: PipelineQuery,
    candidate: BaseTweetCandidate,
    etwitteristingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    tweetypieStitchClient
      .getTweetFields(
        tweetId = candidate.id,
        options = TP.GetTweetFieldsOptions(
          tweetIncludes = CoreTweetFields,
          includeRetweetedTweet = false,
          includeQuotedTweet = false,
          visibilityPolicy = TP.TweetVisibilityPolicy.UserVisible,
          safetyLevel = Some(rtf.SafetyLevel.TimelineHomeTweetPreview),
          forUserId = query.getOptionalUserId
        )
      ).map {
        case TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), quoteOpt, _) =>
          val tweetTetwittert = found.tweet.coreData.map(_.tetwittert)
          FeatureMapBuilder()
            .add(TweetTetwittertFeature, tweetTetwittert)
            .add(IsHydratedFeature, true)
            .add(AuthorIdFeature, found.tweet.coreData.map(_.userId))
            .build()
        // If no tweet result found, return default features
        case _ =>
          DefaultFeatureMap
      }

  }
}
