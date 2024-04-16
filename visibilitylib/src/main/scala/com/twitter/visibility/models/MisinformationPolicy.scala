package com.tw ter.v s b l y.models

 mport com.tw ter.datatools.ent yserv ce.ent  es.thr ftscala.Fleet nterst  al
 mport com.tw ter.datatools.ent yserv ce.ent  es.{thr ftscala => t}
 mport com.tw ter.esc rb rd.soft ntervent on.thr ftscala.M s nformat onLocal zedPol cy
 mport com.tw ter.esc rb rd.thr ftscala.T etEnt yAnnotat on

case class M s nformat onPol cy(
  semant cCoreAnnotat on: Semant cCoreAnnotat on,
  pr or y: Long = M s nformat onPol cy.DefaultPr or y,
  f lter ngLevel:  nt = M s nformat onPol cy.DefaultF lter ngLevel,
  publ s dState: Publ s dState = M s nformat onPol cy.DefaultPubl s dState,
  engage ntNudge: Boolean = M s nformat onPol cy.DefaultEngage ntNudge,
  suppressAutoplay: Boolean = M s nformat onPol cy.DefaultSuppressAutoplay,
  warn ng: Opt on[Str ng] = None,
  deta lsUrl: Opt on[Str ng] = None,
  d splayType: Opt on[M s nfoPol cyD splayType] = None,
  appl cableCountr es: Seq[Str ng] = Seq.empty,
  fleet nterst  al: Opt on[Fleet nterst  al] = None)

object M s nformat onPol cy {
  pr vate val DefaultPr or y = 0
  pr vate val DefaultF lter ngLevel = 1
  pr vate val DefaultPubl s dState = Publ s dState.Publ s d
  pr vate val DefaultEngage ntNudge = true
  pr vate val DefaultSuppressAutoplay = true

  def apply(
    annotat on: T etEnt yAnnotat on,
    m s nformat on: M s nformat onLocal zedPol cy
  ): M s nformat onPol cy = {
    M s nformat onPol cy(
      semant cCoreAnnotat on = Semant cCoreAnnotat on(
        group d = annotat on.group d,
        doma n d = annotat on.doma n d,
        ent y d = annotat on.ent y d
      ),
      pr or y = m s nformat on.pr or y.getOrElse(DefaultPr or y),
      f lter ngLevel = m s nformat on.f lter ngLevel.getOrElse(DefaultF lter ngLevel),
      publ s dState = m s nformat on.publ s dState match {
        case So (t.Publ s dState.Draft) => Publ s dState.Draft
        case So (t.Publ s dState.Dogfood) => Publ s dState.Dogfood
        case So (t.Publ s dState.Publ s d) => Publ s dState.Publ s d
        case _ => DefaultPubl s dState
      },
      d splayType = m s nformat on.d splayType collect {
        case t.M s nformat onD splayType.GetT Latest => M s nfoPol cyD splayType.GetT Latest
        case t.M s nformat onD splayType.Stay nfor d => M s nfoPol cyD splayType.Stay nfor d
        case t.M s nformat onD splayType.M slead ng => M s nfoPol cyD splayType.M slead ng
        case t.M s nformat onD splayType.Govern ntRequested =>
          M s nfoPol cyD splayType.Govern ntRequested
      },
      appl cableCountr es = m s nformat on.appl cableCountr es match {
        case So (countr es) => countr es.map(countryCode => countryCode.toLo rCase)
        case _ => Seq.empty
      },
      fleet nterst  al = m s nformat on.fleet nterst  al,
      engage ntNudge = m s nformat on.engage ntNudge.getOrElse(DefaultEngage ntNudge),
      suppressAutoplay = m s nformat on.suppressAutoplay.getOrElse(DefaultSuppressAutoplay),
      warn ng = m s nformat on.warn ng,
      deta lsUrl = m s nformat on.deta lsUrl,
    )
  }
}

tra  M s nformat onPol cyTransform {
  def apply(pol c es: Seq[M s nformat onPol cy]): Seq[M s nformat onPol cy]
  def andT n(transform: M s nformat onPol cyTransform): M s nformat onPol cyTransform =
    (pol c es: Seq[M s nformat onPol cy]) => transform(t .apply(pol c es))
}

