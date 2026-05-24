package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.mediaservices.commons.tweetmedia.thriftscala.MediaInfo
import com.twitter.mediaservices.commons.tweetmedia.thriftscala.MediaSizeType
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.spam.rtf.thriftscala.SafetyLevel
import com.twitter.stitch.Stitch
import com.twitter.stitch.cache.AsyncValueCache
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.strato.client.Fetcher
import com.twitter.tweet_mixer.feature.TweetInfoFeatures._
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.TweetInfoFeatures
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MinVideoDurationParam
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import com.twitter.tweetypie.thriftscala.TweetVisibilityPolicy
import com.twitter.tweetypie.{thriftscala => TP}
import com.twitter.mediaservices.commons.{thriftscala => mc}
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.tweetypie.managed.TweetMixerTesOnTweetClientColumn
import com.twitter.tweet_mixer.feature.MediaIdFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableTweetEntityServiceMigrationDiffy
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMaxAspectRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMaxDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinAspectRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinHeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LongFormMinWidthParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMaxAspectRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMaxDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinAspectRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinDurationParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinHeightParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ShortFormMinWidthParam

object TweetypieCandidateFeatureHydrator {
  import scala.language.implicitConversions

  val CoreTweetFields: Set[TP.TweetInclude] = Set[TP.TweetInclude](
    TP.TweetInclude.TweetFieldId(TP.Tweet.CoreDataField.id),
    TP.TweetInclude.TweetFieldId(TP.Tweet.MediaField.id),
    TP.TweetInclude.TweetFieldId(TP.Tweet.MediaKeysField.id),
    TP.TweetInclude.TweetFieldId(TP.Tweet.UrlsField.id)
  )

  val DefaultFeatureMap =
    FeatureMap(TweetBooleanInfoFeature, None, AuthorIdFeature, None, MediaIdFeature, None)

  implicit def bool2int(b: Boolean) = if (b) 1 else 0

  private val TTLSeconds = Utils.randomizedTTL(10 * 60)
}

