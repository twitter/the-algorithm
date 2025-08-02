package com.twitter.follow_recommendations.flows.ads
import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

abstract class PromotedAccountsFlowParams[A](default: A) etwittertends Param[A](default) {
  override val statName: String = "ads/" + this.getClass.getSimpleName
}

object PromotedAccountsFlowParams {

  // number of total slots returned to the end user, available to put ads
  case object TargetEligibility etwittertends PromotedAccountsFlowParams[Boolean](true)
  case object ResultSizeParam etwittertends PromotedAccountsFlowParams[Int](Int.MatwitterValue)
  case object BatchSizeParam etwittertends PromotedAccountsFlowParams[Int](Int.MatwitterValue)
  case object FetchCandidateSourceBudget
      etwittertends PromotedAccountsFlowParams[Duration](1000.millisecond)

}
