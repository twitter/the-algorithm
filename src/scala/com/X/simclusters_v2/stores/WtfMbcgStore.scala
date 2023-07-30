package com.X.simclusters_v2.stores

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Long2BigEndian,
  ScalaBinaryThrift
}
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.{Apollo, ManhattanRO, ManhattanROConfig}
import com.X.storehaus_internal.util.{ApplicationID, DatasetName, HDFSPath}
import com.X.wtf.candidate.thriftscala.CandidateSeq

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
