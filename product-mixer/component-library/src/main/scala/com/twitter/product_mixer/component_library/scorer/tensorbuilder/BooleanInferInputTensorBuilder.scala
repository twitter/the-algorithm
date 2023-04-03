packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.telonnsorbuildelonr

import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonquelonst.InfelonrInputTelonnsor

caselon objelonct BoolelonanInfelonrInputTelonnsorBuildelonr elonxtelonnds InfelonrInputTelonnsorBuildelonr[Boolelonan] {
  delonf apply(
    felonaturelonNamelon: String,
    felonaturelonValuelons: Selonq[Boolelonan]
  ): Selonq[InfelonrInputTelonnsor] = {
    val telonnsorShapelon = Selonq(felonaturelonValuelons.sizelon, 1)
    InfelonrInputTelonnsorBuildelonr.buildBoolInfelonrInputTelonnsor(felonaturelonNamelon, felonaturelonValuelons, telonnsorShapelon)
  }
}
