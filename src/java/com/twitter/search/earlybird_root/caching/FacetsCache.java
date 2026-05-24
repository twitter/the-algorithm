package com.twitter.search.earlybird_root.caching;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@BindingAnnotation
public @interface FacetsCache {
}
