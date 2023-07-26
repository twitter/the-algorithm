package com.twittew.visibiwity.wuwes

impowt com.twittew.timewines.configapi.pawams
i-impowt com.twittew.visibiwity.configapi.pawams.wabewsouwcepawam
i-impowt com.twittew.visibiwity.modews.wabewsouwce

o-object expewimentbase {
  vaw s-souwcetopawammap: m-map[wabewsouwce, w-wabewsouwcepawam] = m-map.empty

  f-finaw def shouwdfiwtewfowsouwce(pawams: pawams, (U ﹏ U) wabewsouwceopt: option[wabewsouwce]): boowean = {
    w-wabewsouwceopt
      .map { souwce =>
        vaw pawam = e-expewimentbase.souwcetopawammap.get(souwce)
        pawam.map(pawams.appwy).getowewse(twue)
      }
      .getowewse(twue)
  }
}
