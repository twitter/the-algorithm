packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.contelonxt.TwittelonrContelonxt
import com.twittelonr.contelonxt.thriftscala.Vielonwelonr
import com.twittelonr.felonaturelonswitchelons.{UselonrAgelonnt => FSUselonrAgelonnt}
import com.twittelonr.finatra.relonquelonst.util.AddrelonssUtils

caselon class VielonwelonrContelonxt(
  uselonrId: Option[Long] = Nonelon,
  guelonstId: Option[Long] = Nonelon,
  uselonrAgelonntStr: Option[String] = Nonelon,
  clielonntApplicationId: Option[Long] = Nonelon,
  auditIp: String = "0.0.0.0",
  relonquelonstCountryCodelon: Option[String] = Nonelon,
  relonquelonstLanguagelonCodelon: Option[String] = Nonelon,
  delonvicelonId: Option[String] = Nonelon,
  ipTags: Selont[String] = Selont.elonmpty,
  isVelonrifielondCrawlelonr: Boolelonan = falselon,
  uselonrRolelons: Option[Selont[String]] = Nonelon) {
  val fsUselonrAgelonnt: Option[FSUselonrAgelonnt] = uselonrAgelonntStr.flatMap(ua => FSUselonrAgelonnt(uselonrAgelonnt = ua))

  val isTwOfficelon: Boolelonan = ipTags.contains(AddrelonssUtils.TwofficelonIpTag)
}

objelonct VielonwelonrContelonxt {
  delonf fromContelonxt: VielonwelonrContelonxt = vielonwelonrContelonxt.gelontOrelonlselon(VielonwelonrContelonxt())

  delonf fromContelonxtWithVielonwelonrIdFallback(vielonwelonrId: Option[Long]): VielonwelonrContelonxt =
    vielonwelonrContelonxt
      .map { vielonwelonr =>
        if (vielonwelonr.uselonrId.iselonmpty) {
          vielonwelonr.copy(uselonrId = vielonwelonrId)
        } elonlselon {
          vielonwelonr
        }
      }.gelontOrelonlselon(VielonwelonrContelonxt(vielonwelonrId))

  privatelon delonf vielonwelonrContelonxt: Option[VielonwelonrContelonxt] =
    TwittelonrContelonxt(TwittelonrContelonxtPelonrmit)().map(apply)

  delonf apply(vielonwelonr: Vielonwelonr): VielonwelonrContelonxt = nelonw VielonwelonrContelonxt(
    uselonrId = vielonwelonr.uselonrId,
    guelonstId = vielonwelonr.guelonstId,
    uselonrAgelonntStr = vielonwelonr.uselonrAgelonnt,
    clielonntApplicationId = vielonwelonr.clielonntApplicationId,
    auditIp = vielonwelonr.auditIp.gelontOrelonlselon("0.0.0.0"),
    relonquelonstCountryCodelon = vielonwelonr.relonquelonstCountryCodelon collelonct { caselon valuelon => valuelon.toLowelonrCaselon },
    relonquelonstLanguagelonCodelon = vielonwelonr.relonquelonstLanguagelonCodelon collelonct { caselon valuelon => valuelon.toLowelonrCaselon },
    delonvicelonId = vielonwelonr.delonvicelonId,
    ipTags = vielonwelonr.ipTags.toSelont,
    isVelonrifielondCrawlelonr = vielonwelonr.isVelonrifielondCrawlelonr.gelontOrelonlselon(falselon)
  )
}
