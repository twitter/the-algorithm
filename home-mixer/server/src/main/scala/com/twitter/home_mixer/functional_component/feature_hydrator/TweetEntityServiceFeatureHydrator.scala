package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps.RichDuration
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.FirstMediaIdFeature
import com.twitter.home_mixer.model.HomeFeatures.HasImageFeature
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsArticleFeature
import com.twitter.home_mixer.model.HomeFeatures.IsInReplyToReplyOrDirectedFeature
import com.twitter.home_mixer.model.HomeFeatures.IsInReplyToRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.MentionScreenNameFeature
import com.twitter.home_mixer.model.HomeFeatures.MentionUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.OriginalTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TesBatchedStratoClient
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TweetypieStaticEntitiesCache
import com.twitter.home_mixer.util.tweetypie.content.TweetMediaFeaturesExtractor
import com.twitter.mediaservices.commons.thriftscala.MediaKey
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationIdFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.TtlCache
import com.twitter.servo.keyvalue.KeyValueResult
import com.twitter.stitch.Arrow
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.client.CommunityId
import com.twitter.strato.client.UserId
import com.twitter.strato.generated.client.tweetypie.federated.ArticleOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.CommunityOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.ContextualQuoteTweetRefOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.DirectedAtUserOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.ExclusiveTweetControlOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.MediaKeysOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.MentionsOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.NarrowcastPlaceOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.PureCoreDataOnTweetClientColumn
import com.twitter.strato.generated.client.tweetypie.federated.UrlsOnTweetClientColumn
import com.twitter.strato.graphql.contextual_refs.thriftscala.ContextualTweetRef
import com.twitter.tweetypie.{thriftscala => tp}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TweetEntityServiceFeatureHydrator @Inject() (
  @Named(TweetypieStaticEntitiesCache) cacheClient: TtlCache[Long, tp.Tweet],
  @Named(TesBatchedStratoClient) stratoClient: Client,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {
  import TweetEntityServiceFeatureHydrator._

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(IdentifierName)

  override val features: Set[Feature[_, _]] = Features

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val pureCoreDataNonEmptyCounter = scopedStatsReceiver.counter("pureCoreDataNonEmpty")
  private val pureCoreDataEmptyCounter = scopedStatsReceiver.counter("pureCoreDataEmpty")

  private lazy val getTESRecord: Arrow[Long, TESRecord] = getTESRecordArrow(stratoClient)

  private lazy val getFromTES: Arrow[Seq[Long], Map[Long, tp.Tweet]] = Arrow
    .sequence(getTESRecord)
    .map { record =>
      record
        .map(_.getTweetOpt).filter { tweetOpt =>
          if (tweetOpt.nonEmpty) pureCoreDataNonEmptyCounter.incr()
          else pureCoreDataEmptyCounter.incr()
          tweetOpt.nonEmpty
        }.map(tweet => (tweet.get.id, tweet.get)).toMap
    }

  private lazy val getHydratedTweetMapWithCacheWriteBack: Arrow[
    KeyValueResult[Long, tp.Tweet],
    Map[Long, tp.Tweet]
  ] = Arrow
    .zipWithArg(
      Arrow
        .identity[KeyValueResult[Long, tp.Tweet]]
        .andThen(getFromTES.contramap[KeyValueResult[Long, tp.Tweet]](kv => kv.notFound.toSeq))
    ).map {
      case (fromCache, fromTES) =>
        fromTES.map(kv => cacheClient.set(kv._1, kv._2, CacheTTL))
        fromCache.found ++ fromTES
    }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val tweetIds: Seq[Long] = candidates.map(_.candidate.id)
    val inReplyToIds = candidates.flatMap(_.features.getOrElse(InReplyToTweetIdFeature, None))
    val idsToHydrate = (tweetIds ++ inReplyToIds).distinct

    Stitch
      .callFuture(cacheClient.get(idsToHydrate))
      .flatMap(cacheResult => getHydratedTweetMapWithCacheWriteBack(cacheResult))
      .map { tweetMap: Map[Long, tp.Tweet] =>
        candidates.map { candidate =>
          tweetMap
            .get(candidate.candidate.id).map { tweet =>
              getFeatureMapFromTweet(
                tweet,
                candidate.features.getOrElse(InReplyToTweetIdFeature, None).flatMap(tweetMap.get)
              )
            }.getOrElse(DefaultFeatureMap)
        }
      }
  }
}

object TweetEntityServiceFeatureHydrator {
  private val IdentifierName = "TweetEntityService"
  private val CacheTTL = 48.hours

