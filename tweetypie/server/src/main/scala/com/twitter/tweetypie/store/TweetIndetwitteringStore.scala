package com.twitter.tweetypie
package store

import com.twitter.tweetypie.tflock.TweetIndetwitterer
import com.twitter.tweetypie.thriftscala._

trait TweetIndetwitteringStore
    etwittertends TweetStoreBase[TweetIndetwitteringStore]
    with AsyncInsertTweet.Store
    with AsyncDeleteTweet.Store
    with AsyncUndeleteTweet.Store
    with AsyncSetRetweetVisibility.Store {
  def wrap(w: TweetStore.Wrap): TweetIndetwitteringStore =
    new TweetStoreWrapper(w, this)
      with TweetIndetwitteringStore
      with AsyncInsertTweet.StoreWrapper
      with AsyncDeleteTweet.StoreWrapper
      with AsyncUndeleteTweet.StoreWrapper
      with AsyncSetRetweetVisibility.StoreWrapper
}

/**
 * A TweetStore that sends indetwittering updates to a TweetIndetwitterer.
 */
object TweetIndetwitteringStore {
  val Action: AsyncWriteAction.TweetIndetwitter.type = AsyncWriteAction.TweetIndetwitter

  def apply(indetwitterer: TweetIndetwitterer): TweetIndetwitteringStore =
    new TweetIndetwitteringStore {
      override val asyncInsertTweet: FutureEffect[AsyncInsertTweet.Event] =
        FutureEffect[AsyncInsertTweet.Event](event => indetwitterer.createIndetwitter(event.tweet))

      override val retryAsyncInsertTweet: FutureEffect[
        TweetStoreRetryEvent[AsyncInsertTweet.Event]
      ] =
        TweetStore.retry(Action, asyncInsertTweet)

      override val asyncDeleteTweet: FutureEffect[AsyncDeleteTweet.Event] =
        FutureEffect[AsyncDeleteTweet.Event](event =>
          indetwitterer.deleteIndetwitter(event.tweet, event.isBounceDelete))

      override val retryAsyncDeleteTweet: FutureEffect[
        TweetStoreRetryEvent[AsyncDeleteTweet.Event]
      ] =
        TweetStore.retry(Action, asyncDeleteTweet)

      override val asyncUndeleteTweet: FutureEffect[AsyncUndeleteTweet.Event] =
        FutureEffect[AsyncUndeleteTweet.Event](event => indetwitterer.undeleteIndetwitter(event.tweet))

      override val retryAsyncUndeleteTweet: FutureEffect[
        TweetStoreRetryEvent[AsyncUndeleteTweet.Event]
      ] =
        TweetStore.retry(Action, asyncUndeleteTweet)

      override val asyncSetRetweetVisibility: FutureEffect[AsyncSetRetweetVisibility.Event] =
        FutureEffect[AsyncSetRetweetVisibility.Event] { event =>
          indetwitterer.setRetweetVisibility(event.retweetId, event.visible)
        }

      override val retryAsyncSetRetweetVisibility: FutureEffect[
        TweetStoreRetryEvent[AsyncSetRetweetVisibility.Event]
      ] =
        TweetStore.retry(Action, asyncSetRetweetVisibility)
    }
}
