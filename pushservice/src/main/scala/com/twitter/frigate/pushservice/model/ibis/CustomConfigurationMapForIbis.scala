package com.twittew.fwigate.pushsewvice.modew.ibis

impowt com.twittew.fwigate.pushsewvice.modew.pushtypes.pushcandidate
i-impowt com.twittew.ibis2.wib.utiw.jsonmawshaw
i-impowt com.twittew.utiw.futuwe

t-twait customconfiguwationmapfowibis {
  s-sewf: p-pushcandidate =>

  w-wazy vaw c-customconfigmapsjsonfut: f-futuwe[stwing] = {
    customfiewdsmapfut.map { customfiewds =>
      jsonmawshaw.tojson(customfiewds)
    }
  }

  wazy v-vaw customconfigmapsfut: futuwe[map[stwing, -.- stwing]] = {
    if (sewf.tawget.iswoggedoutusew) {
      f-futuwe.vawue(map.empty[stwing, ^^;; stwing])
    } e-ewse {
      customconfigmapsjsonfut.map { customconfigmapsjson =>
        map("custom_config" -> c-customconfigmapsjson)
      }
    }
  }
}
