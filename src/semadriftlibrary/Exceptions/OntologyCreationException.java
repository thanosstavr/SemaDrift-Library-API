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
package semadriftlibrary.Exceptions;

/**
 *
 * @author stelios
 */
public class OntologyCreationException extends Exception{
    
    public OntologyCreationException() {
        super();
    }
    
    public OntologyCreationException(String message)
    {
       super(message);
    }
    
    public OntologyCreationException(Throwable cause)
    {
       super(cause);
    }
    
    public OntologyCreationException(String message, Throwable cause)
    {
       super(message, cause);
    }
    
}
