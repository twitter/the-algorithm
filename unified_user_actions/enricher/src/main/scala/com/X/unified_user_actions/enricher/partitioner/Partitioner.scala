package com.X.unified_user_actions.enricher.partitioner

import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey

trait Partitioner {
  def repartition(
    instruction: EnrichmentInstruction,
    envelop: EnrichmentEnvelop
  ): Option[EnrichmentKey]
}
