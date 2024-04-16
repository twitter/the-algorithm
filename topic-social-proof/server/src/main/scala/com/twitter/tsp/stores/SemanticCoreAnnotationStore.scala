package com.tw ter.tsp.stores

 mport com.tw ter.esc rb rd.top cannotat on.strato.thr ftscala.Top cAnnotat onValue
 mport com.tw ter.esc rb rd.top cannotat on.strato.thr ftscala.Top cAnnotat onV ew
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.s mclusters_v2.common.Top c d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.ut l.Future

/**
 * T   s cop ed from `src/scala/com/tw ter/top c_recos/stores/Semant cCoreAnnotat onStore.scala`
 * Unfortunately t  r vers on assu s ( ncorrectly) that t re  s no V ew wh ch causes warn ngs.
 * Wh le t se warn ngs may not cause any problems  n pract ce, better safe than sorry.
 */
object Semant cCoreAnnotat onStore {
  pr vate val column = "semant cCore/top cannotat on/top cAnnotat on.T et"

  def getStratoStore(stratoCl ent: Cl ent): ReadableStore[T et d, Top cAnnotat onValue] = {
    StratoFetchableStore
      .w hV ew[T et d, Top cAnnotat onV ew, Top cAnnotat onValue](
        stratoCl ent,
        column,
        Top cAnnotat onV ew())
  }

  case class Top cAnnotat on(
    top c d: Top c d,
     gnoreS mClustersF lter: Boolean,
    modelVers on d: Long)
}

/**
 * G ven a t et  d, return t  l st of annotat ons def ned by t  TS G team.
 */
case class Semant cCoreAnnotat onStore(stratoStore: ReadableStore[T et d, Top cAnnotat onValue])
    extends ReadableStore[T et d, Seq[Semant cCoreAnnotat onStore.Top cAnnotat on]] {
   mport Semant cCoreAnnotat onStore._

  overr de def mult Get[K1 <: T et d](
    ks: Set[K1]
  ): Map[K1, Future[Opt on[Seq[Top cAnnotat on]]]] = {
    stratoStore
      .mult Get(ks)
      .mapValues(_.map(_.map { top cAnnotat onValue =>
        top cAnnotat onValue.annotat onsPerModel match {
          case So (annotat onW hVers ons) =>
            annotat onW hVers ons.flatMap { annotat ons =>
              annotat ons.annotat ons.map { annotat on =>
                Top cAnnotat on(
                  annotat on.ent y d,
                  annotat on. gnoreQual yF lter.getOrElse(false),
                  annotat ons.modelVers on d
                )
              }
            }
          case _ =>
            N l
        }
      }))
  }
}
