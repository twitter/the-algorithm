require "spec_helper"

describe Blocked::UsersController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:blocker) { create(:user) }
  let(:blocked) { create(:user) }

  shared_examples "no other users can access it" do
    context "when logged out" do
      it "redirects with an error" do
        subject.call
        it_redirects_to_with_error(blocker, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end
    end

    context "when logged in as a random user" do
      before { fake_login }

      it "redirects with an error" do
        subject.call
        it_redirects_to_with_error(blocker, "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end

    context "when logged in as the blocked user" do
      before { fake_login_known_user(blocked) }

      it "redirects with an error" do
        subject.call
        it_redirects_to_with_error(blocker, "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end
  end

  shared_examples "admins can't access it" do
    context "when logged in as an admin" do
      before { fake_login_admin(admin) }

      Admin::VALID_ROLES.each do |role|
        context "with role #{role}" do
          let(:admin) { create(:admin, roles: [role]) }

          it "redirects with an error" do
            subject.call
            it_redirects_to_with_error(blocker, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end
      end
    end
  end

  describe "GET #index" do
    subject { -> { get :index, params: { user_id: blocker } } }

    let!(:block) { Block.create(blocker: blocker, blocked: blocked) }

    it_behaves_like "no other users can access it"

    context "when logged in as admin" do
      permitted_roles = %w[policy_and_abuse support superadmin]

      before { fake_login_admin(admin) }

      permitted_roles.each do |role|
        context "with role #{role}" do
          let(:admin) { create(:admin, roles: [role]) }

          it "displays the page" do
            subject.call
            expect(assigns[:blocks]).to contain_exactly(block)
            expect(response).to render_template(:index)
          end
        end
      end

      (Admin::VALID_ROLES - permitted_roles).each do |role|
        context "with role #{role}" do
          let(:admin) { create(:admin, roles: [role]) }

          it "redirects with an error" do
            subject.call
            it_redirects_to_with_error(root_path, "Sorry, only an authorized admin can access the page you were trying to reach.")
          end
        end
      end
    end
  end

  describe "GET #confirm_block" do
    subject { -> { get :confirm_block, params: { user_id: blocker, blocked_id: blocked } } }

    context "when logged in as the blocker" do
      before { fake_login_known_user(blocker) }

      it "displays the page" do
        subject.call
        expect(response).to render_template(:confirm_block)
      end

      context "when no blocked_id is specified" do
        subject { -> { get :confirm_block, params: { user_id: blocker } } }

        it "redirects with an error" do
          subject.call
          it_redirects_to_with_error(user_blocked_users_path(blocker),
                                     "Sorry, we couldn't find a user matching that name.")
        end
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end

  describe "POST #create" do
    subject { -> { post :create, params: { user_id: blocker, blocked_id: blocked } } }

    context "when logged in as the blocker" do
      before { fake_login_known_user(blocker) }

      it "creates the block and redirects" do
        subject.call
        expect(Block.where(blocker: blocker, blocked: blocked)).to be_present
        it_redirects_to_with_notice(user_blocked_users_path(blocker),
                                    "You have blocked the user #{blocked.login}.")
      end

      context "when trying to block more users than the block limit" do
        let(:blocked_2nd) { create(:user) }

        it "redirects with an error" do
          allow(ArchiveConfig).to receive(:MAX_BLOCKED_USERS).and_return(1)
          Block.create(blocker: blocker, blocked: blocked)
          post :create, params: { user_id: blocker, blocked_id: blocked_2nd }
          expect(Block.where(blocker: blocker, blocked: blocked_2nd)).not_to be_present
          it_redirects_to_with_error(user_blocked_users_path(blocker),
                                     "Sorry, you have blocked too many accounts.")
        end
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end

  describe "GET #confirm_unblock" do
    subject { -> { get :confirm_unblock, params: { user_id: blocker, id: block } } }

    let!(:block) { Block.create(blocker: blocker, blocked: blocked) }

    context "when logged in as the blocker" do
      before { fake_login_known_user(blocker) }

      it "displays the page" do
        subject.call
        expect(response).to render_template(:confirm_unblock)
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end

  describe "DELETE #destroy" do
    subject { -> { delete :destroy, params: { user_id: blocker, id: block } } }

    let!(:block) { Block.create(blocker: blocker, blocked: blocked) }

    context "when logged in as the blocker" do
      before { fake_login_known_user(blocker) }

      it "deletes the block and redirects" do
        subject.call
        expect(Block.where(blocker: blocker, blocked: blocked)).to be_blank
        it_redirects_to_with_notice(user_blocked_users_path(blocker),
                                    "You have unblocked the user #{blocked.login}.")
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end
end
