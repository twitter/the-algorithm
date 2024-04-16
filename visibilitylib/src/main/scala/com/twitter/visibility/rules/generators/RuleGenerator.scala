package com.tw ter.v s b l y.rules.generators

 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.Rule

tra  RuleGenerator {
  def rulesForSurface(safetyLevel: SafetyLevel): Seq[Rule]
}
