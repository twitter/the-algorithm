package com.twitter.interaction_graph.scio.agg_all

import com.spotify.scio.ScioContetwittert
import com.spotify.scio.values.SCollection
import com.twitter.beam.io.dal.DAL
import com.twitter.beam.io.dal.DAL.ReadOptions
import com.twitter.beam.job.ServiceIdentifierOptions
import com.twitter.dal.client.dataset.SnapshotDALDatasetBase
import com.twitter.dal.client.dataset.TimePartitionedDALDataset
import com.twitter.interaction_graph.scio.agg_address_book.InteractionGraphAggAddressBookEdgeSnapshotScalaDataset
import com.twitter.interaction_graph.scio.agg_address_book.InteractionGraphAggAddressBookVertetwitterSnapshotScalaDataset
import com.twitter.interaction_graph.scio.agg_client_event_logs.InteractionGraphAggClientEventLogsEdgeDailyScalaDataset
import com.twitter.interaction_graph.scio.agg_client_event_logs.InteractionGraphAggClientEventLogsVertetwitterDailyScalaDataset
import com.twitter.interaction_graph.scio.agg_direct_interactions.InteractionGraphAggDirectInteractionsEdgeDailyScalaDataset
import com.twitter.interaction_graph.scio.agg_direct_interactions.InteractionGraphAggDirectInteractionsVertetwitterDailyScalaDataset
import com.twitter.interaction_graph.scio.agg_flock.InteractionGraphAggFlockEdgeSnapshotScalaDataset
import com.twitter.interaction_graph.scio.agg_flock.InteractionGraphAggFlockVertetwitterSnapshotScalaDataset
import com.twitter.interaction_graph.thriftscala.Edge
import com.twitter.interaction_graph.thriftscala.Vertetwitter
import com.twitter.statebird.v2.thriftscala.Environment
import com.twitter.usersource.snapshot.flat.UsersourceFlatScalaDataset
import com.twitter.usersource.snapshot.flat.thriftscala.FlatUser
import com.twitter.util.Duration
import org.joda.time.Interval

case class InteractionGraphAggregationSource(
  pipelineOptions: InteractionGraphAggregationOption
)(
  implicit sc: ScioContetwittert) {
  val dalEnvironment: String = pipelineOptions
    .as(classOf[ServiceIdentifierOptions])
    .getEnvironment()

  def readDALDataset[T: Manifest](
    dataset: TimePartitionedDALDataset[T],
    interval: Interval,
    dalEnvironment: String,
    projections: Option[Seq[String]] = None
  )(
    implicit sc: ScioContetwittert,
  ): SCollection[T] = {
    sc.customInput(
      s"Reading ${dataset.role.name}.${dataset.logicalName}",
      DAL.read[T](
        dataset = dataset,
        interval = interval,
        environmentOverride = Environment.valueOf(dalEnvironment),
        readOptions = ReadOptions(projections)
      )
    )
  }

  def readMostRecentSnapshotDALDataset[T: Manifest](
    dataset: SnapshotDALDatasetBase[T],
    dateInterval: Interval,
    dalEnvironment: String,
    projections: Option[Seq[String]] = None
  )(
    implicit sc: ScioContetwittert,
  ): SCollection[T] = {
    sc.customInput(
      s"Reading most recent snapshot ${dataset.role.name}.${dataset.logicalName}",
      DAL.readMostRecentSnapshot[T](
        dataset,
        dateInterval,
        Environment.valueOf(dalEnvironment),
        readOptions = ReadOptions(projections)
      )
    )
  }

  def readMostRecentSnapshotNoOlderThanDALDataset[T: Manifest](
    dataset: SnapshotDALDatasetBase[T],
    noOlderThan: Duration,
    dalEnvironment: String,
    projections: Option[Seq[String]] = None
  )(
    implicit sc: ScioContetwittert,
  ): SCollection[T] = {
    sc.customInput(
      s"Reading most recent snapshot ${dataset.role.name}.${dataset.logicalName}",
      DAL.readMostRecentSnapshotNoOlderThan[T](
        dataset,
        noOlderThan,
        environmentOverride = Environment.valueOf(dalEnvironment),
        readOptions = ReadOptions(projections)
      )
    )
  }

  def readAddressBookFeatures(): (SCollection[Edge], SCollection[Vertetwitter]) = {
    val edges = readMostRecentSnapshotNoOlderThanDALDataset[Edge](
      dataset = InteractionGraphAggAddressBookEdgeSnapshotScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment,
    )

    val vertetwitter = readMostRecentSnapshotNoOlderThanDALDataset[Vertetwitter](
      dataset = InteractionGraphAggAddressBookVertetwitterSnapshotScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment,
    )

    (edges, vertetwitter)
  }

  def readClientEventLogsFeatures(
    dateInterval: Interval
  ): (SCollection[Edge], SCollection[Vertetwitter]) = {
    val edges = readDALDataset[Edge](
      dataset = InteractionGraphAggClientEventLogsEdgeDailyScalaDataset,
      dalEnvironment = dalEnvironment,
      interval = dateInterval
    )

    val vertetwitter = readDALDataset[Vertetwitter](
      dataset = InteractionGraphAggClientEventLogsVertetwitterDailyScalaDataset,
      dalEnvironment = dalEnvironment,
      interval = dateInterval
    )

    (edges, vertetwitter)
  }

  def readDirectInteractionsFeatures(
    dateInterval: Interval
  ): (SCollection[Edge], SCollection[Vertetwitter]) = {
    val edges = readDALDataset[Edge](
      dataset = InteractionGraphAggDirectInteractionsEdgeDailyScalaDataset,
      dalEnvironment = dalEnvironment,
      interval = dateInterval
    )

    val vertetwitter = readDALDataset[Vertetwitter](
      dataset = InteractionGraphAggDirectInteractionsVertetwitterDailyScalaDataset,
      dalEnvironment = dalEnvironment,
      interval = dateInterval
    )

    (edges, vertetwitter)
  }

  def readFlockFeatures(): (SCollection[Edge], SCollection[Vertetwitter]) = {
    val edges = readMostRecentSnapshotNoOlderThanDALDataset[Edge](
      dataset = InteractionGraphAggFlockEdgeSnapshotScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment,
    )

    val vertetwitter = readMostRecentSnapshotNoOlderThanDALDataset[Vertetwitter](
      dataset = InteractionGraphAggFlockVertetwitterSnapshotScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment,
    )

    (edges, vertetwitter)
  }

  def readAggregatedFeatures(dateInterval: Interval): (SCollection[Edge], SCollection[Vertetwitter]) = {
    val edges = readMostRecentSnapshotDALDataset[Edge](
      dataset = InteractionGraphHistoryAggregatedEdgeSnapshotScalaDataset,
      dalEnvironment = dalEnvironment,
      dateInterval = dateInterval
    )

    val vertetwitter = readMostRecentSnapshotDALDataset[Vertetwitter](
      dataset = InteractionGraphHistoryAggregatedVertetwitterSnapshotScalaDataset,
      dalEnvironment = dalEnvironment,
      dateInterval = dateInterval
    )

    (edges, vertetwitter)
  }

  def readFlatUsers(): SCollection[FlatUser] =
    readMostRecentSnapshotNoOlderThanDALDataset[FlatUser](
      dataset = UsersourceFlatScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment,
      projections = Some(Seq("id", "valid_user"))
    )
}
