namespace java com.twitter.tweetypie.creative_entity_enrichments.thriftjava
#@ namespace scala com.twitter.tweetypie.creative_entity_enrichments.thriftscala
#@ namespace strato com.twitter.tweetypie.creative_entity_enrichments
namespace py gen.twitter.tweetypie.creative_entity_enrichments

include "com/twitter/strato/columns/creative_entity_enrichments/enrichments.thrift"

struct CreativeEntityEnrichmentRef {
  1: required i64 enrichmentId
}(persisted='true', hasPersonalData='false')

/**
 * This struct represents a collection of enrichments applied to a tweet.
 * The enrichment for a tweet is just a metadata attached to a tweet
 * Each enrichment has a unique id (EnrichmentId) to uniquely identify an enrichment.
 *
 * enrichment_type signifies the type of an enrichment (eg: Interactive Text).
 */
struct CreativeEntityEnrichments {
  1: required map<enrichments.EnrichmentType, CreativeEntityEnrichmentRef> enrichment_type_to_ref
}(persisted='true', hasPersonalData='false')
