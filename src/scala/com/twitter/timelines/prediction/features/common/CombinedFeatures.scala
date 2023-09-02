package com.twitter.timelines.prediction.features.common

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureType
import com.twitter.ml.api.Feature.Binary
import java.lang.{Boolean => JBoolean}
import scala.collection.JavaConverters._

object CombinedFeatures {
  val IS_CLICKED =
    new Binary("timelines.engagement.is_clicked", Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_DWELLED =
    new Binary("timelines.engagement.is_dwelled", Set(TweetsViewed, EngagementsPrivate).asJava)
  val IS_DWELLED_IN_BOUNDS_V1 = new Binary(
    "timelines.engagement.is_dwelled_in_bounds_v1",
    Set(TweetsViewed, EngagementsPrivate).asJava)
  val IS_FAVORITED = new Binary(
    "timelines.engagement.is_favorited",
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_FOLLOWED = new Binary(
    "timelines.engagement.is_followed",
    Set(EngagementsPrivate, EngagementsPublic, Follow).asJava)
  val IS_IMPRESSED =
    new Binary("timelines.engagement.is_impressed", Set(TweetsViewed, EngagementsPrivate).asJava)
  val IS_OPEN_LINKED = new Binary(
    "timelines.engagement.is_open_linked",
    Set(EngagementsPrivate, LinksClickedOn).asJava)
  val IS_PHOTO_EXPANDED = new Binary(
    "timelines.engagement.is_photo_expanded",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED = new Binary(
    "timelines.engagement.is_profile_clicked",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_QUOTED = new Binary(
    "timelines.engagement.is_quoted",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED = new Binary(
    "timelines.engagement.is_replied",
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_RETWEETED = new Binary(
    "timelines.engagement.is_retweeted",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_RETWEETED_WITHOUT_QUOTE = new Binary(
    "timelines.enagagement.is_retweeted_without_quote",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_SHARE_DM_CLICKED =
    new Binary("timelines.engagement.is_tweet_share_dm_clicked", Set(EngagementsPrivate).asJava)
  val IS_SHARE_DM_SENT =
    new Binary("timelines.engagement.is_tweet_share_dm_sent", Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_25 = new Binary(
    "timelines.engagement.is_video_playback_25",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_50 = new Binary(
    "timelines.engagement.is_video_playback_50",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_75 = new Binary(
    "timelines.engagement.is_video_playback_75",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_95 = new Binary(
    "timelines.engagement.is_video_playback_95",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_COMPLETE = new Binary(
    "timelines.engagement.is_video_playback_complete",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_START = new Binary(
    "timelines.engagement.is_video_playback_start",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_VIEWED = new Binary(
    "timelines.engagement.is_video_viewed",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava)
  val IS_VIDEO_QUALITY_VIEWED = new Binary(
    "timelines.engagement.is_video_quality_viewed",
    Set(MediaEngagementActivities, EngagementsPrivate).asJava
  ) 
  // v1: post click engagements: fav, reply
  val IS_GOOD_CLICKED_CONVO_DESC_V1 = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_favorited_or_replied",
    Set(
      TweetsClicked,
      PublicLikes,
      PrivateLikes,
      PublicReplies,
      PrivateReplies,
      EngagementsPrivate,
      EngagementsPublic).asJava)
  // v2: post click engagements: click
  val IS_GOOD_CLICKED_CONVO_DESC_V2 = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_v2",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_WITH_DWELL_SUM_GTE_60S = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_favorited_or_replied_or_dwell_sum_gte_60_secs",
    Set(
      TweetsClicked,
      PublicLikes,
      PrivateLikes,
      PublicReplies,
      PrivateReplies,
      EngagementsPrivate,
      EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_FAVORITED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_favorited",
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_REPLIED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_replied",
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_RETWEETED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_retweeted",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_CLICKED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_clicked",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_FOLLOWED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_followed",
    Set(EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_SHARE_DM_CLICKED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_share_dm_clicked",
    Set(EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_PROFILE_CLICKED = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_profile_clicked",
    Set(EngagementsPrivate).asJava)

  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_0 = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_uam_gt_0",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_1 = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_uam_gt_1",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_2 = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_uam_gt_2",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_3 = new Binary(
    "timelines.engagement.is_good_clicked_convo_desc_uam_gt_3",
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  val IS_TWEET_DETAIL_DWELLED = new Binary(
    "timelines.engagement.is_tweet_detail_dwelled",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_8_SEC = new Binary(
    "timelines.engagement.is_tweet_detail_dwelled_8_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_15_SEC = new Binary(
    "timelines.engagement.is_tweet_detail_dwelled_15_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_25_SEC = new Binary(
    "timelines.engagement.is_tweet_detail_dwelled_25_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_30_SEC = new Binary(
    "timelines.engagement.is_tweet_detail_dwelled_30_sec",
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_PROFILE_DWELLED = new Binary(
    "timelines.engagement.is_profile_dwelled",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_10_SEC = new Binary(
    "timelines.engagement.is_profile_dwelled_10_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_20_SEC = new Binary(
    "timelines.engagement.is_profile_dwelled_20_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_30_SEC = new Binary(
    "timelines.engagement.is_profile_dwelled_30_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED = new Binary(
    "timelines.engagement.is_fullscreen_video_dwelled",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_5_SEC = new Binary(
    "timelines.engagement.is_fullscreen_video_dwelled_5_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_10_SEC = new Binary(
    "timelines.engagement.is_fullscreen_video_dwelled_10_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_20_SEC = new Binary(
    "timelines.engagement.is_fullscreen_video_dwelled_20_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_30_SEC = new Binary(
    "timelines.engagement.is_fullscreen_video_dwelled_30_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_15_SEC = new Binary(
    "timelines.engagement.is_link_dwelled_15_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_30_SEC = new Binary(
    "timelines.engagement.is_link_dwelled_30_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_60_SEC = new Binary(
    "timelines.engagement.is_link_dwelled_60_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_HOME_LATEST_VISITED =
    new Binary("timelines.engagement.is_home_latest_visited", Set(EngagementsPrivate).asJava)

  val IS_BOOKMARKED =
    new Binary("timelines.engagement.is_bookmarked", Set(EngagementsPrivate).asJava)
  val IS_SHARED =
    new Binary("timelines.engagement.is_shared", Set(EngagementsPrivate).asJava)
  val IS_SHARE_MENU_CLICKED =
    new Binary("timelines.engagement.is_share_menu_clicked", Set(EngagementsPrivate).asJava)

  // Negative engagements
  val IS_DONT_LIKE = new Binary("timelines.engagement.is_dont_like", Set(EngagementsPrivate).asJava)
  val IS_BLOCK_CLICKED = new Binary(
    "timelines.engagement.is_block_clicked",
    Set(Blocks, TweetsClicked, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_BLOCK_DIALOG_BLOCKED = new Binary(
    "timelines.engagement.is_block_dialog_blocked",
    Set(Blocks, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_MUTE_CLICKED = new Binary(
    "timelines.engagement.is_mute_clicked",
    Set(Mutes, TweetsClicked, EngagementsPrivate).asJava)
  val IS_MUTE_DIALOG_MUTED =
    new Binary("timelines.engagement.is_mute_dialog_muted", Set(Mutes, EngagementsPrivate).asJava)
  val IS_REPORT_TWEET_CLICKED = new Binary(
    "timelines.engagement.is_report_tweet_clicked",
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_CARET_CLICKED =
    new Binary("timelines.engagement.is_caret_clicked", Set(EngagementsPrivate).asJava)
  val IS_NOT_ABOUT_TOPIC =
    new Binary("timelines.engagement.is_not_about_topic", Set(EngagementsPrivate).asJava)
  val IS_NOT_RECENT =
    new Binary("timelines.engagement.is_not_recent", Set(EngagementsPrivate).asJava)
  val IS_NOT_RELEVANT =
    new Binary("timelines.engagement.is_not_relevant", Set(EngagementsPrivate).asJava)
  val IS_SEE_FEWER =
    new Binary("timelines.engagement.is_see_fewer", Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC =
    new Binary("timelines.engagement.is_unfollow_topic", Set(EngagementsPrivate).asJava)
  val IS_FOLLOW_TOPIC =
    new Binary("timelines.engagement.is_follow_topic", Set(EngagementsPrivate).asJava)
  val IS_NOT_INTERESTED_IN_TOPIC =
    new Binary("timelines.engagement.is_not_interested_in_topic", Set(EngagementsPrivate).asJava)
  val IS_NEGATIVE_FEEDBACK =
    new Binary("timelines.engagement.is_negative_feedback", Set(EngagementsPrivate).asJava)
  val IS_IMPLICIT_POSITIVE_FEEDBACK_UNION =
    new Binary(
      "timelines.engagement.is_implicit_positive_feedback_union",
      Set(EngagementsPrivate).asJava)
  val IS_EXPLICIT_POSITIVE_FEEDBACK_UNION =
    new Binary(
      "timelines.engagement.is_explicit_positive_feedback_union",
      Set(EngagementsPrivate).asJava)
  val IS_ALL_NEGATIVE_FEEDBACK_UNION =
    new Binary(
      "timelines.engagement.is_all_negative_feedback_union",
      Set(EngagementsPrivate).asJava)
  // Reciprocal engagements for reply forward engagement
  val IS_REPLIED_REPLY_IMPRESSED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_impressed_by_author",
    Set(EngagementsPrivate).asJava)
  val IS_REPLIED_REPLY_FAVORITED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_favorited_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateLikes, PublicLikes).asJava)
  val IS_REPLIED_REPLY_QUOTED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_quoted_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava)
  val IS_REPLIED_REPLY_REPLIED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_replied_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateReplies, PublicReplies).asJava)
  val IS_REPLIED_REPLY_RETWEETED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_retweeted_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava)
  val IS_REPLIED_REPLY_BLOCKED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_blocked_by_author",
    Set(Blocks, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_FOLLOWED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_followed_by_author",
    Set(EngagementsPrivate, EngagementsPublic, Follow).asJava)
  val IS_REPLIED_REPLY_UNFOLLOWED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_unfollowed_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_MUTED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_muted_by_author",
    Set(Mutes, EngagementsPrivate).asJava)
  val IS_REPLIED_REPLY_REPORTED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_replied_reply_reported_by_author",
    Set(EngagementsPrivate).asJava)

  // Reciprocal engagements for fav forward engagement
  val IS_FAVORITED_FAV_FAVORITED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_favorited_fav_favorited_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateLikes, PublicLikes).asJava
  )
  val IS_FAVORITED_FAV_REPLIED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_favorited_fav_replied_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateReplies, PublicReplies).asJava
  )
  val IS_FAVORITED_FAV_RETWEETED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_favorited_fav_retweeted_by_author",
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava
  )
  val IS_FAVORITED_FAV_FOLLOWED_BY_AUTHOR = new Binary(
    "timelines.engagement.is_favorited_fav_followed_by_author",
    Set(EngagementsPrivate, EngagementsPublic).asJava
  )

  // define good profile click by considering following engagements (follow, fav, reply, retweet, etc.) at profile page
  val IS_PROFILE_CLICKED_AND_PROFILE_FOLLOW = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_follow",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, Follow).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_FAV = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_fav",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, PrivateLikes, PublicLikes).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_REPLY = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_reply",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, PrivateReplies, PublicReplies).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_RETWEET = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_retweet",
    Set(
      ProfilesViewed,
      ProfilesClicked,
      EngagementsPrivate,
      PrivateRetweets,
      PublicRetweets).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_TWEET_CLICK = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_tweet_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, TweetsClicked).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_SHARE_DM_CLICK = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_share_dm_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // This derived label is the union of all binary features above
  val IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_engaged",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, EngagementsPublic).asJava)

  // define bad profile click by considering following engagements (user report, tweet report, mute, block, etc) at profile page
  val IS_PROFILE_CLICKED_AND_PROFILE_USER_REPORT_CLICK = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_user_report_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_TWEET_REPORT_CLICK = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_tweet_report_click",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_MUTE = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_mute",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_BLOCK = new Binary(
    "timelines.engagement.is_profile_clicked_and_profile_block",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // This derived label is the union of bad profile click engagements and existing negative feedback
  val IS_NEGATIVE_FEEDBACK_V2 = new Binary(
    "timelines.engagement.is_negative_feedback_v2",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_NEGATIVE_FEEDBACK_UNION = new Binary(
    "timelines.engagement.is_negative_feedback_union",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // don't like, mute or profile page -> mute
  val IS_WEAK_NEGATIVE_FEEDBACK = new Binary(
    "timelines.engagement.is_weak_negative_feedback",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // report, block or profile page -> report, block
  val IS_STRONG_NEGATIVE_FEEDBACK = new Binary(
    "timelines.engagement.is_strong_negative_feedback",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // engagement for following user from any surface area
  val IS_FOLLOWED_FROM_ANY_SURFACE_AREA = new Binary(
    "timelines.engagement.is_followed_from_any_surface_area",
    Set(EngagementsPublic, EngagementsPrivate).asJava)
  val IS_RELEVANCE_PROMPT_YES_CLICKED = new Binary(
    "timelines.engagement.is_relevance_prompt_yes_clicked",
    Set(EngagementsPublic, EngagementsPrivate).asJava)

  // Reply downvote engagements
  val IS_REPLY_DOWNVOTED =
    new Binary("timelines.engagement.is_reply_downvoted", Set(EngagementsPrivate).asJava)
  val IS_REPLY_DOWNVOTE_REMOVED =
    new Binary("timelines.engagement.is_reply_downvote_removed", Set(EngagementsPrivate).asJava)

  /**
   * Contains all engagements that are used/consumed by real-time
   * aggregates summingbird jobs. These engagements need to be
   * extractable from [[ClientEvent]].
   */
  val EngagementsRealTime: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_DWELLED,
    IS_FAVORITED,
    IS_FOLLOWED,
    IS_OPEN_LINKED,
    IS_PHOTO_EXPANDED,
    IS_PROFILE_CLICKED,
    IS_QUOTED,
    IS_REPLIED,
    IS_RETWEETED,
    IS_RETWEETED_WITHOUT_QUOTE,
    IS_SHARE_DM_CLICKED,
    IS_SHARE_DM_SENT,
    IS_VIDEO_PLAYBACK_50,
    IS_VIDEO_VIEWED,
    IS_VIDEO_QUALITY_VIEWED
  )

  val NegativeEngagementsRealTime: Set[Feature[JBoolean]] = Set(
    IS_REPORT_TWEET_CLICKED,
    IS_BLOCK_CLICKED,
    IS_MUTE_CLICKED
  )

  val NegativeEngagementsRealTimeDontLike: Set[Feature[JBoolean]] = Set(
    IS_DONT_LIKE
  )

  val NegativeEngagementsSecondary: Set[Feature[JBoolean]] = Set(
    IS_NOT_INTERESTED_IN_TOPIC,
    IS_NOT_ABOUT_TOPIC,
    IS_NOT_RECENT,
    IS_NOT_RELEVANT,
    IS_SEE_FEWER,
    IS_UNFOLLOW_TOPIC
  )

  val PrivateEngagements: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_DWELLED,
    IS_OPEN_LINKED,
    IS_PHOTO_EXPANDED,
    IS_PROFILE_CLICKED,
    IS_QUOTED,
    IS_VIDEO_PLAYBACK_50,
    IS_VIDEO_QUALITY_VIEWED
  )

  val ImpressedEngagements: Set[Feature[JBoolean]] = Set(
    IS_IMPRESSED
  )

  val PrivateEngagementsV2: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_OPEN_LINKED,
    IS_PHOTO_EXPANDED,
    IS_PROFILE_CLICKED,
    IS_VIDEO_PLAYBACK_50,
    IS_VIDEO_QUALITY_VIEWED
  ) ++ ImpressedEngagements

  val CoreEngagements: Set[Feature[JBoolean]] = Set(
    IS_FAVORITED,
    IS_REPLIED,
    IS_RETWEETED
  )

  val DwellEngagements: Set[Feature[JBoolean]] = Set(
    IS_DWELLED
  )

  val PrivateCoreEngagements: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_OPEN_LINKED,
    IS_PHOTO_EXPANDED,
    IS_VIDEO_PLAYBACK_50,
    IS_VIDEO_QUALITY_VIEWED
  )

  val ConditionalEngagements: Set[Feature[JBoolean]] = Set(
    IS_GOOD_CLICKED_CONVO_DESC_V1,
    IS_GOOD_CLICKED_CONVO_DESC_V2,
    IS_GOOD_CLICKED_WITH_DWELL_SUM_GTE_60S
  )

  val ShareEngagements: Set[Feature[JBoolean]] = Set(
    IS_SHARED,
    IS_SHARE_MENU_CLICKED
  )

  val BookmarkEngagements: Set[Feature[JBoolean]] = Set(
    IS_BOOKMARKED
  )

  val TweetDetailDwellEngagements: Set[Feature[JBoolean]] = Set(
    IS_TWEET_DETAIL_DWELLED,
    IS_TWEET_DETAIL_DWELLED_8_SEC,
    IS_TWEET_DETAIL_DWELLED_15_SEC,
    IS_TWEET_DETAIL_DWELLED_25_SEC,
    IS_TWEET_DETAIL_DWELLED_30_SEC
  )

  val ProfileDwellEngagements: Set[Feature[JBoolean]] = Set(
    IS_PROFILE_DWELLED,
    IS_PROFILE_DWELLED_10_SEC,
    IS_PROFILE_DWELLED_20_SEC,
    IS_PROFILE_DWELLED_30_SEC
  )

  val FullscreenVideoDwellEngagements: Set[Feature[JBoolean]] = Set(
    IS_FULLSCREEN_VIDEO_DWELLED,
    IS_FULLSCREEN_VIDEO_DWELLED_5_SEC,
    IS_FULLSCREEN_VIDEO_DWELLED_10_SEC,
    IS_FULLSCREEN_VIDEO_DWELLED_20_SEC,
    IS_FULLSCREEN_VIDEO_DWELLED_30_SEC
  )

  // Please do not add new engagements here until having estimated the impact
  // to capacity requirements. User-author real-time aggregates have a very
  // large key space.
  val UserAuthorEngagements: Set[Feature[JBoolean]] = CoreEngagements ++ DwellEngagements ++ Set(
    IS_CLICKED,
    IS_PROFILE_CLICKED,
    IS_PHOTO_EXPANDED,
    IS_VIDEO_PLAYBACK_50,
    IS_NEGATIVE_FEEDBACK_UNION
  )

  val ImplicitPositiveEngagements: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_DWELLED,
    IS_OPEN_LINKED,
    IS_PROFILE_CLICKED,
    IS_QUOTED,
    IS_VIDEO_PLAYBACK_50,
    IS_VIDEO_QUALITY_VIEWED,
    IS_TWEET_DETAIL_DWELLED,
    IS_GOOD_CLICKED_CONVO_DESC_V1,
    IS_GOOD_CLICKED_CONVO_DESC_V2,
    IS_SHARED,
    IS_SHARE_MENU_CLICKED,
    IS_SHARE_DM_SENT,
    IS_SHARE_DM_CLICKED
  )

  val ExplicitPositiveEngagements: Set[Feature[JBoolean]] = CoreEngagements ++ Set(
    IS_FOLLOWED,
    IS_QUOTED
  )

  val AllNegativeEngagements: Set[Feature[JBoolean]] =
    NegativeEngagementsRealTime ++ NegativeEngagementsRealTimeDontLike ++ Set(
      IS_NOT_RECENT,
      IS_NOT_RELEVANT,
      IS_SEE_FEWER
    )
}
