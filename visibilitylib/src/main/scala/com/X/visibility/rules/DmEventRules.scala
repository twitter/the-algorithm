package com.X.visibility.rules

import com.X.visibility.rules.Reason.Unspecified
import com.X.visibility.rules.Condition.DeactivatedAuthor
import com.X.visibility.rules.Condition.ErasedAuthor
import com.X.visibility.rules.Condition.SuspendedAuthor
import com.X.visibility.rules.Condition.DmEventInOneToOneConversationWithUnavailableUser
import com.X.visibility.rules.Condition.DmEventIsBeforeLastClearedEvent
import com.X.visibility.rules.Condition.DmEventIsBeforeJoinConversationEvent
import com.X.visibility.rules.Condition.DmEventIsDeleted
import com.X.visibility.rules.Condition.DmEventIsHidden
import com.X.visibility.rules.Condition.LastMessageReadUpdateDmEvent
import com.X.visibility.rules.Condition.MessageCreateDmEvent
import com.X.visibility.rules.Condition.PerspectivalJoinConversationDmEvent
import com.X.visibility.rules.Condition.ViewerIsDmEventInitiatingUser
import com.X.visibility.rules.Condition.ViewerIsDmConversationParticipant
import com.X.visibility.configapi.params.RuleParams
import com.X.visibility.rules.Condition.And
import com.X.visibility.rules.Condition.CsFeedbackDismissedDmEvent
import com.X.visibility.rules.Condition.CsFeedbackSubmittedDmEvent
import com.X.visibility.rules.Condition.JoinConversationDmEvent
import com.X.visibility.rules.Condition.Not
import com.X.visibility.rules.Condition.Or
import com.X.visibility.rules.Condition.TrustConversationDmEvent
import com.X.visibility.rules.Condition.WelcomeMessageCreateDmEvent
import com.X.visibility.rules.Condition.DmEventInOneToOneConversation
import com.X.visibility.rules.Condition.ConversationCreateDmEvent

object DmEventRules {

  object MessageCreateEventWithUnavailableSenderDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        Or(SuspendedAuthor, DeactivatedAuthor, ErasedAuthor)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object WelcomeMessageCreateEventOnlyVisibleToRecipientDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        And(ViewerIsDmEventInitiatingUser, WelcomeMessageCreateDmEvent)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object InaccessibleDmEventDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        Or(
          Not(ViewerIsDmConversationParticipant),
          DmEventIsBeforeLastClearedEvent,
          DmEventIsBeforeJoinConversationEvent)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object HiddenAndDeletedDmEventDropRule
      extends RuleWithConstantAction(Drop(Unspecified), Or(DmEventIsDeleted, DmEventIsHidden)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object NonPerspectivalDmEventDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        Or(
          And(Not(PerspectivalJoinConversationDmEvent), JoinConversationDmEvent),
          And(
            Not(ViewerIsDmEventInitiatingUser),
            Or(TrustConversationDmEvent, CsFeedbackSubmittedDmEvent, CsFeedbackDismissedDmEvent))
        )
      ) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object DmEventInOneToOneConversationWithUnavailableUserDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        And(
          Or(MessageCreateDmEvent, LastMessageReadUpdateDmEvent),
          DmEventInOneToOneConversationWithUnavailableUser)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }

  object GroupEventInOneToOneConversationDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        And(
          Or(JoinConversationDmEvent, ConversationCreateDmEvent),
          DmEventInOneToOneConversation)) {
    override def enableFailClosed = Seq(RuleParams.True)
  }
}
