package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.serverut l.ExtendedT et tadataBu lder
 mport com.tw ter.t etyp e.t ettext.Preprocessor._
 mport com.tw ter.t etyp e.t ettext.TextMod f cat on
 mport com.tw ter.t etyp e.thr ftscala.ent  es. mpl c s._

object TextRepa rer {
  def apply(replace: Str ng => Opt on[TextMod f cat on]): Mutat on[T et] =
    Mutat on { t et =>
      replace(getText(t et)).map { mod =>
        val repa redT et = t et.copy(
          coreData = t et.coreData.map(c => c.copy(text = mod.updated)),
          urls = So (getUrls(t et).flatMap(mod.re ndexEnt y(_))),
           nt ons = So (get nt ons(t et).flatMap(mod.re ndexEnt y(_))),
          hashtags = So (getHashtags(t et).flatMap(mod.re ndexEnt y(_))),
          cashtags = So (getCashtags(t et).flatMap(mod.re ndexEnt y(_))),
           d a = So (get d a(t et).flatMap(mod.re ndexEnt y(_))),
          v s bleTextRange = t et.v s bleTextRange.flatMap(mod.re ndexEnt y(_))
        )

        val repa redExtendedT et tadata = repa redT et.selfPermal nk.flatMap { permal nk =>
          val extendedT et tadata = ExtendedT et tadataBu lder(repa redT et, permal nk)
          val repa redTextLength = getText(repa redT et).length
           f (extendedT et tadata.ap Compat bleTruncat on ndex == repa redTextLength) {
            None
          } else {
            So (extendedT et tadata)
          }
        }

        repa redT et.copy(extendedT et tadata = repa redExtendedT et tadata)
      }
    }

  /**
   * Removes wh espace from t  t et, and updates all ent y  nd ces.
   */
  val BlankL neCollapser: Mutat on[T et] = TextRepa rer(collapseBlankL nesMod f cat on _)

  /**
   * Replace a spec al un code str ng that cras s  os app w h '\ufffd'
   */
  val CoreTextBugPatc r: Mutat on[T et] = TextRepa rer(replaceCoreTextBugMod f cat on _)

}
