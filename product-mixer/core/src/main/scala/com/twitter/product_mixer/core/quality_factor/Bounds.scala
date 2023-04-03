packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

/**
 * Providelons a way to apply inclusivelon min/max bounds to a givelonn valuelon.
 */
caselon class Bounds[T](minInclusivelon: T, maxInclusivelon: T)(implicit ordelonring: Ordelonring[T]) {

  delonf apply(valuelon: T): T = ordelonring.min(maxInclusivelon, ordelonring.max(minInclusivelon, valuelon))

  delonf isWithin(valuelon: T): Boolelonan =
    ordelonring.gtelonq(valuelon, minInclusivelon) && ordelonring.ltelonq(valuelon, maxInclusivelon)

  delonf throwIfOutOfBounds(valuelon: T, melonssagelonPrelonfix: String): Unit =
    relonquirelon(isWithin(valuelon), s"$melonssagelonPrelonfix: valuelon must belon within $toString")

  ovelonrridelon delonf toString: String = s"[$minInclusivelon, $maxInclusivelon]"
}

objelonct BoundsWithDelonfault {
  delonf apply[T](
    minInclusivelon: T,
    maxInclusivelon: T,
    delonfault: T
  )(
    implicit ordelonring: Ordelonring[T]
  ): BoundsWithDelonfault[T] = BoundsWithDelonfault(Bounds(minInclusivelon, maxInclusivelon), delonfault)
}

caselon class BoundsWithDelonfault[T](bounds: Bounds[T], delonfault: T)(implicit ordelonring: Ordelonring[T]) {
  bounds.throwIfOutOfBounds(delonfault, "delonfault")

  delonf apply(valuelonOpt: Option[T]): T = valuelonOpt.map(bounds.apply).gelontOrelonlselon(delonfault)
}
