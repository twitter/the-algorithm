package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.X_list

import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.List
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.ListTile
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.ListWithPin
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.ListWithSubscribe
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.XListDisplayType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XListDisplayTypeMarshaller @Inject() () {

  def apply(XListDisplayType: XListDisplayType): urt.XListDisplayType =
    XListDisplayType match {
      case List => urt.XListDisplayType.List
      case ListTile => urt.XListDisplayType.ListTile
      case ListWithPin => urt.XListDisplayType.ListWithPin
      case ListWithSubscribe => urt.XListDisplayType.ListWithSubscribe
    }
}
