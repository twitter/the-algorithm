package com.X.product_mixer.core.model.marshalling.response.urt.item.X_list

sealed trait XListDisplayType

case object List extends XListDisplayType
case object ListTile extends XListDisplayType
case object ListWithPin extends XListDisplayType
case object ListWithSubscribe extends XListDisplayType
