package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala.Esc rb rdEnt yAnnotat ons
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath

object Esc rb rdAnnotat onHydrator {
  type Type = ValueHydrator[Opt on[Esc rb rdEnt yAnnotat ons], T et]

  val hydratedF eld: F eldByPath = f eldByPath(T et.Esc rb rdEnt yAnnotat onsF eld)

  def apply(repo: Esc rb rdAnnotat onRepos ory.Type): Type =
    ValueHydrator[Opt on[Esc rb rdEnt yAnnotat ons], T et] { (curr, t et) =>
      repo(t et).l ftToTry.map {
        case Return(So (anns)) => ValueState.mod f ed(So (anns))
        case Return(None) => ValueState.unmod f ed(curr)
        case Throw(_) => ValueState.part al(curr, hydratedF eld)
      }
    }
}
