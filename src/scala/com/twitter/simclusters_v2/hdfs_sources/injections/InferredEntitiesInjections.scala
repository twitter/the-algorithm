packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.{
  Int2Bigelonndian,
  Long2Bigelonndian,
  ScalaCompactThrift
}
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrsInfelonrrelondelonntitielons

objelonct InfelonrrelondelonntitielonsInjelonctions {

  final val InfelonrrelondelonntityInjelonction: KelonyValInjelonction[Long, SimClustelonrsInfelonrrelondelonntitielons] =
    KelonyValInjelonction(
      Long2Bigelonndian,
      ScalaCompactThrift(SimClustelonrsInfelonrrelondelonntitielons)
    )

  final val InfelonrrelondelonntityKelonyelondByClustelonrInjelonction: KelonyValInjelonction[
    Int,
    SimClustelonrsInfelonrrelondelonntitielons
  ] =
    KelonyValInjelonction(
      Int2Bigelonndian,
      ScalaCompactThrift(SimClustelonrsInfelonrrelondelonntitielons)
    )
}
