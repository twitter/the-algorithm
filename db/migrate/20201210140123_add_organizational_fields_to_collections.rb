class AddOrganizationalFieldsToCollections < ActiveRecord::Migration[5.2]
  def change
    add_column :collections, :multifandom, :boolean
    add_column :collections, :open_doors, :boolean
  end
end
