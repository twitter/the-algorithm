package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object Contr butorHydrator {
  type Type = ValueHydrator[Opt on[Contr butor], T etCtx]

  val hydratedF eld: F eldByPath = f eldByPath(T et.Contr butorF eld, Contr butor.ScreenNa F eld)

  def once(h: Type): Type =
    T etHydrat on.completeOnlyOnce(
      hydrat onType = Hydrat onType.Contr butor,
      hydrator = h
    )

  def apply(repo: User dent yRepos ory.Type): Type =
    ValueHydrator[Contr butor, T etCtx] { (curr, _) =>
      repo(UserKey(curr.user d)).l ftToTry.map {
        case Return(user dent) => ValueState.delta(curr, update(curr, user dent))
        case Throw(NotFound) => ValueState.unmod f ed(curr)
        case Throw(_) => ValueState.part al(curr, hydratedF eld)
      }
    }.only f((curr, _) => curr.screenNa . sEmpty).l ftOpt on

  /**
   * Updates a Contr butor us ng t  g ven user data.
   */
  pr vate def update(curr: Contr butor, user dent: User dent y): Contr butor =
    curr.copy(
      screenNa  = So (user dent.screenNa )
    )
}
