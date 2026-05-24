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
import com.twitter.visibility.rules.ToxicityReplyFilterRules.ToxicityReplyFilterDropNotificationRule
import com.twitter.visibility.rules.ToxicityReplyFilterRules.ToxicityReplyFilterRule
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
    DropExclusiveTweetContentRule,
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
    TombstoneExclusiveTweetContentRule,
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
    extends VisibilityPolicy(
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

case object FilterNonePolicy extends VisibilityPolicy()

object ConversationsAdAvoidanceRules {
  val tweetRules = Seq(
    NsfwHighRecallTweetLabelAvoidRule,
    NsfwHighPrecisionTweetLabelAvoidRule,
    NsfwTextTweetLabelAvoidRule,
    AvoidHighToxicityModelScoreRule,
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
    NsfwTextTweetLabelAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    AvoidHighToxicityModelScoreRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    AvoidReportedTweetModelScoreRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    NsfwHighPrecisionUserLabelAvoidTweetRule -> ruleParams(
      RuleParams.EnableNewAdAvoidanceRulesParam),
    TweetNsfwUserAdminAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    DoNotAmplifyTweetLabelAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
    NsfaHighPrecisionTweetLabelAvoidRule -> ruleParams(RuleParams.EnableNewAdAvoidanceRulesParam),
  )
}

case object FilterDefaultPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule
      ) ++
        LimitedEngagementBaseRules.tweetRules
    )

case object TestPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        TestRule
      )
    )

case object CardsServicePolicy
    extends VisibilityPolicy(
      cardRules = Seq(
        DropProtectedAuthorPollCardRule,
        DropCardUriRootDomainDenylistRule
      ),
      spaceRules = Seq(
        SpaceHighToxicityScoreNonFollowerDropRule,
        SpaceHatefulHighRecallAllUsersDropRule,
        SpaceViolenceHighRecallAllUsersDropRule,
        ViewerIsSoftUserDropRule
      ),
    )

case object CardPollVotingPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object FriendsFollowingListPolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListOwnershipsPolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListRecommendationsPolicy
    extends VisibilityPolicy(
      userRules = RecommendationsPolicy.userRules ++ Seq(
        DropNsfwUserAuthorRule,
        NsfwHighRecallRule,
        SearchBlacklistRule,
        SearchNsfwTextRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAuthorRule
      )
    )

case object ListSearchBaseRules {

  val NonExperimentalSafeSearchMinimalPolicyUserRules: Seq[Rule] =
    SafeSearchMinimalPolicy.userRules.filterNot(_.isExperimental)

  val MinimalPolicyUserRules: Seq[Rule] = NonExperimentalSafeSearchMinimalPolicyUserRules

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
    SafeSearchNsfwTextUserLabelRule,
    SafeSearchSpamHighRecallUserLabelRule,
    SafeSearchDownrankSpamReplyAuthorLabelRule,
    SafeSearchNsfwTextAuthorLabelRule,
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
    extends VisibilityPolicy(
      userRules = ListSearchBaseRules.MinimalPolicyUserRules ++
        ListSearchBaseRules.BlockMutePolicyUserRules ++
        ListSearchBaseRules.StrictPolicyUserRules
    )

case object ListSubscriptionsPolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListMembershipsPolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object AllSubscribedListsPolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object ListHeaderPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object NewUserExperiencePolicy
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
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
        SearchNsfwTextRule,
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
        NsfwTextNonAuthorDropRule
      )
    )

case object DESHomeTimelinePolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        DropAllCommunityTweetsRule
      ) ++
        VisibilityPolicy.baseTweetRules,
      userRules = UserTimelineRules.UserRules
    )

case object DesQuoteTweetTimelinePolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule
      ) ++ ElevatedQuoteTweetTimelinePolicy.tweetRules.diff(Seq(DropStaleTweetsRule)),
      userRules = Seq(
        ProtectedAuthorDropRule
      ),
      policyRuleParams = ElevatedQuoteTweetTimelinePolicy.policyRuleParams
    )

case object DESRealtimeSpamEnrichmentPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        LowQualityTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        SearchBlacklistTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
        DropAllCommunityTweetsRule,
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
        GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
        NsfwReportedHeuristicsAllUsersTweetLabelRule,
        GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
        NsfwCardImageAllUsersTweetLabelRule
      )
    )

case object DESRealtimePolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropAllCommunityTweetsRule,
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
        DropAllCollabInvitationTweetsRule
      ),
      userRules = Seq(
        DropAllProtectedAuthorRule,
        DropProtectedViewerIfPresentRule
      )
    )

case object DESRetweetingUsersPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropAllExclusiveTweetsRule,
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
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ),
      userRules = TimelineLikedByRules.UserRules
    )

case object DESUserBookmarksPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++
        (VisibilityPolicy.baseTweetRules
          ++ Seq(DropAllCommunityTweetsRule)
          ++ TimelineProfileRules.tweetRules),
      userRules = UserTimelineRules.UserRules
    )

case object DESUserLikedTweetsPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllExclusiveTweetsRule,
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
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DropAllCommunityTweetsRule,
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        DropAllExclusiveTweetsRule,
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++
        (VisibilityPolicy.baseTweetRules
          ++ Seq(DropAllCommunityTweetsRule)
          ++ TimelineProfileRules.tweetRules),
      userRules = UserTimelineRules.UserRules
    )

case object DevPlatformComplianceStreamPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        SpamAllUsersTweetLabelRule,
        PdnaAllUsersTweetLabelRule,
        BounceAllUsersTweetLabelRule,
        AbusePolicyEpisodicTweetLabelComplianceTweetNoticeRule,
      )
    )

case object DesTweetDetailPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++ BaseTweetDetailPolicy.tweetRules
    )

case object DevPlatformGetListTweetsPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(DropStaleTweetsRule) ++ DesTweetDetailPolicy.tweetRules
    )

case object FollowerConnectionsPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        SpammyFollowerRule
      )
    )

case object SuperFollowerConnectionsPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        SpammyFollowerRule
      )
    )

case object LivePipelineEngagementCountsPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object LiveVideoTimelinePolicy
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
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
        SearchNsfwTextRule,
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
        NsfwTextNonAuthorDropRule
      )
    )

case object MagicRecsPolicyOverrides {
  val replacements: Map[Rule, Rule] = Map()
  def union(rules: Seq[Rule]*): Seq[Rule] = rules
    .map(ar => ar.map(x => replacements.getOrElse(x, x)))
    .reduce((a, b) => a ++ b.filterNot(a.contains))
}

case object MagicRecsPolicy
    extends VisibilityPolicy(
      tweetRules = MagicRecsPolicyOverrides.union(
        RecommendationsPolicy.tweetRules.filterNot(_ == SafetyCrisisLevel3DropRule),
        NotificationsIbisPolicy.tweetRules,
        Seq(
          NsfaHighRecallTweetLabelRule,
          NsfwHighRecallTweetLabelRule,
          NsfwTextHighPrecisionTweetLabelDropRule),
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = MagicRecsPolicy.tweetRules,
      userRules = MagicRecsPolicy.userRules
    )

case object MagicRecsAggressiveV2Policy
    extends VisibilityPolicy(
      tweetRules = MagicRecsV2Policy.tweetRules,
      userRules = MagicRecsV2Policy.userRules
    )

case object MinimalPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        TsViolationRule
      )
    )

