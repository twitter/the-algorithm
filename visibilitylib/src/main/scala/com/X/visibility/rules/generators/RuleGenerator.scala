package com.X.visibility.rules.generators

import com.X.visibility.models.SafetyLevel
import com.X.visibility.rules.Rule

trait RuleGenerator {
  def rulesForSurface(safetyLevel: SafetyLevel): Seq[Rule]
}
