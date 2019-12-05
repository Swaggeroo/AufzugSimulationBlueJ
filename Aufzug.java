/**
 * Diese Klasse representiert einen Aufzug.
 * 
 * @Heilmann Frederick
 * @3.0
 */

public class Aufzug{
    private int minStockwerk;
    private int maxStockwerk;
    private int richtung = 0;//-1 runter 0 steht 1 hoch
    private int ziel[] = new int[0]; //Stockwerke die angefahren werden sollen
    private int last = 0;
    private int maxLast;
    private int nextZiel;
    private int tuerTick = 0; //Ticks/Sekunden die die Tür schon offen ist
    private float stockwerk;
    private float geschwindigkeit;
    private boolean tuer = false;
    private boolean error = false;
    private boolean wartung = false;
    private String ort;
    private String[] errors = new String[0];
    private Person[] fahrGaeste = new Person[0]; 
    //Namen für die Personen
    private String[] namen = {"Udo","Gandalf","Hainz","Günter","Horst","Angela","Angelo","Luis","Maik","Linus","Jonas","Fred","Gerrit","Tim","Georg","Silke","Frank","Silke",
            "Julia","Ines","Birgit","Frank","Torsten","Brigitte","Janina","Sarah","Ute","Sven","Kevin","Manuela","Christin","Brigitte","René","Tobias","Yvonne","Daniel",
            "Kerstin","Lucas","Andreas","Manuela","Gabriele","Lucas","Lucas","Kristian","Ursula","Marina","Birgit","Paul","Julia","Susanne","Mario","Thomas","Sebastian",
            "Christine","Kevin","Alexander","Florian","Dirk","Frank","Sven","Frank","Klaus","Lucas","Peter","Tim","Tim","Mathias","Sara","Bernd","Marko","Maximilian","Christine",
            "Leon","Sebastian","Klaudia","Nadine","Jana","Lukas","Erik","Jan","Juliane","Janina","Yvonne","Thorsten","Kristin","Claudia","Kathrin","Marie","Luca","Sophie","Mathias",
            "Christian","Mathias","Sandra","Thomas","Uwe","Jürgen","Franziska","Birgit","Peter","Robert","Torsten","Michelle","Tobias","Kathrin","Wolfgang","Alexander","Nicole","Maria",
            "Lukas","Simone","Mandy","Mario","Michelle","Robert","Frank","Sabrina","Andrea","Mike","Anne","Niklas","Ralf","Klaudia","Vanessa","Florian","Ulrich","Sophie","Daniel",
            "Kristian","Mario","Ralph","Daniel","Stephan","Jan","Annett","Barbara","Uwe","Jürgen","Ulrich","Sarah","Bernd","Ulrich","Bernd","Ralf","Christina","Karin","Christin",
            "Sebastian","Simone","Markus","Simone","Dominik","Lea","Ines","Juliane","Bernd","Katrin","Marcel","Thorsten","Marco","Karolin","Thomas","Marcel","Petra","Markus","Sarah",
            "Barbara","Dennis","Susanne","Mandy","Lisa","Erik","Ines","Brigitte","Katrin","Leon","Annett"};

    public Aufzug(int minStockwerk,int maxStockwerk,float geschwindigkeit,int maxLast, String ort){
        this.minStockwerk = minStockwerk;
        this.maxStockwerk = maxStockwerk;
        this.geschwindigkeit = geschwindigkeit;
        this.maxLast = maxLast;
        stockwerk = minStockwerk;
        this.ort = ort;
    }
    
    public float getStockwerk(){
        return stockwerk;
    }

    public int getRichtung(){
        return richtung;
    }

    public String getOrt(){
        return ort;
    }

    public int getNextZiel(){
        return nextZiel;
    }

    public String[] getErrors(){
        return errors;
    }

    public boolean getTuer(){
        return tuer;
    }

    public int getLast(){
        return last;
    }

    public int getMaxStock(){
        return maxStockwerk;
    }

    public int getMinStock(){
        return minStockwerk;
    }

    public Person[] getPersonen(){
        return fahrGaeste;
    }
    
    //Funktion die alle Sekunde ausgeführt wird
    public void tick(){
        //nur ausführen falls nicht in Wartung
        if(!wartung){
            //falls die Tür zu ist
            if(!tuer){
                //Fahre den Aufzug
                stockwerk = stockwerk+(geschwindigkeit*richtung);
                //teste ob am Ziel
                if(richtung == 1){
                    if (stockwerk>nextZiel){
                        stockwerk = nextZiel;
                        pruefeZiel();    
                    }
                }else if(richtung == -1){
                    if(stockwerk<nextZiel){
                        stockwerk = nextZiel;
                        pruefeZiel();
                    }
                }
                if (stockwerk < minStockwerk){
                    stockwerk = minStockwerk;
                }
                if (stockwerk > maxStockwerk){
                    stockwerk = maxStockwerk;
                }
                if(stockwerk == nextZiel){
                    pruefeZiel();
                }
                berechneRichtung();
            }else{
                //falls tür offen ist
                tuerTick();
            }
        }
    }
    
