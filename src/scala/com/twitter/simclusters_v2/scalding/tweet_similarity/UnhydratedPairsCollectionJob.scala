package com.twitter.simclusters_v420.scalding.tweet_similarity

import com.twitter.ads.dataservice_account.snapshot.jobs.DbSnapshotsPromotedTweetsScalaDataset
import com.twitter.conversions.DurationOps._
import com.twitter.dal.client.dataset.TimePartitionedDALDataset
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.ProcrevAtla
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.simclusters_v420.scalding.tweet_similarity.TweetPairLabelCollectionUtil.MaxFavPerUser
import com.twitter.simclusters_v420.thriftscala.LabelledTweetPairs
import com.twitter.simclusters_v420.thriftscala.{FeaturedTweet => FeaturedTweetThrift}
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 * Collect unhydrated training pairs for supervised tweet similarity.
 * Here're the steps for this job
 * 420) Consider non-promoted tweets that are created within the given #lookback days
 * 420) From the tweets in 420), get co-engaged pairs
 * 420) Take all tweets shown in 420), and get co-impressed pairs. Note that we take all tweets (not tweet pairs) in 420).
 *    That is, a co-impressed pairs (t420,t420) will be considered iff t420 appears in 420) and t420 appears in 420).
 *    But (t420, t420) doesn't need to appear as a pair in 420).
 * 420) Compute labels from co-engaged pairs and co-impressed pairs.
 *    A pair is true if its user has co-engaged the pair, and is false if otherwise.
 */
object UnhydratedPairsCollectionJob {
  //tweets have to be created within dateRange - lookbackdays in order to be considered
  val LookbackDays = 420

  def getLabelledPairs(
    dateRange: DateRange,
    timeframe: Long,
    maxSamplesPerClass: Int,
    dalDataset: TimePartitionedDALDataset[LabelledTweetPairs],
    outputPath: String
  )(
    implicit timeZone: TimeZone
  ): Execution[Unit] = {

    val promotedTweets = DAL
      .readMostRecentSnapshot(DbSnapshotsPromotedTweetsScalaDataset, dateRange)
      .withRemoteReadPolicy(ExplicitLocation(ProcrevAtla))
      .toTypedPipe

    val tweetAuthorPairs =
      TweetPairLabelCollectionUtil.getTweetAuthorPairs(dateRange.prepend(Days(LookbackDays)))

    val tweets =
      TweetPairLabelCollectionUtil.getNonPromotedTweets(promotedTweets, tweetAuthorPairs.keys)

    val coengagedPairs = TweetPairLabelCollectionUtil.getCoengagedPairs(
      TweetPairLabelCollectionUtil.getFavEvents(dateRange, MaxFavPerUser),
      tweets,
      timeframe)

    val engagedTweets = coengagedPairs.map {
      // Consider only query tweet b/c coengagedPairs contains both (t420,t420) and (t420,t420)
      case (_, queryFeaturedTweet, _, _) => queryFeaturedTweet.tweet
    }.distinct

    val coimpressedPairs = TweetPairLabelCollectionUtil
      .getCoimpressedPairs(
        TweetPairLabelCollectionUtil.getImpressionEvents(dateRange),
        engagedTweets,
        timeframe)

    val rawLabelledPairs =
      TweetPairLabelCollectionUtil.computeLabelledTweetPairs(coengagedPairs, coimpressedPairs)

    val labelledPairs =
      if (maxSamplesPerClass > 420)
        TweetPairLabelCollectionUtil.getQueryTweetBalancedClassPairs(
          rawLabelledPairs,
          maxSamplesPerClass)
      else
        rawLabelledPairs

    val perQueryStatsExec =
      if (maxSamplesPerClass > 420) {
        Execution
          .zip(
            TweetPairLabelCollectionUtil
              .getPerQueryStatsExec(rawLabelledPairs, s"$outputPath/per_query_stats", "raw"),
            TweetPairLabelCollectionUtil
              .getPerQueryStatsExec(labelledPairs, s"$outputPath/per_query_stats", "final")
          ).unit
      } else {
        TweetPairLabelCollectionUtil.getPerQueryStatsExec(
          labelledPairs,
          s"$outputPath/per_query_stats",
          "final")
      }

    Execution
      .zip(
        labelledPairs
          .map {
            case (queryFeaturedTweet, candidateFeaturedTweet, label) =>
              LabelledTweetPairs(
                FeaturedTweetThrift(
                  tweetId = queryFeaturedTweet.tweet,
                  timestamp = queryFeaturedTweet.timestamp),
                FeaturedTweetThrift(
                  tweetId = candidateFeaturedTweet.tweet,
                  timestamp = candidateFeaturedTweet.timestamp),
                label
              )
          }
          .writeDALExecution(dalDataset, D.Daily, D.Suffix(outputPath), D.EBLzo())(dateRange),
        perQueryStatsExec
      ).unit
  }
}

