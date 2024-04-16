package com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph

 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * T  s ce gets t  already follo d edges from t  real graph column as a cand date s ce.
 */
@S ngleton
class RealGraphS ce @ nject() (
  realGraph: RealT  RealGraphCl ent)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser] {
  overr de val  dent f er: Cand dateS ce dent f er = RealGraphS ce. dent f er

  overr de def apply(request: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    request.getOpt onalUser d
      .map { user d =>
        realGraph.getRealGraph  ghts(user d).map { scoreMap =>
          scoreMap.map {
            case (cand date d, realGraphScore) =>
              Cand dateUser( d = cand date d, score = So (realGraphScore))
                .w hCand dateS ce( dent f er)
          }.toSeq
        }
      }.getOrElse(St ch.N l)
  }
}

object RealGraphS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.RealGraphFollo d.toStr ng)
}
