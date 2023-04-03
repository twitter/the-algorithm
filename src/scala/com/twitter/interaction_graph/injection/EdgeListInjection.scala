packagelon com.twittelonr.intelonraction_graph.injelonction

import com.twittelonr.intelonraction_graph.thriftscala.elondgelonList
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.Long2Bigelonndian
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaCompactThrift

objelonct elondgelonListInjelonction {
  final val injelonction: KelonyValInjelonction[Long, elondgelonList] =
    KelonyValInjelonction(
      Long2Bigelonndian,
      ScalaCompactThrift(elondgelonList)
    )
}
