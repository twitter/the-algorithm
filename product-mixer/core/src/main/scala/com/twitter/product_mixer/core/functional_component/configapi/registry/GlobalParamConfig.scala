package com.twitter.product_mixer.core.functional_component.configapi.registry

/**
 * Register Params that do not relate to a specific product.
 * See [[ParamConfig]] for hooks to register Params based on type.
 */
trait GlobalParamConfig extends ParamConfig with ParamConfigBuilder
