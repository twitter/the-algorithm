# frozen_string_literal: true

require "spec_helper"

describe Admin::AdminInvitationsController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "GET #index" do
    let(:admin) { create(:admin) }

    it "denies non-admins access to index" do
      fake_login
      get :index
      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    it "allows admins to access index" do
      fake_login_admin(admin)
      get :index
      expect(response).to have_http_status(:success)
    end
  end

  describe "POST #create" do
    let(:admin) { create(:admin) }

    it "does not allow non-admins to create invites" do
      email = "test_email@example.com"
      fake_login
      post :create, params: { invitation: { invitee_email: email } }

      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    it "allows admins to create invites" do
      email = "test_email@example.com"
      fake_login_admin(admin)
      post :create, params: { invitation: { invitee_email: email } }

      it_redirects_to_with_notice(admin_invitations_path, "An invitation was sent to #{email}")
    end
  end

  describe "POST #invite_from_queue" do
    let(:admin) { create(:admin) }
    let(:invite_request) { create(:invite_request) }

    it "does not allow non-admins to invite from queue" do
      fake_login
      post :invite_from_queue, params: { invitation: { invite_from_queue: "1" } }

      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    it "allows admins to invite from queue" do
      fake_login_admin(admin)
      post :invite_from_queue, params: { invitation: { invite_from_queue: "1" } }

      it_redirects_to_with_notice(admin_invitations_path, "1 person from the invite queue is being invited.")
    end
  end

  describe "POST #grant_invites_to_users" do
    let(:admin) { create(:admin) }
    let(:invite_request) { create(:invite_request) }

    it "does not allow non-admins to grant invites to all users" do
      fake_login
      post :grant_invites_to_users, params: { invitation: { user_group: "ALL" } }

      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    it "allows admins to grant invites to all users" do
      fake_login_admin(admin)
      post :grant_invites_to_users, params: { invitation: { user_group: "ALL", number_of_invites: "2" } }

      it_redirects_to_with_notice(admin_invitations_path, "Invitations successfully created.")
    end
  end

  describe "GET #find" do
    let(:admin) { create(:admin) }
    let(:user) { create(:user) }
    let(:invitation) { create(:invitation) }

    it "does not allow non-admins to search" do
      fake_login
      get :find, params: { invitation: { user_name: user.login } }

      it_redirects_to_with_notice(root_path, "I'm sorry, only an admin can look at that area")
    end

    it "allows admins to search by user_name" do
      user.update(invitations: [invitation])
      fake_login_admin(admin)
      get :find, params: { invitation: { user_name: user.login } }

      expect(response).to render_template("find")
      expect(assigns(:invitations)).to include(invitation)
    end

    it "allows admins to search by token" do
      fake_login_admin(admin)
      get :find, params: { invitation: { token: invitation.token } }

      expect(response).to render_template("find")
      expect(assigns(:invitation)).to eq(invitation)
    end

    it "allows admins to search by invitee_email" do
      invitation.update(invitee_email: user.email)
      fake_login_admin(admin)
      get :find, params: { invitation: { invitee_email: user.email } }

      expect(response).to render_template("find")
      expect(assigns(:invitation)).to eq(invitation)
    end
  end
end
