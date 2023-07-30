package com.X.frigate.pushservice.target

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.predicate.TargetPromptFeedbackFatiguePredicate
import com.X.frigate.common.predicate.TargetUserPredicates
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.params.PushConstants
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.params.PushParams
import com.X.frigate.pushservice.predicate.TargetNtabCaretClickFatiguePredicate
import com.X.frigate.pushservice.predicate.TargetPredicates
import com.X.hermit.predicate.NamedPredicate

class RFPHTargetPredicateGenerator(implicit statsReceiver: StatsReceiver) {
  val predicates: List[NamedPredicate[Target]] = List(
    TargetPredicates.magicRecsMinDurationSinceSent(),
    TargetPredicates.targetHTLVisitPredicate(),
    TargetPredicates.inlineActionFatiguePredicate(),
    TargetPredicates.targetFatiguePredicate(),
    TargetUserPredicates.secondaryDormantAccountPredicate(),
    TargetPredicates.targetValidMobileSDKPredicate,
    TargetPredicates.targetPushBitEnabledPredicate,
    TargetUserPredicates.targetUserExists(),
    TargetPredicates.paramPredicate(PushFeatureSwitchParams.EnablePushRecommendationsParam),
    TargetPromptFeedbackFatiguePredicate.responseNoPredicate(
      PushParams.EnablePromptFeedbackFatigueResponseNoPredicate,
      PushConstants.AcceptableTimeSinceLastNegativeResponse),
    TargetPredicates.teamExceptedPredicate(TargetNtabCaretClickFatiguePredicate.apply()),
    TargetPredicates.optoutProbPredicate(),
    TargetPredicates.webNotifsHoldback()
  )
}

object RFPHTargetPredicates {
  def apply(implicit statsReceiver: StatsReceiver): List[NamedPredicate[Target]] =
    new RFPHTargetPredicateGenerator().predicates
}
