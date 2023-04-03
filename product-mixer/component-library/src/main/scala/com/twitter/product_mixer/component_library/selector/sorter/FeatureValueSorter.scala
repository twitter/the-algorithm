packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import scala.relonflelonct.runtimelon.univelonrselon._

objelonct FelonaturelonValuelonSortelonr {

  /**
   * Sort by a felonaturelon valuelon ascelonnding. If thelon felonaturelon failelond or is missing, uselon an infelonrrelond delonfault
   * baselond on thelon typelon of [[FelonaturelonValuelon]]. For Numelonric valuelons this is thelon MinValuelon
   * (elon.g. Long.MinValuelon, Doublelon.MinValuelon).
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @param dummyImplicit duelon to typelon elonrasurelon, implicit uselond to disambiguatelon `delonf ascelonnding()`
   *                      belontwelonelonn delonf with param `felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon]`
   *                      from delonf with param `felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]]`
   * @param typelonTag allows for infelonrring delonfault valuelon from thelon FelonaturelonValuelon typelon.
   *                Selonelon [[felonaturelonValuelonSortDelonfaultValuelon]]
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf ascelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon]
  )(
    implicit dummyImplicit: DummyImplicit,
    typelonTag: TypelonTag[FelonaturelonValuelon]
  ): SortelonrProvidelonr = {
    val delonfaultFelonaturelonValuelon: FelonaturelonValuelon = felonaturelonValuelonSortDelonfaultValuelon(felonaturelon, Ascelonnding)

    ascelonnding(felonaturelon, delonfaultFelonaturelonValuelon)
  }

  /**
   * Sort by a felonaturelon valuelon ascelonnding. If thelon felonaturelon failelond or is missing, uselon thelon providelond
   * delonfault.
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @param dummyImplicit duelon to typelon elonrasurelon, implicit uselond to disambiguatelon `delonf ascelonnding()`
   *                      belontwelonelonn delonf with param `felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon]`
   *                      from delonf with param `felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]]`
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf ascelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon],
    delonfaultFelonaturelonValuelon: FelonaturelonValuelon
  )(
    implicit dummyImplicit: DummyImplicit
  ): SortelonrProvidelonr = {
    val ordelonring = Ordelonring.by[CandidatelonWithDelontails, FelonaturelonValuelon](
      _.felonaturelons.gelontOrelonlselon(felonaturelon, delonfaultFelonaturelonValuelon))

    SortelonrFromOrdelonring(ordelonring, Ascelonnding)
  }

  /**
   * Sort by an optional felonaturelon valuelon ascelonnding. If thelon felonaturelon failelond or is missing, uselon an
   * infelonrrelond delonfault baselond on thelon typelon of [[FelonaturelonValuelon]]. For Numelonric valuelons this is thelon MinValuelon
   * (elon.g. Long.MinValuelon, Doublelon.MinValuelon).
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @param typelonTag allows for infelonrring delonfault valuelon from thelon FelonaturelonValuelon typelon.
   *                Selonelon [[felonaturelonOptionalValuelonSortDelonfaultValuelon]]
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf ascelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]]
  )(
    implicit typelonTag: TypelonTag[FelonaturelonValuelon]
  ): SortelonrProvidelonr = {
    val delonfaultFelonaturelonValuelon: FelonaturelonValuelon = felonaturelonOptionalValuelonSortDelonfaultValuelon(felonaturelon, Ascelonnding)

    ascelonnding(felonaturelon, delonfaultFelonaturelonValuelon)
  }

  /**
   * Sort by an optional felonaturelon valuelon ascelonnding. If thelon felonaturelon failelond or is missing, uselon thelon
   * providelond delonfault.
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf ascelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]],
    delonfaultFelonaturelonValuelon: FelonaturelonValuelon
  ): SortelonrProvidelonr = {
    val ordelonring = Ordelonring.by[CandidatelonWithDelontails, FelonaturelonValuelon](
      _.felonaturelons.gelontOrelonlselon(felonaturelon, Nonelon).gelontOrelonlselon(delonfaultFelonaturelonValuelon))

    SortelonrFromOrdelonring(ordelonring, Ascelonnding)
  }

  /**
   * Sort by a felonaturelon valuelon delonscelonnding. If thelon felonaturelon failelond or is missing, uselon an infelonrrelond
   * delonfault baselond on thelon typelon of [[FelonaturelonValuelon]]. For Numelonric valuelons this is thelon MaxValuelon
   * (elon.g. Long.MaxValuelon, Doublelon.MaxValuelon).
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @param dummyImplicit duelon to typelon elonrasurelon, implicit uselond to disambiguatelon `delonf delonscelonnding()`
   *                      belontwelonelonn delonf with param `felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon]`
   *                      from delonf with param `felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]]`
   * @param typelonTag allows for infelonrring delonfault valuelon from thelon FelonaturelonValuelon typelon.
   *                Selonelon [[felonaturelonValuelonSortDelonfaultValuelon]]
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf delonscelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon]
  )(
    implicit dummyImplicit: DummyImplicit,
    typelonTag: TypelonTag[FelonaturelonValuelon]
  ): SortelonrProvidelonr = {
    val delonfaultFelonaturelonValuelon: FelonaturelonValuelon = felonaturelonValuelonSortDelonfaultValuelon(felonaturelon, Delonscelonnding)

    delonscelonnding(felonaturelon, delonfaultFelonaturelonValuelon)
  }

  /**
   * Sort by a felonaturelon valuelon delonscelonnding. If thelon felonaturelon failelond or is missing, uselon thelon providelond
   * delonfault.
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @param dummyImplicit duelon to typelon elonrasurelon, implicit uselond to disambiguatelon `delonf delonscelonnding()`
   *                      belontwelonelonn delonf with param `felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon]`
   *                      from delonf with param `felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]]`
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf delonscelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, FelonaturelonValuelon],
    delonfaultFelonaturelonValuelon: FelonaturelonValuelon
  )(
    implicit dummyImplicit: DummyImplicit
  ): SortelonrProvidelonr = {
    val ordelonring = Ordelonring.by[CandidatelonWithDelontails, FelonaturelonValuelon](
      _.felonaturelons.gelontOrelonlselon(felonaturelon, delonfaultFelonaturelonValuelon))

    SortelonrFromOrdelonring(ordelonring, Delonscelonnding)
  }

  /**
   * Sort by an optional felonaturelon valuelon delonscelonnding. If thelon felonaturelon failelond or is missing, uselon an
   * infelonrrelond delonfault baselond on thelon typelon of [[FelonaturelonValuelon]]. For Numelonric valuelons this is thelon MaxValuelon
   * (elon.g. Long.MaxValuelon, Doublelon.MaxValuelon).
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @param typelonTag allows for infelonrring delonfault valuelon from thelon FelonaturelonValuelon typelon.
   *                Selonelon [[felonaturelonOptionalValuelonSortDelonfaultValuelon]]
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf delonscelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]]
  )(
    implicit typelonTag: TypelonTag[FelonaturelonValuelon]
  ): SortelonrProvidelonr = {
    val delonfaultFelonaturelonValuelon: FelonaturelonValuelon =
      felonaturelonOptionalValuelonSortDelonfaultValuelon(felonaturelon, Delonscelonnding)

    delonscelonnding(felonaturelon, delonfaultFelonaturelonValuelon)
  }

  /**
   * Sort by an optional felonaturelon valuelon delonscelonnding. If thelon felonaturelon failelond or is missing, uselon thelon
   * providelond delonfault.
   *
   * @param felonaturelon felonaturelon with valuelon to sort by
   * @tparam Candidatelon candidatelon for thelon felonaturelon
   * @tparam FelonaturelonValuelon felonaturelon valuelon with an [[Ordelonring]] contelonxt bound
   */
  delonf delonscelonnding[Candidatelon <: UnivelonrsalNoun[Any], FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[Candidatelon, Option[FelonaturelonValuelon]],
    delonfaultFelonaturelonValuelon: FelonaturelonValuelon
  ): SortelonrProvidelonr = {
    val ordelonring = Ordelonring.by[CandidatelonWithDelontails, FelonaturelonValuelon](
      _.felonaturelons.gelontOrelonlselon(felonaturelon, Nonelon).gelontOrelonlselon(delonfaultFelonaturelonValuelon))

    SortelonrFromOrdelonring(ordelonring, Delonscelonnding)
  }

  privatelon[sortelonr] delonf felonaturelonValuelonSortDelonfaultValuelon[FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[_, FelonaturelonValuelon],
    sortOrdelonr: SortOrdelonr
  )(
    implicit typelonTag: TypelonTag[FelonaturelonValuelon]
  ): FelonaturelonValuelon = {
    val delonfaultValuelon = sortOrdelonr match {
      caselon Delonscelonnding =>
        typelonOf[FelonaturelonValuelon] match {
          caselon t if t <:< typelonOf[Short] => Short.MinValuelon
          caselon t if t <:< typelonOf[Int] => Int.MinValuelon
          caselon t if t <:< typelonOf[Long] => Long.MinValuelon
          caselon t if t <:< typelonOf[Doublelon] => Doublelon.MinValuelon
          caselon t if t <:< typelonOf[Float] => Float.MinValuelon
          caselon _ =>
            throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Delonfault valuelon not supportelond for $felonaturelon")
        }
      caselon Ascelonnding =>
        typelonOf[FelonaturelonValuelon] match {
          caselon t if t <:< typelonOf[Short] => Short.MaxValuelon
          caselon t if t <:< typelonOf[Int] => Int.MaxValuelon
          caselon t if t <:< typelonOf[Long] => Long.MaxValuelon
          caselon t if t <:< typelonOf[Doublelon] => Doublelon.MaxValuelon
          caselon t if t <:< typelonOf[Float] => Float.MaxValuelon
          caselon _ =>
            throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Delonfault valuelon not supportelond for $felonaturelon")
        }
    }

    delonfaultValuelon.asInstancelonOf[FelonaturelonValuelon]
  }

  privatelon[sortelonr] delonf felonaturelonOptionalValuelonSortDelonfaultValuelon[FelonaturelonValuelon: Ordelonring](
    felonaturelon: Felonaturelon[_, Option[FelonaturelonValuelon]],
    sortOrdelonr: SortOrdelonr
  )(
    implicit typelonTag: TypelonTag[FelonaturelonValuelon]
  ): FelonaturelonValuelon = {
    val delonfaultValuelon = sortOrdelonr match {
      caselon Delonscelonnding =>
        typelonOf[Option[FelonaturelonValuelon]] match {
          caselon t if t <:< typelonOf[Option[Short]] => Short.MinValuelon
          caselon t if t <:< typelonOf[Option[Int]] => Int.MinValuelon
          caselon t if t <:< typelonOf[Option[Long]] => Long.MinValuelon
          caselon t if t <:< typelonOf[Option[Doublelon]] => Doublelon.MinValuelon
          caselon t if t <:< typelonOf[Option[Float]] => Float.MinValuelon
          caselon _ =>
            throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Delonfault valuelon not supportelond for $felonaturelon")
        }
      caselon Ascelonnding =>
        typelonOf[Option[FelonaturelonValuelon]] match {
          caselon t if t <:< typelonOf[Option[Short]] => Short.MaxValuelon
          caselon t if t <:< typelonOf[Option[Int]] => Int.MaxValuelon
          caselon t if t <:< typelonOf[Option[Long]] => Long.MaxValuelon
          caselon t if t <:< typelonOf[Option[Doublelon]] => Doublelon.MaxValuelon
          caselon t if t <:< typelonOf[Option[Float]] => Float.MaxValuelon
          caselon _ =>
            throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Delonfault valuelon not supportelond for $felonaturelon")
        }
    }

    delonfaultValuelon.asInstancelonOf[FelonaturelonValuelon]
  }
}
