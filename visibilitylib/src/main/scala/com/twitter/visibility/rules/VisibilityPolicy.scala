package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.models.ContentId
import com.twitter.visibility.rules.ConversationControlRules._
import com.twitter.visibility.rules.FollowerRelations.AuthorMutesViewerRule
import com.twitter.visibility.rules.FollowerRelations.ProtectedViewerRule
import com.twitter.visibility.rules.PolicyLevelRuleParams.ruleParams
import com.twitter.visibility.rules.PublicInterestRules._
import com.twitter.visibility.rules.SafeSearchTweetRules._
import com.twitter.visibility.rules.SafeSearchUserRules.SafeSearchNsfwAvatarImageUserLabelRule
import com.twitter.visibility.rules.SafeSearchUserRules._
import com.twitter.visibility.rules.SpaceRules._
import com.twitter.visibility.rules.TotwittericityReplyFilterRules.TotwittericityReplyFilterDropNotificationRule
import com.twitter.visibility.rules.TotwittericityReplyFilterRules.TotwittericityReplyFilterRule
import com.twitter.visibility.rules.UnsafeSearchTweetRules._
import com.twitter.visibility.rules.UserUnavailableStateTombstoneRules._

abstract class VisibilityPolicy(
  val tweetRules: Seq[Rule] = Nil,
  val userRules: Seq[Rule] = Nil,
  val cardRules: Seq[Rule] = Nil,
  val quotedTweetRules: Seq[Rule] = Nil,
  val dmRules: Seq[Rule] = Nil,
  val dmConversationRules: Seq[Rule] = Nil,
  val dmEventRules: Seq[Rule] = Nil,
  val spaceRules: Seq[Rule] = Nil,
  val userUnavailableStateRules: Seq[Rule] = Nil,
  val twitterArticleRules: Seq[Rule] = Nil,
  val deletedTweetRules: Seq[Rule] = Nil,
  val mediaRules: Seq[Rule] = Nil,
  val communityRules: Seq[Rule] = Nil,
  val policyRuleParams: Map[Rule, PolicyLevelRuleParams] = Map.empty) {

  def forContentId(contentId: ContentId): Seq[Rule] =
    contentId match {
      case ContentId.TweetId(_) => tweetRules
      case ContentId.UserId(_) => userRules
      case ContentId.CardId(_) => cardRules
      case ContentId.QuotedTweetRelationship(_, _) => quotedTweetRules
      case ContentId.NotificationId(_) => userRules
      case ContentId.DmId(_) => dmRules
      case ContentId.BlenderTweetId(_) => userRules ++ tweetRules
      case ContentId.SpaceId(_) => spaceRules
      case ContentId.SpacePlusUserId(_) => spaceRules ++ userRules
      case ContentId.DmConversationId(_) => dmConversationRules
      case ContentId.DmEventId(_) => dmEventRules
      case ContentId.UserUnavailableState(_) => userUnavailableStateRules
      case ContentId.TwitterArticleId(_) => twitterArticleRules
      case ContentId.DeleteTweetId(_) => deletedTweetRules
      case ContentId.MediaId(_) => mediaRules
      case ContentId.CommunityId(_) => communityRules
    }

  private[visibility] def allRules: Seq[Rule] =
    (tweetRules ++ userRules ++ cardRules ++ quotedTweetRules ++ dmRules ++ spaceRules ++ dmConversationRules ++ dmEventRules ++ twitterArticleRules ++ deletedTweetRules ++ mediaRules ++ communityRules)
}

object VisibilityPolicy {
  val baseTweetRules = Seq(
    DropCommunityTweetsRule,
    DropCommunityTweetCommunityNotVisibleRule,
    DropProtectedCommunityTweetsRule,
    DropHiddenCommunityTweetsRule,
    DropAuthorRemovedCommunityTweetsRule,
    SpamTweetLabelRule,
    PdnaTweetLabelRule,
    BounceTweetLabelRule,
    DropEtwitterclusiveTweetContentRule,
    DropTrustedFriendsTweetContentRule
  )

  val baseTweetTombstoneRules = Seq(
    TombstoneCommunityTweetsRule,
    TombstoneCommunityTweetCommunityNotVisibleRule,
    TombstoneProtectedCommunityTweetsRule,
    TombstoneHiddenCommunityTweetsRule,
    TombstoneAuthorRemovedCommunityTweetsRule,
    SpamTweetLabelTombstoneRule,
    PdnaTweetLabelTombstoneRule,
    BounceTweetLabelTombstoneRule,
    TombstoneEtwitterclusiveTweetContentRule,
    TombstoneTrustedFriendsTweetContentRule,
  )

  val baseMediaRules = Seq(
  )

  val baseQuotedTweetTombstoneRules = Seq(
    BounceQuotedTweetTombstoneRule
  )

  def union[T](rules: Seq[Rule]*): Seq[Rule] = {
    if (rules.isEmpty) {
      Seq.empty[Rule]
    } else {
      rules.reduce((a, b) => a ++ b.filterNot(a.contains))
    }
  }
}

case class PolicyLevelRuleParams(
  ruleParams: Seq[RuleParam[Boolean]],
  force: Boolean = false) {}

object PolicyLevelRuleParams {
  def ruleParams(ruleParams: RuleParam[Boolean]*): PolicyLevelRuleParams = {
    PolicyLevelRuleParams(ruleParams)
  }

  def ruleParams(force: Boolean, ruleParams: RuleParam[Boolean]*): PolicyLevelRuleParams = {
    PolicyLevelRuleParams(ruleParams, force)
  }
}

case object FilterAllPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(DropAllRule),
      userRules = Seq(DropAllRule),
      cardRules = Seq(DropAllRule),
      quotedTweetRules = Seq(DropAllRule),
      dmRules = Seq(DropAllRule),
      dmConversationRules = Seq(DropAllRule),
      dmEventRules = Seq(DropAllRule),
      spaceRules = Seq(DropAllRule),
      userUnavailableStateRules = Seq(DropAllRule),
      twitterArticleRules = Seq(DropAllRule),
      deletedTweetRules = Seq(DropAllRule),
      mediaRules = Seq(DropAllRule),
      communityRules = Seq(DropAllRule),
    )

case object FilterNonePolicy etwittertends VisibilityPolicy()

object ConversationsAdAvoidanceRules {
  val tweetRules = Seq(
    NsfwHighRecallTweetLabelAvoidRule,
    NsfwHighPrecisionTweetLabelAvoidRule,
    NsfwTetwittertTweetLabelAvoidRule,
    AvoidHighTotwittericityModelScoreRule,
    AvoidReportedTweetModelScoreRule,
    NsfwHighPrecisionUserLabelAvoidTweetRule,
    TweetNsfwUserAdminAvoidRule,
    DoNotAmplifyTweetLabelAvoidRule,
    NsfaHighPrecisionTweetLabelAvoidRule,
  )

  val policyRuleParams = Map[Rule, PolicyLevelRuleParams](
    NsfwHighRecallTweetLabelAvoidRule -> ruleParams(
      RuleParams.EnableNewAdAvoidanceRulesParam
    ),
    NsfwHighPrecisionTweetLabelAvoidRule -> ruleParams(
      RuleParams.EnableNewAdAvoidanceRulesParam
    ),
    NsfwTetwittertTweetLabelAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    AvoidHighTotwittericityModelScoreRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    AvoidReportedTweetModelScoreRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    NsfwHighPrecisionUserLabelAvoidTweetRule -> ruleParams(
      RuleParams.EnableNewAdAvoidanceRulesParam),
    TweetNsfwUserAdminAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    DoNotAmplifyTweetLabelAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    NsfaHighPrecisionTweetLabelAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
  )
}

case object FilterDefaultPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule
        )
    )

case object LimitedEngagementBaseRules
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        StaleTweetLimitedActionsRule,
        LimitRepliesByInvitationConversationRule,
        LimitRepliesCommunityConversationRule,
        LimitRepliesFollowersConversationRule,
        CommunityTweetCommunityNotFoundLimitedActionsRule,
        CommunityTweetCommunityDeletedLimitedActionsRule,
        CommunityTweetCommunitySuspendedLimitedActionsRule,
        CommunityTweetMemberRemovedLimitedActionsRule,
        CommunityTweetHiddenLimitedActionsRule,
        CommunityTweetMemberLimitedActionsRule,
        CommunityTweetNonMemberLimitedActionsRule,
        DynamicProductAdLimitedEngagementTweetLabelRule,
        TrustedFriendsTweetLimitedEngagementsRule
      )
    )

case object WritePathLimitedActionsEnforcementPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule
      ) ++
        LimitedEngagementBaseRules.tweetRules
    )

case object TestPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        TestRule
      )
    )

case object CardsServicePolicy
    etwittertends VisibilityPolicy(
      cardRules = Seq(
        DropProtectedAuthorPollCardRule,
        DropCardUriRootDomainDenylistRule
      ),
      spaceRules = Seq(
        SpaceHighTotwittericityScoreNonFollowerDropRule,
        SpaceHatefulHighRecallAllUsersDropRule,
        SpaceViolenceHighRecallAllUsersDropRule,
        ViewerIsSoftUserDropRule
      ),
    )

case object CardPollVotingPolicy
    etwittertends VisibilityPolicy(
      cardRules = Seq(
        DropProtectedAuthorPollCardRule,
        DropCommunityNonMemberPollCardRule
      )
    )

case object UserTimelineRules {
  val UserRules = Seq(
    AuthorBlocksViewerDropRule,
    ProtectedAuthorDropRule,
    SuspendedAuthorRule
  )
}

case object TimelineLikedByRules {
  val UserRules = Seq(
    CompromisedNonFollowerWithUqfRule,
    EngagementSpammerNonFollowerWithUqfRule,
    LowQualityNonFollowerWithUqfRule,
    ReadOnlyNonFollowerWithUqfRule,
    SpamHighRecallNonFollowerWithUqfRule
  )
}

case object FollowingAndFollowersUserListPolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object FriendsFollowingListPolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListOwnershipsPolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListRecommendationsPolicy
    etwittertends VisibilityPolicy(
      userRules = RecommendationsPolicy.userRules ++ Seq(
        DropNsfwUserAuthorRule,
        NsfwHighRecallRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAuthorRule
      )
    )

case object ListSearchBaseRules {

  val NonEtwitterperimentalSafeSearchMinimalPolicyUserRules: Seq[Rule] =
    SafeSearchMinimalPolicy.userRules.filterNot(_.isEtwitterperimental)

  val MinimalPolicyUserRules: Seq[Rule] = NonEtwitterperimentalSafeSearchMinimalPolicyUserRules

  val BlockMutePolicyUserRules = Seq(
    ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
    ViewerMutesAuthorViewerOptInBlockingOnSearchRule
  )

  val StrictPolicyUserRules = Seq(
    SafeSearchAbusiveUserLabelRule,
    SafeSearchAbusiveHighRecallUserLabelRule,
    SafeSearchCompromisedUserLabelRule,
    SafeSearchDoNotAmplifyNonFollowersUserLabelRule,
    SafeSearchDuplicateContentUserLabelRule,
    SafeSearchLowQualityUserLabelRule,
    SafeSearchNotGraduatedNonFollowersUserLabelRule,
    SafeSearchNsfwHighPrecisionUserLabelRule,
    SafeSearchNsfwAvatarImageUserLabelRule,
    SafeSearchNsfwBannerImageUserLabelRule,
    SafeSearchReadOnlyUserLabelRule,
    SafeSearchSearchBlacklistUserLabelRule,
    SafeSearchNsfwTetwittertUserLabelRule,
    SafeSearchSpamHighRecallUserLabelRule,
    SafeSearchDownrankSpamReplyAuthorLabelRule,
    SafeSearchNsfwTetwittertAuthorLabelRule,
    DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
    DropNsfwUserAuthorViewerOptInFilteringOnSearchRule,
  )
}

