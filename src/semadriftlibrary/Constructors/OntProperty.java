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
public class OntProperty {
    
    private final String name;
    private final String domain;
    private final String range;
    private boolean isDatatype = true;
    
    public OntProperty(String aName, String aDomain, String aRange, boolean anisDatatype){
        name = aName;
        domain = aDomain;
        range = aRange;
        isDatatype = anisDatatype;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDomain(){
        return domain;
    }
    
    public String getRange(){
        return range;
    }
    
    public boolean isDatatype(){
        return isDatatype;
    }
    
    public boolean isObjectProperty(){
        return !isDatatype;
        //assumes that if not the one, it is the other
    }
    
    
}