  private val Features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    CommunityIdFeature,
    DirectedAtUserIdFeature,
    ExclusiveConversationAuthorIdFeature,
    FirstMediaIdFeature,
    HasImageFeature,
    HasVideoFeature,
    IsArticleFeature,
    InReplyToTweetIdFeature,
    InReplyToUserIdFeature,
    IsInReplyToReplyOrDirectedFeature,
    IsInReplyToRetweetFeature,
    IsRetweetFeature,
    LocationIdFeature,
    MentionScreenNameFeature,
    MentionUserIdFeature,
    QuotedTweetIdFeature,
    OriginalTweetIdFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
    TweetMediaIdsFeature,
    TweetUrlsFeature,
  )

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(AuthorIdFeature, None)
    .add(DirectedAtUserIdFeature, None)
    .add(ExclusiveConversationAuthorIdFeature, None)
    .add(HasImageFeature, false)
    .add(HasVideoFeature, false)
    .add(IsArticleFeature, false)
    .add(InReplyToTweetIdFeature, None)
    .add(InReplyToUserIdFeature, None)
    .add(IsInReplyToReplyOrDirectedFeature, false)
    .add(IsInReplyToRetweetFeature, false)
    .add(IsRetweetFeature, false)
    .add(LocationIdFeature, None)
    .add(MentionScreenNameFeature, Seq.empty)
    .add(MentionUserIdFeature, Seq.empty)
    .add(QuotedTweetIdFeature, None)
    .add(OriginalTweetIdFeature, None)
    .add(SourceTweetIdFeature, None)
    .add(SourceUserIdFeature, None)
    .add(CommunityIdFeature, None)
    .add(TweetMediaIdsFeature, Seq.empty)
    .add(FirstMediaIdFeature, None)
    .add(TweetUrlsFeature, Seq.empty)
    .build()

  private def getFeatureMapFromTweet(
    tweet: tp.Tweet,
    inReplyToTweet: Option[tp.Tweet]
  ): FeatureMap = {
    val coreData = tweet.coreData
    val quotedTweet = tweet.quotedTweet
    val mentions = tweet.mentions.getOrElse(Seq.empty)
    val share = coreData.flatMap(_.share)
    val reply = coreData.flatMap(_.reply)
    val urls = tweet.urls.map(_.flatMap(_.expanded)).toSeq.flatten

    val (isInReplyToReplyOrDirected, isInReplyToRetweet) = inReplyToTweet
      .map { tweet =>
        (
          // when inReplyToUserId exists, it can be a reply or a directedAt tweet,
          // depending on whether inReplyToTweetId exists
          tweet.coreData.flatMap(_.reply).map(_.inReplyToUserId).isDefined,
          tweet.coreData.flatMap(_.share).isDefined
        )
      }.getOrElse((false, false))

    // There are cases where the inReplyToUserId exists while inReplyToStatusId does not.
    // They're usually directed tweets that are not replies.
    val inReplyToTweetId = reply.flatMap(_.inReplyToStatusId)
    val inReplyToUserId = if (inReplyToTweetId.nonEmpty) reply.map(_.inReplyToUserId) else None
    val tweetMediaIds = TweetMediaFeaturesExtractor.getMediaIds(tweet)

    FeatureMapBuilder()
      .add(AuthorIdFeature, coreData.map(_.userId))
      .add(DirectedAtUserIdFeature, coreData.flatMap(_.directedAtUser.map(_.userId)))
      .add(
        ExclusiveConversationAuthorIdFeature,
        tweet.exclusiveTweetControl.map(_.conversationAuthorId))
      .add(HasImageFeature, TweetMediaFeaturesExtractor.hasImage(tweet))
      .add(HasVideoFeature, TweetMediaFeaturesExtractor.hasVideo(tweet))
      .add(IsArticleFeature, tweet.article.isDefined)
      .add(InReplyToTweetIdFeature, inReplyToTweetId)
      .add(InReplyToUserIdFeature, inReplyToUserId)
      .add(IsRetweetFeature, share.isDefined)
      .add(IsInReplyToReplyOrDirectedFeature, isInReplyToReplyOrDirected)
      .add(IsInReplyToRetweetFeature, isInReplyToRetweet)
      .add(LocationIdFeature, tweet.narrowcastPlace.map(_.id))
      .add(MentionScreenNameFeature, mentions.map(_.screenName))
      .add(MentionUserIdFeature, mentions.flatMap(_.userId))
      .add(QuotedTweetIdFeature, quotedTweet.map(_.tweetId))
      .add(OriginalTweetIdFeature, Some(share.map(_.sourceStatusId).getOrElse(tweet.id)))
      .add(SourceTweetIdFeature, share.map(_.sourceStatusId))
      .add(SourceUserIdFeature, share.map(_.sourceUserId))
      .add(CommunityIdFeature, tweet.communities.flatMap(_.communityIds.headOption))
      .add(TweetMediaIdsFeature, tweetMediaIds)
      .add(FirstMediaIdFeature, tweetMediaIds.headOption)
      .add(TweetUrlsFeature, urls)
      .build()
  }

  private def getTESRecordArrow(stratoClient: Client): Arrow[Long, TESRecord] = {
    val pureCoreDataArrow: Arrow[Long, Option[tp.PureCoreData]] =
      new PureCoreDataOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val communityArrow: Arrow[Long, Option[CommunityId]] =
      new CommunityOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val directedAtUserArrow: Arrow[Long, Option[UserId]] =
      new DirectedAtUserOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val exclusiveTweetControlArrow: Arrow[Long, Option[tp.ExclusiveTweetControl]] =
      new ExclusiveTweetControlOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val mediaKeysArrow: Arrow[Long, Option[Seq[MediaKey]]] =
      new MediaKeysOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val mentionsArrow: Arrow[Long, Option[Seq[tp.MentionEntity]]] =
      new MentionsOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val contextualQuoteTweetRefArrow: Arrow[Long, Option[ContextualTweetRef]] =
      new ContextualQuoteTweetRefOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val narrowcastPlaceArrow: Arrow[Long, Option[tp.NarrowcastPlace]] =
      new NarrowcastPlaceOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val articleArrow: Arrow[Long, Option[tp.Article]] =
      new ArticleOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    val urlsArrow: Arrow[Long, Option[Seq[tp.UrlEntity]]] =
      new UrlsOnTweetClientColumn(stratoClient).fetcher.asArrow
        .contramap[Long](tweetId => (tweetId, ()))
        .map(_.v)

    Arrow
      .zipWithArg(
        Arrow
          .identity[Long].andThen(Arrow.join(
            pureCoreDataArrow,
            articleArrow,
            communityArrow,
            directedAtUserArrow,
            exclusiveTweetControlArrow,
            mediaKeysArrow,
            mentionsArrow,
            narrowcastPlaceArrow,
            contextualQuoteTweetRefArrow,
            urlsArrow
          ))
      ).map {
        case (
              tweetId,
              (
                pureCoreDataOpt: Option[tp.PureCoreData],
                articleOpt: Option[tp.Article],
                communityIdOpt: Option[CommunityId],
                directedAtUserIdOpt: Option[UserId],
                exclusiveTweetControls: Option[tp.ExclusiveTweetControl],
                mediaKeysOpt: Option[Seq[MediaKey]],
                mentionEntitiesOpt: Option[Seq[tp.MentionEntity]],
                narrowcastPlaceOpt: Option[tp.NarrowcastPlace],
                quotedTweetOpt: Option[ContextualTweetRef],
                urlsOpt: Option[Seq[tp.UrlEntity]]
              )) =>
          TESRecord(
            tweetId,
            pureCoreDataOpt,
            articleOpt,
            communityIdOpt,
            directedAtUserIdOpt,
            exclusiveTweetControls,
            mediaKeysOpt,
            mentionEntitiesOpt,
            narrowcastPlaceOpt,
            quotedTweetOpt,
            urlsOpt
          )
      }
  }
}

