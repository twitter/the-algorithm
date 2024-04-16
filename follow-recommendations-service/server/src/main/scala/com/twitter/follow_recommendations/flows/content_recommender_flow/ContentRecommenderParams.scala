package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

abstract class ContentRecom nderParams[A](default: A) extends Param[A](default) {
  overr de val statNa : Str ng = "content_recom nder/" + t .getClass.getS mpleNa 
}

object ContentRecom nderParams {

  case object TargetEl g b l y
      extends FSParam[Boolean](ContentRecom nderFlowFeatureSw chKeys.TargetUserEl g ble, true)

  case object ResultS zeParam
      extends FSBoundedParam[ nt](ContentRecom nderFlowFeatureSw chKeys.ResultS ze, 15, 1, 500)
  case object BatchS zeParam
      extends FSBoundedParam[ nt](ContentRecom nderFlowFeatureSw chKeys.BatchS ze, 15, 1, 500)
  case object RecentFollow ngPred cateBudget nM ll second
      extends FSBoundedParam[ nt](
        ContentRecom nderFlowFeatureSw chKeys.RecentFollow ngPred cateBudget nM ll second,
        8,
        1,
        50)
  case object FetchCand dateS ceBudget nM ll second
      extends FSBoundedParam[ nt](
        ContentRecom nderFlowFeatureSw chKeys.Cand dateGenerat onBudget nM ll second,
        60,
        1,
        80)
  case object EnableRecentFollow ngPred cate
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys.EnableRecentFollow ngPred cate,
        true)
  case object EnableG zmoduckPred cate
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys.EnableG zmoduckPred cate,
        false)
  case object Enable nact vePred cate
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys.Enable nact vePred cate,
        false)
  case object Enable nval dTargetCand dateRelat onsh pPred cate
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys.Enable nval dTargetCand dateRelat onsh pPred cate,
        false)
  case object  ncludeAct v yBasedCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeAct v yBasedCand dateS ce,
        true)
  case object  ncludeSoc alBasedCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeSoc alBasedCand dateS ce,
        true)
  case object  ncludeGeoBasedCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeGeoBasedCand dateS ce,
        true)
  case object  ncludeHo T  l neT etRecsCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeHo T  l neT etRecsCand dateS ce,
        false)
  case object  ncludeSoc alProofEnforcedCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeSoc alProofEnforcedCand dateS ce,
        false)
  case object  ncludeNewFollow ngNewFollow ngExpans onCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeNewFollow ngNewFollow ngExpans onCand dateS ce,
        false)

  case object  ncludeMoreGeoBasedCand dateS ce
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys. ncludeMoreGeoBasedCand dateS ce,
        false)

  case object GetFollo rsFromSgs
      extends FSParam[Boolean](ContentRecom nderFlowFeatureSw chKeys.GetFollo rsFromSgs, false)

  case object Enable nval dRelat onsh pPred cate
      extends FSParam[Boolean](
        ContentRecom nderFlowFeatureSw chKeys.Enable nval dRelat onsh pPred cate,
        false)
}
