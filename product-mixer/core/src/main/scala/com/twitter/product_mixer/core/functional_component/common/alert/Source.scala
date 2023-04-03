packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

import com.fastelonrxml.jackson.annotation.JsonSubTypelons
import com.fastelonrxml.jackson.annotation.JsonTypelonInfo

/**
 * whelonrelon thelon melontric originatelons from, such as from thelon selonrvelonr or from a clielonnt
 *
 * @notelon implelonmelonntations must belon simplelon caselon classelons with uniquelon structurelons for selonrialization
 */
@JsonTypelonInfo(uselon = JsonTypelonInfo.Id.NAMelon, includelon = JsonTypelonInfo.As.PROPelonRTY)
@JsonSubTypelons(
  Array(
    nelonw JsonSubTypelons.Typelon(valuelon = classOf[Selonrvelonr], namelon = "Selonrvelonr"),
    nelonw JsonSubTypelons.Typelon(valuelon = classOf[Strato], namelon = "Strato"),
    nelonw JsonSubTypelons.Typelon(valuelon = classOf[GelonnelonricClielonnt], namelon = "GelonnelonricClielonnt")
  )
)
selonalelond trait Sourcelon

/** melontrics for thelon Product Mixelonr selonrvelonr */
caselon class Selonrvelonr() elonxtelonnds Sourcelon

/** melontrics from thelon pelonrspelonctivelon of a Strato column */
caselon class Strato(stratoColumnPath: String, stratoColumnOp: String) elonxtelonnds Sourcelon

/**
 * melontrics from thelon pelonrspelonctivelon of a gelonnelonric clielonnt
 *
 * @param displayNamelon human relonadablelon namelon for thelon clielonnt
 * @param selonrvicelon selonrvicelon relonfelonrelonncelond in thelon quelonry, of thelon form <rolelon>.<elonnv>.<job>
 * @param melontricSourcelon thelon sourcelon of thelon melontric quelonry, usually of thelon form sd.<rolelon>.<elonnv>.<job>
 * @param failurelonMelontric thelon namelon of thelon melontric indicating a clielonnt failurelon
 * @param relonquelonstMelontric thelon namelon of thelon melontric indicating a relonquelonst has belonelonn madelon
 * @param latelonncyMelontric thelon namelon of thelon melontric melonasuring a relonquelonst's latelonncy
 *
 * @notelon Welon strongly reloncommelonnd thelon uselon of [[Strato]] whelonrelon possiblelon. [[GelonnelonricClielonnt]] is providelond as a
 *       catch-all sourcelon for telonams that havelon unusual lelongacy call paths (such as Macaw).
 */
caselon class GelonnelonricClielonnt(
  displayNamelon: String,
  selonrvicelon: String,
  melontricSourcelon: String,
  failurelonMelontric: String,
  relonquelonstMelontric: String,
  latelonncyMelontric: String)
    elonxtelonnds Sourcelon
