package com.twitter.product_mixer.core.model.marshalling.response.urt.alert

sealed trait ShowAlertType
case object NewTweets extends ShowAlertType
case object Navigate extends ShowAlertType
