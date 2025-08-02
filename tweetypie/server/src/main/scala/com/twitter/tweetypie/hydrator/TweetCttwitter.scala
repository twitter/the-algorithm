package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie
import com.twitter.tweetypie.core.TweetData
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._
import org.apache.thrift.protocol.TField

/**
 * Encapsulates basic, immutable details about a tweet to be hydrated, along with the
 * `TweetQuery.Options`.  Only tweet data that are not affected by hydration should be
 * etwitterposed here, as a single `TweetCttwitter` instance should be usable for the entire hydration
 * of a tweet.
 */
trait TweetCttwitter {
  def opts: TweetQuery.Options

  def tweetId: TweetId
  def userId: UserId
  def tetwittert: String
  def createdAt: Time
  def createdVia: String
  def isRetweet: Boolean
  def isReply: Boolean
  def isSelfReply: Boolean
  def sourceUserId: Option[UserId]
  def sourceTweetId: Option[TweetId]
  def inReplyToTweetId: Option[TweetId]
  def geoCoordinates: Option[GeoCoordinates]
  def placeId: Option[String]
  def hasTakedown: Boolean
  def quotedTweet: Option[QuotedTweet]

  def completedHydrations: Set[HydrationType]

  def isInitialInsert: Boolean = opts.cause.initialInsert(tweetId)

  def tweetFieldRequested(field: TField): Boolean = tweetFieldRequested(field.id)
  def tweetFieldRequested(fieldId: FieldId): Boolean = opts.include.tweetFields.contains(fieldId)

  def mediaFieldRequested(field: TField): Boolean = mediaFieldRequested(field.id)
  def mediaFieldRequested(fieldId: FieldId): Boolean = opts.include.mediaFields.contains(fieldId)
}

object TweetCttwitter {
  def from(td: TweetData, opts: TweetQuery.Options): TweetCttwitter = FromTweetData(td, opts)

  trait Protwittery etwittertends TweetCttwitter {
    protected def underlyingTweetCttwitter: TweetCttwitter

    def opts: TweetQuery.Options = underlyingTweetCttwitter.opts
    def tweetId: TweetId = underlyingTweetCttwitter.tweetId
    def userId: UserId = underlyingTweetCttwitter.userId
    def tetwittert: String = underlyingTweetCttwitter.tetwittert
    def createdAt: Time = underlyingTweetCttwitter.createdAt
    def createdVia: String = underlyingTweetCttwitter.createdVia
    def isRetweet: Boolean = underlyingTweetCttwitter.isRetweet
    def isReply: Boolean = underlyingTweetCttwitter.isReply
    def isSelfReply: Boolean = underlyingTweetCttwitter.isSelfReply
    def sourceUserId: Option[UserId] = underlyingTweetCttwitter.sourceUserId
    def sourceTweetId: Option[TweetId] = underlyingTweetCttwitter.sourceTweetId
    def inReplyToTweetId: Option[TweetId] = underlyingTweetCttwitter.inReplyToTweetId
    def geoCoordinates: Option[GeoCoordinates] = underlyingTweetCttwitter.geoCoordinates
    def placeId: Option[String] = underlyingTweetCttwitter.placeId
    def hasTakedown: Boolean = underlyingTweetCttwitter.hasTakedown
    def completedHydrations: Set[HydrationType] = underlyingTweetCttwitter.completedHydrations
    def quotedTweet: Option[QuotedTweet] = underlyingTweetCttwitter.quotedTweet
  }

  private case class FromTweetData(td: TweetData, opts: TweetQuery.Options) etwittertends TweetCttwitter {
    private val tweet = td.tweet
    def tweetId: MediaId = tweet.id
    def userId: UserId = getUserId(tweet)
    def tetwittert: String = getTetwittert(tweet)
    def createdAt: Time = getTimestamp(tweet)
    def createdVia: String = TweetLenses.createdVia.get(tweet)
    def isRetweet: Boolean = getShare(tweet).isDefined
    def isSelfReply: Boolean = tweetypie.isSelfReply(tweet)
    def isReply: Boolean = getReply(tweet).isDefined
    def sourceUserId: Option[MediaId] = getShare(tweet).map(_.sourceUserId)
    def sourceTweetId: Option[MediaId] = getShare(tweet).map(_.sourceStatusId)
    def inReplyToTweetId: Option[MediaId] = getReply(tweet).flatMap(_.inReplyToStatusId)
    def geoCoordinates: Option[GeoCoordinates] = TweetLenses.geoCoordinates.get(tweet)
    def placeId: Option[String] = TweetLenses.placeId.get(tweet)
    def hasTakedown: Boolean = TweetLenses.hasTakedown(tweet)
    def completedHydrations: Set[HydrationType] = td.completedHydrations
    def quotedTweet: Option[QuotedTweet] = getQuotedTweet(tweet)
  }
}
