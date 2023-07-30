package com.X.unified_user_actions.adapter.client_event

import com.X.clientapp.thriftscala.ItemType
import com.X.clientapp.thriftscala.LogEvent
import com.X.clientapp.thriftscala.{Item => LogEventItem}
import com.X.unified_user_actions.thriftscala.ActionType
import com.X.unified_user_actions.thriftscala.Item
import com.X.unified_user_actions.thriftscala.TopicInfo

abstract class BaseTopicClientEvent(actionType: ActionType)
    extends BaseClientEvent(actionType = actionType) {
  override def isItemTypeValid(itemTypeOpt: Option[ItemType]): Boolean =
    ItemTypeFilterPredicates.isItemTypeTopic(itemTypeOpt)

  override def getUuaItem(
    ceItem: LogEventItem,
    logEvent: LogEvent
  ): Option[Item] =
    for (actionTopicId <- ClientEventCommonUtils.getTopicId(
        ceItem = ceItem,
        ceNamespaceOpt = logEvent.eventNamespace))
      yield Item.TopicInfo(TopicInfo(actionTopicId = actionTopicId))
}
