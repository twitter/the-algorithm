package com.twitter.unified_user_actions.enricher.partitioner

import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey

trait Partitioner {
  def repartition(
    instruction: EnrichmentInstruction,
    envelop: EnrichmentEnvelop
  ): Option[EnrichmentKey]
}
