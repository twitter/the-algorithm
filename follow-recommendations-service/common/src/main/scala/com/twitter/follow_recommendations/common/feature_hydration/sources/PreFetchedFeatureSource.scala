package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.google. nject. nject
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters.PreFetc dFeatureAdapter
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

@Prov des
@S ngleton
class PreFetc dFeatureS ce @ nject() () extends FeatureS ce {
  overr de def  d: FeatureS ce d = FeatureS ce d.PreFetc dFeatureS ce d
  overr de def featureContext: FeatureContext = PreFetc dFeatureAdapter.getFeatureContext
  overr de def hydrateFeatures(
    target: HasCl entContext
      w h HasPreFetc dFeature
      w h HasParams
      w h HasS m larToContext
      w h HasD splayLocat on,
    cand dates: Seq[Cand dateUser]
  ): St ch[Map[Cand dateUser, DataRecord]] = {
    St ch.value(cand dates.map { cand date =>
      cand date -> PreFetc dFeatureAdapter.adaptToDataRecord((target, cand date))
    }.toMap)
  }
}
