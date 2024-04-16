package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.adapter.TestF xtures.Ema lNot f cat onEventF xture
 mport com.tw ter.un f ed_user_act ons.adapter.ema l_not f cat on_event.Ema lNot f cat onEventAdapter
 mport com.tw ter.ut l.T  

class Ema lNot f cat onEventAdapterSpec extends Test {

  test("Not f cat ons w h cl ck event") {
    new Ema lNot f cat onEventF xture {
      T  .w hT  At(frozenT  ) { _ =>
        val actual = Ema lNot f cat onEventAdapter.adaptEvent(not f cat onEvent)
        assert(expectedUua == actual. ad)
        assert(Ema lNot f cat onEventAdapter.adaptEvent(not f cat onEventWOT et d). sEmpty)
        assert(Ema lNot f cat onEventAdapter.adaptEvent(not f cat onEventWO mpress on d). sEmpty)
      }
    }
  }
}
