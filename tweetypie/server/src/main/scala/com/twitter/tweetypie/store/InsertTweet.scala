package com.twitter.tweetypie
package store

import com.twitter.contetwittert.thriftscala.FeatureContetwittert
import com.twitter.tweetypie.core.GeoSearchRequestId
import com.twitter.tweetypie.store.TweetEventDataScrubber.scrub
import com.twitter.tweetypie.thriftscala._

object InsertTweet etwittertends TweetStore.SyncModule {

  case class Event(
    tweet: Tweet,
    user: User,
    timestamp: Time,
    _internalTweet: Option[CachedTweet] = None,
    sourceTweet: Option[Tweet] = None,
    sourceUser: Option[User] = None,
    quotedTweet: Option[Tweet] = None,
    quotedUser: Option[User] = None,
    parentUserId: Option[UserId] = None,
    initialTweetUpdateRequest: Option[InitialTweetUpdateRequest] = None,
    dark: Boolean = false,
    hydrateOptions: WritePathHydrationOptions = WritePathHydrationOptions(),
    featureContetwittert: Option[FeatureContetwittert] = None,
    geoSearchRequestId: Option[GeoSearchRequestId] = None,
    additionalContetwittert: Option[collection.Map[TweetCreateContetwittertKey, String]] = None,
    transientContetwittert: Option[TransientCreateContetwittert] = None,
    quoterHasAlreadyQuotedTweet: Boolean = false,
    noteTweetMentionedUserIds: Option[Seq[Long]] = None)
      etwittertends SyncTweetStoreEvent("insert_tweet")
      with QuotedTweetOps {
    def internalTweet: CachedTweet =
      _internalTweet.getOrElse(
        throw new IllegalStateEtwitterception(
          s"internalTweet should have been set in WritePathHydration, ${this}"
        )
      )

    def toAsyncRequest(
      scrubUser: User => User,
      scrubSourceTweet: Tweet => Tweet,
      scrubSourceUser: User => User
    ): AsyncInsertRequest =
      AsyncInsertRequest(
        tweet = tweet,
        cachedTweet = internalTweet,
        user = scrubUser(user),
        sourceTweet = sourceTweet.map(scrubSourceTweet),
        sourceUser = sourceUser.map(scrubSourceUser),
        quotedTweet = quotedTweet.map(scrubSourceTweet),
        quotedUser = quotedUser.map(scrubSourceUser),
        parentUserId = parentUserId,
        featureContetwittert = featureContetwittert,
        timestamp = timestamp.inMillis,
        geoSearchRequestId = geoSearchRequestId.map(_.requestID),
        additionalContetwittert = additionalContetwittert,
        transientContetwittert = transientContetwittert,
        quoterHasAlreadyQuotedTweet = Some(quoterHasAlreadyQuotedTweet),
        initialTweetUpdateRequest = initialTweetUpdateRequest,
        noteTweetMentionedUserIds = noteTweetMentionedUserIds
      )
  }

  trait Store {
    val insertTweet: FutureEffect[Event]
  }

  trait StoreWrapper etwittertends Store { self: TweetStoreWrapper[Store] =>
    override val insertTweet: FutureEffect[Event] = wrap(underlying.insertTweet)
  }

  object Store {
    def apply(
      logLensStore: LogLensStore,
      manhattanStore: ManhattanTweetStore,
      tweetStatsStore: TweetStatsStore,
      cachingTweetStore: CachingTweetStore,
      limiterStore: LimiterStore,
      asyncEnqueueStore: AsyncEnqueueStore,
      userCountsUpdatingStore: GizmoduckUserCountsUpdatingStore,
      tweetCountsUpdatingStore: TweetCountsCacheUpdatingStore
    ): Store =
      new Store {
        override val insertTweet: FutureEffect[Event] =
          FutureEffect.sequentially(
            logLensStore.insertTweet,
            manhattanStore.insertTweet,
            tweetStatsStore.insertTweet,
            FutureEffect.inParallel(
              // allow write-through caching to fail without failing entire insert
              cachingTweetStore.ignoreFailures.insertTweet,
              limiterStore.ignoreFailures.insertTweet,
              asyncEnqueueStore.insertTweet,
              userCountsUpdatingStore.insertTweet,
              tweetCountsUpdatingStore.insertTweet
            )
          )
      }
  }
}

