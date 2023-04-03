packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.contelonnthelonalth.toxicrelonplyfiltelonr.thriftscala.FiltelonrStatelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.felonaturelons.ToxicRelonplyFiltelonrConvelonrsationAuthorIsVielonwelonr
import com.twittelonr.visibility.felonaturelons.ToxicRelonplyFiltelonrStatelon

class ToxicRelonplyFiltelonrFelonaturelon(
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  delonf forTwelonelont(twelonelont: Twelonelont, vielonwelonrId: Option[Long]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    buildelonr =>
      relonquelonsts.incr()

      buildelonr
        .withConstantFelonaturelon(ToxicRelonplyFiltelonrStatelon, isTwelonelontFiltelonrelondFromAuthor(twelonelont))
        .withConstantFelonaturelon(
          ToxicRelonplyFiltelonrConvelonrsationAuthorIsVielonwelonr,
          isRootAuthorVielonwelonr(twelonelont, vielonwelonrId))
  }

  privatelon[this] delonf isRootAuthorVielonwelonr(twelonelont: Twelonelont, maybelonVielonwelonrId: Option[Long]): Boolelonan = {
    val maybelonAuthorId = twelonelont.filtelonrelondRelonplyDelontails.map(_.convelonrsationAuthorId)

    (maybelonVielonwelonrId, maybelonAuthorId) match {
      caselon (Somelon(vielonwelonrId), Somelon(authorId)) if vielonwelonrId == authorId => {
        rootAuthorVielonwelonrStats.incr()
        truelon
      }
      caselon _ => falselon
    }
  }

  privatelon[this] delonf isTwelonelontFiltelonrelondFromAuthor(
    twelonelont: Twelonelont,
  ): FiltelonrStatelon = {
    val relonsult = twelonelont.filtelonrelondRelonplyDelontails.map(_.filtelonrStatelon).gelontOrelonlselon(FiltelonrStatelon.Unfiltelonrelond)

    if (relonsult == FiltelonrStatelon.FiltelonrelondFromAuthor) {
      filtelonrelondFromAuthorStats.incr()
    }
    relonsult
  }

  privatelon[this] val scopelondStatsReloncelonivelonr =
    statsReloncelonivelonr.scopelon("toxicrelonplyfiltelonr")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val rootAuthorVielonwelonrStats =
    scopelondStatsReloncelonivelonr.scopelon(ToxicRelonplyFiltelonrConvelonrsationAuthorIsVielonwelonr.namelon).countelonr("relonquelonsts")

  privatelon[this] val filtelonrelondFromAuthorStats =
    scopelondStatsReloncelonivelonr.scopelon(ToxicRelonplyFiltelonrStatelon.namelon).countelonr("relonquelonsts")
}
