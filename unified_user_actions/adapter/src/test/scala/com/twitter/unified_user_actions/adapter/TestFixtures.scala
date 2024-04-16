package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.ads.cards.thr ftscala.CardEvent
 mport com.tw ter.ads.eventstream.thr ftscala.Engage ntEvent
 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.adserver.thr ftscala. mpress onDataNeededAtEngage ntT  
 mport com.tw ter.adserver.thr ftscala.Cl ent nfo
 mport com.tw ter.adserver.thr ftscala.Engage ntType
 mport com.tw ter.adserver.thr ftscala.D splayLocat on
 mport com.tw ter.cl entapp.thr ftscala.Ampl fyDeta ls
 mport com.tw ter.cl entapp.thr ftscala.CardDeta ls
 mport com.tw ter.cl entapp.thr ftscala.EventDeta ls
 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala. mpress onDeta ls
 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala. d aDeta ls
 mport com.tw ter.cl entapp.thr ftscala. d aDeta lsV2
 mport com.tw ter.cl entapp.thr ftscala. d aType
 mport com.tw ter.cl entapp.thr ftscala.Not f cat onDeta ls
 mport com.tw ter.cl entapp.thr ftscala.Not f cat onTabDeta ls
 mport com.tw ter.cl entapp.thr ftscala.PerformanceDeta ls
 mport com.tw ter.cl entapp.thr ftscala.ReportDeta ls
 mport com.tw ter.cl entapp.thr ftscala.SearchDeta ls
 mport com.tw ter.cl entapp.thr ftscala.Suggest onDeta ls
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.cl entapp.thr ftscala.{T etDeta ls => LogEventT etDeta ls}
 mport com.tw ter.g zmoduck.thr ftscala.UserMod f cat on
 mport com.tw ter.g zmoduck.thr ftscala.Prof le
 mport com.tw ter.g zmoduck.thr ftscala.Auth
 mport com.tw ter.g zmoduck.thr ftscala.UpdateD ff em
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter. b s.thr ftscala.Not f cat onScr be
 mport com.tw ter. b s.thr ftscala.Not f cat onScr beType
 mport com.tw ter. es ce.thr ftscala.Cl entEventContext
 mport com.tw ter. es ce.thr ftscala.T et mpress on
 mport com.tw ter. es ce.thr ftscala.Cl entType
 mport com.tw ter. es ce.thr ftscala.ContextualEventNa space
 mport com.tw ter. es ce.thr ftscala.Engag ngContext
 mport com.tw ter. es ce.thr ftscala.EventS ce
 mport com.tw ter. es ce.thr ftscala. nteract onDeta ls
 mport com.tw ter. es ce.thr ftscala. nteract onEvent
 mport com.tw ter. es ce.thr ftscala. nteract onType
 mport com.tw ter. es ce.thr ftscala. nteract onTargetType
 mport com.tw ter. es ce.thr ftscala.{User dent f er => User dent f er E}
 mport com.tw ter.logbase.thr ftscala.Cl entEventRece ver
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter. d aserv ces.commons.thr ftscala. d aCategory
 mport com.tw ter.not f cat onserv ce.ap .thr ftscala.Not f cat onCl entEvent tadata
 mport com.tw ter.reportflow.thr ftscala.ReportType
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerData
 mport com.tw ter.suggests.controller_data.ho _t ets.v1.thr ftscala.{
  Ho T etsControllerData => Ho T etsControllerDataV1
}
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.thr ftscala.T  l nesTop cControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.v1.thr ftscala.{
  T  l nesTop cControllerData => T  l nesTop cControllerDataV1
}
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.T  
 mport com.tw ter.v deo.analyt cs.thr ftscala.Cl ent d aEvent
 mport com.tw ter.v deo.analyt cs.thr ftscala.Sess onState
 mport com.tw ter.v deo.analyt cs.thr ftscala._
 mport com.tw ter.suggests.controller_data.search_response.v1.thr ftscala.{
  SearchResponseControllerData => SearchResponseControllerDataV1
}
 mport com.tw ter.suggests.controller_data.search_response.thr ftscala.SearchResponseControllerData
 mport com.tw ter.suggests.controller_data.search_response.request.thr ftscala.RequestControllerData
 mport com.tw ter.un f ed_user_act ons.thr ftscala.FeedbackPrompt nfo

object TestF xtures {
  tra  CommonF xture {
    val frozenT  : T   = T  .fromM ll seconds(1658949273000L)

    val user d: Long = 123L
    val author d: Long = 112233L
    val  emT et d: Long = 111L
    val  emProf le d: Long = 123456L
    val ret et ngT et d: Long = 222L
    val quotedT et d: Long = 333L
    val quotedAuthor d: Long = 456L
    val  nReplyToT et d: Long = 444L
    val quot ngT et d: Long = 555L
    val top c d: Long = 1234L
    val trace d: Long = 5678L
    val requestJo n d: Long = 91011L
    val not f cat on d: Str ng = "12345"
    val t et ds: Seq[Long] = Seq[Long](111, 222, 333)
    val reportFlow d: Str ng = "report-flow- d"
  }

