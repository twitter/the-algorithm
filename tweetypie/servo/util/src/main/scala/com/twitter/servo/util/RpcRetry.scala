package com.tw ter.servo.ut l

 mport com.tw ter.ut l.Future

object RpcRetry {

  /**
   * Prov des a gener c  mple ntat on of a retry log c to only a subset
   * of requests accord ng to a g ven pred cate and return ng t  result
   *  n t  or g nal order after t  retry.
   * @param rpcs  thods that can transform a Seq[Request] to
   *             Future[Map[Request, Response]], t y w ll be  nvoked  n order
   *             wh le t re are rema n ng rpcs to  nvoke AND so  responses
   *             st ll return false to t  pred cate.
   * @param  sSuccess  f true, keep t  response, else retry.
   * @tparam Req a request object
   * @tparam Resp a response object
   * @return an rpc funct on (Seq[Req] => Future[Map[Req, Resp]]) that performs
   *         t  retr es  nternally.
   */
  def retryableRpc[Req, Resp](
    rpcs: Seq[Seq[Req] => Future[Map[Req, Resp]]],
     sSuccess: Resp => Boolean
  ): Seq[Req] => Future[Map[Req, Resp]] = {
    requestRetryAnd rge[Req, Resp](_,  sSuccess, rpcs.toStream)
  }

  /**
   * Prov des a gener c  mple ntat on of a retry log c to only a subset
   * of requests accord ng to a g ven pred cate and return ng t  result
   *  n t  or g nal order after t  retry.
   * @param rpcs  thods that can transform a Seq[Request] to
   *             Future[Seq[Response]], t y w ll be  nvoked  n order
   *             wh le t re are rema n ng rpcs to  nvoke AND so  responses
   *             st ll return false to t  pred cate.
   *             Note that all Request objects must ad re to hashCode/equals standards
   * @param  sSuccess  f true, keep t  response, else retry.
   * @tparam Req a request object. Must ad re to hashCode/equals standards
   * @tparam Resp a response object
   * @return an rpc funct on (Seq[Req] => Future[Seq[Resp]]) that performs
   *         t  retr es  nternally.
   */
  def retryableRpcSeq[Req, Resp](
    rpcs: Seq[Seq[Req] => Future[Seq[Resp]]],
     sSuccess: Resp => Boolean
  ): Seq[Req] => Future[Seq[Resp]] = {
    requestRetryAnd rgeSeq[Req, Resp](_,  sSuccess, rpcs)
  }

  pr vate[t ] def requestRetryAnd rgeSeq[Req, Resp](
    requests: Seq[Req],
     sSuccess: Resp => Boolean,
    rpcs: Seq[Seq[Req] => Future[Seq[Resp]]]
  ): Future[Seq[Resp]] = {
    requestRetryAnd rge(requests,  sSuccess, (rpcs map { rpcToMapResponse(_) }).toStream) map {
      responseMap =>
        requests map { responseMap(_) }
    }
  }

  pr vate[t ] def requestRetryAnd rge[Req, Resp](
    requests: Seq[Req],
     sSuccess: Resp => Boolean,
    rpcs: Stream[Seq[Req] => Future[Map[Req, Resp]]]
  ): Future[Map[Req, Resp]] = {
     f (rpcs. sEmpty) {
      Future.except on(new  llegalArgu ntExcept on("rpcs  s empty."))
    } else {
      val rpc = rpcs. ad
      rpc(requests) flatMap { responses =>
        val (keep, recurse) = responses part  on {
          case (_, rep) =>  sSuccess(rep)
        }
         f (rpcs.ta l.nonEmpty && recurse.nonEmpty) {
          requestRetryAnd rge(recurse.keys.toSeq,  sSuccess, rpcs.ta l) map { keep ++ _ }
        } else {
          Future.value(responses)
        }
      }
    }
  }

  pr vate[t ] def rpcToMapResponse[Req, Resp](
    rpc: Seq[Req] => Future[Seq[Resp]]
  ): Seq[Req] => Future[Map[Req, Resp]] = { (reqs: Seq[Req]) =>
    rpc(reqs) map { reps =>
      (reqs z p reps).toMap
    }
  }
}
