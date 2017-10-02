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
public class RankedConcept {
    
    private final String rank;
    private final Chain chain;
    private final String strength;
    
    /**
    * Constructor 
    * @param rank This is the rank of this concept.
    * @param chain This is the chain that begins with this concept.
    * @param strength This is the strength of the chain.
    */
    public RankedConcept(String rank, Chain chain, String strength){
        this.rank = rank;
        this.chain = chain;
        this.strength = strength;
    }
    
    /**
    * @return This method returns the rank of this concept.
    */
    public String getRank(){
        return rank;
    }
    
    /**
    * @return This method returns the chain that begins with this concept.
    */
    public Chain getChain(){
        return chain;
    }
    
    /**
    * @return This method returns the strength value of this chain.
    */
    public String getStrength(){
        return strength;
    }
    
}
