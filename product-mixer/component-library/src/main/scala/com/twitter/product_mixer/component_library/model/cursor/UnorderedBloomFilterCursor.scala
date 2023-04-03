packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor
import com.twittelonr.selonarch.common.util.bloomfiltelonr.AdaptivelonLongIntBloomFiltelonr

/**
 * Cursor modelonl that may belon uselond whelonn cursoring ovelonr a unordelonrelond candidatelon sourcelon. On elonach selonrvelonr
 * round-trip, thelon selonrvelonr will add thelon IDs of thelon candidatelons into a spacelon elonfficielonnt bloom filtelonr.
 * Thelonn on subselonquelonnt relonquelonsts thelon clielonnt will relonturn thelon cursor, and thelon bloom filtelonr can belon selonnt to
 * thelon downstrelonam's bloom filtelonr paramelontelonr in selonrializelond form, or elonxcludelon candidatelons locally via a
 * filtelonr on thelon candidatelon sourcelon pipelonlinelon.
 *
 * @param initialSortIndelonx Selonelon [[UrtPipelonlinelonCursor]]
 * @param longIntBloomFiltelonr thelon bloom filtelonr to uselon to delondup candidatelon from thelon candidatelon list
 */
caselon class UrtUnordelonrelondBloomFiltelonrCursor(
  ovelonrridelon val initialSortIndelonx: Long,
  // spacelon-elonfficielonnt and mutablelon variant of thelon BloomFiltelonr class uselond for storing long intelongelonrs.
  longIntBloomFiltelonr: AdaptivelonLongIntBloomFiltelonr)
    elonxtelonnds UrtPipelonlinelonCursor

caselon class UnordelonrelondBloomFiltelonrCursor(
  longIntBloomFiltelonr: AdaptivelonLongIntBloomFiltelonr)
    elonxtelonnds PipelonlinelonCursor
