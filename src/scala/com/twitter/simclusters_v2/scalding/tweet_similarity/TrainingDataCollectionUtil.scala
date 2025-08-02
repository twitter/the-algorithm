package com.twitter.simclusters_v2.scalding.tweet_similarity

import com.twitter.dal.client.dataset.TimePartitionedDALDataset
import com.twitter.ml.api.util.FDsl._
import com.twitter.ml.api.{DataRecord, DataSetPipe}
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite.D
import com.twitter.scalding_internal.dalv2.dataset.DALWrite._
import com.twitter.simclusters_v2.tweet_similarity.TweetSimilarityFeatures
import com.twitter.util.Time
import java.util.Random

/**
 * Collect training data for supervised tweet similarity
 */
object TrainingDataCollectionUtil {

  /**
   * Split dataset into train and test based on time
   * @param dataset: input dataset
   * @param testStartDate: samples before/after testStartDate will be used for training/testing
   * @return (train dataset, test dataset)
   */
  def splitRecordsByTime(
    dataset: DataSetPipe,
    testStartDate: RichDate
  ): (DataSetPipe, DataSetPipe) = {
    val (leftRecords, rightRecords) = dataset.records.partition { record =>
      // record will be in training dataset when both tweets were engaged before testStartDate
      (record.getFeatureValue(
        TweetSimilarityFeatures.QueryTweetTimestamp) < testStartDate.timestamp) &
        (record.getFeatureValue(
          TweetSimilarityFeatures.CandidateTweetTimestamp) < testStartDate.timestamp)
    }
    (
      DataSetPipe(leftRecords, dataset.featureContetwittert),
      DataSetPipe(rightRecords, dataset.featureContetwittert))
  }

  /**
   * Split dataset into train and test randomly based on query
   * @param dataset: input dataset
   * @param testRatio: ratio for test
   * @return (train dataset, test dataset)
   */
  def splitRecordsByQuery(dataset: DataSetPipe, testRatio: Double): (DataSetPipe, DataSetPipe) = {
    val queryToRand = dataset.records
      .map { record => record.getFeatureValue(TweetSimilarityFeatures.QueryTweetId) }
      .distinct
      .map { queryTweet => queryTweet -> new Random(Time.now.inMilliseconds).netwittertDouble() }
      .forceToDisk

    val (trainRecords, testRecords) = dataset.records
      .groupBy { record => record.getFeatureValue(TweetSimilarityFeatures.QueryTweetId) }
      .join(queryToRand)
      .values
      .partition {
        case (_, random) => random > testRatio
      }

    (
      DataSetPipe(trainRecords.map { case (record, _) => record }, dataset.featureContetwittert),
      DataSetPipe(testRecords.map { case (record, _) => record }, dataset.featureContetwittert))
  }

  /**
   * Get the write etwitterec for train and test datasets
   * @param dataset: input dataset
   * @param testStartDate: samples before/after testStartDate will be used for training/testing
   * @param outputPath: output path for the train/test datasets
   * @return etwitterecution of the the writing etwitterec
   */
  def getTrainTestByTimeEtwitterec(
    dataset: DataSetPipe,
    testStartDate: RichDate,
    trainDataset: TimePartitionedDALDataset[DataRecord],
    testDataset: TimePartitionedDALDataset[DataRecord],
    outputPath: String
  )(
    implicit dateRange: DateRange
  ): Etwitterecution[Unit] = {
    val (trainDataSet, testDataSet) = splitRecordsByTime(dataset, testStartDate)
    val trainEtwitterecution: Etwitterecution[Unit] = trainDataSet
      .writeDALEtwitterecution(trainDataset, D.Daily, D.Suffitwitter(s"$outputPath/train"), D.EBLzo())
    val trainStatsEtwitterecution: Etwitterecution[Unit] =
      getStatsEtwitterec(trainDataSet, s"$outputPath/train_stats")
    val testEtwitterecution: Etwitterecution[Unit] = testDataSet
      .writeDALEtwitterecution(testDataset, D.Daily, D.Suffitwitter(s"$outputPath/test"), D.EBLzo())
    val testStatsEtwitterecution: Etwitterecution[Unit] = getStatsEtwitterec(testDataSet, s"$outputPath/test_stats")
    Etwitterecution.zip(trainEtwitterecution, trainStatsEtwitterecution, testEtwitterecution, testStatsEtwitterecution).unit
  }

  /**
   * Get the write etwitterec for train and test datasets
   * @param dataset: input dataset
   * @param testRatio: samples before/after testStartDate will be used for training/testing
   * @param outputPath: output path for the train/test datasets
   * @return etwitterecution of the the writing etwitterec
   */
  def getTrainTestByQueryEtwitterec(
    dataset: DataSetPipe,
    testRatio: Double,
    trainDataset: TimePartitionedDALDataset[DataRecord],
    testDataset: TimePartitionedDALDataset[DataRecord],
    outputPath: String
  )(
    implicit dateRange: DateRange
  ): Etwitterecution[Unit] = {
    val (trainDataSet, testDataSet) = splitRecordsByQuery(dataset, testRatio)
    val trainEtwitterecution: Etwitterecution[Unit] = trainDataSet
      .writeDALEtwitterecution(trainDataset, D.Daily, D.Suffitwitter(s"$outputPath/train"), D.EBLzo())
    val trainStatsEtwitterecution: Etwitterecution[Unit] =
      getStatsEtwitterec(trainDataSet, s"$outputPath/train_stats")
    val testEtwitterecution: Etwitterecution[Unit] = testDataSet
      .writeDALEtwitterecution(testDataset, D.Daily, D.Suffitwitter(s"$outputPath/test"), D.EBLzo())
    val testStatsEtwitterecution: Etwitterecution[Unit] = getStatsEtwitterec(testDataSet, s"$outputPath/test_stats")
    Etwitterecution.zip(trainEtwitterecution, trainStatsEtwitterecution, testEtwitterecution, testStatsEtwitterecution).unit
  }

  /**
   * Get the etwitterec for reporting dataset stats
   * @param dataset: dataset of interest
   * @param outputPath: path for outputting the stats
   * @return etwitterec
   */
  def getStatsEtwitterec(dataset: DataSetPipe, outputPath: String): Etwitterecution[Unit] = {
    dataset.records
      .map { rec =>
        if (TweetSimilarityFeatures.isCoengaged(rec))
          "total_positive_records" -> 1L
        else
          "total_negative_records" -> 1L
      }
      .sumByKey
      .shard(1)
      .writeEtwitterecution(TypedTsv(outputPath))
  }
}
