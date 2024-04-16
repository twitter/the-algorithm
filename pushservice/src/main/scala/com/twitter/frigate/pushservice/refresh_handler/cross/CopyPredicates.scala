package com.tw ter.fr gate.pushserv ce.refresh_handler.cross

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter. rm .pred cate.Pred cate

class CopyPred cates(statsRece ver: StatsRece ver) {
  val alwaysTruePred cate = Pred cate
    .from { _: Cand dateCopyPa r =>
      true
    }.w hStats(statsRece ver.scope("always_true_copy_pred cate"))

  val unrecogn zedCand datePred cate = alwaysTruePred cate.fl p
    .w hStats(statsRece ver.scope("unrecogn zed_cand date"))

  val d splaySoc alContextPred cate = Pred cate
    .from { cand dateCopyPa r: Cand dateCopyPa r =>
      cand dateCopyPa r.cand date match {
        case cand dateW hScAct ons: RawCand date w h Soc alContextAct ons =>
          val soc alContextUser ds = cand dateW hScAct ons.soc alContextAct ons.map(_.user d)
          val countSoc alContext = soc alContextUser ds.s ze
          val pushCopy = cand dateCopyPa r.pushCopy

          countSoc alContext match {
            case 1 => pushCopy.hasOneD splaySoc alContext && !pushCopy.hasOt rSoc alContext
            case 2 => pushCopy.hasTwoD splayContext && !pushCopy.hasOt rSoc alContext
            case c  f c > 2 =>
              pushCopy.hasOneD splaySoc alContext && pushCopy.hasOt rSoc alContext
            case _ => false
          }

        case _ => false
      }
    }.w hStats(statsRece ver.scope("d splay_soc al_context_pred cate"))
}
