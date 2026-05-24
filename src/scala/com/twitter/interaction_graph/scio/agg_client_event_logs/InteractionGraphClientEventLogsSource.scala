package com.twitter.interaction_graph.scio.agg_client_event_logs

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.twitter.beam.job.ServiceIdentifierOptions
import com.twitter.twadoop.user.gen.thriftscala.CombinedUser
import com.twitter.usersource.snapshot.combined.UsersourceScalaDataset
import com.twitter.util.Duration
import com.twitter.cde.scio.dal_read.SourceUtil
import com.twitter.wtf.scalding.client_event_processing.thriftscala.UserInteraction
import com.twitter.wtf.scalding.jobs.client_event_processing.UserInteractionScalaDataset
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
