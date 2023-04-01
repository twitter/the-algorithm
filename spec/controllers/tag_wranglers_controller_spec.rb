require "spec_helper"

describe TagWranglersController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:user) { create(:tag_wrangler) }

  before { fake_login_known_user(user) }

  describe "#show" do
    context "when the target user does not exist" do
      it "raises a 404 error" do
        expect do
          get :show, params: { id: -1 }
        end.to raise_exception(ActiveRecord::RecordNotFound)
      end
    end
  end
end