case object ModeratedTweetsTimelinePolicy
    extends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules.diff(
        Seq(
          AuthorBlocksViewerDropRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule)),
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object MomentsPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules =
          VisibilityPolicy.baseTweetRules ++ Seq(
          AbusiveUqfNonFollowerTweetLabelRule,
          LowQualityTweetLabelDropRule,
          ToxicityReplyFilterDropNotificationRule,
          NsfwHighPrecisionTweetLabelRule,
          GoreAndViolenceHighPrecisionTweetLabelRule,
          NsfwReportedHeuristicsTweetLabelRule,
          GoreAndViolenceReportedHeuristicsTweetLabelRule,
          NsfwCardImageTweetLabelRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTextTweetLabelDropRule,
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
        NsfwTextNonAuthorDropRule
      )
    )

case object NotificationsReadPolicy
    extends VisibilityPolicy(
      tweetRules = NotificationsRules.tweetRules,
      userRules = NotificationsRules.userRules
    )

case object NotificationsTimelineDeviceFollowPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        CompromisedRule
      )
    )

case object NotificationsWritePolicy
    extends VisibilityPolicy(
      tweetRules = NotificationsRules.tweetRules,
      userRules = NotificationsRules.userRules
    )

case object NotificationsWriterV2Policy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = NotificationsRules.tweetRules ++
        Seq(
          LowQualityTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          DuplicateMentionUqfTweetLabelRule,
          LowQualityMentionTweetLabelRule,
          SmyteSpamTweetLabelDropRule,
          ToxicityReplyFilterDropNotificationRule,
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
    extends VisibilityPolicy(
      tweetRules = NotificationsWriterTweetHydratorPolicy.tweetRules,
      userRules = NotificationsWriterV2Policy.userRules
    )

case object NotificationsPlatformPushPolicy
    extends VisibilityPolicy(
      tweetRules = NotificationsIbisPolicy.tweetRules,
      userRules = Seq(ViewerMutesAuthorRule)
        ++ NotificationsIbisPolicy.userRules
    )

case object QuoteTweetTimelinePolicy
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
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
        SearchNsfwTextRule,
        CompromisedRule,
        SpamHighRecallRule,
        DuplicateContentRule,
        NsfwHighPrecisionRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        AbusiveHighRecallRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object ElevatedQuoteTweetTimelinePolicy
    extends VisibilityPolicy(
      tweetRules =
          TweetDetailPolicy.tweetRules.diff(
            Seq(
              MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
              ReportedTweetInterstitialRule)),
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object EmbedsPublicInterestNoticePolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      )
    )

case object RecommendationsPolicy
    extends VisibilityPolicy(
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
          NsfwTextTweetLabelDropRule,
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
        NsfwTextNonAuthorDropRule
      )
    )

case object RecosVideoPolicy
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object RepliesGroupingPolicy
    extends VisibilityPolicy(
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

case object ReturningUserExperiencePolicy
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
        NsfwTextHighPrecisionTweetLabelDropRule,
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
        SearchNsfwTextRule,
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
        NsfwTextNonAuthorDropRule,
        DropNsfwUserAuthorRule,
        NsfwHighRecallRule
      )
    )

case object ReturningUserExperienceFocalTweetPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
        SearchNsfwTextRule,
        DuplicateContentRule,
        DoNotAmplifyNonFollowerRule,
        SearchLikelyIvsLabelNonFollowerDropUserRule
      )
    )

case object SearchHydrationPolicy
    extends VisibilityPolicy(
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
      SafeSearchNsfwTextTweetLabelRule,
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
    SafeSearchNsfwTextTweetLabelRule,
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
    SafeSearchNsfwTextAuthorLabelRule
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
    extends VisibilityPolicy(
      userRules = SearchBlenderRules.userRules
    )

case object SearchLatestUserRulesPolicy
    extends VisibilityPolicy(
      userRules = SearchLatestPolicy.userRules
    )

case object UserSearchSrpPolicy
    extends VisibilityPolicy(
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
        SafeSearchNsfwTextAuthorLabelRule
      )
    )

