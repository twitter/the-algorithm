require "spec_helper"

describe WorkCreatorIndexer, work_search: true do
  describe "#index_documents" do
    context "with multiple works in a batch", :n_plus_one do
      populate { |n| create_list(:work, n) }

      it "generates a constant number of database queries" do
        expect do
          WorkCreatorIndexer.new(Work.ids).index_documents
        end.to perform_constant_number_of_queries
      end
    end
  end
end
