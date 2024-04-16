package com.tw ter.v s b l y.rules.prov ders

 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.M xedV s b l yPol cy
 mport com.tw ter.v s b l y.rules.RuleBase
 mport com.tw ter.v s b l y.rules.generators.T etRuleGenerator

class  njectedPol cyProv der(
  v s b l yDec derGates: V s b l yDec derGates,
  t etRuleGenerator: T etRuleGenerator)
    extends Pol cyProv der {

  pr vate[rules] val pol c esForSurface: Map[SafetyLevel, M xedV s b l yPol cy] =
    RuleBase.RuleMap.map {
      case (safetyLevel, pol cy) =>
        (
          safetyLevel,
          M xedV s b l yPol cy(
            or g nalPol cy = pol cy,
            add  onalT etRules = t etRuleGenerator.rulesForSurface(safetyLevel)))
    }

  overr de def pol cyForSurface(safetyLevel: SafetyLevel): M xedV s b l yPol cy = {
    pol c esForSurface(safetyLevel)
  }
}