case object UserSearchTypeaheadPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        SafeSearchAbusiveUserLabelRule,
        SafeSearchHighRecallUserLabelRule,
        SafeSearchNsfwNearPerfectAuthorRule,
        SafeSearchNsfwHighPrecisionUserLabelRule,
        SafeSearchNsfwAvatarImageUserLabelRule,
        SafeSearchNsfwBannerImageUserLabelRule,
        SafeSearchAbusiveHighRecallUserLabelRule,
        SafeSearchNsfwTextAuthorLabelRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object SearchMixerSrpMinimalPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
        ViewerMutesAuthorViewerOptInBlockingOnSearchRule
      )
    )

case object SearchMixerSrpStrictPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      userRules = SearchBlenderRules.searchQueryMatchesTweetAuthorRules
    )

case object SearchPeopleTypeaheadPolicy
    extends VisibilityPolicy(
      userRules = SearchBlenderRules.searchQueryMatchesTweetAuthorRules
        .diff(
          Seq(
            SafeSearchNotGraduatedNonFollowersUserLabelRule
          )),
      tweetRules = Seq(DropAllRule)
    )

case object SearchPhotoPolicy
    extends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = SearchBlenderRules.userRelevanceRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchTrendTakeoverPromotedTweetPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object SearchVideoPolicy
    extends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = SearchBlenderRules.userRelevanceRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchLatestPolicy
    extends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRecencyRules,
      userRules = SearchBlenderRules.userRecencyBaseRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchTopPolicy
    extends VisibilityPolicy(
      tweetRules = SearchBlenderRules.tweetRelevanceRules,
      userRules = Seq(SpammyUserModelHighPrecisionDropTweetRule) ++
        SearchBlenderRules.basicBlockMuteRules ++
        SearchBlenderRules.searchQueryMatchesTweetAuthorRules,
      policyRuleParams = SearchBlenderRules.basicBlockMutePolicyRuleParam
    )

case object SearchTopQigPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
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
        SearchNsfwTextRule,
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
        NsfwTextNonAuthorDropRule,
      )
    )

case object StickersTimelinePolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules,
      userRules = Seq(
        AbusiveRule,
        LowQualityRule,
        ReadOnlyRule,
        CompromisedRule,
        SearchBlacklistRule,
        SearchNsfwTextRule,
        DuplicateContentRule,
        EngagementSpammerRule,
        EngagementSpammerHighRecallRule,
        NsfwSensitiveRule,
        SpamHighRecallRule,
        AbusiveHighRecallRule
      )
    )

case object StratoExtLimitedEngagementsPolicy
    extends VisibilityPolicy(
      tweetRules =
        VisibilityPolicy.baseTweetRules ++ LimitedEngagementBaseRules.tweetRules
    )

case object InternalPromotedContentPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object StreamServicesPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTextTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object SuperLikePolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusePolicyEpisodicTweetLabelDropRule,
        EmergencyDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTextTweetLabelDropRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object TimelineFocalTweetPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object TimelineBookmarkPolicy
    extends VisibilityPolicy(
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
          DropExclusiveTweetContentRule,
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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

case object ProfileMixerFavoritesPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropExclusiveTweetContentRule,
        DropOuterCommunityTweetsRule,
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      )
    )

case object TimelineMediaPolicy
    extends VisibilityPolicy(
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

case object ProfileMixerMediaPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropStaleTweetsRule,
        DropExclusiveTweetContentRule
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
    TombstoneExclusiveQuotedTweetContentRule,
    DropExclusiveTweetContentRule,
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
      NsfwTextTweetLabelAvoidRule,
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules =
        Seq(
          DropOuterCommunityTweetsRule
        ) ++
          VisibilityPolicy.baseTweetRules ++
          TimelineProfileRules.tweetRules
    )

case object TimelineReactiveBlendingPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        AuthorBlocksViewerDropRule
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object SignalsTweetReactingUsersPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTextAllUsersTweetLabelDropRule,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule,
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object SocialProofPolicy
    extends VisibilityPolicy(
      tweetRules = FilterDefaultPolicy.tweetRules,
      userRules = Seq(
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule
      )
    )

