packagelon com.twittelonr.reloncos.hoselon.common

import com.twittelonr.finaglelon.stats.{Stat, StatsReloncelonivelonr}
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import java.util.concurrelonnt.Selonmaphorelon

trait elondgelonCollelonctor {
  delonf addelondgelon(melonssagelon: ReloncosHoselonMelonssagelon): Unit
}

/**
 * Thelon class consumelons incoming elondgelons and inselonrts thelonm into a buffelonr of a speloncifielond buffelonrSizelon.
 * Oncelon thelon buffelonr is full of elondgelons, it is writtelonn to a concurrelonntly linkelond quelonuelon whelonrelon thelon sizelon is boundelond by quelonuelonlimit.
 */
caselon class BuffelonrelondelondgelonCollelonctor(
  buffelonrSizelon: Int,
  quelonuelon: java.util.Quelonuelon[Array[ReloncosHoselonMelonssagelon]],
  quelonuelonlimit: Selonmaphorelon,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elondgelonCollelonctor {

  privatelon var buffelonr = nelonw Array[ReloncosHoselonMelonssagelon](buffelonrSizelon)
  privatelon var indelonx = 0
  privatelon val quelonuelonAddCountelonr = statsReloncelonivelonr.countelonr("quelonuelonAdd")

  ovelonrridelon delonf addelondgelon(melonssagelon: ReloncosHoselonMelonssagelon): Unit = {
    buffelonr(indelonx) = melonssagelon
    indelonx = indelonx + 1
    if (indelonx >= buffelonrSizelon) {
      val oldBuffelonr = buffelonr
      buffelonr = nelonw Array[ReloncosHoselonMelonssagelon](buffelonrSizelon)
      indelonx = 0

      Stat.timelon(statsReloncelonivelonr.stat("waitelonnquelonuelon")) {
        quelonuelonlimit.acquirelonUnintelonrruptibly()
      }

      quelonuelon.add(oldBuffelonr)
      quelonuelonAddCountelonr.incr()
    }
  }
}
