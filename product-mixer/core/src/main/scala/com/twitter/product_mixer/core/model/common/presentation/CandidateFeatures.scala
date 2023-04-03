packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import scala.collelonction.immutablelon.ListSelont

/**
 * A list selont of all thelon candidatelon pipelonlinelons a candidatelon originatelond from. This is typically a
 * singlelon elonlelonmelonnt selont, but melonrging candidatelons across pipelonlinelons using
 * [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.CombinelonFelonaturelonMapsCandidatelonMelonrgelonr]]
 * will melonrgelon selonts for thelon candidatelon. Thelon last elonlelonmelonnt of thelon selont is thelon first pipelonlinelon idelonntifielonr
 * as welon prelonpelonnd nelonw onelons sincelon welon want O(1) accelonss for thelon last elonlelonmelonnt.
 */
objelonct CandidatelonPipelonlinelons elonxtelonnds Felonaturelon[UnivelonrsalNoun[Any], ListSelont[CandidatelonPipelonlinelonIdelonntifielonr]]

/**
 * A list selont of all thelon candidatelon sourcelons a candidatelon originatelond from. This is typically a
 * singlelon elonlelonmelonnt selont, but melonrging candidatelons across pipelonlinelons using
 * [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.CombinelonFelonaturelonMapsCandidatelonMelonrgelonr]]
 * will melonrgelon selonts for thelon candidatelon. Thelon last elonlelonmelonnt of thelon selont is thelon first sourcelon idelonntifielonr
 * as welon prelonpelonnd nelonw onelons sincelon welon want O(1) accelonss for thelon last elonlelonmelonnt.
 */
objelonct CandidatelonSourcelons elonxtelonnds Felonaturelon[UnivelonrsalNoun[Any], ListSelont[CandidatelonSourcelonIdelonntifielonr]]

/**
 * Thelon sourcelon position relonlativelon to all candidatelons thelon originating candidatelon sourcelon a candidatelon
 * camelon from. Whelonn melonrgelond with othelonr candidatelons, thelon position from thelon first candidatelon sourcelon
 * takelons priority.
 */
objelonct CandidatelonSourcelonPosition elonxtelonnds Felonaturelon[UnivelonrsalNoun[Any], Int]
