package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.repos ory.T etQuery

/**
 * An  nstance of `T etQueryOpt onsExpander.Type` can be used to take a `T etQuery.Opt ons`
 *  nstance prov ded by a user, and expand t  set of opt ons  ncluded to take  nto account
 * dependenc es bet en f elds and opt ons.
 */
object T etQueryOpt onsExpander {
   mport T etQuery._

  /**
   * Used by Add  onalF eldsHydrator, t  funct on type can f lter out or  nject f eld ds to
   * request from Manhattan per t et.
   */
  type Type = Opt ons => Opt ons

  /**
   * T   dent y T etQueryOpt onsExpander, wh ch passes through f eld ds unchanged.
   */
  val un : T etQueryOpt onsExpander.Type =  dent y

  case class Selector(f:  nclude => Boolean) {
    def apply( :  nclude): Boolean = f( )

    def ||(ot r: Selector) = Selector(  => t ( ) || ot r( ))
  }

  pr vate def selectT etF eld(f eld d: F eld d): Selector =
    Selector(_.t etF elds.conta ns(f eld d))

  pr vate val f rstOrderDependenc es: Seq[(Selector,  nclude)] =
    Seq(
      selectT etF eld(T et. d aF eld. d) ->
         nclude(t etF elds = Set(T et.UrlsF eld. d, T et. d aKeysF eld. d)),
      selectT etF eld(T et.QuotedT etF eld. d) ->
         nclude(t etF elds = Set(T et.UrlsF eld. d)),
      selectT etF eld(T et. d aRefsF eld. d) ->
         nclude(t etF elds = Set(T et.UrlsF eld. d, T et. d aKeysF eld. d)),
      selectT etF eld(T et.CardsF eld. d) ->
         nclude(t etF elds = Set(T et.UrlsF eld. d)),
      selectT etF eld(T et.Card2F eld. d) ->
         nclude(t etF elds = Set(T et.UrlsF eld. d, T et.CardReferenceF eld. d)),
      selectT etF eld(T et.CoreDataF eld. d) ->
         nclude(t etF elds = Set(T et.D rectedAtUser tadataF eld. d)),
      selectT etF eld(T et.SelfThread nfoF eld. d) ->
         nclude(t etF elds = Set(T et.CoreDataF eld. d)),
      (selectT etF eld(T et.TakedownCountryCodesF eld. d) ||
        selectT etF eld(T et.TakedownReasonsF eld. d)) ->
         nclude(
          t etF elds = Set(
            T et.T etyp eOnlyTakedownCountryCodesF eld. d,
            T et.T etyp eOnlyTakedownReasonsF eld. d
          )
        ),
      selectT etF eld(T et.Ed Perspect veF eld. d) ->
         nclude(t etF elds = Set(T et.Perspect veF eld. d)),
      Selector(_.quotedT et) ->
         nclude(t etF elds = Set(T et.QuotedT etF eld. d)),
      // ask ng for any count  mpl es gett ng t  T et.counts f eld
      Selector(_.countsF elds.nonEmpty) ->
         nclude(t etF elds = Set(T et.CountsF eld. d)),
      // ask ng for any  d a f eld  mpl es gett ng t  T et. d a f eld
      Selector(_. d aF elds.nonEmpty) ->
         nclude(t etF elds = Set(T et. d aF eld. d)),
      selectT etF eld(T et.Un nt onDataF eld. d) ->
         nclude(t etF elds = Set(T et. nt onsF eld. d)),
    )

  pr vate val allDependenc es =
    f rstOrderDependenc es.map {
      case (sel,  nc) => sel -> trans  veExpand( nc)
    }

  pr vate def trans  veExpand( nc:  nclude):  nclude =
    f rstOrderDependenc es.foldLeft( nc) {
      case (z, (selector,  nclude)) =>
         f (!selector(z)) z
        else z ++  nclude ++ trans  veExpand( nclude)
    }

  /**
   * Sequent ally composes mult ple T etQueryOpt onsExpander  nto a new T etQueryOpt onsExpander
   */
  def sequent ally(updaters: T etQueryOpt onsExpander.Type*): T etQueryOpt onsExpander.Type =
    opt ons =>
      updaters.foldLeft(opt ons) {
        case (opt ons, updater) => updater(opt ons)
      }

  /**
   * For requested f elds that depend on ot r f elds be ng present for correct hydrat on,
   * returns an updated `T etQuery.Opt ons` w h those dependee f elds  ncluded.
   */
  def expandDependenc es: T etQueryOpt onsExpander.Type =
    opt ons =>
      opt ons.copy(
         nclude = allDependenc es.foldLeft(opt ons. nclude) {
          case (z, (selector,  nclude)) =>
             f (!selector(opt ons. nclude)) z
            else z ++  nclude
        }
      )

  /**
   *  f t  gate  s true, add 'f elds' to t  l st of t etF elds to load.
   */
  def gatedT etF eldUpdater(
    gate: Gate[Un ],
    f elds: Seq[F eld d]
  ): T etQueryOpt onsExpander.Type =
    opt ons =>
       f (gate()) {
        opt ons.copy(
           nclude = opt ons. nclude.also(t etF elds = f elds)
        )
      } else {
        opt ons
      }

  /**
   * Uses a `ThreadLocal` to re mber t  last expans on perfor d, and to reuse t 
   * prev ous result  f t   nput value  s t  sa .  T   s useful to avo d repeatedly
   * comput ng t  expans on of t  sa   nput w n mult ple t ets are quer ed toget r
   * w h t  sa  opt ons.
   */
  def threadLocal mo ze(expander: Type): Type = {
    val  mo: ThreadLocal[Opt on[(Opt ons, Opt ons)]] =
      new ThreadLocal[Opt on[(Opt ons, Opt ons)]] {
        overr de def  n  alValue(): None.type = None
      }

    opt ons =>
       mo.get() match {
        case So ((`opt ons`, res)) => res
        case _ =>
          val res = expander(opt ons)
           mo.set(So ((opt ons, res)))
          res
      }
  }
}
