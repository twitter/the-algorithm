include "baseplate.thrift"

/** A unique identifier for a given "context".

A context is an area of the service which a user may be active within, such as
a subreddit or live thread.

*/
typedef string ContextID

/** A unique identifier for a given visitor.

A visitor may be a logged-in user's ID or a logged-out user's LOID value. The
value is not actually stored, but only used to update the internal counters.

*/
typedef string VisitorID


/** A count of visitors active within a context.

If the count is low enough, some fuzzing is applied to the number. If this
kicks in, the `is_fuzzed` attribute will be True.

*/
struct ActivityInfo {
    1: optional i32 count;
    2: optional bool is_fuzzed;
}

/** A specified context ID was invalid */
exception InvalidContextIDException {
}

service ActivityService extends baseplate.BaseplateService {
    /** Register a visitor's activity within a given context.

    The visitor's activity will be recorded but will expire over time. If the
    user continues to be active within the context, this endpoint should be
    called occasionally to ensure they continue to be counted.

    This method is `oneway`; no indication of success or failure is returned.

    */
    oneway void record_activity(1: ContextID context_id, 2: VisitorID visitor_id),

    /** Count how many visitors are currently active in a given context.

    The results of this call are cached for a period of time to ensure that if
    the count is fuzzed, the fuzzing is stable. This prevents repeated requests
    from revealing the range of fuzzing and therefore the true value.

    */
    ActivityInfo count_activity(1: ContextID context_id)
        throws (1: InvalidContextIDException invalid_context_id),

    /** Count how many visitors are active in a number of given contexts.

    This is the same as `count_activity` but allows for querying in batch.

    */
    map<ContextID, ActivityInfo> count_activity_multi(1: list<ContextID> context_ids)
        throws (1: InvalidContextIDException invalid_context_id),
}
