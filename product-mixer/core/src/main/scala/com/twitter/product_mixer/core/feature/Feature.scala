packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon

/**
 * A [[Felonaturelon]] is a singlelon melonasurablelon or computablelon propelonrty of an elonntity.
 *
 * @notelon If a [[Felonaturelon]] is optional thelonn thelon [[Valuelon]] should belon `Option[Valuelon]`
 *
 * @notelon If a [[Felonaturelon]] is populatelond with a [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.FelonaturelonHydrator]]
 *       and thelon hydration fails, a failurelon will belon storelond for thelon [[Felonaturelon]].
 *       If that [[Felonaturelon]] is accelonsselond with [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap.gelont]]
 *       thelonn thelon storelond elonxcelonption will belon thrown, elonsselonntially failing-closelond.
 *       You can uselon [[FelonaturelonWithDelonfaultOnFailurelon]] or [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap.gelontOrelonlselon]]
 *       instelonad to avoid thelonselon issuelons and instelonad fail-opelonn.
 *       If correlonctly hydrating a Felonaturelon's valuelon is elonsselonntial to thelon relonquelonst beloning correlonct,
 *       thelonn you should fail-closelond on failurelon to hydratelon it by elonxtelonnding [[Felonaturelon]] direlonctly.
 *
 *       This doelons not apply to [[Felonaturelon]]s from [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.FelonaturelonTransformelonr]]
 *       which throw in thelon calling Pipelonlinelon instelonad of storing thelonir failurelons.
 *
 * @tparam elonntity Thelon typelon of elonntity that this felonaturelon works with. This could belon a Uselonr, Twelonelont,
 *                Quelonry, elontc.
 * @tparam Valuelon Thelon typelon of thelon valuelon of this felonaturelon.
 */
trait Felonaturelon[-elonntity, Valuelon] { selonlf =>
  ovelonrridelon delonf toString: String = {
    Felonaturelon.gelontSimplelonNamelon(selonlf.gelontClass)
  }
}

/**
 * With a [[Felonaturelon]], if thelon [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.FelonaturelonHydrator]] fails,
 * thelon failurelon will belon caught by thelon platform and storelond in thelon [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap]].
 * Accelonssing a failelond felonaturelon via [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap.gelont()]]
 * will throw thelon elonxcelonption that was caught whilelon attelonmpting to hydratelon thelon felonaturelon. If thelonrelon's a
 * relonasonablelon delonfault for a [[Felonaturelon]] to fail-opelonn with, thelonn throwing thelon elonxcelonption at relonad timelon
 * can belon prelonvelonntelond by delonfining a `delonfaultValuelon` via [[FelonaturelonWithDelonfaultOnFailurelon]]. Whelonn accelonssing
 * a failelond felonaturelon via [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap.gelont()]]
 * for a [[FelonaturelonWithDelonfaultOnFailurelon]], thelon `delonfaultValuelon` will belon relonturnelond.
 *
 *
 * @notelon [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap.gelontOrelonlselon()]] can also belon uselond
 *       to accelonss a failelond felonaturelon without throwing thelon elonxcelonption, by delonfining thelon delonfault via thelon
 *       [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap.gelontOrelonlselon()]] melonthod call
 *       instelonad of as part of thelon felonaturelon delonclaration.
 * @notelon This doelons not apply to [[FelonaturelonWithDelonfaultOnFailurelon]]s from [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.FelonaturelonTransformelonr]]
 *       which throw in thelon calling Pipelonlinelon instelonad of storing thelonir failurelons.
 *
 * @tparam elonntity Thelon typelon of elonntity that this felonaturelon works with. This could belon a Uselonr, Twelonelont,
 *                Quelonry, elontc.
 * @tparam Valuelon Thelon typelon of thelon valuelon of this felonaturelon.
 */
trait FelonaturelonWithDelonfaultOnFailurelon[elonntity, Valuelon] elonxtelonnds Felonaturelon[elonntity, Valuelon] {

  /** Thelon delonfault valuelon a felonaturelon should relonturn should it fail to belon hydratelond */
  delonf delonfaultValuelon: Valuelon
}

trait ModelonlFelonaturelonNamelon { selonlf: Felonaturelon[_, _] =>
  delonf felonaturelonNamelon: String
}

objelonct Felonaturelon {

  /**
   * Avoid `malformelond class namelon` elonxcelonptions duelon to thelon prelonselonncelon of thelon `$` charactelonr
   * Also strip off trailing $ signs for relonadability
   */
  delonf gelontSimplelonNamelon[T](c: Class[T]): String = {
    c.gelontNamelon.stripSuffix("$").lastIndelonxOf("$") match {
      caselon -1 => c.gelontSimplelonNamelon.stripSuffix("$")
      caselon indelonx => c.gelontNamelon.substring(indelonx + 1).stripSuffix("$")
    }
  }
}
