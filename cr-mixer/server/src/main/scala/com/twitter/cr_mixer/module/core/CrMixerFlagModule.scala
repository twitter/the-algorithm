package com.tw ter.cr_m xer.module.core

 mport com.tw ter. nject.Tw terModule

object CrM xerFlagNa  {
  val SERV CE_FLAG = "cr_m xer.flag"
  val DarkTraff cF lterDec derKey = "thr ft.dark.traff c.f lter.dec der_key"
}

object CrM xerFlagModule extends Tw terModule {
   mport CrM xerFlagNa ._

  flag[Boolean](na  = SERV CE_FLAG, default = false,  lp = "T   s a CR M xer flag")

  flag[Str ng](
    na  = DarkTraff cF lterDec derKey,
    default = "dark_traff c_f lter",
     lp = "Dark traff c f lter dec der key"
  )
}
