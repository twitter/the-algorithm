package com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.earlyb rd

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.core_workflows.user_model.{thr ftscala => um}
 mport com.tw ter.ho _m xer.model.Ho Features.UserStateFeature
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.earlyb rd.Earlyb rd nNetworkQueryTransfor r._
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersFeature
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l nes.common.model.T etK ndOpt on

object Earlyb rd nNetworkQueryTransfor r {
  pr vate val DefaultS nceDurat on = 24.h s
  pr vate val ExpandedS nceDurat on = 48.h s
  pr vate val MaxT etsToFetch = 600
  pr vate val TensorflowModel = So ("t  l nes_recap_repl ca")

  pr vate val T etK ndOpt ons: T etK ndOpt on.ValueSet = T etK ndOpt on(
     ncludeRepl es = true,
     ncludeRet ets = true,
     ncludeOr g nalT etsAndQuotes = true,
     ncludeExtendedRepl es = true
  )

  pr vate val UserStatesForExtendedS nceDurat on: Set[um.UserState] = Set(
    um.UserState.L ght,
    um.UserState. d umNonT eter,
    um.UserState. d umT eter,
    um.UserState.NearZero,
    um.UserState.New,
    um.UserState.VeryL ght
  )
}

case class Earlyb rd nNetworkQueryTransfor r[
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

    val userState = query.features.get.getOrElse(UserStateFeature, None)

    val s nceDurat on =
       f (userState.ex sts(UserStatesForExtendedS nceDurat on.conta ns)) ExpandedS nceDurat on
      else DefaultS nceDurat on

    val follo dUser ds =
      query.features
        .map(
          _.getOrElse(
            SGSFollo dUsersFeature,
            Seq.empty)).toSeq.flatten.toSet + query.getRequ redUser d

    bu ldEarlyb rdQuery(query, s nceDurat on, follo dUser ds)
  }
}
