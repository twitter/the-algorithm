package com.X

import com.X.mediaservices.commons.thriftscala.MediaKey
import com.X.snowflake.id.SnowflakeId
import com.X.tweetypie.thriftscala._
import com.X.gizmoduck.thriftscala.QueryFields

package object tweetypie {
  // common imports that many classes need, will probably expand this list in the future.
  type Logger = com.X.util.logging.Logger
  val Logger: com.X.util.logging.Logger.type = com.X.util.logging.Logger
  type StatsReceiver = com.X.finagle.stats.StatsReceiver
  val TweetLenses: com.X.tweetypie.util.TweetLenses.type =
    com.X.tweetypie.util.TweetLenses

  type Future[A] = com.X.util.Future[A]
  val Future: com.X.util.Future.type = com.X.util.Future

  type Duration = com.X.util.Duration
  val Duration: com.X.util.Duration.type = com.X.util.Duration

  type Time = com.X.util.Time
  val Time: com.X.util.Time.type = com.X.util.Time

  type Try[A] = com.X.util.Try[A]
  val Try: com.X.util.Try.type = com.X.util.Try

  type Throw[A] = com.X.util.Throw[A]
  val Throw: com.X.util.Throw.type = com.X.util.Throw

  type Return[A] = com.X.util.Return[A]
  val Return: com.X.util.Return.type = com.X.util.Return

  type Gate[T] = com.X.servo.util.Gate[T]
  val Gate: com.X.servo.util.Gate.type = com.X.servo.util.Gate

  type Effect[A] = com.X.servo.util.Effect[A]
  val Effect: com.X.servo.util.Effect.type = com.X.servo.util.Effect

  type FutureArrow[A, B] = com.X.servo.util.FutureArrow[A, B]
  val FutureArrow: com.X.servo.util.FutureArrow.type = com.X.servo.util.FutureArrow

  type FutureEffect[A] = com.X.servo.util.FutureEffect[A]
  val FutureEffect: com.X.servo.util.FutureEffect.type = com.X.servo.util.FutureEffect

  type Lens[A, B] = com.X.servo.data.Lens[A, B]
  val Lens: com.X.servo.data.Lens.type = com.X.servo.data.Lens

  type Mutation[A] = com.X.servo.data.Mutation[A]
  val Mutation: com.X.servo.data.Mutation.type = com.X.servo.data.Mutation

  type User = com.X.gizmoduck.thriftscala.User
  val User: com.X.gizmoduck.thriftscala.User.type = com.X.gizmoduck.thriftscala.User
  type Safety = com.X.gizmoduck.thriftscala.Safety
  val Safety: com.X.gizmoduck.thriftscala.Safety.type =
    com.X.gizmoduck.thriftscala.Safety
  type UserField = com.X.gizmoduck.thriftscala.QueryFields
  val UserField: QueryFields.type = com.X.gizmoduck.thriftscala.QueryFields

  type Tweet = thriftscala.Tweet
  val Tweet: com.X.tweetypie.thriftscala.Tweet.type = thriftscala.Tweet

  type ThriftTweetService = TweetServiceInternal.MethodPerEndpoint

  type TweetId = Long
  type UserId = Long
  type MediaId = Long
  type AppId = Long
  type KnownDeviceToken = String
  type ConversationId = Long
  type CommunityId = Long
  type PlaceId = String
  type FieldId = Short
  type Count = Long
  type CountryCode = String // ISO 3166-1-alpha-2
  type CreativesContainerId = Long

  def hasGeo(tweet: Tweet): Boolean =
    TweetLenses.placeId.get(tweet).nonEmpty ||
      TweetLenses.geoCoordinates.get(tweet).nonEmpty

  def getUserId(tweet: Tweet): UserId = TweetLenses.userId.get(tweet)
  def getText(tweet: Tweet): String = TweetLenses.text.get(tweet)
  def getCreatedAt(tweet: Tweet): Long = TweetLenses.createdAt.get(tweet)
  def getCreatedVia(tweet: Tweet): String = TweetLenses.createdVia.get(tweet)
  def getReply(tweet: Tweet): Option[Reply] = TweetLenses.reply.get(tweet)
  def getDirectedAtUser(tweet: Tweet): Option[DirectedAtUser] =
    TweetLenses.directedAtUser.get(tweet)
  def getShare(tweet: Tweet): Option[Share] = TweetLenses.share.get(tweet)
  def getQuotedTweet(tweet: Tweet): Option[QuotedTweet] = TweetLenses.quotedTweet.get(tweet)
  def getUrls(tweet: Tweet): Seq[UrlEntity] = TweetLenses.urls.get(tweet)
  def getMedia(tweet: Tweet): Seq[MediaEntity] = TweetLenses.media.get(tweet)
  def getMediaKeys(tweet: Tweet): Seq[MediaKey] = TweetLenses.mediaKeys.get(tweet)
  def getMentions(tweet: Tweet): Seq[MentionEntity] = TweetLenses.mentions.get(tweet)
  def getCashtags(tweet: Tweet): Seq[CashtagEntity] = TweetLenses.cashtags.get(tweet)
  def getHashtags(tweet: Tweet): Seq[HashtagEntity] = TweetLenses.hashtags.get(tweet)
  def getMediaTagMap(tweet: Tweet): Map[MediaId, Seq[MediaTag]] = TweetLenses.mediaTagMap.get(tweet)
  def isRetweet(tweet: Tweet): Boolean = tweet.coreData.flatMap(_.share).nonEmpty
  def isSelfReply(authorUserId: UserId, r: Reply): Boolean =
    r.inReplyToStatusId.isDefined && (r.inReplyToUserId == authorUserId)
  def isSelfReply(tweet: Tweet): Boolean = {
    getReply(tweet).exists { r => isSelfReply(getUserId(tweet), r) }
  }
  def getConversationId(tweet: Tweet): Option[TweetId] = TweetLenses.conversationId.get(tweet)
  def getSelfThreadMetadata(tweet: Tweet): Option[SelfThreadMetadata] =
    TweetLenses.selfThreadMetadata.get(tweet)
  def getCardReference(tweet: Tweet): Option[CardReference] = TweetLenses.cardReference.get(tweet)
  def getEscherbirdAnnotations(tweet: Tweet): Option[EscherbirdEntityAnnotations] =
    TweetLenses.escherbirdEntityAnnotations.get(tweet)
  def getCommunities(tweet: Tweet): Option[Communities] = TweetLenses.communities.get(tweet)
  def getTimestamp(tweet: Tweet): Time =
    if (SnowflakeId.isSnowflakeId(tweet.id)) SnowflakeId(tweet.id).time
    else Time.fromSeconds(getCreatedAt(tweet).toInt)
}
