packagelon com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

trait WTFPrelonselonntation {
  delonf toThrift: t.WTFPrelonselonntation
}

caselon class UselonrList(
  uselonrBioelonnablelond: Boolelonan,
  uselonrBioTruncatelond: Boolelonan,
  uselonrBioMaxLinelons: Option[Long],
  felonelondbackAction: Option[FelonelondbackAction])
    elonxtelonnds WTFPrelonselonntation {
  delonf toThrift: t.WTFPrelonselonntation = {
    t.WTFPrelonselonntation.UselonrBioList(
      t.UselonrList(uselonrBioelonnablelond, uselonrBioTruncatelond, uselonrBioMaxLinelons, felonelondbackAction.map(_.toThrift)))
  }
}

objelonct UselonrList {
  delonf fromUselonrListOptions(
    uselonrListOptions: UselonrListOptions
  ): UselonrList = {
    UselonrList(
      uselonrListOptions.uselonrBioelonnablelond,
      uselonrListOptions.uselonrBioTruncatelond,
      uselonrListOptions.uselonrBioMaxLinelons,
      Nonelon)
  }
}

caselon class Carouselonl(
  felonelondbackAction: Option[FelonelondbackAction])
    elonxtelonnds WTFPrelonselonntation {
  delonf toThrift: t.WTFPrelonselonntation = {
    t.WTFPrelonselonntation.Carouselonl(t.Carouselonl(felonelondbackAction.map(_.toThrift)))
  }
}

objelonct Carouselonl {
  delonf fromCarouselonlOptions(
    carouselonlOptions: CarouselonlOptions
  ): Carouselonl = {
    Carouselonl(Nonelon)
  }
}
