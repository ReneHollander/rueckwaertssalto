package at.hackenbergerhollander.rueckwaertssalto.dbstructure.attributeproperties;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Attribute;

/**
 * Defines an Foreign Key Relation
 *
 * @author Rene Hollander
 * @version 1.0
 */
public class Foreign {
    private Attribute origin;

    /**
     * Origin of the Foreign Key Relation
     *
     * @param origin Origin Attribute
     */
    public Foreign(Attribute origin) {
        this.origin = origin;
    }

    public Attribute getOrigin() {
        return origin;
    }
}
