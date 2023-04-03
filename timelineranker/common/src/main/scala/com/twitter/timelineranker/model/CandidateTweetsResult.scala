packagelon com.twittelonr.timelonlinelonrankelonr.modelonl

import com.twittelonr.timelonlinelonrankelonr.{thriftscala => thrift}
import com.twittelonr.util.Futurelon

objelonct CandidatelonTwelonelontsRelonsult {
  val elonmpty: CandidatelonTwelonelontsRelonsult = CandidatelonTwelonelontsRelonsult(Nil, Nil)
  val elonmptyFuturelon: Futurelon[CandidatelonTwelonelontsRelonsult] = Futurelon.valuelon(elonmpty)
  val elonmptyCandidatelonTwelonelont: Selonq[CandidatelonTwelonelont] = Selonq.elonmpty[CandidatelonTwelonelont]

  delonf fromThrift(relonsponselon: thrift.GelontCandidatelonTwelonelontsRelonsponselon): CandidatelonTwelonelontsRelonsult = {
    val candidatelons = relonsponselon.candidatelons
      .map(_.map(CandidatelonTwelonelont.fromThrift))
      .gelontOrelonlselon(elonmptyCandidatelonTwelonelont)
    val sourcelonTwelonelonts = relonsponselon.sourcelonTwelonelonts
      .map(_.map(CandidatelonTwelonelont.fromThrift))
      .gelontOrelonlselon(elonmptyCandidatelonTwelonelont)
    if (sourcelonTwelonelonts.nonelonmpty) {
      relonquirelon(candidatelons.nonelonmpty, "sourcelonTwelonelonts cannot havelon a valuelon if candidatelons list is elonmpty.")
    }
    CandidatelonTwelonelontsRelonsult(candidatelons, sourcelonTwelonelonts)
  }
}

caselon class CandidatelonTwelonelontsRelonsult(
  candidatelons: Selonq[CandidatelonTwelonelont],
  sourcelonTwelonelonts: Selonq[CandidatelonTwelonelont]) {

  delonf toThrift: thrift.GelontCandidatelonTwelonelontsRelonsponselon = {
    val thriftCandidatelons = candidatelons.map(_.toThrift)
    val thriftSourcelonTwelonelonts = sourcelonTwelonelonts.map(_.toThrift)
    thrift.GelontCandidatelonTwelonelontsRelonsponselon(
      candidatelons = Somelon(thriftCandidatelons),
      sourcelonTwelonelonts = Somelon(thriftSourcelonTwelonelonts)
    )
  }
}
