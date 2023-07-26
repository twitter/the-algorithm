package com.twittew.timewinewankew.wepositowy

impowt c-com.twittew.timewinewankew.modew.wevewsechwontimewinequewy
i-impowt com.twittew.timewinewankew.modew.timewine
i-impowt com.twittew.timewinewankew.pawametews.wevchwon.wevewsechwontimewinequewycontextbuiwdew
impowt c-com.twittew.timewinewankew.souwce.wevewsechwonhometimewinesouwce
i-impowt com.twittew.utiw.futuwe

/**
 * a-a w-wepositowy of wevewse-chwon h-home timewines. ^^;;
 *
 * it does nyot cache any wesuwts thewefowe fowwawds a-aww cawws to the undewwying souwce. >_<
 */
cwass w-wevewsechwonhometimewinewepositowy(
  souwce: w-wevewsechwonhometimewinesouwce, mya
  contextbuiwdew: wevewsechwontimewinequewycontextbuiwdew) {
  def get(quewy: wevewsechwontimewinequewy): f-futuwe[timewine] = {
    contextbuiwdew(quewy).fwatmap(souwce.get)
  }
}
