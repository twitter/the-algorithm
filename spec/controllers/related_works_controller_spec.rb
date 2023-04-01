require 'spec_helper'

describe RelatedWorksController do
  include RedirectExpectationHelper
  include LoginMacros
  let(:child_creator) { FactoryBot.create(:user) }
  let(:child_work) { FactoryBot.create(:work, authors: [child_creator.default_pseud]) }
  let(:parent_creator) { FactoryBot.create(:user) }
  let(:parent_work) { FactoryBot.create(:work, authors: [parent_creator.default_pseud]) }

  describe "GET #index" do
    context "for a blank user" do
      before(:each) do
        get :index, params: { user_id: "" }
      end

      it "sets a flash message and redirects the requester" do
        it_redirects_to_with_error(user_related_works_path, "Whose related works were you looking for?") 
      end
    end

    context "for a nonexistent user" do
      before(:each) do
        get :index, params: { user_id: "user" }
      end

      it "sets a flash message and redirects the requester" do
        it_redirects_to_with_error(user_related_works_path, "Sorry, we couldn't find that user")
      end
    end
  end

  describe "PUT #update" do
    context "by the creator of the child work" do
      before(:each) do
        @related_work = FactoryBot.create(:related_work, work_id: child_work.id)
        fake_login_known_user(child_creator)
        put :update, params: { id: @related_work }
      end

      it "sets a flash message and redirects the requester" do
        it_redirects_to_with_error(related_work_path(@related_work), "Sorry, but you don't have permission to do that. Try removing the link from your own work.")
      end
    end

    context "by a user who is not the creator of either work" do
      before(:each) do
        @related_work = FactoryBot.create(:related_work)
        fake_login
        put :update, params: { id: @related_work }
      end

      it "sets a flash message and redirects the requester" do
        it_redirects_to_with_error(related_work_path(@related_work), "Sorry, but you don't have permission to do that.")
      end
    end

    context "by the creator of the parent work" do
      before(:each) do
        @related_work = FactoryBot.create(:related_work, parent_id: parent_work.id, reciprocal: true)
        fake_login_known_user(parent_creator)
      end

      context "with valid parameters" do
        before(:each) do
          put :update, params: { id: @related_work }
        end

        it "updates the related work attributes" do
          @related_work.reload
          expect(@related_work.reciprocal?).to be false
        end

        it "sets a flash message and redirects the requester" do
          it_redirects_to_with_notice(@related_work.parent, "Link was successfully removed")
        end
      end

      context "with invalid parameters" do
        it "sets a flash message and redirects to the related work" do
          allow_any_instance_of(RelatedWork).to receive(:save).and_return(false)
          put :update, params: { id: @related_work }
          it_redirects_to_with_error(related_work_path(@related_work), "Sorry, something went wrong.")
        end
      end
    end
  end

  describe "DELETE #destroy" do
    context "by the creator of the parent work" do
      before(:each) do
        @related_work = FactoryBot.create(:related_work, parent_id: parent_work.id, reciprocal: true)
        fake_login_known_user(parent_creator)
        delete :destroy, params: { id: @related_work }
      end

      it "sets a flash message and redirects the requester" do
        it_redirects_to_with_error(related_work_path(@related_work), "Sorry, but you don't have permission to do that. You can only approve or remove the link from your own work.")
      end
    end

    context "by a user who is not the creator of either work" do
      before(:each) do
        @related_work = FactoryBot.create(:related_work)
        fake_login
        delete :destroy, params: { id: @related_work }
      end

      it "sets a flash message and redirects the requester" do
        it_redirects_to_with_error(related_work_path(@related_work), "Sorry, but you don't have permission to do that.")
      end
    end

    context "by the creator of the child work" do
      before(:each) do
        @related_work = FactoryBot.create(:related_work, work_id: child_work.id)
        fake_login_known_user(child_creator)
      end

      it "deletes the related work" do
        expect {
          delete :destroy, params: { id: @related_work }
        }.to change(RelatedWork, :count).by(-1)
      end

      it "redirects the requester" do
        delete :destroy, params: { id: @related_work }
        it_redirects_to(related_work_path(@related_work))
      end
    end
  end
end
