packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.urt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.ModulelonItelonmTrelonelonDisplay

/*
 * Trelonelon statelon delonclaring itelonm’s parelonnt relonlationship with any othelonr itelonms in
 * thelon modulelon, any display indelonntation information, and/or collapselond display statelon.
 */
trait WithItelonmTrelonelonDisplay { selonlf: BaselonUrtItelonmPrelonselonntation =>
  delonf trelonelonDisplay: Option[ModulelonItelonmTrelonelonDisplay]
}
