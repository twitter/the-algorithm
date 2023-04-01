# frozen_string_literal: true

require "spec_helper"

describe PseudsController do
  include LoginMacros
  include RedirectExpectationHelper

  describe "destroy" do
    let(:user) { create(:user) }

    before do
      fake_login_known_user(user)
    end

    context "when deleting the default pseud" do
      it "errors and redirects to user_pseuds_path" do
        post :destroy, params: { user_id: user, id: user.default_pseud }
        it_redirects_to_with_error(user_pseuds_path(user), "You cannot delete your default pseudonym, sorry!")
      end
    end

    context "when deleting the pseud that matches your username" do
      it "errors and redirects to user_pseuds_path" do
        matching_pseud = user.default_pseud
        matching_pseud.update_attribute(:is_default, false)
        matching_pseud.reload

        post :destroy, params: { user_id: user, id: matching_pseud }
        it_redirects_to_with_error(user_pseuds_path(user), "You cannot delete the pseud matching your user name, sorry!")
      end
    end
  end
end
