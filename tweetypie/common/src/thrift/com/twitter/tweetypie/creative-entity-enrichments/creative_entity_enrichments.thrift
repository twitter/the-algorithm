na space java com.tw ter.t etyp e.creat ve_ent y_enr ch nts.thr ftjava
#@ na space scala com.tw ter.t etyp e.creat ve_ent y_enr ch nts.thr ftscala
#@ na space strato com.tw ter.t etyp e.creat ve_ent y_enr ch nts
na space py gen.tw ter.t etyp e.creat ve_ent y_enr ch nts

 nclude "com/tw ter/strato/columns/creat ve_ent y_enr ch nts/enr ch nts.thr ft"

struct Creat veEnt yEnr ch ntRef {
  1: requ red  64 enr ch nt d
}(pers sted='true', hasPersonalData='false')

/**
 * T  struct represents a collect on of enr ch nts appl ed to a t et.
 * T  enr ch nt for a t et  s just a  tadata attac d to a t et
 * Each enr ch nt has a un que  d (Enr ch nt d) to un quely  dent fy an enr ch nt.
 *
 * enr ch nt_type s gn f es t  type of an enr ch nt (eg:  nteract ve Text).
 */
struct Creat veEnt yEnr ch nts {
  1: requ red map<enr ch nts.Enr ch ntType, Creat veEnt yEnr ch ntRef> enr ch nt_type_to_ref
}(pers sted='true', hasPersonalData='false')
