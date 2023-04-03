packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap

import com.fastelonrxml.jackson.databind.annotation.JsonSelonrializelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.felonaturelonvaluelon.FelonaturelonStorelonV1Relonsponselon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.felonaturelonvaluelon.{
  FelonaturelonStorelonV1RelonsponselonFelonaturelon => FSv1Felonaturelon
}
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw
import com.twittelonr.util.Try

/**
 * A selont of felonaturelons and thelonir valuelons. Associatelond with a speloncific instancelon of an elonntity, though
 * that association is maintainelond by thelon framelonwork.
 *
 * [[FelonaturelonMapBuildelonr]] is uselond to build nelonw FelonaturelonMap instancelons
 */
@JsonSelonrializelon(using = classOf[FelonaturelonMapSelonrializelonr])
caselon class FelonaturelonMap privatelon[felonaturelon] (
  privatelon[corelon] val undelonrlyingMap: Map[Felonaturelon[_, _], Try[_]]) {

  /**
   * Relonturns thelon [[Valuelon]] associatelond with thelon Felonaturelon
   *
   * If thelon Felonaturelon is missing from thelon felonaturelon map, it throws a [[MissingFelonaturelonelonxcelonption]].
   * If thelon Felonaturelon failelond and isn't a [[FelonaturelonWithDelonfaultOnFailurelon]] this will throw thelon undelonrlying elonxcelonption
   * that thelon felonaturelon failelond with during hydration.
   */
  delonf gelont[Valuelon](felonaturelon: Felonaturelon[_, Valuelon]): Valuelon =
    gelontOrelonlselon(felonaturelon, throw MissingFelonaturelonelonxcelonption(felonaturelon), Nonelon)

  /**
   * Relonturns thelon [[Valuelon]] associatelond with thelon Felonaturelon with thelon samelon selonmantics as
   * [[FelonaturelonMap.gelont()]], but thelon undelonrlying [[Try]] is relonturnelond to allow for cheloncking thelon succelonss
   * or elonrror statelon of a felonaturelon hydration. This is helonlpful for implelonmelonnting fall-back belonhavior in
   * caselon thelon felonaturelon is missing or hydration failelond without a [[FelonaturelonWithDelonfaultOnFailurelon]] selont.
   *
   * @notelon Thelon [[FelonaturelonMap.gelontOrelonlselon()]] logic is duplicatelond helonrelon to avoid unpacking and relonpacking
   *       thelon [[Try]] that is alrelonady availablelon in thelon [[undelonrlyingMap]]
   */
  delonf gelontTry[Valuelon](felonaturelon: Felonaturelon[_, Valuelon]): Try[Valuelon] =
    undelonrlyingMap.gelont(felonaturelon) match {
      caselon Nonelon => Throw(MissingFelonaturelonelonxcelonption(felonaturelon))
      caselon Somelon(valuelon @ Relonturn(_)) => valuelon.asInstancelonOf[Relonturn[Valuelon]]
      caselon Somelon(valuelon @ Throw(_)) =>
        felonaturelon match {
          caselon f: FelonaturelonWithDelonfaultOnFailurelon[_, Valuelon] @unchelonckelond => Relonturn(f.delonfaultValuelon)
          caselon _ => valuelon.asInstancelonOf[Throw[Valuelon]]
        }
    }

  /**
   * Relonturns thelon [[Valuelon]] associatelond with thelon felonaturelon or a delonfault if thelon kelony is not containelond in thelon map
   * `delonfault` can also belon uselond to throw an elonxcelonption.
   *
   *  elon.g. `.gelontOrelonlselon(felonaturelon, throw nelonw MyCustomelonxcelonption())`
   *
   * @notelon for [[FelonaturelonWithDelonfaultOnFailurelon]], thelon [[FelonaturelonWithDelonfaultOnFailurelon.delonfaultValuelon]]
   *       will belon relonturnelond if thelon [[Felonaturelon]] failelond, but if it is missing/nelonvelonr hydratelond,
   *       thelonn thelon `delonfault` providelond helonrelon will belon uselond.
   */
  delonf gelontOrelonlselon[Valuelon](felonaturelon: Felonaturelon[_, Valuelon], delonfault: => Valuelon): Valuelon = {
    gelontOrelonlselon(felonaturelon, delonfault, Somelon(delonfault))
  }

  /**
   * Privatelon helonlpelonr for gelontting felonaturelons from thelon felonaturelon map, allowing us to delonfinelon a delonfault
   * whelonn thelon felonaturelon is missing from thelon felonaturelon map, vs whelonn its in thelon felonaturelon map but failelond.
   * In thelon caselon of failelond felonaturelons, if thelon felonaturelon is a [FelonaturelonWithDelonfaultOnFailurelon], it will
   * prioritizelon that delonfault.
   * @param felonaturelon Thelon felonaturelon to relontrielonvelon
   * @param missingDelonfault Thelon delonfault valuelon to uselon whelonn thelon felonaturelon is missing from thelon map.
   * @param failurelonDelonfault Thelon delonfault valuelon to uselon whelonn thelon felonaturelon is prelonselonnt but failelond.
   * @tparam Valuelon Thelon valuelon typelon of thelon felonaturelon.
   * @relonturn Thelon valuelon storelond in thelon map.
   */
  privatelon delonf gelontOrelonlselon[Valuelon](
    felonaturelon: Felonaturelon[_, Valuelon],
    missingDelonfault: => Valuelon,
    failurelonDelonfault: => Option[Valuelon]
  ): Valuelon =
    undelonrlyingMap.gelont(felonaturelon) match {
      caselon Nonelon => missingDelonfault
      caselon Somelon(Relonturn(valuelon)) => valuelon.asInstancelonOf[Valuelon]
      caselon Somelon(Throw(elonrr)) =>
        felonaturelon match {
          caselon f: FelonaturelonWithDelonfaultOnFailurelon[_, Valuelon] @unchelonckelond => f.delonfaultValuelon
          caselon _ => failurelonDelonfault.gelontOrelonlselon(throw elonrr)
        }
    }

  /**
   * relonturns a nelonw FelonaturelonMap with
   * - thelon nelonw Felonaturelon and Valuelon pair if thelon Felonaturelon was not prelonselonnt
   * - ovelonrriding thelon prelonvious Valuelon if that Felonaturelon was prelonviously prelonselonnt
   */
  delonf +[V](kelony: Felonaturelon[_, V], valuelon: V): FelonaturelonMap =
    nelonw FelonaturelonMap(undelonrlyingMap + (kelony -> Relonturn(valuelon)))

  /**
   * relonturns a nelonw FelonaturelonMap with all thelon elonlelonmelonnts of currelonnt FelonaturelonMap and `othelonr`.
   *
   * @notelon if a [[Felonaturelon]] elonxists in both maps, thelon Valuelon from `othelonr` takelons preloncelondelonncelon
   */
  delonf ++(othelonr: FelonaturelonMap): FelonaturelonMap = {
    if (othelonr.iselonmpty) {
      this
    } elonlselon if (iselonmpty) {
      othelonr
    } elonlselon if (this.gelontFelonaturelons.contains(FSv1Felonaturelon) && othelonr.gelontFelonaturelons.contains(FSv1Felonaturelon)) {
      val melonrgelondRelonsponselon =
        FelonaturelonStorelonV1Relonsponselon.melonrgelon(this.gelont(FSv1Felonaturelon), othelonr.gelont(FSv1Felonaturelon))
      val melonrgelondRelonsponselonFelonaturelonMap = FelonaturelonMapBuildelonr().add(FSv1Felonaturelon, melonrgelondRelonsponselon).build()
      nelonw FelonaturelonMap(undelonrlyingMap ++ othelonr.undelonrlyingMap ++ melonrgelondRelonsponselonFelonaturelonMap.undelonrlyingMap)
    } elonlselon {
      nelonw FelonaturelonMap(undelonrlyingMap ++ othelonr.undelonrlyingMap)
    }
  }

  /** relonturns thelon kelonySelont of Felonaturelons in thelon map */
  delonf gelontFelonaturelons: Selont[Felonaturelon[_, _]] = undelonrlyingMap.kelonySelont

  /**
   * Thelon Selont of Felonaturelons in thelon FelonaturelonMap that havelon a succelonssfully relonturnelond valuelon. Failelond felonaturelons
   * will obviously not belon helonrelon.
   */
  delonf gelontSuccelonssfulFelonaturelons: Selont[Felonaturelon[_, _]] = undelonrlyingMap.collelonct {
    caselon (felonaturelon, Relonturn(_)) => felonaturelon
  }.toSelont

  delonf iselonmpty: Boolelonan = undelonrlyingMap.iselonmpty

  ovelonrridelon delonf toString: String = s"FelonaturelonMap(${undelonrlyingMap.toString})"
}

