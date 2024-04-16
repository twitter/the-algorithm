package com.tw ter.product_m xer.core.qual y_factor

/**
 * Prov des a way to apply  nclus ve m n/max bounds to a g ven value.
 */
case class Bounds[T](m n nclus ve: T, max nclus ve: T)( mpl c  order ng: Order ng[T]) {

  def apply(value: T): T = order ng.m n(max nclus ve, order ng.max(m n nclus ve, value))

  def  sW h n(value: T): Boolean =
    order ng.gteq(value, m n nclus ve) && order ng.lteq(value, max nclus ve)

  def throw fOutOfBounds(value: T,  ssagePref x: Str ng): Un  =
    requ re( sW h n(value), s"$ ssagePref x: value must be w h n $toStr ng")

  overr de def toStr ng: Str ng = s"[$m n nclus ve, $max nclus ve]"
}

object BoundsW hDefault {
  def apply[T](
    m n nclus ve: T,
    max nclus ve: T,
    default: T
  )(
     mpl c  order ng: Order ng[T]
  ): BoundsW hDefault[T] = BoundsW hDefault(Bounds(m n nclus ve, max nclus ve), default)
}

case class BoundsW hDefault[T](bounds: Bounds[T], default: T)( mpl c  order ng: Order ng[T]) {
  bounds.throw fOutOfBounds(default, "default")

  def apply(valueOpt: Opt on[T]): T = valueOpt.map(bounds.apply).getOrElse(default)
}
