package com.X.product_mixer.component_library.pipeline.candidate.flexible_injection_pipeline.transformer

import com.X.onboarding.task.service.{thriftscala => flip}

trait HasFlipInjectionParams {
  def displayLocation: flip.DisplayLocation
  def rankingDisablerWithLatestControlsAvailable: Option[Boolean]
  def isEmptyState: Option[Boolean]
  def isFirstRequestAfterSignup: Option[Boolean]
  def isEndOfTimeline: Option[Boolean]
}
