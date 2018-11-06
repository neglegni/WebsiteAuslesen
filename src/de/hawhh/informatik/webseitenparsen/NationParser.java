package de.hawhh.informatik.webseitenparsen;

import de.hawhh.informatik.Nation;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.valueOf;

public class NationParser {

    private String uri;


    public NationParser(String uri) {
        this.uri= uri;
    }
    // Regulärer Ausdruck für den Anfang der Tabelle
    private static final Pattern TABELLE_BEGIN = Pattern.compile("<h2><span class=\"mw-headline\" id=\"Tabelle\">Tabelle</span>.*?</h2>");
    //Regulärer Ausdruck für das Ende der Tabelle.
    private static final Pattern TABELLE_END = Pattern.compile(".*?</table>");
    private static final Pattern TABELLE_EINTRAG = Pattern.compile("<tr>.*?</tr>",Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern NATION_ZEILE = Pattern.compile("<td.*?>.*?</td>", Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern NATION_SPALTE = Pattern.compile("<.*?>", Pattern.MULTILINE | Pattern.DOTALL);
    private static final String ZAHL = "\\d";
    /**
     * TODO IDEE: Lesen des <tr></tr>-Bereichs --> extrahieren , hinterher weiter Zerstückeln mithilfe class Nation (statt Map(Beispiel))
     <tr>
     <td><i><a href="Abchasien.html" title="Abchasien">Abchasien</a></i><sup id="cite_ref-Anerkennung_A_9-0" class="reference"><a href="#cite_note-Anerkennung_A-9">&#91;9&#93;</a></sup>
     </td>
     <td><i><a href="Republik.html" title="Republik">Republik</a> Abchasien</i><sup id="cite_ref-Langform_10-0" class="reference"><a href="#cite_note-Langform-10">&#91;10&#93;</a></sup>
     </td>
     <td><a href="Sochumi.html" title="Sochumi">Sochumi</a>
     </td>
     <td style="text-align:right">240.000
     </td>
     <td style="text-align:right">8600
     </td>
     <td style="text-align:right">28
     </td>
     <td style="text-align:center"><span style="display:none;">Abchasien</span><a href="Datei_Flag_of_the_Republic_of_Abkhazia.html" class="image" title="Abchasien"><img alt="Abchasien" src="../../../upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Flag_of_the_Republic_of_Abkhazia.svg/30px-Flag_of_the_Republic_of_Abkhazia.svg.png" width="30" height="15" class="noviewer" srcset="//upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Flag_of_the_Republic_of_Abkhazia.svg/45px-Flag_of_the_Republic_of_Abkhazia.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Flag_of_the_Republic_of_Abkhazia.svg/60px-Flag_of_the_Republic_of_Abkhazia.svg.png 2x" data-file-width="600" data-file-height="300" /></a>
     </td>
     <td>ABC
     </td>
     <td>—
     </td>
     <td>
     </td>
     <td>Abkhazia
     </td>
     <td>Aṗsny (<a href="Abchasische_Sprache.html" title="Abchasische Sprache">Abchasisch</a>), Abchazija (<a href="Russische_Sprache.html" title="Russische Sprache">Russisch</a>)
     </td></tr>
     * */


    public void echoPage() throws IOException {
        Scanner sc = new Scanner(new URL(uri).openStream(), StandardCharsets.UTF_8);
        System.out.println("FIRST METHOD SUCCESSFULLY STARTED");
        System.out.println("THERE ARE STILL TODOS");
        // TODO
        while(sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
        sc.close();
    }

    public List<Nation> contentToNationList() throws IOException {
        List<Nation> nations = new ArrayList<>();
        Scanner sc = new Scanner(new URL(uri).openStream(), StandardCharsets.UTF_8);

        System.out.println("SECOND STEP SUCCESSFULLY PERFORMED");
        System.out.println("THERE ARE STILL TODOS");

        // TODO
        // Positionieren des Scanners vor den Beginn der Tabelle. (Delimiter = Seperator)
        sc.useDelimiter(TABELLE_BEGIN);
        // Scanner sc auf die Erste Zeile des Tabelleninhaltes setzen.
        if (sc.hasNext()) {
            sc.next();
        }

        // Lesen des Bereichs bis zum Tabellenende, welches durch das Pattern
        // TABLE_END markiert ist.
        sc.useDelimiter(TABELLE_END);
        if (sc.hasNext()){
            String statesByAlphabet = sc.next();
            //Extrahieren der einzelnen Zeilen.
            Matcher matcherEintrag = TABELLE_EINTRAG.matcher(statesByAlphabet);
            while (matcherEintrag.find()){

                // Matcher merkt sich den letzten passenden String, dieser ist über group() abfragbar
                String currentNation = matcherEintrag.group();
                //System.out.println(currentNation);

                //TODO
                Matcher matcherNation = NATION_ZEILE.matcher(currentNation);
                String name = "";
                String capital = "";
                String area = "";
                String numInhibitats = "";
                String flagRef = "";
                int i = 1;
                while (matcherNation.find()){
                    String str;
                    System.out.println(i + ". ");
                    String g1 = matcherNation.group();
                    switch (i) {
                        case 1: str = matcherNation.group();
                            System.out.println(str);
                            name = replaceTags(str);
                        System.out.println(name);
                        break;
                        case 3: str = matcherNation.group();
                            System.out.println(str);
                            capital = replaceTags(str);
                        System.out.println(capital);
                        break;
                        case 4: str = matcherNation.group();
                            System.out.println(str);
                            numInhibitats = replaceTags(str);
                        System.out.println(numInhibitats);
                        break;
                        case 5: str = matcherNation.group();
                            System.out.println(str);
                            area = replaceTags(str);
                        System.out.println(area);
                        break;
                        case 7: str = matcherNation.group();
                            System.out.println(str);
                        flagRef = replaceTags(str);
                        System.out.println(flagRef);
                        break;
                        default: {}
                    }
                    i++;
                    //String nationAttribut = matcherNation.group();
                    //System.out.println(nationAttribut);
                    //System.out.println();
                }
                long numInhititatsLong= Long.valueOf(numInhibitats.replaceAll("[^\\d]",""));
                long areaLong = Long.valueOf(area.replaceAll("[^\\d]",""));
                Nation nation = new Nation(name, capital, areaLong, numInhititatsLong, flagRef);
                nations.add(nation);
            }
        }

        // In statesByAlphabet steht jetzt der gesamte HTML-Text für die Tabelle der Staaten.
        sc.close();
        return nations;
    }

    public String replaceTags(String spalte) {
        String cleaned = spalte.replaceAll(NATION_SPALTE.toString(), "")
                .replaceAll("\\n|\\r", "").replaceAll("\\.","")
                .replaceAll("&.*","");
        return cleaned;
    }

    public String toDigitString(String str) {
        return str.replaceAll("[^\\d]","");
    }
}
