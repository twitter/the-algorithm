package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback

/**
 * D fferent k nds of follow-ups after a pos  ve-negat ve feedback on a prompt button.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/RelevancePromptFollowUpFeedbackType.html
 */
sealed tra  RelevancePromptFollowUpFeedbackType

case class RelevancePromptFollowUpText nput(
  context: Str ng,
  textF eldPlaceholder: Str ng,
  sendTextCallback: Callback)
    extends RelevancePromptFollowUpFeedbackType
