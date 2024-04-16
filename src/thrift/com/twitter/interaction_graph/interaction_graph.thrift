na space java com.tw ter. nteract on_graph.thr ftjava
#@na space scala com.tw ter. nteract on_graph.thr ftscala
#@na space strato com.tw ter. nteract on_graph

// T se could be e  r a Vertex or an edge feature na 
// w n   add a new feature, update VertexFeatureComb ner.java and EdgeFeatureComb ner.java.
enum FeatureNa  {
  num_ret ets = 1
  num_favor es = 2
  num_ nt ons = 3
  num_d rect_ ssages = 4
  num_t et_cl cks = 5
  num_l nk_cl cks = 6
  num_prof le_v ews = 7
  num_follows = 8
  num_unfollows = 9
  num_mutual_follows = 10
  address_book_ema l = 11
  address_book_phone = 12
  address_book_ n_both = 13
  address_book_mutual_edge_ema l = 14
  address_book_mutual_edge_phone = 15
  address_book_mutual_edge_ n_both = 16
  total_d ll_t   = 17
  num_ nspected_statuses = 18
  num_photo_tags = 19
  num_blocks = 20 
  num_mutes = 21 
  num_report_as_abuses = 22
  num_report_as_spams = 23
  num_t et_quotes = 24
  num_push_opens = 25
  num_ntab_cl cks = 26,
  num_rt_favor es = 27,
  num_rt_repl es = 28,
  num_rt_t et_quotes = 29,
  num_rt_ret ets = 30,
  num_rt_ nt ons = 31,
  num_rt_t et_cl cks = 32,
  num_rt_l nk_cl cks = 33
  num_shares = 34,
  num_ema l_cl ck = 35,
  num_ema l_open = 36,
  num_ntab_d sl ke_7_days = 37,
  num_push_d sm ss = 38,
  num_push_report_t et_cl ck = 39,
  num_push_report_user_cl ck = 40,
  num_repl es = 41,
  // vertex features after 128
  num_create_t ets = 129,
}
// do re mber to update t  tests  n  nteract onGraphAggregat onJobTest w n add ng new features but not updat ng agg_all

struct T  Ser esStat st cs {
  1: requ red double  an;
  // For comput ng var ance onl ne: http://en.w k ped a.org/w k /Algor hms_for_calculat ng_var ance#On-l ne_algor hm
  2: requ red double m2_for_var ance;
  3: requ red double ewma; // Exponent ally   ghted mov ng average: ewma_t = \alpha x_t + (1-\alpha) ewma_{t-1}
  4: requ red  32 num_elapsed_days; // Total number of days s nce   started count ng t  feature
  5: requ red  32 num_non_zero_days; // Number of days w n t   nteract on was non-zero (used to compute  an/var ance)
  6: opt onal  32 num_days_s nce_last; // Number of days s nce t  latest  nteract on happen
}(pers sted="true", hasPersonalData = 'false') 

struct VertexFeature {
  1: requ red FeatureNa  na ;
  2: requ red bool outgo ng; // d rect on e.g. true  s num_ret ets_by_user, and false  s num_ret ets_for_user
  3: requ red T  Ser esStat st cs tss;
}(pers sted="true", hasPersonalData = 'false')

struct Vertex {
  1: requ red  64 user_ d(personalDataType = 'User d');
  2: opt onal double   ght;
  3: l st<VertexFeature> features;
}(pers sted="true", hasPersonalData = 'true')

/*
 * T se features are for an edge (a->b). Examples:
 * ( ) follow  s w t r a follows b
 * (  ) num_ret ets  s number of b's t ets ret et by a
 */
struct EdgeFeature {
  1: requ red FeatureNa  na ;
  2: requ red T  Ser esStat st cs tss;
}(pers sted="true", hasPersonalData = 'false')

struct Edge {
  1: requ red  64 s ce_ d(personalDataType = 'User d');
  2: requ red  64 dest nat on_ d(personalDataType = 'User d');
  3: opt onal double   ght;
  4: l st<EdgeFeature> features;
}(pers sted="true", hasPersonalData = 'true')

// t se structs below are used by   ml p pel ne
struct EdgeLabel {
  1: requ red  64 s ce_ d(personalDataType = 'User d');
  2: requ red  64 dest nat on_ d(personalDataType = 'User d');
  3: requ red set<FeatureNa > labels(personalDataType = 'Aggregate mpress onEngage ntData');
}(pers sted="true", hasPersonalData = 'true')
