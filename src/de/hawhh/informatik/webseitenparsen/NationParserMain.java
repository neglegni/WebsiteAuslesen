package de.hawhh.informatik.webseitenparsen;

import de.hawhh.informatik.Nation;
import de.hawhh.informatik.NationAttribute;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class NationParserMain {

    private static final String RESOURCE_PREFIX = "file:///";
    // RESOURCE_DIR um out/production/A2-WS1819/ verringert
    private static final String RESOURCE_DIR = "de.wikipedia.org/wiki/";
    private static final String RESOURCE_NAME = "Liste_der_Staaten_der_Erde.html";
    public static void main(String[] args) throws IOException {

        /** Wir arbeiten mit einer lokalen Datei / Kopie einer Wikipediaseite.
         * Die Referenz auf die Datei wird als String übergeben: file:///<absoluter Pfad zur Datei>
         * Path nationLocal = Paths.get( RESOURCE_DIR +RESOURCE_NAME); Pfad zur der Datei (Bezug das aktuelle Projekt)
         * nationLocal.toAbsolutePath() expandiert zum absoluten Pfad (als String)
         * "file:///“ beschreibt das Protokoll für das Öffnen Ressource    */

        Path nationLocal = Paths.get( RESOURCE_DIR +RESOURCE_NAME);
        NationParser parser = new NationParser(RESOURCE_PREFIX +
                nationLocal.toAbsolutePath());

        String option = null;

        /**
         * Liest die Programmparameter, die beim Start der Klasse übergeben werden
         * Wenn keine Parameter übergeben werden, dann wird die Methode echoPage aufgerufen.
         *
         * -e: Aufruf von echoPage
         * -l: Aufruf von contentToNationList und Nachbearbeitung der Liste
         */
        if (!(args.length == 0)) {
            option = args[0];

            //    Inhalt der HTML-Seite ausgeben

            if ("-e".equals(option)) {
                System.out.println("ECHO");
                parser.echoPage();
            } else if ("-l".equals(option)) {

                //Liste der Nation-Objekte erzeugen
                System.out.println("LIST OF NATIONS");
                List<Nation> nations = parser.contentToNationList();

                for (Nation nation : nations) {
                    System.out.println(nation);
                    //System.out.println(nation.toShortString(NationAttribute.FLAG)); mit toShortString lassen sich einzelne Eigenschaften von Nation abfragen
                }

                /*
                    Einige Bildreferenzen unter dem <img ...> Tag sind nicht wohlgeformt. Diese werden zum Schluss nochmals ausgegeben
                 */
                System.out.println("\nAND NOW NOT WELL FORMED FLAG REFS\n");
                for (Nation nation : nations) {
                    Pattern korrektPng = Pattern.compile("upload.*?\\.png$");
                    if (!korrektPng.matcher(nation.toShortString(NationAttribute.FLAG)).find()) {
                        System.out.println(nation.toShortString(NationAttribute.FLAG));
                    }
                }
                System.out.println(nations.size()); // 212 Elemente
            }
        } else {
            parser.echoPage();
        }

    }

}
