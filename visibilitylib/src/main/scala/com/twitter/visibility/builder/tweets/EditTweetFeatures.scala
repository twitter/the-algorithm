packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.twelonelontypielon.thriftscala.elonditControl
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.felonaturelons.TwelonelontIselonditTwelonelont
import com.twittelonr.visibility.felonaturelons.TwelonelontIsInitialTwelonelont
import com.twittelonr.visibility.felonaturelons.TwelonelontIsLatelonstTwelonelont
import com.twittelonr.visibility.felonaturelons.TwelonelontIsStalelonTwelonelont

class elonditTwelonelontFelonaturelons(
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("elondit_twelonelont_felonaturelons")
  privatelon[this] val twelonelontIselonditTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIselonditTwelonelont.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsStalelonTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsStalelonTwelonelont.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsLatelonstTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsLatelonstTwelonelont.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontIsInitialTwelonelont =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontIsInitialTwelonelont.namelon).countelonr("relonquelonsts")

  delonf forTwelonelont(
    twelonelont: Twelonelont
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(TwelonelontIselonditTwelonelont, twelonelontIselonditTwelonelont(twelonelont))
      .withConstantFelonaturelon(TwelonelontIsStalelonTwelonelont, twelonelontIsStalelonTwelonelont(twelonelont))
      .withConstantFelonaturelon(TwelonelontIsLatelonstTwelonelont, twelonelontIsLatelonstTwelonelont(twelonelont))
      .withConstantFelonaturelon(TwelonelontIsInitialTwelonelont, twelonelontIsInitialTwelonelont(twelonelont))
  }

  delonf twelonelontIsStalelonTwelonelont(twelonelont: Twelonelont, increlonmelonntMelontric: Boolelonan = truelon): Boolelonan = {
    if (increlonmelonntMelontric) twelonelontIsStalelonTwelonelont.incr()

    twelonelont.elonditControl match {
      caselon Nonelon => falselon
      caselon Somelon(elonc) =>
        elonc match {
          caselon elonci: elonditControl.Initial => elonci.initial.elonditTwelonelontIds.last != twelonelont.id
          caselon eloncelon: elonditControl.elondit =>
            eloncelon.elondit.elonditControlInitial.elonxists(_.elonditTwelonelontIds.last != twelonelont.id)
          caselon _ => falselon
        }
    }
  }

  delonf twelonelontIselonditTwelonelont(twelonelont: Twelonelont, increlonmelonntMelontric: Boolelonan = truelon): Boolelonan = {
    if (increlonmelonntMelontric) twelonelontIselonditTwelonelont.incr()

    twelonelont.elonditControl match {
      caselon Nonelon => falselon
      caselon Somelon(elonc) =>
        elonc match {
          caselon _: elonditControl.Initial => falselon
          caselon _ => truelon
        }
    }
  }

  delonf twelonelontIsLatelonstTwelonelont(twelonelont: Twelonelont): Boolelonan = {
    twelonelontIsLatelonstTwelonelont.incr()
    !twelonelontIsStalelonTwelonelont(twelonelont = twelonelont, increlonmelonntMelontric = falselon)
  }

  delonf twelonelontIsInitialTwelonelont(twelonelont: Twelonelont): Boolelonan = {
    twelonelontIsInitialTwelonelont.incr()
    !twelonelontIselonditTwelonelont(twelonelont = twelonelont, increlonmelonntMelontric = falselon)
  }
}