case class TESRecord(
  tweetId: Long,
  pureCoreDataOpt: Option[tp.PureCoreData],
  articleOpt: Option[tp.Article],
  communityIdOpt: Option[CommunityId],
  directedAtUserIdOpt: Option[UserId],
  exclusiveTweetControls: Option[tp.ExclusiveTweetControl],
  mediaKeysOpt: Option[Seq[MediaKey]],
  mentionEntitiesOpt: Option[Seq[tp.MentionEntity]],
  narrowcastPlaceOpt: Option[tp.NarrowcastPlace],
  quotedTweetOpt: Option[ContextualTweetRef],
  urlsOpt: Option[Seq[tp.UrlEntity]]) {

  def getTweetOpt: Option[tp.Tweet] = pureCoreDataOpt.map { pureCoreData =>
    tp.Tweet(
      id = tweetId,
      coreData = Some(
        tp.TweetCoreData.unsafeEmpty.copy(
          userId = pureCoreData.userId,
          share = pureCoreData.share,
          reply = pureCoreData.reply,
          directedAtUser = directedAtUserIdOpt.map(id => tp.DirectedAtUser(id.value, ""))
        )),
      article = articleOpt,
      communities = communityIdOpt.map(community => tp.Communities(Seq(community.value))),
      exclusiveTweetControl = exclusiveTweetControls
        .map(control => tp.ExclusiveTweetControl(control.conversationAuthorId)),
      media = mediaKeysOpt.map { mediaKeys =>
        mediaKeys.map { key =>
          tp.MediaEntity.unsafeEmpty.copy(mediaId = key.mediaId, mediaKey = Some(key))
        }
      },
      mentions = mentionEntitiesOpt,
      narrowcastPlace = narrowcastPlaceOpt,
      quotedTweet = quotedTweetOpt.map(qt => tp.QuotedTweet.unsafeEmpty.copy(tweetId = qt.id)),
      urls = urlsOpt
    )
  }
}
