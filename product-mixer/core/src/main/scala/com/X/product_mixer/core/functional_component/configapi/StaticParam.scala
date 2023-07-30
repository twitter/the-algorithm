package com.X.product_mixer.core.functional_component.configapi

import com.X.timelines.configapi.Param

/** A [[Param]] used for constant values where we do not require backing by feature switches or deciders */
case class StaticParam[ValueType](value: ValueType) extends Param[ValueType](value)
