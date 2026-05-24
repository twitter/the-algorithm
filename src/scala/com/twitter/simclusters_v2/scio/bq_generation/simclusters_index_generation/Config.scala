package com.twitter.simclusters_v2.scio.bq_generation.simclusters_index_generation

object Config {
  // Common Root Path
  val RootMHPath: String = "manhattan_sequence_files/simclusters_to_tweet_index/"
  val RootThriftPath: String = "processed/simclusters_to_tweet_index/"
  val AdhocRootPath = "adhoc/simclusters_to_tweet_index/"
  // cluster-to-tweet KeyVal Dataset Output Path
  val FavBasedClusterToTweetIndexOutputPath = "fav_based_index"
  val FavBasedEvergreenContentClusterToTweetIndexOutputPath = "fav_based_evergreen_index"
  val FavBasedVideoClusterToTweetIndexOutputPath = "fav_based_video_index"
  val VideoViewBasedClusterToTweetIndexOutputPath = "video_view_based_index"
  val RetweetBasedClusterToTweetIndexOutputPath = "retweet_based_index"
  val ReplyBasedClusterToTweetIndexOutputPath = "reply_based_index"
  val PushOpenBasedClusterToTweetIndexOutputPath = "push_open_based_index"
  val AdsFavBasedClusterToTweetIndexOutputPath = "ads_fav_based_index"
  val AdsFavClickBasedClusterToTweetIndexOutputPath = "ads_fav_click_based_index"

  // SQL file path
  val simclustersEngagementBasedIndexGenerationSQLPath =
    s"/com/twitter/simclusters_v2/scio/bq_generation/sql/engagement_based_index_generation.sql"
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
  val enableIntersectionWithFavBasedClusterTopKTweetsIndex: Boolean = false

  // Min fav/interaction threshold
  val minInteractionCount: Int = 50
  val minFavCount: Int = 50

  // Tweet Embeddings configs
  val tweetEmbeddingsLength: Int = 50
  val tweetEmbeddingsHalfLife: Int = 28800000

  // Cluster-to-tweet index configs
  val clusterTopKTweets: Int = 2000
  val maxTweetAgeHours: Int = 24
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
