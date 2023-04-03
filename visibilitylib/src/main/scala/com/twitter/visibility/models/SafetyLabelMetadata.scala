packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.guano.commons.thriftscala.PolicyInViolation
import com.twittelonr.spam.rtf.{thriftscala => s}

caselon class SafelontyLabelonlMelontadata(
  policyInViolation: Option[PolicyInViolation] = Nonelon,
  policyUrl: Option[String] = Nonelon) {

  delonf toThrift: s.SafelontyLabelonlMelontadata = {
    s.SafelontyLabelonlMelontadata(
      policyInViolation,
      policyUrl
    )
  }
}

objelonct SafelontyLabelonlMelontadata {
  delonf fromThrift(melontadata: s.SafelontyLabelonlMelontadata): SafelontyLabelonlMelontadata = {
    SafelontyLabelonlMelontadata(
      melontadata.policyInViolation,
      melontadata.policyUrl
    )
  }
}
