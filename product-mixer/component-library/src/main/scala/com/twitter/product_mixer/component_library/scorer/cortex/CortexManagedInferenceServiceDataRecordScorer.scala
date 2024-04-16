package com.tw ter.product_m xer.component_l brary.scorer.cortex

 mport com.google.protobuf.ByteStr ng
 mport com.tw ter.ml.pred ct on_serv ce.BatchPred ct onRequest
 mport com.tw ter.ml.pred ct on_serv ce.BatchPred ct onResponse
 mport com.tw ter.product_m xer.component_l brary.scorer.common.ManagedModelCl ent
 mport com.tw ter.product_m xer.component_l brary.scorer.common.ModelSelector
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.datarecord.BaseDataRecordFeature
 mport com.tw ter.product_m xer.core.feature.datarecord.TensorDataRecordCompat ble
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.DataRecordConverter
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.DataRecordExtractor
 mport com.tw ter.product_m xer.core.feature.featuremap.datarecord.FeaturesScope
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport  nference.GrpcServ ce
 mport  nference.GrpcServ ce.Model nferRequest
 mport  nference.GrpcServ ce.Model nferResponse
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch
 mport org.apac .thr ft.TDeser al zer
 mport org.apac .thr ft.TSer al zer
 mport scala.collect on.JavaConverters._

pr vate[cortex] class CortexManagedDataRecordScorer[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  QueryFeatures <: BaseDataRecordFeature[Query, _],
  Cand dateFeatures <: BaseDataRecordFeature[Cand date, _],
  ResultFeatures <: BaseDataRecordFeature[Cand date, _] w h TensorDataRecordCompat ble[_]
](
  overr de val  dent f er: Scorer dent f er,
  modelS gnature: Str ng,
  modelSelector: ModelSelector[Query],
  modelCl ent: ManagedModelCl ent,
  queryFeatures: FeaturesScope[QueryFeatures],
  cand dateFeatures: FeaturesScope[Cand dateFeatures],
  resultFeatures: Set[ResultFeatures])
    extends Scorer[Query, Cand date] {

  requ re(resultFeatures.nonEmpty, "Result features cannot be empty")
  overr de val features: Set[Feature[_, _]] = resultFeatures.as nstanceOf[Set[Feature[_, _]]]

  pr vate val queryDataRecordAdapter = new DataRecordConverter(queryFeatures)
  pr vate val cand datesDataRecordAdapter = new DataRecordConverter(cand dateFeatures)
  pr vate val resultDataRecordExtractor = new DataRecordExtractor(resultFeatures)

  pr vate val localTSer al zer = new ThreadLocal[TSer al zer] {
    overr de protected def  n  alValue: TSer al zer = new TSer al zer()
  }

  pr vate val localTDeser al zer = new ThreadLocal[TDeser al zer] {
    overr de protected def  n  alValue: TDeser al zer = new TDeser al zer()
  }

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]] = {
    modelCl ent.score(bu ldRequest(query, cand dates)).map(bu ldResponse(cand dates, _))
  }

  /**
   * Takes cand dates to be scored and converts   to a Model nferRequest that can be passed to t 
   * managed ML serv ce
   */
  pr vate def bu ldRequest(
    query: Query,
    scorerCand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Model nferRequest = {
    // Convert t  feature maps to thr ft data records and construct thr ft request.
    val thr ftDataRecords = scorerCand dates.map { cand date =>
      cand datesDataRecordAdapter.toDataRecord(cand date.features)
    }
    val batchRequest = new BatchPred ct onRequest(thr ftDataRecords.asJava)
    query.features.foreach { featureMap =>
      batchRequest.setCommonFeatures(queryDataRecordAdapter.toDataRecord(featureMap))
    }
    val ser al zedBatchRequest = localTSer al zer.get().ser al ze(batchRequest)

    // Bu ld Tensor Request
    val requestBu lder = Model nferRequest
      .newBu lder()

    modelSelector.apply(query).foreach { modelNa  =>
      requestBu lder.setModelNa (modelNa ) // model na   n t  model conf g
    }

    val  nputTensorBu lder = Model nferRequest. nfer nputTensor
      .newBu lder()
      .setNa ("request")
      .setDatatype("U NT8")
      .addShape(ser al zedBatchRequest.length)

    val  nferPara ter = GrpcServ ce. nferPara ter
      .newBu lder()
      .setStr ngParam(modelS gnature) // s gnature of exported tf funct on
      .bu ld()

    requestBu lder
      .add nputs( nputTensorBu lder)
      .addRaw nputContents(ByteStr ng.copyFrom(ser al zedBatchRequest))
      .putPara ters("s gnature_na ",  nferPara ter)
      .bu ld()
  }

  pr vate def bu ldResponse(
    scorerCand dates: Seq[Cand dateW hFeatures[Cand date]],
    response: Model nferResponse
  ): Seq[FeatureMap] = {

    val responseByteStr ng =  f (response.getRawOutputContentsL st. sEmpty()) {
      throw P pel neFa lure(
         llegalStateFa lure,
        "Model  nference response has empty raw outputContents")
    } else {
      response.getRawOutputContents(0)
    }
    val batchPred ct onResponse: BatchPred ct onResponse = new BatchPred ct onResponse()
    localTDeser al zer.get().deser al ze(batchPred ct onResponse, responseByteStr ng.toByteArray)

    // get t  pred ct on values from t  batch pred ct on response
    val resultScoreMaps =
      batchPred ct onResponse.pred ct ons.asScala.map(resultDataRecordExtractor.fromDataRecord)

     f (resultScoreMaps.s ze != scorerCand dates.s ze) {
      throw P pel neFa lure( llegalStateFa lure, "Result S ze m smatc d cand dates s ze")
    }

    resultScoreMaps
  }
}
