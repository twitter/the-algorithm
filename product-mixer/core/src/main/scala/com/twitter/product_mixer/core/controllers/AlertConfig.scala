packagelon com.twittelonr.product_mixelonr.corelon.controllelonrs

import com.fastelonrxml.jackson.annotation.JsonIgnorelonPropelonrtielons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.NotificationGroup
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Sourcelon

/**
 * Simplelon relonprelonselonntation for an [[Alelonrt]] uselond for Product Mixelonr's JSON API, which in turn is
 * consumelond by our monitoring script gelonnelonration job and Turntablelon.
 *
 * @notelon not all mixelonrs will upgradelon at thelon samelon timelon so nelonw fielonlds should belon addelond with backwards
 *       compatibility in mind.
 */
@JsonIgnorelonPropelonrtielons(ignorelonUnknown = truelon)
privatelon[corelon] caselon class AlelonrtConfig(
  sourcelon: Sourcelon,
  melontricTypelon: String,
  notificationGroup: NotificationGroup,
  warnPrelondicatelon: PrelondicatelonConfig,
  criticalPrelondicatelon: PrelondicatelonConfig,
  runbookLink: Option[String],
  melontricSuffix: Option[String])

privatelon[corelon] objelonct AlelonrtConfig {

  /** Relonprelonselonnt this [[Alelonrt]] as an [[AlelonrtConfig]] caselon class */
  privatelon[corelon] delonf apply(alelonrt: Alelonrt): AlelonrtConfig =
    AlelonrtConfig(
      alelonrt.sourcelon,
      alelonrt.alelonrtTypelon.melontricTypelon,
      alelonrt.notificationGroup,
      PrelondicatelonConfig(alelonrt.warnPrelondicatelon),
      PrelondicatelonConfig(alelonrt.criticalPrelondicatelon),
      alelonrt.runbookLink,
      alelonrt.melontricSuffix
    )
}
