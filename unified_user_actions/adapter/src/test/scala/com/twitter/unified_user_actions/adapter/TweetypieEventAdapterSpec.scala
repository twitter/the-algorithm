package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter. nject.Test
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t etyp e.thr ftscala.Add  onalF eldDeleteEvent
 mport com.tw ter.t etyp e.thr ftscala.Add  onalF eldUpdateEvent
 mport com.tw ter.t etyp e.thr ftscala.Aud DeleteT et
 mport com.tw ter.t etyp e.thr ftscala.Dev ceS ce
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.Ed ControlEd 
 mport com.tw ter.t etyp e.thr ftscala.Language
 mport com.tw ter.t etyp e.thr ftscala.Place
 mport com.tw ter.t etyp e.thr ftscala.PlaceType
 mport com.tw ter.t etyp e.thr ftscala.QuotedT et
 mport com.tw ter.t etyp e.thr ftscala.QuotedT etDeleteEvent
 mport com.tw ter.t etyp e.thr ftscala.QuotedT etTakedownEvent
 mport com.tw ter.t etyp e.thr ftscala.Reply
 mport com.tw ter.t etyp e.thr ftscala.Share
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.T etCoreData
 mport com.tw ter.t etyp e.thr ftscala.T etCreateEvent
 mport com.tw ter.t etyp e.thr ftscala.T etDeleteEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEventData
 mport com.tw ter.t etyp e.thr ftscala.T etEventFlags
 mport com.tw ter.t etyp e.thr ftscala.T etPoss blySens  veUpdateEvent
 mport com.tw ter.t etyp e.thr ftscala.T etScrubGeoEvent
 mport com.tw ter.t etyp e.thr ftscala.T etTakedownEvent
 mport com.tw ter.t etyp e.thr ftscala.T etUndeleteEvent
 mport com.tw ter.t etyp e.thr ftscala.UserScrubGeoEvent
 mport com.tw ter.un f ed_user_act ons.adapter.t etyp e_event.T etyp eEventAdapter
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.T  
 mport org.scalatest.prop.TableDr venPropertyC cks
 mport org.scalatest.prop.TableFor1
 mport org.scalatest.prop.TableFor2
 mport org.scalatest.prop.TableFor3

