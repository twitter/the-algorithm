namespace java com.twitter.usersignalservice.thriftjava
namespace py gen.twitter.usersignalservice.signal
#@namespace scala com.twitter.usersignalservice.thriftscala
#@namespace strato com.twitter.usersignalservice.strato

include "com/twitter/simclusters_v2/identifier.thrift"


enum SignalType {
  /**
  Please maintain the key space rule to avoid compatibility issue for the downstream production job
  * Prod  Key space:  0-1000
  * Devel Key space:  1000+
  **/


  /* tweet based signals */
  TweetFavorite       = 0, // 540 Days Looback window
  Retweet             = 1, // 540 Days Lookback window
  TrafficAttribution  = 2,
  OriginalTweet       = 3, // 540 Days Looback window
  Reply               = 4, // 540 Days Looback window
  /* Tweets that the user shared (sharer side)
    *  V1: successful shares (click share icon -> click in-app, or off-platform share option
    * or copying link)
    * */
  TweetShare_V1       = 5, // 14 Days Lookback window

  TweetFavorite_90D_V2 = 6, // 90 Days Lookback window : tweet fav from user with recent engagement in the past 90 days
  Retweet_90D_V2 = 7, // 90 Days Lookback window : retweet from user with recent engagement in the past 90 days
  OriginalTweet_90D_V2 = 8, // 90 Days Lookback window : original tweet from user with recent engagement in the past 90 days
  Reply_90D_V2 = 9,// 90 Days Lookback window : reply from user with recent engagement in the past 90 days
  GoodTweetClick = 10,// GoodTweetCilick Signal : Dwell Time  Threshold >=2s

  // video tweets that were watched (10s OR 95%) in the past 90 days, are not ads, and have >=10s video
  VideoView_90D_Quality_V1 = 11   // 90 Days Lookback window
  // video tweets that were watched 50% in the past 90 days, are not ads, and have >=10s video
  VideoView_90D_Playback50_V1 = 12   // 90 Days Lookback window

  /* user based signals */
  AccountFollow = 100, // infinite lookback window
  RepeatedProfileVisit_14D_MinVisit2_V1 = 101,
  RepeatedProfileVisit_90D_MinVisit6_V1 = 102,
  RepeatedProfileVisit_180D_MinVisit6_V1 = 109,
  RepeatedProfileVisit_14D_MinVisit2_V1_No_Negative = 110,
  RepeatedProfileVisit_90D_MinVisit6_V1_No_Negative = 111,
  RepeatedProfileVisit_180D_MinVisit6_V1_No_Negative = 112,
  RealGraphOon                          = 104,
  TrafficAttributionProfile_30D_LastVisit = 105,
  TrafficAttributionProfile_30D_DecayedVisit = 106,
  TrafficAttributionProfile_30D_WeightedEventDecayedVisit = 107,
  TrafficAttributionProfile_30D_DecayedVisit_WithoutAgathaFilter = 108,
  GoodProfileClick = 120, // GoodTweetCilick Signal : Dwell Time  Threshold >=10s
  AdFavorite = 121, // Favorites filtered to ads TweetFavorite has both organic and ads Favs

  // AccountFollowWithDelay should only be used by high-traffic clients and has 1 min delay
  AccountFollowWithDelay = 122,


  /* notifications based signals */
  /* V1: notification clicks from past 90 days with negative events (reports, dislikes) being filtered */
  NotificationOpenAndClick_V1 = 200,

  /*
  negative signals for filter
   */
  NegativeEngagedTweetId = 901 // tweetId for all negative engagements
  NegativeEngagedUserId  = 902 // userId for all negative engagements
  AccountBlock = 903,
  AccountMute = 904,
  // skip 905 - 906 for Account report abuse / report spam
  // User clicked dont like from past 90 Days
  TweetDontLike = 907
  // User clicked see fewer on the recommended tweet from past 90 Days
  TweetSeeFewer = 908
  // User clicked on the "report tweet" option in the tweet caret dropdown menu from past 90 days
  TweetReport = 909

  /*
  devel signals
  use the num > 1000 to test out signals under development/ddg
  put it back to the correct corresponding Key space (0-1000) before ship
  */
  GoodTweetClick_5s = 1001,// GoodTweetCilick Signal : Dwell Time  Threshold >=5s
  GoodTweetClick_10s = 1002,// GoodTweetCilick Signal : Dwell Time  Threshold >=10s
  GoodTweetClick_30s = 1003,// GoodTweetCilick Signal : Dwell Time  Threshold >=30s

  GoodProfileClick_20s = 1004,// GoodProfileClick Signal : Dwell Time  Threshold >=20s
  GoodProfileClick_30s = 1005,// GoodProfileClick Signal : Dwell Time  Threshold >=30s

  GoodProfileClick_Filtered = 1006, // GoodProfileClick Signal filtered by blocks and mutes.
  GoodProfileClick_20s_Filtered = 1007// GoodProfileClick Signal : Dwell Time  Threshold >=20s, filtered  byblocks and mutes.
  GoodProfileClick_30s_Filtered = 1008,// GoodProfileClick Signal : Dwell Time  Threshold >=30s, filtered by blocks and mutes.

  /*
  Unified Signals
  These signals are aimed to unify multiple signal fetches into a single response.
  This might be a healthier way for our retrievals layer to run inference on.
   */
   TweetBasedUnifiedUniformSignal = 1300
   TweetBasedUnifiedEngagementWeightedSignal = 1301
   TweetBasedUnifiedQualityWeightedSignal = 1302
   ProducerBasedUnifiedUniformSignal = 1303
   ProducerBasedUnifiedEngagementWeightedSignal = 1304
   ProducerBasedUnifiedQualityWeightedSignal = 1305

}

struct Signal {
  1: required SignalType signalType
  2: required i64 timestamp
  3: optional identifier.InternalId targetInternalId
}
