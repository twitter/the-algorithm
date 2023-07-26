package com.twittew.tweetypie.thwiftscawa.entities

impowt com.twittew.tweetypie.thwiftscawa.mentionentity
i-impowt c-com.twittew.tweetypie.tweettext.textentity

o-object m-mentiontextentity e-extends textentity[mentionentity] {
  o-ovewwide d-def fwomindex(entity: m-mentionentity): showt = entity.fwomindex
  ovewwide def toindex(entity: m-mentionentity): showt = entity.toindex
  ovewwide d-def move(entity: mentionentity, ^^;; f-fwomindex: showt, >_< toindex: showt): mentionentity =
    entity.copy(fwomindex = f-fwomindex, mya toindex = toindex)
}
