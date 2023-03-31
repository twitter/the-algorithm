package com.twitter.simclusters_v2.scalding.embedding.twice

import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Days
import com.twitter.scalding.Duration
import com.twitter.scalding.Execution
import com.twitter.scalding.RichDate
import com.twitter.scalding.UniqueID
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.common.clustering.ConnectedComponentsClusteringMethod
import com.twitter.simclusters_v2.common.clustering.LargestDimensionClusteringMethod
import com.twitter.simclusters_v2.common.clustering.LouvainClusteringMethod
import com.twitter.simclusters_v2.common.clustering.MedoidRepresentativeSelectionMethod
import com.twitter.simclusters_v2.common.clustering.MaxFavScoreRepresentativeSelectionMethod
import com.twitter.simclusters_v2.common.clustering.SimilarityFunctions
import com.twitter.simclusters_v2.hdfs_sources.ClustersMembersConnectedComponentsApeSimilarityScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.ClustersMembersLargestDimApeSimilarity2DayUpdateScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.ClustersMembersLargestDimApeSimilarityScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.ClustersMembersLouvainApeSimilarityScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.InterestedInTwiceByLargestDim2DayUpdateScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.InterestedInTwiceByLargestDimScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.InterestedInTwiceByLargestDimFavScoreScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.InterestedInTwiceConnectedComponentsScalaDataset
import com.twitter.simclusters_v2.hdfs_sources.InterestedInTwiceLouvainScalaDataset
import com.twitter.simclusters_v2.scalding.embedding.twice.InterestedInTwiceBaseApp.ProducerEmbeddingSource
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 To build & deploy the TWICE scheduled jobs via workflows:

 scalding workflow upload \
  --workflow interested_in_twice-batch \
  --jobs src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_largest_dim-batch,src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_louvain-batch,src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_connected_components-batch \
  --scm-paths "src/scala/com/twitter/simclusters_v2/scalding/embedding/twice/*" \
  --autoplay \

 -> See workflow here: https://workflows.twitter.biz/workflow/cassowary/interested_in_twice-batch

 (Use `scalding workflow upload --help` for a breakdown of the different flags)
 */*/

object InterestedInTwiceLargestDimScheduledApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("2021-09-02")
  override def batchIncrement: Duration = Days(7)

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersMatchingLargestDimension
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runScheduledApp(
      new LargestDimensionClusteringMethod(),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_by_largest_dim",
      "clusters_members_largest_dim_ape_similarity",
      InterestedInTwiceByLargestDimScalaDataset,
      ClustersMembersLargestDimApeSimilarityScalaDataset,
      args.getOrElse("num-reducers", "4000").toInt
    )

  }

}

object InterestedInTwiceLargestDimMaxFavScoreScheduledApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("2022-06-30")
  override def batchIncrement: Duration = Days(7)

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersMatchingLargestDimension
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runScheduledApp(
      new LargestDimensionClusteringMethod(),
      new MaxFavScoreRepresentativeSelectionMethod[SimClustersEmbedding](),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_by_largest_dim_fav_score",
      "clusters_members_largest_dim_ape_similarity",
      InterestedInTwiceByLargestDimFavScoreScalaDataset,
      ClustersMembersLargestDimApeSimilarityScalaDataset,
      args.getOrElse("num-reducers", "4000").toInt
    )

  }

}

object InterestedInTwiceLouvainScheduledApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("2021-09-02")
  override def batchIncrement: Duration = Days(7)

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runScheduledApp(
      new LouvainClusteringMethod(
        args.required("cosine_similarity_threshold").toDouble,
        args.optional("resolution_factor").map(_.toDouble)),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_louvain",
      "clusters_members_louvain_ape_similarity",
      InterestedInTwiceLouvainScalaDataset,
      ClustersMembersLouvainApeSimilarityScalaDataset,
      args.getOrElse("num-reducers", "4000").toInt
    )

  }

}

object InterestedInTwiceConnectedComponentsScheduledApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("2021-09-02")
  override def batchIncrement: Duration = Days(7)
  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runScheduledApp(
      new ConnectedComponentsClusteringMethod(
        args.required("cosine_similarity_threshold").toDouble),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_connected_components",
      "clusters_members_connected_components_ape_similarity",
      InterestedInTwiceConnectedComponentsScalaDataset,
      ClustersMembersConnectedComponentsApeSimilarityScalaDataset,
      args.getOrElse("num-reducers", "4000").toInt
    )

  }

}

/** Production Scalding job that calculates TWICE embeddings in a shorter period (every two days).
 *
 * Given that the input sources of TWICE are updated more frequently (e.g., user_user_graph is
 * updated every 2 day), updating TWICE embedding every 2 day will better capture interests of new
 * users and the interest shift of existing users.
 *
 * To build & deploy the scheduled job via workflows:
 * {{{
 * scalding workflow upload \
 * --workflow interested_in_twice_2_day_update-batch \
 * --jobs src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_largest_dim_2_day_update-batch \
 * --scm-paths "src/scala/com/twitter/simclusters_v2/scalding/embedding/twice/*" \
 * --autoplay
 * }}}
 *
 */*/
