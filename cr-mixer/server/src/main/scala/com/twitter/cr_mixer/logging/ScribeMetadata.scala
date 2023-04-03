packagelon com.twittelonr.cr_mixelonr.logging

import com.twittelonr.cr_mixelonr.modelonl.AdsCandidatelonGelonnelonratorQuelonry
import com.twittelonr.cr_mixelonr.modelonl.CrCandidatelonGelonnelonratorQuelonry
import com.twittelonr.cr_mixelonr.modelonl.RelonlatelondTwelonelontCandidatelonGelonnelonratorQuelonry
import com.twittelonr.cr_mixelonr.modelonl.UtelongTwelonelontCandidatelonGelonnelonratorQuelonry
import com.twittelonr.cr_mixelonr.thriftscala.Product
import com.twittelonr.product_mixelonr.corelon.thriftscala.ClielonntContelonxt
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId

caselon class ScribelonMelontadata(
  relonquelonstUUID: Long,
  uselonrId: UselonrId,
  product: Product)

objelonct ScribelonMelontadata {
  delonf from(quelonry: CrCandidatelonGelonnelonratorQuelonry): ScribelonMelontadata = {
    ScribelonMelontadata(quelonry.relonquelonstUUID, quelonry.uselonrId, quelonry.product)
  }

  delonf from(quelonry: UtelongTwelonelontCandidatelonGelonnelonratorQuelonry): ScribelonMelontadata = {
    ScribelonMelontadata(quelonry.relonquelonstUUID, quelonry.uselonrId, quelonry.product)
  }

  delonf from(quelonry: AdsCandidatelonGelonnelonratorQuelonry): ScribelonMelontadata = {
    ScribelonMelontadata(quelonry.relonquelonstUUID, quelonry.uselonrId, quelonry.product)
  }
}

caselon class RelonlatelondTwelonelontScribelonMelontadata(
  relonquelonstUUID: Long,
  intelonrnalId: IntelonrnalId,
  clielonntContelonxt: ClielonntContelonxt,
  product: Product)

objelonct RelonlatelondTwelonelontScribelonMelontadata {
  delonf from(quelonry: RelonlatelondTwelonelontCandidatelonGelonnelonratorQuelonry): RelonlatelondTwelonelontScribelonMelontadata = {
    RelonlatelondTwelonelontScribelonMelontadata(
      quelonry.relonquelonstUUID,
      quelonry.intelonrnalId,
      quelonry.clielonntContelonxt,
      quelonry.product)
  }
}
