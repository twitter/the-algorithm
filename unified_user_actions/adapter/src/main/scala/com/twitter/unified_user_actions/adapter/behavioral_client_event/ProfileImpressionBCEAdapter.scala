package com.twitter.unified_user_actions.adapter.behavioral_client_event

import com.twitter.client.behavioral_event.action.impress.latest.thriftscala.Impress
import com.twitter.client_event_entities.serverside_context_key.latest.thriftscala.FlattenedServersideContextKey
import com.twitter.storage.behavioral_event.thriftscala.FlattenedEventLog
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.unified_user_actions.thriftscala.ClientProfileV2Impression
import com.twitter.unified_user_actions.thriftscala.Item
import com.twitter.unified_user_actions.thriftscala.ProductSurface
import com.twitter.unified_user_actions.thriftscala.ProfileActionInfo
import com.twitter.unified_user_actions.thriftscala.ProfileInfo
import com.twitter.unified_user_actions.thriftscala.UnifiedUserAction

object ProfileImpressionBCEAdapter {
  val Profile = new ProfileImpressionBCEAdapter()
}

class ProfileImpressionBCEAdapter extends ImpressionBCEAdapter {
  override type ImpressedItem = Item.ProfileInfo

  override def toUUA(e: FlattenedEventLog): Seq[UnifiedUserAction] =
    (e.v2Impress, e.v1UserIds) match {
      case (Some(v2Impress), Some(v1UserIds)) =>
        v1UserIds.map { user =>
          getUnifiedUserAction(
            event = e,
            actionType = ActionType.ClientProfileV2Impression,
            item = getImpressedItem(user, v2Impress),
            productSurface = Some(ProductSurface.ProfilePage)
          )
        }
      case _ => Nil
    }

  override def getImpressedItem(
    context: FlattenedServersideContextKey,
    impression: Impress
  ): ImpressedItem =
    Item.ProfileInfo(
      ProfileInfo(
        actionProfileId = context.serversideContextId.toLong,
        profileActionInfo = Some(
          ProfileActionInfo.ClientProfileV2Impression(
            ClientProfileV2Impression(
              impressStartTimestampMs = getImpressedStartTimestamp(impression),
              impressEndTimestampMs = getImpressedEndTimestamp(impression),
              sourceComponent = getImpressedUISourceComponent(context)
            )
          )
        )
      ))
}
