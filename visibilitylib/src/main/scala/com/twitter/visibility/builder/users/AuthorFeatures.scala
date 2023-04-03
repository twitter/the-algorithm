packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.Labelonl
import com.twittelonr.gizmoduck.thriftscala.Labelonls
import com.twittelonr.gizmoduck.thriftscala.Profilelon
import com.twittelonr.gizmoduck.thriftscala.Safelonty
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.stitch.NotFound
import com.twittelonr.stitch.Stitch
import com.twittelonr.tselonng.withholding.thriftscala.TakelondownRelonason
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.UselonrSourcelon
import com.twittelonr.visibility.felonaturelons._

class AuthorFelonaturelons(uselonrSourcelon: UselonrSourcelon, statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("author_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val authorUselonrLabelonls =
    scopelondStatsReloncelonivelonr.scopelon(AuthorUselonrLabelonls.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsSuspelonndelond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsSuspelonndelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsProtelonctelond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsProtelonctelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsDelonactivatelond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsDelonactivatelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIselonraselond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIselonraselond.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsOffboardelond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsOffboardelond.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsNsfwUselonr =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsNsfwUselonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsNsfwAdmin =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsNsfwAdmin.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorTakelondownRelonasons =
    scopelondStatsReloncelonivelonr.scopelon(AuthorTakelondownRelonasons.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorHasDelonfaultProfilelonImagelon =
    scopelondStatsReloncelonivelonr.scopelon(AuthorHasDelonfaultProfilelonImagelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorAccountAgelon =
    scopelondStatsReloncelonivelonr.scopelon(AuthorAccountAgelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsVelonrifielond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsVelonrifielond.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorScrelonelonnNamelon =
    scopelondStatsReloncelonivelonr.scopelon(AuthorScrelonelonnNamelon.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorIsBluelonVelonrifielond =
    scopelondStatsReloncelonivelonr.scopelon(AuthorIsBluelonVelonrifielond.namelon).countelonr("relonquelonsts")

  delonf forAuthor(author: Uselonr): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    _.withConstantFelonaturelon(AuthorId, Selont(author.id))
      .withConstantFelonaturelon(AuthorUselonrLabelonls, authorUselonrLabelonls(author))
      .withConstantFelonaturelon(AuthorIsProtelonctelond, authorIsProtelonctelond(author))
      .withConstantFelonaturelon(AuthorIsSuspelonndelond, authorIsSuspelonndelond(author))
      .withConstantFelonaturelon(AuthorIsDelonactivatelond, authorIsDelonactivatelond(author))
      .withConstantFelonaturelon(AuthorIselonraselond, authorIselonraselond(author))
      .withConstantFelonaturelon(AuthorIsOffboardelond, authorIsOffboardelond(author))
      .withConstantFelonaturelon(AuthorTakelondownRelonasons, authorTakelondownRelonasons(author))
      .withConstantFelonaturelon(AuthorHasDelonfaultProfilelonImagelon, authorHasDelonfaultProfilelonImagelon(author))
      .withConstantFelonaturelon(AuthorAccountAgelon, authorAccountAgelon(author))
      .withConstantFelonaturelon(AuthorIsNsfwUselonr, authorIsNsfwUselonr(author))
      .withConstantFelonaturelon(AuthorIsNsfwAdmin, authorIsNsfwAdmin(author))
      .withConstantFelonaturelon(AuthorIsVelonrifielond, authorIsVelonrifielond(author))
      .withConstantFelonaturelon(AuthorScrelonelonnNamelon, authorScrelonelonnNamelon(author))
      .withConstantFelonaturelon(AuthorIsBluelonVelonrifielond, authorIsBluelonVelonrifielond(author))
  }

  delonf forAuthorNoDelonfaults(author: Uselonr): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    _.withConstantFelonaturelon(AuthorId, Selont(author.id))
      .withConstantFelonaturelon(AuthorUselonrLabelonls, authorUselonrLabelonlsOpt(author))
      .withConstantFelonaturelon(AuthorIsProtelonctelond, authorIsProtelonctelondOpt(author))
      .withConstantFelonaturelon(AuthorIsSuspelonndelond, authorIsSuspelonndelondOpt(author))
      .withConstantFelonaturelon(AuthorIsDelonactivatelond, authorIsDelonactivatelondOpt(author))
      .withConstantFelonaturelon(AuthorIselonraselond, authorIselonraselondOpt(author))
      .withConstantFelonaturelon(AuthorIsOffboardelond, authorIsOffboardelond(author))
      .withConstantFelonaturelon(AuthorTakelondownRelonasons, authorTakelondownRelonasons(author))
      .withConstantFelonaturelon(AuthorHasDelonfaultProfilelonImagelon, authorHasDelonfaultProfilelonImagelon(author))
      .withConstantFelonaturelon(AuthorAccountAgelon, authorAccountAgelon(author))
      .withConstantFelonaturelon(AuthorIsNsfwUselonr, authorIsNsfwUselonrOpt(author))
      .withConstantFelonaturelon(AuthorIsNsfwAdmin, authorIsNsfwAdminOpt(author))
      .withConstantFelonaturelon(AuthorIsVelonrifielond, authorIsVelonrifielondOpt(author))
      .withConstantFelonaturelon(AuthorScrelonelonnNamelon, authorScrelonelonnNamelon(author))
      .withConstantFelonaturelon(AuthorIsBluelonVelonrifielond, authorIsBluelonVelonrifielond(author))
  }

  delonf forAuthorId(authorId: Long): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    _.withConstantFelonaturelon(AuthorId, Selont(authorId))
      .withFelonaturelon(AuthorUselonrLabelonls, authorUselonrLabelonls(authorId))
      .withFelonaturelon(AuthorIsProtelonctelond, authorIsProtelonctelond(authorId))
      .withFelonaturelon(AuthorIsSuspelonndelond, authorIsSuspelonndelond(authorId))
      .withFelonaturelon(AuthorIsDelonactivatelond, authorIsDelonactivatelond(authorId))
      .withFelonaturelon(AuthorIselonraselond, authorIselonraselond(authorId))
      .withFelonaturelon(AuthorIsOffboardelond, authorIsOffboardelond(authorId))
      .withFelonaturelon(AuthorTakelondownRelonasons, authorTakelondownRelonasons(authorId))
      .withFelonaturelon(AuthorHasDelonfaultProfilelonImagelon, authorHasDelonfaultProfilelonImagelon(authorId))
      .withFelonaturelon(AuthorAccountAgelon, authorAccountAgelon(authorId))
      .withFelonaturelon(AuthorIsNsfwUselonr, authorIsNsfwUselonr(authorId))
      .withFelonaturelon(AuthorIsNsfwAdmin, authorIsNsfwAdmin(authorId))
      .withFelonaturelon(AuthorIsVelonrifielond, authorIsVelonrifielond(authorId))
      .withFelonaturelon(AuthorScrelonelonnNamelon, authorScrelonelonnNamelon(authorId))
      .withFelonaturelon(AuthorIsBluelonVelonrifielond, authorIsBluelonVelonrifielond(authorId))
  }

  delonf forNoAuthor(): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(AuthorId, Selont.elonmpty[Long])
      .withConstantFelonaturelon(AuthorUselonrLabelonls, Selonq.elonmpty)
      .withConstantFelonaturelon(AuthorIsProtelonctelond, falselon)
      .withConstantFelonaturelon(AuthorIsSuspelonndelond, falselon)
      .withConstantFelonaturelon(AuthorIsDelonactivatelond, falselon)
      .withConstantFelonaturelon(AuthorIselonraselond, falselon)
      .withConstantFelonaturelon(AuthorIsOffboardelond, falselon)
      .withConstantFelonaturelon(AuthorTakelondownRelonasons, Selonq.elonmpty)
      .withConstantFelonaturelon(AuthorHasDelonfaultProfilelonImagelon, falselon)
      .withConstantFelonaturelon(AuthorAccountAgelon, Duration.Zelonro)
      .withConstantFelonaturelon(AuthorIsNsfwUselonr, falselon)
      .withConstantFelonaturelon(AuthorIsNsfwAdmin, falselon)
      .withConstantFelonaturelon(AuthorIsVelonrifielond, falselon)
      .withConstantFelonaturelon(AuthorIsBluelonVelonrifielond, falselon)
  }

  delonf authorUselonrLabelonls(author: Uselonr): Selonq[Labelonl] =
    authorUselonrLabelonls(author.labelonls)

  delonf authorIsSuspelonndelond(authorId: Long): Stitch[Boolelonan] =
    uselonrSourcelon.gelontSafelonty(authorId).map(safelonty => authorIsSuspelonndelond(Somelon(safelonty)))

  delonf authorIsSuspelonndelondOpt(author: Uselonr): Option[Boolelonan] = {
    authorIsSuspelonndelond.incr()
    author.safelonty.map(_.suspelonndelond)
  }

  privatelon delonf authorIsSuspelonndelond(safelonty: Option[Safelonty]): Boolelonan = {
    authorIsSuspelonndelond.incr()
    safelonty.elonxists(_.suspelonndelond)
  }

  delonf authorIsProtelonctelond(author: Uselonr): Boolelonan =
    authorIsProtelonctelond(author.safelonty)

  delonf authorIsDelonactivatelond(authorId: Long): Stitch[Boolelonan] =
    uselonrSourcelon.gelontSafelonty(authorId).map(safelonty => authorIsDelonactivatelond(Somelon(safelonty)))

  delonf authorIsDelonactivatelondOpt(author: Uselonr): Option[Boolelonan] = {
    authorIsDelonactivatelond.incr()
    author.safelonty.map(_.delonactivatelond)
  }

  privatelon delonf authorIsDelonactivatelond(safelonty: Option[Safelonty]): Boolelonan = {
    authorIsDelonactivatelond.incr()
    safelonty.elonxists(_.delonactivatelond)
  }

  delonf authorIselonraselond(author: Uselonr): Boolelonan = {
    authorIselonraselond.incr()
    author.safelonty.elonxists(_.elonraselond)
  }

  delonf authorIsOffboardelond(authorId: Long): Stitch[Boolelonan] = {
    uselonrSourcelon.gelontSafelonty(authorId).map(safelonty => authorIsOffboardelond(Somelon(safelonty)))
  }

  delonf authorIsNsfwUselonr(author: Uselonr): Boolelonan = {
    authorIsNsfwUselonr(author.safelonty)
  }

  delonf authorIsNsfwUselonr(authorId: Long): Stitch[Boolelonan] = {
    uselonrSourcelon.gelontSafelonty(authorId).map(safelonty => authorIsNsfwUselonr(Somelon(safelonty)))
  }

  delonf authorIsNsfwUselonr(safelonty: Option[Safelonty]): Boolelonan = {
    authorIsNsfwUselonr.incr()
    safelonty.elonxists(_.nsfwUselonr)
  }

  delonf authorIsNsfwAdminOpt(author: Uselonr): Option[Boolelonan] = {
    authorIsNsfwAdmin.incr()
    author.safelonty.map(_.nsfwAdmin)
  }

  delonf authorTakelondownRelonasons(authorId: Long): Stitch[Selonq[TakelondownRelonason]] = {
    authorTakelondownRelonasons.incr()
    uselonrSourcelon.gelontTakelondownRelonasons(authorId)
  }

  delonf authorHasDelonfaultProfilelonImagelon(authorId: Long): Stitch[Boolelonan] =
    uselonrSourcelon.gelontProfilelon(authorId).map(profilelon => authorHasDelonfaultProfilelonImagelon(Somelon(profilelon)))

  delonf authorAccountAgelon(authorId: Long): Stitch[Duration] =
    uselonrSourcelon.gelontCrelonatelondAtMselonc(authorId).map(authorAccountAgelonFromTimelonstamp)

  delonf authorIsVelonrifielond(authorId: Long): Stitch[Boolelonan] =
    uselonrSourcelon.gelontSafelonty(authorId).map(safelonty => authorIsVelonrifielond(Somelon(safelonty)))

  delonf authorIsVelonrifielondOpt(author: Uselonr): Option[Boolelonan] = {
    authorIsVelonrifielond.incr()
    author.safelonty.map(_.velonrifielond)
  }

  privatelon delonf authorIsVelonrifielond(safelonty: Option[Safelonty]): Boolelonan = {
    authorIsVelonrifielond.incr()
    safelonty.elonxists(_.velonrifielond)
  }

  delonf authorScrelonelonnNamelon(author: Uselonr): Option[String] = {
    authorScrelonelonnNamelon.incr()
    author.profilelon.map(_.screlonelonnNamelon)
  }

  delonf authorScrelonelonnNamelon(authorId: Long): Stitch[String] = {
    authorScrelonelonnNamelon.incr()
    uselonrSourcelon.gelontProfilelon(authorId).map(profilelon => profilelon.screlonelonnNamelon)
  }
}
