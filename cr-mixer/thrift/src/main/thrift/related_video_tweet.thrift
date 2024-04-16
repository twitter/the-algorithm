na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "product.thr ft"
 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"

struct RelatedV deoT etRequest {
  1: requ red  dent f er. nternal d  nternal d
	2: requ red product.Product product
	3: requ red cl ent_context.Cl entContext cl entContext # RUX LogOut w ll have cl entContext.user d = None
	4: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct RelatedV deoT et {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: opt onal double score
} (pers sted='true', hasPersonalData='true')

struct RelatedV deoT etResponse {
  1: requ red l st<RelatedV deoT et> t ets
} (pers sted='true')
