packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.FavoritelondByUselonrIdsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.timelonlinelonselonrvicelon.TimelonlinelonSelonrvicelon
import com.twittelonr.stitch.timelonlinelonselonrvicelon.TimelonlinelonSelonrvicelon.GelontPelonrspelonctivelons
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.PelonrspelonctivelonTypelon
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.PelonrspelonctivelonTypelon.Favoritelond
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Filtelonr out unlikelon elondgelons from likelond-by twelonelonts
 * Uselonful if thelon likelons comelon from a cachelon and beloncauselon UTelonG doelons not fully relonmovelon unlikelon elondgelons.
 */
@Singlelonton
class PelonrspelonctivelonFiltelonrelondSocialContelonxtFelonaturelonHydrator @Injelonct() (timelonlinelonSelonrvicelon: TimelonlinelonSelonrvicelon)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("PelonrspelonctivelonFiltelonrelondSocialContelonxt")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon)

  privatelon val MaxCountUselonrs = 10
  privatelon val favoritelonPelonrspelonctivelonSelont: Selont[PelonrspelonctivelonTypelon] = Selont(Favoritelond)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    val elonngagingUselonrIdtoTwelonelontId = candidatelons.flatMap { candidatelon =>
      candidatelon.felonaturelons
        .gelont(FavoritelondByUselonrIdsFelonaturelon).takelon(MaxCountUselonrs)
        .map(favoritelondBy => favoritelondBy -> candidatelon.candidatelon.id)
    }

    val quelonrielons = elonngagingUselonrIdtoTwelonelontId.map {
      caselon (uselonrId, twelonelontId) =>
        GelontPelonrspelonctivelons.Quelonry(uselonrId = uselonrId, twelonelontId = twelonelontId, typelons = favoritelonPelonrspelonctivelonSelont)
    }

    Stitch.collelonct(quelonrielons.map(timelonlinelonSelonrvicelon.gelontPelonrspelonctivelon)).map { pelonrspelonctivelonRelonsults =>
      val validUselonrIdTwelonelontIds: Selont[(Long, Long)] =
        quelonrielons
          .zip(pelonrspelonctivelonRelonsults)
          .collelonct { caselon (quelonry, pelonrspelonctivelon) if pelonrspelonctivelon.favoritelond => quelonry }
          .map(quelonry => (quelonry.uselonrId, quelonry.twelonelontId))
          .toSelont

      candidatelons.map { candidatelon =>
        val pelonrspelonctivelonFiltelonrelondFavoritelondByUselonrIds: Selonq[Long] = candidatelon.felonaturelons
          .gelont(FavoritelondByUselonrIdsFelonaturelon).takelon(MaxCountUselonrs)
          .filtelonr { uselonrId => validUselonrIdTwelonelontIds.contains((uselonrId, candidatelon.candidatelon.id)) }

        FelonaturelonMapBuildelonr()
          .add(PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon, pelonrspelonctivelonFiltelonrelondFavoritelondByUselonrIds)
          .build()
      }
    }
  }
}
