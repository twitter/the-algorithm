package com.tw ter.ho _m xer.product.scored_t ets.query_transfor r

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.core_workflows.user_model.{thr ftscala => um}
 mport com.tw ter.ho _m xer.model.Ho Features.UserStateFeature
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.FrsSeedUser dsFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam
 mport com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.T  l neRankerFrsQueryTransfor r._
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.t  l neranker.{thr ftscala => t}
 mport com.tw ter.t  l nes.common.model.T etK ndOpt on
 mport com.tw ter.t  l nes.model.cand date.Cand dateT etS ce d

object T  l neRankerFrsQueryTransfor r {
  pr vate val DefaultS nceDurat on = 24.h s
  pr vate val ExpandedS nceDurat on = 48.h s
  pr vate val MaxT etsToFetch = 100

  pr vate val t etK ndOpt ons: T etK ndOpt on.ValueSet =
    T etK ndOpt on( ncludeOr g nalT etsAndQuotes = true)

  pr vate val UserStatesForExtendedS nceDurat on: Set[um.UserState] = Set(
    um.UserState.L ght,
    um.UserState. d umNonT eter,
    um.UserState. d umT eter,
    um.UserState.NearZero,
    um.UserState.New,
    um.UserState.VeryL ght
  )
}

case class T  l neRankerFrsQueryTransfor r[
  Query <: P pel neQuery w h HasQual yFactorStatus w h HasDev ceContext
](
  overr de val cand dateP pel ne dent f er: Cand dateP pel ne dent f er,
  overr de val maxT etsToFetch:  nt = MaxT etsToFetch)
    extends Cand dateP pel neQueryTransfor r[Query, t.RecapQuery]
    w h T  l neRankerQueryTransfor r[Query] {

  overr de val cand dateT etS ce d = Cand dateT etS ce d.FrsT et
  overr de val opt ons = t etK ndOpt ons

  overr de def getTensorflowModel(query: Query): Opt on[Str ng] = {
    So (query.params(ScoredT etsParam.Earlyb rdTensorflowModel.FrsParam))
  }

  overr de def seedAuthor ds(query: Query): Opt on[Seq[Long]] = {
    query.features.flatMap(_.getOrElse(FrsSeedUser dsFeature, None))
  }

  overr de def transform( nput: Query): t.RecapQuery = {
    val userState =  nput.features.get.getOrElse(UserStateFeature, None)

    val s nceDurat on =
       f (userState.ex sts(UserStatesForExtendedS nceDurat on.conta ns)) ExpandedS nceDurat on
      else DefaultS nceDurat on

    bu ldT  l neRankerQuery( nput, s nceDurat on).toThr ftRecapQuery
  }
}
