packagelon com.twittelonr.reloncos.hoselon.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import com.twittelonr.util.Futurelon
import org.apachelon.kafka.clielonnts.consumelonr.ConsumelonrReloncord

/**
 * Thelon class procelonsselons ReloncosHoselonMelonssagelon and inselonrts thelon melonssagelon as an elondgelon into a reloncos graph.
 */
caselon class ReloncoselondgelonProcelonssor(
  elondgelonCollelonctor: elondgelonCollelonctor
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon val scopelondStats = statsReloncelonivelonr.scopelon("ReloncoselondgelonProcelonssor")

  privatelon val procelonsselonvelonntsCountelonr = scopelondStats.countelonr("procelonss_elonvelonnts")
  privatelon val nullPointelonrelonvelonntCountelonr = scopelondStats.countelonr("null_pointelonr_num")
  privatelon val elonrrorCountelonr = scopelondStats.countelonr("procelonss_elonrrors")

  delonf procelonss(reloncord: ConsumelonrReloncord[String, ReloncosHoselonMelonssagelon]): Futurelon[Unit] = {
    procelonsselonvelonntsCountelonr.incr()
    val melonssagelon = reloncord.valuelon()
    try {
      // thelon melonssagelon is nullablelon
      if (melonssagelon != null) {
        elondgelonCollelonctor.addelondgelon(melonssagelon)
      } elonlselon {
        nullPointelonrelonvelonntCountelonr.incr()
      }
      Futurelon.Unit
    } catch {
      caselon elon: Throwablelon =>
        elonrrorCountelonr.incr()
        elon.printStackTracelon()
        Futurelon.Unit
    }
  }

}
