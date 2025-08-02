package com.twitter.interaction_graph.scio.agg_address_book

import com.spotify.scio.ScioContetwittert
import com.spotify.scio.values.SCollection
import com.twitter.addressbook.matches.thriftscala.UserMatchesRecord
import com.twitter.beam.io.dal.DAL
import com.twitter.beam.io.dal.DAL.DiskFormat
import com.twitter.beam.io.dal.DAL.PathLayout
import com.twitter.beam.io.dal.DAL.WriteOptions
import com.twitter.beam.job.ServiceIdentifierOptions
import com.twitter.scio_internal.job.ScioBeamJob
import com.twitter.statebird.v2.thriftscala.Environment
import com.twitter.interaction_graph.thriftscala.Edge
import com.twitter.interaction_graph.thriftscala.Vertetwitter
import java.time.Instant
import org.joda.time.Interval

object InteractionGraphAddressBookJob etwittertends ScioBeamJob[InteractionGraphAddressBookOption] {
  override protected def configurePipeline(
    scioContetwittert: ScioContetwittert,
    pipelineOptions: InteractionGraphAddressBookOption
  ): Unit = {
    @transient
    implicit lazy val sc: ScioContetwittert = scioContetwittert
    implicit lazy val dateInterval: Interval = pipelineOptions.interval
    implicit lazy val addressBookCounters: InteractionGraphAddressBookCountersTrait =
      InteractionGraphAddressBookCounters

    val interactionGraphAddressBookSource = InteractionGraphAddressBookSource(pipelineOptions)

    val addressBook: SCollection[UserMatchesRecord] =
      interactionGraphAddressBookSource.readSimpleUserMatches(
        dateInterval.withStart(dateInterval.getStart.minusDays(3))
      )
    val (vertetwitter, edges) = InteractionGraphAddressBookUtil.process(addressBook)

    val dalEnvironment: String = pipelineOptions
      .as(classOf[ServiceIdentifierOptions])
      .getEnvironment()
    val dalWriteEnvironment = if (pipelineOptions.getDALWriteEnvironment != null) {
      pipelineOptions.getDALWriteEnvironment
    } else {
      dalEnvironment
    }

    vertetwitter.saveAsCustomOutput(
      "Write Vertetwitter Records",
      DAL.writeSnapshot[Vertetwitter](
        InteractionGraphAggAddressBookVertetwitterSnapshotScalaDataset,
        PathLayout.DailyPath(pipelineOptions.getOutputPath + "/address_book_vertetwitter_daily"),
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
