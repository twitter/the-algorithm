package com.tw ter.cr_m xer.f lter

 mport com.tw ter.contentrecom nder.thr ftscala.T et nfo
 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.CrCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. althThreshold
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

@S ngleton
tra  T et nfo althF lterBase extends F lterBase {
  overr de def na : Str ng = t .getClass.getCanon calNa 
  overr de type Conf gType =  althThreshold.Enum.Value
  def thresholdToPropertyMap: Map[ althThreshold.Enum.Value, T et nfo => Opt on[Boolean]]
  def getF lterParamFn: Cand dateGeneratorQuery =>  althThreshold.Enum.Value

  overr de def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    conf g:  althThreshold.Enum.Value
  ): Future[Seq[Seq[ n  alCand date]]] = {
    Future.value(cand dates.map { seq =>
      seq.f lter(p => thresholdToPropertyMap(conf g)(p.t et nfo).getOrElse(true))
    })
  }

  /**
   * Bu ld t  conf g params  re. pass ng  n param()  nto t  f lter  s strongly d sc aged
   * because param() can be slow w n called many t  s
   */
  overr de def requestToConf g[CGQueryType <: Cand dateGeneratorQuery](
    query: CGQueryType
  ):  althThreshold.Enum.Value = {
    query match {
      case q: CrCand dateGeneratorQuery => getF lterParamFn(q)
      case _ =>  althThreshold.Enum.Off
    }
  }
}
