na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "product.thr ft"
 nclude "product_context.thr ft"

 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"
 nclude "com/tw ter/ads/sc ma/shared.thr ft"

struct AdsRequest {
	1: requ red cl ent_context.Cl entContext cl entContext
	2: requ red product.Product product
	# Product-spec f c para ters should be placed  n t  Product Context
	3: opt onal product_context.ProductContext productContext
	4: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct AdsResponse {
  1: requ red l st<AdT etRecom ndat on> ads
} (pers sted='true')

struct AdT etRecom ndat on {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: requ red double score
  3: opt onal l st<L ne em nfo> l ne ems

} (pers sted='true')

struct L ne em nfo {
  1: requ red  64 l ne em d (personalDataType = 'L ne em d')
  2: requ red shared.L ne emObject ve l ne emObject ve
} (pers sted='true')
