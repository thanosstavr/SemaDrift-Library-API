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
public class Concept {
    
    private final String IRI;
    private final String name;
    private final ArrayList<String> labels;
    private final ArrayList<OntProperty> propertiesAsDomain;
    private final ArrayList<OntProperty> propertiesAsRange;
    private final ArrayList<String> instances;
    
    public Concept(){
        IRI = "";
        name = "";
        labels = new ArrayList<>();
        propertiesAsDomain = new ArrayList();
        propertiesAsRange = new ArrayList();
        instances = new ArrayList();
    }
    
    public Concept(String anIRI, String aName, ArrayList<String> someLabels, ArrayList<OntProperty> somePropertiesAsDomain, ArrayList<OntProperty> somePropertiesAsRange, ArrayList<String> someInstances){
        IRI = anIRI;
        name = aName;
        labels = someLabels;
        propertiesAsDomain = somePropertiesAsDomain;
        propertiesAsRange = somePropertiesAsRange;
        instances = someInstances;
    }
    
    public String getIRI(){
        return IRI;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<String> getLabels(){
        return labels;
    }
    
    public ArrayList<OntProperty> getPropertiesAsDomain(){
        return propertiesAsDomain;
    }
    
    public ArrayList<OntProperty> getPropertiesAsRange(){
        return propertiesAsRange;
    }
    
    public ArrayList<String> getInstances(){
        return instances;
    }
    
}
