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
    private static final Pattern IMG_FILTER = Pattern.compile("\\.\\./upload.*?\\.png",Pattern.MULTILINE | Pattern.DOTALL);

    public void echoPage() throws IOException {
        Scanner sc = new Scanner(new URL(uri).openStream(), StandardCharsets.UTF_8);
        System.out.println("FIRST METHOD SUCCESSFULLY STARTED");
        System.out.println("THERE ARE STILL TODOS");
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
                Matcher matcherNation = NATION_ZEILE.matcher(currentNation);
                String name = "";
                String capital = "";
                String area = "";
                String numInhibitats = "";
                String flagRef = "";
                int i = 1;
                while (matcherNation.find()){
                    String str;
                    str = matcherNation.group();
                    switch (i) {
                        case 1: str = matcherNation.group();
                        name = replaceTags(str);
                        break;
                        case 3: str = matcherNation.group();
                        capital = replaceTags(str);
                        break;
                        case 4: str = matcherNation.group();
                        numInhibitats = replaceTags(str);
                        break;
                        case 5: str = matcherNation.group();
                        area = replaceTags(str);
                        break;
                        case 7: str = matcherNation.group();
                        flagRef = convertToImg(str);
                        break;
                        default: {}

                    }
                    i++;
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

    public String convertToImg(String input){
        Matcher matcherImg = IMG_FILTER.matcher(input);
        if (matcherImg.find()){
            String cleaned = matcherImg.group().replaceAll("\\n|\\r", "").replaceAll("&.*","");
            return cleaned;
        }
        return "none found";
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
