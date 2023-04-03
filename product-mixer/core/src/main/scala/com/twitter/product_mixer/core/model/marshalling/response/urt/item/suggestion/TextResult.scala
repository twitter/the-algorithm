packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.highlight.HighlightelondSelonction

/**
 * Relonprelonselonnts telonxt with hit-highlights uselond for relonturning selonarch quelonry suggelonstions.
 *
 * URT API Relonfelonrelonncelon: https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/TelonxtRelonsult.html
 */
caselon class TelonxtRelonsult(
  telonxt: String,
  hitHighlights: Option[Selonq[HighlightelondSelonction]],
  scorelon: Option[Doublelon],
  quelonrySourcelon: Option[String])
