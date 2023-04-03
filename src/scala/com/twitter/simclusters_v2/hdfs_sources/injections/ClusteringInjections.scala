packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaBinaryThrift
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.Long2Bigelonndian
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala._

objelonct ClustelonringInjelonctions {

  final val OrdelonrelondClustelonrsAndMelonmbelonrsInjelonction: KelonyValInjelonction[
    UselonrId,
    OrdelonrelondClustelonrsAndMelonmbelonrs
  ] =
    KelonyValInjelonction(Long2Bigelonndian, ScalaBinaryThrift(OrdelonrelondClustelonrsAndMelonmbelonrs))
}
