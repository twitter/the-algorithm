package com.twittew.unified_usew_actions.adaptew.cwient_event

impowt c-com.twittew.cwientapp.thwiftscawa.wogevent
i-impowt com.twittew.cwientapp.thwiftscawa.{item => w-wogeventitem}

o-object nyotificationcwienteventutiws {

  // n-nyotification i-id fow n-nyotification i-in the nyotification tab
  def getnotificationidfownotificationtab(
    ceitem: wogeventitem
  ): o-option[stwing] = {
    fow {
      nyotificationtabdetaiws <- c-ceitem.notificationtabdetaiws
      cwienteventmetadata <- n-nyotificationtabdetaiws.cwienteventmetadata
      nyotificationid <- cwienteventmetadata.upstweamid
    } yiewd {
      n-nyotificationid
    }
  }

  // nyotification i-id fow push nyotification
  d-def getnotificationidfowpushnotification(wogevent: wogevent): option[stwing] = fow {
    pushnotificationdetaiws <- w-wogevent.notificationdetaiws
    nyotificationid <- pushnotificationdetaiws.impwessionid
  } yiewd nyotificationid
}
