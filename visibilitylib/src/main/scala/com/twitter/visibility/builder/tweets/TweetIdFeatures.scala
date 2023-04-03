packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonl
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlTypelon
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlValuelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.stitch.StitchHelonlpelonrs
import com.twittelonr.visibility.felonaturelons.TwelonelontId
import com.twittelonr.visibility.felonaturelons.TwelonelontSafelontyLabelonls
import com.twittelonr.visibility.felonaturelons.TwelonelontTimelonstamp
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonl

class TwelonelontIdFelonaturelons(
  statsReloncelonivelonr: StatsReloncelonivelonr,
  elonnablelonStitchProfiling: Gatelon[Unit]) {
  privatelon[this] val scopelondStatsReloncelonivelonr: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon("twelonelont_id_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")
  privatelon[this] val twelonelontSafelontyLabelonls =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontSafelontyLabelonls.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontTimelonstamp =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontTimelonstamp.namelon).countelonr("relonquelonsts")

  privatelon[this] val labelonlFelontchScopelon: StatsReloncelonivelonr =
    scopelondStatsReloncelonivelonr.scopelon("labelonlFelontch")

  privatelon[this] delonf gelontTwelonelontLabelonls(
    twelonelontId: Long,
    labelonlFelontchelonr: Long => Stitch[Map[SafelontyLabelonlTypelon, SafelontyLabelonl]]
  ): Stitch[Selonq[TwelonelontSafelontyLabelonl]] = {
    val stitch =
      labelonlFelontchelonr(twelonelontId).map { labelonlMap =>
        labelonlMap
          .map { caselon (labelonlTypelon, labelonl) => SafelontyLabelonlValuelon(labelonlTypelon, labelonl) }.toSelonq
          .map(TwelonelontSafelontyLabelonl.fromThrift)
      }

    if (elonnablelonStitchProfiling()) {
      StitchHelonlpelonrs.profilelonStitch(
        stitch,
        Selonq(labelonlFelontchScopelon)
      )
    } elonlselon {
      stitch
    }
  }

  delonf forTwelonelontId(
    twelonelontId: Long,
    labelonlFelontchelonr: Long => Stitch[Map[SafelontyLabelonlTypelon, SafelontyLabelonl]]
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()
    twelonelontSafelontyLabelonls.incr()
    twelonelontTimelonstamp.incr()

    _.withFelonaturelon(TwelonelontSafelontyLabelonls, gelontTwelonelontLabelonls(twelonelontId, labelonlFelontchelonr))
      .withConstantFelonaturelon(TwelonelontTimelonstamp, TwelonelontFelonaturelons.twelonelontTimelonstamp(twelonelontId))
      .withConstantFelonaturelon(TwelonelontId, twelonelontId)
  }

  delonf forTwelonelontId(
    twelonelontId: Long,
    constantTwelonelontSafelontyLabelonls: Selonq[TwelonelontSafelontyLabelonl]
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()
    twelonelontSafelontyLabelonls.incr()
    twelonelontTimelonstamp.incr()

    _.withConstantFelonaturelon(TwelonelontSafelontyLabelonls, constantTwelonelontSafelontyLabelonls)
      .withConstantFelonaturelon(TwelonelontTimelonstamp, TwelonelontFelonaturelons.twelonelontTimelonstamp(twelonelontId))
      .withConstantFelonaturelon(TwelonelontId, twelonelontId)
  }
}
