package com.tw ter.fr gate.pushserv ce.pred cate.event

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.EventCand date
 mport com.tw ter.fr gate.common.base.Target nfo
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.cand date.Fr gate tory
 mport com.tw ter.fr gate.common. tory.Rec ems
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Locale
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventHydratedCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutNewsEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout.Mag cFanoutPred catesUt l._
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

object EventPred catesForCand date {
  def hasT le(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventHydratedCand date] = {
    val na  = "event_t le_ava lable"
    val scopedStatsRece ver = statsRece ver.scope(s"pred cate_$na ")
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventHydratedCand date =>
        cand date.eventT leFut.map(_.nonEmpty)
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }

  def  sNotDupl cateW hEvent d(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventHydratedCand date] = {
    val na  = "dupl cate_event_ d"
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventHydratedCand date =>
        val useRelaxedFat gueLengthFut: Future[Boolean] =
          cand date match {
            case mfNewsEvent: Mag cFanoutNewsEventPushCand date =>
              mfNewsEvent. sH ghPr or yEvent
            case _ => Future.value(false)
          }
        Future.jo n(cand date.target. tory, useRelaxedFat gueLengthFut).map {
          case ( tory, useRelaxedFat gueLength) =>
            val f lteredNot f cat ons =  f (useRelaxedFat gueLength) {
              val relaxedFat gue nterval =
                cand date.target
                  .params(
                    PushFeatureSw chParams.Mag cFanoutRelaxedEvent dFat gue nterval nH s).h s
               tory.not f cat onMap.f lterKeys { t   =>
                t  .unt lNow <= relaxedFat gue nterval
              }.values
            } else  tory.not f cat onMap.values
            !Rec ems(f lteredNot f cat ons.toSeq).events.ex sts(_.event d == cand date.event d)
        }
      }
      .w hStats(statsRece ver.scope(s"pred cate_$na "))
      .w hNa (na )
  }

  def  sNotDupl cateW hEvent dForCand date[
    T <: TargetUser w h Fr gate tory,
    Cand <: EventCand date w h Target nfo[T]
  ](
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[Cand] = {
    val na  = " s_not_dupl cate_event"
    Pred cate
      .fromAsync { cand date: Cand =>
        cand date.target.pushRec ems.map {
          !_.events.map(_.event d).conta ns(cand date.event d)
        }
      }
      .w hStats(statsRece ver.scope(na ))
      .w hNa (na )
  }

  def accountCountryPred cateW hAllowl st(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventPushCand date] = {
    val na  = "account_country_pred cate_w h_allowl st"
    val scopedStats = stats.scope(na )

    val sk pPred cate = Pred cate
      .from { cand date: Mag cFanoutEventPushCand date =>
        cand date.target.params(PushFeatureSw chParams.Mag cFanoutSk pAccountCountryPred cate)
      }
      .w hStats(stats.scope("sk p_account_country_pred cate_mf"))
      .w hNa ("sk p_account_country_pred cate_mf")

    val excludeEventFromAccountCountryPred cateF lter ng = Pred cate
      .from { cand date: Mag cFanoutEventPushCand date =>
        val event d = cand date.event d
        val target = cand date.target
        target
          .params(PushFeatureSw chParams.Mag cFanoutEventAllowl stToSk pAccountCountryPred cate)
          .ex sts(event d.equals)
      }
      .w hStats(stats.scope("exclude_event_from_account_country_pred cate_f lter ng"))
      .w hNa ("exclude_event_from_account_country_pred cate_f lter ng")

    sk pPred cate
      .or(excludeEventFromAccountCountryPred cateF lter ng)
      .or(accountCountryPred cate)
      .w hStats(scopedStats)
      .w hNa (na )
  }

  /**
   * C ck  f user's country  s targeted
   * @param stats
   */
  def accountCountryPred cate(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[Mag cFanoutEventPushCand date] = {
    val na  = "account_country_pred cate"
    val scopedStatsRece ver = stats.scope(s"pred cate_$na ")
    val  nternat onalLocalePassedCounter =
      scopedStatsRece ver.counter(" nternat onal_locale_passed")
    val  nternat onalLocaleF lteredCounter =
      scopedStatsRece ver.counter(" nternat onal_locale_f ltered")
    Pred cate
      .fromAsync { cand date: Mag cFanoutEventPushCand date =>
        cand date.target.countryCode.map {
          case So (countryCode) =>
            val denyL stedCountryCodes: Seq[Str ng] =
               f (cand date.commonRecType == CommonRecom ndat onType.Mag cFanoutNewsEvent) {
                cand date.target
                  .params(PushFeatureSw chParams.Mag cFanoutDenyL stedCountr es)
              } else  f (cand date.commonRecType == CommonRecom ndat onType.Mag cFanoutSportsEvent) {
                cand date.target
                  .params(PushFeatureSw chParams.Mag cFanoutSportsEventDenyL stedCountr es)
              } else Seq()
            val eventCountr es =
              cand date.newsFor  tadata
                .flatMap(_.locales).getOrElse(Seq.empty[Locale]).flatMap(_.country)
             f ( s nCountryL st(countryCode, eventCountr es)
              && ! s nCountryL st(countryCode, denyL stedCountryCodes)) {
               nternat onalLocalePassedCounter. ncr()
              true
            } else {
               nternat onalLocaleF lteredCounter. ncr()
              false
            }
          case _ => false
        }
      }
      .w hStats(scopedStatsRece ver)
      .w hNa (na )
  }
}
