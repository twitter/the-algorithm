package com.twitter.timelines.prediction.features.itl

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature.Binary
import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.Feature.Discrete
import com.twitter.ml.api.Feature.SparseBinary
import scala.collection.JavaConverters._

object ITLFeatures {
  // engagement
  val IS_RETWEETED =
    new Binary("itl.engagement.is_retweeted", Set(PublicRetweets, PrivateRetweets).asJava)
  val IS_FAVORITED =
    new Binary("itl.engagement.is_favorited", Set(PublicLikes, PrivateLikes).asJava)
  val IS_REPLIED =
    new Binary("itl.engagement.is_replied", Set(PublicReplies, PrivateReplies).asJava)
  // v1: post click engagements: fav, reply
  val IS_GOOD_CLICKED_CONVO_DESC_V1 = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_favorited_or_replied",
    Set(
      PublicLikes,
      PrivateLikes,
      PublicReplies,
      PrivateReplies,
      EngagementsPrivate,
      EngagementsPublic).asJava)
  // v2: post click engagements: click
  val IS_GOOD_CLICKED_CONVO_DESC_V2 = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_v2",
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_GOOD_CLICKED_CONVO_DESC_FAVORITED = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_favorited",
    Set(PublicLikes, PrivateLikes).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_REPLIED = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_replied",
    Set(PublicReplies, PrivateReplies).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_RETWEETED = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_retweeted",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_CLICKED = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_clicked",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_FOLLOWED =
    new Binary("itl.engagement.is_good_clicked_convo_desc_followed", Set(EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_SHARE_DM_CLICKED = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_share_dm_clicked",
    Set(EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_PROFILE_CLICKED = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_profile_clicked",
    Set(EngagementsPrivate).asJava)

  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_0 = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_uam_gt_0",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_1 = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_uam_gt_1",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_2 = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_uam_gt_2",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_3 = new Binary(
    "itl.engagement.is_good_clicked_convo_desc_uam_gt_3",
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  val IS_TWEET_DETAIL_DWELLED = new Binary(
    "itl.engagement.is_tweet_detail_dwelled",
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_TWEET_DETAIL_DWELLED_8_SEC = new Binary(
    "itl.engagement.is_tweet_detail_dwelled_8_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_15_SEC = new Binary(
    "itl.engagement.is_tweet_detail_dwelled_15_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_25_SEC = new Binary(
    "itl.engagement.is_tweet_detail_dwelled_25_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_30_SEC = new Binary(
    "itl.engagement.is_tweet_detail_dwelled_30_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_PROFILE_DWELLED = new Binary(
    "itl.engagement.is_profile_dwelled",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_10_SEC = new Binary(
    "itl.engagement.is_profile_dwelled_10_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_20_SEC = new Binary(
    "itl.engagement.is_profile_dwelled_20_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_30_SEC = new Binary(
    "itl.engagement.is_profile_dwelled_30_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED = new Binary(
    "itl.engagement.is_fullscreen_video_dwelled",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_5_SEC = new Binary(
    "itl.engagement.is_fullscreen_video_dwelled_5_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_10_SEC = new Binary(
    "itl.engagement.is_fullscreen_video_dwelled_10_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_20_SEC = new Binary(
    "itl.engagement.is_fullscreen_video_dwelled_20_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_30_SEC = new Binary(
    "itl.engagement.is_fullscreen_video_dwelled_30_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_15_SEC = new Binary(
    "itl.engagement.is_link_dwelled_15_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_30_SEC = new Binary(
    "itl.engagement.is_link_dwelled_30_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_60_SEC = new Binary(
    "itl.engagement.is_link_dwelled_60_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_QUOTED =
    new Binary("itl.engagement.is_quoted", Set(PublicRetweets, PrivateRetweets).asJava)
  val IS_RETWEETED_WITHOUT_QUOTE = new Binary(
    "itl.engagement.is_retweeted_without_quote",
    Set(PublicRetweets, PrivateRetweets).asJava)
  val IS_CLICKED = new Binary(
    "itl.engagement.is_clicked",
    Set(EngagementsPrivate, TweetsClicked, LinksClickedOn).asJava)
  val IS_PROFILE_CLICKED = new Binary(
    "itl.engagement.is_profile_clicked",
    Set(EngagementsPrivate, TweetsClicked, ProfilesViewed, ProfilesClicked).asJava)
  val IS_DWELLED = new Binary("itl.engagement.is_dwelled", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_IN_BOUNDS_V1 =
    new Binary("itl.engagement.is_dwelled_in_bounds_v1", Set(EngagementsPrivate).asJava)
  val DWELL_NORMALIZED_OVERALL =
    new Continuous("itl.engagement.dwell_normalized_overall", Set(EngagementsPrivate).asJava)
  val DWELL_CDF_OVERALL =
    new Continuous("itl.engagement.dwell_cdf_overall", Set(EngagementsPrivate).asJava)
  val DWELL_CDF = new Continuous("itl.engagement.dwell_cdf", Set(EngagementsPrivate).asJava)

  val IS_DWELLED_1S = new Binary("itl.engagement.is_dwelled_1s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_2S = new Binary("itl.engagement.is_dwelled_2s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_3S = new Binary("itl.engagement.is_dwelled_3s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_4S = new Binary("itl.engagement.is_dwelled_4s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_5S = new Binary("itl.engagement.is_dwelled_5s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_6S = new Binary("itl.engagement.is_dwelled_6s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_7S = new Binary("itl.engagement.is_dwelled_7s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_8S = new Binary("itl.engagement.is_dwelled_8s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_9S = new Binary("itl.engagement.is_dwelled_9s", Set(EngagementsPrivate).asJava)
  val IS_DWELLED_10S = new Binary("itl.engagement.is_dwelled_10s", Set(EngagementsPrivate).asJava)

  val IS_SKIPPED_1S = new Binary("itl.engagement.is_skipped_1s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_2S = new Binary("itl.engagement.is_skipped_2s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_3S = new Binary("itl.engagement.is_skipped_3s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_4S = new Binary("itl.engagement.is_skipped_4s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_5S = new Binary("itl.engagement.is_skipped_5s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_6S = new Binary("itl.engagement.is_skipped_6s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_7S = new Binary("itl.engagement.is_skipped_7s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_8S = new Binary("itl.engagement.is_skipped_8s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_9S = new Binary("itl.engagement.is_skipped_9s", Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_10S = new Binary("itl.engagement.is_skipped_10s", Set(EngagementsPrivate).asJava)

  val IS_FOLLOWED =
    new Binary("itl.engagement.is_followed", Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_IMPRESSED = new Binary("itl.engagement.is_impressed", Set(EngagementsPrivate).asJava)
  val IS_OPEN_LINKED =
    new Binary("itl.engagement.is_open_linked", Set(EngagementsPrivate, LinksClickedOn).asJava)
  val IS_PHOTO_EXPANDED = new Binary(
    "itl.engagement.is_photo_expanded",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_VIDEO_VIEWED =
    new Binary("itl.engagement.is_video_viewed", Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_VIDEO_PLAYBACK_50 = new Binary(
    "itl.engagement.is_video_playback_50",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_VIDEO_QUALITY_VIEWED = new Binary(
    "itl.engagement.is_video_quality_viewed",
    Set(EngagementsPrivate, EngagementsPublic).asJava
  ) 
  val IS_BOOKMARKED =
    new Binary("itl.engagement.is_bookmarked", Set(EngagementsPrivate).asJava)
  val IS_SHARED =
    new Binary("itl.engagement.is_shared", Set(EngagementsPrivate).asJava)
  val IS_SHARE_MENU_CLICKED =
    new Binary("itl.engagement.is_share_menu_clicked", Set(EngagementsPrivate).asJava)

  // Negative engagements
  val IS_DONT_LIKE =
    new Binary("itl.engagement.is_dont_like", Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_BLOCK_CLICKED = new Binary(
    "itl.engagement.is_block_clicked",
    Set(TweetsClicked, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_BLOCK_DIALOG_BLOCKED = new Binary(
    "itl.engagement.is_block_dialog_blocked",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_MUTE_CLICKED =
    new Binary("itl.engagement.is_mute_clicked", Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_MUTE_DIALOG_MUTED =
    new Binary("itl.engagement.is_mute_dialog_muted", Set(EngagementsPrivate).asJava)
  val IS_REPORT_TWEET_CLICKED = new Binary(
    "itl.engagement.is_report_tweet_clicked",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_CARET_CLICKED =
    new Binary("itl.engagement.is_caret_clicked", Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_NOT_ABOUT_TOPIC =
    new Binary("itl.engagement.is_not_about_topic", Set(EngagementsPrivate).asJava)
  val IS_NOT_RECENT =
    new Binary("itl.engagement.is_not_recent", Set(EngagementsPrivate).asJava)
  val IS_NOT_RELEVANT =
    new Binary("itl.engagement.is_not_relevant", Set(EngagementsPrivate).asJava)
  val IS_SEE_FEWER =
    new Binary("itl.engagement.is_see_fewer", Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC =
    new Binary("itl.engagement.is_unfollow_topic", Set(EngagementsPrivate).asJava)
  val IS_FOLLOW_TOPIC =
    new Binary("itl.engagement.is_follow_topic", Set(EngagementsPrivate).asJava)
  val IS_NOT_INTERESTED_IN_TOPIC =
    new Binary("itl.engagement.is_not_interested_in_topic", Set(EngagementsPrivate).asJava)
  val IS_HOME_LATEST_VISITED =
    new Binary("itl.engagement.is_home_latest_visited", Set(EngagementsPrivate).asJava)

  // This derived label is the logical OR of IS_DONT_LIKE, IS_BLOCK_CLICKED, IS_MUTE_CLICKED and IS_REPORT_TWEET_CLICKED
  val IS_NEGATIVE_FEEDBACK =
    new Binary("itl.engagement.is_negative_feedback", Set(EngagementsPrivate).asJava)

  // Reciprocal engagements for reply forward engagement
  val IS_REPLIED_REPLY_IMPRESSED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_impressed_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_FAVORITED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_favorited_by_author",
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_QUOTED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_quoted_by_author",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_REPLIED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_replied_by_author",
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_RETWEETED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_retweeted_by_author",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_BLOCKED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_blocked_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_FOLLOWED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_followed_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_UNFOLLOWED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_unfollowed_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_MUTED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_muted_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_REPORTED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_reported_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  // This derived label is the logical OR of REPLY_REPLIED, REPLY_FAVORITED, REPLY_RETWEETED
  val IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR = new Binary(
    "itl.engagement.is_replied_reply_engaged_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  // Reciprocal engagements for fav forward engagement
  val IS_FAVORITED_FAV_FAVORITED_BY_AUTHOR = new Binary(
    "itl.engagement.is_favorited_fav_favorited_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateLikes, PublicLikes).asJava
  )
  val IS_FAVORITED_FAV_REPLIED_BY_AUTHOR = new Binary(
    "itl.engagement.is_favorited_fav_replied_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateReplies, PublicReplies).asJava
  )
  val IS_FAVORITED_FAV_RETWEETED_BY_AUTHOR = new Binary(
    "itl.engagement.is_favorited_fav_retweeted_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava
  )
  val IS_FAVORITED_FAV_FOLLOWED_BY_AUTHOR = new Binary(
    "itl.engagement.is_favorited_fav_followed_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava
  )
  // This derived label is the logical OR of FAV_REPLIED, FAV_FAVORITED, FAV_RETWEETED, FAV_FOLLOWED
  val IS_FAVORITED_FAV_ENGAGED_BY_AUTHOR = new Binary(
    "itl.engagement.is_favorited_fav_engaged_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava
  )

  // define good profile click by considering following engagements (follow, fav, reply, retweet, etc.) at profile page
  val IS_PROFILE_CLICKED_AND_PROFILE_FOLLOW = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_follow",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, Follow).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_FAV = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_fav",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, PrivateLikes, PublicLikes).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_REPLY = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_reply",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, PrivateReplies, PublicReplies).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_RETWEET = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_retweet",
    Set(
      ProfilesViewed,
      ProfilesClicked,
      EngagementsPrivate,
      PrivateRetweets,
      PublicRetweets).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_TWEET_CLICK = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_tweet_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, TweetsClicked).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_SHARE_DM_CLICK = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_share_dm_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // This derived label is the union of all binary features above
  val IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_engaged",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, EngagementsPublic).asJava)

  // define bad profile click by considering following engagements (user report, tweet report, mute, block, etc) at profile page
  val IS_PROFILE_CLICKED_AND_PROFILE_USER_REPORT_CLICK = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_user_report_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_TWEET_REPORT_CLICK = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_tweet_report_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_MUTE = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_mute",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_BLOCK = new Binary(
    "itl.engagement.is_profile_clicked_and_profile_block",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // This derived label is the union of bad profile click engagements and existing negative feedback
  val IS_NEGATIVE_FEEDBACK_V2 = new Binary(
    "itl.engagement.is_negative_feedback_v2",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // engagement for following user from any surface area
  val IS_FOLLOWED_FROM_ANY_SURFACE_AREA = new Binary(
    "itl.engagement.is_followed_from_any_surface_area",
    Set(EngagementsPublic, EngagementsPrivate).asJava)

  // Relevance prompt tweet engagements
  val IS_RELEVANCE_PROMPT_YES_CLICKED =
    new Binary("itl.engagement.is_relevance_prompt_yes_clicked", Set(EngagementsPrivate).asJava)

  // Reply downvote engagements
  val IS_REPLY_DOWNVOTED =
    new Binary("itl.engagement.is_reply_downvoted", Set(EngagementsPrivate).asJava)
  val IS_REPLY_DOWNVOTE_REMOVED =
    new Binary("itl.engagement.is_reply_downvote_removed", Set(EngagementsPrivate).asJava)

  // features from RecommendedTweet
  val RECTWEET_SCORE = new Continuous("itl.recommended_tweet_features.rectweet_score")
  val NUM_FAVORITING_USERS = new Continuous("itl.recommended_tweet_features.num_favoriting_users")
  val NUM_FOLLOWING_USERS = new Continuous("itl.recommended_tweet_features.num_following_users")
  val CONTENT_SOURCE_TYPE = new Discrete("itl.recommended_tweet_features.content_source_type")

  val RECOS_SCORE = new Continuous(
    "itl.recommended_tweet_features.recos_score",
    Set(EngagementScore, UsersRealGraphScore, UsersSalsaScore).asJava)
  val AUTHOR_REALGRAPH_SCORE = new Continuous(
    "itl.recommended_tweet_features.realgraph_score",
    Set(UsersRealGraphScore).asJava)
  val AUTHOR_SARUS_SCORE = new Continuous(
    "itl.recommended_tweet_features.sarus_score",
    Set(EngagementScore, UsersSalsaScore).asJava)

  val NUM_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.num_interacting_users",
    Set(EngagementScore).asJava
  )
  val MAX_REALGRAPH_SCORE_OF_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.max_realgraph_score_of_interacting_users",
    Set(UsersRealGraphScore, EngagementScore).asJava
  )
  val SUM_REALGRAPH_SCORE_OF_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.sum_realgraph_score_of_interacting_users",
    Set(UsersRealGraphScore, EngagementScore).asJava
  )
  val AVG_REALGRAPH_SCORE_OF_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.avg_realgraph_score_of_interacting_users",
    Set(UsersRealGraphScore, EngagementScore).asJava
  )
  val MAX_SARUS_SCORE_OF_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.max_sarus_score_of_interacting_users",
    Set(EngagementScore, UsersSalsaScore).asJava
  )
  val SUM_SARUS_SCORE_OF_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.sum_sarus_score_of_interacting_users",
    Set(EngagementScore, UsersSalsaScore).asJava
  )
  val AVG_SARUS_SCORE_OF_INTERACTING_USERS = new Continuous(
    "itl.recommended_tweet_features.avg_sarus_score_of_interacting_users",
    Set(EngagementScore, UsersSalsaScore).asJava
  )

  val NUM_INTERACTING_FOLLOWINGS = new Continuous(
    "itl.recommended_tweet_features.num_interacting_followings",
    Set(EngagementScore).asJava
  )

  // features from HydratedTweetFeatures
  val REAL_GRAPH_WEIGHT =
    new Continuous("itl.hydrated_tweet_features.real_graph_weight", Set(UsersRealGraphScore).asJava)
  val SARUS_GRAPH_WEIGHT = new Continuous("itl.hydrated_tweet_features.sarus_graph_weight")
  val FROM_TOP_ENGAGED_USER = new Binary("itl.hydrated_tweet_features.from_top_engaged_user")
  val FROM_TOP_INFLUENCER = new Binary("itl.hydrated_tweet_features.from_top_influencer")
  val TOPIC_SIM_SEARCHER_INTERSTED_IN_AUTHOR_KNOWN_FOR = new Continuous(
    "itl.hydrated_tweet_features.topic_sim_searcher_interested_in_author_known_for"
  )
  val TOPIC_SIM_SEARCHER_AUTHOR_BOTH_INTERESTED_IN = new Continuous(
    "itl.hydrated_tweet_features.topic_sim_searcher_author_both_interested_in"
  )
  val TOPIC_SIM_SEARCHER_AUTHOR_BOTH_KNOWN_FOR = new Continuous(
    "itl.hydrated_tweet_features.topic_sim_searcher_author_both_known_for"
  )
  val USER_REP = new Continuous("itl.hydrated_tweet_features.user_rep")
  val NORMALIZED_PARUS_SCORE = new Continuous("itl.hydrated_tweet_features.normalized_parus_score")
  val CONTAINS_MEDIA = new Binary("itl.hydrated_tweet_features.contains_media")
  val FROM_NEARBY = new Binary("itl.hydrated_tweet_features.from_nearby")
  val TOPIC_SIM_SEARCHER_INTERESTED_IN_TWEET = new Continuous(
    "itl.hydrated_tweet_features.topic_sim_searcher_interested_in_tweet"
  )
  val MATCHES_UI_LANG = new Binary(
    "itl.hydrated_tweet_features.matches_ui_lang",
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val MATCHES_SEARCHER_MAIN_LANG = new Binary(
    "itl.hydrated_tweet_features.matches_searcher_main_lang",
    Set(ProvidedLanguage, InferredLanguage).asJava
  )
  val MATCHES_SEARCHER_LANGS = new Binary(
    "itl.hydrated_tweet_features.matches_searcher_langs",
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val HAS_CARD = new Binary(
    "itl.hydrated_tweet_features.has_card",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_IMAGE = new Binary(
    "itl.hydrated_tweet_features.has_image",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_NATIVE_IMAGE = new Binary(
    "itl.hydrated_tweet_features.has_native_image",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_VIDEO = new Binary("itl.hydrated_tweet_features.has_video")
  val HAS_CONSUMER_VIDEO = new Binary(
    "itl.hydrated_tweet_features.has_consumer_video",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_PRO_VIDEO = new Binary(
    "itl.hydrated_tweet_features.has_pro_video",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_PERISCOPE = new Binary(
    "itl.hydrated_tweet_features.has_periscope",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_VINE = new Binary(
    "itl.hydrated_tweet_features.has_vine",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_NATIVE_VIDEO = new Binary(
    "itl.hydrated_tweet_features.has_native_video",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_LINK = new Binary(
    "itl.hydrated_tweet_features.has_link",
    Set(UrlFoundFlag, PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val LINK_COUNT = new Continuous(
    "itl.hydrated_tweet_features.link_count",
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val URL_DOMAINS = new SparseBinary(
    "itl.hydrated_tweet_features.url_domains",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_VISIBLE_LINK = new Binary(
    "itl.hydrated_tweet_features.has_visible_link",
    Set(UrlFoundFlag, PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_NEWS = new Binary(
    "itl.hydrated_tweet_features.has_news",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_TREND = new Binary(
    "itl.hydrated_tweet_features.has_trend",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val BLENDER_SCORE =
    new Continuous("itl.hydrated_tweet_features.blender_score", Set(EngagementScore).asJava)
  val PARUS_SCORE =
    new Continuous("itl.hydrated_tweet_features.parus_score", Set(EngagementScore).asJava)
  val TEXT_SCORE =
    new Continuous("itl.hydrated_tweet_features.text_score", Set(EngagementScore).asJava)
  val BIDIRECTIONAL_REPLY_COUNT = new Continuous(
    "itl.hydrated_tweet_features.bidirectional_reply_count",
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava
  )
  val UNIDIRECTIONAL_REPLY_COUNT = new Continuous(
    "itl.hydrated_tweet_features.unidirectional_reply_count",
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava
  )
  val BIDIRECTIONAL_RETWEET_COUNT = new Continuous(
    "itl.hydrated_tweet_features.bidirectional_retweet_count",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava
  )
  val UNIDIRECTIONAL_RETWEET_COUNT = new Continuous(
    "itl.hydrated_tweet_features.unidirectional_retweet_count",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava
  )
  val BIDIRECTIONAL_FAV_COUNT = new Continuous(
    "itl.hydrated_tweet_features.bidirectional_fav_count",
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava
  )
  val UNIDIRECTIONAL_FAV_COUNT = new Continuous(
    "itl.hydrated_tweet_features.unidirectional_fav_count",
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava
  )
  val CONVERSATION_COUNT = new Continuous("itl.hydrated_tweet_features.conversation_count")
  val FAV_COUNT = new Continuous(
    "itl.hydrated_tweet_features.fav_count",
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val REPLY_COUNT = new Continuous(
    "itl.hydrated_tweet_features.reply_count",
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val RETWEET_COUNT = new Continuous(
    "itl.hydrated_tweet_features.retweet_count",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val PREV_USER_TWEET_ENGAGEMENT = new Continuous(
    "itl.hydrated_tweet_features.prev_user_tweet_enagagement",
    Set(EngagementScore, EngagementsPrivate, EngagementsPublic).asJava
  )
  val IS_SENSITIVE = new Binary("itl.hydrated_tweet_features.is_sensitive")
  val HAS_MULTIPLE_MEDIA = new Binary(
    "itl.hydrated_tweet_features.has_multiple_media",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_MULTIPLE_HASHTAGS_OR_TRENDS = new Binary(
    "itl.hydrated_tweet_features.has_multiple_hashtag_or_trend",
    Set(
      UserVisibleFlag,
      CountOfPrivateTweetEntitiesAndMetadata,
      CountOfPublicTweetEntitiesAndMetadata).asJava)
  val IS_AUTHOR_PROFILE_EGG =
    new Binary("itl.hydrated_tweet_features.is_author_profile_egg", Set(ProfileImage).asJava)
  val IS_AUTHOR_NEW =
    new Binary("itl.hydrated_tweet_features.is_author_new", Set(UserType, UserState).asJava)
  val NUM_MENTIONS = new Continuous(
    "itl.hydrated_tweet_features.num_mentions",
    Set(
      UserVisibleFlag,
      CountOfPrivateTweetEntitiesAndMetadata,
      CountOfPublicTweetEntitiesAndMetadata).asJava)
  val NUM_HASHTAGS = new Continuous(
    "itl.hydrated_tweet_features.num_hashtags",
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val LANGUAGE = new Discrete(
    "itl.hydrated_tweet_features.language",
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val LINK_LANGUAGE = new Continuous(
    "itl.hydrated_tweet_features.link_language",
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val IS_AUTHOR_NSFW =
    new Binary("itl.hydrated_tweet_features.is_author_nsfw", Set(UserType).asJava)
  val IS_AUTHOR_SPAM =
    new Binary("itl.hydrated_tweet_features.is_author_spam", Set(UserType).asJava)
  val IS_AUTHOR_BOT = new Binary("itl.hydrated_tweet_features.is_author_bot", Set(UserType).asJava)
  val IS_OFFENSIVE = new Binary("itl.hydrated_tweet_features.is_offensive")
  val FROM_VERIFIED_ACCOUNT =
    new Binary("itl.hydrated_tweet_features.from_verified_account", Set(UserVerifiedFlag).asJava)
  val EMBEDS_IMPRESSION_COUNT = new Continuous(
    "itl.hydrated_tweet_features.embeds_impression_count",
    Set(CountOfImpression).asJava)
  val EMBEDS_URL_COUNT =
    new Continuous("itl.hydrated_tweet_features.embeds_url_count", Set(UrlFoundFlag).asJava)
  val FAV_COUNT_V2 = new Continuous(
    "recap.earlybird.fav_count_v2",
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val RETWEET_COUNT_V2 = new Continuous(
    "recap.earlybird.retweet_count_v2",
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val REPLY_COUNT_V2 = new Continuous(
    "recap.earlybird.reply_count_v2",
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
}
