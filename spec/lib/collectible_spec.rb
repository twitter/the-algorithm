require 'spec_helper'

def update_collection_setting(collection, setting, value)
  collection.collection_preference.send("#{setting}=", value)
  collection.collection_preference.save
end

describe Collectible do

  # TO-DO: update this to test the code for all types of collectibles,
  # bookmarks, works, etc

  it "should not be put into a nonexistent collection" do
    fake_name = "blah_blah_blah_not_an_existing_name"
    work = create(:work)
    work.collection_names = fake_name
    expect(work.errors[:base].first).to match("find") # use a very basic part of the error message
    expect(work.errors[:base].first).to match(fake_name)
    expect(work.save).to be_truthy
    work.reload
    expect(work.collection_names).not_to include(fake_name)
  end

  it "should return collections for approved_collections scope" do
    work = create(:work)
    collection1 = create(:collection)
    collection2 = create(:collection)

    work.collections << [collection1, collection2]
    work.collection_items.update_all(
      user_approval_status: "approved",
      collection_approval_status: "approved"
    )

    expect(work.approved_collections.count).to eq(2)
    expect(work.approved_collections).to include(collection1)
    expect(work.approved_collections).to include(collection2)
  end

  context "approved_collections have more than one work" do
    it "should return distinct collections for approved_collections scope" do
      work1 = create(:work)
      work2 = create(:work)

      collection = create(:collection)

      work1.collections << [collection]
      work2.collections << [collection]

      work1.collection_items.first.update(
        user_approval_status: "approved",
        collection_approval_status: "approved"
      )

      work2.collection_items.first.update(
        user_approval_status: "approved",
        collection_approval_status: "approved"
      )

      expect(work1.approved_collections.count).to eq(1)
      expect(work2.approved_collections.count).to eq(1)

      expect(work1.approved_collections).to include(collection)
      expect(work2.approved_collections).to include(collection)
    end
  end

  context "being posted to a collection" do
    let(:collection) { create(:collection) }
    # build but don't save so we can change the collection settings
    let(:work) { build(:work, collection_names: collection.name) }
    subject { work }

    context "once added" do

      it "should be in that collection" do
        work.save
        expect(work.collections).to include(collection)
        expect(collection.works).to include(work)
      end

      it "should be removable" do
        # collection_names= exercises collections_to_(add/remove) methods
        work.collection_names = ""
        work.save
        expect(work.collections).not_to include(collection)
        expect(collection.works).not_to include(work)
      end

      it "should be automatically approved when there is no current user" do
        work.save
        expect(work.approved_collections).to include(collection)
      end

    end

    {
      unrevealed: :in_unrevealed_collection,
      anonymous: :in_anon_collection
    }.each_pair do |state, work_attribute|
      context "which is #{state}" do
        before do
          # set the state of the collection and then save to put the work into the collection
          collection.collection_preference.attributes = { state => true }
          collection.collection_preference.save!
          work.save!
        end

        it "should be #{state}" do
          expect(work.reload.send(work_attribute)).to be_truthy
        end

        context "and when the collection is no longer #{state}" do
          before do
            collection.collection_preference.attributes = { state => false }
            collection.collection_preference.save!
          end

          it "should not be #{state}" do
            expect(collection.send("#{state}?")).to be_falsey
            expect(work.reload.send(work_attribute)).to be_falsey
          end
        end

        context "when the collectible's owner removes it from the collection" do
          before do
            work.collection_names = ""
            work.save!
          end

          it "should not be #{state}" do
            expect(work.reload.send(work_attribute)).to be_falsey
          end
        end

        context "when the collection item is destroyed" do
          before do
            ci = CollectionItem.find_by(item: work, collection: collection)
            ci.destroy
          end

          it "should not be #{state}" do
            expect(work.reload.send(work_attribute)).to be_falsey
          end
        end

        context "when the collection is destroyed" do
          before { collection.destroy }

          it "should not be #{state}" do
            expect(work.reload.send(work_attribute)).to be_falsey
          end
        end

        context "when the work's collection item is individually changed" do
          before do
            ci = CollectionItem.find_by(item: work, collection: collection)
            ci.attributes = { state => false }
            ci.save!
          end

          it "should no longer be #{state}" do
            expect(work.reload.send(work_attribute)).to be_falsey
          end
        end
      end
    end
  end

end
