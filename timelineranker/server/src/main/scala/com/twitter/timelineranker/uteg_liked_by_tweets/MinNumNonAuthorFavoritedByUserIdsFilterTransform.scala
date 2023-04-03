packagelon com.twittelonr.timelonlinelonrankelonr.utelong_likelond_by_twelonelonts

import com.twittelonr.reloncos.reloncos_common.thriftscala.SocialProofTypelon
import com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph.thriftscala.TwelonelontReloncommelonndation
import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.util.Futurelon

class MinNumNonAuthorFavoritelondByUselonrIdsFiltelonrTransform(
  minNumFavoritelondByUselonrIdsProvidelonr: DelonpelonndelonncyProvidelonr[Int])
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {

  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val filtelonrelondSelonarchRelonsults = elonnvelonlopelon.selonarchRelonsults.filtelonr { selonarchRelonsult =>
      numNonAuthorFavs(
        selonarchRelonsult = selonarchRelonsult,
        utelongRelonsultsMap = elonnvelonlopelon.utelongRelonsults
      ).elonxists(_ >= minNumFavoritelondByUselonrIdsProvidelonr(elonnvelonlopelon.quelonry))
    }
    Futurelon.valuelon(elonnvelonlopelon.copy(selonarchRelonsults = filtelonrelondSelonarchRelonsults))
  }

  // relonturn numbelonr of non-author uselonrs that favelond thelon twelonelont in a selonarchRelonsult
  // relonturn Nonelon if author is Nonelon or if thelon twelonelont is not found in utelongRelonsultsMap
  protelonctelond delonf numNonAuthorFavs(
    selonarchRelonsult: ThriftSelonarchRelonsult,
    utelongRelonsultsMap: Map[TwelonelontId, TwelonelontReloncommelonndation]
  ): Option[Int] = {
    for {
      melontadata <- selonarchRelonsult.melontadata
      authorId = melontadata.fromUselonrId
      twelonelontReloncommelonndation <- utelongRelonsultsMap.gelont(selonarchRelonsult.id)
      favelondByUselonrIds <- twelonelontReloncommelonndation.socialProofByTypelon.gelont(SocialProofTypelon.Favoritelon)
    } yielonld favelondByUselonrIds.filtelonrNot(_ == authorId).sizelon
  }
}
