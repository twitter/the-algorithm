na space java com.tw ter.representat onscorer.thr ftjava
#@na space scala com.tw ter.representat onscorer.thr ftscala
#@na space strato com.tw ter.representat onscorer

 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"
 nclude "com/tw ter/s mclusters_v2/onl ne_store.thr ft"
 nclude "com/tw ter/s mclusters_v2/score.thr ft"

struct S mClustersRecentEngage ntS m lar  es {
  // All scores computed us ng cos ne s m lar y
  // 1 - 1000 Pos  ve S gnals
  1: opt onal double fav1dLast10Max // max score from last 10 faves  n t  last 1 day
  2: opt onal double fav1dLast10Avg // avg score from last 10 faves  n t  last 1 day
  3: opt onal double fav7dLast10Max // max score from last 10 faves  n t  last 7 days
  4: opt onal double fav7dLast10Avg // avg score from last 10 faves  n t  last 7 days
  5: opt onal double ret et1dLast10Max // max score from last 10 ret ets  n t  last 1 days
  6: opt onal double ret et1dLast10Avg // avg score from last 10 ret ets  n t  last 1 days
  7: opt onal double ret et7dLast10Max // max score from last 10 ret ets  n t  last 7 days
  8: opt onal double ret et7dLast10Avg // avg score from last 10 ret ets  n t  last 7 days
  9: opt onal double follow7dLast10Max // max score from t  last 10 follows  n t  last 7 days
  10: opt onal double follow7dLast10Avg // avg score from t  last 10 follows  n t  last 7 days
  11: opt onal double follow30dLast10Max // max score from t  last 10 follows  n t  last 30 days
  12: opt onal double follow30dLast10Avg // avg score from t  last 10 follows  n t  last 30 days
  13: opt onal double share1dLast10Max // max score from last 10 shares  n t  last 1 day
  14: opt onal double share1dLast10Avg // avg score from last 10 shares  n t  last 1 day
  15: opt onal double share7dLast10Max // max score from last 10 shares  n t  last 7 days
  16: opt onal double share7dLast10Avg // avg score from last 10 shares  n t  last 7 days
  17: opt onal double reply1dLast10Max // max score from last 10 repl es  n t  last 1 day
  18: opt onal double reply1dLast10Avg // avg score from last 10 repl es  n t  last 1 day
  19: opt onal double reply7dLast10Max // max score from last 10 repl es  n t  last 7 days
  20: opt onal double reply7dLast10Avg // avg score from last 10 repl es  n t  last 7 days
  21: opt onal double or g nalT et1dLast10Max // max score from last 10 or g nal t ets  n t  last 1 day
  22: opt onal double or g nalT et1dLast10Avg // avg score from last 10 or g nal t ets  n t  last 1 day
  23: opt onal double or g nalT et7dLast10Max // max score from last 10 or g nal t ets  n t  last 7 days
  24: opt onal double or g nalT et7dLast10Avg // avg score from last 10 or g nal t ets  n t  last 7 days
  25: opt onal double v deoPlayback1dLast10Max // max score from last 10 v deo playback50  n t  last 1 day
  26: opt onal double v deoPlayback1dLast10Avg // avg score from last 10 v deo playback50  n t  last 1 day
  27: opt onal double v deoPlayback7dLast10Max // max score from last 10 v deo playback50  n t  last 7 days
  28: opt onal double v deoPlayback7dLast10Avg // avg score from last 10 v deo playback50  n t  last 7 days

  // 1001 - 2000  mpl c  S gnals

  // 2001 - 3000 Negat ve S gnals
  // Block Ser es
  2001: opt onal double block1dLast10Avg
  2002: opt onal double block1dLast10Max
  2003: opt onal double block7dLast10Avg
  2004: opt onal double block7dLast10Max
  2005: opt onal double block30dLast10Avg
  2006: opt onal double block30dLast10Max
  // Mute Ser es
  2101: opt onal double mute1dLast10Avg
  2102: opt onal double mute1dLast10Max
  2103: opt onal double mute7dLast10Avg
  2104: opt onal double mute7dLast10Max
  2105: opt onal double mute30dLast10Avg
  2106: opt onal double mute30dLast10Max
  // Report Ser es
  2201: opt onal double report1dLast10Avg
  2202: opt onal double report1dLast10Max
  2203: opt onal double report7dLast10Avg
  2204: opt onal double report7dLast10Max
  2205: opt onal double report30dLast10Avg
  2206: opt onal double report30dLast10Max
  // Dontl ke
  2301: opt onal double dontl ke1dLast10Avg
  2302: opt onal double dontl ke1dLast10Max
  2303: opt onal double dontl ke7dLast10Avg
  2304: opt onal double dontl ke7dLast10Max
  2305: opt onal double dontl ke30dLast10Avg
  2306: opt onal double dontl ke30dLast10Max
  // SeeFe r
  2401: opt onal double seeFe r1dLast10Avg
  2402: opt onal double seeFe r1dLast10Max
  2403: opt onal double seeFe r7dLast10Avg
  2404: opt onal double seeFe r7dLast10Max
  2405: opt onal double seeFe r30dLast10Avg
  2406: opt onal double seeFe r30dLast10Max
}(pers sted='true', hasPersonalData = 'true')

/*
 * L st score AP 
 */
struct L stScore d {
  1: requ red score.Scor ngAlgor hm algor hm
  2: requ red onl ne_store.ModelVers on modelVers on
  3: requ red  dent f er.Embedd ngType targetEmbedd ngType
  4: requ red  dent f er. nternal d target d
  5: requ red  dent f er.Embedd ngType cand dateEmbedd ngType
  6: requ red l st< dent f er. nternal d> cand date ds
}(hasPersonalData = 'true')

struct ScoreResult {
  // T  ap  does not commun cate why a score  s m ss ng. For example,   may be unava lable
  // because t  referenced ent  es do not ex st (e.g. t  embedd ng was not found) or because
  // t  outs prevented us from calculat ng  .
  1: opt onal double score
}

struct L stScoreResponse {
  1: requ red l st<ScoreResult> scores // Guaranteed to be t  sa  number/order as requested
}

struct RecentEngage ntS m lar  esResponse {
  1: requ red l st<S mClustersRecentEngage ntS m lar  es> results // Guaranteed to be t  sa  number/order as requested
}
