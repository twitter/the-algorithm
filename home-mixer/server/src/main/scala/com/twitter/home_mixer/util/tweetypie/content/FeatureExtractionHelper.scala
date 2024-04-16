package com.tw ter.ho _m xer.ut l.t etyp e.content

 mport com.tw ter.ho _m xer.model.ContentFeatures
 mport com.tw ter.t etyp e.{thr ftscala => tp}

object FeatureExtract on lper {

  def extractFeatures(
    t et: tp.T et
  ): ContentFeatures = {
    val contentFeaturesFromT et = ContentFeatures.Empty.copy(
      selfThread tadata = t et.selfThread tadata
    )

    val contentFeaturesW hText = T etTextFeaturesExtractor.addTextFeaturesFromT et(
      contentFeaturesFromT et,
      t et
    )
    val contentFeaturesW h d a = T et d aFeaturesExtractor.add d aFeaturesFromT et(
      contentFeaturesW hText,
      t et
    )

    contentFeaturesW h d a.copy(
      conversat onControl = t et.conversat onControl,
      semant cCoreAnnotat ons = t et.esc rb rdEnt yAnnotat ons.map(_.ent yAnnotat ons)
    )
  }
}
