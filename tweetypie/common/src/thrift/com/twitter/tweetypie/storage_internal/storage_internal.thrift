na space java com.tw ter.t etyp e.storage_ nternal.thr ftjava
#@na space scala com.tw ter.t etyp e.storage_ nternal.thr ftscala

struct StoredReply {
  1:  64  n_reply_to_status_ d (personalDataType = 'T et d')
  2:  64  n_reply_to_user_ d (personalDataType = 'User d')
  3: opt onal  64 conversat on_ d (personalDataType = 'T et d')
} (hasPersonalData = 'true', pers sted='true')

struct StoredShare {
  1:  64 s ce_status_ d (personalDataType = 'T et d')
  2:  64 s ce_user_ d (personalDataType = 'User d')
  3:  64 parent_status_ d (personalDataType = 'T et d')
} (hasPersonalData = 'true', pers sted='true')

struct StoredGeo {
  1: double lat ude (personalDataType = 'GpsCoord nates')
  2: double long ude (personalDataType = 'GpsCoord nates')
  3:  32 geo_prec s on (personalDataType = 'GpsCoord nates')
  4:  64 ent y_ d (personalDataType = 'Publ s dPrec seLocat onT et, Publ s dCoarseLocat onT et')
  5: opt onal str ng na  (personalDataType = 'Publ s dPrec seLocat onT et, Publ s dCoarseLocat onT et')
} (hasPersonalData = 'true', pers sted='true')

struct Stored d aEnt y {
  1:  64  d (personalDataType = ' d a d')
  2:  8  d a_type (personalDataType = 'ContentTypeT et d a')
  3:  16 w dth
  4:  16   ght
} (hasPersonalData = 'true', pers sted='true')

struct StoredNarrowcast {
  1: opt onal l st<str ng> language (personalDataType = ' nferredLanguage')
  2: opt onal l st<str ng> locat on (personalDataType = 'Publ s dCoarseLocat onT et')
  3: opt onal l st< 64>  ds (personalDataType = 'T et d')
} (hasPersonalData = 'true', pers sted='true')

struct StoredQuotedT et {
  1:  64 t et_ d (personalDataType = 'T et d')        // t  t et  d be ng quoted
  2:  64 user_ d (personalDataType = 'User d')          // t  user  d be ng quoted
  3: str ng short_url (personalDataType = 'ShortUrl')   // tco url - used w n render ng  n backwards-compat mode
} (hasPersonalData = 'true', pers sted='true')

struct StoredT et {
  1:  64  d (personalDataType = 'T et d')
  2: opt onal  64 user_ d (personalDataType = 'User d')
  3: opt onal str ng text (personalDataType = 'Pr vateT ets, Publ cT ets')
  4: opt onal str ng created_v a (personalDataType = 'Cl entType')
  5: opt onal  64 created_at_sec (personalDataType = 'Pr vateT  stamp, Publ cT  stamp')    //  n seconds

  6: opt onal StoredReply reply
  7: opt onal StoredShare share
  8: opt onal  64 contr butor_ d (personalDataType = 'Contr butor')
  9: opt onal StoredGeo geo
  11: opt onal bool has_takedown
  12: opt onal bool nsfw_user (personalDataType = 'T etSafetyLabels')
  13: opt onal bool nsfw_adm n (personalDataType = 'T etSafetyLabels')
  14: opt onal l st<Stored d aEnt y>  d a
  15: opt onal StoredNarrowcast narrowcast
  16: opt onal bool nullcast
  17: opt onal  64 track ng_ d (personalDataType = ' mpress on d')
  18: opt onal  64 updated_at (personalDataType = 'Pr vateT  stamp, Publ cT  stamp')
  19: opt onal StoredQuotedT et quoted_t et
} (hasPersonalData = 'true', pers sted='true')

struct CoreF elds {
  2: opt onal  64 user_ d (personalDataType = 'User d')
  3: opt onal str ng text (personalDataType = 'Pr vateT ets, Publ cT ets')
  4: opt onal str ng created_v a (personalDataType = 'Cl entType')
  5: opt onal  64 created_at_sec (personalDataType = 'Pr vateT  stamp, Publ cT  stamp')

  6: opt onal StoredReply reply
  7: opt onal StoredShare share
  8: opt onal  64 contr butor_ d (personalDataType = 'Contr butor')
  19: opt onal StoredQuotedT et quoted_t et
} (hasPersonalData = 'true', pers sted='true')

struct  nternalT et {
 1: opt onal CoreF elds core_f elds
} (hasPersonalData = 'true', pers sted='true')
