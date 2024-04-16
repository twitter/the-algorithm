package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByCountFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.RealGraph nNetworkScoresFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Repl edByCountFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Repl edByEngager dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Ret etedByCountFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Ret etedByEngager dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.UtegSoc alProofRepos ory
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.recos.recos_common.{thr ftscala => rc}
 mport com.tw ter.recos.user_t et_ent y_graph.{thr ftscala => uteg}
 mport com.tw ter.servo.keyvalue.KeyValueResult
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class UtegFeatureHydrator @ nject() (
  @Na d(UtegSoc alProofRepos ory) cl ent: KeyValueRepos ory[
    (Seq[Long], (Long, Map[Long, Double])),
    Long,
    uteg.T etRecom ndat on
  ]) extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h Cond  onally[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Uteg")

  overr de val features: Set[Feature[_, _]] = Set(
    Favor edByUser dsFeature,
    Ret etedByEngager dsFeature,
    Repl edByEngager dsFeature,
    Favor edByCountFeature,
    Ret etedByCountFeature,
    Repl edByCountFeature
  )

  overr de def only f(query: P pel neQuery): Boolean = query.features
    .ex sts(_.getOrElse(RealGraph nNetworkScoresFeature, Map.empty[Long, Double]).nonEmpty)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val seedUser  ghts = query.features.map(_.get(RealGraph nNetworkScoresFeature)).get

    val s ceT et ds = cand dates.flatMap(_.features.getOrElse(S ceT et dFeature, None))
    val  nReplyToT et ds = cand dates.flatMap(_.features.getOrElse( nReplyToT et dFeature, None))
    val t et ds = cand dates.map(_.cand date. d)
    val t et dsToSend = (t et ds ++ s ceT et ds ++  nReplyToT et ds).d st nct

    val utegQuery = (t et dsToSend, (query.getRequ redUser d, seedUser  ghts))

    cl ent(utegQuery).map(handleResponse(cand dates, _))
  }

  pr vate def handleResponse(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]],
    results: KeyValueResult[Long, uteg.T etRecom ndat on],
  ): Seq[FeatureMap] = {
    cand dates.map { cand date =>
      val  nNetwork = cand date.features.getOrElse(From nNetworkS ceFeature, false)
      val cand dateProof = results(cand date.cand date. d).toOpt on.flatten
      val s ceProof = cand date.features
        .getOrElse(S ceT et dFeature, None).flatMap(results(_).toOpt on.flatten)
      val proofs = Seq(cand dateProof, s ceProof).flatten.map(_.soc alProofByType)

      val favor edBy = proofs.flatMap(_.get(rc.Soc alProofType.Favor e)).flatten
      val ret etedBy = proofs.flatMap(_.get(rc.Soc alProofType.Ret et)).flatten
      val repl edBy = proofs.flatMap(_.get(rc.Soc alProofType.Reply)).flatten

      val (favor edByCount, ret etedByCount, repl edByCount) =
         f (! nNetwork) {
          (favor edBy.s ze.toDouble, ret etedBy.s ze.toDouble, repl edBy.s ze.toDouble)
        } else { (0.0, 0.0, 0.0) }

      FeatureMapBu lder()
        .add(Favor edByUser dsFeature, favor edBy)
        .add(Ret etedByEngager dsFeature, ret etedBy)
        .add(Repl edByEngager dsFeature, repl edBy)
        .add(Favor edByCountFeature, favor edByCount)
        .add(Ret etedByCountFeature, ret etedByCount)
        .add(Repl edByCountFeature, repl edByCount)
        .bu ld()
    }
  }
}
