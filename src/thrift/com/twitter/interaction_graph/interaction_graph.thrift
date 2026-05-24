namespace java com.twitter.interaction_graph.thriftjava
#@namespace scala com.twitter.interaction_graph.thriftscala
#@namespace strato com.twitter.interaction_graph

// These could be either a Vertex or an edge feature name
// when you add a new feature, update VertexFeatureCombiner.java and EdgeFeatureCombiner.java.
enum FeatureName {
  num_retweets = 1
  num_favorites = 2
  num_mentions = 3
  num_direct_messages = 4
  num_tweet_clicks = 5
  num_link_clicks = 6
  num_profile_views = 7
  num_follows = 8
  num_unfollows = 9
  num_mutual_follows = 10
  address_book_email = 11
  address_book_phone = 12
  address_book_in_both = 13
  address_book_mutual_edge_email = 14
  address_book_mutual_edge_phone = 15
  address_book_mutual_edge_in_both = 16
  total_dwell_time = 17
  num_inspected_statuses = 18
  num_photo_tags = 19
  num_blocks = 20 
  num_mutes = 21 
  num_report_as_abuses = 22
  num_report_as_spams = 23
  num_tweet_quotes = 24
  num_push_opens = 25
  num_ntab_clicks = 26,
  num_rt_favories = 27,
  num_rt_replies = 28,
  num_rt_tweet_quotes = 29,
  num_rt_retweets = 30,
  num_rt_mentions = 31,
  num_rt_tweet_clicks = 32,
  num_rt_link_clicks = 33
  num_shares = 34,
  num_email_click = 35,
  num_email_open = 36,
  num_ntab_dislike_7_days = 37,
  num_push_dismiss = 38,
  num_push_report_tweet_click = 39,
  num_push_report_user_click = 40,
  num_replies = 41,
  // vertex features after 128
  num_create_tweets = 129,
}
// do remember to update the tests in InteractionGraphAggregationJobTest when adding new features but not updating agg_all

struct TimeSeriesStatistics {
  1: required double mean;
  // For computing variance online: http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#On-line_algorithm
  2: required double m2_for_variance;
  3: required double ewma; // Exponentially weighted moving average: ewma_t = \alpha x_t + (1-\alpha) ewma_{t-1}
  4: required i32 num_elapsed_days; // Total number of days since we started counting this feature
  5: required i32 num_non_zero_days; // Number of days when the interaction was non-zero (used to compute mean/variance)
  6: optional i32 num_days_since_last; // Number of days since the latest interaction happen
}(persisted="true", hasPersonalData = 'false') 

struct VertexFeature {
  1: required FeatureName name;
  2: required bool outgoing; // direction e.g. true is num_retweets_by_user, and false is num_retweets_for_user
  3: required TimeSeriesStatistics tss;
}(persisted="true", hasPersonalData = 'false')

struct Vertex {
  1: required i64 user_id(personalDataType = 'UserId');
  2: optional double weight;
  3: list<VertexFeature> features;
}(persisted="true", hasPersonalData = 'true')

/*
 * These features are for an edge (a->b). Examples:
 * (i) follow is whether a follows b
 * (ii) num_retweets is number of b's tweets retweet by a
 */
struct EdgeFeature {
  1: required FeatureName name;
  2: required TimeSeriesStatistics tss;
}(persisted="true", hasPersonalData = 'false')

struct Edge {
  1: required i64 source_id(personalDataType = 'UserId');
  2: required i64 destination_id(personalDataType = 'UserId');
  3: optional double weight;
  4: list<EdgeFeature> features;
}(persisted="true", hasPersonalData = 'true')

// these structs below are used by our ml pipeline
struct EdgeLabel {
  1: required i64 source_id(personalDataType = 'UserId');
  2: required i64 destination_id(personalDataType = 'UserId');
  3: required set<FeatureName> labels(personalDataType = 'AggregateImpressionEngagementData');
}(persisted="true", hasPersonalData = 'true')