case object TimelineLikedByPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTextAllUsersTweetLabelDropRule,
      userRules = TimelineLikedByRules.UserRules :+ NsfwTextNonAuthorDropRule
    )

case object TimelineRetweetedByPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTextAllUsersTweetLabelDropRule,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object TimelineSuperLikedByPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules :+
        NsfwVideoTweetLabelDropRule :+
        NsfwTextAllUsersTweetLabelDropRule,
      userRules = Seq(
        CompromisedNonFollowerWithUqfRule,
        EngagementSpammerNonFollowerWithUqfRule,
        LowQualityNonFollowerWithUqfRule,
        ReadOnlyNonFollowerWithUqfRule,
        SpamHighRecallNonFollowerWithUqfRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object TimelineContentControlsPolicy
    extends VisibilityPolicy(
      tweetRules = TopicsLandingPageTopicRecommendationsPolicy.tweetRules,
      userRules = TopicsLandingPageTopicRecommendationsPolicy.userRules
    )

case object TimelineConversationsPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTextTweetLabelDropRule,
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
        NsfwTextNonAuthorDropRule
      )
    )

case object TimelineMentionsPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          LowQualityTweetLabelDropRule,
          SpamHighRecallTweetLabelDropRule,
          DuplicateContentTweetLabelDropRule,
          DuplicateMentionUqfTweetLabelRule,
          LowQualityMentionTweetLabelRule,
          SmyteSpamTweetLabelDropRule,
          ToxicityReplyFilterDropNotificationRule,
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusePolicyEpisodicTweetLabelInterstitialRule,
        EmergencyDynamicInterstitialRule,
      ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object QuotedTweetRulesPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropAllExclusiveTweetsRule,
        DropAllTrustedFriendsTweetsRule,
      ) ++ BaseTweetDetailPolicy.tweetRules
    )

case object RecosWritePathPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AbusiveTweetLabelRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        NsfwCardImageTweetLabelRule,
        NsfwVideoTweetLabelDropRule,
        NsfwTextTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
        DuplicateContentTweetLabelDropRule,
        BystanderAbusiveTweetLabelRule,
        SmyteSpamTweetLabelDropRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object BrandSafetyPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        NsfwVideoTweetLabelDropRule,
        NsfwTextTweetLabelDropRule,
        NsfaHighRecallTweetLabelInterstitialRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object VideoAdsPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object AppealsPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        HighToxicityScoreDownrankAbusiveQualitySectionRule,
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
        HighToxicityScoreDownrankLowQualitySectionRule,
        HighPSpammyTweetScoreDownrankLowQualitySectionRule,
        RitoActionedTweetDownrankLowQualitySectionRule,
        HighToxicityScoreDownrankHighQualitySectionRule,
      )
    )

case object TimelineConversationsDownrankingMinimalPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        HighProactiveTosScoreTweetLabelDownrankingRule
      )
    )

case object TimelineHomeRecommendationsPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        AllowAllRule
      )
    )

case object FollowedTopicsTimelinePolicy
    extends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object TopicsLandingPageTopicRecommendationsPolicy
    extends VisibilityPolicy(
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

case object ExploreRecommendationsPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
        NsfwTextAllUsersTweetLabelDropRule,
      ),
      userRules = Seq(
        DropNsfwUserAuthorRule,
        DropNsfwAdminAuthorRule,
        NsfwTextAllUsersDropRule
      )
    )

case object HumanizationNudgePolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules
    )

case object TrendsRepresentativeTweetPolicy
    extends VisibilityPolicy(
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
          NsfwTextTweetLabelDropRule,
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
          SearchNsfwTextRule,
          NsfwHighRecallRule,
          TsViolationRule,
          DownrankSpamReplyAllViewersRule,
          NsfwTextNonAuthorDropRule
        )
      )
    )

case object AdsCampaignPolicy
    extends VisibilityPolicy(
      userRules = Seq(SuspendedAuthorRule),
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object AdsManagerPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        AdsManagerDenyListAllUsersTweetLabelRule,
      )
    )

