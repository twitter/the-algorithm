na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "product.thr ft"
 nclude "product_context.thr ft"

 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"
 nclude "com/tw ter/recos/recos_common.thr ft"

struct UtegT etRequest {
	1: requ red cl ent_context.Cl entContext cl entContext
	2: requ red product.Product product
	# Product-spec f c para ters should be placed  n t  Product Context
	3: opt onal product_context.ProductContext productContext
	4: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct UtegT et {
  // t et  d
  1: requ red  64 t et d(personalDataType = 'T et d')
  // sum of   ghts of seed users who engaged w h t  t et.
  //  f a user engaged w h t  sa  t et tw ce, l ked   and ret eted  , t n  / r   ght was counted tw ce.
  2: requ red double score
  // user soc al proofs per engage nt type
  3: requ red map<recos_common.Soc alProofType, l st< 64>> soc alProofByType(personalDataTypeKey='Engage ntTypePr vate', personalDataTypeValue='User d')
} (pers sted='true', hasPersonalData = 'true')

struct UtegT etResponse {
  1: requ red l st<UtegT et> t ets
} (pers sted='true')
