package com.X.interaction_graph.scio.agg_address_book

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.X.addressbook.jobs.simplematches.SimpleUserMatchesScalaDataset
import com.X.addressbook.matches.thriftscala.UserMatchesRecord
import com.X.beam.job.ServiceIdentifierOptions
import com.X.cde.scio.dal_read.SourceUtil
import org.joda.time.Interval

case class InteractionGraphAddressBookSource(
  pipelineOptions: InteractionGraphAddressBookOption
)(
  implicit sc: ScioContext,
) {
  val dalEnvironment: String = pipelineOptions
    .as(classOf[ServiceIdentifierOptions])
    .getEnvironment()

  def readSimpleUserMatches(
    dateInterval: Interval
  ): SCollection[UserMatchesRecord] = {
    SourceUtil.readMostRecentSnapshotDALDataset[UserMatchesRecord](
      SimpleUserMatchesScalaDataset,
      dateInterval,
      dalEnvironment)
  }
}
