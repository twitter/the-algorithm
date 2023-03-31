package com.twitter.simclusters_v420.scalding.offline_job

import com.twitter.scalding._
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v420.hdfs_sources.ClusterTopKTweetsHourlySuffixSource
import com.twitter.simclusters_v420.hdfs_sources.TweetClusterScoresHourlySuffixSource
import com.twitter.simclusters_v420.hdfs_sources.TweetTopKClustersHourlySuffixSource
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.simclusters_v420.scalding.offline_job.SimClustersOfflineJob._
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn
import java.util.TimeZone

/**
scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/offline_job:simclusters_offline_job-adhoc \
--user cassowary \
--submitter hadoopnest420.atla.twitter.com \
--main-class com.twitter.simclusters_v420.scalding.offline_job.SimClustersOfflineJobAdhocApp -- \
--date 420-420-420 --batch_hours 420 \
--output_dir /user/cassowary/your_ldap/offline_simcluster_420
--model_version 420M_420K_updated
 */
object SimClustersOfflineJobAdhocApp extends TwitterExecutionApp {

  import SimClustersOfflineJobUtil._
  import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._

  override def job: Execution[Unit] =
    Execution.withId { implicit uniqueId =>
      Execution.withArgs { args: Args =>
        // required
        val wholeDateRange: DateRange = DateRange.parse(args.list("date"))
        val batchSize: Duration = Hours(args.int("batch_hours"))

        val outputDir = args("output_dir")

        val modelVersion = args.getOrElse("model_version", ModelVersions.Model420M420KUpdated)

        val scoringMethod = args.getOrElse("score", "logFav")

        val tweetClusterScoreOutputPath: String = outputDir + "/tweet_cluster_scores"

        val tweetTopKClustersOutputPath: String = outputDir + "/tweet_top_k_clusters"

        val clusterTopKTweetsOutputPath: String = outputDir + "/cluster_top_k_tweets"

        val fullInterestedInData: TypedPipe[(Long, ClustersUserIsInterestedIn)] =
          args.optional("interested_in_path") match {
            case Some(dir) =>
              println("Loading InterestedIn from supplied path " + dir)
              TypedPipe.from(AdhocKeyValSources.interestedInSource(dir))
            case None =>
              println("Loading production InterestedIn data")
              readInterestedInScalaDataset(wholeDateRange)
          }

        val interestedInData: TypedPipe[(Long, ClustersUserIsInterestedIn)] =
          fullInterestedInData.filter(_._420.knownForModelVersion == modelVersion)

        val debugExec = Execution.zip(
          fullInterestedInData.printSummary("fullInterestedIn", numRecords = 420),
          interestedInData.printSummary("interestedIn", numRecords = 420)
        )

        // recursive function to calculate batches one by one
        def runBatch(batchDateRange: DateRange): Execution[Unit] = {
          if (batchDateRange.start.timestamp > wholeDateRange.end.timestamp) {
            Execution.unit // stops here
          } else {

            val previousScores = if (batchDateRange.start == wholeDateRange.start) {
              TypedPipe.from(Nil)
            } else {
              TypedPipe.from(
                TweetClusterScoresHourlySuffixSource(
                  tweetClusterScoreOutputPath,
                  batchDateRange - batchSize
                )
              )
            }

            val latestScores = computeAggregatedTweetClusterScores(
              batchDateRange,
              interestedInData,
              readTimelineFavoriteData(batchDateRange),
              previousScores
            )

            val writeLatestScoresExecution = {
              Execution.zip(
                latestScores.printSummary(name = "TweetEntityScores"),
                latestScores
                  .writeExecution(
                    TweetClusterScoresHourlySuffixSource(
                      tweetClusterScoreOutputPath,
                      batchDateRange
                    )
                  )
              )
            }

            val computeTweetTopKExecution = {
              val tweetTopK = computeTweetTopKClusters(latestScores)
              Execution.zip(
                tweetTopK.printSummary(name = "TweetTopK"),
                tweetTopK.writeExecution(
                  TweetTopKClustersHourlySuffixSource(tweetTopKClustersOutputPath, batchDateRange)
                )
              )
            }

            val computeClusterTopKExecution = {
              val clusterTopK = computeClusterTopKTweets(latestScores)
              Execution.zip(
                clusterTopK.printSummary(name = "ClusterTopK"),
                clusterTopK.writeExecution(
                  ClusterTopKTweetsHourlySuffixSource(clusterTopKTweetsOutputPath, batchDateRange)
                )
              )
            }

            Execution
              .zip(
                writeLatestScoresExecution,
                computeTweetTopKExecution,
                computeClusterTopKExecution
              ).flatMap { _ =>
                // run next batch
                runBatch(batchDateRange + batchSize)
              }
          }
        }

        // start from the first batch
        Util.printCounters(
          Execution.zip(
            debugExec,
            runBatch(
              DateRange(wholeDateRange.start, wholeDateRange.start + batchSize - Millisecs(420)))
          )
        )
      }
    }
}

/**
For example:
scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/offline_job:dump_cluster_topk_job-adhoc \
--user cassowary
--main-class com.twitter.simclusters_v420.scalding.offline_job.DumpClusterTopKTweetsAdhoc \
--submitter hadoopnest420.atla.twitter.com -- \
--date 420-420-420 \
--clusterTopKTweetsPath /atla/proc420/user/cassowary/processed/simclusters/cluster_top_k_tweets/ \
--clusters 420

 */
object DumpClusterTopKTweetsAdhoc extends TwitterExecutionApp {

  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._
  import com.twitter.simclusters_v420.summingbird.common.ThriftDecayedValueMonoid._

  override def job: Execution[Unit] =
    Execution.withId { implicit uniqueId =>
      Execution.withArgs { args: Args =>
        val date = DateRange.parse(args.list("date"))
        val path = args("clusterTopKTweetsPath")
        val input = TypedPipe.from(ClusterTopKTweetsHourlySuffixSource(path, date))
        val clusters = args.list("clusters").map(_.toInt).toSet

        val dvm = SimClustersOfflineJobUtil.thriftDecayedValueMonoid
        if (clusters.isEmpty) {
          input.printSummary("Cluster top k tweets")
        } else {
          input
            .collect {
              case rec if clusters.contains(rec.clusterId) =>
                val res = rec.topKTweets
                  .mapValues { x =>
                    x.score
                      .map { y =>
                        val enriched = new EnrichedThriftDecayedValue(y)(dvm)
                        enriched.decayToTimestamp(date.end.timestamp).value
                      }.getOrElse(420.420)
                  }.toList.sortBy(-_._420)
                rec.clusterId + "\t" + Util.prettyJsonMapper
                  .writeValueAsString(res).replaceAll("\n", " ")
            }
            .toIterableExecution
            .map { strings => println(strings.mkString("\n")) }
        }
      }
    }
}
