package com.X.visibility.engine

import com.X.servo.util.Gate
import com.X.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}
import com.X.stitch.Stitch
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.VisibilityResultBuilder
import com.X.visibility.models.SafetyLevel
import com.X.visibility.rules.EvaluationContext
import com.X.visibility.rules.Rule

trait DeciderableVisibilityRuleEngine {
  def apply(
    evaluationContext: EvaluationContext,
    safetyLevel: SafetyLevel,
    visibilityResultBuilder: VisibilityResultBuilder,
    enableShortCircuiting: Gate[Unit] = Gate.True,
    preprocessedRules: Option[Seq[Rule]] = None
  ): Stitch[VisibilityResult]

  def apply(
    evaluationContext: EvaluationContext,
    thriftSafetyLevel: ThriftSafetyLevel,
    visibilityResultBuilder: VisibilityResultBuilder
  ): Stitch[VisibilityResult]
}
