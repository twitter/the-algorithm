package com.twitter.visibility.configapi.configs

import com.twitter.servo.decider.DeciderKeyEnum

private[visibility] object DeciderKey extends DeciderKeyEnum {

  val EnableAllSubscribedListsSafetyLevel: Value = Value(
    "visibility_library_enable_all_subscribed_lists_safety_level"
  )
  val EnableAdsBusinessSettingsSafetyLevel: Value = Value(
    "visibility_library_enable_ads_business_settings_safety_level"
  )
  val EnableAdsCampaignSafetyLevel: Value = Value(
    "visibility_library_enable_ads_campaign_safety_level"
  )
  val EnableAdsManagerSafetyLevel: Value = Value(
    "visibility_library_enable_ads_manager_safety_level"
  )
  val EnableAdsReportingDashboardSafetyLevel: Value = Value(
    "visibility_library_enable_ads_reporting_dashboard_safety_level"
  )
  val EnableAppealsSafetyLevel: Value = Value(
    "visibility_library_enable_appeals_safety_level"
  )
  val EnableArticleTweetTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_article_tweet_timeline_safety_level"
  )
  val EnableBaseQig: Value = Value(
    "visibility_library_enable_base_qig_safety_level"
  )
  val EnableBirdwatchNoteAuthorSafetyLevel: Value = Value(
    "visibility_library_enable_birdwatch_note_author_safety_level"
  )
  val EnableBirdwatchNoteTweetsTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_birdwatch_note_tweets_timeline_safety_level"
  )
  val EnableBirdwatchNeedsYourHelpNotificationsSafetyLevel: Value = Value(
    "visibility_library_enable_birdwatch_needs_your_help_notifications_safety_level"
  )
  val EnableBlockMuteUsersTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_block_mute_users_timeline_safety_level"
  )
  val EnableBrandSafetySafetyLevel: Value = Value(
    "visibility_library_enable_brand_safety_safety_level"
  )
  val EnableCardPollVotingSafetyLevel: Value = Value(
    "visibility_library_enable_card_poll_voting_safety_level"
  )
  val EnableCardsServiceSafetyLevel: Value = Value(
    "visibility_library_enable_cards_service_safety_level"
  )
  val EnableCommunitiesSafetyLevel: Value = Value(
    "visibility_library_enable_communities_safety_level"
  )
  val EnableContentControlToolInstallSafetyLevel: Value = Value(
    "visibility_library_enable_content_control_tool_install_safety_level"
  )
  val EnableConversationFocalPrehydrationSafetyLevel: Value = Value(
    "visibility_library_enable_conversation_focal_prehydration_safety_level"
  )
  val EnableConversationFocalTweetSafetyLevel: Value = Value(
    "visibility_library_enable_conversation_focal_tweet_safety_level"
  )
  val EnableConversationInjectedTweetSafetyLevel: Value = Value(
    "visibility_library_enable_conversation_injected_tweet_safety_level"
  )
  val EnableConversationReplySafetyLevel: Value = Value(
    "visibility_library_enable_conversation_reply_safety_level"
  )
  val EnableCuratedTrendsRepresentativeTweet: Value = Value(
    "visibility_library_curated_trends_representative_tweet"
  )
  val EnableCurationPolicyViolations: Value = Value(
    "visibility_library_curation_policy_violations"
  )
  val EnableDeprecatedSafetyLevelSafetyLevel: Value = Value(
    "visibility_library_enable_deprecated_safety_level_safety_level"
  )
  val EnableDevPlatformGetListTweetsSafetyLevel: Value = Value(
    "visibility_library_enable_dev_platform_get_list_tweets_safety_level"
  )
  val EnableDesFollowingAndFollowersUserListSafetyLevel: Value = Value(
    "visibility_library_enable_des_following_and_followers_user_list_safety_level"
  )
  val EnableDesHomeTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_des_home_timeline_safety_level"
  )
  val EnableDesQuoteTweetTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_des_quote_tweet_timeline_safety_level"
  )
  val EnableDesRealtimeSafetyLevel: Value = Value(
    "visibility_library_enable_des_realtime_safety_level"
  )
  val EnableDesRealtimeSpamEnrichmentSafetyLevel: Value = Value(
    "visibility_library_enable_des_realtime_spam_enrichment_safety_level"
  )
  val EnableDesRealtimeTweetFilterSafetyLevel: Value = Value(
    "visibility_library_enable_des_realtime_tweet_filter_safety_level"
  )
  val EnableDesRetweetingUsersSafetyLevel: Value = Value(
    "visibility_library_enable_des_retweeting_users_safety_level"
  )
  val EnableDesTweetDetailSafetyLevel: Value = Value(
    "visibility_library_enable_des_tweet_detail_safety_level"
  )
  val EnableDesTweetLikingUsersSafetyLevel: Value = Value(
    "visibility_library_enable_des_tweet_liking_users_safety_level"
  )
  val EnableDesUserBookmarksSafetyLevel: Value = Value(
    "visibility_library_enable_des_user_bookmarks_safety_level"
  )
  val EnableDesUserLikedTweetsSafetyLevel: Value = Value(
    "visibility_library_enable_des_user_liked_tweets_safety_level"
  )
  val EnableDesUserMentionsSafetyLevel: Value = Value(
    "visibility_library_enable_des_user_mentions_safety_level"
  )
  val EnableDesUserTweetsSafetyLevel: Value = Value(
    "visibility_library_enable_des_user_tweets_safety_level"
  )
  val EnableDevPlatformComplianceStreamSafetyLevel: Value = Value(
    "visibility_library_enable_dev_platform_compliance_stream_safety_level"
  )
  val EnableDirectMessagesSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_safety_level"
  )
  val EnableDirectMessagesConversationListSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_conversation_list_safety_level"
  )
  val EnableDirectMessagesConversationTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_conversation_timeline_safety_level"
  )
  val EnableDirectMessagesInboxSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_inbox_safety_level"
  )
  val EnableDirectMessagesMutedUsersSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_muted_users_safety_level"
  )
  val EnableDirectMessagesPinnedSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_pinned_safety_level"
  )
  val EnableDirectMessagesSearchSafetyLevel: Value = Value(
    "visibility_library_enable_direct_messages_search_safety_level"
  )
  val EnableElevatedQuoteTweetTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_elevated_quote_tweet_timeline_safety_level"
  )
  val EnableEmbeddedTweetSafetyLevel: Value = Value(
    "visibility_library_enable_embedded_tweet_safety_level"
  )
  val EnableEmbedsPublicInterestNoticeSafetyLevel: Value = Value(
    "visibility_library_enable_embeds_public_interest_notice_safety_level"
  )
  val EnableEmbedTweetMarkupSafetyLevel: Value = Value(
    "visibility_library_enable_embed_tweet_markup_safety_level"
  )
  val EnableWritePathLimitedActionsEnforcementSafetyLevel: Value = Value(
    "visibility_library_enable_write_path_limited_actions_enforcement_safety_level"
  )
  val EnableFilterDefaultSafetyLevel: Value = Value(
    "visibility_library_enable_filter_default_safety_level"
  )
  val EnableFilterNoneSafetyLevel: Value = Value(
    "visibility_library_enable_filter_none_safety_level"
  )

  val EnableFilterAllSafetyLevel: Value = Value(
    "visibility_library_enable_filter_all_safety_level"
  )
  val EnableFilterAllPlaceholderSafetyLevel: Value = Value(
    "visibility_library_enable_filter_all_placeholder_safety_level"
  )

  val EnableFollowedTopicsTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_followed_topics_timeline_safety_level"
  )

  val EnableFollowerConnectionsSafetyLevel: Value = Value(
    "visibility_library_enable_follower_connections_safety_level"
  )
  val EnableFollowingAndFollowersUserListSafetyLevel: Value = Value(
    "visibility_library_enable_following_and_followers_user_list_safety_level"
  )

  val EnableForDevelopmentOnlySafetyLevel: Value = Value(
    "visibility_library_enable_for_development_only_safety_level"
  )

  val EnableFriendsFollowingListSafetyLevel: Value = Value(
    "visibility_library_enable_friends_following_list_safety_level"
  )

  val EnableGraphqlDefaultSafetyLevel: Value = Value(
    "visibility_library_enable_graphql_default_safety_level"
  )

  val EnableGryphonDecksAndColumnsSafetyLevel: Value = Value(
    "visibility_library_enable_gryphon_decks_and_columns_safety_level"
  )

  val EnableHumanizationNudgeSafetyLevel: Value = Value(
    "visibility_library_enable_humanization_nudge_safety_level"
  )

  val EnableKitchenSinkDevelopmentSafetyLevel: Value = Value(
    "visibility_library_enable_kitchen_sink_development_safety_level"
  )

  val EnableListHeaderSafetyLevel: Value = Value(
    "visibility_library_enable_list_header_safety_level"
  )

  val EnableListMembershipsSafetyLevel: Value = Value(
    "visibility_library_enable_list_memberships_safety_level"
  )

  val EnableListOwnershipsSafetyLevel: Value = Value(
    "visibility_library_enable_list_ownerships_safety_level"
  )

  val EnableListRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_list_recommendations_safety_level"
  )

  val EnableListSearchSafetyLevel: Value = Value(
    "visibility_library_enable_list_search_safety_level"
  )

  val EnableListSubscriptionsSafetyLevel: Value = Value(
    "visibility_library_enable_list_subscriptions_safety_level"
  )

  val EnableLivePipelineEngagementCountsSafetyLevel: Value = Value(
    "visibility_library_enable_live_pipeline_engagement_counts_safety_level"
  )
  val EnableLiveVideoTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_live_video_timeline_safety_level"
  )
  val EnableMagicRecsAggressiveSafetyLevel: Value = Value(
    "visibility_library_enable_magic_recs_aggressive_safety_level"
  )
  val EnableMagicRecsAggressiveV2SafetyLevel: Value = Value(
    "visibility_library_enable_magic_recs_aggressive_v2_safety_level"
  )
  val EnableMagicRecsSafetyLevel: Value = Value(
    "visibility_library_enable_magic_recs_safety_level"
  )
  val EnableMagicRecsV2SafetyLevel: Value = Value(
    "visibility_library_enable_magic_recs_v2_safety_level"
  )
  val EnableMinimalSafetyLevel: Value = Value(
    "visibility_library_enable_minimal_safety_level"
  )
  val EnableModeratedTweetsTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_moderated_tweets_timeline_safety_level"
  )
  val EnableMomentsSafetyLevel: Value = Value(
    "visibility_library_enable_moments_safety_level"
  )
  val EnableNearbyTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_nearby_timeline_safety_level"
  )
  val EnableNewUserExperienceSafetyLevel: Value = Value(
    "visibility_library_enable_new_user_experience_safety_level"
  )
  val EnableNotificationsIbisSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_ibis_safety_level"
  )
  val EnableNotificationsPlatformSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_platform_safety_level"
  )
  val EnableNotificationsPlatformPushSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_platform_push_safety_level"
  )
  val EnableNotificationsReadSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_read_safety_level"
  )
  val EnableNotificationsTimelineDeviceFollowSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_timeline_device_follow_safety_level"
  )
  val EnableNotificationsWriteSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_write_safety_level"
  )
  val EnableNotificationsWriterV2SafetyLevel: Value = Value(
    "visibility_library_enable_notification_writer_v2_safety_level"
  )
  val EnableNotificationsWriterTweetHydratorSafetyLevel: Value = Value(
    "visibility_library_enable_notifications_writer_tweet_hydrator_safety_level"
  )
  val EnableQuickPromoteTweetEligibilitySafetyLevel: Value = Value(
    "visibility_library_enable_quick_promote_tweet_eligibility_safety_level"
  )
  val EnableQuoteTweetTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_quote_tweet_timeline_safety_level"
  )
  val EnableQuotedTweetRulesSafetyLevel: Value = Value(
    "visibility_library_enable_quoted_tweet_rules_safety_level"
  )
  val EnableRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_recommendations_safety_level"
  )
  val EnableRecosVideoSafetyLevel: Value = Value(
    "visibility_library_enable_recos_video_safety_level"
  )
  val EnableRecosWritePathSafetyLevel: Value = Value(
    "visibility_library_enable_recos_write_path_safety_level"
  )
  val EnableRepliesGroupingSafetyLevel: Value = Value(
    "visibility_library_enable_replies_grouping_safety_level"
  )
  val EnableReportCenterSafetyLevel: Value = Value(
    "visibility_library_enable_report_center_safety_level"
  )
  val EnableReturningUserExperienceSafetyLevel: Value = Value(
    "visibility_library_enable_returning_user_experience_safety_level"
  )
  val EnableReturningUserExperienceFocalTweetSafetyLevel: Value = Value(
    "visibility_library_enable_returning_user_experience_focal_tweet_safety_level"
  )
  val EnableRevenueSafetyLevel: Value = Value(
    "visibility_library_enable_revenue_safety_level"
  )
  val EnableRitoActionedTweetTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_rito_actioned_tweet_timeline_safety_level"
  )
  val EnableSafeSearchMinimalSafetyLevel: Value = Value(
    "visibility_library_enable_safe_search_minimal_safety_level"
  )
  val EnableSafeSearchStrictSafetyLevel: Value = Value(
    "visibility_library_enable_safe_search_strict_safety_level"
  )
  val EnableAccessInternalPromotedContentSafetyLevel: Value = Value(
    "visibility_library_enable_access_internal_promoted_content_safety_level"
  )
  val EnableSearchHydration: Value = Value(
    "visibility_library_enable_search_hydration_safety_level"
  )
  val EnableSearchLatest: Value = Value(
    "visibility_library_enable_search_latest_safety_level"
  )
  val EnableSearchMixerSrpMinimalSafetyLevel: Value = Value(
    "visibility_library_enable_search_mixer_srp_minimal_safety_level"
  )
  val EnableSearchMixerSrpStrictSafetyLevel: Value = Value(
    "visibility_library_enable_search_mixer_srp_strict_safety_level"
  )
  val EnableUserSearchSrpSafetyLevel: Value = Value(
    "visibility_library_enable_user_search_srp_safety_level"
  )
  val EnableUserSearchTypeaheadSafetyLevel: Value = Value(
    "visibility_library_enable_user_search_typeahead_safety_level"
  )
  val EnableSearchPeopleSrp: Value = Value(
    "visibility_library_enable_search_people_srp_safety_level"
  )
  val EnableSearchPeopleTypeahead: Value = Value(
    "visibility_library_enable_search_people_typeahead_safety_level"
  )
  val EnableSearchPhoto: Value = Value(
    "visibility_library_enable_search_photo_safety_level"
  )
  val EnableSearchTop: Value = Value(
    "visibility_library_enable_search_top_safety_level"
  )
  val EnableSearchTopQig: Value = Value(
    "visibility_library_enable_search_top_qig_safety_level"
  )
  val EnableSearchTrendTakeoverPromotedTweet: Value = Value(
    "visibility_library_enable_search_trend_takeover_promoted_tweet_safety_level"
  )
  val EnableSearchVideo: Value = Value(
    "visibility_library_enable_search_video_safety_level"
  )
  val EnableSearchLatestUserRules: Value = Value(
    "visibility_library_enable_search_latest_user_rules_safety_level"
  )
  val EnableShoppingManagerSpyModeSafetyLevel: Value = Value(
    "visibility_library_enable_shopping_manager_spy_mode_safety_level"
  )
  val EnableSignalsReactions: Value = Value(
    "visibility_library_enable_signals_reactions_safety_level"
  )
  val EnableSignalsTweetReactingUsers: Value = Value(
    "visibility_library_enable_signals_tweet_reacting_users_safety_level"
  )
  val EnableSocialProof: Value = Value(
    "visibility_library_enable_social_proof_safety_level"
  )
  val EnableSoftInterventionPivot: Value = Value(
    "visibility_library_enable_soft_intervention_pivot_safety_level"
  )
  val EnableSpaceFleetlineSafetyLevel: Value = Value(
    "visibility_library_enable_space_fleetline_safety_level"
  )
  val EnableSpaceHomeTimelineUprankingSafetyLevel: Value = Value(
    "visibility_library_enable_space_home_timeline_upranking_safety_level"
  )
  val EnableSpaceJoinScreenSafetyLevel: Value = Value(
    "visibility_library_enable_space_join_screen_safety_level"
  )
  val EnableSpaceNotificationsSafetyLevel: Value = Value(
    "visibility_library_enable_space_notifications_safety_level"
  )
  val EnableSpacesSafetyLevel: Value = Value(
    "visibility_library_enable_spaces_safety_level"
  )
  val EnableSpacesParticipantsSafetyLevel: Value = Value(
    "visibility_library_enable_spaces_participants_safety_level"
  )
  val EnableSpacesSellerApplicationStatus: Value = Value(
    "visibility_library_enable_spaces_seller_application_status_safety_level"
  )
  val EnableSpacesSharingSafetyLevel: Value = Value(
    "visibility_library_enable_spaces_sharing_safety_level"
  )
  val EnableSpaceTweetAvatarHomeTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_space_tweet_avatar_home_timeline_safety_level"
  )
  val EnableStickersTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_stickers_timeline_safety_level"
  )
  val EnableStratoExtLimitedEngagementsSafetyLevel: Value = Value(
    "visibility_library_enable_strato_ext_limited_engagements_safety_level"
  )
  val EnableStreamServicesSafetyLevel: Value = Value(
    "visibility_library_enable_stream_services_safety_level"
  )
  val EnableTestSafetyLevel: Value = Value(
    "visibility_library_enable_test_safety_level"
  )
  val EnableTimelineBookmarkSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_bookmark_safety_level"
  )
  val EnableTimelineContentControlsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_content_controls_safety_level"
  )
  val EnableTimelineConversationsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_conversations_safety_level"
  )
  val EnableTimelineConversationsDownrankingSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_conversations_downranking_safety_level"
  )
  val EnableTimelineConversationsDownrankingMinimalSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_conversations_downranking_minimal_safety_level"
  )
  val EnableTimelineFavoritesSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_favorites_safety_level"
  )
  val EnableSelfViewTimelineFavoritesSafetyLevel: Value = Value(
    "visibility_library_enable_self_view_timeline_favorites_safety_level"
  )
  val EnableTweetTimelineFocalTweetSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_focal_tweet_safety_level"
  )
  val EnableTweetScopedTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_scoped_timeline_safety_level"
  )
  val EnableTimelineFollowingActivitySafetyLevel: Value = Value(
    "visibility_library_enable_timeline_following_activity_safety_level"
  )
  val EnableTimelineHomeSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_safety_level"
  )
  val EnableTimelineHomeCommunitiesSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_communities_safety_level"
  )
  val EnableTimelineHomeHydrationSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_hydration_safety_level"
  )
  val EnableTimelineHomeLatestSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_latest_safety_level"
  )
  val EnableTimelineHomeRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_recommendations_safety_level"
  )
  val EnableTimelineHomeTopicFollowRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_topic_follow_recommendations_safety_level"
  )
  val EnableTimelineScorerSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_scorer_safety_level"
  )
  val EnableTopicsLandingPageTopicRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_topics_landing_page_topic_recommendations_safety_level"
  )
  val EnableExploreRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_explore_recommendations_safety_level"
  )
  val EnableTimelineInjectionSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_injection_safety_level"
  )
  val EnableTimelineLikedBySafetyLevel: Value = Value(
    "visibility_library_enable_timeline_liked_by_safety_level"
  )
  val EnableTimelineListsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_lists_safety_level"
  )
  val EnableTimelineMediaSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_media_safety_level"
  )
  val EnableTimelineMentionsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_mentions_safety_level"
  )
  val EnableTimelineModeratedTweetsHydrationSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_moderated_tweets_hydration_safety_level"
  )
  val EnableTimelineProfileSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_profile_safety_level"
  )
  val EnableTimelineProfileAllSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_profile_all_safety_level"
  )
  val EnableTimelineProfileSpacesSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_profile_spaces_safety_level")
  val EnableTimelineProfileSuperFollowsSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_profile_super_follows_safety_level"
  )
  val EnableTimelineReactiveBlendingSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_reactive_blending_safety_level"
  )
  val EnableTimelineRetweetedBySafetyLevel: Value = Value(
    "visibility_library_enable_timeline_retweeted_by_safety_level"
  )
  val EnableTimelineSuperLikedBySafetyLevel: Value = Value(
    "visibility_library_enable_timeline_super_liked_by_safety_level"
  )
  val EnableTombstoningSafetyLevel: Value = Value(
    "visibility_library_enable_tombstoning_safety_level"
  )
  val EnableTopicRecommendationsSafetyLevel: Value = Value(
    "visibility_library_enable_topic_recommendations_safety_level"
  )
  val EnableTrendsRepresentativeTweetSafetyLevel: Value = Value(
    "visibility_library_enable_trends_representative_tweet_safety_level"
  )
  val EnableTrustedFriendsUserListSafetyLevel: Value = Value(
    "visibility_library_enable_trusted_friends_user_list_safety_level"
  )
  val EnableTwitterDelegateUserListSafetyLevel: Value = Value(
    "visibility_library_enable_twitter_delegate_user_list_safety_level"
  )
  val EnableTweetDetailSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_detail_safety_level"
  )
  val EnableTweetDetailNonTooSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_detail_non_too_safety_level"
  )
  val EnableTweetDetailWithInjectionsHydrationSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_detail_with_injections_hydration_safety_level"
  )
  val EnableTweetEngagersSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_engagers_safety_level"
  )
  val EnableTweetReplyNudgeSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_reply_nudge_safety_level"
  )
  val EnableTweetWritesApiSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_writes_api_safety_level"
  )
  val EnableTwitterArticleComposeSafetyLevel: Value = Value(
    "visibility_library_enable_twitter_article_compose_safety_level"
  )
  val EnableTwitterArticleProfileTabSafetyLevel: Value = Value(
    "visibility_library_enable_twitter_article_profile_tab_safety_level"
  )
  val EnableTwitterArticleReadSafetyLevel: Value = Value(
    "visibility_library_enable_twitter_article_read_safety_level"
  )
  val EnableUserProfileHeaderSafetyLevel: Value = Value(
    "visibility_library_enable_user_profile_header_safety_level"
  )
  val EnableUserMilestoneRecommendationSafetyLevel: Value = Value(
    "visibility_library_enable_user_milestone_recommendation_safety_level"
  )
  val EnableUserScopedTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_user_scoped_timeline_safety_level"
  )
  val EnableUserSettingsSafetyLevel: Value = Value(
    "visibility_library_enable_user_settings_safety_level"
  )
  val EnableVideoAdsSafetyLevel: Value = Value(
    "visibility_library_enable_video_ads_safety_level"
  )
  val EnableTimelineHomePromotedHydrationSafetyLevel: Value = Value(
    "visibility_library_enable_timeline_home_promoted_hydration_safety_level"
  )

  val EnableSuperFollowerConnectionsSafetyLevel: Value = Value(
    "visibility_library_enable_super_follower_connnections_safety_level"
  )

  val EnableSuperLikeSafetyLevel: Value = Value(
    "visibility_library_enable_super_like_safety_level"
  )

  val EnableZipbirdConsumerArchivesSafetyLevel: Value = Value(
    "visibility_library_enable_zipbird_consumer_archives_safety_level"
  )

  val EnableTweetAwardSafetyLevel: Value = Value(
    "visibility_library_enable_tweet_award_safety_level"
  )

  val EnableTweetConversationControlRules: Value = Value(
    "visibility_library_enable_conversation_control_rules"
  )
  val EnableCommunityTweetsControlRules: Value = Value(
    "visibility_library_enable_community_tweets_rules"
  )
  val EnableDropCommunityTweetWithUndefinedCommunityRule: Value = Value(
    "visibility_library_enable_drop_community_tweet_with_undefined_community_rule"
  )
  val EnablePSpammyTweetDownrankConvosLowQuality: Value = Value(
    "visibility_library_enable_p_spammy_tweet_downrank_convos_low_quality"
  )
  val EnableHighPSpammyTweetScoreSearchTweetLabelDropRule: Value = Value(
    "visibility_library_enable_high_p_spammy_tweet_score_search_tweet_label_drop_rule"
  )

  val EnableRitoActionedTweetDownrankConvosLowQuality: Value = Value(
    "visibility_library_enable_rito_actioned_tweet_downrank_convos_low_quality"
  )

  val EnableNewSensitiveMediaSettingsInterstitialRulesHomeTimeline: Value = Value(
    "visibility_library_enable_new_sensitive_media_settings_interstitial_rules_home_timeline")

  val EnableLegacySensitiveMediaRulesHomeTimeline: Value = Value(
    "visibility_library_enable_legacy_sensitive_media_rules_home_timeline"
  )

  val EnableNewSensitiveMediaSettingsInterstitialRulesConversation: Value = Value(
    "visibility_library_enable_new_sensitive_media_settings_interstitial_rules_conversation"
  )

  val EnableLegacySensitiveMediaRulesConversation: Value = Value(
    "visibility_library_enable_legacy_sensitive_media_rules_conversation"
  )

  val EnableNewSensitiveMediaSettingsInterstitialRulesProfileTimeline: Value = Value(
    "visibility_library_enable_new_sensitive_media_settings_interstitials_rules_profile_timeline"
  )

  val EnableLegacySensitiveMediaRulesProfileTimeline: Value = Value(
    "visibility_library_enable_legacy_sensitive_media_rules_profile_timeline"
  )

  val EnableNewSensitiveMediaSettingsInterstitialRulesTweetDetail: Value = Value(
    "visibility_library_enable_new_sensitive_media_settings_interstitials_rules_tweet_detail"
  )

  val EnableLegacySensitiveMediaRulesTweetDetail: Value = Value(
    "visibility_library_enable_legacy_sensitive_media_rules_tweet_detail"
  )

  val EnableLegacySensitiveMediaRulesDirectMessages: Value = Value(
    "visibility_library_enable_legacy_sensitive_media_rules_direct_messages"
  )

  val VisibilityLibraryEnableToxicReplyFilterConversation: Value = Value(
    "visibility_library_enable_toxic_reply_filter_conversation"
  )

  val VisibilityLibraryEnableToxicReplyFilterNotifications: Value = Value(
    "visibility_library_enable_toxic_reply_filter_notifications"
  )

  val EnableSmyteSpamTweetRule: Value = Value(
    "visibility_library_enable_smyte_spam_tweet_rule"
  )

  val EnableHighSpammyTweetContentScoreSearchLatestTweetLabelDropRule: Value = Value(
    "visibility_library_enable_high_spammy_tweet_content_score_search_latest_tweet_label_drop_rule"
  )
  val EnableHighSpammyTweetContentScoreSearchTopTweetLabelDropRule: Value = Value(
    "visibility_library_enable_high_spammy_tweet_content_score_search_top_tweet_label_drop_rule"
  )
  val EnableHighSpammyTweetContentScoreConvoDownrankAbusiveQualityRule: Value = Value(
    "visibility_library_enable_high_spammy_tweet_content_score_convo_downrank_abusive_quality_rule"
  )

  val EnableHighCryptospamScoreConvoDownrankAbusiveQualityRule: Value = Value(
    "visibility_library_enable_high_cryptospam_score_convo_downrank_abusive_quality_rule"
  )
  val EnableHighSpammyTweetContentScoreTrendsTopTweetLabelDropRule: Value = Value(
    "visibility_library_enable_high_spammy_tweet_content_score_trends_top_tweet_label_drop_rule"
  )

  val EnableHighSpammyTweetContentScoreTrendsLatestTweetLabelDropRule: Value = Value(
    "visibility_library_enable_high_spammy_tweet_content_score_trends_latest_tweet_label_drop_rule"
  )

  val EnableGoreAndViolenceTopicHighRecallTweetLabelRule: Value = Value(
    "visibility_library_enable_gore_and_violence_topic_high_recall_tweet_label_rule"
  )

  val EnableLimitRepliesFollowersConversationRule: Value = Value(
    "visibility_library_enable_limit_replies_followers_conversation_rule"
  )

  val EnableBlinkBadDownrankingRule: Value = Value(
    "visibility_library_enable_blink_bad_downranking_rule"
  )

  val EnableBlinkWorstDownrankingRule: Value = Value(
    "visibility_library_enable_blink_worst_downranking_rule"
  )

  val EnableCopypastaSpamDownrankConvosAbusiveQualityRule: Value = Value(
    "visibility_library_enable_copypasta_spam_downrank_convos_abusive_quality_rule"
  )

  val EnableCopypastaSpamSearchDropRule: Value = Value(
    "visibility_library_enable_copypasta_spam_search_drop_rule"
  )

  val EnableSpammyUserModelHighPrecisionDropTweetRule: Value = Value(
    "visibility_library_enable_spammy_user_model_high_precision_drop_tweet_rule"
  )

  val EnableAvoidNsfwRules: Value = Value(
    "visibility_library_enable_avoid_nsfw_rules"
  )

  val EnableReportedTweetInterstitialRule: Value = Value(
    "visibility_library_enable_reported_tweet_interstitial_rule"
  )

  val EnableReportedTweetInterstitialSearchRule: Value = Value(
    "visibility_library_enable_reported_tweet_interstitial_search_rule"
  )

  val EnableDropExclusiveTweetContentRule: Value = Value(
    "visibility_library_enable_drop_exclusive_tweet_content_rule"
  )

  val EnableDropExclusiveTweetContentRuleFailClosed: Value = Value(
    "visibility_library_enable_drop_exclusive_tweet_content_rule_fail_closed"
  )

  val EnableTombstoneExclusiveQtProfileTimelineParam: Value = Value(
    "visibility_library_enable_tombstone_exclusive_quoted_tweet_content_rule"
  )

  val EnableDropAllExclusiveTweetsRule: Value = Value(
    "visibility_library_enable_drop_all_exclusive_tweets_rule"
  )

  val EnableDropAllExclusiveTweetsRuleFailClosed: Value = Value(
    "visibility_library_enable_drop_all_exclusive_tweets_rule_fail_closed"
  )

  val EnableDownrankSpamReplySectioningRule: Value = Value(
    "visibility_library_enable_downrank_spam_reply_sectioning_rule"
  )

  val EnableNsfwTextSectioningRule: Value = Value(
    "visibility_library_enable_nsfw_text_sectioning_rule"
  )

  val EnableNsfwAgeBasedMediaDropRules: Value = Value(
    "visibility_library_enable_nsfw_age_based_media_drop_rules"
  )

  val EnableNsfwUnderageRules: Value = Value(
    "visibility_library_enable_nsfw_underage_rules"
  )

  val EnableNsfwUnderageMediaRules: Value = Value(
    "visibility_library_enable_nsfw_underage_media_rules"
  )

  val EnableNsfwMissingAgeRules: Value = Value(
    "visibility_library_enable_nsfw_missing_age_rules"
  )

  val EnableNsfwMissingAgeMediaRules: Value = Value(
    "visibility_library_enable_nsfw_missing_age_media_rules"
  )

  val EnableSearchIpiSafeSearchWithoutUserInQueryDropRule: Value = Value(
    "visibility_library_enable_search_ipi_safe_search_without_user_in_query_drop_rule"
  )

  val EnableTimelineHomePromotedTweetHealthEnforcementRules: Value = Value(
    "visibility_library_enable_timeline_home_promoted_tweet_health_enforcement_rules"
  )

  val EnableMutedKeywordFilteringSpaceTitleNotificationsRule: Value = Value(
    "visibility_library_enable_muted_keyword_filtering_space_title_notifications_rule"
  )

  val EnableDropTweetsWithGeoRestrictedMediaRule: Value = Value(
    "visibility_library_enable_drop_tweets_with_georestricted_media_rule"
  )
  val EnableDropAllTrustedFriendsTweetsRule: Value = Value(
    "visibility_library_enable_drop_all_trusted_friends_tweets_rule"
  )

  val EnableDropTrustedFriendsTweetContentRule: Value = Value(
    "visibility_library_enable_drop_all_trusted_friends_tweet_content_rule"
  )

  val EnableDropCollabInvitationTweetsRule: Value = Value(
    "visibility_library_enable_drop_all_collab_invitation_tweets_rule"
  )

  val EnableFetchTweetReportedPerspective: Value = Value(
    "visibility_library_enable_fetch_tweet_reported_perspective"
  )

  val EnableFetchTweetMediaMetadata: Value = Value(
    "visibility_library_enable_fetch_tweet_media_metadata"
  )

  val VisibilityLibraryEnableFollowCheckInMutedKeyword: Value = Value(
    "visibility_library_enable_follow_check_in_mutedkeyword"
  )

  val VisibilityLibraryEnableMediaInterstitialComposition: Value = Value(
    "visibility_library_enable_media_interstitial_composition"
  )

  val EnableVerdictScribingFromTweetVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_scribing_from_tweet_visibility_library"
  )

  val EnableVerdictLoggerEventPublisherInstantiationFromTweetVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_logger_event_publisher_instantiation_from_tweet_visibility_library"
  )

  val EnableVerdictScribingFromTimelineConversationsVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_scribing_from_timeline_conversations_visibility_library"
  )

  val EnableVerdictLoggerEventPublisherInstantiationFromTimelineConversationsVisibilityLibrary: Value =
    Value(
      "visibility_library_enable_verdict_logger_event_publisher_instantiation_from_timeline_conversations_visibility_library"
    )

  val EnableVerdictScribingFromBlenderVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_scribing_from_blender_visibility_library"
  )

  val EnableVerdictScribingFromSearchVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_scribing_from_search_visibility_library"
  )

  val EnableVerdictLoggerEventPublisherInstantiationFromBlenderVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_logger_event_publisher_instantiation_from_blender_visibility_library"
  )

  val EnableVerdictLoggerEventPublisherInstantiationFromSearchVisibilityLibrary: Value = Value(
    "visibility_library_enable_verdict_logger_event_publisher_instantiation_from_search_visibility_library"
  )

  val EnableLocalizedTombstoneOnVisibilityResults: Value = Value(
    "visibility_library_enable_localized_tombstones_on_visibility_results"
  )

  val EnableShortCircuitingFromTweetVisibilityLibrary: Value = Value(
    "visibility_library_enable_short_circuiting_from_tweet_visibility_library"
  )

  val EnableShortCircuitingFromTimelineConversationsVisibilityLibrary: Value = Value(
    "visibility_library_enable_short_circuiting_from_timeline_conversations_visibility_library"
  )

  val EnableShortCircuitingFromBlenderVisibilityLibrary: Value = Value(
    "visibility_library_enable_short_circuiting_from_blender_visibility_library"
  )

  val EnableShortCircuitingFromSearchVisibilityLibrary: Value = Value(
    "visibility_library_enable_short_circuiting_from_search_visibility_library"
  )

  val EnableNsfwTextHighPrecisionDropRule: Value = Value(
    "visibility_library_enable_nsfw_text_high_precision_drop_rule"
  )

  val EnableSpammyTweetRuleVerdictLogging: Value = Value(
    "visibility_library_enable_spammy_tweet_rule_verdict_logging"
  )

  val EnableDownlevelRuleVerdictLogging: Value = Value(
    "visibility_library_enable_downlevel_rule_verdict_logging"
  )

  val EnableLikelyIvsUserLabelDropRule: Value = Value(
    "visibility_library_enable_likely_likely_ivs_user_label_drop_rule"
  )

  val EnableCardVisibilityLibraryCardUriParsing: Value = Value(
    "visibility_library_enable_card_visibility_library_card_uri_parsing"
  )

  val EnableCardUriRootDomainDenylistRule: Value = Value(
    "visibility_library_enable_card_uri_root_domain_deny_list_rule"
  )

  val EnableCommunityNonMemberPollCardRule: Value = Value(
    "visibility_library_enable_community_non_member_poll_card_rule"
  )

  val EnableCommunityNonMemberPollCardRuleFailClosed: Value = Value(
    "visibility_library_enable_community_non_member_poll_card_rule_fail_closed"
  )

  val EnableExperimentalNudgeLabelRule: Value = Value(
    "visibility_library_enable_experimental_nudge_label_rule"
  )

  val NsfwHighPrecisionUserLabelAvoidTweetRuleEnabledParam: Value = Value(
    "visibility_library_nsfw_high_precision_user_label_avoid_tweet_rule_enabled"
  )

  val EnableUserSelfViewOnlySafetyLevel: Value = Value(
    "visibility_library_enable_user_self_view_only_safety_level"
  )

  val EnableNewAdAvoidanceRules: Value = Value(
    "visibility_library_enable_new_ad_avoidance_rules"
  )

  val EnableNsfaHighRecallAdAvoidanceParam: Value = Value(
    "visibility_library_enable_nsfa_high_recall_ad_avoidance_rules"
  )

  val EnableNsfaKeywordsHighPrecisionAdAvoidanceParam: Value = Value(
    "visibility_library_enable_nsfa_keywords_high_precision_ad_avoidance_rules"
  )

  val EnableStaleTweetDropRuleParam: Value = Value(
    "visibility_library_enable_stale_tweet_drop_rule"
  )

  val EnableStaleTweetDropRuleFailClosedParam: Value = Value(
    "visibility_library_enable_stale_tweet_drop_rule_fail_closed"
  )

  val EnableEditHistoryTimelineSafetyLevel: Value = Value(
    "visibility_library_enable_edit_history_timeline_safety_level"
  )

  val EnableDeleteStateTweetRules: Value = Value(
    "visibility_library_enable_delete_state_tweet_rules"
  )

  val EnableSpacesSharingNsfwDropRulesParam: Value = Value(
    "visibility_library_enable_spaces_sharing_nsfw_drop_rule"
  )

  val EnableDropMediaLegalRulesParam: Value = Value(
    "visibility_library_enable_drop_media_legal_rules"
  )

  val EnableTombstoneMediaLegalRulesParam: Value = Value(
    "visibility_library_enable_tombstone_media_legal_rules"
  )

  val EnableInterstitialMediaLegalRulesParam: Value = Value(
    "visibility_library_enable_interstitial_media_legal_rules"
  )

  val EnableViewerIsSoftUserDropRuleParam: Value = Value(
    "visibility_library_enable_viewer_is_soft_user_drop_rule"
  )

  val EnableBackendLimitedActions: Value = Value(
    "visibility_library_enable_backend_limited_actions"
  )

  val EnableNotificationsQig: Value = Value(
    "visibility_library_enable_notifications_qig_safety_level"
  )

  val EnablePdnaQuotedTweetTombstoneRule: Value = Value(
    "visibility_library_enable_pdna_quoted_tweet_tombstone_rule"
  )

  val EnableSpamQuotedTweetTombstoneRule: Value = Value(
    "visibility_library_enable_spam_quoted_tweet_tombstone_rule"
  )

  val EnableNsfwHpQuotedTweetDropRule: Value = Value(
    "visibility_library_enable_nsfw_hp_quoted_tweet_drop_experiment_rule"
  )
  val EnableNsfwHpQuotedTweetTombstoneRule: Value = Value(
    "visibility_library_enable_nsfw_hp_quoted_tweet_tombstone_experiment_rule"
  )

  val EnableExperimentalRuleEngine: Value = Value(
    "visibility_library_enable_experimental_rule_engine"
  )

  val EnableFosnrRules: Value = Value(
    "visibility_library_enable_fosnr_rules"
  )

  val EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRule: Value = Value(
    "visibility_library_enable_inner_quoted_tweet_viewer_blocks_author_interstitial_rule"
  )

  val EnableInnerQuotedTweetViewerMutesAuthorInterstitialRule: Value = Value(
    "visibility_library_enable_inner_quoted_tweet_viewer_mutes_author_interstitial_rule"
  )

  val EnableLocalizedInterstitialGenerator: Value = Value(
    "visibility_library_enable_localized_interstitial_generator"
  )

  val EnableProfileMixeMediaSafetyLevel: Value = Value(
    "visibility_library_enable_profile_mixer_media_safety_level")

  val EnableConvosLocalizedInterstitial: Value = Value(
    "visibility_library_convos_enable_localized_interstitial"
  )

  val EnableConvosLegacyInterstitial: Value = Value(
    "visibility_library_convos_enable_legacy_interstitial"
  )

  val DisableLegacyInterstitialFilteredReason: Value = Value(
    "visibility_library_disable_legacy_interstitial_filtered_reason"
  )

  val EnableSearchBasicBlockMuteRules: Value = Value(
    "visibility_library_enable_search_basic_block_mute_rules"
  )

  val EnableLocalizedInterstitialInUserStateLib: Value = Value(
    "visibility_library_enable_localized_interstitial_user_state_lib"
  )

  val EnableProfileMixerFavoritesSafetyLevel: Value = Value(
    "visibility_library_enable_profile_mixer_favorites_safety_level")

  val EnableAbusiveBehaviorDropRule: Value = Value(
    "visibility_library_enable_abusive_behavior_drop_rule"
  )

  val EnableAbusiveBehaviorInterstitialRule: Value = Value(
    "visibility_library_enable_abusive_behavior_interstitial_rule"
  )

  val EnableAbusiveBehaviorLimitedEngagementsRule: Value = Value(
    "visibility_library_enable_abusive_behavior_limited_engagements_rule"
  )

  val EnableNotGraduatedDownrankConvosAbusiveQualityRule: Value = Value(
    "visibility_library_enable_not_graduated_downrank_convos_abusive_quality_rule"
  )

  val EnableNotGraduatedSearchDropRule: Value = Value(
    "visibility_library_enable_not_graduated_search_drop_rule"
  )

  val EnableNotGraduatedDropRule: Value = Value(
    "visibility_library_enable_not_graduated_drop_rule"
  )

  val EnableMemoizeSafetyLevelParams: Value = Value(
    "visibility_library_enable_memoize_safety_level_params"
  )

  val EnableAuthorBlocksViewerDropRule: Value = Value(
    "visibility_library_enable_author_blocks_viewer_drop_rule"
  )
}
