class AddAllowCocreatorToPreference < ActiveRecord::Migration[5.1]
  def change
    add_column :preferences, :allow_cocreator, :boolean, default: false
  end
end
