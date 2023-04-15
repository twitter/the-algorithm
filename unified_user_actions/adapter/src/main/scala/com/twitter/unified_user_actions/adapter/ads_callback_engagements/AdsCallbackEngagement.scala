package com.twitter.unified_user_actions.adapter.ads_callback_engagements

import com.twitter.ads.spendserver.thriftscala.SpendServerEvent
import com.twitter.unified_user_actions.thriftscala._

object AdsCallbackEngagement {
  object PromotedTweetFav extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetFav)

  object PromotedTweetUnfav extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetUnfav)

  object PromotedTweetReply extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetReply)

  object PromotedTweetRetweet
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetRetweet)

  object PromotedTweetBlockAuthor
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetBlockAuthor)

  object PromotedTweetUnblockAuthor
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetUnblockAuthor)

  object PromotedTweetComposeTweet
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetComposeTweet)

  object PromotedTweetClick extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetClick)

  object PromotedTweetReport extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetReport)

  object PromotedProfileFollow
      extends ProfileAdsCallbackEngagement(ActionType.ServerPromotedProfileFollow)

  object PromotedProfileUnfollow
      extends ProfileAdsCallbackEngagement(ActionType.ServerPromotedProfileUnfollow)

  object PromotedTweetMuteAuthor
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetMuteAuthor)

  object PromotedTweetClickProfile
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetClickProfile)

  object PromotedTweetClickHashtag
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetClickHashtag)

  object PromotedTweetOpenLink
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetOpenLink) {
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

  object PromotedTweetCarouselSwipeNext
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetCarouselSwipeNext)

  object PromotedTweetCarouselSwipePrevious
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetCarouselSwipePrevious)

  object PromotedTweetLingerImpressionShort
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetLingerImpressionShort)

  object PromotedTweetLingerImpressionMedium
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetLingerImpressionMedium)

  object PromotedTweetLingerImpressionLong
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetLingerImpressionLong)

  object PromotedTweetClickSpotlight
      extends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTweetClickSpotlight)

  object PromotedTweetViewSpotlight
      extends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTweetViewSpotlight)

  object PromotedTrendView
      extends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTrendView)

  object PromotedTrendClick
      extends BaseTrendAdsCallbackEngagement(ActionType.ServerPromotedTrendClick)

  object PromotedTweetVideoPlayback25
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoPlayback25)

  object PromotedTweetVideoPlayback50
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoPlayback50)

  object PromotedTweetVideoPlayback75
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoPlayback75)

  object PromotedTweetVideoAdPlayback25
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoAdPlayback25)

  object PromotedTweetVideoAdPlayback50
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoAdPlayback50)

  object PromotedTweetVideoAdPlayback75
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerPromotedTweetVideoAdPlayback75)

  object TweetVideoAdPlayback25
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerTweetVideoAdPlayback25)

  object TweetVideoAdPlayback50
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerTweetVideoAdPlayback50)

  object TweetVideoAdPlayback75
      extends BaseVideoAdsCallbackEngagement(ActionType.ServerTweetVideoAdPlayback75)

  object PromotedTweetDismissWithoutReason
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissWithoutReason)

  object PromotedTweetDismissUninteresting
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissUninteresting)

  object PromotedTweetDismissRepetitive
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissRepetitive)

  object PromotedTweetDismissSpam
      extends BaseAdsCallbackEngagement(ActionType.ServerPromotedTweetDismissSpam)
}
