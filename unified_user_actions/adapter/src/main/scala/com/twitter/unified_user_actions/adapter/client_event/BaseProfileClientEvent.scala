package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.clientapp.thriftscala.ItemType
import com.twitter.clientapp.thriftscala.LogEvent
import com.twitter.clientapp.thriftscala.{Item => LogEventItem}
import com.twitter.unified_user_actions.adapter.client_event.ClientEventCommonUtils.getProfileIdFromUserItem
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.unified_user_actions.thriftscala.Item
import com.twitter.unified_user_actions.thriftscala.ProfileInfo

abstract class BaseProfileClientEvent(actionType: ActionType)
    extends BaseClientEvent(actionType = actionType) {
  override def isItemTypeValid(itemTypeOpt: Option[ItemType]): Boolean =
    ItemTypeFilterPredicates.isItemTypeProfile(itemTypeOpt)

  override def getUuaItem(
    ceItem: LogEventItem,
    logEvent: LogEvent
  ): Option[Item] =
    getProfileIdFromUserItem(ceItem).map { id =>
      Item.ProfileInfo(
        ProfileInfo(actionProfileId = id)
      )
    }
}
