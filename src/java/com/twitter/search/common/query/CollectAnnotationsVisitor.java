package com.twitter.search.common.query;


import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import com.twitter.search.queryparser.query.BooleanQuery;
import com.twitter.search.queryparser.query.Conjunction;
import com.twitter.search.queryparser.query.Disjunction;
import com.twitter.search.queryparser.query.Operator;
import com.twitter.search.queryparser.query.Phrase;
import com.twitter.search.queryparser.query.Query;
import com.twitter.search.queryparser.query.QueryParserEtwitterception;
import com.twitter.search.queryparser.query.QueryVisitor;
import com.twitter.search.queryparser.query.SpecialTerm;
import com.twitter.search.queryparser.query.Term;
import com.twitter.search.queryparser.query.annotation.Annotation;

/**
 * Collect the nodes with a specified annotation type in the given query.
 */
public class CollectAnnotationsVisitor etwittertends QueryVisitor<Boolean> {

  protected final Annotation.Type type;

  protected final Map<Query, Boolean> nodeToTypeMap = Maps.newIdentityHashMap();

  public CollectAnnotationsVisitor(Annotation.Type type) {
    this.type = Preconditions.checkNotNull(type);
  }

  @Override
  public Boolean visit(Disjunction disjunction) throws QueryParserEtwitterception {
    return visitBooleanQuery(disjunction);
  }

  @Override
  public Boolean visit(Conjunction conjunction) throws QueryParserEtwitterception {
    return visitBooleanQuery(conjunction);
  }

  @Override
  public Boolean visit(Phrase phrase) throws QueryParserEtwitterception {
    return visitQuery(phrase);
  }

  @Override
  public Boolean visit(Term term) throws QueryParserEtwitterception {
    return visitQuery(term);
  }

  @Override
  public Boolean visit(Operator operator) throws QueryParserEtwitterception {
    return visitQuery(operator);
  }

  @Override
  public Boolean visit(SpecialTerm special) throws QueryParserEtwitterception {
    return visitQuery(special);
  }

  protected boolean visitQuery(Query query) throws QueryParserEtwitterception {
    if (query.hasAnnotationType(type)) {
      collectNode(query);
      return true;
    }
    return false;
  }

  protected void collectNode(Query query) {
    nodeToTypeMap.put(query, true);
  }

  protected boolean visitBooleanQuery(BooleanQuery query) throws QueryParserEtwitterception {
    boolean found = false;
    if (query.hasAnnotationType(type)) {
      collectNode(query);
      found = true;
    }
    for (Query child : query.getChildren()) {
      found |= child.accept(this);
    }
    return found;
  }

  public Set<Query> getNodes() {
    return nodeToTypeMap.keySet();
  }
}
