package com.X.follow_recommendations.flows.ads
import com.X.conversions.DurationOps._
import com.X.timelines.configapi.Param
import com.X.util.Duration

abstract class PromotedAccountsFlowParams[A](default: A) extends Param[A](default) {
  override val statName: String = "ads/" + this.getClass.getSimpleName
}

object PromotedAccountsFlowParams {

  // number of total slots returned to the end user, available to put ads
  case object TargetEligibility extends PromotedAccountsFlowParams[Boolean](true)
  case object ResultSizeParam extends PromotedAccountsFlowParams[Int](Int.MaxValue)
  case object BatchSizeParam extends PromotedAccountsFlowParams[Int](Int.MaxValue)
  case object FetchCandidateSourceBudget
      extends PromotedAccountsFlowParams[Duration](1000.millisecond)

}
