package com.X.visibility.rules

import com.X.visibility.configapi.params.RuleParams
import com.X.visibility.rules.Condition.And
import com.X.visibility.rules.Condition.DmConversationLastReadableEventIdIsValid
import com.X.visibility.rules.Condition.DmConversationTimelineIsEmpty
import com.X.visibility.rules.Condition.ViewerIsDmConversationParticipant
import com.X.visibility.rules.Condition.DmConversationInfoExists
import com.X.visibility.rules.Condition.DmConversationTimelineExists
import com.X.visibility.rules.Condition.Not
import com.X.visibility.rules.Condition.DeactivatedAuthor
import com.X.visibility.rules.Condition.ErasedAuthor
import com.X.visibility.rules.Condition.OneToOneDmConversation
import com.X.visibility.rules.Condition.Or
import com.X.visibility.rules.Condition.SuspendedAuthor
import com.X.visibility.rules.Reason.Unspecified

object DmConversationRules {

  object DropEmptyDmConversationRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        Or(
          Not(DmConversationLastReadableEventIdIsValid),
          And(OneToOneDmConversation, DmConversationTimelineIsEmpty))) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropInaccessibleDmConversationRule
      extends RuleWithConstantAction(Drop(Unspecified), Not(ViewerIsDmConversationParticipant)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropDmConversationWithUndefinedConversationInfoRule
      extends RuleWithConstantAction(Drop(Unspecified), Not(DmConversationInfoExists)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropDmConversationWithUndefinedConversationTimelineRule
      extends RuleWithConstantAction(Drop(Unspecified), Not(DmConversationTimelineExists)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DropOneToOneDmConversationWithUnavailableParticipantsRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        And(OneToOneDmConversation, Or(SuspendedAuthor, DeactivatedAuthor, ErasedAuthor))) {
    override def enableFailClosed = Seq(RuleParams.True)
  }
}
