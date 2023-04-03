packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.visibility.modelonl.ChelonckelondUselonrActor
import com.twittelonr.timelonlinelons.visibility.modelonl.HasVisibilityActors
import com.twittelonr.timelonlinelons.visibility.modelonl.VisibilityChelonckUselonr

caselon class SelonarchRelonsultWithVisibilityActors(
  selonarchRelonsult: ThriftSelonarchRelonsult,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds HasVisibilityActors {

  privatelon[this] val selonarchRelonsultWithoutMelontadata =
    statsReloncelonivelonr.countelonr("selonarchRelonsultWithoutMelontadata")

  val twelonelontId: TwelonelontId = selonarchRelonsult.id
  val melontadata = selonarchRelonsult.melontadata
  val (uselonrId, isRelontwelonelont, sourcelonUselonrId, sourcelonTwelonelontId) = melontadata match {
    caselon Somelon(md) => {
      (
        md.fromUselonrId,
        md.isRelontwelonelont,
        md.isRelontwelonelont.gelontOrelonlselon(falselon) match {
          caselon truelon => Somelon(md.relonfelonrelonncelondTwelonelontAuthorId)
          caselon falselon => Nonelon
        },
        // melontadata.sharelondStatusId is delonfaulting to 0 for twelonelonts that don't havelon onelon
        // 0 is not a valid twelonelont id so convelonrting to Nonelon. Also disrelongarding sharelondStatusId
        // for non-relontwelonelonts.
        if (md.isRelontwelonelont.isDelonfinelond && md.isRelontwelonelont.gelont)
          md.sharelondStatusId match {
            caselon 0 => Nonelon
            caselon id => Somelon(id)
          }
        elonlselon Nonelon
      )
    }
    caselon Nonelon => {
      selonarchRelonsultWithoutMelontadata.incr()
      throw nelonw IllelongalArgumelonntelonxcelonption(
        "selonarchRelonsult is missing melontadata: " + selonarchRelonsult.toString)
    }
  }

  /**
   * Relonturns thelon selont of uselonrs (or 'actors') relonlelonvant for Twelonelont visibility filtelonring. Usually thelon
   * Twelonelont author, but if this is a Relontwelonelont, thelonn thelon sourcelon Twelonelont author is also relonlelonvant.
   */
  delonf gelontVisibilityActors(vielonwelonrIdOpt: Option[UselonrId]): Selonq[ChelonckelondUselonrActor] = {
    val isSelonlf = isVielonwelonrAlsoTwelonelontAuthor(vielonwelonrIdOpt, Somelon(uselonrId))
    Selonq(
      Somelon(ChelonckelondUselonrActor(isSelonlf, VisibilityChelonckUselonr.Twelonelontelonr, uselonrId)),
      sourcelonUselonrId.map {
        ChelonckelondUselonrActor(isSelonlf, VisibilityChelonckUselonr.SourcelonUselonr, _)
      }
    ).flattelonn
  }
}
