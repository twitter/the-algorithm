package com.tw ter.product_m xer.core.model.marshall ng.request

 mport com.tw ter.t  l nes.conf gap .{FeatureValue => Conf gAp FeatureValue}

case class DebugParams(
  featureOverr des: Opt on[Map[Str ng, Conf gAp FeatureValue]],
  overr de val debugOpt ons: Opt on[DebugOpt ons])
    extends HasDebugOpt ons
