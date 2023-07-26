package com.twittew.unified_usew_actions.adaptew

impowt com.twittew.context.thwiftscawa.viewew
impowt c-com.twittew.inject.test
i-impowt c-com.twittew.timewinesewvice.thwiftscawa._
impowt c-com.twittew.unified_usew_actions.adaptew.tws_favs_event.twsfavsadaptew
i-impowt c-com.twittew.unified_usew_actions.thwiftscawa._
i-impowt com.twittew.utiw.time

c-cwass twsfavsadaptewspec extends test {
  twait fixtuwe {

    vaw fwozentime = t-time.fwommiwwiseconds(1658949273000w)

    vaw faveventnowetweet = c-contextuawizedfavowiteevent(
      event = favowiteeventunion.favowite(
        f-favowiteevent(
          usewid = 91w, 🥺
          tweetid = 1w, >_<
          tweetusewid = 101w, ʘwʘ
          e-eventtimems = 1001w
        )
      ), (˘ω˘)
      context = w-wogeventcontext(hostname = "", (✿oωo) t-twaceid = 31w)
    )
    vaw faveventwetweet = contextuawizedfavowiteevent(
      event = favowiteeventunion.favowite(
        favowiteevent(
          u-usewid = 92w, (///ˬ///✿)
          tweetid = 2w, rawr x3
          tweetusewid = 102w, -.-
          eventtimems = 1002w, ^^
          wetweetid = s-some(22w)
        )
      ), (⑅˘꒳˘)
      context = wogeventcontext(hostname = "", nyaa~~ t-twaceid = 32w)
    )
    v-vaw unfaveventnowetweet = c-contextuawizedfavowiteevent(
      e-event = favowiteeventunion.unfavowite(
        unfavowiteevent(
          usewid = 93w, /(^•ω•^)
          t-tweetid = 3w, (U ﹏ U)
          tweetusewid = 103w, 😳😳😳
          eventtimems = 1003w
        )
      ), >w<
      c-context = wogeventcontext(hostname = "", XD twaceid = 33w)
    )
    vaw unfaveventwetweet = contextuawizedfavowiteevent(
      event = favowiteeventunion.unfavowite(
        u-unfavowiteevent(
          usewid = 94w, o.O
          t-tweetid = 4w, mya
          t-tweetusewid = 104w, 🥺
          e-eventtimems = 1004w, ^^;;
          wetweetid = some(44w)
        )
      ), :3
      context = w-wogeventcontext(hostname = "", (U ﹏ U) t-twaceid = 34w)
    )
    vaw f-faveventwithwangandcountwy = contextuawizedfavowiteevent(
      e-event = favowiteeventunion.favowite(
        favowiteevent(
          usewid = 91w, OwO
          t-tweetid = 1w, 😳😳😳
          tweetusewid = 101w, (ˆ ﻌ ˆ)♡
          e-eventtimems = 1001w, XD
          viewewcontext =
            some(viewew(wequestcountwycode = s-some("us"), wequestwanguagecode = some("en")))
        )
      ), (ˆ ﻌ ˆ)♡
      c-context = wogeventcontext(hostname = "", ( ͡o ω ͡o ) t-twaceid = 31w)
    )

    v-vaw expecteduua1 = unifiedusewaction(
      usewidentifiew = usewidentifiew(usewid = some(91w)), rawr x3
      item = item.tweetinfo(
        t-tweetinfo(
          a-actiontweetid = 1w, nyaa~~
          actiontweetauthowinfo = s-some(authowinfo(authowid = s-some(101w))), >_<
        )
      ), ^^;;
      a-actiontype = actiontype.sewvewtweetfav, (ˆ ﻌ ˆ)♡
      eventmetadata = eventmetadata(
        souwcetimestampms = 1001w, ^^;;
        w-weceivedtimestampms = fwozentime.inmiwwiseconds, (⑅˘꒳˘)
        souwcewineage = souwcewineage.sewvewtwsfavs, rawr x3
        twaceid = s-some(31w)
      )
    )
    vaw e-expecteduua2 = unifiedusewaction(
      u-usewidentifiew = u-usewidentifiew(usewid = some(92w)), (///ˬ///✿)
      i-item = item.tweetinfo(
        t-tweetinfo(
          a-actiontweetid = 2w, 🥺
          a-actiontweetauthowinfo = some(authowinfo(authowid = some(102w))), >_<
          w-wetweetingtweetid = s-some(22w)
        )
      ), UwU
      a-actiontype = a-actiontype.sewvewtweetfav, >_<
      e-eventmetadata = eventmetadata(
        souwcetimestampms = 1002w, -.-
        weceivedtimestampms = fwozentime.inmiwwiseconds, mya
        s-souwcewineage = souwcewineage.sewvewtwsfavs, >w<
        twaceid = some(32w)
      )
    )
    vaw expecteduua3 = unifiedusewaction(
      usewidentifiew = u-usewidentifiew(usewid = some(93w)), (U ﹏ U)
      item = item.tweetinfo(
        t-tweetinfo(
          a-actiontweetid = 3w, 😳😳😳
          a-actiontweetauthowinfo = some(authowinfo(authowid = some(103w))), o.O
        )
      ), òωó
      a-actiontype = actiontype.sewvewtweetunfav, 😳😳😳
      e-eventmetadata = e-eventmetadata(
        souwcetimestampms = 1003w, σωσ
        weceivedtimestampms = fwozentime.inmiwwiseconds, (⑅˘꒳˘)
        souwcewineage = souwcewineage.sewvewtwsfavs, (///ˬ///✿)
        t-twaceid = some(33w)
      )
    )
    v-vaw expecteduua4 = unifiedusewaction(
      u-usewidentifiew = u-usewidentifiew(usewid = some(94w)), 🥺
      item = i-item.tweetinfo(
        t-tweetinfo(
          actiontweetid = 4w, OwO
          actiontweetauthowinfo = s-some(authowinfo(authowid = s-some(104w))), >w<
          wetweetingtweetid = some(44w)
        )
      ), 🥺
      actiontype = actiontype.sewvewtweetunfav, nyaa~~
      eventmetadata = eventmetadata(
        souwcetimestampms = 1004w, ^^
        w-weceivedtimestampms = fwozentime.inmiwwiseconds, >w<
        s-souwcewineage = s-souwcewineage.sewvewtwsfavs, OwO
        twaceid = s-some(34w)
      )
    )
    v-vaw expecteduua5 = u-unifiedusewaction(
      usewidentifiew = usewidentifiew(usewid = some(91w)), XD
      item = item.tweetinfo(
        t-tweetinfo(
          a-actiontweetid = 1w, ^^;;
          actiontweetauthowinfo = some(authowinfo(authowid = s-some(101w))), 🥺
        )
      ), XD
      a-actiontype = actiontype.sewvewtweetfav, (U ᵕ U❁)
      eventmetadata = eventmetadata(
        souwcetimestampms = 1001w, :3
        w-weceivedtimestampms = fwozentime.inmiwwiseconds, ( ͡o ω ͡o )
        souwcewineage = souwcewineage.sewvewtwsfavs, òωó
        wanguage = s-some("en"), σωσ
        countwycode = some("us"), (U ᵕ U❁)
        t-twaceid = s-some(31w)
      )
    )
  }

  test("fav event with nyo wetweet") {
    nyew fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        v-vaw actuaw = twsfavsadaptew.adaptevent(faveventnowetweet)
        assewt(seq(expecteduua1) === actuaw)
      }
    }
  }

  t-test("fav event with a wetweet") {
    n-nyew fixtuwe {
      time.withtimeat(fwozentime) { _ =>
        vaw a-actuaw = twsfavsadaptew.adaptevent(faveventwetweet)
        assewt(seq(expecteduua2) === a-actuaw)
      }
    }
  }

  t-test("unfav event with nyo w-wetweet") {
    nyew fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        v-vaw actuaw = twsfavsadaptew.adaptevent(unfaveventnowetweet)
        a-assewt(seq(expecteduua3) === actuaw)
      }
    }
  }

  test("unfav e-event w-with a wetweet") {
    nyew fixtuwe {
      time.withtimeat(fwozentime) { _ =>
        v-vaw actuaw = t-twsfavsadaptew.adaptevent(unfaveventwetweet)
        a-assewt(seq(expecteduua4) === actuaw)
      }
    }
  }

  test("fav event w-with wanguage and countwy") {
    n-nyew fixtuwe {
      t-time.withtimeat(fwozentime) { _ =>
        vaw actuaw = twsfavsadaptew.adaptevent(faveventwithwangandcountwy)
        assewt(seq(expecteduua5) === a-actuaw)
      }
    }
  }
}
