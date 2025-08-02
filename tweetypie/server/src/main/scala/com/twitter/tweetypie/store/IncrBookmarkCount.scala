package com.twitter.tweetypie
package store

import com.twitter.tweetypie.store.TweetStoreEvent.NoRetry
import com.twitter.tweetypie.store.TweetStoreEvent.RetryStrategy
import com.twitter.tweetypie.thriftscala.AsyncIncrBookmarkCountRequest
import com.twitter.tweetypie.thriftscala.AsyncWriteAction

object IncrBookmarkCount etwittertends TweetStore.SyncModule {
  case class Event(tweetId: TweetId, delta: Int, timestamp: Time)
      etwittertends SyncTweetStoreEvent("incr_bookmark_count") {
    val toAsyncRequest: AsyncIncrBookmarkCountRequest =
      AsyncIncrBookmarkCountRequest(tweetId = tweetId, delta = delta)
  }

  trait Store {
    val incrBookmarkCount: FutureEffect[Event]
  }

  trait StoreWrapper etwittertends Store { self: TweetStoreWrapper[Store] =>
    override val incrBookmarkCount: FutureEffect[Event] = wrap(underlying.incrBookmarkCount)
  }

  object Store {
    def apply(
      asyncEnqueueStore: AsyncEnqueueStore,
      replicatingStore: ReplicatingTweetStore
    ): Store = {
      new Store {
        override val incrBookmarkCount: FutureEffect[Event] =
          FutureEffect.inParallel(
            asyncEnqueueStore.incrBookmarkCount,
            replicatingStore.incrBookmarkCount
          )
      }
    }
  }
}

object AsyncIncrBookmarkCount etwittertends TweetStore.AsyncModule {
  case class Event(tweetId: TweetId, delta: Int, timestamp: Time)
      etwittertends AsyncTweetStoreEvent("async_incr_bookmark_event") {
    override def enqueueRetry(service: ThriftTweetService, action: AsyncWriteAction): Future[Unit] =
      Future.Unit

    override def retryStrategy: RetryStrategy = NoRetry
  }

  trait Store {
    def asyncIncrBookmarkCount: FutureEffect[Event]
  }

  trait StoreWrapper etwittertends Store { self: TweetStoreWrapper[Store] =>
    override val asyncIncrBookmarkCount: FutureEffect[Event] = wrap(
      underlying.asyncIncrBookmarkCount)
  }

  object Store {
    def apply(tweetCountsUpdatingStore: TweetCountsCacheUpdatingStore): Store = {
      new Store {
        override def asyncIncrBookmarkCount: FutureEffect[AsyncIncrBookmarkCount.Event] =
          tweetCountsUpdatingStore.asyncIncrBookmarkCount
      }
    }
  }
}

object ReplicatedIncrBookmarkCount etwittertends TweetStore.ReplicatedModule {
  case class Event(tweetId: TweetId, delta: Int)
      etwittertends ReplicatedTweetStoreEvent("replicated_incr_bookmark_count") {
    override def retryStrategy: RetryStrategy = NoRetry
  }

  trait Store {
    val replicatedIncrBookmarkCount: FutureEffect[Event]
  }

  trait StoreWrapper etwittertends Store { self: TweetStoreWrapper[Store] =>
    override val replicatedIncrBookmarkCount: FutureEffect[Event] = wrap(
      underlying.replicatedIncrBookmarkCount)
  }

  object Store {
    def apply(tweetCountsUpdatingStore: TweetCountsCacheUpdatingStore): Store = {
      new Store {
        override val replicatedIncrBookmarkCount: FutureEffect[Event] = {
          tweetCountsUpdatingStore.replicatedIncrBookmarkCount
        }
      }
    }
  }
}
