package at.hackenbergerhollander.rueckwaertssalto.dbstructure;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.attributeproperties.AttributeProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Attribute in a Table of an DBMS
 *
 * @author Rene Hollander
 * @version 1.0
 */
public class Attribute {
    private Table parentTable;

    private String name;
    private List<AttributeProperty> properties;

    protected Attribute(Table parentTable, String name) {
        this.parentTable = parentTable;

        this.name = name;
        this.properties = new ArrayList<AttributeProperty>();
    }

    /**
     * Gets the Name of the Attribute
     *
     * @return Name of the Attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Properties of the Attribute, e.g: PrimaryKey, ForeignKey
     *
     * @return List of the Properties of this Attribute
     */
    public List<AttributeProperty> getProperties() {
        return properties;
    }

    /**
     * Gets the Table this Attribute is in
     *
     * @return Table this Attribute is in
     */
    public Table getParentTable() {
        return parentTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        if (properties != null ? !properties.equals(attribute.properties) : attribute.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Attribute{");
        sb.append("name='").append(name).append('\'');
        sb.append(", properties=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}
