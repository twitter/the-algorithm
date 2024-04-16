package com.tw ter.t etyp e
package hydrator

 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter. d aserv ces. d a_ut l.Gener c d aKey
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.thr ftscala.UrlEnt y
 mport com.tw ter.t etyp e. d a.thr ftscala. d aRef
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath

/**
 *  d aRefsHydrator hydrates t  T et. d aRefs f eld based on stored  d a keys
 * and pasted  d a.  d a keys are ava lable  n three ways:
 *
 * 1. (For old T ets):  n t  stored  d aEnt y
 * 2. (For 2016+ T ets):  n t   d aKeys f eld
 * 3. From ot r T ets us ng pasted  d a
 *
 * T  hydrator comb nes t se three s ces  nto a s ngle f eld, prov d ng t 
 *  d a key and s ce T et  nformat on for pasted  d a.
 *
 * Long-term   w ll move t  log c to t  wr e path and backf ll t  f eld for old T ets.
 */
object  d aRefsHydrator {
  type Type = ValueHydrator[Opt on[Seq[ d aRef]], Ctx]

  case class Ctx(
     d a: Seq[ d aEnt y],
     d aKeys: Seq[ d aKey],
    urlEnt  es: Seq[UrlEnt y],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy {
    def  ncludePasted d a: Boolean = opts. nclude.pasted d a
  }

  val hydratedF eld: F eldByPath = f eldByPath(T et. d aRefsF eld)

  def  d aKeyTo d aRef( d aKey:  d aKey):  d aRef =
     d aRef(
      gener c d aKey = Gener c d aKey( d aKey).toStr ngKey()
    )

  // Convert a pasted T et  nto a Seq of  d aRef from that T et w h t  correct s ceT et d and s ceUser d
  def pastedT etTo d aRefs(
    t et: T et
  ): Seq[ d aRef] =
    t et. d aRefs.toSeq.flatMap {  d aRefs =>
       d aRefs.map(
        _.copy(
          s ceT et d = So (t et. d),
          s ceUser d = So (getUser d(t et))
        ))
    }

  // Fetch  d aRefs from pasted  d a T et URLs  n t  T et text
  def getPasted d aRefs(
    repo: T etRepos ory.Opt onal,
    ctx: Ctx,
     ncludePasted d a: Gate[Un ]
  ): St ch[Seq[ d aRef]] = {
     f ( ncludePasted d a() && ctx. ncludePasted d a) {

      // Extract T et  ds from pasted  d a permal nks  n t  T et text
      val pasted d aT et ds: Seq[T et d] =
        Pasted d aHydrator.pasted dsAndEnt  es(ctx.t et d, ctx.urlEnt  es).map(_._1)

      val opts = T etQuery.Opt ons(
         nclude = T etQuery. nclude(
          t etF elds = Set(T et.CoreDataF eld. d, T et. d aRefsF eld. d),
          pasted d a = false // don't recurs vely load pasted  d a refs
        ))

      // Load a Seq of T ets w h pasted  d a,  gnor ng any returned w h NotFound or a F lteredState
      val pastedT ets: St ch[Seq[T et]] = St ch
        .traverse(pasted d aT et ds) {  d =>
          repo( d, opts)
        }.map(_.flatten)

      pastedT ets.map(_.flatMap(pastedT etTo d aRefs))
    } else {
      St ch.N l
    }
  }

  // Make empty Seq None and non-empty Seq So (Seq(...)) to comply w h t  thr ft f eld type
  def opt onal zeSeq( d aRefs: Seq[ d aRef]): Opt on[Seq[ d aRef]] =
    So ( d aRefs).f lterNot(_. sEmpty)

  def apply(
    repo: T etRepos ory.Opt onal,
     ncludePasted d a: Gate[Un ]
  ): Type = {
    ValueHydrator[Opt on[Seq[ d aRef]], Ctx] { (curr, ctx) =>
      // Fetch  d aRefs from T et  d a
      val stored d aRefs: Seq[ d aRef] = ctx. d a.map {  d aEnt y =>
        // Use  d aKeyHydrator. nfer to determ ne t   d a key from t   d a ent y
        val  d aKey =  d aKeyHydrator. nfer(So (ctx. d aKeys),  d aEnt y)
         d aKeyTo d aRef( d aKey)
      }

      // Fetch  d aRefs from pasted  d a
      getPasted d aRefs(repo, ctx,  ncludePasted d a).l ftToTry.map {
        case Return(pasted d aRefs) =>
          // Comb ne t  refs from t  T et's own  d a and those from pasted  d a, t n l m 
          // to Max d aEnt  esPerT et.
          val l m edRefs =
            (stored d aRefs ++ pasted d aRefs).take(Pasted d aHydrator.Max d aEnt  esPerT et)

          ValueState.delta(curr, opt onal zeSeq(l m edRefs))
        case Throw(_) =>
          ValueState.part al(opt onal zeSeq(stored d aRefs), hydratedF eld)
      }

    }.only f { (_, ctx) =>
      ctx.t etF eldRequested(T et. d aRefsF eld) ||
      ctx.opts.safetyLevel != SafetyLevel.F lterNone
    }
  }
}
