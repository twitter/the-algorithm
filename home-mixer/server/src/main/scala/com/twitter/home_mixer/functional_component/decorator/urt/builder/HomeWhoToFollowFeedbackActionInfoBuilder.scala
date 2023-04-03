packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata.WhoToFollowFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonFelonelondbackActionInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.stringcelonntelonr.clielonnt.elonxtelonrnalStringRelongistry
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import javax.injelonct.Injelonct
import javax.injelonct.Providelonr
import javax.injelonct.Singlelonton
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => tl}
import com.twittelonr.timelonlinelons.util.FelonelondbackRelonquelonstSelonrializelonr
import com.twittelonr.timelonlinelonselonrvicelon.suggelonsts.thriftscala.SuggelonstTypelon
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.FelonelondbackTypelon

objelonct HomelonWhoToFollowFelonelondbackActionInfoBuildelonr {
  privatelon val FelonelondbackMelontadata = tl.FelonelondbackMelontadata(
    injelonctionTypelon = Somelon(SuggelonstTypelon.WhoToFollow),
    elonngagelonmelonntTypelon = Nonelon,
    elonntityIds = Selonq.elonmpty,
    ttlMs = Nonelon
  )
  privatelon val FelonelondbackRelonquelonst =
    tl.DelonfaultFelonelondbackRelonquelonst2(FelonelondbackTypelon.SelonelonFelonwelonr, FelonelondbackMelontadata)
  privatelon val elonncodelondFelonelondbackRelonquelonst =
    FelonelondbackRelonquelonstSelonrializelonr.selonrializelon(tl.FelonelondbackRelonquelonst.DelonfaultFelonelondbackRelonquelonst2(FelonelondbackRelonquelonst))
}

@Singlelonton
caselon class HomelonWhoToFollowFelonelondbackActionInfoBuildelonr @Injelonct() (
  @ProductScopelond elonxtelonrnalStringRelongistryProvidelonr: Providelonr[elonxtelonrnalStringRelongistry],
  @ProductScopelond stringCelonntelonrProvidelonr: Providelonr[StringCelonntelonr])
    elonxtelonnds BaselonFelonelondbackActionInfoBuildelonr[PipelonlinelonQuelonry, UselonrCandidatelon] {

  privatelon val whoToFollowFelonelondbackActionInfoBuildelonr = WhoToFollowFelonelondbackActionInfoBuildelonr(
    elonxtelonrnalStringRelongistry = elonxtelonrnalStringRelongistryProvidelonr.gelont(),
    stringCelonntelonr = stringCelonntelonrProvidelonr.gelont(),
    elonncodelondFelonelondbackRelonquelonst = Somelon(HomelonWhoToFollowFelonelondbackActionInfoBuildelonr.elonncodelondFelonelondbackRelonquelonst)
  )

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UselonrCandidatelon,
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[FelonelondbackActionInfo] =
    whoToFollowFelonelondbackActionInfoBuildelonr.apply(quelonry, candidatelon, candidatelonFelonaturelons)
}
