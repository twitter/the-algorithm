package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.param.UtegT etGlobalParams
 mport com.tw ter.ut l.Future

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Remove un althy cand dates
 * Currently T  l ne Ranker appl es a c ck on t  follow ng three scores:
 *  - tox c yScore
 *  - pBlockScore
 *  - pReportedT etScore
 *
 * W re  sPassT et althF lterStr ct c cks two add  ons scores w h t  sa  threshold:
 *  - pSpam T etScore
 *  - spam T etContentScore
 *
 *  've ver f ed that both f lters behave very s m larly.
 */
@S ngleton
case class Uteg althF lter @ nject() () extends F lterBase {
  overr de def na : Str ng = t .getClass.getCanon calNa 
  overr de type Conf gType = Boolean

  overr de def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    conf g: Conf gType
  ): Future[Seq[Seq[ n  alCand date]]] = {
     f (conf g) {
      Future.value(
        cand dates.map { cand dateSeq =>
          cand dateSeq.f lter { cand date =>
            cand date.t et nfo. sPassT et althF lterStr ct.getOrElse(false)
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
    query.params(UtegT etGlobalParams.EnableTLR althF lterParam)
  }
}
