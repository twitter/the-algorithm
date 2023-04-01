require 'spec_helper'

describe CollectionProfileController do
  include RedirectExpectationHelper
  describe "GET #show" do
    context "collection does not exist" do
      it "redirects and provides an error message" do
        get :show, params: { collection_id: "A non existent collection" }
        it_redirects_to_with_error(collections_path, "What collection did you want to look at?")
      end
    end
  end
end