object SensitiveMediaSettingsTimelineHomeBaseRules {
  val policyRuleParams = Map[Rule, PolicyLevelRuleParams](
    NsfwHighPrecisionInterstitialAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaHomeTimelineRulesParam),
    GoreAndViolenceHighPrecisionAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaHomeTimelineRulesParam),
    NsfwReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaHomeTimelineRulesParam),
    GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaHomeTimelineRulesParam),
    NsfwCardImageAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam)
  )
}

object SensitiveMediaSettingsConversationBaseRules {
  val policyRuleParams = Map[Rule, PolicyLevelRuleParams](
    NsfwHighPrecisionInterstitialAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaConversationRulesParam),
    GoreAndViolenceHighPrecisionAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaConversationRulesParam),
    NsfwReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaConversationRulesParam),
    GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaConversationRulesParam),
    NsfwCardImageAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam)
  )
}

object SensitiveMediaSettingsProfileTimelineBaseRules {
  val policyRuleParams = Map[Rule, PolicyLevelRuleParams](
    NsfwHighPrecisionInterstitialAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaProfileTimelineRulesParam),
    GoreAndViolenceHighPrecisionAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaProfileTimelineRulesParam),
    NsfwReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaProfileTimelineRulesParam),
    GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaProfileTimelineRulesParam),
    NsfwCardImageAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam)
  )
}

object SensitiveMediaSettingsTweetDetailBaseRules {
  val policyRuleParams = Map[Rule, PolicyLevelRuleParams](
    NsfwHighPrecisionInterstitialAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaTweetDetailRulesParam),
    GoreAndViolenceHighPrecisionAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaTweetDetailRulesParam),
    NsfwReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaTweetDetailRulesParam),
    GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaTweetDetailRulesParam),
    NsfwCardImageAllUsersTweetLabelRule -> ruleParams(
      RuleParams.EnableLegacySensitiveMediaTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam),
    SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule -> ruleParams(
      RuleParams.EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam)
  )
}

case object ListSearchPolicy
    etwittertends VisibilityPolicy(
      userRules = ListSearchBaseRules.MinimalPolicyUserRules ++
        ListSearchBaseRules.BlockMutePolicyUserRules ++
        ListSearchBaseRules.StrictPolicyUserRules
    )

case object ListSubscriptionsPolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListMembershipsPolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object AllSubscribedListsPolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListHeaderPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object NewUserEtwitterperiencePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfaHighRecallTweetLabelRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        GoreAndViolenceTweetLabelRule,
        UntrustedUrlTweetLabelRule,
        DownrankSpamReplyTweetLabelRule,
        SearchBlacklistTweetLabelRule,
        AutomationTweetLabelRule,
        DuplicateMentionTweetLabelRule,
        BystanderAbusiveTweetLabelRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        SmyteSpamTweetLabelDropRule,
      ),
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        CompromisedRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        NsfwHighPrecisionRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        AbusiveHighRecallRule,
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object DESHomeTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        DropAllCommunityTweetsRule
      ) ++
        VisibilityPolicy.baseTweetRules,
      userRules = UserTimelineRules.UserRules
    )

case object DesQuoteTweetTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule
      ) ++ ElevatedQuoteTweetTimelinePolicy.tweetRules.diff(Seq(DropStaleTweetsRule)),
      userRules = Seq(
        ProtectedAuthorDropRule
      ),
      policyRuleParams = ElevatedQuoteTweetTimelinePolicy.policyRuleParams
    )

case object DESRealtimeSpamEnrichmentPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        LowQualityTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        SearchBlacklistTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
        DropAllCommunityTweetsRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
        GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
        NsfwReportedHeuristicsAllUsersTweetLabelRule,
        GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        NsfwCardImageAllUsersTweetLabelRule
      )
    )

case object DESRealtimePolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropAllCommunityTweetsRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        DropAllCollabInvitationTweetsRule
      ),
      userRules = Seq(
        DropAllProtectedAuthorRule,
        DropProtectedViewerIfPresentRule
      )
    )

case object DESRetweetingUsersPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ),
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object DESTweetLikingUsersPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ),
      userRules = TimelineLikedByRules.UserRules
    )

case object DESUserBookmarksPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++
        (VisibilityPolicy.baseTweetRules
          ++ Seq(DropAllCommunityTweetsRule)
          ++ TimelineProfileRules.tweetRules),
      userRules = UserTimelineRules.UserRules
    )

case object DESUserLikedTweetsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++
        (
          VisibilityPolicy.baseTweetRules ++
            Seq(
              DropAllCommunityTweetsRule,
              AbusePolicyEpisodicTweetLabelInterstitialRule,
              EmergencyDynamicInterstitialRule,
              ReportedTweetInterstitialRule,
              NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
              GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
              NsfwReportedHeuristicsAllUsersTweetLabelRule,
              GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
              NsfwCardImageAllUsersTweetLabelRule,
              NsfwHighPrecisionTweetLabelAvoidRule,
              NsfwHighRecallTweetLabelAvoidRule,
              GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
              NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
              GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
              NsfwCardImageAvoidAllUsersTweetLabelRule,
              DoNotAmplifyTweetLabelAvoidRule,
              NsfaHighPrecisionTweetLabelAvoidRule,
            ) ++ LimitedEngagementBaseRules.tweetRules
        ),
      userRules = UserTimelineRules.UserRules
    )

case object DESUserMentionsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropAllCommunityTweetsRule,
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
        NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
        GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
        NsfwReportedHeuristicsAllUsersTweetLabelRule,
        GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        NsfwCardImageAllUsersTweetLabelRule,
      ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        SuspendedAuthorRule
      )
    )

case object DESUserTweetsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++
        (VisibilityPolicy.baseTweetRules
          ++ Seq(DropAllCommunityTweetsRule)
          ++ TimelineProfileRules.tweetRules),
      userRules = UserTimelineRules.UserRules
    )

case object DevPlatformComplianceStreamPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        SpamAllUsersTweetLabelRule,
        PdnaAllUsersTweetLabelRule,
        BounceAllUsersTweetLabelRule,
        AbusePolicyEpisodicTweetLabelComplianceTweetNoticeRule,
      )
    )

case object DesTweetDetailPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++ BaseTweetDetailPolicy.tweetRules
    )

case object DevPlatformGetListTweetsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(DropStaleTweetsRule) ++ DesTweetDetailPolicy.tweetRules
    )

case object FollowerConnectionsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        SpammyFollowerRule
      )
    )

case object SuperFollowerConnectionsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        SpammyFollowerRule
      )
    )

case object LivePipelineEngagementCountsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object LiveVideoTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        AbusiveHighRecallTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        LiveLowQualityTweetLabelRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        SearchBlacklistTweetLabelRule,
        BystanderAbusiveTweetLabelRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        SmyteSpamTweetLabelDropRule,
        AbusePolicyEpisodicTweetLabelDropRule,
        EmergencyDropRule,
      ),
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        CompromisedRule,
        NsfwHighPrecisionRule,
        NsfwHighRecallRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        LiveLowQualityRule,
        EngagementSpammerRule,
        EngagementSpammerHighRecallRule,
        AbusiveHighRecallRule,
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object MagicRecsPolicyOverrides {
  val replacements: Map[Rule, Rule] = Map()
  def union(rules: Seq[Rule]*): Seq[Rule] = rules
    .map(ar => ar.map(twitter => replacements.getOrElse(twitter, twitter)))
    .reduce((a, b) => a ++ b.filterNot(a.contains))
}

case object MagicRecsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = MagicRecsPolicyOverrides.union(
        RecommendationsPolicy.tweetRules.filterNot(_ == SafetyCrisisLevel3DropRule),
        NotificationsIbisPolicy.tweetRules,
        Seq(
          NsfaHighRecallTweetLabelRule,
          NsfwHighRecallTweetLabelRule,
          NsfwTetwittertHighPrecisionTweetLabelDropRule),
        Seq(
          AuthorBlocksViewerDropRule,
          ViewerBlocksAuthorRule,
          ViewerMutesAuthorRule
        ),
        Seq(
          DeactivatedAuthorRule,
          SuspendedAuthorRule,
          TweetNsfwUserDropRule,
          TweetNsfwAdminDropRule
        )
      ),
      userRules = MagicRecsPolicyOverrides.union(
        RecommendationsPolicy.userRules,
        NotificationsRules.userRules
      )
    )

case object MagicRecsV2Policy
    etwittertends VisibilityPolicy(
      tweetRules = MagicRecsPolicyOverrides.union(
        MagicRecsPolicy.tweetRules,
        NotificationsWriterTweetHydratorPolicy.tweetRules
      ),
      userRules = MagicRecsPolicyOverrides.union(
        MagicRecsPolicy.userRules,
        NotificationsWriterV2Policy.userRules
      )
    )

case object MagicRecsAggressivePolicy
    etwittertends VisibilityPolicy(
      tweetRules = MagicRecsPolicy.tweetRules,
      userRules = MagicRecsPolicy.userRules
    )

case object MagicRecsAggressiveV2Policy
    etwittertends VisibilityPolicy(
      tweetRules = MagicRecsV2Policy.tweetRules,
      userRules = MagicRecsV2Policy.userRules
    )

case object MinimalPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        TsViolationRule
      )
    )

case object ModeratedTweetsTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules.diff(
        Seq(
          AuthorBlocksViewerDropRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule)),
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object MomentsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AuthorBlocksViewerUnspecifiedRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object NearbyTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = SearchBlenderRules.userBaseRules
    )

private object NotificationsRules {
  val tweetRules: Seq[Rule] =
    DropStaleTweetsRule +: VisibilityPolicy.baseTweetRules

  val userRules: Seq[Rule] = Seq(
    AbusiveRule,
    LowQualityRule,
    ReadOnlyRule,
    CompromisedRule,
    SpamHighRecallRule,
    DuplicateContentRule,
    AbusiveHighRecallRule,
    EngagementSpammerNonFollowerWithUqfRule,
    EngagementSpammerHighRecallNonFollowerWithUqfRule,
    DownrankSpamReplyNonFollowerWithUqfRule
  )
}

