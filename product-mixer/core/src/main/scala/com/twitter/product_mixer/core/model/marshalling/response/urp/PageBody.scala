package com.tw ter.product_m xer.core.model.marshall ng.response.urp

sealed tra  PageBody

case class T  l neKeyPageBody(t  l ne: T  l neKey) extends PageBody

case class Seg ntedT  l nesPageBody(
   n  alT  l ne: Seg ntedT  l ne,
  t  l nes: Seq[Seg ntedT  l ne])
    extends PageBody
