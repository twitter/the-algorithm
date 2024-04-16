na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

 nclude "com/tw ter/ml/ap /data.thr ft"

struct Cand dateS ceDeta ls {
  1: opt onal map<str ng, double> cand dateS ceScores
  2: opt onal  32 pr maryS ce
  3: opt onal map<str ng,  32> cand dateS ceRanks
}(hasPersonalData='false')

struct Score {
  1: requ red double value
  2: opt onal str ng ranker d
  3: opt onal str ng scoreType
}(hasPersonalData='false')

// Conta ns (1) t  ML-based  avy ranker and score (2) scores and rankers  n producer exper  nt fra work
struct Scores {
  1: requ red l st<Score> scores
  2: opt onal str ng selectedRanker d
  3: requ red bool  s nProducerScor ngExper  nt
}(hasPersonalData='false')

struct Rank ng nfo {
  1: opt onal Scores scores
  2: opt onal  32 rank
}(hasPersonalData='false')

// t  encapsulates all  nformat on related to t  rank ng process from generat on to scor ng
struct Scor ngDeta ls {
    1: opt onal Cand dateS ceDeta ls cand dateS ceDeta ls
    2: opt onal double score
    3: opt onal data.DataRecord dataRecord
    4: opt onal l st<str ng> ranker ds
    5: opt onal DebugDataRecord debugDataRecord // t  f eld  s not logged as  's only used for debugg ng
    6: opt onal map<str ng, Rank ng nfo>  nfoPerRank ngStage  // scor ng and rank ng  nfo per rank ng stage
}(hasPersonalData='true')

// exactly t  sa  as a data record, except that   store t  feature na   nstead of t   d
struct DebugDataRecord {
  1: opt onal set<str ng> b naryFeatures;                     // stores B NARY features
  2: opt onal map<str ng, double> cont nuousFeatures;         // stores CONT NUOUS features
  3: opt onal map<str ng,  64> d screteFeatures;              // stores D SCRETE features
  4: opt onal map<str ng, str ng> str ngFeatures;             // stores STR NG features
  5: opt onal map<str ng, set<str ng>> sparseB naryFeatures;  // stores sparse B NARY features
  6: opt onal map<str ng, map<str ng, double>> sparseCont nuousFeatures; // sparse CONT NUOUS features
}(hasPersonalData='true')
