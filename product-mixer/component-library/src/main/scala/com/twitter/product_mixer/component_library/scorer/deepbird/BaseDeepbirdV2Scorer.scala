package com.tw ter.product_m xer.component_l brary.scorer.deepb rd

 mport com.tw ter.product_m xer.core.feature.datarecord.BaseDataRecordFeature
 mport com.tw ter.ml.pred ct on_serv ce.BatchPred ct onRequest
 mport com.tw ter.ml.pred ct on_serv ce.BatchPred ct onResponse
 mport com.tw ter.cortex.deepb rd.thr ftjava.{ModelSelector => TModelSelector}
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.scorer.common.ModelSelector
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.DataRecordConverter
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.DataRecordExtractor
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.FeaturesScope
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport scala.collect on.JavaConverters._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future

abstract class BaseDeepb rdV2Scorer[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  QueryFeatures <: BaseDataRecordFeature[Query, _],
  Cand dateFeatures <: BaseDataRecordFeature[Cand date, _],
  ResultFeatures <: BaseDataRecordFeature[Cand date, _]
](
  overr de val  dent f er: Scorer dent f er,
  model dSelector: ModelSelector[Query],
  queryFeatures: FeaturesScope[QueryFeatures],
  cand dateFeatures: FeaturesScope[Cand dateFeatures],
  resultFeatures: Set[ResultFeatures])
    extends Scorer[Query, Cand date] {

  pr vate val queryDataRecordConverter = new DataRecordConverter(queryFeatures)
  pr vate val cand dateDataRecordConverter = new DataRecordConverter(cand dateFeatures)
  pr vate val resultDataRecordExtractor = new DataRecordExtractor(resultFeatures)

  requ re(resultFeatures.nonEmpty, "Result features cannot be empty")
  overr de val features: Set[Feature[_, _]] = resultFeatures.as nstanceOf[Set[Feature[_, _]]]
  def getBatchPred ct ons(
    request: BatchPred ct onRequest,
    modelSelector: TModelSelector
  ): Future[BatchPred ct onResponse]

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]] = {
    // Convert all cand date feature maps to java datarecords t n to scala datarecords.
    val thr ftCand dateDataRecords = cand dates.map { cand date =>
      cand dateDataRecordConverter.toDataRecord(cand date.features)
    }

    val request = new BatchPred ct onRequest(thr ftCand dateDataRecords.asJava)

    // Convert t  query feature map to data record  f ava lable.
    query.features.foreach { featureMap =>
      request.setCommonFeatures(queryDataRecordConverter.toDataRecord(featureMap))
    }

    val modelSelector = model dSelector
      .apply(query).map {  d =>
        val selector = new TModelSelector()
        selector.set d( d)
        selector
      }.orNull

    St ch.callFuture(getBatchPred ct ons(request, modelSelector)).map { response =>
      val dataRecords = Opt on(response.pred ct ons).map(_.asScala).getOrElse(Seq.empty)
      bu ldResults(cand dates, dataRecords)
    }
  }

  pr vate def bu ldResults(
    cand dates: Seq[Cand dateW hFeatures[Cand date]],
    dataRecords: Seq[DataRecord]
  ): Seq[FeatureMap] = {
     f (dataRecords.s ze != cand dates.s ze) {
      throw P pel neFa lure( llegalStateFa lure, "Result S ze m smatc d cand dates s ze")
    }

    dataRecords.map { resultDataRecord =>
      resultDataRecordExtractor.fromDataRecord(resultDataRecord)
    }
  }
}
