package com.twitter.visibility.rules.providers

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.rules.VisibilityPolicy

trait PolicyProvider {
  def policyForSurface(safetyLevel: SafetyLevel): VisibilityPolicy
}
