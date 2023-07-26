package com.twittew.tsp.moduwes

impowt com.googwe.inject.moduwe
i-impowt com.twittew.finagwe.thwiftmux
i-impowt com.twittew.finagwe.mtws.authentication.sewviceidentifiew
i-impowt com.twittew.finagwe.mtws.cwient.mtwsstackcwient._
impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.finagwe.thwift.cwientid
i-impowt c-com.twittew.finatwa.mtws.thwiftmux.moduwes.mtwscwient
impowt com.twittew.gizmoduck.thwiftscawa.usewsewvice
impowt com.twittew.inject.injectow
impowt c-com.twittew.inject.thwift.moduwes.thwiftmethodbuiwdewcwientmoduwe

object gizmoduckusewmoduwe
    extends thwiftmethodbuiwdewcwientmoduwe[
      u-usewsewvice.sewvicepewendpoint, rawr x3
      usewsewvice.methodpewendpoint
    ]
    w-with mtwscwient {

  ovewwide vaw wabew: stwing = "gizmoduck"
  ovewwide vaw d-dest: stwing = "/s/gizmoduck/gizmoduck"
  ovewwide v-vaw moduwes: s-seq[moduwe] = seq(tspcwientidmoduwe)

  ovewwide def configuwethwiftmuxcwient(
    injectow: injectow, nyaa~~
    cwient: t-thwiftmux.cwient
  ): thwiftmux.cwient = {
    supew
      .configuwethwiftmuxcwient(injectow, /(^•ω•^) cwient)
      .withmutuawtws(injectow.instance[sewviceidentifiew])
      .withcwientid(injectow.instance[cwientid])
      .withstatsweceivew(injectow.instance[statsweceivew].scope("giz"))
  }
}
