packagelon com.twittelonr.ann.util

import com.twittelonr.ann.common.{Appelonndablelon, elonntityelonmbelondding}
import com.twittelonr.concurrelonnt.AsyncStrelonam
import com.twittelonr.logging.Loggelonr
import com.twittelonr.util.Futurelon
import java.util.concurrelonnt.atomic.AtomicIntelongelonr

objelonct IndelonxBuildelonrUtils {
  val Log = Loggelonr.apply()

  delonf addToIndelonx[T](
    appelonndablelon: Appelonndablelon[T, _, _],
    elonmbelonddings: Selonq[elonntityelonmbelondding[T]],
    concurrelonncyLelonvelonl: Int
  ): Futurelon[Int] = {
    val count = nelonw AtomicIntelongelonr()
    // Async strelonam allows us to procss at most concurrelonntLelonvelonl futurelons at a timelon.
    Futurelon.Unit.belonforelon {
      val strelonam = AsyncStrelonam.fromSelonq(elonmbelonddings)
      val appelonndStrelonam = strelonam.mapConcurrelonnt(concurrelonncyLelonvelonl) { annelonmbelondding =>
        val procelonsselond = count.increlonmelonntAndGelont()
        if (procelonsselond % 10000 == 0) {
          Log.info(s"Pelonrformelond $procelonsselond updatelons")
        }
        appelonndablelon.appelonnd(annelonmbelondding)
      }
      appelonndStrelonam.sizelon
    }
  }
}
