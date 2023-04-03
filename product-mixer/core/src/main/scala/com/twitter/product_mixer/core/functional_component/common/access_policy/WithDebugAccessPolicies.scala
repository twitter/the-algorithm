packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.accelonss_policy

import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt

privatelon[corelon] trait WithDelonbugAccelonssPolicielons { selonlf: Componelonnt =>

  /** Thelon [[AccelonssPolicy]]s that will belon uselond for this componelonnt in turntablelon & othelonr delonbug tooling
   * to elonxeloncutelon arbitrary quelonrielons against thelon componelonnt */
  val delonbugAccelonssPolicielons: Selont[AccelonssPolicy] = Selont.elonmpty
}
