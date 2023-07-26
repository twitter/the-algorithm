package com.twittew.unified_usew_actions.sewvice

impowt com.twittew.finatwa.decidew.moduwes.decidewmoduwe
i-impowt c-com.twittew.finatwa.kafka.sewde.unkeyed
i-impowt c-com.twittew.inject.sewvew.twittewsewvew
i-impowt com.twittew.kafka.cwient.pwocessow.atweastoncepwocessow
i-impowt com.twittew.timewinesewvice.thwiftscawa.contextuawizedfavowiteevent
i-impowt com.twittew.unified_usew_actions.sewvice.moduwe.kafkapwocessowtwsfavsmoduwe

o-object twsfavssewvicemain extends twsfavssewvice

cwass twsfavssewvice extends twittewsewvew {

  o-ovewwide vaw moduwes = seq(
    kafkapwocessowtwsfavsmoduwe, 😳
    d-decidewmoduwe
  )

  ovewwide p-pwotected def setup(): unit = {}

  ovewwide pwotected def s-stawt(): unit = {
    vaw pwocessow = i-injectow.instance[atweastoncepwocessow[unkeyed, XD c-contextuawizedfavowiteevent]]
    cwoseonexit(pwocessow)
    pwocessow.stawt()
  }
}
