package com.twitter.unified_user_actions.adapter.ads_callback_engagements

import com.twitter.ads.spendserver.thriftscala.SpendServerEvent
import com.twitter.unified_user_actions.thriftscala._

object AdsCallbackEngagement {
  object PromotedTweetFav etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetFav)

  object PromotedTweetUnfav etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetUnfav)

  object PromotedTweetReply etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetReply)

  object PromotedTweetRetweet
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetRetweet)

  object PromotedTweetBlockAuthor
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetBlockAuthor)

  object PromotedTweetUnblockAuthor
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetUnblockAuthor)

  object PromotedTweetComposeTweet
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetComposeTweet)

  object PromotedTweetClick etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetClick)

  object PromotedTweetReport etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetReport)

  object PromotedProfileFollow
      etwittertends ProfileAdsCallbackEngagement(ActionType.ServerPromotedProfileFollow)

  object PromotedProfileUnfollow
      etwittertends ProfileAdsCallbackEngagement(ActionType.ServerPromotedProfileUnfollow)

  object PromotedTweetMuteAuthor
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetMuteAuthor)

  object PromotedTweetClickProfile
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetClickProfile)

  object PromotedTweetClickHashtag
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetClickHashtag)

  object PromotedTweetOpenLink
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetOpenLink) {
    override def getItem(input: SpendServerEvent): Option[Item] = {
      input.engagementEvent.flatMap { e =>
        e.impressionData.flatMap { i =>
          getPromotedTweetInfo(
            i.promotedTweetId,
            i.advertiserId,
            tweetActionInfoOpt = Some(
              TweetActionInfo.ServerPromotedTweetOpenLink(
                ServerPromotedTweetOpenLink(url = e.url))))
        }
      }
    }
  }

  object PromotedTweetCarouselSwipeNetwittert
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetCarouselSwipeNetwittert)

  object PromotedTweetCarouselSwipePrevious
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetCarouselSwipePrevious)

  object PromotedTweetLingerImpressionShort
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetLingerImpressionShort)

  object PromotedTweetLingerImpressionMedium
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetLingerImpressionMedium)

  object PromotedTweetLingerImpressionLong
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetLingerImpressionLong)

  object PromotedTweetClickSpotlight
      etwittertends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTweetClickSpotlight)

  object PromotedTweetViewSpotlight
      etwittertends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTweetViewSpotlight)

  object PromotedTrendView
      etwittertends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTrendView)

  object PromotedTrendClick
      etwittertends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTrendClick)

  object PromotedTweetVideoPlayback25
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoPlayback25)

  object PromotedTweetVideoPlayback50
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoPlayback50)

  object PromotedTweetVideoPlayback75
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoPlayback75)

  object PromotedTweetVideoAdPlayback25
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoAdPlayback25)

  object PromotedTweetVideoAdPlayback50
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoAdPlayback50)

  object PromotedTweetVideoAdPlayback75
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoAdPlayback75)

  object TweetVideoAdPlayback25
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerTweetVideoAdPlayback25)

  object TweetVideoAdPlayback50
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerTweetVideoAdPlayback50)

  object TweetVideoAdPlayback75
      etwittertends BaseVideoAdsCallbackEngagement(ActionType.ServerTweetVideoAdPlayback75)

  object PromotedTweetDismissWithoutReason
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissWithoutReason)

  object PromotedTweetDismissUninteresting
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissUninteresting)

  object PromotedTweetDismissRepetitive
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissRepetitive)

  object PromotedTweetDismissSpam
      etwittertends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissSpam)
}
