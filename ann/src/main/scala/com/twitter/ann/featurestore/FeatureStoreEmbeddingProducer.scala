package com.tw ter.ann.featurestore

 mport com.tw ter.ann.common.Embedd ngProducer
 mport com.tw ter.f nagle.stats.{ n moryStatsRece ver, StatsRece ver}
 mport com.tw ter.ml.ap .embedd ng.{Embedd ng, Embedd ngSerDe}
 mport com.tw ter.ml.ap .thr ftscala
 mport com.tw ter.ml.ap .thr ftscala.{Embedd ng => TEmbedd ng}
 mport com.tw ter.ml.featurestore.l b.dataset.onl ne.Vers onedOnl neAccessDataset
 mport com.tw ter.ml.featurestore.l b.{Ent y d, RawFloatTensor}
 mport com.tw ter.ml.featurestore.l b.dataset.DatasetParams
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.feature.{BoundFeature, BoundFeatureSet}
 mport com.tw ter.ml.featurestore.l b.onl ne.{FeatureStoreCl ent, FeatureStoreRequest}
 mport com.tw ter.ml.featurestore.l b.params.FeatureStoreParams
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.opcontext.Attr but on
 mport com.tw ter.strato.cl ent.Cl ent

object FeatureStoreEmbedd ngProducer {
  def apply[T <: Ent y d](
    dataset: Vers onedOnl neAccessDataset[T, TEmbedd ng],
    vers on: Long,
    boundFeature: BoundFeature[T, RawFloatTensor],
    cl ent: Cl ent,
    statsRece ver: StatsRece ver = new  n moryStatsRece ver,
    featureStoreAttr but ons: Seq[Attr but on] = Seq.empty
  ): Embedd ngProducer[Ent yW h d[T]] = {
    val featureStoreParams = FeatureStoreParams(
      perDataset = Map(
        dataset. d -> DatasetParams(datasetVers on = So (vers on))
      ),
      global = DatasetParams(attr but ons = featureStoreAttr but ons)
    )
    val featureStoreCl ent = FeatureStoreCl ent(
      BoundFeatureSet(boundFeature),
      cl ent,
      statsRece ver,
      featureStoreParams
    )
    new FeatureStoreEmbedd ngProducer(boundFeature, featureStoreCl ent)
  }
}

pr vate[featurestore] class FeatureStoreEmbedd ngProducer[T <: Ent y d](
  boundFeature: BoundFeature[T, RawFloatTensor],
  featureStoreCl ent: FeatureStoreCl ent)
    extends Embedd ngProducer[Ent yW h d[T]] {
  // Looks up embedd ng from onl ne feature store for an ent y.
  overr de def produceEmbedd ng( nput: Ent yW h d[T]): St ch[Opt on[Embedd ng[Float]]] = {
    val featureStoreRequest = FeatureStoreRequest(
      ent y ds = Seq( nput)
    )

    St ch.callFuture(featureStoreCl ent(featureStoreRequest).map { pred ct onRecord =>
      pred ct onRecord.getFeatureValue(boundFeature) match {
        case So (featureValue) => {
          val embedd ng = Embedd ngSerDe.floatEmbedd ngSerDe.fromThr ft(
            thr ftscala.Embedd ng(So (featureValue.value))
          )
          So (embedd ng)
        }
        case _ => None
      }
    })
  }
}
