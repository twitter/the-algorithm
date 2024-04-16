package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

tra  FeatureS ce {
  def  d: FeatureS ce d
  def featureContext: FeatureContext
  def hydrateFeatures(
    target: HasCl entContext
      w h HasPreFetc dFeature
      w h HasParams
      w h HasS m larToContext
      w h HasD splayLocat on,
    cand dates: Seq[Cand dateUser]
  ): St ch[Map[Cand dateUser, DataRecord]]
}
