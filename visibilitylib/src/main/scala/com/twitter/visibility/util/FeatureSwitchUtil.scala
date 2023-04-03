packagelon com.twittelonr.visibility.util

import com.twittelonr.abdeloncidelonr.ABDeloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.v2.buildelonr.FelonaturelonSwitchelonsBuildelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr

objelonct FelonaturelonSwitchUtil {
  privatelon val LibraryFelonaturelonsConfigPath = "/felonaturelons/visibility/main"
  privatelon val LimitelondActionsFelonaturelonsConfigPath = "/felonaturelons/visibility-limitelond-actions/main"

  delonf mkVisibilityLibraryFelonaturelonSwitchelons(
    abDeloncidelonr: ABDeloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FelonaturelonSwitchelons =
    FelonaturelonSwitchelonsBuildelonr
      .crelonatelonDelonfault(LibraryFelonaturelonsConfigPath, abDeloncidelonr, Somelon(statsReloncelonivelonr)).build()

  delonf mkLimitelondActionsFelonaturelonSwitchelons(statsReloncelonivelonr: StatsReloncelonivelonr): FelonaturelonSwitchelons =
    FelonaturelonSwitchelonsBuildelonr
      .crelonatelonWithNoelonxpelonrimelonnts(LimitelondActionsFelonaturelonsConfigPath, Somelon(statsReloncelonivelonr)).build()
}