case object NotificationsIbisPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
          VisibilityPolicy.baseTweetRules ++ Seq(
          AbusiveUqfNonFollowerTweetLabelRule,
          LowQualityTweetLabelDropRule,
          TotwittericityReplyFilterDropNotificationRule,
          NsfwHighPrecisionTweetLabelRule,
          GoreAndViolenceHighPrecisionTweetLabelRule,
          NsfwReportedHeuristicsTweetLabelRule,
          GoreAndViolenceReportedHeuristicsTweetLabelRule,
          NsfwCardImageTweetLabelRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTetwittertTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          DuplicateMentionTweetLabelRule,
          LowQualityMentionTweetLabelRule,
          UntrustedUrlUqfNonFollowerTweetLabelRule,
          DownrankSpamReplyUqfNonFollowerTweetLabelRule,
          SafetyCrisisAnyLevelDropRule,
          DoNotAmplifyDropRule,
          SmyteSpamTweetLabelDropRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          EmergencyDropRule,
        ),
      userRules = NotificationsRules.userRules ++ Seq(
        DoNotAmplifyNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object NotificationsReadPolicy
    etwittertends VisibilityPolicy(
      tweetRules = NotificationsRules.tweetRules,
      userRules = NotificationsRules.userRules
    )

case object NotificationsTimelineDeviceFollowPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        CompromisedRule
      )
    )

case object NotificationsWritePolicy
    etwittertends VisibilityPolicy(
      tweetRules = NotificationsRules.tweetRules,
      userRules = NotificationsRules.userRules
    )

case object NotificationsWriterV2Policy
    etwittertends VisibilityPolicy(
      userRules =
        Seq(
          AuthorBlocksViewerDropRule,
          DeactivatedAuthorRule,
          ErasedAuthorRule,
          ProtectedAuthorDropRule,
          SuspendedAuthorRule,
          DeactivatedViewerRule,
          SuspendedViewerRule,
          ViewerBlocksAuthorRule,
          ViewerMutesAndDoesNotFollowAuthorRule,
          ViewerIsUnmentionedRule,
          NoConfirmedEmailRule,
          NoConfirmedPhoneRule,
          NoDefaultProfileImageRule,
          NoNewUsersRule,
          NoNotFollowedByRule,
          OnlyPeopleIFollowRule
        ) ++
          NotificationsRules.userRules
    )

case object NotificationsWriterTweetHydratorPolicy
    etwittertends VisibilityPolicy(
      tweetRules = NotificationsRules.tweetRules ++
        Seq(
          LowQualityTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          DuplicateMentionUqfTweetLabelRule,
          LowQualityMentionTweetLabelRule,
          SmyteSpamTweetLabelDropRule,
          TotwittericityReplyFilterDropNotificationRule,
          AbusiveUqfNonFollowerTweetLabelRule,
          UntrustedUrlUqfNonFollowerTweetLabelRule,
          DownrankSpamReplyUqfNonFollowerTweetLabelRule,
          ViewerHasMatchingMutedKeywordForNotificationsRule,
          NsfwCardImageAllUsersTweetLabelRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object NotificationsPlatformPolicy
    etwittertends VisibilityPolicy(
      tweetRules = NotificationsWriterTweetHydratorPolicy.tweetRules,
      userRules = NotificationsWriterV2Policy.userRules
    )

case object NotificationsPlatformPushPolicy
    etwittertends VisibilityPolicy(
      tweetRules = NotificationsIbisPolicy.tweetRules,
      userRules = Seq(ViewerMutesAuthorRule)
        ++ NotificationsIbisPolicy.userRules
    )

case object QuoteTweetTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropStaleTweetsRule,
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        GoreAndViolenceTweetLabelRule,
        UntrustedUrlTweetLabelRule,
        DownrankSpamReplyTweetLabelRule,
        SearchBlacklistTweetLabelRule,
        AutomationTweetLabelRule,
        DuplicateMentionTweetLabelRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        CompromisedRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        NsfwHighPrecisionRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        AbusiveHighRecallRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object ElevatedQuoteTweetTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules =
          TweetDetailPolicy.tweetRules.diff(
            Seq(
              MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
              ReportedTweetInterstitialRule)),
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object EmbedsPublicInterestNoticePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      )
    )

case object RecommendationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusiveTweetLabelRule,
          LowQualityTweetLabelDropRule,
          NsfwHighPrecisionTweetLabelRule,
          GoreAndViolenceHighPrecisionTweetLabelRule,
          NsfwReportedHeuristicsTweetLabelRule,
          GoreAndViolenceReportedHeuristicsTweetLabelRule,
          NsfwCardImageTweetLabelRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTetwittertTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          GoreAndViolenceTweetLabelRule,
          BystanderAbusiveTweetLabelRule,
          DoNotAmplifyDropRule,
          SafetyCrisisLevel3DropRule,
          SmyteSpamTweetLabelDropRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          EmergencyDropRule,
        ),
      userRules = Seq(
        DropNsfwAdminAuthorRule,
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        CompromisedRule,
        RecommendationsBlacklistRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        NsfwHighPrecisionRule,
        NsfwNearPerfectAuthorRule,
        NsfwBannerImageRule,
        NsfwAvatarImageRule,
        EngagementSpammerRule,
        EngagementSpammerHighRecallRule,
        AbusiveHighRecallRule,
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object RecosVideoPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
      ),
      userRules = Seq(NsfwTetwittertNonAuthorDropRule)
    )

case object RepliesGroupingPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          LowQualityTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          DeciderableSpamHighRecallAuthorLabelDropRule,
          SmyteSpamTweetLabelDropRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          NsfwHighPrecisionTweetLabelAvoidRule,
          NsfwHighRecallTweetLabelAvoidRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
          DoNotAmplifyTweetLabelAvoidRule,
          NsfaHighPrecisionTweetLabelAvoidRule,
        ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        LowQualityRule,
        ReadOnlyRule,
        LowQualityHighRecallRule,
        CompromisedRule,
        DeciderableSpamHighRecallRule
      )
    )

case object ReturningUserEtwitterperiencePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfaHighRecallTweetLabelRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        NsfwTetwittertHighPrecisionTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        GoreAndViolenceTweetLabelRule,
        UntrustedUrlTweetLabelRule,
        DownrankSpamReplyTweetLabelRule,
        SearchBlacklistTweetLabelRule,
        AutomationTweetLabelRule,
        DuplicateMentionTweetLabelRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        AbusePolicyEpisodicTweetLabelDropRule,
        EmergencyDropRule,
      ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        CompromisedRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        NsfwHighPrecisionRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        AbusiveHighRecallRule,
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTetwittertNonAuthorDropRule,
        DropNsfwUserAuthorRule,
        NsfwHighRecallRule
      )
    )

case object ReturningUserEtwitterperienceFocalTweetPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AuthorBlocksViewerDropRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ViewerMutesAuthorInterstitialRule,
          ReportedTweetInterstitialRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object RevenuePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusiveTweetLabelRule,
          BystanderAbusiveTweetLabelRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule
        )
    )

case object SafeSearchMinimalPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropOuterCommunityTweetsRule,
      ) ++ VisibilityPolicy.baseTweetRules ++ Seq(
        LowQualityTweetLabelDropRule,
        HighProactiveTosScoreTweetLabelDropSearchRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        SearchBlacklistTweetLabelRule,
        SearchBlacklistHighRecallTweetLabelDropRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        SmyteSpamTweetLabelDropRule,
      ) ++
        Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
        ++ SearchBlenderRules.tweetAvoidRules,
      userRules = Seq(
        LowQualityRule,
        ReadOnlyRule,
        CompromisedRule,
        SpamHighRecallRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        DuplicateContentRule,
        DoNotAmplifyNonFollowerRule,
        SearchLikelyIvsLabelNonFollowerDropUserRule
      )
    )

