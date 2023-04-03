# SimClusters: Biểu diễn dựa trên community cho các gợi ý không đồng nhất trên Twitter.

## Tổng quan
SimClusters được coi là một lớp biểu diễn đa năng dựa trên các community chồng chéo, trong đó người dùng cũng như nội dung không đồng nhất có thể được chụp dưới dạng các vector rãi rác, có thể diễn giải để hỗ trợ nhiều tác vụ gợi ý.

Chúng tôi xây dựng các nhúng SimClusters người dùng và tweet của chúng tôi dựa trên các community được suy luận, và các biểu diễn này cung cấp sức mạnh cho các gợi ý Tweet cá nhân của chúng tôi thông qua dịch vụ phục vụ trực tuyến của chúng tôi, SimClusters ANN.


Chi tiết vui lòng đọc bài báo được publush ở KDD'2020 Applied Data Science Track: https://www.kdd.org/kdd2020/accepted-papers/view/simclusters-community-based-representations-for-heterogeneous-recommendatio

## Tóm tắt ngắn gọn Simclusters Algorithm

### Theo dõi mối quan hệ được biểu diễn dưới dạng đồ thị hai phần.
Mối quan hệ theo dõi trên Twitter có thể được coi như là một đồ thị có hướng, trong đó mỗi nút là một người dùng và mỗi cạnh biểu thị một Follow. Các cạnh được định hướng để User 1 có thể theo dõi User 2, User 2 có thể theo dõi User 1 hoặc cả User 1 và User 2 có thể theo dõi nhau.

Đồ thị có hướng này cũng có thể được coi như là một đồ thị hai phần, trong đó các nút được nhóm thành hai tập, Producers và Consumers. Trong đồ thị hai phần này, Producers là người dùng đã được Follow và Consumers là những người được Follow. Dưới đây là một ví dụ đồ chơi về đồ thị theo dõi cho bốn người dùng:

<img src="images/bipartite_graph.png" width = "400px">

> Hình 1 - Trái: Một đồ thị theo dõi có hướng; Phải: Một biểu diễn đồ thị hai phần của đồ thị có hướng.

### Phát hiện community - Known For 
Đồ thị theo dõi hai phần có thể được sử dụng để xác định các nhóm Producer có người theo dõi tương tự nhau, hoặc được "Nổi tiếng vì" một chủ đề nào đó. Cụ thể, đồ thị hai phần này cũng có thể được biểu diễn dưới dạng ma trận *m x n* (*A*), trong đó Consumers được biểu diễn là *u* và Producers được biểu diễn là *v*.

Sự tương đồng giữa các Producer được tính toán dưới dạng tương đồng cosin giữa các người dùng theo dõi mỗi Producer. Giá trị tương đồng cosin kết quả có thể được sử dụng để xây dựng một đồ thị tương đồng Producer-Producer, trong đó các nút là các Producer và các cạnh có trọng số bằng giá trị tương đồng cosin tương ứng. Sau đó, thực hiện việc loại bỏ nhiễu, sao cho các cạnh có trọng số nhỏ hơn một ngưỡng được chỉ định sẽ bị xóa khỏi đồ thị.

Sau khi hoàn thành việc xóa nhiễu, thực hiện phát hiện community dựa trên mẫu Metropolis-Hastings trên đồ thị tương đồng Producer-Producer để xác định sự liên kết community cho mỗi Producer. Thuật toán này sẽ nhận vào một tham số *k* là số lượng community cần được phát hiện.

<img src="images/producer_producer_similarity.png">

> Hình 2 - Trái: Biểu diễn ma trận của đồ thị theo dõi được miêu tả trong Hình 1; Giữa: Tương đồng Producer-Producer được ước tính bằng cách tính toán tương đồng cosin giữa các người dùng theo dõi mỗi Producer; Phải: Điểm tương đồng cosin được sử dụng để tạo ra đồ thị tương đồng Producer-Producer. Một thuật toán phân cụm được chạy trên đồ thị để xác định các nhóm Producer có người theo dõi tương tự nhau.

