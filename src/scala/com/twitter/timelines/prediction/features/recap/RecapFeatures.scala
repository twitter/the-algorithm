package com.twitter.timelines.prediction.features.recap

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature.Binary
import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.Feature.Discrete
import com.twitter.ml.api.Feature.SparseBinary
import com.twitter.ml.api.Feature.Text
import scala.collection.JavaConverters._

object RecapFeatures extends RecapFeatures("")
object InReplyToRecapFeatures extends RecapFeatures("in_reply_to_tweet")

class RecapFeatures(prefix: String) {
  private def name(featureName: String): String = {
    if (prefix.nonEmpty) {
      s"$prefix.$featureName"
    } else {
      featureName
    }
  }

  val IS_IPAD_CLIENT = new Binary(name("recap.client.is_ipad"), Set(ClientType).asJava)
  val IS_WEB_CLIENT = new Binary(name("recap.client.is_web"), Set(ClientType).asJava)
  val IS_IPHONE_CLIENT = new Binary(name("recap.client.is_phone"), Set(ClientType).asJava)
  val IS_ANDROID_CLIENT = new Binary(name("recap.client.is_android"), Set(ClientType).asJava)
  val IS_ANDROID_TABLET_CLIENT =
    new Binary(name("recap.client.is_android_tablet"), Set(ClientType).asJava)

  // features from userAgent
  val CLIENT_NAME = new Text(name("recap.user_agent.client_name"), Set(ClientType).asJava)
  val CLIENT_SOURCE = new Discrete(name("recap.user_agent.client_source"), Set(ClientType).asJava)
  val CLIENT_VERSION = new Text(name("recap.user_agent.client_version"), Set(ClientVersion).asJava)
  val CLIENT_VERSION_CODE =
    new Text(name("recap.user_agent.client_version_code"), Set(ClientVersion).asJava)
  val DEVICE = new Text(name("recap.user_agent.device"), Set(DeviceType).asJava)
  val FROM_DOG_FOOD = new Binary(name("recap.meta.from_dog_food"), Set(UserAgent).asJava)
  val FROM_TWITTER_CLIENT =
    new Binary(name("recap.user_agent.from_twitter_client"), Set(UserAgent).asJava)
  val MANUFACTURER = new Text(name("recap.user_agent.manufacturer"), Set(UserAgent).asJava)
  val MODEL = new Text(name("recap.user_agent.model"), Set(UserAgent).asJava)
  val NETWORK_CONNECTION =
    new Discrete(name("recap.user_agent.network_connection"), Set(UserAgent).asJava)
  val SDK_VERSION = new Text(name("recap.user_agent.sdk_version"), Set(AppId, UserAgent).asJava)

