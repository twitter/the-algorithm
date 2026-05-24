package com.twitter.unified_user_actions.adapter.ads_callback_engagements

import com.twitter.ads.spendserver.thriftscala.SpendServerEvent
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.unified_user_actions.thriftscala.Item
import com.twitter.unified_user_actions.thriftscala.ProfileInfo

abstract class ProfileAdsCallbackEngagement(actionType: ActionType)
    extends BaseAdsCallbackEngagement(actionType) {

  override protected def getItem(input: SpendServerEvent): Option[Item] = {
    input.engagementEvent.flatMap { e =>
      e.impressionData.flatMap { i =>
        getProfileInfo(i.advertiserId)
      }
    }
  }

  protected def getProfileInfo(advertiserId: Long): Option[Item] = {
    Some(
      Item.ProfileInfo(
        ProfileInfo(
          actionProfileId = advertiserId
        )))
  }
}
