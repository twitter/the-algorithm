package com.tw ter.s mclustersann.modules

 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclustersann.common.FlagNa s

object FlagsModule extends Tw terModule {

  flag[ nt](
    na  = FlagNa s.Serv ceT  out,
    default = 40,
     lp = "T  threshold of Request T  out"
  )

  flag[Str ng](
    na  = FlagNa s.DarkTraff cF lterDec derKey,
    default = "dark_traff c_f lter",
     lp = "Dark traff c f lter dec der key"
  )

  flag[Str ng](
    na  = FlagNa s.Cac Dest,
    default = "/s/cac /content_recom nder_un f ed_v2",
     lp = "Path to  mcac  serv ce. Currently us ng CR un form scor ng cac "
  )

  flag[ nt](
    na  = FlagNa s.Cac T  out,
    default = 15,
     lp = "T  threshold of  mCac  T  out"
  )

  flag[Boolean](
    na  = FlagNa s.Cac AsyncUpdate,
    default = false,
     lp = "W t r to enable t  async update for t   mCac "
  )

  flag[ nt](
    na  = FlagNa s.MaxTopT etPerCluster,
    default = 200,
     lp = "Max mum number of t ets to take per each s mclusters"
  )

}
