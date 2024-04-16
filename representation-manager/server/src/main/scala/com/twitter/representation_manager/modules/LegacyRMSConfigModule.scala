package com.tw ter.representat on_manager.modules

 mport com.google. nject.Prov des
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object LegacyRMSConf gModule extends Tw terModule {
  @S ngleton
  @Prov des
  @Na d("cac HashKeyPref x")
  def prov desCac HashKeyPref x: Str ng = "RMS"

  @S ngleton
  @Prov des
  @Na d("useContentRecom nderConf gurat on")
  def prov desUseContentRecom nderConf gurat on: Boolean = false
}