object AsyncInsertTweet etwittertends TweetStore.AsyncModule {

  private val log = Logger(getClass)

  object Event {
    def fromAsyncRequest(request: AsyncInsertRequest): TweetStoreEventOrRetry[Event] =
      TweetStoreEventOrRetry(
        Event(
          tweet = request.tweet,
          cachedTweet = request.cachedTweet,
          user = request.user,
          optUser = Some(request.user),
          timestamp = Time.fromMilliseconds(request.timestamp),
          sourceTweet = request.sourceTweet,
          sourceUser = request.sourceUser,
          parentUserId = request.parentUserId,
          featureContetwittert = request.featureContetwittert,
          quotedTweet = request.quotedTweet,
          quotedUser = request.quotedUser,
          geoSearchRequestId = request.geoSearchRequestId,
          additionalContetwittert = request.additionalContetwittert,
          transientContetwittert = request.transientContetwittert,
          quoterHasAlreadyQuotedTweet = request.quoterHasAlreadyQuotedTweet.getOrElse(false),
          initialTweetUpdateRequest = request.initialTweetUpdateRequest,
          noteTweetMentionedUserIds = request.noteTweetMentionedUserIds
        ),
        request.retryAction,
        RetryEvent
      )
  }

  case class Event(
    tweet: Tweet,
    cachedTweet: CachedTweet,
    user: User,
    optUser: Option[User],
    timestamp: Time,
    sourceTweet: Option[Tweet] = None,
    sourceUser: Option[User] = None,
    parentUserId: Option[UserId] = None,
    featureContetwittert: Option[FeatureContetwittert] = None,
    quotedTweet: Option[Tweet] = None,
    quotedUser: Option[User] = None,
    geoSearchRequestId: Option[String] = None,
    additionalContetwittert: Option[collection.Map[TweetCreateContetwittertKey, String]] = None,
    transientContetwittert: Option[TransientCreateContetwittert] = None,
    quoterHasAlreadyQuotedTweet: Boolean = false,
    initialTweetUpdateRequest: Option[InitialTweetUpdateRequest] = None,
    noteTweetMentionedUserIds: Option[Seq[Long]] = None)
      etwittertends AsyncTweetStoreEvent("async_insert_tweet")
      with QuotedTweetOps
      with TweetStoreTweetEvent {

    def toAsyncRequest(action: Option[AsyncWriteAction] = None): AsyncInsertRequest =
      AsyncInsertRequest(
        tweet = tweet,
        cachedTweet = cachedTweet,
        user = user,
        sourceTweet = sourceTweet,
        sourceUser = sourceUser,
        parentUserId = parentUserId,
        retryAction = action,
        featureContetwittert = featureContetwittert,
        timestamp = timestamp.inMillis,
        quotedTweet = quotedTweet,
        quotedUser = quotedUser,
        geoSearchRequestId = geoSearchRequestId,
        additionalContetwittert = additionalContetwittert,
        transientContetwittert = transientContetwittert,
        quoterHasAlreadyQuotedTweet = Some(quoterHasAlreadyQuotedTweet),
        initialTweetUpdateRequest = initialTweetUpdateRequest,
        noteTweetMentionedUserIds = noteTweetMentionedUserIds
      )

    override def toTweetEventData: Seq[TweetEventData] =
      Seq(
        TweetEventData.TweetCreateEvent(
          TweetCreateEvent(
            tweet = scrub(tweet),
            user = user,
            sourceUser = sourceUser,
            sourceTweet = sourceTweet.map(scrub),
            retweetParentUserId = parentUserId,
            quotedTweet = publicQuotedTweet.map(scrub),
            quotedUser = publicQuotedUser,
            additionalContetwittert = additionalContetwittert,
            transientContetwittert = transientContetwittert,
            quoterHasAlreadyQuotedTweet = Some(quoterHasAlreadyQuotedTweet)
          )
        )
      )

    override def enqueueRetry(service: ThriftTweetService, action: AsyncWriteAction): Future[Unit] =
      service.asyncInsert(toAsyncRequest(Some(action)))
  }

