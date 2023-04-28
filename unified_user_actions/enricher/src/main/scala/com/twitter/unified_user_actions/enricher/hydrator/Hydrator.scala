package com.twitter.unified_user_actions.enricher.hydrator

import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey
import com.twitter.util.Future

trait Hydrator {
  def hydrate(
    instruction: EnrichmentInstruction,
    key: Option[EnrichmentKey],
    envelop: EnrichmentEnvelop
  ): Future[EnrichmentEnvelop]
}
