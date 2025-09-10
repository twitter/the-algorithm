package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.ArticleIdFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTweetEntityServiceMigrationParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_visibility_reason.VisibilityReason
import com.twitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.{thriftscala => rtf}
import com.twitter.stitch.Stitch
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.tweetypie.managed.HomeMixerOnTweetClientColumn
import com.twitter.tweetypie.{thriftscala => TP}
import javax.inject.Named
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.finagle.stats.StatsReceiver

@Singleton
class TweetPreviewTweetypieCandidateFeatureHydrator @Inject() (
  statsReceiver: StatsReceiver,
  tweetypieStitchClient: TweetypieStitchClient,
  @Named(BatchedStratoClientWithLongTimeout) stratoClient: Client)
    extends CandidateFeatureHydrator[PipelineQuery, BaseTweetCandidate] {

  private val tweetypieTweetsFoundCounter =
    statsReceiver.counter("TweetPreviewTweetypieTweetsFound")
  private val tweetypieTweetsNotFoundCounter =
    statsReceiver.counter("TweetPreviewTweetypieTweetsNotFound")
  private val tesTweetsFoundCounter =
    statsReceiver.counter("TweetPreviewTesTweetsFound")
  private val tesTweetsNotFoundCounter =
    statsReceiver.counter("TweetPreviewTesTweetsNotFound")

  private val CoreTweetFields: Set[TP.TweetInclude] = Set[TP.TweetInclude](
    TP.TweetInclude.TweetFieldId(TP.Tweet.IdField.id),
    TP.TweetInclude.TweetFieldId(TP.Tweet.CoreDataField.id),
    TP.TweetInclude.TweetFieldId(TP.Tweet.ArticleField.id),
  )

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(TweetTextFeature, None)
    .add(IsHydratedFeature, false)
    .add(AuthorIdFeature, None)
    .add(VisibilityReason, None)
    .add(ArticleIdFeature, None)
    .build()

  override val features: Set[Feature[_, _]] =
    Set(TweetTextFeature, IsHydratedFeature, AuthorIdFeature, VisibilityReason, ArticleIdFeature)

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetPreviewTweetypie")

  private def buildFeatureMap(
    gtfResult: Stitch[TP.GetTweetFieldsResult],
    isTes: Boolean
  ): Stitch[FeatureMap] = {
    gtfResult.map {
      case TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), _, _) =>
        if (isTes) tesTweetsFoundCounter.incr()
        else tweetypieTweetsFoundCounter.incr()

        val tweetText = found.tweet.coreData.map(_.text)
        FeatureMapBuilder(sizeHint = 5)
          .add(TweetTextFeature, tweetText)
          .add(IsHydratedFeature, true)
          .add(AuthorIdFeature, found.tweet.coreData.map(_.userId))
          .add(VisibilityReason, found.suppressReason)
          .add(ArticleIdFeature, found.tweet.article.map(_.id))
          .build()
      case _ =>
        if (isTes) tesTweetsNotFoundCounter.incr()
        else tweetypieTweetsNotFoundCounter.incr()
        DefaultFeatureMap
    }
  }

  override def apply(
    query: PipelineQuery,
    candidate: BaseTweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    val getTweetFieldsOptions = TP.GetTweetFieldsOptions(
      tweetIncludes = CoreTweetFields,
      includeRetweetedTweet = false,
      includeQuotedTweet = false,
      visibilityPolicy = TP.TweetVisibilityPolicy.UserVisible,
      safetyLevel = Some(rtf.SafetyLevel.TimelineHomeTweetPreview),
      forUserId = query.getOptionalUserId
    )

    if (query.params(EnableTweetEntityServiceMigrationParam)) {
      val fetcher = new HomeMixerOnTweetClientColumn(stratoClient).fetcher
      fetcher
        .fetch(
          candidate.id,
          getTweetFieldsOptions
        ).map(_.v).flatMap {
          case Some(result) => buildFeatureMap(Stitch.value(result), true)
          case None =>
            tesTweetsNotFoundCounter.incr()
            Stitch.value(DefaultFeatureMap)
        }
    } else {
      val gtfResult: Stitch[TP.GetTweetFieldsResult] = tweetypieStitchClient
        .getTweetFields(
          tweetId = candidate.id,
          options = getTweetFieldsOptions
        )
      buildFeatureMap(gtfResult, false)
    }
  }
}
