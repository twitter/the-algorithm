package com.tw ter.un f ed_user_act ons.adapter

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.adapter.TestF xtures. nteract onEventsF xtures
 mport com.tw ter.un f ed_user_act ons.adapter.uua_aggregates.RekeyUuaFrom nteract onEventsAdapter
 mport com.tw ter.ut l.T  
 mport org.scalatest.prop.TableDr venPropertyC cks

class RekeyUuaFrom nteract onEventsAdapterSpec extends Test w h TableDr venPropertyC cks {
  test("Cl entT etRender mpress ons") {
    new  nteract onEventsF xtures {
      T  .w hT  At(frozenT  ) { _ =>
        assert(
          RekeyUuaFrom nteract onEventsAdapter.adaptEvent(base nteract onEvent) === Seq(
            expectedBaseKeyedUuaT et))
      }
    }
  }

  test("F lter out logged out users") {
    new  nteract onEventsF xtures {
      T  .w hT  At(frozenT  ) { _ =>
        assert(RekeyUuaFrom nteract onEventsAdapter.adaptEvent(loggedOut nteract onEvent) === N l)
      }
    }
  }

  test("F lter out deta l  mpress ons") {
    new  nteract onEventsF xtures {
      T  .w hT  At(frozenT  ) { _ =>
        assert(
          RekeyUuaFrom nteract onEventsAdapter.adaptEvent(deta l mpress on nteract onEvent) === N l)
      }
    }
  }
}
