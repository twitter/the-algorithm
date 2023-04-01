require 'spec_helper'

describe CollectionItemsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:user) { create(:user) }
  let(:collection) { create(:collection) }

  describe "GET #index" do
    let(:rejected_work) { create(:work) }
    let(:approved_work) { create(:work) }
    let(:invited_work) { create(:work) }

    let!(:rejected_work_item) { collection.collection_items.create(item: rejected_work) }
    let!(:approved_work_item) { collection.collection_items.create(item: approved_work) }
    let!(:invited_work_item) { collection.collection_items.create(item: invited_work) }

    before do
      rejected_work_item.rejected_by_collection!
      invited_work_item.unreviewed_by_user!
    end

    context "where the user is not a maintainer" do
      it "redirects and shows an error message" do
        fake_login_known_user(user)
        get :index, params: { collection_id: collection.id }
        it_redirects_to_with_error(collections_path, "You don't have permission to see that, sorry!")
      end
    end

    context "rejected parameter for collection with items in" do
      let(:owner) { collection.owners.first.user }

      it "includes rejected items" do
        fake_login_known_user(owner)
        get :index, params: { collection_id: collection.name, rejected: true }
        expect(response).to have_http_status(:success)
        expect(assigns(:collection_items)).to include rejected_work_item
      end

      it "excludes approved and invited items" do
        fake_login_known_user(owner)
        get :index, params: { collection_id: collection.name, rejected: true }
        expect(assigns(:collection_items)).not_to include approved_work_item
        expect(assigns(:collection_items)).not_to include invited_work_item
      end
    end

    context "invited parameter for collection with items in" do
      let(:owner) { collection.owners.first.user }

      it "includes invited items" do
        fake_login_known_user(owner)
        get :index, params: { collection_id: collection.name, invited: true }
        expect(response).to have_http_status(:success)
        expect(assigns(:collection_items)).to include invited_work_item
      end

      it "excludes approved and rejected items" do
        fake_login_known_user(owner)
        get :index, params: { collection_id: collection.name, invited: true }
        expect(assigns(:collection_items)).not_to include approved_work_item
        expect(assigns(:collection_items)).not_to include rejected_work_item
      end
    end

    context "for collection with items in, default approved parameter" do
      let(:owner) { collection.owners.first.user }

      it "includes approved items" do
        fake_login_known_user(owner)
        get :index, params: { collection_id: collection.name, approved: true }
        expect(response).to have_http_status(:success)
        expect(assigns(:collection_items)).to include approved_work_item
      end

      it "excludes invited and rejected items" do
        fake_login_known_user(owner)
        get :index, params: { collection_id: collection.name, approved: true }
        expect(assigns(:collection_items)).not_to include rejected_work_item
        expect(assigns(:collection_items)).not_to include invited_work_item
      end
    end
  end

  describe "GET #create" do
    context "creation" do
      let(:collection) { FactoryBot.create(:collection) }

      it "fails if collection names missing" do
        get :create, params: { collection_id: collection.id }
        it_redirects_to_with_error(root_path, "What collections did you want to add?")
      end

      it "fails if items missing" do
        get :create, params: { collection_names: collection.name, collection_id: collection.id }
        it_redirects_to_with_error(root_path, "What did you want to add to a collection?")
      end
    end
  end

  describe "POST #create" do
    context "when logged in as the collection maintainer" do
      before { fake_login_known_user(collection.owners.first.user) }

      context "when the item is a work" do
        let(:work) { create(:work) }

        let(:params) do
          {
            collection_names: collection.name,
            work_id: work.id
          }
        end

        context "when the creator does not allow invitations" do
          it "does not create an invitation" do
            post :create, params: params
            it_redirects_to_with_error(work, "This item could not be invited.")
            expect(work.reload.collections).to be_empty
          end
        end

        context "when the creator allows invitations" do
          before do
            work.users.each { |user| user.preference.update!(allow_collection_invitation: true) }
          end

          it "creates an invitation" do
            post :create, params: params
            it_redirects_to_simple(work)
            expect(work.reload.collections).to include(collection)
          end
        end
      end
    end
  end

  describe "PATCH #update_multiple" do
    let(:collection) { create(:collection) }
    let(:work) { create(:work) }
    let(:item) { create(:collection_item, collection: collection, item: work) }

    let(:attributes) { { remove: "1" } }

    describe "on the user collection items page for the work's owner" do
      let(:work_owner) { work.pseuds.first.user }

      let(:params) do
        {
          user_id: work_owner.login,
          collection_items: {
            item.id => attributes
          }
        }
      end

      context "when logged out" do
        before { fake_logout }

        it "errors and redirects" do
          patch :update_multiple, params: params
          it_redirects_to_with_error(work_owner, "You don't have permission to do that, sorry!")
        end
      end

      context "when logged in as a random user" do
        before { fake_login }

        it "errors and redirects" do
          patch :update_multiple, params: params
          it_redirects_to_with_error(work_owner, "You don't have permission to do that, sorry!")
        end
      end

      context "when logged in as the collection owner" do
        before { fake_login_known_user(collection.owners.first.user) }

        it "errors and redirects" do
          patch :update_multiple, params: params
          it_redirects_to_with_error(work_owner, "You don't have permission to do that, sorry!")
        end
      end

      context "when logged in as the work's owner" do
        before { fake_login_known_user(work_owner) }

        context "setting user_approval_status" do
          let(:attributes) { { user_approval_status: "rejected" } }

          it "updates the collection item and redirects" do
            patch :update_multiple, params: params
            expect(item.reload.user_approval_status).to eq("rejected")
            it_redirects_to_with_notice(user_collection_items_path(work_owner),
                                        "Collection status updated!")
          end
        end

        context "setting remove" do
          let(:attributes) { { remove: "1" } }

          it "deletes the collection item and redirects" do
            patch :update_multiple, params: params
            expect { item.reload }.to \
              raise_exception(ActiveRecord::RecordNotFound)
            it_redirects_to_with_notice(user_collection_items_path(work_owner),
                                        "Collection status updated!")
          end
        end

        {
          collection_approval_status: "rejected",
          unrevealed: true,
          anonymous: true
        }.each_pair do |field, value|
          context "setting #{field}" do
            let(:attributes) { { field => value } }

            it "throws an error and doesn't update" do
              expect do
                patch :update_multiple, params: params
              end.to raise_exception(ActionController::UnpermittedParameters)
              expect(item.reload.send(field)).not_to eq(value)
            end
          end
        end
      end
    end

    describe "on the collection items page for the work's collection" do
      let(:params) do
        {
          collection_id: collection.name,
          collection_items: {
            item.id => attributes
          }
        }
      end

      context "when logged out" do
        before { fake_logout }

        it "errors and redirects" do
          patch :update_multiple, params: params
          it_redirects_to_with_error(collection, "You don't have permission to do that, sorry!")
        end
      end

      context "when logged in as a random user" do
        before { fake_login }

        it "errors and redirects" do
          patch :update_multiple, params: params
          it_redirects_to_with_error(collection, "You don't have permission to do that, sorry!")
        end
      end

      context "when logged in as a maintainer" do
        before { fake_login_known_user(collection.owners.first.user) }

        {
          collection_approval_status: "rejected",
          unrevealed: true,
          anonymous: true
        }.each_pair do |field, value|
          context "setting #{field}" do
            let(:attributes) { { field => value } }

            it "updates the collection item and redirects" do
              patch :update_multiple, params: params
              expect(item.reload.send(field)).to eq(value)
              it_redirects_to_with_notice(collection_items_path(collection),
                                          "Collection status updated!")
            end
          end
        end

        context "setting remove" do
          let(:attributes) { { remove: "1" } }

          it "deletes the collection item and redirects" do
            patch :update_multiple, params: params
            expect { item.reload }.to \
              raise_exception(ActiveRecord::RecordNotFound)
            it_redirects_to_with_notice(collection_items_path(collection),
                                        "Collection status updated!")
          end
        end

        context "setting user_approval_status" do
          let(:attributes) { { user_approval_status: "rejected" } }

          it "throws an error and doesn't update" do
            expect do
              patch :update_multiple, params: params
            end.to raise_exception(ActionController::UnpermittedParameters)
            expect(item.reload.user_approval_status).not_to eq("rejected")
          end
        end
      end
    end

    describe "on the collection items page for a different user" do
      let(:user) { create(:user) }
      before { fake_login_known_user(user) }

      let(:params) do
        {
          user_id: user.login,
          collection_items: {
            item.id => { user_approval_status: "rejected" }
          }
        }
      end

      it "silently fails to update the collection item" do
        patch :update_multiple, params: params
        expect(item.reload.user_approval_status).not_to eq("rejected")
        it_redirects_to_with_notice(user_collection_items_path(user),
                                    "Collection status updated!")
      end
    end

    describe "on the collection items page for a different collection" do
      let(:other_collection) { create(:collection) }
      before { fake_login_known_user(other_collection.owners.first.user) }

      let(:params) do
        {
          collection_id: other_collection.name,
          collection_items: {
            item.id => { collection_approval_status: "rejected" }
          }
        }
      end

      it "silently fails to update the collection item" do
        patch :update_multiple, params: params
        expect(item.reload.collection_approval_status).not_to eq("rejected")
        it_redirects_to_with_notice(collection_items_path(other_collection),
                                    "Collection status updated!")
      end
    end
  end
end
