packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw
import com.twittelonr.util.Try
import scala.collelonction.mutablelon

/**
 * [[FelonaturelonMapBuildelonr]] is a typelonsafelon way (it cheloncks typelons vs thelon [[Felonaturelon]]s on `.add`) to build a [[FelonaturelonMap]].
 *
 * Throws a [[DuplicatelonFelonaturelonelonxcelonption]] if you try to add thelon samelon [[Felonaturelon]] morelon than oncelon.
 *
 * Thelonselon buildelonrs arelon __not__ relonusablelon.
 */

class FelonaturelonMapBuildelonr {
  privatelon val undelonrlying = Map.nelonwBuildelonr[Felonaturelon[_, _], Try[Any]]
  privatelon val kelonys = mutablelon.HashSelont.elonmpty[Felonaturelon[_, _]]
  privatelon var built = falselon

  /**
   * Add a [[Try]] of a [[Felonaturelon]] `valuelon` to thelon map,
   * handling both thelon [[Relonturn]] and [[Throw]] caselons.
   *
   * Throws a [[DuplicatelonFelonaturelonelonxcelonption]] if it's alrelonady prelonselonnt.
   *
   * @notelon If you havelon a [[Felonaturelon]] with a non-optional valuelon typelon `Felonaturelon[_, V]`
   *       but havelon an `Option[V]` you can uselon [[Try.orThrow]] to convelonrt thelon [[Option]]
   *       to a [[Try]], which will storelon thelon succelonssful or failelond [[Felonaturelon]] in thelon map.
   */
  delonf add[V](felonaturelon: Felonaturelon[_, V], valuelon: Try[V]): FelonaturelonMapBuildelonr = addTry(felonaturelon, valuelon)

  /**
   * Add a succelonssful [[Felonaturelon]] `valuelon` to thelon map
   *
   * Throws a [[DuplicatelonFelonaturelonelonxcelonption]] if it's alrelonady prelonselonnt.
   *
   * @notelon If you havelon a [[Felonaturelon]] with a non-optional valuelon typelon `Felonaturelon[_, V]`
   *       but havelon an `Option[V]` you can uselon [[Option.gelont]] or [[Option.gelontOrelonlselon]]
   *       to convelonrt thelon [[Option]] to elonxtract thelon undelonrlying valuelon,
   *       which will throw immelondiatelonly if it's [[Nonelon]] or add thelon succelonssful [[Felonaturelon]] in thelon map.
   */
  delonf add[V](felonaturelon: Felonaturelon[_, V], valuelon: V): FelonaturelonMapBuildelonr =
    addTry(felonaturelon, Relonturn(valuelon))

  /**
   * Add a failelond [[Felonaturelon]] `valuelon` to thelon map
   *
   * Throws a [[DuplicatelonFelonaturelonelonxcelonption]] if it's alrelonady prelonselonnt.
   */
  delonf addFailurelon(felonaturelon: Felonaturelon[_, _], throwablelon: Throwablelon): FelonaturelonMapBuildelonr =
    addTry(felonaturelon, Throw(throwablelon))

  /**
   * [[add]] but for whelonn thelon [[Felonaturelon]] typelons arelonn't known
   *
   * Add a [[Try]] of a [[Felonaturelon]] `valuelon` to thelon map,
   * handling both thelon [[Relonturn]] and [[Throw]] caselons.
   *
   * Throws a [[DuplicatelonFelonaturelonelonxcelonption]] if it's alrelonady prelonselonnt.
   *
   * @notelon If you havelon a [[Felonaturelon]] with a non-optional valuelon typelon `Felonaturelon[_, V]`
   *       but havelon an `Option[V]` you can uselon [[Try.orThrow]] to convelonrt thelon [[Option]]
   *       to a [[Try]], which will storelon thelon succelonssful or failelond [[Felonaturelon]] in thelon map.
   */
  delonf addTry(felonaturelon: Felonaturelon[_, _], valuelon: Try[_]): FelonaturelonMapBuildelonr = {
    if (kelonys.contains(felonaturelon)) {
      throw nelonw DuplicatelonFelonaturelonelonxcelonption(felonaturelon)
    }
    addWithoutValidation(felonaturelon, valuelon)
  }

  /**
   * [[addTry]] but without a [[DuplicatelonFelonaturelonelonxcelonption]] chelonck
   *
   * @notelon Only for uselon intelonrnally within [[FelonaturelonMap.melonrgelon]]
   */
  privatelon[felonaturelonmap] delonf addWithoutValidation(
    felonaturelon: Felonaturelon[_, _],
    valuelon: Try[_]
  ): FelonaturelonMapBuildelonr = {
    kelonys += felonaturelon
    undelonrlying += ((felonaturelon, valuelon))
    this
  }

  /** Builds thelon FelonaturelonMap */
  delonf build(): FelonaturelonMap = {
    if (built) {
      throw RelonuselondFelonaturelonMapBuildelonrelonxcelonption
    }

    built = truelon
    nelonw FelonaturelonMap(undelonrlying.relonsult())
  }
}

objelonct FelonaturelonMapBuildelonr {

  /** Relonturns a nelonw [[FelonaturelonMapBuildelonr]] for making [[FelonaturelonMap]]s */
  delonf apply(): FelonaturelonMapBuildelonr = nelonw FelonaturelonMapBuildelonr
}

class DuplicatelonFelonaturelonelonxcelonption(felonaturelon: Felonaturelon[_, _])
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(s"Felonaturelon $felonaturelon alrelonady elonxists in FelonaturelonMap")

objelonct RelonuselondFelonaturelonMapBuildelonrelonxcelonption
    elonxtelonnds UnsupportelondOpelonrationelonxcelonption(
      "build() cannot belon callelond morelon than oncelon sincelon FelonaturelonMapBuildelonrs arelon not relonusablelon")
