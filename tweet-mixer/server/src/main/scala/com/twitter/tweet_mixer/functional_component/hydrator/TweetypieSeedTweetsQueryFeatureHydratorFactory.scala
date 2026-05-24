package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweetypie.thriftscala.TweetVisibilityPolicy
import com.twitter.tweetypie.{thriftscala => TP}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.tweetypie.managed.TweetMixerSeedOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.managed.TweetMixerSeedTesOnTweetClientColumn
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableTweetEntityServiceMigration
import com.twitter.finagle.stats.StatsReceiver

object SeedsTextFeatures extends Feature[PipelineQuery, Option[Map[Long, String]]]

@Singleton
class TweetypieSeedTweetsQueryFeatureHydratorFactory @Inject() (
  tweetypieStitchClient: TweetypieStitchClient,
  statsReceiver: StatsReceiver,
  @Named("StratoClientWithModerateTimeout") stratoClient: Client) {

  private val tesFetcher = new TweetMixerSeedTesOnTweetClientColumn(stratoClient).fetcher
  private val tesDiffyFetcher = new TweetMixerSeedOnTweetClientColumn(stratoClient).fetcher

  def build(signalFn: PipelineQuery => Seq[Long]): TweetypieSeedTweetsQueryFeatureHydrator = {
    new TweetypieSeedTweetsQueryFeatureHydrator(
      tweetypieStitchClient,
      signalFn,
      statsReceiver,
      tesFetcher,
      tesDiffyFetcher)
  }
}

class TweetypieSeedTweetsQueryFeatureHydrator(
  tweetypieStitchClient: TweetypieStitchClient,
  signalFn: PipelineQuery => Seq[Long],
  statsReceiver: StatsReceiver,
  tesFetcher: Fetcher[Long, TP.GetTweetFieldsOptions, TP.GetTweetFieldsResult],
  tesDiffyFetcher: Fetcher[Long, TP.GetTweetFieldsOptions, TP.GetTweetFieldsResult])
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TweetypieSeedTweets")

  override def features: Set[Feature[_, _]] = Set(SeedsTextFeatures, AuthorIdFeature)

  private val TweetLinkUrl = "".r

  private val tesSeedTweetsFoundCounter = statsReceiver.counter("TesSeedTweetsFound")
  private val tesSeedTweetsNotFoundCounter = statsReceiver.counter("TesSeedTweetsNotFound")
  private val tweetypieSeedTweetsFoundCounter = statsReceiver.counter("TweetypieSeedTweetsFound")
  private val tweetypieSeedTweetsNotFoundCounter =
    statsReceiver.counter("TweetypieSeedTweetsNotFound")

  private val getTweetFieldsOption = TP.GetTweetFieldsOptions(
    tweetIncludes = Set(TP.TweetInclude.TweetFieldId(TP.Tweet.CoreDataField.id)),
    includeRetweetedTweet = false,
    includeQuotedTweet = false,
    visibilityPolicy = TweetVisibilityPolicy.UserVisible,
    safetyLevel = None
  )

  private def readFromTweetypie(tweetIds: Seq[Long]): Stitch[FeatureMap] = {
    Stitch
      .traverse(tweetIds) { tweetId =>
        tweetypieStitchClient
          .getTweetFields(
            tweetId = tweetId,
            options = getTweetFieldsOption,
          )
          .map {
            case TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), _, _) =>
              tweetypieSeedTweetsFoundCounter.incr()
              val textOpt = found.tweet.coreData.map(_.text)
              val authorIdOpt = found.tweet.coreData.map(_.userId)
              (tweetId, textOpt, authorIdOpt)
            case _ =>
              tweetypieSeedTweetsNotFoundCounter.incr()
              (tweetId, None, None)
          }
      }.map { idTextAndAuthor: Seq[(Long, Option[String], Option[Long])] =>
        val idToText = idTextAndAuthor.collect {
          case (id, Some(text), _) => (id, TweetLinkUrl.replaceAllIn(text, "").trim)
        }.toMap

        val authorIds = idTextAndAuthor.collect {
          case (_, _, Some(authorId)) => authorId
        }

        FeatureMap(
          SeedsTextFeatures,
          Some(idToText),
          AuthorIdFeature,
          authorIds.headOption
        )
      }
  }

  private def readFromTes(tweetIds: Seq[Long]): Stitch[FeatureMap] = {
    Stitch
      .traverse(tweetIds) { tweetId =>
        tesDiffyFetcher
          .fetch(tweetId, getTweetFieldsOption).map(_.v).map {
            case Some(TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), _, _)) =>
              tesSeedTweetsFoundCounter.incr()
              val textOpt = found.tweet.coreData.map(_.text)
              val authorIdOpt = found.tweet.coreData.map(_.userId)
              (tweetId, textOpt, authorIdOpt)
            case _ =>
              tesSeedTweetsNotFoundCounter.incr()
              (tweetId, None, None)
          }
      }.map { idTextAndAuthor: Seq[(Long, Option[String], Option[Long])] =>
        val idToText = idTextAndAuthor.collect {
          case (id, Some(text), _) => (id, TweetLinkUrl.replaceAllIn(text, "").trim)
        }.toMap

        val authorIds = idTextAndAuthor.collect {
          case (_, _, Some(authorId)) => authorId
        }

        FeatureMap(
          SeedsTextFeatures,
          Some(idToText),
          AuthorIdFeature,
          authorIds.headOption
        )
      }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds = signalFn(query)

    if (query.params(EnableTweetEntityServiceMigration)) {
      readFromTes(tweetIds)
    } else {
      readFromTweetypie(tweetIds)
    }

  }
}
