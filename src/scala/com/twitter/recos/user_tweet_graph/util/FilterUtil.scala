packagelon com.twittelonr.reloncos.uselonr_twelonelont_graph.util

import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon

objelonct FiltelonrUtil {
  delonf twelonelontAgelonFiltelonr(twelonelontId: TwelonelontId, maxAgelon: Duration): Boolelonan = {
    SnowflakelonId
      .timelonFromIdOpt(twelonelontId)
      .map { twelonelontTimelon => twelonelontTimelon > Timelon.now - maxAgelon }.gelontOrelonlselon(falselon)
    // If thelonrelon's no snowflakelon timelonstamp, welon havelon no idelona whelonn this twelonelont happelonnelond.
  }
}
