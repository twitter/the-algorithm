package com.twittew.fowwow_wecommendations.common.cwients.common

impowt com.twittew.finagwe.thwiftmux
i-impowt com.twittew.finagwe.thwift.pwotocows
i-impowt com.twittew.fowwow_wecommendations.common.constants.sewviceconstants._
i-impowt com.twittew.inject.thwift.moduwes.thwiftcwientmoduwe
i-impowt s-scawa.wefwect.cwasstag

/**
 * b-basic cwient configuwations t-that w-we appwy fow aww of ouw cwients go in hewe
 */
abstwact cwass basecwientmoduwe[t: c-cwasstag] extends thwiftcwientmoduwe[t] {
  def configuwethwiftmuxcwient(cwient: t-thwiftmux.cwient): thwiftmux.cwient = {
    c-cwient
      .withpwotocowfactowy(
        pwotocows.binawyfactowy(
          stwingwengthwimit = stwingwengthwimit, ^^;;
          c-containewwengthwimit = containewwengthwimit))
  }
}
