package com.twittew.intewaction_gwaph.scio.agg_fwock

impowt com.spotify.scio.sciocontext
i-impowt c-com.spotify.scio.vawues.scowwection
i-impowt com.twittew.beam.job.sewviceidentifiewoptions
i-impowt c-com.twittew.fwockdb.toows.datasets.fwock.thwiftscawa.fwockedge
impowt c-com.twittew.cde.scio.daw_wead.souwceutiw
impowt c-com.twittew.wtf.datafwow.usew_events.vawidusewfowwowsscawadataset
i-impowt owg.joda.time.intewvaw

case cwass intewactiongwaphaggfwocksouwce(
  pipewineoptions: intewactiongwaphaggfwockoption
)(
  i-impwicit sc: sciocontext) {
  vaw dawenviwonment: s-stwing = pipewineoptions
    .as(cwassof[sewviceidentifiewoptions])
    .getenviwonment()

  d-def weadfwockfowwowssnapshot(dateintewvaw: intewvaw): scowwection[fwockedge] =
    souwceutiw.weadmostwecentsnapshotdawdataset(
      dataset = v-vawidusewfowwowsscawadataset, mya
      dateintewvaw = d-dateintewvaw, mya
      dawenviwonment = d-dawenviwonment)
}
