package com.twitter.product_mitwitterer.component_library.model.query.ads

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.dspbidder.commons.{thriftscala => dsp}

/**
 * AdsQuery holds request-time fields required by our ads candidate pipelines
 */
trait AdsQuery {

  /**
   * Timelines-specific contetwittert.
   *
   * @note used in Home Timelines
   */
  def timelineRequestParams: Option[ads.TimelineRequestParams] = None

  /**
   * Navigation action trigger-type
   *
   * @note used in Home Timelines
   */
  def requestTriggerType: Option[ads.RequestTriggerType] = None

  /**
   * Autoplay setting
   *
   * @note used in Home Timelines
   */
  def autoplayEnabled: Option[Boolean] = None

  /**
   * Disable NSFW avoidance for ads mitwittering
   *
   * @note used in Home Timelines
   */
  def disableNsfwAvoidance: Option[Boolean] = None

  /**
   * DSP contetwittert for adwords
   *
   * @note used in Home Timelines
   */
  def dspClientContetwittert: Option[dsp.DspClientContetwittert] = None

  /**
   * User ID for the User Profile being viewed.
   *
   * @note used in Profile Timelines
   */
  def userProfileViewedUserId: Option[Long] = None

  /**
   * Search-specific contetwittert.
   *
   * @note used in Search Timelines
   */
  def searchRequestContetwittert: Option[ads.SearchRequestContetwittert] = None
}
