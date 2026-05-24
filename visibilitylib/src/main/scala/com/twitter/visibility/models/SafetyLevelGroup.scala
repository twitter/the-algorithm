package com.twitter.visibility.models

import com.twitter.visibility.models.SafetyLevel.AccessInternalPromotedContent
import com.twitter.visibility.models.SafetyLevel.AdsBusinessSettings
import com.twitter.visibility.models.SafetyLevel.AdsCampaign
import com.twitter.visibility.models.SafetyLevel.AdsManager
import com.twitter.visibility.models.SafetyLevel.AdsReportingDashboard
import com.twitter.visibility.models.SafetyLevel.AllSubscribedLists
import com.twitter.visibility.models.SafetyLevel.Appeals
import com.twitter.visibility.models.SafetyLevel.ArticleTweetTimeline
import com.twitter.visibility.models.SafetyLevel.BaseQig
import com.twitter.visibility.models.SafetyLevel.BirdwatchNeedsYourHelpNotifications
import com.twitter.visibility.models.SafetyLevel.BirdwatchNoteAuthor
import com.twitter.visibility.models.SafetyLevel.BirdwatchNoteTweetsTimeline
import com.twitter.visibility.models.SafetyLevel.BlockMuteUsersTimeline
import com.twitter.visibility.models.SafetyLevel.BrandSafety
import com.twitter.visibility.models.SafetyLevel.CardPollVoting
import com.twitter.visibility.models.SafetyLevel.CardsService
import com.twitter.visibility.models.SafetyLevel.ContentControlToolInstall
import com.twitter.visibility.models.SafetyLevel.ConversationFocalPrehydration
import com.twitter.visibility.models.SafetyLevel.ConversationFocalTweet
import com.twitter.visibility.models.SafetyLevel.ConversationInjectedTweet
import com.twitter.visibility.models.SafetyLevel.ConversationReply
import com.twitter.visibility.models.SafetyLevel.CuratedTrendsRepresentativeTweet
import com.twitter.visibility.models.SafetyLevel.CurationPolicyViolations
import com.twitter.visibility.models.SafetyLevel.DesFollowingAndFollowersUserList
import com.twitter.visibility.models.SafetyLevel.DesHomeTimeline
import com.twitter.visibility.models.SafetyLevel.DesQuoteTweetTimeline
import com.twitter.visibility.models.SafetyLevel.DesRealtime
import com.twitter.visibility.models.SafetyLevel.DesRealtimeSpamEnrichment
import com.twitter.visibility.models.SafetyLevel.DesRealtimeTweetFilter
import com.twitter.visibility.models.SafetyLevel.DesRetweetingUsers
import com.twitter.visibility.models.SafetyLevel.DesTweetDetail
import com.twitter.visibility.models.SafetyLevel.DesTweetLikingUsers
import com.twitter.visibility.models.SafetyLevel.DesUserBookmarks
import com.twitter.visibility.models.SafetyLevel.DesUserLikedTweets
import com.twitter.visibility.models.SafetyLevel.DesUserMentions
import com.twitter.visibility.models.SafetyLevel.DesUserTweets
import com.twitter.visibility.models.SafetyLevel.DevPlatformComplianceStream
import com.twitter.visibility.models.SafetyLevel.DevPlatformGetListTweets
import com.twitter.visibility.models.SafetyLevel.DirectMessages
import com.twitter.visibility.models.SafetyLevel.DirectMessagesConversationList
import com.twitter.visibility.models.SafetyLevel.DirectMessagesConversationTimeline
import com.twitter.visibility.models.SafetyLevel.DirectMessagesInbox
import com.twitter.visibility.models.SafetyLevel.DirectMessagesMutedUsers
import com.twitter.visibility.models.SafetyLevel.DirectMessagesPinned
import com.twitter.visibility.models.SafetyLevel.DirectMessagesSearch
import com.twitter.visibility.models.SafetyLevel.EditHistoryTimeline
import com.twitter.visibility.models.SafetyLevel.ElevatedQuoteTweetTimeline
import com.twitter.visibility.models.SafetyLevel.EmbedTweetMarkup
import com.twitter.visibility.models.SafetyLevel.EmbeddedTweet
import com.twitter.visibility.models.SafetyLevel.EmbedsPublicInterestNotice
import com.twitter.visibility.models.SafetyLevel.ExploreRecommendations
import com.twitter.visibility.models.SafetyLevel.FilterAll
import com.twitter.visibility.models.SafetyLevel.FilterAllPlaceholder
import com.twitter.visibility.models.SafetyLevel.FilterDefault
import com.twitter.visibility.models.SafetyLevel.FilterNone
import com.twitter.visibility.models.SafetyLevel.FollowedTopicsTimeline
import com.twitter.visibility.models.SafetyLevel.FollowerConnections
import com.twitter.visibility.models.SafetyLevel.FollowingAndFollowersUserList
import com.twitter.visibility.models.SafetyLevel.ForDevelopmentOnly
import com.twitter.visibility.models.SafetyLevel.FriendsFollowingList
import com.twitter.visibility.models.SafetyLevel.GraphqlDefault
import com.twitter.visibility.models.SafetyLevel.GryphonDecksAndColumns
import com.twitter.visibility.models.SafetyLevel.HumanizationNudge
import com.twitter.visibility.models.SafetyLevel.KitchenSinkDevelopment
import com.twitter.visibility.models.SafetyLevel.ListHeader
import com.twitter.visibility.models.SafetyLevel.ListMemberships
import com.twitter.visibility.models.SafetyLevel.ListOwnerships
import com.twitter.visibility.models.SafetyLevel.ListRecommendations
import com.twitter.visibility.models.SafetyLevel.ListSearch
import com.twitter.visibility.models.SafetyLevel.ListSubscriptions
import com.twitter.visibility.models.SafetyLevel.LivePipelineEngagementCounts
import com.twitter.visibility.models.SafetyLevel.LiveVideoTimeline
import com.twitter.visibility.models.SafetyLevel.MagicRecs
import com.twitter.visibility.models.SafetyLevel.MagicRecsAggressive
import com.twitter.visibility.models.SafetyLevel.MagicRecsAggressiveV2
import com.twitter.visibility.models.SafetyLevel.MagicRecsV2
import com.twitter.visibility.models.SafetyLevel.Minimal
import com.twitter.visibility.models.SafetyLevel.ModeratedTweetsTimeline
import com.twitter.visibility.models.SafetyLevel.Moments
import com.twitter.visibility.models.SafetyLevel.NearbyTimeline
import com.twitter.visibility.models.SafetyLevel.NewUserExperience
import com.twitter.visibility.models.SafetyLevel.NotificationsIbis
import com.twitter.visibility.models.SafetyLevel.NotificationsPlatform
import com.twitter.visibility.models.SafetyLevel.NotificationsPlatformPush
import com.twitter.visibility.models.SafetyLevel.NotificationsQig
import com.twitter.visibility.models.SafetyLevel.NotificationsRead
import com.twitter.visibility.models.SafetyLevel.NotificationsTimelineDeviceFollow
import com.twitter.visibility.models.SafetyLevel.NotificationsWrite
import com.twitter.visibility.models.SafetyLevel.NotificationsWriterTweetHydrator
import com.twitter.visibility.models.SafetyLevel.NotificationsWriterV2
import com.twitter.visibility.models.SafetyLevel.ProfileMixerFavorites
import com.twitter.visibility.models.SafetyLevel.ProfileMixerMedia
import com.twitter.visibility.models.SafetyLevel.QuickPromoteTweetEligibility
import com.twitter.visibility.models.SafetyLevel.QuoteTweetTimeline
import com.twitter.visibility.models.SafetyLevel.QuotedTweetRules
import com.twitter.visibility.models.SafetyLevel.RecosVideo
import com.twitter.visibility.models.SafetyLevel.RecosWritePath
import com.twitter.visibility.models.SafetyLevel.RepliesGrouping
import com.twitter.visibility.models.SafetyLevel.ReportCenter
import com.twitter.visibility.models.SafetyLevel.ReturningUserExperienceFocalTweet
import com.twitter.visibility.models.SafetyLevel.Revenue
import com.twitter.visibility.models.SafetyLevel.SafeSearchMinimal
import com.twitter.visibility.models.SafetyLevel.SafeSearchStrict
import com.twitter.visibility.models.SafetyLevel.SearchBlenderUserRules
import com.twitter.visibility.models.SafetyLevel.SearchHydration
import com.twitter.visibility.models.SafetyLevel.SearchLatest
import com.twitter.visibility.models.SafetyLevel.SearchLatestUserRules
import com.twitter.visibility.models.SafetyLevel.SearchMixerSrpMinimal
import com.twitter.visibility.models.SafetyLevel.SearchMixerSrpStrict
import com.twitter.visibility.models.SafetyLevel.SearchPeopleSrp
import com.twitter.visibility.models.SafetyLevel.SearchPeopleTypeahead
import com.twitter.visibility.models.SafetyLevel.SearchPhoto
import com.twitter.visibility.models.SafetyLevel.SearchTop
import com.twitter.visibility.models.SafetyLevel.SearchTopQig
import com.twitter.visibility.models.SafetyLevel.SearchTrendTakeoverPromotedTweet
import com.twitter.visibility.models.SafetyLevel.SearchVideo
import com.twitter.visibility.models.SafetyLevel.ShoppingManagerSpyMode
import com.twitter.visibility.models.SafetyLevel.SignalsReactions
import com.twitter.visibility.models.SafetyLevel.SignalsTweetReactingUsers
import com.twitter.visibility.models.SafetyLevel.SoftInterventionPivot
import com.twitter.visibility.models.SafetyLevel.SpaceFleetline
import com.twitter.visibility.models.SafetyLevel.SpaceHomeTimelineUpranking
import com.twitter.visibility.models.SafetyLevel.SpaceJoinScreen
import com.twitter.visibility.models.SafetyLevel.SpaceNotifications
import com.twitter.visibility.models.SafetyLevel.SpaceTweetAvatarHomeTimeline
import com.twitter.visibility.models.SafetyLevel.SpacesParticipants
import com.twitter.visibility.models.SafetyLevel.SpacesSellerApplicationStatus
import com.twitter.visibility.models.SafetyLevel.SpacesSharing
import com.twitter.visibility.models.SafetyLevel.StickersTimeline
import com.twitter.visibility.models.SafetyLevel.StratoExtLimitedEngagements
import com.twitter.visibility.models.SafetyLevel.StreamServices
import com.twitter.visibility.models.SafetyLevel.SuperFollowerConnections
import com.twitter.visibility.models.SafetyLevel.SuperLike
import com.twitter.visibility.models.SafetyLevel.Test
import com.twitter.visibility.models.SafetyLevel.TimelineBookmark
import com.twitter.visibility.models.SafetyLevel.TimelineContentControls
import com.twitter.visibility.models.SafetyLevel.TimelineConversations
import com.twitter.visibility.models.SafetyLevel.TimelineConversationsDownranking
import com.twitter.visibility.models.SafetyLevel.TimelineConversationsDownrankingMinimal
import com.twitter.visibility.models.SafetyLevel.TimelineFavorites
import com.twitter.visibility.models.SafetyLevel.TimelineFavoritesSelfView
import com.twitter.visibility.models.SafetyLevel.TimelineFocalTweet
import com.twitter.visibility.models.SafetyLevel.TimelineFollowingActivity
import com.twitter.visibility.models.SafetyLevel.TimelineHomeCommunities
import com.twitter.visibility.models.SafetyLevel.TimelineHomeHydration
import com.twitter.visibility.models.SafetyLevel.TimelineHomeLatest
import com.twitter.visibility.models.SafetyLevel.TimelineHomePromotedHydration
import com.twitter.visibility.models.SafetyLevel.TimelineHomeRecommendations
import com.twitter.visibility.models.SafetyLevel.TimelineHomeTopicFollowRecommendations
import com.twitter.visibility.models.SafetyLevel.TimelineInjection
import com.twitter.visibility.models.SafetyLevel.TimelineLikedBy
import com.twitter.visibility.models.SafetyLevel.TimelineLists
import com.twitter.visibility.models.SafetyLevel.TimelineMedia
import com.twitter.visibility.models.SafetyLevel.TimelineMentions
import com.twitter.visibility.models.SafetyLevel.TimelineModeratedTweetsHydration
import com.twitter.visibility.models.SafetyLevel.TimelineProfileAll
import com.twitter.visibility.models.SafetyLevel.TimelineProfileSpaces
import com.twitter.visibility.models.SafetyLevel.TimelineProfileSuperFollows
import com.twitter.visibility.models.SafetyLevel.TimelineScorer
import com.twitter.visibility.models.SafetyLevel.Tombstoning
import com.twitter.visibility.models.SafetyLevel.TopicsLandingPageTopicRecommendations
import com.twitter.visibility.models.SafetyLevel.TrendsRepresentativeTweet
import com.twitter.visibility.models.SafetyLevel.TrustedFriendsUserList
import com.twitter.visibility.models.SafetyLevel.TweetDetail
import com.twitter.visibility.models.SafetyLevel.TweetDetailNonToo
import com.twitter.visibility.models.SafetyLevel.TweetDetailWithInjectionsHydration
import com.twitter.visibility.models.SafetyLevel.TweetEngagers
import com.twitter.visibility.models.SafetyLevel.TweetReplyNudge
import com.twitter.visibility.models.SafetyLevel.TweetWritesApi
import com.twitter.visibility.models.SafetyLevel.TwitterArticleCompose
import com.twitter.visibility.models.SafetyLevel.TwitterArticleProfileTab
import com.twitter.visibility.models.SafetyLevel.TwitterArticleRead
import com.twitter.visibility.models.SafetyLevel.UserMilestoneRecommendation
import com.twitter.visibility.models.SafetyLevel.UserProfileHeader
import com.twitter.visibility.models.SafetyLevel.UserSelfViewOnly
import com.twitter.visibility.models.SafetyLevel.UserSettings
import com.twitter.visibility.models.SafetyLevel.VideoAds
import com.twitter.visibility.models.SafetyLevel.WritePathLimitedActionsEnforcement
import com.twitter.visibility.models.SafetyLevel.ZipbirdConsumerArchives

