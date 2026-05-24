package com.twitter.visibility.configapi.configs

import com.twitter.decider.Recipient
import com.twitter.decider.SimpleRecipient
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging.Logger
import com.twitter.servo.decider.DeciderGateBuilder
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.BaseRequestContext
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.Param
import com.twitter.timelines.configapi.WithGuestId
import com.twitter.timelines.configapi.WithUserId
import com.twitter.timelines.configapi.decider.DeciderSwitchOverrideValue
import com.twitter.timelines.configapi.decider.GuestRecipient
import com.twitter.timelines.configapi.decider.RecipientBuilder
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.configapi.params.TimelineConversationsDownrankingSpecificParams
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.SafetyLevel._

private[visibility] object VisibilityDeciders {
  val SafetyLevelToDeciderMap: Map[SafetyLevel, DeciderKey.Value] = Map(
    AllSubscribedLists -> DeciderKey.EnableAllSubscribedListsSafetyLevel,
    AccessInternalPromotedContent -> DeciderKey.EnableAccessInternalPromotedContentSafetyLevel,
    AdsBusinessSettings -> DeciderKey.EnableAdsBusinessSettingsSafetyLevel,
    AdsCampaign -> DeciderKey.EnableAdsCampaignSafetyLevel,
    AdsManager -> DeciderKey.EnableAdsManagerSafetyLevel,
    AdsReportingDashboard -> DeciderKey.EnableAdsReportingDashboardSafetyLevel,
    Appeals -> DeciderKey.EnableAppealsSafetyLevel,
    ArticleTweetTimeline -> DeciderKey.EnableArticleTweetTimelineSafetyLevel,
    BaseQig -> DeciderKey.EnableBaseQig,
    BirdwatchNoteAuthor -> DeciderKey.EnableBirdwatchNoteAuthorSafetyLevel,
    BirdwatchNoteTweetsTimeline -> DeciderKey.EnableBirdwatchNoteTweetsTimelineSafetyLevel,
    BirdwatchNeedsYourHelpNotifications -> DeciderKey.EnableBirdwatchNeedsYourHelpNotificationsSafetyLevel,
    BlockMuteUsersTimeline -> DeciderKey.EnableBlockMuteUsersTimelineSafetyLevel,
    BrandSafety -> DeciderKey.EnableBrandSafetySafetyLevel,
    CardPollVoting -> DeciderKey.EnableCardPollVotingSafetyLevel,
    CardsService -> DeciderKey.EnableCardsServiceSafetyLevel,
    Communities -> DeciderKey.EnableCommunitiesSafetyLevel,
    ContentControlToolInstall -> DeciderKey.EnableContentControlToolInstallSafetyLevel,
    ConversationFocalPrehydration -> DeciderKey.EnableConversationFocalPrehydrationSafetyLevel,
    ConversationFocalTweet -> DeciderKey.EnableConversationFocalTweetSafetyLevel,
    ConversationInjectedTweet -> DeciderKey.EnableConversationInjectedTweetSafetyLevel,
    ConversationReply -> DeciderKey.EnableConversationReplySafetyLevel,
    CuratedTrendsRepresentativeTweet -> DeciderKey.EnableCuratedTrendsRepresentativeTweet,
    CurationPolicyViolations -> DeciderKey.EnableCurationPolicyViolations,
    DeprecatedSafetyLevel -> DeciderKey.EnableDeprecatedSafetyLevelSafetyLevel,
    DevPlatformGetListTweets -> DeciderKey.EnableDevPlatformGetListTweetsSafetyLevel,
    DesFollowingAndFollowersUserList -> DeciderKey.EnableDesFollowingAndFollowersUserListSafetyLevel,
    DesHomeTimeline -> DeciderKey.EnableDesHomeTimelineSafetyLevel,
    DesQuoteTweetTimeline -> DeciderKey.EnableDesQuoteTweetTimelineSafetyLevel,
    DesRealtime -> DeciderKey.EnableDesRealtimeSafetyLevel,
    DesRealtimeSpamEnrichment -> DeciderKey.EnableDesRealtimeSpamEnrichmentSafetyLevel,
    DesRealtimeTweetFilter -> DeciderKey.EnableDesRealtimeTweetFilterSafetyLevel,
    DesRetweetingUsers -> DeciderKey.EnableDesRetweetingUsersSafetyLevel,
    DesTweetDetail -> DeciderKey.EnableDesTweetDetailSafetyLevel,
    DesTweetLikingUsers -> DeciderKey.EnableDesTweetLikingUsersSafetyLevel,
    DesUserBookmarks -> DeciderKey.EnableDesUserBookmarksSafetyLevel,
    DesUserLikedTweets -> DeciderKey.EnableDesUserLikedTweetsSafetyLevel,
    DesUserMentions -> DeciderKey.EnableDesUserMentionsSafetyLevel,
    DesUserTweets -> DeciderKey.EnableDesUserTweetsSafetyLevel,
    DevPlatformComplianceStream -> DeciderKey.EnableDevPlatformComplianceStreamSafetyLevel,
    DirectMessages -> DeciderKey.EnableDirectMessagesSafetyLevel,
    DirectMessagesConversationList -> DeciderKey.EnableDirectMessagesConversationListSafetyLevel,
    DirectMessagesConversationTimeline -> DeciderKey.EnableDirectMessagesConversationTimelineSafetyLevel,
    DirectMessagesInbox -> DeciderKey.EnableDirectMessagesInboxSafetyLevel,
    DirectMessagesMutedUsers -> DeciderKey.EnableDirectMessagesMutedUsersSafetyLevel,
    DirectMessagesPinned -> DeciderKey.EnableDirectMessagesPinnedSafetyLevel,
    DirectMessagesSearch -> DeciderKey.EnableDirectMessagesSearchSafetyLevel,
    EditHistoryTimeline -> DeciderKey.EnableEditHistoryTimelineSafetyLevel,
    ElevatedQuoteTweetTimeline -> DeciderKey.EnableElevatedQuoteTweetTimelineSafetyLevel,
    EmbeddedTweet -> DeciderKey.EnableEmbeddedTweetSafetyLevel,
    EmbedsPublicInterestNotice -> DeciderKey.EnableEmbedsPublicInterestNoticeSafetyLevel,
    EmbedTweetMarkup -> DeciderKey.EnableEmbedTweetMarkupSafetyLevel,
    FilterAll -> DeciderKey.EnableFilterAllSafetyLevel,
    FilterAllPlaceholder -> DeciderKey.EnableFilterAllPlaceholderSafetyLevel,
    FilterNone -> DeciderKey.EnableFilterNoneSafetyLevel,
    FilterDefault -> DeciderKey.EnableFilterDefaultSafetyLevel,
    FollowedTopicsTimeline -> DeciderKey.EnableFollowedTopicsTimelineSafetyLevel,
    FollowerConnections -> DeciderKey.EnableFollowerConnectionsSafetyLevel,
    FollowingAndFollowersUserList -> DeciderKey.EnableFollowingAndFollowersUserListSafetyLevel,
    ForDevelopmentOnly -> DeciderKey.EnableForDevelopmentOnlySafetyLevel,
    FriendsFollowingList -> DeciderKey.EnableFriendsFollowingListSafetyLevel,
    GraphqlDefault -> DeciderKey.EnableGraphqlDefaultSafetyLevel,
    GryphonDecksAndColumns -> DeciderKey.EnableGryphonDecksAndColumnsSafetyLevel,
    HumanizationNudge -> DeciderKey.EnableHumanizationNudgeSafetyLevel,
    KitchenSinkDevelopment -> DeciderKey.EnableKitchenSinkDevelopmentSafetyLevel,
    ListHeader -> DeciderKey.EnableListHeaderSafetyLevel,
    ListMemberships -> DeciderKey.EnableListMembershipsSafetyLevel,
    ListOwnerships -> DeciderKey.EnableListOwnershipsSafetyLevel,
    ListRecommendations -> DeciderKey.EnableListRecommendationsSafetyLevel,
    ListSearch -> DeciderKey.EnableListSearchSafetyLevel,
    ListSubscriptions -> DeciderKey.EnableListSubscriptionsSafetyLevel,
    LiveVideoTimeline -> DeciderKey.EnableLiveVideoTimelineSafetyLevel,
    LivePipelineEngagementCounts -> DeciderKey.EnableLivePipelineEngagementCountsSafetyLevel,
    MagicRecs -> DeciderKey.EnableMagicRecsSafetyLevel,
    MagicRecsAggressive -> DeciderKey.EnableMagicRecsAggressiveSafetyLevel,
    MagicRecsAggressiveV2 -> DeciderKey.EnableMagicRecsAggressiveV2SafetyLevel,
    MagicRecsV2 -> DeciderKey.EnableMagicRecsV2SafetyLevel,
    Minimal -> DeciderKey.EnableMinimalSafetyLevel,
    ModeratedTweetsTimeline -> DeciderKey.EnableModeratedTweetsTimelineSafetyLevel,
    Moments -> DeciderKey.EnableMomentsSafetyLevel,
    NearbyTimeline -> DeciderKey.EnableNearbyTimelineSafetyLevel,
    NewUserExperience -> DeciderKey.EnableNewUserExperienceSafetyLevel,
    NotificationsIbis -> DeciderKey.EnableNotificationsIbisSafetyLevel,
    NotificationsPlatform -> DeciderKey.EnableNotificationsPlatformSafetyLevel,
    NotificationsPlatformPush -> DeciderKey.EnableNotificationsPlatformPushSafetyLevel,
    NotificationsQig -> DeciderKey.EnableNotificationsQig,
    NotificationsRead -> DeciderKey.EnableNotificationsReadSafetyLevel,
    NotificationsTimelineDeviceFollow -> DeciderKey.EnableNotificationsTimelineDeviceFollowSafetyLevel,
    NotificationsWrite -> DeciderKey.EnableNotificationsWriteSafetyLevel,
    NotificationsWriterV2 -> DeciderKey.EnableNotificationsWriterV2SafetyLevel,
    NotificationsWriterTweetHydrator -> DeciderKey.EnableNotificationsWriterTweetHydratorSafetyLevel,
    ProfileMixerMedia -> DeciderKey.EnableProfileMixeMediaSafetyLevel,
    ProfileMixerFavorites -> DeciderKey.EnableProfileMixerFavoritesSafetyLevel,
    QuickPromoteTweetEligibility -> DeciderKey.EnableQuickPromoteTweetEligibilitySafetyLevel,
    QuoteTweetTimeline -> DeciderKey.EnableQuoteTweetTimelineSafetyLevel,
    QuotedTweetRules -> DeciderKey.EnableQuotedTweetRulesSafetyLevel,
    Recommendations -> DeciderKey.EnableRecommendationsSafetyLevel,
    RecosVideo -> DeciderKey.EnableRecosVideoSafetyLevel,
    RecosWritePath -> DeciderKey.EnableRecosWritePathSafetyLevel,
    RepliesGrouping -> DeciderKey.EnableRepliesGroupingSafetyLevel,
    ReportCenter -> DeciderKey.EnableReportCenterSafetyLevel,
    ReturningUserExperience -> DeciderKey.EnableReturningUserExperienceSafetyLevel,
    ReturningUserExperienceFocalTweet -> DeciderKey.EnableReturningUserExperienceFocalTweetSafetyLevel,
    Revenue -> DeciderKey.EnableRevenueSafetyLevel,
    RitoActionedTweetTimeline -> DeciderKey.EnableRitoActionedTweetTimelineSafetyLevel,
    SafeSearchMinimal -> DeciderKey.EnableSafeSearchMinimalSafetyLevel,
    SafeSearchStrict -> DeciderKey.EnableSafeSearchStrictSafetyLevel,
    SearchMixerSrpMinimal -> DeciderKey.EnableSearchMixerSrpMinimalSafetyLevel,
    SearchMixerSrpStrict -> DeciderKey.EnableSearchMixerSrpStrictSafetyLevel,
    SearchHydration -> DeciderKey.EnableSearchHydration,
    SearchLatest -> DeciderKey.EnableSearchLatest,
    SearchPeopleSrp -> DeciderKey.EnableSearchPeopleSrp,
    SearchPeopleTypeahead -> DeciderKey.EnableSearchPeopleTypeahead,
    SearchPhoto -> DeciderKey.EnableSearchPhoto,
    SearchTrendTakeoverPromotedTweet -> DeciderKey.EnableSearchTrendTakeoverPromotedTweet,
    SearchTop -> DeciderKey.EnableSearchTop,
    SearchTopQig -> DeciderKey.EnableSearchTopQig,
    SearchVideo -> DeciderKey.EnableSearchVideo,
    SearchBlenderUserRules -> DeciderKey.EnableSearchLatestUserRules,
    SearchLatestUserRules -> DeciderKey.EnableSearchLatestUserRules,
    ShoppingManagerSpyMode -> DeciderKey.EnableShoppingManagerSpyModeSafetyLevel,
    SignalsReactions -> DeciderKey.EnableSignalsReactions,
    SignalsTweetReactingUsers -> DeciderKey.EnableSignalsTweetReactingUsers,
    SocialProof -> DeciderKey.EnableSocialProof,
    SoftInterventionPivot -> DeciderKey.EnableSoftInterventionPivot,
    SpaceFleetline -> DeciderKey.EnableSpaceFleetlineSafetyLevel,
    SpaceHomeTimelineUpranking -> DeciderKey.EnableSpaceHomeTimelineUprankingSafetyLevel,
    SpaceJoinScreen -> DeciderKey.EnableSpaceJoinScreenSafetyLevel,
    SpaceNotifications -> DeciderKey.EnableSpaceNotificationsSafetyLevel,
    Spaces -> DeciderKey.EnableSpacesSafetyLevel,
    SpacesParticipants -> DeciderKey.EnableSpacesParticipantsSafetyLevel,
    SpacesSellerApplicationStatus -> DeciderKey.EnableSpacesSellerApplicationStatus,
    SpacesSharing -> DeciderKey.EnableSpacesSharingSafetyLevel,
    SpaceTweetAvatarHomeTimeline -> DeciderKey.EnableSpaceTweetAvatarHomeTimelineSafetyLevel,
    StickersTimeline -> DeciderKey.EnableStickersTimelineSafetyLevel,
    StratoExtLimitedEngagements -> DeciderKey.EnableStratoExtLimitedEngagementsSafetyLevel,
    StreamServices -> DeciderKey.EnableStreamServicesSafetyLevel,
    SuperFollowerConnections -> DeciderKey.EnableSuperFollowerConnectionsSafetyLevel,
    SuperLike -> DeciderKey.EnableSuperLikeSafetyLevel,
    Test -> DeciderKey.EnableTestSafetyLevel,
    TimelineContentControls -> DeciderKey.EnableTimelineContentControlsSafetyLevel,
    TimelineConversations -> DeciderKey.EnableTimelineConversationsSafetyLevel,
    TimelineConversationsDownranking -> DeciderKey.EnableTimelineConversationsDownrankingSafetyLevel,
    TimelineConversationsDownrankingMinimal -> DeciderKey.EnableTimelineConversationsDownrankingMinimalSafetyLevel,
    TimelineFollowingActivity -> DeciderKey.EnableTimelineFollowingActivitySafetyLevel,
    TimelineHome -> DeciderKey.EnableTimelineHomeSafetyLevel,
    TimelineHomeCommunities -> DeciderKey.EnableTimelineHomeCommunitiesSafetyLevel,
    TimelineHomeHydration -> DeciderKey.EnableTimelineHomeHydrationSafetyLevel,
    TimelineHomePromotedHydration -> DeciderKey.EnableTimelineHomePromotedHydrationSafetyLevel,
    TimelineHomeRecommendations -> DeciderKey.EnableTimelineHomeRecommendationsSafetyLevel,
    TimelineHomeTopicFollowRecommendations -> DeciderKey.EnableTimelineHomeTopicFollowRecommendationsSafetyLevel,
    TimelineScorer -> DeciderKey.EnableTimelineScorerSafetyLevel,
    TopicsLandingPageTopicRecommendations -> DeciderKey.EnableTopicsLandingPageTopicRecommendationsSafetyLevel,
    ExploreRecommendations -> DeciderKey.EnableExploreRecommendationsSafetyLevel,
    TimelineInjection -> DeciderKey.EnableTimelineInjectionSafetyLevel,
    TimelineMentions -> DeciderKey.EnableTimelineMentionsSafetyLevel,
    TimelineModeratedTweetsHydration -> DeciderKey.EnableTimelineModeratedTweetsHydrationSafetyLevel,
    TimelineHomeLatest -> DeciderKey.EnableTimelineHomeLatestSafetyLevel,
    TimelineLikedBy -> DeciderKey.EnableTimelineLikedBySafetyLevel,
    TimelineRetweetedBy -> DeciderKey.EnableTimelineRetweetedBySafetyLevel,
    TimelineSuperLikedBy -> DeciderKey.EnableTimelineSuperLikedBySafetyLevel,
    TimelineBookmark -> DeciderKey.EnableTimelineBookmarkSafetyLevel,
    TimelineMedia -> DeciderKey.EnableTimelineMediaSafetyLevel,
    TimelineReactiveBlending -> DeciderKey.EnableTimelineReactiveBlendingSafetyLevel,
    TimelineFavorites -> DeciderKey.EnableTimelineFavoritesSafetyLevel,
    TimelineFavoritesSelfView -> DeciderKey.EnableSelfViewTimelineFavoritesSafetyLevel,
    TimelineLists -> DeciderKey.EnableTimelineListsSafetyLevel,
    TimelineProfile -> DeciderKey.EnableTimelineProfileSafetyLevel,
    TimelineProfileAll -> DeciderKey.EnableTimelineProfileAllSafetyLevel,
    TimelineProfileSpaces -> DeciderKey.EnableTimelineProfileSpacesSafetyLevel,
    TimelineProfileSuperFollows -> DeciderKey.EnableTimelineProfileSuperFollowsSafetyLevel,
    TimelineFocalTweet -> DeciderKey.EnableTweetTimelineFocalTweetSafetyLevel,
    TweetDetailWithInjectionsHydration -> DeciderKey.EnableTweetDetailWithInjectionsHydrationSafetyLevel,
    Tombstoning -> DeciderKey.EnableTombstoningSafetyLevel,
    TopicRecommendations -> DeciderKey.EnableTopicRecommendationsSafetyLevel,
    TrendsRepresentativeTweet -> DeciderKey.EnableTrendsRepresentativeTweetSafetyLevel,
    TrustedFriendsUserList -> DeciderKey.EnableTrustedFriendsUserListSafetyLevel,
    TwitterDelegateUserList -> DeciderKey.EnableTwitterDelegateUserListSafetyLevel,
    TweetDetail -> DeciderKey.EnableTweetDetailSafetyLevel,
    TweetDetailNonToo -> DeciderKey.EnableTweetDetailNonTooSafetyLevel,
    TweetEngagers -> DeciderKey.EnableTweetEngagersSafetyLevel,
    TweetReplyNudge -> DeciderKey.EnableTweetReplyNudgeSafetyLevel,
    TweetScopedTimeline -> DeciderKey.EnableTweetScopedTimelineSafetyLevel,
    TweetWritesApi -> DeciderKey.EnableTweetWritesApiSafetyLevel,
    TwitterArticleCompose -> DeciderKey.EnableTwitterArticleComposeSafetyLevel,
    TwitterArticleProfileTab -> DeciderKey.EnableTwitterArticleProfileTabSafetyLevel,
    TwitterArticleRead -> DeciderKey.EnableTwitterArticleReadSafetyLevel,
    UserProfileHeader -> DeciderKey.EnableUserProfileHeaderSafetyLevel,
    UserMilestoneRecommendation -> DeciderKey.EnableUserMilestoneRecommendationSafetyLevel,
    UserScopedTimeline -> DeciderKey.EnableUserScopedTimelineSafetyLevel,
    UserSearchSrp -> DeciderKey.EnableUserSearchSrpSafetyLevel,
    UserSearchTypeahead -> DeciderKey.EnableUserSearchTypeaheadSafetyLevel,
    UserSelfViewOnly -> DeciderKey.EnableUserSelfViewOnlySafetyLevel,
    UserSettings -> DeciderKey.EnableUserSettingsSafetyLevel,
    VideoAds -> DeciderKey.EnableVideoAdsSafetyLevel,
    WritePathLimitedActionsEnforcement -> DeciderKey.EnableWritePathLimitedActionsEnforcementSafetyLevel,
    ZipbirdConsumerArchives -> DeciderKey.EnableZipbirdConsumerArchivesSafetyLevel,
    TweetAward -> DeciderKey.EnableTweetAwardSafetyLevel,
  )

  val BoolToDeciderMap: Map[Param[Boolean], DeciderKey.Value] = Map(
    RuleParams.TweetConversationControlEnabledParam ->
      DeciderKey.EnableTweetConversationControlRules,
    RuleParams.CommunityTweetsEnabledParam ->
      DeciderKey.EnableCommunityTweetsControlRules,
    RuleParams.DropCommunityTweetWithUndefinedCommunityRuleEnabledParam ->
      DeciderKey.EnableDropCommunityTweetWithUndefinedCommunityRule,
    TimelineConversationsDownrankingSpecificParams.EnablePSpammyTweetDownrankConvosLowQualityParam ->
      DeciderKey.EnablePSpammyTweetDownrankConvosLowQuality,
    RuleParams.EnableHighPSpammyTweetScoreSearchTweetLabelDropRuleParam ->
      DeciderKey.EnableHighPSpammyTweetScoreSearchTweetLabelDropRule,
    TimelineConversationsDownrankingSpecificParams.EnableRitoActionedTweetDownrankConvosLowQualityParam ->
      DeciderKey.EnableRitoActionedTweetDownrankConvosLowQuality,
    RuleParams.EnableSmyteSpamTweetRuleParam ->
      DeciderKey.EnableSmyteSpamTweetRule,
    RuleParams.EnableHighSpammyTweetContentScoreSearchLatestTweetLabelDropRuleParam ->
      DeciderKey.EnableHighSpammyTweetContentScoreSearchLatestTweetLabelDropRule,
    RuleParams.EnableHighSpammyTweetContentScoreSearchTopTweetLabelDropRuleParam ->
      DeciderKey.EnableHighSpammyTweetContentScoreSearchTopTweetLabelDropRule,
    RuleParams.EnableHighSpammyTweetContentScoreTrendsTopTweetLabelDropRuleParam ->
      DeciderKey.EnableHighSpammyTweetContentScoreTrendsTopTweetLabelDropRule,
    RuleParams.EnableHighSpammyTweetContentScoreTrendsLatestTweetLabelDropRuleParam ->
      DeciderKey.EnableHighSpammyTweetContentScoreTrendsLatestTweetLabelDropRule,
    TimelineConversationsDownrankingSpecificParams.EnableHighSpammyTweetContentScoreConvoDownrankAbusiveQualityRuleParam ->
      DeciderKey.EnableHighSpammyTweetContentScoreConvoDownrankAbusiveQualityRule,
    TimelineConversationsDownrankingSpecificParams.EnableHighCryptospamScoreConvoDownrankAbusiveQualityRuleParam ->
      DeciderKey.EnableHighCryptospamScoreConvoDownrankAbusiveQualityRule,
    RuleParams.EnableGoreAndViolenceTopicHighRecallTweetLabelRule ->
      DeciderKey.EnableGoreAndViolenceTopicHighRecallTweetLabelRule,
    RuleParams.EnableLimitRepliesFollowersConversationRule ->
      DeciderKey.EnableLimitRepliesFollowersConversationRule,
    RuleParams.EnableSearchBasicBlockMuteRulesParam -> DeciderKey.EnableSearchBasicBlockMuteRules,
    RuleParams.EnableBlinkBadDownrankingRuleParam ->
      DeciderKey.EnableBlinkBadDownrankingRule,
    RuleParams.EnableBlinkWorstDownrankingRuleParam ->
      DeciderKey.EnableBlinkWorstDownrankingRule,
    RuleParams.EnableCopypastaSpamDownrankConvosAbusiveQualityRule ->
      DeciderKey.EnableCopypastaSpamDownrankConvosAbusiveQualityRule,
    RuleParams.EnableCopypastaSpamSearchDropRule ->
      DeciderKey.EnableCopypastaSpamSearchDropRule,
    RuleParams.EnableSpammyUserModelTweetDropRuleParam ->
      DeciderKey.EnableSpammyUserModelHighPrecisionDropTweetRule,
    RuleParams.EnableAvoidNsfwRulesParam ->
      DeciderKey.EnableAvoidNsfwRules,
    RuleParams.EnableReportedTweetInterstitialRule ->
      DeciderKey.EnableReportedTweetInterstitialRule,
    RuleParams.EnableReportedTweetInterstitialSearchRule ->
      DeciderKey.EnableReportedTweetInterstitialSearchRule,
    RuleParams.EnableDropExclusiveTweetContentRule ->
      DeciderKey.EnableDropExclusiveTweetContentRule,
    RuleParams.EnableDropExclusiveTweetContentRuleFailClosed ->
      DeciderKey.EnableDropExclusiveTweetContentRuleFailClosed,
    RuleParams.EnableTombstoneExclusiveQtProfileTimelineParam ->
      DeciderKey.EnableTombstoneExclusiveQtProfileTimelineParam,
    RuleParams.EnableDropAllExclusiveTweetsRuleParam -> DeciderKey.EnableDropAllExclusiveTweetsRule,
    RuleParams.EnableDropAllExclusiveTweetsRuleFailClosedParam -> DeciderKey.EnableDropAllExclusiveTweetsRuleFailClosed,
    RuleParams.EnableDownrankSpamReplySectioningRuleParam ->
      DeciderKey.EnableDownrankSpamReplySectioningRule,
    RuleParams.EnableNsfwTextSectioningRuleParam ->
      DeciderKey.EnableNsfwTextSectioningRule,
    RuleParams.EnableSearchIpiSafeSearchWithoutUserInQueryDropRule -> DeciderKey.EnableSearchIpiSafeSearchWithoutUserInQueryDropRule,
    RuleParams.EnableTimelineHomePromotedTweetHealthEnforcementRules -> DeciderKey.EnableTimelineHomePromotedTweetHealthEnforcementRules,
    RuleParams.EnableMutedKeywordFilteringSpaceTitleNotificationsRuleParam -> DeciderKey.EnableMutedKeywordFilteringSpaceTitleNotificationsRule,
    RuleParams.EnableDropTweetsWithGeoRestrictedMediaRuleParam -> DeciderKey.EnableDropTweetsWithGeoRestrictedMediaRule,
    RuleParams.EnableDropAllTrustedFriendsTweetsRuleParam -> DeciderKey.EnableDropAllTrustedFriendsTweetsRule,
    RuleParams.EnableDropTrustedFriendsTweetContentRuleParam -> DeciderKey.EnableDropTrustedFriendsTweetContentRule,
    RuleParams.EnableDropAllCollabInvitationTweetsRuleParam -> DeciderKey.EnableDropCollabInvitationTweetsRule,
    RuleParams.EnableNsfwTextHighPrecisionDropRuleParam -> DeciderKey.EnableNsfwTextHighPrecisionDropRule,
    RuleParams.EnableLikelyIvsUserLabelDropRule -> DeciderKey.EnableLikelyIvsUserLabelDropRule,
    RuleParams.EnableCardUriRootDomainCardDenylistRule -> DeciderKey.EnableCardUriRootDomainDenylistRule,
    RuleParams.EnableCommunityNonMemberPollCardRule -> DeciderKey.EnableCommunityNonMemberPollCardRule,
    RuleParams.EnableCommunityNonMemberPollCardRuleFailClosed -> DeciderKey.EnableCommunityNonMemberPollCardRuleFailClosed,
    RuleParams.EnableExperimentalNudgeEnabledParam -> DeciderKey.EnableExperimentalNudgeLabelRule,
    RuleParams.NsfwHighPrecisionUserLabelAvoidTweetRuleEnabledParam -> DeciderKey.NsfwHighPrecisionUserLabelAvoidTweetRuleEnabledParam,
    RuleParams.EnableNewAdAvoidanceRulesParam -> DeciderKey.EnableNewAdAvoidanceRules,
    RuleParams.EnableNsfaHighRecallAdAvoidanceParam -> DeciderKey.EnableNsfaHighRecallAdAvoidanceParam,
    RuleParams.EnableNsfaKeywordsHighPrecisionAdAvoidanceParam -> DeciderKey.EnableNsfaKeywordsHighPrecisionAdAvoidanceParam,
    RuleParams.EnableStaleTweetDropRuleParam -> DeciderKey.EnableStaleTweetDropRuleParam,
    RuleParams.EnableStaleTweetDropRuleFailClosedParam -> DeciderKey.EnableStaleTweetDropRuleFailClosedParam,
    RuleParams.EnableDeleteStateTweetRulesParam -> DeciderKey.EnableDeleteStateTweetRules,
    RuleParams.EnableSpacesSharingNsfwDropRulesParam -> DeciderKey.EnableSpacesSharingNsfwDropRulesParam,
    RuleParams.EnableViewerIsSoftUserDropRuleParam -> DeciderKey.EnableViewerIsSoftUserDropRuleParam,
    RuleParams.EnablePdnaQuotedTweetTombstoneRuleParam -> DeciderKey.EnablePdnaQuotedTweetTombstoneRule,
    RuleParams.EnableSpamQuotedTweetTombstoneRuleParam -> DeciderKey.EnableSpamQuotedTweetTombstoneRule,
    RuleParams.EnableNsfwHpQuotedTweetDropRuleParam -> DeciderKey.EnableNsfwHpQuotedTweetDropRule,
    RuleParams.EnableNsfwHpQuotedTweetTombstoneRuleParam -> DeciderKey.EnableNsfwHpQuotedTweetTombstoneRule,
    RuleParams.EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam -> DeciderKey.EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRule,
    RuleParams.EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam -> DeciderKey.EnableInnerQuotedTweetViewerMutesAuthorInterstitialRule,
    RuleParams.EnableToxicReplyFilteringConversationRulesParam -> DeciderKey.VisibilityLibraryEnableToxicReplyFilterConversation,
    RuleParams.EnableToxicReplyFilteringNotificationsRulesParam -> DeciderKey.VisibilityLibraryEnableToxicReplyFilterNotifications,
    RuleParams.EnableLegacySensitiveMediaHomeTimelineRulesParam -> DeciderKey.EnableLegacySensitiveMediaRulesHomeTimeline,
    RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam -> DeciderKey.EnableNewSensitiveMediaSettingsInterstitialRulesHomeTimeline,
    RuleParams.EnableLegacySensitiveMediaConversationRulesParam -> DeciderKey.EnableLegacySensitiveMediaRulesConversation,
    RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam -> DeciderKey.EnableNewSensitiveMediaSettingsInterstitialRulesConversation,
    RuleParams.EnableLegacySensitiveMediaProfileTimelineRulesParam -> DeciderKey.EnableLegacySensitiveMediaRulesProfileTimeline,
    RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam -> DeciderKey.EnableNewSensitiveMediaSettingsInterstitialRulesProfileTimeline,
    RuleParams.EnableLegacySensitiveMediaTweetDetailRulesParam -> DeciderKey.EnableLegacySensitiveMediaRulesTweetDetail,
    RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam -> DeciderKey.EnableNewSensitiveMediaSettingsInterstitialRulesTweetDetail,
    RuleParams.EnableLegacySensitiveMediaDirectMessagesRulesParam -> DeciderKey.EnableLegacySensitiveMediaRulesDirectMessages,
    RuleParams.EnableAbusiveBehaviorDropRuleParam -> DeciderKey.EnableAbusiveBehaviorDropRule,
    RuleParams.EnableAbusiveBehaviorInterstitialRuleParam -> DeciderKey.EnableAbusiveBehaviorInterstitialRule,
    RuleParams.EnableAbusiveBehaviorLimitedEngagementsRuleParam -> DeciderKey.EnableAbusiveBehaviorLimitedEngagementsRule,
    RuleParams.EnableNotGraduatedDownrankConvosAbusiveQualityRuleParam -> DeciderKey.EnableNotGraduatedDownrankConvosAbusiveQualityRule,
    RuleParams.EnableNotGraduatedSearchDropRuleParam -> DeciderKey.EnableNotGraduatedSearchDropRule,
    RuleParams.EnableNotGraduatedDropRuleParam -> DeciderKey.EnableNotGraduatedDropRule,
    RuleParams.EnableFosnrRuleParam -> DeciderKey.EnableFosnrRules,
    RuleParams.EnableAuthorBlocksViewerDropRuleParam -> DeciderKey.EnableAuthorBlocksViewerDropRule
  )

  def config(
    deciderGateBuilder: DeciderGateBuilder,
    logger: Logger,
    statsReceiver: StatsReceiver,
    SafetyLevel: SafetyLevel
  ): Config = {

    object UserOrGuestOrRequest extends RecipientBuilder {
      private val scopedStats = statsReceiver.scope("decider_recipient")
      private val userIdDefinedCounter = scopedStats.counter("user_id_defined")
      private val userIdNotDefinedCounter = scopedStats.counter("user_id_undefined")
      private val guestIdDefinedCounter = scopedStats.counter("guest_id_defined")
      private val guestIdNotDefinedCounter = scopedStats.counter("guest_id_undefined")
      private val noIdCounter = scopedStats.counter("no_id_defined")

      def apply(requestContext: BaseRequestContext): Option[Recipient] = requestContext match {
        case c: WithUserId if c.userId.isDefined =>
          userIdDefinedCounter.incr()
          c.userId.map(SimpleRecipient)
        case c: WithGuestId if c.guestId.isDefined =>
          guestIdDefinedCounter.incr()
          c.guestId.map(GuestRecipient)
        case c: WithGuestId =>
          guestIdNotDefinedCounter.incr()
          RecipientBuilder.Request(c)
        case _: WithUserId =>
          userIdNotDefinedCounter.incr()
          None
        case _ =>
          logger.warning("Request Context with no user or guest id trait found: " + requestContext)
          noIdCounter.incr()
          None
      }
    }

    val boolOverrides = BoolToDeciderMap.map {
      case (param, deciderKey) =>
        param.optionalOverrideValue(
          DeciderSwitchOverrideValue(
            feature = deciderGateBuilder.keyToFeature(deciderKey),
            enabledValue = true,
            disabledValueOption = Some(false),
            recipientBuilder = UserOrGuestOrRequest
          )
        )
    }.toSeq

    val safetyLevelOverride = SafetyLevel.enabledParam.optionalOverrideValue(
      DeciderSwitchOverrideValue(
        feature = deciderGateBuilder.keyToFeature(SafetyLevelToDeciderMap(SafetyLevel)),
        enabledValue = true,
        recipientBuilder = UserOrGuestOrRequest
      )
    )

    BaseConfigBuilder(boolOverrides :+ safetyLevelOverride).build("VisibilityDeciders")
  }
}
