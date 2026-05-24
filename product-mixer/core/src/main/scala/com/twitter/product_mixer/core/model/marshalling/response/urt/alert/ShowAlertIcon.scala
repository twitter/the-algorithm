package com.twitter.product_mixer.core.model.marshalling.response.urt.alert

sealed trait ShowAlertIcon
case object UpArrow extends ShowAlertIcon
case object DownArrow extends ShowAlertIcon
