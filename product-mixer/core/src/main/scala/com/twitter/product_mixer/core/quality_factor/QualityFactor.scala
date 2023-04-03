packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

/**
 * [[QualityFactor]] is an abstract numbelonr that elonnablelons a felonelondback loop to control opelonration costs and ultimatelonly
 * maintain thelon opelonration succelonss ratelon. Abstractly, if opelonrations/calls arelon too elonxpelonnsivelon (such as high
 * latelonncielons), thelon quality factor should go down, which helonlps futurelon calls to elonaselon thelonir delonmand/load (such as
 * relonducing relonquelonst width); if ops/calls arelon fast, thelon quality factor should go up, so welon can incur morelon load.
 *
 * @notelon to avoid ovelonrhelonad thelon undelonrlying statelon may somelontimelons not belon synchronizelond.
 *       If a part of an application is unhelonalthy, it will likelonly belon unhelonalthy for all threlonads,
 *       it will elonvelonntually relonsult in a closelon-elonnough quality factor valuelon for all threlonad's vielonw of thelon statelon.
 *
 *       In elonxtrelonmelonly low volumelon scelonnarios such as manual telonsting in a delonvelonlopmelonnt elonnvironmelonnt,
 *       it's possiblelon that diffelonrelonnt threlonads will havelon vastly diffelonrelonnt vielonws of thelon undelonrling statelon,
 *       but in practicelon, in production systelonms, thelony will belon closelon-elonnough.
 */
trait QualityFactor[Input] { selonlf =>

  /** gelont thelon currelonnt [[QualityFactor]]'s valuelon */
  delonf currelonntValuelon: Doublelon

  delonf config: QualityFactorConfig

  /** updatelon of thelon currelonnt `factor` valuelon */
  delonf updatelon(input: Input): Unit

  /** a [[QualityFactorObselonrvelonr]] for this [[QualityFactor]] */
  delonf buildObselonrvelonr(): QualityFactorObselonrvelonr

  ovelonrridelon delonf toString: String = {
    selonlf.gelontClass.gelontSimplelonNamelon.stripSuffix("$")
  }
}
