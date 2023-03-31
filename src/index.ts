interface Post {
  author: string;
  content: string;
}

class TwitterRecommandation {
  private posts: Post[];

  constructor() {
    this.posts = [];
  }

  addPost(post: Post) {
    this.posts.push(post);
  }

  getRecommendedPosts() {
    return this.posts.filter(post => post.author === "Elon Musk");
  }
}

const app = new TwitterRecommandation();

const recommendedPosts = app.getRecommendedPosts();