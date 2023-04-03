packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.visibility.common.UselonrRelonlationshipSourcelon
import com.twittelonr.visibility.felonaturelons._

class RelonlationshipFelonaturelons(
  uselonrRelonlationshipSourcelon: UselonrRelonlationshipSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("relonlationship_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val authorFollowsVielonwelonr =
    scopelondStatsReloncelonivelonr.scopelon(AuthorFollowsVielonwelonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrFollowsAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFollowsAuthor.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorBlocksVielonwelonr =
    scopelondStatsReloncelonivelonr.scopelon(AuthorBlocksVielonwelonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrBlocksAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrBlocksAuthor.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorMutelonsVielonwelonr =
    scopelondStatsReloncelonivelonr.scopelon(AuthorMutelonsVielonwelonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrMutelonsAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrMutelonsAuthor.namelon).countelonr("relonquelonsts")
  privatelon[this] val authorHasRelonportelondVielonwelonr =
    scopelondStatsReloncelonivelonr.scopelon(AuthorRelonportsVielonwelonrAsSpam.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrHasRelonportelondAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrRelonportsAuthorAsSpam.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrMutelonsRelontwelonelontsFromAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrMutelonsRelontwelonelontsFromAuthor.namelon).countelonr("relonquelonsts")

  delonf forAuthorId(
    authorId: Long,
    vielonwelonrId: Option[Long]
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    _.withFelonaturelon(AuthorFollowsVielonwelonr, authorFollowsVielonwelonr(authorId, vielonwelonrId))
      .withFelonaturelon(VielonwelonrFollowsAuthor, vielonwelonrFollowsAuthor(authorId, vielonwelonrId))
      .withFelonaturelon(AuthorBlocksVielonwelonr, authorBlocksVielonwelonr(authorId, vielonwelonrId))
      .withFelonaturelon(VielonwelonrBlocksAuthor, vielonwelonrBlocksAuthor(authorId, vielonwelonrId))
      .withFelonaturelon(AuthorMutelonsVielonwelonr, authorMutelonsVielonwelonr(authorId, vielonwelonrId))
      .withFelonaturelon(VielonwelonrMutelonsAuthor, vielonwelonrMutelonsAuthor(authorId, vielonwelonrId))
      .withFelonaturelon(AuthorRelonportsVielonwelonrAsSpam, authorHasRelonportelondVielonwelonr(authorId, vielonwelonrId))
      .withFelonaturelon(VielonwelonrRelonportsAuthorAsSpam, vielonwelonrHasRelonportelondAuthor(authorId, vielonwelonrId))
      .withFelonaturelon(VielonwelonrMutelonsRelontwelonelontsFromAuthor, vielonwelonrMutelonsRelontwelonelontsFromAuthor(authorId, vielonwelonrId))
  }

  delonf forNoAuthor(): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(AuthorFollowsVielonwelonr, falselon)
      .withConstantFelonaturelon(VielonwelonrFollowsAuthor, falselon)
      .withConstantFelonaturelon(AuthorBlocksVielonwelonr, falselon)
      .withConstantFelonaturelon(VielonwelonrBlocksAuthor, falselon)
      .withConstantFelonaturelon(AuthorMutelonsVielonwelonr, falselon)
      .withConstantFelonaturelon(VielonwelonrMutelonsAuthor, falselon)
      .withConstantFelonaturelon(AuthorRelonportsVielonwelonrAsSpam, falselon)
      .withConstantFelonaturelon(VielonwelonrRelonportsAuthorAsSpam, falselon)
      .withConstantFelonaturelon(VielonwelonrMutelonsRelontwelonelontsFromAuthor, falselon)
  }

  delonf forAuthor(author: Uselonr, vielonwelonrId: Option[Long]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()


    _.withFelonaturelon(AuthorFollowsVielonwelonr, authorFollowsVielonwelonr(author, vielonwelonrId))
      .withFelonaturelon(VielonwelonrFollowsAuthor, vielonwelonrFollowsAuthor(author, vielonwelonrId))
      .withFelonaturelon(AuthorBlocksVielonwelonr, authorBlocksVielonwelonr(author, vielonwelonrId))
      .withFelonaturelon(VielonwelonrBlocksAuthor, vielonwelonrBlocksAuthor(author, vielonwelonrId))
      .withFelonaturelon(AuthorMutelonsVielonwelonr, authorMutelonsVielonwelonr(author, vielonwelonrId))
      .withFelonaturelon(VielonwelonrMutelonsAuthor, vielonwelonrMutelonsAuthor(author, vielonwelonrId))
      .withFelonaturelon(AuthorRelonportsVielonwelonrAsSpam, authorHasRelonportelondVielonwelonr(author.id, vielonwelonrId))
      .withFelonaturelon(VielonwelonrRelonportsAuthorAsSpam, vielonwelonrHasRelonportelondAuthor(author.id, vielonwelonrId))
      .withFelonaturelon(VielonwelonrMutelonsRelontwelonelontsFromAuthor, vielonwelonrMutelonsRelontwelonelontsFromAuthor(author, vielonwelonrId))
  }

  delonf vielonwelonrFollowsAuthor(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(authorId, vielonwelonrId, uselonrRelonlationshipSourcelon.follows, vielonwelonrFollowsAuthor)

  delonf vielonwelonrFollowsAuthor(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      author,
      vielonwelonrId,
      p => p.following,
      uselonrRelonlationshipSourcelon.follows,
      vielonwelonrFollowsAuthor)

  delonf authorFollowsVielonwelonr(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    AuthorVelonrbsVielonwelonr(authorId, vielonwelonrId, uselonrRelonlationshipSourcelon.follows, authorFollowsVielonwelonr)

  delonf authorFollowsVielonwelonr(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    AuthorVelonrbsVielonwelonr(
      author,
      vielonwelonrId,
      p => p.followelondBy,
      uselonrRelonlationshipSourcelon.follows,
      authorFollowsVielonwelonr)

  delonf vielonwelonrBlocksAuthor(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(authorId, vielonwelonrId, uselonrRelonlationshipSourcelon.blocks, vielonwelonrBlocksAuthor)

  delonf vielonwelonrBlocksAuthor(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      author,
      vielonwelonrId,
      p => p.blocking,
      uselonrRelonlationshipSourcelon.blocks,
      vielonwelonrBlocksAuthor)

  delonf authorBlocksVielonwelonr(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(authorId, vielonwelonrId, uselonrRelonlationshipSourcelon.blockelondBy, authorBlocksVielonwelonr)

  delonf authorBlocksVielonwelonr(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      author,
      vielonwelonrId,
      p => p.blockelondBy,
      uselonrRelonlationshipSourcelon.blockelondBy,
      authorBlocksVielonwelonr)

  delonf vielonwelonrMutelonsAuthor(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(authorId, vielonwelonrId, uselonrRelonlationshipSourcelon.mutelons, vielonwelonrMutelonsAuthor)

  delonf vielonwelonrMutelonsAuthor(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      author,
      vielonwelonrId,
      p => p.muting,
      uselonrRelonlationshipSourcelon.mutelons,
      vielonwelonrMutelonsAuthor)

  delonf authorMutelonsVielonwelonr(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(authorId, vielonwelonrId, uselonrRelonlationshipSourcelon.mutelondBy, authorMutelonsVielonwelonr)

  delonf authorMutelonsVielonwelonr(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      author,
      vielonwelonrId,
      p => p.mutelondBy,
      uselonrRelonlationshipSourcelon.mutelondBy,
      authorMutelonsVielonwelonr)

  delonf vielonwelonrHasRelonportelondAuthor(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      authorId,
      vielonwelonrId,
      uselonrRelonlationshipSourcelon.relonportsAsSpam,
      vielonwelonrHasRelonportelondAuthor)

  delonf authorHasRelonportelondVielonwelonr(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      authorId,
      vielonwelonrId,
      uselonrRelonlationshipSourcelon.relonportelondAsSpamBy,
      authorHasRelonportelondVielonwelonr)

  delonf vielonwelonrMutelonsRelontwelonelontsFromAuthor(authorId: UselonrId, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      authorId,
      vielonwelonrId,
      uselonrRelonlationshipSourcelon.noRelontwelonelontsFrom,
      vielonwelonrMutelonsRelontwelonelontsFromAuthor)

  delonf vielonwelonrMutelonsRelontwelonelontsFromAuthor(author: Uselonr, vielonwelonrId: Option[UselonrId]): Stitch[Boolelonan] =
    VielonwelonrVelonrbsAuthor(
      author,
      vielonwelonrId,
      p => p.noRelontwelonelontsFrom,
      uselonrRelonlationshipSourcelon.noRelontwelonelontsFrom,
      vielonwelonrMutelonsRelontwelonelontsFromAuthor)
}
