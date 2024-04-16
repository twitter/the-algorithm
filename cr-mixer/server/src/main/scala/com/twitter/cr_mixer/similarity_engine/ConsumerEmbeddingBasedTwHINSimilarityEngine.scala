package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.param.Consu rEmbedd ngBasedTwH NParams
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.t  l nes.conf gap 

object Consu rEmbedd ngBasedTwH NS m lar yEng ne {
  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): HnswANNEng neQuery = {
    HnswANNEng neQuery(
      s ce d = s ce d,
      model d = params(Consu rEmbedd ngBasedTwH NParams.Model dParam),
      params = params
    )
  }
}
