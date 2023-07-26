package com.twittew.timewinewankew.in_netwowk_tweets

impowt com.twittew.timewinewankew.modew.candidatetweetswesuwt
i-impowt com.twittew.timewinewankew.modew.wecapquewy
i-impowt com.twittew.timewinewankew.modew.wecapquewy.dependencypwovidew
i-impowt c-com.twittew.timewinewankew.pawametews.in_netwowk_tweets.innetwowktweetpawams
i-impowt com.twittew.utiw.futuwe

/**
 * a-a wepositowy o-of in-netwowk t-tweet candidates. rawr x3
 * fow nyow, nyaa~~ it does nyot cache any wesuwts thewefowe fowwawds a-aww cawws to the undewwying souwce. /(^•ω•^)
 */
cwass i-innetwowktweetwepositowy(
  souwce: i-innetwowktweetsouwce, rawr
  weawtimecgsouwce: innetwowktweetsouwce) {

  pwivate[this] vaw enabweweawtimecgpwovidew =
    d-dependencypwovidew.fwom(innetwowktweetpawams.enabweeawwybiwdweawtimecgmigwationpawam)

  def get(quewy: w-wecapquewy): f-futuwe[candidatetweetswesuwt] = {
    if (enabweweawtimecgpwovidew(quewy)) {
      weawtimecgsouwce.get(quewy)
    } ewse {
      souwce.get(quewy)
    }
  }

  d-def get(quewies: seq[wecapquewy]): futuwe[seq[candidatetweetswesuwt]] = {
    futuwe.cowwect(quewies.map(quewy => get(quewy)))
  }
}
