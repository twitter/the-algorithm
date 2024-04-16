na space java com.tw ter.users gnalserv ce.thr ftjava
na space py gen.tw ter.users gnalserv ce.serv ce
#@na space scala com.tw ter.users gnalserv ce.thr ftscala
#@na space strato com.tw ter.users gnalserv ce.strato

# Cl ent dent f er should be def ned as Serv ce d_Product
enum Cl ent dent f er {
  # reserve 1-10 for CrM xer
  CrM xer_Ho  = 1
  CrM xer_Not f cat ons = 2
  CrM xer_Ema l = 3
  # reserve 11-20 for RSX
  Representat onScorer_Ho  = 11
  Representat onScorer_Not f cat ons = 12

  # reserve 21-30 for Explore
  ExploreRanker = 21

  #   w ll throw an except on after   make sure all cl ents are send ng t 
  # Cl ent dent f er  n t  r request.
  Unknown = 9999
}
