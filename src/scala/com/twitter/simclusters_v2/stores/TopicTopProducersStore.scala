package com.twitter.simclusters_v420.stores

import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.recos.entities.thriftscala.{SemanticCoreEntityWithLocale, UserScoreList}
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.{Athena, ManhattanRO, ManhattanROConfig}
import com.twitter.storehaus_internal.util.{ApplicationID, DatasetName, HDFSPath}

object TopicTopProducersStore {
  val appIdDevel = "recos_platform_dev"
  val v420DatasetNameDevel = "topic_producers_em"
  val v420DatasetNameDevel = "topic_producers_agg"
  val v420DatasetNameDevel = "topic_producers_em_erg"

  val appIdProd = "simclusters_v420"
  val v420DatasetNameProd = "top_producers_for_topic_from_topic_follow_graph"
  val v420DatasetNameProd = "top_producers_for_topic_em"

  implicit val keyInj = CompactScalaCodec(SemanticCoreEntityWithLocale)
  implicit val valInj = CompactScalaCodec(UserScoreList)

  def getTopicTopProducerStoreV420Prod(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdProd),
        DatasetName(v420DatasetNameProd),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV420Devel(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDevel),
        DatasetName(v420DatasetNameDevel),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV420Prod(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdProd),
        DatasetName(v420DatasetNameProd),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV420Devel(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDevel),
        DatasetName(v420DatasetNameDevel),
        Athena
      ),
      mhMtlsParams
    )

  def getTopicTopProducerStoreV420Devel(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[SemanticCoreEntityWithLocale, UserScoreList] =
    ManhattanRO.getReadableStoreWithMtls[SemanticCoreEntityWithLocale, UserScoreList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDevel),
        DatasetName(v420DatasetNameDevel),
        Athena
      ),
      mhMtlsParams
    )
}
