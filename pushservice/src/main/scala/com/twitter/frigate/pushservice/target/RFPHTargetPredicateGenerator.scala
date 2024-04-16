package com.tw ter.fr gate.pushserv ce.target

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.pred cate.TargetPromptFeedbackFat guePred cate
 mport com.tw ter.fr gate.common.pred cate.TargetUserPred cates
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetNtabCaretCl ckFat guePred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetPred cates
 mport com.tw ter. rm .pred cate.Na dPred cate

class RFPHTargetPred cateGenerator( mpl c  statsRece ver: StatsRece ver) {
  val pred cates: L st[Na dPred cate[Target]] = L st(
    TargetPred cates.mag cRecsM nDurat onS nceSent(),
    TargetPred cates.targetHTLV s Pred cate(),
    TargetPred cates. nl neAct onFat guePred cate(),
    TargetPred cates.targetFat guePred cate(),
    TargetUserPred cates.secondaryDormantAccountPred cate(),
    TargetPred cates.targetVal dMob leSDKPred cate,
    TargetPred cates.targetPushB EnabledPred cate,
    TargetUserPred cates.targetUserEx sts(),
    TargetPred cates.paramPred cate(PushFeatureSw chParams.EnablePushRecom ndat onsParam),
    TargetPromptFeedbackFat guePred cate.responseNoPred cate(
      PushParams.EnablePromptFeedbackFat gueResponseNoPred cate,
      PushConstants.AcceptableT  S nceLastNegat veResponse),
    TargetPred cates.teamExceptedPred cate(TargetNtabCaretCl ckFat guePred cate.apply()),
    TargetPred cates.optoutProbPred cate(),
    TargetPred cates. bNot fsHoldback()
  )
}

object RFPHTargetPred cates {
  def apply( mpl c  statsRece ver: StatsRece ver): L st[Na dPred cate[Target]] =
    new RFPHTargetPred cateGenerator().pred cates
}
