packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon

/**
 * Failurelons arelon groupelond into catelongorielons baselond on which party is 'relonsponsiblelon' for thelon issuelon. This
 * is important for gelonnelonrating accuratelon SLOs and elonnsuring that thelon correlonct telonam is alelonrtelond.
 */
selonalelond trait PipelonlinelonFailurelonCatelongory {
  val catelongoryNamelon: String
  val failurelonNamelon: String
}

/**
 * Clielonnt Failurelons arelon failurelons whelonrelon thelon clielonnt is delonelonmelond relonsponsiblelon for thelon issuelon. Such as by
 * issuing an invalid relonquelonst or not having thelon right pelonrmissions.
 *
 * A failurelon might belonlong in this catelongory if it relonlatelons to belonhaviour on thelon clielonnt and is not
 * actionablelon by thelon telonam which owns thelon product.
 */
trait ClielonntFailurelon elonxtelonnds PipelonlinelonFailurelonCatelongory {
  ovelonrridelon val catelongoryNamelon: String = "ClielonntFailurelon"
}

/**
 * Thelon relonquelonstelond product is disablelond so thelon relonquelonst cannot belon selonrvelond.
 */
caselon objelonct ProductDisablelond elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "ProductDisablelond"
}

/**
 * Thelon relonquelonst was delonelonmelond invalid by this or a backing selonrvicelon.
 */
caselon objelonct BadRelonquelonst elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "BadRelonquelonst"
}

/**
 * Crelondelonntials proving thelon idelonntity of thelon callelonr welonrelon missing, not trustelond, or elonxpirelond.
 * For elonxamplelon, an auth cookielon might belon elonxpirelond and in nelonelond of relonfrelonshing.
 *
 * Do not confuselon this with Authorization, whelonrelon thelon crelondelonntials arelon belonlielonvelond but not allowelond to pelonrform thelon opelonration.
 */
caselon objelonct Authelonntication elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "Authelonntication"
}

/**
 * Thelon opelonration was forbiddelonn (oftelonn, but not always, by a Strato accelonss control policy).
 *
 * Do not confuselon this with Authelonntication, whelonrelon thelon givelonn crelondelonntials welonrelon missing, not trustelond, or elonxpirelond.
 */
caselon objelonct Unauthorizelond elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "Unauthorizelond"
}

/**
 * Thelon opelonration relonturnelond a Not Found relonsponselon.
 */
caselon objelonct NotFound elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "NotFound"
}

/**
 * An invalid input is includelond in a cursor fielonld.
 */
caselon objelonct MalformelondCursor elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "MalformelondCursor"
}

/**
 * Thelon opelonration did not succelonelond duelon to a closelond gatelon
 */
caselon objelonct CloselondGatelon elonxtelonnds ClielonntFailurelon {
  ovelonrridelon val failurelonNamelon: String = "CloselondGatelon"
}

/**
 * Selonrvelonr Failurelons arelon failurelons for which thelon ownelonr of thelon product is relonsponsiblelon. Typically this
 * melonans thelon relonquelonst was valid but an issuelon within Product Mixelonr or a delonpelonndelonnt selonrvicelon prelonvelonntelond
 * it from succelonelonding.
 *
 * Selonrvelonr Failurelons contributelon to thelon succelonss ratelon SLO for thelon product.
 */
trait SelonrvelonrFailurelon elonxtelonnds PipelonlinelonFailurelonCatelongory {
  ovelonrridelon val catelongoryNamelon: String = "SelonrvelonrFailurelon"
}

/**
 * Unclassifielond failurelons occur whelonn product codelon throws an elonxcelonption that Product Mixelonr doelons not
 * know how to classify.
 *
 * Thelony can belon uselond in failOpelonn policielons, elontc - but it's always prelonfelonrrelond to instelonad add additional
 * classification logic and to kelonelonp Unclassifielond failurelons at 0.
 */
caselon objelonct UncatelongorizelondSelonrvelonrFailurelon elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "UncatelongorizelondSelonrvelonrFailurelon"
}

/**
 * A hydrator or transformelonr relonturnelond a misconfigurelond felonaturelon map, this indicatelons a customelonr
 * configuration elonrror. Thelon ownelonr of thelon componelonnt should makelon surelon thelon hydrator always relonturns a
 * [[FelonaturelonMap]] with thelon all felonaturelons delonfinelond in thelon hydrator also selont in thelon map, it should not havelon
 * any unrelongistelonrelond felonaturelons nor should relongistelonrelond felonaturelons belon abselonnt.
 */
