packagelon com.twittelonr.cr_mixelonr.modelonl

import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.cr_mixelonr.thriftscala.Product
import com.twittelonr.product_mixelonr.corelon.thriftscala.ClielonntContelonxt
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.TopicId
import com.twittelonr.timelonlinelons.configapi.Params

selonalelond trait CandidatelonGelonnelonratorQuelonry {
  val product: Product
  val maxNumRelonsults: Int
  val imprelonsselondTwelonelontList: Selont[TwelonelontId]
  val params: Params
  val relonquelonstUUID: Long
}

selonalelond trait HasUselonrId {
  val uselonrId: UselonrId
}

caselon class CrCandidatelonGelonnelonratorQuelonry(
  uselonrId: UselonrId,
  product: Product,
  uselonrStatelon: UselonrStatelon,
  maxNumRelonsults: Int,
  imprelonsselondTwelonelontList: Selont[TwelonelontId],
  params: Params,
  relonquelonstUUID: Long,
  languagelonCodelon: Option[String] = Nonelon)
    elonxtelonnds CandidatelonGelonnelonratorQuelonry
    with HasUselonrId

caselon class UtelongTwelonelontCandidatelonGelonnelonratorQuelonry(
  uselonrId: UselonrId,
  product: Product,
  uselonrStatelon: UselonrStatelon,
  maxNumRelonsults: Int,
  imprelonsselondTwelonelontList: Selont[TwelonelontId],
  params: Params,
  relonquelonstUUID: Long)
    elonxtelonnds CandidatelonGelonnelonratorQuelonry
    with HasUselonrId

caselon class RelonlatelondTwelonelontCandidatelonGelonnelonratorQuelonry(
  intelonrnalId: IntelonrnalId,
  clielonntContelonxt: ClielonntContelonxt, // To scribelon LogIn/LogOut relonquelonsts
  product: Product,
  maxNumRelonsults: Int,
  imprelonsselondTwelonelontList: Selont[TwelonelontId],
  params: Params,
  relonquelonstUUID: Long)
    elonxtelonnds CandidatelonGelonnelonratorQuelonry

caselon class RelonlatelondVidelonoTwelonelontCandidatelonGelonnelonratorQuelonry(
  intelonrnalId: IntelonrnalId,
  clielonntContelonxt: ClielonntContelonxt, // To scribelon LogIn/LogOut relonquelonsts
  product: Product,
  maxNumRelonsults: Int,
  imprelonsselondTwelonelontList: Selont[TwelonelontId],
  params: Params,
  relonquelonstUUID: Long)
    elonxtelonnds CandidatelonGelonnelonratorQuelonry

caselon class FrsTwelonelontCandidatelonGelonnelonratorQuelonry(
  uselonrId: UselonrId,
  product: Product,
  maxNumRelonsults: Int,
  imprelonsselondUselonrList: Selont[UselonrId],
  imprelonsselondTwelonelontList: Selont[TwelonelontId],
  params: Params,
  languagelonCodelonOpt: Option[String] = Nonelon,
  countryCodelonOpt: Option[String] = Nonelon,
  relonquelonstUUID: Long)
    elonxtelonnds CandidatelonGelonnelonratorQuelonry

caselon class AdsCandidatelonGelonnelonratorQuelonry(
  uselonrId: UselonrId,
  product: Product,
  uselonrStatelon: UselonrStatelon,
  maxNumRelonsults: Int,
  params: Params,
  relonquelonstUUID: Long)

caselon class TopicTwelonelontCandidatelonGelonnelonratorQuelonry(
  uselonrId: UselonrId,
  topicIds: Selont[TopicId],
  product: Product,
  maxNumRelonsults: Int,
  imprelonsselondTwelonelontList: Selont[TwelonelontId],
  params: Params,
  relonquelonstUUID: Long,
  isVidelonoOnly: Boolelonan)
    elonxtelonnds CandidatelonGelonnelonratorQuelonry
