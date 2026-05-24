package com.twitter.timelines.prediction.features.real_graph

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature._
import com.twitter.timelines.real_graph.v1.thriftscala.RealGraphEdgeFeature
import scala.collection.JavaConverters._


object RealGraphDataRecordFeatures {
  // the source user id
  val SRC_ID = new Discrete("realgraph.src_id", Set(UserId).asJava)
  // the destination user id
  val DST_ID = new Discrete("realgraph.dst_id", Set(UserId).asJava)
  // real graph weight
  val WEIGHT = new Continuous("realgraph.weight", Set(UsersRealGraphScore).asJava)
  // the number of retweets that the source user sent to the destination user
  val NUM_RETWEETS_MEAN =
    new Continuous("realgraph.num_retweets.mean", Set(PrivateRetweets, PublicRetweets).asJava)
  val NUM_RETWEETS_EWMA =
    new Continuous("realgraph.num_retweets.ewma", Set(PrivateRetweets, PublicRetweets).asJava)
  val NUM_RETWEETS_VARIANCE =
    new Continuous("realgraph.num_retweets.variance", Set(PrivateRetweets, PublicRetweets).asJava)
  val NUM_RETWEETS_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_retweets.non_zero_days",
    Set(PrivateRetweets, PublicRetweets).asJava)
  val NUM_RETWEETS_ELAPSED_DAYS = new Continuous(
    "realgraph.num_retweets.elapsed_days",
    Set(PrivateRetweets, PublicRetweets).asJava)
  val NUM_RETWEETS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_retweets.days_since_last",
    Set(PrivateRetweets, PublicRetweets).asJava)
  val NUM_RETWEETS_IS_MISSING =
    new Binary("realgraph.num_retweets.is_missing", Set(PrivateRetweets, PublicRetweets).asJava)
  // the number of favories that the source user sent to the destination user
  val NUM_FAVORITES_MEAN =
    new Continuous("realgraph.num_favorites.mean", Set(PublicLikes, PrivateLikes).asJava)
  val NUM_FAVORITES_EWMA =
    new Continuous("realgraph.num_favorites.ewma", Set(PublicLikes, PrivateLikes).asJava)
  val NUM_FAVORITES_VARIANCE =
    new Continuous("realgraph.num_favorites.variance", Set(PublicLikes, PrivateLikes).asJava)
  val NUM_FAVORITES_NON_ZERO_DAYS =
    new Continuous("realgraph.num_favorites.non_zero_days", Set(PublicLikes, PrivateLikes).asJava)
  val NUM_FAVORITES_ELAPSED_DAYS =
    new Continuous("realgraph.num_favorites.elapsed_days", Set(PublicLikes, PrivateLikes).asJava)
  val NUM_FAVORITES_DAYS_SINCE_LAST =
    new Continuous("realgraph.num_favorites.days_since_last", Set(PublicLikes, PrivateLikes).asJava)
  val NUM_FAVORITES_IS_MISSING =
    new Binary("realgraph.num_favorites.is_missing", Set(PublicLikes, PrivateLikes).asJava)
  // the number of mentions that the source user sent to the destination user
  val NUM_MENTIONS_MEAN =
    new Continuous("realgraph.num_mentions.mean", Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_MENTIONS_EWMA =
    new Continuous("realgraph.num_mentions.ewma", Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_MENTIONS_VARIANCE = new Continuous(
    "realgraph.num_mentions.variance",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_MENTIONS_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_mentions.non_zero_days",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_MENTIONS_ELAPSED_DAYS = new Continuous(
    "realgraph.num_mentions.elapsed_days",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_MENTIONS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_mentions.days_since_last",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_MENTIONS_IS_MISSING = new Binary(
    "realgraph.num_mentions.is_missing",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  // the number of direct messages that the source user sent to the destination user
  val NUM_DIRECT_MESSAGES_MEAN = new Continuous(
    "realgraph.num_direct_messages.mean",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava)
  val NUM_DIRECT_MESSAGES_EWMA = new Continuous(
    "realgraph.num_direct_messages.ewma",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava)
  val NUM_DIRECT_MESSAGES_VARIANCE = new Continuous(
    "realgraph.num_direct_messages.variance",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava)
  val NUM_DIRECT_MESSAGES_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_direct_messages.non_zero_days",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava
  )
  val NUM_DIRECT_MESSAGES_ELAPSED_DAYS = new Continuous(
    "realgraph.num_direct_messages.elapsed_days",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava
  )
  val NUM_DIRECT_MESSAGES_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_direct_messages.days_since_last",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava
  )
  val NUM_DIRECT_MESSAGES_IS_MISSING = new Binary(
    "realgraph.num_direct_messages.is_missing",
    Set(DmEntitiesAndMetadata, CountOfDms).asJava)
  // the number of tweet clicks that the source user sent to the destination user
  val NUM_TWEET_CLICKS_MEAN =
    new Continuous("realgraph.num_tweet_clicks.mean", Set(TweetsClicked).asJava)
  val NUM_TWEET_CLICKS_EWMA =
    new Continuous("realgraph.num_tweet_clicks.ewma", Set(TweetsClicked).asJava)
  val NUM_TWEET_CLICKS_VARIANCE =
    new Continuous("realgraph.num_tweet_clicks.variance", Set(TweetsClicked).asJava)
  val NUM_TWEET_CLICKS_NON_ZERO_DAYS =
    new Continuous("realgraph.num_tweet_clicks.non_zero_days", Set(TweetsClicked).asJava)
  val NUM_TWEET_CLICKS_ELAPSED_DAYS =
    new Continuous("realgraph.num_tweet_clicks.elapsed_days", Set(TweetsClicked).asJava)
  val NUM_TWEET_CLICKS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_tweet_clicks.days_since_last",
    Set(TweetsClicked).asJava
  )
  val NUM_TWEET_CLICKS_IS_MISSING =
    new Binary("realgraph.num_tweet_clicks.is_missing", Set(TweetsClicked).asJava)
  // the number of link clicks that the source user sent to the destination user
  val NUM_LINK_CLICKS_MEAN =
    new Continuous("realgraph.num_link_clicks.mean", Set(CountOfTweetEntitiesClicked).asJava)
  val NUM_LINK_CLICKS_EWMA =
    new Continuous("realgraph.num_link_clicks.ewma", Set(CountOfTweetEntitiesClicked).asJava)
  val NUM_LINK_CLICKS_VARIANCE =
    new Continuous("realgraph.num_link_clicks.variance", Set(CountOfTweetEntitiesClicked).asJava)
  val NUM_LINK_CLICKS_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_link_clicks.non_zero_days",
    Set(CountOfTweetEntitiesClicked).asJava)
  val NUM_LINK_CLICKS_ELAPSED_DAYS = new Continuous(
    "realgraph.num_link_clicks.elapsed_days",
    Set(CountOfTweetEntitiesClicked).asJava)
  val NUM_LINK_CLICKS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_link_clicks.days_since_last",
    Set(CountOfTweetEntitiesClicked).asJava)
  val NUM_LINK_CLICKS_IS_MISSING =
    new Binary("realgraph.num_link_clicks.is_missing", Set(CountOfTweetEntitiesClicked).asJava)
  // the number of profile views that the source user sent to the destination user
  val NUM_PROFILE_VIEWS_MEAN =
    new Continuous("realgraph.num_profile_views.mean", Set(ProfilesViewed).asJava)
  val NUM_PROFILE_VIEWS_EWMA =
    new Continuous("realgraph.num_profile_views.ewma", Set(ProfilesViewed).asJava)
  val NUM_PROFILE_VIEWS_VARIANCE =
    new Continuous("realgraph.num_profile_views.variance", Set(ProfilesViewed).asJava)
  val NUM_PROFILE_VIEWS_NON_ZERO_DAYS =
    new Continuous("realgraph.num_profile_views.non_zero_days", Set(ProfilesViewed).asJava)
  val NUM_PROFILE_VIEWS_ELAPSED_DAYS =
    new Continuous("realgraph.num_profile_views.elapsed_days", Set(ProfilesViewed).asJava)
  val NUM_PROFILE_VIEWS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_profile_views.days_since_last",
    Set(ProfilesViewed).asJava
  )
  val NUM_PROFILE_VIEWS_IS_MISSING =
    new Binary("realgraph.num_profile_views.is_missing", Set(ProfilesViewed).asJava)
  // the total dwell time the source user spends on the target user's tweets
  val TOTAL_DWELL_TIME_MEAN =
    new Continuous("realgraph.total_dwell_time.mean", Set(CountOfImpression).asJava)
  val TOTAL_DWELL_TIME_EWMA =
    new Continuous("realgraph.total_dwell_time.ewma", Set(CountOfImpression).asJava)
  val TOTAL_DWELL_TIME_VARIANCE =
    new Continuous("realgraph.total_dwell_time.variance", Set(CountOfImpression).asJava)
  val TOTAL_DWELL_TIME_NON_ZERO_DAYS =
    new Continuous("realgraph.total_dwell_time.non_zero_days", Set(CountOfImpression).asJava)
  val TOTAL_DWELL_TIME_ELAPSED_DAYS =
    new Continuous("realgraph.total_dwell_time.elapsed_days", Set(CountOfImpression).asJava)
  val TOTAL_DWELL_TIME_DAYS_SINCE_LAST = new Continuous(
    "realgraph.total_dwell_time.days_since_last",
    Set(CountOfImpression).asJava
  )
  val TOTAL_DWELL_TIME_IS_MISSING =
    new Binary("realgraph.total_dwell_time.is_missing", Set(CountOfImpression).asJava)
  // the number of the target user's tweets that the source user has inspected
  val NUM_INSPECTED_TWEETS_MEAN =
    new Continuous("realgraph.num_inspected_tweets.mean", Set(CountOfImpression).asJava)
  val NUM_INSPECTED_TWEETS_EWMA =
    new Continuous("realgraph.num_inspected_tweets.ewma", Set(CountOfImpression).asJava)
  val NUM_INSPECTED_TWEETS_VARIANCE =
    new Continuous("realgraph.num_inspected_tweets.variance", Set(CountOfImpression).asJava)
  val NUM_INSPECTED_TWEETS_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_inspected_tweets.non_zero_days",
    Set(CountOfImpression).asJava
  )
  val NUM_INSPECTED_TWEETS_ELAPSED_DAYS = new Continuous(
    "realgraph.num_inspected_tweets.elapsed_days",
    Set(CountOfImpression).asJava
  )
  val NUM_INSPECTED_TWEETS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_inspected_tweets.days_since_last",
    Set(CountOfImpression).asJava
  )
  val NUM_INSPECTED_TWEETS_IS_MISSING =
    new Binary("realgraph.num_inspected_tweets.is_missing", Set(CountOfImpression).asJava)
  // the number of photos in which the source user has tagged the target user
  val NUM_PHOTO_TAGS_MEAN = new Continuous(
    "realgraph.num_photo_tags.mean",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_PHOTO_TAGS_EWMA = new Continuous(
    "realgraph.num_photo_tags.ewma",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_PHOTO_TAGS_VARIANCE = new Continuous(
    "realgraph.num_photo_tags.variance",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_PHOTO_TAGS_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_photo_tags.non_zero_days",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_PHOTO_TAGS_ELAPSED_DAYS = new Continuous(
    "realgraph.num_photo_tags.elapsed_days",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_PHOTO_TAGS_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_photo_tags.days_since_last",
    Set(EngagementsPrivate, EngagementsPublic).asJava)
  val NUM_PHOTO_TAGS_IS_MISSING = new Binary(
    "realgraph.num_photo_tags.is_missing",
    Set(EngagementsPrivate, EngagementsPublic).asJava)

  val NUM_FOLLOW_MEAN = new Continuous(
    "realgraph.num_follow.mean",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_FOLLOW_EWMA = new Continuous(
    "realgraph.num_follow.ewma",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_FOLLOW_VARIANCE = new Continuous(
    "realgraph.num_follow.variance",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_FOLLOW_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_follow.non_zero_days",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_FOLLOW_ELAPSED_DAYS = new Continuous(
    "realgraph.num_follow.elapsed_days",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_FOLLOW_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_follow.days_since_last",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_FOLLOW_IS_MISSING = new Binary(
    "realgraph.num_follow.is_missing",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  // the number of blocks that the source user sent to the destination user
  val NUM_BLOCKS_MEAN =
    new Continuous("realgraph.num_blocks.mean", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_EWMA =
    new Continuous("realgraph.num_blocks.ewma", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_VARIANCE =
    new Continuous("realgraph.num_blocks.variance", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_NON_ZERO_DAYS =
    new Continuous("realgraph.num_blocks.non_zero_days", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_ELAPSED_DAYS =
    new Continuous("realgraph.num_blocks.elapsed_days", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_DAYS_SINCE_LAST =
    new Continuous("realgraph.num_blocks.days_since_last", Set(CountOfBlocks).asJava)
  val NUM_BLOCKS_IS_MISSING =
    new Binary("realgraph.num_blocks.is_missing", Set(CountOfBlocks).asJava)
  // the number of mutes that the source user sent to the destination user
  val NUM_MUTES_MEAN =
    new Continuous("realgraph.num_mutes.mean", Set(CountOfMutes).asJava)
  val NUM_MUTES_EWMA =
    new Continuous("realgraph.num_mutes.ewma", Set(CountOfMutes).asJava)
  val NUM_MUTES_VARIANCE =
    new Continuous("realgraph.num_mutes.variance", Set(CountOfMutes).asJava)
  val NUM_MUTES_NON_ZERO_DAYS =
    new Continuous("realgraph.num_mutes.non_zero_days", Set(CountOfMutes).asJava)
  val NUM_MUTES_ELAPSED_DAYS =
    new Continuous("realgraph.num_mutes.elapsed_days", Set(CountOfMutes).asJava)
  val NUM_MUTES_DAYS_SINCE_LAST =
    new Continuous("realgraph.num_mutes.days_since_last", Set(CountOfMutes).asJava)
  val NUM_MUTES_IS_MISSING =
    new Binary("realgraph.num_mutes.is_missing", Set(CountOfMutes).asJava)
  // the number of report as abuses that the source user sent to the destination user
  val NUM_REPORTS_AS_ABUSES_MEAN =
    new Continuous("realgraph.num_report_as_abuses.mean", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_EWMA =
    new Continuous("realgraph.num_report_as_abuses.ewma", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_VARIANCE =
    new Continuous("realgraph.num_report_as_abuses.variance", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_NON_ZERO_DAYS =
    new Continuous("realgraph.num_report_as_abuses.non_zero_days", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_ELAPSED_DAYS =
    new Continuous("realgraph.num_report_as_abuses.elapsed_days", Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_DAYS_SINCE_LAST =
    new Continuous(
      "realgraph.num_report_as_abuses.days_since_last",
      Set(CountOfAbuseReports).asJava)
  val NUM_REPORTS_AS_ABUSES_IS_MISSING =
    new Binary("realgraph.num_report_as_abuses.is_missing", Set(CountOfAbuseReports).asJava)
  // the number of report as spams that the source user sent to the destination user
  val NUM_REPORTS_AS_SPAMS_MEAN =
    new Continuous(
      "realgraph.num_report_as_spams.mean",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)
  val NUM_REPORTS_AS_SPAMS_EWMA =
    new Continuous(
      "realgraph.num_report_as_spams.ewma",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)
  val NUM_REPORTS_AS_SPAMS_VARIANCE =
    new Continuous(
      "realgraph.num_report_as_spams.variance",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)
  val NUM_REPORTS_AS_SPAMS_NON_ZERO_DAYS =
    new Continuous(
      "realgraph.num_report_as_spams.non_zero_days",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)
  val NUM_REPORTS_AS_SPAMS_ELAPSED_DAYS =
    new Continuous(
      "realgraph.num_report_as_spams.elapsed_days",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)
  val NUM_REPORTS_AS_SPAMS_DAYS_SINCE_LAST =
    new Continuous(
      "realgraph.num_report_as_spams.days_since_last",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)
  val NUM_REPORTS_AS_SPAMS_IS_MISSING =
    new Binary(
      "realgraph.num_report_as_spams.is_missing",
      Set(CountOfAbuseReports, SafetyRelationships).asJava)

  val NUM_MUTUAL_FOLLOW_MEAN = new Continuous(
    "realgraph.num_mutual_follow.mean",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )
  val NUM_MUTUAL_FOLLOW_EWMA = new Continuous(
    "realgraph.num_mutual_follow.ewma",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )
  val NUM_MUTUAL_FOLLOW_VARIANCE = new Continuous(
    "realgraph.num_mutual_follow.variance",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )
  val NUM_MUTUAL_FOLLOW_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_mutual_follow.non_zero_days",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )
  val NUM_MUTUAL_FOLLOW_ELAPSED_DAYS = new Continuous(
    "realgraph.num_mutual_follow.elapsed_days",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )
  val NUM_MUTUAL_FOLLOW_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_mutual_follow.days_since_last",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )
  val NUM_MUTUAL_FOLLOW_IS_MISSING = new Binary(
    "realgraph.num_mutual_follow.is_missing",
    Set(
      Follow,
      PrivateAccountsFollowedBy,
      PublicAccountsFollowedBy,
      PrivateAccountsFollowing,
      PublicAccountsFollowing).asJava
  )

  val NUM_SMS_FOLLOW_MEAN = new Continuous(
    "realgraph.num_sms_follow.mean",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_SMS_FOLLOW_EWMA = new Continuous(
    "realgraph.num_sms_follow.ewma",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_SMS_FOLLOW_VARIANCE = new Continuous(
    "realgraph.num_sms_follow.variance",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_SMS_FOLLOW_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_sms_follow.non_zero_days",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_SMS_FOLLOW_ELAPSED_DAYS = new Continuous(
    "realgraph.num_sms_follow.elapsed_days",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_SMS_FOLLOW_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_sms_follow.days_since_last",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)
  val NUM_SMS_FOLLOW_IS_MISSING = new Binary(
    "realgraph.num_sms_follow.is_missing",
    Set(Follow, PrivateAccountsFollowedBy, PublicAccountsFollowedBy).asJava)

  val NUM_ADDRESS_BOOK_EMAIL_MEAN =
    new Continuous("realgraph.num_address_book_email.mean", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_EMAIL_EWMA =
    new Continuous("realgraph.num_address_book_email.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_EMAIL_VARIANCE =
    new Continuous("realgraph.num_address_book_email.variance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_EMAIL_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_address_book_email.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_EMAIL_ELAPSED_DAYS = new Continuous(
    "realgraph.num_address_book_email.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_EMAIL_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_address_book_email.days_since_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_EMAIL_IS_MISSING =
    new Binary("realgraph.num_address_book_email.is_missing", Set(AddressBook).asJava)

  val NUM_ADDRESS_BOOK_IN_BOTH_MEAN =
    new Continuous("realgraph.num_address_book_in_both.mean", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_IN_BOTH_EWMA =
    new Continuous("realgraph.num_address_book_in_both.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_IN_BOTH_VARIANCE = new Continuous(
    "realgraph.num_address_book_in_both.variance",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_IN_BOTH_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_address_book_in_both.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_IN_BOTH_ELAPSED_DAYS = new Continuous(
    "realgraph.num_address_book_in_both.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_IN_BOTH_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_address_book_in_both.days_since_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_IN_BOTH_IS_MISSING = new Binary(
    "realgraph.num_address_book_in_both.is_missing",
    Set(AddressBook).asJava
  )

  val NUM_ADDRESS_BOOK_PHONE_MEAN =
    new Continuous("realgraph.num_address_book_phone.mean", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_PHONE_EWMA =
    new Continuous("realgraph.num_address_book_phone.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_PHONE_VARIANCE =
    new Continuous("realgraph.num_address_book_phone.variance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_PHONE_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_address_book_phone.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_PHONE_ELAPSED_DAYS = new Continuous(
    "realgraph.num_address_book_phone.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_PHONE_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_address_book_phone.days_since_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_PHONE_IS_MISSING =
    new Binary("realgraph.num_address_book_phone.is_missing", Set(AddressBook).asJava)

  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_MEAN =
    new Continuous("realgraph.num_address_book_mutual_edge_email.mean", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_EWMA =
    new Continuous("realgraph.num_address_book_mutual_edge_email.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_VARIANCE =
    new Continuous("realgraph.num_address_book_mutual_edge_email.variance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_address_book_mutual_edge_email.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_ELAPSED_DAYS = new Continuous(
    "realgraph.num_address_book_mutual_edge_email.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_address_book_mutual_edge_email.days_since_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_EMAIL_IS_MISSING =
    new Binary("realgraph.num_address_book_mutual_edge_email.is_missing", Set(AddressBook).asJava)

  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_MEAN =
    new Continuous("realgraph.num_address_book_mutual_edge_in_both.mean", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_EWMA =
    new Continuous("realgraph.num_address_book_mutual_edge_in_both.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_VARIANCE = new Continuous(
    "realgraph.num_address_book_mutual_edge_in_both.variance",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_address_book_mutual_edge_in_both.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_ELAPSED_DAYS = new Continuous(
    "realgraph.num_address_book_mutual_edge_in_both.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_address_book_mutual_edge_in_both.days_since_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_IN_BOTH_IS_MISSING = new Binary(
    "realgraph.num_address_book_mutual_edge_in_both.is_missing",
    Set(AddressBook).asJava
  )

  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_MEAN =
    new Continuous("realgraph.num_address_book_mutual_edge_phone.mean", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_EWMA =
    new Continuous("realgraph.num_address_book_mutual_edge_phone.ewma", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_VARIANCE =
    new Continuous("realgraph.num_address_book_mutual_edge_phone.variance", Set(AddressBook).asJava)
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_NON_ZERO_DAYS = new Continuous(
    "realgraph.num_address_book_mutual_edge_phone.non_zero_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_ELAPSED_DAYS = new Continuous(
    "realgraph.num_address_book_mutual_edge_phone.elapsed_days",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_DAYS_SINCE_LAST = new Continuous(
    "realgraph.num_address_book_mutual_edge_phone.days_since_last",
    Set(AddressBook).asJava
  )
  val NUM_ADDRESS_BOOK_MUTUAL_EDGE_PHONE_IS_MISSING =
    new Binary("realgraph.num_address_book_mutual_edge_phone.is_missing", Set(AddressBook).asJava)
}

case class RealGraphEdgeDataRecordFeatures(
  edgeFeatureOpt: Option[RealGraphEdgeFeature],
  meanFeature: Continuous,
  ewmaFeature: Continuous,
  varianceFeature: Continuous,
  nonZeroDaysFeature: Continuous,
  elapsedDaysFeature: Continuous,
  daysSinceLastFeature: Continuous,
  isMissingFeature: Binary)
