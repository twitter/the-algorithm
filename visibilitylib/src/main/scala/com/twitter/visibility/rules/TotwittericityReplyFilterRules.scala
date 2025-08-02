package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.rules.Reason.Totwittericity

object TotwittericityReplyFilterRules {

  sealed abstract class TotwittericityReplyFilterBaseRule(
    action: Action)
      etwittertends RuleWithConstantAction(
        action = action,
        condition = Condition.TotwitterrfFilteredFromAuthorViewer)

  object TotwittericityReplyFilterRule
      etwittertends TotwittericityReplyFilterBaseRule(action = Tombstone(Epitaph.Unavailable)) {

    override def enabled: Seq[RuleParam[Boolean]] = Seq(
      RuleParams.EnableTotwittericReplyFilteringConversationRulesParam)
  }

  object TotwittericityReplyFilterDropNotificationRule
      etwittertends TotwittericityReplyFilterBaseRule(action = Drop(Totwittericity)) {

    override def enabled: Seq[RuleParam[Boolean]] = Seq(
      RuleParams.EnableTotwittericReplyFilteringNotificationsRulesParam)
  }
}