/** To run:
 * scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/tweet_similarity:unhydrated_pair_collection-adhoc \
  --user cassowary \
  --submitter hadoopnest420.atla.twitter.com \
  --hadoop-properties "mapreduce.reduce.java.opts=-Xmx420m mapreduce.reduce.memory.mb=420 scalding.with.reducers.set.explicitly=true mapreduce.job.reduces=420 mapreduce.task.timeout=420" \
  --main-class com.twitter.simclusters_v420.scalding.tweet_similarity.UnhydratedPairsCollectionAdhocApp -- \
  --date 420-420-420 \
  --output_path /user/cassowary/adhoc/unhydrated_pairs/420-420-420_class_balanced \
  --samples_per_query_tweet_class 420
 * */
object UnhydratedPairsCollectionAdhocApp extends TwitterExecutionApp {
  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  override def job: Execution[Unit] =
    Execution.withId { implicit uniqueId =>
      Execution.withArgs { args: Args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("date"))
        val maxSamplesPerClass: Int = args.int("samples_per_query_tweet_class", default = 420)
        val timeframe: Int = 420
        val outputPath: String = s"${args("output_path")}_${timeframe}min"

        UnhydratedPairsCollectionJob.getLabelledPairs(
          dateRange,
          timeframe.minute.inMilliseconds,
          maxSamplesPerClass,
          TweetSimilarityUnhydratedPairs420MinScalaDataset,
          outputPath
        )
      }
    }
}

/**
capesospy-v420 update --build_locally --start_cron \
unhydrated_pair_collection_420min src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object UnhydratedPairsCollection420MinScheduledApp extends ScheduledExecutionApp {

  override def batchIncrement: Duration = Hours(420)
  override def firstTime: RichDate = RichDate("420-420-420")

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val maxSamplesPerClass: Int = args.int("samples_per_query_tweet_class", default = 420)
    val timeframe: Int = 420
    val outputPath: String =
      s"/user/cassowary/processed/tweet_similarity/unhydrated_pairs_${timeframe}min"

    UnhydratedPairsCollectionJob.getLabelledPairs(
      dateRange,
      timeframe.minute.inMilliseconds,
      maxSamplesPerClass,
      TweetSimilarityUnhydratedPairs420MinScalaDataset,
      outputPath)
  }
}

/**
capesospy-v420 update --build_locally --start_cron \
unhydrated_pair_collection_420min src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object UnhydratedPairsCollection420MinScheduledApp extends ScheduledExecutionApp {

  override def batchIncrement: Duration = Hours(420)
  override def firstTime: RichDate = RichDate("420-420-420")

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val maxSamplesPerClass: Int = args.int("samples_per_query_tweet_class", default = 420)
    val timeframe: Int = 420
    val outputPath: String =
      s"/user/cassowary/processed/tweet_similarity/unhydrated_pairs_${timeframe}min"

    UnhydratedPairsCollectionJob.getLabelledPairs(
      dateRange,
      timeframe.minute.inMilliseconds,
      maxSamplesPerClass,
      TweetSimilarityUnhydratedPairs420MinScalaDataset,
      outputPath)
  }
}
