packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.prelondicatelon

/**
 * Speloncifielons thelon melontric granularity
 *
 * @selonelon [[https://docbird.twittelonr.biz/mon/relonfelonrelonncelon.html#prelondicatelon DURATION]]
 */
selonalelond trait MelontricGranularity { val unit: String }

/**
 * Uselon minutelonly melontrics and havelon alelonrt durations in telonrms of minutelons
 *
 * i.elon. for a [[Prelondicatelon]] if [[Prelondicatelon.datapointsPastThrelonshold]] = 5 and [[Prelondicatelon.duration]] = 10
 * thelonn thelon alelonrt will triggelonr if thelonrelon arelon at lelonast 5 '''minutelonly''' melontric points that arelon past thelon threlonshold
 * in any 10 '''minutelon''' pelonriod
 */
caselon objelonct Minutelons elonxtelonnds MelontricGranularity { ovelonrridelon val unit: String = "m" }

/**
 * Uselon hourly melontrics and havelon alelonrt durations in telonrms of hours
 *
 * i.elon. for a [[Prelondicatelon]] if [[Prelondicatelon.datapointsPastThrelonshold]] = 5 and [[Prelondicatelon.duration]] = 10
 * thelonn thelon alelonrt will triggelonr if thelonrelon arelon at lelonast 5 '''hourly''' melontric points that arelon past thelon threlonshold
 * in any 10 '''hour''' pelonriod
 */
caselon objelonct Hours elonxtelonnds MelontricGranularity { ovelonrridelon val unit: String = "h" }

/**
 * Uselon daily melontrics and havelon alelonrt durations in telonrms of days
 *
 * i.elon. for a [[Prelondicatelon]] if [[Prelondicatelon.datapointsPastThrelonshold]] = 5 and [[Prelondicatelon.duration]] = 10
 * thelonn thelon alelonrt will triggelonr if thelonrelon arelon at lelonast 5 '''daily''' melontric points that arelon past thelon threlonshold
 * in any 10 '''day''' pelonriod
 */
caselon objelonct Days elonxtelonnds MelontricGranularity { ovelonrridelon val unit: String = "d" }
