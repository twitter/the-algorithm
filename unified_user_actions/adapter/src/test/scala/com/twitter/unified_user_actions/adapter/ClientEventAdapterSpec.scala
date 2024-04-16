package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.cl entapp.thr ftscala. emType
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.Not f cat onTabDeta ls
 mport com.tw ter.cl entapp.thr ftscala.ReportDeta ls
 mport com.tw ter.cl entapp.thr ftscala.SearchDeta ls
 mport com.tw ter.cl entapp.thr ftscala.Suggest onDeta ls
 mport com.tw ter. nject.Test
 mport com.tw ter.logbase.thr ftscala.Cl entEventRece ver
 mport com.tw ter.reportflow.thr ftscala.ReportType
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.un f ed_user_act ons.adapter.cl ent_event.Cl entEventAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.T  
 mport org.scalatest.prop.TableDr venPropertyC cks
 mport org.scalatest.prop.TableFor1
 mport org.scalatest.prop.TableFor2
 mport scala.language. mpl c Convers ons

class Cl entEventAdapterSpec extends Test w h TableDr venPropertyC cks {
  // Tests for  nval d cl ent-events
  test("should  gnore events") {
    new TestF xtures.Cl entEventF xture {
      val eventsToBe gnored: TableFor2[Str ng, LogEvent] = Table(
        ("na space", "event"),
        ("ddg", ddgEvent),
        ("q g_ranker", q gRankerEvent),
        ("t  lnem xer", t  l neM xerEvent),
        ("t  l neserv ce", t  l neServ ceEvent),
        ("t etconvosvc", t etConcServ ceEvent),
        (" em-type  s non-t et", renderNonT et emTypeEvent)
      )

      forEvery(eventsToBe gnored) { (_: Str ng, event: LogEvent) =>
        val actual = Cl entEventAdapter.adaptEvent(event)
        assert(actual. sEmpty)
      }
    }
  }

