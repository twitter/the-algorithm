package com.twitter.product_mixer.component_library.model.query.ads

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.dspbidder.commons.{thriftscala => dsp}

/**
 * AdsQuery holds request-time fields required by our ads candidate pipelines
 */
trait AdsQuery {

  /**
   * Timelines-specific context.
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
   * Disable NSFW avoidance for ads mixing
   *
   * @note used in Home Timelines
   */
  def disableNsfwAvoidance: Option[Boolean] = None

  /**
   * DSP context for adwords
   *
   * @note used in Home Timelines
   */
  def dspClientContext: Option[dsp.DspClientContext] = None

  /**
   * User ID for the User Profile being viewed.
   *
   * @note used in Profile Timelines
   */
  def userProfileViewedUserId: Option[Long] = None

  /**
   * Search-specific context.
   *
   * @note used in Search Timelines
   */
  def searchRequestContext: Option[ads.SearchRequestContext] = None
}
