package com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate

/**
 * Used for bu ld ng [[Pred cate]]s
 *
 * @see [[https://docb rd.tw ter.b z/mon/reference.html#pred cate OPERATOR]]
 */
pr vate[alert] sealed tra  Operator
pr vate[alert] case object `>` extends Operator
pr vate[alert] case object `>=` extends Operator
pr vate[alert] case object `<` extends Operator
pr vate[alert] case object `<=` extends Operator
pr vate[alert] case object `!=` extends Operator
pr vate[alert] case object `=` extends Operator
