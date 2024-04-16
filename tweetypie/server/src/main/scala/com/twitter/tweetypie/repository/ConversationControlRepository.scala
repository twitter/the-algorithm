package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.T etDeleted
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl

/**
 * T  repos ory loads up t  conversat on control values for a t et wh ch controls who can reply
 * to a t et. Because t  conversat on control values are stored on t  root t et of a conversat on,
 *   need to make sure that t  code  s able to load t  data from t  root t et. To ensure t ,
 * no v s b l y f lter ng opt ons are set on t  query to load t  root t et f elds.
 *
 *  f v s b l y f lter ng was enabled, and t  root t et was f ltered for t  request ng user,
 * t n t  conversat on control data would not be returned and enforce nt would effect vely be
 * s de-stepped.
 */
object Conversat onControlRepos ory {
  pr vate[t ] val log = Logger(getClass)
  type Type = (T et d, Cac Control) => St ch[Opt on[Conversat onControl]]

  def apply(repo: T etRepos ory.Type, stats: StatsRece ver): Type =
    (conversat on d: T et d, cac Control: Cac Control) => {
      val opt ons = T etQuery.Opt ons(
         nclude = T etQuery. nclude(Set(T et.Conversat onControlF eld. d)),
        //   want t  root t et of a conversat on that  're look ng up to be
        // cac d w h t  sa  pol cy as t  t et  're look ng up.
        cac Control = cac Control,
        enforceV s b l yF lter ng = false,
        safetyLevel = SafetyLevel.F lterNone
      )

      repo(conversat on d, opt ons)
        .map(rootT et => rootT et.conversat onControl)
        .handle {
          //   don't know of any case w re t ets would return NotFound, but for
          // for pragmat c reasons,  're open ng t  conversat on for repl es
          //  n case a bug caus ng t ets to be NotFound ex sts.
          case NotFound =>
            stats.counter("t et_not_found")
            None
          //  f no root t et  s found, t  reply has no conversat on controls
          // t   s by des gn, delet ng t  root t et "opens" t  conversat on
          case T etDeleted =>
            stats.counter("t et_deleted")
            None
        }
    }
}
