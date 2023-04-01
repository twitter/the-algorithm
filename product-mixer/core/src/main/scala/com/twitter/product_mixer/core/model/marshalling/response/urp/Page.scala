package com.twitter.product_mixer.core.model.marshalling.response.urp

import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineScribeConfig

case class Page(
  id: String,
  pageBody: PageBody,
  scribeConfig: Option[TimelineScribeConfig] = None,
  pageHeader: Option[PageHeader] = None,
  pageNavBar: Option[PageNavBar] = None)
    extends HasMarshalling
