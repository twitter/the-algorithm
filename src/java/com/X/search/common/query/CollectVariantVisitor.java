package com.X.search.common.query;

import com.X.search.queryparser.query.annotation.Annotation;


/**
 * A visitor that collects the nodes that have :v annotation
 */
public class CollectVariantVisitor extends CollectAnnotationsVisitor {
  public CollectVariantVisitor() {
    super(Annotation.Type.VARIANT);
  }
}
