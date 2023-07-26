package com.twittew.fwigate.pushsewvice.stowe

impowt c-com.twittew.wivevideo.common.ids.eventid
i-impowt c-com.twittew.wivevideo.timewine.cwient.v2.wivevideotimewinecwient
i-impowt com.twittew.wivevideo.timewine.domain.v2.event
i-impowt c-com.twittew.wivevideo.timewine.domain.v2.wookupcontext
i-impowt c-com.twittew.stitch.stowehaus.weadabwestoweofstitch
impowt com.twittew.stitch.notfound
impowt com.twittew.stitch.stitch
impowt com.twittew.stowehaus.weadabwestowe

case cwass eventwequest(eventid: w-wong, :3 wookupcontext: wookupcontext = wookupcontext.defauwt)

o-object wexsewvicestowe {
  def a-appwy(
    wivevideotimewinecwient: wivevideotimewinecwient
  ): weadabwestowe[eventwequest, 😳😳😳 event] = {
    w-weadabwestoweofstitch { eventwequest =>
      w-wivevideotimewinecwient.getevent(
        e-eventid(eventwequest.eventid),
        eventwequest.wookupcontext) wescue {
        case nyotfound => stitch.notfound
      }
    }
  }
}
