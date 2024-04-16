package com.tw ter.product_m xer.component_l brary.f lter.l st_v s b l y

 mport com.tw ter.product_m xer.component_l brary.model.cand date.Tw terL stCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.soc algraph.thr ftscala.Soc algraphL st
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Fetch
 mport com.tw ter.strato.generated.cl ent.l sts.reads.CoreOnL stCl entColumn

/* T  F lter quer es t  core.L st.strato column
 * on Strato, and f lters out any l sts that are not
 * returned. core.L st.strato performs an author zat on
 * c ck, and does not return l sts t  v e r  s not author zed
 * to have access to. */
class L stV s b l yF lter[Cand date <: Un versalNoun[Long]](
  l stsColumn: CoreOnL stCl entColumn)
    extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("L stV s b l y")

  def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {

    val l stCand dates = cand dates.collect {
      case Cand dateW hFeatures(cand date: Tw terL stCand date, _) => cand date
    }

    St ch
      .traverse(
        l stCand dates.map(_. d)
      ) { l st d =>
        l stsColumn.fetc r.fetch(l st d)
      }.map { fetchResults =>
        fetchResults.collect {
          case Fetch.Result(So (l st: Soc algraphL st), _) => l st. d
        }
      }.map { allo dL st ds =>
        val (kept, excluded) = cand dates.map(_.cand date).part  on {
          case cand date: Tw terL stCand date => allo dL st ds.conta ns(cand date. d)
          case _ => true
        }
        F lterResult(kept, excluded)
      }
  }
}