  // engagement
  val IS_RETWEETED = new Binary(
    name("recap.engagement.is_retweeted"),
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_FAVORITED = new Binary(
    name("recap.engagement.is_favorited"),
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED = new Binary(
    name("recap.engagement.is_replied"),
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  // v1: post click engagements: fav, reply
  val IS_GOOD_CLICKED_CONVO_DESC_V1 = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_favorited_or_replied"),
    Set(
      PublicLikes,
      PrivateLikes,
      PublicReplies,
      PrivateReplies,
      EngagementsPrivate,
      EngagementsPublic).asJava)
  // v2: post click engagements: click
  val IS_GOOD_CLICKED_CONVO_DESC_V2 = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_v2"),
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_GOOD_CLICKED_CONVO_DESC_FAVORITED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_favorited"),
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_REPLIED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_replied"),
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_RETWEETED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_retweeted"),
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_CLICKED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_clicked"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_FOLLOWED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_followed"),
    Set(EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_SHARE_DM_CLICKED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_share_dm_clicked"),
    Set(EngagementsPrivate).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_PROFILE_CLICKED = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_profile_clicked"),
    Set(EngagementsPrivate).asJava)

  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_0 = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_uam_gt_0"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_1 = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_uam_gt_1"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_2 = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_uam_gt_2"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_GOOD_CLICKED_CONVO_DESC_UAM_GT_3 = new Binary(
    name("recap.engagement.is_good_clicked_convo_desc_uam_gt_3"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  val IS_TWEET_DETAIL_DWELLED = new Binary(
    name("recap.engagement.is_tweet_detail_dwelled"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_8_SEC = new Binary(
    name("recap.engagement.is_tweet_detail_dwelled_8_sec"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_15_SEC = new Binary(
    name("recap.engagement.is_tweet_detail_dwelled_15_sec"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_25_SEC = new Binary(
    name("recap.engagement.is_tweet_detail_dwelled_25_sec"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_TWEET_DETAIL_DWELLED_30_SEC = new Binary(
    name("recap.engagement.is_tweet_detail_dwelled_30_sec"),
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_PROFILE_DWELLED = new Binary(
    "recap.engagement.is_profile_dwelled",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_10_SEC = new Binary(
    "recap.engagement.is_profile_dwelled_10_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_20_SEC = new Binary(
    "recap.engagement.is_profile_dwelled_20_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_DWELLED_30_SEC = new Binary(
    "recap.engagement.is_profile_dwelled_30_sec",
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED = new Binary(
    "recap.engagement.is_fullscreen_video_dwelled",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_5_SEC = new Binary(
    "recap.engagement.is_fullscreen_video_dwelled_5_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_10_SEC = new Binary(
    "recap.engagement.is_fullscreen_video_dwelled_10_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_20_SEC = new Binary(
    "recap.engagement.is_fullscreen_video_dwelled_20_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_FULLSCREEN_VIDEO_DWELLED_30_SEC = new Binary(
    "recap.engagement.is_fullscreen_video_dwelled_30_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_15_SEC = new Binary(
    "recap.engagement.is_link_dwelled_15_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_30_SEC = new Binary(
    "recap.engagement.is_link_dwelled_30_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_LINK_DWELLED_60_SEC = new Binary(
    "recap.engagement.is_link_dwelled_60_sec",
    Set(MediaEngagementActivities, EngagementTypePrivate, EngagementsPrivate).asJava)

  val IS_QUOTED = new Binary(
    name("recap.engagement.is_quoted"),
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_RETWEETED_WITHOUT_QUOTE = new Binary(
    name("recap.engagement.is_retweeted_without_quote"),
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_CLICKED =
    new Binary(name("recap.engagement.is_clicked"), Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_DWELLED = new Binary(name("recap.engagement.is_dwelled"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_IN_BOUNDS_V1 =
    new Binary(name("recap.engagement.is_dwelled_in_bounds_v1"), Set(EngagementsPrivate).asJava)
  val DWELL_NORMALIZED_OVERALL = new Continuous(
    name("recap.engagement.dwell_normalized_overall"),
    Set(EngagementsPrivate).asJava)
  val DWELL_CDF_OVERALL =
    new Continuous(name("recap.engagement.dwell_cdf_overall"), Set(EngagementsPrivate).asJava)
  val DWELL_CDF = new Continuous(name("recap.engagement.dwell_cdf"), Set(EngagementsPrivate).asJava)

  val IS_DWELLED_1S =
    new Binary(name("recap.engagement.is_dwelled_1s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_2S =
    new Binary(name("recap.engagement.is_dwelled_2s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_3S =
    new Binary(name("recap.engagement.is_dwelled_3s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_4S =
    new Binary(name("recap.engagement.is_dwelled_4s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_5S =
    new Binary(name("recap.engagement.is_dwelled_5s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_6S =
    new Binary(name("recap.engagement.is_dwelled_6s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_7S =
    new Binary(name("recap.engagement.is_dwelled_7s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_8S =
    new Binary(name("recap.engagement.is_dwelled_8s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_9S =
    new Binary(name("recap.engagement.is_dwelled_9s"), Set(EngagementsPrivate).asJava)
  val IS_DWELLED_10S =
    new Binary(name("recap.engagement.is_dwelled_10s"), Set(EngagementsPrivate).asJava)

  val IS_SKIPPED_1S =
    new Binary(name("recap.engagement.is_skipped_1s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_2S =
    new Binary(name("recap.engagement.is_skipped_2s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_3S =
    new Binary(name("recap.engagement.is_skipped_3s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_4S =
    new Binary(name("recap.engagement.is_skipped_4s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_5S =
    new Binary(name("recap.engagement.is_skipped_5s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_6S =
    new Binary(name("recap.engagement.is_skipped_6s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_7S =
    new Binary(name("recap.engagement.is_skipped_7s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_8S =
    new Binary(name("recap.engagement.is_skipped_8s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_9S =
    new Binary(name("recap.engagement.is_skipped_9s"), Set(EngagementsPrivate).asJava)
  val IS_SKIPPED_10S =
    new Binary(name("recap.engagement.is_skipped_10s"), Set(EngagementsPrivate).asJava)

  val IS_IMPRESSED =
    new Binary(name("recap.engagement.is_impressed"), Set(EngagementsPrivate).asJava)
  val IS_FOLLOWED =
    new Binary("recap.engagement.is_followed", Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_PROFILE_CLICKED = new Binary(
    name("recap.engagement.is_profile_clicked"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_OPEN_LINKED = new Binary(
    name("recap.engagement.is_open_linked"),
    Set(EngagementsPrivate, LinksClickedOn).asJava)
  val IS_PHOTO_EXPANDED =
    new Binary(name("recap.engagement.is_photo_expanded"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_VIEWED =
    new Binary(name("recap.engagement.is_video_viewed"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_START =
    new Binary(name("recap.engagement.is_video_playback_start"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_25 =
    new Binary(name("recap.engagement.is_video_playback_25"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_50 =
    new Binary(name("recap.engagement.is_video_playback_50"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_75 =
    new Binary(name("recap.engagement.is_video_playback_75"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_95 =
    new Binary(name("recap.engagement.is_video_playback_95"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_PLAYBACK_COMPLETE =
    new Binary(name("recap.engagement.is_video_playback_complete"), Set(EngagementsPrivate).asJava)
  val IS_VIDEO_VIEWED_AND_PLAYBACK_50 = new Binary(
    name("recap.engagement.is_video_viewed_and_playback_50"),
    Set(EngagementsPrivate).asJava)
  val IS_VIDEO_QUALITY_VIEWED = new Binary(
    name("recap.engagement.is_video_quality_viewed"),
    Set(EngagementsPrivate).asJava
  ) 
  val IS_TWEET_SHARE_DM_CLICKED =
    new Binary(name("recap.engagement.is_tweet_share_dm_clicked"), Set(EngagementsPrivate).asJava)
  val IS_TWEET_SHARE_DM_SENT =
    new Binary(name("recap.engagement.is_tweet_share_dm_sent"), Set(EngagementsPrivate).asJava)
  val IS_BOOKMARKED =
    new Binary(name("recap.engagement.is_bookmarked"), Set(EngagementsPrivate).asJava)
  val IS_SHARED =
    new Binary(name("recap.engagement.is_shared"), Set(EngagementsPrivate).asJava)
  val IS_SHARE_MENU_CLICKED =
    new Binary(name("recap.engagement.is_share_menu_clicked"), Set(EngagementsPrivate).asJava)

  // Negative engagements
  val IS_DONT_LIKE =
    new Binary(name("recap.engagement.is_dont_like"), Set(EngagementsPrivate).asJava)
  val IS_BLOCK_CLICKED = new Binary(
    name("recap.engagement.is_block_clicked"),
    Set(TweetsClicked, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_BLOCK_DIALOG_BLOCKED = new Binary(
    name("recap.engagement.is_block_dialog_blocked"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_MUTE_CLICKED = new Binary(
    name("recap.engagement.is_mute_clicked"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_MUTE_DIALOG_MUTED =
    new Binary(name("recap.engagement.is_mute_dialog_muted"), Set(EngagementsPrivate).asJava)
  val IS_REPORT_TWEET_CLICKED = new Binary(
    name("recap.engagement.is_report_tweet_clicked"),
    Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_NEGATIVE_FEEDBACK =
    new Binary("recap.engagement.is_negative_feedback", Set(EngagementsPrivate).asJava)
  val IS_NOT_ABOUT_TOPIC =
    new Binary(name("recap.engagement.is_not_about_topic"), Set(EngagementsPrivate).asJava)
  val IS_NOT_RECENT =
    new Binary(name("recap.engagement.is_not_recent"), Set(EngagementsPrivate).asJava)
  val IS_NOT_RELEVANT =
    new Binary(name("recap.engagement.is_not_relevant"), Set(EngagementsPrivate).asJava)
  val IS_SEE_FEWER =
    new Binary(name("recap.engagement.is_see_fewer"), Set(EngagementsPrivate).asJava)
  val IS_TOPIC_SPEC_NEG_ENGAGEMENT =
    new Binary("recap.engagement.is_topic_spec_neg_engagement", Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC =
    new Binary("recap.engagement.is_unfollow_topic", Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC_EXPLICIT_POSITIVE_LABEL =
    new Binary(
      "recap.engagement.is_unfollow_topic_explicit_positive_label",
      Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC_IMPLICIT_POSITIVE_LABEL =
    new Binary(
      "recap.engagement.is_unfollow_topic_implicit_positive_label",
      Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC_STRONG_EXPLICIT_NEGATIVE_LABEL =
    new Binary(
      "recap.engagement.is_unfollow_topic_strong_explicit_negative_label",
      Set(EngagementsPrivate).asJava)
  val IS_UNFOLLOW_TOPIC_EXPLICIT_NEGATIVE_LABEL =
    new Binary(
      "recap.engagement.is_unfollow_topic_explicit_negative_label",
      Set(EngagementsPrivate).asJava)
  val IS_NOT_INTERESTED_IN =
    new Binary("recap.engagement.is_not_interested_in", Set(EngagementsPrivate).asJava)
  val IS_NOT_INTERESTED_IN_EXPLICIT_POSITIVE_LABEL =
    new Binary(
      "recap.engagement.is_not_interested_in_explicit_positive_label",
      Set(EngagementsPrivate).asJava)
  val IS_NOT_INTERESTED_IN_EXPLICIT_NEGATIVE_LABEL =
    new Binary(
      "recap.engagement.is_not_interested_in_explicit_negative_label",
      Set(EngagementsPrivate).asJava)
  val IS_CARET_CLICKED =
    new Binary(name("recap.engagement.is_caret_clicked"), Set(EngagementsPrivate).asJava)
  val IS_FOLLOW_TOPIC =
    new Binary("recap.engagement.is_follow_topic", Set(EngagementsPrivate).asJava)
  val IS_NOT_INTERESTED_IN_TOPIC =
    new Binary("recap.engagement.is_not_interested_in_topic", Set(EngagementsPrivate).asJava)
  val IS_HOME_LATEST_VISITED =
    new Binary(name("recap.engagement.is_home_latest_visited"), Set(EngagementsPrivate).asJava)

  // Relevance prompt tweet engagements
  val IS_RELEVANCE_PROMPT_YES_CLICKED = new Binary(
    name("recap.engagement.is_relevance_prompt_yes_clicked"),
    Set(EngagementsPrivate).asJava)
  val IS_RELEVANCE_PROMPT_NO_CLICKED = new Binary(
    name("recap.engagement.is_relevance_prompt_no_clicked"),
    Set(EngagementsPrivate).asJava)
  val IS_RELEVANCE_PROMPT_IMPRESSED = new Binary(
    name("recap.engagement.is_relevance_prompt_impressed"),
    Set(EngagementsPrivate).asJava)

  // Reciprocal engagements for reply forward engagement
  val IS_REPLIED_REPLY_IMPRESSED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_impressed_by_author"),
    Set(EngagementsPrivate).asJava)
  val IS_REPLIED_REPLY_FAVORITED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_favorited_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateLikes, PublicLikes).asJava)
  val IS_REPLIED_REPLY_QUOTED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_quoted_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava)
  val IS_REPLIED_REPLY_REPLIED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_replied_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateReplies, PublicReplies).asJava)
  val IS_REPLIED_REPLY_RETWEETED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_retweeted_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava)
  val IS_REPLIED_REPLY_BLOCKED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_blocked_by_author"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_FOLLOWED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_followed_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, Follow).asJava)
  val IS_REPLIED_REPLY_UNFOLLOWED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_unfollowed_by_author"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED_REPLY_MUTED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_muted_by_author"),
    Set(EngagementsPrivate).asJava)
  val IS_REPLIED_REPLY_REPORTED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_reported_by_author"),
    Set(EngagementsPrivate).asJava)

  // This derived label is the logical OR of REPLY_REPLIED, REPLY_FAVORITED, REPLY_RETWEETED
  val IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_replied_reply_engaged_by_author"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  // Reciprocal engagements for fav forward engagement
  val IS_FAVORITED_FAV_FAVORITED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_favorited_fav_favorited_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateLikes, PublicLikes).asJava
  )
  val IS_FAVORITED_FAV_REPLIED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_favorited_fav_replied_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateReplies, PublicReplies).asJava
  )
  val IS_FAVORITED_FAV_RETWEETED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_favorited_fav_retweeted_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava
  )
  val IS_FAVORITED_FAV_FOLLOWED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_favorited_fav_followed_by_author"),
    Set(EngagementsPrivate, EngagementsPublic, PrivateRetweets, PublicRetweets).asJava
  )
  // This derived label is the logical OR of FAV_REPLIED, FAV_FAVORITED, FAV_RETWEETED, FAV_FOLLOWED
  val IS_FAVORITED_FAV_ENGAGED_BY_AUTHOR = new Binary(
    name("recap.engagement.is_favorited_fav_engaged_by_author"),
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  // define good profile click by considering following engagements (follow, fav, reply, retweet, etc.) at profile page
  val IS_PROFILE_CLICKED_AND_PROFILE_FOLLOW = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_follow"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, Follow).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_FAV = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_fav"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, PrivateLikes, PublicLikes).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_REPLY = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_reply"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, PrivateReplies, PublicReplies).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_RETWEET = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_retweet"),
    Set(
      ProfilesViewed,
      ProfilesClicked,
      EngagementsPrivate,
      PrivateRetweets,
      PublicRetweets).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_TWEET_CLICK = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_tweet_click"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, TweetsClicked).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_SHARE_DM_CLICK = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_share_dm_click"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // This derived label is the union of all binary features above
  val IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_engaged"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate, EngagementsPublic).asJava)

  // define bad profile click by considering following engagements (user report, tweet report, mute, block, etc) at profile page
  val IS_PROFILE_CLICKED_AND_PROFILE_USER_REPORT_CLICK = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_user_report_click"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_TWEET_REPORT_CLICK = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_tweet_report_click"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_MUTE = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_mute"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_AND_PROFILE_BLOCK = new Binary(
    name("recap.engagement.is_profile_clicked_and_profile_block"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // This derived label is the union of bad profile click engagements and existing negative feedback
  val IS_NEGATIVE_FEEDBACK_V2 = new Binary(
    name("recap.engagement.is_negative_feedback_v2"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_STRONG_NEGATIVE_FEEDBACK = new Binary(
    name("recap.engagement.is_strong_negative_feedback"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  val IS_WEAK_NEGATIVE_FEEDBACK = new Binary(
    name("recap.engagement.is_weak_negative_feedback"),
    Set(ProfilesViewed, ProfilesClicked, EngagementsPrivate).asJava)
  // engagement for following user from any surface area
  val IS_FOLLOWED_FROM_ANY_SURFACE_AREA = new Binary(
    "recap.engagement.is_followed_from_any_surface_area",
    Set(EngagementsPublic, EngagementsPrivate).asJava)

  // Reply downvote engagements
  val IS_REPLY_DOWNVOTED =
    new Binary(name("recap.engagement.is_reply_downvoted"), Set(EngagementsPrivate).asJava)
  val IS_REPLY_DOWNVOTE_REMOVED =
    new Binary(name("recap.engagement.is_reply_downvote_removed"), Set(EngagementsPrivate).asJava)

  // Other engagements
  val IS_GOOD_OPEN_LINK = new Binary(
    name("recap.engagement.is_good_open_link"),
    Set(EngagementsPrivate, LinksClickedOn).asJava)
  val IS_ENGAGED = new Binary(
    name("recap.engagement.any"),
    Set(EngagementsPrivate, EngagementsPublic).asJava
  ) // Deprecated - to be removed shortly
  val IS_EARLYBIRD_UNIFIED_ENGAGEMENT = new Binary(
    name("recap.engagement.is_unified_engagement"),
    Set(EngagementsPrivate, EngagementsPublic).asJava
  ) // A subset of IS_ENGAGED specifically intended for use in earlybird models

  // features from ThriftTweetFeatures
  val PREV_USER_TWEET_ENGAGEMENT = new Continuous(
    name("recap.tweetfeature.prev_user_tweet_enagagement"),
    Set(EngagementScore, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_SENSITIVE = new Binary(name("recap.tweetfeature.is_sensitive"))
  val HAS_MULTIPLE_MEDIA = new Binary(
    name("recap.tweetfeature.has_multiple_media"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val IS_AUTHOR_PROFILE_EGG = new Binary(name("recap.tweetfeature.is_author_profile_egg"))
  val IS_AUTHOR_NEW =
    new Binary(name("recap.tweetfeature.is_author_new"), Set(UserState, UserType).asJava)
  val NUM_MENTIONS = new Continuous(
    name("recap.tweetfeature.num_mentions"),
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val HAS_MENTION = new Binary(name("recap.tweetfeature.has_mention"), Set(UserVisibleFlag).asJava)
  val NUM_HASHTAGS = new Continuous(
    name("recap.tweetfeature.num_hashtags"),
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val HAS_HASHTAG = new Binary(
    name("recap.tweetfeature.has_hashtag"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val LINK_LANGUAGE = new Continuous(
    name("recap.tweetfeature.link_language"),
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val IS_AUTHOR_NSFW =
    new Binary(name("recap.tweetfeature.is_author_nsfw"), Set(UserSafetyLabels, UserType).asJava)
  val IS_AUTHOR_SPAM =
    new Binary(name("recap.tweetfeature.is_author_spam"), Set(UserSafetyLabels, UserType).asJava)
  val IS_AUTHOR_BOT =
    new Binary(name("recap.tweetfeature.is_author_bot"), Set(UserSafetyLabels, UserType).asJava)
  val SIGNATURE =
    new Discrete(name("recap.tweetfeature.signature"), Set(DigitalSignatureNonrepudiation).asJava)
  val LANGUAGE = new Discrete(
    name("recap.tweetfeature.language"),
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val FROM_INACTIVE_USER =
    new Binary(name("recap.tweetfeature.from_inactive_user"), Set(UserActiveFlag).asJava)
  val PROBABLY_FROM_FOLLOWED_AUTHOR = new Binary(name("recap.v3.tweetfeature.probably_from_follow"))
  val FROM_MUTUAL_FOLLOW = new Binary(name("recap.tweetfeature.from_mutual_follow"))
  val USER_REP = new Continuous(name("recap.tweetfeature.user_rep"))
  val FROM_VERIFIED_ACCOUNT =
    new Binary(name("recap.tweetfeature.from_verified_account"), Set(UserVerifiedFlag).asJava)
  val IS_BUSINESS_SCORE = new Continuous(name("recap.tweetfeature.is_business_score"))
  val HAS_CONSUMER_VIDEO = new Binary(
    name("recap.tweetfeature.has_consumer_video"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_PRO_VIDEO = new Binary(
    name("recap.tweetfeature.has_pro_video"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_VINE = new Binary(
    name("recap.tweetfeature.has_vine"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_PERISCOPE = new Binary(
    name("recap.tweetfeature.has_periscope"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_NATIVE_VIDEO = new Binary(
    name("recap.tweetfeature.has_native_video"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_NATIVE_IMAGE = new Binary(
    name("recap.tweetfeature.has_native_image"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_CARD = new Binary(
    name("recap.tweetfeature.has_card"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_IMAGE = new Binary(
    name("recap.tweetfeature.has_image"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_NEWS = new Binary(
    name("recap.tweetfeature.has_news"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_VIDEO = new Binary(
    name("recap.tweetfeature.has_video"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_VISIBLE_LINK = new Binary(
    name("recap.tweetfeature.has_visible_link"),
    Set(UrlFoundFlag, PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val LINK_COUNT = new Continuous(
    name("recap.tweetfeature.link_count"),
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  val HAS_LINK = new Binary(
    name("recap.tweetfeature.has_link"),
    Set(UrlFoundFlag, PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val IS_OFFENSIVE = new Binary(name("recap.tweetfeature.is_offensive"))
  val HAS_TREND = new Binary(
    name("recap.tweetfeature.has_trend"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val HAS_MULTIPLE_HASHTAGS_OR_TRENDS = new Binary(
    name("recap.tweetfeature.has_multiple_hashtag_or_trend"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val URL_DOMAINS = new SparseBinary(
    name("recap.tweetfeature.url_domains"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val CONTAINS_MEDIA = new Binary(
    name("recap.tweetfeature.contains_media"),
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val RETWEET_SEARCHER = new Binary(name("recap.tweetfeature.retweet_searcher"))
  val REPLY_SEARCHER = new Binary(name("recap.tweetfeature.reply_searcher"))
  val MENTION_SEARCHER =
    new Binary(name("recap.tweetfeature.mention_searcher"), Set(UserVisibleFlag).asJava)
  val REPLY_OTHER =
    new Binary(name("recap.tweetfeature.reply_other"), Set(PublicReplies, PrivateReplies).asJava)
  val RETWEET_OTHER = new Binary(
    name("recap.tweetfeature.retweet_other"),
    Set(PublicRetweets, PrivateRetweets).asJava)
  val IS_REPLY =
    new Binary(name("recap.tweetfeature.is_reply"), Set(PublicReplies, PrivateReplies).asJava)
  val IS_RETWEET =
    new Binary(name("recap.tweetfeature.is_retweet"), Set(PublicRetweets, PrivateRetweets).asJava)
  val IS_EXTENDED_REPLY = new Binary(
    name("recap.tweetfeature.is_extended_reply"),
    Set(PublicReplies, PrivateReplies).asJava)
  val MATCH_UI_LANG = new Binary(
    name("recap.tweetfeature.match_ui_lang"),
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val MATCH_SEARCHER_MAIN_LANG = new Binary(
    name("recap.tweetfeature.match_searcher_main_lang"),
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val MATCH_SEARCHER_LANGS = new Binary(
    name("recap.tweetfeature.match_searcher_langs"),
    Set(ProvidedLanguage, InferredLanguage).asJava)
  val BIDIRECTIONAL_REPLY_COUNT = new Continuous(
    name("recap.tweetfeature.bidirectional_reply_count"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val UNIDIRECTIONAL_REPLY_COUNT = new Continuous(
    name("recap.tweetfeature.unidirectional_reply_count"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val BIDIRECTIONAL_RETWEET_COUNT = new Continuous(
    name("recap.tweetfeature.bidirectional_retweet_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val UNIDIRECTIONAL_RETWEET_COUNT = new Continuous(
    name("recap.tweetfeature.unidirectional_retweet_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val BIDIRECTIONAL_FAV_COUNT = new Continuous(
    name("recap.tweetfeature.bidirectional_fav_count"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val UNIDIRECTIONAL_FAV_COUNT = new Continuous(
    name("recap.tweetfeature.unidirectiona_fav_count"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val CONVERSATIONAL_COUNT = new Continuous(
    name("recap.tweetfeature.conversational_count"),
    Set(CountOfPrivateTweets, CountOfPublicTweets).asJava)
  // tweet impressions on an embedded tweet
  val EMBEDS_IMPRESSION_COUNT = new Continuous(
    name("recap.tweetfeature.embeds_impression_count"),
    Set(CountOfImpression).asJava)
  // number of URLs that embed the tweet
  val EMBEDS_URL_COUNT = new Continuous(
    name("recap.tweetfeature.embeds_url_count"),
    Set(CountOfPrivateTweetEntitiesAndMetadata, CountOfPublicTweetEntitiesAndMetadata).asJava)
  // currently only counts views on Snappy and Amplify pro videos. Counts for other videos forthcoming
  val VIDEO_VIEW_COUNT = new Continuous(
    name("recap.tweetfeature.video_view_count"),
    Set(
      CountOfTweetEntitiesClicked,
      CountOfPrivateTweetEntitiesAndMetadata,
      CountOfPublicTweetEntitiesAndMetadata,
      EngagementsPrivate,
      EngagementsPublic).asJava
  )
  val TWEET_COUNT_FROM_USER_IN_SNAPSHOT = new Continuous(
    name("recap.tweetfeature.tweet_count_from_user_in_snapshot"),
    Set(CountOfPrivateTweets, CountOfPublicTweets).asJava)
  val NORMALIZED_PARUS_SCORE =
    new Continuous("recap.tweetfeature.normalized_parus_score", Set(EngagementScore).asJava)
  val PARUS_SCORE = new Continuous("recap.tweetfeature.parus_score", Set(EngagementScore).asJava)
  val REAL_GRAPH_WEIGHT =
    new Continuous("recap.tweetfeature.real_graph_weight", Set(UsersRealGraphScore).asJava)
  val SARUS_GRAPH_WEIGHT = new Continuous("recap.tweetfeature.sarus_graph_weight")
  val TOPIC_SIM_SEARCHER_INTERSTED_IN_AUTHOR_KNOWN_FOR = new Continuous(
    "recap.tweetfeature.topic_sim_searcher_interested_in_author_known_for")
  val TOPIC_SIM_SEARCHER_AUTHOR_BOTH_INTERESTED_IN = new Continuous(
    "recap.tweetfeature.topic_sim_searcher_author_both_interested_in")
  val TOPIC_SIM_SEARCHER_AUTHOR_BOTH_KNOWN_FOR = new Continuous(
    "recap.tweetfeature.topic_sim_searcher_author_both_known_for")
  val TOPIC_SIM_SEARCHER_INTERESTED_IN_TWEET = new Continuous(
    "recap.tweetfeature.topic_sim_searcher_interested_in_tweet")
  val IS_RETWEETER_PROFILE_EGG =
    new Binary(name("recap.v2.tweetfeature.is_retweeter_profile_egg"), Set(UserType).asJava)
  val IS_RETWEETER_NEW =
    new Binary(name("recap.v2.tweetfeature.is_retweeter_new"), Set(UserType, UserState).asJava)
  val IS_RETWEETER_BOT =
    new Binary(
      name("recap.v2.tweetfeature.is_retweeter_bot"),
      Set(UserType, UserSafetyLabels).asJava)
  val IS_RETWEETER_NSFW =
    new Binary(
      name("recap.v2.tweetfeature.is_retweeter_nsfw"),
      Set(UserType, UserSafetyLabels).asJava)
  val IS_RETWEETER_SPAM =
    new Binary(
      name("recap.v2.tweetfeature.is_retweeter_spam"),
      Set(UserType, UserSafetyLabels).asJava)
  val RETWEET_OF_MUTUAL_FOLLOW = new Binary(
    name("recap.v2.tweetfeature.retweet_of_mutual_follow"),
    Set(PublicRetweets, PrivateRetweets).asJava)
  val SOURCE_AUTHOR_REP = new Continuous(name("recap.v2.tweetfeature.source_author_rep"))
  val IS_RETWEET_OF_REPLY = new Binary(
    name("recap.v2.tweetfeature.is_retweet_of_reply"),
    Set(PublicRetweets, PrivateRetweets).asJava)
  val RETWEET_DIRECTED_AT_USER_IN_FIRST_DEGREE = new Binary(
    name("recap.v2.tweetfeature.is_retweet_directed_at_user_in_first_degree"),
    Set(PublicRetweets, PrivateRetweets, Follow).asJava)
  val MENTIONED_SCREEN_NAMES = new SparseBinary(
    "entities.users.mentioned_screen_names",
    Set(DisplayName, UserVisibleFlag).asJava)
  val MENTIONED_SCREEN_NAME = new Text(
    "entities.users.mentioned_screen_names.member",
    Set(DisplayName, UserVisibleFlag).asJava)
  val HASHTAGS = new SparseBinary(
    "entities.hashtags",
    Set(PublicTweetEntitiesAndMetadata, PrivateTweetEntitiesAndMetadata).asJava)
  val URL_SLUGS = new SparseBinary(name("recap.linkfeature.url_slugs"), Set(UrlFoundFlag).asJava)

  // features from ThriftSearchResultMetadata
  val REPLY_COUNT = new Continuous(
    name("recap.searchfeature.reply_count"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)
  val RETWEET_COUNT = new Continuous(
    name("recap.searchfeature.retweet_count"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val FAV_COUNT = new Continuous(
    name("recap.searchfeature.fav_count"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val BLENDER_SCORE = new Continuous(name("recap.searchfeature.blender_score"))
  val TEXT_SCORE = new Continuous(name("recap.searchfeature.text_score"))

  // features related to content source
  val SOURCE_TYPE = new Discrete(name("recap.source.type"))

  // features from addressbook
  // the author is in the user's email addressbook
  val USER_TO_AUTHOR_EMAIL_REACHABLE =
    new Binary(name("recap.addressbook.user_to_author_email_reachable"), Set(AddressBook).asJava)
  // the author is in the user's phone addressbook
  val USER_TO_AUTHOR_PHONE_REACHABLE =
    new Binary(name("recap.addressbook.user_to_author_phone_reachable"), Set(AddressBook).asJava)
  // the user is in the author's email addressbook
  val AUTHOR_TO_USER_EMAIL_REACHABLE =
    new Binary(name("recap.addressbook.author_to_user_email_reachable"), Set(AddressBook).asJava)
  // the user is in the user's phone addressbook
  val AUTHOR_TO_USER_PHONE_REACHABLE =
    new Binary(name("recap.addressbook.author_to_user_phone_reachable"), Set(AddressBook).asJava)

  // predicted engagement (these features are used by prediction service to return the predicted engagement probability)
  // these should match the names in engagement_to_score_feature_mapping
  val PREDICTED_IS_FAVORITED =
    new Continuous(name("recap.engagement_predicted.is_favorited"), Set(EngagementScore).asJava)
  val PREDICTED_IS_RETWEETED =
    new Continuous(name("recap.engagement_predicted.is_retweeted"), Set(EngagementScore).asJava)
  val PREDICTED_IS_QUOTED =
    new Continuous(name("recap.engagement_predicted.is_quoted"), Set(EngagementScore).asJava)
  val PREDICTED_IS_REPLIED =
    new Continuous(name("recap.engagement_predicted.is_replied"), Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_OPEN_LINK = new Continuous(
    name("recap.engagement_predicted.is_good_open_link"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_PROFILE_CLICKED = new Continuous(
    name("recap.engagement_predicted.is_profile_clicked"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_PROFILE_CLICKED_AND_PROFILE_ENGAGED = new Continuous(
    name("recap.engagement_predicted.is_profile_clicked_and_profile_engaged"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_CLICKED =
    new Continuous(name("recap.engagement_predicted.is_clicked"), Set(EngagementScore).asJava)
  val PREDICTED_IS_PHOTO_EXPANDED = new Continuous(
    name("recap.engagement_predicted.is_photo_expanded"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_DONT_LIKE =
    new Continuous(name("recap.engagement_predicted.is_dont_like"), Set(EngagementScore).asJava)
  val PREDICTED_IS_VIDEO_PLAYBACK_50 = new Continuous(
    name("recap.engagement_predicted.is_video_playback_50"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_VIDEO_QUALITY_VIEWED = new Continuous(
    name("recap.engagement_predicted.is_video_quality_viewed"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_BOOKMARKED =
    new Continuous(name("recap.engagement_predicted.is_bookmarked"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SHARED =
    new Continuous(name("recap.engagement_predicted.is_shared"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SHARE_MENU_CLICKED =
    new Continuous(
      name("recap.engagement_predicted.is_share_menu_clicked"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_PROFILE_DWELLED_20_SEC = new Continuous(
    name("recap.engagement_predicted.is_profile_dwelled_20_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_5_SEC = new Continuous(
    name("recap.engagement_predicted.is_fullscreen_video_dwelled_5_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_10_SEC = new Continuous(
    name("recap.engagement_predicted.is_fullscreen_video_dwelled_10_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_20_SEC = new Continuous(
    name("recap.engagement_predicted.is_fullscreen_video_dwelled_20_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FULLSCREEN_VIDEO_DWELLED_30_SEC = new Continuous(
    name("recap.engagement_predicted.is_fullscreen_video_dwelled_30_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_UNIFIED_ENGAGEMENT = new Continuous(
    name("recap.engagement_predicted.is_unified_engagement"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_COMPOSE_TRIGGERED = new Continuous(
    name("recap.engagement_predicted.is_compose_triggered"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_REPLIED_REPLY_IMPRESSED_BY_AUTHOR = new Continuous(
    name("recap.engagement_predicted.is_replied_reply_impressed_by_author"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_REPLIED_REPLY_ENGAGED_BY_AUTHOR = new Continuous(
    name("recap.engagement_predicted.is_replied_reply_engaged_by_author"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_CLICKED_V1 = new Continuous(
    name("recap.engagement_predicted.is_good_clicked_convo_desc_favorited_or_replied"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_CLICKED_V2 = new Continuous(
    name("recap.engagement_predicted.is_good_clicked_convo_desc_v2"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_8_SEC = new Continuous(
    name("recap.engagement_predicted.is_tweet_detail_dwelled_8_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_15_SEC = new Continuous(
    name("recap.engagement_predicted.is_tweet_detail_dwelled_15_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_25_SEC = new Continuous(
    name("recap.engagement_predicted.is_tweet_detail_dwelled_25_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_TWEET_DETAIL_DWELLED_30_SEC = new Continuous(
    name("recap.engagement_predicted.is_tweet_detail_dwelled_30_sec"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_FAVORITED_FAV_ENGAGED_BY_AUTHOR = new Continuous(
    name("recap.engagement_predicted.is_favorited_fav_engaged_by_author"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_GOOD_CLICKED_WITH_DWELL_SUM_GTE_60S = new Continuous(
    name(
      "recap.engagement_predicted.is_good_clicked_convo_desc_favorited_or_replied_or_dwell_sum_gte_60_secs"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_IN_BOUNDS_V1 = new Continuous(
    name("recap.engagement_predicted.is_dwelled_in_bounds_v1"),
    Set(EngagementScore).asJava)
  val PREDICTED_DWELL_NORMALIZED_OVERALL = new Continuous(
    name("recap.engagement_predicted.dwell_normalized_overall"),
    Set(EngagementScore).asJava)
  val PREDICTED_DWELL_CDF =
    new Continuous(name("recap.engagement_predicted.dwell_cdf"), Set(EngagementScore).asJava)
  val PREDICTED_DWELL_CDF_OVERALL = new Continuous(
    name("recap.engagement_predicted.dwell_cdf_overall"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED =
    new Continuous(name("recap.engagement_predicted.is_dwelled"), Set(EngagementScore).asJava)

  val PREDICTED_IS_DWELLED_1S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_1s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_2S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_2s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_3S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_3s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_4S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_4s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_5S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_5s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_6S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_6s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_7S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_7s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_8S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_8s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_9S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_9s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_DWELLED_10S =
    new Continuous(name("recap.engagement_predicted.is_dwelled_10s"), Set(EngagementScore).asJava)

  val PREDICTED_IS_SKIPPED_1S =
    new Continuous(name("recap.engagement_predicted.is_skipped_1s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_2S =
    new Continuous(name("recap.engagement_predicted.is_skipped_2s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_3S =
    new Continuous(name("recap.engagement_predicted.is_skipped_3s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_4S =
    new Continuous(name("recap.engagement_predicted.is_skipped_4s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_5S =
    new Continuous(name("recap.engagement_predicted.is_skipped_5s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_6S =
    new Continuous(name("recap.engagement_predicted.is_skipped_6s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_7S =
    new Continuous(name("recap.engagement_predicted.is_skipped_7s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_8S =
    new Continuous(name("recap.engagement_predicted.is_skipped_8s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_9S =
    new Continuous(name("recap.engagement_predicted.is_skipped_9s"), Set(EngagementScore).asJava)
  val PREDICTED_IS_SKIPPED_10S =
    new Continuous(name("recap.engagement_predicted.is_skipped_10s"), Set(EngagementScore).asJava)

  val PREDICTED_IS_HOME_LATEST_VISITED = new Continuous(
    name("recap.engagement_predicted.is_home_latest_visited"),
    Set(EngagementScore).asJava)
  val PREDICTED_IS_NEGATIVE_FEEDBACK =
    new Continuous(
      name("recap.engagement_predicted.is_negative_feedback"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_NEGATIVE_FEEDBACK_V2 =
    new Continuous(
      name("recap.engagement_predicted.is_negative_feedback_v2"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_WEAK_NEGATIVE_FEEDBACK =
    new Continuous(
      name("recap.engagement_predicted.is_weak_negative_feedback"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_STRONG_NEGATIVE_FEEDBACK =
    new Continuous(
      name("recap.engagement_predicted.is_strong_negative_feedback"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_REPORT_TWEET_CLICKED =
    new Continuous(
      name("recap.engagement_predicted.is_report_tweet_clicked"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_UNFOLLOW_TOPIC =
    new Continuous(
      name("recap.engagement_predicted.is_unfollow_topic"),
      Set(EngagementScore).asJava)
  val PREDICTED_IS_RELEVANCE_PROMPT_YES_CLICKED = new Continuous(
    name("recap.engagement_predicted.is_relevance_prompt_yes_clicked"),
    Set(EngagementScore).asJava)

  // engagement for following user from any surface area
  val PREDICTED_IS_FOLLOWED_FROM_ANY_SURFACE_AREA = new Continuous(
    "recap.engagement_predicted.is_followed_from_any_surface_area",
    Set(EngagementScore).asJava)

  
  // These are global engagement counts for the Tweets.
  val FAV_COUNT_V2 = new Continuous(
    name("recap.earlybird.fav_count_v2"),
    Set(CountOfPrivateLikes, CountOfPublicLikes).asJava)
  val RETWEET_COUNT_V2 = new Continuous(
    name("recap.earlybird.retweet_count_v2"),
    Set(CountOfPrivateRetweets, CountOfPublicRetweets).asJava)
  val REPLY_COUNT_V2 = new Continuous(
    name("recap.earlybird.reply_count_v2"),
    Set(CountOfPrivateReplies, CountOfPublicReplies).asJava)

  val HAS_US_POLITICAL_ANNOTATION = new Binary(
    name("recap.has_us_political_annotation"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ALL_GROUPS_ANNOTATION = new Binary(
    name("recap.has_us_political_all_groups_annotation"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_HIGH_RECALL = new Binary(
    name("recap.has_us_political_annotation_high_recall"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_HIGH_RECALL_V2 = new Binary(
    name("recap.has_us_political_annotation_high_recall_v2"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_HIGH_PRECISION_V0 = new Binary(
    name("recap.has_us_political_annotation_high_precision_v0"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_BALANCED_PRECISION_RECALL_V0 = new Binary(
    name("recap.has_us_political_annotation_balanced_precision_recall_v0"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_HIGH_RECALL_V3 = new Binary(
    name("recap.has_us_political_annotation_high_recall_v3"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_HIGH_PRECISION_V3 = new Binary(
    name("recap.has_us_political_annotation_high_precision_v3"),
    Set(SemanticcoreClassification).asJava
  )

  val HAS_US_POLITICAL_ANNOTATION_BALANCED_V3 = new Binary(
    name("recap.has_us_political_annotation_balanced_v3"),
    Set(SemanticcoreClassification).asJava
  )

}
