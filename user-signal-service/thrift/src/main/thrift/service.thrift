na space java com.tw ter.users gnalserv ce.thr ftjava
na space py gen.tw ter.users gnalserv ce.serv ce
#@na space scala com.tw ter.users gnalserv ce.thr ftscala
#@na space strato com.tw ter.users gnalserv ce.strato

 nclude "s gnal.thr ft"
 nclude "cl ent_ dent f er.thr ft"

struct S gnalRequest {
  1: opt onal  64 maxResults
  2: requ red s gnal.S gnalType s gnalType
}

struct BatchS gnalRequest {
  1: requ red  64 user d(personalDataType = "User d")
  2: requ red l st<S gnalRequest> s gnalRequest
  # make sure to populate t  cl ent d, ot rw se t  serv ce would throw except ons
  3: opt onal cl ent_ dent f er.Cl ent dent f er cl ent d
}(hasPersonalData='true')

struct BatchS gnalResponse {
  1: requ red map<s gnal.S gnalType, l st<s gnal.S gnal>> s gnalResponse
}
