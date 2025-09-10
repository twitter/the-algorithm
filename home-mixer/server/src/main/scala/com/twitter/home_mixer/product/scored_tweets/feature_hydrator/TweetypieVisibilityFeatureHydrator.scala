package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AncestorsFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsHydratedFeature
import com.twitter.home_mixer.model.HomeFeatures.OonNsfwFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetLanguageFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTweetEntityServiceVisibilityMigrationParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_is_nsfw.IsNsfw
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.tweet_visibility_reason.VisibilityReason
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.{thriftscala => rtf}
import com.twitter.stitch.Stitch
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.tweetypie.managed.HomeMixerOnTweetClientColumn
import com.twitter.tweetypie.{thriftscala => tp}
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

case class TweetypieVisibilityFeatures(
  communityId: Option[Long],
  isHydrated: Boolean,
  isNsfw: Boolean,
  oonNsfw: Boolean,
  tweetText: Option[String],
  tweetLanguage: Option[String],
  visibilityReason: Option[rtf.FilteredReason])

@Singleton
class TweetypieVisibilityFeatureHydrator @Inject() (
  tweetypieStitchClient: TweetypieStitchClient,
  statsReceiver: StatsReceiver,
  @Named(BatchedStratoClientWithLongTimeout) stratoClient: Client)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Logging {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetypieVisibility")

  private val tweetypieTweetsVisibilityFoundCounter =
    statsReceiver.counter("VisibilityFeatureTweetypieTweetsFound")
  private val tweetypieTweetsVisibilityNotFoundCounter =
    statsReceiver.counter("VisibilityFeatureTweetypieTweetsNotFound")
  private val tesTweetsVisibilityFoundCounter =
    statsReceiver.counter("VisibilityFeatureTesTweetsFound")
  private val tesTweetsVisibilityNotFoundCounter =
    statsReceiver.counter("VisibilityFeatureTesTweetsNotFound")

  override val features: Set[Feature[_, _]] = Set(
    CommunityIdFeature,
    IsHydratedFeature,
    IsNsfw,
    OonNsfwFeature,
    TweetTextFeature,
    TweetLanguageFeature,
    VisibilityReason
  )

  private val HydrationFields: Set[tp.TweetInclude] = Set(
    tp.TweetInclude.TweetFieldId(tp.Tweet.CommunitiesField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.CoreDataField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.IdField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.LanguageField.id),
    tp.TweetInclude.TweetFieldId(tp.Tweet.QuotedTweetField.id)
  )

  private val DefaultTweetFieldsOptions = tp.GetTweetFieldsOptions(
    tweetIncludes = HydrationFields,
    includeRetweetedTweet = true,
    includeQuotedTweet = true,
    visibilityPolicy = tp.TweetVisibilityPolicy.UserVisible,
    safetyLevel = Some(rtf.SafetyLevel.TimelineHome)
  )

  private val OutOfNetworkTweetFieldsOptions =
    DefaultTweetFieldsOptions.copy(safetyLevel = Some(rtf.SafetyLevel.TimelineHomeRecommendations))

  private val DefaultTweetypieVisibilityFeatures = TweetypieVisibilityFeatures(
    communityId = None,
    isHydrated = false,
    isNsfw = false,
    oonNsfw = false,
    tweetLanguage = None,
    tweetText = None,
    visibilityReason = None
  )

  private def buildFeatureMap(
    gtfResult: Stitch[tp.GetTweetFieldsResult],
    inNetwork: Boolean,
    fromTes: Boolean,
    tweetId: Long
  ): Stitch[(Long, TweetypieVisibilityFeatures)] = {
    gtfResult.map {
      case tp.GetTweetFieldsResult(_, tp.TweetFieldsResultState.Found(found), quote, _) =>
        if (fromTes) tesTweetsVisibilityFoundCounter.incr()
        else tweetypieTweetsVisibilityFoundCounter.incr()

        val coreData = found.tweet.coreData

        val isNsfwAdmin = coreData.exists(_.nsfwAdmin)
        val isNsfwUser = coreData.exists(_.nsfwUser)
        val sourceTweetIsNsfw =
          found.retweetedTweet.exists(_.coreData.exists(data => data.nsfwAdmin || data.nsfwUser))

        val quotedTweetDropped = quote.exists {
          case _: tp.TweetFieldsResultState.Filtered => true
          case _: tp.TweetFieldsResultState.NotFound => true
          case _ => false
        }
        val quotedTweetIsNsfw = quote.exists {
          case quoteTweet: tp.TweetFieldsResultState.Found =>
            quoteTweet.found.tweet.coreData.exists(data => data.nsfwAdmin || data.nsfwUser)
          case _ => false
        }

        val isNsfw = isNsfwAdmin || isNsfwUser || sourceTweetIsNsfw || quotedTweetIsNsfw

        val communityId = found.tweet.communities.flatMap(_.communityIds.headOption)

        tweetId -> TweetypieVisibilityFeatures(
          communityId = communityId,
          // Since this tweet was Found, it is not dropped, so we only need to check if there
          // was a dropped quoted tweet.
          isHydrated = !quotedTweetDropped,
          isNsfw = isNsfw,
          oonNsfw = !inNetwork && isNsfw,
          tweetLanguage = found.tweet.language.map(_.language),
          tweetText = coreData.map(_.text),
          visibilityReason = found.suppressReason
        )

      case _ =>
        if (fromTes) tesTweetsVisibilityNotFoundCounter.incr()
        else tweetypieTweetsVisibilityNotFoundCounter.incr()
        tweetId -> DefaultTweetypieVisibilityFeatures
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {

    try {
      val followedUserIds = query.features.get.getOrElse(SGSFollowedUsersFeature, Seq.empty).toSet
      val inNetworkTweetFieldsOptions =
        DefaultTweetFieldsOptions.copy(forUserId = Some(query.getRequiredUserId))

      val outOfNetworkTweetFieldsOptions =
        OutOfNetworkTweetFieldsOptions.copy(forUserId = Some(query.getRequiredUserId))

      val resultsStitch = Stitch
        .collect {
          candidates
            .flatMap { candidate =>
              val ancestors = candidate.features.getOrElse(AncestorsFeature, Seq.empty).map {
                ancestor =>
                  val inNetwork =
                    ancestor.userId == query.getRequiredUserId ||
                      followedUserIds.contains(ancestor.userId)
                  (ancestor.tweetId, ancestor.userId, inNetwork)
              }

              val authorId = candidate.features.get(AuthorIdFeature).get
              val inNetwork =
                authorId == query.getRequiredUserId || followedUserIds.contains(authorId)

              Seq((candidate.candidate.id, authorId, inNetwork)) ++ ancestors.headOption ++
                ancestors.lastOption
            }.distinct.map {
              case (tweetId, _, inNetwork) =>
                val gtfOptions =
                  if (inNetwork) inNetworkTweetFieldsOptions else outOfNetworkTweetFieldsOptions

                try {
                  if (query.params(EnableTweetEntityServiceVisibilityMigrationParam)) {
                    val fetcher = new HomeMixerOnTweetClientColumn(stratoClient).fetcher
                    val response = fetcher.fetch(tweetId, gtfOptions).map(_.v)
                    response.flatMap {
                      case Some(result) =>
                        buildFeatureMap(Stitch.value(result), inNetwork, fromTes = true, tweetId)
                      case None =>
                        tesTweetsVisibilityNotFoundCounter.incr()
                        Stitch.value(tweetId -> DefaultTweetypieVisibilityFeatures)
                    }
                  } else {
                    buildFeatureMap(
                      tweetypieStitchClient.getTweetFields(tweetId, gtfOptions),
                      inNetwork,
                      fromTes = false,
                      tweetId
                    )
                  }
                } catch {
                  case e: Exception =>
                    error(s"2 - Error fetching tweetypie visibility: $e")
                    Stitch.value(tweetId -> DefaultTweetypieVisibilityFeatures)
                }
            }
        }.onFailure {
          case e: Exception =>
            error(s"1 - Error fetching tweetypie visibility: $e")
        }

      resultsStitch.map { results =>
        val resultsMap = results.toMap
        candidates.map { candidate =>
          val ancestors = candidate.features.getOrElse(AncestorsFeature, Seq.empty)
          val ancestorTweetypieVisibilityFeatures =
            (ancestors.headOption ++ ancestors.lastOption).toSeq.distinct.map { ancestor =>
              resultsMap.getOrElse(ancestor.tweetId, DefaultTweetypieVisibilityFeatures)
            }

          val ancestorsHydrated =
            ancestorTweetypieVisibilityFeatures.map(_.isHydrated).forall(identity)
          val ancestorsOonNsfw = ancestorTweetypieVisibilityFeatures.map(_.oonNsfw).exists(identity)
          val ancestorsNsfw = ancestorTweetypieVisibilityFeatures.map(_.isNsfw).exists(identity)

          val tweetypieVisibilityFeatures =
            resultsMap.getOrElse(candidate.candidate.id, DefaultTweetypieVisibilityFeatures)

          FeatureMapBuilder()
            .add(CommunityIdFeature, tweetypieVisibilityFeatures.communityId)
            .add(IsHydratedFeature, tweetypieVisibilityFeatures.isHydrated && ancestorsHydrated)
            .add(IsNsfw, Some(tweetypieVisibilityFeatures.isNsfw || ancestorsNsfw))
            .add(OonNsfwFeature, tweetypieVisibilityFeatures.oonNsfw || ancestorsOonNsfw)
            .add(TweetLanguageFeature, tweetypieVisibilityFeatures.tweetLanguage)
            .add(TweetTextFeature, tweetypieVisibilityFeatures.tweetText)
            .add(VisibilityReason, tweetypieVisibilityFeatures.visibilityReason)
            .build()
        }
      }
    } catch {
      case e: Exception =>
        error(s"3 - Error fetching tweetypie visibility: $e")
        Stitch.value(Seq.empty)
    }
  }
}
