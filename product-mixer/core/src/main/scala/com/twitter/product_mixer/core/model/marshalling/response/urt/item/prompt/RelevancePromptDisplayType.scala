package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.prompt

/**
 * Represents t  d fferent ways to d splay t  Relevance Prompt  n a t  l ne.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/RelevancePromptD splayType.html
 */
sealed tra  RelevancePromptD splayType

case object Normal extends RelevancePromptD splayType
case object Compact extends RelevancePromptD splayType
case object Large extends RelevancePromptD splayType
case object ThumbsUpAndDown extends RelevancePromptD splayType
