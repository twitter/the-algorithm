package com.twitter.simclusters_v2.scalding.evaluation

import com.twitter.ml.api.constant.SharedFeatures.AUTHOR_ID
import com.twitter.ml.api.constant.SharedFeatures.TIMESTAMP
import com.twitter.ml.api.constant.SharedFeatures.TWEET_ID
import com.twitter.ml.api.constant.SharedFeatures.USER_ID
import com.twitter.ml.api.DailySuffitwitterFeatureSource
import com.twitter.ml.api.DataSetPipe
import com.twitter.ml.api.RichDataRecord
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecution
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecutionArgs
import com.twitter.scalding_internal.job.analytics_batch.BatchDescription
import com.twitter.scalding_internal.job.analytics_batch.BatchFirstTime
import com.twitter.scalding_internal.job.analytics_batch.BatchIncrement
import com.twitter.scalding_internal.job.analytics_batch.TwitterScheduledEtwitterecutionApp
import com.twitter.simclusters_v2.hdfs_sources.TimelineDataEtwittertractorFitwitteredPathSource
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.thriftscala.DisplayLocation
import com.twitter.simclusters_v2.thriftscala.ReferenceTweet
import com.twitter.simclusters_v2.thriftscala.ReferenceTweets
import com.twitter.simclusters_v2.thriftscala.TweetLabels
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures.IS_LINGER_IMPRESSION
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures.SOURCE_AUTHOR_ID
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures.SOURCE_TWEET_ID
import com.twitter.timelines.prediction.features.itl.ITLFeatures
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import java.util.TimeZone

/**
 * A scheduled version of the job to parse Timelines data for impressed and engaged tweets.
 capesospy-v2 update|create --start_cron tweet_evaluation_timelines_reference_batch src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc.yaml
 */
object ScheduledTimelinesDataEtwittertractionBatch etwittertends TwitterScheduledEtwitterecutionApp {

  val outputPath = "/user/cassowary/processed/tweet_evaluation_reference_set/timelines"

  private val firstTime: String = "2019-03-31"
  private implicit val tz: TimeZone = DateOps.UTC
  private implicit val parser: DateParser = DateParser.default
  private val batchIncrement: Duration = Days(1)

  private val etwitterecArgs = AnalyticsBatchEtwitterecutionArgs(
    batchDesc = BatchDescription(this.getClass.getName.replace("$", "")),
    firstTime = BatchFirstTime(RichDate(firstTime)),
    lastTime = None,
    batchIncrement = BatchIncrement(batchIncrement)
  )

  override def scheduledJob: Etwitterecution[Unit] = AnalyticsBatchEtwitterecution(etwitterecArgs) {
    implicit dateRange =>
      Etwitterecution.withId { implicit uniqueId =>
        Etwitterecution.withArgs { args =>
          val defaultSampleRate = 1.0
          val recaps =
            TimelinesEngagementDataEtwittertractor.readTimelinesRecapTweets(
              recapTweets =
                DailySuffitwitterFeatureSource(TimelinesEngagementDataEtwittertractor.RecapTweetHdfsPath).read,
              sampleRate = defaultSampleRate
            )(dateRange)
          val recTweets =
            TimelinesEngagementDataEtwittertractor.readTimelinesRecTweets(
              recTweets =
                DailySuffitwitterFeatureSource(TimelinesEngagementDataEtwittertractor.RecTweetHdfsPath).read,
              sampleRate = defaultSampleRate
            )(dateRange)

          (recaps ++ recTweets).writeDALSnapshotEtwitterecution(
            TweetEvaluationTimelinesReferenceSetScalaDataset,
            D.Daily,
            D.Suffitwitter(outputPath),
            D.EBLzo(),
            dateRange.end
          )
        }
      }
  }
}

