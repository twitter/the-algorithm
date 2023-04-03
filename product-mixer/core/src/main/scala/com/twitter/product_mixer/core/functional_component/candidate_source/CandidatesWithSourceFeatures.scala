packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap

/**
 * Relonsults from a candidatelon sourcelon, optionally carrying elonxtractelond quelonry lelonvelonl felonaturelons to add
 * to thelon quelonry's felonaturelon map (elon.g, elonxtracting relonusablelon felonaturelons from thelon thrift relonsponselon of thrift
 * call).
 * @param candidatelons Thelon candidatelons relonturnelond from thelon undelonrlying CandidatelonSourelon
 * @param felonaturelons [[FelonaturelonMap]] containing thelon felonaturelons from thelon candidatelon sourcelon
 *                                    to melonrgelon back into thelon PipelonlinelonQuelonry FelonaturelonMap.
 * @tparam Candidatelon Thelon typelon of relonsult
 */
caselon class CandidatelonsWithSourcelonFelonaturelons[+Candidatelon](
  candidatelons: Selonq[Candidatelon],
  felonaturelons: FelonaturelonMap)