    //Wird alle Sekunde ausgeführt wenn die Tür offen ist
    public void tuerTick(){
        //prüfen ob die maxLast überschritten wurde und ob die Tür 5 sek offen war
        if(tuerTick < 5 || last>maxLast){
            tuerTick++;
            //Personen zur Simulationszwecke ein- und aussteigen lassen
            if((getRandomIntegerBetweenRange(0,1)==1) && (last <= (maxLast+200))){
                einsteigenMensch(namen[(int)getRandomIntegerBetweenRange(0,namen.length-1)],(int)getRandomIntegerBetweenRange(50,100));
            }else{
                if(fahrGaeste.length != 0){
                    String name = fahrGaeste[(int)getRandomIntegerBetweenRange(0, fahrGaeste.length-1)].getName();
                    aussteigenMensch(name);
                }
            }
        }else if(tuerTick >= 5 && last<=maxLast){
            //Tür schließen wenn alles ok ist
            tuerTick = 0;
            tuer = false;
        }
    }
    
    //Eine Smarte berechnung in welche Richtung der Aufzug als nächstes fährt.
    private void berechneRichtung(){
        boolean foundNumber = false;
        //Falls der Aufzug als letztes nach oben gefahren ist
        if(richtung == 1){
            //prüfen ob ziele vorhanden sind
            if (this.ziel.length>0){
                //prüfen ob es noch ein Ziel gibt das weiter oben liegt
                for (int i = 0; i<=this.ziel.length-1; i++){
                    if (Float.valueOf(this.ziel[i])>stockwerk){
                        richtung = 1;
                        int groesteZahl = 1000;
                        //finde das Ziel das als nächstes kommt wenn der Fahrstuhl nach oben fährt
                        for (int o = 0; o<=this.ziel.length-1; o++){
                            if (ziel[o] > stockwerk && ziel[o] < groesteZahl){
                                groesteZahl = ziel[o];
                            }
                        }
                        //setze das nächste Ziel
                        nextZiel = groesteZahl;
                        foundNumber = true;
                    }
                }
                //wenn es keine Ziele mehr weiter oben gibt
                if(!foundNumber){
                    //kehre Richtung um und finde das nächste Ziel weiter unten
                    richtung = -1;
                    int kleinsteZahl = -1;
                    for (int i = 0; i<=this.ziel.length-1; i++){
                        if (ziel[i] < stockwerk && ziel[i] > kleinsteZahl){
                            kleinsteZahl = ziel[i];
                        }
                    }
                    //setze das nächste Ziel
                    nextZiel = kleinsteZahl;
                }
            }else{
                //falls keine Ziele mehr existieren -> stoppe
                richtung = 0;
            }
        //Falls der Aufzug als letztes nach unten gefahren ist
        }else if(richtung == -1){
            //prüfen ob ziele vorhanden sind
            if (this.ziel.length>0){
                //prüfen ob es noch ein Ziel gibt das weiter unten liegt
                for (int i = 0; i<=this.ziel.length-1; i++){
                    if (Float.valueOf(this.ziel[i])<stockwerk){
                        richtung = -1;
                        int kleinsteZahl = -1;
                        //finde das Ziel das als nächstes kommt wenn der Fahrstuhl nach unten fährt
                        for (int o = 0; o<=this.ziel.length-1; o++){
                            if (ziel[o] > kleinsteZahl && ziel[o] < stockwerk){
                                kleinsteZahl = ziel[o];
                            }
                        }
                        //setze das nächste Ziel
                        nextZiel = kleinsteZahl;
                        foundNumber = true;
                    }
                }
                //wenn es keine Ziele mehr weiter unten gibt
                if(!foundNumber){
                    //kehre Richtung um und finde das nächste Ziel weiter oben
                    richtung = 1;
                    int groesteZahl = 1000;
                    for (int i = 0; i<=this.ziel.length-1; i++){
                        if (ziel[i] > stockwerk && ziel[i] < groesteZahl){
                            groesteZahl = ziel[i];
                        }
                    }
                    //setze das nächste Ziel
                    nextZiel = groesteZahl;
                }
            }else{
                //falls keine Ziele mehr existieren -> stoppe
                richtung = 0;
            }
        //Falls der Aufzug gestoppt hatte
        }else if(richtung == 0){
            //prüfen ob ziele vorhanden sind
            if(ziel.length > 0){
                //prüfe ob das eingegebene Ziel oberhalb oder unterhalb liegt
                if(Float.valueOf(ziel[0]) > stockwerk){
                    richtung = 1;
                }
                if(Float.valueOf(ziel[0]) < stockwerk){
                    richtung = -1;
                }
                //setze das nächste Ziel
                nextZiel = ziel[0];
            }
        }
    }
    
