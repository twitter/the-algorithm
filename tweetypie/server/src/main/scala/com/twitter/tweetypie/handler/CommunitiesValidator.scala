package com.tw ter.t etyp e.handler

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t etyp e.Future
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.thr ftscala.Commun  es
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState.Commun yProtectedUserCannotT et
 mport com.tw ter.t etyp e.ut l.Commun yUt l

object Commun  esVal dator {
  case class Request(
    matc dResults: Opt on[FeatureSw chResults],
     sProtected: Boolean,
    commun y: Opt on[Commun  es])

  type Type = Request => Future[Un ]

  val Commun yProtectedCanCreateT et = "commun  es_protected_commun y_t et_creat on_enabled"

  val commun yProtectedCanCreateT etGate: Gate[Request] = Gate { request: Request =>
    request.matc dResults
      .flatMap(_.getBoolean(Commun yProtectedCanCreateT et, shouldLog mpress on = true))
      .conta ns(false)
  }

  def apply(): Type =
    (request: Request) => {
      // Order  s  mportant: t  feature-sw ch gate  s c cked only w n t 
      // request  s both protected & commun y so that t  FS exper  nt  asure nts
      // are based only on data from requests that are subject to reject on by t  val dator.
       f (request. sProtected &&
        Commun yUt l.hasCommun y(request.commun y) &&
        commun yProtectedCanCreateT etGate(request)) {
        Future.except on(T etCreateFa lure.State(Commun yProtectedUserCannotT et))
      } else {
        Future.Un 
      }
    }
}
