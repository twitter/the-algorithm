package com.tw ter.product_m xer.core.model.marshall ng.response.urp

 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g

case class Page(
   d: Str ng,
  pageBody: PageBody,
  scr beConf g: Opt on[T  l neScr beConf g] = None,
  page ader: Opt on[Page ader] = None,
  pageNavBar: Opt on[PageNavBar] = None)
    extends HasMarshall ng
