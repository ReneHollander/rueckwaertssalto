package at.hackenbergerhollander.rueckwaertssalto.exporter;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Attribute;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Table;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.attributeproperties.Foreign;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Exporter for an ER-Diagram
 *
 * @author Hackenberger Christoph
 * @version 1.0
 */
public class ERExporter implements Exporter {

    private File outputFile;

    /**
     * Creates a new ERExporter
     * @param outputFile the output file for the diagram
     */
    public ERExporter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Exports the database structer as an ER-Diagram
     * @see at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter#export(at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database)
     */
    @Override
    public boolean export(Database db) {

        Table[] tables = db.getTables();

        StringBuilder dot = new StringBuilder();
        dot.append("digraph \"" + db.getName() + "\"{");
        for(Table table : tables) {
            dot.append("\""+db.getName()+"_"+table.getName() + "\" [shape=box,label="+table.getName()+"];");
            Attribute[] attributes = table.getAttributes();
            for(Attribute attribute : attributes) {
                Map<String, Object> props = attribute.getProperties();
                if(props.containsKey(Attribute.PROPERTY_FOREIGN_KEY)) {
                    Foreign foreign = (Foreign) attribute.getProperties().get(Attribute.PROPERTY_FOREIGN_KEY);
                    String relation = "\""+db.getName()+"_"+table.getName()+"_"+attribute.getName()+"_"+foreign.getOrigin().getName()+"\"";
                    dot.append(relation +
                            " [shape=diamond,label=\""+attribute.getName()+"/"+foreign.getOrigin().getName()+"\"];");
                    if (props.containsKey(Attribute.PROPERTY_PRIMARY_KEY)) {
                        dot.append("\""+db.getName()+"_"+table.getName()+"\" [peripheries=2];");
                        dot.append("\""+db.getName()+"_"+table.getName()+"\"" + " -> " + relation + " [dir=none,color=\"black:white:black\",label=\"" + (props.containsKey(Attribute.PROPERTY_UNIQUE) ? "1" : "n") + "\"];");
                        dot.append(relation + " [peripheries=2];");
                    }else {
                        dot.append("\""+db.getName()+"_"+table.getName()+"\"" + " -> " + relation  + " [dir=none,label=\"" + (props.containsKey(Attribute.PROPERTY_UNIQUE) ? "1" : "n") + "\"];");
                    }
                    dot.append(relation + " -> " + "\""+db.getName()+"_"+foreign.getOrigin().getParentTable().getName() + "\" [dir=none,label=\"1\"];");
                    continue;
                }
                if(props.containsKey(Attribute.PROPERTY_PRIMARY_KEY)) {
                    dot.append("\""+db.getName()+"_"+table.getName()+"_"+attribute.getName() + "\" [label=<<u>"+attribute.getName()+"</u>>];");
                }else {
                    dot.append("\""+db.getName()+"_"+table.getName()+"_"+attribute.getName() + "\" [label="+attribute.getName()+"];");
                }
                dot.append("\""+db.getName()+"_"+table.getName()+"_"+attribute.getName()+"\"" + " -> " + "\""+db.getName()+"_"+table.getName() + "\" [dir=none];");
            }
        }
        dot.append("}");
        try (FileWriter fw = new FileWriter(outputFile)) {
            fw.write(dot.toString());
        }catch (IOException ex) {
            return false;
        }
        return true;
    }
}
