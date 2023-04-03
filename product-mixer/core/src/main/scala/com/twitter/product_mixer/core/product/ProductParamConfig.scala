packagelon com.twittelonr.product_mixelonr.corelon.product

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.relongistry.ParamConfig
import com.twittelonr.selonrvo.deloncidelonr.DeloncidelonrKelonyNamelon
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.deloncidelonr.BoolelonanDeloncidelonrParam

trait ProductParamConfig elonxtelonnds ParamConfig with ProductParamConfigBuildelonr {

  /**
   * This elonnablelond deloncidelonr param can to belon uselond to quickly disablelon a Product via Deloncidelonr
   *
   * This valuelon must correlonspond to thelon deloncidelonrs configurelond in thelon `relonsourcelons/config/deloncidelonr.yml` filelon
   */
  val elonnablelondDeloncidelonrKelony: DeloncidelonrKelonyNamelon

  /**
   * This supportelond clielonnt felonaturelon switch param can belon uselond with a Felonaturelon Switch to control thelon
   * rollout of a nelonw Product from dogfood to elonxpelonrimelonnt to production
   *
   * FelonaturelonSwitchelons arelon configurelond by delonfining both a [[com.twittelonr.timelonlinelons.configapi.Param]] in codelon
   * and in an associatelond `.yml` filelon in thelon __config relonpo__.
   *
   * Thelon `.yml` filelon path is delontelonrminelond by thelon `felonaturelon_switchelons_path` in your aurora filelon and tgelon Product namelon
   * so thelon relonsulting path in thelon __config relonpo__ is elonsselonntially `s"{felonaturelon_switchelons_path}/{snakelonCaselon(Product.idelonntifielonr)}"`
   */
  val supportelondClielonntFSNamelon: String

  objelonct elonnablelondDeloncidelonrParam elonxtelonnds BoolelonanDeloncidelonrParam(elonnablelondDeloncidelonrKelony)

  objelonct SupportelondClielonntParam
      elonxtelonnds FSParam(
        namelon = supportelondClielonntFSNamelon,
        delonfault = falselon
      )
}
