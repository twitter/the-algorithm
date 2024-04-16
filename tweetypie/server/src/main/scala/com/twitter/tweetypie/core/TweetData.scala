package com.tw ter.t etyp e
package core

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.t etyp e.thr ftscala._

object T etData {
  object Lenses {
    val t et: Lens[T etData, T et] = Lens[T etData, T et](_.t et, _.copy(_))

    val suppress: Lens[T etData, Opt on[F lteredState.Suppress]] =
      Lens[T etData, Opt on[F lteredState.Suppress]](
        _.suppress,
        (td, suppress) => td.copy(suppress = suppress)
      )

    val s ceT etResult: Lens[T etData, Opt on[T etResult]] =
      Lens[T etData, Opt on[T etResult]](
        _.s ceT etResult,
        (td, s ceT etResult) => td.copy(s ceT etResult = s ceT etResult)
      )

    val quotedT etResult: Lens[T etData, Opt on[QuotedT etResult]] =
      Lens[T etData, Opt on[QuotedT etResult]](
        _.quotedT etResult,
        (td, quotedT etResult) => td.copy(quotedT etResult = quotedT etResult)
      )

    val cac ableT etResult: Lens[T etData, Opt on[T etResult]] =
      Lens[T etData, Opt on[T etResult]](
        _.cac ableT etResult,
        (td, cac ableT etResult) => td.copy(cac ableT etResult = cac ableT etResult)
      )

    val t etCounts: Lens[T etData, Opt on[StatusCounts]] =
      Lens[T etData, Opt on[StatusCounts]](
        _.t et.counts,
        (td, t etCounts) => td.copy(t et = td.t et.copy(counts = t etCounts))
      )
  }

  def fromCac dT et(cac dT et: Cac dT et, cac dAt: T  ): T etData =
    T etData(
      t et = cac dT et.t et,
      completedHydrat ons = cac dT et.completedHydrat ons.toSet,
      cac dAt = So (cac dAt),
       sBounceDeleted = cac dT et. sBounceDeleted.conta ns(true)
    )
}

/**
 * Encapsulates a t et and so  hydrat on  tadata  n t  hydrat on p pel ne.
 *
 * @param cac dAt  f t  t et was read from cac , `cac dAt` conta ns t  t   at wh ch
 * t  t et was wr ten to cac .
 */
case class T etData(
  t et: T et,
  suppress: Opt on[F lteredState.Suppress] = None,
  completedHydrat ons: Set[Hydrat onType] = Set.empty,
  cac dAt: Opt on[T  ] = None,
  s ceT etResult: Opt on[T etResult] = None,
  quotedT etResult: Opt on[QuotedT etResult] = None,
  cac ableT etResult: Opt on[T etResult] = None,
  storedT etResult: Opt on[StoredT etResult] = None,
  featureSw chResults: Opt on[FeatureSw chResults] = None,
  // T   sBounceDeleted flag  s only used w n read ng from an underly ng
  // t et repo and cach ng records for not-found t ets.   only ex sts
  // as a flag on T etData to marshal bounce-deleted through t  layered
  // transform ng cac s  njected  nto Cach ngT etRepos ory, ult mately
  // stor ng t  flag  n thr ft on Cac dT et.
  //
  // Dur ng t et hydrat on, T etData. sBounceDeleted  s unused and
  // should always be false.
   sBounceDeleted: Boolean = false) {

  def addHydrated(f eld ds: Set[Hydrat onType]): T etData =
    copy(completedHydrat ons = completedHydrat ons ++ f eld ds)

  def toCac dT et: Cac dT et =
    Cac dT et(
      t et = t et,
      completedHydrat ons = completedHydrat ons,
       sBounceDeleted =  f ( sBounceDeleted) So (true) else None
    )
}
