require 'spec_helper'

describe Subscription do
  let(:subscription) { build(:subscription) }

  context "to a work" do
    before do
      subscription.subscribable = create(:work)
      subscription.save!
    end

    describe "when the work is destroyed" do
      before do
        subscription.subscribable.destroy
      end

      it "should be destroyed" do
        expect { subscription.reload }.to raise_error ActiveRecord::RecordNotFound
      end
    end
  end

  context "to a series" do
    before do
      subscription.subscribable = create(:series)
      subscription.save!
    end

    describe "when the series is destroyed" do
      before do
        subscription.subscribable.destroy
      end

      it "should be destroyed" do
        expect { subscription.reload }.to raise_error ActiveRecord::RecordNotFound
      end
    end
  end

  context "to a user" do
    before do
      subscription.subscribable = create(:user)
      subscription.save!
    end

    describe "when the user is destroyed" do
      before do
        subscription.subscribable.destroy
      end

      it "should be destroyed" do
        expect { subscription.reload }.to raise_error ActiveRecord::RecordNotFound
      end
    end
  end

  context "when subscribable does not exist" do
    before do
      work = create(:work)
      subscription.subscribable_id = work.id
      subscription.subscribable_type = "Work"
      work.destroy
    end

    it "should be invalid" do
      expect(subscription.valid?).to be_falsey
    end
  end

  context "when subscribable is not a valid object to subscribe to" do
    before do
      subscription.subscribable_id = 1
      subscription.subscribable_type = "Pseud"
    end

    it "should be invalid" do
      expect(subscription.valid?).to be_falsey
    end
  end

  context "when subscribable_type is not a real model name" do
    before do
      subscription.subscribable_id = 1
      subscription.subscribable_type = "Серия"
    end

    it "should be invalid" do
      expect(subscription.valid?).to be_falsey
    end
  end
end
