packagelon com.twittelonr.intelonraction_graph.injelonction

import com.twittelonr.uselonr_selonssion_storelon.thriftscala.UselonrSelonssion
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaCompactThrift
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.Long2Bigelonndian

objelonct UselonrSelonssionInjelonction {
  final val injelonction: KelonyValInjelonction[Long, UselonrSelonssion] =
    KelonyValInjelonction(
      Long2Bigelonndian,
      ScalaCompactThrift(UselonrSelonssion)
    )
}