  tra  Cl entEventF xture extends CommonF xture {

    val t  stamp = 1001L

    val logBase: LogBase = LogBase(
       pAddress = "",
      transact on d = "",
      t  stamp = 1002L,
      dr ftAdjustedEventCreatedAtMs = So (1001L),
      user d = So (user d),
      cl entEventRece ver = So (Cl entEventRece ver.CesHttp)
    )

    val logBase1: LogBase = LogBase(
       pAddress = "",
      transact on d = "",
      user d = So (user d),
      guest d = So (2L),
      guest dMarket ng = So (2L),
      t  stamp = t  stamp
    )

    def mkSearchResultControllerData(
      queryOpt: Opt on[Str ng],
      queryS ceOpt: Opt on[ nt] = None,
      trace d: Opt on[Long] = None,
      requestJo n d: Opt on[Long] = None
    ): ControllerData = {
      ControllerData.V2(
        ControllerDataV2.SearchResponse(
          SearchResponseControllerData.V1(
            SearchResponseControllerDataV1(requestControllerData = So (
              RequestControllerData(
                rawQuery = queryOpt,
                queryS ce = queryS ceOpt,
                trace d = trace d,
                requestJo n d = requestJo n d
              )))
          )))
    }

    val v deoEventEle ntValues: Seq[Str ng] =
      Seq[Str ng](
        "g f_player",
        "per scope_player",
        "platform_ampl fy_card",
        "v deo_player",
        "v ne_player")

    val  nval dV deoEventEle ntValues: Seq[Str ng] =
      Seq[Str ng](
        "dynam c_v deo_ads",
        "l ve_v deo_player",
        "platform_forward_card",
        "v deo_app_card_canvas",
        " tube_player"
      )

    val cl ent d aEvent: Cl ent d aEvent = Cl ent d aEvent(
      sess onState = Sess onState(
        contentV deo dent f er =  d a dent f er. d aPlatform dent f er(
           d aPlatform dent f er( d a d = 123L,  d aCategory =  d aCategory.T etV deo)),
        sess on d = "",
      ),
       d aCl entEventType =  d aEventType. ntentToPlay( ntentToPlay()),
      play ng d aState = Play ng d aState(
        v deoType = V deoType.Content,
         d aAssetUrl = "",
         d a tadata =  d a tadata(publ s r dent f er = Publ s r dent f er
          .Tw terPubl s r dent f er(Tw terPubl s r dent f er(123456L)))
      ),
      playerState = PlayerState( sMuted = false)
    )

    val  d aDeta lsV2:  d aDeta lsV2 =  d aDeta lsV2(
       d a ems = So (
        Seq[ d aDeta ls](
           d aDeta ls(
            content d = So ("456"),
             d aType = So ( d aType.Consu rV deo),
            dynam cAds = So (false)),
           d aDeta ls(
            content d = So ("123"),
             d aType = So ( d aType.Consu rV deo),
            dynam cAds = So (false)),
           d aDeta ls(
            content d = So ("789"),
             d aType = So ( d aType.Consu rV deo),
            dynam cAds = So (false))
        ))
    )

    val cardDeta ls =
      CardDeta ls(ampl fyDeta ls = So (Ampl fyDeta ls(v deoType = So ("content"))))

    val v deo tadata: T etAct on nfo = T etAct on nfo.T etV deoWatch(
      T etV deoWatch(
         d aType = So ( d aType.Consu rV deo),
         sMonet zable = So (false),
        v deoType = So ("content")))

    val not f cat onDeta ls: Not f cat onDeta ls =
      Not f cat onDeta ls( mpress on d = So (not f cat on d))

    val not f cat onTabT etEventDeta ls: Not f cat onTabDeta ls =
      Not f cat onTabDeta ls(
        cl entEvent tadata = So (
          Not f cat onCl entEvent tadata(
            t et ds = So (Seq[Long]( emT et d)),
            upstream d = So (not f cat on d),
            request d = "",
            not f cat on d = "",
            not f cat onCount = 0))
      )

    val not f cat onTabMult T etEventDeta ls: Not f cat onTabDeta ls =
      Not f cat onTabDeta ls(
        cl entEvent tadata = So (
          Not f cat onCl entEvent tadata(
            t et ds = So (t et ds),
            upstream d = So (not f cat on d),
            request d = "",
            not f cat on d = "",
            not f cat onCount = 0))
      )

    val not f cat onTabUnknownEventDeta ls: Not f cat onTabDeta ls =
      Not f cat onTabDeta ls(
        cl entEvent tadata = So (
          Not f cat onCl entEvent tadata(
            upstream d = So (not f cat on d),
            request d = "",
            not f cat on d = "",
            not f cat onCount = 0))
      )

    val t etNot f cat onContent: Not f cat onContent =
      Not f cat onContent.T etNot f cat on(T etNot f cat on( emT et d))

    val mult T etNot f cat onContent: Not f cat onContent =
      Not f cat onContent.Mult T etNot f cat on(Mult T etNot f cat on(t et ds))

    val unknownNot f cat onContent: Not f cat onContent =
      Not f cat onContent.UnknownNot f cat on(UnknownNot f cat on())

    val reportT etCl ck: T etAct on nfo =
      T etAct on nfo.Cl entT etReport(Cl entT etReport( sReportT etDone = false))

    val reportT etDone: T etAct on nfo =
      T etAct on nfo.Cl entT etReport(Cl entT etReport( sReportT etDone = true))

    val reportT etW hReportFlow d: T etAct on nfo =
      T etAct on nfo.Cl entT etReport(
        Cl entT etReport( sReportT etDone = true, reportFlow d = So (reportFlow d)))

    val reportT etW houtReportFlow d: T etAct on nfo =
      T etAct on nfo.Cl entT etReport(
        Cl entT etReport( sReportT etDone = true, reportFlow d = None))

    val reportT etSubm : T etAct on nfo =
      T etAct on nfo.ServerT etReport(
        ServerT etReport(reportFlow d = So (reportFlow d), reportType = So (ReportType.Abuse)))

    val not f cat onTabProductSurface nfo: ProductSurface nfo =
      ProductSurface nfo.Not f cat onTab nfo(Not f cat onTab nfo(not f cat on d = not f cat on d))

    val cl entOpenL nkW hUrl: T etAct on nfo =
      T etAct on nfo.Cl entT etOpenL nk(Cl entT etOpenL nk(url = So ("go/url")))

    val cl entOpenL nkW houtUrl: T etAct on nfo =
      T etAct on nfo.Cl entT etOpenL nk(Cl entT etOpenL nk(url = None))

    val cl entTakeScreenshot: T etAct on nfo =
      T etAct on nfo.Cl entT etTakeScreenshot(
        Cl entT etTakeScreenshot(percentV s ble  ght100k = So (100)))

    // cl ent-event event_na space
    val ceL ngerEventNa space: EventNa space = EventNa space(
      component = So ("stream"),
      ele nt = So ("l nger"),
      act on = So ("results")
    )
    val ceRenderEventNa space: EventNa space = EventNa space(
      component = So ("stream"),
      act on = So ("results")
    )
    val ceT etDeta lsEventNa space1: EventNa space = EventNa space(
      page = So ("t et"),
      sect on = None,
      component = So ("t et"),
      ele nt = None,
      act on = So (" mpress on")
    )
    val ceGalleryEventNa space: EventNa space = EventNa space(
      component = So ("gallery"),
      ele nt = So ("photo"),
      act on = So (" mpress on")
    )
    val ceFavor eEventNa space: EventNa space = EventNa space(act on = So ("favor e"))
    val ceHo Favor eEventNa space: EventNa space =
      EventNa space(page = So ("ho "), act on = So ("favor e"))
    val ceHo LatestFavor eEventNa space: EventNa space =
      EventNa space(page = So ("ho _latest"), act on = So ("favor e"))
    val ceSearchFavor eEventNa space: EventNa space =
      EventNa space(page = So ("search"), act on = So ("favor e"))
    val ceCl ckReplyEventNa space: EventNa space = EventNa space(act on = So ("reply"))
    val ceReplyEventNa space: EventNa space = EventNa space(act on = So ("send_reply"))
    val ceRet etEventNa space: EventNa space = EventNa space(act on = So ("ret et"))
    val ceV deoPlayback25: EventNa space = EventNa space(act on = So ("playback_25"))
    val ceV deoPlayback50: EventNa space = EventNa space(act on = So ("playback_50"))
    val ceV deoPlayback75: EventNa space = EventNa space(act on = So ("playback_75"))
    val ceV deoPlayback95: EventNa space = EventNa space(act on = So ("playback_95"))
    val ceV deoPlayFromTap: EventNa space = EventNa space(act on = So ("play_from_tap"))
    val ceV deoQual yV ew: EventNa space = EventNa space(act on = So ("v deo_qual y_v ew"))
    val ceV deoV ew: EventNa space = EventNa space(act on = So ("v deo_v ew"))
    val ceV deoMrcV ew: EventNa space = EventNa space(act on = So ("v deo_mrc_v ew"))
    val ceV deoV ewThreshold: EventNa space = EventNa space(act on = So ("v ew_threshold"))
    val ceV deoCtaUrlCl ck: EventNa space = EventNa space(act on = So ("cta_url_cl ck"))
    val ceV deoCtaWatchCl ck: EventNa space = EventNa space(act on = So ("cta_watch_cl ck"))
    val cePhotoExpand: EventNa space =
      EventNa space(ele nt = So ("platform_photo_card"), act on = So ("cl ck"))
    val ceCardCl ck: EventNa space =
      EventNa space(ele nt = So ("platform_card"), act on = So ("cl ck"))
    val ceCardOpenApp: EventNa space = EventNa space(act on = So ("open_app"))
    val ceCardApp nstallAttempt: EventNa space = EventNa space(act on = So (" nstall_app"))
    val cePollCardVote1: EventNa space =
      EventNa space(ele nt = So ("platform_card"), act on = So ("vote"))
    val cePollCardVote2: EventNa space =
      EventNa space(ele nt = So ("platform_forward_card"), act on = So ("vote"))
    val ce nt onCl ck: EventNa space =
      EventNa space(ele nt = So (" nt on"), act on = So ("cl ck"))
    val ceV deoPlaybackStart: EventNa space = EventNa space(act on = So ("playback_start"))
    val ceV deoPlaybackComplete: EventNa space = EventNa space(act on = So ("playback_complete"))
    val ceCl ckHashtag: EventNa space = EventNa space(act on = So ("hashtag_cl ck"))
    val ceTop cFollow1: EventNa space =
      EventNa space(ele nt = So ("top c"), act on = So ("follow"))
    val ceOpenL nk: EventNa space = EventNa space(act on = So ("open_l nk"))
    val ceTakeScreenshot: EventNa space = EventNa space(act on = So ("take_screenshot"))
    val ceTop cFollow2: EventNa space =
      EventNa space(ele nt = So ("soc al_proof"), act on = So ("follow"))
    val ceTop cFollow3: EventNa space =
      EventNa space(ele nt = So ("feedback_follow_top c"), act on = So ("cl ck"))
    val ceTop cUnfollow1: EventNa space =
      EventNa space(ele nt = So ("top c"), act on = So ("unfollow"))
    val ceTop cUnfollow2: EventNa space =
      EventNa space(ele nt = So ("soc al_proof"), act on = So ("unfollow"))
    val ceTop cUnfollow3: EventNa space =
      EventNa space(ele nt = So ("feedback_unfollow_top c"), act on = So ("cl ck"))
    val ceTop cNot nterested n1: EventNa space =
      EventNa space(ele nt = So ("top c"), act on = So ("not_ nterested"))
    val ceTop cNot nterested n2: EventNa space =
      EventNa space(ele nt = So ("feedback_not_ nterested_ n_top c"), act on = So ("cl ck"))
    val ceTop cUndoNot nterested n1: EventNa space =
      EventNa space(ele nt = So ("top c"), act on = So ("un_not_ nterested"))
    val ceTop cUndoNot nterested n2: EventNa space =
      EventNa space(ele nt = So ("feedback_not_ nterested_ n_top c"), act on = So ("undo"))
    val ceProf leFollowAttempt: EventNa space =
      EventNa space(act on = So ("follow_attempt"))
    val ceT etFavor eAttempt: EventNa space =
      EventNa space(act on = So ("favor e_attempt"))
    val ceT etRet etAttempt: EventNa space =
      EventNa space(act on = So ("ret et_attempt"))
    val ceT etReplyAttempt: EventNa space =
      EventNa space(act on = So ("reply_attempt"))
    val ceCl entCTALog nCl ck: EventNa space =
      EventNa space(act on = So ("log n"))
    val ceCl entCTALog nStart: EventNa space =
      EventNa space(page = So ("log n"), act on = So ("show"))
    val ceCl entCTALog nSuccess: EventNa space =
      EventNa space(page = So ("log n"), act on = So ("success"))
    val ceCl entCTAS gnupCl ck: EventNa space =
      EventNa space(act on = So ("s gnup"))
    val ceCl entCTAS gnupSuccess: EventNa space =
      EventNa space(page = So ("s gnup"), act on = So ("success"))
    val ceNot f cat onOpen: EventNa space = EventNa space(
      page = So ("not f cat on"),
      sect on = So ("status_bar"),
      component = None,
      act on = So ("open"))
    val ceNot f cat onCl ck: EventNa space = EventNa space(
      page = So ("ntab"),
      sect on = So ("all"),
      component = So ("urt"),
      ele nt = So ("users_l ked_y _t et"),
      act on = So ("nav gate"))
    val ceTypea adCl ck: EventNa space =
      EventNa space(ele nt = So ("typea ad"), act on = So ("cl ck"))
    val ceT etReport: EventNa space = EventNa space(ele nt = So ("report_t et"))
    def ceEventNa space(ele nt: Str ng, act on: Str ng): EventNa space =
      EventNa space(ele nt = So (ele nt), act on = So (act on))
    def ceT etReportFlow(page: Str ng, act on: Str ng): EventNa space =
      EventNa space(ele nt = So ("t cket"), page = So (page), act on = So (act on))
    val ceNot f cat onSeeLessOften: EventNa space = EventNa space(
      page = So ("ntab"),
      sect on = So ("all"),
      component = So ("urt"),
      act on = So ("see_less_often"))
    val ceNot f cat onD sm ss: EventNa space = EventNa space(
      page = So ("not f cat on"),
      sect on = So ("status_bar"),
      component = None,
      act on = So ("d sm ss"))
    val ceSearchResultsRelevant: EventNa space = EventNa space(
      page = So ("search"),
      component = So ("d d_ _f nd_ _module"),
      ele nt = So (" s_relevant"),
      act on = So ("cl ck")
    )
    val ceSearchResultsNotRelevant: EventNa space = EventNa space(
      page = So ("search"),
      component = So ("d d_ _f nd_ _module"),
      ele nt = So ("not_relevant"),
      act on = So ("cl ck")
    )
    val ceT etRelevantToSearch: EventNa space = EventNa space(
      page = So ("search"),
      component = So ("relevance_prompt_module"),
      ele nt = So (" s_relevant"),
      act on = So ("cl ck"))
    val ceT etNotRelevantToSearch: EventNa space = EventNa space(
      page = So ("search"),
      component = So ("relevance_prompt_module"),
      ele nt = So ("not_relevant"),
      act on = So ("cl ck"))
    val ceProf leBlock: EventNa space =
      EventNa space(page = So ("prof le"), act on = So ("block"))
    val ceProf leUnblock: EventNa space =
      EventNa space(page = So ("prof le"), act on = So ("unblock"))
    val ceProf leMute: EventNa space =
      EventNa space(page = So ("prof le"), act on = So ("mute_user"))
    val ceProf leReport: EventNa space =
      EventNa space(page = So ("prof le"), act on = So ("report"))
    val ceProf leShow: EventNa space =
      EventNa space(page = So ("prof le"), act on = So ("show"))
    val ceProf leFollow: EventNa space =
      EventNa space(act on = So ("follow"))
    val ceProf leCl ck: EventNa space =
      EventNa space(act on = So ("prof le_cl ck"))
    val ceT etFollowAuthor1: EventNa space = EventNa space(
      act on = So ("cl ck"),
      ele nt = So ("follow")
    )
    val ceT etFollowAuthor2: EventNa space = EventNa space(
      act on = So ("follow")
    )
    val ceT etUnfollowAuthor1: EventNa space = EventNa space(
      act on = So ("cl ck"),
      ele nt = So ("unfollow")
    )
    val ceT etUnfollowAuthor2: EventNa space = EventNa space(
      act on = So ("unfollow")
    )
    val ceT etBlockAuthor: EventNa space = EventNa space(
      page = So ("prof le"),
      sect on = So ("t ets"),
      component = So ("t et"),
      act on = So ("cl ck"),
      ele nt = So ("block")
    )
    val ceT etUnblockAuthor: EventNa space = EventNa space(
      sect on = So ("t ets"),
      component = So ("t et"),
      act on = So ("cl ck"),
      ele nt = So ("unblock")
    )
    val ceT etMuteAuthor: EventNa space = EventNa space(
      component = So ("suggest_sc_t et"),
      act on = So ("cl ck"),
      ele nt = So ("mute")
    )
    val ceT etCl ck: EventNa space =
      EventNa space(ele nt = So ("t et"), act on = So ("cl ck"))
    val ceT etCl ckProf le: EventNa space = EventNa space(
      component = So ("t et"),
      ele nt = So ("user"),
      act on = So ("prof le_cl ck"))
    val ceAppEx : EventNa space =
      EventNa space(page = So ("app"), act on = So ("beco _ nact ve"))

    // UUA cl ent_event_na space
    val uuaL ngerCl entEventNa space: Cl entEventNa space = Cl entEventNa space(
      component = So ("stream"),
      ele nt = So ("l nger"),
      act on = So ("results")
    )
    val uuaRenderCl entEventNa space: Cl entEventNa space = Cl entEventNa space(
      component = So ("stream"),
      act on = So ("results")
    )
    val ceT etDeta lsCl entEventNa space1: Cl entEventNa space = Cl entEventNa space(
      page = So ("t et"),
      sect on = None,
      component = So ("t et"),
      ele nt = None,
      act on = So (" mpress on")
    )
    val ceT etDeta lsCl entEventNa space2: Cl entEventNa space = Cl entEventNa space(
      page = So ("t et"),
      sect on = None,
      component = So ("suggest_ranked_l st_t et"),
      ele nt = None,
      act on = So (" mpress on")
    )
    val ceT etDeta lsCl entEventNa space3: Cl entEventNa space = Cl entEventNa space(
      page = So ("t et"),
      sect on = None,
      component = None,
      ele nt = None,
      act on = So (" mpress on")
    )
    val ceT etDeta lsCl entEventNa space4: Cl entEventNa space = Cl entEventNa space(
      page = So ("t et"),
      sect on = None,
      component = None,
      ele nt = None,
      act on = So ("show")
    )
    val ceT etDeta lsCl entEventNa space5: Cl entEventNa space = Cl entEventNa space(
      page = So ("t et"),
      sect on = So ("land ng"),
      component = None,
      ele nt = None,
      act on = So ("show")
    )
    val ceGalleryCl entEventNa space: Cl entEventNa space = Cl entEventNa space(
      component = So ("gallery"),
      ele nt = So ("photo"),
      act on = So (" mpress on")
    )
    val uuaFavor eCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("favor e"))
    val uuaHo Favor eCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(page = So ("ho "), act on = So ("favor e"))
    val uuaSearchFavor eCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(page = So ("search"), act on = So ("favor e"))
    val uuaHo LatestFavor eCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(page = So ("ho _latest"), act on = So ("favor e"))
    val uuaCl ckReplyCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("reply"))
    val uuaReplyCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("send_reply"))
    val uuaRet etCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("ret et"))
    val uuaV deoPlayback25Cl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("playback_25"))
    val uuaV deoPlayback50Cl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("playback_50"))
    val uuaV deoPlayback75Cl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("playback_75"))
    val uuaV deoPlayback95Cl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("playback_95"))
    val uuaOpenL nkCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("open_l nk"))
    val uuaTakeScreenshotCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("take_screenshot"))
    val uuaV deoPlayFromTapCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("play_from_tap"))
    val uuaV deoQual yV ewCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("v deo_qual y_v ew"))
    val uuaV deoV ewCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("v deo_v ew"))
    val uuaV deoMrcV ewCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("v deo_mrc_v ew"))
    val uuaV deoV ewThresholdCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("v ew_threshold"))
    val uuaV deoCtaUrlCl ckCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("cta_url_cl ck"))
    val uuaV deoCtaWatchCl ckCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("cta_watch_cl ck"))
    val uuaPhotoExpandCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("platform_photo_card"), act on = So ("cl ck"))
    val uuaCardCl ckCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("platform_card"), act on = So ("cl ck"))
    val uuaCardOpenAppCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("open_app"))
    val uuaCardApp nstallAttemptCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So (" nstall_app"))
    val uuaPollCardVote1Cl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("platform_card"), act on = So ("vote"))
    val uuaPollCardVote2Cl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("platform_forward_card"), act on = So ("vote"))
    val uua nt onCl ckCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(ele nt = So (" nt on"), act on = So ("cl ck"))
    val uuaV deoPlaybackStartCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("playback_start"))
    val uuaV deoPlaybackCompleteCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("playback_complete"))
    val uuaCl ckHashtagCl entEventNa space: Cl entEventNa space =
      Cl entEventNa space(act on = So ("hashtag_cl ck"))
    val uuaTop cFollowCl entEventNa space1: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("top c"), act on = So ("follow"))
    val uuaTop cFollowCl entEventNa space2: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("soc al_proof"), act on = So ("follow"))
    val uuaTop cFollowCl entEventNa space3: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("feedback_follow_top c"), act on = So ("cl ck"))
    val uuaTop cUnfollowCl entEventNa space1: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("top c"), act on = So ("unfollow"))
    val uuaTop cUnfollowCl entEventNa space2: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("soc al_proof"), act on = So ("unfollow"))
    val uuaTop cUnfollowCl entEventNa space3: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("feedback_unfollow_top c"), act on = So ("cl ck"))
    val uuaTop cNot nterested nCl entEventNa space1: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("top c"), act on = So ("not_ nterested"))
    val uuaTop cNot nterested nCl entEventNa space2: Cl entEventNa space =
      Cl entEventNa space(
        ele nt = So ("feedback_not_ nterested_ n_top c"),
        act on = So ("cl ck"))
    val uuaTop cUndoNot nterested nCl entEventNa space1: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("top c"), act on = So ("un_not_ nterested"))
    val uuaTop cUndoNot nterested nCl entEventNa space2: Cl entEventNa space =
      Cl entEventNa space(
        ele nt = So ("feedback_not_ nterested_ n_top c"),
        act on = So ("undo"))
    val uuaProf leFollowAttempt: Cl entEventNa space =
      Cl entEventNa space(act on = So ("follow_attempt"))
    val uuaT etFavor eAttempt: Cl entEventNa space =
      Cl entEventNa space(act on = So ("favor e_attempt"))
    val uuaT etRet etAttempt: Cl entEventNa space =
      Cl entEventNa space(act on = So ("ret et_attempt"))
    val uuaT etReplyAttempt: Cl entEventNa space =
      Cl entEventNa space(act on = So ("reply_attempt"))
    val uuaCl entCTALog nCl ck: Cl entEventNa space =
      Cl entEventNa space(act on = So ("log n"))
    val uuaCl entCTALog nStart: Cl entEventNa space =
      Cl entEventNa space(page = So ("log n"), act on = So ("show"))
    val uuaCl entCTALog nSuccess: Cl entEventNa space =
      Cl entEventNa space(page = So ("log n"), act on = So ("success"))
    val uuaCl entCTAS gnupCl ck: Cl entEventNa space =
      Cl entEventNa space(act on = So ("s gnup"))
    val uuaCl entCTAS gnupSuccess: Cl entEventNa space =
      Cl entEventNa space(page = So ("s gnup"), act on = So ("success"))
    val uuaNot f cat onOpen: Cl entEventNa space =
      Cl entEventNa space(
        page = So ("not f cat on"),
        sect on = So ("status_bar"),
        component = None,
        act on = So ("open"))
    val uuaNot f cat onCl ck: Cl entEventNa space =
      Cl entEventNa space(
        page = So ("ntab"),
        sect on = So ("all"),
        component = So ("urt"),
        ele nt = So ("users_l ked_y _t et"),
        act on = So ("nav gate"))
    val uuaT etReport: Cl entEventNa space = Cl entEventNa space(ele nt = So ("report_t et"))
    val uuaT etFollowAuthor1: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("follow"), act on = So ("cl ck"))
    val uuaT etFollowAuthor2: Cl entEventNa space =
      Cl entEventNa space(act on = So ("follow"))
    val uuaT etUnfollowAuthor1: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("unfollow"), act on = So ("cl ck"))
    val uuaT etUnfollowAuthor2: Cl entEventNa space =
      Cl entEventNa space(act on = So ("unfollow"))
    val uuaNot f cat onSeeLessOften: Cl entEventNa space = Cl entEventNa space(
      page = So ("ntab"),
      sect on = So ("all"),
      component = So ("urt"),
      act on = So ("see_less_often"))
    def uuaCl entEventNa space(ele nt: Str ng, act on: Str ng): Cl entEventNa space =
      Cl entEventNa space(ele nt = So (ele nt), act on = So (act on))
    def uuaT etReportFlow(page: Str ng, act on: Str ng): Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("t cket"), page = So (page), act on = So (act on))
    val uuaT etCl ck: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("t et"), act on = So ("cl ck"))
    def uuaT etCl ckProf le: Cl entEventNa space = Cl entEventNa space(
      component = So ("t et"),
      ele nt = So ("user"),
      act on = So ("prof le_cl ck"))
    val uuaNot f cat onD sm ss: Cl entEventNa space = Cl entEventNa space(
      page = So ("not f cat on"),
      sect on = So ("status_bar"),
      component = None,
      act on = So ("d sm ss"))
    val uuaTypea adCl ck: Cl entEventNa space =
      Cl entEventNa space(ele nt = So ("typea ad"), act on = So ("cl ck"))
    val uuaSearchResultsRelevant: Cl entEventNa space = Cl entEventNa space(
      page = So ("search"),
      component = So ("d d_ _f nd_ _module"),
      ele nt = So (" s_relevant"),
      act on = So ("cl ck")
    )
    val uuaSearchResultsNotRelevant: Cl entEventNa space = Cl entEventNa space(
      page = So ("search"),
      component = So ("d d_ _f nd_ _module"),
      ele nt = So ("not_relevant"),
      act on = So ("cl ck")
    )
    val uuaT etRelevantToSearch: Cl entEventNa space = Cl entEventNa space(
      page = So ("search"),
      component = So ("relevance_prompt_module"),
      ele nt = So (" s_relevant"),
      act on = So ("cl ck"))
    val uuaT etNotRelevantToSearch: Cl entEventNa space = Cl entEventNa space(
      page = So ("search"),
      component = So ("relevance_prompt_module"),
      ele nt = So ("not_relevant"),
      act on = So ("cl ck"))
    val uuaProf leBlock: Cl entEventNa space =
      Cl entEventNa space(page = So ("prof le"), act on = So ("block"))
    val uuaProf leUnblock: Cl entEventNa space =
      Cl entEventNa space(page = So ("prof le"), act on = So ("unblock"))
    val uuaProf leMute: Cl entEventNa space =
      Cl entEventNa space(page = So ("prof le"), act on = So ("mute_user"))
    val uuaProf leReport: Cl entEventNa space =
      Cl entEventNa space(page = So ("prof le"), act on = So ("report"))
    val uuaProf leShow: Cl entEventNa space =
      Cl entEventNa space(page = So ("prof le"), act on = So ("show"))
    val uuaProf leFollow: Cl entEventNa space =
      Cl entEventNa space(act on = So ("follow"))
    val uuaProf leCl ck: Cl entEventNa space =
      Cl entEventNa space(act on = So ("prof le_cl ck"))
    val uuaT etBlockAuthor: Cl entEventNa space = Cl entEventNa space(
      page = So ("prof le"),
      sect on = So ("t ets"),
      component = So ("t et"),
      act on = So ("cl ck"),
      ele nt = So ("block")
    )
    val uuaT etUnblockAuthor: Cl entEventNa space = Cl entEventNa space(
      sect on = So ("t ets"),
      component = So ("t et"),
      act on = So ("cl ck"),
      ele nt = So ("unblock")
    )
    val uuaT etMuteAuthor: Cl entEventNa space = Cl entEventNa space(
      component = So ("suggest_sc_t et"),
      act on = So ("cl ck"),
      ele nt = So ("mute")
    )
    val uuaAppEx : Cl entEventNa space =
      Cl entEventNa space(page = So ("app"), act on = So ("beco _ nact ve"))

    //  lper  thods for creat ng cl ent-events and UUA objects
    def mkLogEvent(
      eventNa : Str ng = "",
      eventNa space: Opt on[EventNa space],
      eventDeta ls: Opt on[EventDeta ls] = None,
      logBase: Opt on[LogBase] = None,
      pushNot f cat onDeta ls: Opt on[Not f cat onDeta ls] = None,
      reportDeta ls: Opt on[ReportDeta ls] = None,
      searchDeta ls: Opt on[SearchDeta ls] = None,
      performanceDeta ls: Opt on[PerformanceDeta ls] = None
    ): LogEvent = LogEvent(
      eventNa  = eventNa ,
      eventNa space = eventNa space,
      eventDeta ls = eventDeta ls,
      logBase = logBase,
      not f cat onDeta ls = pushNot f cat onDeta ls,
      reportDeta ls = reportDeta ls,
      searchDeta ls = searchDeta ls,
      performanceDeta ls = performanceDeta ls
    )

    def act onTowardDefaultT etEvent(
      eventNa space: Opt on[EventNa space],
       mpress onDeta ls: Opt on[ mpress onDeta ls] = None,
      suggest onDeta ls: Opt on[Suggest onDeta ls] = None,
       em d: Opt on[Long] = So ( emT et d),
       d aDeta lsV2: Opt on[ d aDeta lsV2] = None,
      cl ent d aEvent: Opt on[Cl ent d aEvent] = None,
       emTypeOpt: Opt on[ emType] = So ( emType.T et),
      author d: Opt on[Long] = None,
       sFollo dByAct ngUser: Opt on[Boolean] = None,
       sFollow ngAct ngUser: Opt on[Boolean] = None,
      not f cat onTabDeta ls: Opt on[Not f cat onTabDeta ls] = None,
      reportDeta ls: Opt on[ReportDeta ls] = None,
      logBase: LogBase = logBase,
      t etPos  on: Opt on[ nt] = None,
      promoted d: Opt on[Str ng] = None,
      url: Opt on[Str ng] = None,
      targets: Opt on[Seq[LogEvent em]] = None,
      percentV s ble  ght100k: Opt on[ nt] = None,
      searchDeta ls: Opt on[SearchDeta ls] = None,
      cardDeta ls: Opt on[CardDeta ls] = None
    ): LogEvent =
      mkLogEvent(
        eventNa  = "act on_toward_default_t et_event",
        eventNa space = eventNa space,
        reportDeta ls = reportDeta ls,
        eventDeta ls = So (
          EventDeta ls(
            url = url,
             ems = So (
              Seq(LogEvent em(
                 d =  em d,
                percentV s ble  ght100k = percentV s ble  ght100k,
                 emType =  emTypeOpt,
                 mpress onDeta ls =  mpress onDeta ls,
                suggest onDeta ls = suggest onDeta ls,
                 d aDeta lsV2 =  d aDeta lsV2,
                cl ent d aEvent = cl ent d aEvent,
                cardDeta ls = cardDeta ls,
                t etDeta ls = author d.map {  d => LogEventT etDeta ls(author d = So ( d)) },
                 sV e rFollowsT etAuthor =  sFollo dByAct ngUser,
                 sT etAuthorFollowsV e r =  sFollow ngAct ngUser,
                not f cat onTabDeta ls = not f cat onTabDeta ls,
                pos  on = t etPos  on,
                promoted d = promoted d
              ))),
            targets = targets
          )
        ),
        logBase = So (logBase),
        searchDeta ls = searchDeta ls
      )

    def act onTowardReplyEvent(
      eventNa space: Opt on[EventNa space],
       nReplyToT et d: Long =  nReplyToT et d,
       mpress onDeta ls: Opt on[ mpress onDeta ls] = None
    ): LogEvent =
      mkLogEvent(
        eventNa  = "act on_toward_reply_event",
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls(
             ems = So (
              Seq(
                LogEvent em(
                   d = So ( emT et d),
                   emType = So ( emType.T et),
                   mpress onDeta ls =  mpress onDeta ls,
                  t etDeta ls =
                    So (LogEventT etDeta ls( nReplyToT et d = So ( nReplyToT et d)))
                ))
            )
          )
        ),
        logBase = So (logBase)
      )

    def act onTowardRet etEvent(
      eventNa space: Opt on[EventNa space],
       nReplyToT et d: Opt on[Long] = None,
       mpress onDeta ls: Opt on[ mpress onDeta ls] = None
    ): LogEvent =
      mkLogEvent(
        eventNa  = "act on_toward_ret et_event",
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls(
             ems = So (
              Seq(LogEvent em(
                 d = So ( emT et d),
                 emType = So ( emType.T et),
                 mpress onDeta ls =  mpress onDeta ls,
                t etDeta ls = So (LogEventT etDeta ls(
                  ret et ngT et d = So (ret et ngT et d),
                   nReplyToT et d =  nReplyToT et d))
              )))
          )
        ),
        logBase = So (logBase)
      )

    def act onTowardQuoteEvent(
      eventNa space: Opt on[EventNa space],
       nReplyToT et d: Opt on[Long] = None,
      quotedAuthor d: Opt on[Long] = None,
       mpress onDeta ls: Opt on[ mpress onDeta ls] = None
    ): LogEvent =
      mkLogEvent(
        eventNa  = "act on_toward_quote_event",
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls(
             ems = So (
              Seq(
                LogEvent em(
                   d = So ( emT et d),
                   emType = So ( emType.T et),
                   mpress onDeta ls =  mpress onDeta ls,
                  t etDeta ls = So (
                    LogEventT etDeta ls(
                      quotedT et d = So (quotedT et d),
                       nReplyToT et d =  nReplyToT et d,
                      quotedAuthor d = quotedAuthor d))
                ))
            )
          )
        ),
        logBase = So (logBase)
      )

    def act onTowardRet etEventW hReplyAndQuote(
      eventNa space: Opt on[EventNa space],
       nReplyToT et d: Long =  nReplyToT et d,
       mpress onDeta ls: Opt on[ mpress onDeta ls] = None
    ): LogEvent = mkLogEvent(
      eventNa  = "act on_toward_ret et_event_w h_reply_and_quote",
      eventNa space = eventNa space,
      eventDeta ls = So (
        EventDeta ls(
           ems = So (
            Seq(LogEvent em(
               d = So ( emT et d),
               emType = So ( emType.T et),
               mpress onDeta ls =  mpress onDeta ls,
              t etDeta ls = So (
                LogEventT etDeta ls(
                  ret et ngT et d = So (ret et ngT et d),
                  quotedT et d = So (quotedT et d),
                   nReplyToT et d = So ( nReplyToT et d),
                ))
            )))
        )
      ),
      logBase = So (logBase)
    )

    def pushNot f cat onEvent(
      eventNa space: Opt on[EventNa space],
       em d: Opt on[Long] = So ( emT et d),
       emTypeOpt: Opt on[ emType] = So ( emType.T et),
      not f cat onDeta ls: Opt on[Not f cat onDeta ls],
    ): LogEvent =
      mkLogEvent(
        eventNa  = "push_not f cat on_open",
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls(
             ems = So (
              Seq(
                LogEvent em(
                   d =  em d,
                   emType =  emTypeOpt,
                ))))
        ),
        logBase = So (logBase),
        pushNot f cat onDeta ls = not f cat onDeta ls
      )

    def act onTowardNot f cat onEvent(
      eventNa space: Opt on[EventNa space],
      not f cat onTabDeta ls: Opt on[Not f cat onTabDeta ls],
    ): LogEvent =
      mkLogEvent(
        eventNa  = "not f cat on_event",
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls( ems =
            So (Seq(LogEvent em(not f cat onTabDeta ls = not f cat onTabDeta ls))))),
        logBase = So (logBase)
      )

    def prof leCl ckEvent(eventNa space: Opt on[EventNa space]): LogEvent =
      mkLogEvent(
        eventNa  = "prof le_cl ck",
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls( ems = So (Seq(
            LogEvent em( d = So (user d),  emType = So ( emType.User)),
            LogEvent em(
               d = So ( emT et d),
               emType = So ( emType.T et),
              t etDeta ls = So (LogEventT etDeta ls(author d = So (author d))))
          )))),
        logBase = So (logBase)
      )

    def act onTowardProf leEvent(
      eventNa : Str ng,
      eventNa space: Opt on[EventNa space]
    ): LogEvent =
      mkLogEvent(
        eventNa  = eventNa ,
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls( ems = So (
            Seq(
              LogEvent em( d = So ( emProf le d),  emType = So ( emType.User))
            )))),
        logBase = So (logBase)
      )

    def t etAct onTowardAuthorEvent(
      eventNa : Str ng,
      eventNa space: Opt on[EventNa space]
    ): LogEvent =
      mkLogEvent(
        eventNa  = eventNa ,
        eventNa space = eventNa space,
        eventDeta ls = So (
          EventDeta ls( ems = So (Seq(
            LogEvent em( d = So (user d),  emType = So ( emType.User)),
            LogEvent em(
               d = So ( emT et d),
               emType = So ( emType.T et),
              t etDeta ls = So (LogEventT etDeta ls(author d = So (author d))))
          )))),
        logBase = So (logBase)
      )

    def act onTowardsTypea adEvent(
      eventNa space: Opt on[EventNa space],
      targets: Opt on[Seq[LogEvent em]],
      searchQuery: Str ng
    ): LogEvent =
      mkLogEvent(
        eventNa space = eventNa space,
        eventDeta ls = So (EventDeta ls(targets = targets)),
        logBase = So (logBase),
        searchDeta ls = So (SearchDeta ls(query = So (searchQuery)))
      )
    def act onTowardSearchResultPageEvent(
      eventNa space: Opt on[EventNa space],
      searchDeta ls: Opt on[SearchDeta ls],
       ems: Opt on[Seq[LogEvent em]] = None
    ): LogEvent =
      mkLogEvent(
        eventNa space = eventNa space,
        eventDeta ls = So (EventDeta ls( ems =  ems)),
        logBase = So (logBase),
        searchDeta ls = searchDeta ls
      )

    def act onTowardsUasEvent(
      eventNa space: Opt on[EventNa space],
      cl entApp d: Opt on[Long],
      durat on: Opt on[Long]
    ): LogEvent =
      mkLogEvent(
        eventNa space = eventNa space,
        logBase = So (logBase.copy(cl entApp d = cl entApp d)),
        performanceDeta ls = So (PerformanceDeta ls(durat onMs = durat on))
      )

    def mkUUAEvent tadata(
      cl entEventNa space: Opt on[Cl entEventNa space],
      trace d: Opt on[Long] = None,
      requestJo n d: Opt on[Long] = None,
      cl entApp d: Opt on[Long] = None
    ): Event tadata = Event tadata(
      s ceT  stampMs = 1001L,
      rece vedT  stampMs = frozenT  . nM ll seconds,
      s ceL neage = S ceL neage.Cl entEvents,
      cl entEventNa space = cl entEventNa space,
      trace d = trace d,
      requestJo n d = requestJo n d,
      cl entApp d = cl entApp d
    )

    def mkExpectedUUAForAct onTowardDefaultT etEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       nReplyToT et d: Opt on[Long] = None,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      top c d: Opt on[Long] = None,
      author nfo: Opt on[Author nfo] = None,
      productSurface: Opt on[ProductSurface] = None,
      productSurface nfo: Opt on[ProductSurface nfo] = None,
      t etPos  on: Opt on[ nt] = None,
      promoted d: Opt on[Str ng] = None,
      trace dOpt: Opt on[Long] = None,
      requestJo n dOpt: Opt on[Long] = None,
      guest dMarket ngOpt: Opt on[Long] = None
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er =
        User dent f er(user d = So (user d), guest dMarket ng = guest dMarket ngOpt),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
           nReplyToT et d =  nReplyToT et d,
          t etAct on nfo = t etAct on nfo,
          act onT etTop cSoc alProof d = top c d,
          act onT etAuthor nfo = author nfo,
          t etPos  on = t etPos  on,
          promoted d = promoted d
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(
        cl entEventNa space = cl entEventNa space,
        trace d = trace dOpt,
        requestJo n d = requestJo n dOpt
      ),
      productSurface = productSurface,
      productSurface nfo = productSurface nfo
    )

    def mkExpectedUUAForAct onTowardReplyEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      author nfo: Opt on[Author nfo] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
           nReplyToT et d = So ( nReplyToT et d),
          t etAct on nfo = t etAct on nfo,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForAct onTowardRet etEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       nReplyToT et d: Opt on[Long] = None,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      author nfo: Opt on[Author nfo] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
          ret et ngT et d = So (ret et ngT et d),
           nReplyToT et d =  nReplyToT et d,
          t etAct on nfo = t etAct on nfo,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForAct onTowardQuoteEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       nReplyToT et d: Opt on[Long] = None,
      quotedAuthor d: Opt on[Long] = None,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      author nfo: Opt on[Author nfo] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
          quotedT et d = So (quotedT et d),
          quotedAuthor d = quotedAuthor d,
           nReplyToT et d =  nReplyToT et d,
          t etAct on nfo = t etAct on nfo,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForAct onTowardQuot ngEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       nReplyToT et d: Opt on[Long] = None,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      author nfo: Opt on[Author nfo] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = quotedT et d,
          quot ngT et d = So ( emT et d),
           nReplyToT et d =  nReplyToT et d,
          t etAct on nfo = t etAct on nfo,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       nReplyToT et d: Long =  nReplyToT et d,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      author nfo: Opt on[Author nfo] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
          ret et ngT et d = So (ret et ngT et d),
          quotedT et d = So (quotedT et d),
           nReplyToT et d = So ( nReplyToT et d),
          t etAct on nfo = t etAct on nfo,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuot ng(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       nReplyToT et d: Long =  nReplyToT et d,
      t etAct on nfo: Opt on[T etAct on nfo] = None,
      author nfo: Opt on[Author nfo] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = quotedT et d,
          quot ngT et d = So ( emT et d),
          t etAct on nfo = t etAct on nfo,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForAct onTowardTop cEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      top c d: Long,
      trace d: Opt on[Long] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Top c nfo(
        Top c nfo(
          act onTop c d = top c d,
        )
      ),
      act onType = act onType,
      event tadata =
        mkUUAEvent tadata(cl entEventNa space = cl entEventNa space, trace d = trace d)
    )

    def mkExpectedUUAForNot f cat onEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      not f cat onContent: Not f cat onContent,
      productSurface: Opt on[ProductSurface],
      productSurface nfo: Opt on[ProductSurface nfo],
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Not f cat on nfo(
        Not f cat on nfo(
          act onNot f cat on d = not f cat on d,
          content = not f cat onContent
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space),
      productSurface = productSurface,
      productSurface nfo = productSurface nfo
    )

    def mkExpectedUUAForProf leCl ck(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      author nfo: Opt on[Author nfo] = None
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
          act onT etAuthor nfo = author nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForT etAct onTowardAuthor(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      author nfo: Opt on[Author nfo] = None,
      t etAct on nfo: Opt on[T etAct on nfo] = None
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
          act onT etAuthor nfo = author nfo,
          t etAct on nfo = t etAct on nfo
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForProf leAct on(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      act onProf le d: Long
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Prof le nfo(
        Prof le nfo(
          act onProf le d = act onProf le d
        )
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForTypea adAct on(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      typea adAct on nfo: Typea adAct on nfo,
      searchQuery: Str ng,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Typea ad nfo(
        Typea ad nfo(act onQuery = searchQuery, typea adAct on nfo = typea adAct on nfo)
      ),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space),
      productSurface = So (ProductSurface.SearchTypea ad),
      productSurface nfo =
        So (ProductSurface nfo.SearchTypea ad nfo(SearchTypea ad nfo(query = searchQuery)))
    )
    def mkExpectedUUAForFeedbackSubm Act on(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      feedbackPrompt nfo: FeedbackPrompt nfo,
      searchQuery: Str ng
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.FeedbackPrompt nfo(feedbackPrompt nfo),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space),
      productSurface = So (ProductSurface.SearchResultsPage),
      productSurface nfo =
        So (ProductSurface nfo.SearchResultsPage nfo(SearchResultsPage nfo(query = searchQuery)))
    )

    def mkExpectedUUAForAct onTowardCTAEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      guest dMarket ngOpt: Opt on[Long]
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er =
        User dent f er(user d = So (user d), guest dMarket ng = guest dMarket ngOpt),
       em =  em.Cta nfo(CTA nfo()),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def mkExpectedUUAForUasEvent(
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
      cl entApp d: Opt on[Long],
      durat on: Opt on[Long]
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Uas nfo(UAS nfo(t  SpentMs = durat on.get)),
      act onType = act onType,
      event tadata =
        mkUUAEvent tadata(cl entEventNa space = cl entEventNa space, cl entApp d = cl entApp d)
    )

    def mkExpectedUUAForCardEvent(
       d: Opt on[Long],
      cl entEventNa space: Opt on[Cl entEventNa space],
      act onType: Act onType,
       emType: Opt on[ emType],
      author d: Opt on[Long],
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Card nfo(
        Card nfo(
           d =  d,
           emType =  emType,
          act onT etAuthor nfo = So (Author nfo(author d = author d)))),
      act onType = act onType,
      event tadata = mkUUAEvent tadata(cl entEventNa space = cl entEventNa space)
    )

    def t  l neTop cControllerData(top c d: Long = top c d): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.T  l nesTop c(
          T  l nesTop cControllerData.V1(
            T  l nesTop cControllerDataV1(
              top c d = top c d,
              top cTypesB map = 1
            )
          )))

    def ho T etControllerData(
      top c d: Long = top c d,
      trace d: Long = trace d
    ): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.Ho T ets(
          Ho T etsControllerData.V1(
            Ho T etsControllerDataV1(
              top c d = So (top c d),
              trace d = So (trace d)
            ))))

    def ho T etControllerDataV2(
       njectedPos  on: Opt on[ nt] = None,
      requestJo n d: Opt on[Long] = None,
      trace d: Opt on[Long] = None
    ): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.Ho T ets(
          Ho T etsControllerData.V1(
            Ho T etsControllerDataV1(
               njectedPos  on =  njectedPos  on,
              trace d = trace d,
              requestJo n d = requestJo n d
            ))))

    // mock cl ent-events
    val ddgEvent: LogEvent = mkLogEvent(
      eventNa  = "ddg",
      eventNa space = So (
        EventNa space(
          page = So ("ddg"),
          act on = So ("exper  nt")
        )
      )
    )

    val q gRankerEvent: LogEvent = mkLogEvent(
      eventNa  = "q g_ranker",
      eventNa space = So (
        EventNa space(
          page = So ("q g_ranker"),
        )
      )
    )

    val t  l neM xerEvent: LogEvent = mkLogEvent(
      eventNa  = "t  l nem xer",
      eventNa space = So (
        EventNa space(
          page = So ("t  l nem xer"),
        )
      )
    )

    val t  l neServ ceEvent: LogEvent = mkLogEvent(
      eventNa  = "t  l neserv ce",
      eventNa space = So (
        EventNa space(
          page = So ("t  l neserv ce"),
        )
      )
    )

    val t etConcServ ceEvent: LogEvent = mkLogEvent(
      eventNa  = "t etconvosvc",
      eventNa space = So (
        EventNa space(
          page = So ("t etconvosvc"),
        )
      )
    )

    val renderNonT et emTypeEvent: LogEvent = mkLogEvent(
      eventNa  = "render non-t et  em-type",
      eventNa space = So (ceRenderEventNa space),
      eventDeta ls = So (
        EventDeta ls(
           ems = So (
            Seq(LogEvent em( emType = So ( emType.Event)))
          )
        )
      )
    )

    val renderDefaultT etW hTop c dEvent: LogEvent = act onTowardDefaultT etEvent(
      eventNa space = So (ceRenderEventNa space),
      suggest onDeta ls =
        So (Suggest onDeta ls(decodedControllerData = So (t  l neTop cControllerData())))
    )

    def renderDefaultT etUserFollowStatusEvent(
      author d: Opt on[Long],
       sFollo dByAct ngUser: Boolean = false,
       sFollow ngAct ngUser: Boolean = false
    ): LogEvent = act onTowardDefaultT etEvent(
      eventNa space = So (ceRenderEventNa space),
      author d = author d,
       sFollo dByAct ngUser = So ( sFollo dByAct ngUser),
       sFollow ngAct ngUser = So ( sFollow ngAct ngUser)
    )

    val l ngerDefaultT etEvent: LogEvent = act onTowardDefaultT etEvent(
      eventNa space = So (ceL ngerEventNa space),
       mpress onDeta ls = So (
         mpress onDeta ls(
          v s b l yStart = So (100L),
          v s b l yEnd = So (105L)
        ))
    )

    val l ngerReplyEvent: LogEvent = act onTowardReplyEvent(
      eventNa space = So (ceL ngerEventNa space),
       mpress onDeta ls = So (
         mpress onDeta ls(
          v s b l yStart = So (100L),
          v s b l yEnd = So (105L)
        ))
    )

    val l ngerRet etEvent: LogEvent = act onTowardRet etEvent(
      eventNa space = So (ceL ngerEventNa space),
       mpress onDeta ls = So (
         mpress onDeta ls(
          v s b l yStart = So (100L),
          v s b l yEnd = So (105L)
        ))
    )

    val l ngerQuoteEvent: LogEvent = act onTowardQuoteEvent(
      eventNa space = So (ceL ngerEventNa space),
       mpress onDeta ls = So (
         mpress onDeta ls(
          v s b l yStart = So (100L),
          v s b l yEnd = So (105L)
        ))
    )

    val l ngerRet etW hReplyAndQuoteEvent: LogEvent = act onTowardRet etEventW hReplyAndQuote(
      eventNa space = So (ceL ngerEventNa space),
       mpress onDeta ls = So (
         mpress onDeta ls(
          v s b l yStart = So (100L),
          v s b l yEnd = So (105L)
        ))
    )

    val replyToDefaultT etOrReplyEvent: LogEvent = act onTowardReplyEvent(
      eventNa space = So (ceReplyEventNa space),
      // s nce t  act on  s reply,  em. d =  nReplyToT et d
       nReplyToT et d =  emT et d,
    )

    val replyToRet etEvent: LogEvent = act onTowardRet etEvent(
      eventNa space = So (ceReplyEventNa space),
      // s nce t  act on  s reply,  em. d =  nReplyToT et d
       nReplyToT et d = So ( emT et d),
    )

    val replyToQuoteEvent: LogEvent = act onTowardQuoteEvent(
      eventNa space = So (ceReplyEventNa space),
      // s nce t  act on  s reply,  em. d =  nReplyToT et d
       nReplyToT et d = So ( emT et d),
    )

    val replyToRet etW hReplyAndQuoteEvent: LogEvent = act onTowardRet etEventW hReplyAndQuote(
      eventNa space = So (ceReplyEventNa space),
      // s nce t  act on  s reply,  em. d =  nReplyToT et d
       nReplyToT et d =  emT et d,
    )

    // expected UUA correspond ng to mock cl ent-events
    val expectedT etRenderDefaultT etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaRenderCl entEventNa space),
        act onType = Act onType.Cl entT etRender mpress on
      )

    val expectedT etRenderReplyUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardReplyEvent(
      cl entEventNa space = So (uuaRenderCl entEventNa space),
      act onType = Act onType.Cl entT etRender mpress on
    )

    val expectedT etRenderRet etUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardRet etEvent(
      cl entEventNa space = So (uuaRenderCl entEventNa space),
      act onType = Act onType.Cl entT etRender mpress on
    )

    val expectedT etRenderQuoteUUA1: Un f edUserAct on = mkExpectedUUAForAct onTowardQuoteEvent(
      cl entEventNa space = So (uuaRenderCl entEventNa space),
      act onType = Act onType.Cl entT etRender mpress on,
      quotedAuthor d = So (quotedAuthor d),
    )
    val expectedT etRenderQuoteUUA2: Un f edUserAct on = mkExpectedUUAForAct onTowardQuot ngEvent(
      cl entEventNa space = So (uuaRenderCl entEventNa space),
      act onType = Act onType.Cl entT etRender mpress on,
      author nfo = So (Author nfo(author d = So (quotedAuthor d)))
    )

    val expectedT etRenderRet etW hReplyAndQuoteUUA1: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (uuaRenderCl entEventNa space),
        act onType = Act onType.Cl entT etRender mpress on
      )
    val expectedT etRenderRet etW hReplyAndQuoteUUA2: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuot ng(
        cl entEventNa space = So (uuaRenderCl entEventNa space),
        act onType = Act onType.Cl entT etRender mpress on
      )

    val expectedT etRenderDefaultT etW hTop c dUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaRenderCl entEventNa space),
        act onType = Act onType.Cl entT etRender mpress on,
        top c d = So (top c d)
      )

    val expectedT etDeta l mpress onUUA1: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (ceT etDeta lsCl entEventNa space1),
        act onType = Act onType.Cl entT etDeta ls mpress on
      )

    val expectedT etGallery mpress onUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (ceGalleryCl entEventNa space),
        act onType = Act onType.Cl entT etGallery mpress on
      )

    def expectedT etRenderDefaultT etW hAuthor nfoUUA(
      author nfo: Opt on[Author nfo] = None
    ): Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaRenderCl entEventNa space),
        act onType = Act onType.Cl entT etRender mpress on,
        author nfo = author nfo
      )

    val expectedT etL ngerDefaultT etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaL ngerCl entEventNa space),
        act onType = Act onType.Cl entT etL nger mpress on,
        t etAct on nfo = So (
          T etAct on nfo.Cl entT etL nger mpress on(
            Cl entT etL nger mpress on(
              l ngerStartT  stampMs = 100L,
              l ngerEndT  stampMs = 105L
            ))
        )
      )

    val expectedT etL ngerReplyUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardReplyEvent(
      cl entEventNa space = So (uuaL ngerCl entEventNa space),
      act onType = Act onType.Cl entT etL nger mpress on,
      t etAct on nfo = So (
        T etAct on nfo.Cl entT etL nger mpress on(
          Cl entT etL nger mpress on(
            l ngerStartT  stampMs = 100L,
            l ngerEndT  stampMs = 105L
          ))
      )
    )

    val expectedT etL ngerRet etUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardRet etEvent(
      cl entEventNa space = So (uuaL ngerCl entEventNa space),
      act onType = Act onType.Cl entT etL nger mpress on,
      t etAct on nfo = So (
        T etAct on nfo.Cl entT etL nger mpress on(
          Cl entT etL nger mpress on(
            l ngerStartT  stampMs = 100L,
            l ngerEndT  stampMs = 105L
          ))
      )
    )

    val expectedT etL ngerQuoteUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardQuoteEvent(
      cl entEventNa space = So (uuaL ngerCl entEventNa space),
      act onType = Act onType.Cl entT etL nger mpress on,
      t etAct on nfo = So (
        T etAct on nfo.Cl entT etL nger mpress on(
          Cl entT etL nger mpress on(
            l ngerStartT  stampMs = 100L,
            l ngerEndT  stampMs = 105L
          ))
      )
    )

    val expectedT etL ngerRet etW hReplyAndQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (uuaL ngerCl entEventNa space),
        act onType = Act onType.Cl entT etL nger mpress on,
        t etAct on nfo = So (
          T etAct on nfo.Cl entT etL nger mpress on(
            Cl entT etL nger mpress on(
              l ngerStartT  stampMs = 100L,
              l ngerEndT  stampMs = 105L
            ))
        )
      )

    val expectedT etCl ckQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (
          Cl entEventNa space(
            act on = So ("quote")
          )),
        act onType = Act onType.Cl entT etCl ckQuote
      )

    def expectedT etQuoteUUA(act on: Str ng): Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (
          Cl entEventNa space(
            act on = So (act on)
          )),
        act onType = Act onType.Cl entT etQuote
      )

    val expectedT etFavor eDefaultT etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav
      )

    val expectedHo T etEventW hControllerDataSuggestType: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaHo Favor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.Ho T  l ne),
        productSurface nfo = So (
          ProductSurface nfo.Ho T  l ne nfo(
            Ho T  l ne nfo(suggest onType = So ("Test_type"),  njectedPos  on = So (1)))),
        trace dOpt = So (trace d),
        requestJo n dOpt = So (requestJo n d)
      )

    val expectedHo T etEventW hControllerData: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaHo Favor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.Ho T  l ne),
        productSurface nfo =
          So (ProductSurface nfo.Ho T  l ne nfo(Ho T  l ne nfo( njectedPos  on = So (1)))),
        trace dOpt = So (trace d),
        requestJo n dOpt = So (requestJo n d)
      )

    val expectedSearchT etEventW hControllerData: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaSearchFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.SearchResultsPage),
        productSurface nfo =
          So (ProductSurface nfo.SearchResultsPage nfo(SearchResultsPage nfo(query = "tw ter"))),
        trace dOpt = So (trace d),
        requestJo n dOpt = So (requestJo n d)
      )

    val expectedHo T etEventW hSuggestType: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaHo Favor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.Ho T  l ne),
        productSurface nfo = So (
          ProductSurface nfo.Ho T  l ne nfo(Ho T  l ne nfo(suggest onType = So ("Test_type"))))
      )

    val expectedHo LatestT etEventW hControllerDataSuggestType: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaHo LatestFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.Ho T  l ne),
        productSurface nfo = So (
          ProductSurface nfo.Ho T  l ne nfo(
            Ho T  l ne nfo(suggest onType = So ("Test_type"),  njectedPos  on = So (1)))),
        trace dOpt = So (trace d),
        requestJo n dOpt = So (requestJo n d)
      )

    val expectedHo LatestT etEventW hControllerData: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaHo LatestFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.Ho T  l ne),
        productSurface nfo =
          So (ProductSurface nfo.Ho T  l ne nfo(Ho T  l ne nfo( njectedPos  on = So (1)))),
        trace dOpt = So (trace d),
        requestJo n dOpt = So (requestJo n d)
      )

    val expectedHo LatestT etEventW hSuggestType: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaHo LatestFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav,
        productSurface = So (ProductSurface.Ho T  l ne),
        productSurface nfo = So (
          ProductSurface nfo.Ho T  l ne nfo(Ho T  l ne nfo(suggest onType = So ("Test_type"))))
      )

    val expectedT etFavor eReplyUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardReplyEvent(
      cl entEventNa space = So (uuaFavor eCl entEventNa space),
      act onType = Act onType.Cl entT etFav
    )

    val expectedT etFavor eRet etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEvent(
        cl entEventNa space = So (uuaFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav
      )

    val expectedT etFavor eQuoteUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardQuoteEvent(
      cl entEventNa space = So (uuaFavor eCl entEventNa space),
      act onType = Act onType.Cl entT etFav)

    val expectedT etFavor eRet etW hReplyAndQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (uuaFavor eCl entEventNa space),
        act onType = Act onType.Cl entT etFav
      )

    val expectedT etCl ckReplyDefaultT etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaCl ckReplyCl entEventNa space),
        act onType = Act onType.Cl entT etCl ckReply
      )

    val expectedT etCl ckReplyReplyUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardReplyEvent(
        cl entEventNa space = So (uuaCl ckReplyCl entEventNa space),
        act onType = Act onType.Cl entT etCl ckReply
      )

    val expectedT etCl ckReplyRet etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEvent(
        cl entEventNa space = So (uuaCl ckReplyCl entEventNa space),
        act onType = Act onType.Cl entT etCl ckReply
      )

    val expectedT etCl ckReplyQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardQuoteEvent(
        cl entEventNa space = So (uuaCl ckReplyCl entEventNa space),
        act onType = Act onType.Cl entT etCl ckReply
      )

    val expectedT etCl ckReplyRet etW hReplyAndQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (uuaCl ckReplyCl entEventNa space),
        act onType = Act onType.Cl entT etCl ckReply
      )

    val expectedT etReplyDefaultT etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaReplyCl entEventNa space),
        act onType = Act onType.Cl entT etReply,
         nReplyToT et d = So ( emT et d)
      )

    val expectedT etReplyRet etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEvent(
        cl entEventNa space = So (uuaReplyCl entEventNa space),
        act onType = Act onType.Cl entT etReply,
         nReplyToT et d = So ( emT et d)
      )

    val expectedT etReplyQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardQuoteEvent(
        cl entEventNa space = So (uuaReplyCl entEventNa space),
        act onType = Act onType.Cl entT etReply,
         nReplyToT et d = So ( emT et d)
      )

    val expectedT etReplyRet etW hReplyAndQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (uuaReplyCl entEventNa space),
        act onType = Act onType.Cl entT etReply,
         nReplyToT et d =  emT et d
      )

    val expectedT etRet etDefaultT etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardDefaultT etEvent(
        cl entEventNa space = So (uuaRet etCl entEventNa space),
        act onType = Act onType.Cl entT etRet et
      )

    val expectedT etRet etReplyUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardReplyEvent(
      cl entEventNa space = So (uuaRet etCl entEventNa space),
      act onType = Act onType.Cl entT etRet et
    )

    val expectedT etRet etRet etUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEvent(
        cl entEventNa space = So (uuaRet etCl entEventNa space),
        act onType = Act onType.Cl entT etRet et
      )

    val expectedT etRet etQuoteUUA: Un f edUserAct on = mkExpectedUUAForAct onTowardQuoteEvent(
      cl entEventNa space = So (uuaRet etCl entEventNa space),
      act onType = Act onType.Cl entT etRet et
    )

    val expectedT etRet etRet etW hReplyAndQuoteUUA: Un f edUserAct on =
      mkExpectedUUAForAct onTowardRet etEventW hReplyAndQuoted(
        cl entEventNa space = So (uuaRet etCl entEventNa space),
        act onType = Act onType.Cl entT etRet et
      )
  }

  tra  Ema lNot f cat onEventF xture extends CommonF xture {
    val t  stamp = 1001L
    val pageUrlStatus =
      "https://tw ter.com/a/status/3?cn=a%3D%3D&refsrc=ema l"
    val t et dStatus = 3L

    val pageUrlEvent =
      "https://tw ter.com/ /events/2?cn=a%3D%3D&refsrc=ema l"
    val t et dEvent = 2L

    val pageUrlNoArgs = "https://tw ter.com/ /events/1"
    val t et dNoArgs = 1L

    val logBase1: LogBase = LogBase(
      transact on d = "test",
       pAddress = "127.0.0.1",
      user d = So (user d),
      guest d = So (2L),
      t  stamp = t  stamp,
      page = So (pageUrlStatus),
    )

    val logBase2: LogBase = LogBase(
      transact on d = "test",
       pAddress = "127.0.0.1",
      user d = So (user d),
      guest d = So (2L),
      t  stamp = t  stamp
    )

    val not f cat onEvent: Not f cat onScr be = Not f cat onScr be(
      `type` = Not f cat onScr beType.Cl ck,
       mpress on d = So ("1234"),
      user d = So (user d),
      t  stamp = t  stamp,
      logBase = So (logBase1)
    )

    val not f cat onEventWOT et d: Not f cat onScr be = Not f cat onScr be(
      `type` = Not f cat onScr beType.Cl ck,
       mpress on d = So ("1234"),
      user d = So (user d),
      t  stamp = t  stamp,
      logBase = So (logBase2)
    )

    val not f cat onEventWO mpress on d: Not f cat onScr be = Not f cat onScr be(
      `type` = Not f cat onScr beType.Cl ck,
      user d = So (user d),
      t  stamp = t  stamp,
      logBase = So (logBase1)
    )

    val expectedUua: Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = t et dStatus,
        )
      ),
      act onType = Act onType.Cl entT etEma lCl ck,
      event tadata = Event tadata(
        s ceT  stampMs = t  stamp,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.Ema lNot f cat onEvents,
        trace d = None
      ),
      productSurface nfo = So (
        ProductSurface nfo.Ema lNot f cat on nfo(Ema lNot f cat on nfo(not f cat on d = "1234"))),
      productSurface = So (ProductSurface.Ema lNot f cat on)
    )
  }

  tra  UserMod f cat onEventF xture extends CommonF xture {
    val t  stamp = 1001L
    val userNa  = "A"
    val screenNa  = "B"
    val descr pt on = "t   s A"
    val locat on = "US"
    val url = s"https://www.tw ter.com/${userNa }"

    val baseUserMod f cat on = UserMod f cat on(
      forUser d = So (user d),
      user d = So (user d),
    )

    val userCreate = baseUserMod f cat on.copy(
      create = So (
        User(
           d = user d,
          createdAtMsec = t  stamp,
          updatedAtMsec = t  stamp,
          userType = UserType.Normal,
          prof le = So (
            Prof le(
              na  = userNa ,
              screenNa  = screenNa ,
              descr pt on = descr pt on,
              auth = null.as nstanceOf[Auth],
              locat on = locat on,
              url = url
            ))
        )),
    )

    val updateD ffs = Seq(
      UpdateD ff em(f eldNa  = "user_na ", before = So ("abc"), after = So ("def")),
      UpdateD ff em(f eldNa  = "descr pt on", before = So ("d1"), after = So ("d2")),
    )
    val userUpdate = baseUserMod f cat on.copy(
      updatedAtMsec = So (t  stamp),
      update = So (updateD ffs),
      success = So (true)
    )

    val expectedUuaUserCreate: Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Prof le nfo(
        Prof le nfo(
          act onProf le d = user d,
          na  = So (userNa ),
          handle = So (screenNa ),
          descr pt on = So (descr pt on)
        )
      ),
      act onType = Act onType.ServerUserCreate,
      event tadata = Event tadata(
        s ceT  stampMs = t  stamp,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerG zmoduckUserMod f cat onEvents,
      )
    )

    val expectedUuaUserUpdate: Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.Prof le nfo(
        Prof le nfo(
          act onProf le d = user d,
          prof leAct on nfo = So (
            Prof leAct on nfo.ServerUserUpdate(
              ServerUserUpdate(updates = updateD ffs, success = So (true))))
        )
      ),
      act onType = Act onType.ServerUserUpdate,
      event tadata = Event tadata(
        s ceT  stampMs = t  stamp,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerG zmoduckUserMod f cat onEvents,
      )
    )
  }

  tra  AdsCallbackEngage ntsF xture extends CommonF xture {

    val t  stamp = 1001L
    val engage nt d = 123
    val accountT  Zone = "PST"
    val advert ser d = 2002L
    val d splayLocat on: D splayLocat on = D splayLocat on(value = 1)
    val trend d = 1002

    val author nfo: Author nfo = Author nfo(author d = So (advert ser d))
    val openL nkW hUrl: T etAct on nfo =
      T etAct on nfo.ServerPromotedT etOpenL nk(ServerPromotedT etOpenL nk(url = So ("go/url")))
    val openL nkW houtUrl: T etAct on nfo =
      T etAct on nfo.ServerPromotedT etOpenL nk(ServerPromotedT etOpenL nk(url = None))

    def createT et nfo em(
      author nfo: Opt on[Author nfo] = None,
      act on nfo: Opt on[T etAct on nfo] = None
    ):  em = {
       em.T et nfo(
        T et nfo(
          act onT et d =  emT et d,
          act onT etAuthor nfo = author nfo,
          t etAct on nfo = act on nfo))
    }

    val trend nfo em:  em =  em.Trend nfo(Trend nfo(act onTrend d = trend d))

    val organ cT et d = So (100001L)
    val promotedT et d = So (200002L)

    val organ cT etV deoUu d = So ("organ c_v deo_1")
    val organ cT etV deoOwner d = So (123L)

    val promotedT etV deoUu d = So ("promoted_v deo_1")
    val promotedT etV deoOwner d = So (345L)

    val prerollAdUu d = So ("preroll_ad_1")
    val prerollAdOwner d = So (567L)

    val ampl fyDeta lsPrerollAd = So (
      Ampl fyDeta ls(
        v deoOwner d = prerollAdOwner d,
        v deoUu d = prerollAdUu d,
        prerollOwner d = prerollAdOwner d,
        prerollUu d = prerollAdUu d
      ))

    val t etAct on nfoPrerollAd = So (
      T etAct on nfo.T etV deoWatch(
        T etV deoWatch(
           sMonet zable = So (true),
          v deoOwner d = prerollAdOwner d,
          v deoUu d = prerollAdUu d,
          prerollOwner d = prerollAdOwner d,
          prerollUu d = prerollAdUu d
        )
      )
    )

    val ampl fyDeta lsPromotedT etW houtAd = So (
      Ampl fyDeta ls(
        v deoOwner d = promotedT etV deoOwner d,
        v deoUu d = promotedT etV deoUu d
      ))

    val t etAct on nfoPromotedT etW houtAd = So (
      T etAct on nfo.T etV deoWatch(
        T etV deoWatch(
           sMonet zable = So (true),
          v deoOwner d = promotedT etV deoOwner d,
          v deoUu d = promotedT etV deoUu d,
        )
      )
    )

    val ampl fyDeta lsPromotedT etW hAd = So (
      Ampl fyDeta ls(
        v deoOwner d = promotedT etV deoOwner d,
        v deoUu d = promotedT etV deoUu d,
        prerollOwner d = prerollAdOwner d,
        prerollUu d = prerollAdUu d
      ))

    val t etAct on nfoPromotedT etW hAd = So (
      T etAct on nfo.T etV deoWatch(
        T etV deoWatch(
           sMonet zable = So (true),
          v deoOwner d = promotedT etV deoOwner d,
          v deoUu d = promotedT etV deoUu d,
          prerollOwner d = prerollAdOwner d,
          prerollUu d = prerollAdUu d
        )
      )
    )

    val ampl fyDeta lsOrgan cT etW hAd = So (
      Ampl fyDeta ls(
        v deoOwner d = organ cT etV deoOwner d,
        v deoUu d = organ cT etV deoUu d,
        prerollOwner d = prerollAdOwner d,
        prerollUu d = prerollAdUu d
      ))

    val t etAct on nfoOrgan cT etW hAd = So (
      T etAct on nfo.T etV deoWatch(
        T etV deoWatch(
           sMonet zable = So (true),
          v deoOwner d = organ cT etV deoOwner d,
          v deoUu d = organ cT etV deoUu d,
          prerollOwner d = prerollAdOwner d,
          prerollUu d = prerollAdUu d
        )
      )
    )

    def createExpectedUua(
      act onType: Act onType,
       em:  em
    ): Un f edUserAct on = {
      Un f edUserAct on(
        user dent f er = User dent f er(user d = So (user d)),
         em =  em,
        act onType = act onType,
        event tadata = Event tadata(
          s ceT  stampMs = t  stamp,
          rece vedT  stampMs = frozenT  . nM ll seconds,
          s ceL neage = S ceL neage.ServerAdsCallbackEngage nts
        )
      )
    }

    def createExpectedUuaW hProf le nfo(
      act onType: Act onType
    ): Un f edUserAct on = {
      Un f edUserAct on(
        user dent f er = User dent f er(user d = So (user d)),
         em =  em.Prof le nfo(Prof le nfo(act onProf le d = advert ser d)),
        act onType = act onType,
        event tadata = Event tadata(
          s ceT  stampMs = t  stamp,
          rece vedT  stampMs = frozenT  . nM ll seconds,
          s ceL neage = S ceL neage.ServerAdsCallbackEngage nts
        )
      )
    }

    def createSpendServerEvent(
      engage ntType: Engage ntType,
      url: Opt on[Str ng] = None
    ): SpendServerEvent = {
      SpendServerEvent(
        engage ntEvent = So (
          Engage ntEvent(
            cl ent nfo = So (Cl ent nfo(user d64 = So (user d))),
            engage nt d = engage nt d,
            engage ntEpochT  M ll Sec = t  stamp,
            engage ntType = engage ntType,
            accountT  Zone = accountT  Zone,
            url = url,
             mpress onData = So (
               mpress onDataNeededAtEngage ntT  (
                advert ser d = advert ser d,
                promotedT et d = So ( emT et d),
                d splayLocat on = d splayLocat on,
                promotedTrend d = So (trend d)))
          )))
    }

    def createExpectedV deoUua(
      act onType: Act onType,
      t etAct on nfo: Opt on[T etAct on nfo],
      act onT et d: Opt on[Long]
    ): Un f edUserAct on = {
      Un f edUserAct on(
        user dent f er = User dent f er(user d = So (user d)),
         em =  em.T et nfo(
          T et nfo(
            act onT et d = act onT et d.get,
            act onT etAuthor nfo = So (Author nfo(author d = So (advert ser d))),
            t etAct on nfo = t etAct on nfo
          )
        ),
        act onType = act onType,
        event tadata = Event tadata(
          s ceT  stampMs = t  stamp,
          rece vedT  stampMs = frozenT  . nM ll seconds,
          s ceL neage = S ceL neage.ServerAdsCallbackEngage nts
        )
      )
    }

    def createV deoSpendServerEvent(
      engage ntType: Engage ntType,
      ampl fyDeta ls: Opt on[Ampl fyDeta ls],
      promotedT et d: Opt on[Long],
      organ cT et d: Opt on[Long]
    ): SpendServerEvent = {
      SpendServerEvent(
        engage ntEvent = So (
          Engage ntEvent(
            cl ent nfo = So (Cl ent nfo(user d64 = So (user d))),
            engage nt d = engage nt d,
            engage ntEpochT  M ll Sec = t  stamp,
            engage ntType = engage ntType,
            accountT  Zone = accountT  Zone,
             mpress onData = So (
               mpress onDataNeededAtEngage ntT  (
                advert ser d = advert ser d,
                promotedT et d = promotedT et d,
                d splayLocat on = d splayLocat on,
                organ cT et d = organ cT et d)),
            cardEngage nt = So (
              CardEvent(
                ampl fyDeta ls = ampl fyDeta ls
              )
            )
          )))
    }
  }

  tra   nteract onEventsF xtures extends CommonF xture {
    val t  stamp = 123456L
    val t et d = 1L
    val engag ngUser d = 11L

    val base nteract onEvent:  nteract onEvent =  nteract onEvent(
      target d = t et d,
      targetType =  nteract onTargetType.T et,
      engag ngUser d = engag ngUser d,
      eventS ce = EventS ce.Cl entEvent,
      t  stampM ll s = t  stamp,
       nteract onType = So ( nteract onType.T etRender mpress on),
      deta ls =  nteract onDeta ls.T etRender mpress on(T et mpress on()),
      add  onalEngag ngUser dent f ers = User dent f er E(),
      engag ngContext = Engag ngContext.Cl entEventContext(
        Cl entEventContext(
          cl entEventNa space = ContextualEventNa space(),
          cl entType = Cl entType. phone,
          d splayLocat on = D splayLocat on(1),
           sT etDeta ls mpress on = So (false)))
    )

    val loggedOut nteract onEvent:  nteract onEvent = base nteract onEvent.copy(engag ngUser d = 0L)

    val deta l mpress on nteract onEvent:  nteract onEvent = base nteract onEvent.copy(
      engag ngContext = Engag ngContext.Cl entEventContext(
        Cl entEventContext(
          cl entEventNa space = ContextualEventNa space(),
          cl entType = Cl entType. phone,
          d splayLocat on = D splayLocat on(1),
           sT etDeta ls mpress on = So (true)))
    )

    val expectedBaseKeyedUuaT et: KeyedUuaT et = KeyedUuaT et(
      t et d = t et d,
      act onType = Act onType.Cl entT etRender mpress on,
      user dent f er = User dent f er(user d = So (engag ngUser d)),
      event tadata = Event tadata(
        s ceT  stampMs = t  stamp,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.Cl entEvents
      )
    )
  }
}
