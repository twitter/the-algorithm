package com.X.interaction_graph.scio.agg_address_book

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.X.addressbook.matches.thriftscala.UserMatchesRecord
import com.X.beam.io.dal.DAL
import com.X.beam.io.dal.DAL.DiskFormat
import com.X.beam.io.dal.DAL.PathLayout
import com.X.beam.io.dal.DAL.WriteOptions
import com.X.beam.job.ServiceIdentifierOptions
import com.X.scio_internal.job.ScioBeamJob
import com.X.statebird.v2.thriftscala.Environment
import com.X.interaction_graph.thriftscala.Edge
import com.X.interaction_graph.thriftscala.Vertex
import java.time.Instant
import org.joda.time.Interval

object InteractionGraphAddressBookJob extends ScioBeamJob[InteractionGraphAddressBookOption] {
  override protected def configurePipeline(
    scioContext: ScioContext,
    pipelineOptions: InteractionGraphAddressBookOption
  ): Unit = {
    @transient
    implicit lazy val sc: ScioContext = scioContext
    implicit lazy val dateInterval: Interval = pipelineOptions.interval
    implicit lazy val addressBookCounters: InteractionGraphAddressBookCountersTrait =
      InteractionGraphAddressBookCounters

    val interactionGraphAddressBookSource = InteractionGraphAddressBookSource(pipelineOptions)

    val addressBook: SCollection[UserMatchesRecord] =
      interactionGraphAddressBookSource.readSimpleUserMatches(
        dateInterval.withStart(dateInterval.getStart.minusDays(3))
      )
    val (vertex, edges) = InteractionGraphAddressBookUtil.process(addressBook)

    val dalEnvironment: String = pipelineOptions
      .as(classOf[ServiceIdentifierOptions])
      .getEnvironment()
    val dalWriteEnvironment = if (pipelineOptions.getDALWriteEnvironment != null) {
      pipelineOptions.getDALWriteEnvironment
    } else {
      dalEnvironment
    }

    vertex.saveAsCustomOutput(
      "Write Vertex Records",
      DAL.writeSnapshot[Vertex](
        InteractionGraphAggAddressBookVertexSnapshotScalaDataset,
        PathLayout.DailyPath(pipelineOptions.getOutputPath + "/address_book_vertex_daily"),
        Instant.ofEpochMilli(dateInterval.getEndMillis),
        DiskFormat.Parquet,
        Environment.valueOf(dalWriteEnvironment),
        writeOption =
          WriteOptions(numOfShards = Some((pipelineOptions.getNumberOfShards / 16.0).ceil.toInt))
      )
    )

    edges.saveAsCustomOutput(
      "Write Edge Records",
      DAL.writeSnapshot[Edge](
        InteractionGraphAggAddressBookEdgeSnapshotScalaDataset,
        PathLayout.DailyPath(pipelineOptions.getOutputPath + "/address_book_edge_daily"),
        Instant.ofEpochMilli(dateInterval.getEndMillis),
        DiskFormat.Parquet,
        Environment.valueOf(dalWriteEnvironment),
        writeOption = WriteOptions(numOfShards = Some(pipelineOptions.getNumberOfShards))
      )
    )
  }
}
