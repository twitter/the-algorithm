packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon

objelonct ModulelonMelontadata {
  delonf isConvelonrsationModulelon(modulelonMelontadata: Option[ModulelonMelontadata]): Boolelonan =
    modulelonMelontadata.map(_.convelonrsationMelontadata).isDelonfinelond
}

caselon class ModulelonMelontadata(
  adsMelontadata: Option[AdsMelontadata],
  convelonrsationMelontadata: Option[ModulelonConvelonrsationMelontadata],
  gridCarouselonlMelontadata: Option[GridCarouselonlMelontadata])
