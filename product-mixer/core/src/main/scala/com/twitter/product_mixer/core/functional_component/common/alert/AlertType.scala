package com.tw ter.product_m xer.core.funct onal_component.common.alert

/**
 * [[AlertType]]  s used to  nd cate wh ch  tr c an alert  s for
 *
 * @note add ng new [[AlertType]]s requ res updat ng t  dashboard generat on code
 */
sealed tra  AlertType { val  tr cType: Str ng }

/** Mon ors t  latency */
case object Latency extends AlertType { overr de val  tr cType: Str ng = "Latency" }

/** Mon ors t  success rate __exclud ng__ cl ent fa lures */
case object SuccessRate extends AlertType { overr de val  tr cType: Str ng = "SuccessRate" }

/** Mon ors t  throughput */
case object Throughput extends AlertType { overr de val  tr cType: Str ng = "Throughput" }

/** Mon ors t  empty response rate */
case object EmptyResponseRate extends AlertType {
  overr de val  tr cType: Str ng = "EmptyResponseRate"
}

/** Mon ors t  empty response s ze */
case object ResponseS ze extends AlertType { overr de val  tr cType: Str ng = "ResponseS ze" }
