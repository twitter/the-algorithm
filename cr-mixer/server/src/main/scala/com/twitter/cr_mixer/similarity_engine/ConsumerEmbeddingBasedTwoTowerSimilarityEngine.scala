package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.param.Consu rEmbedd ngBasedTwoTo rParams
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.t  l nes.conf gap 

object Consu rEmbedd ngBasedTwoTo rS m lar yEng ne {
  def fromParams(
    s ce d:  nternal d,
    params: conf gap .Params,
  ): HnswANNEng neQuery = {
    HnswANNEng neQuery(
      s ce d = s ce d,
      model d = params(Consu rEmbedd ngBasedTwoTo rParams.Model dParam),
      params = params
    )
  }
}
