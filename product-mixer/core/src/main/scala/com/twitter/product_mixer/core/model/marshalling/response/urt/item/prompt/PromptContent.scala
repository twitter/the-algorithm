packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.prompt

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Callback

/**
 * Relonprelonselonnts diffelonrelonnt typelons of URT Prompts supportelond such as thelon Relonlelonvancelon Prompt.
 *
 * URT API Relonfelonrelonncelon: https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/PromptContelonnt.html
 */
selonalelond trait PromptContelonnt

/**
 * Relonlelonvancelon Prompt is a Yelons-No stylelon prompt that can belon uselond for colleloncting felonelondback from a Uselonr
 * about a part of thelonir timelonlinelon.
 *
 * URT API Relonfelonrelonncelon: https://docbird.twittelonr.biz/unifielond_rich_timelonlinelons_urt/gelonn/com/twittelonr/timelonlinelons/relonndelonr/thriftscala/RelonlelonvancelonPrompt.html
 */
caselon class RelonlelonvancelonPromptContelonnt(
  titlelon: String,
  confirmation: String,
  isRelonlelonvantTelonxt: String,
  notRelonlelonvantTelonxt: String,
  isRelonlelonvantCallback: Callback,
  notRelonlelonvantCallback: Callback,
  displayTypelon: RelonlelonvancelonPromptDisplayTypelon,
  isRelonlelonvantFollowUp: Option[RelonlelonvancelonPromptFollowUpFelonelondbackTypelon],
  notRelonlelonvantFollowUp: Option[RelonlelonvancelonPromptFollowUpFelonelondbackTypelon])
    elonxtelonnds PromptContelonnt
