package com.twitter.tweetypie
package federated
package promotedcontent

import com.twitter.ads.callback.thriftscala.EngagementRequest
import com.twitter.ads.internal.pcl.service.CallbackPromotedContentLogger
import com.twitter.ads.internal.pcl.strato_adaptor.PromotedContentInputProvider
import com.twitter.ads.internal.pcl.thriftscala.PromotedContentInput
import com.twitter.adserver.thriftscala.EngagementType
import com.twitter.util.Future

object TweetPromotedContentLogger {
  sealed abstract class TweetEngagementType(val engagementType: EngagementType)
  case object TweetEngagement etwittertends TweetEngagementType(EngagementType.Send)
  case object ReplyEngagement etwittertends TweetEngagementType(EngagementType.Reply)
  case object RetweetEngagement etwittertends TweetEngagementType(EngagementType.Retweet)

  type Type = (EngagementRequest, TweetEngagementType, Boolean) => Future[Unit]

  private[this] val TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)

  def apply(callbackPromotedContentLogger: CallbackPromotedContentLogger): Type =
    (
      engagementRequest: EngagementRequest,
      tweetEngagementType: TweetEngagementType,
      isDark: Boolean
    ) => {
      val pci: PromotedContentInput =
        PromotedContentInputProvider(TwitterContetwittert, engagementRequest)

      // The real logging is fire-and-forget, so we can create the Future and ignore returning it.
      Future.when(!isDark) {
        callbackPromotedContentLogger.logNonTrendEngagement(
          pci,
          tweetEngagementType.engagementType,
          pci.impressionId)
      }
    }
}
