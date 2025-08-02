package com.twitter.simclusters_v2.scio.bq_generation.simclusters_indetwitter_generation

object Config {
  // Common Root Path
  val RootMHPath: String = "manhattan_sequence_files/simclusters_to_tweet_indetwitter/"
  val RootThriftPath: String = "processed/simclusters_to_tweet_indetwitter/"
  val AdhocRootPath = "adhoc/simclusters_to_tweet_indetwitter/"
  // cluster-to-tweet KeyVal Dataset Output Path
  val FavBasedClusterToTweetIndetwitterOutputPath = "fav_based_indetwitter"
  val FavBasedEvergreenContentClusterToTweetIndetwitterOutputPath = "fav_based_evergreen_indetwitter"
  val FavBasedVideoClusterToTweetIndetwitterOutputPath = "fav_based_video_indetwitter"
  val VideoViewBasedClusterToTweetIndetwitterOutputPath = "video_view_based_indetwitter"
  val RetweetBasedClusterToTweetIndetwitterOutputPath = "retweet_based_indetwitter"
  val ReplyBasedClusterToTweetIndetwitterOutputPath = "reply_based_indetwitter"
  val PushOpenBasedClusterToTweetIndetwitterOutputPath = "push_open_based_indetwitter"
  val AdsFavBasedClusterToTweetIndetwitterOutputPath = "ads_fav_based_indetwitter"
  val AdsFavClickBasedClusterToTweetIndetwitterOutputPath = "ads_fav_click_based_indetwitter"

  // SQL file path
  val simclustersEngagementBasedIndetwitterGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/engagement_based_indetwitter_generation.sql"
  val unifiedUserTweetActionPairGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/unified_user_tweet_action_pair_generation.sql"
  val combinedUserTweetActionPairGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/combined_user_tweet_action_pair_generation.sql"
  val adsUserTweetActionPairGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/ads_user_tweet_action_pair_generation.sql"
  val evergreenContentUserTweetActionPairGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/evergreen_content_user_tweet_action_pair_generation.sql"
  val favBasedVideoTweetActionPairGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/user_video_tweet_fav_engagement_generation.sql"

  // Table name for server/client engagements
  val clientEngagementTableName: String = "twttr-bq-iesource-prod.user.client_engagements"
  val serverEngagementTableName: String = "twttr-bq-iesource-prod.user.server_engagements"

  // Tweet id column names from UUA
  val actionTweetIdColumn: String = "item.tweetInfo.actionTweetId"
  val retweetTweetIdColumn: String = "item.tweetInfo.retweetedTweetId"
  val replyTweetIdColumn: String = "item.tweetInfo.inReplyToTweetId"
  val pushTweetIdColumn: String = "item.notificationInfo.content.tweetNotification.tweetId"

  // Do not enable health or video filters by default
  val enableHealthAndVideoFilters: Boolean = false

  // Do not enable top k tweets per cluster intersection with fav-based clusters
  val enableIntersectionWithFavBasedClusterTopKTweetsIndetwitter: Boolean = false

  // Min fav/interaction threshold
  val minInteractionCount: Int = 50
  val minFavCount: Int = 50

  // Tweet Embeddings configs
  val tweetEmbeddingsLength: Int = 50
  val tweetEmbeddingsHalfLife: Int = 28800000

  // Cluster-to-tweet indetwitter configs
  val clusterTopKTweets: Int = 2000
  val matwitterTweetAgeHours: Int = 24
  val minEngagementPerCluster: Int = 0

  // Placeholder action type for interactions that don't have undo events (e.g. video views)
  val PlaceholderActionType: String = "PLACEHOLDER_ACTION_TYPE"

  // Ads event engagement type ids
  val AdsFavEngagementTypeIds = Seq(8) // Fav promoted tweet
  val AdsClickEngagementTypeIds = Seq(
    1, //URL
    42, // CARD_URL_CLICK
    53, // WEBSITE_CARD_CONTAINER_CLICK
    54, // WEBSITE_CARD_BUTTON_CLICK
    55, // WEBSITE_CARD_IMAGE_CLICK
    56, // WEBSITE_CARD_TITLE_CLICK
    69, // BUYNOW_CARD_CLICK
    70, // BUYNOW_PURCHASE_SUCCESS
    72, // VIDEO_CTA_URL_CLICK
    76, // VIDEO_AD_CTA_URL_CLICK
    80, // VIDEO_CONTENT_CTA_URL_CLICK
    84, // CL_OFFER_CARD_CLICK
  )

}
