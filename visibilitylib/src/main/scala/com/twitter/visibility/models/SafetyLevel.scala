package com.twitter.visibility.models

import com.twitter.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}
import com.twitter.visibility.configapi.params.SafetyLevelParam
import com.twitter.visibility.configapi.params.SafetyLevelParams._

sealed trait SafetyLevel {
  val name: String = this.getClass.getSimpleName.dropRight(1)
  def enabledParam: SafetyLevelParam
}

object SafetyLevel {
  private lazy val nameToSafetyLevelMap: Map[String, SafetyLevel] =
    SafetyLevel.List.map(s => s.name.toLowerCase -> s).toMap
  def fromName(name: String): Option[SafetyLevel] = {
    nameToSafetyLevelMap.get(name.toLowerCase)
  }

  private val DeprecatedEnumValue = -1

  private lazy val thriftToModelMap: Map[ThriftSafetyLevel, SafetyLevel] = Map(
    ThriftSafetyLevel.AccessInternalPromotedContent -> AccessInternalPromotedContent,
    ThriftSafetyLevel.AdsBusinessSettings -> AdsBusinessSettings,
    ThriftSafetyLevel.AdsCampaign -> AdsCampaign,
    ThriftSafetyLevel.AdsManager -> AdsManager,
    ThriftSafetyLevel.AdsReportingDashboard -> AdsReportingDashboard,
    ThriftSafetyLevel.AllSubscribedLists -> AllSubscribedLists,
    ThriftSafetyLevel.Appeals -> Appeals,
    ThriftSafetyLevel.ArticleTweetTimeline -> ArticleTweetTimeline,
    ThriftSafetyLevel.BaseQig -> BaseQig,
    ThriftSafetyLevel.BirdwatchNoteAuthor -> BirdwatchNoteAuthor,
    ThriftSafetyLevel.BirdwatchNoteTweetsTimeline -> BirdwatchNoteTweetsTimeline,
    ThriftSafetyLevel.BirdwatchNeedsYourHelpNotifications -> BirdwatchNeedsYourHelpNotifications,
    ThriftSafetyLevel.BlockMuteUsersTimeline -> BlockMuteUsersTimeline,
    ThriftSafetyLevel.BrandSafety -> BrandSafety,
    ThriftSafetyLevel.CardPollVoting -> CardPollVoting,
    ThriftSafetyLevel.CardsService -> CardsService,
    ThriftSafetyLevel.Communities -> Communities,
    ThriftSafetyLevel.ContentControlToolInstall -> ContentControlToolInstall,
    ThriftSafetyLevel.ConversationFocalPrehydration -> ConversationFocalPrehydration,
    ThriftSafetyLevel.ConversationFocalTweet -> ConversationFocalTweet,
    ThriftSafetyLevel.ConversationInjectedTweet -> ConversationInjectedTweet,
    ThriftSafetyLevel.ConversationReply -> ConversationReply,
    ThriftSafetyLevel.CuratedTrendsRepresentativeTweet -> CuratedTrendsRepresentativeTweet,
    ThriftSafetyLevel.CurationPolicyViolations -> CurationPolicyViolations,
    ThriftSafetyLevel.DevPlatformGetListTweets -> DevPlatformGetListTweets,
    ThriftSafetyLevel.DesFollowingAndFollowersUserList -> DesFollowingAndFollowersUserList,
    ThriftSafetyLevel.DesHomeTimeline -> DesHomeTimeline,
    ThriftSafetyLevel.DesQuoteTweetTimeline -> DesQuoteTweetTimeline,
    ThriftSafetyLevel.DesRealtime -> DesRealtime,
    ThriftSafetyLevel.DesRealtimeSpamEnrichment -> DesRealtimeSpamEnrichment,
    ThriftSafetyLevel.DesRealtimeTweetFilter -> DesRealtimeTweetFilter,
    ThriftSafetyLevel.DesRetweetingUsers -> DesRetweetingUsers,
    ThriftSafetyLevel.DesTweetDetail -> DesTweetDetail,
    ThriftSafetyLevel.DesTweetLikingUsers -> DesTweetLikingUsers,
    ThriftSafetyLevel.DesUserBookmarks -> DesUserBookmarks,
    ThriftSafetyLevel.DesUserLikedTweets -> DesUserLikedTweets,
    ThriftSafetyLevel.DesUserMentions -> DesUserMentions,
    ThriftSafetyLevel.DesUserTweets -> DesUserTweets,
    ThriftSafetyLevel.DevPlatformComplianceStream -> DevPlatformComplianceStream,
    ThriftSafetyLevel.DirectMessages -> DirectMessages,
    ThriftSafetyLevel.DirectMessagesConversationList -> DirectMessagesConversationList,
    ThriftSafetyLevel.DirectMessagesConversationTimeline -> DirectMessagesConversationTimeline,
    ThriftSafetyLevel.DirectMessagesInbotwitter -> DirectMessagesInbotwitter,
    ThriftSafetyLevel.DirectMessagesMutedUsers -> DirectMessagesMutedUsers,
    ThriftSafetyLevel.DirectMessagesPinned -> DirectMessagesPinned,
    ThriftSafetyLevel.DirectMessagesSearch -> DirectMessagesSearch,
    ThriftSafetyLevel.EditHistoryTimeline -> EditHistoryTimeline,
    ThriftSafetyLevel.ElevatedQuoteTweetTimeline -> ElevatedQuoteTweetTimeline,
    ThriftSafetyLevel.EmbeddedTweet -> EmbeddedTweet,
    ThriftSafetyLevel.EmbedsPublicInterestNotice -> EmbedsPublicInterestNotice,
    ThriftSafetyLevel.EmbedTweetMarkup -> EmbedTweetMarkup,
    ThriftSafetyLevel.EtwitterploreRecommendations -> EtwitterploreRecommendations,
    ThriftSafetyLevel.WritePathLimitedActionsEnforcement -> WritePathLimitedActionsEnforcement,
    ThriftSafetyLevel.FilterAll -> FilterAll,
    ThriftSafetyLevel.FilterAllPlaceholder -> FilterAllPlaceholder,
    ThriftSafetyLevel.FilterDefault -> FilterDefault,
    ThriftSafetyLevel.FilterNone -> FilterNone,
    ThriftSafetyLevel.FollowedTopicsTimeline -> FollowedTopicsTimeline,
    ThriftSafetyLevel.FollowerConnections -> FollowerConnections,
    ThriftSafetyLevel.FollowingAndFollowersUserList -> FollowingAndFollowersUserList,
    ThriftSafetyLevel.ForDevelopmentOnly -> ForDevelopmentOnly,
    ThriftSafetyLevel.FriendsFollowingList -> FriendsFollowingList,
    ThriftSafetyLevel.GraphqlDefault -> GraphqlDefault,
    ThriftSafetyLevel.HumanizationNudge -> HumanizationNudge,
    ThriftSafetyLevel.KitchenSinkDevelopment -> KitchenSinkDevelopment,
    ThriftSafetyLevel.ListHeader -> ListHeader,
    ThriftSafetyLevel.ListMemberships -> ListMemberships,
    ThriftSafetyLevel.ListOwnerships -> ListOwnerships,
    ThriftSafetyLevel.ListRecommendations -> ListRecommendations,
    ThriftSafetyLevel.ListSearch -> ListSearch,
    ThriftSafetyLevel.ListSubscriptions -> ListSubscriptions,
    ThriftSafetyLevel.LivePipelineEngagementCounts -> LivePipelineEngagementCounts,
    ThriftSafetyLevel.LiveVideoTimeline -> LiveVideoTimeline,
    ThriftSafetyLevel.MagicRecs -> MagicRecs,
    ThriftSafetyLevel.MagicRecsV2 -> MagicRecsV2,
    ThriftSafetyLevel.MagicRecsAggressive -> MagicRecsAggressive,
    ThriftSafetyLevel.MagicRecsAggressiveV2 -> MagicRecsAggressiveV2,
    ThriftSafetyLevel.Minimal -> Minimal,
    ThriftSafetyLevel.ModeratedTweetsTimeline -> ModeratedTweetsTimeline,
    ThriftSafetyLevel.Moments -> Moments,
    ThriftSafetyLevel.NearbyTimeline -> NearbyTimeline,
    ThriftSafetyLevel.NewUserEtwitterperience -> NewUserEtwitterperience,
    ThriftSafetyLevel.NotificationsIbis -> NotificationsIbis,
    ThriftSafetyLevel.NotificationsPlatform -> NotificationsPlatform,
    ThriftSafetyLevel.NotificationsPlatformPush -> NotificationsPlatformPush,
    ThriftSafetyLevel.NotificationsQig -> NotificationsQig,
    ThriftSafetyLevel.NotificationsRead -> NotificationsRead,
    ThriftSafetyLevel.NotificationsTimelineDeviceFollow -> NotificationsTimelineDeviceFollow,
    ThriftSafetyLevel.NotificationsWrite -> NotificationsWrite,
    ThriftSafetyLevel.NotificationsWriterTweetHydrator -> NotificationsWriterTweetHydrator,
    ThriftSafetyLevel.NotificationsWriterV2 -> NotificationsWriterV2,
    ThriftSafetyLevel.ProfileMitwittererMedia -> ProfileMitwittererMedia,
    ThriftSafetyLevel.ProfileMitwittererFavorites -> ProfileMitwittererFavorites,
    ThriftSafetyLevel.QuickPromoteTweetEligibility -> QuickPromoteTweetEligibility,
    ThriftSafetyLevel.QuoteTweetTimeline -> QuoteTweetTimeline,
    ThriftSafetyLevel.QuotedTweetRules -> QuotedTweetRules,
    ThriftSafetyLevel.Recommendations -> Recommendations,
    ThriftSafetyLevel.RecosVideo -> RecosVideo,
    ThriftSafetyLevel.RecosWritePath -> RecosWritePath,
    ThriftSafetyLevel.RepliesGrouping -> RepliesGrouping,
    ThriftSafetyLevel.ReportCenter -> ReportCenter,
    ThriftSafetyLevel.ReturningUserEtwitterperience -> ReturningUserEtwitterperience,
    ThriftSafetyLevel.ReturningUserEtwitterperienceFocalTweet -> ReturningUserEtwitterperienceFocalTweet,
    ThriftSafetyLevel.Revenue -> Revenue,
    ThriftSafetyLevel.RitoActionedTweetTimeline -> RitoActionedTweetTimeline,
    ThriftSafetyLevel.SafeSearchMinimal -> SafeSearchMinimal,
    ThriftSafetyLevel.SafeSearchStrict -> SafeSearchStrict,
    ThriftSafetyLevel.SearchHydration -> SearchHydration,
    ThriftSafetyLevel.SearchLatest -> SearchLatest,
    ThriftSafetyLevel.SearchTop -> SearchTop,
    ThriftSafetyLevel.SearchTopQig -> SearchTopQig,
    ThriftSafetyLevel.SearchMitwittererSrpMinimal -> SearchMitwittererSrpMinimal,
    ThriftSafetyLevel.SearchMitwittererSrpStrict -> SearchMitwittererSrpStrict,
    ThriftSafetyLevel.SearchPeopleSrp -> SearchPeopleSrp,
    ThriftSafetyLevel.SearchPeopleTypeahead -> SearchPeopleTypeahead,
    ThriftSafetyLevel.SearchPhoto -> SearchPhoto,
    ThriftSafetyLevel.SearchTrendTakeoverPromotedTweet -> SearchTrendTakeoverPromotedTweet,
    ThriftSafetyLevel.SearchVideo -> SearchVideo,
    ThriftSafetyLevel.SearchBlenderUserRules -> SearchBlenderUserRules,
    ThriftSafetyLevel.SearchLatestUserRules -> SearchLatestUserRules,
    ThriftSafetyLevel.ShoppingManagerSpyMode -> ShoppingManagerSpyMode,
    ThriftSafetyLevel.SignalsReactions -> SignalsReactions,
    ThriftSafetyLevel.SignalsTweetReactingUsers -> SignalsTweetReactingUsers,
    ThriftSafetyLevel.SocialProof -> SocialProof,
    ThriftSafetyLevel.SoftInterventionPivot -> SoftInterventionPivot,
    ThriftSafetyLevel.SpaceFleetline -> SpaceFleetline,
    ThriftSafetyLevel.SpaceHomeTimelineUpranking -> SpaceHomeTimelineUpranking,
    ThriftSafetyLevel.SpaceJoinScreen -> SpaceJoinScreen,
    ThriftSafetyLevel.SpaceNotifications -> SpaceNotifications,
    ThriftSafetyLevel.Spaces -> Spaces,
    ThriftSafetyLevel.SpacesParticipants -> SpacesParticipants,
    ThriftSafetyLevel.SpacesSellerApplicationStatus -> SpacesSellerApplicationStatus,
    ThriftSafetyLevel.SpacesSharing -> SpacesSharing,
    ThriftSafetyLevel.SpaceTweetAvatarHomeTimeline -> SpaceTweetAvatarHomeTimeline,
    ThriftSafetyLevel.StickersTimeline -> StickersTimeline,
    ThriftSafetyLevel.StratoEtwittertLimitedEngagements -> StratoEtwittertLimitedEngagements,
    ThriftSafetyLevel.StreamServices -> StreamServices,
    ThriftSafetyLevel.SuperFollowerConnections -> SuperFollowerConnections,
    ThriftSafetyLevel.SuperLike -> SuperLike,
    ThriftSafetyLevel.Test -> Test,
    ThriftSafetyLevel.TimelineBookmark -> TimelineBookmark,
    ThriftSafetyLevel.TimelineContentControls -> TimelineContentControls,
    ThriftSafetyLevel.TimelineConversations -> TimelineConversations,
    ThriftSafetyLevel.TimelineConversationsDownranking -> TimelineConversationsDownranking,
    ThriftSafetyLevel.TimelineConversationsDownrankingMinimal -> TimelineConversationsDownrankingMinimal,
    ThriftSafetyLevel.TimelineFavorites -> TimelineFavorites,
    ThriftSafetyLevel.TimelineFavoritesSelfView -> TimelineFavoritesSelfView,
    ThriftSafetyLevel.TimelineFocalTweet -> TimelineFocalTweet,
    ThriftSafetyLevel.TimelineFollowingActivity -> TimelineFollowingActivity,
    ThriftSafetyLevel.TimelineHome -> TimelineHome,
    ThriftSafetyLevel.TimelineHomeCommunities -> TimelineHomeCommunities,
    ThriftSafetyLevel.TimelineHomeHydration -> TimelineHomeHydration,
    ThriftSafetyLevel.TimelineHomeLatest -> TimelineHomeLatest,
    ThriftSafetyLevel.TimelineHomePromotedHydration -> TimelineHomePromotedHydration,
    ThriftSafetyLevel.TimelineHomeRecommendations -> TimelineHomeRecommendations,
    ThriftSafetyLevel.TimelineHomeTopicFollowRecommendations -> TimelineHomeTopicFollowRecommendations,
    ThriftSafetyLevel.TimelineScorer -> TimelineScorer,
    ThriftSafetyLevel.TimelineInjection -> TimelineInjection,
    ThriftSafetyLevel.TimelineLikedBy -> TimelineLikedBy,
    ThriftSafetyLevel.TimelineLists -> TimelineLists,
    ThriftSafetyLevel.TimelineMedia -> TimelineMedia,
    ThriftSafetyLevel.TimelineMentions -> TimelineMentions,
    ThriftSafetyLevel.TimelineModeratedTweetsHydration -> TimelineModeratedTweetsHydration,
    ThriftSafetyLevel.TimelineProfile -> TimelineProfile,
    ThriftSafetyLevel.TimelineProfileAll -> TimelineProfileAll,
    ThriftSafetyLevel.TimelineProfileSpaces -> TimelineProfileSpaces,
    ThriftSafetyLevel.TimelineProfileSuperFollows -> TimelineProfileSuperFollows,
    ThriftSafetyLevel.TimelineReactiveBlending -> TimelineReactiveBlending,
    ThriftSafetyLevel.TimelineRetweetedBy -> TimelineRetweetedBy,
    ThriftSafetyLevel.TimelineSuperLikedBy -> TimelineSuperLikedBy,
    ThriftSafetyLevel.Tombstoning -> Tombstoning,
    ThriftSafetyLevel.TopicRecommendations -> TopicRecommendations,
    ThriftSafetyLevel.TopicsLandingPageTopicRecommendations -> TopicsLandingPageTopicRecommendations,
    ThriftSafetyLevel.TrendsRepresentativeTweet -> TrendsRepresentativeTweet,
    ThriftSafetyLevel.TrustedFriendsUserList -> TrustedFriendsUserList,
    ThriftSafetyLevel.TwitterDelegateUserList -> TwitterDelegateUserList,
    ThriftSafetyLevel.GryphonDecksAndColumns -> GryphonDecksAndColumns,
    ThriftSafetyLevel.TweetDetail -> TweetDetail,
    ThriftSafetyLevel.TweetDetailNonToo -> TweetDetailNonToo,
    ThriftSafetyLevel.TweetDetailWithInjectionsHydration -> TweetDetailWithInjectionsHydration,
    ThriftSafetyLevel.TweetEngagers -> TweetEngagers,
    ThriftSafetyLevel.TweetReplyNudge -> TweetReplyNudge,
    ThriftSafetyLevel.TweetScopedTimeline -> TweetScopedTimeline,
    ThriftSafetyLevel.TweetWritesApi -> TweetWritesApi,
    ThriftSafetyLevel.TwitterArticleCompose -> TwitterArticleCompose,
    ThriftSafetyLevel.TwitterArticleProfileTab -> TwitterArticleProfileTab,
    ThriftSafetyLevel.TwitterArticleRead -> TwitterArticleRead,
    ThriftSafetyLevel.UserProfileHeader -> UserProfileHeader,
    ThriftSafetyLevel.UserMilestoneRecommendation -> UserMilestoneRecommendation,
    ThriftSafetyLevel.UserScopedTimeline -> UserScopedTimeline,
    ThriftSafetyLevel.UserSearchSrp -> UserSearchSrp,
    ThriftSafetyLevel.UserSearchTypeahead -> UserSearchTypeahead,
    ThriftSafetyLevel.UserSelfViewOnly -> UserSelfViewOnly,
    ThriftSafetyLevel.UserSettings -> UserSettings,
    ThriftSafetyLevel.VideoAds -> VideoAds,
    ThriftSafetyLevel.ZipbirdConsumerArchives -> ZipbirdConsumerArchives,
    ThriftSafetyLevel.TweetAward -> TweetAward,
  )

