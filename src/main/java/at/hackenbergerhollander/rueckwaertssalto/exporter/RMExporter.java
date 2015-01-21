package at.hackenbergerhollander.rueckwaertssalto.exporter;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Attribute;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Table;
import com.tutego.jrtf.Rtf;
import com.tutego.jrtf.RtfText;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfDocfmt.*;
import static com.tutego.jrtf.RtfHeader.*;
import static com.tutego.jrtf.RtfInfo.*;
import static com.tutego.jrtf.RtfFields.*;
import static com.tutego.jrtf.RtfPara.*;
import static com.tutego.jrtf.RtfSectionFormatAndHeaderFooter.*;
import static com.tutego.jrtf.RtfText.*;
import static com.tutego.jrtf.RtfUnit.*;

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
     * @param outputFile the output file for the model
     */
    public RMExporter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Exports the database structer as a relational model
     * @see at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter#export(at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database)
     */
    @Override
    public boolean export(Database db) {

        Table[] tables = db.getTables();
        List<RtfText> lines = new ArrayList<>();
        for(Table table : tables) {
            Attribute[] attributes = table.getAttributes();
            for(Attribute attribute : attributes) {
                Map<String, Object> props = attribute.getProperties();
                RtfText text;
                if(props.containsKey(Attribute.PROPERTY_PRIMARY_KEY)) {
                    text = RtfText.underline(text);
                    if(props.containsKey(Attribute.PROPERTY_FOREIGN_KEY)) {
                        text = RtfText.dottedUnderline(underline(attribute.getProperties().get(Attribute.PROPERTY_FOREIGN_KEY)));
                    }
                } else {
                    text = RtfText.text(attribute.getName());
                }
                lines.add(text);
            }
        }
    }
}