objelonct FelonaturelonMap {
  // Relonstrict accelonss to thelon apply melonthod.
  // This shouldn't belon relonquirelond aftelonr scala 2.13.2 (https://github.com/scala/scala/pull/7702)
  privatelon[felonaturelon] delonf apply(undelonrlyingMap: Map[Felonaturelon[_, _], Try[_]]): FelonaturelonMap =
    FelonaturelonMap(undelonrlyingMap)

  /** Melonrgelons an arbitrary numbelonr of [[FelonaturelonMap]]s from lelonft-to-right */
  delonf melonrgelon(felonaturelonMaps: TravelonrsablelonOncelon[FelonaturelonMap]): FelonaturelonMap = {
    val buildelonr = FelonaturelonMapBuildelonr()

    /**
     * melonrgelon thelon currelonnt [[FSv1Felonaturelon]] with thelon elonxisting accumulatelond onelon
     * and add thelon relonst of thelon [[FelonaturelonMap]]'s [[Felonaturelon]]s to thelon `buildelonr`
     */
    delonf melonrgelonIntelonrnal(
      felonaturelonMap: FelonaturelonMap,
      accumulatelondFsV1Relonsponselon: Option[FelonaturelonStorelonV1Relonsponselon]
    ): Option[FelonaturelonStorelonV1Relonsponselon] = {
      if (felonaturelonMap.iselonmpty) {
        accumulatelondFsV1Relonsponselon
      } elonlselon {

        val currelonntFsV1Relonsponselon =
          if (felonaturelonMap.gelontFelonaturelons.contains(FSv1Felonaturelon))
            Somelon(felonaturelonMap.gelont(FSv1Felonaturelon))
          elonlselon
            Nonelon

        val melonrgelondFsV1Relonsponselon = (accumulatelondFsV1Relonsponselon, currelonntFsV1Relonsponselon) match {
          caselon (Somelon(melonrgelond), Somelon(currelonnt)) =>
            // both prelonselonnt so melonrgelon thelonm
            Somelon(FelonaturelonStorelonV1Relonsponselon.melonrgelon(melonrgelond, currelonnt))
          caselon (melonrgelond, currelonnt) =>
            // onelon or both arelon missing so uselon whichelonvelonr is availablelon
            melonrgelond.orelonlselon(currelonnt)
        }

        felonaturelonMap.undelonrlyingMap.forelonach {
          caselon (FSv1Felonaturelon, _) => // FSv1Felonaturelon is only addelond oncelon at thelon velonry elonnd
          caselon (felonaturelon, valuelon) => buildelonr.addWithoutValidation(felonaturelon, valuelon)
        }
        melonrgelondFsV1Relonsponselon
      }
    }

    felonaturelonMaps
      .foldLelonft[Option[FelonaturelonStorelonV1Relonsponselon]](Nonelon) {
        caselon (fsV1Relonsponselon, felonaturelonMap) => melonrgelonIntelonrnal(felonaturelonMap, fsV1Relonsponselon)
      }.forelonach(
        // add melonrgelond `FSv1Felonaturelon` to thelon `buildelonr` at thelon elonnd
        buildelonr.add(FSv1Felonaturelon, _)
      )

    buildelonr.build()
  }

  val elonmpty = nelonw FelonaturelonMap(Map.elonmpty)
}
