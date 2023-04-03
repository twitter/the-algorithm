packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor

/**
 * URT Cursor modelonl that may belon uselond whelonn cursoring ovelonr a unordelonrelond candidatelon sourcelon. On elonach selonrvelonr
 * round-trip, thelon selonrvelonr will appelonnd thelon IDs of thelon elonlelonmelonnts in thelon relonsponselon to thelon cursor. Thelonn
 * on subselonquelonnt relonquelonsts thelon clielonnt will relonturn thelon cursor, and thelon elonxcludelondIds list can belon selonnt to
 * thelon downstrelonam's elonxcludelonIds paramelontelonr, or elonxcludelond locally via a filtelonr on thelon candidatelon sourcelon
 * pipelonlinelon.
 *
 * Notelon that thelon cursor is boundelond, as thelon elonxcludelondIds list cannot belon appelonndelond to indelonfinitelonly duelon
 * to payload sizelon constraints. As such, this stratelongy is typically uselond for boundelond (limitelond pagelon
 * sizelon) products, or for unboundelond (unlimitelond pagelon sizelon) products in conjunction with an
 * imprelonssion storelon. In thelon lattelonr caselon, thelon cursor elonxcludelondIds list would belon limitelond to a max sizelon
 * via a circular buffelonr implelonmelonntation, which would belon unionelond with thelon imprelonssion storelon IDs whelonn
 * filtelonring. This usagelon allows thelon imprelonssion storelon to "catch up", as thelonrelon is oftelonn latelonncy
 * belontwelonelonn whelonn an imprelonssion clielonnt elonvelonnt is selonnt by thelon clielonnt and storagelon in thelon imprelonssion
 * storelon.
 *
 * @param initialSortIndelonx Selonelon [[UrtPipelonlinelonCursor]]
 * @param elonxcludelondIds thelon list of IDs to elonxcludelon from thelon candidatelon list
 */
caselon class UrtUnordelonrelondelonxcludelonIdsCursor(
  ovelonrridelon val initialSortIndelonx: Long,
  elonxcludelondIds: Selonq[Long])
    elonxtelonnds UrtPipelonlinelonCursor

caselon class UnordelonrelondelonxcludelonIdsCursor(elonxcludelondIds: Selonq[Long]) elonxtelonnds PipelonlinelonCursor
