package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.twh n_embedd ngs

 mport com.tw ter.ml.ap .DataType
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.ap .ut l.ScalaToJavaDataRecordConvers ons
 mport com.tw ter.ml.ap .{thr ftscala => ml}
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase

sealed tra  Twh nEmbedd ngsAdapter extends T  l nesMutat ngAdapterBase[Opt on[ml.FloatTensor]] {
  def twh nEmbedd ngsFeature: Feature.Tensor

  overr de def getFeatureContext: FeatureContext = new FeatureContext(
    twh nEmbedd ngsFeature
  )

  overr de def setFeatures(
    embedd ng: Opt on[ml.FloatTensor],
    r chDataRecord: R chDataRecord
  ): Un  = {
    embedd ng.foreach { floatTensor =>
      r chDataRecord.setFeatureValue(
        twh nEmbedd ngsFeature,
        ScalaToJavaDataRecordConvers ons.scalaTensor2Java(
          ml.GeneralTensor
            .FloatTensor(floatTensor)))
    }
  }
}

object Twh nEmbedd ngsFeatures {
  val Twh nAuthorFollowEmbedd ngsFeature: Feature.Tensor = new Feature.Tensor(
    "or g nal_author.twh n.tw_h _n.author_follow_as_float_tensor",
    DataType.FLOAT
  )

  val Twh nUserEngage ntEmbedd ngsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twh n.tw_h _n.user_engage nt_as_float_tensor",
    DataType.FLOAT
  )

  val Twh nUserFollowEmbedd ngsFeature: Feature.Tensor = new Feature.Tensor(
    "user.twh n.tw_h _n.user_follow_as_float_tensor",
    DataType.FLOAT
  )
}

object Twh nAuthorFollowEmbedd ngsAdapter extends Twh nEmbedd ngsAdapter {
  overr de val twh nEmbedd ngsFeature: Feature.Tensor =
    Twh nEmbedd ngsFeatures.Twh nAuthorFollowEmbedd ngsFeature

  overr de val commonFeatures: Set[Feature[_]] = Set.empty
}

object Twh nUserEngage ntEmbedd ngsAdapter extends Twh nEmbedd ngsAdapter {
  overr de val twh nEmbedd ngsFeature: Feature.Tensor =
    Twh nEmbedd ngsFeatures.Twh nUserEngage ntEmbedd ngsFeature

  overr de val commonFeatures: Set[Feature[_]] = Set(twh nEmbedd ngsFeature)
}

object Twh nUserFollowEmbedd ngsAdapter extends Twh nEmbedd ngsAdapter {
  overr de val twh nEmbedd ngsFeature: Feature.Tensor =
    Twh nEmbedd ngsFeatures.Twh nUserFollowEmbedd ngsFeature

  overr de val commonFeatures: Set[Feature[_]] = Set(twh nEmbedd ngsFeature)
}
