package com.X.unified_user_actions.adapter.social_graph_event

import com.X.socialgraph.thriftscala.Action
import com.X.socialgraph.thriftscala.SrcTargetRequest
import com.X.unified_user_actions.thriftscala.Item
import com.X.unified_user_actions.thriftscala.ProfileActionInfo
import com.X.unified_user_actions.thriftscala.ProfileInfo
import com.X.unified_user_actions.thriftscala.ServerProfileReport

abstract class BaseReportSocialGraphWriteEvent[T] extends BaseSocialGraphWriteEvent[T] {
  def socialGraphAction: Action

  override def getSocialGraphItem(socialGraphSrcTargetRequest: SrcTargetRequest): Item = {
    Item.ProfileInfo(
      ProfileInfo(
        actionProfileId = socialGraphSrcTargetRequest.target,
        profileActionInfo = Some(
          ProfileActionInfo.ServerProfileReport(
            ServerProfileReport(reportType = socialGraphAction)
          ))
      )
    )
  }
}
