package com.tw ter.product_m xer.component_l brary.cand date_s ce.explore_ranker

 mport com.tw ter.explore_ranker.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ExploreRankerCand dateS ce @ nject() (
  exploreRankerServ ce: t.ExploreRanker. thodPerEndpo nt)
    extends Cand dateS ce[t.ExploreRankerRequest, t. m rs veRecsResult] {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("ExploreRanker")

  overr de def apply(
    request: t.ExploreRankerRequest
  ): St ch[Seq[t. m rs veRecsResult]] = {
    St ch
      .callFuture(exploreRankerServ ce.getRankedResults(request))
      .map {
        case t.ExploreRankerResponse(
              t.ExploreRankerProductResponse
                . m rs veRecsResponse(t. m rs veRecsResponse( m rs veRecsResults))) =>
           m rs veRecsResults
        case response =>
          throw new UnsupportedOperat onExcept on(s"Unknown response type: $response")
      }
  }
}
