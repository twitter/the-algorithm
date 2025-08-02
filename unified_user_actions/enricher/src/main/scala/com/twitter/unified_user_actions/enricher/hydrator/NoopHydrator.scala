package com.twitter.unified_user_actions.enricher.hydrator
import com.twitter.unified_user_actions.enricher.ImplementationEtwitterception
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentEnvelop
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentInstruction
import com.twitter.unified_user_actions.enricher.internal.thriftscala.EnrichmentKey
import com.twitter.util.Future

/**
 * This hydrator does nothing. If it's used by mistake for any reason, an etwitterception will be thrown.
 * Use this when you etwitterpect to have no hydration (for etwitterample, the planner shouldn't hydrate anything
 * and only would perform the partitioning function).
 */
object NoopHydrator {
  val OutputTopic: Option[String] = None
}

class NoopHydrator etwittertends Hydrator {
  override def hydrate(
    instruction: EnrichmentInstruction,
    key: Option[EnrichmentKey],
    envelop: EnrichmentEnvelop
  ): Future[EnrichmentEnvelop] = {
    throw new ImplementationEtwitterception(
      "NoopHydrator shouldn't be invoked when configure. Check your " +
        "enrichment plan.")
  }
}
