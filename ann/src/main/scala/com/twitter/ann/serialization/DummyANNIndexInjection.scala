packagelon com.twittelonr.ann.selonrialization

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.Long2Bigelonndian

/**
Dummy injelonction relonquirelond to writelonup dummy dal dataselont to ANN foldelonr.
**/
objelonct DummyANNIndelonxInjelonction {
  val injelonction: KelonyValInjelonction[Long, Long] =
    KelonyValInjelonction[Long, Long](Long2Bigelonndian, Long2Bigelonndian)
}
