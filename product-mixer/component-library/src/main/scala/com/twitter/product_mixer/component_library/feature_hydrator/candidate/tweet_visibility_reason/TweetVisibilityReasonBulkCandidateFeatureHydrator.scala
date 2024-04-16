package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_v s b l y_reason

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.spam.rtf.{thr ftscala => SPAM}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.{thr ftscala => TP}
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

object V s b l yReason
    extends FeatureW hDefaultOnFa lure[T etCand date, Opt on[SPAM.F lteredReason]] {
  overr de val defaultValue = None
}

/**
 * A [[BulkCand dateFeatureHydrator]] that hydrates T etCand dates w h V s b l yReason features
 * by [[SPAM.SafetyLevel]] w n present. T  [[V s b l yReason]] feature represents a V s b l yF lter ng
 * [[SPAM.F lteredReason]], wh ch conta ns safety f lter ng verd ct  nformat on  nclud ng act on (e.g.
 * Drop, Avo d) and reason (e.g. M s nformat on, Abuse). T  feature can  nform downstream serv ces'
 * handl ng and presentat on of T ets (e.g. ad avo dance).
 *
 * @param t etyp eSt chCl ent used to retr eve T et f elds for BaseT etCand dates
 * @param safetyLevel spec f es V s b l yF lter ng SafetyLabel
 */

@S ngleton
case class T etV s b l yReasonBulkCand dateFeatureHydrator @ nject() (
  t etyp eSt chCl ent: T etyp eSt chCl ent,
  safetyLevel: SPAM.SafetyLevel)
    extends BulkCand dateFeatureHydrator[P pel neQuery, BaseT etCand date]
    w h Logg ng {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "T etV s b l yReason")

  overr de def features: Set[Feature[_, _]] = Set(V s b l yReason)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[BaseT etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    St ch
      .traverse(cand dates.map(_.cand date. d)) { t et d =>
        t etyp eSt chCl ent
          .getT etF elds(
            t et d = t et d,
            opt ons = TP.GetT etF eldsOpt ons(
              forUser d = query.getOpt onalUser d,
              t et ncludes = Set.empty,
              doNotCac  = true,
              v s b l yPol cy = TP.T etV s b l yPol cy.UserV s ble,
              safetyLevel = So (safetyLevel)
            )
          ).l ftToTry
      }.map { getT etF eldsResults: Seq[Try[TP.GetT etF eldsResult]] =>
        val t etF elds: Seq[Try[TP.T etF eldsResultFound]] = getT etF eldsResults.map {
          case Return(TP.GetT etF eldsResult(_, TP.T etF eldsResultState.Found(found), _, _)) =>
            Return(found)
          case Return(TP.GetT etF eldsResult(_, resultState, _, _)) =>
            Throw(
              V s b l yReasonFeatureHydrat onFa lure(
                s"Unexpected t et result state: ${resultState}"))
          case Throw(e) =>
            Throw(e)
        }

        t etF elds.map { t etF eldTry =>
          val t etF lteredReason = t etF eldTry.map { t etF eld =>
            t etF eld.suppressReason match {
              case So (suppressReason) => So (suppressReason)
              case _ => None
            }
          }

          FeatureMapBu lder()
            .add(V s b l yReason, t etF lteredReason)
            .bu ld()
        }
      }
  }
}

case class V s b l yReasonFeatureHydrat onFa lure( ssage: Str ng)
    extends Except on(s"V s b l yReasonFeatureHydrat onFa lure($ ssage)")
