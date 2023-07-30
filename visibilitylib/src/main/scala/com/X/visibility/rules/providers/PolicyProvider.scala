package com.X.visibility.rules.providers

import com.X.visibility.models.SafetyLevel
import com.X.visibility.rules.VisibilityPolicy

trait PolicyProvider {
  def policyForSurface(safetyLevel: SafetyLevel): VisibilityPolicy
}
