# The Algorithm <-> Backend integration contract

The Algorithm is a private, stateless ranking service. The Backend is the only caller and supplies a bounded batch (1-200) of already-authorized candidates plus ranking features. The Algorithm never reads Appwrite, object storage, user sessions or browser traffic and cannot decide canonical visibility, blocks, retention or deletion.

`POST /v1/feed/rank` is authenticated with a dedicated high-entropy `INTERNAL_TOKEN` over the private service network. `GET /healthz` and `/readyz` are infrastructure probes only. Keep the token server-side, rotate it independently from other service credentials, and never ship it to XClone. Backend validates that returned IDs are unique and belong to the submitted candidate set, then reapplies authorization and visibility filters before responding to XClone.

The current rank endpoint and Backend `internal/algorithmclient` implement the first service-to-service path. Feed candidate retrieval, cursor pagination and end-to-end Appwrite-to-ranker tests remain Backend work. Keep ranking policy and tests in this repository; do not create a separate API repository.
