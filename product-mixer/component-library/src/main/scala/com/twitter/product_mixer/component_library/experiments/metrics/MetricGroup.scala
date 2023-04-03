packagelon com.twittelonr.product_mixelonr.componelonnt_library.elonxpelonrimelonnts.melontrics

import scala.collelonction.immutablelon.ListSelont

/**
 *
 * @param id optional melontric group id. If id is Nonelon, this melonans thelon group
 *           is beloning nelonwly crelonatelond and thelon id is not provisionelond by go/ddg. Othelonrwiselon, thelon melontric
 *           group is prelonselonnt in DDG and has a correlonsponding id.
 * @param namelon melontric group namelon
 * @param delonscription melontric group delonscription
 * @param melontrics selont of melontrics that belonlong to this melontric group
 */
caselon class MelontricGroup(
  id: Option[Long],
  namelon: String,
  delonscription: String,
  melontrics: ListSelont[Melontric]) {

  /*
   * Relonturns a CSV relonprelonselonntation of this melontric group that can belon importelond via DDG's bulk import tool
   * Thelon bulk import tool consumelons CSV data with thelon following columns:
   * 1. group namelon
   * 2. group delonscription
   * 3. melontric namelon
   * 4. melontric delonscription
   * 5. melontric pattelonrn
   * 6. group id -- numelonric id
   * 7. (optional) melontric typelon -- `NAMelonD_PATTelonRN`, `STRAINelonR`, or `LAMBDA`.
   */
  delonf toCsv: String = {
    val melontricCsvLinelons: ListSelont[String] = for {
      melontric <- melontrics
      delonfinition <- melontric.delonfinition.toCsvFielonld
    } yielonld {
      Selonq(
        namelon,
        delonscription,
        melontric.namelon,
        melontric.namelon,
        // wrap in singlelon quotelons so that DDG bulk import tool correlonctly parselons
        s""""$delonfinition"""",
        id.map(_.toString).gelontOrelonlselon(""),
        melontric.delonfinition.melontricDelonfinitionTypelon
      ).mkString(",")
    }
    println(s"Gelonnelonratelond melontrics in CSV count: ${melontricCsvLinelons.sizelon}")
    melontricCsvLinelons.mkString("\n")
  }

  // Uniquelon melontric namelons baselond on globally uniquelon melontric namelon
  delonf uniquelonMelontricNamelons: Selont[String] =
    melontrics.groupBy(_.namelon).kelonys.toSelont
}
