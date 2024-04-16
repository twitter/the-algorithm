na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.t et_s m lar y
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

struct FeaturedT et {
  1: requ red  64 t et d(personalDataType = 'T et d')
  # t  stamp w n t  user engaged or  mpressed t  t et
  2: requ red  64 t  stamp(personalDataType = 'Pr vateT  stamp')
}(pers sted = 'true', hasPersonalData = 'true')

struct LabelledT etPa rs {
  1: requ red FeaturedT et queryFeaturedT et
  2: requ red FeaturedT et cand dateFeaturedT et
  3: requ red bool label
}(pers sted = 'true', hasPersonalData = 'true')
