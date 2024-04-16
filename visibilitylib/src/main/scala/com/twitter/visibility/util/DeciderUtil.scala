package com.tw ter.v s b l y.ut l

 mport com.tw ter.abdec der.ABDec derFactory
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.Dec derFactory
 mport com.tw ter.dec der.LocalOverr des
 mport com.tw ter.logg ng._

object Dec derUt l {
  val DefaultDec derPath = "/conf g/com/tw ter/v s b l y/dec der.yml"

  pr vate val zone = Opt on(System.getProperty("dc")).getOrElse("atla")
  val DefaultDec derOverlayPath: So [Str ng] = So (
    s"/usr/local/conf g/overlays/v s b l y-l brary/v s b l y-l brary/prod/$zone/dec der_overlay.yml"
  )

  val DefaultABDec derPath = "/usr/local/conf g/abdec der/abdec der.yml"

  def mkDec der(
    dec derBasePath: Str ng = DefaultDec derPath,
    dec derOverlayPath: Opt on[Str ng] = DefaultDec derOverlayPath,
    useLocalDec derOverr des: Boolean = false,
  ): Dec der = {
    val f leBased = new Dec derFactory(So (dec derBasePath), dec derOverlayPath)()
     f (useLocalDec derOverr des) {
      LocalOverr des.dec der("v s b l y-l brary").orElse(f leBased)
    } else {
      f leBased
    }
  }

  def mkLocalDec der: Dec der = mkDec der(dec derOverlayPath = None)

  def mkABDec der(
    scr beLogger: Opt on[Logger],
    abDec derPath: Str ng = DefaultABDec derPath
  ): Logg ngABDec der = {
    ABDec derFactory(
      abDec derPath,
      So ("product on"),
      scr beLogger = scr beLogger
    ).bu ldW hLogg ng()
  }
}
