require "spec_helper"
require "controllers/api/api_helper"

include ApiHelper

describe Api::V2::BaseController, type: :controller do

  describe "batch_errors" do

    context "with no archivist" do
      it "should return the 'forbidden' status" do
        status, = controller.instance_eval { batch_errors(nil, api_fields) }
        assert_equal status, :forbidden
      end

      it "returns an error message" do
        _, messages = controller.instance_eval { batch_errors(nil, api_fields) }
        assert_equal "The \"archivist\" field must specify the name of an Archive user with archivist privileges.", messages[0]
      end
    end

    context "with a user who is not an archivist" do
      let(:not_archivist) { create(:user) }

      it "returns the 'forbidden' status" do
        user = not_archivist
        status, = controller.instance_eval { batch_errors(user, api_fields) }
        assert_equal status, :forbidden
      end

      it "returns an error message" do
        user = not_archivist
        _, messages = controller.instance_eval { batch_errors(user, api_fields) }
        assert_equal "The \"archivist\" field must specify the name of an Archive user with archivist privileges.", messages[0]
      end
    end
  end

  describe "batch_errors with a valid pseud" do
    let!(:archivist) { create(:archivist) }

    it "returns error messages with no items to import" do
      user = archivist
      _, messages = controller.instance_eval { batch_errors(user, nil) }
      assert_equal "No items to import were provided.", messages[0]
    end

    it "returns error messages with too many items to import" do
      user = archivist
      loads_of_items = Array.new(210) { |_| api_fields }
      _, messages = controller.instance_eval { batch_errors(user, loads_of_items) }
      expect(messages[0]).to start_with "This request contains too many items to import."
    end

    it "returns OK status" do
      user = archivist
      status, = controller.instance_eval { batch_errors(user, api_fields) }
      assert_equal :ok, status
    end

    it "returns no error messages" do
      user = archivist
      _, messages = controller.instance_eval { batch_errors(user, api_fields) }
      assert_equal 0, messages.size
    end
  end
end
