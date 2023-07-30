package com.X.interaction_graph.scio.agg_client_event_logs

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.X.beam.job.ServiceIdentifierOptions
import com.X.twadoop.user.gen.thriftscala.CombinedUser
import com.X.usersource.snapshot.combined.UsersourceScalaDataset
import com.X.util.Duration
import com.X.cde.scio.dal_read.SourceUtil
import com.X.wtf.scalding.client_event_processing.thriftscala.UserInteraction
import com.X.wtf.scalding.jobs.client_event_processing.UserInteractionScalaDataset
import org.joda.time.Interval

case class InteractionGraphClientEventLogsSource(
  pipelineOptions: InteractionGraphClientEventLogsOption
)(
  implicit sc: ScioContext) {

  val dalEnvironment: String = pipelineOptions
    .as(classOf[ServiceIdentifierOptions])
    .getEnvironment()

  def readUserInteractions(dateInterval: Interval): SCollection[UserInteraction] = {

    SourceUtil.readDALDataset[UserInteraction](
      dataset = UserInteractionScalaDataset,
      interval = dateInterval,
      dalEnvironment = dalEnvironment)

  }

  def readCombinedUsers(): SCollection[CombinedUser] = {

    SourceUtil.readMostRecentSnapshotNoOlderThanDALDataset[CombinedUser](
      dataset = UsersourceScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment
    )
  }
}
