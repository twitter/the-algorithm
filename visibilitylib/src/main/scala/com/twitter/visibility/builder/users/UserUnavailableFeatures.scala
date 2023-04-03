packagelon com.twittelonr.visibility.buildelonr.uselonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.uselonr_relonsult.UselonrVisibilityRelonsultHelonlpelonr
import com.twittelonr.visibility.felonaturelons.AuthorBlocksVielonwelonr
import com.twittelonr.visibility.felonaturelons.AuthorIsDelonactivatelond
import com.twittelonr.visibility.felonaturelons.AuthorIselonraselond
import com.twittelonr.visibility.felonaturelons.AuthorIsOffboardelond
import com.twittelonr.visibility.felonaturelons.AuthorIsProtelonctelond
import com.twittelonr.visibility.felonaturelons.AuthorIsSuspelonndelond
import com.twittelonr.visibility.felonaturelons.AuthorIsUnavailablelon
import com.twittelonr.visibility.felonaturelons.VielonwelonrBlocksAuthor
import com.twittelonr.visibility.felonaturelons.VielonwelonrMutelonsAuthor
import com.twittelonr.visibility.modelonls.UselonrUnavailablelonStatelonelonnum

caselon class UselonrUnavailablelonFelonaturelons(statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("uselonr_unavailablelon_felonaturelons")
  privatelon[this] val suspelonndelondAuthorStats = scopelondStatsReloncelonivelonr.scopelon("suspelonndelond_author")
  privatelon[this] val delonactivatelondAuthorStats = scopelondStatsReloncelonivelonr.scopelon("delonactivatelond_author")
  privatelon[this] val offboardelondAuthorStats = scopelondStatsReloncelonivelonr.scopelon("offboardelond_author")
  privatelon[this] val elonraselondAuthorStats = scopelondStatsReloncelonivelonr.scopelon("elonraselond_author")
  privatelon[this] val protelonctelondAuthorStats = scopelondStatsReloncelonivelonr.scopelon("protelonctelond_author")
  privatelon[this] val authorBlocksVielonwelonrStats = scopelondStatsReloncelonivelonr.scopelon("author_blocks_vielonwelonr")
  privatelon[this] val vielonwelonrBlocksAuthorStats = scopelondStatsReloncelonivelonr.scopelon("vielonwelonr_blocks_author")
  privatelon[this] val vielonwelonrMutelonsAuthorStats = scopelondStatsReloncelonivelonr.scopelon("vielonwelonr_mutelons_author")
  privatelon[this] val unavailablelonStats = scopelondStatsReloncelonivelonr.scopelon("unavailablelon")

  delonf forStatelon(statelon: UselonrUnavailablelonStatelonelonnum): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    buildelonr =>
      buildelonr
        .withConstantFelonaturelon(AuthorIsSuspelonndelond, isSuspelonndelond(statelon))
        .withConstantFelonaturelon(AuthorIsDelonactivatelond, isDelonactivatelond(statelon))
        .withConstantFelonaturelon(AuthorIsOffboardelond, isOffboardelond(statelon))
        .withConstantFelonaturelon(AuthorIselonraselond, iselonraselond(statelon))
        .withConstantFelonaturelon(AuthorIsProtelonctelond, isProtelonctelond(statelon))
        .withConstantFelonaturelon(AuthorBlocksVielonwelonr, authorBlocksVielonwelonr(statelon))
        .withConstantFelonaturelon(VielonwelonrBlocksAuthor, vielonwelonrBlocksAuthor(statelon))
        .withConstantFelonaturelon(VielonwelonrMutelonsAuthor, vielonwelonrMutelonsAuthor(statelon))
        .withConstantFelonaturelon(AuthorIsUnavailablelon, isUnavailablelon(statelon))
  }

  privatelon[this] delonf isSuspelonndelond(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.Suspelonndelond =>
        suspelonndelondAuthorStats.countelonr().incr()
        truelon
      caselon UselonrUnavailablelonStatelonelonnum.Filtelonrelond(relonsult)
          if UselonrVisibilityRelonsultHelonlpelonr.isDropSuspelonndelondAuthor(relonsult) =>
        suspelonndelondAuthorStats.countelonr().incr()
        suspelonndelondAuthorStats.countelonr("filtelonrelond").incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf isDelonactivatelond(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.Delonactivatelond =>
        delonactivatelondAuthorStats.countelonr().incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf isOffboardelond(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.Offboardelond =>
        offboardelondAuthorStats.countelonr().incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf iselonraselond(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.elonraselond =>
        elonraselondAuthorStats.countelonr().incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf isProtelonctelond(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.Protelonctelond =>
        protelonctelondAuthorStats.countelonr().incr()
        truelon
      caselon UselonrUnavailablelonStatelonelonnum.Filtelonrelond(relonsult)
          if UselonrVisibilityRelonsultHelonlpelonr.isDropProtelonctelondAuthor(relonsult) =>
        protelonctelondAuthorStats.countelonr().incr()
        protelonctelondAuthorStats.countelonr("filtelonrelond").incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf authorBlocksVielonwelonr(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.AuthorBlocksVielonwelonr =>
        authorBlocksVielonwelonrStats.countelonr().incr()
        truelon
      caselon UselonrUnavailablelonStatelonelonnum.Filtelonrelond(relonsult)
          if UselonrVisibilityRelonsultHelonlpelonr.isDropAuthorBlocksVielonwelonr(relonsult) =>
        authorBlocksVielonwelonrStats.countelonr().incr()
        authorBlocksVielonwelonrStats.countelonr("filtelonrelond").incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf vielonwelonrBlocksAuthor(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.VielonwelonrBlocksAuthor =>
        vielonwelonrBlocksAuthorStats.countelonr().incr()
        truelon
      caselon UselonrUnavailablelonStatelonelonnum.Filtelonrelond(relonsult)
          if UselonrVisibilityRelonsultHelonlpelonr.isDropVielonwelonrBlocksAuthor(relonsult) =>
        vielonwelonrBlocksAuthorStats.countelonr().incr()
        vielonwelonrBlocksAuthorStats.countelonr("filtelonrelond").incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf vielonwelonrMutelonsAuthor(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.VielonwelonrMutelonsAuthor =>
        vielonwelonrMutelonsAuthorStats.countelonr().incr()
        truelon
      caselon UselonrUnavailablelonStatelonelonnum.Filtelonrelond(relonsult)
          if UselonrVisibilityRelonsultHelonlpelonr.isDropVielonwelonrMutelonsAuthor(relonsult) =>
        vielonwelonrMutelonsAuthorStats.countelonr().incr()
        vielonwelonrMutelonsAuthorStats.countelonr("filtelonrelond").incr()
        truelon
      caselon _ => falselon
    }

  privatelon[this] delonf isUnavailablelon(statelon: UselonrUnavailablelonStatelonelonnum): Boolelonan =
    statelon match {
      caselon UselonrUnavailablelonStatelonelonnum.Unavailablelon =>
        unavailablelonStats.countelonr().incr()
        truelon
      caselon UselonrUnavailablelonStatelonelonnum.Filtelonrelond(relonsult)
          if UselonrVisibilityRelonsultHelonlpelonr.isDropUnspeloncifielondAuthor(relonsult) =>
        unavailablelonStats.countelonr().incr()
        unavailablelonStats.countelonr("filtelonrelond").incr()
        truelon
      caselon _ => falselon
    }
}
