package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.cand date.Fr gate tory
 mport com.tw ter.fr gate.common.cand date.HTLV s  tory
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.common.cand date.UserDeta ls
 mport com.tw ter.fr gate.common.pred cate.TargetUserPred cates
 mport com.tw ter.fr gate.common.pred cate.{Fat guePred cate => CommonFat guePred cate}
 mport com.tw ter.fr gate.common.store.dev ce nfo.Mob leCl entType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.target.TargetScor ngDeta ls
 mport com.tw ter.fr gate.pushserv ce.ut l.PushCapUt l
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on
 mport com.tw ter.fr gate.thr ftscala.{CommonRecom ndat onType => CRT}
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future

object TargetPred cates {

  def paramPred cate[T <: Target](
    param: Param[Boolean]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = param.getClass.getS mpleNa .str pSuff x("$")
    Pred cate
      .from { target: T => target.params(param) }
      .w hStats(statsRece ver.scope(s"param_${na }_controlled_pred cate"))
      .w hNa (s"param_${na }_controlled_pred cate")
  }

  /**
   * Use t  pred cate except fn  s true., Sa  as t  cand date vers on but for Target
   */
  def exceptedPred cate[T <: TargetUser](
    na : Str ng,
    fn: T => Future[Boolean],
    pred cate: Pred cate[T]
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    Pred cate
      .fromAsync { e: T => fn(e) }
      .or(pred cate)
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  /**
   * Refresh For push handler target user pred cate to fat gue on v s  ng Ho  t  l ne
   */
  def targetHTLV s Pred cate[
    T <: TargetUser w h UserDeta ls w h TargetABDec der w h HTLV s  tory
  ](
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "target_htl_v s _pred cate"
    Pred cate
      .fromAsync { target: T =>
        val h sToFat gue = target.params(PushFeatureSw chParams.HTLV s Fat gueT  )
        TargetUserPred cates
          .ho T  l neFat gue(h sToFat gue.h s)
          .apply(Seq(target))
          .map(_. ad)
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def targetPushB EnabledPred cate[T <: Target](
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "push_b _enabled"
    val scopedStats = statsRece ver.scope(s"targetpred cate_$na ")

    Pred cate
      .fromAsync { target: T =>
        target.dev ce nfo
          .map {  nfo =>
             nfo.ex sts { dev ce nfo =>
              dev ce nfo. sRecom ndat onsEl g ble ||
              dev ce nfo. sNewsEl g ble ||
              dev ce nfo. sTop csEl g ble ||
              dev ce nfo. sSpacesEl g ble
            }
          }
      }.w hStats(scopedStats)
      .w hNa (na )
  }

  def targetFat guePred cate[T <: Target](
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "target_fat gue_pred cate"
    val pred cateStatScope = statsRece ver.scope(na )
    Pred cate
      .fromAsync { target: T =>
        PushCapUt l
          .getPushCapFat gue(target, pred cateStatScope)
          .flatMap { pushCap nfo =>
            CommonFat guePred cate
              .mag cRecsPushTargetFat guePred cate(
                 nterval = pushCap nfo.fat gue nterval,
                max n nterval = pushCap nfo.pushcap
              )
              .apply(Seq(target))
              .map(_. adOpt on.getOrElse(false))
          }
      }
      .w hStats(pred cateStatScope)
      .w hNa (na )
  }

  def teamExceptedPred cate[T <: TargetUser](
    pred cate: Na dPred cate[T]
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[T] = {
    Pred cate
      .fromAsync { t: T => t. sTeam mber }
      .or(pred cate)
      .w hStats(stats.scope(pred cate.na ))
      .w hNa (pred cate.na )
  }

  def targetVal dMob leSDKPred cate[T <: Target](
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "val d_mob le_sdk"
    val scopedStats = statsRece ver.scope(s"targetpred cate_$na ")

    Pred cate
      .fromAsync { target: T =>
        TargetUserPred cates.val dMob leSDKPred cate
          .apply(Seq(target)).map(_. adOpt on.getOrElse(false))
      }.w hStats(scopedStats)
      .w hNa (na )
  }

  def mag cRecsM nDurat onS nceSent[T <: Target](
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "target_m n_durat on_s nce_push"
    Pred cate
      .fromAsync { target: T =>
        PushCapUt l.getM nDurat onS ncePush(target, statsRece ver).flatMap { m nDurat onS ncePush =>
          CommonFat guePred cate
            .mag cRecsM nDurat onS ncePush( nterval = m nDurat onS ncePush)
            .apply(Seq(target)).map(_. ad)
        }
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def optoutProbPred cate[
    T <: TargetUser w h TargetABDec der w h TargetScor ngDeta ls w h Fr gate tory
  ](
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "target_has_h gh_optout_probab l y"
    Pred cate
      .fromAsync { target: T =>
        val  sNewUser = target. s30DayNewUserFromSnowflake dT  
         f ( sNewUser) {
          statsRece ver.scope(na ).counter("all_new_users"). ncr()
        }
        target.bucketOptoutProbab l y
          .flatMap {
            case So (optoutProb) =>
               f (optoutProb >= target.params(PushFeatureSw chParams.BucketOptoutThresholdParam)) {
                CommonFat guePred cate
                  .mag cRecsPushTargetFat guePred cate(
                     nterval = 24.h s,
                    max n nterval = target.params(PushFeatureSw chParams.OptoutExptPushCapParam)
                  )
                  .apply(Seq(target))
                  .map { values =>
                    val  sVal d = values. adOpt on.getOrElse(false)
                     f (! sVal d &&  sNewUser) {
                      statsRece ver.scope(na ).counter("f ltered_new_users"). ncr()
                    }
                     sVal d
                  }
              } else Future.True
            case _ => Future.True
          }
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  /**
   * Pred cate used to spec fy CRT fat gue g ven  nterval and max number of cand dates w h n  nterval.
   * @param crt                   T  spec f c CRT that t  pred cate  s be ng appl ed to
   * @param  ntervalParam         T  fat gue  nterval
   * @param max n ntervalParam    T  max number of t  g ven CRT's cand dates that are acceptable
   *                               n t   nterval
   * @param stats                 StatsRece ver
   * @return                      Target Pred cate
   */
  def pushRecTypeFat guePred cate(
    crt: CRT,
     ntervalParam: Param[Durat on],
    max n ntervalParam: FSBoundedParam[ nt],
    stats: StatsRece ver
  ): Pred cate[Target] =
    Pred cate.fromAsync { target: Target =>
      val  nterval = target.params( ntervalParam)
      val max n nterval = target.params(max n ntervalParam)
      CommonFat guePred cate
        .recTypeTargetFat guePred cate(
           nterval =  nterval,
          max n nterval = max n nterval,
          recom ndat onType = crt,
          not f cat onD splayLocat on = Not f cat onD splayLocat on.PushToMob leDev ce,
          m n nterval = 30.m nutes
        )(stats.scope(s"${crt}_push_cand date_fat gue")).apply(Seq(target)).map(_. ad)
    }

  def  nl neAct onFat guePred cate(
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[Target] = {
    val na  = " nl ne_act on_fat gue"
    val pred cateRequests = statsRece ver.scope(na ).counter("requests")
    val target s nExpt = statsRece ver.scope(na ).counter("target_ n_expt")
    val pred cateEnabled = statsRece ver.scope(na ).counter("enabled")
    val pred cateD sabled = statsRece ver.scope(na ).counter("d sabled")
    val  nl neFat gueD sabled = statsRece ver.scope(na ).counter(" nl ne_fat gue_d sabled")

    Pred cate
      .fromAsync { target: Target =>
        pred cateRequests. ncr()
         f (target.params(PushFeatureSw chParams.Target n nl neAct onAppV s Fat gue)) {
          target s nExpt. ncr()
          target. nl neAct on tory.map {  nl ne tory =>
             f ( nl ne tory.nonEmpty && target.params(
                PushFeatureSw chParams.Enable nl neAct onAppV s Fat gue)) {
              pred cateEnabled. ncr()
              val  nl neFat gue = target.params(PushFeatureSw chParams. nl neAct onAppV s Fat gue)
              val lookback nMs =  nl neFat gue.ago. nM ll seconds
              val f ltered tory =  nl ne tory.f lter {
                case (t  , _) => t   > lookback nMs
              }
              f ltered tory. sEmpty
            } else {
               nl neFat gueD sabled. ncr()
              true
            }
          }
        } else {
          pred cateD sabled. ncr()
          Future.True
        }
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def  bNot fsHoldback[T <: TargetUser w h UserDeta ls w h TargetABDec der](
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[T] = {
    val na  = "mr_ b_not fs_holdback"
    Pred cate
      .fromAsync { targetUserContext: T =>
        targetUserContext.dev ce nfo.map { dev ce nfoOpt =>
          val  sPr mary b = dev ce nfoOpt.ex sts {
            _.guessedPr maryCl ent.ex sts { cl entType =>
              cl entType == Mob leCl entType. b
            }
          }
          !( sPr mary b && targetUserContext.params(PushFeatureSw chParams.MR bHoldbackParam))
        }
      }
      .w hStats(stats.scope(s"pred cate_$na "))
      .w hNa (na )
  }
}
