package com.twitter.visibility.models

import com.twitter.guano.commons.thriftscala.PolicyInViolation
import com.twitter.spam.rtf.{thriftscala => s}

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
