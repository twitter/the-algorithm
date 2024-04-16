na space java com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons.enr c r. nternal

/**
* An enr ch nt plan.   has mult ple stages for d fferent purposes dur ng t  enr ch nt process.
**/
struct Enr ch ntPlan {
  1: requ red l st<Enr ch ntStage> stages
}(pers sted='true', hasPersonalData='false')

/**
* A stage  n t  enr ch nt process w h respect to t  current key. Currently   can be of 2 opt ons:
* - re-part  on ng on an  d of type X
* - hydrat ng  tadata on an  d of type X
*
* A stage also moves through d fferent statues from  n  al zed, process ng unt l complet on.
* Each stage conta ns one or more  nstruct ons.
**/
struct Enr ch ntStage {
  1: requ red Enr ch ntStageStatus status
  2: requ red Enr ch ntStageType stageType
  3: requ red l st<Enr ch nt nstruct on>  nstruct ons

  // T  output top c for t  stage. T   nformat on  s not ava lable w n t  stage was
  // f rst setup, and  's only ava lable after t  dr ver has f n s d work ng on
  // t  stage.
  4: opt onal str ng outputTop c
}(pers sted='true', hasPersonalData='false')

/**
* T  current process ng status of a stage.   should e  r be done (complet on) or not done ( n  al zed).
* Trans ent statuses such as "process ng"  s dangerous s nce   can't exactly be sure that has been done.
**/
enum Enr ch ntStageStatus {
   n  al zed = 0
  Complet on = 20
}

/**
* T  type of process ng  n t  stage. For example, repart on ng t  data or hydrat ng t  data.
**/
enum Enr ch ntStageType {
  Repart  on = 0
  Hydrat on = 10
}

enum Enr ch nt nstruct on {
  // all enr ch nt based on a t et  d  n UUA goes  re
  T etEnr ch nt = 0
  Not f cat onT etEnr ch nt = 10
}