Sau đó, các điểm liên kết community được sử dụng để xây dựng một ma trận *n x k* "Known For" (*V*). Ma trận này là tối đa thưa, và mỗi Producer liên kết với tối đa một community. Trong môi trường production, tập dữ liệu Known For bao phủ 20 triệu Producer hàng đầu và k ~ = 145.000. Nói cách khác, chúng ta khám phá khoảng 145k community dựa trên đồ thị theo dõi người dùng của Twitter.

<img src="images/knownfor.png">

> Hình 3 - Thuật toán phân cụm trả về các điểm liên kết community cho mỗi Producer. Các điểm này được biểu diễn trong ma trận V.

Trong ví dụ trên, Producer 1 thuộc community 2, Producer 2 thuộc community 1, và tiếp tục như vậy.

### Consumer Embeddings - User InterestedIn
Một ma trận Interested In (*U*) có thể được tính bằng cách nhân ma trận biểu diễn của đồ thị theo dõi (*A*) với ma trận Known For (*V*):

<img src="images/interestedin.png">

Trong ví dụ đơn giản này, Consumer 1 chỉ quan tâm đến community 1, trong khi Consumer 3 quan tâm đến cả ba community. Cũng có một bước loại bỏ nhiễu được áp dụng cho ma trận Quan tâm đến.

Chúng tôi sử dụng InterestedIn embeddings để thu thập thông tin về sở thích lâu dài của Consumer. InterestedIn embeddings là một trong những nguồn chính của chúng tôi cho các đề xuất tweet dựa trên Consumer.

### Producer Embeddings
Khi tính toán ma trận Known For, mỗi Producer chỉ có thể được biết đến trong một community duy nhất. Mặc dù ma trận tối đa thưa này hữu ích từ quan điểm tính toán, chúng ta biết rằng người dùng của chúng ta tweet về nhiều chủ đề khác nhau và có thể được "Known" trong nhiều community khác nhau. Producer embeddings  (*Ṽ*) được sử dụng để thu thập cấu trúc phong phú hơn của đồ thị.

Để tính toán nhúng Producer, tương đồng cosin được tính toán giữa đồ thị theo dõi của mỗi Producer và vector Quan tâm đến cho mỗi community.

<img src="images/producer_embeddings.png">

Producer embeddings được sử dụng cho các đề xuất tweet dựa trên Producer. Ví dụ như: chúng ta có thể đề xuất các tweet tương tự dựa trên một tài khoản mà bạn vừa theo dõi.

### Entity Embeddings
SimCluster cũng có thể được sử dụng để generate embeddings cho những loại nội dung khác nhau, như:
- Tweets (dùng cho Tweet recommendations)
- Topics (dùng cho TopicFollow)

#### Tweet embeddings
Khi một tweet được tạo, tweet embedding được khởi tạo như một vector rỗng. tweet embedding được cập nhật mỗi lần tweet được yêu thích. Cụ thể, vector InterestedIn của mỗi người dùng đã yêu thích tweet được thêm vào vector tweet.

Vì tweet embedding được cập nhật mỗi lần tweet được yêu thích, chúng thay đổi theo thời gian.

Tweet embeddings là rất quan trọng đối với các nhiệm vụ đề xuất tweet recommendation. Có thể tính toán độ tương đồng của tweet và đề xuất các tweet tương tự cho người dùng dựa trên lịch sử tương tác với tweet của họ.

Chúng tôi có một Heron job trực tuyến cập nhật nhúng tweet theo realtime, hãy xem tại [đây](summingbird/README.md) để biết thêm thông tin.

#### Topic embeddings
Topic embeddings (**R**) được xác định bằng cách lấy tương đồng cosin giữa consumers quan tâm đến community và số lượng yêu thích đã được tổng hợp mà mỗi consumerđã thực hiện trên một tweet tcó chú thích topic (với một chút thời gian bị decay)

<img src="images/topic_embeddings.png">


## Project Directory Overview
The whole SimClusters project can be understood as 2 main components
- SimClusters Offline Jobs (Scalding / GCP)
- SimClusters Real-time Streaming Jobs 

### SimClusters Offline Jobs

**SimClusters Scalding Jobs**

