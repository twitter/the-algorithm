package com.X.unified_user_actions.adapter.ads_callback_engagements

import com.X.ads.spendserver.thriftscala.SpendServerEvent
import com.X.unified_user_actions.thriftscala._

abstract class BaseTrendAdsCallbackEngagement(actionType: ActionType)
    extends BaseAdsCallbackEngagement(actionType = actionType) {

  override protected def getItem(input: SpendServerEvent): Option[Item] = {
    input.engagementEvent.flatMap { e =>
      e.impressionData.flatMap { i =>
        i.promotedTrendId.map { promotedTrendId =>
          Item.TrendInfo(TrendInfo(actionTrendId = promotedTrendId))
        }
      }
    }
  }
}
