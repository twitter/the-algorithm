package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Hydrates t  conversat on d f eld for any t et that  s a reply to anot r t et.
 *   uses that ot r t et's conversat on d.
 */
object Conversat on dHydrator {
  type Type = ValueHydrator[Opt on[Conversat on d], T etCtx]

  val hydratedF eld: F eldByPath =
    f eldByPath(T et.CoreDataF eld, T etCoreData.Conversat on dF eld)

  def apply(repo: Conversat on dRepos ory.Type): Type =
    ValueHydrator[Opt on[Conversat on d], T etCtx] { (_, ctx) =>
      ctx. nReplyToT et d match {
        case None =>
          // Not a reply to anot r t et, use t et  d as conversat on root
          St ch.value(ValueState.mod f ed(So (ctx.t et d)))
        case So (parent d) =>
          // Lookup conversat on  d from  n-reply-to t et
          repo(Conversat on dKey(ctx.t et d, parent d)).l ftToTry.map {
            case Return(root d) => ValueState.mod f ed(So (root d))
            case Throw(_) => ValueState.part al(None, hydratedF eld)
          }
      }
    }.only f((curr, _) => curr. sEmpty)
}
