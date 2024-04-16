package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback

/**
 * Represents d fferent types of URT Prompts supported such as t  Relevance Prompt.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/PromptContent.html
 */
sealed tra  PromptContent

/**
 * Relevance Prompt  s a Yes-No style prompt that can be used for collect ng feedback from a User
 * about a part of t  r t  l ne.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/RelevancePrompt.html
 */
case class RelevancePromptContent(
  t le: Str ng,
  conf rmat on: Str ng,
   sRelevantText: Str ng,
  notRelevantText: Str ng,
   sRelevantCallback: Callback,
  notRelevantCallback: Callback,
  d splayType: RelevancePromptD splayType,
   sRelevantFollowUp: Opt on[RelevancePromptFollowUpFeedbackType],
  notRelevantFollowUp: Opt on[RelevancePromptFollowUpFeedbackType])
    extends PromptContent
