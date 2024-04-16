package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason

sealed tra  Pred cateResult {
  def value: Boolean
}

object Pred cateResult {

  case object Val d extends Pred cateResult {
    overr de val value = true
  }

  case class  nval d(reasons: Set[F lterReason] = Set.empty[F lterReason]) extends Pred cateResult {
    overr de val value = false
  }
}