/**
 * Ad-hoc version of the job to process a subset of the Timeline data, either to catch up with data
 * on a particular day, or to generate human readable data for debugging.
 ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/evaluation:tweet_evaluation_timelines_reference_adhoc

 oscar hdfs --screen --user cassowary --bundle tweet_evaluation_timelines_reference_adhoc \
 --tool com.twitter.simclusters_v2.scalding.evaluation.AdhocTimelinesDataEtwittertraction \
 -- --date 2018-11-15 --output_dir /user/cassowary/your_ldap/test_htl_data/recap --sample_rate 0.01 \
 --recap --rectweet --output_tsv
 */
object AdhocTimelinesDataEtwittertraction etwittertends TwitterEtwitterecutionApp {

  @Override
  def job: Etwitterecution[Unit] = {
    Etwitterecution.withArgs { args =>
      implicit val dateRange: DateRange =
        DateRange.parse(args.list("date"))(DateOps.UTC, DateParser.default)

      val outputDir = args("output_dir")
      val readRecTweet = args.boolean("rectweet")
      val readRecap = args.boolean("recap")
      val sampleRate = args.double("sample_rate")
      val useTsv = args.boolean("output_tsv")

      if (!readRecTweet && !readRecap) {
        throw new IllegalArgumentEtwitterception("Must read at least some data!")
      }
      val recTweets = if (readRecTweet) {
        println("RecTweets are included in the dataset")
        TimelinesEngagementDataEtwittertractor.readTimelinesRecTweets(
          recTweets =
            DailySuffitwitterFeatureSource(TimelinesEngagementDataEtwittertractor.RecTweetHdfsPath).read,
          sampleRate = sampleRate)(dateRange)
      } else {
        TypedPipe.empty
      }

      val recaps = if (readRecap) {
        println("Recaps are included in the dataset")
        TimelinesEngagementDataEtwittertractor.readTimelinesRecapTweets(
          recapTweets =
            DailySuffitwitterFeatureSource(TimelinesEngagementDataEtwittertractor.RecapTweetHdfsPath).read,
          sampleRate = sampleRate
        )(dateRange)
      } else {
        TypedPipe.empty
      }

      val referenceTweets = recaps ++ recTweets

      if (useTsv) {
        // Write in plain tetwittert in tsv format for human readability
        referenceTweets
          .map(t => (t.targetUserId, t.impressedTweets))
          .writeEtwitterecution(TypedTsv[(Long, Seq[ReferenceTweet])](outputDir))
      } else {
        // Write in compact thrift lzo format
        referenceTweets
          .writeEtwitterecution(TimelineDataEtwittertractorFitwitteredPathSource(outputDir))
      }
    }
  }
}

/**
 * Base class to provide functions to parse tweet engagement data from Home Timeline's data.
 * We are mainly interested in 2 tweet data sets from Home Timeline:
 * 1. Recap tweet: Tweets + RTs from user's follow graph. We are interested in out of network RTs.
 * 2. RecTweet: Out of network tweets not from user's follow graph.
 */
object TimelinesEngagementDataEtwittertractor {

  val RecapTweetHdfsPath = "/atla/proc2/user/timelines/processed/suggests/recap/data_records"
  val RecTweetHdfsPath = "/atla/proc2/user/timelines/processed/injections/rectweet/data_records"

  // Timelines name the same feature differently depending on the surface area (etwitter. recap vs rectweet).
  // For each data source we etwittertract the features with different feature names. Detail:
  def toRecapTweetLabels(record: RichDataRecord): TweetLabels = {
    val isClicked = record.getFeatureValue(RecapFeatures.IS_CLICKED)
    val isFav = record.getFeatureValue(RecapFeatures.IS_FAVORITED)
    val isRT = record.getFeatureValue(RecapFeatures.IS_RETWEETED)
    val isQuoted = record.getFeatureValue(RecapFeatures.IS_QUOTED)
    val isReplied = record.getFeatureValue(RecapFeatures.IS_REPLIED)
    TweetLabels(isClicked, isFav, isRT, isQuoted, isReplied)
  }

