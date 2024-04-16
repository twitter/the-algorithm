na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space py gen.tw ter.t etyp e.deprecated
na space rb T etyP e

 nclude "com/tw ter/expandodo/cards.thr ft"
 nclude "com/tw ter/g zmoduck/user.thr ft"
 nclude "com/tw ter/t etyp e/ d a_ent y.thr ft"
 nclude "com/tw ter/t etyp e/t et.thr ft"
 nclude "com/tw ter/t etyp e/t et_serv ce.thr ft"

/**
 * @deprecated Use Place
 */
struct Geo {
  /**
   * @deprecated Use coord nates.lat ude
   */
  1: double lat ude = 0.0 (personalDataType = 'GpsCoord nates')

  /**
   * @deprecated Use coord nates.long ude
   */
  2: double long ude = 0.0 (personalDataType = 'GpsCoord nates')

  /**
   * @deprecated Use coord nates.geo_prec s on
   */
  3:  32 geo_prec s on = 0

  /**
   * 0: don't show lat/long
   * 2: show
   *
   * @deprecated
   */
  4:  64 ent y_ d = 0

  /**
   * @deprecated Use place_ d
   */
  5: opt onal str ng na  (personalDataType = 'Publ s dCoarseLocat onT et')

  6: opt onal t et.Place place // prov ded  f StatusRequestOpt ons.load_places  s set
  7: opt onal str ng place_ d // ex: ad2f50942562790b
  8: opt onal t et.GeoCoord nates coord nates
}(pers sted = 'true', hasPersonalData = 'true')

/**
 * @deprecated Use T et and AP s that accept or return T et.
 */
struct Status {
  1:  64  d (personalDataType = 'T et d')
  2:  64 user_ d (personalDataType = 'User d')
  3: str ng text (personalDataType = 'Pr vateT ets, Publ cT ets')
  4: str ng created_v a (personalDataType = 'Cl entType')
  5:  64 created_at //  n seconds
  6: l st<t et.UrlEnt y> urls = []
  7: l st<t et. nt onEnt y>  nt ons = []
  8: l st<t et.HashtagEnt y> hashtags = []
  29: l st<t et.CashtagEnt y> cashtags = []
  9: l st< d a_ent y. d aEnt y>  d a = []
  10: opt onal t et.Reply reply
  31: opt onal t et.D rectedAtUser d rected_at_user
  11: opt onal t et.Share share
  32: opt onal t et.QuotedT et quoted_t et
  12: opt onal t et.Contr butor contr butor
  13: opt onal Geo geo
  // has_takedown  nd cates  f t re  s a takedown spec f cally on t  t et.
  // takedown_country_codes conta ns takedown countr es for both t  t et and t  user,
  // so has_takedown m ght be false wh le takedown_country_codes  s non-empty.
  14: bool has_takedown = 0
  15: bool nsfw_user = 0
  16: bool nsfw_adm n = 0
  17: opt onal t et.StatusCounts counts
  // 18: obsoleted
  19: opt onal t et.Dev ceS ce dev ce_s ce // not set on DB fa lure
  20: opt onal t et.Narrowcast narrowcast
  21: opt onal l st<str ng> takedown_country_codes (personalDataType = 'ContentRestr ct onStatus')
  22: opt onal t et.StatusPerspect ve perspect ve // not set  f no user  D or on TLS fa lure
  23: opt onal l st<cards.Card> cards // only  ncluded  f StatusRequestOpt ons. nclude_cards == true
  // only  ncluded w n StatusRequestOpt ons. nclude_cards == true
  // and StatusRequestOpt ons.cards_platform_key  s set to val d value
  30: opt onal cards.Card2 card2
  24: bool nullcast = 0
  25: opt onal  64 conversat on_ d (personalDataType = 'T et d')
  26: opt onal t et.Language language
  27: opt onal  64 track ng_ d (personalDataType = ' mpress on d')
  28: opt onal map<t et.SpamS gnalType, t et.SpamLabel> spam_labels
  33: opt onal bool has_ d a
  // obsolete 34: opt onal l st<t et.Top cLabel> top c_labels
  // Add  onal f elds for flex ble sc ma
  101: opt onal t et.T et d aTags  d a_tags
  103: opt onal t et.CardB nd ngValues b nd ng_values
  104: opt onal t et.ReplyAddresses reply_addresses
  105: opt onal t et.Tw terSuggest nfo tw ter_suggest_ nfo
}(pers sted = 'true', hasPersonalData = 'true')

