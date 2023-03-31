package com.twitter.search.common.query;

import com.twitter.search.queryparser.query.annotation.Annotation;


/**
 * A visitor that collects the nodes that have :v annotation
 */
public class CollectVariantVisitor extends CollectAnnotationsVisitor {
  public CollectVariantVisitor() {
    super(Annotation.Type.VARIANT);
  }
}