  def toRecTweetLabels(record: RichDataRecord): TweetLabels = {
    // Refer to ITLFeatures for more labels
    val isClicked = record.getFeatureValue(ITLFeatures.IS_CLICKED)
    val isFav = record.getFeatureValue(ITLFeatures.IS_FAVORITED)
    val isRT = record.getFeatureValue(ITLFeatures.IS_RETWEETED)
    val isQuoted = record.getFeatureValue(ITLFeatures.IS_QUOTED)
    val isReplied = record.getFeatureValue(ITLFeatures.IS_REPLIED)
    TweetLabels(isClicked, isFav, isRT, isQuoted, isReplied)
  }

  /**
   * Return Recap tweets, which are in-network tweets. Here we only filter for Retweets of tweets
   * that are outside the user's follow graph.
   */
  def readTimelinesRecapTweets(
    recapTweets: DataSetPipe,
    sampleRate: Double
  )(
    implicit dateRange: DateRange
  ): TypedPipe[ReferenceTweets] = {
    // recapTweets are in network tweets. We want to discover RTs of OON tweets.
    // For Retweets, we check IS_RETWEET and use SOURCE_TWEET_ID, and then check
    // PROBABLY_FROM_FOLLOWED_AUTHOR, which filters in network tweet from user's top 1000 follow graph.

    recapTweets.richRecords
      .sample(sampleRate)
      .filter { record =>
        val isInDateRange = dateRange.contains(RichDate(record.getFeatureValue(TIMESTAMP).toLong))
        val isLingeredImpression = record.getFeatureValue(IS_LINGER_IMPRESSION)
        val isInNetwork =
          record.getFeatureValue(RecapFeatures.PROBABLY_FROM_FOLLOWED_AUTHOR) // approtwitterimate
        val isRetweet = record.getFeatureValue(RecapFeatures.IS_RETWEET)
        isRetweet && (!isInNetwork) && isInDateRange && isLingeredImpression
      }
      .flatMap { record =>
        for {
          userId <- Option(record.getFeatureValue(USER_ID)).map(_.toLong)
          sourceTweetId <- Option(record.getFeatureValue(SOURCE_TWEET_ID)).map(
            _.toLong
          ) // source tweetId is the RT id
          sourceAuthorId <- Option(record.getFeatureValue(SOURCE_AUTHOR_ID)).map(_.toLong)
          timestamp <- Option(record.getFeatureValue(TIMESTAMP)).map(_.toLong)
          labels = toRecapTweetLabels(record)
        } yield {
          (
            userId,
            Seq(
              ReferenceTweet(
                sourceTweetId,
                sourceAuthorId,
                timestamp,
                DisplayLocation.TimelinesRecap,
                labels))
          )
        }
      }
      .sumByKey
      .map { case (uid, tweetSeq) => ReferenceTweets(uid, tweetSeq) }
  }

  /**
   * Return RecTweets, which are out of network tweets served in the Timeline.
   */
  def readTimelinesRecTweets(
    recTweets: DataSetPipe,
    sampleRate: Double
  )(
    implicit dateRange: DateRange
  ): TypedPipe[ReferenceTweets] = {
    // recTweets contain strictly out of network injection tweets

    recTweets.richRecords
      .sample(sampleRate)
      .filter { record =>
        val isInDateRange = dateRange.contains(RichDate(record.getFeatureValue(TIMESTAMP).toLong))
        val isLingeredImpression = record.getFeatureValue(IS_LINGER_IMPRESSION)

        isInDateRange && isLingeredImpression
      }
      .flatMap { record =>
        for {
          userId <- Option(record.getFeatureValue(USER_ID)).map(_.toLong)
          tweetId <- Option(record.getFeatureValue(TWEET_ID)).map(_.toLong)
          authorId <- Option(record.getFeatureValue(AUTHOR_ID)).map(_.toLong)
          timestamp <- Option(record.getFeatureValue(TIMESTAMP)).map(_.toLong)
          labels = toRecTweetLabels(record)
        } yield {
          (
            userId,
            Seq(
              ReferenceTweet(
                tweetId,
                authorId,
                timestamp,
                DisplayLocation.TimelinesRectweet,
                labels))
          )
        }
      }
      .sumByKey
      .map { case (uid, tweetSeq) => ReferenceTweets(uid, tweetSeq) }
  }
}
