package com.tw ter.follow_recom ndat ons.common.cand date_s ces.real_graph

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.realGraph.UserRealgraphOonV2Cl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RealGraphOonV2S ce @ nject() (
  realGraphCl entColumn: UserRealgraphOonV2Cl entColumn)
    extends Cand dateS ce[HasParams w h HasCl entContext, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er =
    RealGraphOonV2S ce. dent f er

  overr de def apply(request: HasParams w h HasCl entContext): St ch[Seq[Cand dateUser]] = {
    request.getOpt onalUser d
      .map { user d =>
        realGraphCl entColumn.fetc r
          .fetch(user d)
          .map { result =>
            result.v
              .map { cand dates => parseStratoResults(request, cand dates) }
              .getOrElse(N l)
              // returned cand dates are sorted by score  n descend ng order
              .take(request.params(RealGraphOonParams.MaxResults))
              .map(_.w hCand dateS ce( dent f er))
          }
      }.getOrElse(St ch(Seq.empty))
  }

  pr vate def parseStratoResults(
    request: HasParams w h HasCl entContext,
    cand dateSeqThr ft: Cand dateSeq
  ): Seq[Cand dateUser] = {
    cand dateSeqThr ft.cand dates.collect {
      case cand date  f cand date.score >= request.params(RealGraphOonParams.ScoreThreshold) =>
        Cand dateUser(
          cand date.user d,
          So (cand date.score)
        )
    }
  }

}

object RealGraphOonV2S ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.RealGraphOonV2.toStr ng
  )
}
