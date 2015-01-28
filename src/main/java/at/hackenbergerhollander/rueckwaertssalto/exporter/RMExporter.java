package at.hackenbergerhollander.rueckwaertssalto.exporter;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Attribute;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Table;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.attributeproperties.Foreign;
import com.tutego.jrtf.Rtf;
import com.tutego.jrtf.RtfPara;
import com.tutego.jrtf.RtfText;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tutego.jrtf.RtfText.textJoinWithSpace;
import static com.tutego.jrtf.RtfText.underline;

/**
 * Exporter for a relational model
 *
 * @author Hackenberger Christoph chackenberger@student.tgm.ac.at
 * @version 1.0
 */
public class RMExporter implements Exporter {


    private File outputFile;

    /**
     * Creates a new RMExporter
     *
     * @param outputFile the output file for the model
     */
    public RMExporter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Exports the database structer as a relational model
     *
     * @see at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter#export(at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database)
     */
    @Override
    public boolean export(Database db) throws Exception {

        Table[] tables = db.getTables();
        List<RtfText> lines = new ArrayList<>();
        for (Table table : tables) {
            Attribute[] attributes = table.getAttributes();

            List<RtfText> texts = new ArrayList<>();
            texts.add(RtfText.text(table.getName()));
            texts.add(RtfText.text("("));
            for (int i = 0; i < attributes.length; i++) {
                Attribute attribute = attributes[i];
                Map<String, Object> props = attribute.getProperties();
                RtfText text;
                if (props.containsKey(Attribute.PROPERTY_PRIMARY_KEY)) {
                    if (props.containsKey(Attribute.PROPERTY_FOREIGN_KEY)) {
                        Foreign foreign = (Foreign) attribute.getProperties().get(Attribute.PROPERTY_FOREIGN_KEY);
                        text = RtfText.dottedUnderline(underline(attribute.getName() + ": " + foreign.getOrigin().getParentTable().getName() + "." + foreign.getOrigin().getName()));
                    } else {
                        text = RtfText.underline(attribute.getName());
                    }
                } else if (props.containsKey(Attribute.PROPERTY_FOREIGN_KEY)) {
                    Foreign foreign = (Foreign) attribute.getProperties().get(Attribute.PROPERTY_FOREIGN_KEY);
                    text = RtfText.dottedUnderline(attribute.getName() + ": " + foreign.getOrigin().getParentTable().getName() + "." + foreign.getOrigin().getName());
                } else {
                    text = RtfText.text(attribute.getName());
                }
                if (i == 0) {
                    texts.add(text);
                } else {
                    texts.add(RtfText.text(", "));
                    texts.add(text);
                }
            }
            texts.add(RtfText.text(")"));
            texts.add(RtfText.lineBreak());
            lines.add(RtfText.text(texts.toArray(new RtfText[texts.size()])));
        }
        Rtf.rtf().section(RtfPara.p(lines.toArray(new RtfText[lines.size()]))).out(new FileWriter(this.outputFile));
        return true;
    }
}
