package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters.Cl entContextAdapter
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce d
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * T  s ce only takes features from t  request (e.g. cl ent context, WTF d splay locat on)
 * No external calls are made.
 */
@Prov des
@S ngleton
class Cl entContextS ce() extends FeatureS ce {

  overr de val  d: FeatureS ce d = FeatureS ce d.Cl entContextS ce d

  overr de val featureContext: FeatureContext = Cl entContextAdapter.getFeatureContext

  overr de def hydrateFeatures(
    t: HasCl entContext
      w h HasPreFetc dFeature
      w h HasParams
      w h HasS m larToContext
      w h HasD splayLocat on,
    cand dates: Seq[Cand dateUser]
  ): St ch[Map[Cand dateUser, DataRecord]] = {
    St ch.value(
      cand dates
        .map(_ -> ((t.cl entContext, t.d splayLocat on))).toMap.mapValues(
          Cl entContextAdapter.adaptToDataRecord))
  }
}