| Jobs   | Code  | Description  |
|---|---|---|
| KnownFor  |  [simclusters_v2/scalding/update_known_for/UpdateKnownFor20M145K2020.scala](scalding/update_known_for/UpdateKnownFor20M145K2020.scala) | Công việc đầu ra các tập dữ liệu KnownFor lưu trữ các mối quan hệ giữa clusterId và producerUserId. </n> Tập dữ liệu KnownFor bao phủ các Producer được theo dõi hàng đầu 20 triệu. Chúng tôi sử dụng tập dữ liệu KnownFor này (hoặc được gọi là các cụm) để xây dựng tất cả các nhúng thực thể khác. |
| InterestedIn Embeddings|  [simclusters_v2/scalding/InterestedInFromKnownFor.scala](scalding/InterestedInFromKnownFor.scala) | Đoạn mã này triển khai công việc tính toán  users' interestedIn embedding từ tập dữ liệu KnownFor. </n> Chúng tôi sử dụng tập dữ liệu này cho producer-based tweet recommendations. |
| Producer Embeddings  | [simclusters_v2/scalding/embedding/ProducerEmbeddingsFromInterestedIn.scala](scalding/embedding/ProducerEmbeddingsFromInterestedIn.scala)  | Đoạn mã này triển khai công việc tính toán producer embeddings, cái mà người dùng tạo ra được đại diện bởi các tweet. </n> Chúng tôi sử dụng tập dữ liệu này cho producer-based tweet recommendations. |
| Semantic Core Entity Embeddings  | [simclusters_v2/scalding/embedding/EntityToSimClustersEmbeddingsJob.scala](scalding/embedding/EntityToSimClustersEmbeddingsJob.scala)   | Công việc tính toán các nhúng thực thể trung tâm ngữ nghĩa (semantic core entity embeddings). Đầu ra của nó là tập dữ liệu được lưu trữ "SemanticCore entityId -> List(clusterId)" and "clusterId -> List(SemanticCore entityId))" relationships. |
| Topic Embeddings | [simclusters_v2/scalding/embedding/tfg/FavTfgBasedTopicEmbeddings.scala](scalding/embedding/tfg/FavTfgBasedTopicEmbeddings.scala)  | Công việc để tạo nhúng chủ đề dựa trên Topic-Follow-Graph (TFG) </n> A topic's fav-based TFG embedding is the sum of its followers' fav-based InterestedIn. We use this embedding for topic related recommendations. |

**SimClusters GCP Jobs**

Chúng ta có một GCP pipeline nơi mà chúng tôi xây dựng SimClusters ANN index thông qua BigQuery. Điều này cũng cho phép chúng ta có thể lặp lại nhanh và xây dựng embeddings mới hiệu quả hơn so với Scalding.

Tất cả SimClusters liên quan đến GCP jobs được viết tại đây [src/scala/com/twitter/simclusters_v2/scio/bq_generation](scio/bq_generation).

| Jobs   | Code  | Description  |
|---|---|---|
| PushOpenBased SimClusters ANN Index  |  [EngagementEventBasedClusterToTweetIndexGenerationJob.scala](scio/bq_generation/simclusters_index_generation/EngagementEventBasedClusterToTweetIndexGenerationJob.scala) | The job builds a clusterId -> TopTweet index based on user-open engagement history. </n> This SANN source is used for candidate generation for Notifications. |
| VideoViewBased SimClusters Index|  [EngagementEventBasedClusterToTweetIndexGenerationJob.scala](scio/bq_generation/simclusters_index_generation/EngagementEventBasedClusterToTweetIndexGenerationJob.scala) |  The job builds a clusterId -> TopTweet index based on the user's video view history. </n> This SANN source is used for video recommendation on Home.|

### SimClusters Real-Time Streaming Tweets Jobs

| Jobs   | Code  | Description  |
|---|---|---|
| Tweet Embedding Job |  [simclusters_v2/summingbird/storm/TweetJob.scala](summingbird/storm/TweetJob.scala) | Generate the Tweet embedding and index of tweets for the SimClusters |
| Persistent Tweet Embedding Job|  [simclusters_v2/summingbird/storm/PersistentTweetJob.scala](summingbird/storm/PersistentTweetJob.scala) |  Persistent the tweet embeddings from MemCache into Manhattan.|