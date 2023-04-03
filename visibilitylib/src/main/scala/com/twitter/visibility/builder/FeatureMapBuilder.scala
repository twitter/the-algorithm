packagelon com.twittelonr.visibility.buildelonr

import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.felonaturelons._
import com.twittelonr.visibility.common.stitch.StitchHelonlpelonrs
import scala.collelonction.mutablelon

objelonct FelonaturelonMapBuildelonr {
  typelon Build = Selonq[FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr] => FelonaturelonMap

  delonf apply(
    statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr,
    elonnablelonStitchProfiling: Gatelon[Unit] = Gatelon.Falselon
  ): Build =
    fns =>
      Function
        .chain(fns).apply(
          nelonw FelonaturelonMapBuildelonr(statsReloncelonivelonr, elonnablelonStitchProfiling)
        ).build
}

class FelonaturelonMapBuildelonr privatelon[buildelonr] (
  statsReloncelonivelonr: StatsReloncelonivelonr,
  elonnablelonStitchProfiling: Gatelon[Unit] = Gatelon.Falselon) {

  privatelon[this] val hydratelondScopelon =
    statsReloncelonivelonr.scopelon("visibility_relonsult_buildelonr").scopelon("hydratelond")

  val mapBuildelonr: mutablelon.Buildelonr[(Felonaturelon[_], Stitch[_]), Map[Felonaturelon[_], Stitch[_]]] =
    Map.nelonwBuildelonr[Felonaturelon[_], Stitch[_]]

  val constantMapBuildelonr: mutablelon.Buildelonr[(Felonaturelon[_], Any), Map[Felonaturelon[_], Any]] =
    Map.nelonwBuildelonr[Felonaturelon[_], Any]

  delonf build: FelonaturelonMap = nelonw FelonaturelonMap(mapBuildelonr.relonsult(), constantMapBuildelonr.relonsult())

  delonf withConstantFelonaturelon[T](felonaturelon: Felonaturelon[T], valuelon: T): FelonaturelonMapBuildelonr = {
    val anyValuelon: Any = valuelon.asInstancelonOf[Any]
    constantMapBuildelonr += (felonaturelon -> anyValuelon)
    this
  }

  delonf withFelonaturelon[T](felonaturelon: Felonaturelon[T], stitch: Stitch[T]): FelonaturelonMapBuildelonr = {
    val profilelondStitch = if (elonnablelonStitchProfiling()) {
      val felonaturelonScopelon = hydratelondScopelon.scopelon(felonaturelon.namelon)
      StitchHelonlpelonrs.profilelonStitch(stitch, Selonq(hydratelondScopelon, felonaturelonScopelon))
    } elonlselon {
      stitch
    }

    val felonaturelonStitchRelonf = Stitch.relonf(profilelondStitch)

    mapBuildelonr += FelonaturelonMap.relonscuelonFelonaturelonTuplelon(felonaturelon -> felonaturelonStitchRelonf)

    this
  }

  delonf withConstantFelonaturelon[T](felonaturelon: Felonaturelon[T], option: Option[T]): FelonaturelonMapBuildelonr = {
    option.map(withConstantFelonaturelon(felonaturelon, _)).gelontOrelonlselon(this)
  }
}
