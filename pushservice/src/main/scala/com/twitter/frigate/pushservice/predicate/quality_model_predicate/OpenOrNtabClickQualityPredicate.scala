package com.tw ter.fr gate.pushserv ce.pred cate.qual y_model_pred cate

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.ut l.Future

object Expl c OONCF lterPred cate extends Qual yPred cateBase {
  overr de lazy val na  = "open_or_ntab_cl ck_expl c _threshold"

  overr de lazy val thresholdExtractor = (t: Target) =>
    Future.value(t.params(PushFeatureSw chParams.Qual yPred cateExpl c ThresholdParam))

  overr de def scoreExtractor = (cand date: PushCand date) =>
    cand date.mr  ghtedOpenOrNtabCl ckRank ngProbab l y
}

object   ghtedOpenOrNtabCl ckQual yPred cate extends Qual yPred cateBase {
  overr de lazy val na  = "  ghted_open_or_ntab_cl ck_model"

  overr de lazy val thresholdExtractor = (t: Target) => {
    Future.value(0.0)
  }

  overr de def scoreExtractor =
    (cand date: PushCand date) => cand date.mr  ghtedOpenOrNtabCl ckF lter ngProbab l y
}
