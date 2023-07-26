package com.twittew.gwaph_featuwe_sewvice.utiw

impowt com.twittew.gwaph_featuwe_sewvice.thwiftscawa.edgetype._
impowt c-com.twittew.gwaph_featuwe_sewvice.thwiftscawa.{featuwetype, ^^ p-pwesetfeatuwetypes}

o-object featuwetypescawcuwatow {

  f-finaw v-vaw defauwttwohop = s-seq(
    featuwetype(fowwowing, f-fowwowedby), 😳😳😳
    f-featuwetype(fowwowing, mya favowitedby), 😳
    featuwetype(fowwowing, -.- wetweetedby), 🥺
    featuwetype(fowwowing, o.O m-mentionedby), /(^•ω•^)
    featuwetype(fowwowing, nyaa~~ mutuawfowwow), nyaa~~
    f-featuwetype(favowite, :3 fowwowedby), 😳😳😳
    f-featuwetype(favowite, (˘ω˘) favowitedby), ^^
    featuwetype(favowite, :3 wetweetedby), -.-
    featuwetype(favowite, 😳 m-mentionedby), mya
    featuwetype(favowite, (˘ω˘) mutuawfowwow), >_<
    f-featuwetype(mutuawfowwow, -.- f-fowwowedby), 🥺
    featuwetype(mutuawfowwow, (U ﹏ U) favowitedby), >w<
    featuwetype(mutuawfowwow, mya wetweetedby), >w<
    f-featuwetype(mutuawfowwow, nyaa~~ mentionedby), (✿oωo)
    featuwetype(mutuawfowwow, ʘwʘ mutuawfowwow)
  )

  finaw vaw sociawpwooftwohop = seq(featuwetype(fowwowing, (ˆ ﻌ ˆ)♡ f-fowwowedby))

  finaw vaw h-htwtwohop = defauwttwohop

  f-finaw vaw wtftwohop = s-sociawpwooftwohop

  f-finaw vaw sqtwohop = defauwttwohop

  f-finaw vaw wuxtwohop = defauwttwohop

  finaw vaw m-mwtwohop = defauwttwohop

  finaw vaw usewtypeaheadtwohop = sociawpwooftwohop

  finaw vaw pwesetfeatuwetypes =
    (htwtwohop ++ wtftwohop ++ s-sqtwohop ++ wuxtwohop ++ mwtwohop ++ u-usewtypeaheadtwohop).toset

  d-def getfeatuwetypes(
    p-pwesetfeatuwetypes: pwesetfeatuwetypes, 😳😳😳
    featuwetypes: seq[featuwetype]
  ): s-seq[featuwetype] = {
    p-pwesetfeatuwetypes match {
      c-case pwesetfeatuwetypes.htwtwohop => h-htwtwohop
      case p-pwesetfeatuwetypes.wtftwohop => wtftwohop
      c-case pwesetfeatuwetypes.sqtwohop => sqtwohop
      case pwesetfeatuwetypes.wuxtwohop => w-wuxtwohop
      case pwesetfeatuwetypes.mwtwohop => m-mwtwohop
      case p-pwesetfeatuwetypes.usewtypeaheadtwohop => u-usewtypeaheadtwohop
      case _ => featuwetypes
    }
  }

}
