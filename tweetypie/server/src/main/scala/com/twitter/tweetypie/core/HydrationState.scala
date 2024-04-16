package com.tw ter.t etyp e.core

 mport com.tw ter.t etyp e.thr ftscala.F eldByPath
 mport com.tw ter.t etyp e.thr ftscala.Hydrat onType

/**
 * Hydrat onState  s used to record w t r a part cular p ece of data was mod f ed as a result
 * of hydrat on, and/or  f t re was a fa lure to hydrate t  data.
 */
sealed tra  Hydrat onState {
  def  sEmpty: Boolean
  def mod f ed: Boolean
  def completedHydrat ons: Set[Hydrat onType] = Set.empty
  def fa ledF elds: Set[F eldByPath] = Set.empty
  def cac ErrorEncountered: Boolean = false
  def ++(that: Hydrat onState): Hydrat onState
}

object Hydrat onState {

  /**
   * Base `Hydrat onState`.    acts as an  dent y value w n comb ned w h any ot r
   * `Hydrat onState`.
   */
  case object Empty extends Hydrat onState {
    def  sEmpty = true
    def mod f ed = false
    def ++(that: Hydrat onState): Hydrat onState = that
  }

  /**
   * A `Hydrat onState` w h  tadata  nd cat ng a non-fatal hydrat on operat on.
   */
  case class Success(
    overr de val mod f ed: Boolean = false,
    overr de val completedHydrat ons: Set[Hydrat onType] = Set.empty,
    overr de val fa ledF elds: Set[F eldByPath] = Set.empty,
    overr de val cac ErrorEncountered: Boolean = false)
      extends Hydrat onState {

    def  sEmpty: Boolean = !mod f ed && fa ledF elds. sEmpty && !cac ErrorEncountered

    def ++(that: Hydrat onState): Hydrat onState =
      that match {
        case Empty => t 
        case that: Success =>
          Hydrat onState(
            mod f ed || that.mod f ed,
            completedHydrat ons ++ that.completedHydrat ons,
            fa ledF elds ++ that.fa ledF elds,
            cac ErrorEncountered || that.cac ErrorEncountered
          )
      }

    /**
     * An  mple ntat on of `copy` that avo ds unnecessary allocat ons, by
     * us ng t  constant `Hydrat onState.unmod f ed` and `Hydrat onState.mod f ed`
     * values w n poss ble.
     */
    def copy(
      mod f ed: Boolean = t .mod f ed,
      completedHydrat ons: Set[Hydrat onType] = t .completedHydrat ons,
      fa ledF elds: Set[F eldByPath] = t .fa ledF elds,
      cac ErrorEncountered: Boolean = t .cac ErrorEncountered
    ): Hydrat onState =
      Hydrat onState(mod f ed, completedHydrat ons, fa ledF elds, cac ErrorEncountered)
  }

  val empty: Hydrat onState = Empty
  val mod f ed: Hydrat onState = Success(true)

  def mod f ed(completedHydrat on: Hydrat onType): Hydrat onState =
    mod f ed(Set(completedHydrat on))

  def mod f ed(completedHydrat ons: Set[Hydrat onType]): Hydrat onState =
    Success(mod f ed = true, completedHydrat ons = completedHydrat ons)

  def part al(fa ledF eld: F eldByPath): Hydrat onState =
    part al(Set(fa ledF eld))

  def part al(fa ledF elds: Set[F eldByPath]): Hydrat onState =
    Success(mod f ed = false, fa ledF elds = fa ledF elds)

  def apply(
    mod f ed: Boolean,
    completedHydrat ons: Set[Hydrat onType] = Set.empty,
    fa ledF elds: Set[F eldByPath] = Set.empty,
    cac ErrorEncountered: Boolean = false
  ): Hydrat onState =
     f (completedHydrat ons.nonEmpty || fa ledF elds.nonEmpty || cac ErrorEncountered) {
      Success(mod f ed, completedHydrat ons, fa ledF elds, cac ErrorEncountered)
    } else  f (mod f ed) {
      Hydrat onState.mod f ed
    } else {
      Hydrat onState.empty
    }

  /**
   * Creates a new Hydrat onState w h mod f ed set to true  f `next` and `prev` are d fferent,
   * or false  f t y are t  sa .
   */
  def delta[A](prev: A, next: A): Hydrat onState =
     f (next != prev) mod f ed else empty

  /**
   * Jo n a l st of Hydrat onStates  nto a s ngle Hydrat onState.
   *
   * Note: t  could just be a reduce over t  Hydrat onStates but that would allocate
   * _N_ Hydrat onStates. T  approach also allows for shortc rcu  ng over t  boolean
   * f elds.
   */
  def jo n(states: Hydrat onState*): Hydrat onState = {
    val statesSet = states.toSet

    Hydrat onState(
      mod f ed = states.ex sts(_.mod f ed),
      completedHydrat ons = statesSet.flatMap(_.completedHydrat ons),
      fa ledF elds = statesSet.flatMap(_.fa ledF elds),
      cac ErrorEncountered = states.ex sts(_.cac ErrorEncountered)
    )
  }
}
