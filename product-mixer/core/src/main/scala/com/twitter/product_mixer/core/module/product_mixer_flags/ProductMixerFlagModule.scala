package com.tw ter.product_m xer.core.module.product_m xer_flags

 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.Durat on

object ProductM xerFlagModule extends Tw terModule {
  f nal val Serv ceLocal = "serv ce.local"
  f nal val Conf gRepoLocalPath = "conf grepo.local_path"
  f nal val FeatureSw c sPath = "feature_sw c s.path"
  f nal val StratoLocalRequestT  out = "strato.local.request_t  out"
  f nal val Scr beAB mpress ons = "scr be.ab_ mpress ons"
  f nal val P pel neExecut onLoggerAllowL st = "p pel ne_execut on_logger.allow_l st"

  flag[Boolean](
    na  = Serv ceLocal,
    default = false,
     lp = " s t  server runn ng locally or  n a DC")

  flag[Str ng](
    na  = Conf gRepoLocalPath,
    default = System.getProperty("user.ho ") + "/workspace/conf g",
     lp = "Path to y  local conf g repo"
  )

  flag[Boolean](
    na  = Scr beAB mpress ons,
     lp = "Enable scr b ng of AB  mpress ons"
  )

  flag[Str ng](
    na  = FeatureSw c sPath,
     lp = "Path to y  local conf g repo"
  )

  flag[Durat on](
    na  = StratoLocalRequestT  out,
     lp = "Overr de t  request t  out for Strato w n runn ng locally"
  )

  flag[Seq[Str ng]](
    na  = P pel neExecut onLoggerAllowL st,
    default = Seq.empty,
     lp =
      "Spec fy user role(s) for wh ch deta led log  ssages (conta n ng P  ) can be made. Accepts a s ngle role or a comma separated l st 'a,b,c'"
  )

  /**
   *  nvoked at t  end of server startup.
   *
   *  f  're runn ng locally,   d splay a n ce  ssage and a l nk to t  adm n page
   */
  overr de def s ngletonPostWarmupComplete( njector:  njector): Un  = {
    val  sLocalServ ce =  njector. nstance[Boolean](Flags.na d(Serv ceLocal))
     f ( sLocalServ ce) {
      // Extract serv ce na  from cl ent d s nce t re  sn't a spec f c flag for that
      val cl ent d =  njector. nstance[Str ng](Flags.na d("thr ft.cl ent d"))
      val na  = cl ent d.spl ("\\.")(0)

      val adm nPort =  njector. nstance[Str ng](Flags.na d("adm n.port"))
      val url = s"http://localhost$adm nPort/"

      // Pr nt  nstead of log, so   goes on stdout and doesn't get t  logg ng decorat ons.
      // Update   local develop nt rec pe (local-develop nt.rst)  f mak ng changes to t 
      //  ssage.
      pr ntln("===============================================================================")
      pr ntln(s" lco  to a Product M xer Serv ce, $na ")
      pr ntln(s"  can v ew t  adm n endpo nt and thr ft  b forms at $url")
      pr ntln("Look ng for support? Have quest ons? #product-m xer on Slack.")
      pr ntln("===============================================================================")
    }
  }
}
