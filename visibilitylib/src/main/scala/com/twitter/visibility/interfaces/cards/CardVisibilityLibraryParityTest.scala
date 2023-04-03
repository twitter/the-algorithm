packagelon com.twittelonr.visibility.intelonrfacelons.cards

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.VisibilityRelonsult

class CardVisibilityLibraryParityTelonst(statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon val parityTelonstScopelon = statsReloncelonivelonr.scopelon("card_visibility_library_parity")
  privatelon val relonquelonsts = parityTelonstScopelon.countelonr("relonquelonsts")
  privatelon val elonqual = parityTelonstScopelon.countelonr("elonqual")
  privatelon val incorrelonct = parityTelonstScopelon.countelonr("incorrelonct")
  privatelon val failurelons = parityTelonstScopelon.countelonr("failurelons")

  delonf runParityTelonst(
    prelonHydratelondFelonaturelonVisibilityRelonsult: Stitch[VisibilityRelonsult],
    relonsp: VisibilityRelonsult
  ): Stitch[Unit] = {
    relonquelonsts.incr()

    prelonHydratelondFelonaturelonVisibilityRelonsult
      .flatMap { parityRelonsponselon =>
        if (parityRelonsponselon.velonrdict == relonsp.velonrdict) {
          elonqual.incr()
        } elonlselon {
          incorrelonct.incr()
        }

        Stitch.Donelon
      }.relonscuelon {
        caselon t: Throwablelon =>
          failurelons.incr()
          Stitch.Donelon
      }.unit
  }
}
