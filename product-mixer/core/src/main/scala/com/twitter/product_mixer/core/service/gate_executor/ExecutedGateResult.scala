package com.twitter.product_mixer.core.service.gate_executor

import com.twitter.product_mixer.core.functional_component.gate.GateResult
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier

case class ExecutedGateResult(identifier: GateIdentifier, result: GateResult)
