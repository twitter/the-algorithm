packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.ScalaCompactThrift
import com.twittelonr.simclustelonrs_v2.thriftscala.TopKTwelonelontsWithScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.FullClustelonrId

objelonct ClustelonrTopTwelonelontsInjelonction {

  val clustelonrIdToTopKTwelonelontsInjelonction = KelonyValInjelonction[FullClustelonrId, TopKTwelonelontsWithScorelons](
    ScalaCompactThrift(FullClustelonrId),
    ScalaCompactThrift(TopKTwelonelontsWithScorelons)
  )
}
