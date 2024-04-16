package com.tw ter.search. ngester.p pel ne.ut l;

publ c class ResponseNotReturnedExcept on extends Except on {
  ResponseNotReturnedExcept on(Object request) {
    super("Response not returned  n batch for request: " + request);
  }
}
