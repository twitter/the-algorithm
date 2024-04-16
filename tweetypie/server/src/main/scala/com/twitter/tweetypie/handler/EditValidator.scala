package com.tw ter.t etyp e
package handler

 mport com.tw ter.scrooge.sc ma.scrooge.scala.Comp ledScroogeDefBu lder
 mport com.tw ter.scrooge.sc ma.scrooge.scala.Comp ledScroogeValueExtractor
 mport com.tw ter.scrooge.sc ma.tree.Def n  onTraversal
 mport com.tw ter.scrooge.sc ma.tree.F eldPath
 mport com.tw ter.scrooge.sc ma.{Thr ftDef n  ons => DEF}
 mport com.tw ter.scrooge_ nternal.l nter.known_annotat ons.Allo dAnnotat onKeys.T etEd Allo d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.repos ory.T etQuery.Opt ons
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState.F eldEd NotAllo d
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState. n  alT etNotFound
 mport com.tw ter.t etyp e.thr ftscala.Ed Opt ons
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.logg ng.Logger

/**
 * T  class constructs a val dator `T et => Future[Un ]` wh ch
 * takes a new ed  t et and performs so  val dat ons. Spec f cally,  
 *
 * 1) ensures that no uned able f elds  re ed ed. Uned able f elds are marked
 * on t  t et.thr ft us ng t  thr ft annotat on "t etEd Allo d=false".
 * By default, f elds w h no annotat on are treated as ed able.
 *
 * 2) ensures that t  conversat onControl f eld (wh ch  s ed able) rema ns t 
 * sa  type, e.g. a Conversat onControl.By nv at on doesn't change to a
 * Conversat onControl.Commun y.
 *
 *  f e  r of t se val dat ons fa l, t  val dator fa ls w h a `F eldEd NotAllo d`
 * t et create state.
 */
object Ed Val dator {
  type Type = (T et, Opt on[Ed Opt ons]) => Future[Un ]

  val log: Logger = Logger(getClass)

  // An object that descr bes t  t et thr ft, used to walk a t et object look ng
  // for annotated f elds.
  val T etDef = Comp ledScroogeDefBu lder.bu ld[T et].as nstanceOf[DEF.StructDef]

  // Collect t  `F eldPath` for any nested t et f eld w h a uned able f eld annotat on
  // that  s set to false. T se are t  f elds that t  val dator ensures cannot be ed ed.
  val uned ableF eldPaths: Seq[F eldPath] = {
    Def n  onTraversal().collect(T etDef) {
      case (d: DEF.F eldDef, path)  f (d.annotat ons.get(T etEd Allo d).conta ns("false")) =>
        path
    }
  }

  // A t et query opt ons wh ch  ncludes
  // - any top level t et f eld wh ch e  r  s an uned able f eld, or conta ns an uned able
  //   subf eld.
  // - t  conversat onControl f eld
  // T se f elds must be present on t   n  al t et  n order for us to compare t m aga nst t 
  // ed  t et.
  val prev ousT etQueryOpt ons = {
    // A set of t  top level f eld  ds for each (potent ally nested) uned able f eld.
    val topLevelUned ableT etF elds = uned ableF eldPaths.map(_. ds. ad).toSet
    Opt ons(
      T etQuery. nclude(
        t etF elds = topLevelUned ableT etF elds + T et.Conversat onControlF eld. d
      ))
  }

  def val dateUned ableF elds(prev ousT et: T et, ed T et: T et): Un  = {
    // Collect uned able f elds that  re ed ed
    val  nval dEd edF elds = uned ableF eldPaths.flatMap { f eldPath =>
      val prev ousValue =
        F eldPath.lensGet(Comp ledScroogeValueExtractor, prev ousT et, f eldPath)
      val ed Value = F eldPath.lensGet(Comp ledScroogeValueExtractor, ed T et, f eldPath)

       f (prev ousValue != ed Value) {
        So (f eldPath.toStr ng)
      } else {
        None
      }
    }

     f ( nval dEd edF elds.nonEmpty) {
      //  f any  nequal  es are found, log t m and return an except on.
      val msg = "uned able f elds  re ed ed: " +  nval dEd edF elds.mkStr ng(",")
      log.error(msg)
      throw T etCreateFa lure.State(F eldEd NotAllo d, So (msg))
    }
  }

  def val dateConversat onControl(
    prev ous: Opt on[Conversat onControl],
    ed : Opt on[Conversat onControl]
  ): Un  = {
     mport Conversat onControl.By nv at on
     mport Conversat onControl.Commun y
     mport Conversat onControl.Follo rs

    (prev ous, ed ) match {
      case (None, None) => ()
      case (So (By nv at on(_)), So (By nv at on(_))) => ()
      case (So (Commun y(_)), So (Commun y(_))) => ()
      case (So (Follo rs(_)), So (Follo rs(_))) => ()
      case (_, _) =>
        val msg = "conversat onControl type was ed ed"
        log.error(msg)
        throw T etCreateFa lure.State(F eldEd NotAllo d, So (msg))
    }
  }

  def apply(t etRepo: T etRepos ory.Opt onal): Type = { (t et, ed Opt ons) =>
    St ch.run(
      ed Opt ons match {
        case So (Ed Opt ons(prev ousT et d)) => {
          // Query for t  prev ous t et so that   can compare t 
          // f elds bet en t  two t ets.
          t etRepo(prev ousT et d, prev ousT etQueryOpt ons).map {
            case So (prev ousT et) =>
              val dateUned ableF elds(prev ousT et, t et)
              val dateConversat onControl(
                prev ousT et.conversat onControl,
                t et.conversat onControl)
            case _ =>
              //  f t  prev ous t et  s not found   cannot perform val dat ons that
              // compare t et f elds and   have to fa l t et creat on.
              throw T etCreateFa lure.State( n  alT etNotFound)
          }
        }
        // T   s t  case w re t   sn't an ed  t et (s nce ed Opt ons = None)
        // S nce t  t et  s not an ed  t re are no f elds to val date.
        case _ => St ch.Un 
      }
    )
  }
}
