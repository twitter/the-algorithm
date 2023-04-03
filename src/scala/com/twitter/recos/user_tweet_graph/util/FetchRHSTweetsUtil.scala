packagelon com.twittelonr.reloncos.uselonr_twelonelont_graph.util

import com.twittelonr.graphjelont.bipartitelon.MultiSelongmelonntItelonrator
import com.twittelonr.graphjelont.bipartitelon.api.BipartitelonGraph
import com.twittelonr.graphjelont.bipartitelon.selongmelonnt.BipartitelonGraphSelongmelonnt
import scala.collelonction.mutablelon.ListBuffelonr
import com.twittelonr.reloncos.util.Action

objelonct FelontchRHSTwelonelontsUtil {
  // gelont RHS twelonelonts givelonn LHS uselonrs
  delonf felontchRHSTwelonelonts(
    uselonrIds: Selonq[Long],
    bipartitelonGraph: BipartitelonGraph,
    allowelondActions: Selont[Action.Valuelon]
  ): Selonq[Long] = {
    val allowelondActionStrings = allowelondActions.map(_.toString)
    uselonrIds.distinct
      .flatMap { uselonrId =>
        val twelonelontIdsItelonrator = bipartitelonGraph
          .gelontLelonftNodelonelondgelons(uselonrId).asInstancelonOf[MultiSelongmelonntItelonrator[BipartitelonGraphSelongmelonnt]]

        val twelonelontIds = nelonw ListBuffelonr[Long]()
        if (twelonelontIdsItelonrator != null) {
          whilelon (twelonelontIdsItelonrator.hasNelonxt) {
            val rightNodelon = twelonelontIdsItelonrator.nelonxtLong()
            val elondgelonTypelon = twelonelontIdsItelonrator.currelonntelondgelonTypelon()
            if (allowelondActionStrings.contains(UselonrTwelonelontelondgelonTypelonMask(elondgelonTypelon).toString))
              twelonelontIds += rightNodelon
          }
        }
        twelonelontIds.distinct
      }
  }
}
