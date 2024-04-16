package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.ut l.T  

class AdapterUt lsSpec extends Test {
  tra  F xture {

    val frozenT  : T   = T  .fromM ll seconds(1658949273000L)
    val languageCode = "en"
    val countryCode = "us"
  }

  test("tests") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = T  .fromM ll seconds(AdapterUt ls.currentT  stampMs)
        assert(frozenT   === actual)
      }

      val act onedT et d = 1554576940756246272L
      assert(AdapterUt ls.getT  stampMsFromT et d(act onedT et d) === 1659474999976L)

      assert(languageCode.toUpperCase === AdapterUt ls.normal zeLanguageCode(languageCode))
      assert(countryCode.toUpperCase === AdapterUt ls.normal zeCountryCode(countryCode))
    }
  }
}
