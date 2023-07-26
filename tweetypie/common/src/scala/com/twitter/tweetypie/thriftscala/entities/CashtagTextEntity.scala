package com.twittew.tweetypie.thwiftscawa.entities

impowt com.twittew.tweetypie.thwiftscawa.cashtagentity
i-impowt c-com.twittew.tweetypie.tweettext.textentity

o-object c-cashtagtextentity e-extends textentity[cashtagentity] {
  o-ovewwide d-def fwomindex(entity: c-cashtagentity): showt = entity.fwomindex
  ovewwide def toindex(entity: c-cashtagentity): showt = entity.toindex
  ovewwide d-def move(entity: cashtagentity, ^^;; f-fwomindex: showt, >_< toindex: showt): cashtagentity =
    entity.copy(fwomindex = f-fwomindex, mya toindex = toindex)
}
