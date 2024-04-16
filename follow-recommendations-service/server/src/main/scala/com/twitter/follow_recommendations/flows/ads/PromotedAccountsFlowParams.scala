package com.tw ter.follow_recom ndat ons.flows.ads
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

abstract class PromotedAccountsFlowParams[A](default: A) extends Param[A](default) {
  overr de val statNa : Str ng = "ads/" + t .getClass.getS mpleNa 
}

object PromotedAccountsFlowParams {

  // number of total slots returned to t  end user, ava lable to put ads
  case object TargetEl g b l y extends PromotedAccountsFlowParams[Boolean](true)
  case object ResultS zeParam extends PromotedAccountsFlowParams[ nt]( nt.MaxValue)
  case object BatchS zeParam extends PromotedAccountsFlowParams[ nt]( nt.MaxValue)
  case object FetchCand dateS ceBudget
      extends PromotedAccountsFlowParams[Durat on](1000.m ll second)

}
