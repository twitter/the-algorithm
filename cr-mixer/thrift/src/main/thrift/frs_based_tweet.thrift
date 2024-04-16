na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "product.thr ft"
 nclude "product_context.thr ft"
 nclude "com/tw ter/product_m xer/core/cl ent_context.thr ft"

struct FrsT etRequest {
1: requ red cl ent_context.Cl entContext cl entContext
2: requ red product.Product product
3: opt onal product_context.ProductContext productContext
# excludedUser ds - user  ds to be excluded from FRS cand date generat on
4: opt onal l st< 64> excludedUser ds (personalDataType = 'User d')
# excludedT et ds - t et  ds to be excluded from Earlyb rd cand date generat on
5: opt onal l st< 64> excludedT et ds (personalDataType = 'T et d')
} (pers sted='true', hasPersonalData='true')

struct FrsT et {
1: requ red  64 t et d (personalDataType = 'T et d')
2: requ red  64 author d (personalDataType = 'User d')
# sk p 3  n case   need t et score  n t  future
# frsPr maryS ce - wh ch FRS cand date s ce  s t  pr mary one to generate t  author
4: opt onal  32 frsPr maryS ce
# frsCand dateS ceScores - FRS cand date s ces and t  scores for t  author
# for  32 to algor hm mapp ng, see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/ rm / rm -core/src/ma n/scala/com/tw ter/ rm /constants/Algor hmFeedbackTokens.scala?L12
5: opt onal map< 32, double> frsCand dateS ceScores
# frsPr maryScore - t  score of t  FRS pr mary cand date s ce
6: opt onal double frsAuthorScore
} (pers sted='true', hasPersonalData = 'true')

struct FrsT etResponse {
  1: requ red l st<FrsT et> t ets
} (pers sted='true')

