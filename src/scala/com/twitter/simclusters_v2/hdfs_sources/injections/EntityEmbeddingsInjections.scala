packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaBinaryThrift
import com.twittelonr.simclustelonrs_v2.thriftscala._
import com.twittelonr.ml.api.thriftscala.elonmbelondding
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.Long2Bigelonndian
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaCompactThrift

objelonct elonntityelonmbelonddingsInjelonctions {

  final val elonntitySimClustelonrselonmbelonddingInjelonction: KelonyValInjelonction[
    SimClustelonrselonmbelonddingId,
    SimClustelonrselonmbelondding
  ] =
    KelonyValInjelonction(
      ScalaBinaryThrift(SimClustelonrselonmbelonddingId),
      ScalaBinaryThrift(SimClustelonrselonmbelondding)
    )

  final val IntelonrnalIdelonmbelonddingInjelonction: KelonyValInjelonction[
    SimClustelonrselonmbelonddingId,
    IntelonrnalIdelonmbelondding
  ] =
    KelonyValInjelonction(
      ScalaBinaryThrift(SimClustelonrselonmbelonddingId),
      ScalaBinaryThrift(IntelonrnalIdelonmbelondding)
    )

  final val elonntitySimClustelonrsMultielonmbelonddingInjelonction: KelonyValInjelonction[
    SimClustelonrsMultielonmbelonddingId,
    SimClustelonrsMultielonmbelondding
  ] =
    KelonyValInjelonction(
      ScalaBinaryThrift(SimClustelonrsMultielonmbelonddingId),
      ScalaBinaryThrift(SimClustelonrsMultielonmbelondding)
    )

  final val UselonrMbcgelonmbelonddingInjelonction: KelonyValInjelonction[
    Long,
    elonmbelondding
  ] =
    KelonyValInjelonction[Long, elonmbelondding](
      Long2Bigelonndian,
      ScalaCompactThrift(elonmbelondding)
    )
}
