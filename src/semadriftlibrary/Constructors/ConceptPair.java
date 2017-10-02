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

import semadriftlibrary.Constants.Constants;

/**
 *
 * @author andreadisst
 */
public class ConceptPair {
    
    private final Concept from;
    private final Concept to;
    private final double stability;
    
    /**
    * Constructor 
    * @param from This is the first concept being compared.
    * @param to This is the second concept being compared.
    * @param stability This is the stability value between the two compared concepts. 
    */
    public ConceptPair(Concept from, Concept to, double stability){
        this.from = from;
        this.to = to;
        this.stability = stability;
    }
    
    /**
    * @return This method returns the name of the first concept being compared. 
    */
    public String getFrom(){
        return from.getName();
    }
    
    /**
    * @return This method returns the IRI of the first concept being compared. 
    */
    public String getFromIRI(){
        return from.getIRI();
    }
    
    /**
    * @return This method returns the name of the second concept being compared. 
    */
    public String getTo(){
        return to.getName();
    }
    
    /**
    * @return This method returns the stability value between the two compared concepts, in String format. 
    */
    public String getStability(){
        return Constants.formatter.format(stability);
    }
    
    /**
    * @return This method returns the stability value between the two compared concepts. 
    */
    public double getStabilityValue(){
        return stability;
    }
    
}
