na space java com.tw ter.t etyp e.thr ftjava
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e

 nclude "com/tw ter/t etyp e/t et.thr ft"

struct HardDeleted {
  1:  64 soft_deleted_t  stamp_msec
  2:  64 t  stamp_msec
}

struct SoftDeleted {
  1:  64 t  stamp_msec
}

struct BounceDeleted {
  1:  64 t  stamp_msec
}

struct Undeleted {
  1:  64 t  stamp_msec
}

struct ForceAdded {
  1:  64 t  stamp_msec
}

struct NotFound {}

un on StoredT etState {
  1: HardDeleted hard_deleted
  2: SoftDeleted soft_deleted
  3: BounceDeleted bounce_deleted
  4: Undeleted undeleted
  5: ForceAdded force_added
  6: NotFound not_found
}

enum StoredT etError {
  CORRUPT                     = 1,
  SCRUBBED_F ELDS_PRESENT     = 2,
  F ELDS_M SS NG_OR_ NVAL D   = 3,
  SHOULD_BE_HARD_DELETED      = 4,
  FA LED_FETCH                = 5
}

struct StoredT et nfo {
  1: requ red  64 t et_ d
  2: opt onal t et.T et t et
  3: opt onal StoredT etState stored_t et_state
  4: requ red l st<StoredT etError> errors = []
}
