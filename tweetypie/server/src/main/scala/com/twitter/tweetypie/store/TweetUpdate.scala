package com.twitter.tweetypie
package store

import com.twitter.tweetypie.thriftscala._

object TweetUpdate {

  /**
   * Copies takedown information from the source [[Tweet]] into [[CachedTweet]].
   *
   * Note that this method requires the source [[Tweet]] to have been loaded with the following
   * additional fields (which happens for all paths that create [[ReplicatedTakedown.Event]], in
   * both [[TakedownHandler]] and [[UserTakedownHandler]]:
   * - TweetypieOnlyTakedownReasonsField
   * - TweetypieOnlyTakedownCountryCodesField
   * This is done to ensure the remote datacenter of a takedown does not incorrectly try to load
   * from MH as the data is already cached.
   */
  def copyTakedownFieldsForUpdate(source: Tweet): CachedTweet => CachedTweet =
    ct => {
      val newCoreData = source.coreData.get
      val updatedCoreData = ct.tweet.coreData.map(_.copy(hasTakedown = newCoreData.hasTakedown))
      ct.copy(
        tweet = ct.tweet.copy(
          coreData = updatedCoreData,
          tweetypieOnlyTakedownCountryCodes = source.tweetypieOnlyTakedownCountryCodes,
          tweetypieOnlyTakedownReasons = source.tweetypieOnlyTakedownReasons
        )
      )
    }

  def copyNsfwFieldsForUpdate(source: Tweet): Tweet => Tweet =
    tweet => {
      val newCoreData = source.coreData.get
      val updatedCoreData =
        tweet.coreData.map { core =>
          core.copy(nsfwUser = newCoreData.nsfwUser, nsfwAdmin = newCoreData.nsfwAdmin)
        }
      tweet.copy(coreData = updatedCoreData)
    }
}
