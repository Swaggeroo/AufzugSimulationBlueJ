public class Aufzug{
    private int minStockwerk;
    private int maxStockwerk;
    private float stockwerk;
    private float geschwindigkeit;
    private int richtung = 0;//-1 runter 0 steht 1 hoch
    private int ziel[] = new int[0]; //Stockwerke die angefahren werden sollen
    private int maxLast;
    private int last = 0;
    private int nextZiel;
    private int tuerTick = 0;
    Person[] fahrGaeste = new Person[0];
    String ort;
    boolean tuer = false;
    boolean error = false;
    boolean wartung = false;
    String[] errors = new String[0];
    String[] namen = {"Udo","Gandalf","Hainz","Günter","Horst","Angela","Angelo","Luis","Maik","Linus","Jonas","Fred","Gerrit","Tim","Georg","Silke","Frank","Silke",
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

    public void ergaenzeZiel(int ziel){
        if(ziel == stockwerk){
            System.out.println("Bereits auf diesem Stockwerk");
        }else if(ziel>=minStockwerk && ziel <= maxStockwerk){
            int[] zwischenspeicher = new int[this.ziel.length+1];
            for(int i = 0; i<=this.ziel.length-1; i++){
                zwischenspeicher[i] = this.ziel[i];
            }
            zwischenspeicher[zwischenspeicher.length-1] = ziel;
            //this.ziel = new int[zwischenspeicher.length];
            this.ziel = zwischenspeicher;
            berechneRichtung();
        }else{
            System.out.println("Stockwerk nicht erreichbar");
        }
    }

    public void pruefeZiel(){
        for (int i = 0; i<=this.ziel.length-1; i++){
            if(ziel[i] == stockwerk){
                loescheZiel(i);
                berechneRichtung();
                tuer = true;
            }
        }
    }

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

    private void berechneRichtung(){
        boolean foundNumber = false;
        if(richtung == 1){
            if (this.ziel.length>0){
                for (int i = 0; i<=this.ziel.length-1; i++){
                    if (Float.valueOf(this.ziel[i])>stockwerk){
                        richtung = 1;
                        int groesteZahl = 1000;
                        for (int o = 0; o<=this.ziel.length-1; o++){
                            if (ziel[o] > stockwerk && ziel[o] < groesteZahl){
                                groesteZahl = ziel[o];
                            }
                        }
                        nextZiel = groesteZahl;
                        foundNumber = true;
                    }
                }
                if(!foundNumber){
                    richtung = -1;
                    int kleinsteZahl = -1;
                    for (int i = 0; i<=this.ziel.length-1; i++){
                        if (ziel[i] < stockwerk && ziel[i] > kleinsteZahl){
                            kleinsteZahl = ziel[i];
                        }
                    }
                    nextZiel = kleinsteZahl;
                }
            }else{
                richtung = 0;
            }
        }else if(richtung == -1){
            if (this.ziel.length>0){
                for (int i = 0; i<=this.ziel.length-1; i++){
                    if (Float.valueOf(this.ziel[i])<stockwerk){
                        richtung = -1;
                        int kleinsteZahl = -1;
                        for (int o = 0; o<=this.ziel.length-1; o++){
                            if (ziel[o] > kleinsteZahl && ziel[o] < stockwerk){
                                kleinsteZahl = ziel[o];
                            }
                        }
                        nextZiel = kleinsteZahl;
                        foundNumber = true;
                    }
                }
                if(!foundNumber){
                    richtung = 1;
                    int groesteZahl = 1000;
                    for (int i = 0; i<=this.ziel.length-1; i++){
                        if (ziel[i] > stockwerk && ziel[i] < groesteZahl){
                            groesteZahl = ziel[i];
                        }
                    }
                    nextZiel = groesteZahl;
                }
            }else{
                richtung = 0;
            }
        }else if(richtung == 0){
            if(ziel.length > 0){
                if(Float.valueOf(ziel[0]) > stockwerk){
                    richtung = 1;
                }
                if(Float.valueOf(ziel[0]) < stockwerk){
                    richtung = -1;
                }
                nextZiel = ziel[0];
            }
        }
    }

    private void berechneLast(){
        last = 0;
        for(int i =0; i<=fahrGaeste.length-1; i++){
            last += fahrGaeste[i].getGewicht();
        }
        boolean schonUeberladen = false;
        for(int i=0;i<=errors.length-1;i++){
            if(errors[i].equals("Überladen")){
                schonUeberladen = true;
            }
        }
        if(last > maxLast){
            if(!schonUeberladen){
                error("Überladen");
            }
        }else{
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

    public void tick(){
        if(!wartung){
            if(!tuer){
                stockwerk = stockwerk+(geschwindigkeit*richtung);
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
                tuerTick();
            }
        }
    }

    public void tuerTick(){
        if(tuerTick < 5 || last>maxLast){
            tuerTick++;
            if((getRandomIntegerBetweenRange(0,1)==1) && (last <= (maxLast+200))){
                einsteigenMensch(namen[(int)getRandomIntegerBetweenRange(0,namen.length-1)],(int)getRandomIntegerBetweenRange(50,100));
            }else{
                if(fahrGaeste.length != 0){
                    String name = fahrGaeste[(int)getRandomIntegerBetweenRange(0, fahrGaeste.length-1)].getName();
                    aussteigenMensch(name);
                }
            }
        }else if(tuerTick >= 5 && last<=maxLast){
            tuerTick = 0;
            tuer = false;
        }
    }

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

    public void wartung(){
        error("Wartung");
        stockwerk=minStockwerk-1;
        wartung = true;
        fahrGaeste = new Person[0];
        ziel = new int[0];
        geschwindigkeit=0;
    }

    private static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
