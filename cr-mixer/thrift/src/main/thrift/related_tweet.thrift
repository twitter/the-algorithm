na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "product.thr ft"
 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"

struct RelatedT etRequest {
  1: requ red  dent f er. nternal d  nternal d
	2: requ red product.Product product
	3: requ red cl ent_context.Cl entContext cl entContext # RUX LogOut w ll have cl entContext.user d = None
	4: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct RelatedT et {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: opt onal double score
  3: opt onal  64 author d (personalDataType = 'User d')
} (pers sted='true', hasPersonalData='true')

struct RelatedT etResponse {
  1: requ red l st<RelatedT et> t ets
} (pers sted='true')
