# frozen_string_literal: true

require "spec_helper"

describe SerialWork do
  describe "#destroy" do
    context "when the series is missing" do
      let(:work) { create(:work) }
      let(:serial_work) { create(:serial_work, series: nil, work: work) }

      it "deletes the SerialWork" do
        expect { serial_work.destroy }.not_to raise_exception
        expect { serial_work.reload }.to \
          raise_exception ActiveRecord::RecordNotFound
      end
    end

    context "when the work is missing" do
      let(:series) { create(:series) }
      let(:serial_work) { create(:serial_work, series: series, work: nil) }

      it "deletes the SerialWork" do
        expect { serial_work.destroy }.not_to raise_exception
        expect { serial_work.reload }.to \
          raise_exception ActiveRecord::RecordNotFound
      end
    end
  end
end
