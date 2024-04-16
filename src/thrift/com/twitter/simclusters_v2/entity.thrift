na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.ent y
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "com/tw ter/algeb rd_ nternal/algeb rd.thr ft"

/**
 * Pengu n text ent y. All f elds are requ red as t   s used as a part of a  mcac  key.
 **/
struct Pengu nKey {
  1: requ red str ng textEnt y
}(hasPersonalData = 'false')

/**
 * NER text ent y. All f elds are requ red as t   s used as a part of a  mcac  key.
 **/
struct NerKey {
  1: requ red str ng textEnt y
  2: requ red  32 wholeEnt yType
}(hasPersonalData = 'false')

/**
 * Semant c Core text ent y. All f elds are requ red as t   s used as a part of a  mcac  key.
 **/
struct Semant cCoreKey {
  1: requ red  64 ent y d(personalDataType = 'Semant ccoreClass f cat on')
}(hasPersonalData = 'true')

/**
 * Represents an ent y extracted from a t et.
 **/
un on T etTextEnt y {
  1: str ng hashtag
  2: Pengu nKey pengu n
  3: NerKey ner
  4: Semant cCoreKey semant cCore
}(hasPersonalData = 'true')

struct Space d {
  1: str ng  d
}(hasPersonalData = 'true')

/**
 * All poss ble ent  es that s mclusters are assoc ated w h.
 **/
un on S mClusterEnt y {
  1:  64 t et d(personalDataType = 'T et d')
  2: T etTextEnt y t etEnt y
  3: Space d space d
}(hasPersonalData = 'true')
