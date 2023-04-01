require 'spec_helper'

describe Users::RegistrationsController do
  include RedirectExpectationHelper

  def valid_user_attributes
    {
      email: "sna.foo@gmail.com", login: "myname", age_over_13: "1",
      terms_of_service: "1", password: "password"
    }
  end

  before do
    @request.env["devise.mapping"] = Devise.mappings[:user]
  end

  describe "create" do
    context "when invitations are required to sign up" do
      let(:invitation) { create(:invitation) }

      before do
        AdminSetting.update_all(
          account_creation_enabled: true,
          creation_requires_invite: true,
          invite_from_queue_enabled: true
        )
      end

      context "signing up with no invitation" do
        it "redirects with an error" do
          post :create, params: { user_registration: valid_user_attributes }

          it_redirects_to_with_error(
            invite_requests_path,
            "To create an account, you'll need an invitation. One option is " \
            "to add your name to the automatic queue below."
          )
        end
      end

      context "signing up with an invalid invitation" do
        it "redirects with an error" do
          post :create, params: { user_registration: valid_user_attributes,
                                  invitation_token: "asdf" }

          it_redirects_to_with_error(
            new_feedback_report_path,
            "There was an error with your invitation token, please contact " \
            "support"
          )
        end
      end

      context "signing up with a valid invitation" do
        it "succeeeds in creating the account" do
          post :create, params: { user_registration: valid_user_attributes,
                                  invitation_token: invitation.token }

          new_user = User.last

          expect(response).to be_successful
          expect(assigns(:user)).to be_a(User)
          expect(assigns(:user)).to eq(new_user)
          expect(assigns(:user).login).to eq("myname")

          invitation.reload
          expect(invitation.redeemed_at).not_to be_nil
          expect(invitation.invitee).to eq(new_user)
          expect(new_user.invitation).to eq(invitation)
        end
      end

      context "signing up with a used invitation" do
        let(:previous_user) { create(:user) }

        before do
          invitation.mark_as_redeemed(previous_user)
          previous_user.update(invitation_id: invitation.id)
        end

        it "redirects with an error" do
          post :create, params: { user_registration: valid_user_attributes,
                                  invitation_token: invitation.token }

          it_redirects_to_with_error(
            root_path,
            "This invitation has already been used to create an account, " \
            "sorry!"
          )
        end

        context "when the previous user deletes their account" do
          it "redirects with an error" do
            previous_user.destroy

            post :create, params: { user_registration: valid_user_attributes,
                                    invitation_token: invitation.token }

            it_redirects_to_with_error(
              root_path,
              "This invitation has already been used to create an account, " \
              "sorry!"
            )
          end
        end
      end
    end
  end
end
