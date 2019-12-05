/**
 * Klasse für die erstellung von Personen.
 * 
 * @Heilmann Frederick
 * @1.0
 */

public class Person{
    //Person mit Name und Gewicht anlegen
    int gewicht;
    String name;
    public Person(int gewicht, String name){
        this.gewicht = gewicht;
        this.name = name;
    }
    
    public int getGewicht(){
        return gewicht;
    }

    public String getName(){
        return name;
    }
}
