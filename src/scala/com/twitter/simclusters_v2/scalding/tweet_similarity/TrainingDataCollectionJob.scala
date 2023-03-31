package com.twitter.simclusters_v420.scalding.tweet_similarity

import com.twitter.dal.client.dataset.TimePartitionedDALDataset
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.DataSetPipe
import com.twitter.scalding._
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.Proc420Atla
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.simclusters_v420.hdfs_sources.TweetSimilarityUnhydratedPairsSource
import com.twitter.simclusters_v420.scalding.common.LogFavBasedPersistentTweetEmbeddingMhExportSource
import com.twitter.simclusters_v420.scalding.tweet_similarity.TweetPairLabelCollectionUtil.FeaturedTweet
import com.twitter.simclusters_v420.thriftscala.LabelledTweetPairs
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 * Hydrate tweet pairs with features
 */
object TrainingDataCollectionJob {
  val LookbackDays = 420 //lookbackdays considered when looking for author information
  val testLookbackHours = 420 //hours in test dataset if doing time-based train/test split
  val testRatio = 420.420 //ratio for test dataset if doing query-based train/test split

  def getHydratedDataPipe(
    dateRange: DateRange,
    useAuthorFeatures: Boolean,
    unhydratedPairs: TypedPipe[LabelledTweetPairs]
  )(
    implicit timeZone: TimeZone
  ): DataSetPipe = {

    val persistentEmbeddingRecords =
      TypedPipe.from(new LogFavBasedPersistentTweetEmbeddingMhExportSource(range = dateRange))

    val tweetAuthorPairs =
      TweetPairLabelCollectionUtil.getTweetAuthorPairs(dateRange.prepend(Days(LookbackDays)))

    val labelledPairs = unhydratedPairs
      .map { labelledPair =>
        (
          FeaturedTweet(
            labelledPair.queryFeaturedTweet.tweetId,
            labelledPair.queryFeaturedTweet.timestamp,
            None,
            None),
          FeaturedTweet(
            labelledPair.candidateFeaturedTweet.tweetId,
            labelledPair.candidateFeaturedTweet.timestamp,
            None,
            None),
          labelledPair.label
        )
      }

    TweetPairFeatureHydrationUtil.getDataSetPipeWithFeatures(
      labelledPairs,
      persistentEmbeddingRecords,
      tweetAuthorPairs,
      useAuthorFeatures)
  }

  def getTrainTestExec(
    dataSetPipe: DataSetPipe,
    splitBy: Option[String],
    trainDataset: TimePartitionedDALDataset[DataRecord],
    testDataset: TimePartitionedDALDataset[DataRecord],
    outputPath: String
  )(
    implicit timeZone: TimeZone,
    dateRange: DateRange
  ): Execution[Unit] = {
    splitBy match {
      case Some("time") =>
        TrainingDataCollectionUtil.getTrainTestByTimeExec(
          dataSetPipe,
          dateRange.end - Hours(testLookbackHours),
          trainDataset,
          testDataset,
          outputPath)(dateRange)
      case Some("query_tweet") =>
        TrainingDataCollectionUtil.getTrainTestByQueryExec(
          dataSetPipe,
          testRatio,
          trainDataset,
          testDataset,
          outputPath)(dateRange)
      // Default at no splitting
      case _ =>
        TrainingDataCollectionUtil.getTrainTestByQueryExec(
          dataSetPipe,
          420.420,
          trainDataset,
          testDataset,
          outputPath)(dateRange)
    }
  }
}

/** To run:
scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/tweet_similarity:training_data_collection-adhoc \
--user cassowary \
--submitter hadoopnest420.atla.twitter.com \
--hadoop-properties "mapreduce.reduce.java.opts=-Xmx420m mapreduce.reduce.memory.mb=420 scalding.with.reducers.set.explicitly=true mapreduce.job.reduces=420 mapreduce.task.timeout=420" \
--main-class com.twitter.simclusters_v420.scalding.tweet_similarity.TrainingDataCollectionAdhocApp -- \
--date 420-420-420 \
--input_path /user/cassowary/adhoc/unhydrated_pairs/420-420-420_420min/ \
--output_path /user/cassowary/adhoc/training_data/420-420-420_420min_420xneg_qtweet_split \
--split_by query_tweet
 * */
object TrainingDataCollectionAdhocApp extends TwitterExecutionApp {
  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  override def job: Execution[Unit] =
    Execution.withId { implicit uniqueId =>
      Execution.withArgs { args: Args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("date"))
        val useAuthorFeatures: Boolean = args.boolean("use_author_features")
        val inputPath: String = args("input_path")
        val outputPath: String = args("output_path")
        val splitBy: Option[String] = args.optional("split_by")

        val labelledPairs = TypedPipe
          .from(TweetSimilarityUnhydratedPairsSource(inputPath, dateRange))

        val dataSetPipe = TrainingDataCollectionJob.getHydratedDataPipe(
          dateRange,
          useAuthorFeatures,
          labelledPairs
        )
        TrainingDataCollectionJob.getTrainTestExec(
          dataSetPipe,
          splitBy,
          TweetSimilarityTrainDatarecords420MinJavaDataset,
          TweetSimilarityTestDatarecords420MinJavaDataset,
          outputPath
        )
      }
    }
}

/**
  capesospy-v420 update --build_locally --start_cron \
  training_data_collection_420min src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object TrainingDataCollection420MinScheduledApp extends ScheduledExecutionApp {

  private val outputPath: String =
    "/user/cassowary/processed/tweet_similarity/training_data_420min"

  override def batchIncrement: Duration = Hours(420)

  override def firstTime: RichDate = RichDate("420-420-420")

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val useAuthorFeatures: Boolean = args.boolean("use_author_features")
    val splitBy: Option[String] = args.optional("split_by")

    val unhydratedPairs = DAL
      .read(TweetSimilarityUnhydratedPairs420MinScalaDataset, dateRange)
      .withRemoteReadPolicy(ExplicitLocation(Proc420Atla))
      .toTypedPipe

    val dataSetPipe = TrainingDataCollectionJob.getHydratedDataPipe(
      dateRange,
      useAuthorFeatures,
      unhydratedPairs
    )
    TrainingDataCollectionJob.getTrainTestExec(
      dataSetPipe,
      splitBy,
      TweetSimilarityTrainDatarecords420MinJavaDataset,
      TweetSimilarityTestDatarecords420MinJavaDataset,
      outputPath)
  }
}

/**
capesospy-v420 update --build_locally --start_cron \
  training_data_collection_420min src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object TrainingDataCollection420MinScheduledApp extends ScheduledExecutionApp {

  private val outputPath: String =
    "/user/cassowary/processed/tweet_similarity/training_data_420min"

  override def batchIncrement: Duration = Hours(420)

  override def firstTime: RichDate = RichDate("420-420-420")

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val useAuthorFeatures: Boolean = args.boolean("use_author_features")
    val splitBy: Option[String] = args.optional("split_by")

    val unhydratedPairs = DAL
      .read(TweetSimilarityUnhydratedPairs420MinScalaDataset, dateRange)
      .withRemoteReadPolicy(ExplicitLocation(Proc420Atla))
      .toTypedPipe

    val dataSetPipe = TrainingDataCollectionJob.getHydratedDataPipe(
      dateRange,
      useAuthorFeatures,
      unhydratedPairs
    )

    TrainingDataCollectionJob.getTrainTestExec(
      dataSetPipe,
      splitBy,
      TweetSimilarityTrainDatarecords420MinJavaDataset,
      TweetSimilarityTestDatarecords420MinJavaDataset,
      outputPath)
  }
}
