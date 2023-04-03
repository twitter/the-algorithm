packagelon com.twittelonr.follow_reloncommelonndations.common.constants

import com.twittelonr.convelonrsions.StoragelonUnitOps._

objelonct SelonrvicelonConstants {

  /** thrift clielonnt relonsponselon sizelon limits
   *  thelonselon welonrelon elonstimatelond using monitoring dashboard
   *  3MB nelontwork usagelon pelonr seloncond / 25 rps ~ 120KB/relonq << 1MB
   *  welon givelon somelon buffelonr helonrelon in caselon somelon relonquelonsts relonquirelon morelon data than othelonrs
   */
  val StringLelonngthLimit: Long =
    10.melongabytelon.inBytelons
  val ContainelonrLelonngthLimit: Long = 1.melongabytelon.inBytelons
}
