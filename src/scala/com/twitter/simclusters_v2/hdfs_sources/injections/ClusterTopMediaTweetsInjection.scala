packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaCompactThrift
import com.twittelonr.simclustelonrs_v2.thriftscala.{TwelonelontsWithScorelon, DayPartitionelondClustelonrId}

objelonct ClustelonrTopMelondiaTwelonelontsInjelonction {

  val injelonction = KelonyValInjelonction[DayPartitionelondClustelonrId, TwelonelontsWithScorelon](
    ScalaCompactThrift(DayPartitionelondClustelonrId),
    ScalaCompactThrift(TwelonelontsWithScorelon)
  )
}
