packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.who_to_follow_modulelon

import com.twittelonr.account_reloncommelonndations_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonquelonst.ClielonntContelonxtMarshallelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonQuelonryTransformelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.BadRelonquelonst
import com.twittelonr.timelonlinelons.configapi.Param

objelonct WhoToFollowArmCandidatelonPipelonlinelonQuelonryTransformelonr {
  val HomelonDisplayLocation = "timelonlinelon"
  val HomelonRelonvelonrselonChronDisplayLocation = "timelonlinelon_relonvelonrselon_chron"
  val ProfilelonDisplayLocation = "profilelon_timelonlinelon"
}

caselon class WhoToFollowArmCandidatelonPipelonlinelonQuelonryTransformelonr[-Quelonry <: PipelonlinelonQuelonry](
  displayLocationParam: Param[String],
  elonxcludelondUselonrIdsFelonaturelon: Option[Felonaturelon[PipelonlinelonQuelonry, Selonq[Long]]],
  profilelonUselonrIdFelonaturelon: Option[Felonaturelon[PipelonlinelonQuelonry, Long]])
    elonxtelonnds CandidatelonPipelonlinelonQuelonryTransformelonr[Quelonry, t.AccountReloncommelonndationsMixelonrRelonquelonst] {

  ovelonrridelon delonf transform(input: Quelonry): t.AccountReloncommelonndationsMixelonrRelonquelonst = {
    input.params(displayLocationParam) match {
      caselon WhoToFollowArmCandidatelonPipelonlinelonQuelonryTransformelonr.HomelonRelonvelonrselonChronDisplayLocation =>
        t.AccountReloncommelonndationsMixelonrRelonquelonst(
          clielonntContelonxt = ClielonntContelonxtMarshallelonr(input.clielonntContelonxt),
          product = t.Product.HomelonRelonvelonrselonChronWhoToFollow,
          productContelonxt = Somelon(
            t.ProductContelonxt.HomelonRelonvelonrselonChronWhoToFollowProductContelonxt(
              t.HomelonRelonvelonrselonChronWhoToFollowProductContelonxt(
                wtfRelonactivelonContelonxt = Somelon(gelontWhoToFollowRelonactivelonContelonxt(input))
              )))
        )
      caselon WhoToFollowArmCandidatelonPipelonlinelonQuelonryTransformelonr.HomelonDisplayLocation =>
        t.AccountReloncommelonndationsMixelonrRelonquelonst(
          clielonntContelonxt = ClielonntContelonxtMarshallelonr(input.clielonntContelonxt),
          product = t.Product.HomelonWhoToFollow,
          productContelonxt = Somelon(
            t.ProductContelonxt.HomelonWhoToFollowProductContelonxt(
              t.HomelonWhoToFollowProductContelonxt(
                wtfRelonactivelonContelonxt = Somelon(gelontWhoToFollowRelonactivelonContelonxt(input))
              )))
        )
      caselon WhoToFollowArmCandidatelonPipelonlinelonQuelonryTransformelonr.ProfilelonDisplayLocation =>
        t.AccountReloncommelonndationsMixelonrRelonquelonst(
          clielonntContelonxt = ClielonntContelonxtMarshallelonr(input.clielonntContelonxt),
          product = t.Product.ProfilelonWhoToFollow,
          productContelonxt = Somelon(
            t.ProductContelonxt.ProfilelonWhoToFollowProductContelonxt(t.ProfilelonWhoToFollowProductContelonxt(
              wtfRelonactivelonContelonxt = Somelon(gelontWhoToFollowRelonactivelonContelonxt(input)),
              profilelonUselonrId = profilelonUselonrIdFelonaturelon
                .flatMap(felonaturelon => input.felonaturelons.map(_.gelont(felonaturelon)))
                .gelontOrelonlselon(throw PipelonlinelonFailurelon(BadRelonquelonst, "profilelonUselonrId not providelond")),
            )))
        )
      caselon displayLocation =>
        throw PipelonlinelonFailurelon(BadRelonquelonst, s"display location $displayLocation not supportelond")
    }
  }

  privatelon delonf gelontWhoToFollowRelonactivelonContelonxt(
    input: Quelonry
  ): t.WhoToFollowRelonactivelonContelonxt = {
    t.WhoToFollowRelonactivelonContelonxt(
      elonxcludelondUselonrIds = elonxcludelondUselonrIdsFelonaturelon.flatMap(felonaturelon =>
        input.felonaturelons
          .map(_.gelont(felonaturelon))),
    )
  }
}
