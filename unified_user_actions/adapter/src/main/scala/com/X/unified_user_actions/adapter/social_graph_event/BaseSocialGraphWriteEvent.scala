package com.X.unified_user_actions.adapter.social_graph_event

import com.X.socialgraph.thriftscala.LogEventContext
import com.X.socialgraph.thriftscala.SrcTargetRequest
import com.X.socialgraph.thriftscala.WriteEvent
import com.X.socialgraph.thriftscala.WriteRequestResult
import com.X.unified_user_actions.adapter.common.AdapterUtils
import com.X.unified_user_actions.thriftscala.ActionType
import com.X.unified_user_actions.thriftscala.EventMetadata
import com.X.unified_user_actions.thriftscala.Item
import com.X.unified_user_actions.thriftscala.ProfileInfo
import com.X.unified_user_actions.thriftscala.SourceLineage
import com.X.unified_user_actions.thriftscala.UnifiedUserAction
import com.X.unified_user_actions.thriftscala.UserIdentifier

trait BaseSocialGraphWriteEvent[T] {
  def uuaActionType: ActionType

  def getSrcTargetRequest(
    e: WriteEvent
  ): Seq[SrcTargetRequest] = getSubType(e) match {
    case Some(subType: Seq[T]) =>
      getWriteRequestResultFromSubType(subType).collect {
        case r if r.validationError.isEmpty => r.request
      }
    case _ => Nil
  }

  def getSubType(e: WriteEvent): Option[Seq[T]]
  def getWriteRequestResultFromSubType(subType: Seq[T]): Seq[WriteRequestResult]

  def toUnifiedUserAction(
    writeEvent: WriteEvent,
    uuaAction: BaseSocialGraphWriteEvent[_]
  ): Seq[UnifiedUserAction] =
    uuaAction.getSrcTargetRequest(writeEvent).map { srcTargetRequest =>
      UnifiedUserAction(
        userIdentifier = UserIdentifier(userId = writeEvent.context.loggedInUserId),
        item = getSocialGraphItem(srcTargetRequest),
        actionType = uuaAction.uuaActionType,
        eventMetadata = getEventMetadata(writeEvent.context)
      )
    }

  def getSocialGraphItem(socialGraphSrcTargetRequest: SrcTargetRequest): Item = {
    Item.ProfileInfo(
      ProfileInfo(
        actionProfileId = socialGraphSrcTargetRequest.target
      )
    )
  }

  def getEventMetadata(context: LogEventContext): EventMetadata = {
    EventMetadata(
      sourceTimestampMs = context.timestamp,
      receivedTimestampMs = AdapterUtils.currentTimestampMs,
      sourceLineage = SourceLineage.ServerSocialGraphEvents,
    )
  }
}
