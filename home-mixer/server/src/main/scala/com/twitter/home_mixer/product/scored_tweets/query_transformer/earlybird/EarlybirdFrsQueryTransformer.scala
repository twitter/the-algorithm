package com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.earlyb rd

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.FrsSeedUser dsFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.earlyb rd.Earlyb rdFrsQueryTransfor r._
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l nes.common.model.T etK ndOpt on

object Earlyb rdFrsQueryTransfor r {
  pr vate val S nceDurat on = 24.h s
  pr vate val MaxT etsToFetch = 100
  pr vate val TensorflowModel = So ("t  l nes_rect et_repl ca")

  pr vate val T etK ndOpt ons: T etK ndOpt on.ValueSet =
    T etK ndOpt on( ncludeOr g nalT etsAndQuotes = true)
}

case class Earlyb rdFrsQueryTransfor r[
  Query <: P pel neQuery w h HasQual yFactorStatus w h HasDev ceContext
](
  cand dateP pel ne dent f er: Cand dateP pel ne dent f er,
  overr de val cl ent d: Opt on[Str ng])
    extends Cand dateP pel neQueryTransfor r[Query, eb.Earlyb rdRequest]
    w h Earlyb rdQueryTransfor r[Query] {

  overr de val t etK ndOpt ons: T etK ndOpt on.ValueSet = T etK ndOpt ons
  overr de val maxT etsToFetch:  nt = MaxT etsToFetch
  overr de val tensorflowModel: Opt on[Str ng] = TensorflowModel

  overr de def transform(query: Query): eb.Earlyb rdRequest = {
    val seedUser ds = query.features
      .flatMap(_.getOrElse(FrsSeedUser dsFeature, None))
      .getOrElse(Seq.empty).toSet
    bu ldEarlyb rdQuery(query, S nceDurat on, seedUser ds)
  }
}
