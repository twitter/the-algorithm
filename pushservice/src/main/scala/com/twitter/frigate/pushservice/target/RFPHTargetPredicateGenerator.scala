package com.twitter.frigate.pushservice.target

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.predicate.TargetPromptFeedbackFatiguePredicate
import com.twitter.frigate.common.predicate.TargetUserPredicates
import com.twitter.frigate.pushservice.model.PushTypes.Target
import com.twitter.frigate.pushservice.params.PushConstants
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.params.PushParams
import com.twitter.frigate.pushservice.predicate.TargetNtabCaretClickFatiguePredicate
import com.twitter.frigate.pushservice.predicate.TargetPredicates
import com.twitter.hermit.predicate.NamedPredicate

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
