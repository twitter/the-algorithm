package com.tw ter.cr_m xer.module.core

 mport com.google. nject.Prov des
 mport com.google. nject.na .Na d
 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.logg ng.Logger
 mport javax. nject.S ngleton

object ABDec derModule extends Tw terModule {

  flag(
    na  = "abdec der.path",
    default = "/usr/local/conf g/abdec der/abdec der.yml",
     lp = "path to t  abdec der Yml f le locat on"
  )

  @Prov des
  @S ngleton
  def prov deABDec der(
    @Flag("abdec der.path") abDec derYmlPath: Str ng,
    @Na d(ModuleNa s.AbDec derLogger) scr beLogger: Logger
  ): Logg ngABDec der = {
    ABDec derFactory(
      abDec derYmlPath = abDec derYmlPath,
      scr beLogger = So (scr beLogger),
      env ron nt = So ("product on")
    ).bu ldW hLogg ng()
  }
}
