package com.twittew.unified_usew_actions.sewvice

impowt com.twittew.finatwa.decidew.moduwes.decidewmoduwe
i-impowt c-com.twittew.finatwa.kafka.sewde.unkeyed
i-impowt c-com.twittew.inject.sewvew.twittewsewvew
i-impowt com.twittew.kafka.cwient.pwocessow.atweastoncepwocessow
i-impowt com.twittew.timewinesewvice.fanout.thwiftscawa.favowiteawchivawevent
i-impowt com.twittew.unified_usew_actions.sewvice.moduwe.kafkapwocessowfavowiteawchivaweventsmoduwe

o-object favowiteawchivaweventssewvicemain extends favowiteawchivaweventssewvice

cwass favowiteawchivaweventssewvice extends twittewsewvew {

  o-ovewwide vaw moduwes = seq(
    kafkapwocessowfavowiteawchivaweventsmoduwe, 😳
    d-decidewmoduwe
  )

  ovewwide p-pwotected def setup(): unit = {}

  ovewwide pwotected def stawt(): u-unit = {
    vaw pwocessow = i-injectow.instance[atweastoncepwocessow[unkeyed, XD f-favowiteawchivawevent]]
    cwoseonexit(pwocessow)
    pwocessow.stawt()
  }
}
