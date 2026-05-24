package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.clientapp.thriftscala.ItemType

object ItemTypeFilterPredicates {
  private val TweetItemTypes = Set[ItemType](ItemType.Tweet, ItemType.QuotedTweet)
  private val TopicItemTypes = Set[ItemType](ItemType.Tweet, ItemType.QuotedTweet, ItemType.Topic)
  private val ProfileItemTypes = Set[ItemType](ItemType.User)
  private val TypeaheadResultItemTypes = Set[ItemType](ItemType.Search, ItemType.User)
  private val SearchResultsPageFeedbackSubmitItemTypes =
    Set[ItemType](ItemType.Tweet, ItemType.RelevancePrompt)

  /**
   *  DDG lambda metrics count Tweets based on the `itemType`
   *  Reference code - https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/src/scala/com/twitter/experiments/lambda/shared/Timelines.scala?L156
   *  Since enums `PROMOTED_TWEET` and `POPULAR_TWEET` are deprecated in the following thrift
   *  https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/src/thrift/com/twitter/clientapp/gen/client_app.thrift?L131
   *  UUA filters two types of Tweets only: `TWEET` and `QUOTED_TWEET`
   */
  def isItemTypeTweet(itemTypeOpt: Option[ItemType]): Boolean =
    itemTypeOpt.exists(itemType => TweetItemTypes.contains(itemType))

  def isItemTypeTopic(itemTypeOpt: Option[ItemType]): Boolean =
    itemTypeOpt.exists(itemType => TopicItemTypes.contains(itemType))

  def isItemTypeProfile(itemTypeOpt: Option[ItemType]): Boolean =
    itemTypeOpt.exists(itemType => ProfileItemTypes.contains(itemType))

  def isItemTypeTypeaheadResult(itemTypeOpt: Option[ItemType]): Boolean =
    itemTypeOpt.exists(itemType => TypeaheadResultItemTypes.contains(itemType))

  def isItemTypeForSearchResultsPageFeedbackSubmit(itemTypeOpt: Option[ItemType]): Boolean =
    itemTypeOpt.exists(itemType => SearchResultsPageFeedbackSubmitItemTypes.contains(itemType))

  /**
   * Always return true. Use this when there is no need to filter based on `item_type` and all
   * values of `item_type` are acceptable.
   */
  def ignoreItemType(itemTypeOpt: Option[ItemType]): Boolean = true
}
