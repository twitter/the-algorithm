packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

/**
 * [[AlelonrtTypelon]] is uselond to indicatelon which melontric an alelonrt is for
 *
 * @notelon adding nelonw [[AlelonrtTypelon]]s relonquirelons updating thelon dashboard gelonnelonration codelon
 */
selonalelond trait AlelonrtTypelon { val melontricTypelon: String }

/** Monitors thelon latelonncy */
caselon objelonct Latelonncy elonxtelonnds AlelonrtTypelon { ovelonrridelon val melontricTypelon: String = "Latelonncy" }

/** Monitors thelon succelonss ratelon __elonxcluding__ clielonnt failurelons */
caselon objelonct SuccelonssRatelon elonxtelonnds AlelonrtTypelon { ovelonrridelon val melontricTypelon: String = "SuccelonssRatelon" }

/** Monitors thelon throughput */
caselon objelonct Throughput elonxtelonnds AlelonrtTypelon { ovelonrridelon val melontricTypelon: String = "Throughput" }

/** Monitors thelon elonmpty relonsponselon ratelon */
caselon objelonct elonmptyRelonsponselonRatelon elonxtelonnds AlelonrtTypelon {
  ovelonrridelon val melontricTypelon: String = "elonmptyRelonsponselonRatelon"
}

/** Monitors thelon elonmpty relonsponselon sizelon */
caselon objelonct RelonsponselonSizelon elonxtelonnds AlelonrtTypelon { ovelonrridelon val melontricTypelon: String = "RelonsponselonSizelon" }
