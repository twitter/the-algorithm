package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter. nject.Test
 mport com.tw ter.t  l neserv ce.thr ftscala._
 mport com.tw ter.un f ed_user_act ons.adapter.tls_favs_event.TlsFavsAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.T  

class TlsFavsAdapterSpec extends Test {
  tra  F xture {

    val frozenT   = T  .fromM ll seconds(1658949273000L)

    val favEventNoRet et = Contextual zedFavor eEvent(
      event = Favor eEventUn on.Favor e(
        Favor eEvent(
          user d = 91L,
          t et d = 1L,
          t etUser d = 101L,
          eventT  Ms = 1001L
        )
      ),
      context = LogEventContext(hostna  = "", trace d = 31L)
    )
    val favEventRet et = Contextual zedFavor eEvent(
      event = Favor eEventUn on.Favor e(
        Favor eEvent(
          user d = 92L,
          t et d = 2L,
          t etUser d = 102L,
          eventT  Ms = 1002L,
          ret et d = So (22L)
        )
      ),
      context = LogEventContext(hostna  = "", trace d = 32L)
    )
    val unfavEventNoRet et = Contextual zedFavor eEvent(
      event = Favor eEventUn on.Unfavor e(
        Unfavor eEvent(
          user d = 93L,
          t et d = 3L,
          t etUser d = 103L,
          eventT  Ms = 1003L
        )
      ),
      context = LogEventContext(hostna  = "", trace d = 33L)
    )
    val unfavEventRet et = Contextual zedFavor eEvent(
      event = Favor eEventUn on.Unfavor e(
        Unfavor eEvent(
          user d = 94L,
          t et d = 4L,
          t etUser d = 104L,
          eventT  Ms = 1004L,
          ret et d = So (44L)
        )
      ),
      context = LogEventContext(hostna  = "", trace d = 34L)
    )
    val favEventW hLangAndCountry = Contextual zedFavor eEvent(
      event = Favor eEventUn on.Favor e(
        Favor eEvent(
          user d = 91L,
          t et d = 1L,
          t etUser d = 101L,
          eventT  Ms = 1001L,
          v e rContext =
            So (V e r(requestCountryCode = So ("us"), requestLanguageCode = So ("en")))
        )
      ),
      context = LogEventContext(hostna  = "", trace d = 31L)
    )

    val expectedUua1 = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (91L)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = 1L,
          act onT etAuthor nfo = So (Author nfo(author d = So (101L))),
        )
      ),
      act onType = Act onType.ServerT etFav,
      event tadata = Event tadata(
        s ceT  stampMs = 1001L,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerTlsFavs,
        trace d = So (31L)
      )
    )
    val expectedUua2 = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (92L)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = 2L,
          act onT etAuthor nfo = So (Author nfo(author d = So (102L))),
          ret et ngT et d = So (22L)
        )
      ),
      act onType = Act onType.ServerT etFav,
      event tadata = Event tadata(
        s ceT  stampMs = 1002L,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerTlsFavs,
        trace d = So (32L)
      )
    )
    val expectedUua3 = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (93L)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = 3L,
          act onT etAuthor nfo = So (Author nfo(author d = So (103L))),
        )
      ),
      act onType = Act onType.ServerT etUnfav,
      event tadata = Event tadata(
        s ceT  stampMs = 1003L,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerTlsFavs,
        trace d = So (33L)
      )
    )
    val expectedUua4 = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (94L)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = 4L,
          act onT etAuthor nfo = So (Author nfo(author d = So (104L))),
          ret et ngT et d = So (44L)
        )
      ),
      act onType = Act onType.ServerT etUnfav,
      event tadata = Event tadata(
        s ceT  stampMs = 1004L,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerTlsFavs,
        trace d = So (34L)
      )
    )
    val expectedUua5 = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (91L)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = 1L,
          act onT etAuthor nfo = So (Author nfo(author d = So (101L))),
        )
      ),
      act onType = Act onType.ServerT etFav,
      event tadata = Event tadata(
        s ceT  stampMs = 1001L,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerTlsFavs,
        language = So ("EN"),
        countryCode = So ("US"),
        trace d = So (31L)
      )
    )
  }

  test("fav event w h no ret et") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(favEventNoRet et)
        assert(Seq(expectedUua1) === actual)
      }
    }
  }

  test("fav event w h a ret et") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(favEventRet et)
        assert(Seq(expectedUua2) === actual)
      }
    }
  }

  test("unfav event w h no ret et") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(unfavEventNoRet et)
        assert(Seq(expectedUua3) === actual)
      }
    }
  }

  test("unfav event w h a ret et") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(unfavEventRet et)
        assert(Seq(expectedUua4) === actual)
      }
    }
  }

  test("fav event w h language and country") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = TlsFavsAdapter.adaptEvent(favEventW hLangAndCountry)
        assert(Seq(expectedUua5) === actual)
      }
    }
  }
}
