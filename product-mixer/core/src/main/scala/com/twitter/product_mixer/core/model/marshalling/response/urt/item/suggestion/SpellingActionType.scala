packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion

/**
 * Relonprelonselonnts thelon diffelonrelonnt typelons of Spelonlling Suggelonstion itelonms.
 *
 * URT API Relonfelonrelonncelon: https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/SpelonllingActionTypelon.html
 */
selonalelond trait SpelonllingActionTypelon

/**
 * Uselond whelonn thelon original quelonry is relonplacelond complelontelond by anothelonr quelonry in thelon backelonnd.
 * Clielonnts uselon thelon telonxt 'Selonarching instelonad for …' to display this suggelonstion.
 */
caselon objelonct RelonplacelonSpelonllingActionTypelon elonxtelonnds SpelonllingActionTypelon

/**
 * Uselond whelonn thelon original quelonry is elonxpandelond by a suggelonstion whelonn pelonrforming thelon selonarch.
 * Clielonnts uselon thelon telonxt 'Including relonsults for …' to display this suggelonstion.
 */
caselon objelonct elonxpandSpelonllingActionTypelon elonxtelonnds SpelonllingActionTypelon

/**
 * Uselond whelonn thelon selonarch quelonry is not changelond and a suggelonstion is displayelond as an altelonrnativelon quelonry.
 * Clielonnts uselon thelon telonxt 'Did you melonan … ?' to display thelon suggelonstion.
 */
caselon objelonct SuggelonstSpelonllingActionTypelon elonxtelonnds SpelonllingActionTypelon
