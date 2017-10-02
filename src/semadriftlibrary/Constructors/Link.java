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

/**
 *
 * @author andreadisst
 */
public class Link {
    
    private final Version from;
    private final Version to;
    private final ConceptPair pair;
    
    /**
    * Constructor 
    * @param from This is the name of the first ontology being compared.
    * @param to This is the name of the second ontology being compared.
    * @param pair This is the ConceptPair that creates a link between the two ontologies.
    */
    public Link(Version from, Version to, ConceptPair pair){
        this.from = from;
        this.to = to;
        this.pair = pair;
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
    * @return This method returns the ConceptPair that creates a link between the two ontologies.
    */
    public ConceptPair getPair(){
        return pair;
    }
    
}
