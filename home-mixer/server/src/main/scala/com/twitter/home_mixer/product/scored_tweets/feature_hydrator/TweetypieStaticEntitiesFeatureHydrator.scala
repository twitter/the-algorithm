package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.google. nject.na .Na d
 mport com.tw ter.convers ons.Durat onOps.R chDurat on
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.D rectedAtUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Has mageFeature
 mport com.tw ter.ho _m xer.model.Ho Features.HasV deoFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nt onScreenNa Feature
 mport com.tw ter.ho _m xer.model.Ho Features. nt onUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.QuotedUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T etyp eStat cEnt  esCac 
 mport com.tw ter.ho _m xer.ut l.t etyp e.RequestF elds
 mport com.tw ter.ho _m xer.ut l.t etyp e.content.T et d aFeaturesExtractor
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.cac .TtlCac 
 mport com.tw ter.spam.rtf.{thr ftscala => sp}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.{thr ftscala => tp}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etyp eStat cEnt  esFeatureHydrator @ nject() (
  t etyp eSt chCl ent: T etyp eSt chCl ent,
  @Na d(T etyp eStat cEnt  esCac ) cac Cl ent: TtlCac [Long, tp.T et])
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("T etyp eStat cEnt  es")

  overr de val features: Set[Feature[_, _]] = Set(
    Author dFeature,
    D rectedAtUser dFeature,
    Exclus veConversat onAuthor dFeature,
    Has mageFeature,
    HasV deoFeature,
     nReplyToT et dFeature,
     nReplyToUser dFeature,
     sRet etFeature,
     nt onScreenNa Feature,
     nt onUser dFeature,
    QuotedT et dFeature,
    QuotedUser dFeature,
    S ceT et dFeature,
    S ceUser dFeature
  )

  pr vate val Cac TTL = 24.h s

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(Author dFeature, None)
    .add(D rectedAtUser dFeature, None)
    .add(Exclus veConversat onAuthor dFeature, None)
    .add(Has mageFeature, false)
    .add(HasV deoFeature, false)
    .add( nReplyToT et dFeature, None)
    .add( nReplyToUser dFeature, None)
    .add( sRet etFeature, false)
    .add( nt onScreenNa Feature, Seq.empty)
    .add( nt onUser dFeature, Seq.empty)
    .add(QuotedT et dFeature, None)
    .add(QuotedUser dFeature, None)
    .add(S ceT et dFeature, None)
    .add(S ceUser dFeature, None)
    .bu ld()

  /**
   * Steps:
   *  1. query cac  w h all cand dates
   *  2. create a cac d feature map
   *  3.  erate cand dates to hydrate features
   *  3.a transform cac d cand dates
   *  3.b hydrate non-cac d cand dates from T etyp e and wr e to cac 
   */
  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val t et ds = cand dates.map(_.cand date. d)
    val cac dT etsMapFu = cac Cl ent
      .get(t et ds)
      .map(_.found)

    St ch.callFuture(cac dT etsMapFu).flatMap { cac dT ets =>
      St ch.collect {
        cand dates.map { cand date =>
           f (cac dT ets.conta ns(cand date.cand date. d))
            St ch.value(createFeatureMap(cac dT ets(cand date.cand date. d)))
          else readFromT etyp e(query, cand date)
        }
      }
    }
  }

  pr vate def createFeatureMap(t et: tp.T et): FeatureMap = {
    val coreData = t et.coreData
    val quotedT et = t et.quotedT et
    val  nt ons = t et. nt ons.getOrElse(Seq.empty)
    val share = coreData.flatMap(_.share)
    val reply = coreData.flatMap(_.reply)

    FeatureMapBu lder()
      .add(Author dFeature, coreData.map(_.user d))
      .add(D rectedAtUser dFeature, coreData.flatMap(_.d rectedAtUser.map(_.user d)))
      .add(
        Exclus veConversat onAuthor dFeature,
        t et.exclus veT etControl.map(_.conversat onAuthor d))
      .add(Has mageFeature, T et d aFeaturesExtractor.has mage(t et))
      .add(HasV deoFeature, T et d aFeaturesExtractor.hasV deo(t et))
      .add( nReplyToT et dFeature, reply.flatMap(_. nReplyToStatus d))
      .add( nReplyToUser dFeature, reply.map(_. nReplyToUser d))
      .add( sRet etFeature, share. sDef ned)
      .add( nt onScreenNa Feature,  nt ons.map(_.screenNa ))
      .add( nt onUser dFeature,  nt ons.flatMap(_.user d))
      .add(QuotedT et dFeature, quotedT et.map(_.t et d))
      .add(QuotedUser dFeature, quotedT et.map(_.user d))
      .add(S ceT et dFeature, share.map(_.s ceStatus d))
      .add(S ceUser dFeature, share.map(_.s ceUser d))
      .bu ld()
  }

  pr vate def readFromT etyp e(
    query: P pel neQuery,
    cand date: Cand dateW hFeatures[T etCand date]
  ): St ch[FeatureMap] = {
    t etyp eSt chCl ent
      .getT etF elds(
        t et d = cand date.cand date. d,
        opt ons = tp.GetT etF eldsOpt ons(
          t et ncludes = RequestF elds.T etStat cEnt  esF elds,
           ncludeRet etedT et = false,
           ncludeQuotedT et = false,
          forUser d = query.getOpt onalUser d, // Needed to get protected T ets for certa n users
          v s b l yPol cy = tp.T etV s b l yPol cy.UserV s ble,
          safetyLevel = So (sp.SafetyLevel.F lterNone) // VF  s handled  n t  For   product
        )
      ).map {
        case tp.GetT etF eldsResult(_, tp.T etF eldsResultState.Found(found), _, _) =>
          cac Cl ent.set(cand date.cand date. d, found.t et, Cac TTL)
          createFeatureMap(found.t et)
        case _ =>
          DefaultFeatureMap + (Author dFeature, cand date.features.getOrElse(Author dFeature, None))
      }
  }
}
