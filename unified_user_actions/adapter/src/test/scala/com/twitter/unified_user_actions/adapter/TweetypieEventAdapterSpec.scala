package com.twittew.unified_usew_actions.adaptew

impowt com.twittew.gizmoduck.thwiftscawa.usew
impowt c-com.twittew.gizmoduck.thwiftscawa.usewtype
i-impowt com.twittew.inject.test
i-impowt com.twittew.snowfwake.id.snowfwakeid
i-impowt c-com.twittew.tweetypie.thwiftscawa.additionawfiewddeweteevent
i-impowt com.twittew.tweetypie.thwiftscawa.additionawfiewdupdateevent
i-impowt com.twittew.tweetypie.thwiftscawa.auditdewetetweet
i-impowt com.twittew.tweetypie.thwiftscawa.devicesouwce
impowt com.twittew.tweetypie.thwiftscawa.editcontwow
impowt com.twittew.tweetypie.thwiftscawa.editcontwowedit
i-impowt com.twittew.tweetypie.thwiftscawa.wanguage
impowt com.twittew.tweetypie.thwiftscawa.pwace
impowt com.twittew.tweetypie.thwiftscawa.pwacetype
i-impowt com.twittew.tweetypie.thwiftscawa.quotedtweet
impowt c-com.twittew.tweetypie.thwiftscawa.quotedtweetdeweteevent
impowt com.twittew.tweetypie.thwiftscawa.quotedtweettakedownevent
impowt c-com.twittew.tweetypie.thwiftscawa.wepwy
impowt c-com.twittew.tweetypie.thwiftscawa.shawe
i-impowt com.twittew.tweetypie.thwiftscawa.tweet
impowt com.twittew.tweetypie.thwiftscawa.tweetcowedata
impowt com.twittew.tweetypie.thwiftscawa.tweetcweateevent
i-impowt com.twittew.tweetypie.thwiftscawa.tweetdeweteevent
impowt com.twittew.tweetypie.thwiftscawa.tweetevent
impowt com.twittew.tweetypie.thwiftscawa.tweeteventdata
i-impowt com.twittew.tweetypie.thwiftscawa.tweeteventfwags
impowt c-com.twittew.tweetypie.thwiftscawa.tweetpossibwysensitiveupdateevent
i-impowt com.twittew.tweetypie.thwiftscawa.tweetscwubgeoevent
i-impowt com.twittew.tweetypie.thwiftscawa.tweettakedownevent
i-impowt com.twittew.tweetypie.thwiftscawa.tweetundeweteevent
impowt c-com.twittew.tweetypie.thwiftscawa.usewscwubgeoevent
impowt com.twittew.unified_usew_actions.adaptew.tweetypie_event.tweetypieeventadaptew
impowt c-com.twittew.unified_usew_actions.thwiftscawa._
impowt com.twittew.utiw.time
impowt owg.scawatest.pwop.tabwedwivenpwopewtychecks
impowt owg.scawatest.pwop.tabwefow1
impowt owg.scawatest.pwop.tabwefow2
i-impowt owg.scawatest.pwop.tabwefow3

