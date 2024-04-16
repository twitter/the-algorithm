package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.cl entapp.thr ftscala._
 mport com.tw ter.cl entapp.thr ftscala.Suggest onDeta ls
 mport com.tw ter.gu de.scr b ng.thr ftscala._
 mport com.tw ter.gu de.scr b ng.thr ftscala.{Semant cCore nterest => Semant cCore nterestV1}
 mport com.tw ter.gu de.scr b ng.thr ftscala.{S mCluster nterest => S mCluster nterestV1}
 mport com.tw ter.gu de.scr b ng.thr ftscala.Top cModule tadata.Semant cCore nterest
 mport com.tw ter.gu de.scr b ng.thr ftscala.Top cModule tadata.S mCluster nterest
 mport com.tw ter.gu de.scr b ng.thr ftscala.TransparentGu deDeta ls.Top c tadata
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.scrooge.TF eldBlob
 mport com.tw ter.suggests.controller_data.ho _h l_top c_annotat on_prompt.thr ftscala.Ho H lTop cAnnotat onPromptControllerData
 mport com.tw ter.suggests.controller_data.ho _h l_top c_annotat on_prompt.v1.thr ftscala.{
  Ho H lTop cAnnotat onPromptControllerData => Ho H lTop cAnnotat onPromptControllerDataV1
}
 mport com.tw ter.suggests.controller_data.ho _top c_annotat on_prompt.thr ftscala.Ho Top cAnnotat onPromptControllerData
 mport com.tw ter.suggests.controller_data.ho _top c_annotat on_prompt.v1.thr ftscala.{
  Ho Top cAnnotat onPromptControllerData => Ho Top cAnnotat onPromptControllerDataV1
}
 mport com.tw ter.suggests.controller_data.ho _top c_follow_prompt.thr ftscala.Ho Top cFollowPromptControllerData
 mport com.tw ter.suggests.controller_data.ho _top c_follow_prompt.v1.thr ftscala.{
  Ho Top cFollowPromptControllerData => Ho Top cFollowPromptControllerDataV1
}
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerData
 mport com.tw ter.suggests.controller_data.ho _t ets.v1.thr ftscala.{
  Ho T etsControllerData => Ho T etsControllerDataV1
}
 mport com.tw ter.suggests.controller_data.search_response. em_types.thr ftscala. emTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response.thr ftscala.SearchResponseControllerData
 mport com.tw ter.suggests.controller_data.search_response.top c_follow_prompt.thr ftscala.SearchTop cFollowPromptControllerData
 mport com.tw ter.suggests.controller_data.search_response.t et_types.thr ftscala.T etTypesControllerData
 mport com.tw ter.suggests.controller_data.search_response.v1.thr ftscala.{
  SearchResponseControllerData => SearchResponseControllerDataV1
}
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.thr ftscala.T  l nesTop cControllerData
 mport com.tw ter.suggests.controller_data.t  l nes_top c.v1.thr ftscala.{
  T  l nesTop cControllerData => T  l nesTop cControllerDataV1
}
 mport com.tw ter.suggests.controller_data.v2.thr ftscala.{ControllerData => ControllerDataV2}
 mport org.apac .thr ft.protocol.TF eld
 mport org.jun .runner.RunW h
 mport org.scalatest.funsu e.AnyFunSu e
 mport org.scalatest.matc rs.should.Matc rs
 mport org.scalatestplus.jun .JUn Runner
 mport com.tw ter.ut l.mock.Mock o
 mport org.mock o.Mock o.w n
 mport org.scalatest.prop.TableDr venPropertyC cks

