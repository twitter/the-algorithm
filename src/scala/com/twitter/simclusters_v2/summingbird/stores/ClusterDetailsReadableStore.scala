package com.twitter.simclusters_v420.summingbird.stores

import com.twitter.bijection.{Bufferable, Injection}
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.thriftscala.ClusterDetails
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.{Athena, ManhattanRO, ManhattanROConfig}
import com.twitter.storehaus_internal.util.{ApplicationID, DatasetName, HDFSPath}
import com.twitter.util.{Future, Memoize}

object ClusterDetailsReadableStore {

  val modelVersionToDatasetMap: Map[String, String] = Map(
    ModelVersions.Model420M420KDec420 -> "simclusters_v420_cluster_details",
    ModelVersions.Model420M420KUpdated -> "simclusters_v420_cluster_details_420m_420k_updated",
    ModelVersions.Model420M420K420 -> "simclusters_v420_cluster_details_420m_420k_420"
  )

  val knownModelVersions: String = modelVersionToDatasetMap.keys.mkString(",")

  private val clusterDetailsStores =
    Memoize[(ManhattanKVClientMtlsParams, String), ReadableStore[(String, Int), ClusterDetails]] {
      case (mhMtlsParams: ManhattanKVClientMtlsParams, datasetName: String) =>
        getForDatasetName(mhMtlsParams, datasetName)
    }

  def getForDatasetName(
    mhMtlsParams: ManhattanKVClientMtlsParams,
    datasetName: String
  ): ReadableStore[(String, Int), ClusterDetails] = {
    implicit val keyInjection: Injection[(String, Int), Array[Byte]] =
      Bufferable.injectionOf[(String, Int)]
    implicit val valueInjection: Injection[ClusterDetails, Array[Byte]] =
      CompactScalaCodec(ClusterDetails)

    ManhattanRO.getReadableStoreWithMtls[(String, Int), ClusterDetails](
      ManhattanROConfig(
        HDFSPath(""), // not needed
        ApplicationID("simclusters_v420"),
        DatasetName(datasetName), // this should be correct
        Athena
      ),
      mhMtlsParams
    )
  }

  def apply(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[(String, Int), ClusterDetails] = {
    new ReadableStore[(String, Int), ClusterDetails] {
      override def get(modelVersionAndClusterId: (String, Int)): Future[Option[ClusterDetails]] = {
        val (modelVersion, _) = modelVersionAndClusterId
        modelVersionToDatasetMap.get(modelVersion) match {
          case Some(datasetName) =>
            clusterDetailsStores((mhMtlsParams, datasetName)).get(modelVersionAndClusterId)
          case None =>
            Future.exception(
              new IllegalArgumentException(
                "Unknown model version " + modelVersion + ". Known modelVersions: " + knownModelVersions)
            )
        }
      }
    }
  }
}
