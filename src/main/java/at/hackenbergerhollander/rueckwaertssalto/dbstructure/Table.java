package at.hackenbergerhollander.rueckwaertssalto.dbstructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represent a Table in a DBMS
 *
 * @author Rene Hollander
 * @version 1.0
 */
public class Table {
    private Database parentDatabase;

    private String name;
    private List<Attribute> attributes;

    protected Table(Database parentDatabase, String name) {
        this.parentDatabase = parentDatabase;

        this.name = name;
        this.attributes = new ArrayList<>();
    }

    /**
     * Get the Name of the Table
     *
     * @return Name of the Table
     */
    public String getName() {
        return name;
    }

    /**
     * Add an Attribute to the Table
     *
     * @param name Name of the Attribute
     * @return Attribute added to the Table
     */
    public Attribute addAttribute(String name) {
        Attribute attribute = new Attribute(this, name);
        this.attributes.add(attribute);
        return attribute;
    }

    /**
     * Get the Attribute at the specified index
     *
     * @param index Index of the Attribute
     * @return Attribute at the index
     */
    public Attribute getAttribute(int index) {
        return this.attributes.get(index);
    }

    /**
     * Gets the Attribute by its name
     *
     * @param name Name of the Attribute in the Table
     * @return Attribute, or Null if it doesnt exist
     */
    public Attribute getAttribute(String name) {
        Optional<Attribute> first = this.attributes.stream().filter(attribute -> attribute.getName().equals(name)).findFirst();
        return first.isPresent() ? first.get() : null;
    }

    /**
     * Remove the Attribute at the given index
     *
     * @param index Index of the Attribute to get removed
     * @return Removed Attribute
     */
    public Attribute removeAttribute(int index) {
        return this.attributes.remove(index);
    }

    /**
     * Remove the Attribute with the given name from the Table
     *
     * @param name Name of the Attribute to get removed
     * @return Removed Attribute
     */
    public Attribute removeAttribute(String name) {
        Attribute toRemove = this.getAttribute(name);
        if (toRemove == null) {
            return null;
        }
        this.attributes.remove(toRemove);
        return toRemove;
    }

    /**
     * Gets the count of the Attributes in the Table
     *
     * @return Attribute Count
     */
    public int getAttributeCount() {
        return this.attributes.size();
    }

    /**
     * Get an Array Representation of the Attributes in the Table
     *
     * @return Array of the Attributes
     */
    public Attribute[] getAttributes() {
        return this.attributes.toArray(new Attribute[this.attributes.size()]);
    }

    /**
     * @param action Action to be performed for each element
     * @see java.lang.Iterable#forEach(java.util.function.Consumer)
     */
    public void forEachAttribute(Consumer<? super Attribute> action) {
        this.attributes.forEach(action);
    }

    /**
     * Get the Database this Table is in
     *
     * @return Database this Table is in
     */
    public Database getParentDatabase() {
        return this.parentDatabase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;

        Table table = (Table) o;

        if (attributes != null ? !attributes.equals(table.attributes) : table.attributes != null) return false;
        if (name != null ? !name.equals(table.name) : table.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Table{");
        sb.append("name='").append(name).append('\'');
        sb.append(", attributes=").append(attributes);
        sb.append('}');
        return sb.toString();
    }
}