object InterestedInTwiceLargestDim2DayUpdateScheduledApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("2022-04-06")
  override def batchIncrement: Duration = Days(2)

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersMatchingLargestDimension
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runScheduledApp(
      new LargestDimensionClusteringMethod(),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_by_largest_dim_2_day_update",
      "clusters_members_largest_dim_ape_similarity_2_day_update",
      InterestedInTwiceByLargestDim2DayUpdateScalaDataset,
      ClustersMembersLargestDimApeSimilarity2DayUpdateScalaDataset,
      args.getOrElse("num-reducers", "4000").toInt
    )
  }
}

/**

[Preferred way] To run a locally built adhoc job:
 ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_<CLUSTERING_METHOD>-adhoc
 scalding remote run --target src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_<CLUSTERING_METHOD>-adhoc

To build and run a adhoc job with workflows:
 scalding workflow upload \
  --workflow interested_in_twice-adhoc \
  --jobs src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_largest_dim-adhoc,src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_louvain-adhoc,src/scala/com/twitter/simclusters_v2/scalding/embedding/twice:interested_in_twice_connected_components-adhoc \
  --scm-paths "src/scala/com/twitter/simclusters_v2/scalding/embedding/twice/*" \
  --autoplay \

 */*/
object InterestedInTwiceLargestDimAdhocApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with AdhocExecutionApp {

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersMatchingLargestDimension
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runAdhocApp(
      new LargestDimensionClusteringMethod(),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_by_largest_dim",
      "clusters_members_largest_dim_ape_similarity",
      args.getOrElse("num-reducers", "4000").toInt
    )

  }
}

object InterestedInTwiceLargestDimMaxFavScoreAdhocApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with AdhocExecutionApp {

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersMatchingLargestDimension
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runAdhocApp(
      new LargestDimensionClusteringMethod(),
      new MaxFavScoreRepresentativeSelectionMethod[SimClustersEmbedding](),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_by_largest_dim_fav_score",
      "clusters_members_largest_dim_ape_similarity",
      args.getOrElse("num-reducers", "4000").toInt
    )

  }
}

object InterestedInTwiceLouvainAdhocApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with AdhocExecutionApp {

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runAdhocApp(
      new LouvainClusteringMethod(
        args.required("cosine_similarity_threshold").toDouble,
        args.optional("resolution_factor").map(_.toDouble)),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_louvain",
      "clusters_members_louvain_ape_similarity",
      args.getOrElse("num-reducers", "4000").toInt
    )

  }
}

object InterestedInTwiceConnectedComponentsAdhocApp
    extends InterestedInTwiceBaseApp[SimClustersEmbedding]
    with AdhocExecutionApp {

  override def producerProducerSimilarityFnForClustering: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity
  override def producerProducerSimilarityFnForClusterRepresentative: (
    SimClustersEmbedding,
    SimClustersEmbedding
  ) => Double =
    SimilarityFunctions.simClustersCosineSimilarity

  /**
   * Top-level method of this application.
   */
  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueId: UniqueID
  ): Execution[Unit] = {

    runAdhocApp(
      new ConnectedComponentsClusteringMethod(
        args.required("cosine_similarity_threshold").toDouble),
      new MedoidRepresentativeSelectionMethod[SimClustersEmbedding](
        producerProducerSimilarityFnForClusterRepresentative),
      ProducerEmbeddingSource.getAggregatableProducerEmbeddings,
      "interested_in_twice_connected_components",
      "clusters_members_connected_components_ape_similarity",
      args.getOrElse("num-reducers", "4000").toInt
    )
  }
}
