package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.Cand dateShuffler
 mport com.tw ter.follow_recom ndat ons.common.rankers.  ghted_cand date_s ce_ranker.Exponent alShuffler
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

abstract class PostNuxMlParams[A](default: A) extends Param[A](default) {
  overr de val statNa : Str ng = "post_nux_ml/" + t .getClass.getS mpleNa 
}

object PostNuxMlParams {

  //  nfra params:
  case object FetchCand dateS ceBudget extends PostNuxMlParams[Durat on](90.m ll second)

  // WTF  mpress on Store has very h gh ta l latency (p9990 or p9999), but p99 latency  s pretty good (~100ms)
  // set t  t   budget for t  step to be 200ms to make t  performance of serv ce more pred ctable
  case object Fat gueRankerBudget extends PostNuxMlParams[Durat on](200.m ll second)

  case object MlRankerBudget
      extends FSBoundedParam[Durat on](
        na  = PostNuxMlFlowFeatureSw chKeys.MLRankerBudget,
        default = 400.m ll second,
        m n = 100.m ll second,
        max = 800.m ll second)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }

  // product params:
  case object TargetEl g b l y extends PostNuxMlParams[Boolean](true)

  case object ResultS zeParam extends PostNuxMlParams[ nt](3)
  case object BatchS zeParam extends PostNuxMlParams[ nt](12)

  case object Cand dateShuffler
      extends PostNuxMlParams[Cand dateShuffler[Cand dateUser]](
        new Exponent alShuffler[Cand dateUser])
  case object LogRandomRanker d extends PostNuxMlParams[Boolean](false)

  // w t r or not to use t  ml ranker at all (feature hydrat on + ranker)
  case object UseMlRanker
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.UseMlRanker, false)

  // w t r or not to enable cand date param hydrat on  n postnux_ml_flow
  case object EnableCand dateParamHydrat on
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.EnableCand dateParamHydrat on, false)

  // W t r or not Onl neSTP cand dates are cons dered  n t  f nal pool of cand dates.
  //  f set to `false`, t  cand date s ce w ll be removed *after* all ot r cons derat ons.
  case object Onl neSTPEnabled
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.Onl neSTPEnabled, false)

  // W t r or not t  cand dates are sampled from a Plackett-Luce model
  case object Sampl ngTransformEnabled
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.Sampl ngTransformEnabled, false)

  // W t r or not Follow2Vec cand dates are cons dered  n t  f nal pool of cand dates.
  //  f set to `false`, t  cand date s ce w ll be removed *after* all ot r cons derat ons.
  case object Follow2VecL nearRegress onEnabled
      extends FSParam[Boolean](
        PostNuxMlFlowFeatureSw chKeys.Follow2VecL nearRegress onEnabled,
        false)

  // W t r or not to enable AdhocRanker to allow adhoc, non-ML, score mod f cat ons.
  case object EnableAdhocRanker
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.EnableAdhocRanker, false)

  // W t r t   mpress on-based fat gue ranker  s enabled or not.
  case object EnableFat gueRanker
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.EnableFat gueRanker, true)

  // w t r or not to enable  nterleaveRanker for producer-s de exper  nts.
  case object Enable nterleaveRanker
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.Enable nterleaveRanker, false)

  // w t r to exclude users  n near zero user state
  case object ExcludeNearZeroCand dates
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.ExcludeNearZeroCand dates, false)

  case object EnablePPM LocaleFollowS ce nPostNux
      extends FSParam[Boolean](
        PostNuxMlFlowFeatureSw chKeys.EnablePPM LocaleFollowS ce nPostNux,
        false)

  case object Enable nterestsOptOutPred cate
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.Enable nterestsOptOutPred cate, false)

  case object Enable nval dRelat onsh pPred cate
      extends FSParam[Boolean](
        PostNuxMlFlowFeatureSw chKeys.Enable nval dRelat onsh pPred cate,
        false)

  // Totally d sabl ng SGS pred cate need to d sable Enable nval dRelat onsh pPred cate as  ll
  case object EnableSGSPred cate
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.EnableSGSPred cate, true)

  case object EnableHssPred cate
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.EnableHssPred cate, true)

  // W t r or not to  nclude RepeatedProf leV s s as one of t  cand date s ces  n t  PostNuxMlFlow.  f false,
  // RepeatedProf leV s sS ce would not be run for t  users  n cand date_generat on.
  case object  ncludeRepeatedProf leV s sCand dateS ce
      extends FSParam[Boolean](
        PostNuxMlFlowFeatureSw chKeys. ncludeRepeatedProf leV s sCand dateS ce,
        false)

  case object EnableRealGraphOonV2
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.EnableRealGraphOonV2, false)

  case object GetFollo rsFromSgs
      extends FSParam[Boolean](PostNuxMlFlowFeatureSw chKeys.GetFollo rsFromSgs, false)

  case object EnableRemoveAccountProofTransform
      extends FSParam[Boolean](
        PostNuxMlFlowFeatureSw chKeys.EnableRemoveAccountProofTransform,
        false)

  // qual y factor threshold to turn off ML ranker completely
  object TurnoffMLScorerQFThreshold
      extends FSBoundedParam[Double](
        na  = PostNuxMlFlowFeatureSw chKeys.TurnOffMLScorerQFThreshold,
        default = 0.3,
        m n = 0.1,
        max = 1.0)
}
