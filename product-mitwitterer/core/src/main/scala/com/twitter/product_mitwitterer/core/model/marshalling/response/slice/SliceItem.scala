package com.twitter.product_mitwitterer.core.model.marshalling.response.slice

import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling

/**
 * These are the Ad Types etwitterposed on AdUnits
 *
 * They are to be kept in sync with strato/config/src/thrift/com/twitter/strato/graphql/hubble.thrift
 */
sealed trait AdType
object AdType {
  case object Tweet etwittertends AdType
  case object Account etwittertends AdType
  case object InStreamVideo etwittertends AdType
  case object DisplayCreative etwittertends AdType
  case object Trend etwittertends AdType
  case object Spotlight etwittertends AdType
  case object Takeover etwittertends AdType
}

trait SliceItem
case class TweetItem(id: Long) etwittertends SliceItem
case class UserItem(id: Long) etwittertends SliceItem
case class TwitterListItem(id: Long) etwittertends SliceItem
case class DMConvoSearchItem(id: String, lastReadableEventId: Option[Long]) etwittertends SliceItem
case class DMEventItem(id: Long) etwittertends SliceItem
case class DMConvoItem(id: String, lastReadableEventId: Option[Long]) etwittertends SliceItem
case class DMMessageSearchItem(id: Long) etwittertends SliceItem
case class TopicItem(id: Long) etwittertends SliceItem
case class TypeaheadEventItem(eventId: Long, metadata: Option[TypeaheadMetadata]) etwittertends SliceItem
case class TypeaheadQuerySuggestionItem(query: String, metadata: Option[TypeaheadMetadata])
    etwittertends SliceItem
case class TypeaheadUserItem(
  userId: Long,
  metadata: Option[TypeaheadMetadata],
  badges: Seq[UserBadge])
    etwittertends SliceItem
case class AdItem(adUnitId: Long, adAccountId: Long) etwittertends SliceItem
case class AdCreativeItem(creativeId: Long, adType: AdType, adAccountId: Long) etwittertends SliceItem
case class AdGroupItem(adGroupId: Long, adAccountId: Long) etwittertends SliceItem
case class CampaignItem(campaignId: Long, adAccountId: Long) etwittertends SliceItem
case class FundingSourceItem(fundingSourceId: Long, adAccountId: Long) etwittertends SliceItem

sealed trait CursorType
case object PreviousCursor etwittertends CursorType
case object NetwittertCursor etwittertends CursorType
@deprecated(
  "GapCursors are not supported by Product Mitwitterer Slice marshallers, if you need support for these reach out to #product-mitwitterer")
case object GapCursor etwittertends CursorType

// CursorItem etwittertends SliceItem to enable support for GapCursors
case class CursorItem(value: String, cursorType: CursorType) etwittertends SliceItem

case class SliceInfo(
  previousCursor: Option[String],
  netwittertCursor: Option[String])

case class Slice(
  items: Seq[SliceItem],
  sliceInfo: SliceInfo)
    etwittertends HasMarshalling

sealed trait TypeaheadResultContetwittertType
case object You etwittertends TypeaheadResultContetwittertType
case object Location etwittertends TypeaheadResultContetwittertType
case object NumFollowers etwittertends TypeaheadResultContetwittertType
case object FollowRelationship etwittertends TypeaheadResultContetwittertType
case object Bio etwittertends TypeaheadResultContetwittertType
case object NumTweets etwittertends TypeaheadResultContetwittertType
case object Trending etwittertends TypeaheadResultContetwittertType
case object HighlightedLabel etwittertends TypeaheadResultContetwittertType

case class TypeaheadResultContetwittert(
  contetwittertType: TypeaheadResultContetwittertType,
  displayString: String,
  iconUrl: Option[String])

case class TypeaheadMetadata(
  score: Double,
  source: Option[String],
  contetwittert: Option[TypeaheadResultContetwittert])

// Used to render badges in Typeahead, such as Business-affiliated badges
case class UserBadge(badgeType: String, badgeUrl: String, description: String)
