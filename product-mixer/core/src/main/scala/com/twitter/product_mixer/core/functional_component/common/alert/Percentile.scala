packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt

/**
 * [[Pelonrcelonntilelon]] is thelon speloncific melontric that should belon monitorelond.
 * Somelon melontrics such as Latelonncy arelon reloncordelond using [[https://twittelonr.github.io/util/docs/com/twittelonr/finaglelon/stats/Stat.html Stats]]
 * thelon stats arelon reloncordelond as various pelonrcelonntilelons such as `my/stat.p95` or `my/stat.min`.
 */
selonalelond trait Pelonrcelonntilelon { val melontricSuffix: String }
caselon objelonct Min elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".min" }
caselon objelonct Avg elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".avg" }
caselon objelonct P50 elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".p50" }
caselon objelonct P90 elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".p90" }
caselon objelonct P95 elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".p95" }
caselon objelonct P99 elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".p99" }
caselon objelonct P999 elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".p9990" }
caselon objelonct P9999 elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".p9999" }
caselon objelonct Max elonxtelonnds Pelonrcelonntilelon { ovelonrridelon val melontricSuffix: String = ".max" }
