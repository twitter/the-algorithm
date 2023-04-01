package com.twitter.product_mixer.core.product

import com.twitter.product_mixer.core.functional_component.configapi.registry.ParamConfig
import com.twitter.servo.decider.DeciderKeyName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.decider.BooleanDeciderParam

trait ProductParamConfig extends ParamConfig with ProductParamConfigBuilder {

  /**
   * This enabled decider param can to be used to quickly disable a Product via Decider
   *
   * This value must correspond to the deciders configured in the `resources/config/decider.yml` file
   */
  val enabledDeciderKey: DeciderKeyName

  /**
   * This supported client feature switch param can be used with a Feature Switch to control the
   * rollout of a new Product from dogfood to experiment to production
   *
   * FeatureSwitches are configured by defining both a [[com.twitter.timelines.configapi.Param]] in code
   * and in an associated `.yml` file in the __config repo__.
   *
   * The `.yml` file path is determined by the `feature_switches_path` in your aurora file and tge Product name
   * so the resulting path in the __config repo__ is essentially `s"{feature_switches_path}/{snakeCase(Product.identifier)}"`
   */
  val supportedClientFSName: String

  object EnabledDeciderParam extends BooleanDeciderParam(enabledDeciderKey)

  object SupportedClientParam
      extends FSParam(
        name = supportedClientFSName,
        default = false
      )
}
