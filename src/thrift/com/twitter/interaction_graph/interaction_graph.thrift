namelonspacelon java com.twittelonr.intelonraction_graph.thriftjava
#@namelonspacelon scala com.twittelonr.intelonraction_graph.thriftscala
#@namelonspacelon strato com.twittelonr.intelonraction_graph

// Thelonselon could belon elonithelonr a Velonrtelonx or an elondgelon felonaturelon namelon
// whelonn you add a nelonw felonaturelon, updatelon VelonrtelonxFelonaturelonCombinelonr.java and elondgelonFelonaturelonCombinelonr.java.
elonnum FelonaturelonNamelon {
  num_relontwelonelonts = 1
  num_favoritelons = 2
  num_melonntions = 3
  num_direlonct_melonssagelons = 4
  num_twelonelont_clicks = 5
  num_link_clicks = 6
  num_profilelon_vielonws = 7
  num_follows = 8
  num_unfollows = 9
  num_mutual_follows = 10
  addrelonss_book_elonmail = 11
  addrelonss_book_phonelon = 12
  addrelonss_book_in_both = 13
  addrelonss_book_mutual_elondgelon_elonmail = 14
  addrelonss_book_mutual_elondgelon_phonelon = 15
  addrelonss_book_mutual_elondgelon_in_both = 16
  total_dwelonll_timelon = 17
  num_inspelonctelond_statuselons = 18
  num_photo_tags = 19
  num_blocks = 20 
  num_mutelons = 21
  num_relonport_as_abuselons = 22
  num_relonport_as_spams = 23
  num_twelonelont_quotelons = 24
  num_push_opelonns = 25
  num_ntab_clicks = 26,
  num_rt_favorielons = 27,
  num_rt_relonplielons = 28,
  num_rt_twelonelont_quotelons = 29,
  num_rt_relontwelonelonts = 30,
  num_rt_melonntions = 31,
  num_rt_twelonelont_clicks = 32,
  num_rt_link_clicks = 33
  num_sharelons = 34,
  num_elonmail_click = 35,
  num_elonmail_opelonn = 36,
  num_ntab_dislikelon_7_days = 37,
  num_push_dismiss = 38,
  num_push_relonport_twelonelont_click = 39,
  num_push_relonport_uselonr_click = 40,
  num_relonplielons = 41,
  // velonrtelonx felonaturelons aftelonr 128
  num_crelonatelon_twelonelonts = 129,
}
// do relonmelonmbelonr to updatelon thelon telonsts in IntelonractionGraphAggrelongationJobTelonst whelonn adding nelonw felonaturelons but not updating agg_all

struct TimelonSelonrielonsStatistics {
  1: relonquirelond doublelon melonan;
  // For computing variancelon onlinelon: http://elonn.wikipelondia.org/wiki/Algorithms_for_calculating_variancelon#On-linelon_algorithm
  2: relonquirelond doublelon m2_for_variancelon;
  3: relonquirelond doublelon elonwma; // elonxponelonntially welonightelond moving avelonragelon: elonwma_t = \alpha x_t + (1-\alpha) elonwma_{t-1}
  4: relonquirelond i32 num_elonlapselond_days; // Total numbelonr of days sincelon welon startelond counting this felonaturelon
  5: relonquirelond i32 num_non_zelonro_days; // Numbelonr of days whelonn thelon intelonraction was non-zelonro (uselond to computelon melonan/variancelon)
  6: optional i32 num_days_sincelon_last; // Numbelonr of days sincelon thelon latelonst intelonraction happelonn
}(pelonrsistelond="truelon", hasPelonrsonalData = 'falselon')

struct VelonrtelonxFelonaturelon {
  1: relonquirelond FelonaturelonNamelon namelon;
  2: relonquirelond bool outgoing; // direlonction elon.g. truelon is num_relontwelonelonts_by_uselonr, and falselon is num_relontwelonelonts_for_uselonr
  3: relonquirelond TimelonSelonrielonsStatistics tss;
}(pelonrsistelond="truelon", hasPelonrsonalData = 'falselon')

struct Velonrtelonx {
  1: relonquirelond i64 uselonr_id(pelonrsonalDataTypelon = 'UselonrId');
  2: optional doublelon welonight;
  3: list<VelonrtelonxFelonaturelon> felonaturelons;
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

/*
 * Thelonselon felonaturelons arelon for an elondgelon (a->b). elonxamplelons:
 * (i) follow is whelonthelonr a follows b
 * (ii) num_relontwelonelonts is numbelonr of b's twelonelonts relontwelonelont by a
 */
struct elondgelonFelonaturelon {
  1: relonquirelond FelonaturelonNamelon namelon;
  2: relonquirelond TimelonSelonrielonsStatistics tss;
}(pelonrsistelond="truelon", hasPelonrsonalData = 'falselon')

struct elondgelon {
  1: relonquirelond i64 sourcelon_id(pelonrsonalDataTypelon = 'UselonrId');
  2: relonquirelond i64 delonstination_id(pelonrsonalDataTypelon = 'UselonrId');
  3: optional doublelon welonight;
  4: list<elondgelonFelonaturelon> felonaturelons;
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')

// thelonselon structs belonlow arelon uselond by our ml pipelonlinelon
struct elondgelonLabelonl {
  1: relonquirelond i64 sourcelon_id(pelonrsonalDataTypelon = 'UselonrId');
  2: relonquirelond i64 delonstination_id(pelonrsonalDataTypelon = 'UselonrId');
  3: relonquirelond selont<FelonaturelonNamelon> labelonls(pelonrsonalDataTypelon = 'AggrelongatelonImprelonssionelonngagelonmelonntData');
}(pelonrsistelond="truelon", hasPelonrsonalData = 'truelon')