case object AdsReportingDashboardPolicy
    extends VisibilityPolicy(
      tweetRules = AdsManagerPolicy.tweetRules,
      userRules = AdsCampaignPolicy.userRules
    )

case object BirdwatchNoteAuthorPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        SuspendedAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAuthorRule
      )
    )

case object BirdwatchNoteTweetsTimelinePolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object UserProfileHeaderPolicy
    extends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules = Seq(DropAllRule)
    )

case object UserScopedTimelinePolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules,
      tweetRules = Seq(DropAllRule)
    )

case object TweetScopedTimelinePolicy
    extends VisibilityPolicy(
      userRules = UserTimelineRules.UserRules,
      tweetRules = Seq.empty
    )

case object SoftInterventionPivotPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules
    )

case object CuratedTrendsRepresentativeTweetPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        SuspendedAuthorRule,
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAndDoesNotFollowAuthorRule
      )
    )

case object CommunitiesPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = Seq(
        ViewerHasMatchingMutedKeywordForHomeTimelinePromotedTweetRule,
        ViewerMutesAuthorHomeTimelinePromotedTweetRule,
        ViewerBlocksAuthorHomeTimelinePromotedTweetRule
      ) ++ TimelineHomeHydrationPolicy.tweetRules,
      policyRuleParams = TimelineHomeHydrationPolicy.policyRuleParams
    )

case object SpacesPolicy
    extends VisibilityPolicy(
        SpaceDoNotAmplifyAllUsersDropRule,
        SpaceNsfwHighPrecisionNonFollowerDropRule),
      userRules = Seq(
        AuthorBlocksViewerDropRule
      )
    )

case object SpacesSellerApplicationStatusPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        ViewerIsNotAuthorDropRule
      )
    )

case object SpacesParticipantsPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(DropAllRule),
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        SuspendedAuthorRule
      )
    )

case object SpacesSharingPolicy
    extends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object SpaceFleetlinePolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
        SearchNsfwTextRule,
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
    extends VisibilityPolicy(
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
        SearchNsfwTextRule,
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
    extends VisibilityPolicy(
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
        SearchNsfwTextRule,
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
    extends VisibilityPolicy(
      spaceRules = Seq(
        SpaceNsfwHighPrecisionAllUsersInterstitialRule
      )
    )

case object KitchenSinkDevelopmentPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules.diff(
        Seq(
          BounceTweetLabelRule,
          DropExclusiveTweetContentRule,
          DropTrustedFriendsTweetContentRule
        )
      ) ++ Seq(
        BounceTweetLabelTombstoneRule,
        TombstoneExclusiveTweetContentRule,
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
          ExperimentalNudgeLabelRule,
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
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DoNotAmplifyAllViewersDropRule,
      ),
      userRules = Seq(
        DoNotAmplifyUserRule,
        TsViolationRule
      )
    )

case object GraphqlDefaultPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object UserSettingsPolicy
    extends VisibilityPolicy(
      userRules = Seq(ViewerIsNotAuthorDropRule),
      tweetRules = Seq(DropAllRule)
    )

case object BlockMuteUsersTimelinePolicy
    extends VisibilityPolicy(
      userRules = Seq(SuspendedAuthorRule),
      tweetRules = Seq(DropAllRule)
    )

case object TopicRecommendationsPolicy
    extends VisibilityPolicy(
      tweetRules =
        Seq(
          NsfwHighRecallTweetLabelRule,
          NsfwTextHighPrecisionTweetLabelDropRule
        )
          ++ RecommendationsPolicy.tweetRules,
      userRules = RecommendationsPolicy.userRules
    )

case object RitoActionedTweetTimelinePolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      )
    )

