require "spec_helper"

describe Muted::UsersController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:muter) { create(:user) }
  let(:muted) { create(:user) }

  shared_examples "no other users can access it" do
    context "when logged out" do
      it "redirects with an error" do
        subject.call
        it_redirects_to_with_error(muter, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
      end
    end

    context "when logged in as a random user" do
      before { fake_login }

      it "redirects with an error" do
        subject.call
        it_redirects_to_with_error(muter, "Sorry, you don't have permission to access the page you were trying to reach.")
      end
    end

    context "when logged in as the muted user" do
      before { fake_login_known_user(muted) }

      it "redirects with an error" do
        subject.call
        it_redirects_to_with_error(muter, "Sorry, you don't have permission to access the page you were trying to reach.")
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
            it_redirects_to_with_error(muter, "Sorry, you don't have permission to access the page you were trying to reach. Please log in.")
          end
        end
      end
    end
  end

  describe "GET #index" do
    subject { -> { get :index, params: { user_id: muter } } }

    let!(:mute) { Mute.create(muter: muter, muted: muted) }

    it_behaves_like "no other users can access it"

    context "when logged in as admin" do
      permitted_roles = %w[policy_and_abuse support superadmin]

      before { fake_login_admin(admin) }

      permitted_roles.each do |role|
        context "with role #{role}" do
          let(:admin) { create(:admin, roles: [role]) }

          it "displays the page" do
            subject.call
            expect(assigns[:mutes]).to contain_exactly(mute)
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

  describe "GET #confirm_mute" do
    subject { -> { get :confirm_mute, params: { user_id: muter, muted_id: muted } } }

    context "when logged in as the muter" do
      before { fake_login_known_user(muter) }

      it "displays the page" do
        subject.call
        expect(response).to render_template(:confirm_mute)
      end

      context "when no muted_id is specified" do
        subject { -> { get :confirm_mute, params: { user_id: muter } } }

        it "redirects with an error" do
          subject.call
          it_redirects_to_with_error(user_muted_users_path(muter),
                                     "Sorry, we couldn't find a user matching that name.")
        end
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end

  describe "POST #create" do
    subject { -> { post :create, params: { user_id: muter, muted_id: muted } } }

    context "when logged in as the muter" do
      before { fake_login_known_user(muter) }

      it "creates the mute and redirects" do
        subject.call
        expect(Mute.where(muter: muter, muted: muted)).to be_present
        it_redirects_to_with_notice(user_muted_users_path(muter),
                                    "You have muted the user #{muted.login}.")
      end

      context "when trying to mute more users than the mute limit" do
        let(:muted_2nd) { create(:user) }

        it "redirects with an error" do
          allow(ArchiveConfig).to receive(:MAX_MUTED_USERS).and_return(1)
          Mute.create(muter: muter, muted: muted)
          post :create, params: { user_id: muter, muted_id: muted_2nd }
          expect(Mute.where(muter: muter, muted: muted_2nd)).not_to be_present
          it_redirects_to_with_error(user_muted_users_path(muter),
                                     "Sorry, you have muted too many accounts.")
        end
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end

  describe "GET #confirm_unmute" do
    subject { -> { get :confirm_unmute, params: { user_id: muter, id: mute } } }

    let!(:mute) { Mute.create(muter: muter, muted: muted) }

    context "when logged in as the muter" do
      before { fake_login_known_user(muter) }

      it "displays the page" do
        subject.call
        expect(response).to render_template(:confirm_unmute)
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end

  describe "DELETE #destroy" do
    subject { -> { delete :destroy, params: { user_id: muter, id: mute } } }

    let!(:mute) { Mute.create(muter: muter, muted: muted) }

    context "when logged in as the muter" do
      before { fake_login_known_user(muter) }

      it "deletes the mute and redirects" do
        subject.call
        expect(Mute.where(muter: muter, muted: muted)).to be_blank
        it_redirects_to_with_notice(user_muted_users_path(muter),
                                    "You have unmuted the user #{muted.login}.")
      end
    end

    it_behaves_like "no other users can access it"
    it_behaves_like "admins can't access it"
  end
end
