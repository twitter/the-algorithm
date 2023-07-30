package com.X.tweetypie
package federated
package promotedcontent

import com.X.ads.callback.thriftscala.EngagementRequest
import com.X.ads.internal.pcl.service.CallbackPromotedContentLogger
import com.X.ads.internal.pcl.strato_adaptor.PromotedContentInputProvider
import com.X.ads.internal.pcl.thriftscala.PromotedContentInput
import com.X.adserver.thriftscala.EngagementType
import com.X.util.Future

object TweetPromotedContentLogger {
  sealed abstract class TweetEngagementType(val engagementType: EngagementType)
  case object TweetEngagement extends TweetEngagementType(EngagementType.Send)
  case object ReplyEngagement extends TweetEngagementType(EngagementType.Reply)
  case object RetweetEngagement extends TweetEngagementType(EngagementType.Retweet)

  type Type = (EngagementRequest, TweetEngagementType, Boolean) => Future[Unit]

  private[this] val XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)

  def apply(callbackPromotedContentLogger: CallbackPromotedContentLogger): Type =
    (
      engagementRequest: EngagementRequest,
      tweetEngagementType: TweetEngagementType,
      isDark: Boolean
    ) => {
      val pci: PromotedContentInput =
        PromotedContentInputProvider(XContext, engagementRequest)

      // The real logging is fire-and-forget, so we can create the Future and ignore returning it.
      Future.when(!isDark) {
        callbackPromotedContentLogger.logNonTrendEngagement(
          pci,
          tweetEngagementType.engagementType,
          pci.impressionId)
      }
    }
}
