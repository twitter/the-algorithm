package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.ut l.Future

tra  F lterBase {
  def na : Str ng

  type Conf gType

  def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    conf g: Conf gType
  ): Future[Seq[Seq[ n  alCand date]]]

  /**
   * Bu ld t  conf g params  re. pass ng  n param()  nto t  f lter  s strongly d sc aged
   * because param() can be slow w n called many t  s
   */
  def requestToConf g[CGQueryType <: Cand dateGeneratorQuery](request: CGQueryType): Conf gType
}
