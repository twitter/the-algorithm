require "spec_helper"

describe CommentsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:comment) { create(:comment) }
  let(:unreviewed_comment) { create(:comment, :unreviewed) }

  before do
    request.env["HTTP_REFERER"] = "/where_i_came_from"
  end

  describe "GET #add_comment_reply" do
    context "when comment is unreviewed" do
      it "redirects logged out user to login path with an error" do
        get :add_comment_reply, params: { comment_id: unreviewed_comment.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you cannot reply to an unapproved comment.")
      end

      it "redirects logged in user to root path with an error" do
        fake_login
        get :add_comment_reply, params: { comment_id: unreviewed_comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you cannot reply to an unapproved comment.")
      end
    end

    context "when comment is not unreviewed" do
      it "redirects to the comment on the commentable without an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        expect(flash[:error]).to be_nil
        expect(response).to redirect_to(chapter_path(comment.commentable, show_comments: true, anchor: "comment_#{comment.id}"))
      end

      it "redirects to the comment on the commentable with the reply form open and without an error" do
        get :add_comment_reply, params: { comment_id: comment.id, id: comment.id }
        expect(flash[:error]).to be_nil
        expect(response).to redirect_to(chapter_path(comment.commentable, add_comment_reply_id: comment.id, show_comments: true, anchor: "comment_#{comment.id}"))
      end
    end

    shared_examples "no one can add comment reply on a frozen comment" do
      it "redirects logged out user with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error("/where_i_came_from", "Sorry, you cannot reply to a frozen comment.")
      end

      it "redirects logged in user with an error" do
        fake_login
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error("/where_i_came_from", "Sorry, you cannot reply to a frozen comment.")
      end
    end

    context "when comment is frozen" do
      context "when commentable is an admin post" do
        let(:comment) { create(:comment, :on_admin_post, iced: true) }

        it_behaves_like "no one can add comment reply on a frozen comment"
      end

      context "when commentable is a tag" do
        let(:comment) { create(:comment, :on_tag, iced: true) }

        it_behaves_like "no one can add comment reply on a frozen comment"
      end

      context "when commentable is a work" do
        let(:comment) { create(:comment, iced: true) }

        it_behaves_like "no one can add comment reply on a frozen comment"
      end
    end

    shared_examples "no one can add comment reply on a hidden comment" do
      it "redirects logged out user with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error("/where_i_came_from", "Sorry, you cannot reply to a hidden comment.")
      end

      it "redirects logged in user with an error" do
        fake_login
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error("/where_i_came_from", "Sorry, you cannot reply to a hidden comment.")
      end
    end

    context "when comment is hidden by admin" do
      context "when commentable is an admin post" do
        let(:comment) { create(:comment, :on_admin_post, hidden_by_admin: true) }

        it_behaves_like "no one can add comment reply on a hidden comment"
      end

      context "when commentable is a tag" do
        let(:comment) { create(:comment, :on_tag, hidden_by_admin: true) }

        it_behaves_like "no one can add comment reply on a hidden comment"
      end

      context "when commentable is a work" do
        let(:comment) { create(:comment, hidden_by_admin: true) }

        it_behaves_like "no one can add comment reply on a hidden comment"
      end
    end
  end

  describe "GET #unreviewed" do
    let!(:user) { create(:user) }
    let!(:work) { create(:work, authors: [user.default_pseud], moderated_commenting_enabled: true) }
    let(:comment) { create(:comment, :unreviewed, commentable: work.first_chapter) }

    it "redirects logged out users to login path with an error" do
      get :unreviewed, params: { comment_id: comment.id, work_id: work.id }
      it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to see those unreviewed comments.")
    end

    it "redirects to root path with an error when logged in user does not own the commentable" do
      fake_login
      get :unreviewed, params: { comment_id: comment.id, work_id: work.id }
      it_redirects_to_with_error(root_path, "Sorry, you don't have permission to see those unreviewed comments.")
    end

    it "renders the :unreviewed template for a user who owns the work" do
      fake_login_known_user(user)
      get :unreviewed, params: { work_id: work.id }
      expect(response).to render_template("unreviewed")
    end

    it "renders the :unreviewed template for an admin" do
      fake_login_admin(create(:admin))
      get :unreviewed, params: { work_id: work.id }
      expect(response).to render_template("unreviewed")
    end
  end

  describe "POST #new" do
    it "errors if the commentable is not a valid tag" do
      post :new, params: { tag_id: "Non existent tag" }
      expect(flash[:error]).to eq "What did you want to comment on?"
    end

    it "renders the :new template if commentable is a valid admin post" do
      admin_post = create(:admin_post)
      post :new, params: { admin_post_id: admin_post.id }
      expect(response).to render_template("new")
      expect(assigns(:name)).to eq(admin_post.title)
    end

    context "when the commentable is a valid tag" do
      let(:fandom) { create(:fandom) }

      context "when logged in as an admin" do
        before { fake_login_admin(create(:admin)) }

        it "renders the :new template" do
          post :new, params: { tag_id: fandom.name }
          expect(response).to render_template("new")
          expect(assigns(:name)).to eq("Fandom")
        end
      end

      context "when logged in as a tag wrangler" do
        before { fake_login_known_user(create(:tag_wrangler)) }

        it "renders the :new template" do
          post :new, params: { tag_id: fandom.name }
          expect(response).to render_template("new")
          expect(assigns(:name)).to eq("Fandom")
        end
      end

      context "when logged in as a random user" do
        before { fake_login }

        it "shows an error and redirects" do
          post :new, params: { tag_id: fandom.name }
          it_redirects_to_with_error(user_path(controller.current_user),
                                     "Sorry, you don't have permission to " \
                                     "access the page you were trying to " \
                                     "reach.")
        end
      end

      context "when logged out" do
        before { fake_logout }

        it "shows an error and redirects" do
          post :new, params: { tag_id: fandom.name }
          it_redirects_to_with_error(new_user_session_path,
                                     "Sorry, you don't have permission to " \
                                     "access the page you were trying to " \
                                     "reach. Please log in.")
        end
      end
    end

    it "renders the :new template if commentable is a valid comment" do
      comment = create(:comment)
      post :new, params: { comment_id: comment.id }
      expect(response).to render_template("new")
      expect(assigns(:name)).to eq("Previous Comment")
    end

    it "shows an error and redirects if commentable is a frozen comment" do
      comment = create(:comment, iced: true)
      post :new, params: { comment_id: comment.id }
      it_redirects_to_with_error("/where_i_came_from", "Sorry, you cannot reply to a frozen comment.")
    end

    it "shows an error and redirects if commentable is a hidden comment" do
      comment = create(:comment, hidden_by_admin: true)
      post :new, params: { comment_id: comment.id }
      it_redirects_to_with_error("/where_i_came_from", "Sorry, you cannot reply to a hidden comment.")
    end
  end

  describe "POST #create" do
    let(:anon_comment_attributes) do
      attributes_for(:comment, :by_guest).slice(:name, :email, :comment_content)
    end

    context "when replying from the inbox" do
      let!(:user) { create(:user) }
      let!(:comment) { create(:comment) }

      before do
        fake_login_known_user(user)
        request.env["HTTP_REFERER"] = user_inbox_path(user)
      end

      it "creates the reply and redirects to user inbox path" do
        comment_attributes = {
          pseud_id: user.default_pseud_id,
          comment_content: "Hello fellow human!"
        }
        post :create, params: { comment_id: comment.id, comment: comment_attributes, filters: { date: "asc" } }
        expect(response).to redirect_to(user_inbox_path(user, filters: { date: "asc" }))
        expect(flash[:comment_notice]).to eq "Comment created!"
      end
    end

    context "when the commentable is a valid tag" do
      let(:fandom) { create(:fandom) }

      context "when logged in as an admin" do
        before { fake_login_admin(create(:admin)) }

        it "posts the comment and shows it in context" do
          post :create, params: { tag_id: fandom.name, comment: anon_comment_attributes }
          comment = Comment.last
          expect(comment.commentable).to eq fandom
          expect(comment.name).to eq anon_comment_attributes[:name]
          expect(comment.email).to eq anon_comment_attributes[:email]
          expect(comment.comment_content).to include anon_comment_attributes[:comment_content]
          path = comments_path(tag_id: fandom.to_param,
                               anchor: "comment_#{comment.id}")
          expect(response).to redirect_to path
        end
      end

      context "when logged in as a tag wrangler" do
        before { fake_login_known_user(create(:tag_wrangler)) }

        it "posts the comment and shows it in context" do
          post :create, params: { tag_id: fandom.name, comment: anon_comment_attributes }
          comment = Comment.last
          expect(comment.commentable).to eq fandom
          expect(comment.name).to eq anon_comment_attributes[:name]
          expect(comment.email).to eq anon_comment_attributes[:email]
          expect(comment.comment_content).to include anon_comment_attributes[:comment_content]
          path = comments_path(tag_id: fandom.to_param,
                               anchor: "comment_#{comment.id}")
          expect(response).to redirect_to path
        end
      end

      context "when logged in as a random user" do
        before { fake_login }

        it "shows an error and redirects" do
          post :create, params: { tag_id: fandom.name, comment: anon_comment_attributes }
          it_redirects_to_with_error(user_path(controller.current_user),
                                     "Sorry, you don't have permission to " \
                                     "access the page you were trying to " \
                                     "reach.")
        end
      end

      context "when logged out" do
        before { fake_logout }

        it "shows an error and redirects" do
          post :create, params: { tag_id: fandom.name, comment: anon_comment_attributes }
          it_redirects_to_with_error(new_user_session_path,
                                     "Sorry, you don't have permission to " \
                                     "access the page you were trying to " \
                                     "reach. Please log in.")
        end
      end
    end

    context "when the commentable is a work" do
      context "when the work is restricted" do
        let(:work) { create(:work, restricted: true) }

        it "redirects to the login page" do
          post :create, params: { work_id: work.id, comment: anon_comment_attributes }
          it_redirects_to(new_user_session_path(restricted_commenting: true))
        end
      end

      context "when the work has all comments disabled" do
        let(:work) { create(:work, comment_permissions: :disable_all) }

        it "shows an error and redirects" do
          post :create, params: { work_id: work.id, comment: anon_comment_attributes }
          it_redirects_to_with_error(work_path(work),
                                     "Sorry, this work doesn't allow comments.")
        end

        it "sets flash_is_set to bypass caching" do
          post :create, params: { work_id: work.id, comment: anon_comment_attributes }
          expect(cookies[:flash_is_set]).to eq(1)
        end
      end

      context "when the work has anonymous comments disabled" do
        let(:work) { create(:work, comment_permissions: :disable_anon) }

        it "shows an error and redirects" do
          post :create, params: { work_id: work.id, comment: anon_comment_attributes }
          it_redirects_to_with_error(work_path(work),
                                     "Sorry, this work doesn't allow non-Archive users to comment.")
        end

        it "sets flash_is_set to bypass caching" do
          post :create, params: { work_id: work.id, comment: anon_comment_attributes }
          expect(cookies[:flash_is_set]).to eq(1)
        end
      end
    end

    context "when the commentable is an admin post" do
      context "where all comments are disabled" do
        let(:admin_post) { create(:admin_post, comment_permissions: :disable_all) }

        it "shows an error and redirects" do
          post :create, params: { admin_post_id: admin_post.id, comment: anon_comment_attributes }
          it_redirects_to_with_error(admin_post_path(admin_post),
                                     "Sorry, this news post doesn't allow comments.")
        end
      end

      context "where anonymous comments are disabled" do
        let(:admin_post) { create(:admin_post, comment_permissions: :disable_anon) }

        it "shows an error and redirects" do
          post :create, params: { admin_post_id: admin_post.id, comment: anon_comment_attributes }
          it_redirects_to_with_error(admin_post_path(admin_post),
                                     "Sorry, this news post doesn't allow non-Archive users to comment.")
        end
      end
    end

    context "when the commentable is a comment" do
      context "on a parent work" do
        context "where all comments are disabled" do
          let(:work) { create(:work, comment_permissions: :disable_all) }
          let(:comment) { create(:comment, commentable: work.first_chapter) }

          it "shows an error and redirects" do
            post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
            it_redirects_to_with_error(work_path(work),
                                       "Sorry, this work doesn't allow comments.")
          end
        end

        context "where anonymous comments are disabled" do
          let(:work) { create(:work, comment_permissions: :disable_anon) }
          let(:comment) { create(:comment, commentable: work.first_chapter) }

          it "shows an error and redirects" do
            post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
            it_redirects_to_with_error(work_path(work),
                                       "Sorry, this work doesn't allow non-Archive users to comment.")
          end
        end
      end

      context "on an admin post" do
        context "where all comments are disabled" do
          let(:admin_post) { create(:admin_post, comment_permissions: :disable_all) }
          let(:comment) { create(:comment, commentable: admin_post) }

          it "shows an error and redirects" do
            post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
            it_redirects_to_with_error(admin_post_path(admin_post),
                                       "Sorry, this news post doesn't allow comments.")
          end
        end

        context "where anonymous comments are disabled" do
          let(:admin_post) { create(:admin_post, comment_permissions: :disable_anon) }
          let(:comment) { create(:comment, commentable: admin_post) }

          it "shows an error and redirects" do
            post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
            it_redirects_to_with_error(admin_post_path(admin_post),
                                       "Sorry, this news post doesn't allow non-Archive users to comment.")
          end
        end
      end

      context "when the commentable is frozen" do
        let(:comment) { create(:comment, iced: true) }

        it "shows an error and redirects" do
          post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
          it_redirects_to_with_error("/where_i_came_from",
                                     "Sorry, you cannot reply to a frozen comment.")
        end
      end

      context "when the commentable is hidden" do
        let(:comment) { create(:comment, hidden_by_admin: true) }

        it "shows an error and redirects" do
          post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
          it_redirects_to_with_error("/where_i_came_from",
                                     "Sorry, you cannot reply to a hidden comment.")
        end
      end

      context "when the commentable is spam" do
        let(:spam_comment) { create(:comment) }

        before { spam_comment.update_attribute(:approved, false) }

        it "shows an error and redirects if commentable is a comment marked as spam" do
          post :create, params: { comment_id: spam_comment.id, comment: anon_comment_attributes }

          it_redirects_to_with_error("/where_i_came_from",
                                     "Sorry, you can't reply to a comment that has been marked as spam.")
        end
      end
    end
  end

  describe "PUT #review_all" do
    it "redirects to root path with an error if current user does not own the commentable" do
      fake_login
      put :review_all, params: { work_id: unreviewed_comment.commentable.work_id }
      it_redirects_to_with_error(root_path, "What did you want to review comments on?")
    end
  end

  describe "PUT #approve" do
    before { comment.update_column(:approved, false) }

    context "when logged-in as admin without a role" do
      before { fake_login_admin(create(:admin)) }

      it "leaves the comment marked as spam and redirects with an error" do
        put :approve, params: { id: comment.id }
        expect(comment.reload.approved).to be_falsey
        it_redirects_to_with_error(
          root_path,
          "Sorry, only an authorized admin can access the page you were trying to reach."
        )
      end
    end

    context "when logged-in as admin with authorized role" do
      before { fake_login_admin(create(:superadmin)) }

      it "marks the comment as not spam" do
        put :approve, params: { id: comment.id }
        expect(flash[:error]).to be_nil
        expect(response).to redirect_to(work_path(comment.ultimate_parent,
                                                  show_comments: true,
                                                  anchor: "comments"))
        expect(comment.reload.approved).to be_truthy
      end
    end

    context "when logged-in as the work's creator" do
      before { fake_login_known_user(comment.ultimate_parent.users.first) }

      it "leaves the comment marked as spam and redirects with an error" do
        put :approve, params: { id: comment.id }
        expect(comment.reload.approved).to be_falsey
        it_redirects_to_with_error(
          root_path,
          "Sorry, only an authorized admin can access the page you were trying to reach."
        )
      end
    end

    context "when logged-in as the comment writer" do
      before { fake_login_known_user(comment.pseud.user) }

      it "leaves the comment marked as spam and redirects with an error" do
        put :approve, params: { id: comment.id }
        expect(comment.reload.approved).to be_falsey
        it_redirects_to_with_error(
          root_path,
          "Sorry, you don't have permission to moderate that comment."
        )
      end
    end

    context "when logged-in as a random user" do
      before { fake_login }

      it "leaves the comment marked as spam and redirects with an error" do
        put :approve, params: { id: comment.id }
        expect(comment.reload.approved).to be_falsey
        it_redirects_to_with_error(
          root_path,
          "Sorry, you don't have permission to moderate that comment."
        )
      end
    end

    context "when not logged-in" do
      before { fake_logout }

      it "leaves the comment marked as spam and redirects with an error" do
        put :approve, params: { id: comment.id }
        expect(comment.reload.approved).to be_falsey
        it_redirects_to_with_error(
          new_user_session_path,
          "Sorry, you don't have permission to moderate that comment."
        )
      end
    end
  end

  describe "PUT #reject" do
    shared_examples "marking a comment spam" do
      context "when logged-in as admin" do
        let(:admin) { create(:admin) }

        it "fails to mark the comment as spam if admin does not have correct role" do
          admin.update(roles: [])
          fake_login_admin(admin)
          put :reject, params: { id: comment.id }
          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end

        it "marks the comment as spam when admin has correct role" do
          admin.update(roles: ["policy_and_abuse"])
          fake_login_admin(admin)
          put :reject, params: { id: comment.id }
          expect(flash[:error]).to be_nil
          expect(response).to redirect_to(work_path(comment.ultimate_parent,
                                                    show_comments: true,
                                                    anchor: "comments"))
          expect(comment.reload.approved).to be_falsey
        end
      end

      context "when logged-in as the work's creator" do
        before { fake_login_known_user(comment.ultimate_parent.users.first) }

        it "marks the comment as spam" do
          put :reject, params: { id: comment.id }
          expect(flash[:error]).to be_nil
          expect(response).to redirect_to(work_path(comment.ultimate_parent,
                                                    show_comments: true,
                                                    anchor: "comments"))
          expect(comment.reload.approved).to be_falsey
        end
      end

      context "when logged-in as the comment writer" do
        before { fake_login_known_user(comment.pseud.user) }

        it "doesn't mark the comment as spam and redirects with an error" do
          put :reject, params: { id: comment.id }
          expect(comment.reload.approved).to be_truthy
          it_redirects_to_with_error(
            root_path,
            "Sorry, you don't have permission to moderate that comment."
          )
        end
      end

      context "when logged-in as a random user" do
        before { fake_login }

        it "doesn't mark the comment as spam and redirects with an error" do
          put :reject, params: { id: comment.id }
          expect(comment.reload.approved).to be_truthy
          it_redirects_to_with_error(
            root_path,
            "Sorry, you don't have permission to moderate that comment."
          )
        end
      end

      context "when not logged-in" do
        before { fake_logout }

        it "doesn't mark the comment as spam and redirects with an error" do
          put :reject, params: { id: comment.id }
          expect(comment.reload.approved).to be_truthy
          it_redirects_to_with_error(
            new_user_session_path,
            "Sorry, you don't have permission to moderate that comment."
          )
        end
      end
    end

    it_behaves_like "marking a comment spam"

    context "when comment is frozen" do
      let(:comment) { create(:comment, iced: true) }

      it_behaves_like "marking a comment spam"
    end
  end

  describe "PUT #freeze" do
    context "when comment is not frozen" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post) }

        context "when logged out" do
          it "doesn't freeze comment and redirects with error" do
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "freezes comment and redirects with success message" do
            fake_login_admin(admin)
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_comment_notice(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment thread successfully frozen!"
            )
          end
        end

        context "when logged in as a user" do
          it "doesn't freeze comment and redirects with error" do
            fake_login
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag) }

        context "when logged out" do
          it "doesn't freeze comment and redirects with error" do
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't freeze comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :freeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "freezes comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :freeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_truthy
                it_redirects_to_with_comment_notice(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Comment thread successfully frozen!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't freeze comment and redirects with error" do
            fake_login
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "doesn't freeze comment and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment) }

        context "when logged out" do
          it "doesn't freeze comment and redirects with error" do
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't freeze comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :freeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "freezes comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :freeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_truthy
                it_redirects_to_with_comment_notice(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Comment thread successfully frozen!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't freeze comment and redirects with error" do
            fake_login
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "freezes the comment and redirects with success message" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_comment_notice(
              work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment thread successfully frozen!"
            )
          end
        end
      end

      context "when comment is the start of a thread" do
        let!(:comment) { create(:comment) }
        let!(:child1) { create(:comment, commentable: comment) }
        let!(:grandchild) { create(:comment, commentable: child1) }
        let!(:child2) { create(:comment, commentable: comment) }

        it "freezes entire thread and redirects with success message" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          [comment, child1, child2, grandchild].each do |comment|
            expect(comment.reload.iced).to be_truthy
          end
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully frozen!"
          )
        end
      end

      context "when comment is the middle of a thread" do
        let!(:parent) { create(:comment) }
        let!(:comment) { create(:comment, commentable: parent) }
        let!(:child) { create(:comment, commentable: comment) }
        let!(:sibling) { create(:comment, commentable: parent) }

        it "freezes the comment and its child, but not its parent or sibling, and redirects with success message" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          expect(comment.reload.iced).to be_truthy
          expect(child.reload.iced).to be_truthy
          expect(parent.reload.iced).to be_falsey
          expect(sibling.reload.iced).to be_falsey
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully frozen!"
          )
        end
      end

      context "when comment is the end of a thread" do
        let!(:parent) { create(:comment) }
        let!(:child1) { create(:comment, commentable: parent) }
        let!(:child2) { create(:comment, commentable: parent) }
        let!(:comment) { create(:comment, commentable: child1) }

        it "freezes the comment, but no other comments in the thread, and redirects with success message" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          expect(parent.reload.iced).to be_falsey
          expect(child1.reload.iced).to be_falsey
          expect(child2.reload.iced).to be_falsey
          expect(comment.reload.iced).to be_truthy
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully frozen!"
          )
        end
      end

      context "when comment is spam" do
        let(:comment) { create(:comment) }

        before { comment.update_attribute(:approved, false) }

        it "freezes the comment and redirects with success message without changing the approved status" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          expect(comment.reload.iced).to be_truthy
          expect(comment.reload.approved).to be_falsey
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully frozen!"
          )
        end
      end

      context "when comment is not saved" do
        let!(:comment) { create(:comment) }

        before do
          allow_any_instance_of(Comment).to receive(:save).and_return(false)
        end

        it "redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be frozen."
          )
        end
      end
    end

    context "when comment is frozen" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post, iced: true) }

        context "when logged out" do
          it "leaves comment frozen and redirects with error" do
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "leaves comment frozen and redirects with error" do
            fake_login_admin(admin)
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_comment_error(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Sorry, that comment thread could not be frozen."
            )
          end
        end

        context "when logged in as a user" do
          it "leaves comment frozen and redirects with error" do
            fake_login
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag, iced: true) }

        context "when logged out" do
          it "leaves comment frozen and redirects with error" do
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment frozen and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :freeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment frozen and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :freeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_truthy
                it_redirects_to_with_comment_error(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Sorry, that comment thread could not be frozen."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment frozen and redirects with error" do
            fake_login
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "leaves comment frozen and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment, iced: true) }

        context "when logged out" do
          it "leaves comment frozen and redirects with error" do
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment frozen and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :freeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment frozen and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :freeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_truthy
                it_redirects_to_with_comment_error(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Sorry, that comment thread could not be frozen."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment frozen and redirects with error" do
            fake_login
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "leaves comment frozen and redirects with error" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :freeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_comment_error(
              work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Sorry, that comment thread could not be frozen."
            )
          end
        end
      end

      context "when comment is the start of a thread" do
        let!(:comment) { create(:comment, iced: true) }
        let!(:child1) { create(:comment, commentable: comment, iced: true) }
        let!(:grandchild) { create(:comment, commentable: child1, iced: true) }
        let!(:child2) { create(:comment, commentable: comment, iced: true) }

        it "leaves thread frozen and redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          [comment, child1, child2, grandchild].each do |comment|
            expect(comment.reload.iced).to be_truthy
          end
          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be frozen."
          )
        end
      end

      context "when comment is the middle of a thread" do
        let!(:parent) { create(:comment, iced: true) }
        let!(:comment) { create(:comment, commentable: parent, iced: true) }
        let!(:child) { create(:comment, commentable: comment, iced: true) }
        let!(:sibling) { create(:comment, commentable: parent, iced: true) }

        it "leaves the comment and its child frozen, as well as its parent and sibling, and redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          [comment, child, parent, sibling].each do |comment|
            expect(comment.reload.iced).to be_truthy
          end
          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be frozen."
          )
        end
      end

      context "when comment is the end of a thread" do
        let!(:parent) { create(:comment, iced: true) }
        let!(:child1) { create(:comment, commentable: parent, iced: true) }
        let!(:child2) { create(:comment, commentable: parent, iced: true) }
        let!(:comment) { create(:comment, commentable: child1, iced: true) }

        it "leaves the comment frozen, along with any other comments in the thread, and redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          [comment, parent, child1, child2].each do |comment|
            expect(comment.reload.iced).to be_truthy
          end
          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be frozen."
          )
        end
      end

      context "when comment is not saved" do
        let!(:comment) { create(:comment, iced: true) }

        before do
          allow_any_instance_of(Comment).to receive(:save).and_return(false)
        end

        it "redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :freeze, params: { id: comment.id }

          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be frozen."
          )
        end
      end
    end
  end

  describe "PUT #unfreeze" do
    context "when comment is not frozen" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post) }

        context "when logged out" do
          it "leaves comment unfrozen and redirects with error" do
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "leaves comment unfrozen and redirects with error" do
            fake_login_admin(admin)
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_comment_error(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Sorry, that comment thread could not be unfrozen."
            )
          end
        end

        context "when logged in as a user" do
          it "leaves comment unfrozen and redirects with error" do
            fake_login
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag) }

        context "when logged out" do
          it "leaves comment unfrozen and redirects with error" do
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaces comment unfrozen and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unfreeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment unfrozen and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unfreeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_falsey
                it_redirects_to_with_comment_error(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Sorry, that comment thread could not be unfrozen."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment unfrozen and redirects with error" do
            fake_login
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "leaves comment unfrozen and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment) }

        context "when logged out" do
          it "leaves comment unfrozen and redirects with error" do
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment unfrozen and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unfreeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment unfrozen and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unfreeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_falsey
                it_redirects_to_with_comment_error(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Sorry, that comment thread could not be unfrozen."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment unfrozen and redirects with error" do
            fake_login
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "leaves comment unfrozen and redirects with error" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_comment_error(
              work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Sorry, that comment thread could not be unfrozen."
            )
          end
        end
      end

      context "when comment is the start of a thread" do
        let!(:comment) { create(:comment) }
        let!(:child1) { create(:comment, commentable: comment) }
        let!(:grandchild) { create(:comment, commentable: child1) }
        let!(:child2) { create(:comment, commentable: comment) }

        it "leaves entire thread unfrozen and redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          [comment, child1, child2, grandchild].each do |comment|
            expect(comment.reload.iced).to be_falsey
          end
          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be unfrozen."
          )
        end
      end

      context "when comment is the middle of a thread" do
        let!(:parent) { create(:comment) }
        let!(:comment) { create(:comment, commentable: parent) }
        let!(:child) { create(:comment, commentable: comment) }
        let!(:sibling) { create(:comment, commentable: parent) }

        it "leaves the comment and its child frozen, as well as its parent and sibling, and redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          [comment, child, parent, sibling].each do |comment|
            expect(comment.reload.iced).to be_falsey
          end
          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be unfrozen."
          )
        end
      end

      context "when comment is the end of a thread" do
        let!(:parent) { create(:comment) }
        let!(:child1) { create(:comment, commentable: parent) }
        let!(:child2) { create(:comment, commentable: parent) }
        let!(:comment) { create(:comment, commentable: child1) }

        it "leaves the comment unfrozen, along with any other comments in the thread, and redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          [comment, parent, child1, child2].each do |comment|
            expect(comment.reload.iced).to be_falsey
          end
          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be unfrozen."
          )
        end
      end

      context "when comment is not saved" do
        let!(:comment) { create(:comment) }

        before do
          allow_any_instance_of(Comment).to receive(:save).and_return(false)
        end

        it "redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be unfrozen."
          )
        end
      end
    end

    context "when comment is frozen" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post, iced: true) }

        context "when logged out" do
          it "doesn't unfreeze comment and redirects with error" do
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "unfreezes comment and redirects with success message" do
            fake_login_admin(admin)
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_comment_notice(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment thread successfully unfrozen!"
            )
          end
        end

        context "when logged in as a user" do
          it "doesn't unfreeze comment and redirects with error" do
            fake_login
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag, iced: true) }

        context "when logged out" do
          it "doesn't unfreeze comment and redirects with error" do
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't unfreeze comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unfreeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "unfreezes comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unfreeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_falsey
                it_redirects_to_with_comment_notice(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Comment thread successfully unfrozen!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't unfreeze comment and redirects with error" do
            fake_login
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "doesn't unfreeze comment and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment, iced: true) }

        context "when logged out" do
          it "doesn't unfreeze comment and redirects with error" do
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't unfreeze comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unfreeze, params: { id: comment.id }

              expect(comment.reload.iced).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "unfreezes comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unfreeze, params: { id: comment.id }

                expect(comment.reload.iced).to be_falsey
                it_redirects_to_with_comment_notice(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Comment thread successfully unfrozen!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't unfreeze comment and redirects with error" do
            fake_login
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "freezes the comment and redirects with success message" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :unfreeze, params: { id: comment.id }

            expect(comment.reload.iced).to be_falsey
            it_redirects_to_with_comment_notice(
              work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment thread successfully unfrozen!"
            )
          end
        end
      end

      context "when comment is the start of a thread" do
        let!(:comment) { create(:comment, iced: true) }
        let!(:child1) { create(:comment, commentable: comment, iced: true) }
        let!(:grandchild) { create(:comment, commentable: child1, iced: true) }
        let!(:child2) { create(:comment, commentable: comment, iced: true) }

        it "unfreezes entire thread and redirects with success message" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          [comment, child1, child2, grandchild].each do |comment|
            expect(comment.reload.iced).to be_falsey
          end
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully unfrozen!"
          )
        end
      end

      context "when comment is the middle of a thread" do
        let!(:parent) { create(:comment, iced: true) }
        let!(:comment) { create(:comment, commentable: parent, iced: true) }
        let!(:child) { create(:comment, commentable: comment, iced: true) }
        let!(:sibling) { create(:comment, commentable: parent, iced: true) }

        it "unfreezes the comment and its child, but not its parent or sibling, and redirects with success message" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          expect(comment.reload.iced).to be_falsey
          expect(child.reload.iced).to be_falsey
          expect(parent.reload.iced).to be_truthy
          expect(sibling.reload.iced).to be_truthy
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully unfrozen!"
          )
        end
      end

      context "when comment is the end of a thread" do
        let!(:parent) { create(:comment, iced: true) }
        let!(:child1) { create(:comment, commentable: parent, iced: true) }
        let!(:child2) { create(:comment, commentable: parent, iced: true) }
        let!(:comment) { create(:comment, commentable: child1, iced: true) }

        it "unfreezes the comment, but no other comments in the thread, and redirects with success message" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          expect(parent.reload.iced).to be_truthy
          expect(child1.reload.iced).to be_truthy
          expect(child2.reload.iced).to be_truthy
          expect(comment.reload.iced).to be_falsey
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully unfrozen!"
          )
        end
      end

      context "when comment is spam" do
        let(:comment) { create(:comment, iced: true) }

        before { comment.update_attribute(:approved, false) }

        it "unfreezes the comment and redirects with success message without changing the approved status" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          expect(comment.reload.iced).to be_falsey
          expect(comment.reload.approved).to be_falsey
          it_redirects_to_with_comment_notice(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Comment thread successfully unfrozen!"
          )
        end
      end

      context "when comment is not saved" do
        let!(:comment) { create(:comment, iced: true) }

        before do
          allow_any_instance_of(Comment).to receive(:save).and_return(false)
        end

        it "redirects with error" do
          fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
          put :unfreeze, params: { id: comment.id }

          it_redirects_to_with_comment_error(
            work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
            "Sorry, that comment thread could not be unfrozen."
          )
        end
      end
    end
  end

  describe "PUT #hide" do
    context "when comment is not hidden" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post) }

        context "when logged out" do
          it "doesn't hide comment and redirects with error" do
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "hides comment and redirects with success message" do
            fake_login_admin(admin)
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_comment_notice(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment successfully hidden!"
            )
          end
        end

        context "when logged in as a user" do
          it "doesn't hide comment and redirects with error" do
            fake_login
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag) }

        context "when logged out" do
          it "doesn't hide comment and redirects with error" do
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't hide comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :hide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "hides comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :hide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_truthy
                it_redirects_to_with_comment_notice(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Comment successfully hidden!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't hide comment and redirects with error" do
            fake_login
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "doesn't hide comment and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment) }

        context "when logged out" do
          it "doesn't hide comment and redirects with error" do
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't hide comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :hide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "hides comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :hide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_truthy
                it_redirects_to_with_comment_notice(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Comment successfully hidden!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't hide comment and redirects with error" do
            fake_login
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "doesn't hide the comment and redirects with error" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end
      end
    end

    context "when comment is hidden" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post, hidden_by_admin: true) }

        context "when logged out" do
          it "leaves comment hidden and redirects with error" do
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "leaves comment hidden and redirects with error" do
            fake_login_admin(admin)
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_comment_error(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Sorry, that comment could not be hidden."
            )
          end
        end

        context "when logged in as a user" do
          it "leaves comment hidden and redirects with error" do
            fake_login
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag, hidden_by_admin: true) }

        context "when logged out" do
          it "leaves comment hidden and redirects with error" do
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment hidden and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :hide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment hidden and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :hide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_truthy
                it_redirects_to_with_comment_error(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Sorry, that comment could not be hidden."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment hidden and redirects with error" do
            fake_login
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "leaves comment hidden and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment, hidden_by_admin: true) }

        context "when logged out" do
          it "leaves comment hidden and redirects with error" do
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment hidden and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :hide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment hidden and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :hide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_truthy
                it_redirects_to_with_comment_error(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Sorry, that comment could not be hidden."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment hidden and redirects with error" do
            fake_login
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "leaves comment hidden and redirects with error" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :hide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to hide that comment.")
          end
        end
      end
    end
  end

  describe "PUT #unhide" do
    context "when comment is hidden" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post, hidden_by_admin: true) }

        context "when logged out" do
          it "doesn't unhide comment and redirects with error" do
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "unhides comment and redirects with success message" do
            fake_login_admin(admin)
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_comment_notice(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment successfully unhidden!"
            )
          end
        end

        context "when logged in as a user" do
          it "doesn't unhide comment and redirects with error" do
            fake_login
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag, hidden_by_admin: true) }

        context "when logged out" do
          it "doesn't unhide comment and redirects with error" do
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't unhide comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unhide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "unhides comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unhide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_falsey
                it_redirects_to_with_comment_notice(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Comment successfully unhidden!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't unhide comment and redirects with error" do
            fake_login
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "doesn't unhide comment and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment, hidden_by_admin: true) }

        context "when logged out" do
          it "doesn't unhide comment and redirects with error" do
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't unhide comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unhide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_truthy
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "unhides comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unhide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_falsey
                it_redirects_to_with_comment_notice(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Comment successfully unhidden!"
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "doesn't unhide comment and redirects with error" do
            fake_login
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "doesn't unhide the comment and redirects with error" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_truthy
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end
      end
    end

    context "when comment is not hidden" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post) }

        context "when logged out" do
          it "leaves comment unhidden and redirects with error" do
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          it "leaves comment unhidden and redirects with error" do
            fake_login_admin(admin)
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_comment_error(
              admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Sorry, that comment could not be unhidden."
            )
          end
        end

        context "when logged in as a user" do
          it "leaves comment unhidden and redirects with error" do
            fake_login
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag) }

        context "when logged out" do
          it "leaves comment unhidden and redirects with error" do
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment unhidden and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unhide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
            end
          end

          %w[superadmin tag_wrangling].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment unhidden and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unhide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_falsey
                it_redirects_to_with_comment_error(
                  comments_path(tag_id: comment.ultimate_parent, anchor: :comments),
                  "Sorry, that comment could not be unhidden."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment unhidden and redirects with error" do
            fake_login
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
          end
        end

        context "when logged in as a user with the tag wrangling role" do
          let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }

          it "leaves comment unhidden and redirects with error" do
            fake_login_known_user(tag_wrangler)
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment) }

        context "when logged out" do
          it "leaves comment unhidden and redirects with error" do
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "leaves comment unhidden and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              put :unhide, params: { id: comment.id }

              expect(comment.reload.hidden_by_admin?).to be_falsey
              it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "leaves comment unhidden and redirects with error" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :unhide, params: { id: comment.id }

                expect(comment.reload.hidden_by_admin?).to be_falsey
                it_redirects_to_with_comment_error(
                  work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
                  "Sorry, that comment could not be unhidden."
                )
              end
            end
          end
        end

        context "when logged in as a random user" do
          it "leaves comment unhidden and redirects with error" do
            fake_login
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end

        context "when logged in as a user who owns the work" do
          it "leaves comment unhidden and redirects with error" do
            fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
            put :unhide, params: { id: comment.id }

            expect(comment.reload.hidden_by_admin?).to be_falsey
            it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unhide that comment.")
          end
        end
      end
    end
  end

  describe "GET #show_comments" do
    context "when the commentable is a valid tag" do
      let(:fandom) { create(:fandom) }

      let(:all_comments_path) do
        comments_path(tag_id: fandom.to_param, anchor: "comments")
      end

      context "when logged in as an admin" do
        before { fake_login_admin(create(:admin)) }

        it "redirects to the tag comments page when the format is html" do
          get :show_comments, params: { tag_id: fandom.name }
          expect(response).to redirect_to all_comments_path
        end

        it "loads the comments when the format is javascript" do
          get :show_comments, params: { tag_id: fandom.name, format: :js }, xhr: true
          expect(response).to render_template(:show_comments)
        end
      end

      context "when logged in as a tag wrangler" do
        before { fake_login_known_user(create(:tag_wrangler)) }

        it "redirects to the tag comments page when the format is html" do
          get :show_comments, params: { tag_id: fandom.name }
          expect(response).to redirect_to all_comments_path
        end

        it "loads the comments when the format is javascript" do
          get :show_comments, params: { tag_id: fandom.name, format: :js }, xhr: true
          expect(response).to render_template(:show_comments)
        end
      end

      context "when logged in as a random user" do
        before { fake_login }

        it "shows an error and redirects" do
          get :show_comments, params: { tag_id: fandom.name }
          it_redirects_to_with_error(user_path(controller.current_user),
                                     "Sorry, you don't have permission to " \
                                     "access the page you were trying to " \
                                     "reach.")
        end
      end

      context "when logged out" do
        before { fake_logout }

        it "shows an error and redirects" do
          get :show_comments, params: { tag_id: fandom.name }
          it_redirects_to_with_error(new_user_session_path,
                                     "Sorry, you don't have permission to " \
                                     "access the page you were trying to " \
                                     "reach. Please log in.")
        end
      end
    end
  end

  describe "GET #hide_comments" do
    it "redirects to the comment path without an error" do
      get :hide_comments, params: { comment_id: unreviewed_comment.id }
      expect(flash[:error]).to be_nil
      expect(response).to redirect_to(comment_path(unreviewed_comment, anchor: "comments"))
    end
  end

  describe "GET #cancel_comment_reply" do
    context "with only valid params" do
      it "redirects to comment path with the comments anchor and without an error" do
        get :cancel_comment_reply, params: { comment_id: comment.id }
        expect(flash[:error]).to be_nil
        expect(response).to redirect_to(comment_path(comment, anchor: "comments"))
      end
    end

    context "with valid and invalid params" do
      it "removes invalid params and redirects without an error to comment path with valid params and the comments anchor" do
        get :cancel_comment_reply, params: { comment_id: comment.id, show_comments: "yes", random_option: "no" }
        expect(flash[:error]).to be_nil
        expect(response).to redirect_to(comment_path(comment, show_comments: "yes", anchor: "comments"))
      end
    end
  end

  describe "GET #cancel_comment_delete" do
    it "redirects to the comment on the commentable without an error" do
      get :cancel_comment_delete, params: { id: comment.id }
      expect(flash[:error]).to be_nil
      expect(response).to redirect_to(chapter_path(comment.commentable, show_comments: true, anchor: "comment_#{comment.id}"))
    end
  end

  describe "GET #cancel_comment_edit" do
    context "when logged in as the comment writer" do
      before { fake_login_known_user(comment.pseud.user) }

      context "when the format is html" do
        it "redirects to the comment on the commentable without an error" do
          get :cancel_comment_edit, params: { id: comment.id }
          expect(flash[:error]).to be_nil
          expect(response).to redirect_to(chapter_path(comment.commentable, show_comments: true, anchor: "comment_#{comment.id}"))
        end
      end

      context "when the format is javascript" do
        it "loads the javascript to restore the comment" do
          get :cancel_comment_edit, params: { id: comment.id, format: :js }, xhr: true
          expect(response).to render_template("cancel_comment_edit")
        end
      end
    end

    context "when logged in as a random user" do
      before { fake_login }

      it "shows an error and redirects" do
        get :cancel_comment_edit, params: { id: comment.id }
        it_redirects_to_with_error(comment,
                                   "Sorry, you don't have permission to " \
                                   "access the page you were trying to " \
                                   "reach.")
      end
    end

    context "when logged out" do
      before { fake_logout }

      it "shows an error and redirects" do
        get :cancel_comment_edit, params: { id: comment.id }
        it_redirects_to_with_error(comment,
                                   "Sorry, you don't have permission to " \
                                   "access the page you were trying to " \
                                   "reach. Please log in.")
      end
    end
  end

  describe "DELETE #destroy" do
    context "when logged in as the owner of the unreviewed comment" do
      before { fake_login_known_user(unreviewed_comment.pseud.user) }

      it "deletes the comment and redirects to referer with a notice" do
        delete :destroy, params: { id: unreviewed_comment.id }
        expect { unreviewed_comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
        it_redirects_to_with_notice("/where_i_came_from", "Comment deleted.")
      end

      it "redirects and gives an error if the comment could not be deleted" do
        allow_any_instance_of(Comment).to receive(:destroy_or_mark_deleted).and_return(false)
        delete :destroy, params: { id: unreviewed_comment.id }
        expect(unreviewed_comment.reload).to be_present
        expect(response).to redirect_to(chapter_path(unreviewed_comment.commentable, show_comments: true, anchor: "comment_#{unreviewed_comment.id}"))
        expect(flash[:comment_error]).to eq "We couldn't delete that comment."
      end
    end

    context "when comment is frozen" do
      context "when ultimate parent is an AdminPost" do
        let(:comment) { create(:comment, :on_admin_post, iced: true) }

        context "when logged out" do
          it "doesn't destroy comment and redirects with error" do
            delete :destroy, params: { id: comment.id }

            it_redirects_to_with_error(comment, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
            expect { comment.reload }.not_to raise_exception
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't destroy comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              delete :destroy, params: { id: comment.id }

              it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
              expect { comment.reload }.not_to raise_exception
            end
          end

          %w[superadmin board policy_and_abuse communications support].each do |admin_role|
            context "with role #{admin_role}" do
              it "destroys comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                delete :destroy, params: { id: comment.id }

                expect(flash[:comment_notice]).to eq("Comment deleted.")
                it_redirects_to_simple(admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments))
                expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
              end
            end
          end
        end

        context "when logged in as a user" do
          context "when user does not own comment" do
            it "doesn't destroy comment and redirects with error" do
              fake_login
              delete :destroy, params: { id: comment.id }

              it_redirects_to_with_error(comment, "Sorry, you don't have permission to access the page you were trying to reach.")
              expect { comment.reload }.not_to raise_exception
            end
          end

          context "when user owns comment" do
            it "destroys comment and redirects with success message" do
              fake_login_known_user(comment.pseud.user)
              delete :destroy, params: { id: comment.id }

              expect(flash[:comment_notice]).to eq("Comment deleted.")
              it_redirects_to_simple(admin_post_path(comment.ultimate_parent, show_comments: true, anchor: :comments))
              expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
            end
          end
        end
      end

      context "when ultimate parent is a Tag" do
        let(:comment) { create(:comment, :on_tag, iced: true) }

        context "when logged out" do
          it "doesn't destroy comment and redirects with error" do
            delete :destroy, params: { id: comment.id }

            it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
            expect { comment.reload }.not_to raise_exception
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't destroy comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              delete :destroy, params: { id: comment.id }

              it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
              expect { comment.reload }.not_to raise_exception
            end
          end

          %w[superadmin board policy_and_abuse communications support].each do |admin_role|
            context "with the #{admin_role} role" do
              it "destroys comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                delete :destroy, params: { id: comment.id }

                expect(flash[:comment_notice]).to eq("Comment deleted.")
                it_redirects_to_simple(comments_path(tag_id: comment.ultimate_parent, anchor: :comments))
                expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
              end
            end
          end
        end

        context "when logged in as a user" do
          context "when user does not have tag wrangler role" do
            context "when user does not own comment" do
              it "doesn't destroy comment and redirects with error" do
                fake_login
                delete :destroy, params: { id: comment.id }

                it_redirects_to_with_error(user_path(controller.current_user), "Sorry, you don't have permission to access the page you were trying to reach.")
                expect { comment.reload }.not_to raise_exception
              end
            end

            context "when user owns comment" do
              it "doesn't destroy comment and redirects with error" do
                fake_login_known_user(comment.pseud.user)
                delete :destroy, params: { id: comment.id }

                it_redirects_to_with_error(user_path(comment.pseud.user), "Sorry, you don't have permission to access the page you were trying to reach.")
                expect { comment.reload }.not_to raise_exception
              end
            end
          end

          context "when user has tag wrangler role" do
            let(:tag_wrangler) { create(:user, roles: [Role.new(name: "tag_wrangler")]) }
            let(:frozen_wrangler_comment) { create(:comment, :on_tag, iced: true, pseud: tag_wrangler.pseuds.first) }

            context "when user does not own comment" do
              it "doesn't destroy comment and redirects with error" do
                fake_login_known_user(tag_wrangler)
                delete :destroy, params: { id: comment.id }

                it_redirects_to_with_error(comment, "Sorry, you don't have permission to access the page you were trying to reach.")
                expect { comment.reload }.not_to raise_exception
              end
            end

            context "when user owns comment" do
              it "destroys comment and redirects with success message" do
                fake_login_known_user(tag_wrangler)
                delete :destroy, params: { id: frozen_wrangler_comment.id }

                expect(flash[:comment_notice]).to eq("Comment deleted.")
                it_redirects_to_simple(comments_path(tag_id: frozen_wrangler_comment.ultimate_parent, anchor: :comments))
                expect { frozen_wrangler_comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
              end
            end
          end
        end
      end

      context "when ultimate parent is a Work" do
        let(:comment) { create(:comment, iced: true) }

        context "when logged out" do
          it "doesn't destroy comment and redirects with error" do
            delete :destroy, params: { id: comment.id }

            it_redirects_to_with_error(comment, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
            expect { comment.reload }.not_to raise_exception
          end

          context "when Work is restricted" do
            context "when commentable is a comment" do
              let(:work) { comment.ultimate_parent }

              before { work.update!(restricted: true) }

              it "redirects to the login page" do
                delete :destroy, params: { id: comment.id }
                it_redirects_to(new_user_session_path(restricted_commenting: true))
              end
            end
          end
        end

        context "when logged in as an admin" do
          let(:admin) { create(:admin) }

          context "with no role" do
            it "doesn't destroy comment and redirects with error" do
              admin.update(roles: [])
              fake_login_admin(admin)
              delete :destroy, params: { id: comment.id }

              it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
              expect { comment.reload }.not_to raise_exception
            end
          end

          %w[superadmin policy_and_abuse].each do |admin_role|
            context "with the #{admin_role} role" do
              it "destroys comment and redirects with success message" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                delete :destroy, params: { id: comment.id }

                expect(flash[:comment_notice]).to eq("Comment deleted.")
                it_redirects_to_simple(work_path(comment.ultimate_parent, show_comments: true, anchor: :comments))
                expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
              end
            end
          end
        end

        context "when logged in as a user" do
          context "when user does not own comment" do
            it "doesn't destroy comment and redirects with error" do
              fake_login
              delete :destroy, params: { id: comment.id }

              it_redirects_to_with_error(comment, "Sorry, you don't have permission to access the page you were trying to reach.")
              expect { comment.reload }.not_to raise_exception
            end
          end

          context "when user owns comment" do
            it "destroys comment and redirects with success message" do
              fake_login_known_user(comment.pseud.user)
              delete :destroy, params: { id: comment.id }

              expect(flash[:comment_notice]).to eq("Comment deleted.")
              it_redirects_to_simple(work_path(comment.ultimate_parent, show_comments: true, anchor: :comments))
              expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
            end
          end

          context "when user owns work" do
            it "destroys comment and redirects with success message" do
              fake_login_known_user(comment.ultimate_parent.pseuds.first.user)
              delete :destroy, params: { id: comment.id }

              expect(flash[:comment_notice]).to eq("Comment deleted.")
              it_redirects_to_simple(work_path(comment.ultimate_parent, show_comments: true, anchor: :comments))
              expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
            end
          end
        end
      end
    end
  end

  describe "PUT #review" do
    let!(:user) { create(:user) }
    let!(:work) { create(:work, authors: [user.default_pseud], moderated_commenting_enabled: true) }
    let(:comment) { create(:comment, :unreviewed, commentable: work.first_chapter) }

    before do
      fake_login_known_user(user)
    end

    context "when recipient approves comment from inbox" do
      it "marks comment reviewed and redirects to user inbox path with success message" do
        put :review, params: { id: comment.id, approved_from: "inbox" }
        expect(response).to redirect_to(user_inbox_path(user))
        expect(flash[:notice]).to eq "Comment approved."
        comment.reload
        expect(comment.unreviewed).to be false
      end
    end

    context "when recipient approves comment from inbox with filters" do
      it "marks comment reviewed and redirects to user inbox path with success message" do
        put :review, params: { id: comment.id, approved_from: "inbox", filters: { date: "asc" } }
        expect(response).to redirect_to(user_inbox_path(user, filters: { date: "asc" }))
        expect(flash[:notice]).to eq "Comment approved."
        comment.reload
        expect(comment.unreviewed).to be false
      end
    end

    context "when recipient approves comment from homepage" do
      it "marks comment reviewed and redirects to root path with success message" do
        put :review, params: { id: comment.id, approved_from: "home" }
        expect(response).to redirect_to(root_path)
        expect(flash[:notice]).to eq "Comment approved."
        comment.reload
        expect(comment.unreviewed).to be false
      end
    end
  end

  describe "GET #show" do
    it "redirects to root path if logged in user does not have permission to access comment" do
      fake_login
      get :show, params: { id: unreviewed_comment.id }
      it_redirects_to_with_error(root_path, "Sorry, that comment is currently in moderation.")
    end
  end

  describe "GET #index" do
    it "redirects to 404 when not logged in as admin" do
      get :index

      it_redirects_to_simple("/404")
    end

    it "redirects to 404 when logged in as admin" do
      fake_login_admin(create(:admin))

      get :index

      it_redirects_to_simple("/404")
    end
  end

  shared_examples "no one can add or edit comments" do
    let(:anon_comment_attributes) do
      attributes_for(:comment, :by_guest).slice(:name, :email, :comment_content)
    end

    context "when logged out" do
      it "DELETE #destroy redirects to the home page with an error" do
        delete :destroy, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "GET #add_comment_reply redirects to the home page with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "GET #index redirects to the home page with an error" do
        get :index, params: { work_id: work.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "GET #new (on a comment) redirects to the home page with an error" do
        get :new, params: { comment_id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "GET #new redirects to the home page with an error" do
        get :new, params: { work_id: work.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "GET #show redirects to the home page with an error" do
        get :show, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "GET #show_comments redirects to the home page with an error" do
        get :show_comments, params: { work_id: work.id, format: :js }, xhr: true
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "POST #create (on a comment) redirects to the home page with an error" do
        post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "POST #create redirects to the home page with an error" do
        post :create, params: { work_id: work.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "PUT #freeze redirects to the home page with an error" do
        put :freeze, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end

      it "PUT #unfreeze redirects to the home page with an error" do
        put :unfreeze, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end
    end

    context "when logged in as a random user" do
      before { fake_login }

      it "DELETE #destroy redirects to the home page with an error" do
        delete :destroy, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #add_comment_reply redirects to the home page with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #index redirects to the home page with an error" do
        get :index, params: { work_id: work.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #new (on a comment) redirects to the home page with an error" do
        get :new, params: { comment_id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #new redirects to the home page with an error" do
        get :new, params: { work_id: work.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #show redirects to the home page with an error" do
        get :show, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #show_comments redirects to the home page with an error" do
        get :show_comments, params: { work_id: work.id, format: :js }, xhr: true
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "POST #create (on a comment) redirects to the home page with an error" do
        post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "POST #create redirects to the home page with an error" do
        post :create, params: { work_id: work.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "PUT #freeze redirects to the home page with an error" do
        put :freeze, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "PUT #unfreeze redirects to the home page with an error" do
        put :unfreeze, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end

    context "when logged in as the comment writer" do
      before { fake_login_known_user(comment.pseud.user) }

      it "DELETE #destroy redirects to the home page with an error" do
        delete :destroy, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #add_comment_reply redirects to the home page with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #new redirects to the home page with an error" do
        get :new, params: { comment_id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "GET #show redirects to the home page with an error" do
        get :show, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "POST #create redirects to the home page with an error" do
        post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "PUT #freeze redirects to the home page with an error" do
        put :freeze, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end

      it "PUT #unfreeze redirects to the home page with an error" do
        put :unfreeze, params: { id: comment.id }
        it_redirects_to_with_error(root_path, "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end

    context "when logged in as the work's owner" do
      before { fake_login_known_user(work.users.first) }

      it "DELETE #destroy successfully deletes the comment" do
        delete :destroy, params: { id: comment.id }
        expect(flash[:comment_notice]).to eq "Comment deleted."
        it_redirects_to_simple(work_path(work, show_comments: true, anchor: :comments))
        expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
      end

      it "GET #add_comment_reply redirects to the work with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "GET #index renders the index template" do
        get :index, params: { work_id: work.id }
        expect(response).to render_template(:index)
      end

      it "GET #new (on a comment) redirects to the work with an error" do
        get :new, params: { comment_id: comment.id }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "GET #new redirects to the work with an error" do
        get :new, params: { work_id: work.id }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "GET #show_comments renders the show_comments template" do
        get :show_comments, params: { work_id: work.id, format: :js }, xhr: true
        expect(response).to render_template(:show_comments)
      end

      it "GET #show successfully displays the comment" do
        get :show, params: { id: comment.id }
        expect(response).to render_template(:show)
        expect(assigns[:comment]).to eq(comment)
      end

      it "POST #create (on a comment) redirects to the work with an error" do
        post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "POST #create redirects to the work with an error" do
        post :create, params: { work_id: work.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "PUT #freeze successfully freezes the comment" do
        put :freeze, params: { id: comment.id }
        it_redirects_to_with_comment_notice(
          work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
          "Comment thread successfully frozen!"
        )
        expect(comment.reload.iced).to be_truthy
      end

      it "PUT #unfreeze successfully unfreezes the comment" do
        comment.update(iced: true)
        put :unfreeze, params: { id: comment.id }
        it_redirects_to_with_comment_notice(
          work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
          "Comment thread successfully unfrozen!"
        )
        expect(comment.reload.iced).to be_falsey
      end
    end

    context "when logged in as an admin" do
      before { fake_login_admin(create(:admin, roles: ["policy_and_abuse"])) }

      let(:admin) { create(:admin) }

      context "DELETE #destroy" do
        it "does not permit deletion of the comment when admin has no role" do
          admin.update(roles: [])
          fake_login_admin(admin)
          delete :destroy, params: { id: comment.id }
          it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
        end

        %w[superadmin board communications policy_and_abuse support].each do |admin_role|
          it "successfully deletes the comment when admin has #{admin_role} role" do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
            delete :destroy, params: { id: comment.id }
            expect(flash[:comment_notice]).to eq "Comment deleted."
            it_redirects_to_simple(work_path(work, show_comments: true, anchor: :comments))
            expect { comment.reload }.to raise_exception(ActiveRecord::RecordNotFound)
          end
        end
      end

      it "GET #add_comment_reply redirects to the work with an error" do
        get :add_comment_reply, params: { comment_id: comment.id }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "GET #index renders the index template" do
        get :index, params: { work_id: work.id }
        expect(response).to render_template(:index)
      end

      it "GET #new (on a comment) redirects to the work with an error" do
        get :new, params: { comment_id: comment.id }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "GET #new redirects to the work with an error" do
        get :new, params: { work_id: work.id }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "GET #show_comments renders the show_comments template" do
        get :show_comments, params: { work_id: work.id, format: :js }, xhr: true
        expect(response).to render_template(:show_comments)
      end

      it "GET #show successfully displays the comment" do
        get :show, params: { id: comment.id }
        expect(response).to render_template(:show)
        expect(assigns[:comment]).to eq(comment)
      end

      it "POST #create (on a comment) redirects to the work with an error" do
        post :create, params: { comment_id: comment.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      it "POST #create redirects to the work with an error" do
        post :create, params: { work_id: work.id, comment: anon_comment_attributes }
        it_redirects_to_with_error(work_path(work), edit_error_message)
      end

      context "PUT #freeze" do
        it "does not permit freezing of the comment when admin has no role" do
          admin.update(roles: [])
          fake_login_admin(admin)
          put :freeze, params: { id: comment.id }
          it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to freeze that comment thread.")
        end

        %w[superadmin policy_and_abuse].each do |admin_role|
          it "successfully freezes the comment when admin has #{admin_role} role" do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
            put :freeze, params: { id: comment.id }
            it_redirects_to_with_comment_notice(
              work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment thread successfully frozen!"
            )
            expect(comment.reload.iced).to be_truthy
          end
        end
      end

      context "PUT #unfreeze" do
        it "does not permit unfreezing of the comment when admin has no role" do
          comment.update(iced: true)
          admin.update(roles: [])
          fake_login_admin(admin)
          put :unfreeze, params: { id: comment.id }
          it_redirects_to_with_error("/where_i_came_from", "Sorry, you don't have permission to unfreeze that comment thread.")
        end

        %w[superadmin policy_and_abuse].each do |admin_role|
          it "successfully unfreezes the comment when admin has #{admin_role} role" do
            comment.update(iced: true)
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
            put :unfreeze, params: { id: comment.id }
            it_redirects_to_with_comment_notice(
              work_path(comment.ultimate_parent, show_comments: true, anchor: :comments),
              "Comment thread successfully unfrozen!"
            )
            expect(comment.reload.iced).to be_falsey
          end
        end
      end
    end
  end

  context "on a work hidden by an admin" do
    it_behaves_like "no one can add or edit comments" do
      let(:edit_error_message) { "Sorry, you can't add or edit comments on a hidden work." }
      let(:work) { comment.ultimate_parent }
      before { work.update_column(:hidden_by_admin, true) }
    end
  end

  context "on an unrevealed work" do
    it_behaves_like "no one can add or edit comments" do
      let(:edit_error_message) { "Sorry, you can't add or edit comments on an unrevealed work." }
      let(:work) { comment.ultimate_parent }
      before { work.update!(collection_names: create(:unrevealed_collection).name) }
    end
  end
end
