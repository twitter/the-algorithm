package com.twittew.gwaph_featuwe_sewvice.wowkew.utiw

impowt com.twittew.gwaph_featuwe_sewvice.thwiftscawa.edgetype
i-impowt com.twittew.utiw.futuwe

c-case cwass gwaphcontainew(
  g-gwaphs: map[gwaphkey, (U ﹏ U) a-autoupdatinggwaph]) {

  f-finaw vaw topawtiawmap: m-map[edgetype, -.- a-autoupdatinggwaph] =
    gwaphs.cowwect {
      c-case (pawtiawvawuegwaph: pawtiawvawuegwaph, (ˆ ﻌ ˆ)♡ gwaph) =>
        pawtiawvawuegwaph.edgetype -> gwaph
    }

  // woad aww the g-gwaphs fwom constantdb fowmat to memowy
  def wawmup: f-futuwe[unit] = {
    futuwe.cowwect(gwaphs.mapvawues(_.wawmup())).unit
  }
}
