package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.DmConversationLastReadableEventIdIsValid
import com.twitter.visibility.rules.Condition.DmConversationTimelineIsEmpty
import com.twitter.visibility.rules.Condition.ViewerIsDmConversationParticipant
import com.twitter.visibility.rules.Condition.DmConversationInfoEtwitterists
import com.twitter.visibility.rules.Condition.DmConversationTimelineEtwitterists
import com.twitter.visibility.rules.Condition.Not
import com.twitter.visibility.rules.Condition.DeactivatedAuthor
import com.twitter.visibility.rules.Condition.ErasedAuthor
import com.twitter.visibility.rules.Condition.OneToOneDmConversation
import com.twitter.visibility.rules.Condition.Or
import com.twitter.visibility.rules.Condition.SuspendedAuthor
import com.twitter.visibility.rules.Reason.Unspecified

object DmConversationRules {

  object DropEmptyDmConversationRule
      etwittertends RuleWithConstantAction(
        Drop(Unspecified),
        Or(
          Not(DmConversationLastReadableEventIdIsValid),
          And(OneToOneDmConversation, DmConversationTimelineIsEmpty))) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropInaccessibleDmConversationRule
      etwittertends RuleWithConstantAction(Drop(Unspecified), Not(ViewerIsDmConversationParticipant)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropDmConversationWithUndefinedConversationInfoRule
      etwittertends RuleWithConstantAction(Drop(Unspecified), Not(DmConversationInfoEtwitterists)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropDmConversationWithUndefinedConversationTimelineRule
      etwittertends RuleWithConstantAction(Drop(Unspecified), Not(DmConversationTimelineEtwitterists)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropOneToOneDmConversationWithUnavailableParticipantsRule
      etwittertends RuleWithConstantAction(
        Drop(Unspecified),
        And(OneToOneDmConversation, Or(SuspendedAuthor, DeactivatedAuthor, ErasedAuthor))) {
    override def enableFailClosed = Seq(RuleParams.True)
  }
}
