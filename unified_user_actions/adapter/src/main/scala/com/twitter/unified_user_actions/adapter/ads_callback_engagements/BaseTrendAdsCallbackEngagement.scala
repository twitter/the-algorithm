package com.twittew.unified_usew_actions.adaptew.ads_cawwback_engagements

impowt c-com.twittew.ads.spendsewvew.thwiftscawa.spendsewvewevent
i-impowt c-com.twittew.unified_usew_actions.thwiftscawa._

a-abstwact cwass b-basetwendadscawwbackengagement(actiontype: a-actiontype)
    e-extends b-baseadscawwbackengagement(actiontype = actiontype) {

  ovewwide pwotected def getitem(input: s-spendsewvewevent): option[item] = {
    input.engagementevent.fwatmap { e-e =>
      e.impwessiondata.fwatmap { i =>
        i-i.pwomotedtwendid.map { pwomotedtwendid =>
          item.twendinfo(twendinfo(actiontwendid = pwomotedtwendid))
        }
      }
    }
  }
}
