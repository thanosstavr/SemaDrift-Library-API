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
package semadriftlibrary;

import semadriftlibrary.Constructors.Concept;

/**
 *
 * @author andreadisst
 */
public class Aspects {
    
    private final Simmetrics sim;
    
    public Aspects(){
        sim = new Simmetrics();
    }
    
    public double label(Concept one, Concept two){
        if(one.getLabels().isEmpty() && two.getLabels().isEmpty()){
            return 1;
        }
        else if(one.getLabels().isEmpty() || two.getLabels().isEmpty()){
            return 0;
        }
        return sim.MongeElkanSimilarity(one.getLabels().get(0), two.getLabels().get(0)); //compare only first label
    }
    
    public double intensional(Concept one, Concept two){
        if(one.getPropertiesAsDomain().isEmpty() && two.getPropertiesAsDomain().isEmpty() && one.getPropertiesAsRange().isEmpty() && two.getPropertiesAsRange().isEmpty()){
            return 1;
        }else if(!one.getPropertiesAsDomain().isEmpty() && !two.getPropertiesAsDomain().isEmpty()){
            double jaccardAsDomain = sim.JaccardToTriplets(one.getPropertiesAsDomain(), two.getPropertiesAsDomain(), true);
            double sizeAsDomain = one.getPropertiesAsDomain().size() + two.getPropertiesAsDomain().size();
            
            if(!one.getPropertiesAsRange().isEmpty() && !two.getPropertiesAsRange().isEmpty()){
                double jaccardAsRange = sim.JaccardToTriplets(one.getPropertiesAsRange(), two.getPropertiesAsRange(), false);
                double sizeAsRange = one.getPropertiesAsRange().size() + two.getPropertiesAsRange().size();
                
                return ( (sizeAsDomain * jaccardAsDomain) + (sizeAsRange * jaccardAsRange) ) / (sizeAsDomain + sizeAsRange); //weighted average
            }else{
                return jaccardAsDomain;
            }
        }else if(!one.getPropertiesAsRange().isEmpty() && !two.getPropertiesAsRange().isEmpty()){
            double jaccardAsRange = sim.JaccardToTriplets(one.getPropertiesAsRange(), two.getPropertiesAsRange(), false);
            
            return jaccardAsRange;
        }else{
            return 0;
        }
    }
    
    public double extensional(Concept one, Concept two){
        if(one.getInstances().isEmpty() && two.getInstances().isEmpty()){
            return 1;
        }else if(one.getInstances().isEmpty() || two.getInstances().isEmpty()){
            return 0;
        }
        return sim.JaccardToStringSets(one.getInstances(), two.getInstances());
    }
    
    public double whole(Concept one, Concept two){
        return ( label(one,two) + intensional(one,two) + extensional(one,two) ) / 3;
    }
    
}
