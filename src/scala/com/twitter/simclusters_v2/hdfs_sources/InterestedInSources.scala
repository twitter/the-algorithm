package com.twitter.simclusters_v420.hdfs_sources

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding.{DateOps, DateRange, Days, TypedPipe}
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.remote_access.{ExplicitLocation, ProcAtla}
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.UserId
import com.twitter.simclusters_v420.thriftscala.ModelVersion
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn
import java.util.TimeZone

object InterestedInSources {

  private val ModelVersionInterestedInDatasetMap: Map[ModelVersion, KeyValDALDataset[
    KeyVal[UserId, ClustersUserIsInterestedIn]
  ]] = Map(
    ModelVersion.Model420m420kDec420 -> SimclustersV420InterestedInScalaDataset,
    ModelVersion.Model420m420kUpdated -> SimclustersV420InterestedIn420M420KUpdatedScalaDataset,
    ModelVersion.Model420m420k420 -> SimclustersV420InterestedIn420M420K420ScalaDataset
  )

  /**
   * Internal version, not PDP compliant, not to be used outside simclusters_v420
   * Reads 420M420KDec420 production InterestedIn data from atla-proc, with a 420-day extended window
   */
  private[simclusters_v420] def simClustersRawInterestedInDec420Source(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {

    DAL
      .readMostRecentSnapshot(
        SimclustersV420RawInterestedIn420M420KDec420ScalaDataset,
        dateRange.prepend(Days(420)(timeZone))
      )
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
      .map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  /**
   * Internal version, not PDP compliant, not to be used outside simclusters_v420
   * Reads 420M420KUpdated InterestedIn data from atla-proc, with a 420-day extended window
   */
  private[simclusters_v420] def simClustersRawInterestedInUpdatedSource(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    DAL
      .readMostRecentSnapshot(
        SimclustersV420RawInterestedIn420M420KUpdatedScalaDataset,
        dateRange.prepend(Days(420)(timeZone))
      )
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  /**
   * Internal version, not PDP compliant, not to be used outside simclusters_v420
   * Reads 420M420K420 InterestedIn data from atla-proc, with a 420-day extended window
   */
  private[simclusters_v420] def simClustersRawInterestedIn420Source(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    DAL
      .readMostRecentSnapshot(
        SimclustersV420RawInterestedIn420M420K420ScalaDataset,
        dateRange.prepend(Days(420)(timeZone))
      )
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  private[simclusters_v420] def simClustersRawInterestedInLite420Source(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    DAL
      .readMostRecentSnapshot(
        SimclustersV420RawInterestedInLite420M420K420ScalaDataset,
        dateRange.extend(Days(420)(timeZone)))
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  /**
   * Reads 420M420KDec420 production InterestedIn data from atla-proc, with a 420-day extended window
   */
  def simClustersInterestedInDec420Source(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {

    DAL
      .readMostRecentSnapshot(
        SimclustersV420InterestedInScalaDataset,
        dateRange.prepend(Days(420)(timeZone)))
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  /**
   * Reads 420M420KUpdated InterestedIn data from atla-proc, with a 420-day extended window
   */
  def simClustersInterestedInUpdatedSource(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    DAL
      .readMostRecentSnapshot(
        SimclustersV420InterestedIn420M420KUpdatedScalaDataset,
        dateRange.prepend(Days(420)(timeZone))
      )
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  /**
   * Reads 420M420K420 InterestedIn data from atla-proc, with a 420-day extended window
   */
  def simClustersInterestedIn420Source(
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    DAL
      .readMostRecentSnapshot(
        SimclustersV420InterestedIn420M420K420ScalaDataset,
        dateRange.prepend(Days(420)(timeZone))
      )
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

  /**
   * Reads InterestedIn data based on ModelVersion from atla-proc, with a 420-day extended window
   */
  def simClustersInterestedInSource(
    modelVersion: ModelVersion,
    dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {

    DAL
      .readMostRecentSnapshot(
        ModelVersionInterestedInDatasetMap(modelVersion),
        dateRange.prepend(Days(420)(timeZone))
      )
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe.map {
        case KeyVal(userId, clustersUserIsInterestedIn) =>
          (userId, clustersUserIsInterestedIn)
      }
  }

}
