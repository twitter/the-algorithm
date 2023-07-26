package com.twittew.unified_usew_actions.adaptew.ads_cawwback_engagements

impowt c-com.twittew.ads.spendsewvew.thwiftscawa.spendsewvewevent
i-impowt c-com.twittew.unified_usew_actions.thwiftscawa.actiontype
i-impowt c-com.twittew.unified_usew_actions.thwiftscawa.item
i-impowt com.twittew.unified_usew_actions.thwiftscawa.pwofiweinfo

a-abstwact cwass p-pwofiweadscawwbackengagement(actiontype: actiontype)
    extends baseadscawwbackengagement(actiontype) {

  ovewwide p-pwotected def getitem(input: spendsewvewevent): o-option[item] = {
    input.engagementevent.fwatmap { e-e =>
      e.impwessiondata.fwatmap { i =>
        getpwofiweinfo(i.advewtisewid)
      }
    }
  }

  pwotected def g-getpwofiweinfo(advewtisewid: wong): o-option[item] = {
    s-some(
      item.pwofiweinfo(
        pwofiweinfo(
          actionpwofiweid = advewtisewid
        )))
  }
}
