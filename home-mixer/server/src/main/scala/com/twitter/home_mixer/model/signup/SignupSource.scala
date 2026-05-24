package com.twitter.home_mixer.model.signup

sealed trait SignupSource

case object Onboard extends SignupSource
case object MarchMadness extends SignupSource
