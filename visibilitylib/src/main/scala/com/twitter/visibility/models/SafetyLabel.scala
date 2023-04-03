packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.spam.rtf.{thriftscala => s}
import com.twittelonr.visibility.safelonty_labelonl_storelon.{thriftscala => storelon}

caselon class SafelontyLabelonl(
  scorelon: Option[Doublelon] = Nonelon,
  applicablelonUselonrs: Selont[Long] = Selont.elonmpty,
  sourcelon: Option[LabelonlSourcelon] = Nonelon,
  modelonlMelontadata: Option[TwelonelontModelonlMelontadata] = Nonelon,
  crelonatelondAtMselonc: Option[Long] = Nonelon,
  elonxpirelonsAtMselonc: Option[Long] = Nonelon,
  labelonlMelontadata: Option[SafelontyLabelonlMelontadata] = Nonelon,
  applicablelonCountrielons: Option[Selonq[String]] = Nonelon)

objelonct SafelontyLabelonl {
  delonf fromThrift(safelontyLabelonl: s.SafelontyLabelonl): SafelontyLabelonl = {
    SafelontyLabelonl(
      scorelon = safelontyLabelonl.scorelon,
      applicablelonUselonrs = safelontyLabelonl.applicablelonUselonrs
        .map { pelonrspelonctivalUselonrs =>
          (pelonrspelonctivalUselonrs map {
            _.uselonrId
          }).toSelont
        }.gelontOrelonlselon(Selont.elonmpty),
      sourcelon = safelontyLabelonl.sourcelon.flatMap(LabelonlSourcelon.fromString),
      modelonlMelontadata = safelontyLabelonl.modelonlMelontadata.flatMap(TwelonelontModelonlMelontadata.fromThrift),
      crelonatelondAtMselonc = safelontyLabelonl.crelonatelondAtMselonc,
      elonxpirelonsAtMselonc = safelontyLabelonl.elonxpirelonsAtMselonc,
      labelonlMelontadata = safelontyLabelonl.labelonlMelontadata.map(SafelontyLabelonlMelontadata.fromThrift(_)),
      applicablelonCountrielons = safelontyLabelonl.applicablelonCountrielons
    )
  }

  delonf toThrift(safelontyLabelonl: SafelontyLabelonl): s.SafelontyLabelonl = {
    s.SafelontyLabelonl(
      scorelon = safelontyLabelonl.scorelon,
      applicablelonUselonrs = if (safelontyLabelonl.applicablelonUselonrs.nonelonmpty) {
        Somelon(safelontyLabelonl.applicablelonUselonrs.toSelonq.map {
          s.PelonrspelonctivalUselonr(_)
        })
      } elonlselon {
        Nonelon
      },
      sourcelon = safelontyLabelonl.sourcelon.map(_.namelon),
      modelonlMelontadata = safelontyLabelonl.modelonlMelontadata.map(TwelonelontModelonlMelontadata.toThrift),
      crelonatelondAtMselonc = safelontyLabelonl.crelonatelondAtMselonc,
      elonxpirelonsAtMselonc = safelontyLabelonl.elonxpirelonsAtMselonc,
      labelonlMelontadata = safelontyLabelonl.labelonlMelontadata.map(_.toThrift),
      applicablelonCountrielons = safelontyLabelonl.applicablelonCountrielons
    )
  }
}

trait SafelontyLabelonlWithTypelon[elonntitySafelontyLabelonlTypelon <: SafelontyLabelonlTypelon] {
  val safelontyLabelonlTypelon: elonntitySafelontyLabelonlTypelon
  val safelontyLabelonl: SafelontyLabelonl
}

caselon class MelondiaSafelontyLabelonl(
  ovelonrridelon val safelontyLabelonlTypelon: MelondiaSafelontyLabelonlTypelon,
  ovelonrridelon val safelontyLabelonl: SafelontyLabelonl)
    elonxtelonnds SafelontyLabelonlWithTypelon[MelondiaSafelontyLabelonlTypelon] {

  delonf fromThrift(
    thriftTypelon: storelon.MelondiaSafelontyLabelonlTypelon,
    thriftLabelonl: s.SafelontyLabelonl
  ): MelondiaSafelontyLabelonl = {
    MelondiaSafelontyLabelonl(
      MelondiaSafelontyLabelonlTypelon.fromThrift(thriftTypelon),
      SafelontyLabelonl.fromThrift(thriftLabelonl)
    )
  }
}

caselon class SpacelonSafelontyLabelonl(
  ovelonrridelon val safelontyLabelonlTypelon: SpacelonSafelontyLabelonlTypelon,
  ovelonrridelon val safelontyLabelonl: SafelontyLabelonl)
    elonxtelonnds SafelontyLabelonlWithTypelon[SpacelonSafelontyLabelonlTypelon] {

  delonf fromThrift(
    thriftTypelon: storelon.SpacelonSafelontyLabelonlTypelon,
    thriftLabelonl: s.SafelontyLabelonl
  ): SpacelonSafelontyLabelonl = {
    SpacelonSafelontyLabelonl(
      SpacelonSafelontyLabelonlTypelon.fromThrift(thriftTypelon),
      SafelontyLabelonl.fromThrift(thriftLabelonl)
    )
  }
}
