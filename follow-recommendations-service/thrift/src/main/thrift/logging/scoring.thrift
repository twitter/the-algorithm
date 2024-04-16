na space java com.tw ter.follow_recom ndat ons.logg ng.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.logg ng.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons.logg ng

 nclude "com/tw ter/ml/ap /data.thr ft"

struct Cand dateS ceDeta ls {
  1: opt onal map<str ng, double> cand dateS ceScores
  2: opt onal  32 pr maryS ce
}(pers sted='true', hasPersonalData='false')

struct Score {
  1: requ red double value
  2: opt onal str ng ranker d
  3: opt onal str ng scoreType
}(pers sted='true', hasPersonalData='false') // scor ng and rank ng  nfo per rank ng stage

// Conta ns (1) t  ML-based  avy ranker and score (2) scores and rankers  n producer exper  nt fra work
struct Scores {
  1: requ red l st<Score> scores
  2: opt onal str ng selectedRanker d
  3: requ red bool  s nProducerScor ngExper  nt
}(pers sted='true', hasPersonalData='false')

struct Rank ng nfo {
  1: opt onal Scores scores
  2: opt onal  32 rank
}(pers sted='true', hasPersonalData='false')

// t  encapsulates all  nformat on related to t  rank ng process from generat on to scor ng
struct Scor ngDeta ls {
    1: opt onal Cand dateS ceDeta ls cand dateS ceDeta ls
    2: opt onal double score  // T  ML-based  avy ranker score
    3: opt onal data.DataRecord dataRecord
    4: opt onal l st<str ng> ranker ds  // all ranker  ds,  nclud ng (1) ML-based  avy ranker (2) non-ML adhoc rankers
    5: opt onal map<str ng, Rank ng nfo>  nfoPerRank ngStage  // scor ng and rank ng  nfo per rank ng stage
}(pers sted='true', hasPersonalData='true')

