package com.twitter.simclusters_v2.scalding.tweet_similarity.evaluation

import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.DailySuffixFeatureSource
import com.twitter.ml.api.DataSetPipe
import com.twitter.ml.api.RichDataRecord
import com.twitter.scalding._
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.simclusters_v2.tweet_similarity.TweetSimilarityFeatures
import com.twitter.twml.runtime.scalding.TensorflowBatchPredictor
import java.util.TimeZone

/**
 * Scalding execution app for scoring a Dataset against an exported Tensorflow model.

** Arguments:
 * dataset_path - Path for the dataset on hdfs
 * date - Date for the dataset paths, required if Daily dataset.
 * model_source - Path of the exported model on HDFS. Must start with hdfs:// scheme.
 * output_path - Path of the output result file

scalding remote run --target src/scala/com/twitter/simclusters_v2/scalding/tweet_similarity:model_eval-adhoc \
--user cassowary \
--submitter hadoopnest2.atla.twitter.com \
--main-class com.twitter.simclusters_v2.scalding.tweet_similarity.ModelEvalAdhocApp -- \
--date 2020-02-19 \
--dataset_path /user/cassowary/adhoc/training_data/2020-02-19_class_balanced/test \
--model_path hdfs:///user/cassowary/tweet_similarity/2020-02-07-15-20-15/exported_models/1581253926 \
--output_path /user/cassowary/adhoc/training_data/2020-02-19_class_balanced/test/prediction_v1
 **/
object ModelEvalAdhocApp extends TwitterExecutionApp {
  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  /**
   * Get predictor for the given model path
   * @param modelName name of the model
   * @param modelSource path of the exported model on HDFS. Must start with hdfs:// scheme.
   * @return
   */
  def getPredictor(modelName: String, modelSource: String): TensorflowBatchPredictor = {
    val defaultInputNode = "request:0"
    val defaultOutputNode = "response:0"
    TensorflowBatchPredictor(modelName, modelSource, defaultInputNode, defaultOutputNode)
  }

  /**
   * Given input pipe and predictor, return the predictions in TypedPipe
   * @param dataset dataset for prediction
   * @param batchPredictor predictor
   * @return
   */
  def getPrediction(
    dataset: DataSetPipe,
    batchPredictor: TensorflowBatchPredictor
  ): TypedPipe[(Long, Long, Boolean, Double, Double)] = {
    val featureContext = dataset.featureContext
    val predictionFeature = new Continuous("output")

    batchPredictor
      .predict(dataset.records)
      .map {
        case (originalDataRecord, predictedDataRecord) =>
          val prediction = new RichDataRecord(predictedDataRecord, featureContext)
            .getFeatureValue(predictionFeature).toDouble
          val richDataRecord = new RichDataRecord(originalDataRecord, featureContext)
          (
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.QueryTweetId).toLong,
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.CandidateTweetId).toLong,
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.Label).booleanValue,
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.CosineSimilarity).toDouble,
            prediction
          )
      }
  }

  override def job: Execution[Unit] =
    Execution.withId { implicit uniqueId =>
      Execution.withArgs { args: Args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("date"))
        val outputPath: String = args("output_path")
        val dataset: DataSetPipe = DailySuffixFeatureSource(args("dataset_path")).read
        val modelSource: String = args("model_path")
        val modelName: String = "tweet_similarity"

        getPrediction(dataset, getPredictor(modelName, modelSource))
          .writeExecution(TypedTsv[(Long, Long, Boolean, Double, Double)](outputPath))
      }
    }
}
