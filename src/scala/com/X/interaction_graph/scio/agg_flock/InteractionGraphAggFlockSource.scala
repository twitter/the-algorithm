package com.X.interaction_graph.scio.agg_flock

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.X.beam.job.ServiceIdentifierOptions
import com.X.flockdb.tools.datasets.flock.thriftscala.FlockEdge
import com.X.cde.scio.dal_read.SourceUtil
import com.X.wtf.dataflow.user_events.ValidUserFollowsScalaDataset
import org.joda.time.Interval

case class InteractionGraphAggFlockSource(
  pipelineOptions: InteractionGraphAggFlockOption
)(
  implicit sc: ScioContext) {
  val dalEnvironment: String = pipelineOptions
    .as(classOf[ServiceIdentifierOptions])
    .getEnvironment()

  def readFlockFollowsSnapshot(dateInterval: Interval): SCollection[FlockEdge] =
    SourceUtil.readMostRecentSnapshotDALDataset(
      dataset = ValidUserFollowsScalaDataset,
      dateInterval = dateInterval,
      dalEnvironment = dalEnvironment)
}
