packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.felonaturelonstorelonv1

import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.Ascelonnding
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.Delonscelonnding
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.FelonaturelonValuelonSortelonr.felonaturelonValuelonSortDelonfaultValuelon
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.SortelonrFromOrdelonring
import com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.SortelonrProvidelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.felonaturelonstorelonv1.FelonaturelonStorelonV1FelonaturelonMap._
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.FelonaturelonStorelonV1CandidatelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import scala.relonflelonct.runtimelon.univelonrselon._

/**
 * Felonaturelon Storelon v1 velonrsion of [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.FelonaturelonValuelonSortelonr]]
 */
objelonct FelonaturelonStorelonV1FelonaturelonValuelonSortelonr {

  /**
   * Sort by a Felonaturelon Storelon v1 felonaturelon valuelon ascelonnding. If thelon felonaturelon failelond or is missing, uselon an
   * infelonrrelond delonfault baselond on thelon typelon of [[FelonaturelonValuelon]]. For Numelonric valuelons this is thelon MinValuelon
   * (elon.g. Long.MinValuelon, Doublelon.MinValuelon).
   *
   * @param felonaturelon Felonaturelon Storelon v1 felonaturelon with valuelon to sort by
   * @param typelonTag allows for infelonrring delonfault valuelon from thelon FelonaturelonValuelon typelon.
   *                Selonelon [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.FelonaturelonValuelonSortelonr.felonaturelonValuelonSortDelonfaultValuelon]]
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf ascelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[PipelonlinelonQuelonry, Candidatelon, _ <: elonntityId, FelonaturelonValuelon]
  )(
    implicit typelonTag: TypelonTag[FelonaturelonValuelon]
  ): SortelonrProvidelonr = {
    val delonfaultFelonaturelonValuelon: FelonaturelonValuelon = felonaturelonValuelonSortDelonfaultValuelon(felonaturelon, Ascelonnding)

    ascelonnding(felonaturelon, delonfaultFelonaturelonValuelon)
  }

  /**
   * Sort by a Felonaturelon Storelon v1 felonaturelon valuelon ascelonnding. If thelon felonaturelon failelond or is missing, uselon
   * thelon providelond delonfault.
   *
   * @param felonaturelon Felonaturelon Storelon v1 felonaturelon with valuelon to sort by
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf ascelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[PipelonlinelonQuelonry, Candidatelon, _ <: elonntityId, FelonaturelonValuelon],
    delonfaultFelonaturelonValuelon: FelonaturelonValuelon
  ): SortelonrProvidelonr = {
    val ordelonring = Ordelonring.by[CandidatelonWithDelontails, FelonaturelonValuelon](
      _.felonaturelons.gelontOrelonlselonFelonaturelonStorelonV1CandidatelonFelonaturelon(felonaturelon, delonfaultFelonaturelonValuelon))

    SortelonrFromOrdelonring(ordelonring, Ascelonnding)
  }

  /**
   * Sort by a Felonaturelon Storelon v1 felonaturelon valuelon delonscelonnding. If thelon felonaturelon failelond or is missing, uselon
   * an infelonrrelond delonfault baselond on thelon typelon of [[FelonaturelonValuelon]]. For Numelonric valuelons this is thelon
   * MaxValuelon (elon.g. Long.MaxValuelon, Doublelon.MaxValuelon).
   *
   * @param felonaturelon Felonaturelon Storelon v1 felonaturelon with valuelon to sort by
   * @param typelonTag allows for infelonrring delonfault valuelon from thelon FelonaturelonValuelon typelon.
   *                Selonelon [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.FelonaturelonValuelonSortelonr.felonaturelonValuelonSortDelonfaultValuelon]]
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf delonscelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[PipelonlinelonQuelonry, Candidatelon, _ <: elonntityId, FelonaturelonValuelon]
  )(
    implicit typelonTag: TypelonTag[FelonaturelonValuelon]
  ): SortelonrProvidelonr = {
    val delonfaultFelonaturelonValuelon: FelonaturelonValuelon = felonaturelonValuelonSortDelonfaultValuelon(felonaturelon, Delonscelonnding)

    delonscelonnding(felonaturelon, delonfaultFelonaturelonValuelon)
  }

  /**
   * Sort by a Felonaturelon Storelon v1 felonaturelon valuelon delonscelonnding. If thelon felonaturelon failelond or is missing, uselon
   * thelon providelond delonfault.
   *
   * @param felonaturelon Felonaturelon Storelon v1 felonaturelon with valuelon to sort by
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf delonscelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[PipelonlinelonQuelonry, Candidatelon, _ <: elonntityId, FelonaturelonValuelon],
    delonfaultFelonaturelonValuelon: FelonaturelonValuelon
  ): SortelonrProvidelonr = {
    val ordelonring = Ordelonring.by[CandidatelonWithDelontails, FelonaturelonValuelon](
      _.felonaturelons.gelontOrelonlselonFelonaturelonStorelonV1CandidatelonFelonaturelon(felonaturelon, delonfaultFelonaturelonValuelon))

    SortelonrFromOrdelonring(ordelonring, Delonscelonnding)
  }
}