  test("Tests for  emType f lter") {
    /// T et events
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val events = Table(
          (" emType", "expectedUUA"),
          (So ( emType.T et), Seq(expectedT etRenderDefaultT etUUA)),
          (So ( emType.QuotedT et), Seq(expectedT etRenderDefaultT etUUA)),
          (So ( emType.Top c), N l),
          (None, N l)
        )

        forEvery(events) { ( emTypeOpt: Opt on[ emType], expected: Seq[Un f edUserAct on]) =>
          val actual = Cl entEventAdapter.adaptEvent(
            act onTowardDefaultT etEvent(
              eventNa space = So (ceRenderEventNa space),
               emTypeOpt =  emTypeOpt
            ))
          assert(expected === actual)
        }
      }
    }

    /// Top c events
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val expected: Un f edUserAct on = mkExpectedUUAForAct onTowardTop cEvent(
          top c d = top c d,
          cl entEventNa space = So (uuaTop cFollowCl entEventNa space1),
          act onType = Act onType.Cl entTop cFollow
        )
        val events = Table(
          (" emType", "expectedUUA"),
          (So ( emType.T et), Seq(expected)),
          (So ( emType.QuotedT et), Seq(expected)),
          (So ( emType.Top c), Seq(expected)),
          (None, N l)
        )

        forEvery(events) { ( emTypeOpt: Opt on[ emType], expected: Seq[Un f edUserAct on]) =>
          val actual = Cl entEventAdapter.adaptEvent(
            act onTowardDefaultT etEvent(
              eventNa space = So (ceTop cFollow1),
               em d = None,
              suggest onDeta ls =
                So (Suggest onDeta ls(decodedControllerData = So (ho T etControllerData()))),
               emTypeOpt =  emTypeOpt
            ))
          assert(expected === actual)
        }
      }
    }
  }

  // Tests for Cl entT etRender mpress on
  test("Cl entT etRender mpress on") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          (
            "Default",
            act onTowardDefaultT etEvent(eventNa space = So (ceRenderEventNa space)),
            Seq(expectedT etRenderDefaultT etUUA)),
          (
            "Reply",
            act onTowardReplyEvent(eventNa space = So (ceRenderEventNa space)),
            Seq(expectedT etRenderReplyUUA)),
          (
            "Ret et",
            act onTowardRet etEvent(eventNa space = So (ceRenderEventNa space)),
            Seq(expectedT etRenderRet etUUA)),
          (
            "Quote",
            act onTowardQuoteEvent(
              eventNa space = So (ceRenderEventNa space),
              quotedAuthor d = So (456L)),
            Seq(expectedT etRenderQuoteUUA1, expectedT etRenderQuoteUUA2)),
          (
            "Ret et of a reply that quoted anot r T et",
            act onTowardRet etEventW hReplyAndQuote(eventNa space =
              So (ceRenderEventNa space)),
            Seq(
              expectedT etRenderRet etW hReplyAndQuoteUUA1,
              expectedT etRenderRet etW hReplyAndQuoteUUA2))
        )
        forEvery(cl entEvents) {
          (_: Str ng, event: LogEvent, expectedUUA: Seq[Un f edUserAct on]) =>
            val actual = Cl entEventAdapter.adaptEvent(event)
            actual should conta n t Sa Ele ntsAs expectedUUA
        }
      }
    }
  }

  test("Cl entT etGallery/Deta l mpress on") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          (
            "Deta l mpress on: t et::t et:: mpress on",
            act onTowardDefaultT etEvent(eventNa space = So (ceT etDeta lsEventNa space1)),
            expectedT etDeta l mpress onUUA1),
          (
            "Gallery mpress on: gallery:photo: mpress on",
            act onTowardDefaultT etEvent(eventNa space = So (ceGalleryEventNa space)),
            expectedT etGallery mpress onUUA),
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etL nger mpress on
  test("Cl entT etL nger mpress on") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          ("Default", l ngerDefaultT etEvent, expectedT etL ngerDefaultT etUUA),
          ("Reply", l ngerReplyEvent, expectedT etL ngerReplyUUA),
          ("Ret et", l ngerRet etEvent, expectedT etL ngerRet etUUA),
          ("Quote", l ngerQuoteEvent, expectedT etL ngerQuoteUUA),
          (
            "Ret et of a reply that quoted anot r T et",
            l ngerRet etW hReplyAndQuoteEvent,
            expectedT etL ngerRet etW hReplyAndQuoteUUA),
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etCl ckQuote
  test(
    "Cl ckQuote, wh ch  s t  cl ck on t  quote button, results  n sett ng ret et ng,  nReplyTo, quoted t et  ds") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = Cl entEventAdapter.adaptEvent(
          // t re shouldn't be any quot ngT et d  n CE w n    s "quote"
          act onTowardRet etEventW hReplyAndQuote(eventNa space = So (
            EventNa space(
              act on = So ("quote")
            ))))
        assert(Seq(expectedT etCl ckQuoteUUA) === actual)
      }
    }
  }

  // Tests for Cl entT etQuote
  test(
    "Quote, wh ch  s send ng t  quote, results  n sett ng ret et ng,  nReplyTo, quoted t et  ds") {
    new TestF xtures.Cl entEventF xture {
      val act ons: TableFor1[Str ng] = Table(
        "act on",
        "send_quote_t et",
        "ret et_w h_com nt"
      )

      T  .w hT  At(frozenT  ) { _ =>
        forEvery(act ons) { act on =>
          val actual = Cl entEventAdapter.adaptEvent(
            // t re shouldn't be any quot ngT et d  n CE w n    s "quote"
            act onTowardRet etEventW hReplyAndQuote(eventNa space = So (
              EventNa space(
                act on = So (act on)
              ))))
          assert(Seq(expectedT etQuoteUUA(act on)) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etFav and Cl entT etUnfav
  test("Cl entT etFav and Cl entT etUnfav") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          (
            "Default T et favor e",
            act onTowardDefaultT etEvent(eventNa space = So (ceFavor eEventNa space)),
            expectedT etFavor eDefaultT etUUA),
          (
            "Reply T et favor e",
            act onTowardReplyEvent(eventNa space = So (ceFavor eEventNa space)),
            expectedT etFavor eReplyUUA),
          (
            "Ret et T et favor e",
            act onTowardRet etEvent(eventNa space = So (ceFavor eEventNa space)),
            expectedT etFavor eRet etUUA),
          (
            "Quote T et favor e",
            act onTowardQuoteEvent(eventNa space = So (ceFavor eEventNa space)),
            expectedT etFavor eQuoteUUA),
          (
            "Ret et of a reply that quoted anot r T et favor e",
            act onTowardRet etEventW hReplyAndQuote(eventNa space =
              So (ceFavor eEventNa space)),
            expectedT etFavor eRet etW hReplyAndQuoteUUA),
          (
            "Default T et unfavor e",
            act onTowardDefaultT etEvent(
              eventNa space = So (EventNa space(act on = So ("unfavor e"))),
            ),
            mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space = So (Cl entEventNa space(act on = So ("unfavor e"))),
              act onType = Act onType.Cl entT etUnfav
            ))
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etCl ckReply
  test("Cl entT etCl ckReply") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          (
            "Default",
            act onTowardDefaultT etEvent(eventNa space = So (ceCl ckReplyEventNa space)),
            expectedT etCl ckReplyDefaultT etUUA),
          (
            "Reply",
            act onTowardReplyEvent(eventNa space = So (ceCl ckReplyEventNa space)),
            expectedT etCl ckReplyReplyUUA),
          (
            "Ret et",
            act onTowardRet etEvent(eventNa space = So (ceCl ckReplyEventNa space)),
            expectedT etCl ckReplyRet etUUA),
          (
            "Quote",
            act onTowardQuoteEvent(eventNa space = So (ceCl ckReplyEventNa space)),
            expectedT etCl ckReplyQuoteUUA),
          (
            "Ret et of a reply that quoted anot r T et",
            act onTowardRet etEventW hReplyAndQuote(eventNa space =
              So (ceCl ckReplyEventNa space)),
            expectedT etCl ckReplyRet etW hReplyAndQuoteUUA)
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etReply
  test("Cl entT etReply") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          ("DefaultOrReply", replyToDefaultT etOrReplyEvent, expectedT etReplyDefaultT etUUA),
          ("Ret et", replyToRet etEvent, expectedT etReplyRet etUUA),
          ("Quote", replyToQuoteEvent, expectedT etReplyQuoteUUA),
          (
            "Ret et of a reply that quoted anot r T et",
            replyToRet etW hReplyAndQuoteEvent,
            expectedT etReplyRet etW hReplyAndQuoteUUA)
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etRet et and Cl entT etUnret et
  test("Cl entT etRet et and Cl entT etUnret et") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          (
            "Default T et ret et",
            act onTowardDefaultT etEvent(eventNa space = So (ceRet etEventNa space)),
            expectedT etRet etDefaultT etUUA),
          (
            "Reply T et ret et",
            act onTowardReplyEvent(eventNa space = So (ceRet etEventNa space)),
            expectedT etRet etReplyUUA),
          (
            "Ret et T et ret et",
            act onTowardRet etEvent(eventNa space = So (ceRet etEventNa space)),
            expectedT etRet etRet etUUA),
          (
            "Quote T et ret et",
            act onTowardQuoteEvent(eventNa space = So (ceRet etEventNa space)),
            expectedT etRet etQuoteUUA),
          (
            "Ret et of a reply that quoted anot r T et ret et",
            act onTowardRet etEventW hReplyAndQuote(eventNa space =
              So (ceRet etEventNa space)),
            expectedT etRet etRet etW hReplyAndQuoteUUA),
          (
            "Default T et unret et",
            act onTowardDefaultT etEvent(
              eventNa space = So (EventNa space(act on = So ("unret et"))),
            ),
            mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space = So (Cl entEventNa space(act on = So ("unret et"))),
              act onType = Act onType.Cl entT etUnret et
            ))
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  test(" nclude Top c  d") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = Cl entEventAdapter.adaptEvent(renderDefaultT etW hTop c dEvent)
        assert(Seq(expectedT etRenderDefaultT etW hTop c dUUA) === actual)
      }
    }
  }

  // Tests for Cl entT etV deoPlayback0, 25, 50, 75, 95, 100 PlayFromTap, Qual yV ew,
  // V deoV ew, MrcV ew, V ewThreshold
  test("Cl entT etV deoPlayback*") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("ceNa space", "uuaNa space", "uuaAct onType"),
          (
            ceV deoPlayback25,
            uuaV deoPlayback25Cl entEventNa space,
            Act onType.Cl entT etV deoPlayback25),
          (
            ceV deoPlayback50,
            uuaV deoPlayback50Cl entEventNa space,
            Act onType.Cl entT etV deoPlayback50),
          (
            ceV deoPlayback75,
            uuaV deoPlayback75Cl entEventNa space,
            Act onType.Cl entT etV deoPlayback75),
          (
            ceV deoPlayback95,
            uuaV deoPlayback95Cl entEventNa space,
            Act onType.Cl entT etV deoPlayback95),
          (
            ceV deoPlayFromTap,
            uuaV deoPlayFromTapCl entEventNa space,
            Act onType.Cl entT etV deoPlayFromTap),
          (
            ceV deoQual yV ew,
            uuaV deoQual yV ewCl entEventNa space,
            Act onType.Cl entT etV deoQual yV ew),
          (ceV deoV ew, uuaV deoV ewCl entEventNa space, Act onType.Cl entT etV deoV ew),
          (ceV deoMrcV ew, uuaV deoMrcV ewCl entEventNa space, Act onType.Cl entT etV deoMrcV ew),
          (
            ceV deoV ewThreshold,
            uuaV deoV ewThresholdCl entEventNa space,
            Act onType.Cl entT etV deoV ewThreshold),
          (
            ceV deoCtaUrlCl ck,
            uuaV deoCtaUrlCl ckCl entEventNa space,
            Act onType.Cl entT etV deoCtaUrlCl ck),
          (
            ceV deoCtaWatchCl ck,
            uuaV deoCtaWatchCl ckCl entEventNa space,
            Act onType.Cl entT etV deoCtaWatchCl ck),
        )

        for (ele nt <- v deoEventEle ntValues) {
          forEvery(cl entEvents) {
            (
              ceNa space: EventNa space,
              uuaNa space: Cl entEventNa space,
              uuaAct onType: Act onType
            ) =>
              val event = act onTowardDefaultT etEvent(
                eventNa space = So (ceNa space.copy(ele nt = So (ele nt))),
                 d aDeta lsV2 = So ( d aDeta lsV2),
                cl ent d aEvent = So (cl ent d aEvent),
                cardDeta ls = So (cardDeta ls)
              )
              val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
                cl entEventNa space = So (uuaNa space.copy(ele nt = So (ele nt))),
                act onType = uuaAct onType,
                t etAct on nfo = So (v deo tadata)
              )
              val actual = Cl entEventAdapter.adaptEvent(event)
              assert(Seq(expectedUUA) === actual)
          }
        }
      }
    }
  }

  // Tests for Cl entT etPhotoExpand
  test("Cl ent T et Photo Expand") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvent = act onTowardDefaultT etEvent(eventNa space = So (cePhotoExpand))
        val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
          cl entEventNa space = So (uuaPhotoExpandCl entEventNa space),
          act onType = Act onType.Cl entT etPhotoExpand
        )
        assert(Seq(expectedUUA) === Cl entEventAdapter.adaptEvent(cl entEvent))
      }
    }
  }

  // Tests for Cl entCardCl ck
  test("Cl ent Card Related") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("ceNa space", "ce emType", "uuaNa space", "uuaAct onType"),
          (
            ceCardCl ck,
             emType.T et,
            uuaCardCl ckCl entEventNa space,
            Act onType.Cl entCardCl ck),
          (
            ceCardCl ck,
             emType.User,
            uuaCardCl ckCl entEventNa space,
            Act onType.Cl entCardCl ck),
          (
            ceCardOpenApp,
             emType.T et,
            uuaCardOpenAppCl entEventNa space,
            Act onType.Cl entCardOpenApp),
          (
            ceCardApp nstallAttempt,
             emType.T et,
            uuaCardApp nstallAttemptCl entEventNa space,
            Act onType.Cl entCardApp nstallAttempt),
          (
            cePollCardVote1,
             emType.T et,
            uuaPollCardVote1Cl entEventNa space,
            Act onType.Cl entPollCardVote),
          (
            cePollCardVote2,
             emType.T et,
            uuaPollCardVote2Cl entEventNa space,
            Act onType.Cl entPollCardVote),
        )
        forEvery(cl entEvents) {
          (
            ceNa space: EventNa space,
            ce emType:  emType,
            uuaNa space: Cl entEventNa space,
            uuaAct onType: Act onType
          ) =>
            val event = act onTowardDefaultT etEvent(
              eventNa space = So (ceNa space),
               emTypeOpt = So (ce emType),
              author d = So (author d)
            )
            val expectedUUA = mkExpectedUUAForCardEvent(
               d = So ( emT et d),
              cl entEventNa space = So (uuaNa space),
              act onType = uuaAct onType,
               emType = So (ce emType),
              author d = So (author d)
            )
            val actual = Cl entEventAdapter.adaptEvent(event)
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etCl ck nt onScreenNa 
  test("Cl entT etCl ck nt onScreenNa ") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val userHandle = "so Handle"
        val cl entEvent = act onTowardDefaultT etEvent(
          eventNa space = So (ce nt onCl ck),
          targets = So (
            Seq(
              LogEvent em(
                 emType = So ( emType.User),
                 d = So (user d),
                na  = So (userHandle)))))
        val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
          cl entEventNa space = So (uua nt onCl ckCl entEventNa space),
          act onType = Act onType.Cl entT etCl ck nt onScreenNa ,
          t etAct on nfo = So (
            T etAct on nfo.Cl entT etCl ck nt onScreenNa (
              Cl entT etCl ck nt onScreenNa (act onProf le d = user d, handle = userHandle)))
        )
        assert(Seq(expectedUUA) === Cl entEventAdapter.adaptEvent(cl entEvent))
      }
    }
  }

  // Tests for Top c Follow/Unfollow act ons
  test("Top c Follow/Unfollow Act ons") {
    // T  Top c  d  s mostly from T  l neTop c controller data or Ho T ets controller data!
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("cl entEventNa sapce", "expectedUUANa space", "controllerData", "act onType"),
          (
            ceTop cFollow1,
            uuaTop cFollowCl entEventNa space1,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cFollow
          ),
          (
            ceTop cFollow1,
            uuaTop cFollowCl entEventNa space1,
            ho T etControllerData(),
            Act onType.Cl entTop cFollow),
          (
            ceTop cFollow2,
            uuaTop cFollowCl entEventNa space2,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cFollow
          ),
          (
            ceTop cFollow2,
            uuaTop cFollowCl entEventNa space2,
            ho T etControllerData(),
            Act onType.Cl entTop cFollow),
          (
            ceTop cFollow3,
            uuaTop cFollowCl entEventNa space3,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cFollow
          ),
          (
            ceTop cFollow3,
            uuaTop cFollowCl entEventNa space3,
            ho T etControllerData(),
            Act onType.Cl entTop cFollow),
          (
            ceTop cUnfollow1,
            uuaTop cUnfollowCl entEventNa space1,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cUnfollow
          ),
          (
            ceTop cUnfollow1,
            uuaTop cUnfollowCl entEventNa space1,
            ho T etControllerData(),
            Act onType.Cl entTop cUnfollow),
          (
            ceTop cUnfollow2,
            uuaTop cUnfollowCl entEventNa space2,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cUnfollow
          ),
          (
            ceTop cFollow2,
            uuaTop cFollowCl entEventNa space2,
            ho T etControllerData(),
            Act onType.Cl entTop cFollow),
          (
            ceTop cUnfollow3,
            uuaTop cUnfollowCl entEventNa space3,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cUnfollow
          ),
          (
            ceTop cUnfollow3,
            uuaTop cUnfollowCl entEventNa space3,
            ho T etControllerData(),
            Act onType.Cl entTop cUnfollow),
        )

        forEvery(cl entEvents) {
          (
            eventNa space: EventNa space,
            uuaNs: Cl entEventNa space,
            controllerData: ControllerData,
            act onType: Act onType
          ) =>
            val event = act onTowardDefaultT etEvent(
              eventNa space = So (eventNa space),
               em d = None,
              suggest onDeta ls =
                So (Suggest onDeta ls(decodedControllerData = So (controllerData)))
            )
            val expectedUUA = mkExpectedUUAForAct onTowardTop cEvent(
              top c d = top c d,
              trace d = None,
              cl entEventNa space = So (uuaNs),
              act onType = act onType
            )
            val actual = Cl entEventAdapter.adaptEvent(event)
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Top c Not nterested n &  s Undo act ons
  test("Top c Not nterested n &  s Undo act ons") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("cl entEventNa sapce", "expectedUUANa space", "controllerData", "act onType"),
          (
            ceTop cNot nterested n1,
            uuaTop cNot nterested nCl entEventNa space1,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cNot nterested n
          ),
          (
            ceTop cNot nterested n1,
            uuaTop cNot nterested nCl entEventNa space1,
            ho T etControllerData(),
            Act onType.Cl entTop cNot nterested n),
          (
            ceTop cNot nterested n2,
            uuaTop cNot nterested nCl entEventNa space2,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cNot nterested n
          ),
          (
            ceTop cNot nterested n2,
            uuaTop cNot nterested nCl entEventNa space2,
            ho T etControllerData(),
            Act onType.Cl entTop cNot nterested n),
          (
            ceTop cUndoNot nterested n1,
            uuaTop cUndoNot nterested nCl entEventNa space1,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cUndoNot nterested n
          ),
          (
            ceTop cUndoNot nterested n1,
            uuaTop cUndoNot nterested nCl entEventNa space1,
            ho T etControllerData(),
            Act onType.Cl entTop cUndoNot nterested n),
          (
            ceTop cUndoNot nterested n2,
            uuaTop cUndoNot nterested nCl entEventNa space2,
            t  l neTop cControllerData(),
            Act onType.Cl entTop cUndoNot nterested n
          ),
          (
            ceTop cUndoNot nterested n2,
            uuaTop cUndoNot nterested nCl entEventNa space2,
            ho T etControllerData(),
            Act onType.Cl entTop cUndoNot nterested n),
        )

        forEvery(cl entEvents) {
          (
            eventNa space: EventNa space,
            uuaNs: Cl entEventNa space,
            controllerData: ControllerData,
            act onType: Act onType
          ) =>
            val event = act onTowardDefaultT etEvent(
              eventNa space = So (eventNa space),
               em d = None,
              suggest onDeta ls =
                So (Suggest onDeta ls(decodedControllerData = So (controllerData)))
            )
            val expectedUUA = mkExpectedUUAForAct onTowardTop cEvent(
              top c d = top c d,
              trace d = None,
              cl entEventNa space = So (uuaNs),
              act onType = act onType
            )
            val actual = Cl entEventAdapter.adaptEvent(event)
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for author nfo
  test("author nfo") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("author dOpt", " sFollo dByAct ngUser", " sFollow ngAct ngUser"),
          (So (author d), true, false),
          (So (author d), true, true),
          (So (author d), false, true),
          (So (author d), false, false),
          (None, true, true),
        )
        forEvery(cl entEvents) {
          (
            author dOpt: Opt on[Long],
             sFollo dByAct ngUser: Boolean,
             sFollow ngAct ngUser: Boolean
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              renderDefaultT etUserFollowStatusEvent(
                author d = author dOpt,
                 sFollo dByAct ngUser =  sFollo dByAct ngUser,
                 sFollow ngAct ngUser =  sFollow ngAct ngUser
              ))
            val expected =
              expectedT etRenderDefaultT etW hAuthor nfoUUA(author nfo = author dOpt.map {  d =>
                Author nfo(
                  author d = So ( d),
                   sFollo dByAct ngUser = So ( sFollo dByAct ngUser),
                   sFollow ngAct ngUser = So ( sFollow ngAct ngUser)
                )
              })
            assert(Seq(expected) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etReport
  test("Cl entT etReport") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val ceNTabT etReport: EventNa space =
          ceT etReport.copy(page = So ("ntab"), sect on = So ("all"), component = So ("urt"))

        val uuaNTabT etReport: Cl entEventNa space =
          uuaT etReport.copy(page = So ("ntab"), sect on = So ("all"), component = So ("urt"))

        val params = Table(
          (
            "eventType",
            "ceNa space",
            "ceNot f cat onTabDeta ls",
            "ceReportDeta ls",
            "uuaNa space",
            "uuaT etAct on nfo",
            "uuaProductSurface",
            "uuaProductSurface nfo"),
          (
            "ntabReportT etCl ck",
            ceNTabT etReport.copy(act on = So ("cl ck")),
            So (not f cat onTabT etEventDeta ls),
            None,
            uuaNTabT etReport.copy(act on = So ("cl ck")),
            reportT etCl ck,
            So (ProductSurface.Not f cat onTab),
            So (not f cat onTabProductSurface nfo)
          ),
          (
            "ntabReportT etDone",
            ceNTabT etReport.copy(act on = So ("done")),
            So (not f cat onTabT etEventDeta ls),
            None,
            uuaNTabT etReport.copy(act on = So ("done")),
            reportT etDone,
            So (ProductSurface.Not f cat onTab),
            So (not f cat onTabProductSurface nfo)
          ),
          (
            "defaultReportT etDone",
            ceT etReport.copy(page = So ("t et"), act on = So ("done")),
            None,
            None,
            uuaT etReport.copy(page = So ("t et"), act on = So ("done")),
            reportT etDone,
            None,
            None
          ),
          (
            "defaultReportT etW hReportFlow d",
            ceT etReport.copy(page = So ("t et"), act on = So ("done")),
            None,
            So (ReportDeta ls(reportFlow d = So (reportFlow d))),
            uuaT etReport.copy(page = So ("t et"), act on = So ("done")),
            reportT etW hReportFlow d,
            None,
            None
          ),
          (
            "defaultReportT etW houtReportFlow d",
            ceT etReport.copy(page = So ("t et"), act on = So ("done")),
            None,
            None,
            uuaT etReport.copy(page = So ("t et"), act on = So ("done")),
            reportT etW houtReportFlow d,
            None,
            None
          ),
        )

        forEvery(params) {
          (
            _: Str ng,
            ceNa space: EventNa space,
            ceNot f cat onTabDeta ls: Opt on[Not f cat onTabDeta ls],
            ceReportDeta ls: Opt on[ReportDeta ls],
            uuaNa space: Cl entEventNa space,
            uuaT etAct on nfo: T etAct on nfo,
            productSurface: Opt on[ProductSurface],
            productSurface nfo: Opt on[ProductSurface nfo]
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              act onTowardDefaultT etEvent(
                eventNa space = So (ceNa space),
                not f cat onTabDeta ls = ceNot f cat onTabDeta ls,
                reportDeta ls = ceReportDeta ls))

            val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space = So (uuaNa space),
              act onType = Act onType.Cl entT etReport,
              t etAct on nfo = So (uuaT etAct on nfo),
              productSurface = productSurface,
              productSurface nfo = productSurface nfo
            )

            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etNot lpful and Cl entT etUndoNot lpful
  test("Cl entT etNot lpful & UndoNot lpful") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val act ons = Table(("act on"), "cl ck", "undo")
        val ele nt = "feedback_g vefeedback"
        forEvery(act ons) { act on =>
          val cl entEvent =
            act onTowardDefaultT etEvent(
              eventNa space = So (ceEventNa space(ele nt, act on)),
            )

          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaCl entEventNa space(ele nt, act on)),
            act onType = act on match {
              case "cl ck" => Act onType.Cl entT etNot lpful
              case "undo" => Act onType.Cl entT etUndoNot lpful
            }
          )

          val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etNot nterested n and Cl entT etUndoNot nterested n
  test("Cl entT etNot nterested n & UndoNot nterested n") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val act ons = Table(("act on"), "cl ck", "undo")
        val ele nt = "feedback_dontl ke"
        forEvery(act ons) { act on =>
          val cl entEvent =
            act onTowardDefaultT etEvent(
              eventNa space = So (ceEventNa space(ele nt, act on)),
            )

          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaCl entEventNa space(ele nt, act on)),
            act onType = act on match {
              case "cl ck" => Act onType.Cl entT etNot nterested n
              case "undo" => Act onType.Cl entT etUndoNot nterested n
            }
          )

          val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etNotAboutTop c & Cl entT etUndoNotAboutTop c
  test("Cl entT etNotAboutTop c & Cl entT etUndoNotAboutTop c") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val act ons = Table(("act on"), "cl ck", "undo")
        val ele nt = "feedback_notabouttop c"
        forEvery(act ons) { act on =>
          val cl entEvent =
            act onTowardDefaultT etEvent(
              eventNa space = So (ceEventNa space(ele nt, act on)),
            )

          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaCl entEventNa space(ele nt, act on)),
            act onType = act on match {
              case "cl ck" => Act onType.Cl entT etNotAboutTop c
              case "undo" => Act onType.Cl entT etUndoNotAboutTop c
            }
          )

          val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etNotRecent and Cl entT etUndoNotRecent
  test("Cl entT etNotRecent & UndoNotRecent") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val act ons = Table(("act on"), "cl ck", "undo")
        val ele nt = "feedback_notrecent"
        forEvery(act ons) { act on =>
          val cl entEvent =
            act onTowardDefaultT etEvent(
              eventNa space = So (ceEventNa space(ele nt, act on)),
            )

          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaCl entEventNa space(ele nt, act on)),
            act onType = act on match {
              case "cl ck" => Act onType.Cl entT etNotRecent
              case "undo" => Act onType.Cl entT etUndoNotRecent
            }
          )

          val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etSeeFe r and Cl entT etUndoSeeFe r
  test("Cl entT etSeeFe r & Cl entT etUndoSeeFe r") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val act ons = Table(("act on"), "cl ck", "undo")
        val ele nt = "feedback_seefe r"
        forEvery(act ons) { act on =>
          val cl entEvent =
            act onTowardDefaultT etEvent(
              eventNa space = So (ceEventNa space(ele nt, act on)),
            )

          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaCl entEventNa space(ele nt, act on)),
            act onType = act on match {
              case "cl ck" => Act onType.Cl entT etSeeFe r
              case "undo" => Act onType.Cl entT etUndoSeeFe r
            }
          )

          val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for getEvent tadata
  test("getEvent tadata") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("cl entEventNa sapce", "expectedUUANa space", "controllerData"),
          (
            ceRenderEventNa space,
            uuaRenderCl entEventNa space,
            ho T etControllerData()
          ),
        )

        forEvery(cl entEvents) {
          (
            eventNa space: EventNa space,
            uuaNs: Cl entEventNa space,
            controllerData: ControllerData
          ) =>
            val event = act onTowardDefaultT etEvent(
              eventNa space = So (eventNa space),
              suggest onDeta ls =
                So (Suggest onDeta ls(decodedControllerData = So (controllerData)))
            )
            val expectedEvent taData = mkUUAEvent tadata(
              cl entEventNa space = So (uuaNs)
            )
            val actual = Cl entEventAdapter.adaptEvent(event). ad.event tadata
            assert(expectedEvent taData === actual)
        }
      }
    }
  }

  // Tests for getS ceT  stamp
  test("getS ceT  stamp") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val params = Table(
          ("testCase", "cl entEvent", "expectedUUAEventT  stamp"),
          (
            "CES event w h Dr ftAdjustedEventCreatedAtMs",
            act onTowardDefaultT etEvent(eventNa space = So (ceRenderEventNa space)),
            logBase.dr ftAdjustedEventCreatedAtMs),
          (
            "CES event w hout Dr ftAdjustedEventCreatedAtMs:  gnore",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceRenderEventNa space),
              logBase = logBase.unsetDr ftAdjustedEventCreatedAtMs),
            None),
          (
            "Non-CES event w hout Dr ftAdjustedEventCreatedAtMs: use logBase.t  stamp",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceRenderEventNa space),
              logBase = logBase
                .copy(
                  cl entEventRece ver =
                    So (Cl entEventRece ver.Unknown)).unsetDr ftAdjustedEventCreatedAtMs
            ),
            So (logBase.t  stamp))
        )
        forEvery(params) { (_: Str ng, event: LogEvent, expectedUUAEventT  stamp: Opt on[Long]) =>
          val actual =
            Cl entEventAdapter.adaptEvent(event).map(_.event tadata.s ceT  stampMs). adOpt on
          assert(expectedUUAEventT  stamp === actual)
        }
      }
    }
  }

  // Tests for ServerT etReport
  test("ServerT etReport") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val params = Table(
          ("eventType", "ceNa space", "ceReportDeta ls", "uuaNa space", "uuaT etAct on nfo"),
          (
            "Report mpress on sNotAdapted",
            ceT etReportFlow(page = "report_abuse", act on = " mpress on"),
            So (ReportDeta ls(reportFlow d = So (reportFlow d))),
            None,
            None
          ),
          (
            "ReportSubm  sAdapted",
            ceT etReportFlow(page = "report_abuse", act on = "subm "),
            So (
              ReportDeta ls(
                reportFlow d = So (reportFlow d),
                reportType = So (ReportType.Abuse))),
            So (uuaT etReportFlow(page = "report_abuse", act on = "subm ")),
            So (reportT etSubm )
          ),
        )

        forEvery(params) {
          (
            _: Str ng,
            ceNa space: EventNa space,
            ceReportDeta ls: Opt on[ReportDeta ls],
            uuaNa space: Opt on[Cl entEventNa space],
            uuaT etAct on nfo: Opt on[T etAct on nfo]
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              act onTowardDefaultT etEvent(
                eventNa space = So (ceNa space),
                reportDeta ls = ceReportDeta ls))

            val expectedUUA =
               f (ceNa space.act on.conta ns("subm "))
                Seq(
                  mkExpectedUUAForAct onTowardDefaultT etEvent(
                    cl entEventNa space = uuaNa space,
                    act onType = Act onType.ServerT etReport,
                    t etAct on nfo = uuaT etAct on nfo
                  ))
              else N l

            assert(expectedUUA === actual)
        }
      }
    }
  }

  // Tests for Cl entNot f cat onOpen
  test("Cl entNot f cat onOpen") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvent =
          pushNot f cat onEvent(
            eventNa space = So (ceNot f cat onOpen),
            not f cat onDeta ls = So (not f cat onDeta ls))

        val expectedUUA = mkExpectedUUAForNot f cat onEvent(
          cl entEventNa space = So (uuaNot f cat onOpen),
          act onType = Act onType.Cl entNot f cat onOpen,
          not f cat onContent = t etNot f cat onContent,
          productSurface = So (ProductSurface.PushNot f cat on),
          productSurface nfo = So (
            ProductSurface nfo.PushNot f cat on nfo(
              PushNot f cat on nfo(not f cat on d = not f cat on d)))
        )

        val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Tests for Cl entNot f cat onCl ck
  test("Cl entNot f cat onCl ck") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val params = Table(
          ("not f cat onType", "ceNot f cat onTabDeta ls", "uuaNot f cat onContent"),
          ("t etNot f cat on", not f cat onTabT etEventDeta ls, t etNot f cat onContent),
          (
            "mult T etNot f cat on",
            not f cat onTabMult T etEventDeta ls,
            mult T etNot f cat onContent),
          (
            "unknownNot f cat on",
            not f cat onTabUnknownEventDeta ls,
            unknownNot f cat onContent
          ),
        )

        forEvery(params) {
          (
            _: Str ng,
            ceNot f cat onTabDeta ls: Not f cat onTabDeta ls,
            uuaNot f cat onContent: Not f cat onContent
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              act onTowardNot f cat onEvent(
                eventNa space = So (ceNot f cat onCl ck),
                not f cat onTabDeta ls = So (ceNot f cat onTabDeta ls)))

            val expectedUUA = mkExpectedUUAForNot f cat onEvent(
              cl entEventNa space = So (uuaNot f cat onCl ck),
              act onType = Act onType.Cl entNot f cat onCl ck,
              not f cat onContent = uuaNot f cat onContent,
              productSurface = So (ProductSurface.Not f cat onTab),
              productSurface nfo = So (not f cat onTabProductSurface nfo)
            )

            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entNot f cat onSeeLessOften
  test("Cl entNot f cat onSeeLessOften") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val params = Table(
          ("not f cat onType", "ceNot f cat onTabDeta ls", "uuaNot f cat onContent"),
          ("t etNot f cat on", not f cat onTabT etEventDeta ls, t etNot f cat onContent),
          (
            "mult T etNot f cat on",
            not f cat onTabMult T etEventDeta ls,
            mult T etNot f cat onContent),
          ("unknownNot f cat on", not f cat onTabUnknownEventDeta ls, unknownNot f cat onContent),
        )

        forEvery(params) {
          (
            _: Str ng,
            ceNot f cat onTabDeta ls: Not f cat onTabDeta ls,
            uuaNot f cat onContent: Not f cat onContent
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              act onTowardNot f cat onEvent(
                eventNa space = So (ceNot f cat onSeeLessOften),
                not f cat onTabDeta ls = So (ceNot f cat onTabDeta ls)))

            val expectedUUA = mkExpectedUUAForNot f cat onEvent(
              cl entEventNa space = So (uuaNot f cat onSeeLessOften),
              act onType = Act onType.Cl entNot f cat onSeeLessOften,
              not f cat onContent = uuaNot f cat onContent,
              productSurface = So (ProductSurface.Not f cat onTab),
              productSurface nfo = So (not f cat onTabProductSurface nfo)
            )

            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etCl ck
  test("Cl entT etCl ck") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val params = Table(
          ("eventNa ", "page", "nTabDeta ls", "uuaProductSurface", "uuaProductSurface nfo"),
          ("t etCl ck", " ssages", None, None, None),
          (
            "t etCl ck nNTab",
            "ntab",
            So (not f cat onTabT etEventDeta ls),
            So (ProductSurface.Not f cat onTab),
            So (not f cat onTabProductSurface nfo))
        )

        forEvery(params) {
          (
            _: Str ng,
            page: Str ng,
            not f cat onTabDeta ls: Opt on[Not f cat onTabDeta ls],
            uuaProductSurface: Opt on[ProductSurface],
            uuaProductSurface nfo: Opt on[ProductSurface nfo]
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              act onTowardDefaultT etEvent(
                eventNa space = So (ceT etCl ck.copy(page = So (page))),
                not f cat onTabDeta ls = not f cat onTabDeta ls))

            val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space = So (uuaT etCl ck.copy(page = So (page))),
              act onType = Act onType.Cl entT etCl ck,
              productSurface = uuaProductSurface,
              productSurface nfo = uuaProductSurface nfo
            )

            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etCl ckProf le
  test("Cl entT etCl ckProf le") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual =
          Cl entEventAdapter.adaptEvent(
            prof leCl ckEvent(eventNa space = So (ceT etCl ckProf le)))

        val expectedUUA = mkExpectedUUAForProf leCl ck(
          cl entEventNa space = So (uuaT etCl ckProf le),
          act onType = Act onType.Cl entT etCl ckProf le,
          author nfo = So (
            Author nfo(
              author d = So (author d)
            )))
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Tests for Cl entT etCl ckShare
  test("Cl entT etCl ckShare") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual =
          Cl entEventAdapter.adaptEvent(
            act onTowardDefaultT etEvent(
              eventNa space = So (EventNa space(act on = So ("share_ nu_cl ck"))),
              author d = So (author d),
              t etPos  on = So (1),
              promoted d = So ("promted_123")
            ))

        val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
          cl entEventNa space = So (Cl entEventNa space(act on = So ("share_ nu_cl ck"))),
          act onType = Act onType.Cl entT etCl ckShare,
          author nfo = So (
            Author nfo(
              author d = So (author d)
            )),
          t etPos  on = So (1),
          promoted d = So ("promted_123")
        )
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Tests for Cl entT etShareV a* and Cl entT etUnbookmark
  test("Cl entT etShareV a and Unbookmark") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  nput = Table(
          ("eventNa spaceAct on", "uuaAct onTypes"),
          ("bookmark", Seq(Act onType.Cl entT etShareV aBookmark, Act onType.Cl entT etBookmark)),
          ("copy_l nk", Seq(Act onType.Cl entT etShareV aCopyL nk)),
          ("share_v a_dm", Seq(Act onType.Cl entT etCl ckSendV aD rect ssage)),
          ("unbookmark", Seq(Act onType.Cl entT etUnbookmark))
        )

        forEvery( nput) { (eventNa spaceAct on: Str ng, uuaAct onTypes: Seq[Act onType]) =>
          val actual: Seq[Un f edUserAct on] =
            Cl entEventAdapter.adaptEvent(
              act onTowardDefaultT etEvent(
                eventNa space = So (EventNa space(act on = So (eventNa spaceAct on))),
                author d = So (author d)))

           mpl c  def any2 erable[A](a: A):  erable[A] = So (a)
          val expectedUUA: Seq[Un f edUserAct on] = uuaAct onTypes.flatMap { uuaAct onType =>
            mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space =
                So (Cl entEventNa space(act on = So (eventNa spaceAct on))),
              act onType = uuaAct onType,
              author nfo = So (
                Author nfo(
                  author d = So (author d)
                ))
            )
          }
          assert(expectedUUA === actual)
        }
      }
    }
  }

  // Test for Cl entT etCl ckHashtag
  test("Cl entT etCl ckHashtag") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val events = Table(
          ("targets", "t etAct on nfo"),
          (
            So (Seq(LogEvent em(na  = So ("test_hashtag")))),
            So (
              T etAct on nfo.Cl entT etCl ckHashtag(
                Cl entT etCl ckHashtag(hashtag = So ("test_hashtag"))))),
          (
            So (Seq.empty[LogEvent em]),
            So (T etAct on nfo.Cl entT etCl ckHashtag(Cl entT etCl ckHashtag(hashtag = None)))),
          (
            So (N l),
            So (T etAct on nfo.Cl entT etCl ckHashtag(Cl entT etCl ckHashtag(hashtag = None)))),
          (
            None,
            So (T etAct on nfo.Cl entT etCl ckHashtag(Cl entT etCl ckHashtag(hashtag = None))))
        )
        forEvery(events) {
          (targets: Opt on[Seq[LogEvent em]], t etAct on nfo: Opt on[T etAct on nfo]) =>
            val cl entEvent =
              act onTowardDefaultT etEvent(
                eventNa space = So (ceCl ckHashtag),
                targets = targets)
            val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space = So (uuaCl ckHashtagCl entEventNa space),
              act onType = Act onType.Cl entT etCl ckHashtag,
              t etAct on nfo = t etAct on nfo
            )
            assert(Seq(expectedUUA) === Cl entEventAdapter.adaptEvent(cl entEvent))
        }

      }
    }
  }

  // Tests for Cl entT etV deoPlaybackStart and Cl entT etV deoPlaybackComplete
  test("Cl ent T et V deo Playback Start and Complete") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  nput = Table(
          ("ceNa space", "uuaNa space", "uuaAct onType"),
          (
            ceV deoPlaybackStart,
            uuaV deoPlaybackStartCl entEventNa space,
            Act onType.Cl entT etV deoPlaybackStart),
          (
            ceV deoPlaybackComplete,
            uuaV deoPlaybackCompleteCl entEventNa space,
            Act onType.Cl entT etV deoPlaybackComplete),
        )
        for (ele nt <- v deoEventEle ntValues) {
          forEvery( nput) {
            (
              ceNa space: EventNa space,
              uuaNa space: Cl entEventNa space,
              uuaAct onType: Act onType
            ) =>
              val cl entEvent = act onTowardDefaultT etEvent(
                eventNa space = So (ceNa space.copy(ele nt = So (ele nt))),
                 d aDeta lsV2 = So ( d aDeta lsV2),
                cl ent d aEvent = So (cl ent d aEvent),
                cardDeta ls = So (cardDeta ls)
              )
              val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
                cl entEventNa space = So (uuaNa space.copy(ele nt = So (ele nt))),
                act onType = uuaAct onType,
                t etAct on nfo = So (v deo tadata)
              )
              assert(Cl entEventAdapter.adaptEvent(cl entEvent).conta ns(expectedUUA))
          }
        }

        for (ele nt <-  nval dV deoEventEle ntValues) {
          forEvery( nput) {
            (
              ceNa space: EventNa space,
              uuaNa space: Cl entEventNa space,
              uuaAct onType: Act onType
            ) =>
              val cl entEvent = act onTowardDefaultT etEvent(
                eventNa space = So (ceNa space.copy(ele nt = So (ele nt))),
                 d aDeta lsV2 = So ( d aDeta lsV2),
                cl ent d aEvent = So (cl ent d aEvent)
              )
              val unexpectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
                cl entEventNa space = So (uuaNa space.copy(ele nt = So (ele nt))),
                act onType = uuaAct onType,
                t etAct on nfo = So (v deo tadata)
              )
              assert(!Cl entEventAdapter.adaptEvent(cl entEvent).conta ns(unexpectedUUA))
          }
        }
      }
    }
  }

  // Tests for Cl entT etNotRelevant and Cl entT etUndoNotRelevant
  test("Cl entT etNotRelevant & UndoNotRelevant") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val act ons = Table(("act on"), "cl ck", "undo")
        val ele nt = "feedback_notrelevant"
        forEvery(act ons) { act on =>
          val cl entEvent =
            act onTowardDefaultT etEvent(
              eventNa space = So (ceEventNa space(ele nt, act on)),
            )

          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaCl entEventNa space(ele nt, act on)),
            act onType = act on match {
              case "cl ck" => Act onType.Cl entT etNotRelevant
              case "undo" => Act onType.Cl entT etUndoNotRelevant
            }
          )

          val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entNot f cat onD sm ss
  test("Cl entNot f cat onD sm ss") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvent =
          pushNot f cat onEvent(
            eventNa space = So (ceNot f cat onD sm ss),
            not f cat onDeta ls = So (not f cat onDeta ls))

        val expectedUUA = mkExpectedUUAForNot f cat onEvent(
          cl entEventNa space = So (uuaNot f cat onD sm ss),
          act onType = Act onType.Cl entNot f cat onD sm ss,
          not f cat onContent = t etNot f cat onContent,
          productSurface = So (ProductSurface.PushNot f cat on),
          productSurface nfo = So (
            ProductSurface nfo.PushNot f cat on nfo(
              PushNot f cat on nfo(not f cat on d = not f cat on d)))
        )

        val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Tests for Cl entTypea adCl ck
  test("Cl entTypea adCl ck") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val searchQuery = "searchQuery"

        val  nput = Table(
          ("cl entEventTargets", "typea adAct on nfo"),
          (
            So (Seq(LogEvent em( d = So (user d),  emType = So ( emType.User)))),
            Typea adAct on nfo.UserResult(UserResult(prof le d = user d))),
          (
            So (Seq(LogEvent em(na  = So (s"$searchQuery"),  emType = So ( emType.Search)))),
            Typea adAct on nfo.Top cQueryResult(
              Top cQueryResult(suggestedTop cQuery = s"$searchQuery")))
        )
        forEvery( nput) {
          (
            cl entEventTargets: Opt on[Seq[LogEvent em]],
            typea adAct on nfo: Typea adAct on nfo,
          ) =>
            val cl entEvent =
              act onTowardsTypea adEvent(
                eventNa space = So (ceTypea adCl ck),
                targets = cl entEventTargets,
                searchQuery = searchQuery)
            val expectedUUA = mkExpectedUUAForTypea adAct on(
              cl entEventNa space = So (uuaTypea adCl ck),
              act onType = Act onType.Cl entTypea adCl ck,
              typea adAct on nfo = typea adAct on nfo,
              searchQuery = searchQuery
            )
            val actual = Cl entEventAdapter.adaptEvent(cl entEvent)
            assert(Seq(expectedUUA) === actual)
        }
        // Test ng  nval d target  em type case
        assert(
          Seq() === Cl entEventAdapter.adaptEvent(
            act onTowardsTypea adEvent(
              eventNa space = So (ceTypea adCl ck),
              targets =
                So (Seq(LogEvent em( d = So ( emT et d),  emType = So ( emType.T et)))),
              searchQuery = searchQuery)))
      }
    }
  }

  // Tests for Cl entFeedbackPromptSubm 
  test("Cl entFeedbackPromptSubm ") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val searchQuery: Str ng = "searchQuery"
        val searchDeta ls = So (SearchDeta ls(query = So (searchQuery)))
        val  nput = Table(
          ("logEvent", "uuaNa space", "uuaAct onType", "FeedbackPrompt nfo"),
          (
            act onTowardDefaultT etEvent(
              eventNa space = So (ceT etRelevantToSearch),
              searchDeta ls = searchDeta ls
            ),
            uuaT etRelevantToSearch,
            Act onType.Cl entFeedbackPromptSubm ,
            FeedbackPrompt nfo(feedbackPromptAct on nfo =
              FeedbackPromptAct on nfo.T etRelevantToSearch(
                T etRelevantToSearch(
                  searchQuery = searchQuery,
                  t et d =  emT et d,
                   sRelevant = So (true))))),
          (
            act onTowardDefaultT etEvent(
              eventNa space = So (ceT etNotRelevantToSearch),
              searchDeta ls = searchDeta ls
            ),
            uuaT etNotRelevantToSearch,
            Act onType.Cl entFeedbackPromptSubm ,
            FeedbackPrompt nfo(feedbackPromptAct on nfo =
              FeedbackPromptAct on nfo.T etRelevantToSearch(
                T etRelevantToSearch(
                  searchQuery = searchQuery,
                  t et d =  emT et d,
                   sRelevant = So (false))))),
          (
            act onTowardSearchResultPageEvent(
              eventNa space = So (ceSearchResultsRelevant),
              searchDeta ls = searchDeta ls,
               ems = So (Seq(LogEvent em( emType = So ( emType.RelevancePrompt))))
            ),
            uuaSearchResultsRelevant,
            Act onType.Cl entFeedbackPromptSubm ,
            FeedbackPrompt nfo(feedbackPromptAct on nfo =
              FeedbackPromptAct on nfo.D d F nd Search(
                D d F nd Search(searchQuery = searchQuery,  sRelevant = So (true))))),
          (
            act onTowardSearchResultPageEvent(
              eventNa space = So (ceSearchResultsNotRelevant),
              searchDeta ls = searchDeta ls,
               ems = So (Seq(LogEvent em( emType = So ( emType.RelevancePrompt))))
            ),
            uuaSearchResultsNotRelevant,
            Act onType.Cl entFeedbackPromptSubm ,
            FeedbackPrompt nfo(feedbackPromptAct on nfo =
              FeedbackPromptAct on nfo.D d F nd Search(
                D d F nd Search(searchQuery = searchQuery,  sRelevant = So (false)))))
        )

        forEvery( nput) {
          (
            logEvent: LogEvent,
            uuaNa space: Cl entEventNa space,
            uuaAct onType: Act onType,
            feedbackPrompt nfo: FeedbackPrompt nfo
          ) =>
            val actual =
              Cl entEventAdapter.adaptEvent(logEvent)
            val expectedUUA = mkExpectedUUAForFeedbackSubm Act on(
              cl entEventNa space = So (uuaNa space),
              act onType = uuaAct onType,
              feedbackPrompt nfo = feedbackPrompt nfo,
              searchQuery = searchQuery)
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entProf le*
  test("Cl entProf le*") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  nput = Table(
          ("eventNa ", "ceNa space", "uuaNa space", "uuaAct onType"),
          ("prof le_block", ceProf leBlock, uuaProf leBlock, Act onType.Cl entProf leBlock),
          ("prof le_unblock", ceProf leUnblock, uuaProf leUnblock, Act onType.Cl entProf leUnblock),
          ("prof le_mute", ceProf leMute, uuaProf leMute, Act onType.Cl entProf leMute),
          ("prof le_report", ceProf leReport, uuaProf leReport, Act onType.Cl entProf leReport),
          ("prof le_follow", ceProf leFollow, uuaProf leFollow, Act onType.Cl entProf leFollow),
          ("prof le_cl ck", ceProf leCl ck, uuaProf leCl ck, Act onType.Cl entProf leCl ck),
          (
            "prof le_follow_attempt",
            ceProf leFollowAttempt,
            uuaProf leFollowAttempt,
            Act onType.Cl entProf leFollowAttempt),
          ("prof le_show", ceProf leShow, uuaProf leShow, Act onType.Cl entProf leShow),
        )
        forEvery( nput) {
          (
            eventNa : Str ng,
            ceNa space: EventNa space,
            uuaNa space: Cl entEventNa space,
            uuaAct onType: Act onType
          ) =>
            val actual =
              Cl entEventAdapter.adaptEvent(
                act onTowardProf leEvent(
                  eventNa  = eventNa ,
                  eventNa space = So (ceNa space)
                ))
            val expectedUUA = mkExpectedUUAForProf leAct on(
              cl entEventNa space = So (uuaNa space),
              act onType = uuaAct onType,
              act onProf le d =  emProf le d)
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }
  // Tests for Cl entT etEngage ntAttempt
  test("Cl entT etEngage ntAttempt") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("eventNa ", "ceNa space", "uuaNa space", "uuaAct onType"),
          (
            "t et_fav  e_attempt",
            ceT etFavor eAttempt,
            uuaT etFavor eAttempt,
            Act onType.Cl entT etFavor eAttempt),
          (
            "t et_ret et_attempt",
            ceT etRet etAttempt,
            uuaT etRet etAttempt,
            Act onType.Cl entT etRet etAttempt),
          (
            "t et_reply_attempt",
            ceT etReplyAttempt,
            uuaT etReplyAttempt,
            Act onType.Cl entT etReplyAttempt),
        )
        forEvery(cl entEvents) {
          (
            eventNa : Str ng,
            ceNa space: EventNa space,
            uuaNa space: Cl entEventNa space,
            uuaAct onType: Act onType
          ) =>
            val actual =
              Cl entEventAdapter.adaptEvent(act onTowardDefaultT etEvent(So (ceNa space)))
            val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
              cl entEventNa space = So (uuaNa space),
              act onType = uuaAct onType)
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for LoggedOut for Cl entLog n*
  test("Cl entLog n*") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("eventNa ", "ceNa space", "uuaNa space", "uuaAct onType"),
          (
            "cl ent_cl ck_log n",
            ceCl entCTALog nCl ck,
            uuaCl entCTALog nCl ck,
            Act onType.Cl entCTALog nCl ck),
          (
            "cl ent_cl ck_show",
            ceCl entCTALog nStart,
            uuaCl entCTALog nStart,
            Act onType.Cl entCTALog nStart),
          (
            "cl ent_log n_success",
            ceCl entCTALog nSuccess,
            uuaCl entCTALog nSuccess,
            Act onType.Cl entCTALog nSuccess),
        )

        forEvery(cl entEvents) {
          (
            eventNa : Str ng,
            ceNa space: EventNa space,
            uuaNa space: Cl entEventNa space,
            uuaAct onType: Act onType
          ) =>
            val actual =
              Cl entEventAdapter.adaptEvent(
                mkLogEvent(
                  eventNa ,
                  So (ceNa space),
                  logBase = So (logBase1),
                  eventDeta ls = None,
                  pushNot f cat onDeta ls = None,
                  reportDeta ls = None,
                  searchDeta ls = None))
            val expectedUUA = mkExpectedUUAForAct onTowardCTAEvent(
              cl entEventNa space = So (uuaNa space),
              act onType = uuaAct onType,
              guest dMarket ngOpt = logBase1.guest dMarket ng
            )

            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for LoggedOut for Cl entS gnup*
  test("Cl entS gnup*") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("eventNa ", "ceNa space", "uuaNa space", "uuaAct onType"),
          (
            "cl ent_cl ck_s gnup",
            ceCl entCTAS gnupCl ck,
            uuaCl entCTAS gnupCl ck,
            Act onType.Cl entCTAS gnupCl ck),
          (
            "cl ent_s gnup_success",
            ceCl entCTAS gnupSuccess,
            uuaCl entCTAS gnupSuccess,
            Act onType.Cl entCTAS gnupSuccess),
        )

        forEvery(cl entEvents) {
          (
            eventNa : Str ng,
            ceNa space: EventNa space,
            uuaNa space: Cl entEventNa space,
            uuaAct onType: Act onType
          ) =>
            val actual =
              Cl entEventAdapter.adaptEvent(
                mkLogEvent(
                  eventNa ,
                  So (ceNa space),
                  logBase = So (logBase1),
                  eventDeta ls = None,
                  pushNot f cat onDeta ls = None,
                  reportDeta ls = None,
                  searchDeta ls = None))
            val expectedUUA = mkExpectedUUAForAct onTowardCTAEvent(
              cl entEventNa space = So (uuaNa space),
              act onType = uuaAct onType,
              guest dMarket ngOpt = logBase1.guest dMarket ng
            )
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etFollowAuthor
  test("Cl entT etFollowAuthor") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val testEventsL st = Seq(
          (ceT etFollowAuthor1, uuaT etFollowAuthor1, T etAuthorFollowCl ckS ce.Caret nu),
          (ceT etFollowAuthor2, uuaT etFollowAuthor2, T etAuthorFollowCl ckS ce.Prof le mage)
        )
        testEventsL st.foreach {
          case (eventNa space, cl entEventNa space, followCl ckS ce) =>
            val actual =
              Cl entEventAdapter.adaptEvent(
                t etAct onTowardAuthorEvent(
                  eventNa  = "t et_follow_author",
                  eventNa space = So (eventNa space)
                ))
            val expectedUUA = mkExpectedUUAForT etAct onTowardAuthor(
              cl entEventNa space = So (cl entEventNa space),
              act onType = Act onType.Cl entT etFollowAuthor,
              author nfo = So (
                Author nfo(
                  author d = So (author d)
                )),
              t etAct on nfo = So (
                T etAct on nfo.Cl entT etFollowAuthor(
                  Cl entT etFollowAuthor(followCl ckS ce)
                ))
            )
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etUnfollowAuthor
  test("Cl entT etUnfollowAuthor") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val testEventsL st = Seq(
          (
            ceT etUnfollowAuthor1,
            uuaT etUnfollowAuthor1,
            T etAuthorUnfollowCl ckS ce.Caret nu),
          (
            ceT etUnfollowAuthor2,
            uuaT etUnfollowAuthor2,
            T etAuthorUnfollowCl ckS ce.Prof le mage)
        )
        testEventsL st.foreach {
          case (eventNa space, cl entEventNa space, unfollowCl ckS ce) =>
            val actual =
              Cl entEventAdapter.adaptEvent(
                t etAct onTowardAuthorEvent(
                  eventNa  = "t et_unfollow_author",
                  eventNa space = So (eventNa space)
                ))
            val expectedUUA = mkExpectedUUAForT etAct onTowardAuthor(
              cl entEventNa space = So (cl entEventNa space),
              act onType = Act onType.Cl entT etUnfollowAuthor,
              author nfo = So (
                Author nfo(
                  author d = So (author d)
                )),
              t etAct on nfo = So (
                T etAct on nfo.Cl entT etUnfollowAuthor(
                  Cl entT etUnfollowAuthor(unfollowCl ckS ce)
                ))
            )
            assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  // Tests for Cl entT etMuteAuthor
  test("Cl entT etMuteAuthor") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual =
          Cl entEventAdapter.adaptEvent(
            t etAct onTowardAuthorEvent(
              eventNa  = "t et_mute_author",
              eventNa space = So (ceT etMuteAuthor)
            ))

        val expectedUUA = mkExpectedUUAForT etAct onTowardAuthor(
          cl entEventNa space = So (uuaT etMuteAuthor),
          act onType = Act onType.Cl entT etMuteAuthor,
          author nfo = So (
            Author nfo(
              author d = So (author d)
            )))
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Tests for Cl entT etBlockAuthor
  test("Cl entT etBlockAuthor") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual =
          Cl entEventAdapter.adaptEvent(
            t etAct onTowardAuthorEvent(
              eventNa  = "t et_block_author",
              eventNa space = So (ceT etBlockAuthor)
            ))

        val expectedUUA = mkExpectedUUAForT etAct onTowardAuthor(
          cl entEventNa space = So (uuaT etBlockAuthor),
          act onType = Act onType.Cl entT etBlockAuthor,
          author nfo = So (
            Author nfo(
              author d = So (author d)
            )))
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Tests for Cl entT etUnblockAuthor
  test("Cl entT etUnblockAuthor") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual =
          Cl entEventAdapter.adaptEvent(
            t etAct onTowardAuthorEvent(
              eventNa  = "t et_unblock_author",
              eventNa space = So (ceT etUnblockAuthor)
            ))

        val expectedUUA = mkExpectedUUAForT etAct onTowardAuthor(
          cl entEventNa space = So (uuaT etUnblockAuthor),
          act onType = Act onType.Cl entT etUnblockAuthor,
          author nfo = So (
            Author nfo(
              author d = So (author d)
            )))
        assert(Seq(expectedUUA) === actual)
      }
    }
  }

  // Test for Cl entT etOpenL nk
  test("Cl entT etOpenL nk") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  nput = Table(
          ("url", "t etAct on nfo"),
          (So ("go/url"), cl entOpenL nkW hUrl),
          (None, cl entOpenL nkW houtUrl)
        )

        forEvery( nput) { (url: Opt on[Str ng], t etAct on nfo: T etAct on nfo) =>
          val cl entEvent =
            act onTowardDefaultT etEvent(eventNa space = So (ceOpenL nk), url = url)
          val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
            cl entEventNa space = So (uuaOpenL nkCl entEventNa space),
            act onType = Act onType.Cl entT etOpenL nk,
            t etAct on nfo = So (t etAct on nfo)
          )
          assert(Seq(expectedUUA) === Cl entEventAdapter.adaptEvent(cl entEvent))
        }
      }
    }
  }

  // Test for Cl entT etTakeScreenshot
  test("Cl ent take screenshot") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvent =
          act onTowardDefaultT etEvent(
            eventNa space = So (ceTakeScreenshot),
            percentV s ble  ght100k = So (100))
        val expectedUUA = mkExpectedUUAForAct onTowardDefaultT etEvent(
          cl entEventNa space = So (uuaTakeScreenshotCl entEventNa space),
          act onType = Act onType.Cl entT etTakeScreenshot,
          t etAct on nfo = So (cl entTakeScreenshot)
        )
        assert(Seq(expectedUUA) === Cl entEventAdapter.adaptEvent(cl entEvent))
      }
    }
  }

  test("Ho  / Search product surface  ta data") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val cl entEvents = Table(
          ("act onT etType", "cl entEvent", "expectedUUAEvent"),
          (
            "ho T etEventW hControllerData",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceHo Favor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(decodedControllerData = So (
                  ho T etControllerDataV2(
                     njectedPos  on = So (1),
                    trace d = So (trace d),
                    requestJo n d = So (requestJo n d)
                  ))))
            ),
            expectedHo T etEventW hControllerData),
          (
            "ho T etEventW hSuggest onType",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceHo Favor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(
                  suggest onType = So ("Test_type")
                ))),
            expectedHo T etEventW hSuggestType),
          (
            "ho T etEventW hControllerDataSuggest onType",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceHo Favor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(
                  suggest onType = So ("Test_type"),
                  decodedControllerData = So (
                    ho T etControllerDataV2(
                       njectedPos  on = So (1),
                      trace d = So (trace d),
                      requestJo n d = So (requestJo n d)))
                ))
            ),
            expectedHo T etEventW hControllerDataSuggestType),
          (
            "ho LatestT etEventW hControllerData",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceHo LatestFavor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(decodedControllerData = So (
                  ho T etControllerDataV2(
                     njectedPos  on = So (1),
                    trace d = So (trace d),
                    requestJo n d = So (requestJo n d)
                  ))))
            ),
            expectedHo LatestT etEventW hControllerData),
          (
            "ho LatestT etEventW hSuggest onType",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceHo LatestFavor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(
                  suggest onType = So ("Test_type")
                ))),
            expectedHo LatestT etEventW hSuggestType),
          (
            "ho LatestT etEventW hControllerDataSuggest onType",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceHo LatestFavor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(
                  suggest onType = So ("Test_type"),
                  decodedControllerData = So (
                    ho T etControllerDataV2(
                       njectedPos  on = So (1),
                      trace d = So (trace d),
                      requestJo n d = So (requestJo n d)))
                ))
            ),
            expectedHo LatestT etEventW hControllerDataSuggestType),
          (
            "searchT etEventW hControllerData",
            act onTowardDefaultT etEvent(
              eventNa space = So (ceSearchFavor eEventNa space),
              suggest onDeta ls = So (
                Suggest onDeta ls(decodedControllerData = So (
                  mkSearchResultControllerData(
                    queryOpt = So ("tw ter"),
                    trace d = So (trace d),
                    requestJo n d = So (requestJo n d)
                  ))))
            ),
            expectedSearchT etEventW hControllerData),
        )
        forEvery(cl entEvents) { (_: Str ng, event: LogEvent, expectedUUA: Un f edUserAct on) =>
          val actual = Cl entEventAdapter.adaptEvent(event)
          assert(Seq(expectedUUA) === actual)
        }
      }
    }
  }

  test("Cl entAppEx ") {
    new TestF xtures.Cl entEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val durat on: Opt on[Long] = So (10000L)
        val  nputTable = Table(
          ("eventType", "cl entApp d", "sect on", "durat on", " sVal dEvent"),
          ("uas- Phone", So (129032L), So ("enter_background"), durat on, true),
          ("uas- Pad", So (191841L), So ("enter_background"), durat on, true),
          ("uas-andro d", So (258901L), None, durat on, true),
          ("none-cl ent d", None, None, durat on, false),
          (" nval d-cl ent d", So (1L), None, durat on, false),
          ("none-durat on", So (258901L), None, None, false),
          ("non-uas- Phone", So (129032L), None, durat on, false)
        )

        forEvery( nputTable) {
          (
            _: Str ng,
            cl entApp d: Opt on[Long],
            sect on: Opt on[Str ng],
            durat on: Opt on[Long],
             sVal dEvent: Boolean
          ) =>
            val actual = Cl entEventAdapter.adaptEvent(
              act onTowardsUasEvent(
                eventNa space = So (ceAppEx .copy(sect on = sect on)),
                cl entApp d = cl entApp d,
                durat on = durat on
              ))

             f ( sVal dEvent) {
              // create UUA UAS event
              val expectedUUA = mkExpectedUUAForUasEvent(
                cl entEventNa space = So (uuaAppEx .copy(sect on = sect on)),
                act onType = Act onType.Cl entAppEx ,
                cl entApp d = cl entApp d,
                durat on = durat on
              )
              assert(Seq(expectedUUA) === actual)
            } else {
              //  gnore t  event and do not create UUA UAS event
              assert(actual. sEmpty)
            }
        }
      }
    }
  }
}
