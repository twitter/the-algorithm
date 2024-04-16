package com.tw ter.ho _m xer.product.l st_recom nded_users.cand date_s ce

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.search.adapt ve.adapt ve_results.thr ftscala.Adapt veSearchResultData
 mport com.tw ter.search.adapt ve.adapt ve_results.thr ftscala.Result
 mport com.tw ter.search.adapt ve.adapt ve_results.thr ftscala.ResultData
 mport com.tw ter.search.blender.adapt ve_search.thr ftscala.Adapt veSearchResponse
 mport com.tw ter.search.blender.adapt ve_search.thr ftscala.Conta ner
 mport com.tw ter.search.blender.thr ftscala.BlenderServ ce
 mport com.tw ter.search.blender.thr ftscala.Thr ftBlenderRequest
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class BlenderUsersCand dateS ce @ nject() (
  blenderCl ent: BlenderServ ce. thodPerEndpo nt)
    extends Cand dateS ce[Thr ftBlenderRequest, Long] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("BlenderUsers")

  overr de def apply(request: Thr ftBlenderRequest): St ch[Seq[Long]] = {
    St ch.callFuture(
      blenderCl ent.serveV2(request).map { response =>
        val user dsOpt =
          response.adapt veSearchResponse.map(extractUser dsFromAdapt veSearchResponse)
        user dsOpt.getOrElse(Seq.empty)
      }
    )
  }

  pr vate def extractUser dsFromAdapt veSearchResponse(
    response: Adapt veSearchResponse
  ): Seq[Long] = {
    response match {
      case Adapt veSearchResponse(So (Seq(Conta ner(So (results), _))), _, _) =>
        results.map(_.data).collect {
          case Adapt veSearchResultData.Result(Result(ResultData.User(user), _)) =>
            user. d
        }
      case _ => Seq.empty
    }
  }
}
