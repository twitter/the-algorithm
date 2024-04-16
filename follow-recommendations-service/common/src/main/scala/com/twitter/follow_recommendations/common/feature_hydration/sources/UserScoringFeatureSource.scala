package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.google. nject. nject
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce d
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .DataRecord rger
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * T  s ce wraps around t  separate s ces that   hydrate features from
 * @param featureStoreS ce        gets features that requ re a RPC call to feature store
 * @param stratoFeatureHydrat onS ce    gets features that requ re a RPC call to strato columns
 * @param cl entContextS ce       gets features that are already present  n t  request context
 * @param cand dateAlgor hmS ce  gets features that are already present from cand date generat on
 * @param preFetc dFeatureS ce   gets features that  re prehydrated (shared  n request l fecycle)
 */
@Prov des
@S ngleton
class UserScor ngFeatureS ce @ nject() (
  featureStoreS ce: FeatureStoreS ce,
  featureStoreG zmoduckS ce: FeatureStoreG zmoduckS ce,
  featureStorePostNuxAlgor hmS ce: FeatureStorePostNuxAlgor hmS ce,
  featureStoreT  l nesAuthorS ce: FeatureStoreT  l nesAuthorS ce,
  featureStoreUser tr cCountsS ce: FeatureStoreUser tr cCountsS ce,
  cl entContextS ce: Cl entContextS ce,
  cand dateAlgor hmS ce: Cand dateAlgor hmS ce,
  preFetc dFeatureS ce: PreFetc dFeatureS ce)
    extends FeatureS ce {

  overr de val  d: FeatureS ce d = FeatureS ce d.UserScor ngFeatureS ce d

  overr de val featureContext: FeatureContext = FeatureContext. rge(
    featureStoreS ce.featureContext,
    featureStoreG zmoduckS ce.featureContext,
    featureStorePostNuxAlgor hmS ce.featureContext,
    featureStoreT  l nesAuthorS ce.featureContext,
    featureStoreUser tr cCountsS ce.featureContext,
    cl entContextS ce.featureContext,
    cand dateAlgor hmS ce.featureContext,
    preFetc dFeatureS ce.featureContext,
  )

  val s ces =
    Seq(
      featureStoreS ce,
      featureStorePostNuxAlgor hmS ce,
      featureStoreT  l nesAuthorS ce,
      featureStoreUser tr cCountsS ce,
      featureStoreG zmoduckS ce,
      cl entContextS ce,
      cand dateAlgor hmS ce,
      preFetc dFeatureS ce
    )

  val dataRecord rger = new DataRecord rger

  def hydrateFeatures(
    target: HasCl entContext
      w h HasPreFetc dFeature
      w h HasParams
      w h HasS m larToContext
      w h HasD splayLocat on,
    cand dates: Seq[Cand dateUser]
  ): St ch[Map[Cand dateUser, DataRecord]] = {
    St ch.collect(s ces.map(_.hydrateFeatures(target, cand dates))).map { featureMaps =>
      (for {
        cand date <- cand dates
      } y eld {
        val comb nedDataRecord = new DataRecord
        featureMaps
          .flatMap(_.get(cand date).toSeq).foreach(dataRecord rger. rge(comb nedDataRecord, _))
        cand date -> comb nedDataRecord
      }).toMap
    }
  }
}