case object SearchHydrationPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
        ReportedTweetInterstitialSearchRule,
        NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
        GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
        NsfwReportedHeuristicsAllUsersTweetLabelRule,
        GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        NsfwCardImageAllUsersTweetLabelRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object SearchBlenderRules {
  val limitedEngagementBaseRules: Seq[Rule] = LimitedEngagementBaseRules.tweetRules

  val tweetAvoidRules: Seq[Rule] =
    Seq(
      NsfwHighPrecisionTweetLabelAvoidRule,
      NsfwHighRecallTweetLabelAvoidRule,
      GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
      NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
      GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
      NsfwCardImageAvoidAllUsersTweetLabelRule,
      SearchAvoidTweetNsfwAdminRule,
      SearchAvoidTweetNsfwUserRule,
      DoNotAmplifyTweetLabelAvoidRule,
      NsfaHighPrecisionTweetLabelAvoidRule,
    )

  val basicBlockMuteRules: Seq[Rule] = Seq(
    AuthorBlocksViewerDropRule,
    ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
    ViewerMutesAuthorViewerOptInBlockingOnSearchRule
  )

  val tweetRelevanceRules: Seq[Rule] =
    Seq(
      DropOuterCommunityTweetsRule,
      DropStaleTweetsRule,
    ) ++ VisibilityPolicy.baseTweetRules ++ Seq(
      SafeSearchAbusiveTweetLabelRule,
      LowQualityTweetLabelDropRule,
      HighProactiveTosScoreTweetLabelDropSearchRule,
      HighPSpammyTweetScoreSearchTweetLabelDropRule,
      HighSpammyTweetContentScoreSearchTopTweetLabelDropRule,
      HighSpammyTweetContentScoreTrendsTopTweetLabelDropRule,
      SafeSearchNsfwHighPrecisionTweetLabelRule,
      SafeSearchGoreAndViolenceHighPrecisionTweetLabelRule,
      SafeSearchNsfwReportedHeuristicsTweetLabelRule,
      SafeSearchGoreAndViolenceReportedHeuristicsTweetLabelRule,
      SafeSearchNsfwCardImageTweetLabelRule,
      SafeSearchNsfwHighRecallTweetLabelRule,
      SafeSearchNsfwVideoTweetLabelRule,
      SafeSearchNsfwTetwittertTweetLabelRule,
      SpamHighRecallTweetLabelDropRule,
      DuplicateContentTweetLabelDropRule,
      SafeSearchGoreAndViolenceTweetLabelRule,
      SafeSearchUntrustedUrlTweetLabelRule,
      SafeSearchDownrankSpamReplyTweetLabelRule,
      SearchBlacklistTweetLabelRule,
      SearchBlacklistHighRecallTweetLabelDropRule,
      SmyteSpamTweetLabelDropSearchRule,
      CopypastaSpamAllViewersSearchTweetLabelRule,
    ) ++ basicBlockMuteRules ++
      Seq(
        SafeSearchAutomationNonFollowerTweetLabelRule,
        SafeSearchDuplicateMentionNonFollowerTweetLabelRule,
        SafeSearchBystanderAbusiveTweetLabelRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        SearchIpiSafeSearchWithoutUserInQueryDropRule,
        SearchEdiSafeSearchWithoutUserInQueryDropRule,
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
        UnsafeSearchNsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
        UnsafeSearchGoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
        UnsafeSearchNsfwReportedHeuristicsAllUsersTweetLabelRule,
        UnsafeSearchGoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        UnsafeSearchNsfwCardImageAllUsersTweetLabelRule,
      ) ++
      limitedEngagementBaseRules ++
      tweetAvoidRules

    VisibilityPolicy.baseTweetRules ++ Seq(
    SafeSearchAbusiveTweetLabelRule,
    LowQualityTweetLabelDropRule,
    HighProactiveTosScoreTweetLabelDropSearchRule,
    HighSpammyTweetContentScoreSearchLatestTweetLabelDropRule,
    HighSpammyTweetContentScoreTrendsLatestTweetLabelDropRule,
    SafeSearchNsfwHighPrecisionTweetLabelRule,
    SafeSearchGoreAndViolenceHighPrecisionTweetLabelRule,
    SafeSearchNsfwReportedHeuristicsTweetLabelRule,
    SafeSearchGoreAndViolenceReportedHeuristicsTweetLabelRule,
    SafeSearchNsfwCardImageTweetLabelRule,
    SafeSearchNsfwHighRecallTweetLabelRule,
    SafeSearchNsfwVideoTweetLabelRule,
    SafeSearchNsfwTetwittertTweetLabelRule,
    SpamHighRecallTweetLabelDropRule,
    DuplicateContentTweetLabelDropRule,
    SafeSearchGoreAndViolenceTweetLabelRule,
    SafeSearchUntrustedUrlTweetLabelRule,
    SafeSearchDownrankSpamReplyTweetLabelRule,
    SearchBlacklistTweetLabelRule,
    SearchBlacklistHighRecallTweetLabelDropRule,
    SmyteSpamTweetLabelDropSearchRule,
    CopypastaSpamNonFollowerSearchTweetLabelRule,
  ) ++
    basicBlockMuteRules ++
    Seq(
      SafeSearchAutomationNonFollowerTweetLabelRule,
      SafeSearchDuplicateMentionNonFollowerTweetLabelRule,
      SafeSearchBystanderAbusiveTweetLabelRule,
      SafetyCrisisLevel3DropRule,
      SafetyCrisisLevel4DropRule,
      SearchIpiSafeSearchWithoutUserInQueryDropRule,
      SearchEdiSafeSearchWithoutUserInQueryDropRule,
      AbusePolicyEpisodicTweetLabelInterstitialRule,
      EmergencyDynamicInterstitialRule,
      UnsafeSearchNsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
      UnsafeSearchGoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
      UnsafeSearchNsfwReportedHeuristicsAllUsersTweetLabelRule,
      UnsafeSearchGoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
      UnsafeSearchNsfwCardImageAllUsersTweetLabelRule,
    ) ++ limitedEngagementBaseRules ++ tweetAvoidRules

  val userBaseRules: Seq[ConditionWithUserLabelRule] = Seq(
    SafeSearchAbusiveUserLabelRule,
    LowQualityRule,
    ReadOnlyRule,
    SearchBlacklistRule,
    CompromisedRule,
    SpamHighRecallRule,
    DuplicateContentRule,
    DoNotAmplifyNonFollowerRule,
    SearchLikelyIvsLabelNonFollowerDropUserRule,
    SafeSearchNsfwHighPrecisionUserLabelRule,
    SafeSearchNsfwAvatarImageUserLabelRule,
    SafeSearchNsfwBannerImageUserLabelRule,
    SafeSearchAbusiveHighRecallUserLabelRule,
    SafeSearchDownrankSpamReplyAuthorLabelRule,
    SafeSearchNotGraduatedNonFollowersUserLabelRule,
    SafeSearchNsfwTetwittertAuthorLabelRule
  )

  val userRules: Seq[ConditionWithUserLabelRule] = userBaseRules

  val userRelevanceBaseRules = userBaseRules ++ basicBlockMuteRules

  val userRelevanceRules = userRelevanceBaseRules

  val userRecencyBaseRules = userBaseRules.filterNot(
    Seq(DoNotAmplifyNonFollowerRule, SearchLikelyIvsLabelNonFollowerDropUserRule).contains
  ) ++ basicBlockMuteRules

  val searchQueryMatchesTweetAuthorRules: Seq[ConditionWithUserLabelRule] =
    userBaseRules

  val basicBlockMutePolicyRuleParam: Map[Rule, PolicyLevelRuleParams] =
    SearchBlenderRules.basicBlockMuteRules
      .map(rule => rule -> ruleParams(RuleParams.EnableSearchBasicBlockMuteRulesParam)).toMap
}

case object SearchBlenderUserRulesPolicy
    etwittertends VisibilityPolicy(
      userRules = SearchBlenderRules.userRules
    )

case object SearchLatestUserRulesPolicy
    etwittertends VisibilityPolicy(
      userRules = SearchLatestPolicy.userRules
    )

case object UserSearchSrpPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
        ViewerMutesAuthorViewerOptInBlockingOnSearchRule,
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        SafeSearchAbusiveUserLabelRule,
        SafeSearchHighRecallUserLabelRule,
        SafeSearchNsfwNearPerfectAuthorRule,
        SafeSearchNsfwHighPrecisionUserLabelRule,
        SafeSearchNsfwAvatarImageUserLabelRule,
        SafeSearchNsfwBannerImageUserLabelRule,
        SafeSearchAbusiveHighRecallUserLabelRule,
        SafeSearchNsfwTetwittertAuthorLabelRule
      )
    )

case object UserSearchTypeaheadPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        SafeSearchAbusiveUserLabelRule,
        SafeSearchHighRecallUserLabelRule,
        SafeSearchNsfwNearPerfectAuthorRule,
        SafeSearchNsfwHighPrecisionUserLabelRule,
        SafeSearchNsfwAvatarImageUserLabelRule,
        SafeSearchNsfwBannerImageUserLabelRule,
        SafeSearchAbusiveHighRecallUserLabelRule,
        SafeSearchNsfwTetwittertAuthorLabelRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object SearchMitwittererSrpMinimalPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
        ViewerMutesAuthorViewerOptInBlockingOnSearchRule
      )
    )

case object SearchMitwittererSrpStrictPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
        ViewerMutesAuthorViewerOptInBlockingOnSearchRule,
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        NsfwNearPerfectAuthorRule,
        NsfwHighPrecisionRule,
        NsfwHighRecallRule,
        NsfwSensitiveRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule
      ) ++ SearchBlenderRules.searchQueryMatchesTweetAuthorRules
        .diff(Seq(SafeSearchNotGraduatedNonFollowersUserLabelRule))
    )

case object SearchPeopleSrpPolicy
    etwittertends VisibilityPolicy(
      userRules = SearchBlenderRules.searchQueryMatchesTweetAuthorRules
    )

case object SearchPeopleTypeaheadPolicy
    etwittertends VisibilityPolicy(
      userRules = SearchBlenderRules.searchQueryMatchesTweetAuthorRules
        .diff(
          Seq(
            SafeSearchNotGraduatedNonFollowersUserLabelRule
          )),
      tweetRules = Seq(DropAllRule)
    )

case object SearchPhotoPolicy
    etwittertends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = SearchBlenderRules.userRelevanceRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchTrendTakeoverPromotedTweetPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object SearchVideoPolicy
    etwittertends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = SearchBlenderRules.userRelevanceRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchLatestPolicy
    etwittertends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRecencyRules,
      userRules = SearchBlenderRules.userRecencyBaseRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchTopPolicy
    etwittertends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = Seq(SpammyUserModelHighPrecisionDropTweetRule) ++
        SearchBlenderRules.basicBlockMuteRules ++
        SearchBlenderRules.searchQueryMatchesTweetAuthorRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchTopQigPolicy
    etwittertends VisibilityPolicy(
      tweetRules = BaseQigPolicy.tweetRules ++
        Seq(
          UnsafeSearchGoreAndViolenceHighPrecisionAllUsersTweetLabelDropRule,
          UnsafeSearchGoreAndViolenceReportedHeuristicsAllUsersTweetLabelDropRule,
          UnsafeSearchNsfwCardImageAllUsersTweetLabelDropRule,
          UnsafeSearchNsfwReportedHeuristicsAllUsersTweetLabelDropRule,
          UnsafeSearchNsfwHighPrecisionAllUsersTweetLabelDropRule
        ) ++
        SearchTopPolicy.tweetRules.diff(
          Seq(
            SearchIpiSafeSearchWithoutUserInQueryDropRule,
            SearchEdiSafeSearchWithoutUserInQueryDropRule,
            HighSpammyTweetContentScoreTrendsTopTweetLabelDropRule,
            UnsafeSearchNsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            UnsafeSearchGoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            UnsafeSearchGoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            UnsafeSearchNsfwCardImageAllUsersTweetLabelRule,
            UnsafeSearchNsfwReportedHeuristicsAllUsersTweetLabelRule
          ) ++
            SearchTopPolicy.tweetRules.intersect(BaseQigPolicy.tweetRules)),
      userRules = BaseQigPolicy.userRules ++ Seq(
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        NsfwNearPerfectAuthorRule,
      ) ++ SearchTopPolicy.userRules.diff(
        SearchTopPolicy.userRules.intersect(BaseQigPolicy.userRules)),
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SafeSearchStrictPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropOuterCommunityTweetsRule,
      ) ++ VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        HighProactiveTosScoreTweetLabelDropSearchRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        GoreAndViolenceTweetLabelRule,
        UntrustedUrlTweetLabelRule,
        DownrankSpamReplyTweetLabelRule,
        SearchBlacklistTweetLabelRule,
        SearchBlacklistHighRecallTweetLabelDropRule,
        AutomationTweetLabelRule,
        DuplicateMentionTweetLabelRule,
        BystanderAbusiveTweetLabelRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        SmyteSpamTweetLabelDropRule,
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
        ++ SearchBlenderRules.tweetAvoidRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        CompromisedRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        NsfwHighPrecisionRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        AbusiveHighRecallRule,
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        SearchLikelyIvsLabelNonFollowerDropUserRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTetwittertNonAuthorDropRule,
      )
    )

case object StickersTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        CompromisedRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        DuplicateContentRule,
        EngagementSpammerRule,
        EngagementSpammerHighRecallRule,
        NsfwSensitiveRule,
        SpamHighRecallRule,
        AbusiveHighRecallRule
      )
    )

case object StratoEtwittertLimitedEngagementsPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        VisibilityPolicy.baseTweetRules ++ LimitedEngagementBaseRules.tweetRules
    )

case object InternalPromotedContentPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object StreamServicesPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule
      ),
      userRules = Seq(NsfwTetwittertNonAuthorDropRule)
    )

case object SuperLikePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusePolicyEpisodicTweetLabelDropRule,
        EmergencyDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule
      ),
      userRules = Seq(NsfwTetwittertNonAuthorDropRule)
    )

case object TimelineFocalTweetPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object TimelineBookmarkPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        Seq(
          DropCommunityTweetsRule,
          DropCommunityTweetCommunityNotVisibleRule,
          DropProtectedCommunityTweetsRule,
          DropHiddenCommunityTweetsRule,
          DropAuthorRemovedCommunityTweetsRule,
          SpamTweetLabelRule,
          PdnaTweetLabelRule,
          BounceOuterTweetTombstoneRule,
          BounceQuotedTweetTombstoneRule,
          DropEtwitterclusiveTweetContentRule,
          DropTrustedFriendsTweetContentRule,
        ) ++
          Seq(
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            ViewerBlocksAuthorInnerQuotedTweetInterstitialRule,
            ViewerMutesAuthorInnerQuotedTweetInterstitialRule,
            NsfwCardImageAllUsersTweetLabelRule,
          ) ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneBounceDeletedTweetRule,
        TombstoneDeletedQuotedTweetRule
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
    )

