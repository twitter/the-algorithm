package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e. d a. d a
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object  d a sProtectedHydrator {
  type Ctx =  d aEnt yHydrator.Cac able.Ctx
  type Type =  d aEnt yHydrator.Cac able.Type

  val hydratedF eld: F eldByPath =  d aEnt yHydrator.hydratedF eld

  def apply(repo: UserProtect onRepos ory.Type): Type =
    ValueHydrator[ d aEnt y, Ctx] { (curr, ctx) =>
      val request = UserKey(ctx.user d)

      repo(request).l ftToTry.map {
        case Return(p) => ValueState.mod f ed(curr.copy( sProtected = So (p)))
        case Throw(NotFound) => ValueState.unmod f ed(curr)
        case Throw(_) => ValueState.part al(curr, hydratedF eld)
      }
    }.only f { (curr, ctx) =>
      //   need to update  sProtected for  d a ent  es that:
      // 1. Do not already have   set.
      // 2. D d not co  from anot r t et.
      //
      //  f t  ent y does not have an expandedUrl,   can't be sure
      // w t r t   d a or g nated w h t  t et.
      curr. sProtected. sEmpty &&
       d a. sOwn d a(ctx.t et d, curr) &&
      curr.expandedUrl != null
    }
}
