package com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.pred cate.Fat guePred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.CaretFeedback toryF lter
 mport com.tw ter.fr gate.pushserv ce.pred cate.{
  TargetNtabCaretCl ckFat guePred cate => CommonNtabCaretCl ckFat guePred cate
}
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on
 mport com.tw ter.fr gate.thr ftscala.{CommonRecom ndat onType => CRT}
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.not f cat onserv ce.thr ftscala.CaretFeedbackDeta ls
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future

object RecTypeNtabCaretCl ckFat guePred cate {
  val defaultNa  = "RecTypeNtabCaretCl ckFat guePred cateForCand date"

  pr vate def cand dateFat guePred cate(
    gener cTypeCategor es: Seq[Str ng],
    crts: Set[CRT]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date
  ] = {
    val na  = "f1Tr ggeredCRTBasedFat guePredc ate"
    val scopedStats = stats.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: PushCand date =>
         f (cand date.fr gateNot f cat on.not f cat onD splayLocat on == Not f cat onD splayLocat on.PushToMob leDev ce) {
           f (cand date.target.params(PushParams.EnableFat gueNtabCaretCl ck ngParam)) {
            NtabCaretCl ckContFnFat guePred cate
              .ntabCaretCl ckContFnFat guePred cates(
                f lter tory = Fat guePred cate.recTypesOnlyF lter(crts),
                f lterCaretFeedback tory =
                  CaretFeedback toryF lter.caretFeedback toryF lter(gener cTypeCategor es),
                f lter nl neFeedback tory =
                  NtabCaretCl ckFat gueUt ls.feedbackModelF lterByCRT(crts)
              ).apply(Seq(cand date))
              .map(_. adOpt on.getOrElse(false))
          } else Future.True
        } else {
          Future.True
        }
      }.w hStats(scopedStats)
      .w hNa (na )
  }

  def apply(
    gener cTypeCategor es: Seq[Str ng],
    crts: Set[CRT],
    calculateFat guePer od: Seq[CaretFeedbackDeta ls] => Durat on,
    useMostRecentD sl keT  : Boolean,
    na : Str ng = defaultNa 
  )(
     mpl c  globalStats: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val scopedStats = globalStats.scope(na )
    val commonNtabCaretCl ckFat guePred cate = CommonNtabCaretCl ckFat guePred cate(
      f lterCaretFeedback tory =
        CaretFeedback toryF lter.caretFeedback toryF lter(gener cTypeCategor es),
      f lter tory = Fat guePred cate.recTypesOnlyF lter(crts),
      calculateFat guePer od = calculateFat guePer od,
      useMostRecentD sl keT   = useMostRecentD sl keT  ,
      na  = na 
    )(globalStats)

    Pred cate
      .fromAsync { cand date: PushCand date =>
         f (cand date.fr gateNot f cat on.not f cat onD splayLocat on == Not f cat onD splayLocat on.PushToMob leDev ce) {
           f (cand date.target.params(PushParams.EnableFat gueNtabCaretCl ck ngParam)) {
            commonNtabCaretCl ckFat guePred cate
              .apply(Seq(cand date.target))
              .map(_. adOpt on.getOrElse(false))
          } else Future.True
        } else {
          Future.True
        }
      }.andT n(cand dateFat guePred cate(gener cTypeCategor es, crts))
      .w hStats(scopedStats)
      .w hNa (na )
  }
}
