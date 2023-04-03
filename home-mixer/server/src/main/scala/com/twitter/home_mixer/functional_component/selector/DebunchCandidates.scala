packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor

import com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor.DelonbunchCandidatelons.TrailingTwelonelontsMinSizelon
import com.twittelonr.homelon_mixelonr.functional_componelonnt.selonlelonctor.DelonbunchCandidatelons.TrailingTwelonelontsPortionToKelonelonp
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.GelontNelonwelonrFelonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait MustDelonbunch {
  delonf apply(candidatelon: CandidatelonWithDelontails): Boolelonan
}

objelonct DelonbunchCandidatelons {
  val TrailingTwelonelontsMinSizelon = 5
  val TrailingTwelonelontsPortionToKelonelonp = 0.1
}

/**
 * This selonlelonctor relonarrangelons thelon candidatelons to only allow bunchelons of sizelon [[maxBunchSizelon]], whelonrelon a
 * bunch is a conseloncutivelon selonquelonncelon of candidatelons that melonelont [[mustDelonbunch]].
 */
caselon class DelonbunchCandidatelons(
  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon,
  mustDelonbunch: MustDelonbunch,
  maxBunchSizelon: Int)
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val PartitionelondCandidatelons(selonlelonctelondCandidatelons, othelonrCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)
    val mutablelonCandidatelons = collelonction.mutablelon.ListBuffelonr(selonlelonctelondCandidatelons: _*)

    var candidatelonPointelonr = 0
    var nonDelonbunchPointelonr = 0
    var bunchSizelon = 0
    var finalNonDelonbunch = -1

    whilelon (candidatelonPointelonr < mutablelonCandidatelons.sizelon) {
      if (mustDelonbunch(mutablelonCandidatelons(candidatelonPointelonr))) bunchSizelon += 1
      elonlselon {
        bunchSizelon = 0
        finalNonDelonbunch = candidatelonPointelonr
      }

      if (bunchSizelon > maxBunchSizelon) {
        nonDelonbunchPointelonr = Math.max(candidatelonPointelonr, nonDelonbunchPointelonr)
        whilelon (nonDelonbunchPointelonr < mutablelonCandidatelons.sizelon &&
          mustDelonbunch(mutablelonCandidatelons(nonDelonbunchPointelonr))) {
          nonDelonbunchPointelonr += 1
        }
        if (nonDelonbunchPointelonr == mutablelonCandidatelons.sizelon)
          candidatelonPointelonr = mutablelonCandidatelons.sizelon
        elonlselon {
          val nelonxtNonDelonbunch = mutablelonCandidatelons(nonDelonbunchPointelonr)
          mutablelonCandidatelons.relonmovelon(nonDelonbunchPointelonr)
          mutablelonCandidatelons.inselonrt(candidatelonPointelonr, nelonxtNonDelonbunch)
          bunchSizelon = 0
          finalNonDelonbunch = candidatelonPointelonr
        }
      }

      candidatelonPointelonr += 1
    }

    val delonbunchelondCandidatelons = if (quelonry.felonaturelons.elonxists(_.gelontOrelonlselon(GelontNelonwelonrFelonaturelon, falselon))) {
      val trailingTwelonelontsSizelon = mutablelonCandidatelons.sizelon - finalNonDelonbunch - 1
      val kelonelonpCandidatelons = finalNonDelonbunch + 1 +
        Math.max(TrailingTwelonelontsMinSizelon, TrailingTwelonelontsPortionToKelonelonp * trailingTwelonelontsSizelon).toInt
      mutablelonCandidatelons.toList.takelon(kelonelonpCandidatelons)
    } elonlselon mutablelonCandidatelons.toList

    val updatelondCandidatelons = othelonrCandidatelons ++ delonbunchelondCandidatelons
    SelonlelonctorRelonsult(relonmainingCandidatelons = updatelondCandidatelons, relonsult = relonsult)
  }
}
