package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.RealGraphFeatureRepos ory
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.repos ory.Repos ory
 mport com.tw ter.t  l nes.real_graph.{thr ftscala => rg}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.real_graph.v1.thr ftscala.RealGraphEdgeFeatures
 mport com.tw ter.user_sess on_store.{thr ftscala => uss}

 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object RealGraphFeatures extends Feature[P pel neQuery, Opt on[Map[User d, RealGraphEdgeFeatures]]]

@S ngleton
class RealGraphQueryFeatureHydrator @ nject() (
  @Na d(RealGraphFeatureRepos ory) repos ory: Repos ory[Long, Opt on[uss.UserSess on]])
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("RealGraphFeatures")

  overr de val features: Set[Feature[_, _]] = Set(RealGraphFeatures)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    St ch.callFuture {
      repos ory(query.getRequ redUser d).map { userSess on =>
        val realGraphFeaturesMap = userSess on.flatMap { userSess on =>
          userSess on.realGraphFeatures.collect {
            case rg.RealGraphFeatures.V1(realGraphFeatures) =>
              val edgeFeatures = realGraphFeatures.edgeFeatures ++ realGraphFeatures.oonEdgeFeatures
              edgeFeatures.map { edge => edge.dest d -> edge }.toMap
          }
        }

        FeatureMapBu lder().add(RealGraphFeatures, realGraphFeaturesMap).bu ld()
      }
    }
  }
}
