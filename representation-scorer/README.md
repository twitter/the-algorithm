# Representation Scorer #

**Representation Scorer** (RSX) serves as a centralized scoring system, offering SimClusters or other embedding-based scoring solutions as machine learning features.

The Representation Scorer acquires user behavior data from the User Signal Service (USS) and extracts embeddings from the Representation Manager (RMS). It then calculates both pairwise and listwise features. These features are used at various stages, including candidate retrieval and ranking.