/** Copyright 2010 Twitter, Inc. */
package com.twitter.tweetypie
package tflock

import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.util.Future

trait TweetIndexer {

  /**
   * Called at tweet-creation time, this method should set up all relevant indices on the tweet.
   */
  def createIndex(tweet: Tweet): Future[Unit] = Future.Unit

  /**
   * Called at tweet-undelete time (which isn't yet handled), this method should
   * restore all relevant indices on the tweet.
   */
  def undeleteIndex(tweet: Tweet): Future[Unit] = Future.Unit

  /**
   * Called at tweet-delete time, this method should archive all relevant indices on the tweet.
   */
  def deleteIndex(tweet: Tweet, isBounceDelete: Boolean): Future[Unit] = Future.Unit

  /**
   * This method should archive or unarchive the retweet edge in TFlock RetweetsGraph.
   */
  def setRetweetVisibility(retweetId: TweetId, visible: Boolean): Future[Unit] = Future.Unit
}