class TweetypieCandidateFeatureHydrator(
  tweetypieStitchClient: TweetypieStitchClient,
  safetyLevelPredicate: PipelineQuery => SafetyLevel,
  memcache: MemcacheStitchClient,
  inMemoryCache: AsyncValueCache[java.lang.Long, Option[(Int, Long, Long)]],
  statsReceiver: StatsReceiver,
  stratoClient: Client)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  import TweetypieCandidateFeatureHydrator._

  override val features: Set[Feature[_, _]] = Set(
    TweetBooleanInfoFeature,
    AuthorIdFeature,
    MediaIdFeature
  )

  private lazy val tesFetcher = new TweetMixerTesOnTweetClientColumn(stratoClient).fetcher

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Tweetypie")

  private val inMemoryCacheRequestCounter = statsReceiver.counter("InMemCacheRequests")
  private val inMemoryCacheMissCounter = statsReceiver.counter("InMemCacheMisses")

  private def getCacheKey(tweetId: Long): String = {
    "Tweetypie:" + tweetId.toString
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val candidatesBatched = candidates.grouped(1000)

    val featureMapsBatched = candidatesBatched.map { candidatesBatch =>
      OffloadFuturePools.offloadStitch {
        Stitch.traverse(candidatesBatch) { candidate =>
          val fromInNetwork = candidate.features.getOrElse(FromInNetworkSourceFeature, false)
          if (fromInNetwork) {
            val featureMap = {
              FeatureMap(
                TweetBooleanInfoFeature,
                None,
                AuthorIdFeature,
                candidate.features.getOrElse(AuthorIdFeature, None),
                MediaIdFeature,
                None
              )
            }
            Stitch.value(featureMap)
          } else {
            inMemoryCacheRequestCounter.incr()
            val tweetId = candidate.candidate.id
            inMemoryCache
              .get(tweetId)
              .getOrElse(getTweetInfo(query, tweetId))
              .map(createFeatureMap(_))
              .handle { case _ => DefaultFeatureMap }
          }
        }
      }
    }
    Stitch.collect(featureMapsBatched.toSeq).map(_.flatten)
  }

  private def getTweetInfo(
    query: PipelineQuery,
    tweetId: Long
  ): Stitch[Option[(Int, Long, Long)]] = {
    inMemoryCacheMissCounter.incr()
    memcache
      .get(getCacheKey(tweetId))
      .flatMap {
        case Some(value) =>
          Stitch
            .value(Transformers.deserializeTpCacheOption(value))
            .applyEffect { result =>
              Stitch.async {
                inMemoryCache.set(tweetId, Stitch.value(result))
              }
            }
        case None =>
          if (query.params(EnableTweetEntityServiceMigrationDiffy)) {
            Stitch.None.applyEffect { _ =>
              Stitch.async(readFromTweetEntityService(query, tweetId, tesFetcher))
            }
          } else {
            Stitch.None.applyEffect { _ =>
              Stitch.async(readFromTweetypie(query, tweetId))
            }
          }
      }
  }

  private def createFeatureMap(tweetypieResponse: Option[(Int, Long, Long)]): FeatureMap = {
    val tweetBooleanInfo = tweetypieResponse.map(_._1)
    val authorId = tweetypieResponse.map(_._2)
    val mediaId = tweetypieResponse.map(_._3)
    FeatureMap(
      TweetBooleanInfoFeature,
      tweetBooleanInfo,
      AuthorIdFeature,
      authorId,
      MediaIdFeature,
      mediaId
    )
  }

  private def processTweet(query: PipelineQuery, tweet: TP.Tweet): Option[(Int, Long, Long)] = {
    val coreData = tweet.coreData
    val isReply = coreData.exists(_.reply.nonEmpty)
    val isRetweet = coreData.exists(_.share.isDefined)

    val hasMultipleMedia =
      tweet.mediaKeys.exists(_.map(_.mediaCategory).size > 1)

    val durationInfo = tweet.media.flatMap(_.flatMap {
      _.mediaInfo match {
        case Some(MediaInfo.VideoInfo(info)) =>
          Some((info.durationMillis + 999) / 1000) // video playtime always round up
        case _ => None
      }
    }.headOption)

    val hasImage =
      tweet.mediaKeys.exists(_.exists(_.mediaCategory == mc.MediaCategory.TweetImage))

    val hasVideo = durationInfo.isDefined

    val isLongVideo = durationInfo.exists(_ > query.params(MinVideoDurationParam).inSeconds)

    val mediaDimensionsOpt =
      tweet.media.flatMap(
        _.headOption.flatMap(_.sizes
          .find(_.sizeType == MediaSizeType.Orig).map(size => (size.width, size.height))))

    val mediaWidth = mediaDimensionsOpt.map(_._1).getOrElse(1)
    val mediaHeight = mediaDimensionsOpt.map(_._2).getOrElse(1)
    val aspectRatio: Double = mediaWidth.toDouble / math.max(1.0, mediaHeight.toDouble)
    // high resolution media's width is always greater than 480px and height is always greater than 480px
    val isHighMediaResolution = mediaHeight > 480 && mediaWidth > 480

    val isLandscapeVideo = mediaWidth >= mediaHeight

    val hasUrl = tweet.urls.exists(_.nonEmpty)

    val isLongFormVideo =
      hasVideo && aspectRatio >= query.params(LongFormMinAspectRatioParam) && aspectRatio <= query
        .params(LongFormMaxAspectRatioParam) && durationInfo.exists(
        _ >= query.params(LongFormMinDurationParam).inSeconds) && durationInfo.exists(
        _ <= query.params(LongFormMaxDurationParam).inSeconds) && mediaWidth >= query.params(
        LongFormMinWidthParam) && mediaHeight >= query.params(LongFormMinHeightParam)

    val isShortFormVideo =
      hasVideo && aspectRatio >= query.params(ShortFormMinAspectRatioParam) && aspectRatio <= query
        .params(ShortFormMaxAspectRatioParam) && durationInfo.exists(
        _ >= query.params(ShortFormMinDurationParam).inSeconds) && durationInfo.exists(
        _ <= query.params(ShortFormMaxDurationParam).inSeconds) && mediaWidth >= query.params(
        ShortFormMinWidthParam) && mediaHeight >= query.params(ShortFormMinHeightParam)

    val tweetBooleanInfo = List(
      IsReply -> isReply,
      HasMultipleMedia -> hasMultipleMedia,
      HasVideo -> hasVideo,
      IsHighMediaResolution -> isHighMediaResolution,
      HasUrl -> hasUrl,
      IsLongVideo -> isLongVideo,
      IsLandscapeVideo -> isLandscapeVideo,
      IsRetweet -> isRetweet,
      HasImage -> hasImage,
      IsShortFormVideo -> isShortFormVideo,
      IsLongFormVideo -> isLongFormVideo
    ).foldLeft(0)((acc, feature) =>
      if (feature._2) TweetInfoFeatures.setFeature(feature._1, acc) else acc)

    val authorIdOpt = coreData.map(_.userId)
    val mediaId = tweet.media.flatMap(_.headOption).map(_.mediaId).getOrElse(0L)

    authorIdOpt.map((tweetBooleanInfo, _, mediaId))
  }

  private def readFromTweetEntityService(
    query: PipelineQuery,
    tweetId: Long,
    fetcher: Fetcher[Long, Unit, TP.Tweet]
  ): Stitch[Option[(Int, Long, Long)]] = {

    val fetchResult: Stitch[Option[TP.Tweet]] = fetcher.fetch(tweetId, ()).map(_.v)

    fetchResult
      .flatMap {
        case Some(tweet) =>
          val tweetData = processTweet(query, tweet)
          val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(tweetData))
          val memCacheSet = memcache.set(
            getCacheKey(tweetId),
            Transformers.serializeTpCacheOption(tweetData),
            TTLSeconds
          )
          Stitch.join(inMemoryCacheSet, memCacheSet)
        case _ =>
          val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(None))
          val memCacheSet = memcache.set(
            getCacheKey(tweetId),
            Transformers.serializeTpCacheOption(None),
            TTLSeconds
          )
          Stitch.join(inMemoryCacheSet, memCacheSet)
      }.map { case (tweetResponse, _) => tweetResponse }
  }

  private def readFromTweetypie(
    query: PipelineQuery,
    tweetId: Long
  ): Stitch[Option[(Int, Long, Long)]] = {
    tweetypieStitchClient
      .getTweetFields(
        tweetId = tweetId,
        options = TP.GetTweetFieldsOptions(
          tweetIncludes = CoreTweetFields,
          includeRetweetedTweet = false,
          includeQuotedTweet = false,
          visibilityPolicy = TweetVisibilityPolicy.UserVisible,
          safetyLevel = Some(safetyLevelPredicate(query))
        )
      )
      .flatMap {
        case TP.GetTweetFieldsResult(_, TP.TweetFieldsResultState.Found(found), _, _) =>
          val tweet = found.tweet
          val tweetypieResponse = processTweet(query, tweet)
          val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(tweetypieResponse))
          val memCacheSet = memcache.set(
            getCacheKey(tweetId),
            Transformers.serializeTpCacheOption(tweetypieResponse),
            TTLSeconds)
          Stitch.join(inMemoryCacheSet, memCacheSet)
        case _ =>
          val inMemoryCacheSet = inMemoryCache.set(tweetId, Stitch.value(None))
          val memCacheSet = memcache.set(
            getCacheKey(tweetId),
            Transformers.serializeTpCacheOption(None),
            TTLSeconds)
          Stitch.join(inMemoryCacheSet, memCacheSet)
      }.map { case (tweetypieResponse, _) => tweetypieResponse }
  }
}
