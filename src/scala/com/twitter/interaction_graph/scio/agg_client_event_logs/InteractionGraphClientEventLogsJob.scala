package com.twitter.interaction_graph.scio.agg_client_event_logs

import com.spotify.scio.ScioContetwittert
import com.twitter.beam.io.dal.DAL
import com.twitter.beam.io.dal.DAL.DiskFormat
import com.twitter.beam.io.dal.DAL.WriteOptions
import com.twitter.beam.io.fs.multiformat.PathLayout
import com.twitter.beam.job.ServiceIdentifierOptions
import com.twitter.interaction_graph.scio.common.UserUtil
import com.twitter.interaction_graph.thriftscala.Edge
import com.twitter.interaction_graph.thriftscala.Vertetwitter
import com.twitter.scio_internal.job.ScioBeamJob
import com.twitter.statebird.v2.thriftscala.Environment
import org.joda.time.Interval

object InteractionGraphClientEventLogsJob
    etwittertends ScioBeamJob[InteractionGraphClientEventLogsOption] {
  override protected def configurePipeline(
    scioContetwittert: ScioContetwittert,
    pipelineOptions: InteractionGraphClientEventLogsOption
  ): Unit = {

    @transient
    implicit lazy val sc: ScioContetwittert = scioContetwittert
    implicit lazy val jobCounters: InteractionGraphClientEventLogsCountersTrait =
      InteractionGraphClientEventLogsCounters

    lazy val dateInterval: Interval = pipelineOptions.interval

    val sources = InteractionGraphClientEventLogsSource(pipelineOptions)

    val userInteractions = sources.readUserInteractions(dateInterval)
    val rawUsers = sources.readCombinedUsers()
    val safeUsers = UserUtil.getValidUsers(rawUsers)

    val (vertetwitter, edges) = InteractionGraphClientEventLogsUtil.process(userInteractions, safeUsers)

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
      DAL.write[Vertetwitter](
        InteractionGraphAggClientEventLogsVertetwitterDailyScalaDataset,
        PathLayout.DailyPath(
          pipelineOptions.getOutputPath + "/aggregated_client_event_logs_vertetwitter_daily"),
        dateInterval,
        DiskFormat.Parquet,
        Environment.valueOf(dalWriteEnvironment),
        writeOption =
          WriteOptions(numOfShards = Some((pipelineOptions.getNumberOfShards / 32.0).ceil.toInt))
      )
    )

    edges.saveAsCustomOutput(
      "Write Edge Records",
      DAL.write[Edge](
        InteractionGraphAggClientEventLogsEdgeDailyScalaDataset,
        PathLayout.DailyPath(
          pipelineOptions.getOutputPath + "/aggregated_client_event_logs_edge_daily"),
        dateInterval,
        DiskFormat.Parquet,
        Environment.valueOf(dalWriteEnvironment),
        writeOption = WriteOptions(numOfShards = Some(pipelineOptions.getNumberOfShards))
      )
    )
  }
}
