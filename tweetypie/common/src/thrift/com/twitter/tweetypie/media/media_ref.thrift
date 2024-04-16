na space java com.tw ter.t etyp e. d a.thr ftjava
#@na space scala com.tw ter.t etyp e. d a.thr ftscala
#@na space strato com.tw ter.t etyp e. d a
na space py gen.tw ter.t etyp e. d a
na space rb T etyP e


/**
* A  d aRef represents a reference to a p ece of  d a  n  d a nfoServ ce, along w h  tadata
* about t  s ce T et that t   d a ca  from  n case of pasted  d a.
**/
struct  d aRef {
  1: str ng gener c_ d a_key (personalDataType = ' d a d')

  // For T ets w h pasted  d a, t   d of t  T et w re t   d a was cop ed from
  2: opt onal  64 s ce_t et_ d (personalDataType = 'T et d')

  // T  author of s ce_t et_ d
  3: opt onal  64 s ce_user_ d (personalDataType = 'User d')
}(pers sted='true', hasPersonalData='true')