sealed trait SafetyLevelGroup { val levels: Set[SafetyLevel] }

object SafetyLevelGroup {
  case object Ads extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      AdsBusinessSettings,
      AdsCampaign,
      AdsManager,
      AdsReportingDashboard,
      BrandSafety,
      VideoAds,
      QuickPromoteTweetEligibility
    )
  }

  case object ArticleTimeline extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      ArticleTweetTimeline,
    )
  }

  case object ArticleTweets extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      TwitterArticleCompose,
      TwitterArticleProfileTab,
      TwitterArticleRead,
    )
  }

  case object Birdwatch extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      BirdwatchNoteAuthor,
      BirdwatchNoteTweetsTimeline,
      BirdwatchNeedsYourHelpNotifications,
    )
  }

  case object Cards extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      CardPollVoting,
      CardsService,
    )
  }

  case object Communities extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.Communities
    )
  }

  case object Conversation extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      ConversationFocalPrehydration,
      ConversationFocalTweet,
      ConversationInjectedTweet,
      ConversationReply,
      Tombstoning,
    )
  }

  case object CreativeContainerService extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      AccessInternalPromotedContent
    )
  }

  case object Des extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      DevPlatformGetListTweets,
      DesFollowingAndFollowersUserList,
      DesHomeTimeline,
      DesQuoteTweetTimeline,
      DesRetweetingUsers,
      DesTweetDetail,
      DesTweetLikingUsers,
      DesUserBookmarks,
      DesUserLikedTweets,
      DesUserMentions,
      DesUserTweets,
      DevPlatformComplianceStream,
    )
  }

  case object DesStream extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      DesRealtime,
      DesRealtimeSpamEnrichment,
      DesRealtimeTweetFilter,
    )
  }

  case object Dm extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      DirectMessages,
      DirectMessagesConversationList,
      DirectMessagesConversationTimeline,
      DirectMessagesInbox,
      DirectMessagesMutedUsers,
      DirectMessagesPinned,
      DirectMessagesSearch,
    )
  }

  case object Followers extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      FollowedTopicsTimeline,
      FollowerConnections,
      FollowingAndFollowersUserList,
      FriendsFollowingList,
    )
  }

  case object Graphql extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      FilterDefault,
      GraphqlDefault,
      SoftInterventionPivot,
    )
  }

  case object Jiminy extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      HumanizationNudge,
      TweetReplyNudge,
    )
  }

  case object Lists extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      AllSubscribedLists,
      ListHeader,
      ListMemberships,
      ListOwnerships,
      ListRecommendations,
      ListSearch,
      ListSubscriptions,
    )
  }

  case object Notifications extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      NotificationsIbis,
      NotificationsPlatform,
      NotificationsPlatformPush,
      NotificationsQig,
      NotificationsRead,
      NotificationsTimelineDeviceFollow,
      NotificationsWrite,
      NotificationsWriterTweetHydrator,
      NotificationsWriterV2,
    )
  }

  case object Other extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      CuratedTrendsRepresentativeTweet,
      CurationPolicyViolations,
      BaseQig,
      Appeals,
      ContentControlToolInstall,
      EditHistoryTimeline,
      ElevatedQuoteTweetTimeline,
      EmbeddedTweet,
      EmbedsPublicInterestNotice,
      EmbedTweetMarkup,
      ExploreRecommendations,
      WritePathLimitedActionsEnforcement,
      LiveVideoTimeline,
      LivePipelineEngagementCounts,
      Minimal,
      Moments,
      NearbyTimeline,
      NewUserExperience,
      QuoteTweetTimeline,
      QuotedTweetRules,
      ReportCenter,
      Revenue,
      ShoppingManagerSpyMode,
      StickersTimeline,
      SuperLike,
      TrendsRepresentativeTweet,
      TrustedFriendsUserList,
      GryphonDecksAndColumns,
      TweetEngagers,
      TweetWritesApi,
      UserMilestoneRecommendation,
      StreamServices,
      ZipbirdConsumerArchives
    )
  }

  case object Profile extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      UserProfileHeader,
      UserSelfViewOnly,
      UserSettings,
    )
  }

  case object Reactions extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SignalsReactions,
      SignalsTweetReactingUsers,
    )
  }

  case object Recommendations extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      MagicRecs,
      MagicRecsV2,
      MagicRecsAggressive,
      MagicRecsAggressiveV2,
      SafetyLevel.Recommendations,
      RecosVideo,
      RecosWritePath,
    )
  }

  case object ReturningUserExperience extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.ReturningUserExperience,
      ReturningUserExperienceFocalTweet,
    )
  }

  case object SafeSearch extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafeSearchMinimal,
      SafeSearchStrict,
    )
  }

  case object Search extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SearchHydration,
      SearchLatest,
      SearchTop,
      SearchTopQig,
      SearchPeopleSrp,
      SearchPeopleTypeahead,
      SearchPhoto,
      SearchTrendTakeoverPromotedTweet,
      SearchVideo,
      SearchBlenderUserRules,
      SearchLatestUserRules,
    )
  }

  case object SearchMixer extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SearchMixerSrpMinimal,
      SearchMixerSrpStrict,
    )
  }

  case object Socialproof extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.SocialProof
    )
  }

  case object Spaces extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SpaceFleetline,
      SpaceHomeTimelineUpranking,
      SpaceJoinScreen,
      SpaceNotifications,
      SafetyLevel.Spaces,
      SpacesParticipants,
      SpacesSellerApplicationStatus,
      SpacesSharing,
      SpaceTweetAvatarHomeTimeline,
    )
  }

  case object Strato extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      StratoExtLimitedEngagements
    )
  }

  case object Superfollows extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SuperFollowerConnections,
      TimelineProfileSuperFollows,
    )
  }

  case object Testing extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      ForDevelopmentOnly,
      KitchenSinkDevelopment,
      Test,
    )
  }

  case object Timeline extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      BlockMuteUsersTimeline,
      TimelineBookmark,
      TimelineContentControls,
      TimelineConversationsDownranking,
      TimelineConversationsDownrankingMinimal,
      TimelineFavorites,
      TimelineFavoritesSelfView,
      TimelineFollowingActivity,
      TimelineScorer,
      TimelineInjection,
      TimelineLikedBy,
      TimelineLists,
      TimelineMedia,
      TimelineMentions,
      ModeratedTweetsTimeline,
      TimelineModeratedTweetsHydration,
    )
  }

  case object TopicRecommendations extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.TopicRecommendations,
      TopicsLandingPageTopicRecommendations,
    )
  }

  case object TimelineProfile extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.TimelineProfile,
      TimelineProfileAll,
      TimelineProfileSpaces,
      TimelineMedia,
      ProfileMixerMedia,
      TimelineFavorites,
      ProfileMixerFavorites
    )
  }

  case object TimelineHome extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.TimelineHome,
      TimelineHomeCommunities,
      TimelineHomeHydration,
      TimelineHomeLatest,
      TimelineHomePromotedHydration,
      TimelineHomeRecommendations,
      TimelineHomeTopicFollowRecommendations,
    )
  }

  case object TlsApi extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      TimelineConversations,
      TimelineFocalTweet,
    )
  }

  case object TweetDetails extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      TweetDetail,
      TweetDetailNonToo,
      TweetDetailWithInjectionsHydration,
      RepliesGrouping,
    )
  }

  case object Special extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      FilterAll,
      FilterAllPlaceholder,
      FilterNone,
    )
  }
}
