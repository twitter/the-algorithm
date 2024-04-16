na space java com.tw ter.t etyp e.thr ftjava.federated
#@na space scala com.tw ter.t etyp e.thr ftscala.federated
#@na space strato com.tw ter.t etyp e.federated

 nclude "com/tw ter/t etyp e/stored_t et_ nfo.thr ft"

typedef  16 F eld d

struct GetStoredT etsV ew {
  1: bool bypass_v s b l y_f lter ng = 0
  2: opt onal  64 for_user_ d
  3: l st<F eld d> add  onal_f eld_ ds = []
}

struct GetStoredT etsResponse {
  1: stored_t et_ nfo.StoredT et nfo stored_t et
}

struct GetStoredT etsByUserV ew {
  1: bool bypass_v s b l y_f lter ng = 0
  2: bool set_for_user_ d = 0
  3: opt onal  64 start_t  _msec
  4: opt onal  64 end_t  _msec
  5: opt onal  64 cursor
  6: bool start_from_oldest = 0
  7: l st<F eld d> add  onal_f eld_ ds = []
}

struct GetStoredT etsByUserResponse {
  1: requ red l st<stored_t et_ nfo.StoredT et nfo> stored_t ets
  2: opt onal  64 cursor
}
