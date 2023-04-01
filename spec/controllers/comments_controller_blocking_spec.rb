require "spec_helper"

describe CommentsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:blocked) { create(:user) }

  let(:comment_params) do
    {
      comment: {
        pseud_id: blocked.default_pseud.id,
        comment_content: "<p>Let me in.</p>"
      }
    }
  end

  let!(:existing_comment) do
    create(:comment,
           pseud: blocked.default_pseud,
           commentable: commentable)
  end

  let(:commentable_params) do
    if commentable.is_a?(Tag)
      { tag_id: commentable }
    else
      { commentable.model_name.param_key.foreign_key => commentable }
    end
  end

  before do
    Block.create(blocker: blocker, blocked: blocked)
    fake_login_known_user(blocked)
  end

  shared_examples "creating and editing comments is allowed" do
    describe "GET #new" do
      it "renders the template" do
        get :new, params: commentable_params

        expect(response).to render_template(:new)
      end
    end

    describe "POST #create" do
      it "creates the comment" do
        expect do
          post :create, params: commentable_params.merge(comment_params)
        end.to change { commentable.comments.count }.by(1)
      end
    end

    describe "GET #edit" do
      it "renders the template" do
        get :edit, params: { id: existing_comment }

        expect(response).to render_template(:edit)
      end
    end

    describe "PUT #update" do
      it "changes the comment" do
        expect do
          put :update, params: { id: existing_comment }.merge(comment_params)
        end.to change { existing_comment.reload.comment_content }.to("<p>Let me in.</p>")
      end
    end
  end

  shared_examples "creating and editing comments is not allowed" do |message:|
    describe "GET #new" do
      it "redirects with error" do
        get :new, params: commentable_params

        it_redirects_to_with_comment_error(failure_path, message)
      end
    end

    describe "POST #create" do
      it "doesn't create the comment, and redirects with error" do
        expect do
          post :create, params: commentable_params.merge(comment_params)
        end.not_to change { commentable.comments.count }

        it_redirects_to_with_comment_error(failure_path, message)
      end
    end

    describe "GET #edit" do
      it "redirects with error" do
        get :edit, params: { id: existing_comment }

        it_redirects_to_with_comment_error(failure_path, message)
      end
    end

    describe "PUT #update" do
      it "doesn't change the comment, and redirects with error" do
        expect do
          put :update, params: { id: existing_comment }.merge(comment_params)
        end.not_to change { existing_comment.reload.comment_content }

        it_redirects_to_with_comment_error(failure_path, message)
      end
    end
  end

  shared_examples "the reply button works" do
    describe "GET #add_comment_reply" do
      it "renders the template" do
        get :add_comment_reply,
            params: commentable_params.merge(id: commentable),
            format: :js, xhr: true

        expect(response).to render_template(:add_comment_reply)
      end
    end
  end

  shared_examples "the reply button doesn't work" do |message:|
    describe "GET #add_comment_reply" do
      it "redirects with error" do
        get :add_comment_reply,
            params: commentable_params.merge(id: commentable),
            format: :js, xhr: true

        it_redirects_to_with_comment_error(failure_path, message)
      end
    end
  end

  shared_examples "deleting comments is allowed" do
    describe "DELETE #destroy" do
      it "successfully deletes the comment" do
        expect do
          delete :destroy, params: { id: existing_comment }
        end.to change { commentable.comments.count }.by(-1)
      end

      context "when the comment has a reply" do
        let!(:reply) { create(:comment, commentable: existing_comment) }

        it "successfully marks the comment as deleted" do
          expect do
            delete :destroy, params: { id: existing_comment }
          end.to change { existing_comment.reload.is_deleted }.to true
        end
      end
    end
  end

  context "when the commenter is blocked by the work's owner" do
    let(:work) { create(:work) }
    let(:blocker) { work.users.first }
    let(:failure_path) { work_path(work, show_comments: true, anchor: :comments) }

    context "when commenting directly on the work" do
      let(:commentable) { work.first_chapter }

      it_behaves_like "creating and editing comments is not allowed",
                      message: "Sorry, you have been blocked by one or more of this work's creators."
      it_behaves_like "deleting comments is allowed"
    end

    context "when replying to someone else's comment on the work" do
      let(:commentable) { create(:comment, commentable: work.first_chapter) }

      it_behaves_like "creating and editing comments is not allowed",
                      message: "Sorry, you have been blocked by one or more of this work's creators."
      it_behaves_like "the reply button doesn't work",
                      message: "Sorry, you have been blocked by one or more of this work's creators."
      it_behaves_like "deleting comments is allowed"
    end
  end

  context "when the commenter shares the work with their blocker" do
    let(:blocker) { create(:user) }
    let(:work) { create(:work, authors: [blocker.default_pseud, blocked.default_pseud]) }
    let(:failure_path) { work_path(work, show_comments: true, anchor: :comments) }

    context "when commenting directly on the work" do
      let(:commentable) { work.first_chapter }

      it_behaves_like "creating and editing comments is allowed"
      it_behaves_like "deleting comments is allowed"
    end

    context "when replying to someone else's comment on the work" do
      let(:commentable) { create(:comment, commentable: work.first_chapter) }

      it_behaves_like "creating and editing comments is allowed"
      it_behaves_like "the reply button works"
      it_behaves_like "deleting comments is allowed"
    end

    context "when replying to their blocker on their shared work" do
      let(:commentable) { create(:comment, pseud: blocker.default_pseud, commentable: work.first_chapter) }

      it_behaves_like "creating and editing comments is not allowed",
                      message: "Sorry, you have been blocked by that user."
      it_behaves_like "the reply button doesn't work",
                      message: "Sorry, you have been blocked by that user."
      it_behaves_like "deleting comments is allowed"
    end
  end

  context "when the commenter is blocked by the person they're replying to" do
    let(:blocker) { commentable.user }

    context "on a work" do
      let(:commentable) { create(:comment) }
      let(:failure_path) { work_path(commentable.ultimate_parent, show_comments: true, anchor: :comments) }

      it_behaves_like "creating and editing comments is not allowed",
                      message: "Sorry, you have been blocked by that user."
      it_behaves_like "the reply button doesn't work",
                      message: "Sorry, you have been blocked by that user."
      it_behaves_like "deleting comments is allowed"
    end

    context "on an admin post" do
      let(:commentable) { create(:comment, :on_admin_post) }
      let(:failure_path) { admin_post_path(commentable.parent, show_comments: true, anchor: :comments) }

      it_behaves_like "creating and editing comments is not allowed",
                      message: "Sorry, you have been blocked by that user."
      it_behaves_like "the reply button doesn't work",
                      message: "Sorry, you have been blocked by that user."
      it_behaves_like "deleting comments is allowed"
    end

    context "on a tag" do
      let(:blocked) { create(:tag_wrangler) }
      let(:commentable) { create(:comment, :on_tag) }
      let(:failure_path) { comments_path(tag_id: commentable.parent, anchor: :comments) }

      it_behaves_like "creating and editing comments is allowed"
      it_behaves_like "the reply button works"
      it_behaves_like "deleting comments is allowed"
    end
  end
end
