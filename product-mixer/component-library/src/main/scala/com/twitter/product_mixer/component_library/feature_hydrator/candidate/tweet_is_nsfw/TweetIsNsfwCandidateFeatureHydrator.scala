package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_ s_nsfw

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
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.{thr ftscala => t}
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng

// T  VF NsfwH ghPrec s onLabel that po rs t  NSFW determ nat on  re has been deprecated and  s no longer wr ten to.
@deprecated("Prefer V s b l yReason")
object  sNsfw extends FeatureW hDefaultOnFa lure[T etCand date, Opt on[Boolean]] {

  /**
   * Gener c Log c to evaluate w t r a t et  s nsfw
   * @param hasNsfwH ghPrec s onLabel flag for t etyp eT et nsfwH ghPrec s on label
   * @param  sNsfwUser flag for t etyp eT et coreData nsfwUser flag
   * @param  sNsfwAdm n flag for t etyp eT et coreData nsfwAdm n flag
   * @return  sNsfw to true  f any of t  three flags  s true
   */
  def apply(
    hasNsfwH ghPrec s onLabel: Opt on[Boolean],
     sNsfwUser: Opt on[Boolean],
     sNsfwAdm n: Opt on[Boolean]
  ): Boolean = {
    hasNsfwH ghPrec s onLabel
      .getOrElse(false) || ( sNsfwUser.getOrElse(false) ||  sNsfwAdm n.getOrElse(false))
  }

  overr de val defaultValue = None
}

// T  VF NsfwH ghPrec s onLabel that po rs t  NSFW determ nat on  re has been deprecated and  s no longer wr ten to.
// TODO: Remove after all dependenc es have m grated to us ng T etCand dateV s b l yReasonFeatureHydrator.
@deprecated("Prefer T etCand dateV s b l yReasonFeatureHydrator")
case class T et sNsfwCand dateFeatureHydrator(
  t etyp eSt chCl ent: T etyp eSt chCl ent,
  t etV s b l yPol cy: t.T etV s b l yPol cy)
    extends BulkCand dateFeatureHydrator[P pel neQuery, BaseT etCand date]
    w h Logg ng {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("T et sNsfw")

  overr de def features: Set[Feature[_, _]] = Set( sNsfw)

  pr vate val NsfwLabelF elds: Set[t.T et nclude] = Set[t.T et nclude](
    // T et f elds conta n ng NSFW related attr butes,  n add  on to what ex sts  n coreData.
    t.T et nclude.T etF eld d(t.T et.NsfwH ghPrec s onLabelF eld. d),
    t.T et nclude.T etF eld d(t.T et.CoreDataF eld. d)
  )

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[BaseT etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    St ch
      .traverse(cand dates.map(_.cand date. d)) { t et d =>
        t etyp eSt chCl ent
          .getT etF elds(
            t et d = t et d,
            opt ons = t.GetT etF eldsOpt ons(
              forUser d = query.getOpt onalUser d,
              t et ncludes = NsfwLabelF elds,
              doNotCac  = true,
              v s b l yPol cy = t etV s b l yPol cy,
              safetyLevel = None,
            )
          ).l ftToTry
      }.map { getT etF eldsResults: Seq[Try[t.GetT etF eldsResult]] =>
        val t ets: Seq[Try[t.T et]] = getT etF eldsResults.map {
          case Return(t.GetT etF eldsResult(_, t.T etF eldsResultState.Found(found), _, _)) =>
            Return(found.t et)
          case Return(t.GetT etF eldsResult(_, resultState, _, _)) =>
            Throw( sNsfwFeatureHydrat onFa lure(s"Unexpected t et result state: ${resultState}"))
          case Throw(e) =>
            Throw(e)
        }

        cand dates.z p(t ets).map {
          case (cand dateW hFeatures, t etTry) =>
            val  sNsfwFeature = t etTry.map { t et =>
               sNsfw(
                hasNsfwH ghPrec s onLabel = So (t et.nsfwH ghPrec s onLabel. sDef ned),
                 sNsfwUser = t et.coreData.map(_.nsfwUser),
                 sNsfwAdm n = t et.coreData.map(_.nsfwAdm n)
              )
            }

            FeatureMapBu lder()
              .add( sNsfw,  sNsfwFeature.map(So (_)))
              .bu ld()
        }
      }
  }
}

case class  sNsfwFeatureHydrat onFa lure( ssage: Str ng)
    extends Except on(s" sNsfwFeatureHydrat onFa lure(${ ssage})")
