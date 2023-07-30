package com.X.timelines.data_processing.ml_util.aggregation_framework.scalding

import com.X.gizmoduck.snapshot.DeletedUserScalaDataset
import com.X.ml.api.DataRecord
import com.X.ml.api.Feature
import com.X.scalding.typed.TypedPipe
import com.X.scalding.DateOps
import com.X.scalding.DateRange
import com.X.scalding.Days
import com.X.scalding.RichDate
import com.X.scalding_internal.dalv2.DAL
import com.X.scalding_internal.dalv2.remote_access.AllowCrossClusterSameDC
import com.X.scalding_internal.job.RequiredBinaryComparators.ordSer
import com.X.scalding_internal.pruner.Pruner
import com.X.timelines.data_processing.ml_util.aggregation_framework.AggregationKey
import com.X.timelines.data_processing.ml_util.aggregation_framework.TypedAggregateGroup
import com.X.scalding.serialization.macros.impl.ordered_serialization.runtime_helpers.MacroEqualityOrderedSerialization
import java.{util => ju}

object DeletedUserSeqPruner extends Pruner[Seq[Long]] {
  implicit val tz: ju.TimeZone = DateOps.UTC
  implicit val userIdSequenceOrdering: MacroEqualityOrderedSerialization[Seq[Long]] =
    ordSer[Seq[Long]]

  private[scalding] def pruneDeletedUsers[T](
    input: TypedPipe[T],
    extractor: T => Seq[Long],
    deletedUsers: TypedPipe[Long]
  ): TypedPipe[T] = {
    val userIdsAndValues = input.map { t: T =>
      val userIds: Seq[Long] = extractor(t)
      (userIds, t)
    }

    // Find all valid sequences of userids in the input pipe
    // that contain at least one deleted user. This is efficient
    // as long as the number of deleted users is small.
    val userSequencesWithDeletedUsers = userIdsAndValues
      .flatMap { case (userIds, _) => userIds.map((_, userIds)) }
      .leftJoin(deletedUsers.asKeys)
      .collect { case (_, (userIds, Some(_))) => userIds }
      .distinct

    userIdsAndValues
      .leftJoin(userSequencesWithDeletedUsers.asKeys)
      .collect { case (_, (t, None)) => t }
  }

  override def prune[T](
    input: TypedPipe[T],
    put: (T, Seq[Long]) => Option[T],
    get: T => Seq[Long],
    writeTime: RichDate
  ): TypedPipe[T] = {
    lazy val deletedUsers = DAL
      .readMostRecentSnapshot(DeletedUserScalaDataset, DateRange(writeTime - Days(7), writeTime))
      .withRemoteReadPolicy(AllowCrossClusterSameDC)
      .toTypedPipe
      .map(_.userId)

    pruneDeletedUsers(input, get, deletedUsers)
  }
}

object AggregationKeyPruner {

  /**
   * Makes a pruner that prunes aggregate records where any of the
   * "userIdFeatures" set in the aggregation key correspond to a
   * user who has deleted their account. Here, "userIdFeatures" is
   * intended as a catch-all term for all features corresponding to
   * a X user in the input data record -- the feature itself
   * could represent an authorId, retweeterId, engagerId, etc.
   */
  def mkDeletedUsersPruner(
    userIdFeatures: Seq[Feature[_]]
  ): Pruner[(AggregationKey, DataRecord)] = {
    val userIdFeatureIds = userIdFeatures.map(TypedAggregateGroup.getDenseFeatureId)

    def getter(tupled: (AggregationKey, DataRecord)): Seq[Long] = {
      tupled match {
        case (aggregationKey, _) =>
          userIdFeatureIds.flatMap { id =>
            aggregationKey.discreteFeaturesById
              .get(id)
              .orElse(aggregationKey.textFeaturesById.get(id).map(_.toLong))
          }
      }
    }

    // Setting putter to always return None here. The put function is not used within pruneDeletedUsers, this function is just needed for xmap api.
    def putter: ((AggregationKey, DataRecord), Seq[Long]) => Option[(AggregationKey, DataRecord)] =
      (t, seq) => None

    DeletedUserSeqPruner.xmap(putter, getter)
  }
}
