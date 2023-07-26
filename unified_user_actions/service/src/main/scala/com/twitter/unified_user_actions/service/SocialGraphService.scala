package com.twittew.unified_usew_actions.sewvice

impowt com.twittew.finatwa.decidew.moduwes.decidewmoduwe
i-impowt c-com.twittew.finatwa.kafka.sewde.unkeyed
i-impowt c-com.twittew.inject.sewvew.twittewsewvew
i-impowt com.twittew.kafka.cwient.pwocessow.atweastoncepwocessow
i-impowt com.twittew.sociawgwaph.thwiftscawa.wwiteevent
i-impowt c-com.twittew.unified_usew_actions.sewvice.moduwe.kafkapwocessowsociawgwaphmoduwe

object sociawgwaphsewvicemain extends sociawgwaphsewvice

cwass sociawgwaphsewvice extends t-twittewsewvew {
  ovewwide vaw moduwes = seq(
    k-kafkapwocessowsociawgwaphmoduwe, XD
    decidewmoduwe
  )

  o-ovewwide pwotected def setup(): unit = {}

  ovewwide p-pwotected def stawt(): unit = {
    v-vaw pwocessow = i-injectow.instance[atweastoncepwocessow[unkeyed, :3 wwiteevent]]
    cwoseonexit(pwocessow)
    pwocessow.stawt()
  }
}