case object TimelineListsPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        Seq(
          DropOuterCommunityTweetsRule,
          DropStaleTweetsRule,
        ) ++
          VisibilityPolicy.baseTweetRules ++
          Seq(
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            NsfwCardImageAllUsersTweetLabelRule,
            NsfwHighPrecisionTweetLabelAvoidRule,
            NsfwHighRecallTweetLabelAvoidRule,
            GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
            NsfwCardImageAvoidAllUsersTweetLabelRule,
            DoNotAmplifyTweetLabelAvoidRule,
            NsfaHighPrecisionTweetLabelAvoidRule,
          ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object TimelineFavoritesPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        Seq(
          DropOuterCommunityTweetsRule,
          DropStaleTweetsRule,
        )
          ++ TimelineProfileRules.baseTweetRules
          ++ Seq(
            DynamicProductAdDropTweetLabelRule,
            NsfwHighPrecisionTombstoneInnerQuotedTweetLabelRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
            SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            ReportedTweetInterstitialRule,
            ViewerMutesAuthorInterstitialRule,
            ViewerBlocksAuthorInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            NsfwCardImageAllUsersTweetLabelRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
            SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
            NsfwHighPrecisionTweetLabelAvoidRule,
            NsfwHighRecallTweetLabelAvoidRule,
            GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
            NsfwCardImageAvoidAllUsersTweetLabelRule,
            DoNotAmplifyTweetLabelAvoidRule,
            NsfaHighPrecisionTweetLabelAvoidRule,
          ) ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableInnerQuotedTweetTombstoneRule,
        DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule,
        OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule,
        ErasedUserUnavailableInnerQuotedTweetTombstoneRule,
        ProtectedUserUnavailableInnerQuotedTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = SensitiveMediaSettingsProfileTimelineBaseRules.policyRuleParams
    )

case object ProfileMitwittererFavoritesPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropEtwitterclusiveTweetContentRule,
        DropOuterCommunityTweetsRule,
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      )
    )

case object TimelineMediaPolicy
    etwittertends VisibilityPolicy(
        TimelineProfileRules.baseTweetRules
        ++ Seq(
          NsfwHighPrecisionTombstoneInnerQuotedTweetLabelRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          ReportedTweetInterstitialRule,
          ViewerMutesAuthorInnerQuotedTweetInterstitialRule,
          ViewerBlocksAuthorInnerQuotedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          NsfwHighPrecisionTweetLabelAvoidRule,
          NsfwHighRecallTweetLabelAvoidRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
          NsfwCardImageAvoidAllUsersTweetLabelRule,
          DoNotAmplifyTweetLabelAvoidRule,
          NsfaHighPrecisionTweetLabelAvoidRule,
        ) ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableInnerQuotedTweetTombstoneRule,
        DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule,
        OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule,
        ErasedUserUnavailableInnerQuotedTweetTombstoneRule,
        ProtectedUserUnavailableInnerQuotedTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = SensitiveMediaSettingsProfileTimelineBaseRules.policyRuleParams
    )

case object ProfileMitwittererMediaPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropEtwitterclusiveTweetContentRule
      ),
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      )
    )

object TimelineProfileRules {

  val baseTweetRules: Seq[Rule] = Seq(
    TombstoneCommunityTweetsRule,
    TombstoneCommunityTweetCommunityNotVisibleRule,
    TombstoneProtectedCommunityTweetsRule,
    TombstoneHiddenCommunityTweetsRule,
    TombstoneAuthorRemovedCommunityTweetsRule,
    SpamQuotedTweetLabelTombstoneRule,
    SpamTweetLabelRule,
    PdnaQuotedTweetLabelTombstoneRule,
    PdnaTweetLabelRule,
    BounceTweetLabelTombstoneRule,
    TombstoneEtwitterclusiveQuotedTweetContentRule,
    DropEtwitterclusiveTweetContentRule,
    DropTrustedFriendsTweetContentRule
  )

  val tweetRules: Seq[Rule] =
    Seq(
      DynamicProductAdDropTweetLabelRule,
      AbusePolicyEpisodicTweetLabelInterstitialRule,
      EmergencyDynamicInterstitialRule,
      ReportedTweetInterstitialRule,
      NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
      GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
      NsfwReportedHeuristicsAllUsersTweetLabelRule,
      GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
      NsfwCardImageAllUsersTweetLabelRule,
      NsfwHighPrecisionTweetLabelAvoidRule,
      NsfwHighRecallTweetLabelAvoidRule,
      GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
      NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
      GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
      NsfwCardImageAvoidAllUsersTweetLabelRule,
      NsfwTetwittertTweetLabelAvoidRule,
      DoNotAmplifyTweetLabelAvoidRule,
      NsfaHighPrecisionTweetLabelAvoidRule,
    ) ++ LimitedEngagementBaseRules.tweetRules

  val tweetTombstoneRules: Seq[Rule] =
    Seq(
      DynamicProductAdDropTweetLabelRule,
      NsfwHighPrecisionInnerQuotedTweetLabelRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
      SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
      AbusePolicyEpisodicTweetLabelInterstitialRule,
      EmergencyDynamicInterstitialRule,
      ReportedTweetInterstitialRule,
      ViewerMutesAuthorInnerQuotedTweetInterstitialRule,
      ViewerBlocksAuthorInnerQuotedTweetInterstitialRule,
      NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
      GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
      NsfwReportedHeuristicsAllUsersTweetLabelRule,
      GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
      NsfwCardImageAllUsersTweetLabelRule,
      SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
      SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
      SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
      SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
      SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
      SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
      SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
      NsfwHighPrecisionTweetLabelAvoidRule,
      NsfwHighRecallTweetLabelAvoidRule,
      GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
      NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
      GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
      NsfwCardImageAvoidAllUsersTweetLabelRule,
      DoNotAmplifyTweetLabelAvoidRule,
      NsfaHighPrecisionTweetLabelAvoidRule,
    ) ++ LimitedEngagementBaseRules.tweetRules
}

case object TimelineProfilePolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        Seq(
          DropOuterCommunityTweetsRule,
          DropStaleTweetsRule,
        )
          ++ TimelineProfileRules.baseTweetRules
          ++ TimelineProfileRules.tweetTombstoneRules,
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableInnerQuotedTweetTombstoneRule,
        DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule,
        OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule,
        ErasedUserUnavailableInnerQuotedTweetTombstoneRule,
        ProtectedUserUnavailableInnerQuotedTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = SensitiveMediaSettingsProfileTimelineBaseRules.policyRuleParams
    )

case object TimelineProfileAllPolicy
    etwittertends VisibilityPolicy(
        TimelineProfileRules.baseTweetRules
        ++ TimelineProfileRules.tweetTombstoneRules,
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableInnerQuotedTweetTombstoneRule,
        DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule,
        OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule,
        ErasedUserUnavailableInnerQuotedTweetTombstoneRule,
        ProtectedUserUnavailableInnerQuotedTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = SensitiveMediaSettingsProfileTimelineBaseRules.policyRuleParams
    )

case object TimelineProfileSuperFollowsPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        Seq(
          DropOuterCommunityTweetsRule
        ) ++
          VisibilityPolicy.baseTweetRules ++
          TimelineProfileRules.tweetRules
    )

case object TimelineReactiveBlendingPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          ViewerHasMatchingMutedKeywordForHomeTimelineRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object TimelineHomePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseQuotedTweetTombstoneRules ++
        VisibilityPolicy.baseTweetRules ++
        Seq(
          NullcastedTweetRule,
          DropOuterCommunityTweetsRule,
          DynamicProductAdDropTweetLabelRule,
          MutedRetweetsRule,
          DropAllAuthorRemovedCommunityTweetsRule,
          DropAllHiddenCommunityTweetsRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          EmergencyDropRule,
          SafetyCrisisLevel4DropRule,
          ViewerHasMatchingMutedKeywordForHomeTimelineRule,
          SensitiveMediaTweetDropRules.AdultMediaNsfwHighPrecisionTweetLabelDropRule,
          SensitiveMediaTweetDropRules.ViolentMediaGoreAndViolenceHighPrecisionDropRule,
          SensitiveMediaTweetDropRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropRule,
          SensitiveMediaTweetDropRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropRule,
          SensitiveMediaTweetDropRules.AdultMediaNsfwCardImageTweetLabelDropRule,
          SensitiveMediaTweetDropRules.OtherSensitiveMediaNsfwUserTweetFlagDropRule,
          SensitiveMediaTweetDropRules.OtherSensitiveMediaNsfwAdminTweetFlagDropRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          NsfwHighPrecisionTweetLabelAvoidRule,
          NsfwHighRecallTweetLabelAvoidRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
          NsfwCardImageAvoidAllUsersTweetLabelRule,
          DoNotAmplifyTweetLabelAvoidRule,
          NsfaHighPrecisionTweetLabelAvoidRule,
        )
        ++
          LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        ViewerMutesAuthorRule,
        ViewerBlocksAuthorRule,
        DeciderableAuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        DeactivatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        DropTakendownUserRule
      ),
      policyRuleParams = SensitiveMediaSettingsTimelineHomeBaseRules.policyRuleParams
    )

case object BaseTimelineHomePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseQuotedTweetTombstoneRules ++
        VisibilityPolicy.baseTweetRules ++
        Seq(
          NullcastedTweetRule,
          DropOuterCommunityTweetsRule,
          DynamicProductAdDropTweetLabelRule,
          MutedRetweetsRule,
          DropAllAuthorRemovedCommunityTweetsRule,
          DropAllHiddenCommunityTweetsRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          EmergencyDropRule,
          SafetyCrisisLevel4DropRule,
          ViewerHasMatchingMutedKeywordForHomeTimelineRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          NsfwHighPrecisionTweetLabelAvoidRule,
          NsfwHighRecallTweetLabelAvoidRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
          NsfwCardImageAvoidAllUsersTweetLabelRule,
          DoNotAmplifyTweetLabelAvoidRule,
          NsfaHighPrecisionTweetLabelAvoidRule,
        )
        ++
          LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        ViewerMutesAuthorRule,
        ViewerBlocksAuthorRule,
        DeciderableAuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        DeactivatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        DropTakendownUserRule
      )
    )

case object TimelineHomeHydrationPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
          VisibilityPolicy.baseQuotedTweetTombstoneRules ++
          VisibilityPolicy.baseTweetRules ++
          Seq(
            SensitiveMediaTweetDropRules.AdultMediaNsfwHighPrecisionTweetLabelDropRule,
            SensitiveMediaTweetDropRules.ViolentMediaGoreAndViolenceHighPrecisionDropRule,
            SensitiveMediaTweetDropRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropRule,
            SensitiveMediaTweetDropRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropRule,
            SensitiveMediaTweetDropRules.AdultMediaNsfwCardImageTweetLabelDropRule,
            SensitiveMediaTweetDropRules.OtherSensitiveMediaNsfwUserTweetFlagDropRule,
            SensitiveMediaTweetDropRules.OtherSensitiveMediaNsfwAdminTweetFlagDropRule,
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            NsfwCardImageAllUsersTweetLabelRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
            SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
            NsfaHighPrecisionTweetLabelAvoidRule,
            NsfwHighPrecisionTweetLabelAvoidRule,
            NsfwHighRecallTweetLabelAvoidRule,
          ) ++ LimitedEngagementBaseRules.tweetRules,
      policyRuleParams = SensitiveMediaSettingsTimelineHomeBaseRules.policyRuleParams
    )

