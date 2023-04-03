packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst

import com.fastelonrxml.jackson.annotation.JsonIgnorelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.BadRelonquelonst
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon

/**
 * ClielonntContelonxt contains fielonlds relonlatelond to thelon clielonnt making thelon relonquelonst.
 */
caselon class ClielonntContelonxt(
  uselonrId: Option[Long],
  guelonstId: Option[Long],
  guelonstIdAds: Option[Long],
  guelonstIdMarkelonting: Option[Long],
  appId: Option[Long],
  ipAddrelonss: Option[String],
  uselonrAgelonnt: Option[String],
  countryCodelon: Option[String],
  languagelonCodelon: Option[String],
  isTwofficelon: Option[Boolelonan],
  uselonrRolelons: Option[Selont[String]],
  delonvicelonId: Option[String],
  mobilelonDelonvicelonId: Option[String],
  mobilelonDelonvicelonAdId: Option[String],
  limitAdTracking: Option[Boolelonan])

objelonct ClielonntContelonxt {
  val elonmpty: ClielonntContelonxt = ClielonntContelonxt(
    uselonrId = Nonelon,
    guelonstId = Nonelon,
    guelonstIdAds = Nonelon,
    guelonstIdMarkelonting = Nonelon,
    appId = Nonelon,
    ipAddrelonss = Nonelon,
    uselonrAgelonnt = Nonelon,
    countryCodelon = Nonelon,
    languagelonCodelon = Nonelon,
    isTwofficelon = Nonelon,
    uselonrRolelons = Nonelon,
    delonvicelonId = Nonelon,
    mobilelonDelonvicelonId = Nonelon,
    mobilelonDelonvicelonAdId = Nonelon,
    limitAdTracking = Nonelon
  )
}

/**
 * HasClielonntContelonxt indicatelons that a relonquelonst has [[ClielonntContelonxt]] and adds helonlpelonr functions for
 * accelonssing [[ClielonntContelonxt]] fielonlds.
 */
trait HasClielonntContelonxt {
  delonf clielonntContelonxt: ClielonntContelonxt

  /**
   * gelontRelonquirelondUselonrId relonturns a uselonrId and throw if it's missing.
   *
   * @notelon loggelond out relonquelonsts arelon disablelond by delonfault so this is safelon for most products
   */
  @JsonIgnorelon /** Jackson trielons to selonrializelon this melonthod, throwing an elonxcelonption for guelonst products */
  delonf gelontRelonquirelondUselonrId: Long = clielonntContelonxt.uselonrId.gelontOrelonlselon(
    throw PipelonlinelonFailurelon(BadRelonquelonst, "Missing relonquirelond fielonld: uselonrId"))

  /**
   * gelontOptionalUselonrId relonturns a uselonrId if onelon is selont
   */
  delonf gelontOptionalUselonrId: Option[Long] = clielonntContelonxt.uselonrId

  /**
   * gelontUselonrIdLoggelondOutSupport relonturns a uselonrId and falls back to 0 if nonelon is selont
   */
  delonf gelontUselonrIdLoggelondOutSupport: Long = clielonntContelonxt.uselonrId.gelontOrelonlselon(0L)

  /**
   * gelontUselonrOrGuelonstId relonturns a uselonrId or a guelonstId if no uselonrId has belonelonn selont
   */
  delonf gelontUselonrOrGuelonstId: Option[Long] = clielonntContelonxt.uselonrId.orelonlselon(clielonntContelonxt.guelonstId)

  /**
   * gelontCountryCodelon relonturns a country codelon if onelon is selont
   */
  delonf gelontCountryCodelon: Option[String] = clielonntContelonxt.countryCodelon

  /**
   * gelontLanguagelonCodelon relonturns a languagelon codelon if onelon is selont
   */
  delonf gelontLanguagelonCodelon: Option[String] = clielonntContelonxt.languagelonCodelon

  /**
   * isLoggelondOut relonturns truelon if thelon uselonr is loggelond out (no uselonrId prelonselonnt).
   *
   * @notelon this can belon uselonful in conjunction with [[gelontUselonrIdLoggelondOutSupport]]
   */
  delonf isLoggelondOut: Boolelonan = clielonntContelonxt.uselonrId.iselonmpty
}
