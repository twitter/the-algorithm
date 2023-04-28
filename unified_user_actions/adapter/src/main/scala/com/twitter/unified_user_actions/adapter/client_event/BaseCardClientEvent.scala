package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.clientapp.thriftscala.LogEvent
import com.twitter.clientapp.thriftscala.{Item => LogEventItem}
import com.twitter.clientapp.thriftscala.ItemType
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.unified_user_actions.thriftscala.CardInfo
import com.twitter.unified_user_actions.thriftscala.Item

abstract class BaseCardClientEvent(actionType: ActionType)
    extends BaseClientEvent(actionType = actionType) {

  override def isItemTypeValid(itemTypeOpt: Option[ItemType]): Boolean =
    ItemTypeFilterPredicates.ignoreItemType(itemTypeOpt)
  override def getUuaItem(
    ceItem: LogEventItem,
    logEvent: LogEvent
  ): Option[Item] = Some(
    Item.CardInfo(
      CardInfo(
        id = ceItem.id,
        itemType = ceItem.itemType,
        actionTweetAuthorInfo = ClientEventCommonUtils.getAuthorInfo(ceItem),
      ))
  )
}
