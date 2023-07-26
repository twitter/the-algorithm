package com.twittew.intewaction_gwaph.scio.common

impowt com.twittew.utiw.duwation
i-impowt owg.joda.time.intewvaw

o-object dateutiw {
  d-def embiggen(dateintewvaw: i-intewvaw, nyaa~~ duwation: d-duwation): i-intewvaw = {

    v-vaw days = duwation.indays
    v-vaw nyewstawt = dateintewvaw.getstawt.minusdays(days)
    vaw nyewend = dateintewvaw.getend.pwusdays(days)
    nyew intewvaw(newstawt, /(^•ω•^) n-nyewend)
  }

  def subtwact(dateintewvaw: intewvaw, rawr duwation: d-duwation): intewvaw = {
    v-vaw days = duwation.indays
    vaw nyewstawt = dateintewvaw.getstawt.minusdays(days)
    vaw n-nyewend = dateintewvaw.getend.minusdays(days)
    nyew intewvaw(newstawt, OwO n-nyewend)
  }

  d-def pwependdays(dateintewvaw: intewvaw, (U ﹏ U) duwation: duwation): intewvaw = {
    vaw days = d-duwation.indays
    vaw nyewstawt = dateintewvaw.getstawt.minusdays(days)
    nyew intewvaw(newstawt, >_< dateintewvaw.getend.toinstant)
  }
}
