package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FSParams}
 mport com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue.Cont nuousFunct on
 mport com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue.Cont nuousFunct onParam
 mport com.tw ter.fr gate.pushserv ce.ut l.Overr deNot f cat onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushCapUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType.Mag cFanoutSportsEvent
 mport com.tw ter. b s2.l b.ut l.JsonMarshal
 mport com.tw ter.ut l.Future

tra  Overr deFor b s2Request {
  self: PushCand date =>

  pr vate lazy val overr deStats = self.statsRece ver.scope("overr de_for_ b s2")

  pr vate lazy val addedOverr deAndro dCounter =
    overr deStats.scope("andro d").counter("added_overr de_for_ b s2_request")
  pr vate lazy val addedSmartPushConf gAndro dCounter =
    overr deStats.scope("andro d").counter("added_smart_push_conf g_for_ b s2_request")
  pr vate lazy val addedOverr de osCounter =
    overr deStats.scope(" os").counter("added_overr de_for_ b s2_request")
  pr vate lazy val noOverr deCounter = overr deStats.counter("no_overr de_for_ b s2_request")
  pr vate lazy val noOverr deDueToDev ce nfoCounter =
    overr deStats.counter("no_overr de_due_to_dev ce_ nfo")
  pr vate lazy val addedMlScoreToPayloadAndro d =
    overr deStats.scope("andro d").counter("added_ml_score")
  pr vate lazy val noMlScoreAddedToPayload =
    overr deStats.counter("no_ml_score")
  pr vate lazy val addedNSlotsToPayload =
    overr deStats.counter("added_n_slots")
  pr vate lazy val noNSlotsAddedToPayload =
    overr deStats.counter("no_n_slots")
  pr vate lazy val addedCustomThread dToPayload =
    overr deStats.counter("added_custom_thread_ d")
  pr vate lazy val noCustomThread dAddedToPayload =
    overr deStats.counter("no_custom_thread_ d")
  pr vate lazy val enableTarget dOverr deForMag cFanoutSportsEventCounter =
    overr deStats.counter("enable_target_ d_overr de_for_mf_sports_event")

  lazy val cand dateModelScoreFut: Future[Opt on[Double]] = {
     f (RecTypes.notEl g bleForModelScoreTrack ng(commonRecType)) Future.None
    else mr  ghtedOpenOrNtabCl ckRank ngProbab l y
  }

  lazy val overr deModelValueFut: Future[Map[Str ng, Str ng]] = {
     f (self.target. sLoggedOutUser) {
      Future.value(Map.empty[Str ng, Str ng])
    } else {
      Future
        .jo n(
          target.dev ce nfo,
          target.accountCountryCode,
          Overr deNot f cat onUt l.getCollapseAnd mpress on dForOverr de(self),
          cand dateModelScoreFut,
          target.dynam cPushcap,
          target.optoutAdjustedPushcap,
          PushCapUt l.getDefaultPushCap(target)
        ).map {
          case (
                dev ce nfoOpt,
                countryCodeOpt,
                So ((collapse d,  mpress on ds)),
                mlScore,
                dynam cPushcapOpt,
                optoutAdjustedPushcapOpt,
                defaultPushCap) =>
            val pushCap:  nt = (dynam cPushcapOpt, optoutAdjustedPushcapOpt) match {
              case (_, So (optoutAdjustedPushcap)) => optoutAdjustedPushcap
              case (So (pushcap nfo), _) => pushcap nfo.pushcap
              case _ => defaultPushCap
            }
            getCl entSpec f cOverr deModelValues(
              target,
              dev ce nfoOpt,
              countryCodeOpt,
              collapse d,
               mpress on ds,
              mlScore,
              pushCap)
          case _ =>
            noOverr deCounter. ncr()
            Map.empty[Str ng, Str ng]
        }
    }
  }

  /**
   * Determ nes t  appropr ate Overr de Not f cat on model values based on t  cl ent
   * @param target          Target that w ll be rece v ng t  push recom ndat on
   * @param dev ce nfoOpt   Target's Dev ce  nfo
   * @param collapse d      Collapse  D determ ned by Overr deNot f cat onUt l
   * @param  mpress on ds    mpress on  Ds of prev ously sent Overr de Not f cat ons
   * @param mlScore         Open/NTab cl ck rank ng score of t  current push cand date
   * @param pushCap         Push cap for t  target
   * @return                Map cons st ng of t  model values that need to be added to t   b s2 Request
   */
  def getCl entSpec f cOverr deModelValues(
    target: Target,
    dev ce nfoOpt: Opt on[Dev ce nfo],
    countryCodeOpt: Opt on[Str ng],
    collapse d: Str ng,
     mpress on ds: Seq[Str ng],
    mlScoreOpt: Opt on[Double],
    pushCap:  nt
  ): Map[Str ng, Str ng] = {

    val pr maryDev ce os = PushDev ceUt l. sPr maryDev ce OS(dev ce nfoOpt)
    val pr maryDev ceAndro d = PushDev ceUt l. sPr maryDev ceAndro d(dev ce nfoOpt)

     f (pr maryDev ce os ||
      (pr maryDev ceAndro d &&
      target.params(FSParams.EnableOverr deNot f cat onsSmartPushConf gForAndro d))) {

       f (pr maryDev ce os) addedOverr de osCounter. ncr()
      else addedSmartPushConf gAndro dCounter. ncr()

      val  mpress on dsSeq = {
         f (target.params(FSParams.EnableTarget ds nSmartPushPayload)) {
           f (target.params(FSParams.EnableOverr deNot f cat onsMult pleTarget ds))
             mpress on ds
          else Seq( mpress on ds. ad)
        }
        // Expl c ly enable target d-based overr de for Mag cFanoutSportsEvent cand dates (l ve sport update not f cat ons)
        else  f (self.commonRecType == Mag cFanoutSportsEvent && target.params(
            FSParams.EnableTarget d nSmartPushPayloadForMag cFanoutSportsEvent)) {
          enableTarget dOverr deForMag cFanoutSportsEventCounter. ncr()
          Seq( mpress on ds. ad)
        } else Seq.empty[Str ng]
      }

      val mlScoreMap = mlScoreOpt match {
        case So (mlScore)
             f target.params(FSParams.EnableOverr deNot f cat onsScoreBasedOverr de) =>
          addedMlScoreToPayloadAndro d. ncr()
          Map("score" -> mlScore)
        case _ =>
          noMlScoreAddedToPayload. ncr()
          Map.empty
      }

      val nSlotsMap = {
         f (target.params(FSParams.EnableOverr deNot f cat onsNSlots)) {
           f (target.params(FSParams.EnableOverr deMaxSlotFn)) {
            val nslotFnParam = Cont nuousFunct onParam(
              target
                .params(PushFeatureSw chParams.Overr deMaxSlotFnPushCapKnobs),
              target
                .params(PushFeatureSw chParams.Overr deMaxSlotFnNSlotKnobs),
              target
                .params(PushFeatureSw chParams.Overr deMaxSlotFnPo rKnobs),
              target
                .params(PushFeatureSw chParams.Overr deMaxSlotFn  ght),
              target.params(FSParams.Overr deNot f cat onsMaxNumOfSlots)
            )
            val numOfSlots = Cont nuousFunct on.safeEvaluateFn(
              pushCap,
              nslotFnParam,
              overr deStats.scope("max_nslot_fn"))
            overr deStats.counter("max_not f cat on_slots_num_" + numOfSlots.toStr ng). ncr()
            addedNSlotsToPayload. ncr()
            Map("max_not f cat on_slots" -> numOfSlots)
          } else {
            addedNSlotsToPayload. ncr()
            val numOfSlots = target.params(FSParams.Overr deNot f cat onsMaxNumOfSlots)
            Map("max_not f cat on_slots" -> numOfSlots)
          }
        } else {
          noNSlotsAddedToPayload. ncr()
          Map.empty
        }
      }

      val baseAct onDeta lsMap = Map("target_ ds" ->  mpress on dsSeq)

      val act onDeta lsMap =
        Map("act on_deta ls" -> (baseAct onDeta lsMap ++ nSlotsMap))

      val baseSmartPushConf gMap = Map("not f cat on_act on" -> "REPLACE")

      val customThread d = {
         f (target.params(FSParams.EnableCustomThread dForOverr de)) {
          addedCustomThread dToPayload. ncr()
          Map("custom_thread_ d" ->  mpress on d)
        } else {
          noCustomThread dAddedToPayload. ncr()
          Map.empty
        }
      }

      val smartPushConf gMap =
        JsonMarshal.toJson(
          baseSmartPushConf gMap ++ act onDeta lsMap ++ mlScoreMap ++ customThread d)

      Map("smart_not f cat on_conf gurat on" -> smartPushConf gMap)
    } else  f (pr maryDev ceAndro d) {
      addedOverr deAndro dCounter. ncr()
      Map("not f cat on_ d" -> collapse d, "overr d ng_ mpress on_ d" ->  mpress on ds. ad)
    } else {
      noOverr deDueToDev ce nfoCounter. ncr()
      Map.empty[Str ng, Str ng]
    }
  }
}
