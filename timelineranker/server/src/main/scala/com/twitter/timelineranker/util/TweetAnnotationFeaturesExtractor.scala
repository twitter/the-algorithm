package com.tw ter.t  l neranker.ut l

 mport com.tw ter.t etyp e.{thr ftscala => t etyp e}
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures

object T etAnnotat onFeaturesExtractor {
  def addAnnotat onFeaturesFromT et(
     nputFeatures: ContentFeatures,
    t et: t etyp e.T et,
    hydrateSemant cCoreFeatures: Boolean
  ): ContentFeatures = {
     f (hydrateSemant cCoreFeatures) {
      val annotat ons = t et.esc rb rdEnt yAnnotat ons.map(_.ent yAnnotat ons)
       nputFeatures.copy(semant cCoreAnnotat ons = annotat ons)
    } else {
       nputFeatures
    }
  }
}
