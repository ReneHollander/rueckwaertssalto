package at.hackenbergerhollander.rueckwaertssalto.dbstructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represent a Database in a DBMS
 *
 * @author Rene Hollander
 * @version 1.0
 */
public class Database {

    private String name;
    private List<Table> tables;

    /**
     * Creates a Database representing a Database in a DBMS
     *
     * @param name Name of the Database
     */
    public Database(String name) {
        this.name = name;
        this.tables = new ArrayList<>();
    }

    /**
     * @return Name of the Database
     */
    public String getName() {
        return name;
    }

    /**
     * Add a Table with the given Name to the Database
     *
     * @param name Name of the Table
     * @return Created Table Object
     */
    public Table addTable(String name) {
        Table table = new Table(this, name);
        this.tables.add(table);
        return table;
    }

    /**
     * Get the Table at a given index
     *
     * @param index Index of the Table
     * @return Table at the index
     */
    public Table getTable(int index) {
        return tables.get(index);
    }

    /**
     * Get the Table by its name
     *
     * @param name Name of the Table
     * @return Table with the given name, null if not found
     */
    public Table getTable(String name) {
        Optional<Table> first = this.tables.stream().filter(table -> table.getName().equals(name)).findFirst();
        return first.isPresent() ? first.get() : null;
    }

    /**
     * Remove the Table at the given index
     *
     * @param index Index of the Table to be removed
     * @return The Table that got removed
     */
    public Table removeTable(int index) {
        return this.tables.remove(index);
    }

    /**
     * Remove the Table with the specified name
     *
     * @param name Name of the Table to be removed
     * @return The Table that got removed
     */
    public Table removeTable(String name) {
        Table toRemove = this.getTable(name);
        if (toRemove == null) {
            return null;
        }
        this.tables.remove(toRemove);
        return toRemove;
    }

    /**
     * Get the Count of Tables in the Database
     *
     * @return Count of Tables
     */
    public int getTableCount() {
        return tables.size();
    }

    /**
     * Get All the Tables in the Database
     *
     * @return Tables in the Database
     */
    public Table[] getTables() {
        return tables.toArray(new Table[tables.size()]);
    }

    /**
     * @param action Action to be performed for each element
     * @see java.lang.Iterable#forEach(java.util.function.Consumer)
     */
    public void forEachTable(Consumer<? super Table> action) {
        this.tables.forEach(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Database)) return false;

        Database database = (Database) o;

        if (name != null ? !name.equals(database.name) : database.name != null) return false;
        if (tables != null ? !tables.equals(database.tables) : database.tables != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tables != null ? tables.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Database{");
        sb.append("name='").append(name).append('\'');
        sb.append(", tables=").append(tables);
        sb.append('}');
        return sb.toString();
    }
}