case object TimelineHomeLatestPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
          VisibilityPolicy.baseQuotedTweetTombstoneRules ++
          VisibilityPolicy.baseTweetRules ++
          Seq(
            NullcastedTweetRule,
            DropOuterCommunityTweetsRule,
            DynamicProductAdDropTweetLabelRule,
            MutedRetweetsRule,
            ViewerHasMatchingMutedKeywordForHomeTimelineRule,
            SensitiveMediaTweetDropRules.AdultMediaNsfwHighPrecisionTweetLabelDropRule,
            SensitiveMediaTweetDropRules.ViolentMediaGoreAndViolenceHighPrecisionDropRule,
            SensitiveMediaTweetDropRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropRule,
            SensitiveMediaTweetDropRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropRule,
            SensitiveMediaTweetDropRules.AdultMediaNsfwCardImageTweetLabelDropRule,
            SensitiveMediaTweetDropRules.OtherSensitiveMediaNsfwUserTweetFlagDropRule,
            SensitiveMediaTweetDropRules.OtherSensitiveMediaNsfwAdminTweetFlagDropRule,
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            NsfwCardImageAllUsersTweetLabelRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
            SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
            SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
            SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
            NsfwHighPrecisionTweetLabelAvoidRule,
            NsfwHighRecallTweetLabelAvoidRule,
            GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule,
            NsfwCardImageAvoidAllUsersTweetLabelRule,
            DoNotAmplifyTweetLabelAvoidRule,
            NsfaHighPrecisionTweetLabelAvoidRule,
          )
          ++
            LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        ViewerMutesAuthorRule,
        ViewerBlocksAuthorRule,
        DeciderableAuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        DeactivatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        DropTakendownUserRule
      ),
      policyRuleParams = SensitiveMediaSettingsTimelineHomeBaseRules.policyRuleParams
    )

case object TimelineModeratedTweetsHydrationPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object SignalsReactionsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AuthorBlocksViewerDropRule
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object SignalsTweetReactingUsersPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTetwittertAllUsersTweetLabelDropRule,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule,
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object SocialProofPolicy
    etwittertends VisibilityPolicy(
      tweetRules = FilterDefaultPolicy.tweetRules,
      userRules = Seq(
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule
      )
    )

case object TimelineLikedByPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTetwittertAllUsersTweetLabelDropRule,
      userRules = TimelineLikedByRules.UserRules :+ NsfwTetwittertNonAuthorDropRule
    )

case object TimelineRetweetedByPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTetwittertAllUsersTweetLabelDropRule,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object TimelineSuperLikedByPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTetwittertAllUsersTweetLabelDropRule,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object TimelineContentControlsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = TopicsLandingPageTopicRecommendationsPolicy.tweetRules,
      userRules = TopicsLandingPageTopicRecommendationsPolicy.userRules
    )

case object TimelineConversationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusiveNonFollowerTweetLabelRule,
          LowQualityTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          BystanderAbusiveNonFollowerTweetLabelRule,
          UntrustedUrlAllViewersTweetLabelRule,
          DownrankSpamReplyAllViewersTweetLabelRule,
          SmyteSpamTweetLabelDropRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          AbusiveHighRecallNonFollowerTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        LowQualityHighRecallRule,
        CompromisedRule,
        SpamHighRecallRule,
        AbusiveHighRecallRule,
        DownrankSpamReplyAllViewersRule,
      ),
      policyRuleParams = SensitiveMediaSettingsConversationBaseRules.policyRuleParams
    )

case object TimelineFollowingActivityPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusiveTweetLabelRule,
          BystanderAbusiveTweetLabelRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object TimelineInjectionPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SafetyCrisisLevel2DropRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        DoNotAmplifyDropRule,
        HighProactiveTosScoreTweetLabelDropRule
      ),
      userRules = Seq(
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        NsfwTetwittertNonAuthorDropRule
      )
    )

case object TimelineMentionsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          LowQualityTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          DuplicateMentionUqfTweetLabelRule,
          LowQualityMentionTweetLabelRule,
          SmyteSpamTweetLabelDropRule,
          TotwittericityReplyFilterDropNotificationRule,
          AbusiveUqfNonFollowerTweetLabelRule,
          UntrustedUrlUqfNonFollowerTweetLabelRule,
          DownrankSpamReplyUqfNonFollowerTweetLabelRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        CompromisedRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        AbusiveHighRecallRule,
        EngagementSpammerNonFollowerWithUqfRule,
        EngagementSpammerHighRecallNonFollowerWithUqfRule,
        DownrankSpamReplyNonFollowerWithUqfRule
      )
    )

case object TweetEngagersPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule
      )
    )

case object TweetWritesApiPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object QuotedTweetRulesPolicy
    etwittertends VisibilityPolicy(
      quotedTweetRules = Seq(
        DeactivatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        SuspendedAuthorRule,
        AuthorBlocksOuterAuthorRule,
        ViewerBlocksAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerMutesAndDoesNotFollowAuthorRule,
        ProtectedQuoteTweetAuthorRule
      )
    )

case object TweetDetailPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AuthorBlocksViewerDropRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          NsfwHighPrecisionTweetLabelAvoidRule,
          NsfwHighRecallTweetLabelAvoidRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
          DoNotAmplifyTweetLabelAvoidRule,
          NsfaHighPrecisionTweetLabelAvoidRule,
          MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules,
      policyRuleParams = SensitiveMediaSettingsTweetDetailBaseRules.policyRuleParams
    )

case object BaseTweetDetailPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AuthorBlocksViewerDropRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          NsfwHighPrecisionTweetLabelAvoidRule,
          NsfwHighRecallTweetLabelAvoidRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
          DoNotAmplifyTweetLabelAvoidRule,
          NsfaHighPrecisionTweetLabelAvoidRule,
          MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules
    )

case object TweetDetailWithInjectionsHydrationPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
          ReportedTweetInterstitialRule,
        ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = UserTimelineRules.UserRules
    )

case object TweetDetailNonTooPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropAllEtwitterclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++ BaseTweetDetailPolicy.tweetRules
    )

case object RecosWritePathPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule
      ),
      userRules = Seq(NsfwTetwittertNonAuthorDropRule)
    )

case object BrandSafetyPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        NsfwVideoTweetLabelDropRule,
        NsfwTetwittertTweetLabelDropRule,
        NsfaHighRecallTweetLabelInterstitialRule
      ),
      userRules = Seq(NsfwTetwittertNonAuthorDropRule)
    )

case object VideoAdsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object AppealsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          NsfwCardImageAllUsersTweetLabelRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        )
    )

case object TimelineConversationsDownrankingPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        HighTotwittericityScoreDownrankAbusiveQualitySectionRule,
        UntrustedUrlConversationsTweetLabelRule,
        DownrankSpamReplyConversationsTweetLabelRule,
        DownrankSpamReplyConversationsAuthorLabelRule,
        HighProactiveTosScoreTweetLabelDownrankingRule,
        SafetyCrisisLevel3SectionRule,
        SafetyCrisisLevel4SectionRule,
        DoNotAmplifySectionRule,
        DoNotAmplifySectionUserRule,
        NotGraduatedConversationsAuthorLabelRule,
        HighSpammyTweetContentScoreConvoDownrankAbusiveQualityRule,
        HighCryptospamScoreConvoDownrankAbusiveQualityRule,
        CopypastaSpamAbusiveQualityTweetLabelRule,
        HighTotwittericityScoreDownrankLowQualitySectionRule,
        HighPSpammyTweetScoreDownrankLowQualitySectionRule,
        RitoActionedTweetDownrankLowQualitySectionRule,
        HighTotwittericityScoreDownrankHighQualitySectionRule,
      )
    )

case object TimelineConversationsDownrankingMinimalPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        HighProactiveTosScoreTweetLabelDownrankingRule
      )
    )

case object TimelineHomeRecommendationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.union(
        RecommendationsPolicy.tweetRules.filter(
          _ != NsfwHighPrecisionTweetLabelRule
        ),
        Seq(
          SafetyCrisisLevel2DropRule,
          SafetyCrisisLevel3DropRule,
          SafetyCrisisLevel4DropRule,
          HighProactiveTosScoreTweetLabelDropRule,
          NsfwHighRecallTweetLabelRule,
        ),
        BaseTimelineHomePolicy.tweetRules,
      ),
      userRules = VisibilityPolicy.union(
        RecommendationsPolicy.userRules,
        BaseTimelineHomePolicy.userRules
      )
    )

case object TimelineHomeTopicFollowRecommendationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.union(
        Seq(
          SearchBlacklistTweetLabelRule,
          GoreAndViolenceTopicHighRecallTweetLabelRule,
          NsfwHighRecallTweetLabelRule,
        ),
        RecommendationsPolicy.tweetRules
          .filterNot(
            Seq(
              NsfwHighPrecisionTweetLabelRule,
            ).contains),
        BaseTimelineHomePolicy.tweetRules
      ),
      userRules = VisibilityPolicy.union(
        RecommendationsPolicy.userRules,
        BaseTimelineHomePolicy.userRules
      )
    )

case object TimelineScorerPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AllowAllRule
      )
    )

case object FollowedTopicsTimelinePolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object TopicsLandingPageTopicRecommendationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.union(
        Seq(
          SearchBlacklistTweetLabelRule,
          GoreAndViolenceTopicHighRecallTweetLabelRule,
          NsfwHighRecallTweetLabelRule
        ),
        RecommendationsPolicy.tweetRules,
        BaseTimelineHomePolicy.tweetRules,
      ),
      userRules = VisibilityPolicy.union(
        RecommendationsPolicy.userRules,
        BaseTimelineHomePolicy.userRules
      ) ++ Seq(
        AuthorBlocksViewerDropRule
      )
    )

case object EtwitterploreRecommendationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropOuterCommunityTweetsRule,
        SearchBlacklistTweetLabelRule,
        GoreAndViolenceTopicHighRecallTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        DropTweetsWithGeoRestrictedMediaRule,
        TweetNsfwUserDropRule,
        TweetNsfwAdminDropRule,
        ViewerHasMatchingMutedKeywordForHomeTimelineRule,
        ViewerHasMatchingMutedKeywordForNotificationsRule,
      ) ++ VisibilityPolicy.union(
        RecommendationsPolicy.tweetRules
      ),
      userRules = VisibilityPolicy.union(
        RecommendationsPolicy.userRules
      ) ++ Seq(
        AuthorBlocksViewerDropRule,
        ViewerMutesAuthorRule,
        ViewerBlocksAuthorRule
      )
    )

case object TombstoningPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        TombstoneIf.ViewerIsBlockedByAuthor,
        TombstoneIf.AuthorIsProtected,
        TombstoneIf.ReplyIsModeratedByRootAuthor,
        TombstoneIf.AuthorIsSuspended,
        TombstoneIf.AuthorIsDeactivated,
        InterstitialIf.ViewerHardMutedAuthor
      )
    )

