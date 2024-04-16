package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.T  Ut l
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FSParams}
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport java.ut l.Calendar
 mport java.ut l.T  Zone

case class M nDurat onMod f erCalculator() {

  pr vate def mapCountryCodeToT  Zone(
    countryCode: Str ng,
    stats: StatsRece ver
  ): Opt on[Calendar] = {
    PushConstants.countryCodeToT  ZoneMap
      .get(countryCode.toUpperCase).map(t  zone =>
        Calendar.get nstance(T  Zone.getT  Zone(t  zone)))
  }

  pr vate def transformToH (
    dayOfH :  nt
  ):  nt = {
     f (dayOfH  < 0) dayOfH  + 24
    else dayOfH 
  }

  pr vate def getM nDurat onByH OfDay(
    h OfDay:  nt,
    startT  L st: Seq[ nt],
    endT  L st: Seq[ nt],
    m nDurat onT  Mod f erConst: Seq[ nt],
    stats: StatsRece ver
  ): Opt on[ nt] = {
    val scopedStats = stats.scope("getM nDurat onByH OfDay")
    scopedStats.counter("request"). ncr()
    val durat onOpt = (startT  L st, endT  L st, m nDurat onT  Mod f erConst).z pped.toL st
      .f lter {
        case (startT  , endT  , _) =>
           f (startT   <= endT  ) h OfDay >= startT   && h OfDay < endT  
          else (h OfDay >= startT  ) || h OfDay < endT  
        case _ => false
      }.map {
        case (_, _, mod f er) => mod f er
      }. adOpt on
    durat onOpt match {
      case So (durat on) => scopedStats.counter(s"$durat on.m nutes"). ncr()
      case _ => scopedStats.counter("none"). ncr()
    }
    durat onOpt
  }

  def getM nDurat onMod f er(
    target: Target,
    calendar: Calendar,
    stats: StatsRece ver
  ): Opt on[ nt] = {
    val startT  L st = target.params(FSParams.M nDurat onMod f erStartH L st)
    val endT  L st = target.params(FSParams.M nDurat onMod f erEndH L st)
    val m nDurat onT  Mod f erConst = target.params(FSParams.M nDurat onT  Mod f erConst)
     f (startT  L st.length != endT  L st.length || m nDurat onT  Mod f erConst.length != startT  L st.length) {
      None
    } else {
      val h OfDay = calendar.get(Calendar.HOUR_OF_DAY)
      getM nDurat onByH OfDay(
        h OfDay,
        startT  L st,
        endT  L st,
        m nDurat onT  Mod f erConst,
        stats)
    }
  }

  def getM nDurat onMod f er(
    target: Target,
    countryCodeOpt: Opt on[Str ng],
    stats: StatsRece ver
  ): Opt on[ nt] = {
    val scopedStats = stats
      .scope("getM nDurat onMod f er")
    scopedStats.counter("total_requests"). ncr()

    countryCodeOpt match {
      case So (countryCode) =>
        scopedStats
          .counter("country_code_ex sts"). ncr()
        val calendarOpt = mapCountryCodeToT  Zone(countryCode, scopedStats)
        calendarOpt.flatMap(calendar => getM nDurat onMod f er(target, calendar, scopedStats))
      case _ => None
    }
  }

  def getM nDurat onMod f er(target: Target, stats: StatsRece ver): Future[Opt on[ nt]] = {
    val scopedStats = stats
      .scope("getM nDurat onMod f er")
    scopedStats.counter("total_requests"). ncr()

    val startT  L st = target.params(FSParams.M nDurat onMod f erStartH L st)
    val endT  L st = target.params(FSParams.M nDurat onMod f erEndH L st)
    val m nDurat onT  Mod f erConst = target.params(FSParams.M nDurat onT  Mod f erConst)
     f (startT  L st.length != endT  L st.length || m nDurat onT  Mod f erConst.length != startT  L st.length) {
      Future.value(None)
    } else {
      target.localT   nHHMM.map {
        case (h OfDay, _) =>
          getM nDurat onByH OfDay(
            h OfDay,
            startT  L st,
            endT  L st,
            m nDurat onT  Mod f erConst,
            scopedStats)
        case _ => None
      }
    }
  }

  def getM nDurat onMod f erByUserOpened tory(
    target: Target,
    openedPushByH AggregatedOpt: Opt on[Map[ nt,  nt]],
    stats: StatsRece ver
  ): Opt on[ nt] = {
    val scopedStats = stats
      .scope("getM nDurat onMod f erByUserOpened tory")
    scopedStats.counter("total_requests"). ncr()
    openedPushByH AggregatedOpt match {
      case So (openedPushByH Aggregated) =>
         f (openedPushByH Aggregated. sEmpty) {
          scopedStats.counter("openedPushByH Aggregated_empty"). ncr()
          None
        } else {
          val currentUTCH  = T  Ut l.h OfDay(T  .now)
          val utcH W hMaxOpened =  f (target.params(FSParams.EnableRandomH ForQu ckSend)) {
            (target.target d % 24).to nt
          } else {
            openedPushByH Aggregated.maxBy(_._2)._1
          }
          val numOfMaxOpened = openedPushByH Aggregated.maxBy(_._2)._2
           f (numOfMaxOpened >= target.params(FSParams.SendT  ByUser toryMaxOpenedThreshold)) {
            scopedStats.counter("pass_exper  nt_bucket_threshold"). ncr()
             f (numOfMaxOpened >= target
                .params(FSParams.SendT  ByUser toryMaxOpenedThreshold)) { // only update  f number of opened pus s  et threshold
              scopedStats.counter("pass_max_threshold"). ncr()
              val qu ckSendBeforeH s =
                target.params(FSParams.SendT  ByUser toryQu ckSendBeforeH s)
              val qu ckSendAfterH s =
                target.params(FSParams.SendT  ByUser toryQu ckSendAfterH s)

              val h sToLessSend = target.params(FSParams.SendT  ByUser toryNoSendsH s)

              val qu ckSendT  M nDurat on nM nute =
                target.params(FSParams.SendT  ByUser toryQu ckSendM nDurat on nM nute)
              val noSendT  M nDurat on =
                target.params(FSParams.SendT  ByUser toryNoSendM nDurat on)

              val startT  ForNoSend = transformToH (
                utcH W hMaxOpened - qu ckSendBeforeH s - h sToLessSend)
              val startT  ForQu ckSend = transformToH (
                utcH W hMaxOpened - qu ckSendBeforeH s)
              val endT  ForNoSend =
                transformToH (utcH W hMaxOpened - qu ckSendBeforeH s)
              val endT  ForQu ckSend =
                transformToH (utcH W hMaxOpened + qu ckSendAfterH s) + 1

              val startT  L st = Seq(startT  ForNoSend, startT  ForQu ckSend)
              val endT  L st = Seq(endT  ForNoSend, endT  ForQu ckSend)
              val m nDurat onT  Mod f erConst =
                Seq(noSendT  M nDurat on, qu ckSendT  M nDurat on nM nute)

              getM nDurat onByH OfDay(
                currentUTCH ,
                startT  L st,
                endT  L st,
                m nDurat onT  Mod f erConst,
                scopedStats)

            } else None
          } else None
        }
      case _ =>
        None
    }
  }

}
