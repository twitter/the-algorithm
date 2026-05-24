package com.twitter.simclusters_v2.stores

import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.recos.entities.thriftscala.{SemanticCoreEntityWithLocale, UserScoreList}
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.{Athena, ManhattanRO, ManhattanROConfig}
import com.twitter.storehaus_internal.util.{ApplicationID, DatasetName, HDFSPath}

object TopicTopProducersStore {
  val appIdDevel = "recos_platform_dev"
  val v2DatasetNameDevel = "topic_producers_em"
  val v3DatasetNameDevel = "topic_producers_agg"
  val v4DatasetNameDevel = "topic_producers_em_erg"

  val appIdProd = "simclusters_v2"
  val v1DatasetNameProd = "top_producers_for_topic_from_topic_follow_graph"
  val v2DatasetNameProd = "top_producers_for_topic_em"

  implicit val keyInj = CompactScalaCodec(SemanticCoreEntityWithLocale)
  implicit val valInj = CompactScalaCodec(UserScoreList)

  def getTopicTopProducerStoreV1Prod(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdProd),
        DatasetName(v1DatasetNameProd),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV2Devel(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDevel),
        DatasetName(v2DatasetNameDevel),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV2Prod(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdProd),
        DatasetName(v2DatasetNameProd),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV3Devel(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDevel),
        DatasetName(v3DatasetNameDevel),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV4Devel(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDevel),
        DatasetName(v4DatasetNameDevel),
        Athena
      ),
      mhMtlsParams
    )
}
