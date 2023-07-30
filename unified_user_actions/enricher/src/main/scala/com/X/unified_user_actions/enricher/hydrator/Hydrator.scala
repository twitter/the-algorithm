package com.X.unified_user_actions.enricher.hydrator

import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.X.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey
import com.X.util.Future

trait Hydrator {
  def hydrate(
    instruction: EnrichmentInstruction,
    key: Option[EnrichmentKey],
    envelop: EnrichmentEnvelop
  ): Future[EnrichmentEnvelop]
}