case object TweetReplyNudgePolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        SpamAllUsersTweetLabelRule,
        PdnaAllUsersTweetLabelRule,
        BounceAllUsersTweetLabelRule,
        TweetNsfwAdminDropRule,
        TweetNsfwUserDropRule,
        NsfwHighRecallAllUsersTweetLabelDropRule,
        NsfwHighPrecisionAllUsersTweetLabelDropRule,
        GoreAndViolenceHighPrecisionAllUsersTweetLabelDropRule,
        NsfwReportedHeuristicsAllUsersTweetLabelDropRule,
        GoreAndViolenceReportedHeuristicsAllUsersTweetLabelDropRule,
        NsfwCardImageAllUsersTweetLabelDropRule,
        NsfwVideoAllUsersTweetLabelDropRule,
        NsfwTetwittertAllUsersTweetLabelDropRule,
      ),
      userRules = Seq(
        DropNsfwUserAuthorRule,
        DropNsfwAdminAuthorRule,
        NsfwTetwittertAllUsersDropRule
      )
    )

case object HumanizationNudgePolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object TrendsRepresentativeTweetPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.union(
        RecommendationsPolicy.tweetRules,
        Seq(
          AbusiveHighRecallTweetLabelRule,
          BystanderAbusiveTweetLabelRule,
          DuplicateContentTweetLabelDropRule,
          LowQualityTweetLabelDropRule,
          HighProactiveTosScoreTweetLabelDropRule,
          NsfaHighRecallTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelDropRule,
          NsfwHighPrecisionTweetLabelRule,
          NsfwHighRecallAllUsersTweetLabelDropRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTetwittertTweetLabelDropRule,
          PdnaAllUsersTweetLabelRule,
          SearchBlacklistTweetLabelRule,
          SpamHighRecallTweetLabelDropRule,
          UntrustedUrlAllViewersTweetLabelRule,
          DownrankSpamReplyAllViewersTweetLabelRule,
          HighPSpammyScoreAllViewerDropRule,
          DoNotAmplifyAllViewersDropRule,
          SmyteSpamTweetLabelDropRule,
          AuthorBlocksViewerDropRule,
          ViewerBlocksAuthorRule,
          ViewerMutesAuthorRule,
          CopypastaSpamAllViewersTweetLabelRule,
        )
      ),
      userRules = VisibilityPolicy.union(
        RecommendationsPolicy.userRules,
        Seq(
          AbusiveRule,
          LowQualityRule,
          ReadOnlyRule,
          CompromisedRule,
          RecommendationsBlacklistRule,
          SpamHighRecallRule,
          DuplicateContentRule,
          NsfwHighPrecisionRule,
          NsfwNearPerfectAuthorRule,
          NsfwBannerImageRule,
          NsfwAvatarImageRule,
          EngagementSpammerRule,
          EngagementSpammerHighRecallRule,
          AbusiveHighRecallRule,
          SearchBlacklistRule,
          SearchNsfwTetwittertRule,
          NsfwHighRecallRule,
          TsViolationRule,
          DownrankSpamReplyAllViewersRule,
          NsfwTetwittertNonAuthorDropRule
        )
      )
    )

case object AdsCampaignPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(SuspendedAuthorRule),
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object AdsManagerPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AdsManagerDenyListAllUsersTweetLabelRule,
      )
    )

case object AdsReportingDashboardPolicy
    etwittertends VisibilityPolicy(
      tweetRules = AdsManagerPolicy.tweetRules,
      userRules = AdsCampaignPolicy.userRules
    )

case object BirdwatchNoteAuthorPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        SuspendedAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAuthorRule
      )
    )

case object BirdwatchNoteTweetsTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          MutedRetweetsRule,
          AuthorBlocksViewerDropRule,
          ViewerMutesAuthorRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object BirdwatchNeedsYourHelpNotificationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AuthorBlocksViewerDropRule,
          ViewerBlocksAuthorRule,
          ViewerMutesAuthorRule,
          ViewerHasMatchingMutedKeywordForHomeTimelineRule,
          ViewerHasMatchingMutedKeywordForNotificationsRule,
        )
    )

case object ForDevelopmentOnlyPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object UserProfileHeaderPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules = Seq(DropAllRule)
    )

case object UserScopedTimelinePolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules,
      tweetRules = Seq(DropAllRule)
    )

case object TweetScopedTimelinePolicy
    etwittertends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules,
      tweetRules = Seq.empty
    )

case object SoftInterventionPivotPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object CuratedTrendsRepresentativeTweetPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        SuspendedAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAndDoesNotFollowAuthorRule
      )
    )

case object CommunitiesPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          RetweetDropRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          EmergencyDropRule,
          SafetyCrisisLevel4DropRule,
          ReportedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object TimelineHomeCommunitiesPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.union(
        Seq(
          DropAllAuthorRemovedCommunityTweetsRule,
          DropAllHiddenCommunityTweetsRule,
          ViewerHasMatchingMutedKeywordForHomeTimelineRule,
        ),
        VisibilityPolicy.baseQuotedTweetTombstoneRules,
        CommunitiesPolicy.tweetRules,
      ),
      userRules = Seq(
        ViewerMutesAuthorRule,
        ViewerBlocksAuthorRule,
      )
    )

case object TimelineHomePromotedHydrationPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        ViewerHasMatchingMutedKeywordForHomeTimelinePromotedTweetRule,
        ViewerMutesAuthorHomeTimelinePromotedTweetRule,
        ViewerBlocksAuthorHomeTimelinePromotedTweetRule
      ) ++ TimelineHomeHydrationPolicy.tweetRules,
      policyRuleParams = TimelineHomeHydrationPolicy.policyRuleParams
    )

case object SpacesPolicy
    etwittertends VisibilityPolicy(
        SpaceDoNotAmplifyAllUsersDropRule,
        SpaceNsfwHighPrecisionNonFollowerDropRule),
      userRules = Seq(
        AuthorBlocksViewerDropRule
      )
    )

case object SpacesSellerApplicationStatusPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        ViewerIsNotAuthorDropRule
      )
    )

case object SpacesParticipantsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(DropAllRule),
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        SuspendedAuthorRule
      )
    )

case object SpacesSharingPolicy
    etwittertends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object SpaceFleetlinePolicy
    etwittertends VisibilityPolicy(
      spaceRules = Seq(
        SpaceDoNotAmplifyNonFollowerDropRule,
        SpaceCoordHarmfulActivityHighRecallNonFollowerDropRule,
        SpaceUntrustedUrlNonFollowerDropRule,
        SpaceMisleadingHighRecallNonFollowerDropRule,
        SpaceNsfwHighPrecisionAllUsersInterstitialRule
      ),
      userRules = Seq(
        TsViolationRule,
        DoNotAmplifyNonFollowerRule,
        NotGraduatedNonFollowerRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        UserAbusiveNonFollowerDropRule
      )
    )

case object SpaceNotificationsPolicy
    etwittertends VisibilityPolicy(
      spaceRules = Seq(
        SpaceHatefulHighRecallAllUsersDropRule,
        SpaceViolenceHighRecallAllUsersDropRule,
        SpaceDoNotAmplifyAllUsersDropRule,
        SpaceCoordHarmfulActivityHighRecallAllUsersDropRule,
        SpaceUntrustedUrlNonFollowerDropRule,
        SpaceMisleadingHighRecallNonFollowerDropRule,
        SpaceNsfwHighPrecisionAllUsersDropRule,
        SpaceNsfwHighRecallAllUsersDropRule,
        ViewerHasMatchingMutedKeywordInSpaceTitleForNotificationsRule
      ),
      userRules = Seq(
        ViewerMutesAuthorRule,
        ViewerBlocksAuthorRule,
        AuthorBlocksViewerDropRule,
        TsViolationRule,
        DoNotAmplifyUserRule,
        AbusiveRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        RecommendationsBlacklistRule,
        NotGraduatedRule,
        SpamHighRecallRule,
        AbusiveHighRecallRule,
        UserBlinkWorstAllUsersDropRule,
        UserNsfwNearPerfectNonFollowerDropRule,
        SpaceNsfwHighPrecisionNonFollowerDropRule,
        UserNsfwAvatarImageNonFollowerDropRule,
        UserNsfwBannerImageNonFollowerDropRule
      )
    )

case object SpaceTweetAvatarHomeTimelinePolicy
    etwittertends VisibilityPolicy(
      spaceRules = Seq(
        SpaceDoNotAmplifyNonFollowerDropRule,
        SpaceCoordHarmfulActivityHighRecallNonFollowerDropRule,
        SpaceUntrustedUrlNonFollowerDropRule,
        SpaceMisleadingHighRecallNonFollowerDropRule,
        SpaceNsfwHighPrecisionAllUsersDropRule,
        SpaceNsfwHighPrecisionAllUsersInterstitialRule
      ),
      userRules = Seq(
        TsViolationRule,
        DoNotAmplifyUserRule,
        NotGraduatedNonFollowerRule,
        AbusiveRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        RecommendationsBlacklistRule,
        SpamHighRecallRule,
        AbusiveHighRecallRule,
        UserBlinkWorstAllUsersDropRule,
        UserNsfwNearPerfectNonFollowerDropRule,
        SpaceNsfwHighPrecisionNonFollowerDropRule,
        UserNsfwAvatarImageNonFollowerDropRule,
        UserNsfwBannerImageNonFollowerDropRule
      )
    )

case object SpaceHomeTimelineUprankingPolicy
    etwittertends VisibilityPolicy(
      spaceRules = Seq(
        SpaceDoNotAmplifyNonFollowerDropRule,
        SpaceCoordHarmfulActivityHighRecallNonFollowerDropRule,
        SpaceUntrustedUrlNonFollowerDropRule,
        SpaceMisleadingHighRecallNonFollowerDropRule,
        SpaceNsfwHighPrecisionNonFollowerDropRule,
        SpaceNsfwHighPrecisionSafeSearchNonFollowerDropRule,
        SpaceNsfwHighRecallSafeSearchNonFollowerDropRule
      ),
      userRules = Seq(
        TsViolationRule,
        DoNotAmplifyUserRule,
        NotGraduatedRule,
        AbusiveRule,
        SearchBlacklistRule,
        SearchNsfwTetwittertRule,
        RecommendationsBlacklistRule,
        SpamHighRecallRule,
        AbusiveHighRecallRule,
        UserBlinkWorstAllUsersDropRule,
        UserNsfwNearPerfectNonFollowerDropRule,
        UserNsfwAvatarImageNonFollowerDropRule,
        UserNsfwBannerImageNonFollowerDropRule
      )
    )

case object SpaceJoinScreenPolicy
    etwittertends VisibilityPolicy(
      spaceRules = Seq(
        SpaceNsfwHighPrecisionAllUsersInterstitialRule
      )
    )

case object KitchenSinkDevelopmentPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules.diff(
        Seq(
          BounceTweetLabelRule,
          DropEtwitterclusiveTweetContentRule,
          DropTrustedFriendsTweetContentRule
        )
      ) ++ Seq(
        BounceTweetLabelTombstoneRule,
        TombstoneEtwitterclusiveTweetContentRule,
        TombstoneTrustedFriendsTweetContentRule)
        ++ Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          ViewerReportsAuthorInterstitialRule,
          ViewerMutesAuthorInterstitialRule,
          ViewerBlocksAuthorInterstitialRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          EtwitterperimentalNudgeLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorTombstoneRule,
        SuspendedAuthorRule
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableRetweetTombstoneRule,
        DeactivatedUserUnavailableRetweetTombstoneRule,
        OffBoardedUserUnavailableRetweetTombstoneRule,
        ErasedUserUnavailableRetweetTombstoneRule,
        ProtectedUserUnavailableRetweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableRetweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableRetweetTombstoneRule,
        ViewerMutesAuthorUserUnavailableRetweetTombstoneRule,
        SuspendedUserUnavailableInnerQuotedTweetTombstoneRule,
        DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule,
        OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule,
        ErasedUserUnavailableInnerQuotedTweetTombstoneRule,
        ProtectedUserUnavailableInnerQuotedTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      ),
      mediaRules = VisibilityPolicy.baseMediaRules
    )

case object CurationPolicyViolationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DoNotAmplifyAllViewersDropRule,
      ),
      userRules = Seq(
        DoNotAmplifyUserRule,
        TsViolationRule
      )
    )

case object GraphqlDefaultPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object GryphonDecksAndColumnsSharingPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object UserSettingsPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(ViewerIsNotAuthorDropRule),
      tweetRules = Seq(DropAllRule)
    )

case object BlockMuteUsersTimelinePolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(SuspendedAuthorRule),
      tweetRules = Seq(DropAllRule)
    )

case object TopicRecommendationsPolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        Seq(
          NsfwHighRecallTweetLabelRule,
          NsfwTetwittertHighPrecisionTweetLabelDropRule
        )
          ++ RecommendationsPolicy.tweetRules,
      userRules = RecommendationsPolicy.userRules
    )

case object RitoActionedTweetTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules =
        VisibilityPolicy.baseTweetTombstoneRules
          ++ Seq(
            AuthorBlocksViewerTombstoneRule,
            ProtectedAuthorTombstoneRule
          ),
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
    )

case object EmbeddedTweetsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
      )
    )

case object EmbedTweetMarkupPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(DropStaleTweetsRule) ++
        VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
    )

case object ArticleTweetTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules =
          VisibilityPolicy.baseTweetRules ++
          Seq(
            ViewerHasMatchingMutedKeywordForHomeTimelineRule,
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            NsfwCardImageAllUsersTweetLabelRule,
          ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAuthorRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object ConversationFocalPrehydrationPolicy
    etwittertends VisibilityPolicy(
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      )
    )

case object ConversationFocalTweetPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          DynamicProductAdDropTweetLabelRule,
          AuthorBlocksViewerTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          ReportedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
          MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
          ViewerMutesAuthorInnerQuotedTweetInterstitialRule,
          ViewerBlocksAuthorInnerQuotedTweetInterstitialRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules
        ++ ConversationsAdAvoidanceRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = ConversationsAdAvoidanceRules.policyRuleParams
        ++ SensitiveMediaSettingsConversationBaseRules.policyRuleParams
    )

case object ConversationReplyPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          LowQualityTweetLabelTombstoneRule,
          SpamHighRecallTweetLabelTombstoneRule,
          DuplicateContentTweetLabelTombstoneRule,
          DeciderableSpamHighRecallAuthorLabelTombstoneRule,
          SmyteSpamTweetLabelTombstoneRule,
          AuthorBlocksViewerTombstoneRule,
          TotwittericityReplyFilterRule,
          DynamicProductAdDropTweetLabelRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule,
          ViewerBlocksAuthorInterstitialRule,
          ViewerMutesAuthorInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules
        ++ ConversationsAdAvoidanceRules.tweetRules,
      userRules = Seq(
        LowQualityRule,
        ReadOnlyRule,
        LowQualityHighRecallRule,
        CompromisedRule,
        DeciderableSpamHighRecallRule
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = ConversationsAdAvoidanceRules.policyRuleParams
        ++ SensitiveMediaSettingsConversationBaseRules.policyRuleParams
    )

case object AdsBusinessSettingsPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(DropAllRule)
    )

case object UserMilestoneRecommendationPolicy
    etwittertends VisibilityPolicy(
      userRules = RecommendationsPolicy.userRules ++ Seq(
      )
    )

case object TrustedFriendsUserListPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(DropAllRule),
      userRules = Seq(
        ViewerBlocksAuthorRule
      )
    )

case object TwitterDelegateUserListPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(
        ViewerBlocksAuthorRule,
        ViewerIsAuthorDropRule,
        DeactivatedAuthorRule,
        AuthorBlocksViewerDropRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object QuickPromoteTweetEligibilityPolicy
    etwittertends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules,
      userRules = UserTimelineRules.UserRules,
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object ReportCenterPolicy
    etwittertends VisibilityPolicy(
      tweetRules = ConversationFocalTweetPolicy.tweetRules.diff(
        ConversationsAdAvoidanceRules.tweetRules
      ),
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
        TombstoneDeletedOuterTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = ConversationFocalTweetPolicy.policyRuleParams
    )

case object ConversationInjectedTweetPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++
        LimitedEngagementBaseRules.tweetRules ++ Seq(
        SkipTweetDetailLimitedEngagementTweetLabelRule
      )
    )

case object EditHistoryTimelinePolicy
    etwittertends VisibilityPolicy(
      tweetRules = ConversationReplyPolicy.tweetRules,
      policyRuleParams = ConversationReplyPolicy.policyRuleParams,
      deletedTweetRules = ConversationReplyPolicy.deletedTweetRules,
      userUnavailableStateRules = ConversationReplyPolicy.userUnavailableStateRules)

case object UserSelfViewOnlyPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq(ViewerIsNotAuthorDropRule),
      tweetRules = Seq(DropAllRule)
    )

case object TwitterArticleComposePolicy
    etwittertends VisibilityPolicy(
      twitterArticleRules = Seq(
        ViewerIsNotAuthorDropRule
      )
    )

case object TwitterArticleProfileTabPolicy
    etwittertends VisibilityPolicy(
      twitterArticleRules = Seq(
        AuthorBlocksViewerDropRule
      )
    )

case object TwitterArticleReadPolicy
    etwittertends VisibilityPolicy(
      twitterArticleRules = Seq(
        AuthorBlocksViewerDropRule,
      )
    )

case object ContentControlToolInstallPolicy
    etwittertends VisibilityPolicy(
      userRules = UserProfileHeaderPolicy.userRules,
      tweetRules = UserProfileHeaderPolicy.tweetRules
    )

case object TimelineProfileSpacesPolicy
    etwittertends VisibilityPolicy(
      userRules = UserProfileHeaderPolicy.userRules,
      tweetRules = UserProfileHeaderPolicy.tweetRules
    )

case object TimelineFavoritesSelfViewPolicy
    etwittertends VisibilityPolicy(
      tweetRules = TimelineFavoritesPolicy.tweetRules.diff(Seq(DropStaleTweetsRule)),
      policyRuleParams = TimelineFavoritesPolicy.policyRuleParams,
      deletedTweetRules = TimelineFavoritesPolicy.deletedTweetRules,
      userUnavailableStateRules = TimelineFavoritesPolicy.userUnavailableStateRules
    )

case object BaseQigPolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelDropRule,
        AutomationTweetLabelRule,
        DoNotAmplifyDropRule,
        DownrankSpamReplyTweetLabelRule,
        DuplicateContentTweetLabelDropRule,
        DuplicateMentionTweetLabelRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        NsfwTetwittertTweetLabelDropRule,
        NsfwVideoTweetLabelDropRule,
        PdnaTweetLabelRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        SearchBlacklistHighRecallTweetLabelDropRule,
        SearchBlacklistTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
      ),
      userRules = Seq(
        DuplicateContentRule,
        EngagementSpammerHighRecallRule,
        EngagementSpammerRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        NsfwHighPrecisionRule,
        NsfwHighRecallRule,
        NsfwSensitiveRule,
        ReadOnlyRule,
        RecommendationsBlacklistRule,
        SearchBlacklistRule,
        SpamHighRecallRule
      ))

case object NotificationsQigPolicy
    etwittertends VisibilityPolicy(
      tweetRules = BaseQigPolicy.tweetRules ++ Seq(
        DropAllCommunityTweetsRule,
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        HighProactiveTosScoreTweetLabelDropSearchRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionRule,
        NsfwHighRecallRule,
        NsfwNearPerfectAuthorRule,
        NsfwSensitiveRule,
      ),
      userRules = BaseQigPolicy.userRules ++ Seq(
        AbusiveRule,
        LowQualityRule,
        CompromisedRule,
        ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
        ViewerMutesAuthorViewerOptInBlockingOnSearchRule,
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        NsfwNearPerfectAuthorRule
      )
    )

case object ShoppingManagerSpyModePolicy
    etwittertends VisibilityPolicy(
      tweetRules = Seq(
        DropAllRule
      ),
      userRules = Seq(
        SuspendedAuthorRule,
        DeactivatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule
      )
    )

case object ZipbirdConsumerArchivesPolicy
    etwittertends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
      ),
      userUnavailableStateRules = Seq(
        AuthorBlocksViewerUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        SuspendedUserUnavailableTweetTombstoneRule,
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedTweetRule,
        TombstoneBounceDeletedTweetRule,
      )
    )

case class MitwitteredVisibilityPolicy(
  originalPolicy: VisibilityPolicy,
  additionalTweetRules: Seq[Rule])
    etwittertends VisibilityPolicy(
      tweetRules = (additionalTweetRules ++ originalPolicy.tweetRules)
        .sortWith(_.actionBuilder.actionSeverity > _.actionBuilder.actionSeverity),
      userRules = originalPolicy.userRules,
      cardRules = originalPolicy.cardRules,
      quotedTweetRules = originalPolicy.quotedTweetRules,
      dmRules = originalPolicy.dmRules,
      dmConversationRules = originalPolicy.dmConversationRules,
      dmEventRules = originalPolicy.dmEventRules,
      spaceRules = originalPolicy.spaceRules,
      userUnavailableStateRules = originalPolicy.userUnavailableStateRules,
      twitterArticleRules = originalPolicy.twitterArticleRules,
      deletedTweetRules = originalPolicy.deletedTweetRules,
      mediaRules = originalPolicy.mediaRules,
      communityRules = originalPolicy.communityRules,
      policyRuleParams = originalPolicy.policyRuleParams
    )

case object TweetAwardPolicy
    etwittertends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules =
        VisibilityPolicy.baseTweetRules ++ Seq(
          EmergencyDropRule,
          NsfwHighPrecisionTweetLabelRule,
          NsfwHighRecallTweetLabelRule,
          NsfwReportedHeuristicsTweetLabelRule,
          NsfwCardImageTweetLabelRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTetwittertTweetLabelDropRule,
          GoreAndViolenceHighPrecisionTweetLabelRule,
          GoreAndViolenceReportedHeuristicsTweetLabelRule,
          GoreAndViolenceTweetLabelRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          AbusiveTweetLabelRule,
          BystanderAbusiveTweetLabelRule
        )
    )
