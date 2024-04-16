package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.adapter.TestF xtures.Ema lNot f cat onEventF xture
 mport com.tw ter.un f ed_user_act ons.adapter.ema l_not f cat on_event.Ema lNot f cat onEventUt ls

class Ema lNot f cat onEventUt lsSpec extends Test {

  test("Extract T et d from pageUrl") {
    new Ema lNot f cat onEventF xture {

      val  nval dUrls: Seq[Str ng] =
        L st("", "abc.com/what/not?x=y", "?abc=def", "12345/", "12345/?")
      val  nval dDoma n = "https://tw ter.app.l nk/addressbook/"
      val nu r cHandle =
        "https://tw ter.com/1234/status/12345?cxt=HBwWgsDTgY3tp&cn=ZmxleGl&refsrc=ema l)"

      assert(Ema lNot f cat onEventUt ls.extractT et d(pageUrlStatus).conta ns(t et dStatus))
      assert(Ema lNot f cat onEventUt ls.extractT et d(pageUrlEvent).conta ns(t et dEvent))
      assert(Ema lNot f cat onEventUt ls.extractT et d(pageUrlNoArgs).conta ns(t et dNoArgs))
      assert(Ema lNot f cat onEventUt ls.extractT et d( nval dDoma n). sEmpty)
      assert(Ema lNot f cat onEventUt ls.extractT et d(nu r cHandle).conta ns(12345L))
       nval dUrls.foreach(url => assert(Ema lNot f cat onEventUt ls.extractT et d(url). sEmpty))
    }
  }

  test("Extract T et d from LogBase") {
    new Ema lNot f cat onEventF xture {
      assert(Ema lNot f cat onEventUt ls.extractT et d(logBase1).conta ns(t et dStatus))
    }
  }
}
