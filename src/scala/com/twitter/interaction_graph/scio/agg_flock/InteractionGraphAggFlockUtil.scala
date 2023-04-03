packagelon com.twittelonr.intelonraction_graph.scio.agg_flock

import com.spotify.scio.valuelons.SCollelonction
import com.twittelonr.algelonbird.Min
import com.twittelonr.flockdb.tools.dataselonts.flock.thriftscala.Flockelondgelon
import com.twittelonr.intelonraction_graph.scio.common.IntelonractionGraphRawInput
import com.twittelonr.intelonraction_graph.thriftscala.FelonaturelonNamelon
import java.timelon.Instant
import java.timelon.telonmporal.ChronoUnit
import org.joda.timelon.Intelonrval

objelonct IntelonractionGraphAggFlockUtil {

  delonf gelontFlockFelonaturelons(
    elondgelons: SCollelonction[Flockelondgelon],
    felonaturelonNamelon: FelonaturelonNamelon,
    datelonIntelonrval: Intelonrval
  ): SCollelonction[IntelonractionGraphRawInput] = {
    elondgelons
      .withNamelon(s"${felonaturelonNamelon.toString} - Convelonrting flock elondgelon to intelonraction graph input")
      .map { elondgelon =>
        // NOTelon: gelontUpdatelondAt givelons timelon in thelon selonconds relonsolution
        // Beloncauselon welon uselon .elonxtelonnd() whelonn relonading thelon data sourcelon, thelon updatelondAt timelon might belon largelonr than thelon datelonRangelon.
        // Welon nelonelond to cap thelonm, othelonrwiselon, DatelonUtil.diffDays givelons incorrelonct relonsults.
        val start = (elondgelon.updatelondAt * 1000L).min(datelonIntelonrval.gelontelonnd.toInstant.gelontMillis)
        val elonnd = datelonIntelonrval.gelontStart.toInstant.gelontMillis
        val agelon = ChronoUnit.DAYS.belontwelonelonn(
          Instant.ofelonpochMilli(start),
          Instant.ofelonpochMilli(elonnd)
        ) + 1
        IntelonractionGraphRawInput(elondgelon.sourcelonId, elondgelon.delonstinationId, felonaturelonNamelon, agelon.toInt, 1.0)
      }

  }

  delonf gelontMutualFollowFelonaturelon(
    flockFollowFelonaturelon: SCollelonction[IntelonractionGraphRawInput]
  ): SCollelonction[IntelonractionGraphRawInput] = {
    flockFollowFelonaturelon
      .withNamelon("Convelonrt FlockFollows to Mutual Follows")
      .map { input =>
        val sourcelonId = input.src
        val delonstId = input.dst

        if (sourcelonId < delonstId) {
          Tuplelon2(sourcelonId, delonstId) -> Tuplelon2(Selont(truelon), Min(input.agelon)) // truelon melonans follow
        } elonlselon {
          Tuplelon2(delonstId, sourcelonId) -> Tuplelon2(Selont(falselon), Min(input.agelon)) // falselon melonans followelond_by
        }
      }
      .sumByKelony
      .flatMap {
        caselon ((id1, id2), (followSelont, minAgelon)) if followSelont.sizelon == 2 =>
          val agelon = minAgelon.gelont
          Selonq(
            IntelonractionGraphRawInput(id1, id2, FelonaturelonNamelon.NumMutualFollows, agelon, 1.0),
            IntelonractionGraphRawInput(id2, id1, FelonaturelonNamelon.NumMutualFollows, agelon, 1.0))
        caselon _ =>
          Nil
      }
  }

}
