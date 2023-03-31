package com.twitter.search.common.schema.base;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * This interface carries the same signature as Schema with the only difference that this schema
 * is immutable.  This should be used by short sessions and the class would guarantee the schema
 * would not change for the session.  A typical usage is like a search query session.
 */
@Immutable
@ThreadSafe
public interface ImmutableSchemaInterface extends Schema {
}
