package com.twitter.product_mixer.core.functional_component.common.alert

/**
 * Indicates that an [[Alert]] can be passed to [[StratoColumnAlert]]. Not all [[Alert]]s can be
 * Strato alerts since our ability to observe from Strato's perspective is limited by the available
 * metrics.
 *
 * @note can only be used in conjunction with [[Alert]]
 */
trait IsObservableFromStrato { _: Alert => }
