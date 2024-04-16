package com.tw ter.search. ngester.p pel ne.ut l;

publ c class P pel neStageExcept on extends Except on {
  publ c P pel neStageExcept on(Object locat on, Str ng  ssage, Throwable cause) {
    super( ssage + "  n Stage : " + locat on.getClass(), cause);
  }

  publ c P pel neStageExcept on(Throwable cause) {
    super(cause);
  }

  publ c P pel neStageExcept on(Str ng  ssage) {
    super( ssage);
  }

  publ c P pel neStageExcept on(Object locat on, Str ng  ssage) {
    super( ssage + "  n Stage : " + locat on.getClass());
  }
}
