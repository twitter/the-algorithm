package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.product_m xer.component_l brary.f lter.T etV s b l yF lter._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.{thr ftscala => TP}
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Try

object T etV s b l yF lter {
  val DefaultT et ncludes = Set(TP.T et nclude.T etF eld d(TP.T et. dF eld. d))
  pr vate f nal val getT etF eldsFa lure ssage = "T etyP e.getT etF elds fa led: "
}

case class T etV s b l yF lter[Cand date <: BaseT etCand date](
  t etyp eSt chCl ent: T etyp eSt chCl ent,
  t etV s b l yPol cy: TP.T etV s b l yPol cy,
  safetyLevel: SafetyLevel,
  t et ncludes: Set[TP.T et nclude.T etF eld d] = DefaultT et ncludes)
    extends F lter[P pel neQuery, Cand date]
    w h Logg ng {

  overr de val  dent f er: F lter dent f er = F lter dent f er("T etV s b l y")

  def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {
    St ch
      .traverse(cand dates.map(_.cand date. d)) { t et d =>
        t etyp eSt chCl ent
          .getT etF elds(t et d, getT etF eldsOpt ons(query.getOpt onalUser d))
          .l ftToTry
      }
      .map { getT etF eldsResults: Seq[Try[TP.GetT etF eldsResult]] =>
        val (c ckedSucceeded, c ckFa led) = getT etF eldsResults.part  on(_. sReturn)
        c ckFa led.foreach(e => warn(() => getT etF eldsFa lure ssage, e.throwable))
         f (c ckFa led.nonEmpty) {
          warn(() =>
            s"T etV s b l yF lter dropped ${c ckFa led.s ze} cand dates due to t etyp e fa lure.")
        }

        val allo dT ets = c ckedSucceeded.collect {
          case Return(TP.GetT etF eldsResult(_, TP.T etF eldsResultState.Found(found), _, _)) =>
            found.t et. d
        }.toSet

        val (kept, removed) =
          cand dates.map(_.cand date).part  on(cand date => allo dT ets.conta ns(cand date. d))

        F lterResult(kept = kept, removed = removed)
      }
  }

  pr vate def getT etF eldsOpt ons(user d: Opt on[Long]) =
    TP.GetT etF eldsOpt ons(
      forUser d = user d,
      t et ncludes = t et ncludes.toSet,
      doNotCac  = true,
      v s b l yPol cy = t etV s b l yPol cy,
      safetyLevel = So (safetyLevel)
    )
}