case object ConversationFocalTweetPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          LowQualityTweetLabelTombstoneRule,
          SpamHighRecallTweetLabelTombstoneRule,
          DuplicateContentTweetLabelTombstoneRule,
          DeciderableSpamHighRecallAuthorLabelTombstoneRule,
          SmyteSpamTweetLabelTombstoneRule,
          AuthorBlocksViewerTombstoneRule,
          ToxicityReplyFilterRule,
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
    extends VisibilityPolicy(
      tweetRules = Seq(DropAllRule)
    )

case object UserMilestoneRecommendationPolicy
    extends VisibilityPolicy(
      userRules = RecommendationsPolicy.userRules ++ Seq(
      )
    )

case object TrustedFriendsUserListPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(DropAllRule),
      userRules = Seq(
        ViewerBlocksAuthorRule
      )
    )

case object TwitterDelegateUserListPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        ViewerBlocksAuthorRule,
        ViewerIsAuthorDropRule,
        DeactivatedAuthorRule,
        AuthorBlocksViewerDropRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object QuickPromoteTweetEligibilityPolicy
    extends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules,
      userRules = UserTimelineRules.UserRules,
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object ReportCenterPolicy
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      tweetRules = ConversationReplyPolicy.tweetRules,
      policyRuleParams = ConversationReplyPolicy.policyRuleParams,
      deletedTweetRules = ConversationReplyPolicy.deletedTweetRules,
      userUnavailableStateRules = ConversationReplyPolicy.userUnavailableStateRules)

case object UserSelfViewOnlyPolicy
    extends VisibilityPolicy(
      userRules = Seq(ViewerIsNotAuthorDropRule),
      tweetRules = Seq(DropAllRule)
    )

case object TwitterArticleComposePolicy
    extends VisibilityPolicy(
      twitterArticleRules = Seq(
        ViewerIsNotAuthorDropRule
      )
    )

case object TwitterArticleProfileTabPolicy
    extends VisibilityPolicy(
      twitterArticleRules = Seq(
        AuthorBlocksViewerDropRule
      )
    )

case object TwitterArticleReadPolicy
    extends VisibilityPolicy(
      twitterArticleRules = Seq(
        AuthorBlocksViewerDropRule,
      )
    )

case object ContentControlToolInstallPolicy
    extends VisibilityPolicy(
      userRules = UserProfileHeaderPolicy.userRules,
      tweetRules = UserProfileHeaderPolicy.tweetRules
    )

case object TimelineProfileSpacesPolicy
    extends VisibilityPolicy(
      userRules = UserProfileHeaderPolicy.userRules,
      tweetRules = UserProfileHeaderPolicy.tweetRules
    )

case object TimelineFavoritesSelfViewPolicy
    extends VisibilityPolicy(
      tweetRules = TimelineFavoritesPolicy.tweetRules.diff(Seq(DropStaleTweetsRule)),
      policyRuleParams = TimelineFavoritesPolicy.policyRuleParams,
      deletedTweetRules = TimelineFavoritesPolicy.deletedTweetRules,
      userUnavailableStateRules = TimelineFavoritesPolicy.userUnavailableStateRules
    )

case object BaseQigPolicy
    extends VisibilityPolicy(
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
        NsfwTextTweetLabelDropRule,
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
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

case class MixedVisibilityPolicy(
  originalPolicy: VisibilityPolicy,
  additionalTweetRules: Seq[Rule])
    extends VisibilityPolicy(
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
    extends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules =
        VisibilityPolicy.baseTweetRules ++ Seq(
          EmergencyDropRule,
          NsfwHighPrecisionTweetLabelRule,
          NsfwHighRecallTweetLabelRule,
          NsfwReportedHeuristicsTweetLabelRule,
          NsfwCardImageTweetLabelRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTextTweetLabelDropRule,
          GoreAndViolenceHighPrecisionTweetLabelRule,
          GoreAndViolenceReportedHeuristicsTweetLabelRule,
          GoreAndViolenceTweetLabelRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          AbusiveTweetLabelRule,
          BystanderAbusiveTweetLabelRule
        )
    )
