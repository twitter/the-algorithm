package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.cand date.Fr gate tory
 mport com.tw ter.fr gate.common. tory. tory
 mport com.tw ter.fr gate.common.pred cate.Fr gate toryFat guePred cate
 mport com.tw ter.fr gate.common.pred cate.{Fat guePred cate => TargetFat guePred cate}
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object D scoverTw terPred cate {

  /**
   * Pred cate used to determ ne  f a m n mum durat on has elapsed s nce t  last MR push
   * for a CRT to be val d.
   * @param na              dent f er of t  caller (used for stats)
   * @param  ntervalParam   T  m n mum durat on  nterval
   * @param stats           StatsRece ver
   * @return                Target Pred cate
   */
  def m nDurat onElapsedS nceLastMrPushPred cate(
    na : Str ng,
     ntervalParam: Param[Durat on],
    stats: StatsRece ver
  ): Pred cate[Target] =
    Pred cate
      .fromAsync { target: Target =>
        val  nterval =
          target.params( ntervalParam)
        Fr gate toryFat guePred cate(
          m n nterval =  nterval,
          getSorted tory = { h:  tory =>
            val mag cRecsOnly tory =
              TargetFat guePred cate.mag cRecsPushOnlyF lter(h.sortedPushDm tory)
            TargetFat guePred cate.mag cRecsNewUserPlaybookPushF lter(mag cRecsOnly tory)
          }
        ).flatContraMap { target: TargetUser w h Fr gate tory =>
            target. tory
          }.apply(Seq(target)).map {
            _. ad
          }
      }.w hStats(stats.scope(s"${na }_pred cate_mr_push_m n_ nterval"))
      .w hNa (s"${na }_pred cate_mr_push_m n_ nterval")
}
