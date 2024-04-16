package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1

 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecordAdapter
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreRequest
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1QueryEnt y
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1Response
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1ResponseFeature
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.FeatureHydrat onFa led
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.logg ng.Logg ng

tra  FeatureStoreV1QueryFeatureHydrator[Query <: P pel neQuery]
    extends BaseQueryFeatureHydrator[
      Query,
      BaseFeatureStoreV1QueryFeature[Query, _ <: Ent y d, _]
    ]
    w h Logg ng {

  def features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: Ent y d, _]]

  def cl entBu lder: FeatureStoreV1Dynam cCl entBu lder

  pr vate lazy val hydrat onConf g = FeatureStoreV1QueryFeatureHydrat onConf g(features)

  pr vate lazy val cl ent = cl entBu lder.bu ld(hydrat onConf g)

  pr vate lazy val datasetToFeatures =
    FeatureStoreDatasetErrorHandler.datasetToFeaturesMapp ng(features)

  pr vate lazy val dataRecordAdapter =
    Pred ct onRecordAdapter.oneToOne(hydrat onConf g.allBoundFeatures)

  pr vate lazy val featureContext = hydrat onConf g.allBoundFeatures.toFeatureContext

  overr de def hydrate(
    query: Query
  ): St ch[FeatureMap] = {
    // Dupl cate ent  es are expected across features, so de-dupe v a t  Set before convert ng to Seq
    val ent  es: Seq[FeatureStoreV1QueryEnt y[Query, _ <: Ent y d]] =
      features.map(_.ent y).toSeq
    val ent y ds: Seq[Ent yW h d[_ <: Ent y d]] = ent  es.map(_.ent yW h d(query))

    val featureStoreRequest = Seq(FeatureStoreRequest(ent y ds = ent y ds))

    val featureMap = cl ent(featureStoreRequest, query).map { pred ct onRecords =>
      // Should not happen as FSv1  s guaranteed to return a pred ct on record per feature store request
      val pred ct onRecord = pred ct onRecords. adOpt on.getOrElse {
        throw P pel neFa lure(
          FeatureHydrat onFa led,
          "Unexpected empty response from Feature Store V1 wh le hydrat ng query features")
      }

      val datasetErrors = pred ct onRecord.getDatasetHydrat onErrors
      val errorMap =
        FeatureStoreDatasetErrorHandler.featureToHydrat onErrors(datasetToFeatures, datasetErrors)

       f (errorMap.nonEmpty) {
        logger.debug(() => s"$ dent f er hydrat on errors for query: $errorMap")
      }

      val r chDataRecord =
        SR chDataRecord(dataRecordAdapter.adaptToDataRecord(pred ct onRecord), featureContext)
      val featureStoreResponse =
        FeatureStoreV1Response(r chDataRecord, errorMap)
      FeatureMapBu lder().add(FeatureStoreV1ResponseFeature, featureStoreResponse).bu ld()
    }

    St ch.callFuture(featureMap)
  }
}
