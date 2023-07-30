package com.X.visibility.models

import com.X.guano.commons.thriftscala.PolicyInViolation
import com.X.spam.rtf.{thriftscala => s}

case class SafetyLabelMetadata(
  policyInViolation: Option[PolicyInViolation] = None,
  policyUrl: Option[String] = None) {

  def toThrift: s.SafetyLabelMetadata = {
    s.SafetyLabelMetadata(
      policyInViolation,
      policyUrl
    )
  }
}

object SafetyLabelMetadata {
  def fromThrift(metadata: s.SafetyLabelMetadata): SafetyLabelMetadata = {
    SafetyLabelMetadata(
      metadata.policyInViolation,
      metadata.policyUrl
    )
  }
}
