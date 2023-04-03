packagelon com.twittelonr.simclustelonrs_v2.storelons

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.{
  Long2Bigelonndian,
  ScalaBinaryThrift
}
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVClielonntMtlsParams
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.manhattan.{Apollo, ManhattanRO, ManhattanROConfig}
import com.twittelonr.storelonhaus_intelonrnal.util.{ApplicationID, DataselontNamelon, HDFSPath}
import com.twittelonr.wtf.candidatelon.thriftscala.CandidatelonSelonq

objelonct WtfMbcgStorelon {

  val appId = "reloncos_platform_apollo"

  implicit val kelonyInj = Long2Bigelonndian
  implicit val valInj = ScalaBinaryThrift(CandidatelonSelonq)

  delonf gelontWtfMbcgStorelon(
    mhMtlsParams: ManhattanKVClielonntMtlsParams,
    dataselontNamelon: String
  ): RelonadablelonStorelon[Long, CandidatelonSelonq] = {
    ManhattanRO.gelontRelonadablelonStorelonWithMtls[Long, CandidatelonSelonq](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DataselontNamelon(dataselontNamelon),
        Apollo
      ),
      mhMtlsParams
    )
  }
}
