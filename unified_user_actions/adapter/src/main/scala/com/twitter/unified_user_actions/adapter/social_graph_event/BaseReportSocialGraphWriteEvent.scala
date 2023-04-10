package com.twitter.unified_user_actions.adapter.social_graph_event

import com.twitter.socialgraph.thriftscala.Action
import com.twitter.socialgraph.thriftscala.SrcTargetRequest
import com.twitter.unified_user_actions.thriftscala.Item
import com.twitter.unified_user_actions.thriftscala.ProfileActionInfo
import com.twitter.unified_user_actions.thriftscala.ProfileInfo
import com.twitter.unified_user_actions.thriftscala.ServerProfileReport

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
