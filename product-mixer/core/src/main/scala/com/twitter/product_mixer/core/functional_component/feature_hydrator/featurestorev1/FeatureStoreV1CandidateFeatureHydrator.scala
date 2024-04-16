package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1

 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecordAdapter
 mport com.tw ter.ml.featurestore.l b.ent y.Ent yW h d
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreRequest
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.feature.featurestorev1.FeatureStoreV1Cand dateEnt y
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1Response
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1ResponseFeature
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseBulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.FeatureHydrat onFa led
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.logg ng.Logg ng

tra  FeatureStoreV1Cand dateFeatureHydrator[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any]]
    extends BaseBulkCand dateFeatureHydrator[
      Query,
      Cand date,
      BaseFeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, _]
    ]
    w h Logg ng {

  overr de def features: Set[BaseFeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, _]]

  def cl entBu lder: FeatureStoreV1Dynam cCl entBu lder

  pr vate lazy val hydrat onConf g = FeatureStoreV1Cand dateFeatureHydrat onConf g(features)

  pr vate lazy val cl ent = cl entBu lder.bu ld(hydrat onConf g)

  pr vate lazy val datasetToFeatures =
    FeatureStoreDatasetErrorHandler.datasetToFeaturesMapp ng(features)

  pr vate lazy val dataRecordAdapter =
    Pred ct onRecordAdapter.oneToOne(hydrat onConf g.allBoundFeatures)

  pr vate lazy val featureContext = hydrat onConf g.allBoundFeatures.toFeatureContext

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]] = {
    // Dupl cate ent  es are expected across features, so de-dupe v a t  Set before convert ng to Seq
    val ent  es: Seq[FeatureStoreV1Cand dateEnt y[Query, Cand date, _ <: Ent y d]] =
      features.map(_.ent y).toSeq

    val featureStoreRequests = cand dates.map { cand date =>
      val cand dateEnt y ds: Seq[Ent yW h d[_ <: Ent y d]] =
        ent  es.map(_.ent yW h d(query, cand date.cand date, cand date.features))

      FeatureStoreRequest(ent y ds = cand dateEnt y ds)
    }

    val featureMaps = cl ent(featureStoreRequests, query).map { pred ct onRecords =>
       f (pred ct onRecords.s ze == cand dates.s ze)
        pred ct onRecords
          .z p(cand dates).map {
            case (pred ct onRecord, cand date) =>
              val datasetErrors = pred ct onRecord.getDatasetHydrat onErrors
              val errorMap =
                FeatureStoreDatasetErrorHandler.featureToHydrat onErrors(
                  datasetToFeatures,
                  datasetErrors)

               f (errorMap.nonEmpty) {
                logger.debug(() =>
                  s"$ dent f er hydrat on errors for cand date ${cand date.cand date. d}: $errorMap")
              }
              val dataRecord =
                new SR chDataRecord(
                  dataRecordAdapter.adaptToDataRecord(pred ct onRecord),
                  featureContext)
              val featureStoreResponse =
                FeatureStoreV1Response(dataRecord, errorMap)
              FeatureMapBu lder()
                .add(FeatureStoreV1ResponseFeature, featureStoreResponse).bu ld()
          }
      else
        // Should not happen as FSv1  s guaranteed to return a pred ct on record per feature store request
        throw P pel neFa lure(
          FeatureHydrat onFa led,
          "Unexpected response length from Feature Store V1 wh le hydrat ng cand date features")
    }

    St ch.callFuture(featureMaps)
  }
}
