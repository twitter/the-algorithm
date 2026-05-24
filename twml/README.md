# TWML

---
Note: `twml` is no longer under development. Much of the code here is out of date and unused.
It is included here for completeness, because `twml` is still used to train the light ranker models
(see `src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/README.md`)
---

TWML is one of Twitter's machine learning frameworks, which uses Tensorflow under the hood. While it is mostly
deprecated,
it is still currently used to train the Earlybird light ranking models (
see `src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/train.py`).
The most relevant part of this is the `DataRecordTrainer` class, which is where the core training logic resides.  
