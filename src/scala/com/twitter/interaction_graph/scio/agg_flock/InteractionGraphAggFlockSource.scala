package com.twitter.interaction_graph.scio.agg_flock

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.twitter.beam.job.ServiceIdentifierOptions
import com.twitter.flockdb.tools.datasets.flock.thriftscala.FlockEdge
import com.twitter.cde.scio.dal_read.SourceUtil
import com.twitter.wtf.dataflow.user_events.ValidUserFollowsScalaDataset
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
