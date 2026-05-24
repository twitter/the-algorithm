package com.twitter.product_mixer.core.functional_component.side_effect

/**
 * A modifier for any [[SideEffect]] so that the request waits for it to complete before being returned
 *
 * @note this will make the [[SideEffect]]'s latency impact the overall request's latency
 *
 * @example {{{
 * class MySideEffect extends PipelineResultSideEffect[T] with ExecuteSynchronously {...}
 * }}}
 *
 * @example {{{
 * class MySideEffect extends ScribeLogEventSideEffect[T] with ExecuteSynchronously {...}
 * }}}
 */
trait ExecuteSynchronously { _: SideEffect[_] => }

/**
 * A modifier for any [[ExecuteSynchronously]] [[SideEffect]] that makes it so failures will be
 * reported in the results but wont cause the request as a whole to fail.
 */
trait FailOpen { _: ExecuteSynchronously => }
