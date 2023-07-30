package com.X.visibility.rules

import com.X.visibility.configapi.params.RuleParam
import com.X.visibility.configapi.params.RuleParams
import com.X.visibility.rules.Reason.Toxicity

object ToxicityReplyFilterRules {

  sealed abstract class ToxicityReplyFilterBaseRule(
    action: Action)
      extends RuleWithConstantAction(
        action = action,
        condition = Condition.ToxrfFilteredFromAuthorViewer)

  object ToxicityReplyFilterRule
      extends ToxicityReplyFilterBaseRule(action = Tombstone(Epitaph.Unavailable)) {

    override def enabled: Seq[RuleParam[Boolean]] = Seq(
      RuleParams.EnableToxicReplyFilteringConversationRulesParam)
  }

  object ToxicityReplyFilterDropNotificationRule
      extends ToxicityReplyFilterBaseRule(action = Drop(Toxicity)) {

    override def enabled: Seq[RuleParam[Boolean]] = Seq(
      RuleParams.EnableToxicReplyFilteringNotificationsRulesParam)
  }
}
