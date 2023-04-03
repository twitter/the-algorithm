packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.async_felonaturelon_map

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.asyncfelonaturelonmap.AsyncFelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.PipelonlinelonStelonpIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasAsyncFelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.async_felonaturelon_map_elonxeloncutor.AsyncFelonaturelonMapelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.async_felonaturelon_map_elonxeloncutor.AsyncFelonaturelonMapelonxeloncutorRelonsults
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * Async Felonaturelon Hydrator Stelonp, it takelons an elonxisting asyn felonaturelon map and elonxeloncutelons any hydration
 * nelonelondelond belonforelon thelon nelonxt stelonp. Thelon statelon objelonct is relonsponsiblelon for kelonelonping thelon updatelond quelonry
 * with thelon updatelond felonaturelon map.
 *
 * @param asyncFelonaturelonMapelonxeloncutor Async felonaturelon map elonxeloncutor
 *
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class AsyncFelonaturelonMapStelonp[
  Quelonry <: PipelonlinelonQuelonry,
  Statelon <: HasQuelonry[Quelonry, Statelon] with HasAsyncFelonaturelonMap[Statelon]] @Injelonct() (
  asyncFelonaturelonMapelonxeloncutor: AsyncFelonaturelonMapelonxeloncutor)
    elonxtelonnds Stelonp[
      Statelon,
      AsyncFelonaturelonMapStelonpConfig,
      AsyncFelonaturelonMap,
      AsyncFelonaturelonMapelonxeloncutorRelonsults
    ] {
  ovelonrridelon delonf iselonmpty(config: AsyncFelonaturelonMapStelonpConfig): Boolelonan = falselon

  ovelonrridelon delonf adaptInput(
    statelon: Statelon,
    config: AsyncFelonaturelonMapStelonpConfig
  ): AsyncFelonaturelonMap = statelon.asyncFelonaturelonMap

  ovelonrridelon delonf arrow(
    config: AsyncFelonaturelonMapStelonpConfig,
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[AsyncFelonaturelonMap, AsyncFelonaturelonMapelonxeloncutorRelonsults] =
    asyncFelonaturelonMapelonxeloncutor.arrow(config.stelonpToHydratelonFor, config.currelonntStelonp, contelonxt)

  ovelonrridelon delonf updatelonStatelon(
    statelon: Statelon,
    elonxeloncutorRelonsult: AsyncFelonaturelonMapelonxeloncutorRelonsults,
    config: AsyncFelonaturelonMapStelonpConfig
  ): Statelon = {
    val hydratelondFelonaturelonMap =
      elonxeloncutorRelonsult.felonaturelonMapsByStelonp.gelontOrelonlselon(config.stelonpToHydratelonFor, FelonaturelonMap.elonmpty)
    if (hydratelondFelonaturelonMap.iselonmpty) {
      statelon
    } elonlselon {
      val updatelondFelonaturelonMap = statelon.quelonry.felonaturelons
        .gelontOrelonlselon(FelonaturelonMap.elonmpty) ++ hydratelondFelonaturelonMap
      statelon.updatelonQuelonry(
        statelon.quelonry
          .withFelonaturelonMap(updatelondFelonaturelonMap).asInstancelonOf[Quelonry])
    }
  }
}

caselon class AsyncFelonaturelonMapStelonpConfig(
  stelonpToHydratelonFor: PipelonlinelonStelonpIdelonntifielonr,
  currelonntStelonp: PipelonlinelonStelonpIdelonntifielonr)
