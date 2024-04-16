package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter. nject.Test
 mport com.tw ter.soc algraph.thr ftscala.Act on
 mport com.tw ter.soc algraph.thr ftscala.BlockGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.FollowGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.FollowRequestGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.FollowRet etsGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.LogEventContext
 mport com.tw ter.soc algraph.thr ftscala.MuteGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.ReportAsAbuseGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.ReportAsSpamGraphEvent
 mport com.tw ter.soc algraph.thr ftscala.SrcTargetRequest
 mport com.tw ter.soc algraph.thr ftscala.Wr eEvent
 mport com.tw ter.soc algraph.thr ftscala.Wr eRequestResult
 mport com.tw ter.un f ed_user_act ons.adapter.soc al_graph_event.Soc alGraphAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.T  
 mport org.scalatest.prop.TableDr venPropertyC cks
 mport org.scalatest.prop.TableFor1
 mport org.scalatest.prop.TableFor3

class Soc alGraphAdapterSpec extends Test w h TableDr venPropertyC cks {
  tra  F xture {

    val frozenT  : T   = T  .fromM ll seconds(1658949273000L)

    val testLogEventContext: LogEventContext = LogEventContext(
      t  stamp = 1001L,
      hostna  = "",
      transact on d = "",
      soc alGraphCl ent d = "",
      logged nUser d = So (1111L),
    )

    val testWr eRequestResult: Wr eRequestResult = Wr eRequestResult(
      request = SrcTargetRequest(
        s ce = 1111L,
        target = 2222L
      )
    )

    val testWr eRequestResultW hVal dat onError: Wr eRequestResult = Wr eRequestResult(
      request = SrcTargetRequest(
        s ce = 1111L,
        target = 2222L
      ),
      val dat onError = So ("act on unsuccessful")
    )

    val baseEvent: Wr eEvent = Wr eEvent(
      context = testLogEventContext,
      act on = Act on.AcceptFollowRequest
    )

    val sgFollowEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Follow,
      follow = So (L st(FollowGraphEvent(testWr eRequestResult))))

