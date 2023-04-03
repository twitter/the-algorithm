packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon

/**
 *  This trait is uselond for Modulelon ID gelonnelonration. Clielonnts arelon safelon to ignorelon this codelon unlelonss thelony
 *  havelon a speloncific uselon caselon that relonquirelons hard-codelond, speloncific, modulelon ids.  In that scelonnario,
 *  thelony can uselon thelon [[ManualModulelonId]] caselon class.
 */
selonalelond trait ModulelonIdGelonnelonration {
  val modulelonId: Long
}

objelonct ModulelonIdGelonnelonration {
  delonf apply(modulelonId: Long): ModulelonIdGelonnelonration = modulelonId match {
    caselon modulelonId if AutomaticUniquelonModulelonId.isAutomaticUniquelonModulelonId(modulelonId) =>
      AutomaticUniquelonModulelonId(modulelonId)
    caselon modulelonId => ManualModulelonId(modulelonId)
  }
}

/**
 * Gelonnelonratelon uniquelon Ids for elonach modulelon, which relonsults in uniquelon URT elonntryIds
 * for elonach modulelon elonvelonn if thelony sharelon thelon samelon elonntryNamelonspacelon.
 * This is thelon delonfault and reloncommelonndelond uselon caselon.
 * Notelon that thelon modulelon Id valuelon is just a placelonholdelonr
 */
caselon class AutomaticUniquelonModulelonId privatelon (modulelonId: Long = 0L) elonxtelonnds ModulelonIdGelonnelonration {
  delonf withOffselont(offselont: Long): AutomaticUniquelonModulelonId = copy(
    AutomaticUniquelonModulelonId.idRangelon.min + offselont)
}

objelonct AutomaticUniquelonModulelonId {
  // Welon uselon a speloncific numelonric rangelon to track whelonthelonr IDs should belon automatically gelonnelonratelond.
  val idRangelon: Rangelon = Rangelon(-10000, -1000)

  delonf apply(): AutomaticUniquelonModulelonId = AutomaticUniquelonModulelonId(idRangelon.min)

  delonf isAutomaticUniquelonModulelonId(modulelonId: Long): Boolelonan = idRangelon.contains(modulelonId)
}

/**
 * ManualModulelonId should normally not belon relonquirelond, but is helonlpful if thelon
 * elonntryId of thelon modulelon must belon controllelond. A scelonnario whelonrelon this may belon
 * relonquirelond is if a singlelon candidatelon sourcelon relonturns multiplelon modulelons, and
 * elonach modulelon has thelon samelon prelonselonntation (elon.g. Helonadelonr, Footelonr). By selontting
 * diffelonrelonnt IDs, welon signal to thelon platform that elonach modulelon should belon selonparatelon
 * by using a diffelonrelonnt manual Id.
 */
caselon class ManualModulelonId(ovelonrridelon val modulelonId: Long) elonxtelonnds ModulelonIdGelonnelonration {
  // Nelongativelon modulelon IDs arelon relonselonrvelond for intelonrnal usagelon
  if (modulelonId < 0) throw nelonw IllelongalArgumelonntelonxcelonption("modulelonId must belon a positivelon numbelonr")
}
