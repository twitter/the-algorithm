package com.twittew.home_mixew.functionaw_component.gate

impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.gate.gate
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.gateidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
i-impowt com.twittew.stitch.stitch

o-object s-suppowtedwanguagesgate extends gate[pipewinequewy] {

  ovewwide vaw identifiew: g-gateidentifiew = gateidentifiew("suppowtedwanguages")

  // pwoduction w-wanguages which have high t-twanswation covewage fow stwings used in home timewine. 😳😳😳
  pwivate v-vaw suppowtedwanguages: set[stwing] = s-set(
    "aw", mya // a-awabic
    "aw-x-fm", 😳 // awabic (femawe)
    "bg", -.- // buwgawian
    "bn", 🥺 // bengawi
    "ca", o.O // catawan
    "cs", /(^•ω•^) // czech
    "da", // d-danish
    "de", nyaa~~ // gewman
    "ew", nyaa~~ // gweek
    "en", :3 // engwish
    "en-gb", 😳😳😳 // bwitish engwish
    "en-ss", (˘ω˘) // e-engwish scween shot
    "en-xx", ^^ // e-engwish p-pseudo
    "es", :3 // s-spanish
    "eu", -.- // basque
    "fa", 😳 // f-fawsi (pewsian)
    "fi", mya // finnish
    "fiw", (˘ω˘) // fiwipino
    "fw", >_< // f-fwench
    "ga", -.- // iwish
    "gw", 🥺 // gawician
    "gu", // g-gujawati
    "he", (U ﹏ U) // hebwew
    "hi", >w< // hindi
    "hw", mya // cwoatian
    "hu", >w< // hungawian
    "id", nyaa~~ // indonesian
    "it", (✿oωo) // itawian
    "ja", ʘwʘ // j-japanese
    "kn", (ˆ ﻌ ˆ)♡ // kannada
    "ko", 😳😳😳 // k-kowean
    "mw", :3 // mawathi
    "msa", OwO // m-maway
    "nw", (U ﹏ U) // d-dutch
    "no", >w< // nyowwegian
    "pw", (U ﹏ U) // powish
    "pt", 😳 // powtuguese
    "wo", // womanian
    "wu", (ˆ ﻌ ˆ)♡ // w-wussian
    "sk", 😳😳😳 // s-swovak
    "sw", (U ﹏ U) // sewbian
    "sv", (///ˬ///✿) // s-swedish
    "ta", 😳 // t-tamiw
    "th", 😳 // thai
    "tw", σωσ // t-tuwkish
    "uk", rawr x3 // ukwainian
    "uw", OwO // u-uwdu
    "vi", /(^•ω•^) // vietnamese
    "zh-cn", 😳😳😳 // simpwified c-chinese
    "zh-tw" // twaditionaw c-chinese
  )

  ovewwide def s-shouwdcontinue(quewy: p-pipewinequewy): stitch[boowean] =
    stitch.vawue(quewy.getwanguagecode.fowaww(suppowtedwanguages.contains))
}
