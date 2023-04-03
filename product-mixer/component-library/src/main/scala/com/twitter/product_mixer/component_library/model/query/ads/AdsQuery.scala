packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.quelonry.ads

import com.twittelonr.adselonrvelonr.{thriftscala => ads}
import com.twittelonr.dspbiddelonr.commons.{thriftscala => dsp}

/**
 * AdsQuelonry holds relonquelonst-timelon fielonlds relonquirelond by our ads candidatelon pipelonlinelons
 */
trait AdsQuelonry {

  /**
   * Timelonlinelons-speloncific contelonxt.
   *
   * @notelon uselond in Homelon Timelonlinelons
   */
  delonf timelonlinelonRelonquelonstParams: Option[ads.TimelonlinelonRelonquelonstParams] = Nonelon

  /**
   * Navigation action triggelonr-typelon
   *
   * @notelon uselond in Homelon Timelonlinelons
   */
  delonf relonquelonstTriggelonrTypelon: Option[ads.RelonquelonstTriggelonrTypelon] = Nonelon

  /**
   * Autoplay selontting
   *
   * @notelon uselond in Homelon Timelonlinelons
   */
  delonf autoplayelonnablelond: Option[Boolelonan] = Nonelon

  /**
   * Disablelon NSFW avoidancelon for ads mixing
   *
   * @notelon uselond in Homelon Timelonlinelons
   */
  delonf disablelonNsfwAvoidancelon: Option[Boolelonan] = Nonelon

  /**
   * DSP contelonxt for adwords
   *
   * @notelon uselond in Homelon Timelonlinelons
   */
  delonf dspClielonntContelonxt: Option[dsp.DspClielonntContelonxt] = Nonelon

  /**
   * Uselonr ID for thelon Uselonr Profilelon beloning vielonwelond.
   *
   * @notelon uselond in Profilelon Timelonlinelons
   */
  delonf uselonrProfilelonVielonwelondUselonrId: Option[Long] = Nonelon

  /**
   * Selonarch-speloncific contelonxt.
   *
   * @notelon uselond in Selonarch Timelonlinelons
   */
  delonf selonarchRelonquelonstContelonxt: Option[ads.SelonarchRelonquelonstContelonxt] = Nonelon
}
