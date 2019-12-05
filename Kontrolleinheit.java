import java.util.*;

public class Kontrolleinheit
{
    Timer timer;
    int zealer = 0;
    Aufzug[] Aufzuege = {new Aufzug(1,60,0.5F,600,"1L"),new Aufzug(1,60,0.5F,600,"1L"),new Aufzug(1,60,0.5F,600,"1L"),
            new Aufzug(1,60,0.5F,600,"1M"),new Aufzug(1,60,0.5F,600,"1M"),new Aufzug(1,60,0.5F,600,"1M"),
            new Aufzug(1,60,0.5F,600,"1R"),new Aufzug(1,60,0.5F,600,"1R"),new Aufzug(1,60,0.5F,600,"1R"),
            new Aufzug(61,110,0.5F,600,"2R"),new Aufzug(61,110,0.5F,600,"2R"),
            new Aufzug(61,110,0.5F,600,"2L"),new Aufzug(61,110,0.5F,600,"2L"),
            new Aufzug(111,150,0.5F,600,"3")};
    MusikPlayer player;

    public Kontrolleinheit() {
        player = new MusikPlayer();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
                public void run(){
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
                    System.out.println("Sonstige errors:");

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

    private static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public void fahreAufzug(int index,int stockwerk){
        if(index<=Aufzuege.length){
            Aufzuege[index].ergaenzeZiel(stockwerk);
        }
    }

    public void einsteigenAufzug(int index,int gewicht,String name){
        if(index<=Aufzuege.length){
            Aufzuege[index].einsteigenMensch(name, gewicht);
        }
    }

    public void notfall(int index){
        if(index<=Aufzuege.length){
            Aufzuege[index].error("Notfall");
        }
    }

    public void wartung(int index){
        if(index<=Aufzuege.length){
            Aufzuege[index].wartung();
        }
    }
    
    public void spieleMusik(){
        player.starteAbspielen("./fahrstulmusik.mp3");
    }
    
    public void stoppeMusik(){
        player.stop();
    }
}
