packagelon com.twittelonr.intelonraction_graph.scio.common

import com.twittelonr.intelonraction_graph.thriftscala.FelonaturelonNamelon

/** Intelonraction Graph Raw Input typelon delonfinelons a common typelon for elondgelon / velonrtelonx felonaturelon calculation
 * It has fielonlds: (sourcelon Id, delonstination Id, Felonaturelon Namelon, agelon of this relonlationship (in days),
 * and valuelon to belon aggrelongatelond)
 */
caselon class IntelonractionGraphRawInput(
  src: Long,
  dst: Long,
  namelon: FelonaturelonNamelon,
  agelon: Int,
  felonaturelonValuelon: Doublelon)

caselon class FelonaturelonKelony(
  src: Long,
  delonst: Long,
  namelon: FelonaturelonNamelon)

caselon class Twelonelonpcrelond(uselonrId: Long, twelonelonpcrelond: Short)
