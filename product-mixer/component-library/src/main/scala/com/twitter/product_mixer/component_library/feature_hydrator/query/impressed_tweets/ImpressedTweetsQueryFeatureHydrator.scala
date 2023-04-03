packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.imprelonsselond_twelonelonts

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.timelonlinelons.imprelonssionstorelon.thriftscala.ImprelonssionList
import com.twittelonr.util.Futurelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Quelonry Felonaturelon to storelon ids of thelon twelonelonts imprelonsselond by thelon uselonr.
 */
caselon objelonct ImprelonsselondTwelonelonts elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[PipelonlinelonQuelonry, Selonq[Long]] {
  ovelonrridelon val delonfaultValuelon: Selonq[Long] = Selonq.elonmpty
}

/**
 * elonnrich thelon quelonry with a list of twelonelont ids that thelon uselonr has alrelonady selonelonn.
 */
@Singlelonton
caselon class ImprelonsselondTwelonelontsQuelonryFelonaturelonHydrator @Injelonct() (
  twelonelontImprelonssionStorelon: RelonadablelonStorelon[Long, ImprelonssionList])
    elonxtelonnds QuelonryFelonaturelonHydrator[PipelonlinelonQuelonry] {
  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("TwelonelontsToelonxcludelon")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ImprelonsselondTwelonelonts)

  ovelonrridelon delonf hydratelon(quelonry: PipelonlinelonQuelonry): Stitch[FelonaturelonMap] = {
    quelonry.gelontOptionalUselonrId match {
      caselon Somelon(uselonrId) =>
        val felonaturelonMapRelonsult: Futurelon[FelonaturelonMap] = twelonelontImprelonssionStorelon
          .gelont(uselonrId).map { imprelonssionListOpt =>
            val twelonelontIdsOpt = for {
              imprelonssionList <- imprelonssionListOpt
              imprelonssions <- imprelonssionList.imprelonssions
            } yielonld {
              imprelonssions.map(_.twelonelontId)
            }
            val twelonelontIds = twelonelontIdsOpt.gelontOrelonlselon(Selonq.elonmpty)
            FelonaturelonMapBuildelonr().add(ImprelonsselondTwelonelonts, twelonelontIds).build()
          }
        Stitch.callFuturelon(felonaturelonMapRelonsult)
      // Non-loggelond-in uselonrs do not havelon uselonrId, relonturns elonmpty felonaturelon

      caselon Nonelon =>
        val felonaturelonMapRelonsult = FelonaturelonMapBuildelonr().add(ImprelonsselondTwelonelonts, Selonq.elonmpty).build()
        Stitch.valuelon(felonaturelonMapRelonsult)
    }
  }
}
