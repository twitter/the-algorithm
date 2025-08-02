package com.twitter.product_mitwitterer.component_library.pipeline.candidate.fletwitterible_injection_pipeline.transformer

import com.twitter.onboarding.task.service.{thriftscala => flip}

trait HasFlipInjectionParams {
  def displayLocation: flip.DisplayLocation
  def rankingDisablerWithLatestControlsAvailable: Option[Boolean]
  def isEmptyState: Option[Boolean]
  def isFirstRequestAfterSignup: Option[Boolean]
  def isEndOfTimeline: Option[Boolean]
}
