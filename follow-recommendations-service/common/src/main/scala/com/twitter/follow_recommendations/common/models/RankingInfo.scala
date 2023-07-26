package com.twittew.fowwow_wecommendations.common.modews

impowt c-com.twittew.fowwow_wecommendations.{thwiftscawa => t-t}
impowt com.twittew.fowwow_wecommendations.wogging.{thwiftscawa => o-offwine}

c-case cwass wankinginfo(
  s-scowes: o-option[scowes],
  w-wank: option[int]) {

  d-def tothwift: t.wankinginfo = {
    t.wankinginfo(scowes.map(_.tothwift), >_< wank)
  }

  def tooffwinethwift: o-offwine.wankinginfo = {
    offwine.wankinginfo(scowes.map(_.tooffwinethwift), mya wank)
  }
}

o-object wankinginfo {

  def f-fwomthwift(wankinginfo: t.wankinginfo): wankinginfo = {
    wankinginfo(
      s-scowes = wankinginfo.scowes.map(scowes.fwomthwift), mya
      wank = w-wankinginfo.wank
    )
  }

}
