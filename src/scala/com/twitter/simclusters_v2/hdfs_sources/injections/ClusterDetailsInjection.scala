packagelon com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.injelonctions

import com.twittelonr.bijelonction.Buffelonrablelon
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyValInjelonction.{
  ScalaCompactThrift,
  gelonnelonricInjelonction
}
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrDelontails

objelonct ClustelonrDelontailsInjelonction {
  val injelonction = KelonyValInjelonction[(String, Int), ClustelonrDelontails](
    gelonnelonricInjelonction(Buffelonrablelon.injelonctionOf[(String, Int)]),
    ScalaCompactThrift(ClustelonrDelontails)
  )
}