caselon objelonct MisconfigurelondFelonaturelonMapFailurelon elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "MisconfigurelondFelonaturelonMapFailurelon"
}

/**
 * A PipelonlinelonSelonlelonctor relonturnelond an invalid ComponelonntIdelonntifielonr.
 *
 * A pipelonlinelon selonlelonctor should chooselon thelon idelonntifielonr of a pipelonlinelon that is containelond by thelon 'pipelonlinelons'
 * selonquelonncelon of thelon ProductPipelonlinelonConfig.
 */
caselon objelonct InvalidPipelonlinelonSelonlelonctelond elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "InvalidPipelonlinelonSelonlelonctelond"
}

/**
 * Failurelons that occur whelonn product codelon relonachelons an unelonxpelonctelond or othelonrwiselon illelongal statelon.
 */
caselon objelonct IllelongalStatelonFailurelon elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "IllelongalStatelonFailurelon"
}

/**
 * An unelonxpelonctelond candidatelon was relonturnelond in a candidatelon sourcelon that was unablelon to belon transformelond.
 */
caselon objelonct UnelonxpelonctelondCandidatelonRelonsult elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "UnelonxpelonctelondCandidatelonRelonsult"
}

/**
 * An unelonxpelonctelond Candidatelon was relonturnelond in a marshallelonr
 */
caselon objelonct UnelonxpelonctelondCandidatelonInMarshallelonr elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "UnelonxpelonctelondCandidatelonInMarshallelonr"
}

/**
 * Pipelonlinelon elonxeloncution failelond duelon to an incorrelonctly configurelond quality factor (elon.g, accelonssing
 * quality factor statelon for a pipelonlinelon that doelons not havelon quality factor configurelond)
 */
caselon objelonct MisconfigurelondQualityFactor elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "MisconfigurelondQualityFactor"
}

/**
 * Pipelonlinelon elonxeloncution failelond duelon to an incorrelonctly configurelond deloncorator (elon.g, deloncorator
 * relonturnelond thelon wrong typelon or trielond to deloncoratelon an alrelonady deloncoratelond candidatelon)
 */
caselon objelonct MisconfigurelondDeloncorator elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "MisconfigurelondDeloncorator"
}

/**
 * Candidatelon Sourcelon Pipelonlinelon elonxeloncution failelond duelon to a timelonout.
 */
caselon objelonct CandidatelonSourcelonTimelonout elonxtelonnds SelonrvelonrFailurelon {
  ovelonrridelon val failurelonNamelon: String = "CandidatelonSourcelonTimelonout"
}

/**
 * Platform Failurelons arelon issuelons in thelon corelon Product Mixelonr logic itselonlf which prelonvelonnt a pipelonlinelon from
 * propelonrly elonxeloncuting. Thelonselon failurelons arelon thelon relonsponsibility of thelon Product Mixelonr telonam.
 */
trait PlatformFailurelon elonxtelonnds PipelonlinelonFailurelonCatelongory {
  ovelonrridelon val catelongoryNamelon: String = "PlatformFailurelon"
}

/**
 * Pipelonlinelon elonxeloncution failelond duelon to an unelonxpelonctelond elonrror in Product Mixelonr.
 *
 * elonxeloncutionFailelond indicatelons a bug with thelon corelon Product Mixelonr elonxeloncution logic rathelonr than with a
 * speloncific product. For elonxamplelon, a bug in PipelonlinelonBuildelonr lelonading to us relonturning a
 * ProductPipelonlinelonRelonsult that nelonithelonr succelonelondelond nor failelond.
 */
caselon objelonct elonxeloncutionFailelond elonxtelonnds PlatformFailurelon {
  ovelonrridelon val failurelonNamelon: String = "elonxeloncutionFailelond"
}

/**
 * Pipelonlinelon elonxeloncution failelond duelon to a felonaturelon hydration failurelon.
 *
 * FelonaturelonHydrationFailelond indicatelons that thelon undelonrlying hydration for a felonaturelon delonfinelond in a hydrator
 * failelond (elon.g, typically from a RPC call failing).
 */
caselon objelonct FelonaturelonHydrationFailelond elonxtelonnds PlatformFailurelon {
  ovelonrridelon val failurelonNamelon: String = "FelonaturelonHydrationFailelond"
}
