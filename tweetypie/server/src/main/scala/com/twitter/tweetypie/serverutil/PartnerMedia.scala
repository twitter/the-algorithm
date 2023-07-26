package com.twittew.tweetypie.sewvewutiw

impowt c-com.twittew.config.yamw.yamwmap
i-impowt scawa.utiw.matching.wegex

o-object pawtnewmedia {
  d-def woad(yamwmap: y-yamwmap): s-seq[wegex] =
    (httpowhttps(yamwmap) ++ h-httponwy(yamwmap)).map(_.w)

  pwivate d-def httpowhttps(yamwmap: yamwmap): seq[stwing] =
    yamwmap.stwingseq("http_ow_https").map("""^(?:https?\:\/\/)?""" + _)

  pwivate def httponwy(yamwmap: y-yamwmap): seq[stwing] =
    yamwmap.stwingseq("http_onwy").map("""^(?:http\:\/\/)?""" + _)
}
