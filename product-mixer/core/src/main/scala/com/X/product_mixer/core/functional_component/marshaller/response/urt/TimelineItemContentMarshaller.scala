package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.commerce.CommerceProductGroupItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.commerce.CommerceProductItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.article.ArticleItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.audio_space.AudioSpaceItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.card.CardItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.event.EventSummaryItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item.GenericSummaryItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.icon_label.IconLabelItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.label.LabelItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.message.MessagePromptItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.moment.MomentAnnotationItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.prompt.PromptItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion.SpellingItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.thread.ThreadHeaderItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tile.TileItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tombstone.TombstoneItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicFollowPromptItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.topic.TopicItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.trend.TrendItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tweet.TweetItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tweet_composer.TweetComposerItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.X_list.XListItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.user.UserItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.item.vertical_grid_item.VerticalGridItemMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.operation.CursorItemMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.Cover
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.ArticleItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.audio_space.AudioSpaceItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.card.CardItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductGroupItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.event.EventSummaryItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.icon_label.IconLabelItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.label.LabelItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.message.MessagePromptItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.moment.MomentAnnotationItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.suggestion.SpellingItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.thread.ThreadHeaderItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.tile.TileItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.tombstone.TombstoneItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFollowPromptItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.trend.TrendItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.tweet_composer.TweetComposerItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.XListItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.vertical_grid_item.VerticalGridItem
import com.X.product_mixer.core.model.marshalling.response.urt.operation.CursorItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineItemContentMarshaller @Inject() (
  articleItemMarshaller: ArticleItemMarshaller,
  audioSpaceItemMarshaller: AudioSpaceItemMarshaller,
  cardItemMarshaller: CardItemMarshaller,
  cursorItemMarshaller: CursorItemMarshaller,
  eventSummaryItemMarshaller: EventSummaryItemMarshaller,
  iconLabelItemMarshaller: IconLabelItemMarshaller,
  labelItemMarshaller: LabelItemMarshaller,
  messagePromptItemMarshaller: MessagePromptItemMarshaller,
  tileItemMarshaller: TileItemMarshaller,
  tombstoneItemMarshaller: TombstoneItemMarshaller,
  topicFollowPromptItemMarshaller: TopicFollowPromptItemMarshaller,
  topicItemMarshaller: TopicItemMarshaller,
  tweetComposerItemMarshaller: TweetComposerItemMarshaller,
  tweetItemMarshaller: TweetItemMarshaller,
  XListItemMarshaller: XListItemMarshaller,
  userItemMarshaller: UserItemMarshaller,
  verticalGridItemMarshaller: VerticalGridItemMarshaller,
  threadHeaderItemMarshaller: ThreadHeaderItemMarshaller,
  promptItemMarshaller: PromptItemMarshaller,
  spellingItemMarshaller: SpellingItemMarshaller,
  momentAnnotationItemMarshaller: MomentAnnotationItemMarshaller,
  genericSummaryItemMarshaller: GenericSummaryItemMarshaller,
  commerceProductItemMarshaller: CommerceProductItemMarshaller,
  commerceProductGroupItemMarshaller: CommerceProductGroupItemMarshaller,
  trendItemMarshaller: TrendItemMarshaller) {

  def apply(item: TimelineItem): urt.TimelineItemContent = item match {
    case articleItem: ArticleItem => articleItemMarshaller(articleItem)
    case audioSpaceItem: AudioSpaceItem => audioSpaceItemMarshaller(audioSpaceItem)
    case cardItem: CardItem => cardItemMarshaller(cardItem)
    case cursorItem: CursorItem => cursorItemMarshaller(cursorItem)
    case eventSummaryItem: EventSummaryItem => eventSummaryItemMarshaller(eventSummaryItem)
    case genericSummaryItem: GenericSummaryItem => genericSummaryItemMarshaller(genericSummaryItem)
    case iconLabelItem: IconLabelItem => iconLabelItemMarshaller(iconLabelItem)
    case labelItem: LabelItem => labelItemMarshaller(labelItem)
    case messagePromptItem: MessagePromptItem => messagePromptItemMarshaller(messagePromptItem)
    case tileItem: TileItem => tileItemMarshaller(tileItem)
    case tombstoneItem: TombstoneItem => tombstoneItemMarshaller(tombstoneItem)
    case topicFollowPromptItem: TopicFollowPromptItem =>
      topicFollowPromptItemMarshaller(topicFollowPromptItem)
    case topicItem: TopicItem => topicItemMarshaller(topicItem)
    case tweetComposerItem: TweetComposerItem => tweetComposerItemMarshaller(tweetComposerItem)
    case tweetItem: TweetItem => tweetItemMarshaller(tweetItem)
    case XListItem: XListItem => XListItemMarshaller(XListItem)
    case userItem: UserItem => userItemMarshaller(userItem)
    case verticalGridItem: VerticalGridItem => verticalGridItemMarshaller(verticalGridItem)
    case threadHeaderItem: ThreadHeaderItem => threadHeaderItemMarshaller(threadHeaderItem)
    case promptItem: PromptItem => promptItemMarshaller(promptItem)
    case spellingItem: SpellingItem => spellingItemMarshaller(spellingItem)
    case momentAnnotationItem: MomentAnnotationItem =>
      momentAnnotationItemMarshaller(momentAnnotationItem)
    case commerceProductItem: CommerceProductItem =>
      commerceProductItemMarshaller(commerceProductItem)
    case commerceProductGroupItem: CommerceProductGroupItem =>
      commerceProductGroupItemMarshaller(commerceProductGroupItem)
    case trendItem: TrendItem => trendItemMarshaller(trendItem)
    case _: Cover => throw TimelineCoverNotFilteredException
    case _ => throw new UnsupportedTimelineItemException(item)
  }
}

class UnsupportedTimelineItemException(timelineItem: TimelineItem)
    extends UnsupportedOperationException(
      "Unsupported timeline item " + TransportMarshaller.getSimpleName(timelineItem.getClass))

object TimelineCoverNotFilteredException
    extends UnsupportedOperationException("AddEntriesInstructionBuilder does not support Cover. " +
      "ShowCoverInstructionBuilder should be used with AddEntriesWithShowCoverInstructionBuilder " +
      "in order to filter out the Cover.")
