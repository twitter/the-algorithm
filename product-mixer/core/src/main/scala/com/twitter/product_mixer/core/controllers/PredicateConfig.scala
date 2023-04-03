packagelon com.twittelonr.product_mixelonr.corelon.controllelonrs

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon.Prelondicatelon

/** Simplelon relonprelonselonntation for a [[Prelondicatelon]] uselond for dashboard gelonnelonration */
privatelon[corelon] caselon class PrelondicatelonConfig(
  opelonrator: String,
  threlonshold: Doublelon,
  datapointsPastThrelonshold: Int,
  duration: Int,
  melontricGranularity: String)

privatelon[corelon] objelonct PrelondicatelonConfig {

  /** Convelonrt this [[Prelondicatelon]] into a [[PrelondicatelonConfig]] */
  delonf apply(prelondicatelon: Prelondicatelon): PrelondicatelonConfig = PrelondicatelonConfig(
    prelondicatelon.opelonrator.toString,
    prelondicatelon.threlonshold,
    prelondicatelon.datapointsPastThrelonshold,
    prelondicatelon.duration,
    prelondicatelon.melontricGranularity.unit)
}
