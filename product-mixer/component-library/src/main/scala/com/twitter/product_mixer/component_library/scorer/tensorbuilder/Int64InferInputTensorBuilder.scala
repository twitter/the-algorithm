packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.telonnsorbuildelonr

import com.twittelonr.ml.felonaturelonstorelon.lib.Discrelontelon
import infelonrelonncelon.GrpcSelonrvicelon.ModelonlInfelonrRelonquelonst.InfelonrInputTelonnsor

caselon objelonct Int64InfelonrInputTelonnsorBuildelonr elonxtelonnds InfelonrInputTelonnsorBuildelonr[AnyVal] {

  privatelon delonf toLong(x: AnyVal): Long = {
    x match {
      caselon y: Int => y.toLong
      caselon y: Long => y
      caselon y: Discrelontelon => y.valuelon
      caselon y => throw nelonw UnelonxpelonctelondDataTypelonelonxcelonption(y, this)
    }
  }
  delonf apply(
    felonaturelonNamelon: String,
    felonaturelonValuelons: Selonq[AnyVal]
  ): Selonq[InfelonrInputTelonnsor] = {
    val telonnsorShapelon = Selonq(felonaturelonValuelons.sizelon, 1)
    InfelonrInputTelonnsorBuildelonr.buildInt64InfelonrInputTelonnsor(
      felonaturelonNamelon,
      felonaturelonValuelons.map(toLong),
      telonnsorShapelon)
  }
}