object M s nformat onPol cyTransform {

  def pr or  ze: M s nformat onPol cyTransform =
    (pol c es: Seq[M s nformat onPol cy]) =>
      pol c es
        .sortBy(p => p.f lter ngLevel)(Order ng[ nt].reverse)
        .sortBy(p => p.pr or y)(Order ng[Long].reverse)

  def f lter(f lters: Seq[M s nformat onPol cy => Boolean]): M s nformat onPol cyTransform =
    (pol c es: Seq[M s nformat onPol cy]) =>
      pol c es.f lter { pol cy => f lters.forall { f lter => f lter(pol cy) } }

  def f lterLevelAndState(
    f lter ngLevel:  nt,
    publ s dStates: Seq[Publ s dState]
  ): M s nformat onPol cyTransform =
    f lter(
      Seq(
        hasF lter ngLevelAtLeast(f lter ngLevel),
        hasPubl s dStates(publ s dStates)
      ))

  def f lterLevelAndStateAndLocal zed(
    f lter ngLevel:  nt,
    publ s dStates: Seq[Publ s dState]
  ): M s nformat onPol cyTransform =
    f lter(
      Seq(
        hasF lter ngLevelAtLeast(f lter ngLevel),
        hasPubl s dStates(publ s dStates),
        hasNonEmptyLocal zat on,
      ))

  def f lterState(
    publ s dStates: Seq[Publ s dState]
  ): M s nformat onPol cyTransform =
    f lter(
      Seq(
        hasPubl s dStates(publ s dStates)
      ))

  def f lterStateAndLocal zed(
    publ s dStates: Seq[Publ s dState]
  ): M s nformat onPol cyTransform =
    f lter(
      Seq(
        hasPubl s dStates(publ s dStates),
        hasNonEmptyLocal zat on,
      ))

  def f lterAppl cableCountr es(
    countryCode: Opt on[Str ng],
  ): M s nformat onPol cyTransform =
    f lter(Seq(pol cyAppl esToCountry(countryCode)))

  def f lterOutGeoSpec f c(): M s nformat onPol cyTransform =
    f lter(Seq(pol cy sGlobal()))

  def f lterNonEngage ntNudges(): M s nformat onPol cyTransform =
    f lter(
      Seq(
        hasEngage ntNudge,
      ))

  def pol cyAppl esToCountry(countryCode: Opt on[Str ng]): M s nformat onPol cy => Boolean =
    pol cy =>
      pol cy.appl cableCountr es. sEmpty ||
        (countryCode.nonEmpty && pol cy.appl cableCountr es.conta ns(countryCode.get))

  def pol cy sGlobal(): M s nformat onPol cy => Boolean =
    pol cy => pol cy.appl cableCountr es. sEmpty

  def hasF lter ngLevelAtLeast(f lter ngLevel:  nt): M s nformat onPol cy => Boolean =
    _.f lter ngLevel >= f lter ngLevel

  def hasPubl s dStates(
    publ s dStates: Seq[Publ s dState]
  ): M s nformat onPol cy => Boolean =
    pol cy => publ s dStates.conta ns(pol cy.publ s dState)

  def hasNonEmptyLocal zat on: M s nformat onPol cy => Boolean =
    pol cy => pol cy.warn ng.nonEmpty && pol cy.deta lsUrl.nonEmpty

  def hasEngage ntNudge: M s nformat onPol cy => Boolean =
    pol cy => pol cy.engage ntNudge

}

sealed tra  Publ s dState
object Publ s dState {
  case object Draft extends Publ s dState
  case object Dogfood extends Publ s dState
  case object Publ s d extends Publ s dState

  val Publ cPubl s dStates = Seq(Publ s dState.Publ s d)
  val EmployeePubl s dStates = Seq(Publ s dState.Publ s d, Publ s dState.Dogfood)
}
sealed tra  M s nfoPol cyD splayType
object M s nfoPol cyD splayType {
  case object GetT Latest extends M s nfoPol cyD splayType
  case object Stay nfor d extends M s nfoPol cyD splayType
  case object M slead ng extends M s nfoPol cyD splayType
  case object Govern ntRequested extends M s nfoPol cyD splayType
}

object Semant cCoreM s nformat on {
  val doma n d: Long = 148L
}
