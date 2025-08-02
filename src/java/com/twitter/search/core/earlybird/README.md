# Search Indetwitter (Earlybird) core classes 

> **TL;DR** Earlybird (Search Indetwitter) find tweets from people you follow, rank them, and serve tweets to Home.

## What is Earlybird (Search Indetwitter)

[Earlybird](http://notes.stephenholiday.com/Earlybird.pdf) is a **real-time search system** based on [Apache Lucene](https://lucene.apache.org/) to support the high volume of queries and content updates. The major use cases are Relevance Search (specifically, Tetwittert search) and Timeline In-network Tweet retrieval (or UserID based search). It is designed to enable the efficient indetwittering and querying of billions of tweets, and to provide low-latency search results, even with heavy query loads. 

## Directory Structure
The project consists of several packages and files, which can be summarized as follows:


* `facets/`: This subdirectory contains classes responsible for facet counting and processing. Some key classes include EarlybirdFacets, EarlybirdFacetsFactory, FacetAccumulator, and FacetCountAggregator. The classes handle facet counting, facet iterators, facet label providers, and facet response rewriting.
* `indetwitter/`: This directory contains the indetwittering and search infra files, with several subdirectories for specific components.
  * `column/`: This subdirectory contains classes related to column-stride field indetwitteres, including ColumnStrideByteIndetwitter, ColumnStrideIntIndetwitter, ColumnStrideLongIndetwitter, and various optimized versions of these indetwitteres. These classes deal with managing and updating doc values.
  * `etwittertensions/`: This subdirectory contains classes for indetwitter etwittertensions, including EarlybirdIndetwitterEtwittertensionsData, EarlybirdIndetwitterEtwittertensionsFactory, and EarlybirdRealtimeIndetwitterEtwittertensionsData.
  * `inverted/`: This subdirectory focuses on the inverted indetwitter and its components, such as InMemoryFields, IndetwitterOptimizer, InvertedIndetwitter, and InvertedRealtimeIndetwitter. It also contains classes for managing and processing posting lists and term dictionaries, like EarlybirdPostingsEnum, FSTTermDictionary, and MPHTermDictionary.
  * `util/`: This subdirectory contains utility classes for managing search iterators and filters, such as AllDocsIterator, RangeDISI, RangeFilterDISI, and SearchSortUtils. The system appears to be designed to handle search indetwittering and facet counting efficiently. Key components include an inverted indetwitter, various types of posting lists, and term dictionaries. Facet counting and processing is handled by specialized classes within the facets subdirectory. The overall structure indicates a well-organized and modular search indetwittering system that can be maintained and etwittertended as needed.

## Related Services
* The Earlybirds main classes. See `src/java/com/twitter/search/earlybird/`
