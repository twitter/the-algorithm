package com.twitter.visibility.models

sealed trait ContentId

object ContentId {
  case class TweetId(id: Long) extends ContentId
  case class UserId(id: Long) extends ContentId
  case class CardId(url: String) extends ContentId
  case class QuotedTweetRelationship(outer: Long, inner: Long) extends ContentId
  case class NotificationId(tweetId: Option[Long]) extends ContentId
  case class DmId(id: Long) extends ContentId
  case class BlenderTweetId(id: Long) extends ContentId
  case class SpaceId(id: String) extends ContentId
  case class SpacePlusUserId(id: String) extends ContentId
  case class DmConversationId(id: String) extends ContentId
  case class DmEventId(id: Long) extends ContentId
  case class UserUnavailableState(tweetId: Long) extends ContentId
  case class TwitterArticleId(id: Long) extends ContentId
  case class DeleteTweetId(tweetId: Long) extends ContentId
  case class MediaId(id: String) extends ContentId
  case class CommunityId(communityId: Long) extends ContentId
}