class T etyp eEventAdapterSpec extends Test w h TableDr venPropertyC cks {
  tra  F xture {
    val frozenT  : T   = T  .fromM ll seconds(1658949273000L)

    val t etDeleteEventT  : T   = T  .fromM ll seconds(1658949253000L)

    val t et d = 1554576940856246272L
    val t  stamp: Long = Snowflake d.un xT  M ll sFrom d(t et d)
    val user d = 1L
    val user: User = User(
       d = user d,
      createdAtMsec = 1000L,
      updatedAtMsec = 1000L,
      userType = UserType.Normal,
    )

    val act onedT et d = 1554576940756246333L
    val act onedT etT  stamp: Long = Snowflake d.un xT  M ll sFrom d(act onedT et d)
    val act onedT etAuthor d = 2L

    val act onedByAct onedT et d = 1554566940756246272L
    val act onedByAct onedT etT  stamp: Long =
      Snowflake d.un xT  M ll sFrom d(act onedByAct onedT et d)
    val act onedByAct onedT etAuthor d = 3L

    val t etEventFlags: T etEventFlags = T etEventFlags(t  stampMs = t  stamp)
    val language: Opt on[Language] = So (Language("EN-US", false))
    val dev ceS ce: Opt on[Dev ceS ce] = So (
      Dev ceS ce(
         d = 0,
        para ter = "",
         nternalNa  = "",
        na  = "na ",
        url = "url",
        d splay = "d splay",
        cl entApp d = Opt on(100L)))
    val place: Opt on[Place] = So (
      Place(
         d = " d",
        `type` = PlaceType.C y,
        fullNa  = "San Franc sco",
        na  = "SF",
        countryCode = So ("US"),
      ))

    // for T etDeleteEvent
    val aud DeleteT et = So (
      Aud DeleteT et(
        cl entAppl cat on d = Opt on(200L)
      ))

    val t etCoreData: T etCoreData =
      T etCoreData(user d, text = "text", createdV a = "created_v a", createdAtSecs = t  stamp)
    val baseT et: T et = T et(
      t et d,
      coreData = So (t etCoreData),
      language = language,
      dev ceS ce = dev ceS ce,
      place = place)

    def getCreateT etCoreData(user d: Long, t  stamp: Long): T etCoreData =
      t etCoreData.copy(user d = user d, createdAtSecs = t  stamp)
    def getRet etT etCoreData(
      user d: Long,
      ret etedT et d: Long,
      ret etedAuthor d: Long,
      parentStatus d: Long,
      t  stamp: Long
    ): T etCoreData = t etCoreData.copy(
      user d = user d,
      share = So (
        Share(
          s ceStatus d = ret etedT et d,
          s ceUser d = ret etedAuthor d,
          parentStatus d = parentStatus d
        )),
      createdAtSecs = t  stamp
    )
    def getReplyT etCoreData(
      user d: Long,
      repl edT et d: Long,
      repl edAuthor d: Long,
      t  stamp: Long
    ): T etCoreData = t etCoreData.copy(
      user d = user d,
      reply = So (
        Reply(
           nReplyToStatus d = So (repl edT et d),
           nReplyToUser d = repl edAuthor d,
        )
      ),
      createdAtSecs = t  stamp)
    def getQuoteT etCoreData(user d: Long, t  stamp: Long): T etCoreData =
      t etCoreData.copy(user d = user d, createdAtSecs = t  stamp)

    def getT et(t et d: Long, user d: Long, t  stamp: Long): T et =
      baseT et.copy( d = t et d, coreData = So (getCreateT etCoreData(user d, t  stamp)))

    def getRet et(
      t et d: Long,
      user d: Long,
      t  stamp: Long,
      ret etedT et d: Long,
      ret etedUser d: Long,
      parentStatus d: Opt on[Long] = None
    ): T et =
      baseT et.copy(
         d = t et d,
        coreData = So (
          getRet etT etCoreData(
            user d,
            ret etedT et d,
            ret etedUser d,
            parentStatus d.getOrElse(ret etedT et d),
            t  stamp)))

    def getQuote(
      t et d: Long,
      user d: Long,
      t  stamp: Long,
      quotedT et d: Long,
      quotedUser d: Long
    ): T et =
      baseT et.copy(
         d = t et d,
        coreData = So (getQuoteT etCoreData(user d, t  stamp)),
        quotedT et = So (QuotedT et(quotedT et d, quotedUser d)))

    def getReply(
      t et d: Long,
      user d: Long,
      repl edT et d: Long,
      repl edAuthor d: Long,
      t  stamp: Long
    ): T et =
      baseT et.copy(
         d = t et d,
        coreData = So (getReplyT etCoreData(user d, repl edT et d, repl edAuthor d, t  stamp)),
      )

    //  gnored t et events
    val add  onalF eldUpdateEvent: T etEvent = T etEvent(
      T etEventData.Add  onalF eldUpdateEvent(Add  onalF eldUpdateEvent(baseT et)),
      t etEventFlags)
    val add  onalF eldDeleteEvent: T etEvent = T etEvent(
      T etEventData.Add  onalF eldDeleteEvent(
        Add  onalF eldDeleteEvent(Map(t et d -> Seq.empty))
      ),
      t etEventFlags
    )
    val t etUndeleteEvent: T etEvent = T etEvent(
      T etEventData.T etUndeleteEvent(T etUndeleteEvent(baseT et)),
      t etEventFlags
    )
    val t etScrubGeoEvent: T etEvent = T etEvent(
      T etEventData.T etScrubGeoEvent(T etScrubGeoEvent(t et d, user d)),
      t etEventFlags)
    val t etTakedownEvent: T etEvent = T etEvent(
      T etEventData.T etTakedownEvent(T etTakedownEvent(t et d, user d)),
      t etEventFlags
    )
    val userScrubGeoEvent: T etEvent = T etEvent(
      T etEventData.UserScrubGeoEvent(UserScrubGeoEvent(user d = user d, maxT et d = t et d)),
      t etEventFlags
    )
    val t etPoss blySens  veUpdateEvent: T etEvent = T etEvent(
      T etEventData.T etPoss blySens  veUpdateEvent(
        T etPoss blySens  veUpdateEvent(
          t et d = t et d,
          user d = user d,
          nsfwAdm n = false,
          nsfwUser = false)),
      t etEventFlags
    )
    val quotedT etDeleteEvent: T etEvent = T etEvent(
      T etEventData.QuotedT etDeleteEvent(
        QuotedT etDeleteEvent(
          quot ngT et d = t et d,
          quot ngUser d = user d,
          quotedT et d = t et d,
          quotedUser d = user d)),
      t etEventFlags
    )
    val quotedT etTakedownEvent: T etEvent = T etEvent(
      T etEventData.QuotedT etTakedownEvent(
        QuotedT etTakedownEvent(
          quot ngT et d = t et d,
          quot ngUser d = user d,
          quotedT et d = t et d,
          quotedUser d = user d,
          takedownCountryCodes = Seq.empty,
          takedownReasons = Seq.empty
        )
      ),
      t etEventFlags
    )
    val replyOnlyT et =
      getReply(t et d, user d, act onedT et d, act onedT etAuthor d, t  stamp)
    val replyAndRet etT et = replyOnlyT et.copy(coreData = replyOnlyT et.coreData.map(
      _.copy(share = So (
        Share(
          s ceStatus d = act onedT et d,
          s ceUser d = act onedT etAuthor d,
          parentStatus d = act onedT et d
        )))))
    val replyRet etPresentEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = replyAndRet etT et,
          user = user,
          s ceT et =
            So (getT et(act onedT et d, act onedT etAuthor d, act onedT etT  stamp))
        )),
      t etEventFlags
    )

    def getExpectedUUA(
      user d: Long,
      act onT et d: Long,
      act onT etAuthor d: Long,
      s ceT  stampMs: Long,
      act onType: Act onType,
      reply ngT et d: Opt on[Long] = None,
      quot ngT et d: Opt on[Long] = None,
      ret et ngT et d: Opt on[Long] = None,
       nReplyToT et d: Opt on[Long] = None,
      quotedT et d: Opt on[Long] = None,
      ret etedT et d: Opt on[Long] = None,
      ed edT et d: Opt on[Long] = None,
      app d: Opt on[Long] = None,
    ): Un f edUserAct on = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (user d)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = act onT et d,
          act onT etAuthor nfo = So (Author nfo(author d = So (act onT etAuthor d))),
          reply ngT et d = reply ngT et d,
          quot ngT et d = quot ngT et d,
          ret et ngT et d = ret et ngT et d,
           nReplyToT et d =  nReplyToT et d,
          quotedT et d = quotedT et d,
          ret etedT et d = ret etedT et d,
          ed edT et d = ed edT et d
        )
      ),
      act onType = act onType,
      event tadata = Event tadata(
        s ceT  stampMs = s ceT  stampMs,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerT etyp eEvents,
        language = None,
        countryCode = So ("US"),
        cl entApp d = app d,
      )
    )

    /* Note: T   s a deprecated f eld {Act onT etType}.
     *   keep t   re to docu nt t  behav ors of each un  test.
    /*
     * Types of t ets on wh ch act ons can take place.
     * Note that ret ets are not  ncluded because act ons can NOT take place
     * on ret ets. T y can only take place on s ce t ets of ret ets,
     * wh ch are one of t  Act onT etTypes l sted below.
     */
    enum Act onT etType {
    /*  s a standard (non-ret et, non-reply, non-quote) t et */
    Default = 0

    /*
     *  s a t et  n a reply cha n (t   ncludes t ets
     * w hout a lead ng @ nt on, as long as t y are  n reply
     * to so  t et  d)
     */
    Reply = 1

    /*  s a ret et w h com nt */
    Quote = 2
    }(pers sted='true', hasPersonalData='false')
     */

    // t et create
    val t etCreateEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getT et(t et d, user d, t  stamp),
          user = user,
        )
      ),
      t etEventFlags)
    val expectedUUACreate = getExpectedUUA(
      user d = user d,
      act onT et d = t et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Default),
       */
      act onT etAuthor d = user d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etCreate,
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )

    // t et reply to a default
    val t etReplyDefaultEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getReply(t et d, user d, act onedT et d, act onedT etAuthor d, t  stamp),
          user = user
        )
      ),
      t etEventFlags
    )
    val expectedUUAReplyDefault = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = None,
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etReply,
      reply ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et reply to a reply
    val t etReplyToReplyEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getReply(t et d, user d, act onedT et d, act onedT etAuthor d, t  stamp),
          user = user
        )
      ),
      t etEventFlags
    )
    // t et reply to a quote
    val t etReplyToQuoteEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getReply(t et d, user d, act onedT et d, act onedT etAuthor d, t  stamp),
          user = user
        )
      ),
      t etEventFlags
    )
    // t et quote a default
    val t etQuoteDefaultEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getQuote(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = user,
          quotedT et =
            So (getT et(act onedT et d, act onedT etAuthor d, act onedT etT  stamp))
        )
      ),
      t etEventFlags
    )
    val expectedUUAQuoteDefault: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Default),
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etQuote,
      quot ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et quote a reply
    val t etQuoteReplyEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getQuote(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = user,
          quotedT et = So (
            getReply(
              t et d = act onedT et d,
              user d = act onedT etAuthor d,
              repl edT et d = act onedByAct onedT et d,
              repl edAuthor d = act onedByAct onedT etAuthor d,
              t  stamp = act onedT etT  stamp
            ))
        )
      ),
      t etEventFlags
    )
    val expectedUUAQuoteReply: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Reply),
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etQuote,
      quot ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et quote a quote
    val t etQuoteQuoteEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getQuote(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = user,
          quotedT et = So (
            getQuote(
              t et d = act onedT et d,
              user d = act onedT etAuthor d,
              t  stamp = act onedT etT  stamp,
              quotedT et d = act onedByAct onedT et d,
              quotedUser d = act onedByAct onedT etAuthor d,
            ))
        )
      ),
      t etEventFlags
    )
    val expectedUUAQuoteQuote: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Quote),
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etQuote,
      quot ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et ret et a default
    val t etRet etDefaultEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getRet et(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = user,
          s ceT et =
            So (getT et(act onedT et d, act onedT etAuthor d, act onedT etT  stamp))
        )
      ),
      t etEventFlags
    )
    val expectedUUARet etDefault: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Default),
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etRet et,
      ret et ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et ret et a reply
    val t etRet etReplyEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getRet et(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = user,
          s ceT et = So (
            getReply(
              act onedT et d,
              act onedT etAuthor d,
              act onedByAct onedT et d,
              act onedByAct onedT etAuthor d,
              act onedT etT  stamp))
        )
      ),
      t etEventFlags
    )
    val expectedUUARet etReply: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Reply),
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etRet et,
      ret et ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et ret et a quote
    val t etRet etQuoteEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getRet et(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = user,
          s ceT et = So (
            getQuote(
              act onedT et d,
              act onedT etAuthor d,
              act onedT etT  stamp,
              act onedByAct onedT et d,
              act onedByAct onedT etAuthor d
            ))
        )
      ),
      t etEventFlags
    )
    val expectedUUARet etQuote: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Quote),
       */
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etRet et,
      ret et ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // t et ret et a ret et
    val t etRet etRet etEvent: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getRet et(
            t et d,
            user d,
            t  stamp,
            act onedByAct onedT et d,
            act onedByAct onedT etAuthor d,
            So (act onedT et d)),
          user = user,
          s ceT et = So (
            getT et(
              act onedByAct onedT et d,
              act onedByAct onedT etAuthor d,
              act onedByAct onedT etT  stamp,
            ))
        )
      ),
      t etEventFlags
    )
    val expectedUUARet etRet et: Un f edUserAct on = getExpectedUUA(
      user d = user d,
      act onT et d = act onedByAct onedT et d,
      /* @see com nt above for Act onT etType
      act onT etType = So (Act onT etType.Default),
       */
      act onT etAuthor d = act onedByAct onedT etAuthor d,
      s ceT  stampMs = t  stamp,
      act onType = Act onType.ServerT etRet et,
      ret et ngT et d = So (t et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // delete a t et
    val t etDeleteEvent: T etEvent = T etEvent(
      T etEventData.T etDeleteEvent(
        T etDeleteEvent(
          t et = getT et(t et d, user d, t  stamp),
          user = So (user),
          aud  = aud DeleteT et
        )
      ),
      t etEventFlags.copy(t  stampMs = t etDeleteEventT  . nM ll seconds)
    )
    val expectedUUADeleteDefault: Un f edUserAct on = getExpectedUUA(
      user d = user. d,
      act onT et d = t et d,
      act onT etAuthor d = user d,
      s ceT  stampMs = t etDeleteEventT  . nM ll seconds,
      act onType = Act onType.ServerT etDelete,
      app d = aud DeleteT et.flatMap(_.cl entAppl cat on d)
    )
    // delete a reply - Unreply
    val t etUnreplyEvent: T etEvent = T etEvent(
      T etEventData.T etDeleteEvent(
        T etDeleteEvent(
          t et = getReply(t et d, user d, act onedT et d, act onedT etAuthor d, t  stamp),
          user = So (user),
          aud  = aud DeleteT et
        )
      ),
      t etEventFlags.copy(t  stampMs = t etDeleteEventT  . nM ll seconds)
    )
    val expectedUUAUnreply: Un f edUserAct on = getExpectedUUA(
      user d = user. d,
      act onT et d = act onedT et d,
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t etDeleteEventT  . nM ll seconds,
      act onType = Act onType.ServerT etUnreply,
      reply ngT et d = So (t et d),
      app d = aud DeleteT et.flatMap(_.cl entAppl cat on d)
    )
    // delete a quote - Unquote
    val t etUnquoteEvent: T etEvent = T etEvent(
      T etEventData.T etDeleteEvent(
        T etDeleteEvent(
          t et = getQuote(t et d, user d, t  stamp, act onedT et d, act onedT etAuthor d),
          user = So (user),
          aud  = aud DeleteT et
        )
      ),
      t etEventFlags.copy(t  stampMs = t etDeleteEventT  . nM ll seconds)
    )
    val expectedUUAUnquote: Un f edUserAct on = getExpectedUUA(
      user d = user. d,
      act onT et d = act onedT et d,
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t etDeleteEventT  . nM ll seconds,
      act onType = Act onType.ServerT etUnquote,
      quot ngT et d = So (t et d),
      app d = aud DeleteT et.flatMap(_.cl entAppl cat on d)
    )
    // delete a ret et / unret et
    val t etUnret etEvent: T etEvent = T etEvent(
      T etEventData.T etDeleteEvent(
        T etDeleteEvent(
          t et = getRet et(
            t et d,
            user d,
            t  stamp,
            act onedT et d,
            act onedT etAuthor d,
            So (act onedT et d)),
          user = So (user),
          aud  = aud DeleteT et
        )
      ),
      t etEventFlags.copy(t  stampMs = t etDeleteEventT  . nM ll seconds)
    )
    val expectedUUAUnret et: Un f edUserAct on = getExpectedUUA(
      user d = user. d,
      act onT et d = act onedT et d,
      act onT etAuthor d = act onedT etAuthor d,
      s ceT  stampMs = t etDeleteEventT  . nM ll seconds,
      act onType = Act onType.ServerT etUnret et,
      ret et ngT et d = So (t et d),
      app d = aud DeleteT et.flatMap(_.cl entAppl cat on d)
    )
    // ed  a t et, t  new t et from ed   s a default t et (not reply/quote/ret et)
    val regularT etFromEd Event: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getT et(
            t et d,
            user d,
            t  stamp
          ).copy(ed Control =
            So (Ed Control.Ed (Ed ControlEd ( n  alT et d = act onedT et d)))),
          user = user,
        )
      ),
      t etEventFlags
    )
    val expectedUUARegularT etFromEd : Un f edUserAct on = getExpectedUUA(
      user d = user. d,
      act onT et d = t et d,
      act onT etAuthor d = user d,
      s ceT  stampMs = t etEventFlags.t  stampMs,
      act onType = Act onType.ServerT etEd ,
      ed edT et d = So (act onedT et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
    // ed  a t et, t  new t et from ed   s a Quote
    val quoteFromEd Event: T etEvent = T etEvent(
      T etEventData.T etCreateEvent(
        T etCreateEvent(
          t et = getQuote(
            t et d,
            user d,
            t  stamp,
            act onedT et d,
            act onedT etAuthor d
          ).copy(ed Control =
            So (Ed Control.Ed (Ed ControlEd ( n  alT et d = act onedByAct onedT et d)))),
          user = user,
        )
      ),
      t etEventFlags
    )
    val expectedUUAQuoteFromEd : Un f edUserAct on = getExpectedUUA(
      user d = user. d,
      act onT et d = t et d,
      act onT etAuthor d = user d,
      s ceT  stampMs = t etEventFlags.t  stampMs,
      act onType = Act onType.ServerT etEd ,
      ed edT et d = So (act onedByAct onedT et d),
      quotedT et d = So (act onedT et d),
      app d = dev ceS ce.flatMap(_.cl entApp d)
    )
  }

  test(" gnore non-T etCreate / non-T etDelete events") {
    new F xture {
      val  gnoredT etEvents: TableFor1[T etEvent] = Table(
        " gnoredT etEvents",
        add  onalF eldUpdateEvent,
        add  onalF eldDeleteEvent,
        t etUndeleteEvent,
        t etScrubGeoEvent,
        t etTakedownEvent,
        userScrubGeoEvent,
        t etPoss blySens  veUpdateEvent,
        quotedT etDeleteEvent,
        quotedT etTakedownEvent
      )
      forEvery( gnoredT etEvents) { t etEvent: T etEvent =>
        val actual = T etyp eEventAdapter.adaptEvent(t etEvent)
        assert(actual. sEmpty)
      }
    }
  }

  test(" gnore  nval d T etCreate events") {
    new F xture {
      val  gnoredT etEvents: TableFor2[Str ng, T etEvent] = Table(
        (" nval dType", "event"),
        ("replyAndRet etBothPresent", replyRet etPresentEvent)
      )
      forEvery( gnoredT etEvents) { (_, event) =>
        val actual = T etyp eEventAdapter.adaptEvent(event)
        assert(actual. sEmpty)
      }
    }
  }

  test("T etyp eCreateEvent") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = T etyp eEventAdapter.adaptEvent(t etCreateEvent)
        assert(Seq(expectedUUACreate) == actual)
      }
    }
  }

  test("T etyp eReplyEvent") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val t etRepl es: TableFor3[Str ng, T etEvent, Un f edUserAct on] = Table(
          ("act onT etType", "event", "expected"),
          ("Default", t etReplyDefaultEvent, expectedUUAReplyDefault),
          ("Reply", t etReplyToReplyEvent, expectedUUAReplyDefault),
          ("Quote", t etReplyToQuoteEvent, expectedUUAReplyDefault),
        )
        forEvery(t etRepl es) { (_: Str ng, event: T etEvent, expected: Un f edUserAct on) =>
          val actual = T etyp eEventAdapter.adaptEvent(event)
          assert(Seq(expected) === actual)
        }
      }
    }
  }

  test("T etyp eQuoteEvent") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val t etQuotes: TableFor3[Str ng, T etEvent, Un f edUserAct on] = Table(
          ("act onT etType", "event", "expected"),
          ("Default", t etQuoteDefaultEvent, expectedUUAQuoteDefault),
          ("Reply", t etQuoteReplyEvent, expectedUUAQuoteReply),
          ("Quote", t etQuoteQuoteEvent, expectedUUAQuoteQuote),
        )
        forEvery(t etQuotes) { (_: Str ng, event: T etEvent, expected: Un f edUserAct on) =>
          val actual = T etyp eEventAdapter.adaptEvent(event)
          assert(Seq(expected) === actual)
        }
      }
    }
  }

  test("T etyp eRet etEvent") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val t etRet ets: TableFor3[Str ng, T etEvent, Un f edUserAct on] = Table(
          ("act onT etType", "event", "expected"),
          ("Default", t etRet etDefaultEvent, expectedUUARet etDefault),
          ("Reply", t etRet etReplyEvent, expectedUUARet etReply),
          ("Quote", t etRet etQuoteEvent, expectedUUARet etQuote),
          ("Ret et", t etRet etRet etEvent, expectedUUARet etRet et),
        )
        forEvery(t etRet ets) { (_: Str ng, event: T etEvent, expected: Un f edUserAct on) =>
          val actual = T etyp eEventAdapter.adaptEvent(event)
          assert(Seq(expected) === actual)
        }
      }
    }
  }

  test("T etyp eDeleteEvent") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val t etDeletes: TableFor3[Str ng, T etEvent, Un f edUserAct on] = Table(
          ("act onT etType", "event", "expected"),
          ("Default", t etDeleteEvent, expectedUUADeleteDefault),
          ("Reply", t etUnreplyEvent, expectedUUAUnreply),
          ("Quote", t etUnquoteEvent, expectedUUAUnquote),
          ("Ret et", t etUnret etEvent, expectedUUAUnret et),
        )
        forEvery(t etDeletes) { (_: Str ng, event: T etEvent, expected: Un f edUserAct on) =>
          val actual = T etyp eEventAdapter.adaptEvent(event)
          assert(Seq(expected) === actual)
        }
      }
    }
  }

  test("T etyp eEd Event") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val t etEd s: TableFor3[Str ng, T etEvent, Un f edUserAct on] = Table(
          ("act onT etType", "event", "expected"),
          ("RegularT etFromEd ", regularT etFromEd Event, expectedUUARegularT etFromEd ),
          ("QuoteFromEd ", quoteFromEd Event, expectedUUAQuoteFromEd )
        )
        forEvery(t etEd s) { (_: Str ng, event: T etEvent, expected: Un f edUserAct on) =>
          val actual = T etyp eEventAdapter.adaptEvent(event)
          assert(Seq(expected) === actual)
        }
      }
    }
  }

}