    val sgUnfollowEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unfollow,
      follow = So (L st(FollowGraphEvent(testWr eRequestResult))))

    val sgFollowRedundantEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Follow,
      follow = So (
        L st(
          FollowGraphEvent(
            result = testWr eRequestResult,
            redundantOperat on = So (true)
          ))))

    val sgFollowRedundant sFalseEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Follow,
      follow = So (
        L st(
          FollowGraphEvent(
            result = testWr eRequestResult,
            redundantOperat on = So (false)
          ))))

    val sgUnfollowRedundantEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unfollow,
      follow = So (
        L st(
          FollowGraphEvent(
            result = testWr eRequestResult,
            redundantOperat on = So (true)
          ))))

    val sgUnfollowRedundant sFalseEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unfollow,
      follow = So (
        L st(
          FollowGraphEvent(
            result = testWr eRequestResult,
            redundantOperat on = So (false)
          ))))

    val sgUnsuccessfulFollowEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Follow,
      follow = So (L st(FollowGraphEvent(testWr eRequestResultW hVal dat onError))))

    val sgUnsuccessfulUnfollowEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unfollow,
      follow = So (L st(FollowGraphEvent(testWr eRequestResultW hVal dat onError))))

    val sgBlockEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Block,
      block = So (L st(BlockGraphEvent(testWr eRequestResult))))

    val sgUnsuccessfulBlockEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Block,
      block = So (L st(BlockGraphEvent(testWr eRequestResultW hVal dat onError))))

    val sgUnblockEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unblock,
      block = So (L st(BlockGraphEvent(testWr eRequestResult))))

    val sgUnsuccessfulUnblockEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unblock,
      block = So (L st(BlockGraphEvent(testWr eRequestResultW hVal dat onError))))

    val sgMuteEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Mute,
      mute = So (L st(MuteGraphEvent(testWr eRequestResult))))

    val sgUnsuccessfulMuteEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Mute,
      mute = So (L st(MuteGraphEvent(testWr eRequestResultW hVal dat onError))))

    val sgUnmuteEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unmute,
      mute = So (L st(MuteGraphEvent(testWr eRequestResult))))

    val sgUnsuccessfulUnmuteEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.Unmute,
      mute = So (L st(MuteGraphEvent(testWr eRequestResultW hVal dat onError))))

    val sgCreateFollowRequestEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.CreateFollowRequest,
      followRequest = So (L st(FollowRequestGraphEvent(testWr eRequestResult)))
    )

    val sgCancelFollowRequestEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.CancelFollowRequest,
      followRequest = So (L st(FollowRequestGraphEvent(testWr eRequestResult)))
    )

    val sgAcceptFollowRequestEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.AcceptFollowRequest,
      followRequest = So (L st(FollowRequestGraphEvent(testWr eRequestResult)))
    )

    val sgAcceptFollowRet etEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.FollowRet ets,
      followRet ets = So (L st(FollowRet etsGraphEvent(testWr eRequestResult)))
    )

    val sgAcceptUnfollowRet etEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.UnfollowRet ets,
      followRet ets = So (L st(FollowRet etsGraphEvent(testWr eRequestResult)))
    )

    val sgReportAsSpamEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.ReportAsSpam,
      reportAsSpam = So (
        L st(
          ReportAsSpamGraphEvent(
            result = testWr eRequestResult
          ))))

    val sgReportAsAbuseEvent: Wr eEvent = baseEvent.copy(
      act on = Act on.ReportAsAbuse,
      reportAsAbuse = So (
        L st(
          ReportAsAbuseGraphEvent(
            result = testWr eRequestResult
          ))))

    def getExpectedUUA(
      user d: Long,
      act onProf le d: Long,
      s ceT  stampMs: Long,
      act onType: Act onType,
      soc alGraphAct on: Opt on[Act on] = None
    ): Un f edUserAct on = {
      val act on em = soc alGraphAct on match {
        case So (sgAct on) =>
           em.Prof le nfo(
            Prof le nfo(
              act onProf le d = act onProf le d,
              prof leAct on nfo = So (
                Prof leAct on nfo.ServerProf leReport(
                  ServerProf leReport(reportType = sgAct on)
                ))
            )
          )
        case _ =>
           em.Prof le nfo(
            Prof le nfo(
              act onProf le d = act onProf le d
            )
          )
      }

      Un f edUserAct on(
        user dent f er = User dent f er(user d = So (user d)),
         em = act on em,
        act onType = act onType,
        event tadata = Event tadata(
          s ceT  stampMs = s ceT  stampMs,
          rece vedT  stampMs = frozenT  . nM ll seconds,
          s ceL neage = S ceL neage.ServerSoc alGraphEvents
        )
      )
    }

    val expectedUuaFollow: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leFollow
    )

    val expectedUuaUnfollow: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leUnfollow
    )

    val expectedUuaMute: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leMute
    )

    val expectedUuaUnmute: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leUnmute
    )

    val expectedUuaBlock: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leBlock
    )

    val expectedUuaUnblock: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leUnblock
    )

    val expectedUuaReportAsSpam: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leReport,
      soc alGraphAct on = So (Act on.ReportAsSpam)
    )

    val expectedUuaReportAsAbuse: Un f edUserAct on = getExpectedUUA(
      user d = 1111L,
      act onProf le d = 2222L,
      s ceT  stampMs = 1001L,
      act onType = Act onType.ServerProf leReport,
      soc alGraphAct on = So (Act on.ReportAsAbuse)
    )
  }

  test("Soc alGraphAdapter  gnore events not  n t  l st") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  gnoredSoc alGraphEvents: TableFor1[Wr eEvent] = Table(
          " gnoredSoc alGraphEvents",
          sgAcceptUnfollowRet etEvent,
          sgAcceptFollowRequestEvent,
          sgAcceptFollowRet etEvent,
          sgCreateFollowRequestEvent,
          sgCancelFollowRequestEvent,
        )
        forEvery( gnoredSoc alGraphEvents) { wr eEvent: Wr eEvent =>
          val actual = Soc alGraphAdapter.adaptEvent(wr eEvent)
          assert(actual. sEmpty)
        }
      }
    }
  }

  test("Test Soc alGraphAdapter consum ng Wr e events") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val soc alProf leAct ons: TableFor3[Str ng, Wr eEvent, Un f edUserAct on] = Table(
          ("act onType", "event", "expectedUn f edUserAct on"),
          ("Prof leFollow", sgFollowEvent, expectedUuaFollow),
          ("Prof leUnfollow", sgUnfollowEvent, expectedUuaUnfollow),
          ("Prof leBlock", sgBlockEvent, expectedUuaBlock),
          ("Prof leUnBlock", sgUnblockEvent, expectedUuaUnblock),
          ("Prof leMute", sgMuteEvent, expectedUuaMute),
          ("Prof leUnmute", sgUnmuteEvent, expectedUuaUnmute),
          ("Prof leReportAsSpam", sgReportAsSpamEvent, expectedUuaReportAsSpam),
          ("Prof leReportAsAbuse", sgReportAsAbuseEvent, expectedUuaReportAsAbuse),
        )
        forEvery(soc alProf leAct ons) {
          (_: Str ng, event: Wr eEvent, expected: Un f edUserAct on) =>
            val actual = Soc alGraphAdapter.adaptEvent(event)
            assert(Seq(expected) === actual)
        }
      }
    }
  }

  test("Soc alGraphAdapter  gnore redundant follow/unfollow events") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val soc alGraphAct ons: TableFor3[Str ng, Wr eEvent, Seq[Un f edUserAct on]] = Table(
          ("act onType", " gnoredRedundantFollowUnfollowEvents", "expectedUn f edUserAct on"),
          ("Prof leFollow", sgFollowRedundantEvent, N l),
          ("Prof leFollow", sgFollowRedundant sFalseEvent, Seq(expectedUuaFollow)),
          ("Prof leUnfollow", sgUnfollowRedundantEvent, N l),
          ("Prof leUnfollow", sgUnfollowRedundant sFalseEvent, Seq(expectedUuaUnfollow))
        )
        forEvery(soc alGraphAct ons) {
          (_: Str ng, event: Wr eEvent, expected: Seq[Un f edUserAct on]) =>
            val actual = Soc alGraphAdapter.adaptEvent(event)
            assert(expected === actual)
        }
      }
    }
  }

  test("Soc alGraphAdapter  gnore Unsuccessful Soc alGraph events") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val unsuccessfulSoc alGraphEvents: TableFor1[Wr eEvent] = Table(
          " gnoredSoc alGraphEvents",
          sgUnsuccessfulFollowEvent,
          sgUnsuccessfulUnfollowEvent,
          sgUnsuccessfulBlockEvent,
          sgUnsuccessfulUnblockEvent,
          sgUnsuccessfulMuteEvent,
          sgUnsuccessfulUnmuteEvent
        )

        forEvery(unsuccessfulSoc alGraphEvents) { wr eEvent: Wr eEvent =>
          val actual = Soc alGraphAdapter.adaptEvent(wr eEvent)
          assert(actual. sEmpty)
        }
      }
    }
  }
}
