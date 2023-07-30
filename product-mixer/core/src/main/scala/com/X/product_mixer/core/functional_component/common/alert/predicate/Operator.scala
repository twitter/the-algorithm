package com.X.product_mixer.core.functional_component.common.alert.predicate

/**
 * Used for building [[Predicate]]s
 *
 * @see [[https://docbird.X.biz/mon/reference.html#predicate OPERATOR]]
 */
private[alert] sealed trait Operator
private[alert] case object `>` extends Operator
private[alert] case object `>=` extends Operator
private[alert] case object `<` extends Operator
private[alert] case object `<=` extends Operator
private[alert] case object `!=` extends Operator
private[alert] case object `=` extends Operator
