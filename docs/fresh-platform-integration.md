# Fresh platform — Algorithm boundary

Appwrite Auth and Appwrite Database are the new platform's identity and canonical data layer. Algorithm is a private ranking service called only by the Go Backend. It never receives Appwrite server keys, queries Appwrite, accepts browser requests, or makes authorization decisions.

Backend supplies bounded candidate batches only after applying blocks, visibility, moderation, privacy and follow rules. Ranking may reorder only supplied IDs. Backend rechecks visibility before returning a feed. No Supabase connection, legacy ID lookup or legacy content import belongs here. Federation remains disabled.
