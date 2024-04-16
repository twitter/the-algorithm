package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object ReplyScreenNa Hydrator {
   mport T etLenses.Reply.{ nReplyToScreenNa  => screenNa Lens}

  type Type = ValueHydrator[Opt on[Reply], T etCtx]

  val hydratedF eld: F eldByPath =
    f eldByPath(T et.CoreDataF eld, T etCoreData.ReplyF eld, Reply. nReplyToScreenNa F eld)

  def once(h: ValueHydrator[Opt on[Reply], T etCtx]): Type =
    T etHydrat on.completeOnlyOnce(
      hydrat onType = Hydrat onType.ReplyScreenNa ,
      hydrator = h
    )

  def apply[C](repo: User dent yRepos ory.Type): ValueHydrator[Opt on[Reply], C] =
    ValueHydrator[Reply, C] { (reply, ctx) =>
      val key = UserKey(reply. nReplyToUser d)

      repo(key).l ftToTry.map {
        case Return(user) => ValueState.mod f ed(screenNa Lens.set(reply, So (user.screenNa )))
        case Throw(NotFound) => ValueState.unmod f ed(reply)
        case Throw(_) => ValueState.part al(reply, hydratedF eld)
      }
    }.only f((reply, _) => screenNa Lens.get(reply). sEmpty).l ftOpt on
}
