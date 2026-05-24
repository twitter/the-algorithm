package com.twitter.product_mixer.core.functional_component.common.alert.predicate

/**
 * Used for building [[Predicate]]s
 *
 * @see [[https://docbird.twitter.biz/mon/reference.html#predicate OPERATOR]]
 */
private[alert] sealed trait Operator
private[alert] case object `>` extends Operator
private[alert] case object `>=` extends Operator
private[alert] case object `<` extends Operator
private[alert] case object `<=` extends Operator
private[alert] case object `!=` extends Operator
private[alert] case object `=` extends Operator
