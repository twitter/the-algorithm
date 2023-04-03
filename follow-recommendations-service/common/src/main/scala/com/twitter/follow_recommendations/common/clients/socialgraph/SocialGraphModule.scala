packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.common.BaselonClielonntModulelon
import com.twittelonr.socialgraph.thriftscala.SocialGraphSelonrvicelon
import com.twittelonr.stitch.socialgraph.SocialGraph
import javax.injelonct.Singlelonton

objelonct SocialGraphModulelon
    elonxtelonnds BaselonClielonntModulelon[SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint]
    with MtlsClielonnt {
  ovelonrridelon val labelonl = "social-graph-selonrvicelon"
  ovelonrridelon val delonst = "/s/socialgraph/socialgraph"

  ovelonrridelon delonf configurelonThriftMuxClielonnt(clielonnt: ThriftMux.Clielonnt): ThriftMux.Clielonnt =
    clielonnt.withSelonssionQualifielonr.noFailFast

  @Providelons
  @Singlelonton
  delonf providelonsStitchClielonnt(futurelonIfacelon: SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint): SocialGraph = {
    SocialGraph(futurelonIfacelon)
  }
}