c-cwass t-tweetypieeventadaptewspec e-extends test with tabwedwivenpwopewtychecks {
  twait f-fixtuwe {
    v-vaw fwozentime: time = time.fwommiwwiseconds(1658949273000w)

    v-vaw tweetdeweteeventtime: t-time = time.fwommiwwiseconds(1658949253000w)

    v-vaw tweetid = 1554576940856246272w
    vaw timestamp: w-wong = snowfwakeid.unixtimemiwwisfwomid(tweetid)
    vaw usewid = 1w
    vaw usew: usew = u-usew(
      id = usewid, σωσ
      cweatedatmsec = 1000w, rawr x3
      u-updatedatmsec = 1000w, (ˆ ﻌ ˆ)♡
      usewtype = u-usewtype.nowmaw, rawr
    )

    v-vaw actionedtweetid = 1554576940756246333w
    vaw actionedtweettimestamp: wong = snowfwakeid.unixtimemiwwisfwomid(actionedtweetid)
    vaw actionedtweetauthowid = 2w

    vaw actionedbyactionedtweetid = 1554566940756246272w
    v-vaw actionedbyactionedtweettimestamp: w-wong =
      snowfwakeid.unixtimemiwwisfwomid(actionedbyactionedtweetid)
    v-vaw actionedbyactionedtweetauthowid = 3w

    v-vaw tweeteventfwags: t-tweeteventfwags = tweeteventfwags(timestampms = timestamp)
    vaw wanguage: o-option[wanguage] = some(wanguage("en-us", :3 fawse))
    vaw devicesouwce: option[devicesouwce] = s-some(
      devicesouwce(
        i-id = 0, rawr
        p-pawametew = "", (˘ω˘)
        i-intewnawname = "",
        nyame = "name", (ˆ ﻌ ˆ)♡
        u-uww = "uww", mya
        d-dispway = "dispway", (U ᵕ U❁)
        c-cwientappid = o-option(100w)))
    vaw pwace: option[pwace] = s-some(
      pwace(
        i-id = "id",
        `type` = p-pwacetype.city, mya
        f-fuwwname = "san f-fwancisco", ʘwʘ
        nyame = "sf", (˘ω˘)
        countwycode = some("us"), 😳
      ))

    // f-fow tweetdeweteevent
    vaw auditdewetetweet = some(
      auditdewetetweet(
        cwientappwicationid = option(200w)
      ))

    v-vaw tweetcowedata: tweetcowedata =
      tweetcowedata(usewid, òωó t-text = "text", nyaa~~ c-cweatedvia = "cweated_via", o.O c-cweatedatsecs = timestamp)
    v-vaw basetweet: tweet = tweet(
      t-tweetid, nyaa~~
      c-cowedata = some(tweetcowedata), (U ᵕ U❁)
      wanguage = wanguage, 😳😳😳
      devicesouwce = devicesouwce, (U ﹏ U)
      pwace = p-pwace)

    def getcweatetweetcowedata(usewid: w-wong, ^•ﻌ•^ timestamp: wong): tweetcowedata =
      t-tweetcowedata.copy(usewid = u-usewid, (⑅˘꒳˘) cweatedatsecs = timestamp)
    d-def getwetweettweetcowedata(
      u-usewid: wong,
      wetweetedtweetid: w-wong,
      w-wetweetedauthowid: wong, >_<
      pawentstatusid: wong, (⑅˘꒳˘)
      timestamp: w-wong
    ): tweetcowedata = t-tweetcowedata.copy(
      u-usewid = usewid, σωσ
      shawe = s-some(
        s-shawe(
          souwcestatusid = w-wetweetedtweetid, 🥺
          souwceusewid = wetweetedauthowid, :3
          pawentstatusid = pawentstatusid
        )), (ꈍᴗꈍ)
      cweatedatsecs = timestamp
    )
    d-def getwepwytweetcowedata(
      u-usewid: wong,
      wepwiedtweetid: wong, ^•ﻌ•^
      w-wepwiedauthowid: w-wong, (˘ω˘)
      timestamp: wong
    ): tweetcowedata = tweetcowedata.copy(
      u-usewid = usewid, 🥺
      wepwy = some(
        wepwy(
          inwepwytostatusid = some(wepwiedtweetid), (✿oωo)
          i-inwepwytousewid = wepwiedauthowid, XD
        )
      ),
      cweatedatsecs = t-timestamp)
    def g-getquotetweetcowedata(usewid: wong, (///ˬ///✿) timestamp: wong): tweetcowedata =
      tweetcowedata.copy(usewid = usewid, ( ͡o ω ͡o ) c-cweatedatsecs = t-timestamp)

    def gettweet(tweetid: wong, ʘwʘ usewid: wong, rawr timestamp: w-wong): tweet =
      basetweet.copy(id = t-tweetid, o.O cowedata = some(getcweatetweetcowedata(usewid, ^•ﻌ•^ timestamp)))

    def getwetweet(
      t-tweetid: wong, (///ˬ///✿)
      usewid: wong, (ˆ ﻌ ˆ)♡
      t-timestamp: w-wong, XD
      wetweetedtweetid: w-wong, (✿oωo)
      wetweetedusewid: wong,
      pawentstatusid: o-option[wong] = n-nyone
    ): t-tweet =
      basetweet.copy(
        i-id = t-tweetid,
        cowedata = some(
          getwetweettweetcowedata(
            usewid,
            w-wetweetedtweetid,
            w-wetweetedusewid, -.-
            p-pawentstatusid.getowewse(wetweetedtweetid), XD
            timestamp)))

    def g-getquote(
      tweetid: wong, (✿oωo)
      u-usewid: wong, (˘ω˘)
      t-timestamp: wong, (ˆ ﻌ ˆ)♡
      quotedtweetid: wong, >_<
      quotedusewid: w-wong
    ): t-tweet =
      b-basetweet.copy(
        i-id = tweetid, -.-
        c-cowedata = some(getquotetweetcowedata(usewid, (///ˬ///✿) timestamp)), XD
        quotedtweet = some(quotedtweet(quotedtweetid, ^^;; quotedusewid)))

    def getwepwy(
      t-tweetid: wong, rawr x3
      u-usewid: wong, OwO
      wepwiedtweetid: w-wong, ʘwʘ
      wepwiedauthowid: w-wong, rawr
      timestamp: wong
    ): t-tweet =
      b-basetweet.copy(
        i-id = t-tweetid, UwU
        c-cowedata = some(getwepwytweetcowedata(usewid, (ꈍᴗꈍ) wepwiedtweetid, (✿oωo) wepwiedauthowid, (⑅˘꒳˘) timestamp)), OwO
      )

    // ignowed tweet events
    vaw additionawfiewdupdateevent: tweetevent = tweetevent(
      t-tweeteventdata.additionawfiewdupdateevent(additionawfiewdupdateevent(basetweet)), 🥺
      t-tweeteventfwags)
    v-vaw additionawfiewddeweteevent: tweetevent = tweetevent(
      t-tweeteventdata.additionawfiewddeweteevent(
        additionawfiewddeweteevent(map(tweetid -> seq.empty))
      ), >_<
      tweeteventfwags
    )
    v-vaw tweetundeweteevent: t-tweetevent = tweetevent(
      t-tweeteventdata.tweetundeweteevent(tweetundeweteevent(basetweet)), (ꈍᴗꈍ)
      tweeteventfwags
    )
    vaw t-tweetscwubgeoevent: t-tweetevent = tweetevent(
      t-tweeteventdata.tweetscwubgeoevent(tweetscwubgeoevent(tweetid, 😳 u-usewid)),
      tweeteventfwags)
    vaw tweettakedownevent: tweetevent = tweetevent(
      t-tweeteventdata.tweettakedownevent(tweettakedownevent(tweetid, 🥺 u-usewid)), nyaa~~
      t-tweeteventfwags
    )
    v-vaw usewscwubgeoevent: t-tweetevent = tweetevent(
      t-tweeteventdata.usewscwubgeoevent(usewscwubgeoevent(usewid = u-usewid, ^•ﻌ•^ maxtweetid = tweetid)), (ˆ ﻌ ˆ)♡
      t-tweeteventfwags
    )
    v-vaw tweetpossibwysensitiveupdateevent: tweetevent = t-tweetevent(
      tweeteventdata.tweetpossibwysensitiveupdateevent(
        tweetpossibwysensitiveupdateevent(
          t-tweetid = tweetid, (U ᵕ U❁)
          usewid = usewid, mya
          n-nysfwadmin = f-fawse, 😳
          nysfwusew = f-fawse)), σωσ
      tweeteventfwags
    )
    vaw quotedtweetdeweteevent: t-tweetevent = t-tweetevent(
      t-tweeteventdata.quotedtweetdeweteevent(
        quotedtweetdeweteevent(
          quotingtweetid = tweetid, ( ͡o ω ͡o )
          quotingusewid = usewid, XD
          q-quotedtweetid = tweetid,
          quotedusewid = u-usewid)), :3
      t-tweeteventfwags
    )
    vaw q-quotedtweettakedownevent: tweetevent = t-tweetevent(
      t-tweeteventdata.quotedtweettakedownevent(
        quotedtweettakedownevent(
          quotingtweetid = tweetid, :3
          q-quotingusewid = usewid, (⑅˘꒳˘)
          quotedtweetid = t-tweetid, òωó
          q-quotedusewid = usewid, mya
          t-takedowncountwycodes = seq.empty, 😳😳😳
          t-takedownweasons = s-seq.empty
        )
      ),
      t-tweeteventfwags
    )
    vaw wepwyonwytweet =
      getwepwy(tweetid, :3 usewid, >_< actionedtweetid, 🥺 actionedtweetauthowid, (ꈍᴗꈍ) timestamp)
    vaw wepwyandwetweettweet = wepwyonwytweet.copy(cowedata = wepwyonwytweet.cowedata.map(
      _.copy(shawe = some(
        shawe(
          souwcestatusid = actionedtweetid, rawr x3
          souwceusewid = a-actionedtweetauthowid, (U ﹏ U)
          p-pawentstatusid = actionedtweetid
        )))))
    vaw wepwywetweetpwesentevent: t-tweetevent = t-tweetevent(
      t-tweeteventdata.tweetcweateevent(
        tweetcweateevent(
          t-tweet = wepwyandwetweettweet, ( ͡o ω ͡o )
          u-usew = usew, 😳😳😳
          s-souwcetweet =
            some(gettweet(actionedtweetid, 🥺 a-actionedtweetauthowid, òωó actionedtweettimestamp))
        )),
      t-tweeteventfwags
    )

    d-def getexpecteduua(
      usewid: wong, XD
      actiontweetid: w-wong, XD
      a-actiontweetauthowid: wong, ( ͡o ω ͡o )
      s-souwcetimestampms: w-wong,
      a-actiontype: a-actiontype, >w<
      w-wepwyingtweetid: o-option[wong] = n-nyone, mya
      quotingtweetid: o-option[wong] = n-nyone, (ꈍᴗꈍ)
      w-wetweetingtweetid: option[wong] = n-nyone, -.-
      inwepwytotweetid: option[wong] = n-nyone, (⑅˘꒳˘)
      quotedtweetid: option[wong] = n-nyone, (U ﹏ U)
      w-wetweetedtweetid: o-option[wong] = nyone, σωσ
      e-editedtweetid: option[wong] = n-nyone, :3
      appid: option[wong] = n-nyone, /(^•ω•^)
    ): unifiedusewaction = u-unifiedusewaction(
      usewidentifiew = usewidentifiew(usewid = some(usewid)), σωσ
      item = item.tweetinfo(
        t-tweetinfo(
          actiontweetid = a-actiontweetid, (U ᵕ U❁)
          a-actiontweetauthowinfo = some(authowinfo(authowid = some(actiontweetauthowid))), 😳
          wepwyingtweetid = w-wepwyingtweetid, ʘwʘ
          quotingtweetid = q-quotingtweetid, (⑅˘꒳˘)
          w-wetweetingtweetid = w-wetweetingtweetid, ^•ﻌ•^
          inwepwytotweetid = inwepwytotweetid, nyaa~~
          q-quotedtweetid = q-quotedtweetid, XD
          wetweetedtweetid = w-wetweetedtweetid,
          editedtweetid = editedtweetid
        )
      ), /(^•ω•^)
      actiontype = a-actiontype, (U ᵕ U❁)
      eventmetadata = e-eventmetadata(
        s-souwcetimestampms = s-souwcetimestampms, mya
        weceivedtimestampms = f-fwozentime.inmiwwiseconds, (ˆ ﻌ ˆ)♡
        s-souwcewineage = s-souwcewineage.sewvewtweetypieevents, (✿oωo)
        w-wanguage = nyone, (✿oωo)
        c-countwycode = s-some("us"), òωó
        c-cwientappid = a-appid, (˘ω˘)
      )
    )

    /* n-nyote: this is a-a depwecated fiewd {actiontweettype}. (ˆ ﻌ ˆ)♡
     * w-we k-keep this hewe to document the behaviows o-of each unit test.
    /*
     * t-types of tweets on which a-actions can take p-pwace. ( ͡o ω ͡o )
     * n-nyote that wetweets awe nyot incwuded because actions can nyot t-take pwace
     * o-on wetweets. t-they can onwy take pwace on souwce tweets of wetweets, rawr x3
     * which a-awe one of the a-actiontweettypes wisted bewow. (˘ω˘)
     */
    e-enum a-actiontweettype {
    /* is a standawd (non-wetweet, òωó nyon-wepwy, n-nyon-quote) t-tweet */
    defauwt = 0

    /*
     * i-is a tweet i-in a wepwy chain (this incwudes tweets
     * w-without a weading @mention, ( ͡o ω ͡o ) a-as wong as they awe in wepwy
     * t-to some tweet id)
     */
    wepwy = 1

    /* is a wetweet with comment */
    q-quote = 2
    }(pewsisted='twue', σωσ haspewsonawdata='fawse')
     */

    // t-tweet c-cweate
    vaw tweetcweateevent: t-tweetevent = t-tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = g-gettweet(tweetid, (U ﹏ U) u-usewid, rawr timestamp),
          u-usew = usew, -.-
        )
      ), ( ͡o ω ͡o )
      t-tweeteventfwags)
    vaw expecteduuacweate = g-getexpecteduua(
      u-usewid = u-usewid, >_<
      actiontweetid = t-tweetid, o.O
      /* @see comment above fow actiontweettype
      actiontweettype = s-some(actiontweettype.defauwt), σωσ
       */
      a-actiontweetauthowid = u-usewid, -.-
      souwcetimestampms = timestamp, σωσ
      actiontype = actiontype.sewvewtweetcweate, :3
      a-appid = devicesouwce.fwatmap(_.cwientappid)
    )

    // t-tweet wepwy t-to a defauwt
    vaw tweetwepwydefauwtevent: tweetevent = t-tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          t-tweet = getwepwy(tweetid, ^^ u-usewid, a-actionedtweetid, òωó a-actionedtweetauthowid, (ˆ ﻌ ˆ)♡ timestamp), XD
          usew = usew
        )
      ), òωó
      tweeteventfwags
    )
    vaw expecteduuawepwydefauwt = g-getexpecteduua(
      usewid = usewid, (ꈍᴗꈍ)
      a-actiontweetid = actionedtweetid, UwU
      /* @see comment above fow actiontweettype
      a-actiontweettype = nyone, >w<
       */
      actiontweetauthowid = actionedtweetauthowid, ʘwʘ
      souwcetimestampms = t-timestamp, :3
      a-actiontype = actiontype.sewvewtweetwepwy, ^•ﻌ•^
      w-wepwyingtweetid = some(tweetid), (ˆ ﻌ ˆ)♡
      appid = d-devicesouwce.fwatmap(_.cwientappid)
    )
    // t-tweet wepwy to a wepwy
    vaw t-tweetwepwytowepwyevent: tweetevent = t-tweetevent(
      tweeteventdata.tweetcweateevent(
        tweetcweateevent(
          tweet = g-getwepwy(tweetid, 🥺 usewid, OwO actionedtweetid, 🥺 a-actionedtweetauthowid, OwO t-timestamp), (U ᵕ U❁)
          u-usew = usew
        )
      ), ( ͡o ω ͡o )
      tweeteventfwags
    )
    // t-tweet wepwy to a quote
    vaw tweetwepwytoquoteevent: tweetevent = tweetevent(
      tweeteventdata.tweetcweateevent(
        tweetcweateevent(
          t-tweet = g-getwepwy(tweetid, ^•ﻌ•^ u-usewid, actionedtweetid, o.O a-actionedtweetauthowid, (⑅˘꒳˘) timestamp), (ˆ ﻌ ˆ)♡
          usew = u-usew
        )
      ), :3
      t-tweeteventfwags
    )
    // tweet quote a defauwt
    v-vaw tweetquotedefauwtevent: tweetevent = tweetevent(
      t-tweeteventdata.tweetcweateevent(
        tweetcweateevent(
          tweet = getquote(tweetid, /(^•ω•^) u-usewid, òωó timestamp, a-actionedtweetid, :3 actionedtweetauthowid), (˘ω˘)
          u-usew = usew, 😳
          q-quotedtweet =
            s-some(gettweet(actionedtweetid, σωσ actionedtweetauthowid, UwU actionedtweettimestamp))
        )
      ), -.-
      t-tweeteventfwags
    )
    vaw expecteduuaquotedefauwt: unifiedusewaction = g-getexpecteduua(
      usewid = usewid, 🥺
      actiontweetid = actionedtweetid, 😳😳😳
      /* @see c-comment above f-fow actiontweettype
      actiontweettype = s-some(actiontweettype.defauwt), 🥺
       */
      a-actiontweetauthowid = a-actionedtweetauthowid, ^^
      souwcetimestampms = t-timestamp, ^^;;
      actiontype = actiontype.sewvewtweetquote, >w<
      q-quotingtweetid = some(tweetid), σωσ
      a-appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // tweet quote a-a wepwy
    vaw t-tweetquotewepwyevent: tweetevent = t-tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          t-tweet = getquote(tweetid, >w< u-usewid, (⑅˘꒳˘) timestamp, a-actionedtweetid, òωó actionedtweetauthowid), (⑅˘꒳˘)
          u-usew = usew, (ꈍᴗꈍ)
          quotedtweet = some(
            g-getwepwy(
              tweetid = a-actionedtweetid, rawr x3
              usewid = actionedtweetauthowid, ( ͡o ω ͡o )
              wepwiedtweetid = a-actionedbyactionedtweetid, UwU
              w-wepwiedauthowid = actionedbyactionedtweetauthowid,
              t-timestamp = actionedtweettimestamp
            ))
        )
      ), ^^
      t-tweeteventfwags
    )
    v-vaw expecteduuaquotewepwy: unifiedusewaction = g-getexpecteduua(
      usewid = u-usewid, (˘ω˘)
      actiontweetid = actionedtweetid, (ˆ ﻌ ˆ)♡
      /* @see comment a-above fow a-actiontweettype
      actiontweettype = some(actiontweettype.wepwy), OwO
       */
      actiontweetauthowid = actionedtweetauthowid,
      s-souwcetimestampms = t-timestamp, 😳
      actiontype = actiontype.sewvewtweetquote, UwU
      quotingtweetid = s-some(tweetid), 🥺
      appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // t-tweet q-quote a quote
    vaw tweetquotequoteevent: tweetevent = tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = getquote(tweetid, 😳😳😳 usewid, t-timestamp, ʘwʘ actionedtweetid, /(^•ω•^) actionedtweetauthowid), :3
          u-usew = usew, :3
          q-quotedtweet = some(
            g-getquote(
              t-tweetid = actionedtweetid,
              u-usewid = a-actionedtweetauthowid, mya
              t-timestamp = a-actionedtweettimestamp, (///ˬ///✿)
              quotedtweetid = actionedbyactionedtweetid, (⑅˘꒳˘)
              quotedusewid = actionedbyactionedtweetauthowid, :3
            ))
        )
      ), /(^•ω•^)
      tweeteventfwags
    )
    v-vaw expecteduuaquotequote: u-unifiedusewaction = g-getexpecteduua(
      u-usewid = u-usewid, ^^;;
      a-actiontweetid = actionedtweetid, (U ᵕ U❁)
      /* @see comment above fow actiontweettype
      actiontweettype = s-some(actiontweettype.quote), (U ﹏ U)
       */
      a-actiontweetauthowid = actionedtweetauthowid,
      souwcetimestampms = timestamp, mya
      actiontype = a-actiontype.sewvewtweetquote, ^•ﻌ•^
      quotingtweetid = s-some(tweetid), (U ﹏ U)
      a-appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // tweet wetweet a defauwt
    v-vaw tweetwetweetdefauwtevent: tweetevent = tweetevent(
      t-tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = getwetweet(tweetid, u-usewid, :3 timestamp, rawr x3 actionedtweetid, 😳😳😳 a-actionedtweetauthowid),
          u-usew = usew, >w<
          s-souwcetweet =
            some(gettweet(actionedtweetid, òωó a-actionedtweetauthowid, 😳 a-actionedtweettimestamp))
        )
      ), (✿oωo)
      t-tweeteventfwags
    )
    v-vaw expecteduuawetweetdefauwt: u-unifiedusewaction = getexpecteduua(
      u-usewid = u-usewid, OwO
      actiontweetid = a-actionedtweetid, (U ﹏ U)
      /* @see comment above fow actiontweettype
      a-actiontweettype = some(actiontweettype.defauwt), (ꈍᴗꈍ)
       */
      a-actiontweetauthowid = actionedtweetauthowid, rawr
      souwcetimestampms = t-timestamp, ^^
      a-actiontype = actiontype.sewvewtweetwetweet, rawr
      wetweetingtweetid = some(tweetid), nyaa~~
      a-appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // tweet wetweet a-a wepwy
    vaw t-tweetwetweetwepwyevent: tweetevent = tweetevent(
      t-tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = getwetweet(tweetid, nyaa~~ u-usewid, o.O timestamp, òωó actionedtweetid, ^^;; actionedtweetauthowid), rawr
          usew = usew, ^•ﻌ•^
          s-souwcetweet = s-some(
            getwepwy(
              a-actionedtweetid, nyaa~~
              actionedtweetauthowid,
              a-actionedbyactionedtweetid, nyaa~~
              actionedbyactionedtweetauthowid, 😳😳😳
              actionedtweettimestamp))
        )
      ), 😳😳😳
      t-tweeteventfwags
    )
    v-vaw expecteduuawetweetwepwy: u-unifiedusewaction = g-getexpecteduua(
      usewid = usewid, σωσ
      actiontweetid = actionedtweetid, o.O
      /* @see comment above fow actiontweettype
      actiontweettype = some(actiontweettype.wepwy),
       */
      a-actiontweetauthowid = a-actionedtweetauthowid, σωσ
      souwcetimestampms = t-timestamp, nyaa~~
      a-actiontype = a-actiontype.sewvewtweetwetweet, rawr x3
      w-wetweetingtweetid = some(tweetid), (///ˬ///✿)
      appid = d-devicesouwce.fwatmap(_.cwientappid)
    )
    // t-tweet wetweet a quote
    v-vaw tweetwetweetquoteevent: t-tweetevent = tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = getwetweet(tweetid, o.O usewid, t-timestamp, òωó actionedtweetid, OwO a-actionedtweetauthowid), σωσ
          u-usew = usew, nyaa~~
          souwcetweet = s-some(
            g-getquote(
              a-actionedtweetid, OwO
              actionedtweetauthowid, ^^
              a-actionedtweettimestamp, (///ˬ///✿)
              a-actionedbyactionedtweetid, σωσ
              actionedbyactionedtweetauthowid
            ))
        )
      ), rawr x3
      t-tweeteventfwags
    )
    vaw expecteduuawetweetquote: u-unifiedusewaction = g-getexpecteduua(
      usewid = u-usewid, (ˆ ﻌ ˆ)♡
      actiontweetid = a-actionedtweetid, 🥺
      /* @see comment above fow actiontweettype
      a-actiontweettype = some(actiontweettype.quote), (⑅˘꒳˘)
       */
      actiontweetauthowid = actionedtweetauthowid, 😳😳😳
      souwcetimestampms = timestamp, /(^•ω•^)
      actiontype = actiontype.sewvewtweetwetweet, >w<
      wetweetingtweetid = s-some(tweetid), ^•ﻌ•^
      appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // tweet wetweet a wetweet
    vaw tweetwetweetwetweetevent: tweetevent = tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = getwetweet(
            tweetid, 😳😳😳
            u-usewid, :3
            timestamp, (ꈍᴗꈍ)
            actionedbyactionedtweetid, ^•ﻌ•^
            a-actionedbyactionedtweetauthowid, >w<
            some(actionedtweetid)),
          usew = u-usew, ^^;;
          souwcetweet = s-some(
            gettweet(
              a-actionedbyactionedtweetid, (✿oωo)
              a-actionedbyactionedtweetauthowid, òωó
              actionedbyactionedtweettimestamp, ^^
            ))
        )
      ), ^^
      tweeteventfwags
    )
    v-vaw expecteduuawetweetwetweet: unifiedusewaction = getexpecteduua(
      usewid = usewid, rawr
      a-actiontweetid = actionedbyactionedtweetid, XD
      /* @see c-comment above fow actiontweettype
      a-actiontweettype = some(actiontweettype.defauwt), rawr
       */
      a-actiontweetauthowid = actionedbyactionedtweetauthowid,
      s-souwcetimestampms = timestamp, 😳
      actiontype = a-actiontype.sewvewtweetwetweet, 🥺
      wetweetingtweetid = some(tweetid), (U ᵕ U❁)
      a-appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // dewete a tweet
    vaw tweetdeweteevent: tweetevent = tweetevent(
      t-tweeteventdata.tweetdeweteevent(
        t-tweetdeweteevent(
          tweet = g-gettweet(tweetid, 😳 u-usewid, timestamp), 🥺
          usew = some(usew), (///ˬ///✿)
          a-audit = auditdewetetweet
        )
      ), mya
      tweeteventfwags.copy(timestampms = tweetdeweteeventtime.inmiwwiseconds)
    )
    vaw expecteduuadewetedefauwt: unifiedusewaction = g-getexpecteduua(
      u-usewid = usew.id, (✿oωo)
      a-actiontweetid = t-tweetid, ^•ﻌ•^
      actiontweetauthowid = u-usewid, o.O
      souwcetimestampms = tweetdeweteeventtime.inmiwwiseconds, o.O
      a-actiontype = actiontype.sewvewtweetdewete, XD
      appid = a-auditdewetetweet.fwatmap(_.cwientappwicationid)
    )
    // d-dewete a wepwy - unwepwy
    vaw tweetunwepwyevent: t-tweetevent = tweetevent(
      tweeteventdata.tweetdeweteevent(
        tweetdeweteevent(
          tweet = getwepwy(tweetid, ^•ﻌ•^ usewid, actionedtweetid, ʘwʘ actionedtweetauthowid, (U ﹏ U) timestamp),
          usew = some(usew), 😳😳😳
          audit = auditdewetetweet
        )
      ),
      t-tweeteventfwags.copy(timestampms = t-tweetdeweteeventtime.inmiwwiseconds)
    )
    vaw expecteduuaunwepwy: u-unifiedusewaction = g-getexpecteduua(
      usewid = u-usew.id, 🥺
      actiontweetid = actionedtweetid, (///ˬ///✿)
      actiontweetauthowid = actionedtweetauthowid,
      souwcetimestampms = t-tweetdeweteeventtime.inmiwwiseconds, (˘ω˘)
      actiontype = actiontype.sewvewtweetunwepwy, :3
      wepwyingtweetid = some(tweetid), /(^•ω•^)
      a-appid = auditdewetetweet.fwatmap(_.cwientappwicationid)
    )
    // d-dewete a q-quote - unquote
    vaw tweetunquoteevent: tweetevent = tweetevent(
      t-tweeteventdata.tweetdeweteevent(
        t-tweetdeweteevent(
          tweet = g-getquote(tweetid, :3 usewid, t-timestamp, mya actionedtweetid, XD actionedtweetauthowid), (///ˬ///✿)
          usew = s-some(usew),
          audit = a-auditdewetetweet
        )
      ), 🥺
      tweeteventfwags.copy(timestampms = t-tweetdeweteeventtime.inmiwwiseconds)
    )
    vaw expecteduuaunquote: unifiedusewaction = g-getexpecteduua(
      usewid = usew.id, o.O
      a-actiontweetid = a-actionedtweetid, mya
      actiontweetauthowid = a-actionedtweetauthowid, rawr x3
      s-souwcetimestampms = tweetdeweteeventtime.inmiwwiseconds, 😳
      a-actiontype = actiontype.sewvewtweetunquote, 😳😳😳
      q-quotingtweetid = some(tweetid), >_<
      a-appid = a-auditdewetetweet.fwatmap(_.cwientappwicationid)
    )
    // dewete a wetweet / unwetweet
    v-vaw tweetunwetweetevent: tweetevent = tweetevent(
      tweeteventdata.tweetdeweteevent(
        tweetdeweteevent(
          tweet = getwetweet(
            tweetid, >w<
            u-usewid, rawr x3
            timestamp, XD
            actionedtweetid, ^^
            a-actionedtweetauthowid, (✿oωo)
            some(actionedtweetid)),
          u-usew = some(usew), >w<
          audit = auditdewetetweet
        )
      ), 😳😳😳
      tweeteventfwags.copy(timestampms = t-tweetdeweteeventtime.inmiwwiseconds)
    )
    vaw expecteduuaunwetweet: unifiedusewaction = getexpecteduua(
      u-usewid = usew.id, (ꈍᴗꈍ)
      actiontweetid = actionedtweetid, (✿oωo)
      a-actiontweetauthowid = actionedtweetauthowid, (˘ω˘)
      souwcetimestampms = t-tweetdeweteeventtime.inmiwwiseconds, nyaa~~
      actiontype = actiontype.sewvewtweetunwetweet, ( ͡o ω ͡o )
      w-wetweetingtweetid = s-some(tweetid), 🥺
      appid = auditdewetetweet.fwatmap(_.cwientappwicationid)
    )
    // edit a tweet, (U ﹏ U) t-the nyew tweet f-fwom edit is a defauwt tweet (not w-wepwy/quote/wetweet)
    v-vaw weguwawtweetfwomeditevent: tweetevent = tweetevent(
      tweeteventdata.tweetcweateevent(
        t-tweetcweateevent(
          tweet = gettweet(
            tweetid, ( ͡o ω ͡o )
            usewid,
            t-timestamp
          ).copy(editcontwow =
            some(editcontwow.edit(editcontwowedit(initiawtweetid = actionedtweetid)))), (///ˬ///✿)
          usew = usew, (///ˬ///✿)
        )
      ), (✿oωo)
      tweeteventfwags
    )
    v-vaw expecteduuaweguwawtweetfwomedit: u-unifiedusewaction = g-getexpecteduua(
      usewid = usew.id,
      actiontweetid = tweetid, (U ᵕ U❁)
      a-actiontweetauthowid = usewid, ʘwʘ
      souwcetimestampms = t-tweeteventfwags.timestampms, ʘwʘ
      actiontype = a-actiontype.sewvewtweetedit, XD
      e-editedtweetid = some(actionedtweetid), (✿oωo)
      appid = devicesouwce.fwatmap(_.cwientappid)
    )
    // edit a tweet, ^•ﻌ•^ the nyew tweet fwom edit i-is a quote
    v-vaw quotefwomeditevent: tweetevent = tweetevent(
      t-tweeteventdata.tweetcweateevent(
        tweetcweateevent(
          tweet = g-getquote(
            t-tweetid,
            u-usewid, ^•ﻌ•^
            t-timestamp, >_<
            a-actionedtweetid, mya
            a-actionedtweetauthowid
          ).copy(editcontwow =
            some(editcontwow.edit(editcontwowedit(initiawtweetid = actionedbyactionedtweetid)))), σωσ
          u-usew = u-usew, rawr
        )
      ), (✿oωo)
      tweeteventfwags
    )
    v-vaw expecteduuaquotefwomedit: u-unifiedusewaction = g-getexpecteduua(
      u-usewid = usew.id, :3
      actiontweetid = t-tweetid, rawr x3
      a-actiontweetauthowid = u-usewid, ^^
      souwcetimestampms = tweeteventfwags.timestampms, ^^
      a-actiontype = actiontype.sewvewtweetedit, OwO
      editedtweetid = s-some(actionedbyactionedtweetid), ʘwʘ
      quotedtweetid = some(actionedtweetid), /(^•ω•^)
      a-appid = devicesouwce.fwatmap(_.cwientappid)
    )
  }

  test("ignowe n-nyon-tweetcweate / nyon-tweetdewete events") {
    nyew fixtuwe {
      vaw ignowedtweetevents: t-tabwefow1[tweetevent] = t-tabwe(
        "ignowedtweetevents", ʘwʘ
        additionawfiewdupdateevent, (⑅˘꒳˘)
        a-additionawfiewddeweteevent, UwU
        t-tweetundeweteevent, -.-
        tweetscwubgeoevent, :3
        tweettakedownevent, >_<
        usewscwubgeoevent, nyaa~~
        t-tweetpossibwysensitiveupdateevent, ( ͡o ω ͡o )
        q-quotedtweetdeweteevent, o.O
        quotedtweettakedownevent
      )
      fowevewy(ignowedtweetevents) { t-tweetevent: t-tweetevent =>
        vaw actuaw = tweetypieeventadaptew.adaptevent(tweetevent)
        a-assewt(actuaw.isempty)
      }
    }
  }

  test("ignowe invawid tweetcweate events") {
    new fixtuwe {
      vaw i-ignowedtweetevents: tabwefow2[stwing, :3 tweetevent] = t-tabwe(
        ("invawidtype", (˘ω˘) "event"), rawr x3
        ("wepwyandwetweetbothpwesent", (U ᵕ U❁) w-wepwywetweetpwesentevent)
      )
      f-fowevewy(ignowedtweetevents) { (_, 🥺 event) =>
        v-vaw actuaw = tweetypieeventadaptew.adaptevent(event)
        assewt(actuaw.isempty)
      }
    }
  }

  t-test("tweetypiecweateevent") {
    n-nyew f-fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        vaw actuaw = tweetypieeventadaptew.adaptevent(tweetcweateevent)
        a-assewt(seq(expecteduuacweate) == a-actuaw)
      }
    }
  }

  t-test("tweetypiewepwyevent") {
    nyew fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        v-vaw tweetwepwies: t-tabwefow3[stwing, >_< tweetevent, :3 u-unifiedusewaction] = t-tabwe(
          ("actiontweettype", :3 "event", "expected"), (ꈍᴗꈍ)
          ("defauwt", σωσ t-tweetwepwydefauwtevent, 😳 e-expecteduuawepwydefauwt), mya
          ("wepwy", (///ˬ///✿) t-tweetwepwytowepwyevent, ^^ expecteduuawepwydefauwt), (✿oωo)
          ("quote", t-tweetwepwytoquoteevent, ( ͡o ω ͡o ) expecteduuawepwydefauwt), ^^;;
        )
        f-fowevewy(tweetwepwies) { (_: s-stwing, :3 event: tweetevent, 😳 expected: unifiedusewaction) =>
          vaw a-actuaw = tweetypieeventadaptew.adaptevent(event)
          a-assewt(seq(expected) === actuaw)
        }
      }
    }
  }

  t-test("tweetypiequoteevent") {
    nyew f-fixtuwe {
      time.withtimeat(fwozentime) { _ =>
        vaw tweetquotes: t-tabwefow3[stwing, XD t-tweetevent, (///ˬ///✿) unifiedusewaction] = t-tabwe(
          ("actiontweettype", o.O "event", "expected"), o.O
          ("defauwt", t-tweetquotedefauwtevent, XD e-expecteduuaquotedefauwt), ^^;;
          ("wepwy", 😳😳😳 t-tweetquotewepwyevent, (U ᵕ U❁) expecteduuaquotewepwy), /(^•ω•^)
          ("quote", 😳😳😳 tweetquotequoteevent, rawr x3 e-expecteduuaquotequote), ʘwʘ
        )
        fowevewy(tweetquotes) { (_: stwing, UwU event: tweetevent, (⑅˘꒳˘) expected: unifiedusewaction) =>
          v-vaw a-actuaw = tweetypieeventadaptew.adaptevent(event)
          assewt(seq(expected) === actuaw)
        }
      }
    }
  }

  test("tweetypiewetweetevent") {
    n-nyew fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        vaw tweetwetweets: tabwefow3[stwing, ^^ t-tweetevent, unifiedusewaction] = t-tabwe(
          ("actiontweettype", 😳😳😳 "event", òωó "expected"), ^^;;
          ("defauwt", (✿oωo) t-tweetwetweetdefauwtevent, rawr e-expecteduuawetweetdefauwt), XD
          ("wepwy", 😳 tweetwetweetwepwyevent, (U ᵕ U❁) expecteduuawetweetwepwy), UwU
          ("quote", OwO tweetwetweetquoteevent, 😳 expecteduuawetweetquote), (˘ω˘)
          ("wetweet", òωó tweetwetweetwetweetevent, OwO e-expecteduuawetweetwetweet), (✿oωo)
        )
        fowevewy(tweetwetweets) { (_: s-stwing, (⑅˘꒳˘) event: tweetevent, /(^•ω•^) e-expected: unifiedusewaction) =>
          vaw actuaw = tweetypieeventadaptew.adaptevent(event)
          a-assewt(seq(expected) === actuaw)
        }
      }
    }
  }

  t-test("tweetypiedeweteevent") {
    nyew fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        vaw t-tweetdewetes: tabwefow3[stwing, 🥺 tweetevent, unifiedusewaction] = tabwe(
          ("actiontweettype", -.- "event", ( ͡o ω ͡o ) "expected"),
          ("defauwt", 😳😳😳 tweetdeweteevent, (˘ω˘) expecteduuadewetedefauwt), ^^
          ("wepwy", σωσ tweetunwepwyevent, 🥺 expecteduuaunwepwy), 🥺
          ("quote", /(^•ω•^) tweetunquoteevent, (⑅˘꒳˘) e-expecteduuaunquote), -.-
          ("wetweet", 😳 t-tweetunwetweetevent, 😳😳😳 e-expecteduuaunwetweet), >w<
        )
        f-fowevewy(tweetdewetes) { (_: stwing, UwU event: tweetevent, /(^•ω•^) e-expected: unifiedusewaction) =>
          vaw actuaw = tweetypieeventadaptew.adaptevent(event)
          assewt(seq(expected) === a-actuaw)
        }
      }
    }
  }

  t-test("tweetypieeditevent") {
    n-nyew f-fixtuwe {
      time.withtimeat(fwozentime) { _ =>
        vaw tweetedits: tabwefow3[stwing, 🥺 tweetevent, unifiedusewaction] = t-tabwe(
          ("actiontweettype", >_< "event", rawr "expected"), (ꈍᴗꈍ)
          ("weguwawtweetfwomedit", -.- w-weguwawtweetfwomeditevent, ( ͡o ω ͡o ) expecteduuaweguwawtweetfwomedit), (⑅˘꒳˘)
          ("quotefwomedit", mya quotefwomeditevent, rawr x3 expecteduuaquotefwomedit)
        )
        f-fowevewy(tweetedits) { (_: stwing, (ꈍᴗꈍ) event: t-tweetevent, ʘwʘ expected: u-unifiedusewaction) =>
          v-vaw actuaw = tweetypieeventadaptew.adaptevent(event)
          assewt(seq(expected) === actuaw)
        }
      }
    }
  }

}
