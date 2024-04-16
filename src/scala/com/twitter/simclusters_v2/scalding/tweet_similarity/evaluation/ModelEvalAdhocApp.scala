package com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.evaluat on

 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Da lySuff xFeatureS ce
 mport com.tw ter.ml.ap .DataSetP pe
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.s mclusters_v2.t et_s m lar y.T etS m lar yFeatures
 mport com.tw ter.twml.runt  .scald ng.TensorflowBatchPred ctor
 mport java.ut l.T  Zone

/**
 * Scald ng execut on app for scor ng a Dataset aga nst an exported Tensorflow model.

** Argu nts:
 * dataset_path - Path for t  dataset on hdfs
 * date - Date for t  dataset paths, requ red  f Da ly dataset.
 * model_s ce - Path of t  exported model on HDFS. Must start w h hdfs:// sc  .
 * output_path - Path of t  output result f le

scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/t et_s m lar y:model_eval-adhoc \
--user cassowary \
--subm ter hadoopnest2.atla.tw ter.com \
--ma n-class com.tw ter.s mclusters_v2.scald ng.t et_s m lar y.ModelEvalAdhocApp -- \
--date 2020-02-19 \
--dataset_path /user/cassowary/adhoc/tra n ng_data/2020-02-19_class_balanced/test \
--model_path hdfs:///user/cassowary/t et_s m lar y/2020-02-07-15-20-15/exported_models/1581253926 \
--output_path /user/cassowary/adhoc/tra n ng_data/2020-02-19_class_balanced/test/pred ct on_v1
 **/
object ModelEvalAdhocApp extends Tw terExecut onApp {
   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

  /**
   * Get pred ctor for t  g ven model path
   * @param modelNa  na  of t  model
   * @param modelS ce path of t  exported model on HDFS. Must start w h hdfs:// sc  .
   * @return
   */
  def getPred ctor(modelNa : Str ng, modelS ce: Str ng): TensorflowBatchPred ctor = {
    val default nputNode = "request:0"
    val defaultOutputNode = "response:0"
    TensorflowBatchPred ctor(modelNa , modelS ce, default nputNode, defaultOutputNode)
  }

  /**
   * G ven  nput p pe and pred ctor, return t  pred ct ons  n TypedP pe
   * @param dataset dataset for pred ct on
   * @param batchPred ctor pred ctor
   * @return
   */
  def getPred ct on(
    dataset: DataSetP pe,
    batchPred ctor: TensorflowBatchPred ctor
  ): TypedP pe[(Long, Long, Boolean, Double, Double)] = {
    val featureContext = dataset.featureContext
    val pred ct onFeature = new Cont nuous("output")

    batchPred ctor
      .pred ct(dataset.records)
      .map {
        case (or g nalDataRecord, pred ctedDataRecord) =>
          val pred ct on = new R chDataRecord(pred ctedDataRecord, featureContext)
            .getFeatureValue(pred ct onFeature).toDouble
          val r chDataRecord = new R chDataRecord(or g nalDataRecord, featureContext)
          (
            r chDataRecord.getFeatureValue(T etS m lar yFeatures.QueryT et d).toLong,
            r chDataRecord.getFeatureValue(T etS m lar yFeatures.Cand dateT et d).toLong,
            r chDataRecord.getFeatureValue(T etS m lar yFeatures.Label).booleanValue,
            r chDataRecord.getFeatureValue(T etS m lar yFeatures.Cos neS m lar y).toDouble,
            pred ct on
          )
      }
  }

  overr de def job: Execut on[Un ] =
    Execut on.w h d {  mpl c  un que d =>
      Execut on.w hArgs { args: Args =>
         mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))
        val outputPath: Str ng = args("output_path")
        val dataset: DataSetP pe = Da lySuff xFeatureS ce(args("dataset_path")).read
        val modelS ce: Str ng = args("model_path")
        val modelNa : Str ng = "t et_s m lar y"

        getPred ct on(dataset, getPred ctor(modelNa , modelS ce))
          .wr eExecut on(TypedTsv[(Long, Long, Boolean, Double, Double)](outputPath))
      }
    }
}
