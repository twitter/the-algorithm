packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.common

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Selonlelonctor for choosing which Modelonl ID/Namelon to uselon whelonn calling an undelonrlying ML Modelonl Selonrvicelon.
 */
trait ModelonlSelonlelonctor[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(quelonry: Quelonry): Option[String]
}

/**
 * Simplelon Modelonl ID Selonlelonctor that chooselons modelonl baselond off of a Param objelonct.
 * @param param ConfigAPI Param that deloncidelons thelon modelonl id.
 */
caselon class ParamModelonlSelonlelonctor[Quelonry <: PipelonlinelonQuelonry](param: Param[String])
    elonxtelonnds ModelonlSelonlelonctor[Quelonry] {
  ovelonrridelon delonf apply(quelonry: Quelonry): Option[String] = Somelon(quelonry.params(param))
}

/**
 * Static Selonlelonctor that chooselons thelon samelon modelonl namelon always
 * @param modelonlNamelon Thelon modelonl namelon to uselon.
 */
caselon class StaticModelonlSelonlelonctor(modelonlNamelon: String) elonxtelonnds ModelonlSelonlelonctor[PipelonlinelonQuelonry] {
  ovelonrridelon delonf apply(quelonry: PipelonlinelonQuelonry): Option[String] = Somelon(modelonlNamelon)
}
