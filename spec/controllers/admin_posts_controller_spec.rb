require "spec_helper"

describe AdminPostsController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "POST #create" do
    before { fake_login_admin(create(:admin, roles: ["communications"])) }

    let(:base_params) { { title: "AdminPost Title",
                          content: "AdminPost content long enough to pass validation" } }

    context "when admin post is valid" do
      it "redirects to post with notice" do
        post :create, params: { admin_post: base_params }
        it_redirects_to_with_notice(admin_post_path(assigns[:admin_post]), "Admin Post was successfully created.")
      end
    end

    context "when admin post is invalid" do
      context "with invalid translated post id" do
        it "renders the new template with error message" do
          post :create, params: { admin_post: { translated_post_id: 0 } }.merge(base_params)

          expect(response).to render_template(:new)
          expect(assigns[:admin_post].errors.full_messages).to include("Translated post does not exist")
        end

        it "doesn't create new tags" do
          post :create, params: { admin_post: { translated_post_id: 0, tag_list: "badtag" } }.merge(base_params)
          expect(AdminPostTag.find_by(name: "badtag")).to be_nil
        end
      end
    end

    context "when translated post has same language id" do
      let(:admin_post) { create(:admin_post) }

      it "renders the new template with error message" do
        post :create, params: { admin_post: { translated_post_id: admin_post.id, language_id: admin_post.language_id } }.merge(base_params)

        expect(response).to render_template(:new)
        expect(assigns[:admin_post].errors.full_messages).to include("Translated post cannot be same language as original post")
      end
    end
  end

  describe "POST #update" do
    let(:admin) { create(:admin) }
    let(:post) { create(:admin_post) }

    context "when admin does not have correct authorization" do
      it "redirects with error" do
        admin.update(roles: [])
        fake_login_admin(admin)
        put :update, params: { id: post.id, admin_post: { admin_id: admin.id } }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    context "when admin has correct authorization" do
      %w[superadmin board communications support translation].each do |admin_role|
        context "with #{admin_role} role" do
          context "with valid title" do
            it "updates title and redirects with notice" do
              admin.update(roles: [admin_role])
              fake_login_admin(admin)
              put :update, params: { id: post.id, admin_post: { admin_id: admin.id, title: "Modified Title of Post" } }

              expect(post.reload.title).to eq("Modified Title of Post")
              it_redirects_to_with_notice(admin_post_path(assigns[:admin_post]), "Admin Post was successfully updated.")
            end
          end

          context "with invalid translated_post_id" do
            it "renders the edit template with error message" do
              admin.update(roles: [admin_role])
              fake_login_admin(admin)
              put :update, params: { id: post.id, admin_post: { admin_id: admin.id, translated_post_id: 0 } }

              expect(response).to render_template(:edit)
              expect(assigns[:admin_post].errors.full_messages).to include("Translated post does not exist")
            end
          end

          context "with valid translated_post_id" do
            let!(:translation) { create(:admin_post, translated_post_id: post.id, language_id: create(:language).id) }

            context "with valid comment_permissions" do
              it "does not change comment_permissions and redirects with notice" do
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                expect do
                  put :update, params: {
                    id: translation.id,
                    admin_post: {
                      admin_id: admin.id,
                      comment_permissions: :disable_all
                    }
                  }
                end.not_to change { AdminPost.comment_permissions }

                expect(translation.reload.comment_permissions).to eq(post.comment_permissions)
                it_redirects_to_with_notice(admin_post_path(translation), "Admin Post was successfully updated.")
              end
            end

            context "with invalid translated_post language" do
              it "renders the edit template with error message" do 
                admin.update(roles: [admin_role])
                fake_login_admin(admin)
                put :update, params: { id: translation.id, admin_post: { language_id:post.language_id } }
                expect(response).to render_template(:edit)
                expect(assigns[:admin_post].errors.full_messages).to include("Translated post cannot be same language as original post")
              end 
            end
          end
        end
      end
    end
  end

  describe "GET #edit" do
    let(:admin) { create(:admin) }
    let(:post) { create(:admin_post) }

    context "when admin does not have correct authorization" do
      it "redirects with error" do
        admin.update(roles: [])
        fake_login_admin(admin)
        get :edit, params: { id: post.id }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    context "when admin has correct authorization" do
      %w[superadmin board communications support translation].each do |admin_role|
        context "with #{admin_role} role" do
          it "renders edit template" do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
            get :edit, params: { id: post.id }

            expect(response).to render_template(:edit)
          end
        end
      end
    end
  end

  describe "GET #new" do
    let(:admin) { create(:admin) }
    let(:post) { create(:admin_post) }

    context "when admin does not have correct authorization" do
      it "redirects with error" do
        admin.update(roles: [])
        fake_login_admin(admin)
        get :edit, params: { id: post.id }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    context "when admin has correct authorization" do
      %w[superadmin board communications support translation].each do |admin_role|
        context "with #{admin_role} role" do
          it "renders new template" do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
            get :new, params: { id: post.id }

            expect(response).to render_template(:new)
          end
        end
      end
    end
  end

  describe "DELETE #destroy" do
    let(:admin) { create(:admin) }
    let(:post) { create(:admin_post) }

    context "when admin does not have correct authorization" do
      it "redirects with error" do
        admin.update(roles: [])
        fake_login_admin(admin)
        delete :destroy, params: { id: post.id }

        it_redirects_to_with_error(root_url, "Sorry, only an authorized admin can access the page you were trying to reach.")
      end
    end

    context "when admin has correct authorization" do
      %w[superadmin board communications support translation].each do |admin_role|
        context "with #{admin_role} role" do
          it "deletes post and redirects without notice" do
            admin.update(roles: [admin_role])
            fake_login_admin(admin)
            delete :destroy, params: { id: post.id }

            expect { post.reload }.to raise_exception(ActiveRecord::RecordNotFound)
            it_redirects_to(admin_posts_path)
          end
          
          context "with translated post" do
            let!(:translation) { create(:admin_post, translated_post_id: post.id, language_id: create(:language).id) }

            it "deletes translations of post along with post" do 
              admin.update(roles: [admin_role])
              fake_login_admin(admin)
              delete :destroy, params: { id: post.id }
  
              expect { post.reload }.to raise_exception(ActiveRecord::RecordNotFound)
              expect { translation.reload }.to raise_exception(ActiveRecord::RecordNotFound)
            end
          end
        end
      end
    end
  end
end
