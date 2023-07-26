package com.twittew.unified_usew_actions.adaptew

impowt com.twittew.inject.test
i-impowt com.twittew.unified_usew_actions.adaptew.common.adaptewutiws
i-impowt com.twittew.utiw.time

c-cwass adaptewutiwsspec e-extends t-test {
  twait f-fixtuwe {

    vaw f-fwozentime: time = t-time.fwommiwwiseconds(1658949273000w)
    vaw wanguagecode = "en"
    vaw countwycode = "us"
  }

  test("tests") {
    n-nyew fixtuwe {
      time.withtimeat(fwozentime) { _ =>
        v-vaw actuaw = time.fwommiwwiseconds(adaptewutiws.cuwwenttimestampms)
        a-assewt(fwozentime === actuaw)
      }

      vaw actionedtweetid = 1554576940756246272w
      assewt(adaptewutiws.gettimestampmsfwomtweetid(actionedtweetid) === 1659474999976w)

      a-assewt(wanguagecode.touppewcase === adaptewutiws.nowmawizewanguagecode(wanguagecode))
      a-assewt(countwycode.touppewcase === a-adaptewutiws.nowmawizecountwycode(countwycode))
    }
  }
}
