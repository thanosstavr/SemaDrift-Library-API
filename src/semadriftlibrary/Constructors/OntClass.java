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
public class OntClass {
    
    private final String IRI;
    private final String name;
    private final ArrayList<OntClass> subclasses;
    private final ArrayList<OntProperty> properties;
    
    public OntClass(){
        IRI = "";
        name = "";
        subclasses = new ArrayList<>();
        properties = new ArrayList();
    }
    
    public OntClass(String anIRI, String aName, ArrayList<OntClass> someSubclasses, ArrayList<OntProperty> someProperties){
        IRI = anIRI;
        name = aName;
        subclasses = someSubclasses;
        properties = someProperties;
    }
    
    public String getIRI(){
        return IRI;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<OntClass> getSubclasses(){
        return subclasses;
    }
    
    public ArrayList<OntProperty> getProperties(){
        return properties;
    }
    
}
