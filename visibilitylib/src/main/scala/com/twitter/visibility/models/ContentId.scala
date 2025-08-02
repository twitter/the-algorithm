package com.twitter.visibility.models

sealed trait ContentId

object ContentId {
  case class TweetId(id: Long) etwittertends ContentId
  case class UserId(id: Long) etwittertends ContentId
  case class CardId(url: String) etwittertends ContentId
  case class QuotedTweetRelationship(outer: Long, inner: Long) etwittertends ContentId
  case class NotificationId(tweetId: Option[Long]) etwittertends ContentId
  case class DmId(id: Long) etwittertends ContentId
  case class BlenderTweetId(id: Long) etwittertends ContentId
  case class SpaceId(id: String) etwittertends ContentId
  case class SpacePlusUserId(id: String) etwittertends ContentId
  case class DmConversationId(id: String) etwittertends ContentId
  case class DmEventId(id: Long) etwittertends ContentId
  case class UserUnavailableState(tweetId: Long) etwittertends ContentId
  case class TwitterArticleId(id: Long) etwittertends ContentId
  case class DeleteTweetId(tweetId: Long) etwittertends ContentId
  case class MediaId(id: String) etwittertends ContentId
  case class CommunityId(communityId: Long) etwittertends ContentId
}
