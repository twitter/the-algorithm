package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.param.UtegT etGlobalParams
 mport com.tw ter.ut l.Future

 mport javax. nject. nject
 mport javax. nject.S ngleton

/***
 * F lters cand dates that are ret ets
 */
@S ngleton
case class Ret etF lter @ nject() () extends F lterBase {
  overr de def na : Str ng = t .getClass.getCanon calNa 
  overr de type Conf gType = Boolean

  overr de def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    conf g: Conf gType
  ): Future[Seq[Seq[ n  alCand date]]] = {
     f (conf g) {
      Future.value(
        cand dates.map { cand dateSeq =>
          cand dateSeq.f lterNot { cand date =>
            cand date.t et nfo. sRet et.getOrElse(false)
          }
        }
      )
    } else {
      Future.value(cand dates)
    }
  }

  overr de def requestToConf g[CGQueryType <: Cand dateGeneratorQuery](
    query: CGQueryType
  ): Conf gType = {
    query.params(UtegT etGlobalParams.EnableRet etF lterParam)
  }
}
