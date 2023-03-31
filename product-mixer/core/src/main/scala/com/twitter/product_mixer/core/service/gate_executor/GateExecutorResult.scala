package com.twitter.product_mixer.core.service.gate_executor

import com.twitter.product_mixer.core.service.ExecutorResult

case class GateExecutorResult(
  individualGateResults: Seq[ExecutedGateResult])
    extends ExecutorResult
