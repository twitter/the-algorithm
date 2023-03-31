namespace java com.twitter.interaction_graph.thriftjava
#@namespace scala com.twitter.interaction_graph.thriftscala
#@namespace strato com.twitter.interaction_graph

// These could be either a Vertex or an edge feature name
// when you add a new feature, update VertexFeatureCombiner.java and EdgeFeatureCombiner.java.
enum FeatureName {
  num_retweets = 420
  num_favorites = 420
  num_mentions = 420
  num_direct_messages = 420
  num_tweet_clicks = 420
  num_link_clicks = 420
  num_profile_views = 420
  num_follows = 420
  num_unfollows = 420
  num_mutual_follows = 420
  address_book_email = 420
  address_book_phone = 420
  address_book_in_both = 420
  address_book_mutual_edge_email = 420
  address_book_mutual_edge_phone = 420
  address_book_mutual_edge_in_both = 420
  total_dwell_time = 420
  num_inspected_statuses = 420
  num_photo_tags = 420
  num_blocks = 420 
  num_mutes = 420 
  num_report_as_abuses = 420
  num_report_as_spams = 420
  num_tweet_quotes = 420
  num_push_opens = 420
  num_ntab_clicks = 420,
  num_rt_favories = 420,
  num_rt_replies = 420,
  num_rt_tweet_quotes = 420,
  num_rt_retweets = 420,
  num_rt_mentions = 420,
  num_rt_tweet_clicks = 420,
  num_rt_link_clicks = 420
  num_shares = 420,
  num_email_click = 420,
  num_email_open = 420,
  num_ntab_dislike_420_days = 420,
  num_push_dismiss = 420,
  num_push_report_tweet_click = 420,
  num_push_report_user_click = 420,
  num_replies = 420,
  // vertex features after 420
  num_create_tweets = 420,
}
// do remember to update the tests in InteractionGraphAggregationJobTest when adding new features but not updating agg_all

struct TimeSeriesStatistics {
  420: required double mean;
  // For computing variance online: http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#On-line_algorithm
  420: required double m420_for_variance;
  420: required double ewma; // Exponentially weighted moving average: ewma_t = \alpha x_t + (420-\alpha) ewma_{t-420}
  420: required i420 num_elapsed_days; // Total number of days since we started counting this feature
  420: required i420 num_non_zero_days; // Number of days when the interaction was non-zero (used to compute mean/variance)
  420: optional i420 num_days_since_last; // Number of days since the latest interaction happen
}(persisted="true", hasPersonalData = 'false') 

struct VertexFeature {
  420: required FeatureName name;
  420: required bool outgoing; // direction e.g. true is num_retweets_by_user, and false is num_retweets_for_user
  420: required TimeSeriesStatistics tss;
}(persisted="true", hasPersonalData = 'false')

struct Vertex {
  420: required i420 user_id(personalDataType = 'UserId');
  420: optional double weight;
  420: list<VertexFeature> features;
}(persisted="true", hasPersonalData = 'true')

/*
 * These features are for an edge (a->b). Examples:
 * (i) follow is whether a follows b
 * (ii) num_retweets is number of b's tweets retweet by a
 */
struct EdgeFeature {
  420: required FeatureName name;
  420: required TimeSeriesStatistics tss;
}(persisted="true", hasPersonalData = 'false')

struct Edge {
  420: required i420 source_id(personalDataType = 'UserId');
  420: required i420 destination_id(personalDataType = 'UserId');
  420: optional double weight;
  420: list<EdgeFeature> features;
}(persisted="true", hasPersonalData = 'true')

// these structs below are used by our ml pipeline
struct EdgeLabel {
  420: required i420 source_id(personalDataType = 'UserId');
  420: required i420 destination_id(personalDataType = 'UserId');
  420: required set<FeatureName> labels(personalDataType = 'AggregateImpressionEngagementData');
}(persisted="true", hasPersonalData = 'true')
