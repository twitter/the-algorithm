package com.twittew.ann.sewvice.quewy_sewvew.common

impowt com.googwe.inject.moduwe
i-impowt com.twittew.ann.common.thwiftscawa.annquewysewvice
i-impowt c-com.twittew.app.fwag
i-impowt c-com.twittew.finatwa.decidew.moduwes.decidewmoduwe
i-impowt com.twittew.finatwa.thwift.thwiftsewvew
i-impowt com.twittew.finatwa.mtws.thwiftmux.mtws
i-impowt com.twittew.finatwa.mtws.thwiftmux.moduwes.mtwsthwiftwebfowmsmoduwe
impowt com.twittew.finatwa.thwift.fiwtews.{
  accesswoggingfiwtew, σωσ
  woggingmdcfiwtew, OwO
  s-statsfiwtew, 😳😳😳
  thwiftmdcfiwtew, 😳😳😳
  twaceidmdcfiwtew
}
i-impowt com.twittew.finatwa.thwift.wouting.thwiftwoutew

/**
 * t-this cwass pwovides most of the configuwation nyeeded fow w-wogging, o.O stats, decidews etc. ( ͡o ω ͡o )
 */
a-abstwact cwass b-basequewyindexsewvew extends thwiftsewvew with mtws {

  pwotected vaw enviwonment: f-fwag[stwing] = fwag[stwing]("enviwonment", (U ﹏ U) "sewvice enviwonment")

  /**
   * ovewwide with method to pwovide m-mowe moduwe to guice. (///ˬ///✿)
   */
  p-pwotected def a-additionawmoduwes: s-seq[moduwe]

  /**
   * o-ovewwide this method to add the contwowwew t-to the thwift woutew. >w< basequewyindexsewvew takes
   * cawe o-of most of the othew configuwation fow you. rawr
   * @pawam woutew
   */
  pwotected def addcontwowwew(woutew: t-thwiftwoutew): unit

  o-ovewwide pwotected f-finaw wazy v-vaw moduwes: seq[moduwe] = seq(
    decidewmoduwe, mya
    nyew mtwsthwiftwebfowmsmoduwe[annquewysewvice.methodpewendpoint](this)
  ) ++ a-additionawmoduwes

  o-ovewwide pwotected f-finaw def configuwethwift(woutew: t-thwiftwoutew): unit = {
    woutew
      .fiwtew[woggingmdcfiwtew]
      .fiwtew[twaceidmdcfiwtew]
      .fiwtew[thwiftmdcfiwtew]
      .fiwtew[accesswoggingfiwtew]
      .fiwtew[statsfiwtew]

    a-addcontwowwew(woutew)
  }
}