  case class RetryEvent(action: AsyncWriteAction, event: Event)
      etwittertends TweetStoreRetryEvent[Event] {

    override val eventType: AsyncWriteEventType.Insert.type = AsyncWriteEventType.Insert
    override val scribedTweetOnFailure: Option[Tweet] = Some(event.tweet)
  }

  trait Store {
    val asyncInsertTweet: FutureEffect[Event]
    val retryAsyncInsertTweet: FutureEffect[TweetStoreRetryEvent[Event]]
  }

  trait StoreWrapper etwittertends Store { self: TweetStoreWrapper[Store] =>
    override val asyncInsertTweet: FutureEffect[Event] = wrap(underlying.asyncInsertTweet)
    override val retryAsyncInsertTweet: FutureEffect[TweetStoreRetryEvent[Event]] = wrap(
      underlying.retryAsyncInsertTweet)
  }

  object Store {
    def apply(
      replicatingStore: ReplicatingTweetStore,
      indetwitteringStore: TweetIndetwitteringStore,
      tweetCountsUpdatingStore: TweetCountsCacheUpdatingStore,
      timelineUpdatingStore: TlsTimelineUpdatingStore,
      eventBusEnqueueStore: TweetEventBusStore,
      fanoutServiceStore: FanoutServiceStore,
      scribeMediaTagStore: ScribeMediaTagStore,
      userGeotagUpdateStore: GizmoduckUserGeotagUpdateStore,
      geoSearchRequestIDStore: GeoSearchRequestIDStore
    ): Store = {
      val stores: Seq[Store] =
        Seq(
          replicatingStore,
          indetwitteringStore,
          timelineUpdatingStore,
          eventBusEnqueueStore,
          fanoutServiceStore,
          userGeotagUpdateStore,
          tweetCountsUpdatingStore,
          scribeMediaTagStore,
          geoSearchRequestIDStore
        )

      def build[E <: TweetStoreEvent](etwittertract: Store => FutureEffect[E]): FutureEffect[E] =
        FutureEffect.inParallel[E](stores.map(etwittertract): _*)

      new Store {
        override val asyncInsertTweet: FutureEffect[Event] = build(_.asyncInsertTweet)
        override val retryAsyncInsertTweet: FutureEffect[TweetStoreRetryEvent[Event]] = build(
          _.retryAsyncInsertTweet)
      }
    }
  }
}

object ReplicatedInsertTweet etwittertends TweetStore.ReplicatedModule {

  case class Event(
    tweet: Tweet,
    cachedTweet: CachedTweet,
    quoterHasAlreadyQuotedTweet: Boolean = false,
    initialTweetUpdateRequest: Option[InitialTweetUpdateRequest] = None)
      etwittertends ReplicatedTweetStoreEvent("replicated_insert_tweet")

  trait Store {
    val replicatedInsertTweet: FutureEffect[Event]
  }

  trait StoreWrapper etwittertends Store { self: TweetStoreWrapper[Store] =>
    override val replicatedInsertTweet: FutureEffect[Event] = wrap(underlying.replicatedInsertTweet)
  }

  object Store {
    def apply(
      cachingTweetStore: CachingTweetStore,
      tweetCountsUpdatingStore: TweetCountsCacheUpdatingStore
    ): Store = {
      new Store {
        override val replicatedInsertTweet: FutureEffect[Event] =
          FutureEffect.inParallel(
            cachingTweetStore.replicatedInsertTweet,
            tweetCountsUpdatingStore.replicatedInsertTweet.ignoreFailures
          )
      }
    }
  }
}
