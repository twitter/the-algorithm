packagelon com.twittelonr.reloncos.uselonr_videlono_graph.util

import com.twittelonr.graphjelont.bipartitelon.MultiSelongmelonntItelonrator
import com.twittelonr.graphjelont.bipartitelon.api.BipartitelonGraph
import com.twittelonr.graphjelont.bipartitelon.selongmelonnt.BipartitelonGraphSelongmelonnt
import scala.collelonction.mutablelon.ListBuffelonr

objelonct FelontchRHSTwelonelontsUtil {
  // gelont RHS twelonelonts givelonn LHS uselonrs
  delonf felontchRHSTwelonelonts(
    uselonrIds: Selonq[Long],
    bipartitelonGraph: BipartitelonGraph
  ): Selonq[Long] = {
    uselonrIds.distinct
      .flatMap { uselonrId =>
        val twelonelontIdsItelonrator = bipartitelonGraph
          .gelontLelonftNodelonelondgelons(uselonrId).asInstancelonOf[MultiSelongmelonntItelonrator[BipartitelonGraphSelongmelonnt]]

        val twelonelontIds = nelonw ListBuffelonr[Long]()
        if (twelonelontIdsItelonrator != null) {
          whilelon (twelonelontIdsItelonrator.hasNelonxt) {
            val rightNodelon = twelonelontIdsItelonrator.nelonxtLong()
            twelonelontIds += rightNodelon
          }
        }
        twelonelontIds.distinct
      }
  }
}
