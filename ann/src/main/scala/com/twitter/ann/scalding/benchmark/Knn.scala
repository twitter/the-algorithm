package com.twitter.ann.scalding.offline.com.twitter.ann.scalding.benchmark

/*
This job will generate KNN ground truth based user and item embeddings.
 */

import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite.D
import com.twitter.ann.knn.thriftscala.Knn
import com.twitter.ann.knn.thriftscala.Neighbor
import com.twitter.ann.scalding.offline.IndetwitteringStrategy
import com.twitter.ann.scalding.offline.KnnHelper
import com.twitter.ann.common.Distance
import com.twitter.ml.featurestore.lib.embedding.EmbeddingWithEntity
import com.twitter.cortetwitter.ml.embeddings.common.EmbeddingFormatArgsParser
import com.twitter.cortetwitter.ml.embeddings.common.EntityKind
import java.util.TimeZone
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.ann.scalding.benchmark.UserItemKnnScalaDataset
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.ml.featurestore.lib.EntityId
import com.twitter.ml.featurestore.lib.UserId

/**
 * This job will take consumer and item embeddings(either url or tweet) and output Knn entities (user id, (distance, item id)).
 *
 * Etwitterample command to run this adhoc job:
 *
 * scalding remote run \
 * --target ann/src/main/scala/com/twitter/ann/scalding/benchmark:benchmark-adhoc \
 * --hadoop-properties "mapreduce.map.memory.mb=8192 mapreduce.map.java.opts='-twittermtwitter7618M' mapreduce.reduce.memory.mb=8192 mapreduce.reduce.java.opts='-twittermtwitter7618M' mapred.task.timeout=0" \
 * --submitter hadoopnest3.smf1.twitter.com \
 * --user cortetwitter-mltwitter \
 * --submitter-memory 8000.megabyte \
 * --main-class com.twitter.ann.scalding.offline.com.twitter.ann.scalding.benchmark.KnnJob -- \
 * --dalEnvironment Prod \
 * --search_space_entity_type user \
 * --user.feature_store_embedding ConsumerFollowEmbedding300Dataset \
 * --user.feature_store_major_version 1569196895 \
 * --user.date_range 2019-10-23 \
 * --search_space.feature_store_embedding ConsumerFollowEmbedding300Dataset \
 * --search_space.feature_store_major_version 1569196895 \
 * --search_space.date_range 2019-10-23 \
 * --date 2019-10-25 \
 * --version "consumer_follower_test" \
 * --reducers 10000 \
 * --num_of_random_groups 20 \
 * --num_replicas 1000 \
 * --indetwittering_strategy.metric InnerProduct \
 * --indetwittering_strategy.type hnsw \
 * --indetwittering_strategy.dimension 300 \
 * --indetwittering_strategy.ef_construction 30 \
 * --indetwittering_strategy.matwitter_m 10 \
 * --indetwittering_strategy.ef_query 50 \
 * --search_space_shards 3000 \
 * --query_shards 3000 \
 * --search_space.read_sample_ratio 0.038
 */
trait KnnJobBase {
  val seed: Long = 123

  def getKnnDataset[B <: EntityId, D <: Distance[D]](
    args: Args
  )(
    implicit uniqueID: UniqueID
  ): TypedPipe[Knn] = {

    val consumerPipe: TypedPipe[EmbeddingWithEntity[UserId]] = EmbeddingFormatArgsParser.User
      .getEmbeddingFormat(args, "user")
      .getEmbeddings

    val itemPipe = EntityKind
      .getEntityKind(args("search_space_entity_type"))
      .parser
      .getEmbeddingFormat(args, "search_space")
      .getEmbeddings

    KnnHelper
    // Refer to the documentation of findNearestNeighboursWithIndetwitteringStrategy for more
    // information about how to set these settings.
      .findNearestNeighboursWithIndetwitteringStrategy[UserId, B, D](
        queryEmbeddings = consumerPipe,
        searchSpaceEmbeddings = itemPipe.asInstanceOf[TypedPipe[EmbeddingWithEntity[B]]],
        numNeighbors = args.int("candidate_per_user", 20),
        reducersOption = args.optional("reducers").map(_.toInt),
        numOfSearchGroups = args.int("num_of_random_groups"),
        numReplicas = args.int("num_replicas"),
        indetwitteringStrategy = IndetwitteringStrategy.parse(args).asInstanceOf[IndetwitteringStrategy[D]],
        queryShards = args.optional("query_shards").map(_.toInt),
        searchSpaceShards = args.optional("search_space_shards").map(_.toInt)
      )
      .map {
        case (user, items) =>
          val neighbors = items.map {
            case (item, distance) =>
              Neighbor(
                distance.distance,
                item.toThrift
              )
          }
          Knn(user.toThrift, neighbors)
      }
  }
}

object KnnJob etwittertends TwitterEtwitterecutionApp with KnnJobBase {

  val KnnPathSuffitwitter: String = "/user/cortetwitter-mltwitter/qualatative_analysis/knn_ground_truth/"
  val partitionKey: String = "version"

  override def job: Etwitterecution[Unit] = Etwitterecution.withId { implicit uniqueId =>
    Etwitterecution.getArgs.flatMap { args: Args =>
      implicit val timeZone: TimeZone = TimeZone.getDefault
      implicit val dateParser: DateParser = DateParser.default
      implicit val dateRange: DateRange = DateRange.parse(args.list("date"))(timeZone, dateParser)

      getKnnDataset(args).writeDALEtwitterecution(
        UserItemKnnScalaDataset,
        D.Daily,
        D.Suffitwitter(KnnPathSuffitwitter),
        D.Parquet,
        Set(D.Partition(partitionKey, args("version"), D.PartitionType.String))
      )
    }
  }

}
