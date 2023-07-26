package com.twittew.unified_usew_actions.sewvice

impowt com.twittew.finatwa.decidew.moduwes.decidewmoduwe
i-impowt c-com.twittew.finatwa.kafka.sewde.unkeyed
i-impowt c-com.twittew.ibis.thwiftscawa.notificationscwibe
i-impowt com.twittew.inject.sewvew.twittewsewvew
impowt c-com.twittew.kafka.cwient.pwocessow.atweastoncepwocessow
i-impowt c-com.twittew.unified_usew_actions.sewvice.moduwe.kafkapwocessowemaiwnotificationeventmoduwe

object emaiwnotificationeventsewvicemain extends emaiwnotificationeventsewvice

cwass emaiwnotificationeventsewvice e-extends twittewsewvew {

  ovewwide vaw moduwes = seq(
    k-kafkapwocessowemaiwnotificationeventmoduwe,
    decidewmoduwe
  )

  o-ovewwide pwotected def setup(): unit = {}

  ovewwide pwotected d-def stawt(): unit = {
    vaw p-pwocessow = injectow.instance[atweastoncepwocessow[unkeyed, mya notificationscwibe]]
    c-cwoseonexit(pwocessow)
    pwocessow.stawt()
  }
}
