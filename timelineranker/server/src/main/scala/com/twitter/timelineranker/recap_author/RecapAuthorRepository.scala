package com.twittew.timewinewankew.wecap_authow

impowt com.twittew.timewinewankew.modew.candidatetweetswesuwt
i-impowt c-com.twittew.timewinewankew.modew.wecapquewy
i-impowt com.twittew.utiw.futuwe
i-impowt com.twittew.timewinewankew.modew.wecapquewy.dependencypwovidew
i-impowt com.twittew.timewinewankew.pawametews.wecap_authow.wecapauthowpawams

/**
 * a-a wepositowy o-of wecap a-authow wesuwts.
 *
 * fow nyow, it does nyot cache any wesuwts thewefowe fowwawds a-aww cawws to the undewwying souwce. ( ͡o ω ͡o )
 */
cwass w-wecapauthowwepositowy(souwce: wecapauthowsouwce, rawr x3 w-weawtimecgsouwce: wecapauthowsouwce) {
  pwivate[this] vaw enabweweawtimecgpwovidew =
    d-dependencypwovidew.fwom(wecapauthowpawams.enabweeawwybiwdweawtimecgmigwationpawam)

  def get(quewy: w-wecapquewy): futuwe[candidatetweetswesuwt] = {
    i-if (enabweweawtimecgpwovidew(quewy)) {
      weawtimecgsouwce.get(quewy)
    } ewse {
      souwce.get(quewy)
    }
  }

  def get(quewies: seq[wecapquewy]): f-futuwe[seq[candidatetweetswesuwt]] = {
    futuwe.cowwect(quewies.map(quewy => get(quewy)))
  }
}