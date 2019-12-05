import java.util.*;

/** 
 * Das ist die Kontrolleinheit die alle Fahrstühle erzeugt und auch jede Sekunde
 * den Fahrstühlen mitteilt das sie fahren sollen.
 * 
 * @Heilmann Frederick
 * @2.0
 */

public class Kontrolleinheit
{
    private Timer timer;
    private int zealer = 0;
    /*
     * Hier werden alle Aufzüge erzeugt:
     * (Das Minimale Stockwerk, Das Maximale Stockwerk, die Geschwindigkeit
     * in Stockwerke die Sekunde, Maximale Last in Kg, Der Name/Ort("1M = Erster Abschnitt Mitte"))
     */
    private Aufzug[] Aufzuege = {new Aufzug(1,60,0.5F,600,"1L"),new Aufzug(1,60,0.5F,600,"1L"),new Aufzug(1,60,0.5F,600,"1L"),
            new Aufzug(1,60,0.5F,600,"1M"),new Aufzug(1,60,0.5F,600,"1M"),new Aufzug(1,60,0.5F,600,"1M"),
            new Aufzug(1,60,0.5F,600,"1R"),new Aufzug(1,60,0.5F,600,"1R"),new Aufzug(1,60,0.5F,600,"1R"),
            new Aufzug(61,110,0.5F,600,"2R"),new Aufzug(61,110,0.5F,600,"2R"),
            new Aufzug(61,110,0.5F,600,"2L"),new Aufzug(61,110,0.5F,600,"2L"),
            new Aufzug(111,150,0.5F,600,"3")};
    //Player für Fahrstuhlmusik
    private MusikPlayer player;

    public Kontrolleinheit() {
        player = new MusikPlayer();
        Timer timer = new Timer();
        //Timer der den Code alle Sekunde ausführt
        timer.scheduleAtFixedRate(new TimerTask(){
                public void run(){
                    //Die Anzeige wird aktuallisiert
                    System.out.print('\u000C');
                    System.out.println("working at fixed rate delay");
                    System.out.println("Ort | Stockwerk  |  Richtung | Ziel  |  Tür  |  Last  |  Personen ");
                    for (int i = 0; i<=Aufzuege.length-1; i++){
                        System.out.print(Aufzuege[i].getOrt()+"\t");
                        System.out.print(String.valueOf(Aufzuege[i].getStockwerk())+"\t\t");
                        System.out.print(String.valueOf(Aufzuege[i].getRichtung())+"\t");
                        System.out.print(String.valueOf(Aufzuege[i].getNextZiel())+"\t");
                        if(Aufzuege[i].getTuer()){
                            System.out.print("auf"+"\t");
                        }else{
                            System.out.print("zu"+"\t");
                        }
                        System.out.print(String.valueOf(Aufzuege[i].getLast())+" kg"+"\t");
                        Person[] personen = Aufzuege[i].getPersonen();
                        if(personen.length!=0){
                            for(int o=0; o<=personen.length-1; o++){
                                System.out.print(personen[o].getName()+" ");
                            }
                        }
                        System.out.println();
                        Aufzuege[i].tick();
                    }
                    System.out.println();

                    //Alle Errors aus allen Fahrstühlen auslesen
                    System.out.println("Errors:");
                    for (int i = 0; i<=Aufzuege.length-1; i++){
                        String[] errors = Aufzuege[i].getErrors();
                        System.out.print(Aufzuege[i].getOrt()+": ");
                        for (int o = 0; o<=errors.length-1; o++){
                            System.out.print(errors[o]+" ");
                        }
                        System.out.println();
                    }
                    System.out.println();

                    //Überschrift für sonstige errors die bei den Fahrstühlen entstehen
                    System.out.println("Sonstige errors:");

                    //Für die Simulation alle 15 Sekunden ein Ziel den Fahrstühlen mitteilen
                    if(zealer >= 15){
                        zealer = 0;
                        for (int i = 0; i<=Aufzuege.length-1; i++){
                            int number = (int) getRandomIntegerBetweenRange(Aufzuege[i].getMinStock(),Aufzuege[i].getMaxStock());
                            Aufzuege[i].ergaenzeZiel(number);
                        }
                    }else{
                        zealer++;
                    }
                }
            },1,1000);
    }

    //Manuell einem Fahrstul ein Ziel mitteilen
    public void fahreAufzug(int index,int stockwerk){
        if(index<=Aufzuege.length){
            Aufzuege[index].ergaenzeZiel(stockwerk);
        }
    }

    //Manuel Menschen in den Aufzug einsteigen lassen
    public void einsteigenAufzug(int index,int gewicht,String name){
        if(index<=Aufzuege.length){
            Aufzuege[index].einsteigenMensch(name, gewicht);
        }
    }

    //Notfall auslösen
    public void notfall(int index){
        if(index<=Aufzuege.length){
            Aufzuege[index].error("Notfall");
        }
    }
    
    //Fahrstul zur Wartung schicken -> siehe Fahrstuhl
    public void wartung(int index){
        if(index<=Aufzuege.length){
            Aufzuege[index].wartung();
        }
    }

    //Fahrstuhlmusik abspielen
    public void spieleMusik(){
        player.starteAbspielen("./fahrstulmusik.mp3");
    }

    //Fahrstuhlmusik stoppen
    public void stoppeMusik(){
        player.stop();
    }
    
    //Methode für Zufällige Integer zu generieren
    private static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
