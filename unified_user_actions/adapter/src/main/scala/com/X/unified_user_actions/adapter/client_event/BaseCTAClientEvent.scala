package com.X.unified_user_actions.adapter.client_event

import com.X.clientapp.thriftscala.LogEvent
import com.X.logbase.thriftscala.LogBase
import com.X.unified_user_actions.thriftscala.ActionType
import com.X.unified_user_actions.thriftscala.Item
import com.X.unified_user_actions.thriftscala.UnifiedUserAction
import com.X.unified_user_actions.thriftscala._
import com.X.clientapp.thriftscala.{Item => LogEventItem}

abstract class BaseCTAClientEvent(actionType: ActionType)
    extends BaseClientEvent(actionType = actionType) {

  override def toUnifiedUserAction(logEvent: LogEvent): Seq[UnifiedUserAction] = {
    val logBase: Option[LogBase] = logEvent.logBase
    val userIdentifier: UserIdentifier = UserIdentifier(
      userId = logBase.flatMap(_.userId),
      guestIdMarketing = logBase.flatMap(_.guestIdMarketing))
    val uuaItem: Item = Item.CtaInfo(CTAInfo())
    val eventTimestamp = logBase.flatMap(getSourceTimestamp).getOrElse(0L)
    val ceItem = LogEventItem.unsafeEmpty

    val productSurface: Option[ProductSurface] = ProductSurfaceUtils
      .getProductSurface(logEvent.eventNamespace)

    val eventMetaData: EventMetadata = ClientEventCommonUtils
      .getEventMetadata(
        eventTimestamp = eventTimestamp,
        logEvent = logEvent,
        ceItem = ceItem,
        productSurface = productSurface
      )

    Seq(
      UnifiedUserAction(
        userIdentifier = userIdentifier,
        item = uuaItem,
        actionType = actionType,
        eventMetadata = eventMetaData,
        productSurface = productSurface,
        productSurfaceInfo =
          ProductSurfaceUtils.getProductSurfaceInfo(productSurface, ceItem, logEvent)
      ))
  }

}
