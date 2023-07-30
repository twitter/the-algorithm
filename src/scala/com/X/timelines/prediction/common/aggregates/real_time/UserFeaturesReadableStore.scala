package com.X.timelines.prediction.common.aggregates.real_time

import com.X.ml.api.DataRecord
import com.X.ml.featurestore.lib.UserId
import com.X.ml.featurestore.lib.data.PredictionRecord
import com.X.ml.featurestore.lib.entity.Entity
import com.X.ml.featurestore.lib.online.{FeatureStoreClient, FeatureStoreRequest}
import com.X.storehaus.ReadableStore
import com.X.timelines.prediction.common.adapters.TimelinesAdapterBase
import com.X.util.Future
import scala.collection.JavaConverters._

class UserFeaturesReadableStore(
  featureStoreClient: FeatureStoreClient,
  userEntity: Entity[UserId],
  userFeaturesAdapter: TimelinesAdapterBase[PredictionRecord])
    extends ReadableStore[Set[Long], DataRecord] {

  override def multiGet[K <: Set[Long]](keys: Set[K]): Map[K, Future[Option[DataRecord]]] = {
    val orderedKeys = keys.toSeq
    val featureStoreRequests: Seq[FeatureStoreRequest] = orderedKeys.map { key: Set[Long] =>
      FeatureStoreRequest(
        entityIds = key.map(userId => userEntity.withId(UserId(userId))).toSeq
      )
    }
    val predictionRecordsFut: Future[Seq[PredictionRecord]] = featureStoreClient(
      featureStoreRequests)

    orderedKeys.zipWithIndex.map {
      case (userId, index) =>
        val dataRecordFutOpt = predictionRecordsFut.map { predictionRecords =>
          userFeaturesAdapter.adaptToDataRecords(predictionRecords(index)).asScala.headOption
        }
        (userId, dataRecordFutOpt)
    }.toMap
  }
}
