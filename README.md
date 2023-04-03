# Twitter Recommendation Algorithm (VN)

The Twitter Recommendation Algorithm là một tập hợp các dịch vụ và công việc đảm nhận việc xây dựng và cung cấp Dòng thời trang ở trang chủ (Home Timeline). Để tìm hiểu cách giải thuật hoạt động, vui lòng xem bài viết trên [engineering blog](https://blog.twitter.com/engineering/en_us/topics/open-source/2023/twitter-recommendation-algorithm). Hình vẽ bên dưới mô tả cách các dịch vụ và công việc chính liên kết với nhau.

![](docs/system-diagram.png)

Đây là các thành phần chính của Recommendation Algorithm  được bao gồm trong kho lưu trữ này:

| Type | Component | Description |
|------------|------------|------------|
| Chức năng | [SimClusters](src/scala/com/twitter/simclusters_v2/README.md) | Phát hiện cộng đồng và nhúng rãi rác vào những cộng đồng đó. |
|         | [TwHIN](https://github.com/twitter/the-algorithm-ml/blob/main/projects/twhin/README.md) | Nhúng *knowledge graph* cho User và Tweet. |
|         | [trust-and-safety-models](trust_and_safety_models/README.md) | Các mô hình để phát hiện nội dung NSFW hoặc lạm dụng. |
|         | [real-graph](src/scala/com/twitter/interaction_graph/README.md) | Mô hình dự đoán khả năng tương tác của người dùng Twitter với người dùng khác. |
|         | [tweepcred](src/scala/com/twitter/graph/batch/job/tweepcred/README) | Thuật toán PageRank để tính toán sự uy tín của người dùng Twitter. |
|         | [recos-injector](recos-injector/README.md) | Bộ xử lý sự kiện trực tiếp để xây dựng các luồng đầu vào cho các dịch vụ dựa trên [GraphJet](https://github.com/twitter/GraphJet) |
|         | [graph-feature-service](graph-feature-service/README.md) | Cung cấp các tính năng *graph* cho một cặp Người dùng trực tiếp. (ví dụ: bao nhiêu người theo dõi của Người dùng A đã thích Tweet từ Người dùng B). |
| Candidate Source | [search-index](src/java/com/twitter/search/README.md) | Tìm kiếm và xếp hạng các Tweet trong mạng nội bộ. Khoảng 50% Tweet đến từ nguồn ứng cử viên này. |
|                  | [cr-mixer](cr-mixer/README.md) | Lớp điều phối để lấy các ứng cử viên Tweet Ngoài mạng từ các dịch vụ tính toán cơ bản. |
|                  | [user-tweet-entity-graph](src/scala/com/twitter/recos/user_tweet_entity_graph/README.md) (UTEG)| Dùng để duy trì đồ thị tương tác Người dùng với Tweet trong bộ nhớ và tìm kiếm các ứng cử viên dựa trên điều tra của đồ thị này. Được xây dựng trên khung [GraphJet](https://github.com/twitter/GraphJet). Một số tính năng và nguồn ứng cử viên khác dựa trên GraphJet được đặt tại [đây](src/scala/com/twitter/recos) (src / scala / com / twitter / recos). |
|                  | [follow-recommendation-service](follow-recommendations-service/README.md) (FRS)| Cung cấp người dùng các gợi ý về tài khoản để theo dõi và các Tweet từ những tài khoản đó. |
| Ranking | [light-ranker](src/python/twitter/deepbird/projects/timelines/scripts/models/earlybird/README.md) | Mô hình xếp hạng nhẹ được sử dụng bởi chỉ mục tìm kiếm (Earlybird) để xếp hạng các Tweet. |
|         | [heavy-ranker](https://github.com/twitter/the-algorithm-ml/blob/main/projects/home/recap/README.md) | Mạng nơ-ron để xếp hạng các ứng cử viên Tweet. Một trong những tín hiệu chính được sử dụng để lựa chọn các Tweet trên dòng thời gian sau khi thu thập ứng cử viên. |
| Tweet mixing & filtering | [home-mixer](home-mixer/README.md) | Dịch vụ chính được sử dụng để xây dựng và phục vụ Dòng thời gian Trang chủ. Xây dựng trên [product-mixer](product-mixer/README.md) |
|                          | [visibility-filters](visibilitylib/README.md) | Chịu trách nhiệm lọc nội dung Twitter để hỗ trợ tuân thủ những quy định, cải thiện chất lượng sản phẩm, tăng sự tin tưởng của người dùng, bảo vệ doanh thu thông qua việc sử dụng bộ lọc cứng, các xử lý sản phẩm hiển thị rõ ràng và giảm xếp hạng thô. |
|                          | [timelineranker](timelineranker/README.md) | Dịch vụ kế thừa cung cấp các Tweet được tính điểm liên quan từ chỉ mục tìm kiếm Earlybird và dịch vụ UTEG. |
| Software framework | [navi](navi/navi/README.md) | Mô hình học máy hiệu suất cao được viết bằng Rust. |
|                    | [product-mixer](product-mixer/README.md) | Framework để build nội dung của feeds (bản tin) |
|                    | [twml](twml/README.md) | Machine learning framework được kế thừa và xây dựng trên TensorFlow v1 |

We include Bazel BUILD files for most components, but not a top level BUILD or WORKSPACE file.

Chúng tôi bao gồm các tệp BUILD Bazel cho hầu hết các components, nhưng không bao gồm tệp top level của BUILD hoặc WORKSPACE.

