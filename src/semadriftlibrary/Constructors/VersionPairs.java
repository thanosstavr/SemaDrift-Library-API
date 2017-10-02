/*
 *    Copyright 2016 CERTH-ITI (http://certh.gr, http://iti.gr)
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package semadriftlibrary.Constructors;
import java.util.ArrayList;

/**
 *
 * @author andreadisst
 */
public class VersionPairs {
    
    private final Version from;
    private final Version to;
    private final ArrayList<ConceptPair> pairs;
    
    /**
    * Constructor 
    * @param from This is the name of the first ontology being compared.
    * @param to This is the name of the second ontology being compared.
    * @param pairs This is an arrayList of ConceptPair instances that include names of compared concepts and their stability.
    */
    public VersionPairs(Version from, Version to, ArrayList<ConceptPair> pairs){
        this.from = from;
        this.to = to;
        this.pairs = pairs;
    }
    
    /**
    * @return This method returns the name of the first ontology being compared. 
    */
    public String getFrom(){
        return from.getName();
    }
    
    /**
    * @return This method returns the name of the second ontology being compared. 
    */
    public String getTo(){
        return to.getName();
    }
    
    /**
    * @return This method returns an arrayList of ConceptPair instances that include names of compared concepts and their stability.
    */
    public ArrayList<ConceptPair> getPairs(){
        return pairs;
    }
    
    /**
    * @return This method returns every concept of the first ontology that has been compared. This arrayList will serve as the y axis of the table Concept-per-Concept.
    */
    public ArrayList<String> getYAxis(){
        ArrayList<String> yAxis = new ArrayList<>();
        for(ConceptPair pair : pairs){
            if(!yAxis.contains(pair.getFrom())){
                    yAxis.add(pair.getFrom());
            }
        }
        return yAxis;
    }
    
    /**
    * @return This method returns every concept of the second ontology that has been compared. This arrayList will serve as the x axis of the table Concept-per-Concept.
    */
    public ArrayList<String> getXAxis(){
        ArrayList<String> xAxis = new ArrayList<>();
        for(ConceptPair pair : pairs){
            if(!xAxis.contains(pair.getTo())){
                    xAxis.add(pair.getTo());
            }
        }
        return xAxis;
    }
    
    /**
    * @param from This is the first concept being compared.
    * @param to This is the second concept being compared.
    * @return This method returns the stability of a specified pair. This will serve as the main data of the table Concept-per-Concept.
    */
    public String getStabilityForPair(String from, String to){
        for(ConceptPair pair : pairs){
            if(pair.getFrom().equals(from) && pair.getTo().equals(to)){
                return pair.getStability();
            }
        }
        return "0.000";
    }
    
}
