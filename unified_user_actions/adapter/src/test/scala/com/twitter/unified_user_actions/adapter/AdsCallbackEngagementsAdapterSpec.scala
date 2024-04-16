package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.ads.spendserver.thr ftscala.SpendServerEvent
 mport com.tw ter.adserver.thr ftscala.Engage ntType
 mport com.tw ter.cl entapp.thr ftscala.Ampl fyDeta ls
 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.adapter.TestF xtures.AdsCallbackEngage ntsF xture
 mport com.tw ter.un f ed_user_act ons.adapter.ads_callback_engage nts.AdsCallbackEngage ntsAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etAct on nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.ut l.T  
 mport org.scalatest.prop.TableDr venPropertyC cks

class AdsCallbackEngage ntsAdapterSpec extends Test w h TableDr venPropertyC cks {

  test("Test bas c convers on for ads callback engage nt type fav") {

    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val events = Table(
          (" nputEvent", "expectedUuaOutput"),
          ( // Test w h author d
            createSpendServerEvent(Engage ntType.Fav),
            Seq(
              createExpectedUua(
                Act onType.ServerPromotedT etFav,
                createT et nfo em(author nfo = So (author nfo)))))
        )
        forEvery(events) { (event: SpendServerEvent, expected: Seq[Un f edUserAct on]) =>
          val actual = AdsCallbackEngage ntsAdapter.adaptEvent(event)
          assert(expected === actual)
        }
      }
    }
  }

  test("Test bas c convers on for d fferent engage nt types") {
    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val mapp ngs = Table(
          ("engage ntType", "act onType"),
          (Engage ntType.Unfav, Act onType.ServerPromotedT etUnfav),
          (Engage ntType.Reply, Act onType.ServerPromotedT etReply),
          (Engage ntType.Ret et, Act onType.ServerPromotedT etRet et),
          (Engage ntType.Block, Act onType.ServerPromotedT etBlockAuthor),
          (Engage ntType.Unblock, Act onType.ServerPromotedT etUnblockAuthor),
          (Engage ntType.Send, Act onType.ServerPromotedT etComposeT et),
          (Engage ntType.Deta l, Act onType.ServerPromotedT etCl ck),
          (Engage ntType.Report, Act onType.ServerPromotedT etReport),
          (Engage ntType.Mute, Act onType.ServerPromotedT etMuteAuthor),
          (Engage ntType.Prof leP c, Act onType.ServerPromotedT etCl ckProf le),
          (Engage ntType.ScreenNa , Act onType.ServerPromotedT etCl ckProf le),
          (Engage ntType.UserNa , Act onType.ServerPromotedT etCl ckProf le),
          (Engage ntType.Hashtag, Act onType.ServerPromotedT etCl ckHashtag),
          (Engage ntType.CarouselSw peNext, Act onType.ServerPromotedT etCarouselSw peNext),
          (
            Engage ntType.CarouselSw pePrev ous,
            Act onType.ServerPromotedT etCarouselSw pePrev ous),
          (Engage ntType.D llShort, Act onType.ServerPromotedT etL nger mpress onShort),
          (Engage ntType.D ll d um, Act onType.ServerPromotedT etL nger mpress on d um),
          (Engage ntType.D llLong, Act onType.ServerPromotedT etL nger mpress onLong),
          (Engage ntType.D sm ssSpam, Act onType.ServerPromotedT etD sm ssSpam),
          (Engage ntType.D sm ssW houtReason, Act onType.ServerPromotedT etD sm ssW houtReason),
          (Engage ntType.D sm ssUn nterest ng, Act onType.ServerPromotedT etD sm ssUn nterest ng),
          (Engage ntType.D sm ssRepet  ve, Act onType.ServerPromotedT etD sm ssRepet  ve),
        )

        forEvery(mapp ngs) { (engage ntType: Engage ntType, act onType: Act onType) =>
          val event = createSpendServerEvent(engage ntType)
          val actual = AdsCallbackEngage ntsAdapter.adaptEvent(event)
          val expected =
            Seq(createExpectedUua(act onType, createT et nfo em(author nfo = So (author nfo))))
          assert(expected === actual)
        }
      }
    }
  }

  test("Test convers on for ads callback engage nt type spotl ght v ew and cl ck") {
    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  nput = Table(
          ("adsEngage nt", "uuaAct on"),
          (Engage ntType.Spotl ghtCl ck, Act onType.ServerPromotedT etCl ckSpotl ght),
          (Engage ntType.Spotl ghtV ew, Act onType.ServerPromotedT etV ewSpotl ght),
          (Engage ntType.TrendV ew, Act onType.ServerPromotedTrendV ew),
          (Engage ntType.TrendCl ck, Act onType.ServerPromotedTrendCl ck),
        )
        forEvery( nput) { (engage ntType: Engage ntType, act onType: Act onType) =>
          val adsEvent = createSpendServerEvent(engage ntType)
          val expected = Seq(createExpectedUua(act onType, trend nfo em))
          val actual = AdsCallbackEngage ntsAdapter.adaptEvent(adsEvent)
          assert(expected === actual)
        }
      }
    }
  }

  test("Test bas c convers on for ads callback engage nt open l nk w h or w hout url") {
    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val  nput = Table(
          ("url", "t etAct on nfo"),
          (So ("go/url"), openL nkW hUrl),
          (None, openL nkW houtUrl)
        )

        forEvery( nput) { (url: Opt on[Str ng], t etAct on nfo: T etAct on nfo) =>
          val event = createSpendServerEvent(engage ntType = Engage ntType.Url, url = url)
          val actual = AdsCallbackEngage ntsAdapter.adaptEvent(event)
          val expected = Seq(createExpectedUua(
            Act onType.ServerPromotedT etOpenL nk,
            createT et nfo em(author nfo = So (author nfo), act on nfo = So (t etAct on nfo))))
          assert(expected === actual)
        }
      }
    }
  }

  test("Test bas c convers on for d fferent engage nt types w h prof le  nfo") {
    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val mapp ngs = Table(
          ("engage ntType", "act onType"),
          (Engage ntType.Follow, Act onType.ServerPromotedProf leFollow),
          (Engage ntType.Unfollow, Act onType.ServerPromotedProf leUnfollow)
        )
        forEvery(mapp ngs) { (engage ntType: Engage ntType, act onType: Act onType) =>
          val event = createSpendServerEvent(engage ntType)
          val actual = AdsCallbackEngage ntsAdapter.adaptEvent(event)
          val expected = Seq(createExpectedUuaW hProf le nfo(act onType))
          assert(expected === actual)
        }
      }
    }
  }

  test("Test bas c convers on for ads callback engage nt type v deo_content_*") {
    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val events = Table(
          ("engage ntType", "ampl fyDeta ls", "act onType", "t etAct on nfo"),
          //For v deo_content_* events on promoted t ets w n t re  s no preroll ad played
          (
            Engage ntType.V deoContentPlayback25,
            ampl fyDeta lsPromotedT etW houtAd,
            Act onType.ServerPromotedT etV deoPlayback25,
            t etAct on nfoPromotedT etW houtAd),
          (
            Engage ntType.V deoContentPlayback50,
            ampl fyDeta lsPromotedT etW houtAd,
            Act onType.ServerPromotedT etV deoPlayback50,
            t etAct on nfoPromotedT etW houtAd),
          (
            Engage ntType.V deoContentPlayback75,
            ampl fyDeta lsPromotedT etW houtAd,
            Act onType.ServerPromotedT etV deoPlayback75,
            t etAct on nfoPromotedT etW houtAd),
          //For v deo_content_* events on promoted t ets w n t re  s a preroll ad
          (
            Engage ntType.V deoContentPlayback25,
            ampl fyDeta lsPromotedT etW hAd,
            Act onType.ServerPromotedT etV deoPlayback25,
            t etAct on nfoPromotedT etW hAd),
          (
            Engage ntType.V deoContentPlayback50,
            ampl fyDeta lsPromotedT etW hAd,
            Act onType.ServerPromotedT etV deoPlayback50,
            t etAct on nfoPromotedT etW hAd),
          (
            Engage ntType.V deoContentPlayback75,
            ampl fyDeta lsPromotedT etW hAd,
            Act onType.ServerPromotedT etV deoPlayback75,
            t etAct on nfoPromotedT etW hAd),
        )
        forEvery(events) {
          (
            engage ntType: Engage ntType,
            ampl fyDeta ls: Opt on[Ampl fyDeta ls],
            act onType: Act onType,
            act on nfo: Opt on[T etAct on nfo]
          ) =>
            val spendEvent =
              createV deoSpendServerEvent(engage ntType, ampl fyDeta ls, promotedT et d, None)
            val expected = Seq(createExpectedV deoUua(act onType, act on nfo, promotedT et d))

            val actual = AdsCallbackEngage ntsAdapter.adaptEvent(spendEvent)
            assert(expected === actual)
        }
      }
    }
  }

  test("Test bas c convers on for ads callback engage nt type v deo_ad_*") {

    new AdsCallbackEngage ntsF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val events = Table(
          (
            "engage ntType",
            "ampl fyDeta ls",
            "act onType",
            "t etAct on nfo",
            "promotedT et d",
            "organ cT et d"),
          //For v deo_ad_* events w n t  preroll ad  s on a promoted t et.
          (
            Engage ntType.V deoAdPlayback25,
            ampl fyDeta lsPrerollAd,
            Act onType.ServerPromotedT etV deoAdPlayback25,
            t etAct on nfoPrerollAd,
            promotedT et d,
            None
          ),
          (
            Engage ntType.V deoAdPlayback50,
            ampl fyDeta lsPrerollAd,
            Act onType.ServerPromotedT etV deoAdPlayback50,
            t etAct on nfoPrerollAd,
            promotedT et d,
            None
          ),
          (
            Engage ntType.V deoAdPlayback75,
            ampl fyDeta lsPrerollAd,
            Act onType.ServerPromotedT etV deoAdPlayback75,
            t etAct on nfoPrerollAd,
            promotedT et d,
            None
          ),
          // For v deo_ad_* events w n t  preroll ad  s on an organ c t et.
          (
            Engage ntType.V deoAdPlayback25,
            ampl fyDeta lsPrerollAd,
            Act onType.ServerT etV deoAdPlayback25,
            t etAct on nfoPrerollAd,
            None,
            organ cT et d
          ),
          (
            Engage ntType.V deoAdPlayback50,
            ampl fyDeta lsPrerollAd,
            Act onType.ServerT etV deoAdPlayback50,
            t etAct on nfoPrerollAd,
            None,
            organ cT et d
          ),
          (
            Engage ntType.V deoAdPlayback75,
            ampl fyDeta lsPrerollAd,
            Act onType.ServerT etV deoAdPlayback75,
            t etAct on nfoPrerollAd,
            None,
            organ cT et d
          ),
        )
        forEvery(events) {
          (
            engage ntType: Engage ntType,
            ampl fyDeta ls: Opt on[Ampl fyDeta ls],
            act onType: Act onType,
            act on nfo: Opt on[T etAct on nfo],
            promotedT et d: Opt on[Long],
            organ cT et d: Opt on[Long],
          ) =>
            val spendEvent =
              createV deoSpendServerEvent(
                engage ntType,
                ampl fyDeta ls,
                promotedT et d,
                organ cT et d)
            val act onT et d =  f (organ cT et d. sDef ned) organ cT et d else promotedT et d
            val expected = Seq(createExpectedV deoUua(act onType, act on nfo, act onT et d))

            val actual = AdsCallbackEngage ntsAdapter.adaptEvent(spendEvent)
            assert(expected === actual)
        }
      }
    }
  }
}
