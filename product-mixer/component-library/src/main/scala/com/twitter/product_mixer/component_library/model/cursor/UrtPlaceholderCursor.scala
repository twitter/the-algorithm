packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor

/**
 * Cursor modelonl that may belon uselond whelonn welon just nelonelond a placelonholdelonr but no relonal cursor valuelon. Sincelon URT
 * relonquirelons that top and bottom cursors arelon always prelonselonnt, placelonholdelonrs arelon oftelonn uselond whelonn up
 * scrolling (PTR) is not supportelond on a timelonlinelon. Whilelon placelonholdelonr cursors gelonnelonrally should not belon
 * submittelond back by thelon clielonnt, thelony somelontimelons arelon likelon in thelon caselon of clielonnt-sidelon background
 * auto-relonfrelonsh. If submittelond, thelon backelonnd will trelonat any relonquelonst with a placelonholdelonr cursor likelon no
 * cursor was submittelond, which will belonhavelon thelon samelon way as an initial pagelon load.
 */
caselon class UrtPlacelonholdelonrCursor() elonxtelonnds UrtPipelonlinelonCursor {
  // This valuelon is unuselond, in that it is not selonrializelond into thelon final cursor valuelon
  ovelonrridelon delonf initialSortIndelonx: Long = throw nelonw UnsupportelondOpelonrationelonxcelonption(
    "initialSortIndelonx is not delonfinelond for placelonholdelonr cursors")
}
