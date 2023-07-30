package com.X.frigate.pushservice.store

import com.X.copyselectionservice.thriftscala._
import com.X.storehaus.ReadableStore
import com.X.util.Future

class CopySelectionServiceStore(copySelectionServiceClient: CopySelectionService.FinagledClient)
    extends ReadableStore[CopySelectionRequestV1, Copy] {
  override def get(k: CopySelectionRequestV1): Future[Option[Copy]] =
    copySelectionServiceClient.getSelectedCopy(CopySelectionRequest.V1(k)).map {
      case CopySelectionResponse.V1(response) =>
        Some(response.selectedCopy)
      case _ => throw CopyServiceException(CopyServiceErrorCode.VersionNotFound)
    }
}
