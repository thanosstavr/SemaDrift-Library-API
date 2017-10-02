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

import java.util.Comparator;
import org.semanticweb.owlapi.model.OWLClass;

/**
 *
 * @author andreadisst
 */
public class ClassNameSorter implements Comparator<OWLClass>{

        @Override
        public int compare(OWLClass one, OWLClass another){
            return getShortForm(one.getIRI().toString()).compareTo(getShortForm(another.getIRI().toString()));
        }
        
        private String getShortForm(String iri){
        if(iri.contains("#")){
            iri = iri.substring(iri.lastIndexOf("#")+1);
        }
        iri = iri.replace(">", "");
        return iri;
    }

}
