package com.tw ter.fr gate.pushserv ce.pred cate.qual y_model_pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.Qual yPred cateEnum
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter. rm .pred cate.Na dPred cate

object Qual yPred cateMap {

  def apply(
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Map[Qual yPred cateEnum.Value, Na dPred cate[PushCand date]] = {
    Map(
      Qual yPred cateEnum.  ghtedOpenOrNtabCl ck ->   ghtedOpenOrNtabCl ckQual yPred cate(),
      Qual yPred cateEnum.Expl c OpenOrNtabCl ckF lter -> Expl c OONCF lterPred cate(),
      Qual yPred cateEnum.AlwaysTrue -> Pred catesForCand date.alwaysTruePushCand datePred cate,
    )
  }
}
