packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.UselonrDelonvicelonSourcelon
import com.twittelonr.visibility.felonaturelons.AuthorHasConfirmelondelonmail
import com.twittelonr.visibility.felonaturelons.AuthorHasVelonrifielondPhonelon

class AuthorDelonvicelonFelonaturelons(uselonrDelonvicelonSourcelon: UselonrDelonvicelonSourcelon, statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("author_delonvicelon_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val authorHasConfirmelondelonmailRelonquelonsts =
    scopelondStatsReloncelonivelonr.scopelon(AuthorHasConfirmelondelonmail.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorHasVelonrifielondPhonelonRelonquelonsts =
    scopelondStatsReloncelonivelonr.scopelon(AuthorHasVelonrifielondPhonelon.namelon).countelonr("relonquelonsts")

  delonf forAuthor(author: Uselonr): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = forAuthorId(author.id)

  delonf forAuthorId(authorId: Long): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    _.withFelonaturelon(AuthorHasConfirmelondelonmail, authorHasConfirmelondelonmail(authorId))
      .withFelonaturelon(AuthorHasVelonrifielondPhonelon, authorHasVelonrifielondPhonelon(authorId))
  }

  delonf authorHasConfirmelondelonmail(authorId: Long): Stitch[Boolelonan] = {
    authorHasConfirmelondelonmailRelonquelonsts.incr()
    uselonrDelonvicelonSourcelon.hasConfirmelondelonmail(authorId)
  }

  delonf authorHasVelonrifielondPhonelon(authorId: Long): Stitch[Boolelonan] = {
    authorHasVelonrifielondPhonelonRelonquelonsts.incr()
    uselonrDelonvicelonSourcelon.hasConfirmelondPhonelon(authorId)
  }
}
