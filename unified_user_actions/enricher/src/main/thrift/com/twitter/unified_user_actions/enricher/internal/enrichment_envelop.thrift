namespace java com.twitter.unified_user_actions.enricher.internal.thriftjava
#@namespace scala com.twitter.unified_user_actions.enricher.internal.thriftscala
#@namespace strato com.twitter.unified_user_actions.enricher.internal

include "com/twitter/unified_user_actions/unified_user_actions.thrift"
include "enrichment_plan.thrift"

struct EnrichmentEnvelop {
  /**
  * An internal ID that uniquely identifies this event created during the early stages of enrichment.
  * It is useful for detecting debugging, tracing & profiling the events throughout the process.
  **/
  1: required i64 envelopId

  /**
  * The UUA event to be enriched / currently being enriched / has been enriched depending on the
  * stages of the enrichment process.
  **/
  2: unified_user_actions.UnifiedUserAction uua

  /**
  * The current enrichment plan. It keeps track of what is currently being enriched, what still
  * needs to be done so that we can bring the enrichment process to completion.
  **/
  3: enrichment_plan.EnrichmentPlan plan
}(persisted='true', hasPersonalData='true')
