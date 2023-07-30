package com.X.visibility.rules

import com.X.visibility.configapi.params.FSRuleParams.CardUriRootDomainDenyListParam
import com.X.visibility.configapi.params.RuleParam
import com.X.visibility.configapi.params.RuleParams.EnableCardUriRootDomainCardDenylistRule
import com.X.visibility.configapi.params.RuleParams.EnableCommunityNonMemberPollCardRule
import com.X.visibility.configapi.params.RuleParams.EnableCommunityNonMemberPollCardRuleFailClosed
import com.X.visibility.rules.Condition.And
import com.X.visibility.rules.Condition.CardUriHasRootDomain
import com.X.visibility.rules.Condition.CommunityTweetCommunityVisible
import com.X.visibility.rules.Condition.IsPollCard
import com.X.visibility.rules.Condition.LoggedOutOrViewerNotFollowingAuthor
import com.X.visibility.rules.Condition.Not
import com.X.visibility.rules.Condition.Or
import com.X.visibility.rules.Condition.ProtectedAuthor
import com.X.visibility.rules.Condition.TweetIsCommunityTweet
import com.X.visibility.rules.Condition.ViewerIsCommunityMember

object DropProtectedAuthorPollCardRule
    extends RuleWithConstantAction(
      Drop(Reason.ProtectedAuthor),
      And(
        IsPollCard,
        ProtectedAuthor,
        LoggedOutOrViewerNotFollowingAuthor,
      )
    )

object DropCardUriRootDomainDenylistRule
    extends RuleWithConstantAction(
      Drop(Reason.Unspecified),
      And(CardUriHasRootDomain(CardUriRootDomainDenyListParam))
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableCardUriRootDomainCardDenylistRule)
}

object DropCommunityNonMemberPollCardRule
    extends RuleWithConstantAction(
      Drop(Reason.CommunityNotAMember),
      And(
        IsPollCard,
        TweetIsCommunityTweet,
        Or(
          Not(ViewerIsCommunityMember),
          Not(CommunityTweetCommunityVisible),
        )
      ),
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableCommunityNonMemberPollCardRule)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableCommunityNonMemberPollCardRuleFailClosed)
}