  private lazy val modelToThriftMap: Map[SafetyLevel, ThriftSafetyLevel] =
    for ((k, v) <- thriftToModelMap) yield (v, k)

  case object AdsBusinessSettings etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsBusinessSettingsSafetyLevelParam
  }
  case object AdsCampaign etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsCampaignSafetyLevelParam
  }
  case object AdsManager etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsManagerSafetyLevelParam
  }
  case object AdsReportingDashboard etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsReportingDashboardSafetyLevelParam
  }
  case object AllSubscribedLists etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAllSubscribedListsSafetyLevelParam
  }
  case object Appeals etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAppealsSafetyLevelParam
  }
  case object ArticleTweetTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableArticleTweetTimelineSafetyLevelParam
  }
  case object BaseQig etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBaseQigSafetyLevelParam
  }
  case object BirdwatchNoteAuthor etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBirdwatchNoteAuthorSafetyLevel
  }
  case object BirdwatchNoteTweetsTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBirdwatchNoteTweetsTimelineSafetyLevel
  }
  case object BirdwatchNeedsYourHelpNotifications etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableBirdwatchNeedsYourHelpNotificationsSafetyLevel
  }
  case object BlockMuteUsersTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBlockMuteUsersTimelineSafetyLevelParam
  }
  case object BrandSafety etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBrandSafetySafetyLevelParam
  }
  case object CardPollVoting etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCardPollVotingSafetyLevelParam
  }
  case object CardsService etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCardsServiceSafetyLevelParam
  }
  case object Communities etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCommunitiesSafetyLevelParam
  }
  case object ContentControlToolInstall etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableContentControlToolInstallSafetyLevelParam
  }
  case object ConversationFocalPrehydration etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableConversationFocalPrehydrationSafetyLevelParam
  }
  case object ConversationFocalTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableConversationFocalTweetSafetyLevelParam
  }
  case object ConversationInjectedTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableConversationInjectedTweetSafetyLevelParam
  }
  case object ConversationReply etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableConversationReplySafetyLevelParam
  }
  case object AccessInternalPromotedContent etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableAccessInternalPromotedContentSafetyLevelParam
  }
  case object CuratedTrendsRepresentativeTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCuratedTrendsRepresentativeTweet
  }
  case object CurationPolicyViolations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCurationPolicyViolations
  }
  case object DevPlatformGetListTweets etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDevPlatformGetListTweetsSafetyLevelParam
  }
  case object DesFollowingAndFollowersUserList etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDESFollowingAndFollowersUserListSafetyLevelParam
  }
  case object DesHomeTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESHomeTimelineSafetyLevelParam
  }
  case object DesQuoteTweetTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESQuoteTweetTimelineSafetyLevelParam
  }
  case object DesRealtime etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRealtimeSafetyLevelParam
  }
  case object DesRealtimeSpamEnrichment etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRealtimeSpamEnrichmentSafetyLevelParam
  }
  case object DesRealtimeTweetFilter etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRealtimeTweetFilterSafetyLevelParam
  }
  case object DesRetweetingUsers etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRetweetingUsersSafetyLevelParam
  }
  case object DesTweetDetail etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDesTweetDetailSafetyLevelParam
  }
  case object DesTweetLikingUsers etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESTweetLikingUsersSafetyLevelParam
  }
  case object DesUserBookmarks etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserBookmarksSafetyLevelParam
  }
  case object DesUserLikedTweets etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserLikedTweetSafetyLevelParam
  }
  case object DesUserMentions etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserMentionsSafetyLevelParam
  }
  case object DesUserTweets etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserTweetsSafetyLevelParam
  }
  case object DevPlatformComplianceStream etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDevPlatformComplianceStreamSafetyLevelParam
  }
  case object DirectMessages etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesSafetyLevelParam
  }
  case object DirectMessagesConversationList etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDirectMessagesConversationListSafetyLevelParam
  }
  case object DirectMessagesConversationTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDirectMessagesConversationTimelineSafetyLevelParam
  }
  case object DirectMessagesInbotwitter etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDirectMessagesInbotwitterSafetyLevelParam
  }
  case object DirectMessagesMutedUsers etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesMutedUsersSafetyLevelParam
  }
  case object DirectMessagesPinned etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesPinnedSafetyLevelParam
  }
  case object DirectMessagesSearch etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesSearchSafetyLevelParam
  }
  case object EditHistoryTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableEditHistoryTimelineSafetyLevelParam
  }
  case object ElevatedQuoteTweetTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableElevatedQuoteTweetTimelineSafetyLevelParam
  }
  case object EmbeddedTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableEmbeddedTweetSafetyLevelParam
  }
  case object EmbedsPublicInterestNotice etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableEmbedsPublicInterestNoticeSafetyLevelParam
  }
  case object EmbedTweetMarkup etwittertends SafetyLevel {
    override def enabledParam: SafetyLevelParam = EnableEmbedTweetMarkupSafetyLevelParam
  }
  case object WritePathLimitedActionsEnforcement etwittertends SafetyLevel {
    override def enabledParam: SafetyLevelParam =
      EnableWritePathLimitedActionsEnforcementSafetyLevelParam
  }
  case object FilterNone etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterNoneSafetyLevelParam
  }
  case object FilterAll etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterAllSafetyLevelParam
  }
  case object FilterAllPlaceholder etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterDefaultSafetyLevelParam
  }
  case object FilterDefault etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterDefaultSafetyLevelParam
  }
  case object FollowedTopicsTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFollowedTopicsTimelineSafetyLevelParam
  }
  case object FollowerConnections etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFollowerConnectionsSafetyLevelParam
  }
  case object FollowingAndFollowersUserList etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableFollowingAndFollowersUserListSafetyLevelParam
  }
  case object ForDevelopmentOnly etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableForDevelopmentOnlySafetyLevelParam
  }
  case object FriendsFollowingList etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFriendsFollowingListSafetyLevelParam
  }
  case object GraphqlDefault etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableGraphqlDefaultSafetyLevelParam
  }
  case object GryphonDecksAndColumns etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableGryphonDecksAndColumnsSafetyLevelParam
  }
  case object HumanizationNudge etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableHumanizationNudgeSafetyLevelParam
  }
  case object KitchenSinkDevelopment etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableKitchenSinkDevelopmentSafetyLevelParam
  }
  case object ListHeader etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListHeaderSafetyLevelParam
  }
  case object ListMemberships etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListMembershipsSafetyLevelParam
  }
  case object ListOwnerships etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListOwnershipsSafetyLevelParam
  }
  case object ListRecommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListRecommendationsSafetyLevelParam
  }
  case object ListSearch etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListSearchSafetyLevelParam
  }
  case object ListSubscriptions etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListSubscriptionsSafetyLevelParam
  }
  case object LivePipelineEngagementCounts etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableLivePipelineEngagementCountsSafetyLevelParam
  }
  case object LiveVideoTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableLiveVideoTimelineSafetyLevelParam
  }
  case object MagicRecs etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsSafetyLevelParam
  }
  case object MagicRecsAggressive etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsAggressiveSafetyLevelParam
  }
  case object MagicRecsAggressiveV2 etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsAggressiveV2SafetyLevelParam
  }
  case object MagicRecsV2 etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsV2SafetyLevelParam
  }
  case object Minimal etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMinimalSafetyLevelParam
  }
  case object ModeratedTweetsTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableModeratedTweetsTimelineSafetyLevelParam
  }
  case object Moments etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMomentsSafetyLevelParam
  }
  case object NearbyTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNearbySafetyLevelParam
  }
  case object NewUserEtwitterperience etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNewUserEtwitterperienceSafetyLevelParam
  }
  case object NotificationsIbis etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsIbisSafetyLevelParam
  }
  case object NotificationsPlatform etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsPlatformSafetyLevelParam
  }
  case object NotificationsPlatformPush etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsPlatformPushSafetyLevelParam
  }
  case object NotificationsQig etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsQigSafetyLevelParam
  }
  case object NotificationsRead etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsReadSafetyLevelParam
  }
  case object NotificationsTimelineDeviceFollow etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableNotificationsTimelineDeviceFollowSafetyLevelParam
  }
  case object NotificationsWrite etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsWriteSafetyLevelParam
  }
  case object NotificationsWriterV2 etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsWriterV2SafetyLevelParam
  }
  case object NotificationsWriterTweetHydrator etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableNotificationsWriterTweetHydratorSafetyLevelParam
  }
  case object ProfileMitwittererMedia etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableProfileMitwittererMediaSafetyLevelParam
  }
  case object ProfileMitwittererFavorites etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableProfileMitwittererFavoritesSafetyLevelParam
  }
  case object QuickPromoteTweetEligibility etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableQuickPromoteTweetEligibilitySafetyLevelParam
  }
  case object QuoteTweetTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableQuoteTweetTimelineSafetyLevelParam
  }
  case object QuotedTweetRules etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableQuotedTweetRulesParam
  }
  case object Recommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRecommendationsSafetyLevelParam
  }
  case object RecosVideo etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRecosVideoSafetyLevelParam
  }
  case object RecosWritePath etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRecosWritePathSafetyLevelParam
  }
  case object RepliesGrouping etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRepliesGroupingSafetyLevelParam
  }
  case object ReportCenter etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableReportCenterSafetyLevelParam
  }
  case object ReturningUserEtwitterperience etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableReturningUserEtwitterperienceSafetyLevelParam
  }
  case object ReturningUserEtwitterperienceFocalTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableReturningUserEtwitterperienceFocalTweetSafetyLevelParam
  }
  case object Revenue etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRevenueSafetyLevelParam
  }
  case object RitoActionedTweetTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRitoActionedTweetTimelineParam
  }
  case object SafeSearchMinimal etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSafeSearchMinimalSafetyLevelParam
  }
  case object SafeSearchStrict etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSafeSearchStrictSafetyLevelParam
  }
  case object SearchHydration etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchHydrationSafetyLevelParam
  }
  case object SearchLatest etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchLatestSafetyLevelParam
  }
  case object SearchMitwittererSrpMinimal etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchMitwittererSrpMinimalSafetyLevelParam
  }
  case object SearchMitwittererSrpStrict etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchMitwittererSrpStrictSafetyLevelParam
  }
  case object SearchPeopleSrp etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchPeopleSearchResultPageSafetyLevelParam
  }
  case object SearchPeopleTypeahead etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchPeopleTypeaheadSafetyLevelParam
  }
  case object SearchPhoto etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchPhotoSafetyLevelParam
  }
  case object ShoppingManagerSpyMode etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableShoppingManagerSpyModeSafetyLevelParam
  }
  case object StratoEtwittertLimitedEngagements etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableStratoEtwittertLimitedEngagementsSafetyLevelParam
  }
  case object SearchTop etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchTopSafetyLevelParam
  }
  case object SearchTopQig etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchTopQigSafetyLevelParam
  }
  case object SearchTrendTakeoverPromotedTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = SearchTrendTakeoverPromotedTweetSafetyLevelParam
  }
  case object SearchVideo etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchVideoSafetyLevelParam
  }
  case object SearchBlenderUserRules etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchBlenderUserRulesSafetyLevelParam
  }
  case object SearchLatestUserRules etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchLatestUserRulesSafetyLevelParam
  }
  case object SignalsReactions etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSignalsReactionsSafetyLevelParam
  }
  case object SignalsTweetReactingUsers etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSignalsTweetReactingUsersSafetyLevelParam
  }
  case object SocialProof etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSocialProofSafetyLevelParam
  }
  case object SoftInterventionPivot etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSoftInterventionPivotSafetyLevelParam
  }
  case object SpaceFleetline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceFleetlineSafetyLevelParam
  }
  case object SpaceHomeTimelineUpranking etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceHomeTimelineUprankingSafetyLevelParam
  }
  case object SpaceJoinScreen etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceJoinScreenSafetyLevelParam
  }
  case object SpaceNotifications etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceNotificationSafetyLevelParam
  }
  case object Spaces etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpacesSafetyLevelParam
  }
  case object SpacesParticipants etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpacesParticipantsSafetyLevelParam
  }
  case object SpacesSellerApplicationStatus etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableSpacesSellerApplicationStatusSafetyLevelParam
  }
  case object SpacesSharing etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpacesSharingSafetyLevelParam
  }
  case object SpaceTweetAvatarHomeTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceTweetAvatarHomeTimelineSafetyLevelParam
  }
  case object StickersTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableStickersTimelineSafetyLevelParam
  }
  case object StreamServices etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableStreamServicesSafetyLevelParam
  }
  case object SuperFollowerConnections etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSuperFollowerConnectionsSafetyLevelParam
  }
  case object SuperLike etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSuperLikeSafetyLevelParam
  }
  case object Test etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTestSafetyLevelParam
  }
  case object TimelineConversations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineConversationsSafetyLevelParam
  }
  case object TimelineConversationsDownranking etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineConversationsDownrankingSafetyLevelParam
  }
  case object TimelineConversationsDownrankingMinimal etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineConversationsDownrankingMinimalSafetyLevelParam
  }
  case object TimelineFollowingActivity etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFollowingActivitySafetyLevelParam
  }
  case object TimelineHome etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeSafetyLevelParam
  }
  case object TimelineHomeCommunities etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeCommunitiesSafetyLevelParam
  }
  case object TimelineHomeHydration etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeHydrationSafetyLevelParam
  }
  case object TimelineHomePromotedHydration etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineHomePromotedHydrationSafetyLevelParam
  }
  case object TimelineHomeRecommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeRecommendationsSafetyLevelParam
  }
  case object TimelineHomeTopicFollowRecommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineHomeTopicFollowRecommendationsSafetyLevelParam
  }
  case object TimelineScorer etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineScorerSafetyLevelParam
  }
  case object TopicsLandingPageTopicRecommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTopicsLandingPageTopicRecommendationsSafetyLevelParam
  }
  case object EtwitterploreRecommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableEtwitterploreRecommendationsSafetyLevelParam
  }
  case object TimelineModeratedTweetsHydration etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineModeratedTweetsHydrationSafetyLevelParam
  }
  case object TimelineInjection etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineInjectionSafetyLevelParam
  }
  case object TimelineMentions etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineMentionsSafetyLevelParam
  }
  case object TimelineHomeLatest etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeLatestSafetyLevelParam
  }
  case object TimelineLikedBy etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineLikedBySafetyLevelParam
  }
  case object TimelineRetweetedBy etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineRetweetedBySafetyLevelParam
  }
  case object TimelineSuperLikedBy etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineLikedBySafetyLevelParam
  }
  case object TimelineBookmark etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineBookmarkSafetyLevelParam
  }
  case object TimelineContentControls etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineContentControlsSafetyLevelParam
  }
  case object TimelineMedia etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineMediaSafetyLevelParam
  }
  case object TimelineReactiveBlending etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineReactiveBlendingSafetyLevelParam
  }
  case object TimelineFavorites etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFavoritesSafetyLevelParam
  }
  case object TimelineFavoritesSelfView etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFavoritesSelfViewSafetyLevelParam
  }
  case object TimelineLists etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineListsSafetyLevelParam
  }
  case object TimelineProfile etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileSafetyLevelParam
  }
  case object TimelineProfileAll etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileAllSafetyLevelParam
  }

  case object TimelineProfileSpaces etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileSpacesSafetyLevelParam
  }

  case object TimelineProfileSuperFollows etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileSuperFollowsSafetyLevelParam
  }
  case object TimelineFocalTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFocalTweetSafetyLevelParam
  }
  case object Tombstoning etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTombstoningSafetyLevelParam
  }
  case object TopicRecommendations etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTopicRecommendationsSafetyLevelParam
  }
  case object TrendsRepresentativeTweet etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTrendsRepresentativeTweetSafetyLevelParam
  }
  case object TrustedFriendsUserList etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTrustedFriendsUserListSafetyLevelParam
  }
  case object TwitterDelegateUserList etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterDelegateUserListSafetyLevelParam
  }
  case object TweetDetail etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetDetailSafetyLevelParam
  }
  case object TweetDetailNonToo etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetDetailNonTooSafetyLevelParam
  }
  case object TweetDetailWithInjectionsHydration etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTweetDetailWithInjectionsHydrationSafetyLevelParam
  }
  case object TweetEngagers etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetEngagersSafetyLevelParam
  }
  case object TweetReplyNudge etwittertends SafetyLevel {
    override def enabledParam: SafetyLevelParam = EnableTweetReplyNudgeParam
  }
  case object TweetScopedTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetScopedTimelineSafetyLevelParam
  }
  case object TweetWritesApi etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetWritesApiSafetyLevelParam
  }
  case object TwitterArticleCompose etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterArticleComposeSafetyLevelParam
  }
  case object TwitterArticleProfileTab etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterArticleProfileTabSafetyLevelParam
  }
  case object TwitterArticleRead etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterArticleReadSafetyLevelParam
  }
  case object UserProfileHeader etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserProfileHeaderSafetyLevelParam
  }
  case object UserMilestoneRecommendation etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserMilestoneRecommendationSafetyLevelParam
  }
  case object UserScopedTimeline etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserScopedTimelineSafetyLevelParam
  }
  case object UserSearchSrp etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSearchSrpSafetyLevelParam
  }
  case object UserSearchTypeahead etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSearchTypeaheadSafetyLevelParam
  }
  case object UserSelfViewOnly etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSelfViewOnlySafetyLevelParam
  }
  case object UserSettings etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSettingsSafetyLevelParam
  }
  case object VideoAds etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableVideoAdsSafetyLevelParam
  }
  case object ZipbirdConsumerArchives etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableZipbirdConsumerArchivesSafetyLevelParam
  }
  case object TweetAward etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetAwardSafetyLevelParam
  }

  case object DeprecatedSafetyLevel etwittertends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDeprecatedSafetyLevel
  }


  def fromThrift(safetyLevel: ThriftSafetyLevel): SafetyLevel =
    thriftToModelMap.get(safetyLevel).getOrElse(DeprecatedSafetyLevel)

  def toThrift(safetyLevel: SafetyLevel): ThriftSafetyLevel =
    modelToThriftMap
      .get(safetyLevel).getOrElse(ThriftSafetyLevel.EnumUnknownSafetyLevel(DeprecatedEnumValue))

  val List: Seq[SafetyLevel] =
    ThriftSafetyLevel.list.map(fromThrift).filter(_ != DeprecatedSafetyLevel)
}
