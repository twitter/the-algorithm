na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"
 nclude "product.thr ft"
 nclude "product_context.thr ft"
 nclude "s ce_type.thr ft"


struct Top cT etRequest {
    1: requ red cl ent_context.Cl entContext cl entContext
    2: requ red product.Product product
    3: requ red l st< 64> top c ds
    5: opt onal product_context.ProductContext productContext
    6: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct Top cT et {
    1: requ red  64 t et d (personalDataType = 'T et d')
    2: requ red double score
    3: requ red s ce_type.S m lar yEng neType s m lar yEng neType
} (pers sted='true', hasPersonalData = 'true')

struct Top cT etResponse {
    1: requ red map< 64, l st<Top cT et>> t ets
} (pers sted='true')

