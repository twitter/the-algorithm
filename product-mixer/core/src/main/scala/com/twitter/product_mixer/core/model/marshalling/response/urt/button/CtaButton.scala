package com.tw ter.product_m xer.core.model.marshall ng.response.urt.button

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url

sealed tra  CtaButton

case class TextCtaButton(buttonText: Str ng, url: Url) extends CtaButton

case class  conCtaButton(button con: Hor zon con, access b l yLabel: Str ng, url: Url)
    extends CtaButton
