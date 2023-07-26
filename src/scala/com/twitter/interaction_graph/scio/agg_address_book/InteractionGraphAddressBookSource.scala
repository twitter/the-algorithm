package com.twittew.intewaction_gwaph.scio.agg_addwess_book

impowt c-com.spotify.scio.sciocontext
i-impowt com.spotify.scio.vawues.scowwection
i-impowt c-com.twittew.addwessbook.jobs.simpwematches.simpweusewmatchesscawadataset
i-impowt c-com.twittew.addwessbook.matches.thwiftscawa.usewmatcheswecowd
i-impowt com.twittew.beam.job.sewviceidentifiewoptions
i-impowt com.twittew.cde.scio.daw_wead.souwceutiw
impowt owg.joda.time.intewvaw

case cwass intewactiongwaphaddwessbooksouwce(
  pipewineoptions: intewactiongwaphaddwessbookoption
)(
  i-impwicit sc: sciocontext, mya
) {
  vaw d-dawenviwonment: stwing = pipewineoptions
    .as(cwassof[sewviceidentifiewoptions])
    .getenviwonment()

  d-def weadsimpweusewmatches(
    dateintewvaw: intewvaw
  ): s-scowwection[usewmatcheswecowd] = {
    souwceutiw.weadmostwecentsnapshotdawdataset[usewmatcheswecowd](
      simpweusewmatchesscawadataset, mya
      d-dateintewvaw, 😳
      d-dawenviwonment)
  }
}