    //Ziel zum ziel Array hinzufügen
    public void ergaenzeZiel(int ziel){
        if(ziel == stockwerk){
            System.out.println("Bereits auf diesem Stockwerk");
        }else if(ziel>=minStockwerk && ziel <= maxStockwerk){
            int[] zwischenspeicher = new int[this.ziel.length+1];
            for(int i = 0; i<=this.ziel.length-1; i++){
                zwischenspeicher[i] = this.ziel[i];
            }
            zwischenspeicher[zwischenspeicher.length-1] = ziel;
            this.ziel = zwischenspeicher;
            berechneRichtung();
        }else{
            System.out.println("Stockwerk nicht erreichbar");
        }
    }

    //Überprüfe ob der Fahrstuhl an einem Ziel angekommen ist und wenn dieses Ziel löschen
    public void pruefeZiel(){
        for (int i = 0; i<=this.ziel.length-1; i++){
            if(ziel[i] == stockwerk){
                loescheZiel(i);
                berechneRichtung();
                tuer = true;
            }
        }
    }

    //Lösche Ziel aus dem Array
    public void loescheZiel(int index){
        int zaeler = 0;
        int[] zwischenSpeicher = new int[this.ziel.length-1];
        for (int i = 0; i<=this.ziel.length-1; i++){
            if (i != index){
                zwischenSpeicher[zaeler] = this.ziel[i];
                zaeler++;
            }
        }
        this.ziel = zwischenSpeicher;
    }

    //Füge eine Person dem Aufzug hinzu und berechne das Gewicht neu
    public void einsteigenMensch(String name, int gewicht){
        Person newPerson = new Person(gewicht, name);
        Person[] zwischenspeicher = new Person[this.fahrGaeste.length+1];
        for(int i = 0; i<=this.fahrGaeste.length-1; i++){
            zwischenspeicher[i] = this.fahrGaeste[i];
        }
        zwischenspeicher[zwischenspeicher.length-1] = newPerson;
        this.fahrGaeste = new Person[zwischenspeicher.length];
        this.fahrGaeste = zwischenspeicher;
        berechneLast();
    }

    //Entferne eine Person aus dem Aufzug und berechne das Gewicht neu
    public void aussteigenMensch(String name){
        int indexDerPerson = -1;
        for(int i = 0; i<=this.fahrGaeste.length-1; i++){
            if (fahrGaeste[i].getName().equals(name)){
                indexDerPerson = i;
            }
        }
        if (indexDerPerson != -1){
            Person[] zwischenspeicher = new Person[fahrGaeste.length-1];
            int zaeler = 0;
            for (int i = 0; i<=this.fahrGaeste.length-1; i++){
                if (i!=indexDerPerson){
                    zwischenspeicher[zaeler] = fahrGaeste[i];
                    zaeler++;
                }
            }
            fahrGaeste = zwischenspeicher;
        }
        berechneLast();
    }
    
    //berechne die Gesamtlast die durch alle Personen im Aufzug auftritt
    private void berechneLast(){
        last = 0;
        for(int i =0; i<=fahrGaeste.length-1; i++){
            last += fahrGaeste[i].getGewicht();
        }
        boolean schonUeberladen = false;
        //teste ob schon ein Error gesendet wurde
        for(int i=0;i<=errors.length-1;i++){
            if(errors[i].equals("Überladen")){
                schonUeberladen = true;
            }
        }
        //Sende ein Error falls der Aufzug Überladen ist und noch kein error gesendet wurde
        if(last > maxLast){
            if(!schonUeberladen){
                error("Überladen");
            }
        }else{
            //error löschen falls der Aufzug nicht mehr Überladen ist
            for(int i=0;i<=errors.length-1;i++){
                if(errors[i].equals("Überladen")){
                    String[] zwischenspeicher = new String[errors.length-1];
                    int zaehler = 0;
                    for(int o=0;o<=errors.length-1;o++){
                        if(o!=i){
                            zwischenspeicher[zaehler] = errors[o];
                            zaehler++;
                        }
                    }
                    errors = zwischenspeicher;
                }
            }
        }
    }
    
    //Error auffangen
    public void error(String error){
        this.error = true;
        String[] zwischenspeicher = new String[this.errors.length+1];
        for(int i = 0; i<=this.errors.length-1; i++){
            zwischenspeicher[i] = this.errors[i];
        }
        zwischenspeicher[zwischenspeicher.length-1] = error;
        this.errors = new String[zwischenspeicher.length];
        this.errors = zwischenspeicher;
    }
    
    /*
     * Zur wartung fahren:
     * Ein stockwerk unter min Stockwerk fahren
     * Alle Ziele löschen und personen Aussteigen lassen
     * Aufzug stoppen
    */
    public void wartung(){
        error("Wartung");
        stockwerk=minStockwerk-1;
        wartung = true;
        fahrGaeste = new Person[0];
        ziel = new int[0];
        geschwindigkeit=0;
    }
    
    //Methode für Zufällige Integer zu generieren
    private static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
