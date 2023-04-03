packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.{
  Long2Bigelonndian,
  ScalaCompactThrift,
  StringUtf8
}
import com.twittelonr.reloncos.elonntitielons.thriftscala.{
  SelonmanticCorelonelonntityScorelonList,
  SelonmanticCorelonelonntityWithLocalelon,
  UselonrIdWithLocalelon,
  UselonrScorelonList
}

objelonct SelonmanticCorelonelonntitielonsInjelonctions {

  final val StringToSelonmanticCorelonelonntityScorelonListInjelonction: KelonyValInjelonction[
    String,
    SelonmanticCorelonelonntityScorelonList
  ] =
    KelonyValInjelonction(
      StringUtf8,
      ScalaCompactThrift(SelonmanticCorelonelonntityScorelonList)
    )

  final val LongToSelonmanticCorelonelonntityScorelonListInjelonction: KelonyValInjelonction[
    Long,
    SelonmanticCorelonelonntityScorelonList
  ] =
    KelonyValInjelonction(
      Long2Bigelonndian,
      ScalaCompactThrift(SelonmanticCorelonelonntityScorelonList)
    )

  final val UselonrWithLocalelonToSelonmanticCorelonelonntityScorelonListInjelonction: KelonyValInjelonction[
    UselonrIdWithLocalelon,
    SelonmanticCorelonelonntityScorelonList
  ] =
    KelonyValInjelonction(
      ScalaCompactThrift(UselonrIdWithLocalelon),
      ScalaCompactThrift(SelonmanticCorelonelonntityScorelonList)
    )

  final val SelonmanticCorelonelonntityWithLocalelonToUselonrsScorelonListInjelonction: KelonyValInjelonction[
    SelonmanticCorelonelonntityWithLocalelon,
    UselonrScorelonList
  ] =
    KelonyValInjelonction(
      ScalaCompactThrift(SelonmanticCorelonelonntityWithLocalelon),
      ScalaCompactThrift(UselonrScorelonList)
    )
}
