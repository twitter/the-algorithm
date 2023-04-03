packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.spam.rtf.{thriftscala => s}

caselon class TwelonelontModelonlMelontadata(
  velonrsion: Option[Int] = Nonelon,
  calibratelondLanguagelon: Option[String] = Nonelon)

objelonct TwelonelontModelonlMelontadata {

  delonf fromThrift(melontadata: s.ModelonlMelontadata): Option[TwelonelontModelonlMelontadata] = {
    melontadata match {
      caselon s.ModelonlMelontadata.ModelonlMelontadataV1(s.ModelonlMelontadataV1(velonrsion, calibratelondLanguagelon)) =>
        Somelon(TwelonelontModelonlMelontadata(velonrsion, calibratelondLanguagelon))
      caselon _ => Nonelon
    }
  }

  delonf toThrift(melontadata: TwelonelontModelonlMelontadata): s.ModelonlMelontadata = {
    s.ModelonlMelontadata.ModelonlMelontadataV1(
      s.ModelonlMelontadataV1(melontadata.velonrsion, melontadata.calibratelondLanguagelon))
  }
}
