package com.twitter.simclusters_v2.scalding.tweet_similarity

import com.twitter.ads.dataservice_account.snapshot.jobs.DbSnapshotsPromotedTweetsScalaDataset
import com.twitter.conversions.DurationOps._
import com.twitter.dal.client.dataset.TimePartitionedDALDataset
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2.remote_access.EtwitterplicitLocation
import com.twitter.scalding_internal.dalv2.remote_access.ProcrevAtla
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.simclusters_v2.scalding.tweet_similarity.TweetPairLabelCollectionUtil.MatwitterFavPerUser
import com.twitter.simclusters_v2.thriftscala.LabelledTweetPairs
import com.twitter.simclusters_v2.thriftscala.{FeaturedTweet => FeaturedTweetThrift}
import com.twitter.wtf.scalding.jobs.common.ScheduledEtwitterecutionApp
import java.util.TimeZone

/**
 * Collect unhydrated training pairs for supervised tweet similarity.
 * Here're the steps for this job
 * 1) Consider non-promoted tweets that are created within the given #lookback days
 * 2) From the tweets in 1), get co-engaged pairs
 * 3) Take all tweets shown in 2), and get co-impressed pairs. Note that we take all tweets (not tweet pairs) in 2).
 *    That is, a co-impressed pairs (t1,t2) will be considered iff t1 appears in 2) and t2 appears in 2).
 *    But (t1, t2) doesn't need to appear as a pair in 2).
 * 4) Compute labels from co-engaged pairs and co-impressed pairs.
 *    A pair is true if its user has co-engaged the pair, and is false if otherwise.
 */
object UnhydratedPairsCollectionJob {
  //tweets have to be created within dateRange - lookbackdays in order to be considered
  val LookbackDays = 2

  def getLabelledPairs(
    dateRange: DateRange,
    timeframe: Long,
    matwitterSamplesPerClass: Int,
    dalDataset: TimePartitionedDALDataset[LabelledTweetPairs],
    outputPath: String
  )(
    implicit timeZone: TimeZone
  ): Etwitterecution[Unit] = {

    val promotedTweets = DAL
      .readMostRecentSnapshot(DbSnapshotsPromotedTweetsScalaDataset, dateRange)
      .withRemoteReadPolicy(EtwitterplicitLocation(ProcrevAtla))
      .toTypedPipe

    val tweetAuthorPairs =
      TweetPairLabelCollectionUtil.getTweetAuthorPairs(dateRange.prepend(Days(LookbackDays)))

    val tweets =
      TweetPairLabelCollectionUtil.getNonPromotedTweets(promotedTweets, tweetAuthorPairs.keys)

    val coengagedPairs = TweetPairLabelCollectionUtil.getCoengagedPairs(
      TweetPairLabelCollectionUtil.getFavEvents(dateRange, MatwitterFavPerUser),
      tweets,
      timeframe)

    val engagedTweets = coengagedPairs.map {
      // Consider only query tweet b/c coengagedPairs contains both (t1,t2) and (t2,t1)
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
      if (matwitterSamplesPerClass > 0)
        TweetPairLabelCollectionUtil.getQueryTweetBalancedClassPairs(
          rawLabelledPairs,
          matwitterSamplesPerClass)
      else
        rawLabelledPairs

    val perQueryStatsEtwitterec =
      if (matwitterSamplesPerClass > 0) {
        Etwitterecution
          .zip(
            TweetPairLabelCollectionUtil
              .getPerQueryStatsEtwitterec(rawLabelledPairs, s"$outputPath/per_query_stats", "raw"),
            TweetPairLabelCollectionUtil
              .getPerQueryStatsEtwitterec(labelledPairs, s"$outputPath/per_query_stats", "final")
          ).unit
      } else {
        TweetPairLabelCollectionUtil.getPerQueryStatsEtwitterec(
          labelledPairs,
          s"$outputPath/per_query_stats",
          "final")
      }

    Etwitterecution
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
          .writeDALEtwitterecution(dalDataset, D.Daily, D.Suffitwitter(outputPath), D.EBLzo())(dateRange),
        perQueryStatsEtwitterec
      ).unit
  }
}

/** To run:
 * scalding remote run --target src/scala/com/twitter/simclusters_v2/scalding/tweet_similarity:unhydrated_pair_collection-adhoc \
  --user cassowary \
  --submitter hadoopnest2.atla.twitter.com \
  --hadoop-properties "mapreduce.reduce.java.opts=-twittermtwitter8000m mapreduce.reduce.memory.mb=8000 scalding.with.reducers.set.etwitterplicitly=true mapreduce.job.reduces=2000 mapreduce.task.timeout=0" \
  --main-class com.twitter.simclusters_v2.scalding.tweet_similarity.UnhydratedPairsCollectionAdhocApp -- \
  --date 2020-03-04 \
  --output_path /user/cassowary/adhoc/unhydrated_pairs/2020-03-04_class_balanced \
  --samples_per_query_tweet_class 2000
 * */
object UnhydratedPairsCollectionAdhocApp etwittertends TwitterEtwitterecutionApp {
  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  override def job: Etwitterecution[Unit] =
    Etwitterecution.withId { implicit uniqueId =>
      Etwitterecution.withArgs { args: Args =>
        implicit val dateRange: DateRange = DateRange.parse(args.list("date"))
        val matwitterSamplesPerClass: Int = args.int("samples_per_query_tweet_class", default = 2000)
        val timeframe: Int = 30
        val outputPath: String = s"${args("output_path")}_${timeframe}min"

        UnhydratedPairsCollectionJob.getLabelledPairs(
          dateRange,
          timeframe.minute.inMilliseconds,
          matwitterSamplesPerClass,
          TweetSimilarityUnhydratedPairs30MinScalaDataset,
          outputPath
        )
      }
    }
}

/**
capesospy-v2 update --build_locally --start_cron \
unhydrated_pair_collection_30min src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object UnhydratedPairsCollection30MinScheduledApp etwittertends ScheduledEtwitterecutionApp {

  override def batchIncrement: Duration = Hours(24)
  override def firstTime: RichDate = RichDate("2020-03-26")

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val matwitterSamplesPerClass: Int = args.int("samples_per_query_tweet_class", default = 2000)
    val timeframe: Int = 30
    val outputPath: String =
      s"/user/cassowary/processed/tweet_similarity/unhydrated_pairs_${timeframe}min"

    UnhydratedPairsCollectionJob.getLabelledPairs(
      dateRange,
      timeframe.minute.inMilliseconds,
      matwitterSamplesPerClass,
      TweetSimilarityUnhydratedPairs30MinScalaDataset,
      outputPath)
  }
}

/**
capesospy-v2 update --build_locally --start_cron \
unhydrated_pair_collection_120min src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object UnhydratedPairsCollection120MinScheduledApp etwittertends ScheduledEtwitterecutionApp {

  override def batchIncrement: Duration = Hours(24)
  override def firstTime: RichDate = RichDate("2020-03-26")

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val matwitterSamplesPerClass: Int = args.int("samples_per_query_tweet_class", default = 2000)
    val timeframe: Int = 120
    val outputPath: String =
      s"/user/cassowary/processed/tweet_similarity/unhydrated_pairs_${timeframe}min"

    UnhydratedPairsCollectionJob.getLabelledPairs(
      dateRange,
      timeframe.minute.inMilliseconds,
      matwitterSamplesPerClass,
      TweetSimilarityUnhydratedPairs120MinScalaDataset,
      outputPath)
  }
}
