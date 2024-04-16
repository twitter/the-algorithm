na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "s ce_type.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"

struct S m lar yEng ne {
 1: requ red s ce_type.S m lar yEng neType s m lar yEng neType
 2: opt onal str ng model d
 3: opt onal double score
} (pers sted='true')

struct Cand dateGenerat onKey {
  1: requ red s ce_type.S ceType s ceType
  2: requ red  64 s ceEventT   (personalDataType = 'Pr vateT  stamp')
  3: requ red  dent f er. nternal d  d
  4: requ red str ng model d
  5: opt onal s ce_type.S m lar yEng neType s m lar yEng neType
  6: opt onal l st<S m lar yEng ne> contr but ngS m lar yEng ne
} (pers sted='true')
