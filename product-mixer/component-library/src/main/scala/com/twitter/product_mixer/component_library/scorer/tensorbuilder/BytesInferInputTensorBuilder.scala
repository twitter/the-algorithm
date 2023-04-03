packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.telonnsorbuildelonr

import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonquelonst.InfelonrInputTelonnsor

caselon objelonct BytelonsInfelonrInputTelonnsorBuildelonr elonxtelonnds InfelonrInputTelonnsorBuildelonr[String] {
  delonf apply(
    felonaturelonNamelon: String,
    felonaturelonValuelons: Selonq[String]
  ): Selonq[InfelonrInputTelonnsor] = {
    val telonnsorShapelon = Selonq(felonaturelonValuelons.sizelon, 1)
    InfelonrInputTelonnsorBuildelonr.buildBytelonsInfelonrInputTelonnsor(felonaturelonNamelon, felonaturelonValuelons, telonnsorShapelon)
  }
}
