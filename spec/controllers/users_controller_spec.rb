require "spec_helper"

describe UsersController do
  include RedirectExpectationHelper

  describe "GET #activate" do
    let(:user) { create(:user, confirmed_at: nil) }

    context "with no activation key" do
      it "redirects with an error" do
        get :activate, params: { id: "" }
        it_redirects_to_with_error(root_path, "Your activation key is missing.")
      end
    end

    context "with an invalid activation key" do
      it "redirects with an error" do
        get :activate, params: { id: "foobar" }
        it_redirects_to_with_error(root_path, "Your activation key is invalid. If you didn't activate within 14 days, your account was deleted. Please sign up again, or contact support via the link in our footer for more help.")
      end
    end

    context "with a used activation key" do
      before { user.activate }

      it "redirects with an error" do
        expect(user.active?).to be_truthy
        get :activate, params: { id: user.confirmation_token }
        it_redirects_to_with_error(user_path(user), "Your account has already been activated.")
      end
    end

    context "with a valid activation key" do
      it "activates the account and redirects with a success message" do
        expect(user.active?).to be_falsey
        get :activate, params: { id: user.confirmation_token }
        expect(user.reload.active?).to be_truthy
        it_redirects_to_with_notice(new_user_session_path, "Account activation complete! Please log in.")
      end
    end
  end
end
