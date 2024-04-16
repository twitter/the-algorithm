package com.tw ter.product_m xer.core.funct onal_component.common.alert

/**
 * [[Percent le]]  s t  spec f c  tr c that should be mon ored.
 * So   tr cs such as Latency are recorded us ng [[https://tw ter.g hub. o/ut l/docs/com/tw ter/f nagle/stats/Stat.html Stats]]
 * t  stats are recorded as var ous percent les such as ` /stat.p95` or ` /stat.m n`.
 */
sealed tra  Percent le { val  tr cSuff x: Str ng }
case object M n extends Percent le { overr de val  tr cSuff x: Str ng = ".m n" }
case object Avg extends Percent le { overr de val  tr cSuff x: Str ng = ".avg" }
case object P50 extends Percent le { overr de val  tr cSuff x: Str ng = ".p50" }
case object P90 extends Percent le { overr de val  tr cSuff x: Str ng = ".p90" }
case object P95 extends Percent le { overr de val  tr cSuff x: Str ng = ".p95" }
case object P99 extends Percent le { overr de val  tr cSuff x: Str ng = ".p99" }
case object P999 extends Percent le { overr de val  tr cSuff x: Str ng = ".p9990" }
case object P9999 extends Percent le { overr de val  tr cSuff x: Str ng = ".p9999" }
case object Max extends Percent le { overr de val  tr cSuff x: Str ng = ".max" }
