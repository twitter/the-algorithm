package com.twitter.visibility.engine

import com.twitter.servo.util.Gate
import com.twitter.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}
import com.twitter.stitch.Stitch
import com.twitter.visibility.builder.VisibilityResult
import com.twitter.visibility.builder.VisibilityResultBuilder
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.rules.EvaluationContext
import com.twitter.visibility.rules.Rule

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
