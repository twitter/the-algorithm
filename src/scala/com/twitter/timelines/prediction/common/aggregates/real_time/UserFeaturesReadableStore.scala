package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.data.Pred ct onRecord
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.onl ne.{FeatureStoreCl ent, FeatureStoreRequest}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesAdapterBase
 mport com.tw ter.ut l.Future
 mport scala.collect on.JavaConverters._

class UserFeaturesReadableStore(
  featureStoreCl ent: FeatureStoreCl ent,
  userEnt y: Ent y[User d],
  userFeaturesAdapter: T  l nesAdapterBase[Pred ct onRecord])
    extends ReadableStore[Set[Long], DataRecord] {

  overr de def mult Get[K <: Set[Long]](keys: Set[K]): Map[K, Future[Opt on[DataRecord]]] = {
    val orderedKeys = keys.toSeq
    val featureStoreRequests: Seq[FeatureStoreRequest] = orderedKeys.map { key: Set[Long] =>
      FeatureStoreRequest(
        ent y ds = key.map(user d => userEnt y.w h d(User d(user d))).toSeq
      )
    }
    val pred ct onRecordsFut: Future[Seq[Pred ct onRecord]] = featureStoreCl ent(
      featureStoreRequests)

    orderedKeys.z pW h ndex.map {
      case (user d,  ndex) =>
        val dataRecordFutOpt = pred ct onRecordsFut.map { pred ct onRecords =>
          userFeaturesAdapter.adaptToDataRecords(pred ct onRecords( ndex)).asScala. adOpt on
        }
        (user d, dataRecordFutOpt)
    }.toMap
  }
}
