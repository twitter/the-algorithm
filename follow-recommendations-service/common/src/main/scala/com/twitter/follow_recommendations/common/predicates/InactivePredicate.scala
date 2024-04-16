package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.google. nject.na .Na d
 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.pred cates. nact vePred cateParams._
 mport com.tw ter.serv ce. tastore.gen.thr ftscala.UserRecom ndab l yFeatures
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.follow_recom ndat ons.common.models.HasUserState
 mport com.tw ter.follow_recom ndat ons.common.pred cates. nact vePred cateParams.Default nact v yThreshold
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext

 mport java.lang.{Long => JLong}

@S ngleton
case class  nact vePred cate @ nject() (
  statsRece ver: StatsRece ver,
  @Na d(Gu ceNa dConstants.USER_RECOMMENDAB L TY_FETCHER) userRecom ndab l yFetc r: Fetc r[
    Long,
    Un ,
    UserRecom ndab l yFeatures
  ]) extends Pred cate[(HasParams w h HasCl entContext w h HasUserState, Cand dateUser)] {

  pr vate val stats: StatsRece ver = statsRece ver.scope(" nact vePred cate")
  pr vate val cac Stats = stats.scope("cac ")

  pr vate def queryUserRecom ndable(user d: Long): St ch[Opt on[UserRecom ndab l yFeatures]] =
    userRecom ndab l yFetc r.fetch(user d).map(_.v)

  pr vate val userRecom ndableCac  =
    St chCac [JLong, Opt on[UserRecom ndab l yFeatures]](
      maxCac S ze = 100000,
      ttl = 12.h s,
      statsRece ver = cac Stats.scope("UserRecom ndable"),
      underly ngCall = (user d: JLong) => queryUserRecom ndable(user d)
    )

  overr de def apply(
    targetAndCand date: (HasParams w h HasCl entContext w h HasUserState, Cand dateUser)
  ): St ch[Pred cateResult] = {
    val (target, cand date) = targetAndCand date

    userRecom ndableCac 
      .readThrough(cand date. d).map {
        case recFeaturesFetchResult =>
          recFeaturesFetchResult match {
            case None =>
              Pred cateResult. nval d(Set(F lterReason.M ss ngRecom ndab l yData))
            case So (recFeatures) =>
               f (d sable nact v yPred cate(target, target.userState, recFeatures.userState)) {
                Pred cateResult.Val d
              } else {
                val default nact v yThreshold = target.params(Default nact v yThreshold).days
                val hasBeenAct veRecently = recFeatures.lastStatusUpdateMs
                  .map(T  .now - T  .fromM ll seconds(_)).getOrElse(
                    Durat on.Top) < default nact v yThreshold
                stats
                  .scope(default nact v yThreshold.toStr ng).counter(
                     f (hasBeenAct veRecently)
                      "act ve"
                    else
                      " nact ve"
                  ). ncr()
                 f (hasBeenAct veRecently && (!target
                    .params(UseEggF lter) || recFeatures. sNotEgg.conta ns(1))) {
                  Pred cateResult.Val d
                } else {
                  Pred cateResult. nval d(Set(F lterReason. nact ve))
                }
              }
          }
      }.rescue {
        case e: Except on =>
          stats.counter(e.getClass.getS mpleNa ). ncr()
          St ch(Pred cateResult. nval d(Set(F lterReason.Fa lOpen)))
      }
  }

  pr vate[t ] def d sable nact v yPred cate(
    target: HasParams,
    consu rState: Opt on[UserState],
    cand dateState: Opt on[UserState]
  ): Boolean = {
    target.params(M ghtBeD sabled) &&
    consu rState.ex sts( nact vePred cate.Val dConsu rStates.conta ns) &&
    (
      (
        cand dateState.ex sts( nact vePred cate.Val dCand dateStates.conta ns) &&
        !target.params(OnlyD sableForNewUserStateCand dates)
      ) ||
      (
        cand dateState.conta ns(UserState.New) &&
        target.params(OnlyD sableForNewUserStateCand dates)
      )
    )
  }
}

object  nact vePred cate {
  val Val dConsu rStates: Set[UserState] = Set(
    UserState. avyNonT eter,
    UserState. d umNonT eter,
    UserState. avyT eter,
    UserState. d umT eter
  )
  val Val dCand dateStates: Set[UserState] =
    Set(UserState.New, UserState.VeryL ght, UserState.L ght, UserState.NearZero)
}
