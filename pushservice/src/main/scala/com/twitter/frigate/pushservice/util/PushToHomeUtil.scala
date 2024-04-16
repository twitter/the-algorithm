package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.dev ce nfo.Dev ce nfo
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
object PushToHo Ut l {
  def get b s2ModelValue(
    dev ce nfoOpt: Opt on[Dev ce nfo],
    target: Target,
    stats: StatsRece ver
  ): Opt on[Map[Str ng, Str ng]] = {
    dev ce nfoOpt.flatMap { dev ce nfo =>
      val  sAndro dEnabled = dev ce nfo. sLandOnHo Andro d && target.params(
        PushFeatureSw chParams.EnableT etPushToHo Andro d)
      val  s OSEnabled = dev ce nfo. sLandOnHo  OS && target.params(
        PushFeatureSw chParams.EnableT etPushToHo  OS)
       f ( sAndro dEnabled ||  s OSEnabled) {
        stats.counter("enable_push_to_ho "). ncr()
        So (Map(" s_land_on_ho " -> "true"))
      } else None
    }
  }
}
