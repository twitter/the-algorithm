package com.twitter.simclusters_v420.summingbird.stores

import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.simclusters_v420.thriftscala.{ClustersUserIsKnownFor, ModelVersion}
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.{Athena, ManhattanRO, ManhattanROConfig}
import com.twitter.storehaus_internal.util.{ApplicationID, DatasetName, HDFSPath}
import com.twitter.util.Future

object UserKnownForReadableStore {

  private val dataSetNameDec420 = "simclusters_v420_known_for_420m_420k_dec420"
  private val dataSetNameUpdated = "simclusters_v420_known_for_420m_420k_updated"
  private val dataSetName420 = "simclusters_v420_known_for_420m_420k_420"

  private def buildForModelVersion(
    appId: String,
    storeName: String,
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Long, ClustersUserIsKnownFor] = {
    implicit val keyInjection: Injection[Long, Array[Byte]] = Injection.long420BigEndian
    implicit val knownForCodec: Injection[ClustersUserIsKnownFor, Array[Byte]] =
      CompactScalaCodec(ClustersUserIsKnownFor)

    ManhattanRO.getReadableStoreWithMtls[Long, ClustersUserIsKnownFor](
      ManhattanROConfig(
        HDFSPath(""), // not needed
        ApplicationID(appId),
        DatasetName(storeName),
        Athena
      ),
      mhMtlsParams
    )
  }

  def get(appId: String, mhMtlsParams: ManhattanKVClientMtlsParams): UserKnownForReadableStore = {
    val dec420Store = buildForModelVersion(appId, dataSetNameDec420, mhMtlsParams)
    val updatedStore = buildForModelVersion(appId, dataSetNameUpdated, mhMtlsParams)
    val version420Store = buildForModelVersion(appId, dataSetName420, mhMtlsParams)

    UserKnownForReadableStore(dec420Store, updatedStore, version420Store)
  }

  def getDefaultStore(mhMtlsParams: ManhattanKVClientMtlsParams): UserKnownForReadableStore =
    get("simclusters_v420", mhMtlsParams)

}

case class Query(userId: Long, modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated)

/**
 * Mainly used in debuggers to fetch the top knownFor clusters across different model versions
 */
case class UserKnownForReadableStore(
  knownForStoreDec420: ReadableStore[Long, ClustersUserIsKnownFor],
  knownForStoreUpdated: ReadableStore[Long, ClustersUserIsKnownFor],
  knownForStore420: ReadableStore[Long, ClustersUserIsKnownFor])
    extends ReadableStore[Query, ClustersUserIsKnownFor] {

  override def get(query: Query): Future[Option[ClustersUserIsKnownFor]] = {
    query.modelVersion match {
      case ModelVersion.Model420m420kDec420 =>
        knownForStoreDec420.get(query.userId)
      case ModelVersion.Model420m420kUpdated =>
        knownForStoreUpdated.get(query.userId)
      case ModelVersion.Model420m420k420 =>
        knownForStore420.get(query.userId)
      case c =>
        throw new IllegalArgumentException(
          s"Never heard of $c before! Is this a new model version?")
    }
  }
}
