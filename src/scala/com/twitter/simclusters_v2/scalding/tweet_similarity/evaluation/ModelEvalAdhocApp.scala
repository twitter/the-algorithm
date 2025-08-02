package com.twitter.simclusters_v2.scalding.tweet_similarity.evaluation

import com.twitter.ml.api.Feature.Continuous
import com.twitter.ml.api.DailySuffitwitterFeatureSource
import com.twitter.ml.api.DataSetPipe
import com.twitter.ml.api.RichDataRecord
import com.twitter.scalding._
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.simclusters_v2.tweet_similarity.TweetSimilarityFeatures
import com.twitter.twml.runtime.scalding.TensorflowBatchPredictor
import java.util.TimeZone

/**
 * Scalding etwitterecution app for scoring a Dataset against an etwitterported Tensorflow model.

** Arguments:
 * dataset_path - Path for the dataset on hdfs
 * date - Date for the dataset paths, required if Daily dataset.
 * model_source - Path of the etwitterported model on HDFS. Must start with hdfs:// scheme.
 * output_path - Path of the output result file

scalding remote run --target src/scala/com/twitter/simclusters_v2/scalding/tweet_similarity:model_eval-adhoc \
--user cassowary \
--submitter hadoopnest2.atla.twitter.com \
--main-class com.twitter.simclusters_v2.scalding.tweet_similarity.ModelEvalAdhocApp -- \
--date 2020-02-19 \
--dataset_path /user/cassowary/adhoc/training_data/2020-02-19_class_balanced/test \
--model_path hdfs:///user/cassowary/tweet_similarity/2020-02-07-15-20-15/etwitterported_models/1581253926 \
--output_path /user/cassowary/adhoc/training_data/2020-02-19_class_balanced/test/prediction_v1
 **/
object ModelEvalAdhocApp etwittertends TwitterEtwitterecutionApp {
  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  /**
   * Get predictor for the given model path
   * @param modelName name of the model
   * @param modelSource path of the etwitterported model on HDFS. Must start with hdfs:// scheme.
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
    val featureContetwittert = dataset.featureContetwittert
    val predictionFeature = new Continuous("output")

    batchPredictor
      .predict(dataset.records)
      .map {
        case (originalDataRecord, predictedDataRecord) =>
          val prediction = new RichDataRecord(predictedDataRecord, featureContetwittert)
            .getFeatureValue(predictionFeature).toDouble
          val richDataRecord = new RichDataRecord(originalDataRecord, featureContetwittert)
          (
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.QueryTweetId).toLong,
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.CandidateTweetId).toLong,
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.Label).booleanValue,
            richDataRecord.getFeatureValue(TweetSimilarityFeatures.CosineSimilarity).toDouble,
            prediction
          )
      }
  }

  override def job: Etwitterecution[Unit] =
    Etwitterecution.withId { implicit uniqueId =>
      Etwitterecution.withArgs { args: Args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("date"))
        val outputPath: String = args("output_path")
        val dataset: DataSetPipe = DailySuffitwitterFeatureSource(args("dataset_path")).read
        val modelSource: String = args("model_path")
        val modelName: String = "tweet_similarity"

        getPrediction(dataset, getPredictor(modelName, modelSource))
          .writeEtwitterecution(TypedTsv[(Long, Long, Boolean, Double, Double)](outputPath))
      }
    }
}
