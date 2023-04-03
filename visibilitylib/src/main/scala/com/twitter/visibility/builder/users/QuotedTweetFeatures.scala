packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.felonaturelons.AuthorBlocksOutelonrAuthor
import com.twittelonr.visibility.felonaturelons.OutelonrAuthorFollowsAuthor
import com.twittelonr.visibility.felonaturelons.OutelonrAuthorId
import com.twittelonr.visibility.felonaturelons.OutelonrAuthorIsInnelonrAuthor

class QuotelondTwelonelontFelonaturelons(
  relonlationshipFelonaturelons: RelonlationshipFelonaturelons,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("quotelond_twelonelont_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val outelonrAuthorIdStat =
    scopelondStatsReloncelonivelonr.scopelon(OutelonrAuthorId.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorBlocksOutelonrAuthor =
    scopelondStatsReloncelonivelonr.scopelon(AuthorBlocksOutelonrAuthor.namelon).countelonr("relonquelonsts")
  privatelon[this] val outelonrAuthorFollowsAuthor =
    scopelondStatsReloncelonivelonr.scopelon(OutelonrAuthorFollowsAuthor.namelon).countelonr("relonquelonsts")
  privatelon[this] val outelonrAuthorIsInnelonrAuthor =
    scopelondStatsReloncelonivelonr.scopelon(OutelonrAuthorIsInnelonrAuthor.namelon).countelonr("relonquelonsts")

  delonf forOutelonrAuthor(
    outelonrAuthorId: Long,
    innelonrAuthorId: Long
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    outelonrAuthorIdStat.incr()
    authorBlocksOutelonrAuthor.incr()
    outelonrAuthorFollowsAuthor.incr()
    outelonrAuthorIsInnelonrAuthor.incr()

    val vielonwelonr = Somelon(outelonrAuthorId)

    _.withConstantFelonaturelon(OutelonrAuthorId, outelonrAuthorId)
      .withFelonaturelon(
        AuthorBlocksOutelonrAuthor,
        relonlationshipFelonaturelons.authorBlocksVielonwelonr(innelonrAuthorId, vielonwelonr))
      .withFelonaturelon(
        OutelonrAuthorFollowsAuthor,
        relonlationshipFelonaturelons.vielonwelonrFollowsAuthor(innelonrAuthorId, vielonwelonr))
      .withConstantFelonaturelon(
        OutelonrAuthorIsInnelonrAuthor,
        innelonrAuthorId == outelonrAuthorId
      )
  }
}