@RunW h(classOf[JUn Runner])
class Top cs dUt lsSpec
    extends AnyFunSu e
    w h Matc rs
    w h Mock o
    w h TableDr venPropertyC cks {
   mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Top c dUt ls._

  tra  F xture {
    def bu ldLogBase(user d: Long): LogBase = {
      val logBase = mock[LogBase]
      w n(logBase.country).t nReturn(So ("US"))
      w n(logBase.user d).t nReturn(So (user d))
      w n(logBase.t  stamp).t nReturn(100L)
      w n(logBase.guest d).t nReturn(So (1L))
      w n(logBase.userAgent).t nReturn(None)
      w n(logBase.language).t nReturn(So ("en"))
      logBase
    }

    def bu ld emForT  l ne(
       em d: Long,
       emType:  emType,
      top c d: Long,
      fn: Long => ControllerData.V2
    ):  em = {
      val  em =  em(
         d = So ( em d),
         emType = So ( emType),
        suggest onDeta ls = So (Suggest onDeta ls(decodedControllerData = So (fn(top c d))))
      )
       em
    }

    def bu ldCl entEventForHo SearchT  l ne(
       em d: Long,
       emType:  emType,
      top c d: Long,
      fn: Long => ControllerData.V2,
      user d: Long = 1L,
      eventNa spaceOpt: Opt on[EventNa space] = None,
    ): LogEvent = {
      val logEvent = mock[LogEvent]
      w n(logEvent.eventNa space).t nReturn(eventNa spaceOpt)
      val eventsDeta ls = mock[EventDeta ls]
      w n(eventsDeta ls. ems)
        .t nReturn(So (Seq(bu ld emForT  l ne( em d,  emType, top c d, fn))))
      val logbase = bu ldLogBase(user d)
      w n(logEvent.logBase).t nReturn(So (logbase))
      w n(logEvent.eventDeta ls).t nReturn(So (eventsDeta ls))
      logEvent
    }

    def bu ldCl entEventForHo T etsT  l ne(
       em d: Long,
       emType:  emType,
      top c d: Long,
      top c ds: Set[Long],
      fn: (Long, Set[Long]) => ControllerData.V2,
      user d: Long = 1L,
      eventNa spaceOpt: Opt on[EventNa space] = None,
    ): LogEvent = {
      val logEvent = mock[LogEvent]
      w n(logEvent.eventNa space).t nReturn(eventNa spaceOpt)
      val eventsDeta ls = mock[EventDeta ls]
      w n(eventsDeta ls. ems)
        .t nReturn(So (Seq(bu ld emForHo T  l ne( em d,  emType, top c d, top c ds, fn))))
      val logbase = bu ldLogBase(user d)
      w n(logEvent.logBase).t nReturn(So (logbase))
      w n(logEvent.eventDeta ls).t nReturn(So (eventsDeta ls))
      logEvent
    }

    def bu ldCl entEventForGu de(
       em d: Long,
       emType:  emType,
      top c d: Long,
      fn: Long => Top c tadata,
      user d: Long = 1L,
      eventNa spaceOpt: Opt on[EventNa space] = None,
    ): LogEvent = {
      val logEvent = mock[LogEvent]
      w n(logEvent.eventNa space).t nReturn(eventNa spaceOpt)
      val logbase = bu ldLogBase(user d)
      w n(logEvent.logBase).t nReturn(So (logbase))
      val eventDeta ls = mock[EventDeta ls]
      val  em = bu ld emForGu de( em d,  emType, top c d, fn)
      w n(eventDeta ls. ems).t nReturn(So (Seq( em)))
      w n(logEvent.eventDeta ls).t nReturn(So (eventDeta ls))
      logEvent
    }

    def bu ldCl entEventForOnboard ng(
       em d: Long,
      top c d: Long,
      user d: Long = 1L
    ): LogEvent = {
      val logEvent = mock[LogEvent]
      val logbase = bu ldLogBase(user d)
      w n(logEvent.logBase).t nReturn(So (logbase))
      w n(logEvent.eventNa space).t nReturn(So (bu ldNa spaceForOnboard ng))
      val eventDeta ls = mock[EventDeta ls]
      val  em = bu ld emForOnboard ng( em d, top c d)
      w n(eventDeta ls. ems)
        .t nReturn(So (Seq( em)))
      w n(logEvent.eventDeta ls).t nReturn(So (eventDeta ls))
      logEvent
    }

    def bu ldCl entEventForOnboard ngBackend(
      top c d: Long,
      user d: Long = 1L
    ): LogEvent = {
      val logEvent = mock[LogEvent]
      val logbase = bu ldLogBase(user d)
      w n(logEvent.logBase).t nReturn(So (logbase))
      w n(logEvent.eventNa space).t nReturn(So (bu ldNa spaceForOnboard ngBackend))
      val eventDeta ls = bu ldEventDeta lsForOnboard ngBackend(top c d)
      w n(logEvent.eventDeta ls).t nReturn(So (eventDeta ls))
      logEvent
    }

    def defaultNa space: EventNa space = {
      EventNa space(So (" phone"), None, None, None, None, So ("favor e"))
    }

    def bu ldNa spaceForOnboard ngBackend: EventNa space = {
      EventNa space(
        So (" phone"),
        So ("onboard ng_backend"),
        So ("subtasks"),
        So ("top cs_selector"),
        So ("removed"),
        So ("selected"))
    }

    def bu ldNa spaceForOnboard ng: EventNa space = {
      EventNa space(
        So (" phone"),
        So ("onboard ng"),
        So ("top cs_selector"),
        None,
        So ("top c"),
        So ("follow")
      )
    }

    def bu ld emForHo T  l ne(
       em d: Long,
       emType:  emType,
      top c d: Long,
      top c ds: Set[Long],
      fn: (Long, Set[Long]) => ControllerData.V2
    ):  em = {
      val  em =  em(
         d = So ( em d),
         emType = So ( emType),
        suggest onDeta ls =
          So (Suggest onDeta ls(decodedControllerData = So (fn(top c d, top c ds))))
      )
       em
    }

    def bu ld emForGu de(
       em d: Long,
       emType:  emType,
      top c d: Long,
      fn: Long => Top c tadata
    ):  em = {
      val  em = mock[ em]
      w n( em. d).t nReturn(So ( em d))
      w n( em. emType).t nReturn(So ( emType))
      w n( em.suggest onDeta ls)
        .t nReturn(So (Suggest onDeta ls(suggest onType = So ("ErgT et"))))
      val gu de emDeta ls = mock[Gu de emDeta ls]
      w n(gu de emDeta ls.transparentGu deDeta ls).t nReturn(So (fn(top c d)))
      w n( em.gu de emDeta ls).t nReturn(So (gu de emDeta ls))
       em
    }

    def bu ld emForOnboard ng(
       em d: Long,
      top c d: Long
    ):  em = {
      val  em =  em(
         d = So ( em d),
         emType = None,
        descr pt on = So (s" d=$top c d,row=1")
      )
       em
    }

    def bu ldEventDeta lsForOnboard ngBackend(
      top c d: Long
    ): EventDeta ls = {
      val eventDeta ls = mock[EventDeta ls]
      val  em =  em(
         d = So (top c d)
      )
      val  emTmp = bu ld emForOnboard ng(10, top c d)
      w n(eventDeta ls. ems).t nReturn(So (Seq( emTmp)))
      w n(eventDeta ls.targets).t nReturn(So (Seq( em)))
      eventDeta ls
    }

    def top c tadata nGu de(top c d: Long): Top c tadata =
      Top c tadata(
        Semant cCore nterest(
          Semant cCore nterestV1(doma n d = "131", ent y d = top c d.toStr ng)
        )
      )

    def s mCluster tadata nGu de(s mcluster d: Long = 1L): Top c tadata =
      Top c tadata(
        S mCluster nterest(
          S mCluster nterestV1(s mcluster d.toStr ng)
        )
      )

    def t  l neTop cControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.T  l nesTop c(
          T  l nesTop cControllerData.V1(
            T  l nesTop cControllerDataV1(
              top c d = top c d,
              top cTypesB map = 1
            )
          )))

    def ho T etControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.Ho T ets(
          Ho T etsControllerData.V1(
            Ho T etsControllerDataV1(
              top c d = So (top c d)
            ))))

    def ho Top cFollowPromptControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.Ho Top cFollowPrompt(Ho Top cFollowPromptControllerData.V1(
          Ho Top cFollowPromptControllerDataV1(So (top c d)))))

    def ho Top cAnnotat onPromptControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.Ho Top cAnnotat onPrompt(Ho Top cAnnotat onPromptControllerData.V1(
          Ho Top cAnnotat onPromptControllerDataV1(t et d = 1L, top c d = top c d))))

    def ho H lTop cAnnotat onPromptControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.Ho H lTop cAnnotat onPrompt(
          Ho H lTop cAnnotat onPromptControllerData.V1(
            Ho H lTop cAnnotat onPromptControllerDataV1(t et d = 2L, top c d = top c d))))

    def searchTop cFollowPromptControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.SearchResponse(
          SearchResponseControllerData.V1(
            SearchResponseControllerDataV1(
              So ( emTypesControllerData.Top cFollowControllerData(
                SearchTop cFollowPromptControllerData(So (top c d))
              )),
              None
            ))))

    def searchT etTypesControllerData(top c d: Long): ControllerData.V2 =
      ControllerData.V2(
        ControllerDataV2.SearchResponse(
          SearchResponseControllerData.V1(
            SearchResponseControllerDataV1(
              So ( emTypesControllerData.T etTypesControllerData(
                T etTypesControllerData(None, So (top c d))
              )),
              None
            )
          )))

    //used for creat ng logged out user cl ent events
    def bu ldLogBaseW houtUser d(guest d: Long): LogBase =
      LogBase(
         pAddress = "120.10.10.20",
        guest d = So (guest d),
        userAgent = None,
        transact on d = "",
        country = So ("US"),
        t  stamp = 100L,
        language = So ("en")
      )
  }

  test("getTop c d should correctly f nd top c  d from  em for ho  t  l ne and search") {
    new F xture {

      val testData = Table(
        (" emType", "top c d", "controllerData"),
        ( emType.T et, 1L, t  l neTop cControllerData(1L)),
        ( emType.User, 2L, t  l neTop cControllerData(2L)),
        ( emType.Top c, 3L, ho T etControllerData(3L)),
        ( emType.Top c, 4L, ho Top cFollowPromptControllerData(4L)),
        ( emType.Top c, 5L, searchTop cFollowPromptControllerData(5L)),
        ( emType.Top c, 6L, ho H lTop cAnnotat onPromptControllerData(6L))
      )

      forEvery(testData) {
        ( emType:  emType, top c d: Long, controllerDataV2: ControllerData.V2) =>
          getTop c d(
            bu ld emForT  l ne(1,  emType, top c d, _ => controllerDataV2),
            defaultNa space) shouldEqual So (top c d)
      }
    }
  }

  test("getTop c d should correctly f nd top c  d from  em for gu de events") {
    new F xture {
      getTop c d(
        bu ld emForGu de(1,  emType.T et, 100, top c tadata nGu de),
        defaultNa space
      ) shouldEqual So (100)
    }
  }

  test("getTop c d should correctly f nd top c  d for onboard ng events") {
    new F xture {
      getTop c d(
        bu ld emForOnboard ng(1, 100),
        bu ldNa spaceForOnboard ng
      ) shouldEqual So (100)
    }
  }

  test("should return Top c d From Ho Search") {
    val testData = Table(
      ("controllerData", "top c d"),
      (
        ControllerData.V2(
          ControllerDataV2.Ho T ets(
            Ho T etsControllerData.V1(Ho T etsControllerDataV1(top c d = So (1L))))
        ),
        So (1L)),
      (
        ControllerData.V2(
          ControllerDataV2.Ho Top cFollowPrompt(Ho Top cFollowPromptControllerData
            .V1(Ho Top cFollowPromptControllerDataV1(top c d = So (2L))))),
        So (2L)),
      (
        ControllerData.V2(
          ControllerDataV2.T  l nesTop c(
            T  l nesTop cControllerData.V1(
              T  l nesTop cControllerDataV1(top c d = 3L, top cTypesB map = 100)
            ))),
        So (3L)),
      (
        ControllerData.V2(
          ControllerDataV2.SearchResponse(
            SearchResponseControllerData.V1(SearchResponseControllerDataV1( emTypesControllerData =
              So ( emTypesControllerData.Top cFollowControllerData(
                SearchTop cFollowPromptControllerData(top c d = So (4L)))))))),
        So (4L)),
      (
        ControllerData.V2(
          ControllerDataV2.SearchResponse(
            SearchResponseControllerData.V1(
              SearchResponseControllerDataV1( emTypesControllerData = So ( emTypesControllerData
                .T etTypesControllerData(T etTypesControllerData(top c d = So (5L)))))))),
        So (5L)),
      (
        ControllerData.V2(
          ControllerDataV2
            .SearchResponse(SearchResponseControllerData.V1(SearchResponseControllerDataV1()))),
        None)
    )

    forEvery(testData) { (controllerDataV2: ControllerData.V2, top c d: Opt on[Long]) =>
      getTop c dFromHo Search(
         em(suggest onDeta ls = So (
          Suggest onDeta ls(decodedControllerData = So (controllerDataV2))))) shouldEqual top c d
    }
  }

  test("test Top c d From Onboard ng") {
    val testData = Table(
      (" em", "EventNa space", "top c d"),
      (
         em(descr pt on = So (" d=11,key=value")),
        EventNa space(
          page = So ("onboard ng"),
          sect on = So ("sect on has top c"),
          component = So ("component has top c"),
          ele nt = So ("ele nt has top c")
        ),
        So (11L)),
      (
         em(descr pt on = So (" d=22,key=value")),
        EventNa space(
          page = So ("onboard ng"),
          sect on = So ("sect on has top c")
        ),
        So (22L)),
      (
         em(descr pt on = So (" d=33,key=value")),
        EventNa space(
          page = So ("onboard ng"),
          component = So ("component has top c")
        ),
        So (33L)),
      (
         em(descr pt on = So (" d=44,key=value")),
        EventNa space(
          page = So ("onboard ng"),
          ele nt = So ("ele nt has top c")
        ),
        So (44L)),
      (
         em(descr pt on = So (" d=678,key=value")),
        EventNa space(
          page = So ("onXYZboard ng"),
          sect on = So ("sect on has top c"),
          component = So ("component has top c"),
          ele nt = So ("ele nt has top c")
        ),
        None),
      (
         em(descr pt on = So (" d=678,key=value")),
        EventNa space(
          page = So ("page has onboard ng"),
          sect on = So ("sect on has topP c"),
          component = So ("component has topP c"),
          ele nt = So ("ele nt has topP c")
        ),
        None),
      (
         em(descr pt on = So ("key=value, d=678")),
        EventNa space(
          page = So ("page has onboard ng"),
          sect on = So ("sect on has top c"),
          component = So ("component has top c"),
          ele nt = So ("ele nt has top c")
        ),
        None)
    )

    forEvery(testData) { ( em:  em, eventNa space: EventNa space, top c d: Opt on[Long]) =>
      getTop cFromOnboard ng( em, eventNa space) shouldEqual top c d
    }
  }

  test("test from Gu de") {
    val testData = Table(
      ("gu de emDeta ls", "top c d"),
      (
        Gu de emDeta ls(transparentGu deDeta ls = So (
          TransparentGu deDeta ls.Top c tadata(
            Top cModule tadata.Ttt nterest(ttt nterest = Ttt nterest.unsafeEmpty)))),
        None),
      (
        Gu de emDeta ls(transparentGu deDeta ls = So (
          TransparentGu deDeta ls.Top c tadata(
            Top cModule tadata.S mCluster nterest(s mCluster nterest =
              com.tw ter.gu de.scr b ng.thr ftscala.S mCluster nterest.unsafeEmpty)))),
        None),
      (
        Gu de emDeta ls(transparentGu deDeta ls = So (
          TransparentGu deDeta ls.Top c tadata(Top cModule tadata.UnknownUn onF eld(f eld =
            TF eldBlob(new TF eld(), Array.empty[Byte]))))),
        None),
      (
        Gu de emDeta ls(transparentGu deDeta ls = So (
          TransparentGu deDeta ls.Top c tadata(
            Top cModule tadata.Semant cCore nterest(
              com.tw ter.gu de.scr b ng.thr ftscala.Semant cCore nterest.unsafeEmpty
                .copy(doma n d = "131", ent y d = "1"))))),
        So (1L)),
    )

    forEvery(testData) { (gu de emDeta ls: Gu de emDeta ls, top c d: Opt on[Long]) =>
      getTop cFromGu de( em(gu de emDeta ls = So (gu de emDeta ls))) shouldEqual top c d
    }
  }

  test("getTop c d should return top c ds") {
    getTop c d(
       em =  em(suggest onDeta ls = So (
        Suggest onDeta ls(decodedControllerData = So (
          ControllerData.V2(
            ControllerDataV2.Ho T ets(
              Ho T etsControllerData.V1(Ho T etsControllerDataV1(top c d = So (1L))))
          ))))),
      na space = EventNa space(
        page = So ("onboard ng"),
        sect on = So ("sect on has top c"),
        component = So ("component has top c"),
        ele nt = So ("ele nt has top c")
      )
    ) shouldEqual So (1L)
  }
}
