packagelon com.twittelonr.simclustelonrs_v2.storelons

import com.twittelonr.bijelonction.scroogelon.CompactScalaCodelonc
import com.twittelonr.reloncos.elonntitielons.thriftscala.{SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList}
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVClielonntMtlsParams
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.manhattan.{Athelonna, ManhattanRO, ManhattanROConfig}
import com.twittelonr.storelonhaus_intelonrnal.util.{ApplicationID, DataselontNamelon, HDFSPath}

objelonct TopicTopProducelonrsStorelon {
  val appIdDelonvelonl = "reloncos_platform_delonv"
  val v2DataselontNamelonDelonvelonl = "topic_producelonrs_elonm"
  val v3DataselontNamelonDelonvelonl = "topic_producelonrs_agg"
  val v4DataselontNamelonDelonvelonl = "topic_producelonrs_elonm_elonrg"

  val appIdProd = "simclustelonrs_v2"
  val v1DataselontNamelonProd = "top_producelonrs_for_topic_from_topic_follow_graph"
  val v2DataselontNamelonProd = "top_producelonrs_for_topic_elonm"

  implicit val kelonyInj = CompactScalaCodelonc(SelonmanticCorelonelonntityWithLocalelon)
  implicit val valInj = CompactScalaCodelonc(UselonrScorelonList)

  delonf gelontTopicTopProducelonrStorelonV1Prod(
    mhMtlsParams: ManhattanKVClielonntMtlsParams
  ): RelonadablelonStorelon[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList] =
    ManhattanRO.gelontRelonadablelonStorelonWithMtls[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdProd),
        DataselontNamelon(v1DataselontNamelonProd),
        Athelonna
      ),
      mhMtlsParams
    )

  delonf gelontTopicTopProducelonrStorelonV2Delonvelonl(
    mhMtlsParams: ManhattanKVClielonntMtlsParams
  ): RelonadablelonStorelon[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList] =
    ManhattanRO.gelontRelonadablelonStorelonWithMtls[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDelonvelonl),
        DataselontNamelon(v2DataselontNamelonDelonvelonl),
        Athelonna
      ),
      mhMtlsParams
    )

  delonf gelontTopicTopProducelonrStorelonV2Prod(
    mhMtlsParams: ManhattanKVClielonntMtlsParams
  ): RelonadablelonStorelon[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList] =
    ManhattanRO.gelontRelonadablelonStorelonWithMtls[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdProd),
        DataselontNamelon(v2DataselontNamelonProd),
        Athelonna
      ),
      mhMtlsParams
    )

  delonf gelontTopicTopProducelonrStorelonV3Delonvelonl(
    mhMtlsParams: ManhattanKVClielonntMtlsParams
  ): RelonadablelonStorelon[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList] =
    ManhattanRO.gelontRelonadablelonStorelonWithMtls[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDelonvelonl),
        DataselontNamelon(v3DataselontNamelonDelonvelonl),
        Athelonna
      ),
      mhMtlsParams
    )

  delonf gelontTopicTopProducelonrStorelonV4Delonvelonl(
    mhMtlsParams: ManhattanKVClielonntMtlsParams
  ): RelonadablelonStorelon[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList] =
    ManhattanRO.gelontRelonadablelonStorelonWithMtls[SelonmanticCorelonelonntityWithLocalelon, UselonrScorelonList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appIdDelonvelonl),
        DataselontNamelon(v4DataselontNamelonDelonvelonl),
        Athelonna
      ),
      mhMtlsParams
    )
}
