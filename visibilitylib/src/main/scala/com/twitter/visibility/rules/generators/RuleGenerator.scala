package com.twitter.visibility.rules.generators

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.rules.Rule

trait RuleGenerator {
  def rulesForSurface(safetyLevel: SafetyLevel): Seq[Rule]
}
