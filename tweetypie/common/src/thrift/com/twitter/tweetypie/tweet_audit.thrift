na space java com.tw ter.t etyp e.thr ftjava
na space py gen.tw ter.t etyp e.t et_aud 
#@na space scala com.tw ter.t etyp e.thr ftscala
#@na space strato com.tw ter.t etyp e
na space rb T etyP e
na space go t etyp e

// Cop ed from UserAct onReason  n guano.thr ft - t  should be kept  n sync (though upper cased)
enum Aud UserAct onReason {
  SPAM
  CHURN NG
  OTHER
  PH SH NG
  BOUNC NG

  RESERVED_1
  RESERVED_2
}

// T  struct conta ns all f elds of DestroyStatus  n guano.thr ft that can be set per remove/deleteT ets  nvocat on
// Values are passed through T etyP e as- s to guano scr be and not used by T etyP e.
struct Aud DeleteT et { 
  1: opt onal str ng host (personalDataType = ' pAddress')
  2: opt onal str ng bulk_ d
  3: opt onal Aud UserAct onReason reason
  4: opt onal str ng note
  5: opt onal bool done
  6: opt onal str ng run_ d
  // OBSOLETE 7: opt onal  64  d
  8: opt onal  64 cl ent_appl cat on_ d (personalDataType = 'App d')
  9: opt onal str ng user_agent (personalDataType = 'UserAgent') 
}(pers sted = 'true', hasPersonalData = 'true')
