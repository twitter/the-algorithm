package com.twitter.product_mitwitterer.core.functional_component.side_effect

/**
 * A modifier for any [[SideEffect]] so that the request waits for it to complete before being returned
 *
 * @note this will make the [[SideEffect]]'s latency impact the overall request's latency
 *
 * @etwitterample {{{
 * class MySideEffect etwittertends PipelineResultSideEffect[T] with EtwitterecuteSynchronously {...}
 * }}}
 *
 * @etwitterample {{{
 * class MySideEffect etwittertends ScribeLogEventSideEffect[T] with EtwitterecuteSynchronously {...}
 * }}}
 */
trait EtwitterecuteSynchronously { _: SideEffect[_] => }

/**
 * A modifier for any [[EtwitterecuteSynchronously]] [[SideEffect]] that makes it so failures will be
 * reported in the results but wont cause the request as a whole to fail.
 */
trait FailOpen { _: EtwitterecuteSynchronously => }
