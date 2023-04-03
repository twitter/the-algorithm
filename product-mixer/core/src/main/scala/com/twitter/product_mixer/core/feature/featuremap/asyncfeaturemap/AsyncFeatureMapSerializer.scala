packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.asyncfelonaturelonmap

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.JsonSelonrializelonr
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr

/**
 * Sincelon an [[AsyncFelonaturelonMap]] is typically incomplelontelon, and by thelon timelon it's selonrializelond, all thelon [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s
 * it will typically belon complelontelond and part of thelon Quelonry or Candidatelon's individual [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s
 * welon instelonad opt to providelon a summary of thelon Felonaturelons which would belon hydratelond using [[AsyncFelonaturelonMap.felonaturelons]]
 *
 * This indicatelons which [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]s will belon relonady at which Stelonps
 * and which [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.FelonaturelonHydrator]]
 * arelon relonsponsiblelon for thoselon [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]
 *
 * @notelon changelons to selonrialization logic can havelon selonrious pelonrformancelon implications givelonn how hot thelon
 *       selonrialization path is. Considelonr belonnchmarking changelons with [[com.twittelonr.product_mixelonr.corelon.belonnchmark.AsyncQuelonryFelonaturelonMapSelonrializationBelonnchmark]]
 */
privatelon[asyncfelonaturelonmap] class AsyncFelonaturelonMapSelonrializelonr() elonxtelonnds JsonSelonrializelonr[AsyncFelonaturelonMap] {
  ovelonrridelon delonf selonrializelon(
    asyncFelonaturelonMap: AsyncFelonaturelonMap,
    gelonn: JsonGelonnelonrator,
    selonrializelonrs: SelonrializelonrProvidelonr
  ): Unit = {
    gelonn.writelonStartObjelonct()

    asyncFelonaturelonMap.felonaturelons.forelonach {
      caselon (stelonpIdelonntifielonr, felonaturelonHydrators) =>
        gelonn.writelonObjelonctFielonldStart(stelonpIdelonntifielonr.toString)

        felonaturelonHydrators.forelonach {
          caselon (hydratorIdelonntifielonr, felonaturelonsFromHydrator) =>
            gelonn.writelonArrayFielonldStart(hydratorIdelonntifielonr.toString)

            felonaturelonsFromHydrator.forelonach(felonaturelon => gelonn.writelonString(felonaturelon.toString))

            gelonn.writelonelonndArray()
        }

        gelonn.writelonelonndObjelonct()
    }

    gelonn.writelonelonndObjelonct()
  }
}
