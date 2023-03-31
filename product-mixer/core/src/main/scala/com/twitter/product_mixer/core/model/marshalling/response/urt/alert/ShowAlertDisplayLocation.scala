package com.twitter.product_mixer.core.model.marshalling.response.urt.alert

sealed trait ShowAlertDisplayLocation
case object Top extends ShowAlertDisplayLocation
case object Bottom extends ShowAlertDisplayLocation
