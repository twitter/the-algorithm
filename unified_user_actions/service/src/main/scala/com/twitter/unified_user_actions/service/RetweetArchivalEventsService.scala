package com.twittew.unified_usew_actions.sewvice

impowt com.twittew.finatwa.decidew.moduwes.decidewmoduwe
i-impowt c-com.twittew.finatwa.kafka.sewde.unkeyed
i-impowt c-com.twittew.inject.sewvew.twittewsewvew
i-impowt com.twittew.kafka.cwient.pwocessow.atweastoncepwocessow
i-impowt com.twittew.tweetypie.thwiftscawa.wetweetawchivawevent
i-impowt com.twittew.unified_usew_actions.sewvice.moduwe.kafkapwocessowwetweetawchivaweventsmoduwe

o-object wetweetawchivaweventssewvicemain extends wetweetawchivaweventssewvice

cwass wetweetawchivaweventssewvice extends twittewsewvew {

  o-ovewwide vaw moduwes = seq(
    kafkapwocessowwetweetawchivaweventsmoduwe,
    d-decidewmoduwe
  )

  ovewwide p-pwotected def setup(): unit = {}

  ovewwide pwotected def stawt(): u-unit = {
    vaw pwocessow = i-injectow.instance[atweastoncepwocessow[unkeyed, 😳 w-wetweetawchivawevent]]
    cwoseonexit(pwocessow)
    pwocessow.stawt()
  }
}
