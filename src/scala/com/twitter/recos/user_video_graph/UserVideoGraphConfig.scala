package com.twittew.wecos.usew_video_gwaph

impowt c-com.twittew.wecos.gwaph_common.muwtisegmentpowewwawbipawtitegwaphbuiwdew.gwaphbuiwdewconfig

/**
 * t-the cwass h-howds aww the config p-pawametews f-fow wecos gwaph. rawr
 */
o-object wecosconfig {
  v-vaw m-maxnumsegments: int = 8
  vaw maxnumedgespewsegment: int =
    (1 << 28) // 268m edges pew segment, mya shouwd be abwe t-to incwude 2 days' data
  vaw expectednumweftnodes: i-int =
    (1 << 26) // shouwd c-cowwespond to 67m nyodes stowage
  vaw expectedmaxweftdegwee: int = 64
  vaw w-weftpowewwawexponent: doubwe = 16.0 // s-steep powew w-waw as most nyodes wiww have a smow degwee
  vaw expectednumwightnodes: int = (1 << 26) // 67m n-nyodes
  vaw expectedmaxwightdegwee: int = scawa.math.pow(1024, ^^ 2).toint // some nyodes wiww be vewy popuwaw
  v-vaw wightpowewwawexponent: doubwe = 4.0 // t-this w-wiww be wess s-steep

  vaw gwaphbuiwdewconfig = g-gwaphbuiwdewconfig(
    maxnumsegments = maxnumsegments, 😳😳😳
    maxnumedgespewsegment = m-maxnumedgespewsegment, mya
    expectednumweftnodes = expectednumweftnodes, 😳
    e-expectedmaxweftdegwee = expectedmaxweftdegwee, -.-
    weftpowewwawexponent = weftpowewwawexponent, 🥺
    expectednumwightnodes = expectednumwightnodes, o.O
    expectedmaxwightdegwee = e-expectedmaxwightdegwee, /(^•ω•^)
    wightpowewwawexponent = wightpowewwawexponent
  )

  p-pwintwn("wecosconfig -          m-maxnumsegments " + m-maxnumsegments)
  pwintwn("wecosconfig -   maxnumedgespewsegment " + maxnumedgespewsegment)
  p-pwintwn("wecosconfig -    expectednumweftnodes " + e-expectednumweftnodes)
  pwintwn("wecosconfig -   e-expectedmaxweftdegwee " + e-expectedmaxweftdegwee)
  pwintwn("wecosconfig -    w-weftpowewwawexponent " + weftpowewwawexponent)
  pwintwn("wecosconfig -   e-expectednumwightnodes " + expectednumwightnodes)
  pwintwn("wecosconfig -  e-expectedmaxwightdegwee " + expectedmaxwightdegwee)
  p-pwintwn("wecosconfig -   wightpowewwawexponent " + w-wightpowewwawexponent)
}
