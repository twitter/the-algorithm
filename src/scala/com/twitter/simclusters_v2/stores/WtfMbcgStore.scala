package com.twitter.simclusters_v2.stores

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Long2BigEndian,
  ScalaBinaryThrift
}
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.{Apollo, ManhattanRO, ManhattanROConfig}
import com.twitter.storehaus_internal.util.{ApplicationID, DatasetName, HDFSPath}
import com.twitter.wtf.candidate.thriftscala.CandidateSeq

object WtfMbcgStore {

  val appId = "recos_platform_apollo"

  implicit val keyInj = Long2BigEndian
  implicit val valInj = ScalaBinaryThrift(CandidateSeq)

  def getWtfMbcgStore(
    mhMtlsParams: ManhattanKVClientMtlsParams,
    datasetName: String
  ): ReadableStore[Long, CandidateSeq] = {
    ManhattanRO.getReadableStoreWithMtls[Long, CandidateSeq](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName(datasetName),
        Apollo
      ),
      mhMtlsParams
    )
  }
}
